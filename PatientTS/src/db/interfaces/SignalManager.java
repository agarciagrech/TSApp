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
    public boolean editSignal(Integer signalid, java.util.Date startDate,String ECGFilename,String EMGFilename);
    public Signal selectSignalByName (String name);
    public Signal selectSignalById (Integer id);
    public List<Signal> listSignalsByPatient(Integer patient_medcard);
    public List<String> ListSignalsFilenamesByPatient(Integer patient_medcard);
    
}



