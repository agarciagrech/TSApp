/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jpa;

import java.util.*;
import pojos.users.*;

/**
 *
 * @author agarc
 */
public interface UserManager {
    public void connect();
    public void disconnect();
    public void newUser(User u);
    public void newRole(Role r);
    public Role getRole(int id);
    public List<Role> getRoles();
    public Role getRoleByName(String name);
    public User checkPassword(String username, String password);
    public void deleteUser(User u);
    public User getUser(Integer userId);
    public void updateUser(User u, byte[] password);
    public boolean existingUserName(String username);
}
