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
    public void addSignal(Signal s, PatientTS p);
    public boolean editSignal(Integer signalid, java.util.Date startDate,String sname,int samplingRate,String ECGFilename,String EMGFilename,String comment);
    public Signal selectSignalByName (String name);
    public boolean deleteSignalById(Integer signalid);
    public Signal selectSignalById (Integer id);
    public List<Signal> listSignalsByPatient(Integer patient_medcard);
    public List<Signal> listECGSignals(Integer patient_medcard);
    public List<Signal> listEMGSignals(Integer patient_medcard);
}



