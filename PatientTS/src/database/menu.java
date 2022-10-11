/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.rmi.NotBoundException;

import java.security.*;
import java.time.LocalDate;
import java.util.*;


import database.pojos.*;
import users.*;
import database.jdbc.*;
import database.jpa.JPAUserManager;
import org.sqlite.SQLiteException;


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
			}catch(SQLiteException ex) { //PREGUNTAR
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
        private static void register(String name, String surname, Integer idUser) throws Exception {
		//autogenerateID
		String username = ""+name.charAt(0)+"."+surname+""+Integer.parseInt(surname.substring(0, 1));
		//autogenerated password
		String[] symbols = {"0", "1", "9", "7", "K", "Q", "a", "b", "c", "U","w","3","0"};
        int length = 14;
        Random random;
            random = SecureRandom.getInstanceStrong();
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                 int indexRandom = random.nextInt ( symbols.length );
                 sb.append( symbols[indexRandom] );
            }
            String password = sb.toString();
		
		//generate the hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(username, hash);
		userman.newUser(user);
		System.out.println("The autogenerated username is:"+ username);
		System.out.println("The autogenerated password is:"+ password);
	} 
	
	private static void login() throws Exception{
		sc = new Scanner (System.in);
		System.out.println("Please enter your username and password:");
		System.out.println("Username:");
		String username = sc.next();
		System.out.println("Password:");
		String password = sc.next();
		User user = userman.checkPassword(username, password);
		if(user == null) {
			System.out.println("Wrong username or password");
			return;
                //cambiar esto
		} else{
			patientMenu(user.getUserId());
		}
	}
        
        public static void patientMenu(Integer userID) throws Exception{
		sc = new Scanner (System.in);
		
		do{

			int a = 0;

			PatientTS patient = new PatientTS(patientman.selectPatientByUserId(userID));
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
					signals.addAll(jdbc.searchSignal());
					for (Signal signal : signals) { 
						System.out.println(signal.toString());
					}
                                        System.out.println("Please, enter the name of the signal:");
					String signal_name = sc.next();
                                        signals.addAll(jdbc.searchSignaltByName(patient, signal_name));
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
        
        public static void createPatient () throws NotBoundException, Exception {

		sc = new Scanner (System.in);
		PatientTS p = new PatientTS();

		System.out.println("Please, input the patient info:");
		System.out.print("Name: "); 
		String name = sc.next();
		p.setPatientName(name);
		System.out.print("Surname: "); 
		String surname = sc.next();
		p.setPatientSurname(surname);
		System.out.print("Medical card number: "); 
		Integer medCardNumber=1; 
		Boolean validMedNumber = false;
			do { 
				try {
				medCardNumber = sc.nextInt(); 
				validMedNumber = true;
				}catch(Exception e) {
					System.out.println("Please introduce a valid medical card number which only contains numbers");
				}
				System.out.print("Error. Please introduce medical card number: "); 
			} while (patientman.selectPatient(medCardNumber) != null&&(!validMedNumber) );
			p.setMedCardId(medCardNumber);

		System.out.print("Gender: ");
		String gender = sc.next();   
		try {
			if (gender.equalsIgnoreCase("male")) {
				gender = "Male";
			} else {
				gender = "Female";
			}

			p.setPatientGender(gender);		
		} catch (NotBoundException e) {
			do{
				System.out.print("Not a valid gender. Please introduce a gender (Male or Female): ");
				gender = sc.next();
			} while (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")));

			if (gender.equalsIgnoreCase("male")) {
				gender = "Male";
			} else {
				gender = "Female";
			}

			p.setPatientGender(gender);
		}


		System.out.print("Date of birth [yyyy-mm-dd]: ");	
		String birthdate = sc.next();
		Date bdate; 
		try {
			bdate = Date.valueOf(birthdate);
			if (bdate.before(Date.valueOf(LocalDate.now())) || bdate.equals(Date.valueOf(LocalDate.now()))) {
				p.setPatientDob(bdate);
			} else {
				do {
					System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");
					birthdate = sc.next();
					bdate = Date.valueOf(birthdate);
				} while ((!bdate.before(Date.valueOf(LocalDate.now()))) || bdate.equals(Date.valueOf(LocalDate.now())));
				p.setPatientDob(bdate);
			}
		} catch (Exception e) {
			int b=0;
			do {
				try {	
					System.out.print("Please introduce a valid date format [yyyy-mm-dd]: ");
					birthdate = sc.next();
					bdate = Date.valueOf(birthdate); 
			
					if (bdate.before(Date.valueOf(LocalDate.now())) || bdate.equals(Date.valueOf(LocalDate.now()))) {
						p.setPatientDob(bdate);
					} else {
						do {
							System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");							
							birthdate = sc.next();
							bdate = Date.valueOf(birthdate);
						} while ((!bdate.before(Date.valueOf(LocalDate.now()))) || bdate.equals(Date.valueOf(LocalDate.now())));
						p.setPatientDob(bdate);
					}
					b=1;
				} catch (Exception e1) {
				}
			} while (b==0);
		}

		System.out.print("Address: ");				
		String address = sc.next();
		p.setPatientAddress(address);
                
                System.out.print("Email:: ");				
		String email = sc.next();
                p.setPatientEmail(email);
		
		System.out.println("Let's proceed with the registration, the username and password will be autogenerated by the system:");
		register(p.getPatientName(),p.getPatientSurname(), p.getMedCardId());
		System.out.println("The patient was succesfully added to the database");
	}
        
        private static void deletePatient() throws Exception {
		sc = new Scanner (System.in);
		PatientTS p = patientmanselectPatient();
		User u = userman.getUser(p.getPatientUserId());
		jdbc.deletePatientByMedicalCardId(p.getMedCardId());
		userman.deleteUser(u);
	}
        
        private static void firstlogin(){
		try{
		String username = "admin";
		String password = "admin";
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(username, hash);
		userman.newUser(user);
		System.out.println("Admin created");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
        
        public static void changePassword() {
		sc = new Scanner (System.in);
		try{
			System.out.println("Please enter your username and password:");
			System.out.println("Username:");
			String username = sc.next();
			System.out.println("Password:");
			String password = sc.next();
			User user = userman.checkPassword(username, password);
			System.out.println("Introduce the new password: ");
			String newPassword1 = sc.next();
			System.out.println("Confirm your new password: ");
			String newPassword2 = sc.next();
			if(newPassword1.equals(newPassword2)) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(newPassword1.getBytes());
				byte[] hash = md.digest();
				userman.updateUser(user, hash);
				System.out.println("Password updated");
			} else {
				System.out.println("Error. Password confirmation does not match");
			}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
	}
        
        
}
