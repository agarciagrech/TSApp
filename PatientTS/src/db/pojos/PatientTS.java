/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.pojos;

import java.io.*;
import java.rmi.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

public class PatientTS implements Serializable {

    private static final long serialVersionUID = -1156840724257282729L;

    private Integer medical_card_number;
    //Unique for each patient - cannot be repeated for another patient.
    private String name;
    private String surname;
    private Date dob;
    private String address;
    private String email;
    private String diagnosis;
    private String allergies;
    private String gender;
    //Can be either Male or Female, nothing else.
    private Integer userId;
    private String macAddress;
    


    public PatientTS() {
    }

    /**
     *
     * @param medCardId - The medical card id of the patient (int) [Cannot be
     * changed once it's created]
     * @param name - Name of the patient (String)
     * @param surname - Surname of the patient (String)
     * @param dob - Birthdate of the patient (SQL Date)
     * @param address - String with the home address of the patient.
     * @param email - String with the email of the patient
     * @param diagnosis - String with the diagnosis of the patient (for the
     * clinical history)
     * @param allergies - String with the allergies of the patient (for the
     * clinical history)
     * @param gender - Gender of the patient [Must be Male or Female] (String)
     * @param signal
     * @param userID
     * @throws java.rmi.NotBoundException
     */
    public PatientTS(Integer medCardId, String name, String surname, Date dob, String address, String email, String diagnosis, String allergies, String gender, Integer userID) throws NotBoundException {
        this.medical_card_number = medCardId;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.diagnosis = diagnosis;
        this.allergies = allergies;
        this.gender = gender;
        this.userId = userID;
    }

    /**
     *
     * @param medCardId - The medical card id of the patient (int) [Cannot be
     * changed once it's created]
     * @param name - Name of the patient (String)
     * @param surname - Surname of the patient (String)
     * @param dob - Birthdate of the patient (SQL Date)
     * @param address - String with the home address of the patient.
     * @param email - String with the email of the patient
     * @param diagnosis - String with the diagnosis of the patient (for the
     * clinical history)
     * @param allergies - String with the allergies of the patient (for the
     * clinical history)
     * @param gender - Gender of the patient [Must be Male or Female] (String)
     * @param userId - User ID associated to the patient (Integer)
     * @param macAddress - MAC address associated to the computer of the patient
     * (String)
     * @throws java.rmi.NotBoundException
     */
    public PatientTS(Integer medCardId, String name, String surname, Date dob, String address, String email, String diagnosis, String allergies, String gender, Integer userId, String macAddress) throws NotBoundException {
        this.medical_card_number = medCardId;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.diagnosis = diagnosis;
        this.allergies = allergies;
        this.gender = gender;
        this.userId = userId;
        this.macAddress = macAddress;
    }
    
    /**
     *
     * @param medCardId - The medical card id of the patient (int) [Cannot be
     * changed once it's created]
     * @param name - Name of the patient (String)
     * @param surname - Surname of the patient (String)
     * @param dob - Birthdate of the patient (SQL Date)
     * @param address - String with the home address of the patient.
     * @param email - String with the email of the patient
     * @param diagnosis - String with the diagnosis of the patient (for the
     * clinical history)
     * @param gender - Gender of the patient [Must be Male or Female] (String)
     * @param macAddress - MAC address associated to the computer of the patient
     * (String)
     */
    public PatientTS(Integer medCardId, String name, String surname, Date dob, String address, String email, String diagnosis, String gender, String macAddress){
        this.medical_card_number = medCardId;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.diagnosis = diagnosis;
        this.gender = gender;
        this.macAddress = macAddress;
    }
    

    

    public PatientTS(Integer medical_card_number, String name, String surname, Date dob, String address, String email, String diagnosis, String allergies, String gender, String macAddress) {
        this.medical_card_number = medical_card_number;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.diagnosis = diagnosis;
        this.allergies = allergies;
        this.gender = gender;
        this.macAddress = macAddress;
    }

    /**
     *
     * @param p - Object patient (PatientTS)
     * @throws java.rmi.NotBoundException
     */
   
    public PatientTS(PatientTS p) throws NotBoundException {
        this.setMedCardId(p.medical_card_number);
        this.setPatientAddress(p.address);
        this.setPatientAllergies(p.allergies);
        this.setPatientDiagnosis(p.diagnosis);
        this.setPatientDob(p.dob);
        this.setPatientEmail(p.email);
        this.setPatientGender(p.gender);
        this.setPatientName(p.name);
        this.setPatientSurname(p.surname);
    }

    //ESTO IGUAL SE PUEDE METER EN UTILITIES
    /**
     * Convert from Date format (dd/MM/yyyy) to a String format.
     *
     * @param dob
     * @return String containing the date.
     */
    public String formatDate(Date dob) {
        SimpleDateFormat formato = new SimpleDateFormat("YYYY/MM/dd");
        return formato.format(dob);
    }

    /**
     * Used to get the medical card number of the patient
     *
     * @return [Integer] The patient's medical card number
     */
    public Integer getMedCardId() {
        return medical_card_number;
    }

    /**
     * Used to get the MAC address.
     *
     * @return [String] The MAC address related to the patient
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Used to get the name of the patient
     *
     * @return [String] The patient's name
     */
    public String getPatientName() {
        return name;
    }

    /**
     * Used to get the surname of the patient.
     *
     * @return [String] The patient's surname
     */
    public String getPatientSurname() {
        return surname;
    }

    /**
     * Used to get the birthdate of the patient.
     *
     * @return [Date] The patient birthdate
     */
    public Date getPatientDob() {
        return dob;
    }

    /**
     * Used to get the home address of the patient.
     *
     * @return [String] The patient's address
     */
    public String getPatientAddress() {
        return address;
    }

    /**
     * Used to get the email of the patient.
     *
     * @return [String] The patient's email
     */
    public String getPatientEmail() {
        return email;
    }

    /**
     * Used to get the diagnosis of the patient.
     *
     * @return the diagnosis of the patient
     */
    public String getPatientDiagnosis() {
        return diagnosis;
    }

    /**
     * Used to get the patient allergies
     *
     * @return A string with all of the patient allergies.
     */
    public String getPatientAllergies() {
        return allergies;
    }

    /**
     * Used to get the gender of the patient.
     *
     * @return [String] The patient's gender (Male / Female)
     */
    public String getPatientGender() {
        return gender;
    }

    

    /**
     * Used to set the medical card id of the patient
     *
     * @param medCardId
     */
    public void setMedCardId(Integer medCardId) {
        this.medical_card_number = medCardId;
    }

    /**
     * Used to get the patient's name
     *
     * @param name
     */
    public void setPatientName(String name) {
        this.name = name;
    }

    /**
     * Used to set the patient's surname.
     *
     * @param surname
     */
    public void setPatientSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Used to set the patient's date of birth
     *
     * @param dob
     */
    public void setPatientDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Used to set the patient address
     *
     * @param address
     */
    public void setPatientAddress(String address) {
        this.address = address;
    }

    /**
     * Used to set the MAC address of the patient
     *
     * @param macAddress
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Used to set the patient's email.
     *
     * @param email
     * @throws java.rmi.NotBoundException
     */
    public void setPatientEmail(String email) throws NotBoundException {
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            this.email = email;
        } else {
            throw new NotBoundException("Not valid email");
        }
    }

    /**
     * Used to set the diagnosis of the patient.
     *
     * @param diagnosis - String that contains the diagnosis of the patient.
     */
    public void setPatientDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Used to set the patient allergies.
     *
     * @param allergy - String that contains all of the patient allergies.
     */
    public void setPatientAllergies(String allergy) {
        this.allergies = allergy;
    }

    /**
     * Sets the gender of the patient.
     *
     * @param gender - Must be Male or Female.
     * @throws NotBoundException if not a correct gender
     */
    public void setPatientGender(String gender) throws NotBoundException {
        if (gender.equalsIgnoreCase("Male")) {
            this.gender = gender;
        } else if (gender.equalsIgnoreCase("Female")) {
            this.gender = gender;
        } else {
            throw new NotBoundException("Not a gender.");
        }
    }

   

    /**
     * Used to get the user Id associated with the patient.
     *
     * @return [Integer] UserId associated with the patient.
     */
    public Integer getPatientUserId() {
        return userId;
    }

    /**
     * Used to set the user Id associated with the patient.
     *
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Patient{" + "medical_card_number=" + medical_card_number + ", name=" + name + ", surname=" + surname + ", dob=" + formatDate(dob) + ", address=" + address + ", email=" + email + ", diagnosis=" + diagnosis + ", allergies=" + allergies + ", gender=" + gender + ", macAddress=" + macAddress + '}';
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
