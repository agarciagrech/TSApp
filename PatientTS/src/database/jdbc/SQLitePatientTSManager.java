/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;
import database.pojos.*;
import db.interfaces.PatientTSManager;
//import java.sql.*;
import java.util.*;
import BITalino.BitalinoDemo;
import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author agarc
 */
public class SQLitePatientTSManager implements PatientTSManager {
    
    private Connection c;
    
    public SQLitePatientTSManager(Connection c){
        this.c = c;
    }
      public SQLitePatientTSManager() {
        super();
    }
     @Override
    public void recordSignal(PatientTS p, TypeOfSignal type, String signal_file) throws IOException, Exception{
       
        List<Integer> signal_list = BITalino.BitalinoDemo.main(); //PREGUNTAR
        
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
            
        Signal s = new Signal(patient_signal,filePath_signal,type);
        //HAY QUE HACER EL INSERT EN LA DB
        
        
    }

    @Override
    public PatientTS selectPatient(Integer medcard) {
        PatientTS newPatient = null;
        try {
            String sql = "SELECT * FROM patient" + " WHERE id = ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setInt(1, medcard);
            ResultSet rs = prep.executeQuery();
            boolean patientCreated = false;
            while (rs.next()) {
                if (!patientCreated) {
                    Integer patientMedCard = rs.getInt(1);
                    String patientName = rs.getString(2);
                    String patientSurname = rs.getString(3);
                    Date patientdob = rs.getDate(4);
                    String patientAddress = rs.getString(5);
                    String patientEmail = rs.getString(6);
                    String patientGender = rs.getString(7);

                    newPatient = new PatientTS(patientMedCard, patientName, patientSurname,
                            patientdob, patientAddress, patientEmail,patientGender);

                    patientCreated = true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newPatient;
        }
    }
