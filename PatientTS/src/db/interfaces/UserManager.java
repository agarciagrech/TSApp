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
    public void checkPassword(String medCardNumber, String password);
    public void addUser(User u) throws SQLException;
    public void deleteUserByUserid(Integer userid) throws SQLException;
    
}
