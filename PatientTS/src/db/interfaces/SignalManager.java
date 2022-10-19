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
    public List<Signal> searchSignal(String sname) throws SQLException, NotBoundException;
    public Signal selectSignal(Integer signalId) throws SQLException, NotBoundException;
    public List<Signal> selectAllSignals() throws SQLException, NotBoundException;
}
