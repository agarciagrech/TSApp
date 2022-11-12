/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTSThreads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utilities.ClientUtilities;
import db.jdbc.SQLiteDoctorManager;
import db.jdbc.SQLitePatientTSManager;
import db.jpa.JPAUserManager;
import db.pojos.Doctor;
import java.security.MessageDigest;
import java.util.Scanner;
import pojos.users.Role;
import pojos.users.User;

/**
 *
 * @author agarc
 */
public class ServerTSThreads {
    
    public static Socket socketClient;
    
    public static ServerSocket serverSocketClient; 
   
    public static ClientThread cThread;
    
    
    public static Thread[] clientsThreadsList;
   
    
    public static void main(String[] args) throws IOException{
       
        serverSocketClient = new ServerSocket(9200); //IP, PORT?
       
        
        clientsThreadsList = new Thread[1000];
        
        firstlogin();
        
        //COMO COMPROBAR SI ES UN PATIENT O UN DOCTOR??
        while(true){
            int contadorClients = 0;
            socketClient = serverSocketClient.accept();
            cThread = new ClientThread(socketClient);
            Thread clientThread = new Thread(cThread);
            clientThread.start();
            clientsThreadsList[contadorClients] = clientThread;
            contadorClients++;
                    
            
        }
        //SI ES PATIENT HACER UNA COSA Y SI ES DOCTOR HACER OTRA  
                
        }
        
        
         public static void ReleaseResourcesServerTSClient(ServerSocket severSocketClient){
        try{
            serverSocketClient.close();
        }catch(IOException ex){
            Logger.getLogger(ServerTSThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
   private static void firstlogin(){
           
   Scanner sc = new Scanner(System.in);
   JPAUserManager userman = new JPAUserManager();
    SQLitePatientTSManager patientman = new SQLitePatientTSManager();
    SQLiteDoctorManager doctorman = new SQLiteDoctorManager();
    String trashcan;
        try{
        String username = "admin";
        String password = "admin";
        Role role = userman.getRole(2);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] hash = md.digest();
        User user = new User(username, hash, role);
        userman.newUser(user);
        Doctor doctor = new Doctor("admin", "adnmin", 1);
        doctorman.addDoctor(doctor);
	Doctor created = new Doctor(doctorman.selectDoctor(1));
	doctorman.createLinkUserDoctor(user.getUserId(), created.getDoctorId());
        System.out.println("Admin created");
        }catch(Exception ex) {
                ex.printStackTrace();
        }
    }
    
}
