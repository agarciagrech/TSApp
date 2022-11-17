/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.sql.Connection;

/**
 *
 * @author agarc
 */
public interface DBManager {
    
    public void connect();
    public void disconnect();
    public PatientTSManager getPatientManager(); 
    public DoctorManager getDoctorManager();
    public SignalManager getSignalManager();
    public Connection getConnection();
    public boolean createTables();
    
}
