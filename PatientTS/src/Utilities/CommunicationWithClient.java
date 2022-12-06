/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import db.interfaces.SignalManager;
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
    
    public static SQLiteSignalManager sman = new SQLiteSignalManager();
    
    public static void sendPatientList (List<PatientTS> patientList,PrintWriter pw,BufferedReader bf){
        for (int i =0;i<patientList.size();i++){
          pw.println("Patient:"+patientList.get(i).getPatientName()+"/"+patientList.get(i).getPatientSurname()+"/"+patientList.get(i).getMedCardId());
        }
        pw.println("End of list");
    }
    public static PatientTS receivePatient(BufferedReader bufferReader){
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
        printWriter.println(u.toString());
    }
    
    public static void sendAllSignal(BufferedReader bf, PrintWriter pw, SignalManager sman, int medcard){
        List<String> filenames = sman.ListSignalsFilenamesByPatient(medcard); 
        pw.println(filenames.size()); 
        for (int i=0; i<filenames.size();i++){
            pw.println(filenames.get(i));
        }
    }
    
    public static Signal recieveSignal(BufferedReader bf, PrintWriter pw){
        try {
            Signal s = new Signal();
            List<Integer> ecgVals = new ArrayList<>();
            List<Integer> emgVals = new ArrayList<>();

            int ecg_size = Integer.parseInt(bf.readLine());
            for (int i =0;i<ecg_size;i++){
                Integer ecg_val = Integer.parseInt(bf.readLine());
                ecgVals.add(i,ecg_val);
            }
            int emg_size = Integer.parseInt(bf.readLine());
            for (int i =0;i<emg_size;i++){
                Integer emg_val = Integer.parseInt(bf.readLine());
                emgVals.add(i,emg_val);
            }
               
            System.out.println("ECG: " + ecgVals.toString() + "EMG: " + emgVals.toString());
            s.setECG_values(ecgVals);
            s.setEMG_values(emgVals);
            
            return s;
            
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static User receiveUser (BufferedReader br){
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
    

