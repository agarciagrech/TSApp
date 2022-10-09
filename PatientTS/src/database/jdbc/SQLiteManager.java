/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import db.interfaces.DBManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author agarc
 */
public class SQLiteManager implements DBManager {
    
    private Connection c; 
    
    public SQLiteManager(){
        super();
        this.connect();
    }

    @Override
    public void connect() {
        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./db/DSS.db");
            c.createStatement().execute("PRAGMA foreign_keys=ON");
            
             } catch (Exception e) {
            e.printStackTrace();
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
    public void createTables() {
      Statement stmt1;
      try{
          stmt1 = c.createStatement();
          String sql1 = "CREATE TABLE patient " + "(medCardId INTEGER PRIMARY KEY, " 
                  + "name TEXT NOT NULL, " + "surname TEXT NOT NULL, "
                  + "dob DATE NOT NULL, " + " address TEXT NOT NULL, "
                  + "email TEXT NOT NULL, " + "gender TEXT NOT NULL)";
          stmt1.executeUpdate(sql1);
          
          stmt1 = c.createStatement();
          String sql2 = "CREATE TABLE signal " + "(id INTEGER PRIMARY KEY "
                  + "type TEXT NOT NULL " + "(id_patient REFERENCES patient,"+ "(signal_values BYTES)";
          stmt1.executeUpdate(sql2);
      }catch(SQLException e){
          if(e.getMessage().contains("already exists")){
              
          }else{
               e.printStackTrace();
          }
      }
      
          
      
}
}