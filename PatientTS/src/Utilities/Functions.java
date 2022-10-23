/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import db.jdbc.*;
import db.jpa.JPAUserManager;
import db.pojos.*;
import java.rmi.NotBoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pojos.users.User;
/**
 *
 * @author gisel
 */
public class Functions {
    
    private static Scanner sc = new Scanner(System.in);
    private static SQLiteManager jdbc = new SQLiteManager();
    private static SQLitePatientTSManager patientman = new SQLitePatientTSManager();
    private static SQLiteDoctorManager doctorman = new SQLiteDoctorManager();
    private static JPAUserManager userman = new JPAUserManager();
    public static Integer option = null;
    public static String trashcan;
    
    
    //addSignal
    //consultSignals(Patient)
    //deleteSignal
    
    public static void createPatient () throws NotBoundException, Exception {

        sc = new Scanner (System.in);
        PatientTS p = new PatientTS();

        System.out.println("Please, input the patient info:");
        System.out.print("Name: "); 
        String name = sc.next();
        p.setPatientName(name);
        System.out.print("Surname: "); 
        String surname = sc.next();
        p.setPatientSurname(surname);
        System.out.print("Medical card number: "); 
        Integer medCardNumber=1; 
        Boolean validMedNumber = false;
        do { 
            try {
            medCardNumber = sc.nextInt(); 
            validMedNumber = true;
            }catch(Exception e) {
                    System.out.println("Please introduce a valid medical card number which only contains numbers");
            }
            System.out.print("Error. Please introduce medical card number: "); 
        } while (patientman.selectPatient(medCardNumber) != null&&(!validMedNumber) );
        p.setMedCardId(medCardNumber);

        System.out.print("Gender: ");
        String gender = sc.next();   
        try {
            if (gender.equalsIgnoreCase("male")) {
                    gender = "Male";
            } else {
                    gender = "Female";
            }

            p.setPatientGender(gender);		
        } catch (NotBoundException e) {
            do{
                    System.out.print("Not a valid gender. Please introduce a gender (Male or Female): ");
                    gender = sc.next();
            } while (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")));

            if (gender.equalsIgnoreCase("male")) {
                    gender = "Male";
            } else {
                    gender = "Female";
            }

            p.setPatientGender(gender);
        }


        System.out.print("Date of birth [yyyy-mm-dd]: ");	
        String birthdate = sc.next();
        Date bdate; 
        try {
            bdate = Date.valueOf(birthdate);
            if (bdate.before(Date.valueOf(LocalDate.now())) || bdate.equals(Date.valueOf(LocalDate.now()))) {
                    p.setPatientDob(bdate);
            } else {
                    do {
                            System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");
                            birthdate = sc.next();
                            bdate = Date.valueOf(birthdate);
                    } while ((!bdate.before(Date.valueOf(LocalDate.now()))) || bdate.equals(Date.valueOf(LocalDate.now())));
                    p.setPatientDob(bdate);
            }
        } catch (Exception e) {
            int b=0;
            do {
                    try {	
                            System.out.print("Please introduce a valid date format [yyyy-mm-dd]: ");
                            birthdate = sc.next();
                            bdate = Date.valueOf(birthdate); 

                            if (bdate.before(Date.valueOf(LocalDate.now())) || bdate.equals(Date.valueOf(LocalDate.now()))) {
                                    p.setPatientDob(bdate);
                            } else {
                                    do {
                                            System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");							
                                            birthdate = sc.next();
                                            bdate = Date.valueOf(birthdate);
                                    } while ((!bdate.before(Date.valueOf(LocalDate.now()))) || bdate.equals(Date.valueOf(LocalDate.now())));
                                    p.setPatientDob(bdate);
                            }
                            b=1;
                    } catch (Exception e1) {
                    }
            } while (b==0);
        }

        System.out.print("Address: ");				
        String address = sc.next();
        p.setPatientAddress(address);

        System.out.print("Email:: ");				
        String email = sc.next();
        p.setPatientEmail(email);

        System.out.println("Let's proceed with the registration, the username and password will be autogenerated by the system:");
        register(p.getPatientName(),p.getPatientSurname(), p.getMedCardId());
        System.out.println("The patient was succesfully added to the database");
    }
    
    private static PatientTS selectPatient() throws Exception{
        List<PatientTS> patientList = new ArrayList<PatientTS>();
        PatientTS patient = null;
        sc = new Scanner(System.in);
        while(patientList.isEmpty()){
            Integer medCard=null;
            System.out.println(patientList.toString());
            System.out.println("Enter the medical card number of the chosen patient: ");
            try{
                medCard = Integer.parseInt(sc.next());
            }catch(Exception ex){
                System.out.println("Not a valid medical card number ONLY NUMBERS");
            }
            if(patientman.selectPatient(medCard)!=null) {
                patient = new PatientTS(patientman.selectPatient(medCard)); // integer id
            }
        }
        return patient; 
    }
    private static void deletePatient() throws Exception {
        sc = new Scanner (System.in);
        PatientTS p = new PatientTS();
        p=patientman.selectPatient(p.getMedCardId());
        User u = userman.getUser(p.getPatientUserId());
        patientman.deletePatientByMedicalCardId(p.getMedCardId());
        userman.deleteUser(u);
    }
     
    public static void createDoctor() throws Exception{
        Doctor d = new Doctor();
        sc = new Scanner (System.in);

        System.out.println("Please, input the doctor info:");
        System.out.print("Name: ");
        String name = sc.next();
        d.setDoctorName(name);

        System.out.print("Surname: ");
        String surname = sc.next();
        d.setDoctorSurname(surname);

        doctorman.addDoctor(d);
        d.setDoctorId(jdbc.getLastIdIntroduced());//create method in SQL

        System.out.println("Let's proceed with the registration, the username and password will be autogenerated by the system:");
        register(d.getDoctorName(), d.getDoctorSurname(), d.getDoctorId());  
        System.out.println("The doctor was succesfully added to the database");
    }
    
    private static Doctor selectDoctor() throws Exception {
        List<Doctor> dList = new ArrayList<Doctor>();
        Doctor d = null;
        sc = new Scanner (System.in);
        while(dList.isEmpty()) {
            System.out.println("Enter the doctor's surname:");
            String surname = sc.next();
            dList = doctorman.searchDoctor(surname);//crear en SQL
        }
        while(d == null) {
            System.out.println(dList.toString()); 
            Integer id=null;
            System.out.println("Enter the id of the chosen worker:");
            try {
                id = Integer.parseInt(sc.next());
            }catch(Exception e) {
                    System.out.println("Not a valid id ONLY NUMBERS");
            }
            if(doctorman.selectDoctor(id)!=null) {
                d = new Doctor(doctorman.selectDoctor(id));
            }
        }
        return d;
    }
    
    private static void deleteDoctor() throws Exception {
        sc = new Scanner (System.in);
        Doctor d = selectDoctor();
        User u = userman.getUser(d.getDoctorUserId());
        doctorman.deleteDoctorById(d.getDoctorId());
        userman.deleteUser(u);
    }
    
    private static void accessToAPatientsProfile(Doctor d) throws Exception {
        PatientTS patient = selectPatient();
        sc = new Scanner (System.in);
        do {
            System.out.println("Choose an option[0-2]:");
            System.out.println("\n1. Consult recording \n2. Edit diagnosis \\n 0. Exit");

            int a = 0;
            do {
                try {
                        option = sc.nextInt();
                        a = 1;
                } catch (Exception e) {
                        trashcan = sc.next();
                        System.out.println("Please input a valid option.");
                }
            } while (a==0);

            switch(option) {
            case 0:
                    System.out.println("Thank you for using our system");
                    jdbc.disconnect();
                    userman.disconnect();
            System.exit(0);
            case 1:
                    System.out.println("Consult medical tests");
                    consultSignals(patient);
                    break;
            case 2:
                    System.out.println("Edit diagnosis");
                    editDiagnosis(patient);
                    break;
            }
        } while(true);
    }
    
    private static void editDiagnosis (PatientTS p) throws Exception{
        System.out.println("Enter the new diagnosis:");
	String diagnosis = sc.next();
        p.setPatientDiagnosis(diagnosis);
    }
    
}
