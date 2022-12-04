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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
        

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
    public void addSignal(Signal s, PatientTS p ) {
        String sq1 = "INSERT INTO signal (startDate, ECGFilename, EMGFilename, id_patient) VALUES (?, ?, ?, ?)";
	PreparedStatement template;
        try {
            template = c.prepareStatement(sq1);
            template.setString(1, formatDate(s.getSignalStartDate()));
            template.setString(2, s.getECGFilename());
            template.setString(3, s.getEMGFilename());
            template.setInt(4, p.getMedCardId());
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
     * @param ECGFilename
     * @param EMGFilename
     * @param comment
     * @return boolean
     */
    @Override
    public boolean editSignal(Integer signalid, java.util.Date startDate,String ECGFilename,String EMGFilename){
        String sql;
        PreparedStatement pStatement;
        try {
            if (startDate != null) {
                    sql = "UPDATE signal SET startDate = ? WHERE signalid = ?";
                    pStatement = c.prepareStatement(sql);
                    pStatement.setString(1, formatDate(startDate));
                    pStatement.setInt(2,signalid );
                    pStatement.executeUpdate();	
            } 
           
            if (ECGFilename != null) {
                    sql = "UPDATE signal SET ECGFilename = ? WHERE signalid = ?";
                    pStatement = c.prepareStatement(sql);
                    pStatement.setString(1, ECGFilename);
                    pStatement.setInt(2, signalid);
                    pStatement.executeUpdate();	
            }
            
            if (EMGFilename != null) {
                    sql = "UPDATE signal SET EMGFilename = ? WHERE signalid = ?";
                    pStatement = c.prepareStatement(sql);
                    pStatement.setString(1, EMGFilename);
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
        Signal s= new Signal();
            String cadena1;
            String cadena2;
            String ruta1;
            String ruta2;
            List<Integer> values = new ArrayList();
            List<Integer> values2 = new ArrayList();
            Date date;
            FileReader f = null;
            FileReader f2 = null;
            BufferedReader b = null;
            BufferedReader b2 = null;
	try {
            String SQL_code = "SELECT * FROM signal WHERE signalId LIKE ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1, id);
                       
            ResultSet result_set = template.executeQuery();
            result_set.next();
            s.setSignalStartDate(date = new Date(result_set.getString("startDate")));
            s.setECGFilename(result_set.getString("ECGFilename"));
            s.setEMGFilename(result_set.getString("EMGFilename"));

            // Get the values of the ECG: 
            ruta1 = "../PatientTS/"+s.getECGFilename();
            f = new FileReader(ruta1);
            b = new BufferedReader(f);
            while((cadena1 = b.readLine())!=null) {
                String[] separatedCadena = cadena1.replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ","").split(",");
                for (int i=0; i < separatedCadena.length;i++){
                     values.add(i, Integer.parseInt(separatedCadena[i]));
                }
                    s.setECG_values(values);
                
            }
            // Get the values of the EMG: 
            ruta2 ="../PatientTS/"+s.getEMGFilename();
            f2 = new FileReader(ruta2);
            b2 = new BufferedReader(f2);
            while((cadena2 = b2.readLine())!=null) {
                String[] separatedCadena2 = cadena2.replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
                for (int i=0; i < separatedCadena2.length;i++){
                   values2.add(i, Integer.parseInt(separatedCadena2[i]));
                }
                    s.setEMG_values(values2);
                
            }
            
            template.close();
            
        } catch (SQLException selectSignalByType_error) {
                selectSignalByType_error.printStackTrace();
                return null;
        } catch (FileNotFoundException ex) {       
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally{
            try {
                
                b.close();
                b2.close();
                f.close();
                f2.close();
                return s;
            } catch (IOException ex) {
                Logger.getLogger(SQLiteSignalManager.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            
        }
        
        
    }
       
    /**
     * Select a signal by searching for its name
     * @param name - name of the signal we are looking for (String)
     * @return s [Signal]
     */
    @Override
    public Signal selectSignalByName (String name) {
         Signal s= new Signal();
        String cadena1;
        String cadena2;
        String ruta1;
        String ruta2;
        List<Integer> values = new ArrayList();
        List<Integer> values2 = new ArrayList();
       
        Date date;
        FileReader f = null;
        FileReader f2 = null;
        BufferedReader b = null;
        BufferedReader b2 = null;
        try {
            String SQL_code = "SELECT * FROM signal WHERE  ECGFilename = ? OR EMGFilename = ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setString(1, name);
            template.setString(2, name);
           
            ResultSet result_set = template.executeQuery();
            while(result_set.next()){
            s.setSignalId(result_set.getInt("signalId"));
            s.setSignalStartDate(date = new Date(result_set.getString("startDate")));
            s.setECGFilename(result_set.getString("ECGFilename"));
            s.setEMGFilename(result_set.getString("EMGFilename"));

            // Get the values of the ECG:
            if(name.contains("ECG")){
            ruta1 = "../PatientTS/"+s.getECGFilename();
            f = new FileReader(ruta1);
            b = new BufferedReader(f);
            while((cadena1 = b.readLine())!=null) {
                String[] separatedCadena = cadena1.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "").split(",");
                for (int i=0; i < separatedCadena.length;i++){
                    values.add(i, Integer.parseInt(separatedCadena[i]));
                }
                    s.setECG_values(values);
                    //System.out.println(s.getECG_values());
                
            }}else{
            // Get the values of the EMG: 
            ruta2 = "../PatientTS/"+s.getECGFilename();
            f2 = new FileReader(ruta2);
            b2 = new BufferedReader(f2);
            while((cadena2 = b2.readLine())!=null) {
                String[] separatedCadena = cadena2.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "").split(",");
                for (int i=0; i < separatedCadena.length;i++){
                    values2.add(i, Integer.parseInt(separatedCadena[i]));
                }
                    s.setEMG_values(values2);
                    //System.out.println(s.getEMG_values());
                
            }}
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
        
        Date date;
        
        try {
            String SQL_code = "SELECT * FROM signal WHERE id_patient = ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1,patient_medcard);
            ResultSet rs = template.executeQuery();
            while(rs.next()) {
                 List<Integer> values = new ArrayList();
                List<Integer> values2 = new ArrayList();
                String ruta1;
                String ruta2;
                FileReader f = null;
                FileReader f2 = null;
                BufferedReader b = null;
                BufferedReader b2 = null;
                String cadena1;
                String cadena2;
        
                Integer signalId = rs.getInt("signalId");
                Date startDate = (date = new Date(rs.getString("startDate")));
                String ECGFilename = rs.getString("ECGFilename");
                String EMGFilename = rs.getString("EMGFilename");
                Signal s=new Signal(signalId,startDate,ECGFilename,EMGFilename);
                // Get the values of the ECG: 
                ruta1 = "../PatientTS/"+s.getECGFilename();
                f = new FileReader(ruta1);
                b = new BufferedReader(f);
                while((cadena1 = b.readLine())!=null) {
                    String[] separatedCadena = cadena1.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "").split(",");
                    for (int i=0; i < separatedCadena.length;i++){
                        values.add(i, Integer.parseInt(separatedCadena[i]));
                    }
                        s.setECG_values(values);
                 }
            // Get the values of the EMG: 
                ruta2 = "../PatientTS/"+s.getEMGFilename();
                f2 = new FileReader(ruta2);
                b2 = new BufferedReader(f2);
                while((cadena2 = b2.readLine())!=null) {
                    String[] separatedCadena2 = cadena2.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "").split(",");
                    for (int i=0; i < separatedCadena2.length;i++){
                       values2.add(i, Integer.parseInt(separatedCadena2[i]));
                    }
                        s.setEMG_values(values2);
                 }
                
                signals.add(s);
               
            }
            
            template.close();
            return signals;
         }    catch (SQLException listSignalsByPatient_error) {
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
     * List all the signals Filenames of a patient
     * @param patient_medcard
     * @return
     */
    @Override
    public List<String> ListSignalsFilenamesByPatient(Integer patient_medcard) {
        List<String> signalsFilenames = new ArrayList();
        
        try {
            String SQL_code = "SELECT * FROM signal WHERE id_patient = ?";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1,patient_medcard);
            ResultSet rs = template.executeQuery();
            while(rs.next()) {
                String ECGFilename = rs.getString("ECGFilename");
                String EMGFilename = rs.getString("EMGFilename");
                signalsFilenames.add(ECGFilename);
                signalsFilenames.add(EMGFilename);
            }
            template.close();
            return signalsFilenames;
         }  catch (SQLException listSignalsByPatient_error) {
            listSignalsByPatient_error.printStackTrace(); 
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
    
    public int getId (String filename){
        String sql1 = "SELECT * FROM signal WHERE ECGFilename = ?";
        int id=0;
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sql1);
                    PreparedStatement p = c.prepareStatement(sql1);
                    p.setString(1,filename);
                    ResultSet rs = p.executeQuery();
                    id = rs.getInt("signalId");
                    return id;
                } catch (SQLException ex) {
                Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        
    }
    
    public String formatDate (java.util.Date dob){
        SimpleDateFormat  formato = new SimpleDateFormat("YYYY/MM/dd");
        return formato.format(dob);
    }
    
   
}