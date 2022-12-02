/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTSThreads;

import db.jdbc.SQLiteManager;
import db.jdbc.SQLitePatientTSManager;
import db.jdbc.SQLiteSignalManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.users.User;
import Utilities.*;
import db.interfaces.DoctorManager;
import db.interfaces.PatientTSManager;
import db.interfaces.RoleManager;
import db.interfaces.SignalManager;
import db.interfaces.UserManager;
import db.jdbc.SQLiteDoctorManager;
import db.jdbc.SQLiteRoleManager;
import db.jdbc.SQLiteUserManager;
import db.pojos.Doctor;
import db.pojos.PatientTS;
import db.pojos.Signal;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import pojos.users.Role;

/**
 *
 * @author agarc
 */
public class ClientThread implements Runnable {

    public static Socket socket;
    public static UserManager userman;
    public static RoleManager roleman;
    public static PatientTSManager patientman;
    public static DoctorManager doctorman;
    public static SignalManager signalman;
    public static int position;
    public static boolean exit; 

    public ClientThread(Socket socket) {
        this.socket = socket;
        //this.position = position;
    }

    @Override
    public void run() {
        
        SQLiteManager manager = new SQLiteManager();
        manager.connect();
        Connection c = manager.getConnection();
        this.userman = new SQLiteUserManager(c);
        this.roleman = new SQLiteRoleManager(c);
        this.patientman = new SQLitePatientTSManager(c);
        this.doctorman = new SQLiteDoctorManager(c);
        this.signalman = new SQLiteSignalManager(c);
        boolean create = manager.createTables();
        
        if (create){
            createRoles(roleman);
            Utilities.ClientUtilities.firstlogin(userman,doctorman,roleman);
        }
        
        
        
        String byteRead;
        Scanner sc = new Scanner(System.in);
      
        String trashcan;
        InputStream inputStream;
        OutputStream outputStream;

        try {
          
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter pw = new PrintWriter(outputStream, true);
            first(inputStream,outputStream,br, pw, userman, patientman, signalman, doctorman);

        } catch (IOException e) {
            System.out.println("An error has occured");
        }
    }

    public static void first(InputStream inputStream,OutputStream outputStream,BufferedReader br, PrintWriter pw, UserManager userman, PatientTSManager patientman, SignalManager signalman, DoctorManager doctorman) {
        int option = 1;
        exit = false;
        do {
            try {
                option = Integer.parseInt(br.readLine());
               
                switch (option) {
                    case 1:
                        Utilities.ClientUtilities.registerPatient(br, pw, userman, patientman, doctorman);
                        break;
                    case 2:
                        int a = Integer.parseInt(br.readLine());
                        if (a == 1){
                            break;
                        }else{
                        User user = Utilities.ClientUtilities.login(br, pw, userman);

                        System.out.println();
                        if (user.getRole() == 1) {
                            System.out.println("the user is a patient");
                            pw.println("patient");
                            PatientTS p = patientman.selectPatientByUserId(user.getUserId());
                            Utilities.CommunicationWithClient.sendPatient(pw, p);
                            patientMenu(user, br, pw, userman, patientman, signalman);
                        } else if (user.getRole() == 2) {
                            System.out.println("the user is a doctor");
                            pw.println("doctor");
                            Doctor d = doctorman.selectDoctorByUserId(user.getUserId());
                            Utilities.CommunicationWithClient.sendDoctor(pw, d);
                            doctorMenu(user, br, pw, userman, patientman, signalman, doctorman);
                        }}
                        break;
                    case 0: 
                        break;
                       
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (option != 0 || exit == false);
         ServerTSThreads.releaseClientResources(inputStream,outputStream,socket);
    }

    public static void patientMenu(User u, BufferedReader br, PrintWriter pw, UserManager userman, PatientTSManager patientman, SignalManager signalman) {

        int option = 1;

        
            try {
                option = Integer.parseInt(br.readLine());
                switch (option) {
                    case 0:
                        exit= true;
                        
                        break;
                    case 1:
                        int userid1 = userman.getId(u.getUsername());
                        PatientTS p1 = patientman.selectPatientByUserId(userid1);
                        Signal s = Utilities.CommunicationWithClient.recieveSignal(br, pw);
                        s.CreateECGFilename(p1.getPatientName());
                        s.CreateEMGFilename(p1.getPatientName());
                        s.StartDate();
                        s.StoreECGinFile(p1.getPatientName());
                        s.StoreEMGinFile(p1.getPatientName());
                        signalman.addSignal(s, p1);
                        break;
                    case 3:
                        int userid3 = userman.getId(u.getUsername());
                        PatientTS p3 = patientman.selectPatientByUserId(userid3);
                        Utilities.CommunicationWithClient.sendAllSignal(br, pw, signalman, p3.getMedCardId());
                        String filename = br.readLine();
                        Signal s1 = signalman.selectSignalByName(filename);
                        pw.println(s1.toString());
                        break;
                    case 4:
                        PatientTS updatep = Utilities.CommunicationWithClient.receivePatient(br);
                        patientman.editPatient(updatep.getMedCardId(), updatep.getPatientName(), updatep.getPatientSurname(), updatep.getPatientDob(), updatep.getPatientAddress(), updatep.getPatientEmail(), updatep.getPatientDiagnosis(), updatep.getPatientAllergies(), updatep.getPatientGender(), updatep.getMacAddress());
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public static void createRoles(RoleManager roleman) {
        Role role1 = new Role("patient");
        roleman.addRole(role1);
        Role role2 = new Role("doctor");
        roleman.addRole(role2);
    }

    public static void doctorMenu(User u, BufferedReader br, PrintWriter pw, UserManager userman, PatientTSManager patientman, SignalManager signalman, DoctorManager doctorman) {
        int option = 1;
       
            try {
                option = Integer.parseInt(br.readLine());
                System.out.println(option);
                switch (option) {
                    case 0:
                        exit= true;
                        break;
                    case 1:
                        Utilities.ClientUtilities.registerDoctor(br, pw, userman, doctorman);
                        break;
                    case 2:
                        int userid = userman.getId(u.getUsername());
                        Doctor d = doctorman.selectDoctorByUserId(userid);
                        List<PatientTS> patientList = patientman.selectPatientsByDoctorId(doctorman.getId(d.getDoctorName()));
                        Utilities.CommunicationWithClient.sendPatientList(patientList, pw, br);
                        break;
                    case 3:
                        int a = Integer.parseInt(br.readLine());
                        while (a != 0) {
                            int uid = userman.getId(u.getUsername());
                            Doctor d3 = doctorman.selectDoctorByUserId(uid);
                            List<PatientTS> pList = patientman.selectPatientsByDoctorId(doctorman.getId(d3.getDoctorName()));
                            Utilities.CommunicationWithClient.sendPatientList(pList, pw, br);
                            int medcard = Integer.parseInt(br.readLine());
                            PatientTS p = patientman.selectPatient(medcard);
                            Utilities.CommunicationWithClient.sendPatient(pw, p);
                            PatientTS updatep = Utilities.CommunicationWithClient.receivePatient(br);
                            patientman.editPatient(updatep.getMedCardId(), updatep.getPatientName(), updatep.getPatientSurname(), updatep.getPatientDob(), updatep.getPatientAddress(), updatep.getPatientEmail(), updatep.getPatientDiagnosis(), updatep.getPatientAllergies(), updatep.getPatientGender(), updatep.getMacAddress());
                            a = Integer.parseInt(br.readLine());
                        }
                        break;
                    case 4:
                        int userid1 = userman.getId(u.getUsername());
                        Doctor d1 = doctorman.selectDoctorByUserId(userid1);
                        List<PatientTS> patientList1 = patientman.selectPatientsByDoctorId(doctorman.getId(d1.getDoctorName()));
                        Utilities.CommunicationWithClient.sendPatientList(patientList1, pw, br);
                        int medcard2 = Integer.parseInt(br.readLine());
                        if(medcard2==2){
                            option=2;
                            break;
                        }else if (medcard2==0){
                            break;
                        }
                        Utilities.CommunicationWithClient.sendAllSignal(br, pw, signalman, medcard2);
                        String filename = br.readLine();
                        Signal s1 = signalman.selectSignalByName(filename);
                        pw.println(s1.toString());

                        break;

                    case 5:
                        int userid2 = userman.getId(u.getUsername());
                        Doctor d2 = doctorman.selectDoctorByUserId(userid2);
                        List<PatientTS> patientList2 = patientman.selectPatientsByDoctorId(doctorman.getId(d2.getDoctorName()));
                        Utilities.CommunicationWithClient.sendPatientList(patientList2, pw, br);
                        int medcard3 = Integer.parseInt(br.readLine());
                        if(medcard3==2){
                            option=2;
                            break;
                        }else if (medcard3==0){
                            break;
                        }
                        System.out.println("Med card received: " + medcard3);
                        PatientTS pToDelete = patientman.selectPatient(medcard3);
                        System.out.println("Patient to delete: " + pToDelete.toString());
                        patientman.deletePatientByMedicalCardId(pToDelete.getMedCardId());
                        String medcard = ""+medcard3;
                        userman.deleteUserByUserName(medcard);
                        break;
                }

            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    public static void ReleaseResourcesClient(InputStream inputStream, OutputStream outputStream, Socket socket) {
        try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
