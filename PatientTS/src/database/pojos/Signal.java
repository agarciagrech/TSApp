/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.pojos;

import java.io.*;
import java.rmi.*;
import java.util.*;

public class Signal {
    
    
    private Integer signalId;
    private byte[] svalues; //preguntar que tipo
    private Date startDate; //Date when the signal starts recording
    private String sname;
    private TypeOfSignal stype;

    public Signal() {
        super();
    }
    
    public Signal(Integer signalId, byte[] signal_values, Date sDate, String signal_name, TypeOfSignal type) throws Exception {
        this.signalId = signalId;
        this.svalues = signal_values;
        this.startDate = sDate;
        this.sname = signal_name;
        this.stype = type;
    }
    
    public Signal(byte[] signal_values, Date sDate, String signal_name, TypeOfSignal type) throws Exception {
        this.svalues = signal_values;
        this.startDate = sDate;
        this.sname = signal_name;
        this.stype = type;
    }

    public Integer getSignalId() {
        return signalId;
    }

    public void setSignalId(Integer signalId) {
        this.signalId = signalId;
    }

    public byte[] getSignalvalues() {
        return svalues;
    }

    public void setSignalvalues(byte[] svalues) {
        this.svalues = svalues;
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

    public TypeOfSignal getSignaltype() {
        return stype;
    }

    public void setSignaltype(TypeOfSignal stype) {
        this.stype = stype;
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
        return "Signal{" + "signalId=" + signalId + ", svalues=" + svalues + ", sname=" + sname + ", stype=" + stype + '}';
    }
    
    
    
}
