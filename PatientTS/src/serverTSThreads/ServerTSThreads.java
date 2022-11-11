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
       
        serverSocketClient = new ServerSocket(); //IP, PORT?
       
        
        clientsThreadsList = new Thread[1000];
        
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
    
    
   
    
}
