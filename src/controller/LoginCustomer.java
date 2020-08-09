package controller;

import dao.LoginDAO;
import model.Login;
import service.NumberValidation;
import service.PasswordValidation;
import service.UserNameValidation;

public class LoginCustomer {
	public void customerLogin() throws Exception{
		System.out.println("\nPlease select your choice\n1. Alredy Existing Customer\n2. Registration for New Customer\n3. Exit");
		NumberValidation numberValidation = new NumberValidation();
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice){
		case 1:
			login();
			break;
		case 2:
			register();
			break;
		case 3:
			System.exit(0);
			break;
		default:
			System.out.println("Please select correct option.");
			customerLogin();
		}
	}

	private void register() throws Exception {
		RegistrationCustomer registrationCustomer = new RegistrationCustomer ();
		registrationCustomer.customerRegister(); 
	}

	private void login() throws Exception{
		try {
			UserNameValidation userNameValidation = new UserNameValidation();
			String userName = userNameValidation.enterUserName();
			PasswordValidation passwordValidation = new PasswordValidation();
			String password = passwordValidation.enterPassword(1);
			
			Login login = new Login(userName,password);
			LoginDAO loginDAO = new LoginDAO();
			
			if(loginDAO.validate(login)) {
				System.out.println("\n=========================================\n"+"\nLogged In Successfully\n"+"\n=========================================\n");
				CustomerControl customerControl = new CustomerControl();
				String cusID = loginDAO.findCusID(login);
				customerControl.customerOptions(cusID);
			}
			else{
				System.out.println("\nPlease enter correct Credentials\n");
				customerLogin();
			}
			
		}
		catch(Exception e) {
			System.out.println("Error in LoginCustomer");
		}		
	}
}
