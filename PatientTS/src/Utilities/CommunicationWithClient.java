/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import db.jdbc.SQLiteSignalManager;
import db.pojos.Doctor;
import db.pojos.PatientTS;
import db.pojos.Signal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.users.User;

/**
 *
 * @author albic
 */
public class CommunicationWithClient {
    SQLiteSignalManager sman = new SQLiteSignalManager();
    public static void sendPatientList (List<PatientTS> patientList,PrintWriter pw,BufferedReader bf){
        for (PatientTS p : patientList){
            System.out.println("Patient:"+p.getPatientName()+"/"+p.getPatientSurname()+"/"+p.getMedCardId());
           pw.println("Patient:"+p.getPatientName()+"/"+p.getPatientSurname()+"/"+p.getMedCardId());
            
        }
        pw.println("End of list");
    }
    public static PatientTS receivePatient(BufferedReader bufferReader){
        System.out.println("in receive patient");
        boolean recieved = true; 
        PatientTS p = new PatientTS();
        
        try{
            String line = bufferReader.readLine();
            line=line.replace("{", "");
            line=line.replace("}", "");
            line=line.replace("Patient", "");
            String[] atribute = line.split(",");
            SimpleDateFormat  format = new SimpleDateFormat("dd/MM/yyyy"); 
        
            for (int i =0;i <atribute.length; i++){
                String[] data2 = atribute[i].split("=");
                for (int j =0;j <data2.length - 1; j++){
                    data2[j]=data2[j].replace(" ", "");
                    switch(data2[j]){
                        case "medical_card_number": 
                            p.setMedCardId(Integer.parseInt(data2[j+1])); 
                            break;
                        case "name":p.setPatientName(data2[j+1]); 
                                     break;
                        case "surname":  p.setPatientSurname(data2[j+1]);
                                        break;
                         case "dob": 
                             //Date dob = java.sql.Date.valueOf(data2[j+1]);
                           // p.setPatientDob(dob);
                            try{
                               p.setPatientDob(format.parse(data2[j+1]));
                            }catch(ParseException ex){
                                
                            }
                                     break;
                        case "address": p.setPatientAddress(data2[j+1]);
                                        break;
                        case "email": p.setPatientEmail(data2[j+1]);
                                     break;
                        case "diagnosis": p.setPatientDiagnosis(data2[j+1]);
                                         break;
                        case "allergies":  p.setPatientAllergies(data2[j+1]);
                                        break;
                        case "gender": p.setPatientGender(data2[j+1]);
                                        break;
                        case "userId": p.setUserId(Integer.parseInt(data2[j+1]));
                                        break;
                        case "macAddress": p.setMacAddress(data2[j+1]);
                                         break;
                    }
 
                }
                
             }
        System.out.println("Patient received:");
        System.out.println(p.toString());
        
        return p;
        }catch(IOException exception){
            return null;
        } catch (NotBoundException ex) {
             Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
             return null;
         }
       
    }
        public static Doctor receiveDoctor(BufferedReader bufferReader){
        System.out.println("in receive doctor");
        Doctor d= new Doctor();
        try{
            String line = bufferReader.readLine();
            System.out.println(line);
            line=line.replace("{", "");
            line=line.replace("}", "");
            line=line.replace("Doctor", "");
            String[] atribute = line.split(",");
            for (int i =0;i <atribute.length; i++){
                String[] data2 = atribute[i].split("=");
                for (int j =0;j <data2.length - 1; j++){
                    data2[j]=data2[j].replace(" ", "");
                    switch(data2[j]){
                        case "id":
                                        break;
                        case "name":d.setDoctorName(data2[j+1]); 
                                     break;
                        case "surname":d.setDoctorSurname(data2[j+1]);
                                        break;
                        case "email": d.setDoctorEmail(data2[j+1]); 
                                     break;
                        
                    }
 
                }
                
             }
        System.out.println("Doctor received:");
        System.out.println(d.toString());
        return d;
        
        }catch(IOException exception){
            return null;
        } catch (NotBoundException ex) {
             Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
             return null;
         }
         
    }
    public static void sendPatient (PrintWriter printWriter,PatientTS p){
        printWriter.println(p.toString());
    }
    public static void sendDoctor (PrintWriter printWriter, Doctor d){
        printWriter.println(d.toString());
    }
    public static void sendUser (PrintWriter printWriter,User u){
        System.out.println("in send user");
        printWriter.println(u.toString());
    }
    
    public static int sendAllSignal(BufferedReader bf, PrintWriter pw){
        System.out.println("Inside senAllSignals");
        String line;
        int medcard;
        try {
        if (bf.readLine().equalsIgnoreCase("Send signals")){
            
                medcard = bf.read();
                return medcard;
           
           }else{
            pw.println("error");
            return 0;
        }
         } catch (IOException ex) {
                Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
                pw.println("error");
                return 0;
         }
        }
    public static Signal recieveSignal(BufferedReader bf, PrintWriter pw){
        System.out.println("Inside recieve signal");
       
        try {
            Signal s = new Signal();

            //System.out.println(line);
            //line=line.replace("[", "");
            //line=line.replace("]","");
            //line=line.replace(",","");
            //String[] signals = line.split("// ");
            int[] ECG = new int[16];
            int[] EMG = new int[16];

            List<Integer> ecgVals = new ArrayList<>();
            List<Integer> emgVals = new ArrayList<>();

            boolean endECG = false;
            while (!endECG) {
                String line = bf.readLine();
                if (line != null) {
                    if (line.equals("END OF ECG")) {
                        endECG = true;
                    } else {

                        Integer ecg_val = Integer.parseInt(line);
                        ecgVals.add(ecg_val);
                    }

                } else {
                    System.out.println("Error");
                }
            }
            for (int i = 0; i < ecgVals.size(); i++) {
                ECG[i] = ecgVals.get(i);
            }

            boolean endEMG = false;
            while (!endEMG) {
                String line2 = bf.readLine();
                if (line2 != null) {
                    if (line2.equals("END OF EMG")) {
                        endEMG = true;
                    } else {
                        Integer emg_val = Integer.parseInt(line2);
                        emgVals.add(emg_val);
                    }
                } else {
                    System.out.println("Error");
                }
            }

            for (int j = 0; j < emgVals.size(); j++) {
                EMG[j] = emgVals.get(j);
            }

            System.out.println("ECG: " + ecgVals.toString() + "EMG: " + emgVals.toString());
            s.setECG_values(ECG);
            s.setEMG_values(EMG);
            
            
            //HAY QUE CONSEGUIR AQUI EL NOMBRE Y EL DATE DE LA SEÑAL Y AÑADIRLO  S
            
            return s;
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    
    }
    
    public static User receiveUser (BufferedReader br){
        System.out.println("in receive user");
        User u = new User();
        try {
        String line = br.readLine();
        line=line.replace("{", "");
        line=line.replace("User", "");
        line=line.replace("}", "");
        String[] atribute = line.split(",");
        for (int i =0;i <atribute.length; i++){
            String[] data2 = atribute[i].split("=");
            for (int j =0;j <data2.length - 1; j++){
                data2[j]=data2[j].replace(" ", "");
                switch(data2[j]){
                    case "username":
                        u.setUsername(data2[j+1]);
                        break;
                    case "password":
                        u.setPassword(data2[j+1]);
                        break;
                    case "role":
                        u.setRole(Integer.parseInt(data2[j+1]));
                        break;
                    case "userId":
                        u.setUserId(Integer.parseInt(data2[j+1]));
                        break;
                }
            }
        }
            System.out.println(u.toString());
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    
    public static void releaseResources(BufferedReader bufferedReader, Socket socket, ServerSocket serverSocket) {
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
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
    

