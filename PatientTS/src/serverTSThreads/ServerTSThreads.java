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
import Utilities.CommunicationWithClient;
import db.interfaces.DoctorManager;
import db.interfaces.PatientTSManager;
import db.interfaces.RoleManager;
import db.interfaces.SignalManager;
import db.interfaces.UserManager;
import db.jdbc.SQLiteDoctorManager;
import db.jdbc.SQLiteManager;
import db.jdbc.SQLitePatientTSManager;
import db.jdbc.SQLiteRoleManager;
import db.jdbc.SQLiteSignalManager;
import db.jdbc.SQLiteUserManager;
import db.pojos.Doctor;
import java.io.BufferedReader;
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pojos.users.Role;
import pojos.users.User;
import static serverTSThreads.ClientThread.createRoles;

/**
 *
 * @author agarc
 */
public class ServerTSThreads {
    
    public static Socket socketClient;
    
    public static ServerSocket serverSocketClient; 
   
    public static ClientThread cThread;
    
    
    public static List<Thread> clientsThreadsList = new ArrayList();
   
    
    public static void main(String[] args) throws IOException{
       
        serverSocketClient = new ServerSocket(9000); //IP, PORT?
        SQLiteManager manager = new SQLiteManager();
        manager.connect();
        Connection c = manager.getConnection();
        UserManager userman = new SQLiteUserManager(c);
        RoleManager roleman = new SQLiteRoleManager(c);
        PatientTSManager patientman = new SQLitePatientTSManager(c);
        DoctorManager doctorman = new SQLiteDoctorManager(c);
        SignalManager signalman = new SQLiteSignalManager(c);
        boolean create = manager.createTables();
        if (create){
            createRoles(roleman);
            Utilities.ClientUtilities.firstlogin(userman,doctorman,roleman);
            
        }
        while(true){
            
            socketClient = serverSocketClient.accept();
            cThread = new ClientThread(socketClient,userman,roleman,patientman,doctorman,signalman);
            Thread clientThread = new Thread(cThread);
            clientThread.start();
            clientsThreadsList.add(clientThread);
        }
        //SI ES PATIENT HACER UNA COSA Y SI ES DOCTOR HACER OTRA  
                
        }
        
        
        public static void ReleaseResourcesServerTSClient(ServerSocket severSocketClient){
        try{
            if(clientsThreadsList.isEmpty()==true){
            serverSocketClient.close();
            }
        }catch(IOException ex){
            Logger.getLogger(ServerTSThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public static void releaseClientResources(BufferedReader bufferedReader, Socket socket) {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientsThreadsList.remove(socket);
        
    }
    
    
  
    
}
