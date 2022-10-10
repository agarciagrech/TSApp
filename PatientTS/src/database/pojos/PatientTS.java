/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.pojos;

import java.io.*;
import java.rmi.*;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author macbookair
 */

public class PatientTS implements Serializable {

    private static final long serialVersionUID = -1156840724257282729L;
    
    
    private Integer medical_card_number;
        //Unique for each patient - cannot be repeated for another patient.
    private String name;
    private String surname;
    private Date dob;
    private String address;
    private String email;
    private String gender;
        //Can be either Male or Female, nothing else.
    private Signal ecg ;
    private Signal emg ;
    
    /**
     *
     * @param medCardId - The medical card id of the patient (int) [Cannot be changed once it's created]
     * @param name - Name of the patient (String)
     * @param surname - Surname of the patient (String)
     * @param dob - Birthdate of the patient (SQL Date)
     * @param address - String with the home address of the patient.
     * @param email
     * @param gender Gender of the patient [Must be Male or Female] (String)
     * @param ecg
     * @param emg
     * @throws java.rmi.NotBoundException
     */
    public PatientTS(Integer medCardId, String name, String surname, Date dob, String address, String email, String gender, Signal ecg, Signal emg) throws NotBoundException{
        this.medical_card_number = medCardId;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.emg = emg;
        this.ecg = ecg;
    }

  
    
    public PatientTS(Integer medCardId, String name, String surname, Date dob, String address, String email, String gender) {
        this.medical_card_number = medCardId;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }
    
    public PatientTS() {
    }
    
    @Override
    public String toString() {
        return "PatientTS{" + "Medical card =" + medical_card_number + ", Name =" + name + ", Surname =" + surname + ", Date of birth =" + dob + ", Address=" + address + ", Email=" + email + ", Gender=" + gender + ", Ecg=" + ecg + ", Emg=" + emg + '}';
    }

//Getters+Setters
    
    
    
    /**
     *Used to get the medical card number of the patient
     * @return
     */
    public Integer getMedCardId() {
        return medical_card_number;
    }

    /**
     * Used to get the name of the patient
     * @return [String] The patient's name
     */
    public String getPatientName() {
        return name;
    }

    /**
     * Used to get the surname of the patient.
     * @return [String] The patient's surname
     */
    public String getPatientSurname() {
        return surname;
    }

    /**
     * Used to get the birthdate of the patient.
     * @return [Date] The patient birthdate
     */
    public Date getPatientDob() {
        return dob;
    }

    /**
     * Used to get the home address of the patient.
     * @return [String] The patient's address
     */
    public String getPatientAddress() {
        return address;
    }

    /**
     * Used to get the email of the patient.
     * @return [String] The patient's email
     */
    public String getPatientEmail() {
        return email;
    }

    /**
     * Used to get the gender of the patient.
     * @return [String] The patient's gender (Male / Female)
     */
    public String getPatientGender() {
        return gender;
    }

    /**
     * Used to get the ECG signal of the patient.
     * @return [Signal] The patient's ECG signal.
     */
    public Signal getPatientECG() {
        return ecg;
    }

    /**
     * Used to get the EMG signal of the patient.
     * @return [Signal] The patient's ECG signal.
     */
    public Signal getPatientEMG() {
        return emg;
    }

    /**
     * Used to set the medical card id of the patient
     * @param medCardId
     */
    public void setMedCardId(Integer medCardId) {
        this.medical_card_number = medCardId;
    }

    /**
     * Used to get the patient's name
     * @param name
     */
    public void setPatientName(String name) {
        this.name = name;
    }

    /**
     * Used to set the patient's surname.
     * @param surname
     */
    public void setPatientSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Used to set the patient's date of birth
     * @param dob
     */
    public void setPatientDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Used to set the patient address
     * @param address
     */
    public void setPatientAddress(String address) {
        this.address = address;
    }

    /**
     * Used to set the patient's email.
     * @param email
     * @throws java.rmi.NotBoundException
     */
    public void setPatientEmail(String email) throws NotBoundException {
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            this.email = email;
        } else {
            throw new NotBoundException("Not valid email") ;
        }
    }

    /**
     * Sets the gender of the patient.
     * @param gender - Must be Male or Female.
     * @throws NotBoundException if not a correct gender
     */
    public void setPatientGender(String gender) throws NotBoundException {
        if(gender.equalsIgnoreCase("Male")) {
                this.gender = gender;
        } else if(gender.equalsIgnoreCase("Female")){
                this.gender = gender;		
        } else {
                throw new NotBoundException("Not a gender.") ;
        }
    }

    /**
     * Used to set the patient's ECG signal.
     * @param ecg
     */
    public void setPatientECG(Signal ecg) {
        this.ecg = ecg;
    }

    /**
     * Used to set the patient's EMG signal.
     * @param emg
     */
    public void setPatientEMG
        (Signal emg) {
        this.emg = emg;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.medical_card_number);
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
        final PatientTS other = (PatientTS) obj;
        return Objects.equals(this.medical_card_number, other.medical_card_number);
    }


}
