/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import users.User;

/**
 *
 * @author agarc
 */
public interface UserManager {
    public void connect();
    public void disconnect();
    public void newUser(User u);
    public User checkPassword(String username, String password);
    public void deleteUser(User u);
    public User getUser(int userId);
    public void updateUser(User u, byte[] password);
    
}
