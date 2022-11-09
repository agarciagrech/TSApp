/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos.users;

import java.io.*;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="roles")
    @TableGenerator(name="roles", table="sqite_sequence",
            pkColumnName="name", valueColumnName="seq", pkColumnValue="roles")
    private Integer id;
    private String type;
    @OneToMany(mappedBy = "role")
    private List<User> users;
    
    
    public Role() {
        super();
        this.users = new ArrayList<User>();
    }
    
    /**
     *
     * @param role - [String] Role of the user (patient/doctor).
     */
    public Role(String role){
        super();
        this.type = role;
        this.users = new ArrayList<User>();
    }

    /**
     * Used to get the ID of the role (1-patient, 2-doctor).
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Used to set the ID of the role (1-patient, 2-doctor).
     * @param id - ID of the role (Integer).
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Used to get the type of role (patient/doctor)
     * @return type
     */
    public String getRole() {
        return type;
    }

    /**
     * Used to set the type of role (patient/doctor).
     * @param role - Type of role (String)
     */
    public void setRole(String role) {
        this.type = role;
    }

    /**
     * Used to get the list of users that have a role
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Used to set the list of users that have a role.
     * @param users - list of users with a role (List<User>).
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((users == null) ? 0 : users.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
                return true;
        if (obj == null)
                return false;
        if (getClass() != obj.getClass())
                return false;
        Role other = (Role) obj;
        if (id == null) {
                if (other.id != null)
                        return false;
        } else if (!id.equals(other.id))
                return false;
        if (type == null) {
                if (other.type != null)
                        return false;
        } else if (!type.equals(other.type))
                return false;
        if (users == null) {
                if (other.users != null)
                        return false;
        } else if (!users.equals(other.users))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", role=" + type + "]";
    }
    
}
