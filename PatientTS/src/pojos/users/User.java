/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos.users;

import java.io.*;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name =  "users")

public class User implements Serializable {
    
    private static final long serialVersionUID = -8462818311128616934L;
	@Id
	@GeneratedValue(generator="users")
	@TableGenerator(name="users", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="users")
	private Integer userId;
	private String username;
	@Lob
	private byte[] password;
        @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;
	
        public User() {
            super();
	}
        
    /**
     *
     * @param username - username assocated with the user (String).
     * @param password - password assocated with the user (byte[]).
     * @param role - - role assocated with the user (Role).
     */
    public User(String username, byte[] password, Role role) {
		super();
		this.username = username;
		this.password = password;
                this.role = role;
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
    public byte[] getPassword() {
            return password;
    }

    /**
     * Used to set the password associated to the user.
     * @param password
     */
    public void setPassword(byte[] password) {
            this.password = password;
    }

    /**
     * Used to get the role of the user (patient/doctor).
     * @return role
     */
    public Role getRole() {
            return role;
    }

    /**
     * Used to set the role of the user (patient/doctor).
     * @param role
     */
    public void setRole(Role role) {
            this.role = role;
    }

    @Override
    public String toString() {
            String password1 = new String(this.password);
            return "User [userId=" + userId + ", username=" + username + ", password=" + password1
                            + ", role=" + role + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        User other = (User) obj;
        if (role == null) {
                if (other.role != null)
                        return false;
        } else if (!role.equals(other.role))
                return false;
        if (userId == null) {
                if (other.userId != null)
                        return false;
        } else if (!userId.equals(other.userId))
                return false;
        if (username == null) {
                if (other.username != null)
                        return false;
        } else if (!username.equals(other.username))
                return false;
        return true;
    }
    
}
