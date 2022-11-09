/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jdbc;

import db.pojos.PatientTS;
import db.pojos.Signal;
import db.interfaces.SignalManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agarc
 */
public class SQLiteSignalManager implements SignalManager{
    private Connection c;
    
     public SQLiteSignalManager(Connection c){
        this.c = c;
    }
      public SQLiteSignalManager() {
        super();
    }

    /**
     * Introduce a new signal in the database.
     * @param s - signal recorded to be introduced into the database (Signal).
     * @param p - patient whose signal is recorded (PatientTS).
     */
    @Override
    public void insertSignal(Signal s, PatientTS p ) {
        String sq1 = "INSERT INTO signal (signalId, startDate,sname,filename,comment,id_patient) VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
			PreparedStatement template;
        try {
            template = c.prepareStatement(sq1);
            template.setInt(1, s.getSignalId());
			template.setDate(2, (Date) s.getSignalStartDate());
                        template.setString(3, s.getSignalname());
                        template.setInt(4, s.getSignalSamplingRate());
                        template.setString(5, s.getSignalfileName());
                        template.setString(6,s.getSignalComment());
                        template.setInt(7, p.getMedCardId());
			template.executeUpdate();
			template.close();	
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
			
    }
  
  /**
     * Edit the information of a signal
     * @param signalid
     * @param startDate
     * @param sname
     * @param samplingRate
     * @param fileName
     * @param comment
     * @return boolean
     */
    @Override
    public boolean editSignal(Integer signalid, java.util.Date startDate,String sname,int samplingRate,String fileName,String comment){
        String sql;
        PreparedStatement pStatement;
        try {
            if (startDate != null) {
                    sql = "UPDATE signal SET startDate = ? WHERE signalid = ?";
                    pStatement = c.prepareStatement(sql);
                    pStatement.setDate(1, (Date) startDate);
                    pStatement.setInt(2,signalid );
                    pStatement.executeUpdate();	
            } 
            if (sname != null) {
                    sql = "UPDATE patient SET sname = ? WHERE signalid = ?";
                    pStatement = c.prepareStatement(sql);
                    pStatement.setString(1, sname);
                    pStatement.setInt(2, signalid);
                    pStatement.executeUpdate();
            }

            if (samplingRate != 0) {
                    sql = "UPDATE patient SET samplingRate = ? WHERE signalid = ?";
                    pStatement = c.prepareStatement(sql);
                    pStatement.setInt(1, samplingRate);
                    pStatement.setInt(2, signalid);
                    pStatement.executeUpdate();
            }

            if (fileName != null) {
                    sql = "UPDATE patient SET fileName = ? WHERE signalid = ?";
                    pStatement = c.prepareStatement(sql);
                    pStatement.setString(1, fileName);
                    pStatement.setInt(2, signalid);
                    pStatement.executeUpdate();	
            }

            if (comment != null) {
                    sql = "UPDATE patient SET fileName = ? WHERE signalid = ?";
                    pStatement = c.prepareStatement(sql);
                    pStatement.setString(1, fileName);
                    pStatement.setInt(2, signalid);
                    pStatement.executeUpdate();	
            }

               return true;
        } catch (SQLException update_signal_error) {
            update_signal_error.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes any signal with an id that matches the given signalid.
     * @param signalid - [Integer] Medical card number from the patient that will be deleted.
     * @return boolean
     */
    @Override
    public boolean deleteSignalById(Integer signalid) {
        try {
            String SQL_code = "DELETE FROM signal WHERE signalId = ?;";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1, signalid);
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException deleteSignalById_error) {
            deleteSignalById_error.printStackTrace();
            return false;
        }
    }

     // PENSAR BIEN ESTO:
    /**
     * Selects a signal searching by id.
     * @param id - id of the signal (int).
     * @return s [Signal]
     */
    @Override
    public Signal selectSignalById (Integer id) {
	try {
            String SQL_code = "SELECT * FROM signal WHERE id LIKE ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1, id);
            Signal s= new Signal();
            String cadena;
            int[] values = new int[10];
                        
            ResultSet result_set = template.executeQuery();
            result_set.next();
            s.setSignalStartDate(result_set.getDate("startDate"));
            s.setSignalname(result_set.getString("sname"));
            s.setSignalSamplingRate(result_set.getInt("samplingRate"));
            s.setSignalfileName(result_set.getString("fileName"));

            // Get the values of the ECG or EMG: 
            FileReader f = new FileReader(s.getSignalfileName());
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                String[] separatedCadena = cadena.replaceAll("\\[", "").replaceAll("]", "").split(",");
                for (int i=0; i < cadena.length();i++){
                    values[i]=Integer.parseInt(separatedCadena[i]);
                }
                if(s.getSignalfileName().contains("ECG")){
                    s.setECG_values(values);
                }else{
                    s.setEMG_values(values);
                }
            }
            template.close();
            return s;
        } catch (SQLException selectSignalByType_error) {
                selectSignalByType_error.printStackTrace();
                return null;
        } catch (FileNotFoundException ex) {       
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }       
    }
       
    /**
     * Select a signal by searching for its name
     * @param name - name of the signal we are looking for (String)
     * @return s [Signal]
     */
    @Override
    public Signal selectSignalByName (String name) {
        try {
            String SQL_code = "SELECT * FROM signal WHERE sname LIKE ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setString(1, name);
            Signal s= new Signal();
            String cadena;
            int[] values = new int[10];

            ResultSet result_set = template.executeQuery();
            result_set.next();
            s.setSignalStartDate(result_set.getDate("startDate"));
            s.setSignalname(result_set.getString("sname"));
            s.setSignalSamplingRate(result_set.getInt("samplingRate"));
            s.setSignalfileName(result_set.getString("fileName"));

            // Get the values of the ECG or EMG: 
            FileReader f = new FileReader(s.getSignalfileName());
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                String[] separatedCadena = cadena.replaceAll("\\[", "").replaceAll("]", "").split(",");
                for (int i=0; i < cadena.length();i++){
                    values[i]=Integer.parseInt(separatedCadena[i]);
                }
                if(s.getSignalfileName().contains("ECG")){
                    s.setECG_values(values);
                }else{
                    s.setEMG_values(values);
                } 
            }
            template.close();
            return s;
        } catch (SQLException selectSignalByType_error) {
            selectSignalByType_error.printStackTrace();
            return null;
	} catch (FileNotFoundException ex) {       
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }       
    }


    /**
     * List all the signals of a patient
     * @param patient_medcard
     * @return
     */
    @Override
    public List<Signal> listSignalsByPatient(Integer patient_medcard) {
        List<Signal> signals = new LinkedList();
        String cadena;
        int[] values = new int[10];
        try {
            Statement statement = this.c.createStatement();
            String SQL_code = "SELECT * FROM signal WHERE id_patient LIKE ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1,patient_medcard);
            ResultSet rs = statement.executeQuery(SQL_code);
            while(rs.next()) {
                Integer signalId = rs.getInt("signalId");
                Date startDate = rs.getDate("sartDate");
                String sname = rs.getString("sname");
                Integer samplingRate = rs.getInt("samplingRate");
                String fileName = rs.getString("fileName");
                String comment = rs.getString("comment");
                Signal s=new Signal(signalId,startDate,sname,samplingRate,fileName,comment);
                FileReader f = new FileReader(fileName);
                BufferedReader b = new BufferedReader(f);
                while((cadena = b.readLine())!=null) {
                    String[] separatedCadena = cadena.replaceAll("\\[", "").replaceAll("]", "").split(",");
                    for (int i=0; i < cadena.length();i++){
                        values[i]=Integer.parseInt(separatedCadena[i]);
                    }
                    if(fileName.contains("ECG")){
                        s.setECG_values(values);
                    }else{
                        s.setEMG_values(values);
                    }    
                } 
                signals.add(s);
            }
            template.close();
            return signals;
        } catch (SQLException listSignalsByPatient_error) {
            listSignalsByPatient_error.printStackTrace(); 
            return null;
        } catch (IOException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
	

    /**
     * List all the ECG signals of an specific patient.
     * @param patient_medcard - Medical card number of the patient (Integer)
     * @return signals [List of Signals].
     */
    @Override
    public List<Signal> listECGSignals(Integer patient_medcard) {
       List<Signal> signals = new LinkedList();
       String cadena;
       int[] values = new int[10];
       try {
            Statement statement = this.c.createStatement();
            String SQL_code = "SELECT * FROM signal WHERE id_patient LIKE ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1,patient_medcard);
            ResultSet rs = statement.executeQuery(SQL_code);
            while(rs.next()) {
                Integer signalId = rs.getInt("signalId");
                Date startDate = rs.getDate("sartDate");
                String sname = rs.getString("sname");
                Integer samplingRate = rs.getInt("samplingRate");
                String fileName = rs.getString("fileName");
                String comment = rs.getString("comment");
                Signal s=new Signal(signalId,startDate,sname,samplingRate,fileName,comment);
                FileReader f = new FileReader(fileName);
                BufferedReader b = new BufferedReader(f);
                while((cadena = b.readLine())!=null) {
                    String[] separatedCadena = cadena.replaceAll("\\[", "").replaceAll("]", "").split(",");
                    for (int i=0; i < cadena.length();i++){
                        values[i]=Integer.parseInt(separatedCadena[i]);
                      }
                    if(fileName.contains("ECG")){
                        s.setECG_values(values);
                    }    
                }
                signals.add(s);
            }
            template.close();
            return signals;
        } catch (SQLException listECGSignals_error) {
            listECGSignals_error.printStackTrace(); 
            return null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
         
    
    /**
     * List all the EMG signals of an specific patient.
     * @param patient_medcard - Medical card number of the patient (Integer)
     * @return signals [List of Signals].
     */
    @Override
    public List<Signal> listEMGSignals(Integer patient_medcard) {
        List<Signal> signals = new LinkedList();
        String cadena;
        int[] values = new int[10];
        try {
            Statement statement = this.c.createStatement();
            String SQL_code = "SELECT * FROM signal WHERE id_patient LIKE ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1,patient_medcard);
            ResultSet rs = statement.executeQuery(SQL_code);
            while(rs.next()) {
                Integer signalId = rs.getInt("signalId");
                Date startDate = rs.getDate("sartDate");
                String sname = rs.getString("sname");
                Integer samplingRate = rs.getInt("samplingRate");
                String fileName = rs.getString("fileName");
                String comment = rs.getString("comment");
                Signal s=new Signal(signalId,startDate,sname,samplingRate,fileName,comment);
                FileReader f = new FileReader(fileName);
                BufferedReader b = new BufferedReader(f);
                while((cadena = b.readLine())!=null) {
                    String[] separatedCadena = cadena.replaceAll("\\[", "").replaceAll("]", "").split(",");
                    for (int i=0; i < cadena.length();i++){
                        values[i]=Integer.parseInt(separatedCadena[i]);
                    }
                    if(fileName.contains("EMG")){
                        s.setEMG_values(values);
                    }     
                }
                signals.add(s);
            }
            template.close();
            return signals;
        } catch (SQLException listECGSignals_error) {
            listECGSignals_error.printStackTrace(); 
            return null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}