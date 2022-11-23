/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package db.interfaces;

import java.sql.SQLException;
import pojos.users.User;

/**
 *
 * @author albic
 */
public interface UserManager {
    public User checkPassword(String medCardNumber, String password);
    public void addUser(User u);
    public void deleteUserByUserid(Integer userid);
    public boolean existingUserName(String username);
    public int getId (String username);
    
}
