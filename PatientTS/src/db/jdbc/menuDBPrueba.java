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
import java.util.List;
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
        
        String date2 = "2000/11/18";
        Date dob2 = new Date(date2);
        
        String date3 = "2000/12/19";
        Date dob3 = new Date(date3);
        
        PatientTS p1 = new PatientTS(medCard,name,surname,dob,address,email,diagnosis,allergies,gender,macAddres);
        PatientTS p2 = new PatientTS(5678,"pepe","martin",dob2,"calle 1","pepe@gmail.com","diabetes","male","25:30:11:22");
        PatientTS p3 = new PatientTS(9013,"maria","martin",dob3,"calle 2","mm@gmail.com","epilepsia","female","22:80:50:31");
        
        System.out.println(p1.getPatientAllergies());
        System.out.println(p1.getPatientGender());
        System.out.println(p1.formatDate(dob));
        
        
        patientman.addPatient(p1);
        System.out.println("pateint 1 added");
        patientman.addPatient(p2);
        System.out.println("patient 2 added");
        patientman.addPatient(p3);
        System.out.println("patient 3 added");
        
        PatientTS p4 = patientman.selectPatient(medCard);
        System.out.println(p4.toString());
        
        List<PatientTS> patientList = patientman.selectAllPatients();
        for(PatientTS p : patientList){
            System.out.println(p.toString());
        }
        
        patientman.deletePatientByMedicalCardId(5678);
        System.out.println("p2 deleted");
        List<PatientTS> patientList2 = patientman.selectAllPatients();
        for(PatientTS p : patientList2){
            System.out.println(p.toString());
        }
        
        patientman.editPatient(medCard, name, surname, dob3, address, email, diagnosis, allergies, gender,macAddres);
        System.out.println("p1 edited");
        PatientTS p5 = patientman.selectPatient(medCard);
        System.out.println(p5.toString());
        
    }
    
}
