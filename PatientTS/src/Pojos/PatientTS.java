/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author macbookair
 */
public class PatientTS implements Serializable {

    private static final long serialVersionUID = -1156840724257282729L;
    private String dni;
    private String name;
    private String surname;
    private Date dob;
    private String address;
    private String email;
    private int age;
    private String sexe;
    private List<Integer> ecgs;
    
    
    
    //private Doctor doctor;

    public PatientTS(String dni, String name, String surname, Date dob, String address, String email, int age, String sexe) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.age = age;
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return "Patient{" + "dni=" + dni + ", name=" + name + ", surname=" + surname + ", dob=" + dob + ", address=" + address + ", email=" + email + ", age=" + age + ", sexe=" + sexe + '}';
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getSexe() {
        return sexe;
    }

}
