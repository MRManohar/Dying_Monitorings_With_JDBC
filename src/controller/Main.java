package controller;

import service.NumberValidation;

public class Main {

	public static void main(String[] args) throws Exception{
		System.out.println("\n*********** Hello! Namaste..! ***********\n"+"\n#########################################\n\n"+"WELCOME TO LAKSHMI NARASIMHA SWAMI DYINGS\n"+"\n#########################################\n\n");
		System.out.println("\nWe can make any color for your SILK THREADs\n\n");
		selectChoice();
	}
	public static void selectChoice() throws Exception{
		System.out.println("Please choose one option: \n1.Admin\n2.Customer\n3.Exit\n");
		
		NumberValidation numberValidation = new NumberValidation();
		int choice = Integer.parseInt(numberValidation.enterNumber());
		// Selection for Admin or Customer
		switch(choice) {
		case 1:
			System.out.println("<------- Directing to ADMIN Login ------->");
			LoginAdmin loginAdmin = new LoginAdmin();
			loginAdmin.adminLogin();
			break;
		case 2:
			customer();
			break;
		case 3:
			System.exit(0);
			break;
		default:
			System.out.println("Please Enter Correct Choice");
			selectChoice();
		}
	}
	
	public static void customer() throws Exception{
		System.out.println("Enter your Choice\n1.Register\n2.Login\n3.Exit\n");
		
		NumberValidation numberValidation = new NumberValidation();
		int option = Integer.parseInt(numberValidation.enterNumber());
		
		//Selecting register or login
		switch(option){
		case 1:
			RegistrationCustomer registrationCustomer = new RegistrationCustomer();
			registrationCustomer.customerRegister();
			break;
		case 2:
			LoginCustomer loginCustomer = new LoginCustomer();
			loginCustomer.customerLogin();
			break;
		case 3:
			System.exit(0);
			break;
		default:
			System.out.println("Please Enter Correct Choice");
			customer();
		}
	}
	public static String spacing(String str) {
		for(int i=str.length();i<25;i++) {
			str += " ";
		}
		return str;
	}
}
