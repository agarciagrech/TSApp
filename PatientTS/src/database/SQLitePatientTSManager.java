/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import Pojos.PatientTS;
import Pojos.TypeOfSignal;
import db.interfaces.PatientTSManager;
import java.sql.Connection;
import java.util.List;
import BITalino.BitalinoDemo;
import Pojos.Signal;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author agarc
 */
public class SQLitePatientTSManager {
    
    private Connection c;
    
    public SQLitePatientTSManager(Connection c){
        this.c = c;
    }
      public SQLitePatientTSManager() {
        super();
    }
    public void recordSignal(PatientTS p, TypeOfSignal type, String signal_file) throws IOException{
       
        List<Integer> signal_list = BITalino.BitalinoDemo.main();
        
        PrintWriter pw = null; 
        try{
            pw = new PrintWriter(signal_file);
            for(int i = 0; i<signal_list.size(); i++){
                pw.print(signal_list.get(i) + "\n");
            }
        }catch(IOException e){
            
        }finally{
            if(pw != null){
            pw.close();
        }
        }
        
        String filePath_signal = signal_file;

       
        byte[] patient_signal = Files.readAllBytes(Paths.get(filePath_signal));
            
        Signal s = new Signal(patient_signal,type);
        //HAY QUE HACER EL INSERT EN LA DB
    }
}
