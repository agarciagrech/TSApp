/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import pojos.users.User;
import db.pojos.Signal;
import db.pojos.PatientTS;
import db.jdbc.SQLiteManager;
import db.jdbc.SQLiteSignalManager;
import db.jdbc.SQLitePatientTSManager;
import java.rmi.NotBoundException;

import java.security.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

import db.jpa.JPAUserManager;
import Utilities.*;


public class menu {
    
    // static Connection c ;
	private static Scanner sc = new Scanner(System.in);
	private static SQLiteManager jdbc = new SQLiteManager();
        private static SQLitePatientTSManager patientman = new SQLitePatientTSManager();
        private static SQLiteSignalManager signalman = new SQLiteSignalManager();
	private static JPAUserManager userman = new JPAUserManager();
	public static Integer option = null;
	public static String trashcan;

        public static void main(String[] args) throws Exception {

		
		try{
			jdbc.connect();
			
			try{
				jdbc.createTables();	//CREAR SIEMPRE?		
				userman.connect();
				firstlogin();
			}catch(Exception ex) { //PREGUNTAR
				if(!ex.getMessage().contains("already exists")) {
					ex.printStackTrace();
				}
			}
			
			userman.connect();
			do {
				try {
					System.out.println("Welcome to Telesomnia");
					System.out.println("1. Login");
					System.out.println("2. Change password");
					System.out.println("0. Exit");
					int choice = sc.nextInt();
					switch(choice) {
					case 1:
						login();
						break;
					case 2:
						changePassword();
						break;
					case 0: 
						jdbc.disconnect();
						userman.disconnect();
						System.exit(0);
						break;
					default:
						System.out.println("Not a valid option.");
						break;
					}	
				} catch (Exception e) {
					trashcan = sc.next();
					System.out.println("Please introduce a valid option.");
				}
			}while(true);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
        
	
        
        public static void patientMenu(Integer userID) throws Exception{
		sc = new Scanner (System.in);
		PatientTS patient = null;
		do{

			int a = 0;
                      
			patient = patientman.selectPatient(userID);
			System.out.println("Hello Mr/Ms "+patient.getPatientSurname());
			System.out.println("Choose an option [0-3]:"
					+ "\n1. Start recording \n2. Stop recording \n3. Show my recordings \n0.Exit");
			List<Signal> signals = new ArrayList<>();

			do {
				try {
					option = sc.nextInt();
					a = 1;
				} catch (Exception e) {
					trashcan = sc.next();
					System.out.println("Please input a valid option.");
				}
			} while (a==0);

			switch (option) {
				case 0:
					System.out.println("Thank you for using our system");
					jdbc.disconnect();
					userman.disconnect();
					System.exit(0);
				case 1:
					System.out.println("You can start recording your signal");
					
					break;
				case 2:
					System.out.println("You can stop recording your signal");
		
					break;
				case 3:
					System.out.println("Here you can see all your signals recorded");
					signals.addAll(signalman.selectAllSignals());
					for (Signal signal : signals) { 
						System.out.println(signal.toString());
					}
                                        System.out.println("Please, enter the name of the signal:");
					String signal_name = sc.next();
                                        signals.addAll(signalman.searchSignal(signal_name));
					for (Signal signal : signals) { 
						System.out.println(signal.toString());
					}	
					break;
				default:
					System.out.println("Not a valid option.");
					break;
			}		
		}while(true);
	}
        
}
