/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jdbc;

import db.interfaces.DBManager;
import db.interfaces.PatientTSManager;
import db.pojos.PatientTS;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author agarc
 */
public class menuDBPrueba {
    private static DBManager dbManager;
    private static PatientTSManager patientmanager;
    
    public static void main(String[] args) throws SQLException{
        dbManager.connect();
        dbManager.createTables();
        
        Integer medCard = 1234;
        String name = "paco";
        String surname = "perez";
        Date dob = new Date("2000-11-17");
        String address ="abc";
        String email = "paco@gmail.com";
        String diagnosis = "casa";
        String gender = "male";
        String macAddres = "20:50:13:22";
        String password = "paco123";
        String role = "Patient";
          
        PatientTS p1 = new PatientTS(medCard,name,surname,dob,address,email,diagnosis,gender,macAddres,password,role);
        
        patientmanager.addPatient(p1);
        
    }
    
}
