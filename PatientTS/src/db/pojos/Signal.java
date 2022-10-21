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

    public Signal() {
        super();
    }
    
    /**
     *
     * @param ECG_values
     * @param EMG_values
     * @param sname
     * @throws java.lang.Exception
     */
    
      public Signal(int[] ECG_values, int[] EMG_values, String sname) throws Exception{
        this.ECG_values = ECG_values;
        this.EMG_values = EMG_values;
        this.sname = sname;
    }
      
     public Signal(Integer signalId, int[] ECG_values, int[] EMG_values, Date sDate, String sname) throws Exception{
        this.signalId = signalId;
         this.ECG_values = ECG_values;
        this.EMG_values = EMG_values;
        this.startDate = sDate;
        this.sname = sname;
    }
     
    public Signal(int[] ECG_values, int[] EMG_values, Date sDate, String sname) throws Exception{
         this.ECG_values = ECG_values;
        this.EMG_values = EMG_values;
        this.startDate = sDate;
        this.sname = sname;
    }

    public Integer getSignalId() {
        return signalId;
    }

    public void setSignalId(Integer signalId) {
        this.signalId = signalId;
    }

    public int[] getECG_values() {
        return ECG_values;
    }

    public int[] getEMG_values() {
        return EMG_values;
    }

    public void setECG_values(int[] ECG_values) {
        this.ECG_values = ECG_values;
    }

    public void setEMG_values(int[] EMG_values) {
        this.EMG_values = EMG_values;
    }

    public Date getSignalStartDate() {
        return startDate;
    }

    public void setSignalStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public String getSignalname() {
        return sname;
    }

    public void setSignalname(String sname) {
        this.sname = sname;
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

    @Override
    public String toString() {
        return "Signal{" + "signalId=" + signalId + ", startDate=" + startDate + ", sname=" + sname + '}';
    }

}
