/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jdbc;

import db.interfaces.DBManager;
import db.interfaces.PatientTSManager;
import db.pojos.Doctor;
import db.pojos.PatientTS;
import db.pojos.Signal;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.users.Role;
import pojos.users.User;

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
        SQLiteSignalManager signalman = new SQLiteSignalManager(manager.getConnection());
        boolean createTables = manager.createTables();
        
        String username="doctorJuan";
        String password="lalala";
        String roleType = "Doctor";
        
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
        
        String roleType1 = "Patient";
        
        String date2 = "2000/11/18";
        Date dob2 = new Date(date2);
        
        String date3 = "2000/12/19";
        Date dob3 = new Date(date3);
        
        PatientTS p1 = new PatientTS(medCard,name,surname,dob,address,email,diagnosis,allergies,gender,macAddres);
        /*
        PatientTS p2 = new PatientTS(5678,"pepe","martin",dob2,"calle 1","pepe@gmail.com","diabetes","male","25:30:11:22");
        PatientTS p3 = new PatientTS(9013,"maria","martin",dob3,"calle 2","mm@gmail.com","epilepsia","female","22:80:50:31");
        
        User u = new User("doctorJuan","ivbrhal");
        User u2 = new User("doctorPepe","qryihpu");
        */
        User u3 = new User(Integer.toString(p1.getMedCardId()),"pfygb");
       /*
        User u4 = new User (Integer.toString(p2.getMedCardId()),"vpbubr");
        User u5 = new User (Integer.toString(p3.getMedCardId()),"uqfbpu");
        
        Doctor d = new Doctor("juan","perez","j@gmail.com");
        Doctor d2 = new Doctor ("pepe","flores","p@gmail.com");
        */
        Role role = new Role(roleType);
        Role role2 = new Role (roleType1);
        
        // Add roles:
        boolean existrole1 = roleman.existingRoleType(role.getRole());
        if (existrole1== false){
          roleman.addRole(role);
          System.out.println("Added role 1 to db");  
          role.setId(roleman.getId(role.getRole()));
          
        }
        
         boolean existrole2 = roleman.existingRoleType(role2.getRole());
        if (existrole2== false){
          roleman.addRole(role2);
          System.out.println("Added role 2 to db");  
          role2.setId(roleman.getId(role2.getRole()));
         
        }
        
        /*
        // Add doctor:
        
        boolean exist2 = userman.existingUserName(username);
        if (exist2== false){
          userman.addUser(u);
          System.out.println("\n Added user 1 to db");
          u.setUserId(userman.getId(username));
          userman.createLinkUserRole(role.getId(), u.getUserId());
         // patientman.addPatient(p1);
          doctorman.addDoctor(d);
          System.out.println("doctot 1 added");
          //patientman.createLinkUserPatient(u.getUserId(), p1.getMedCardId());
          d.setDoctorId(doctorman.getId(d.getDoctorName()));
          doctorman.createLinkUserDoctor(u.getUserId(), d.getDoctorId());
          System.out.println("Link created");
        }
        
        boolean exist4 = roleman.existingRoleType(role.getRole());
        if (exist4== false){
          roleman.addRole(role);
          System.out.println("Added role to db");  
          role.setId(roleman.getId(role.getRole()));
          u.setRole(role.getId());
        }
        
        
        boolean exist3 = userman.existingUserName(u2.getUsername());
        if (exist3== false){
          userman.addUser(u2);
          System.out.println("\n Added user 2 to db");
          u2.setUserId(userman.getId(u2.getUsername()));
          d2.setDoctorId(doctorman.getId(d2.getDoctorName()));
          userman.createLinkUserRole(role.getId(), u2.getUserId());
          //patientman.addPatient(p2);
          doctorman.addDoctor(d2);
          System.out.println("doctor 2 added");
          //patientman.createLinkUserPatient(u2.getUserId(), p2.getMedCardId());
          d2.setDoctorId(doctorman.getId(d2.getDoctorName()));
          doctorman.createLinkUserDoctor(u2.getUserId(), d2.getDoctorId());
          System.out.println("Link created");
          
          
        }
        */
        // Add patient: 
        boolean existpatient = userman.existingUserName(u3.getUsername());
        if (existpatient== false){
          userman.addUser(u3);
          System.out.println("\n Added user 3 to db");
          u3.setUserId(userman.getId(u3.getUsername()));
          userman.createLinkUserRole(role2.getId(), u3.getUserId());
          patientman.addPatient(p1);
          System.out.println("Patient 1 added");
          patientman.createLinkUserPatient(u3.getUserId(), p1.getMedCardId());
          System.out.println("User Link created");
          //patientman.createLinkDoctorPatient(p1.getMedCardId(),d.getDoctorId());
           // System.out.println("Doctor Link created");
          
        }
        /*
        boolean existpatient2 = userman.existingUserName(u4.getUsername());
        if (existpatient2== false){
          userman.addUser(u4);
          System.out.println("\n Added user 4 to db");
          u4.setUserId(userman.getId(u4.getUsername()));
          userman.createLinkUserRole(role2.getId(), u4.getUserId());
          patientman.addPatient(p2);
          System.out.println("Patient 2 added");
          patientman.createLinkUserPatient(u4.getUserId(), p2.getMedCardId());
          System.out.println("User Link created");
          patientman.createLinkDoctorPatient(p2.getMedCardId(),d.getDoctorId());
            System.out.println("Doctor Link created");
          
        }
        
        boolean existpatient3 = userman.existingUserName(u5.getUsername());
        if (existpatient3== false){
          userman.addUser(u5);
          System.out.println("\n Added user 3 to db");
          u5.setUserId(userman.getId(u5.getUsername()));
          userman.createLinkUserRole(role2.getId(), u5.getUserId());
          patientman.addPatient(p3);
          System.out.println("Patient 3 added");
          patientman.createLinkUserPatient(u5.getUserId(), p3.getMedCardId());
          System.out.println("User Link created");
          patientman.createLinkDoctorPatient(p3.getMedCardId(),d2.getDoctorId());
            System.out.println("Doctor Link created");
          
        }
        
        List<PatientTS> patientList = patientman.selectAllPatients();
        for(PatientTS p : patientList){
            System.out.println(p.toString());
        }
        
        List<PatientTS> patientList2 = patientman.selectPatientsByDoctorId(d.getDoctorId());
        System.out.println("\n\n Patients of doctor"+d.getDoctorId());
        for(PatientTS patient : patientList2){
            System.out.println(patient.toString());
        }
        
        /*
        Doctor d3 = doctorman.selectDoctor(1);
          System.out.println(d3.toString());
        
        //  doctorman.deleteDoctorById(1);
        //  System.out.println("doctor 1 deleted");
        /*
        userman.deleteUserByUserName(Integer.toString(medCard));
        System.out.println("p1 deleted");
        
        */
        
        
        
        
        
        
        
        /*
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
        */
        
        // Signals:
        
        int[] ECG = {1,2,3,4};
        int[] EMG = {5,6,7,8};
        int[] ECG2 = {9,10,11,12};
        int[] EMG2 = {13,14,15,16};
        
        Signal s1 = new Signal(ECG,EMG,"name",10);
        Signal s2 = new Signal (ECG2,EMG2,"name2",10);
       
        s1.StartDate();
        s2.StartDate();
        
        s1.StoreECGinFile(p1.getPatientName());
        s1.StoreEMGinFile(p1.getPatientName());
        
        s2.StoreECGinFile(p1.getPatientName());
        s2.StoreEMGinFile(p1.getPatientName());
        
        signalman.addSignal(s1, p1);
        System.out.println("Signal 1 added ");
        signalman.addSignal(s2, p1);
        System.out.println("Signal 2 added ");
        s1.setSignalId(signalman.getId(s1.getSignalname()));
        s2.setSignalId(signalman.getId(s2.getSignalname()));
        
            

        
        
        Signal s3 = signalman.selectSignalById(s1.getSignalId());
        System.out.println(s3.toString());
        System.out.println("ECG:"+Arrays.toString(s3.getECG_values()));
        System.out.println("EMG:"+Arrays.toString(s3.getEMG_values()));
        
        List<Signal> signalList = signalman.listSignalsByPatient(p1.getMedCardId());
        for(Signal s : signalList){
            System.out.println(s.toString());
            System.out.println("ECG:"+Arrays.toString(s.getECG_values()));
            System.out.println("EMG:"+Arrays.toString(s.getEMG_values()));
        }
        signalman.deleteSignalById(s1.getSignalId());
        System.out.println("Signal 1 deleted");
        
        
    }
    
}
