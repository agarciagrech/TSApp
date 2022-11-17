/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jdbc;

import db.interfaces.DBManager;
import db.interfaces.PatientTSManager;
import db.pojos.PatientTS;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agarc
 */
public class menuDBPrueba {
    private static DBManager dbManager;
    private static PatientTSManager patientmanager;
//    
    public static void main(String[] args) throws SQLException{
        
        
        SQLiteManager manager = new SQLiteManager();
        manager.connect();
        SQLiteUserManager userman = new SQLiteUserManager(manager.getConnection());
        SQLiteRoleManager roleman = new SQLiteRoleManager(manager.getConnection());
        SQLitePatientTSManager patientman = new SQLitePatientTSManager(manager.getConnection());
        SQLiteDoctorManager doctorman = new SQLiteDoctorManager(manager.getConnection());
        boolean createTables = manager.createTables();
        
        
        Integer medCard = 1234;
        String name = "paco";
        String surname = "perez";
        String date= "2000/11/17";
        Date dob = new Date(date);
        String address ="abc";
        String email = "paco@gmail.com";
        String diagnosis = "casa";
        String gender = "male";
        String macAddres = "20:50:13:22";
        String allergies = "fruta";
        String password = "paco123";
        String role = "Patient";
        
        PatientTS p1 = new PatientTS(medCard,name,surname,dob,address,email,diagnosis,allergies,gender,macAddres);
       
        System.out.println(p1.getPatientAllergies());
        System.out.println(p1.getPatientGender());
        System.out.println(p1.getPatientDob().toString());
        
        Date prueba = new Date (p1.getPatientDob().toString());
        System.out.println(prueba);
        
        System.out.println("before patient");
        patientman.addPatient(p1);
        System.out.println("pateint added");
        
    }
    
}
