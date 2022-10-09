/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

/**
 *
 * @author jaime
 */

public class Signal {
    private Integer id;
    private byte[] signal_values; //preguntar que tipo
    //private fecha fecha 
    private String signal_name;
    private TypeOfSignal type;

    public Signal(byte[] signal_values, String signal_name, TypeOfSignal type) {
        this.signal_values = signal_values;
        this.signal_name = signal_name;
        this.type = type;
    }
    
    
    
}
