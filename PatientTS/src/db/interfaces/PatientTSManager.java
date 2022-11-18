/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.pojos.PatientTS;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author agarc
 */
public interface PatientTSManager {
    
    public void addPatient(PatientTS p);
    public PatientTS selectPatient(Integer medCard);
    public PatientTS selectPatientByUserId( Integer userId);
    public List<PatientTS> selectAllPatients();
    public boolean editPatient(Integer medCardNum, String name, String surname, Date dob, String address, String email, String diagnosis, String allergies, String gender, String macAd);
    public void deletePatientByMedicalCardId(Integer medCardNumber);
    public void createLinkDoctorPatient( int medCardNumber, int doctorId);
    public void createLinkUserPatient(int userId, int medCardNumber);
    

}

