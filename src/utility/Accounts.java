package utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import controller.AdminControl;
import controller.Main;
import dao.FirmReceiptsDAO;
import dao.PayWagesDAO;
import model.FirmReceipts;
import model.PayWages;
import service.NumberValidation;

public class Accounts {
	static NumberValidation numberValidation = new NumberValidation();
	public void accountsOfFirm() throws Exception{
		System.out.println("Please enter your option\n1. Payments\n2. Receipts\n3. Exit");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			payments();
			break;
		case 2:
			receipts();
			break;
		case 3:
			System.exit(0);
			break;
		default:
			System.out.println("Please enter valid option");
			accountsOfFirm();
		}
	}
	private void receipts() throws Exception{
		System.out.println("Available workers");
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		String names = "#";
		String ids = "#";
		int i = 0;
		ResultSet rs = st.executeQuery("SELECT * FROM cus_details");
		System.out.println("  "+Main.spacing("Worker ID")+Main.spacing("Worker Name"));
		while(rs.next()) {
			i++;
			System.out.println(i+" "+Main.spacing(rs.getString(2))+Main.spacing(rs.getString(3)));
			names = names+","+rs.getString(3);
			ids = ids+","+rs.getString(2);
		}
		String[] nameArr = names.split(",");
		String[] idArr = ids.split(",");
		int n = Integer.parseInt(numberValidation.enterNumber());
		String des = "Amount recieved from "+nameArr[n];
		System.out.println("Enter the receiving amount: ");
		int amt = Integer.parseInt(numberValidation.enterNumber());
		FirmReceipts firmReceipts = new FirmReceipts(amt,des);
		FirmReceiptsDAO firmReceiptsDAO = new FirmReceiptsDAO();
		firmReceiptsDAO.firmReceipts(idArr[n], firmReceipts);
		redirecting();
	}
	private void payments() throws Exception{
		System.out.println("Please enter your option\n1. Pay Wages\n2. Firm Expences\n3. Exit");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			payWages();
			break;
		case 2:
			firmExpences();
			break;
		case 3:
			System.exit(0);
			break;
		default:
			System.out.println("Please enter valid option");
			payments();
		}
	}

	private void firmExpences() throws Exception{
		try {
			int pastBalance = 0,balance = 0;
			String des = typeOfExpences();
			System.out.println("Enter amount to pay:");
			int amt = Integer.parseInt(numberValidation.enterNumber());
			ConnectionManager cm = new ConnectionManager();
			Statement smt = cm.getConnection().createStatement();
			
			String sql1Firm = "select * from firm_account";
			ResultSet rst1Firm = smt.executeQuery(sql1Firm);
			while(rst1Firm.next()) {
				pastBalance = rst1Firm.getInt(7);
			}	
			balance = pastBalance-amt;
			
			String sqlFirm = "insert into firm_account VALUES(\'"+LocalDate.now().toString()+"\',transaction_ID.nextval,?,?,?,?,?)";
			PreparedStatement ptFirm = cm.getConnection().prepareStatement(sqlFirm);
			ptFirm.setString(1, des);
			ptFirm.setInt(2, 0);
			ptFirm.setInt(3, amt);
			ptFirm.setInt(4, 0);
			ptFirm.setInt(5, balance);
			
			ptFirm.executeUpdate();
			cm.getConnection().close();
			System.out.println("Amount successfully paid");
		}
		catch(Exception e) {
			System.out.println("Error in firm expences");
		}
		redirecting();
	}

	private String typeOfExpences() {
		System.out.println("Please enter your choice\n1. Pay for colours\n2. Pay for Rent\n3. Pay for wood\n4. Pay for Stationary");
		int n = Integer.parseInt(numberValidation.enterNumber());
		switch(n) {
		case 1:
			return "Payment for colurs";
		case 2:
			return "Payment for rent";
		case 3:
			return "Payment for wood";
		case 4:
			return "Payment for stationary";
		}
		return null;
	}
	private void payWages() throws Exception{
	try {
		System.out.println("Available workers");
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		String names = "#";
		String ids = "#";
		int i = 0;
		ResultSet rs = st.executeQuery("SELECT * FROM workers");
		System.out.println("  "+Main.spacing("Worker ID")+Main.spacing("Worker Name"));
		while(rs.next()) {
			i++;
			System.out.println(i+" "+Main.spacing(rs.getString(2))+Main.spacing(rs.getString(3)));
			names = names+","+rs.getString(3);
			ids = ids+","+rs.getString(2);
		}
		String[] nameArr = names.split(",");
		String[] idArr = ids.split(",");
		int n = Integer.parseInt(numberValidation.enterNumber());
		String des = "Amount paid to "+nameArr[n];
		System.out.println("Enter amount to pay: ");
		int amt = Integer.parseInt(numberValidation.enterNumber());
		PayWages payWages = new PayWages(LocalDate.now(),des,amt);
		PayWagesDAO payWagesDAO = new PayWagesDAO();
		payWagesDAO.payWages(idArr[n],payWages);
		cm.getConnection().close();
		
	}
	catch(Exception e) {
		System.out.println("Error in paying wages.");
	}
		redirecting();
	}
	private void redirecting() throws Exception{
		System.out.println("\nPlease select your choice\n1. Account Section\n2. Admin Section\n3. Main Section\n4. Exit\n");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			accountsOfFirm();
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
