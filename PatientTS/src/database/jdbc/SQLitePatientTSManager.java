/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;
import BITalino.BITalino;
import BITalino.BITalinoException;
import database.pojos.*;
import db.interfaces.PatientTSManager;
//import java.sql.*;
import java.util.*;
import BITalino.BitalinoDemo;
import static BITalino.BitalinoDemo.arraySignal;
import static BITalino.BitalinoDemo.frame;
import BITalino.Frame;
import java.io.*;
import java.nio.file.*;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;

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
    public PatientTS selectPatient(Integer userId) {
        PatientTS newPatient = null;
        try {
            String sql = "SELECT * FROM patient" + " WHERE id = ?"; //no estoy segura de que aqui sea id
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setInt(1, userId);
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
                    Integer patientuserId = rs.getInt(10);
                    String patientmacAddress = rs.getString(11);
                            
                    newPatient = new PatientTS(patientMedCard, patientName, patientSurname,
                            patientdob, patientAddress, patientEmail, patientDiagnosis, patientAllergies, patientGender,patientuserId,patientmacAddress);

                    patientCreated = true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newPatient;
        }

    @Override
    public void recordSignal(PatientTS p, String name) {
        Frame[] frame;
        List <Integer> arraySignal = new ArrayList <Integer>();
        
        BITalino bitalino = null;
        Signal s = null;
        byte[] ecg_values = null;
        byte[] emg_values = null;
        try {
            bitalino = new BITalino();
            // Code to find Devices
            //Only works on some OS
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

           
            String macAddress = p.getMacAddress(); 
            
            //Sampling rate, should be 10, 100 or 1000
            int SamplingRate = 10; //PONER COMO PARAMETRO?
            bitalino.open(macAddress, SamplingRate);

            // Start acquisition on analog channels A2 and A6
            // For example, If you want A1, A3 and A4 you should use {0,2,3}
            int[] channelsToAcquire = {1,2}; //for ECG and EMG
            bitalino.start(channelsToAcquire);

            //Read in total 10000000 times --> por que elegimos este num
            for (int j = 0; j < 10000000; j++) {

                //Each time read a block of 10 samples --> por que elegimos este num
                int block_size=10;
                frame = bitalino.read(block_size);

                System.out.println("size block: " + frame.length);

                //Store the samples --> preguntar si se guarda el fichero 
                for (int i = 0; i < frame.length; i++) {
                    arraySignal.add(frame[i].analog[2]);
                    System.out.println(" seq: " + frame[i].seq + " "
                            + frame[i].analog[0] + " ");
                    
                    
                    
                }
            }
            //stop acquisition
            bitalino.stop();
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //close bluetooth connection
                if (bitalino != null) {
                    bitalino.close();
                }
            } catch (BITalinoException ex) {
                Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //ecg_values = 
        //emg_values = PREGUNTAR COMO COGER CADA COLUMNA DEL FRAME
        s = new Signal(ecg_values, emg_values, name); 

    }

   
    @Override
    public void createPatient(PatientTS p) {
        if (p.getPatientAllergies()==null) {
                String sq1 = "INSERT INTO patient ( medical_card_number, name, surname, dob, address, email, diagnosis, gender, macAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                    preparedStatement.setString(9, p.getMacAddress());
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
    //@Override SERIA IGUAL QUE EL DE MEDCARD
    /*public PatientTS selectPatientByUserId(Integer userId) {
           try{
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
    }*/

    

   
 }
