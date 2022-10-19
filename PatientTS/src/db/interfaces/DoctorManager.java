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
    public List<PatientTS> searchPatient(String surname) throws SQLException, NotBoundException;
    public Doctor selectDoctorByUserId(Integer userID) throws Exception;
    public Doctor selectDoctor(Integer doctorId) throws SQLException, NotBoundException;
    public void deleteDoctorById(Integer doctorId) throws SQLException;
    public void createLinkUserDoctor(Integer userId, Integer doctorId) throws Exception;
	
}
