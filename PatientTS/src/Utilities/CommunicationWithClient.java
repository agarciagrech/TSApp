/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import db.pojos.Doctor;
import db.pojos.PatientTS;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author albic
 */
public class CommunicationWithClient {
     public static void main(String args[]) throws IOException, NotBoundException {
        ServerSocket serverSocket = new ServerSocket(9009);
        Socket socket = serverSocket.accept();
        System.out.println("Connection client created");
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        System.out.println("Text Received:\n");
        String line;
        
        while ((line = bufferedReader.readLine()) != null) {
            if (line.toLowerCase().contains("stop")) {
                System.out.println("Stopping the server");
                releaseResources(bufferedReader, socket, serverSocket);
                System.exit(0);
            }
            System.out.println(line);
            
            
        }
        
        
    }
    // ToDo : Al final de los métodos deberían de insertarse en la db 
    public static boolean recievePatient(BufferedReader bufferReader){
        boolean recieved = true; 
        PatientTS p = new PatientTS();
        try{
            String line = bufferReader.readLine();
            line=line.replace("{", "");
            line=line.replace("Patient", "");
            String[] atribute = line.split(",");
            SimpleDateFormat  format = new SimpleDateFormat("dd/MM/yyyy"); 
        
            for (int i =0;i <atribute.length; i++){
                String[] data2 = atribute[i].split("=");
                for (int j =0;j <data2.length - 1; j++){
                    data2[j]=data2[j].replace(" ", "");
                    switch(data2[j]){
                        case "medical_card_number": p.setMedCardId(Integer.parseInt(data2[j+1])); 
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
        System.out.println("Patient recieved:");
        System.out.println(p.toString());
        
        
        }catch(IOException exception){
            recieved = false;
        } catch (NotBoundException ex) {
             Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
         }
        return recieved; 
    }
        public static boolean recieveDoctor(BufferedReader bufferReader){
        boolean recieved = true; 
        Doctor d= new Doctor();
        try{
            String line = bufferReader.readLine();
            line=line.replace("{", "");
            line=line.replace("Patient", "");
            String[] atribute = line.split(",");
            for (int i =0;i <atribute.length; i++){
                String[] data2 = atribute[i].split("=");
                for (int j =0;j <data2.length - 1; j++){
                    data2[j]=data2[j].replace(" ", "");
                    switch(data2[j]){
                         case "name":d.setDoctorName(data2[j+1]); 
                                     break;
                        case "surname":d.setDoctorSurname(data2[j+1]);
                                        break;
                        case "email": d.setDoctorEmail(data2[j+1]); 
                                     break;
                        case "id":d.setDoctorId(Integer.parseInt(data2[j+1]));
                                        break;
                    }
 
                }
                
             }
        System.out.println("Doctor recieved:");
        System.out.println(d.toString());
        
        
        }catch(IOException exception){
            recieved = false;
        } catch (NotBoundException ex) {
             Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
         }
        return recieved; 
    }
    
    private static void releaseResources(BufferedReader bufferedReader, Socket socket, ServerSocket serverSocket) {
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
    

