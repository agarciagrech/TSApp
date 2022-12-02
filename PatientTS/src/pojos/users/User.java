/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos.users;

import java.io.*;
import java.util.Objects;
import javax.persistence.*;



public class User implements Serializable {
    
    private static final long serialVersionUID = -8462818311128616934L;
	private int userId;
	private String username;
        private int role;
        private String password;
        private byte[] hash;//check: tema encriptacion

    public User(String username, byte[] hash) {
        this.username = username;
        this.hash = hash;
    }

    

        public User() {
            super();
	}
        
    /**
     *
     * @param username - username assocated with the user (String).
     * @param password - password assocated with the user (byte[]).
     * @param role - - role assocated with the user (Role).
     */
    public User(String username, String password, int role) {
		super();
		this.username = username;
		this.password = password;
                this.role = role;
	}
     /**
     *
     * @param username - username assocated with the user (String).
     * @param password - password assocated with the user (byte[]).
     */
    public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
    }

    /**
     * Used to get the user Id associated to the user.
     * @return userId
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * Used to set the user Id associated to the user.
     * @param userId - user Id associated to the user (Integer).
     */
    public void setUserId(Integer userId) {
            this.userId = userId;
    }

    
    

    
    /**
     * Used to get the username associated to the user.
     * @return username
     */
    public String getUsername() {
            return username;
    }

    /**
     * Used to set the username associated to the user.
     * @param username
     */
    public void setUsername(String username) {
            this.username = username;
    }

    /**
     * Used to get the password associated to the user.
     * @return password
     */
    public String getPassword() {
            return password;
    }

    /**
     * Used to set the password associated to the user.
     * @param password
     */
    public void setPassword(String password) {
            this.password = password;
    }

    /**
     * Used to get the role of the user (patient/doctor).
     * @return role
     */
    public int getRole() {
            return role;
    }

    /**
     * Used to set the role of the user (patient/doctor).
     * @param role
     */
    public void setRole(int role) {
            this.role = role;
    }

    @Override
    public String toString() {
           
            return "User{" + "username=" + username + ", password=" + password + ", role=" + role + ", userId=" + userId + '}';
    }
}
