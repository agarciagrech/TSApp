/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.util.*;

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
    private String sex;
    private Signal ecg ;
    private Signal emg ;
    
    
    
    

    public PatientTS(String dni, String name, String surname, Date dob, String address, String email, int age, String sexe, Signal ecg, Signal emg) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.age = age;
        this.sex = sexe;
        this.emg = emg;
        this.ecg = ecg;
    }

    @Override
    public String toString() {
        return "PatientTS{" + "dni=" + dni + ", name=" + name + ", surname=" + surname + ", dob=" + dob + ", address=" + address + ", email=" + email + ", age=" + age + ", sex=" + sex + ", ecg=" + ecg + ", emg=" + emg + '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDni() {
        return dni;
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

    public String getSex() {
        return sex;
    }

    public Signal getEcg() {
        return ecg;
    }

    public Signal getEmg() {
        return emg;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEcg(Signal ecg) {
        this.ecg = ecg;
    }

    public void setEmg(Signal emg) {
        this.emg = emg;
    }


}
