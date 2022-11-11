/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTSThreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.users.User;

/**
 *
 * @author agarc
 */
public class ClientThread implements Runnable {

    public static Socket socket;
    String byteRead;
    
    public ClientThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        
        InputStream inputStream = null;
        OutputStream outputStream = null;
        User u = new User();
        
        try{
            inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader (new InputStreamReader(inputStream));
            //byteRead = br.readLine();
            String line = br.readLine();
            line=line.replace("{", "");
            line=line.replace("User", "");
            String[] atribute = line.split(",");
        
            for (int i =0;i <atribute.length; i++){
                String[] data2 = atribute[i].split("=");
                for (int j =0;j <data2.length - 1; j++){
                    data2[j]=data2[j].replace(" ", "");
                    switch(data2[j]){
                        case "username": u.setUsername((data2[j+1])); 
                                                     break;
                        case "password":u.setPassword(data2[j+1].getBytes()); 
                                     break;
                        case "role":  u.setRole(Integer.parseInt(data2[j+1])); // 1 patient, 2 doctor 
                         
                        
                   }
                }
            }
 
            
        }catch(IOException ex){
             Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //LOGIN Y REGISTER DEL PATIENT 
    }
    
    public static void ReleaseResourcesClient(InputStream inputStream, OutputStream outputStream, Socket socket ){
        try{
            inputStream.close();
        }catch(IOException ex){
             Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            outputStream.close();
        }catch(IOException ex){
             Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            socket.close();
        }catch(IOException ex){
             Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
