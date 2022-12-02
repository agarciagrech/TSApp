/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.jdbc;

import db.interfaces.UserManager;
import db.pojos.PatientTS;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import pojos.users.User;

/**
 *
 * @author albic
 */
public class SQLiteUserManager implements UserManager {

    private Connection c;

    public SQLiteUserManager(Connection c) {
        this.c = c;
    }

    public SQLiteUserManager() {
        super();
    }

    // TO CHECK: 
    @Override
    public User checkPassword(String username, String password) {
        User user = new User();
        try {
//                    MessageDigest md = MessageDigest.getInstance("MD5");
//                    md.update(password.getBytes());
//                    byte[] hash = md.digest();
            String sql = "SELECT * FROM users WHERE userName = ? AND userPassword = ? ";
            PreparedStatement preparedStatement = c.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user.setPassword(rs.getString("userPassword"));
                user.setUsername(rs.getString("userName"));
            }
            preparedStatement.close();
            rs.close();
            return user;
//            } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
        } catch (NoResultException e) {
            user = null;

        } catch (SQLException ex) {
            Logger.getLogger(SQLiteUserManager.class.getName()).log(Level.SEVERE, null, ex);
            user = null;
        }
        return user;
    }

    @Override
    public void addUser(User u) {

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

    @Override
    public int getId(String username) {
        String sql1 = "SELECT * FROM users WHERE userName = ?";
        int id = 0;
        try {
            PreparedStatement preparedStatement = c.prepareStatement(sql1);
            PreparedStatement p = c.prepareStatement(sql1);
            p.setString(1, username);
            ResultSet rs = p.executeQuery();
            id = rs.getInt("userid");
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * Deletes any user with a userid that matches the given userid.
     *
     * @param userid - [Integer] Medical card number from the patient that will
     * be deleted.
     *
     */
    @Override
    public void deleteUserByUserid(Integer userid) {
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
     *
     * @param userName- [String] username of the user to be deleted.
     *
     */
    @Override
    public void deleteUserByUserName(String userName) {
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
     *
     * @param username - [String]
     * @return      *
     */

    @Override
    public boolean existingUserName(String username) {
        try {
            String sql = "SELECT * FROM users WHERE userName = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, username);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteUserManager.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    /**
     * Associates a user with a role of the application
     *
     * @param userId
     * @param roleId
     */
    @Override
    public void createLinkUserRole(int roleId, int userId) {
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

    /**
     * Selects a patient by using the patients's user Id.
     *
     * @param userId - [Integer] User Id related to the patient
     * @return - [PatientTS] The patient to whom the inserted user Id
     * corresponds.
     *
     */
    @Override
    public User selectUserByUserId(Integer userId) {
        try {
            Date date;
            String sql = "SELECT * FROM users WHERE userid = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1, userId);
            ResultSet rs = p.executeQuery();
            User u = new User();
            if (rs.next()) {
                u.setPassword(rs.getString("userPassword"));
                u.setRole(rs.getInt("userRoleid"));
                u.setUserId(userId);
                u.setUsername(rs.getString("userName"));
            }
            p.close();
            rs.close();
            return u;
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
