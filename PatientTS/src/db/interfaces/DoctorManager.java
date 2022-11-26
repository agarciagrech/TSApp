/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package db.interfaces;

import db.pojos.Doctor;
import db.pojos.PatientTS;
import java.rmi.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author gisel
 */
public interface DoctorManager {
    
    public void addDoctor(Doctor d) throws SQLException;
    public List<PatientTS> searchPatient(String surname);
    public Doctor selectDoctorByUserId(Integer userID) ;
    public Doctor selectDoctor(Integer doctorId) ;
    public void deleteDoctorById(Integer doctorId) ;
    public void createLinkUserDoctor(Integer userId, Integer doctorId);
    public int getId (String dname);
    public List<Doctor> selectAllDoctors();
    
}
