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
    public Doctor selectDoctorByUserId(Integer userID) throws Exception;
    public Doctor selectDoctor(Integer doctorId) throws SQLException, NotBoundException;
    public void deleteDoctorById(Integer doctorId) throws SQLException;
    //public List<Signal> searchSignal(String sname) throws SQLException, NotBoundException;
    //public Signal selectSignal(Integer signalId) throws SQLException, NotBoundException;
    
}
