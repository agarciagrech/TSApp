/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.pojos.PatientTS;
import database.pojos.Signal;
import db.interfaces.SignalManager;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
/*
  public void addSignal(Signal s) throws SQLException {
        String sq1 = "INSERT INTO signal (signalId, startDate,sname,stype,signal_values,userId) VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
			PreparedStatement template = c.prepareStatement(sq1);
			template.setInt(1, s.getSignalId());
			template.setDate(2, (Date) s.getSignalStartDate());
                        template.setString(3, s.getSignalname());
                        template.setString(4,s.getSignaltype());
                        template.setBytes(5, s.getSignalvalues());
                        template.setInt(1, s.getUser);//--> no hay atributo
			template.executeUpdate();
			template.close();	
    }
  
  public boolean updateSignal(Signal s) {
        try {
            String sq2 = "UPDATE signal SET startDate = ?, sname = ?, stype = ?, signal_values = ? WHERE signalId = ?";
            PreparedStatement template = this.c.prepareStatement(sq2);
            template.setDate(1, (Date) s.getSignalStartDate());
            template.setString(2, s.getSignalname());
            template.setString(3,s.getSignaltype());
            template.setBytes(4, s.getSignalvalues());
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException updateSignal_error) {
            updateSignal_error.printStackTrace();
            return false;
        }
    }
  
  	public boolean deleteSignalById(Signal s) {
        try {
            String SQL_code = "DELETE FROM signal WHERE signalId = ?;";
            PreparedStatement template = this.c.prepareStatement(SQL_code);
            template.setInt(1, s.getSignalId());
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException deleteSignalById_error) {
            deleteSignalById_error.printStackTrace();
            return false;
        }
    }

      // esto igual hay que modificarlo:
    
      public Signal selectSignalByType (String t ) {
		try {
			String SQL_code = "SELECT * FROM signal WHERE stype LIKE ?";
			PreparedStatement template = this.c.prepareStatement(SQL_code);
			template.setString(1, t);
			Signal s= new Signal();
			ResultSet result_set = template.executeQuery();
			result_set.next();
                        s.setSignalStartDate(result_set.getDate("startDate"));
                        s.setSignalname(result_set.getString("sname"));
                        s.setSignaltype(t);
                        s.setSignalvalues(result_set.getBytes("svalues"));
			template.close();
			return s;
		} catch (SQLException selectSignalByType_error) {
			selectSignalByType_error.printStackTrace();
			return null;
	}
      
       public Signal selectSignalById (int id) {
		try {
			String SQL_code = "SELECT * FROM signal WHERE id LIKE ?";
			PreparedStatement template = this.c.prepareStatement(SQL_code);
			template.setString(1, id);
			Signal s= new Signal();
			ResultSet result_set = template.executeQuery();
			result_set.next();
                        s.setSignalStartDate(result_set.getDate("startDate"));
                        s.setSignalname(result_set.getString("sname"));
                        s.setSignaltype(result_set.getString("stype"));
                        s.setSignalvalues(result_set.getBytes("svalues"));
			template.close();
			return s;
		} catch (SQLException selectSignalByType_error) {
			selectSignalByType_error.printStackTrace();
			return null;
	}       
}

      
     // list all the signals of a patient
     public List<Signal> listSignalsByPatient(int userid) {
	    List<Signal> signals = new LinkedList<Signal>();
	    try {
	        Statement statement = this.c.createStatement();
	        String SQL_code = "SELECT * FROM signal WHERE id_patient LIKE ?";
                PreparedStatement template = this.c.prepareStatement(SQL_code);
                template.setInt(1,userid);
	        ResultSet rs = statement.executeQuery(SQL_code);
	        while(rs.next()) {
	            Date startDate = rs.getDate("sartDate");
                    String sname = rs.getString("sname");
                    String stype = rs.getString("stype");
                    bytes[] signal_salues = rs.getBytes("signal_values");
	            signals.add(new Signal(startDate,sname,stype,signal_values)); // -> mirar bien
                }
                template.close();
                return signals;
	    } catch (SQLException listSignalsByPatient_error) {
	        listSignalsByPatient_error.printStackTrace(); 
	        return null;
	    }
	
	}
     //list all the signaal depending on the type
         public List<Signal> listSignalsByType(String t) {
	    List<Signal> signals = new LinkedList<Signal>();
	    try {
	        Statement statement = this.c.createStatement();
	        String SQL_code = "SELECT * FROM signal WHERE stype LIKE ?";
	        PreparedStatement template = this.c.prepareStatement(SQL_code);
                template.setString(1,t);
                ResultSet rs = statement.executeQuery(SQL_code);
	        while(rs.next()) {
	            Date startDate = rs.getDate("sartDate");
                    String sname = rs.getString("sname");
                    String stype = rs.getString("stype");
                    Bytes[] signal_salues = rs.getBytes("signal_values");
	            signals.add(new Signal(startDate,sname,stype,signal_values)); // -> mirar bien
                }
                template.close();
                return signals;
	    } catch (SQLException listSignalsByType_error) {
	        listSignalsByType_error.printStackTrace(); 
	        return null;
	    }
	
	}
      // list all one type of signals of an specific patient
         public List<Signal> listPatientSignalsByType(int userid,String t) {
	    List<Signal> signals = new LinkedList<Signal>();
	    try {
	        Statement statement = this.c.createStatement();
	        String SQL_code = "SELECT * FROM signal WHERE user_id LIKE ? AND stype LIKE ?";
	        PreparedStatement template = this.c.prepareStatement(SQL_code);
                template.setString(1,userid);
                template.setString(1,t);
                ResultSet rs = statement.executeQuery(SQL_code);
	        while(rs.next()) {
	            Date startDate = rs.getDate("sartDate");
                    String sname = rs.getString("sname");
                    String stype = rs.getString("stype");
                    Bytes[] signal_salues = rs.getBytes("signal_values");
	            signals.add(new Signal(startDate,sname,stype,signal_values)); // -> mirar bien
                }
                template.close();
                return signals;
	    } catch (SQLException listPatientSignalsByType_error) {
	        listPatientSignalsByType_error.printStackTrace(); 
	        return null;
	    }
	
	}
*/

    @Override
    public void insertSignal(Signal s, PatientTS p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Signal> searchSignal(String sname) throws SQLException, NotBoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Signal selectSignal(Integer signalId) throws SQLException, NotBoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Signal> selectAllSignals() throws SQLException, NotBoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
