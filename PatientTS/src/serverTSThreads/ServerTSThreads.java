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
import java.io.InputStream;
import java.io.OutputStream;
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
    
    public static int contador;
    public static List<Thread> clientsThreadsList = new ArrayList();
   
    
    public static void main(String[] args) throws IOException{
       
        serverSocketClient = new ServerSocket(9000);
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
        int position;
        while(true){
            
            socketClient = serverSocketClient.accept();
            position = contador;
            contador++;
            cThread = new ClientThread(socketClient,userman,roleman,patientman,doctorman,signalman,position);
            Thread clientThread = new Thread(cThread);
            clientThread.start();
            clientsThreadsList.add(clientThread);
            
        }
        //SI ES PATIENT HACER UNA COSA Y SI ES DOCTOR HACER OTRA  
                
        }
        
        
        public static void ReleaseResourcesServerTSClient(ServerSocket severSocketClient){
        try{
            
            serverSocketClient.close();
            System.exit(0);
            
        }catch(IOException ex){
            Logger.getLogger(ServerTSThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
}
        
        public static void releaseClientResources(InputStream inputStream, OutputStream outputStream, Socket socket, int position) {
            System.out.println("in release ClientResources");
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
        contador = contador -1;
        clientsThreadsList.remove(position);
        if (contador==0){
            ReleaseResourcesServerTSClient(serverSocketClient);
        }
    }
    
    
  
    
}
