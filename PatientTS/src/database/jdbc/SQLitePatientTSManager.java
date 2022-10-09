/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;
import database.pojos.*;
import db.interfaces.PatientTSManager;
import java.sql.*;
import java.util.*;
import BITalino.BitalinoDemo;
import java.io.*;
import java.nio.file.*;

/**
 *
 * @author agarc
 */
public class SQLitePatientTSManager {
    
    private Connection c;
    
    public SQLitePatientTSManager(Connection c){
        this.c = c;
    }
      public SQLitePatientTSManager() {
        super();
    }
    public void recordSignal(PatientTS p, TypeOfSignal type, String signal_file) throws IOException{
       
        List<Integer> signal_list = BITalino.BitalinoDemo.main();
        
        PrintWriter pw = null; 
        try{
            pw = new PrintWriter(signal_file);
            for(int i = 0; i<signal_list.size(); i++){
                pw.print(signal_list.get(i) + "\n");
            }
        }catch(IOException e){
            
        }finally{
            if(pw != null){
            pw.close();
        }
        }
        
        String filePath_signal = signal_file;

       
        byte[] patient_signal = Files.readAllBytes(Paths.get(filePath_signal));
            
        Signal s = new Signal(patient_signal,filePath_signal,type);
        //HAY QUE HACER EL INSERT EN LA DB
        
        @Override
	public PatientTS selectPatient(Integer medCard) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM patients WHERE medical_card_number = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,medCard);
		ResultSet rs = p.executeQuery();
		PatientTS patient = null;
		if(rs.next()){
			patient = new PatientTS(rs.getString("name"), rs.getString("surname"), rs.getString("gender"), rs.getString("blood_type"), rs.getString("allergies"), rs.getString("address"), rs.getDate("birthdate"), rs.getDate("check_in"), rs.getBoolean("hospitalized"), rs.getInt("medical_card_number"));
		}
		p.close();
		rs.close();
		return patient;	
	}
    }
}
