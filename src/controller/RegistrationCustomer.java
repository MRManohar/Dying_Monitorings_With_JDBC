package controller;

//import java.sql.ResultSet;
//import java.sql.Statement;
import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Random;

import dao.RegistrationDAO;
import model.Registrations;
import service.EmailValidation;
import service.MobileValidation;
import service.NameValidation;
import service.PasswordValidation;
import service.UserNameValidation;
//import utility.ConnectionManager;

public class RegistrationCustomer {
	public void customerRegister() throws Exception{
		LocalDate dateTime = LocalDate.now();
		try {
			System.out.println("--------------- Helloo..! ---------------\n"+"\n#########################################\n"+"\nWelcome to LAKSHMI NARASIMHA SWAMI DYINGS\n"+"\n#########################################\n"+"\n*****************************************\n"+"\nYou are registering as a Customer\n"+"\n*****************************************\n");
//			Enter Name using Name Validation
			NameValidation nameValidation = new NameValidation();
			String name = nameValidation.enterName();
//			Enter User Name using UserName Validation
			UserNameValidation userNameValidation = new UserNameValidation();
			String userName = userNameValidation.enterUserName();
//			Enter Mobile Number using Mobile Number Validation 
			MobileValidation mobileValidation = new MobileValidation();
			String mobileNumber = mobileValidation.enterMobileNumber();
//			Enter email ID using email validation
			EmailValidation emailValidation = new EmailValidation();
			String email = emailValidation.enterEmail();
//			Enter password and Confirm password using password validation
			PasswordValidation passwordValidation = new PasswordValidation();
			String password = passwordValidation.enterPassword(0);
//			Passing values using Registrations constructor in model package
			String cus_ID = generateCusID();
			Registrations registrations = new Registrations(dateTime,cus_ID,name, userName, email, mobileNumber, password);
			registrations.setDate(dateTime);
			registrations.setCus_ID(cus_ID);
			registrations.setName(name);
			registrations.setUserName(userName);
			registrations.setMobileNumber(mobileNumber);
			registrations.setEmail(email);
			registrations.setPassword(password);
			
//			Print registrations data
			printCustomerInfo(registrations);
			RegistrationDAO registrationDAO = new RegistrationDAO();
			registrationDAO.addUser(registrations);
			System.out.println("\n=========================================\n"+"\nRegistration Success...!\n"+"\n=========================================\n"+"\nRedirecting to Login\n");
			LoginCustomer loginCustomer = new LoginCustomer();
			loginCustomer.customerLogin();
		}
		catch(Exception e) {
			System.out.println("Error occured at RegistrationCustomer in Registration Package");
		}
	}

	private String generateCusID() throws Exception{
		String finalID = null,idStr = null;
        Random rand = new Random();
		int id = rand.nextInt(10000);
        idStr = Integer.toString(id);
		if(idStr.length() == 3)
			finalID = "LNS0"+idStr+"CUS";
		else if(idStr.length() == 2)
			finalID = "LNS00"+idStr+"CUS";
		else if(idStr.length() == 1)
			finalID = "LNS000"+idStr+"CUS";
		else if(idStr.length() == 0)
			generateCusID();
		else
			finalID = "LNS"+idStr+"CUS";
			return finalID;
	}

	public static void printCustomerInfo(Registrations registrations) {
		System.out.println("Your datails");
		System.out.println("Your Customer ID	\t:\t "+registrations.getCus_ID());
		System.out.println("Your name		\t:\t "+registrations.getName());
		System.out.println("Your userName		\t:\t "+registrations.getUserName());
		System.out.println("Your mobile number	\t:\t "+registrations.getMobileNumber());
		System.out.println("Your emial address	\t:\t "+registrations.getEmail());
		System.out.println("Check your password	\t:\t "+registrations.getPassword());
	}
}
