/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.jdbc;

import db.pojos.Doctor;
import db.pojos.PatientTS;
import db.interfaces.DoctorManager;
import java.sql.*;
import java.util.*;
import java.rmi.*;

/**
 *
 * @author gisel
 */
public class SQLiteDoctorManager implements DoctorManager{
    private Connection c;
    
    public SQLiteDoctorManager(Connection c){
        this.c = c;
    }
      public SQLiteDoctorManager() {
        super();
    }

    /**
     * Creates and adds a new doctor into the database
     * @param d - [Doctor] Doctor that is added to the database
     * @throws SQLException
     */
    @Override
    public void addDoctor(Doctor d) throws SQLException {
        String sq1 = "INSERT INTO doctor (dname, dsurname, demail) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setString(1, d.getDoctorName());
			preparedStatement.setString(2, d.getDoctorSurname());
			preparedStatement.setString(3, d.getDoctorEmail());
			preparedStatement.executeUpdate();
			preparedStatement.close();	
    }

    /**
     * Searches patients with the given surname.
     * @param surname - [String] Surname of the patient the doctor is looking for
     * @return - List of patients with the same surname that the docotr has been looking for
     * @throws SQLException
     * @throws NotBoundException
     */
    @Override
    public List<PatientTS> searchPatient(String surname) throws SQLException, NotBoundException {
        String sql = "SELECT * FROM patient WHERE surname LIKE ?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1,"%" + surname + "%");
        ResultSet rs = p.executeQuery();
        List <PatientTS> pList = new ArrayList<PatientTS>();
        while(rs.next()){
                pList.add(new PatientTS(rs.getInt("medical_card_number"),rs.getString("name"),rs.getString("surname"),rs.getDate("dob"), 
                        rs.getString("address"),rs.getString("email"),rs.getString("diagnosis"),rs.getString("allergies"),rs.getString("gender")));
        }
        p.close();
        rs.close();
        return pList;
    }



    /**
     * Selects a dctor by using the doctor's userId.
     * @param userID - [Integer] User Id of the doctor.
     * @return - [Doctor] The doctor to whom the inserted user Id corresponds.
     * @throws SQLException
     * @throws NotBoundException
     */
    @Override
    public Doctor selectDoctorByUserId(Integer userID) throws Exception {
        String sql = "SELECT * FROM workers WHERE userId = ? ";
        PreparedStatement pStatement = c.prepareStatement(sql);
        pStatement.setInt(1, userID);
        ResultSet rs = pStatement.executeQuery();
        Doctor doctor = null;
        if(rs.next()){
                doctor = new Doctor(rs.getInt("doctorId"), rs.getString("dname"), rs.getString("dsurname"), rs.getString("demail"));
        }
        pStatement.close();
        rs.close();
        return doctor;
    }

    /**
     * Select a doctor by using its id
     * @param doctorId - [Integer] Id of the doctor we are looking for.
     * @return - [Doctor] The doctor to whom the inserted Id corresponds.
     * @throws SQLException
     * @throws NotBoundException
     */
    @Override
    public Doctor selectDoctor(Integer doctorId) throws SQLException, NotBoundException {
        String sql = "SELECT * FROM workers WHERE workerId = ? AND typeWorker = ?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setInt(1,doctorId);
        ResultSet rs = p.executeQuery();
        Doctor doctor = null;
        if(rs.next()){
                doctor = new Doctor(rs.getInt("doctorId"), rs.getString("dname"), rs.getString("dsurname"), rs.getString("demail"));
        }
        p.close();
        rs.close();
        return doctor;	
    }
    
    /**
     * Deletes any doctor with an id that matches the given id.
     * @param doctorId - [Integer] Id from the doctor that will be deleted.
     * @throws SQLException
     */
    @Override
    public void deleteDoctorById(Integer doctorId) throws SQLException {
        String sql = "DELETE FROM doctor WHERE doctorId = ?";
        PreparedStatement pStatement = c.prepareStatement(sql);
        pStatement.setInt(1, doctorId);
        pStatement.executeUpdate();
        pStatement.close();
    }

    /**
     * Associates a doctor with a user of the application
     * @param userId
     * @param doctorId
     * @throws Exception
     */
    @Override
    public void createLinkUserDoctor(Integer userId, Integer doctorId) throws Exception {
        String sql1 = "UPDATE doctor SET userId = ? WHERE doctorId = ? ";
        PreparedStatement pStatement = c.prepareStatement(sql1);
        pStatement.setInt(1, userId);
        pStatement.setInt(2, doctorId);
        pStatement.executeUpdate();
        pStatement.close();
    }
     
}