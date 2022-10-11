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
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     
    /*public void recordSignal(PatientTS p, TypeOfSignal type, String signal_file) throws IOException, Exception{
       
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
        
        
    }*/

    @Override
    public PatientTS selectPatient(Integer medcard) {
        PatientTS newPatient = null;
        try {
            String sql = "SELECT * FROM patient" + " WHERE id = ?"; //no estoy segura de que aqui sea id
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
                    String patientDiagnosis = rs.getString(7);
                    String patientAllergies = rs.getString(8);
                    String patientGender = rs.getString(9);

                    newPatient = new PatientTS(patientMedCard, patientName, patientSurname,
                            patientdob, patientAddress, patientEmail, patientDiagnosis, patientAllergies, patientGender);

                    patientCreated = true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newPatient;
        }

    @Override
    public void recordSignal(PatientTS p, String type, String signal_file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    @Override
    public void createPatient(PatientTS p) {
        if (p.getPatientAllergies()==null) {
                String sq1 = "INSERT INTO patient ( medical_card_number, name, surname, dob, address, email, diagnosis, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sq1);
                    preparedStatement.setInt(1, p.getMedCardId());
                    preparedStatement.setString(2, p.getPatientName());
                    preparedStatement.setString(3, p.getPatientSurname());
                    preparedStatement.setDate(4, (java.sql.Date) p.getPatientDob());
                    preparedStatement.setString(5, p.getPatientAddress());
                    preparedStatement.setString(6, p.getPatientEmail());
                    preparedStatement.setString(7, p.getPatientDiagnosis());
                    preparedStatement.setString(8, p.getPatientGender());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException ex) {
                Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
                String sq1 = "INSERT INTO patient ( medical_card_number, name, surname, dob, address, email, diagnosis, allergies, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sq1);
                    preparedStatement.setInt(1, p.getMedCardId());
                    preparedStatement.setString(2, p.getPatientName());
                    preparedStatement.setString(3, p.getPatientSurname());
                    preparedStatement.setDate(4, (java.sql.Date) p.getPatientDob());
                    preparedStatement.setString(5, p.getPatientAddress());
                    preparedStatement.setString(6, p.getPatientEmail());
                    preparedStatement.setString(7, p.getPatientDiagnosis());
                    preparedStatement.setString(8, p.getPatientAllergies());
                    preparedStatement.setString(9, p.getPatientGender());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException ex) {
                Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public PatientTS selectPatientByUserId(Integer userId) throws SQLException, NotBoundException {
            String sql = "SELECT * FROM patient WHERE userId = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1,userId);
            ResultSet rs = p.executeQuery();
            PatientTS patient = null;
            if(rs.next()){
                    patient = new PatientTS(rs.getInt("medical_card_number"), 
                            rs.getString("name"), 
                            rs.getString("surname"), 
                            rs.getDate("dob"),
                            rs.getString("address"),
                            rs.getString("email"),
                            rs.getString("diagnosis"),
                            rs.getString("allergies"),
                            rs.getString("gender"));
            }
            p.close();
            rs.close();
            return patient;	
    }
 }
