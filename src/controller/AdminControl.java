package controller;

import java.sql.ResultSet;
import java.sql.Statement;

import service.NumberValidation;
import utility.Accounts;
import utility.Bills;
import utility.CheckOrders;
import utility.ConnectionManager;
import utility.WorkersPerticulars;

public class AdminControl {
	static NumberValidation numberValidation = new NumberValidation();
	public void adminOptions() throws Exception{
		System.out.println("Please enter your choice");
		System.out.println("1. Check Orders\n"+"2. Workers\n"+"3. Accounts\n"+"4. Bills\n"+"5. Customers Info\n"+"6. Logout");
		
		// selecting different options for admin
		int choice = Integer.parseInt(numberValidation.enterNumber());
		
		switch(choice) {
		case 1:
			CheckOrders checkOrders = new CheckOrders(); // printing orders all complete 
			checkOrders.checkDailyOrders();
			break;
		case 2:
			// for workers section
			WorkersPerticulars workersPerticulars = new WorkersPerticulars();  
			workersPerticulars.workers();
			break;
		case 3:
			// Accounting section
			Accounts accounts = new Accounts(); 
			accounts.accountsOfFirm();
			break;
		case 4:
			// Billing Section
			Bills bills = new Bills();
			bills.billsGenerating();
			break;
		case 5:
			// Customers information section
			customerInfo();
			break;
		case 6:
			//logout section
			logout(); 
			break;
		default:
			System.out.println("Please enter valid choice...!");
			adminOptions();
		}
	}

	private void logout() throws Exception{
		System.out.println("\n=========================================\n"+"\nYou Successfully Logged out\n"+"\n=========================================\n");
		System.out.println("\nPlease select your choice\n1. Main Section\n2. Exit");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		
		switch(choice) {
		case 1:
			System.out.println("\nRedirecting to Main Page..!\n");
			Main.selectChoice();
			break;
		case 2:
			System.exit(0);
			break;
		default:
			System.out.println("\nPlease select valid option\n");
			logout();
		}		
	}

	private void customerInfo() throws Exception{
		try {
			System.out.println("Available Customers\n");
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM cus_details");
			System.out.println(Main.spacing("Date")+Main.spacing("Customer ID")+Main.spacing("Customer Name")+Main.spacing("Mobile Number")+Main.spacing("Email ID"));
			while(rs.next()) {
				System.out.println(Main.spacing(rs.getString(1))+Main.spacing(rs.getString(2))+Main.spacing(rs.getString(3))+Main.spacing(rs.getString(6))+Main.spacing(rs.getString(5)));
			}
			cm.getConnection().close();
			}	
		catch(Exception e) {
			System.out.println("Error in checking orders");
		}
		redirecting();
	}

	private void redirecting() throws Exception{
		System.out.println("\n\nPlease select your choice\n1. Admin Section\n2. Main Section\n3. Exit\n");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			adminOptions();
			break;
		case 2:
			Main.selectChoice();
			break;
		case 3:
			System.exit(0);
			break;
		default:
			System.out.println("\nPlease enter valid choice");
			redirecting();
		}		
	}
}

