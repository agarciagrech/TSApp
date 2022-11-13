/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTSThreads;

import db.jdbc.SQLiteManager;
import db.jdbc.SQLitePatientTSManager;
import db.jdbc.SQLiteSignalManager;
import db.jpa.JPAUserManager;
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
import db.pojos.PatientTS;
import java.rmi.NotBoundException;
import java.util.Date;
/**
 *
 * @author agarc
 */
public class ClientThread implements Runnable {

    public static Socket socket;
    String byteRead;
    private static SQLiteManager jdbc = new SQLiteManager();
    private static SQLitePatientTSManager patientman = new SQLitePatientTSManager();
    private static SQLiteSignalManager signalman = new SQLiteSignalManager();
    private static JPAUserManager userman = new JPAUserManager();
    private static InputStream inputStream = null;
    private static OutputStream outputStream = null;
    
    public ClientThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        
       
        User u = new User();
        //jdbc.connect();
        
        int medcard =123;
        String name = "paquito";
        String surname = "perez";
        Date dob = new Date(1/1/2000);
        String address = "Calle luna";
        String email = "paquito@gmail.com";
        String diagnosis = "sm";
        String allergies = "oarng";
        String gender = "Male";
        String mac = "20:17:11:20:52:36";
        PatientTS p = new PatientTS (medcard,name,surname,dob,address,email,diagnosis,allergies,gender,mac);
        
        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            BufferedReader br = new BufferedReader (new InputStreamReader(inputStream));
            PrintWriter pw = new PrintWriter(outputStream, true);
            /*
            byteRead = br.readLine();
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
            */
            String s;
            Integer roleUser = Integer.parseInt(br.readLine());
            if(roleUser == 1 || roleUser == 2){
//                s = "Connection stablished";
//                pw.println(s);
                System.out.println("RoleUser correct");
                
            }else{
//                s = "Invalid option";
//                pw.println(s);
                System.out.println("invalid roleuser");
            }
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(outputStream,true);
            while(roleUser!=0){
                switch (roleUser){
                        case 1: 
                            System.out.println("Inside of user 1");
                            
                            Utilities.CommunicationWithClient.recieveSignal(br, pw);

                            // This for the final proyect, when we solved the problems with the db:
            //                try{
            //                 int choice = Integer.parseInt(reader.readLine());
            //                  switch(choice){
            //                    case 1:
            //                        String m;
            //                        String line2 = reader.readLine();
            //                        line2=line2.replace("{", "");
            //                        line2=line2.replace("User", "");
            //                        String[] atribute2 = line2.split(",");
            //
            //                        for (int i =0;i <atribute2.length; i++){
            //                            String[] data2 = atribute2[i].split("=");
            //                            for (int j =0;j <data2.length - 1; j++){
            //                                data2[j]=data2[j].replace(" ", "");
            //                                switch(data2[j]){
            //                                    case "username": u.setUsername((data2[j+1]));
            //                                        break;
            //                                    case "password":u.setPassword(data2[j+1].getBytes());
            //                                    break;
            //                                }
            //                            }
            //                        }
            //                        User user_correct = new User();
            //                        String password = new String (u.getPassword());
            //                        user_correct = userman.checkPassword(u.getUsername(), password);
            //                        if(user_correct != null){
            //                            m = "Correct login";
            //                            pw.println(m);
            //                        }else{
            //                            m = "Incorrect username or password";
            //                            pw.println(m);
            //                        }
            //                         break;
            //                case 2:
            //                       
            //                        ArrayList<Integer> signalValues = new ArrayList<Integer>();
            //                        String byteRead = reader.readLine();
            //                        if(byteRead !=null){
            //                        Integer value = Integer.parseInt(byteRead);
            //                        signalValues.add(value);
            //                        }
            //
            //
            //                        //recordSignal
            //                break;
            //                case 3:
            //                //view signal
            //                break;
                
                                break;
                        case 2: 
                            System.out.println("Inside user 2");
                            int choice;
                            do{
                            choice = Integer.parseInt(reader.readLine());

                            switch (choice){
                                case 1:
                                    System.out.println("Doctor case 1");
                                     Utilities.CommunicationWithClient.receiveDoctor(br);
                                     break;
                                case 2:
                                    System.out.println("Doctor case 2");
                                    Utilities.CommunicationWithClient.receivePatient(br);
                                    break;
                                case 3:
                                    System.out.println("Doctor case 3");
                                    Utilities.CommunicationWithClient.receivePatient(br);
                                    break;
                                case 4:
                                    System.out.println("Doctor case 4");
                                    Utilities.CommunicationWithClient.sendPatientList(pw, br);
                                    break;
                            }
                            }while(choice!=0);
                            break;
                }       
            }

        }catch(Exception e){
            System.out.println("An error has occured");
        }
        }
                   
            /*}else if(roleUser == 2){
                //Login aqui 
           while(true){
           BufferedReader reader;
           reader = new BufferedReader(new InputStreamReader(inputStream));
           PrintWriter printWriter = new PrintWriter(outputStream, true);
           
           try{
               int choice = Integer.parseInt(reader.readLine());
               switch(choice){
                   
               }
           }catch(Exception e){
               System.out.println("Error");
           }
           }  
         
               
            }
 
            
        }catch(IOException ex){
             Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //LOGIN Y REGISTER DEL PATIENT 
    }   */

    
   
    
    
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
