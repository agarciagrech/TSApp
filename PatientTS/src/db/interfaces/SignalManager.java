/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.pojos.Signal;
import db.pojos.PatientTS;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author agarc
 */
public interface SignalManager {
    public void insertSignal(Signal s, PatientTS p);
    public boolean editSignal(int signalid, java.util.Date startDate,String sname,int samplingRate,String fileName,String comment);
    public Signal selectSignalByName (String name);
    public boolean deleteSignalById(int signalid);
    public Signal selectSignalById (int id);
    public List<Signal> listSignalsByPatient(int patient_medcard);
    public List<Signal> listECGSignals(int patient_medcard);
    public List<Signal> listEMGSignals(int patient_medcard);
}



