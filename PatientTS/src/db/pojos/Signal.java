/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.pojos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Signal {
    
    
    private Integer signalId;
    private int[] ECG_values; 
    private int[] EMG_values;
    private Date startDate;
    private String sname;
    private int samplingRate;
    private String ECGFilename;
    private String EMGFilename;
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
     * @param sr - The sampling rate of the signal recorded (int)
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
    
    /**
     *
     * @param signalId - The id of the signal (int) [Cannot be changed once it's created]
     * @param startDate - The date and hour when the signal starts recording (Date)
     * @param sname - The name of the signal (String)
     * @param samplingRate - The sampling rate of the signal recorded (int)
     * @throws Exception
     */
    public Signal(Integer signalId, Date startDate, String sname, int samplingRate) throws Exception{
        this.signalId = signalId;
        this.startDate = startDate;
        this.sname = sname;
        this.samplingRate = samplingRate;
    }
    
    /**
     *
     * @param signalId - The id of the signal (int) [Cannot be changed once it's created]
     * @param startDate - The date and hour when the signal starts recording (Date)
     * @param sname - The name of the signal (String)
     * @param samplingRate - The sampling rate of the signal recorded (int)
     * @param fileName - The name of the file were the signal is stored (String)
     * @param comment - The comments included by the doctor about the signal (String)
     * @throws Exception
     */
     public Signal(Integer signalId, Date startDate, String sname, int samplingRate, String ECGFileName,String EMGFileName, String comment) throws Exception{
        this.signalId = signalId;
        this.startDate = startDate;
        this.sname = sname;
        this.samplingRate = samplingRate;
        this.ECGFilename = ECGFileName;
        this.EMGFilename = EMGFileName;
        this.comment = comment;
    }

    public String getECGFilename() {
        return ECGFilename;
    }

    public void setECGFilename(String ECGFilename) {
        this.ECGFilename = ECGFilename;
    }

    public String getEMGFilename() {
        return EMGFilename;
    }

    public void setEMGFilename(String EMGFilename) {
        this.EMGFilename = EMGFilename;
    }
     
    /**
     *
     * @param ECG_values - Values of the ECG signal (int[])
     * @param EMG_values - Values of the EMG signal (int[])
     * @param sDate - The date and hour when the signal starts recording (Date)
     * @param sname - The name of the signal (String)
     * @param sr - The sampling rate of the signal recorded.
     
     */
    public Signal(int[] ECG_values, int[] EMG_values, Date sDate, String sname, int sr){
        this.ECG_values = ECG_values;
        this.EMG_values = EMG_values;
        this.startDate = sDate;
        this.sname = sname;
        this.samplingRate = sr;
    }
    
    
    /**
     *
     * @param ECG_values - Values of the ECG signal (int[])
     * @param EMG_values - Values of the EMG signal (int[])
     * @param sname - The name of the signal (String)
     
     */
    public Signal(int[] ECG_values, int[] EMG_values, String sname,int sr){
        this.ECG_values = ECG_values;
        this.EMG_values = EMG_values;
        this.sname = sname;
        this.samplingRate = sr;
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
    
    
    
    
   
    /**
     * Used to get the comments includfed by the doctor about the signal.
     * @return [String] The comments related to the signal.
     */
    public String getSignalComment() {
        return comment;
    }

    /**
     * Used to set the comments includfed by the doctor about the signal.
     * @param comment
     */
    public void setSignalComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
       return "Signal{" + "signalId=" + signalId + ", startDate=" + startDate + ", sname=" + sname +", ECGfilename="+ ECGFilename +", EMGFilename="+ EMGFilename+'}';
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
    
    /**
     * Used to print the values of the ECG signal recorded one by one
     * @param ECG_values
     */
    public void ImprimirECG (){
         System.out.println("ECG");
        for (int i=0; i<this.ECG_values.length; i++){
            System.out.println(this.ECG_values[i]);
        } 
    }
    
    /**
     * Used to print the values of the EMG signal recorded one by one
     * @param EMG_values
     */
    public void ImprimirEMG (){
         System.out.println("EMG");
        for (int i=0; i<this.EMG_values.length; i++){
            System.out.println(this.EMG_values[i]);
        } 
    }
    
    public String formatDate (Date startDate){
        SimpleDateFormat  formato = new SimpleDateFormat("YYYY/MM/dd");
        return formato.format(startDate);
    }
    
    public void CreateECGFilename (String patientName){
        Calendar c = Calendar.getInstance();
         String day=Integer.toString(c.get(Calendar.DATE));
         String month=Integer.toString(c.get(Calendar.MONTH));
         String year=Integer.toString(c.get(Calendar.YEAR));
         String hour = Integer.toString(c.get(Calendar.HOUR));
         String minute = Integer.toString(c.get(Calendar.MINUTE));
         String second = Integer.toString(c.get(Calendar.SECOND));
         String millisecond = Integer.toString(c.get(Calendar.MILLISECOND));
         this.ECGFilename=patientName+"ECG"+day+month+year+"_"+hour+minute+second+millisecond+".txt";     
    }
    public void CreateEMGFilename (String patientName){
        Calendar c = Calendar.getInstance();
         String day=Integer.toString(c.get(Calendar.DATE));
         String month=Integer.toString(c.get(Calendar.MONTH));
         String year=Integer.toString(c.get(Calendar.YEAR));
         String hour = Integer.toString(c.get(Calendar.HOUR));
         String minute = Integer.toString(c.get(Calendar.MINUTE));
         String second = Integer.toString(c.get(Calendar.SECOND));
         String millisecond = Integer.toString(c.get(Calendar.MILLISECOND));
         this.EMGFilename=patientName+"EMG"+day+month+year+"_"+hour+minute+second+millisecond+".txt";     
    }
    
    public void StartDate(){
        Calendar c = Calendar.getInstance();
         String day=Integer.toString(c.get(Calendar.DATE));
         String month=Integer.toString(c.get(Calendar.MONTH));
         String year=Integer.toString(c.get(Calendar.YEAR));
         String date =year+"/"+month+"/"+day; 
         this.startDate= new Date (date);
    }
    
   public void StoreECGinFile(String patientName){
       FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            CreateECGFilename(patientName);
            String ruta = "../PatientTS/"+this.ECGFilename;
            String contenido = Arrays.toString(this.ECG_values);
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(contenido);
            
        } catch (IOException ex) {
            Logger.getLogger(Signal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Signal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   } 
   
   public void StoreEMGinFile(String patientName){
       FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            CreateEMGFilename(patientName);
            String ruta = "../PatientTS/"+this.EMGFilename;
            String contenido = Arrays.toString(this.EMG_values);
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(contenido);
            
        } catch (IOException ex) {
            Logger.getLogger(Signal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Signal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   }
   

}
