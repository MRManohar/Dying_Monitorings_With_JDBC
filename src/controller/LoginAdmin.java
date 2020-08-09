package controller;

import java.util.Scanner;

public class LoginAdmin {
	
	public void adminLogin() throws Exception {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter user name");
			String userName = sc.nextLine();
			System.out.println("Enter password");
			String password = sc.nextLine();
			if(userName.equals("Admin") && password.equals("Admin@123")) {
				System.out.println("\n=========================================\n"+"\nLogged In Successfully\n"+"\n=========================================\n");
				AdminControl adminControl = new AdminControl();
				adminControl.adminOptions();
			}
			else{
				System.out.println("\nPlease enter correct Credentials\n");
				adminLogin();
			}
			sc.close();
		}
		catch(Exception e) {
			System.out.println("Error in AdminLogin");
		}
	}
}
