/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import database.pojos.PatientTS;
import database.pojos.TypeOfSignal;

/**
 *
 * @author agarc
 */
public interface PatientTSManager {
    public void recordSignal(PatientTS p, TypeOfSignal type, String signal_file);
    public PatientTS selectPatient(Integer medcard);
}
