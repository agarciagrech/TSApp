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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void addDoctor(Doctor d) throws SQLException  {
        String sq1 = "INSERT INTO doctor (dname, dsurname, demail) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(sq1);
        preparedStatement.setString(1, d.getDoctorName());
        preparedStatement.setString(2, d.getDoctorSurname());
        preparedStatement.setString(3, d.getDoctorEmail());
        preparedStatement.executeUpdate();	
        preparedStatement.close();
    }
    
  /**
     * Selects a dctor by using the doctor's userId.
     * @param userID - [Integer] User Id of the doctor.
     * @return - [Doctor] The doctor to whom the inserted user Id corresponds.
     * @throws SQLException
    */
    @Override
    public Doctor selectDoctorByUserId(Integer userID) throws SQLException {
        String sql = "SELECT * FROM doctor WHERE userId = ? ";
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
     */
    @Override
    public Doctor selectDoctor(Integer doctorId) throws SQLException {
        String sql = "SELECT * FROM doctor WHERE doctorId = ?";
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
    
    @Override
    public List<Doctor> selectAllDoctors()throws SQLException{
        String sql = "SELECT * FROM doctor";
        PreparedStatement p = c.prepareStatement(sql);

        ResultSet rs = p.executeQuery();
        List <Doctor> dList = new ArrayList<Doctor>();
        while(rs.next()){ 
            dList.add(new Doctor(rs.getInt("doctorId"),rs.getString("dname"),rs.getString("dsurname"),rs.getString("demail")));
        }
        p.close();
        rs.close();
        return dList;
    }
    
    
    
    /**
     * Associates a doctor with a user of the application
     * @param userId
     * @param doctorId
     
     */
    @Override
    public void createLinkUserDoctor(Integer userId, Integer doctorId) {
        try {
            String sql1 = "UPDATE doctor SET userId = ? WHERE doctorId = ? ";
            PreparedStatement pStatement = c.prepareStatement(sql1);
            pStatement.setInt(1, userId);
            pStatement.setInt(2, doctorId);
            pStatement.executeUpdate();
            pStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDoctorManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getId (String dname){
        String sql1 = "SELECT * FROM doctor WHERE dname = ?";
        int id=0;
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sql1);
                    PreparedStatement p = c.prepareStatement(sql1);
                    p.setString(1,dname);
                    ResultSet rs = p.executeQuery();
                    id = rs.getInt("doctorid");
                    return id;
                } catch (SQLException ex) {
                Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
    }
}