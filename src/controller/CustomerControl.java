package controller;

import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.CusPaymentDAO;
import model.CusPaymentModel;
import service.EmailValidation;
import service.MobileValidation;
import service.NameValidation;
import service.NumberValidation;
import service.PasswordValidation;
import service.UserNameValidation;
import utility.Bills;
import utility.ConnectionManager;

public class CustomerControl {	
	static NumberValidation numberValidation = new NumberValidation();
	public void customerOptions(String cusID) throws Exception{
		
		System.out.println("Please enter your choice");
		System.out.println("1. Orders\n2. Bill\n3. Display My Orders\n4. Edit My Profile\n5. Display My Profile\n6. Logout\n7. Exit\n");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1: 
			placeOrders(cusID);
			break;
		case 2:
			generateBill(cusID,1);
			break;
		case 3:
			displayMyOrders(cusID);
			break;
		case 4:
			editProfile(cusID);
			break;
		case 5:
			displayProfile(cusID);
			break;
		case 6:
			logout();
			break;
		case 7:
			System.exit(0);
			break;
		default:
			System.out.println("Please enter correct choice");
			customerOptions(cusID);
		}
	}

	private void logout() throws Exception{
		System.out.println("\n=========================================\n"+"\nYou Successfully Logged out\n"+"\n=========================================\n");
		System.out.println("\nPlease select your choice\n1. Login\n2. Exit");
		NumberValidation num =new NumberValidation();
		int choice = Integer.parseInt(num.enterNumber());
		
		switch(choice) {
		case 1:
			System.out.println("\nRedirecting to Login Page..!\n");
			LoginCustomer loginCustomer = new LoginCustomer();
			loginCustomer.customerLogin();
			break;
		case 2:
			System.exit(0);
			break;
		default:
			System.out.println("\nPlease select valid option\n");
			logout();
		}
	}

	private void displayProfile(String cusID) throws Exception{
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		
		ResultSet rs = st.executeQuery("SELECT * FROM cus_details");
		
		while(rs.next()) {
			if(cusID.equals(rs.getString(2))) {
				System.out.println("Your Customer ID	\t:\t "+rs.getString(2));
				System.out.println("Your name		\t:\t "+rs.getString(3));
				System.out.println("Your userName		\t:\t "+rs.getString(4));
				System.out.println("Your mobile number	\t:\t "+rs.getString(6));
				System.out.println("Your emial address	\t:\t "+rs.getString(5));
				System.out.println("Check your password	\t:\t "+rs.getString(7));
				
			}
		}
		cm.getConnection().close();
		redirecting(cusID);
	}

	private void editProfile(String cusID) throws Exception{
		System.out.println("Enter no of updates");
		int n = Integer.parseInt(numberValidation.enterNumber());
		for(int i=0;i<n;i++) {
			System.out.println("\nPlease enter your choice\n1. Name\n2. User Name\n3. Email Address\n4. Mobile Number\n5. Password\n");
			int choice = Integer.parseInt(numberValidation.enterNumber());
			switch(choice) {
			case 1:
				NameValidation nameValidation = new NameValidation();
				String name = nameValidation.enterName();
				ConnectionManager cmName = new ConnectionManager();
				Statement stName = cmName.getConnection().createStatement();
				String sqlName = "UPDATE cus_details SET cus_name=\'"+name+"\' where cus_id=\'"+cusID+"\'";
				System.out.println("sqlName = "+sqlName);
				stName.executeUpdate(sqlName);
				cmName.getConnection().close();
				System.out.println("\n\n==============================\n");
				System.out.println("Successfully updated\n");
				System.out.println("\n\n==============================\n");
				break;
			case 2:
				UserNameValidation userNameValidation = new UserNameValidation ();
				String userName = userNameValidation.enterUserName();
				ConnectionManager cmUserName = new ConnectionManager();
				Statement stUserName = cmUserName.getConnection().createStatement();
				String sqlUserName = "UPDATE cus_details SET user_name=\'"+userName+"\' where cus_id=\'"+cusID+"\'";
				stUserName.executeUpdate(sqlUserName);
				cmUserName.getConnection().close();
				System.out.println("\n\n==============================\n");
				System.out.println("Successfully updated\n");
				System.out.println("\n\n==============================\n");
				break;
			case 3:
				EmailValidation emailValidation = new EmailValidation();
				String email = emailValidation.enterEmail();
				ConnectionManager cmEmail = new ConnectionManager();
				Statement stEmail = cmEmail.getConnection().createStatement();
				String sqlEmail = "UPDATE cus_details SET email=\'"+email+"\' where cus_id=\'"+cusID+"\'";
				stEmail.executeUpdate(sqlEmail);
				cmEmail.getConnection().close();
				System.out.println("\n\n==============================\n");
				System.out.println("Successfully updated\n");
				System.out.println("\n\n==============================\n");
				break;
			case 4:
				MobileValidation mobileValidation = new MobileValidation();
				String mobileNumber = mobileValidation.enterMobileNumber();
				ConnectionManager cmMobileNumber = new ConnectionManager();
				Statement stMobileNumber = cmMobileNumber.getConnection().createStatement();
				String sqlMobile = "UPDATE cus_details SET mobile_number=\'"+mobileNumber+"\' where cus_id=\'"+cusID+"\'";
				stMobileNumber.executeUpdate(sqlMobile);
				cmMobileNumber.getConnection().close();
				System.out.println("\n\n==============================\n");
				System.out.println("Successfully updated\n");
				System.out.println("\n\n==============================\n");
				break;
			case 5:
				PasswordValidation passwordValidation = new PasswordValidation();
				String password = passwordValidation.enterPassword(0);
				ConnectionManager cmPassword = new ConnectionManager();
				Statement stPassword = cmPassword.getConnection().createStatement();
				String sqlPassword = "UPDATE cus_details SET password=\'"+password+"\' where cus_id=\'"+cusID+"\'";
				stPassword.executeUpdate(sqlPassword);
				cmPassword.getConnection().close();
				System.out.println("\n\n==============================\n");
				System.out.println("Successfully updated\n");
				System.out.println("\n\n==============================\n");
				break;
			}
		}
		redirecting(cusID);
	}

	private void displayMyOrders(String cusID) throws Exception{
		System.out.println("Please enter your chocice\n1.Display orders\n2.Print orders");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			toDisplayMyOrders(cusID);
			break;
		case 2:
			toPrintMyOrders(cusID);
			break;
		default:
			System.out.println("Please enter valid choice");
			displayMyOrders(cusID);
		}
	}

	private void toDisplayMyOrders(String cusID) throws Exception{
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		
		ResultSet rs = st.executeQuery("SELECT * FROM "+cusID);
		System.out.println("My Order are...");
		System.out.println("\n\n========================================================================================================================\n");
		System.out.println(Main.spacing("Date")+Main.spacing("Order ID")+Main.spacing("No of Varpulu")+Main.spacing("KGs of Sapuri")+Main.spacing("KGs of Dupin"));
		System.out.println("\n\n========================================================================================================================\n");
		while(rs.next()) {
			System.out.println(Main.spacing(rs.getString(1))+Main.spacing(Integer.toString(rs.getInt(2)))+Main.spacing(Integer.toString(rs.getInt(3)))+Main.spacing(Integer.toString(rs.getInt(4)))+Main.spacing(Integer.toString(rs.getInt(5))));
		}
		System.out.println("\n\n========================================================================================================================\n");
		cm.getConnection().close();
		redirecting(cusID);		
	}

	private void toPrintMyOrders(String cusID) throws Exception{
		try {
			Document doc = new Document();
			PdfWriter wr = PdfWriter.getInstance(doc, new FileOutputStream(cusID+"-orders.pdf"));
			Font boldFontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			Font boldFontSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12);
			
			float [] pointColumnWidths = {150F,150f, 150F,150F,150F,150F};   
			PdfPTable table = new PdfPTable(6);
			table.setWidths(pointColumnWidths);
			
			doc.open();
			
			Image image1 = Image.getInstance("LNS_Dyings_Logo.png");
			//Fixed Positioning
			image1.setAbsolutePosition(350f,720f);
			//Scale to new height and new width of image
			image1.scaleAbsolute(200, 65);
			doc.add(image1);
			
//			doc.add(new Paragraph("RETAIL INVOICE").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
			doc.add(new Paragraph("Lakshmi Narasimhas Swami Dyings              ",boldFontTitle));
			doc.add(new Paragraph("Venkateswara Kottalu, Proddatur,Kadapa",boldFontSubTitle));
			doc.add(new Paragraph("Email :- LNSDYINGS@GMAIL.COM",boldFontSubTitle));
			doc.add(new Paragraph("Contact No:- +91 - 9876543210",boldFontSubTitle));
			doc.add(new Paragraph("   ============================================================================",boldFontSubTitle));
			doc.add(new Paragraph("\n                                                                                                                          Date :- "+LocalDate.now()));
			
			doc.add(new Paragraph("\n                                                            "+cusID+"\'s-Orders..!\n"+"\n                             ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"));
			
			PdfPCell cell0 = new PdfPCell(new Paragraph("S.No"));
			PdfPCell cell1 = new PdfPCell(new Paragraph("Date of Order"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Order ID"));
		    PdfPCell cell3 = new PdfPCell(new Paragraph("No of Varpulu"));
		    PdfPCell cell4 = new PdfPCell(new Paragraph("No of KGs Sapuri"));
		    PdfPCell cell5 = new PdfPCell(new Paragraph("No of KGs Dupin"));
		    
		    table.addCell(cell0);
		    table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        
	        int n1 = 0;
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM "+cusID);
			
			while(rs.next()) {
				n1++;
				String n2 = Integer.toString(n1);
				
				cell0 = new PdfPCell(new Paragraph(n2));
				cell1 = new PdfPCell(new Paragraph(rs.getString(1)));
			    cell2 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(2))));
			    cell3 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(3))));
			    cell4 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(4))));
			    cell5 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(5))));
			    
			    table.addCell(cell0);
			    table.addCell(cell1);
		        table.addCell(cell2);
		        table.addCell(cell3);
		        table.addCell(cell4);
		        table.addCell(cell5);
		        
			}
			 doc.add(table);
		        
			doc.close();
			wr.close();
			System.out.println("\n\n========================================================================================================================\n");
			System.out.println("PDF generated..");
			System.out.println("\n\n========================================================================================================================\n");
			cm.getConnection().close();
			redirecting(cusID);
		}
		catch(Exception e) {
			System.out.println("Error in printing my orders in customer controller");
		}
	}

	public void generateBill(String cusID,int a) throws Exception{
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		Long sum=(long) 0;
		ResultSet rs = st.executeQuery("SELECT * FROM "+cusID);
		System.out.println(Main.spacing("Date")+Main.spacing("Order ID")+Main.spacing("No of Varpulu")+Main.spacing("KGs of Sapuri")+Main.spacing("KGs of Dupin")+Main.spacing("Amount"));
		System.out.println("\n\n========================================================================================================================\n");
		while(rs.next()) {
			Long varpulu = rs.getLong(3);
			Long sapuri = rs.getLong(3);
			Long dupin = rs.getLong(3);
			
			Long amt = amount(varpulu,sapuri,dupin);
			System.out.println(Main.spacing(rs.getString(1))+Main.spacing(Integer.toString(rs.getInt(2)))+Main.spacing(Integer.toString(rs.getInt(3)))+Main.spacing(Integer.toString(rs.getInt(4)))+Main.spacing(Integer.toString(rs.getInt(5)))+Main.spacing(Long.toString(amt)));
			sum+=amt;
		}
		System.out.println("\n\n========================================================================================================================\n");
		System.out.println(Main.spacing(" ")+Main.spacing(" ")+Main.spacing(" ")+Main.spacing(" ")+Main.spacing("Total")+Main.spacing(Long.toString(sum))+"\n");
		
		cm.getConnection().close();
		boolean ch = cusPayment();
		if(ch) {
			System.out.println("Please enter amount :");
			int amt = Integer.parseInt(numberValidation.enterNumber());
			String des = "Amount paid to dying";
			CusPaymentModel cusPaymentModel = new CusPaymentModel(amt,des);
			CusPaymentDAO cusPaymentDAO = new CusPaymentDAO();
			cusPaymentDAO.cusPayment(cusID, cusPaymentModel);
		}
		else {
			if(a==1)
				redirecting(cusID);
			else {
				Bills bills = new Bills();
				bills.redirecting();
			}
		}
		redirecting(cusID);
	}

	private boolean cusPayment() throws Exception{
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Do you want to pay?\n Please enter Y (Yes) or N (No)");
		String c = in.next();
		if(c.charAt(0) == 'Y' || c.charAt(0) == 'y') 
			return true;
		else if((c.charAt(0) == 'N' || c.charAt(0) == 'n'))
			return false;
		else
			cusPayment();
		return false;
	}

	private Long amount(Long varpulu, Long sapuri, Long dupin) {
		return (varpulu*450)+(sapuri*200)+(dupin*120);
	}

	private void placeOrders(String cusID) throws Exception{
		int id = generateOrderID();
		String dateStr = LocalDate.now().toString();
		System.out.println("Enter no of varpulu");
		int varpulu = Integer.parseInt(numberValidation.enterNumber());
		System.out.println("Enter no of KGs of Sapuri");
		int sapuri = Integer.parseInt(numberValidation.enterNumber());
		System.out.println("Enter no of KGs of Dupin");
		int dupin = Integer.parseInt(numberValidation.enterNumber());
		try {
			ConnectionManager cm = new ConnectionManager();
			//Install all the details into database
			String sqlOrders = "insert into orders VALUES(?,?,?,?,?,?)";
			String sqlCusIds = "insert into "+cusID+" VALUES(?,?,?,?,?)";
			PreparedStatement stOrders = cm.getConnection().prepareStatement(sqlOrders);
			
			stOrders.setString(1, dateStr);
			stOrders.setInt(2, id);
			stOrders.setString(3, cusID);
			stOrders.setInt(4, varpulu);
			stOrders.setInt(5, sapuri);
			stOrders.setInt(6, dupin);
		
			stOrders.executeUpdate();
				
			PreparedStatement stCusIDs = cm.getConnection().prepareStatement(sqlCusIds);
			
			
			stCusIDs.setString(1, dateStr);
			stCusIDs.setInt(2, id);
			stCusIDs.setInt(3, varpulu);
			stCusIDs.setInt(4, sapuri);
			stCusIDs.setInt(5, dupin);
			
			stCusIDs.executeUpdate();
			int balance =0,pastBalance = 0;
			Statement smt = cm.getConnection().createStatement();
			String sql1 = "select * from "+cusID+"_account";
			ResultSet rSet = smt.executeQuery(sql1);
			while(rSet.next()) {
				pastBalance = rSet.getInt(6);
			}
			int amt = (varpulu*450)+(sapuri*200)+(dupin*150);
			balance = pastBalance-amt;
			String des = "Order given by order ID = "+ Integer.toString(id);
			String sql = "insert into "+cusID+"_account VALUES(?,cus_transaction_ID.nextval,?,?,?,?)";
			PreparedStatement pst = cm.getConnection().prepareStatement(sql);
			
			pst.setString(1, LocalDate.now().toString());
			pst.setString(2, des);
			pst.setInt(3, amt);
			pst.setInt(4, 0);
			pst.setInt(5, balance);
			
			pst.executeUpdate();
			cm.getConnection().close();
			System.out.println("\n\n========================================================================================================================\n");
			System.out.println("Your order successfully Placed");
			System.out.println("\n\n========================================================================================================================\n");
			
		}
		catch(Exception e) {
			System.out.println("Error in storing orders into cus_ids and Orders tables");
		}
		redirecting(cusID);
	}
	private void redirecting(String cusID) throws Exception{
		System.out.println("\n\nPlease select your choice\n1. Customer Section\n2. Main Section\n3. Exit\n");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			customerOptions( cusID);
			break;
		case 2:
			Main.selectChoice();
			break;
		case 3:
			System.exit(0);
			break;
		default:
			System.out.println("\nPlease enter valid choice");
			redirecting(cusID);
		}
	}
	private int generateOrderID() throws Exception{
        Random rand = new Random();
		int id = rand.nextInt(100000);
       	return id;
	}
}