/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.jdbc;


import db.interfaces.UserManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.users.User;

/**
 *
 * @author albic
 */
public class SQLiteUserManager implements UserManager {
    
    private Connection c;
     public SQLiteUserManager(Connection c){
        this.c = c;
    }
      public SQLiteUserManager() {
        super();
    }
     
    // TO DO: 
     public void checkPassword(String medCardNumber, String password) {
            
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
    
    public void addUser(User u){
       
                String sq1 = "INSERT INTO users ( userName, userPassword) VALUES (?, ?)";
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
    public int getId (String username){
        String sql1 = "SELECT * FROM users WHERE username = ?";
        int id=0;
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sql1);
                    PreparedStatement p = c.prepareStatement(sql1);
                    p.setString(1,username);
                    ResultSet rs = p.executeQuery();
                    id = rs.getInt("userid");
                } catch (SQLException ex) {
                Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        return id;
    }
    
    
    
     /**
     * Deletes any user with a userid that matches the given userid.
     * @param userid - [Integer] Medical card number from the patient that will be deleted.
     
     */
   
    public void deleteUserByUserid(Integer userid){
        try {
            String sql = "DELETE FROM users WHERE userid = ?";
            PreparedStatement pStatement = c.prepareStatement(sql);
            pStatement.setInt(1, userid);
            pStatement.executeUpdate();
            pStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteUserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Deletes any user with a userid that matches the given userid.
     * @param userid - [Integer] Medical card number from the patient that will be deleted.
     
     */
   
    public void deleteUserByUserName(String userName){
        try {
            String sql = "DELETE FROM users WHERE userName = ?";
            PreparedStatement pStatement = c.prepareStatement(sql);
            pStatement.setString(1, userName);
            pStatement.executeUpdate();
            pStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteUserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Checks if it exists a username in the database
     * @param username - [String] 
     
     */
   
    public boolean existingUserName(String username){
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1,username);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteUserManager.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return false;
     }
    
  /**
     * Associates a user with a role of the application
     * @param userId
     * @param roleId
     */
    
    public void createLinkUserRole(int roleId, int userId){
        try {
            String sql1 = "UPDATE users SET userRoleid = ? WHERE userid = ? ";
            PreparedStatement pStatement = c.prepareStatement(sql1);
            pStatement.setInt(1, roleId);
            pStatement.setInt(2, userId);
            pStatement.executeUpdate();
            pStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
