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
    
    @Override
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.c = DriverManager.getConnection("jdbc:sqlite:./db/DSS.db");
            c.createStatement().execute("PRAGMA foreign_keys=ON");
            patient = new SQLitePatientTSManager(c);
            doctor = new SQLiteDoctorManager(c);
            signalman = new SQLiteSignalManager(c);
            
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
          String sql1 = "CREATE TABLE patient " 
                  + "(medical_card_number INTEGER PRIMARY KEY, " 
                  + "name TEXT NOT NULL, " 
                  + "surname TEXT NOT NULL, "
                  + "dob DATE NOT NULL, " 
                  + "address TEXT NOT NULL, "
                  + "email TEXT NOT NULL, " 
                  + "diagnosis TEXT NOT NULL, "
                  + "allergies TEXT, "
                  + "gender TEXT NOT NULL, "
                  + "macAddress TEXT NOT NULL"
                  + "userId INTEGER REFERENCES users(USERID) ON UPDATE CASCADE ON DELETE SET NULL)";
          stmt1.executeUpdate(sql1);
          stmt1.close();
          Statement stmt2 = c.createStatement();
          String sql2 = "CREATE TABLE signal " 
                  + "(signalId INTEGER PRIMARY KEY "
                  + "svalues TEXT NOT NULL " //--> NO HAY ATRIBUTOS DE ESTO, SI AÑADIMOS MODIFICAR METODOS DE MANAGER
                  + "startDate DATE NOT NULL " //--> NO HAY ATRIBUTOS DE ESTO, SI AÑADIMOS MODIFICAR METODOS DE MANAGER
                  + "sname TEXT NOT NULL " 
                  + "stype TEXT NOT NULL " 
                  + "id_patient REFERENCES patient (medical_card_number),"
                  + "(signal_values BYTES)";
          stmt2.executeUpdate(sql2);
          stmt2.close();
          
          Statement stmt3 = c.createStatement();
          String sql3 = "CREATE TABLE doctor " 
                  + "(doctorId INTEGER PRIMARY KEY, " 
                  + "dname TEXT NOT NULL, " 
                  + "dsurname TEXT NOT NULL, "
                  + "demail TEXT NOT NULL, " 
                  + "userId INTEGER REFERENCES users(USERID) ON UPDATE CASCADE ON DELETE SET NULL)";
          stmt3.executeUpdate(sql3);
          stmt3.close();
          
          Statement stmt4 = c.createStatement();
		String sql4 = "CREATE TABLE doctor_patient "
				   + "(patient_id     INTEGER  REFERENCES patient(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " doctor_id   INTEGER  REFERENCES doctor(doctorId) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " PRIMARY KEY (patient_id,doctor_id))";
		stmt4.executeUpdate(sql4);
		stmt4.close();
          
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