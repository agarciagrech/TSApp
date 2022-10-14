/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import database.pojos.PatientTS;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author agarc
 */
public interface PatientTSManager {
    
    public void recordSignal(PatientTS p, String name);
    public void addPatient(PatientTS p) throws SQLException;
    public PatientTS selectPatient(Integer medCard) throws SQLException, NotBoundException;
    public PatientTS selectPatientByUserId( Integer userId) throws SQLException, NotBoundException;
    public List<PatientTS> selectAllPatients() throws SQLException, NotBoundException;
    public boolean editPatient(Integer medCardNum, String name, String surname, Date dob, String address, String email, String diagnosis, String allergies, String gender, String macAd);
    public void deletePatientByMedicalCardId(Integer medCardNumber) throws SQLException;
    public void createLinkDoctorPatient( int medCardNumber, int doctorId) throws SQLException;
    public void createLinkUserPatient( int userId, int medCardNumber) throws Exception;

}
