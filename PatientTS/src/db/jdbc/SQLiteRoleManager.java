/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.jdbc;


import db.interfaces.RoleManager;
import java.rmi.NotBoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.users.Role;

/**
 *
 * @author albic
 */
public class SQLiteRoleManager implements RoleManager {
    
    private Connection c;
    
    public SQLiteRoleManager(Connection c){
        this.c = c;
    }
    
      public SQLiteRoleManager() {
        super();
    }
      
    /**
     * Creates and adds a new role into the database
     * @param r - [Role] Role that is added to the database
     
     */
    public void addRole(Role r) {
       
        String sq1 = "INSERT INTO role (type) VALUES (?)";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(sq1);
            preparedStatement.setString(1, r.getRole());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    /**
     * Selects a role by using the id.
     * @param roleid - [Integer] 
     * @return - [Role] The role to whom the inserted id corresponds.
     
     */
    public Role selectRoleById(Integer roleid) {
        try {
            String sql = "SELECT * FROM role WHERE roleid = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1,roleid);
            ResultSet rs = p.executeQuery();
            Role role = null;
            if(rs.next()){
                role = new Role (rs.getInt("roleid"),rs.getString("type"));
            }
            p.close();
            rs.close();
            return role ;
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteRoleManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Select all the roles available
     * @return - [List] List of all the roles.
    
     */
    
    public List<Role> selectAllRoles() {
        try {
            String sql = "SELECT * FROM role";
            PreparedStatement p = c.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            List <Role> rList = new ArrayList<Role>();
            while(rs.next()){
                rList.add( new Role (rs.getInt("roleid"),rs.getString("type")));
            }
            p.close();
            rs.close();
            return rList;
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteRoleManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public int getId (String type){
        String sql1 = "SELECT * FROM role WHERE type = ?";
        int id=0;
                try {
                    PreparedStatement preparedStatement = c.prepareStatement(sql1);
                    PreparedStatement p = c.prepareStatement(sql1);
                    p.setString(1,type);
                    ResultSet rs = p.executeQuery();
                    id = rs.getInt("roleid");
                } catch (SQLException ex) {
                Logger.getLogger(SQLitePatientTSManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        return id;
    }
    
     public boolean existingRoleType(String type){
        try {
            String sql = "SELECT * FROM role WHERE type = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1,type);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteUserManager.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return false;
     }
}