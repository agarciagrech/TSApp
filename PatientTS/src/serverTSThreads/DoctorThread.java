/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTSThreads;

import java.net.Socket;

/**
 *
 * @author agarc
 */
public class DoctorThread implements Runnable{
    
    public static Socket socket;
    
    public DoctorThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //LOGIN Y REGISTER DEL PATIENT 
    }
    
    
}
