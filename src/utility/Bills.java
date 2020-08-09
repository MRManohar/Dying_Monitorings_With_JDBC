package utility;

import java.sql.ResultSet;
import java.sql.Statement;

import controller.AdminControl;
import controller.CustomerControl;
import controller.Main;
import service.NameValidation;
import service.NumberValidation;

public class Bills {
	static NumberValidation numberValidation = new NumberValidation();
	
	public void billsGenerating() throws Exception {
		try {
			int i=0;
			String cusID = null;
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			ResultSet rs1 = st.executeQuery("SELECT * FROM cus_details");
			System.out.println("  Customer Name");
			while(rs1.next()) {
				i++;
				System.out.println(i+" "+rs1.getString(3));
			}
			System.out.println("Please enter your choice to select the customer and generate bill");
			NameValidation nameValidation = new NameValidation();
			String name = nameValidation.enterName();
			ResultSet rs = st.executeQuery("SELECT * FROM cus_details");
			while(rs.next()) {
				if(name.equals(rs.getString(3)))
					cusID = rs.getString(2);
			}
			System.out.println("Bill name = "+name);
			CustomerControl customerControl = new CustomerControl();
			customerControl.generateBill(cusID, 0);		
		}
		catch(Exception e) {
			System.out.println("Error in bill generating in admin controller");
		}
	}
	public void redirecting() throws Exception{
		System.out.println("\nPlease select your choice\n1. Bill Section\n2. Admin Section\n3. Main Section\n4. Exit\n");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			billsGenerating();
			break;
		case 2:
			AdminControl adminControl = new AdminControl();
			adminControl.adminOptions();
			break;
		case 3:
			Main.selectChoice();
			break;
		case 4:
			System.exit(0);
			break;
		default:
			System.out.println("\nPlease enter valid choice");
			redirecting();
		}		
	}
}