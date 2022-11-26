/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jdbc;
import db.pojos.PatientTS;
import db.interfaces.PatientTSManager;
//import java.sql.*;
import java.util.*;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;


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
     

    /**
     * Creates and adds a new patient into the database
     * @param p - [PatientTS] Patient that is added to the database
     * @throws SQLException
     */
      
    @Override
    public void addPatient(PatientTS p) {
        if (p.getPatientAllergies()==null) {
                String sq1 = "INSERT INTO patient ( medical_card_number, name, surname, dob, address, email, diagnosis, gender, macAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sq1);
                    preparedStatement.setInt(1, p.getMedCardId());
                    preparedStatement.setString(2, p.getPatientName());
                    preparedStatement.setString(3, p.getPatientSurname());
                    preparedStatement.setString(4, p.formatDate(p.getPatientDob()));
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
                String sq1 = "INSERT INTO patient ( medical_card_number, name, surname, dob, address, email, diagnosis, allergies, gender, macAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sq1);
                    preparedStatement.setInt(1, p.getMedCardId());
                    preparedStatement.setString(2, p.getPatientName());
                    preparedStatement.setString(3, p.getPatientSurname());
                    preparedStatement.setString(4,  p.formatDate(p.getPatientDob()));
                    preparedStatement.setString(5, p.getPatientAddress());
                    preparedStatement.setString(6, p.getPatientEmail());
                    preparedStatement.setString(7, p.getPatientDiagnosis());
                    preparedStatement.setString(8, p.getPatientAllergies());
                    preparedStatement.setString(9, p.getPatientGender());
                    preparedStatement.setString(10, p.getMacAddress());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException ex) {
                Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    /**
     * Edit the information of a patient
     * @param medCardNum
     * @param name
     * @param surname
     * @param dob
     * @param address
     * @param email
     * @param diagnosis
     * @param allergies
     * @param gender
     * @param macAd
     * @return
     * TODO edit signal
     */
	public boolean editPatient(Integer medCardNum, String name, String surname, Date dob, String address, 
                String email, String diagnosis, String allergies, String gender, String macAd) {

		String sql;
		PreparedStatement pStatement;
                try {
		if (name != null) {
			sql = "UPDATE patient SET name = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, name);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();	
		} 
		if (surname != null) {
			sql = "UPDATE patient SET surname = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, surname);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
		}
                
                if (dob != null) {
			sql = "UPDATE patient SET dob = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1,formatDate(dob));
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
		}
                
                if (address != null) {
			sql = "UPDATE patient SET address = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, address);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();	
		}
                
                if (email != null) {
			sql = "UPDATE patient SET email = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, email);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();	
		}
                
                if (diagnosis != null) {
			sql = "UPDATE patient SET diagnosis = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, diagnosis);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();	
		}
                
                if (allergies != null) {
			sql = "UPDATE patient SET allergies = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, allergies);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
		}
                
		if (gender != null) {
			sql = "UPDATE patient SET gender = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, gender);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();	
		}
		
		if (macAd != null) {
			sql = "UPDATE patient SET macAddress = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, macAd);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
		}
                
		
		   return true;
            } catch (SQLException update_patient_error) {
                update_patient_error.printStackTrace();
                return false;
            }
	}

    /**
     * Selects a patient by using the patients's user Id.
     * @param userId - [Integer] User Id related to the patient
     * @return - [PatientTS] The patient to whom the inserted user Id corresponds.
     * @throws SQLException
     * @throws NotBoundException
     */
    @Override
    public PatientTS selectPatientByUserId(Integer userId){
        try {
            Date date;
            String sql = "SELECT * FROM patient WHERE userId = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1,userId);
            ResultSet rs = p.executeQuery();
            PatientTS patient = null;
            if(rs.next()){
                patient = new PatientTS(rs.getInt("medical_card_number"),rs.getString("name"),rs.getString("surname"),date = new Date(rs.getString("dob")),
                        rs.getString("address"),rs.getString("email"),rs.getString("diagnosis"),rs.getString("allergies"),rs.getString("gender"),rs.getString("macAddress"));
            }
            p.close();
            rs.close();
            return patient;
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Selects a patient by using the patients's medical card number.
     * @param medCard - [Integer] Medical cavd number of the patient.
     * @return - [PatientTS] The patient to whom the inserted medical card number corresponds.
     * @throws SQLException
     * @throws NotBoundException
     */
    @Override
    public PatientTS selectPatient(Integer medCard){
        try {
            Date date;
            String sql = "SELECT * FROM patient WHERE medical_card_number = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1,medCard);
            ResultSet rs = p.executeQuery();
            PatientTS patient = null;
            if(rs.next()){
                patient = new PatientTS(rs.getInt("medical_card_number"),rs.getString("name"),rs.getString("surname"),date = new Date(rs.getString("dob")),
                        rs.getString("address"),rs.getString("email"),rs.getString("diagnosis"),rs.getString("allergies"),rs.getString("gender"),rs.getString("macAddress"));
            }
            p.close();
            rs.close();
            return patient;
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }

    /**
     * Select every patient related to the doctor
     * @return - [List] List of all the patients.
    
     */
    @Override
    public List<PatientTS> selectAllPatients(){
        try {
            String sql = "SELECT * FROM patient";
            PreparedStatement p = c.prepareStatement(sql);
            
            ResultSet rs = p.executeQuery();
            List <PatientTS> pList = new ArrayList<PatientTS>();
            Date date;
            while(rs.next()){ 
            
                pList.add(  new PatientTS(rs.getInt("medical_card_number"),rs.getString("name"),rs.getString("surname"),date = new Date(rs.getString("dob")),
                          rs.getString("address"),rs.getString("email"),rs.getString("diagnosis"),rs.getString("allergies"),rs.getString("gender"),rs.getString("macAddress")));
                
            }
            p.close();
            rs.close();
            return pList;
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    /**
     * Select every patient related to the doctor
     * @param doctorId
     * @return - [List] List of all the patients.
     
     */
    
    public List<PatientTS> selectPatientsByDoctorId(int doctorId){
        try {
            String sql = "SELECT * FROM doctor_patient WHERE doctor_id = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1,doctorId);
            ResultSet rs = p.executeQuery();
            List <PatientTS> pList = new ArrayList<PatientTS>();
            
            while(rs.next()){ 
                pList.add(selectPatient(rs.getInt("patient_id")));
            }
            p.close();
            rs.close();
            return pList;
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    
    /**
     * Deletes any patient with a medical card number that matches the given medical card number.
     * @param medCardNumber - [Integer] Medical card number from the patient that will be deleted.
     * @throws SQLException
     */
    @Override
    public void deletePatientByMedicalCardId(Integer medCardNumber){
        try {
            String sql = "DELETE FROM patient WHERE medical_card_number = ?";
            PreparedStatement pStatement = c.prepareStatement(sql);
            pStatement.setInt(1, medCardNumber);
            pStatement.executeUpdate();
            pStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    /**
     * Associates a doctor with a patient
     * @param medCardNumber
     * @param doctorId
     * @throws SQLException
     */
    @Override
    public void createLinkDoctorPatient(int medCardNumber, int doctorId){
        try {
            String sql = "INSERT INTO doctor_patient (patient_id, doctor_id) VALUES (?,?)";
            PreparedStatement pStatement = c.prepareStatement(sql);
            pStatement.setInt(1, medCardNumber);
            pStatement.setInt(2, doctorId);
            pStatement.executeUpdate();
            pStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Associates a patient with a user of the application
     * @param userId
     * @param medCardNumber
     * @throws Exception
     */
    @Override
    public void createLinkUserPatient(int userId, int medCardNumber){
        try {
            String sql1 = "UPDATE patient SET userId = ? WHERE medical_card_number = ? ";
            PreparedStatement pStatement = c.prepareStatement(sql1);
            pStatement.setInt(1, userId);
            pStatement.setInt(2, medCardNumber);
            pStatement.executeUpdate();
            pStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String formatDate (Date dob){
        SimpleDateFormat  formato = new SimpleDateFormat("YYYY/MM/dd");
        return formato.format(dob);
    }
    
   
}