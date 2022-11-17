/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jdbc;

import db.interfaces.PatientTSManager;
import db.interfaces.SignalManager;
import db.interfaces.DoctorManager;
import db.interfaces.DBManager;
import db.interfaces.RoleManager;
import db.interfaces.UserManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agarc
 */
public class SQLiteManager implements DBManager {
    
    private Connection c;
    private PatientTSManager patient;
    private DoctorManager doctor;
    private SignalManager signalman;
    private UserManager user;
    private RoleManager role;
    
    public SQLiteManager(){
        super();
        this.connect();
    }
    
    @Override
    public PatientTSManager getPatientManager() {
        return patient;
    }

    @Override
    public DoctorManager getDoctorManager() {
        return doctor;
    }
    
    @Override
    public SignalManager getSignalManager() {
        return signalman;
    }
    
    public UserManager getUserManager() {
        return user;
    }
    
    public RoleManager getRoleManager() {
        return role;
    }
    
    public Connection getConnection() {
        return c;
    }
    
    
    
    @Override
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./db/TSApp.db");
            c.createStatement().execute("PRAGMA foreign_keys=ON");
            patient = new SQLitePatientTSManager(c);
            doctor = new SQLiteDoctorManager(c);
            signalman = new SQLiteSignalManager(c);
            user = new SQLiteUserManager(c);
            role = new SQLiteRoleManager(c);
            
        } catch (ClassNotFoundException exc) {
            exc.printStackTrace();

        } catch (SQLException ex) {
            Logger.getLogger(SQLiteManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void disconnect() {
        try {
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean createTables() {
      try{
          Statement stmt1 = c.createStatement();
          String sql1 = "CREATE TABLE role " 
                  + "(roleid INTEGER PRIMARY KEY AUTOINCREMENT, " 
                  + "type TEXT NOT NULL)"; 
          stmt1.execute(sql1);
          stmt1.close();
          
          Statement stmt2 = c.createStatement();
          String sql2 = "CREATE TABLE users " 
                  + "(userid INTEGER PRIMARY KEY AUTOINCREMENT, " 
                  + "userName TEXT NOT NULL, " 
                  + "userPassword TEXT NOT NULL, "
                  + "userRoleid FOREING KEY REFERENCES role(roleid) ON UPDATE CASCADE ON DELETE SET NULL) ";
          stmt2.execute(sql2);
          stmt2.close();
          
          Statement stmt3 = c.createStatement();
          String sql3 = "CREATE TABLE patient " 
                  + "(medical_card_number INTEGER PRIMARY KEY, " 
                  + "name TEXT NOT NULL, " 
                  + "surname TEXT NOT NULL, "
                  + "dob TEXT NOT NULL, " 
                  + "address TEXT NOT NULL, "
                  + "email TEXT NOT NULL, " 
                  + "diagnosis TEXT NOT NULL, "
                  + "allergies TEXT, "
                  + "gender TEXT NOT NULL, "
                  + "macAddress TEXT NOT NULL, "
                  + "userId FOREING KEY REFERENCES users(userid) ON UPDATE CASCADE ON DELETE SET NULL)";
          stmt3.execute(sql3);
          stmt3.close();
          
          Statement stmt4 = c.createStatement();
          String sql4 = "CREATE TABLE signal " 
                  + "(signalId INTEGER PRIMARY KEY AUTOINCREMENT, "
                  + "startDate TEXT NOT NULL, " 
                  + "sname TEXT NOT NULL, " 
                  + "samplingRate INTEGER NOT NULL, " 
                  + "fileName STRING NOT NULL, "
                  + "comment STRING, "
                  + "id_patient FOREING KEY REFERENCES patient(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL)";
          stmt4.executeUpdate(sql4);
          stmt4.close();
          
          Statement stmt5 = c.createStatement();
          String sql5 = "CREATE TABLE doctor " 
                  + "(doctorId INTEGER PRIMARY KEY AUTOINCREMENT, " 
                  + "dname TEXT NOT NULL, " 
                  + "dsurname TEXT NOT NULL, "
                  + "demail TEXT NOT NULL, "
                  + "userId FOREING KEY REFERENCES users(userid) ON UPDATE CASCADE ON DELETE SET NULL)";
          stmt5.execute(sql5);
          stmt5.close();
         
          Statement stmt6 = c.createStatement();
		String sql6 = "CREATE TABLE doctor_patient "
				   + "(patient_id REFERENCES patient(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " doctor_id REFERENCES doctor(doctorId) ON UPDATE CASCADE ON DELETE SET NULL, "
				   + " PRIMARY KEY (patient_id,doctor_id))";
		stmt6.execute(sql6);
		stmt6.close();
               
          return true;
      }catch(SQLException tables_error){
          if (tables_error.getMessage().contains("already exists")) {
				System.out.println("Database already exists.");
				return false;
			} else {
				System.out.println("Error creating tables! Abort.");
				tables_error.printStackTrace();
				return false;
			}
      }
            
    }

}