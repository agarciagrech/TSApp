/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTSThreads;

import java.util.Scanner;

/**
 *
 * @author agarc
 */
public class ThreadToStopServer implements Runnable{

    @Override
    public void run() {
         ServerTSThreads.ExitServer();
         
         
    }
    
}
