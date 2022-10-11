/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import database.pojos.PatientTS;

/**
 *
 * @author agarc
 */
public interface PatientTSManager {
    public void recordSignal(PatientTS p, String name);
    public void createPatient(PatientTS p);
    public PatientTS selectPatient(Integer userId);
    //public PatientTS selectPatientByUserId(Integer userId);
}
