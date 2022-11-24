/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db.pojos;

import java.rmi.NotBoundException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Doctor {
    
    private Integer doctorId;
        //Unique for each doctor - cannot be repeated for another patient.
    private String dname;
    private String dsurname;
    private String demail;
    private Integer userId;
   


    public Doctor() {
    }
    
    /**
     *
     * @param id - [Integer] The id of the doctor [Cannot be changed once it's created]
     * @param name - [String] The name of the doctor
     * @param surname - [String] The surname of the doctor
     * @param email - [String] The professional email of the doctor
     * @param userId - [Integer] User Id associated to the doctor
     * @throws java.rmi.NotBoundException
     */
    public Doctor(Integer id, String name, String surname, String email, Integer userId){
        this.doctorId = id;
        this.dname = name;
        this.dsurname = surname;
        this.demail = email;
        this.userId = userId;
    }
    
    /**
     *
     * @param id - [Integer] The id of the doctor [Cannot be changed once it's created]
     * @param name - [String] The name of the doctor
     * @param surname - [String] The surname of the doctor
     * @param email - [String] The professional email of the doctor
     * @throws java.rmi.NotBoundException
     */
    public Doctor(Integer id, String name, String surname, String email){
        this.doctorId = id;
        this.dname = name;
        this.dsurname = surname;
        this.demail = email;
    }
    
    /**
     *
     * @param name - [String] The name of the doctor
     * @param surname - [String] The surname of the doctor
     * @param userId - [Integer] User Id associated to the doctor
     * @throws java.rmi.NotBoundException
     */
    public Doctor(String name, String surname, Integer userId){
        this.dname = name;
        this.dsurname = surname;
        this.userId = userId;
    }
    
    /**
     * @param name - [String] The name of the doctor
     * @param surname - [String] The surname of the doctor
     * @param email - [String] The professional email of the doctor
    
     */
    public Doctor( String name, String surname, String email){
        this.dname = name;
        this.dsurname = surname;
        this.demail = email;
        
    }
    
    /**
     *
     * @param d - [Doctor] Object doctor
     * @throws java.rmi.NotBoundException
     */
    public Doctor(Doctor d) throws NotBoundException{
        super();
        this.doctorId = d.doctorId;
        this.dname = d.dname;
        this.dsurname = d.dsurname;
        this.demail = d.demail;
    }

    /**
     * Used to get the id number of the doctor
     * @return [Integer] Doctor's Id
     */
    public Integer getDoctorId() {
        return doctorId;
    }

    /**
     * Used to set the id number of the doctor
     * @param id
     */
    public void setDoctorId(Integer id) {
        this.doctorId = id;
    }

    /**
     * Used to get the name of the doctor
     * @return [String] Doctor's name
     */
    public String getDoctorName() {
        return dname;
    }

    /**
     * Used to set the name of the doctor
     * @param name 
     */
    public void setDoctorName(String name) {
        this.dname = name;
    }

    /**
     * Used to get the surname of the doctor
     * @return [String] Doctor's surname
     */
    public String getDoctorSurname() {
        return dsurname;
    }

    /**
     * Used to set the surname of the doctor
     * @param surname
     */
    public void setDoctorSurname(String surname) {
        this.dsurname = surname;
    }

    /**
     * Used to get the email of the doctor
     * @return [String] Doctor's professional email
     */
    public String getDoctorEmail() {
        return demail;
    }

    /**
     * Used to set the email of the doctor
     * @param email
     * @throws NotBoundException
     */
    public void setDoctorEmail(String email) throws NotBoundException {
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            this.demail = email;
        } else {
            throw new NotBoundException("Not valid email") ;
        }
    }

    /**
     * Used to get the asseigned user Id of the doctor
     * @return [Integer] Assigned user id of the doctor
     */
    public Integer getDoctorUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Doctor{" + "id=" + doctorId + ", name=" + dname + ", surname=" + dsurname + ", email=" + demail + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.doctorId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Doctor other = (Doctor) obj;
        return Objects.equals(this.doctorId, other.doctorId);
    }

}
