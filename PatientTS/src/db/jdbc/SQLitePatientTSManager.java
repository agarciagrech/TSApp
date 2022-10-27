/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jdbc;
import db.pojos.Signal;
import db.pojos.PatientTS;
import BITalino.BITalino;
import BITalino.BITalinoException;
import db.interfaces.PatientTSManager;
//import java.sql.*;
import java.util.*;
import BITalino.BitalinoDemo;
import BITalino.Frame;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
     

    /**
     * Creates and adds a new patient into the database
     * @param p - [PatientTS] Patient that is added to the database
     * @throws SQLException
     */
    @Override
    public void addPatient(PatientTS p) throws SQLException{
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
                String sq1 = "INSERT INTO patient ( medical_card_number, name, surname, dob, address, email, diagnosis, allergies, gender, macAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
    @Override
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
			pStatement.setDate(1, (java.sql.Date) dob);
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
    public PatientTS selectPatientByUserId(Integer userId) throws SQLException, NotBoundException {
        String sql = "SELECT * FROM patient WHERE userId = ?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setInt(1,userId);
        ResultSet rs = p.executeQuery();
        PatientTS patient = null;
        if(rs.next()){
                patient = new PatientTS(rs.getInt("medical_card_number"),rs.getString("name"),rs.getString("surname"),rs.getDate("dob"), 
                rs.getString("address"),rs.getString("email"),rs.getString("diagnosis"),rs.getString("allergies"),rs.getString("gender"));
        }
        p.close();
        rs.close();
        return patient;	
    }
    
    /**
     * Selects a patient by using the patients's medical card number.
     * @param medCard - [Integer] Medical cavd number of the patient.
     * @return - [PatientTS] The patient to whom the inserted medical card number corresponds.
     * @throws SQLException
     * @throws NotBoundException
     */
    @Override
    public PatientTS selectPatient(Integer medCard) throws SQLException, NotBoundException {
        String sql = "SELECT * FROM patients WHERE medical_card_number = ?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setInt(1,medCard);
        ResultSet rs = p.executeQuery();
        PatientTS patient = null;
        if(rs.next()){
                patient = new PatientTS(rs.getInt("medical_card_number"),rs.getString("name"),rs.getString("surname"),rs.getDate("dob"), 
                rs.getString("address"),rs.getString("email"),rs.getString("diagnosis"),rs.getString("allergies"),rs.getString("gender"));
        }
        p.close();
        rs.close();
        return patient;	
    }

    /**
     * Select every patient related to the doctor
     * @return - [List] List of all the patients.
     * @throws SQLException
     * @throws NotBoundException
     */
    @Override
    public List<PatientTS> selectAllPatients() throws SQLException, NotBoundException {
    String sql = "SELECT * FROM patient";
		PreparedStatement p = c.prepareStatement(sql);
		ResultSet rs = p.executeQuery();
		List <PatientTS> pList = new ArrayList<PatientTS>();
		while(rs.next()){
			pList.add(  new PatientTS(rs.getInt("medical_card_number"),rs.getString("name"),rs.getString("surname"),rs.getDate("dob"), 
                        rs.getString("address"),rs.getString("email"),rs.getString("diagnosis"),rs.getString("allergies"),rs.getString("gender")));
        }
		p.close();
		rs.close();
		return pList;
    }
    
    @Override
    public void recordSignal(PatientTS p, String sname) {
        Frame[] frame;
        BITalino bitalino = null;
        Signal s = new Signal();
        int[] ecg_values = new int[1000000];
        int[] emg_values = new int[100];
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

                for (int i = 0; i < frame.length; i++) {
                    ecg_values[i]=frame[i].analog[0];
                    emg_values[i]=frame[i].analog[1];
                    System.out.println(" seq: " + frame[i].seq + " "
                            + frame[i].analog[0] + " ");
                }
                s.setECG_values(ecg_values);
                s.setEMG_values(emg_values);
                 
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
        try {
            //ruta + signal_name + date ".txt"
            String ruta = "/TSApp/ECG.txt";
            String ruta2 = "TSApp/EMG.txt";
            String contenido = Arrays.toString(s.getECG_values());
            String contenido2 = Arrays.toString(s.getEMG_values());
            File file = new File(ruta);
            File file2 = new File(ruta2);
            // Si el archivo no existe es creado
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deletes any patient with a medical card number that matches the given medical card number.
     * @param medCardNumber - [Integer] Medical card number from the patient that will be deleted.
     * @throws SQLException
     */
    @Override
    public void deletePatientByMedicalCardId(Integer medCardNumber) throws SQLException{
        String sql = "DELETE FROM patient WHERE medical_card_number = ?";
        PreparedStatement pStatement = c.prepareStatement(sql);
        pStatement.setInt(1, medCardNumber);
        pStatement.executeUpdate();
        pStatement.close();
    }

    /**
     * Associates a doctor with a patient
     * @param medCardNumber
     * @param doctorId
     * @throws SQLException
     */
    @Override
    public void createLinkDoctorPatient(int medCardNumber, int doctorId) throws SQLException {
        String sql = "INSERT INTO doctor_patient (patient_id, doctor_id) VALUES (?,?)";
        PreparedStatement pStatement = c.prepareStatement(sql);
        pStatement.setInt(1, medCardNumber);
        pStatement.setInt(2, doctorId);
        pStatement.executeUpdate();
        pStatement.close();
    }

    /**
     * Associates a patient with a user of the application
     * @param userId
     * @param medCardNumber
     * @throws Exception
     */
    @Override
    public void createLinkUserPatient(int userId, int medCardNumber) throws Exception {
        String sql1 = "UPDATE patient SET userId = ? WHERE medical_card_number = ? ";
        PreparedStatement pStatement = c.prepareStatement(sql1);
        pStatement.setInt(1, userId);
        pStatement.setInt(2, medCardNumber);
        pStatement.executeUpdate();
        pStatement.close();
    }
}