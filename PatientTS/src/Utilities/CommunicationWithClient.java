/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author albic
 */
public class CommunicationWithClient {
     public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9009);
        Socket socket = serverSocket.accept();
        System.out.println("Connection client created");
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        System.out.println("Text Received:\n");
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.toLowerCase().contains("stop")) {
                System.out.println("Stopping the server");
                releaseResources(bufferedReader, socket, serverSocket);
                System.exit(0);
            }
            System.out.println(line);
    }
    }
    
    private static void releaseResources(BufferedReader bufferedReader, Socket socket, ServerSocket serverSocket) {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(CommunicationWithClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
    

