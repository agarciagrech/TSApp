/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database.pojos;

import java.util.Objects;


/**
 *
 * @author gisel
 */
public class Doctor {
    
    private Integer doctotrId;
        //Unique for each doctor - cannot be repeated for another patient.
    private String dname;
    private String dsurname;
    private String demail;
    private Integer userId;

    public Doctor() {
    }
    
    public Doctor(Integer id, String name, String surname, String email, Integer userId) {
        this.doctotrId = id;
        this.dname = name;
        this.dsurname = surname;
        this.demail = email;
        this.userId = userId;
    }

    public Doctor(Integer id, String name, String surname, String email) {
        this.doctotrId = id;
        this.dname = name;
        this.dsurname = surname;
        this.demail = email;
    }

    public Integer getDoctorId() {
        return doctotrId;
    }

    public void setDoctorId(Integer id) {
        this.doctotrId = id;
    }

    public String getDoctorName() {
        return dname;
    }

    public void setDoctorName(String name) {
        this.dname = name;
    }

    public String getDoctorSurname() {
        return dsurname;
    }

    public void setDoctorSurname(String surname) {
        this.dsurname = surname;
    }

    public String getDoctorEmail() {
        return demail;
    }

    public void setDoctorEmail(String email) {
        this.demail = email;
    }

    public Integer getDoctorUserId() {
        return userId;
    }

    public void setDoctorUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Doctor{" + "id=" + doctotrId + ", name=" + dname + ", surname=" + dsurname + ", email=" + demail + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.doctotrId);
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
        return Objects.equals(this.doctotrId, other.doctotrId);
    }

}
