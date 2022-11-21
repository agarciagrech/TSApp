/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package db.interfaces;

import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.List;
import pojos.users.Role;

/**
 *
 * @author albic
 */
public interface RoleManager {
    public void addRole(Role r);
     public Role selectRoleById(Integer roleid);
     public List<Role> selectAllRoles();
     public int getId (String type);
}   
