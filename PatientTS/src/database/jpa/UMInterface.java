/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database.jpa;

import users.*;

public class UMInterface {
    
    public void connect();
    public void disconnect();
    public void newUser(User u);
    public User checkPassword(String username, String password);
    public void deleteUser(User u);
    public User getUser(int userId);
    public void updateUser(User u, byte[] password);
    
}
