/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.users.User;

/**
 *
 * @author albic
 */
public class SQLiteUserManager {
    
    private Connection c;
     public SQLiteUserManager(Connection c){
        this.c = c;
    }
      public SQLiteUserManager() {
        super();
    }
     
    // TO DO: 
     public void checkPassword(String medCardNumber, String password) {
            User user = null;
            try {   String sql = "SELECT * FROM user WHERE username = ? AND password = ? LIMIT 1";
                    PreparedStatement pStatement = c.prepareStatement(sql);
                    pStatement.setString(1, medCardNumber);
                    pStatement.setString(2, password);
                    pStatement.executeUpdate();
                    pStatement.close();
                   
            
            } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
    
    public void addUser(User u) throws SQLException{
       
                String sq1 = "INSERT INTO user ( userName, userPassword) VALUES (?, ?)";
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sq1);
                    preparedStatement.setString(1, u.getUsername());
                    preparedStatement.setString(2, u.getPassword());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException ex) {
                Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    }
    
     /**
     * Deletes any user with a userid that matches the given userid.
     * @param userid - [Integer] Medical card number from the patient that will be deleted.
     * @throws SQLException
     */
   
    public void deleteUserByUserid(Integer userid) throws SQLException{
        String sql = "DELETE FROM users WHERE userid = ?";
        PreparedStatement pStatement = c.prepareStatement(sql);
        pStatement.setInt(1, userid);
        pStatement.executeUpdate();
        pStatement.close();
    }
    
    
}
