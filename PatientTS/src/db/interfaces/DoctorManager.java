/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package db.interfaces;

import database.pojos.*;
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
    public PatientTS selectPatient(Integer medCard) throws SQLException, NotBoundException;
    public PatientTS selectPatientByUserId(Integer userId) throws SQLException, NotBoundException;
    public Doctor selectDoctor(Integer doctorId) throws SQLException, NotBoundException;
    public void deleteDoctorById(Integer doctorId) throws SQLException;
    
}
