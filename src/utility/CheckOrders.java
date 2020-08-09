package utility;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import controller.AdminControl;
import controller.Main;
import service.NumberValidation;

public class CheckOrders {
	static NumberValidation numberValidation = new NumberValidation();
	
	public void checkDailyOrders() throws Exception {
		orderInPerticularDate();
	}
	
	private void orderInPerticularDate() throws Exception {
		System.out.println("Check ordres for ----->\n1. Today\n2. Past Days Orders");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			todaysOrder();
			break;
		case 2:
			pastDaysOrder();
			break;
		default:
			System.out.println("Enter valid choice");
			orderInPerticularDate();
		}
	}

	private void pastDaysOrder() throws Exception {
		previousDays();
	}
	
	private void todaysOrder() throws Exception {
		System.out.println("Please enter your choice\n1. Display Today\'s orders\n2. Print Today\'s orders");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			displayTodaysOrders();
			break;
		case 2: 
//			printTodaysOrders();
			break;
		default:
			System.out.println("Please enter valid choice");
			todaysOrder();
		}
	}

	private void displayTodaysOrders() throws Exception {
		try {
			LocalDate presentDate = LocalDate.now();
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM orders");
			System.out.println("Date\tOrder ID\tCustomer ID\tNo of Varpulu\tKGs of Sapuri\tKGs of Dupin");
			while(rs.next()) {
				LocalDate pastDate = LocalDate.parse(rs.getString(1));
				if(pastDate.equals(presentDate)) {
					System.out.println(rs.getString(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getInt(6));
				}
			}
			cm.getConnection().close();
			}	
		catch(Exception e) {
			System.out.println("Error in checking orders");
		}
		redirecting();
	}

	private void previousDays() throws Exception {
		System.out.println("Please select your choice\n1. Orders till yesterday\n2. Perticular Day of Orders");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			ordersTillYesterday();
			break;
		case 2:
			ordersForPerticularDay();
			break;
		default:
			System.out.println("Please select valid choice");
			previousDays();
		}
	}
	
	private void ordersForPerticularDay() throws Exception {
		System.out.println("Please enter your choice\n1. Display PerticularDay\'s orders\n2. Print PerticularDay\'s orders");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			displayPerticularDayOrders();
			break;
		case 2: 
//			printPerticularDayOrders();
			break;
		default:
			System.out.println("Please enter valid choice");
			ordersForPerticularDay();
		}		
	}

	private void displayPerticularDayOrders() throws Exception {
		try {
			int i=0;
			String pastDate=null;
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			ResultSet rs1 = st.executeQuery("SELECT * FROM orders");
			System.out.println("  Date");
			while(rs1.next()) {
				pastDate = rs1.getString(1);
				break;
			}
			System.out.println("1 "+pastDate);
			String orderDate = pastDate;
			ResultSet rst = st.executeQuery("SELECT * FROM orders");
			while(rst.next()) {
				if(!(pastDate.equals(rst.getString(1)))) {
					i++;
					System.out.println(i+" "+rst.getString(1));
					pastDate = rst.getString(1);
					orderDate = orderDate.concat(","+rst.getString(1));
				}
			}
			String[] orderArr = orderDate.split(",");
			int n = Integer.parseInt(numberValidation.enterNumber());
			String date = orderArr[n-1];
			System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+"\n "+date+" Orders..!\n"+"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
			ResultSet rs = st.executeQuery("SELECT * FROM orders");
			System.out.println("Date\tOrder ID\tCustomer ID\tNo of Varpulu\tKGs of Sapuri\tKGs of Dupin");
			while(rs.next()) {
				System.out.println(rs.getString(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getInt(6));
			}
			cm.getConnection().close();
			}	
		catch(Exception e) {
			System.out.println("Error in checking orders");
		}
		redirecting();
	}
	
	private void ordersTillYesterday() throws Exception {
		System.out.println("Please enter your choice\n1. Display Orders Till Yesterday\n2. Print Orders Till Yesterday");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			displayOrdersTillYesterday();
			break;
		case 2: 
//			printOrdersTillYesterday();
			break;
		default:
			System.out.println("Please enter valid choice");
			ordersTillYesterday();
		}
	}

	private void displayOrdersTillYesterday() throws Exception {
		try {
			LocalDate todaysDate = LocalDate.now();
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM orders");
			System.out.println("Date\tOrder ID\tCustomer ID\tNo of Varpulu\tKGs of Sapuri\tKGs of Dupin");
			while(rs.next()) {
				LocalDate pastDate = LocalDate.parse(rs.getString(1));
				if(pastDate.isBefore(todaysDate)) {
					System.out.println(rs.getString(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getInt(6));
				}
			}
			cm.getConnection().close();
		}
		catch(Exception e) {
			System.out.println("Error in displaying till yesterday");
		}
		redirecting();
	}

	private void redirecting() throws Exception{
		System.out.println("\nPlease select your choice\n1. Orders Section\n2. Admin Section\n3. Main Section\n4. Exit\n");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			checkDailyOrders();
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
