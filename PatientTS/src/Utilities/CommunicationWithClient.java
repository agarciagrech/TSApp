/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author albic
 */
public class CommunicationWithClient {
//     public static void main(String args[]) throws IOException, NotBoundException {
//        ServerSocket serverSocket = new ServerSocket(9009);
//        Socket socket = serverSocket.accept();
//        System.out.println("Connection client created");
//        BufferedReader bufferedReader = new BufferedReader(
//        new InputStreamReader(socket.getInputStream()));
//        System.out.println("Text Received:\n");
//        String line;
//       
//        while ((line = bufferedReader.readLine()) != null) {
//            if (line.toLowerCase().contains("stop")) {
//                System.out.println("Stopping the server");
//                releaseResources(bufferedReader, socket, serverSocket);
//                System.exit(0);
//            }
//            System.out.println(line);
//           Signal s = new Signal();
//           
//           
//           line=line.replace("[", "");
//           line=line.replace("]","");
//           line=line.replace(",","");
//           String[] signals = line.split("/n");
//           for (int j=0; j < signals.length;j++){
//              String[] lines;
//              lines=signals[j].split(" ");
//              
//           
//            int [] ECG= new int[10];
//            int [] EMG= new int[10];
//            if (lines[0].equals("ECG")){
//
//                for (int i = 1; i<lines.length; i++){
//                     ECG[i-1]=Integer.parseInt(lines[i]);
//                }
//               s.setECG_values(ECG);
//               System.out.println("Siganl saved");
//                System.out.println(Arrays.toString(s.getECG_values())); 
//            } else{
//                for (int i = 1; i<lines.length; i++){
//                     EMG[i-1]=Integer.parseInt(lines[i]);
//                }
//               s.setEMG_values(EMG);
//               System.out.println("Siganl saved");
//               System.out.println(Arrays.toString(s.getEMG_values())); 
//                
//            }
//
//
//            }
//           
//            
//        }
//        
//        
//    }
    // ToDo : Al final de los métodos deberían de insertarse en la db 
    public static void sendPatientList (PrintWriter pw,BufferedReader bf) throws NotBoundException{
        List<PatientTS> patientList = new ArrayList<>();
        // DO HERE: Get arrayList from db, till db works we create a list to try the method
        Date dob = new Date("1/2/2000");
        PatientTS p1 = new PatientTS (123,"paquito","perez",dob,"Calle 1","p@gamil.com","diabetes","lala","male");
        PatientTS p2 = new PatientTS (456,"pepe","perez",dob,"Calle 1","p@gamil.com","diabetes","lala","male");
        PatientTS p3 = new PatientTS (789,"Juan","perez",dob,"Calle 1","p@gamil.com","diabetes","lala","male");
        patientList.add(p1);
        patientList.add(p2);
        patientList.add(p3);
        for (PatientTS p : patientList){
            System.out.println("Patient:"+p.getPatientName()+"/"+p.getPatientSurname()+"/"+p.getMedCardId());
           pw.println("Patient:"+p.getPatientName()+"/"+p.getPatientSurname()+"/"+p.getMedCardId());
            
        }
        pw.println("End of list");
    }
    public static boolean receivePatient(BufferedReader bufferReader){
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
        System.out.println("Patient received:");
        System.out.println(p.toString());
        
        
        }catch(IOException exception){
            recieved = false;
        } catch (NotBoundException ex) {
             Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
         }
        return recieved; 
    }
        public static boolean receiveDoctor(BufferedReader bufferReader){
        boolean recieved = true; 
        Doctor d= new Doctor();
        try{
            String line = bufferReader.readLine();
            line=line.replace("{", "");
            line=line.replace("Doctor", "");
            String[] atribute = line.split(",");
            for (int i =0;i <atribute.length; i++){
                String[] data2 = atribute[i].split("=");
                for (int j =0;j <data2.length - 1; j++){
                    data2[j]=data2[j].replace(" ", "");
                    switch(data2[j]){
                        case "doctorId":d.setDoctorId(Integer.parseInt(data2[j+1]));
                                        break;
                        case "dname":d.setDoctorName(data2[j+1]); 
                                     break;
                        case "dsurname":d.setDoctorSurname(data2[j+1]);
                                        break;
                        case "demail": d.setDoctorEmail(data2[j+1]); 
                                     break;
                        
                    }
 
                }
                
             }
        System.out.println("Doctor received:");
        System.out.println(d.toString());
        
        
        }catch(IOException exception){
            recieved = false;
        } catch (NotBoundException ex) {
             Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
         }
        return recieved; 
    }
    public static void sendPatient (PrintWriter printWriter,PatientTS p){
        printWriter.println(p.toString());
    }
    public static void recieveSignal(BufferedReader bf, PrintWriter pw){
        System.out.println("Inside recieve signal");
         try {
            Signal s = new Signal();
            String line = bf.readLine();
            System.out.println(line);
            line=line.replace("[", "");
            line=line.replace("]","");
            line=line.replace(",","");
            String[] signals = line.split("// ");
            int [] ECG= new int[10];
            int [] EMG= new int[10];
            String[] lines;
           
            for (int j=0; j < signals.length; j++){            
                lines=signals[j].split(" ");
                String option = lines[0];
                switch(option){
                    case "ECG:": 
                        for (int i = 1; i<lines.length; i++){
                         ECG[i-1]=Integer.parseInt(lines[i]);
                        }
                        s.setECG_values(ECG);
                        System.out.println("Siganl saved");
                        break;
                    case "EMG:":
                        for (int i = 1; i<lines.length; i++){
                            EMG[i-1]=Integer.parseInt(lines[i]);
                        }
                        s.setEMG_values(EMG);
                        System.out.println("Siganl saved");
                        break;
                    default:
                        break;
                }
            }
            
            System.out.println("ECG: " + Arrays.toString(ECG) + " EMG: " + Arrays.toString(EMG));
            String line2 = bf.readLine();
            String line3 = bf.readLine();
            
            System.out.println(line2);
            System.out.println(line3);
             
            String filenames1[] = line2.split("=");
            String filenames2[] = line3.split("=");
            
             String ruta = "../PatientTS/"+filenames1[1]+".txt";
             String ruta2 = "../PatientTS/"+filenames2[1]+".txt";
                String contenido = Arrays.toString(ECG);
                String contenido2 = Arrays.toString(EMG);
                File file = new File(ruta);
                File file2 = new File(ruta2);
                if (!file.exists()) {
                    file.createNewFile();
                }
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileWriter fwECG = new FileWriter(file);
                FileWriter fwEMG = new FileWriter(file2);
                BufferedWriter bwECG = new BufferedWriter(fwECG);
                BufferedWriter bwEMG = new BufferedWriter(fwEMG);
                bwECG.write(contenido);
                bwEMG.write(contenido2);
                bwECG.close();
                bwEMG.close();
                
                System.out.println("Ok");
             
         } catch (IOException ex) {
             Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
         }
       
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
    

