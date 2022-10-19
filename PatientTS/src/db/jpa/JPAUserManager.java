/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.jpa;

import pojos.users.User;
import java.security.*;
import java.util.List;
import javax.persistence.*;


public class JPAUserManager implements UserManager{
    
    private EntityManager em;
	
    @Override
    public void connect() {
        em = Persistence.createEntityManagerFactory("ER-provider").createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
        em.getTransaction().commit();
    }
	

    @Override
    public void disconnect() {
            em.close();
    }

    @Override
    public void newUser(User u) {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
    }

    @Override
    public User getUser(Integer userId) {
            Query q = em.createNativeQuery("SELECT * FROM users WHERE userId = ?", User.class);
            q.setParameter(1, userId);
            return (User) q.getSingleResult();
    }

    @Override
    public void updateUser(User u, byte[] password) {
            Query q = em.createNativeQuery("SELECT * FROM users WHERE userId = ?", User.class);
            q.setParameter(1, u.getUserId());
            User userToUpdate = (User) q.getSingleResult();
            em.getTransaction().begin();
            userToUpdate.setUsername(u.getUsername());
            userToUpdate.setPassword(password);
            em.getTransaction().commit();
    }

    @Override
    public void deleteUser(User u) {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
    }

    @Override
    public User checkPassword(String username, String password) {
            User user = null;
            try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getBytes());
                    byte[] hash = md.digest();
                    Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1", User.class);
                    q.setParameter(1, username);
                    q.setParameter(2, hash);
                    user = (User) q.getSingleResult();
            } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
            } catch(NoResultException e) {
                    user=null;
            }
            return user;
    }
     @Override
     public boolean existingUserName(String username){
         Query q = em.createNativeQuery("SELECT * FROM users WHERE email = ?", User.class);
	 q.setParameter(1, username);
	 List<User> userList= (List) q.getResultList();
	 if(userList.isEmpty()) {
		return false;
	}
	else {
		return true;
	}
     }
}

