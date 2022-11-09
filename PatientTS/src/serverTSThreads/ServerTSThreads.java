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
    public static Socket socketPatient;
    public static Socket socketDoctor;
    public static ServerSocket serverSocketPatient; 
    public static ServerSocket serverSocketDoctor;
    public static PatientThread pThread;
    public static DoctorThread dThread;
    
    public static Thread[] patientThreadsList;
    public static Thread[] doctorThreadsList;
    
    public static void main(String[] args) throws IOException{
       
        serverSocketPatient = new ServerSocket(); //IP, PORT?
        serverSocketDoctor = new ServerSocket(); //IP, PORT?
        
        patientThreadsList = new Thread[1000];
        doctorThreadsList = new Thread[10000];
        //COMO COMPROBAR SI ES UN PATIENT O UN DOCTOR??
        while(true){
            int contadorPatients = 0;
            int contadorDoctors = 0;
            if(Patient){
                socketPatient = serverSocketPatient.accept();
                pThread = new PatientThread(socketPatient);
                Thread patientThread =  new Thread(pThread);
                patientThreadsList[contadorPatients] = patientThread;
                contadorPatients ++;
            
        }else if(Doctor){
            socketDoctor = serverSocketDoctor.accept();
            dThread = new DoctorThread(socketDoctor);
            Thread doctorThread = new Thread(dThread);
            doctorThreadsList[contadorDoctors] = doctorThread;
            contadorDoctors ++; 
        }
                //DONDE HACER EL RELEASE RESOURCES 
                
        }
        
        
        
    }
    
    public static void ReleaseResourcesServerTSPatient(ServerSocket severSocketPatient){
        try{
            serverSocketPatient.close();
        }catch(IOException ex){
            Logger.getLogger(ServerTSThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static void ReleaseResourcesServerTSDoctor(ServerSocket severSocketDoctor){
        try{
            serverSocketDoctor.close();
        }catch(IOException ex){
            Logger.getLogger(ServerTSThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
