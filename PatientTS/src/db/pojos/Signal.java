/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.pojos;

import java.util.*;

public class Signal {
    
    
    private Integer signalId;
    private int[] ECG_values; 
    private int[] EMG_values;
    private Date startDate;
    private String sname;
    private int samplingRate;
    private String fileName;
    private String comment;

 
    
    
    public Signal() {
        super();
    }
    
    /**
     *
     * @param signalId - The id of the signal (int) [Cannot be changed once it's created]
     * @param ECG_values - Values of the ECG signal (int[])
     * @param EMG_values - Values of the EMG signal (int[])
     * @param sDate - The date and hour when the signal starts recording (Date)
     * @param sname - The name of the signal (String)
     * @param sr - The sampling rate of the signal recorded.
     * @param fileName - The name of the file where specific signal is saved (String)
     * @throws Exception
     */
    public Signal(Integer signalId, int[] ECG_values, int[] EMG_values, Date sDate, String sname, int sr) throws Exception{
        this.signalId = signalId;
        this.ECG_values = ECG_values;
        this.EMG_values = EMG_values;
        this.startDate = sDate;
        this.sname = sname;
        this.samplingRate = sr;
    }

    public Signal(Integer signalId, Date startDate, String sname, int samplingRate) {
        this.signalId = signalId;
        this.startDate = startDate;
        this.sname = sname;
        this.samplingRate = samplingRate;
    }
     public Signal(Integer signalId, Date startDate, String sname, int samplingRate, String fileName, String comment) {
        this.signalId = signalId;
        this.startDate = startDate;
        this.sname = sname;
        this.samplingRate = samplingRate;
        this.fileName = fileName;
        this.comment = comment;
    }
    public Signal(int[] ECG_values, int[] EMG_values, Date sDate, String sname, int sr) throws Exception{
        this.ECG_values = ECG_values;
        this.EMG_values = EMG_values;
        this.startDate = sDate;
        this.sname = sname;
        this.samplingRate = sr;
    }
    
    public Signal(int[] ECG_values, int[] EMG_values, String sname) throws Exception{
        this.ECG_values = ECG_values;
        this.EMG_values = EMG_values;
        this.sname = sname;
    }

    /**
     * Used to get the id of the signal
     * @return [Integer] The signal's id
     */
    public Integer getSignalId() {
        return signalId;
    }

    /**
     * Used to set the id of the signal
     * @param signalId
     */
    public void setSignalId(Integer signalId) {
        this.signalId = signalId;
    }

    /**
     * Used to get the values of the ECG signal
     * @return [int[]] The signal's ECG values
     */
    public int[] getECG_values() {
        return ECG_values;
    }

    /**
     * Used to get the values of the EMG signal
     * @return [int[]] The signal's EMG values
     */
    public int[] getEMG_values() {
        return EMG_values;
    }

    /**
     * Used to set the values of the ECG signal
     * @param ECG_values
     */
    public void setECG_values(int[] ECG_values) {
        this.ECG_values = ECG_values;
    }

    /**
     * Used to set the values of the EMG signal
     * @param EMG_values
     */
    public void setEMG_values(int[] EMG_values) {
        this.EMG_values = EMG_values;
    }

    /**
     * Used to get the start date and hour of the signal
     * @return [Date] The signal's start date and hour
     */
    public Date getSignalStartDate() {
        return startDate;
    }

    /**
     * Used to set the start date and hour of the signal
     * @param startDate
     */
    public void setSignalStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    /**
     * Used to get the name of the signal
     * @return [Date] The signal's name
     */
    public String getSignalname() {
        return sname;
    }

    /**
     * Used to set the name of the signal
     * @param sname
     */
    public void setSignalname(String sname) {
        this.sname = sname;
    }

    /**
    * Used to get the sampling rate of the signal
    * @return [int] The signal's sampling rate
     */
    public int getSignalSamplingRate() {
        return samplingRate;
    }

    /**
     * Used to set the sampling rate of the signal
     * @param samplingRate
     */
    public void setSignalSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
    }
    
    public String getSignalfileName() {
        return fileName;
    }
    
    public void setSignalfileName(String SignalfileName) {
        this.fileName = SignalfileName;
    }
   
       public String getSignalComment() {
        return comment;
    }

    public void setSignalComment(String comment) {
        this.comment = comment;
    }

   


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.signalId);
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
        final Signal other = (Signal) obj;
        return Objects.equals(this.signalId, other.signalId);
    }
    
    public void ImprimirECG (int[] ECG_values){
         System.out.println("ECG");
        for (int i=0; i<ECG_values.length; i++){
            System.out.println(ECG_values[i]);
        } 
    }
    
    public void ImprimirEMG (int[] EMG_values){
         System.out.println("EMG");
        for (int i=0; i<EMG_values.length; i++){
            System.out.println(EMG_values[i]);
        } 
    }

    @Override
    public String toString() {
       return "Signal{" + "signalId=" + signalId + ", startDate=" + startDate + ", sname=" + sname + '}';
    }

}
