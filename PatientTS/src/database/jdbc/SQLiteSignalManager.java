/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.pojos.Signal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author agarc
 */
public class SQLiteSignalManager {
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

*/
}
