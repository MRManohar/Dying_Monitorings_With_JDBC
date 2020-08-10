package utility;

import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
		System.out.println("Please enter your option\n1. Payments\n2. Receipts\n3. Account Statement\n4. Exit");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			payments();
			break;
		case 2:
			receipts();
			break;
		case 3:
			accountStatement();
			break;
		case 4:
			System.exit(0);
			break;
		default:
			System.out.println("Please enter valid option");
			accountsOfFirm();
		}
	}
	private void accountStatement() throws Exception{
		System.out.println("Please enter your choice\n1. To display my account statement\n2. To print my account statement");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			toDisplayAccountStatement();
			break;
		case 2:
			toPrintAccountStatement();
			break;
		default:
			System.out.println("Please enter valid option");
			accountStatement();
		}
	}
	private void toPrintAccountStatement() throws Exception{
		try {
			Document doc = new Document();
			PdfWriter wr = PdfWriter.getInstance(doc, new FileOutputStream("Firm-Account-Statement.pdf"));
			Font boldFontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			Font boldFontSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12);
			
			float [] pointColumnWidths = {100F,180F,180F,180F,180F,130F,130F,130F};   
			PdfPTable table = new PdfPTable(8);
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
			
			doc.add(new Paragraph("\n                                                            "+"Account Statement\n"+"\n                             ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"));
			
			PdfPCell cell0 = new PdfPCell(new Paragraph("S.No"));
			PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Transaction ID"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Description"));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Reference Transaction ID"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Debit"));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Credit"));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Balance"));
		    
		    table.addCell(cell0);
		    table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
	        table.addCell(cell7);
	        
	        int n1 = 0;
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM firm_account");
			
			while(rs.next()) {
				n1++;
				String n2 = Integer.toString(n1);
					
				cell0 = new PdfPCell(new Paragraph(n2));
				cell1 = new PdfPCell(new Paragraph(rs.getString(1)));
				cell2 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(2))));
				cell3 = new PdfPCell(new Paragraph(rs.getString(3)));
				cell4 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(4))));
				cell5 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(5))));
				cell6 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(6))));
				cell7 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(7))));
				   
				table.addCell(cell0);
				table.addCell(cell1);
			    table.addCell(cell2);
			    table.addCell(cell3);
			    table.addCell(cell4);
			    table.addCell(cell5);
			    table.addCell(cell6);
			    table.addCell(cell7);
			    
			}
			doc.add(table);
	        
			doc.close();
			wr.close();
			System.out.println("\n=========================================\n");
			System.out.println("PDF generated..");
			System.out.println("\n=========================================\n");
			cm.getConnection().close();
			redirecting();
		}
		catch(Exception e) {
			System.out.println("Error in printing account statement in Admin control");
		}
	}

	private void toDisplayAccountStatement() throws Exception{
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		
		ResultSet rs = st.executeQuery("SELECT * FROM firm_account");
		int i=0;
		System.out.println(Main.spacing("S. No")+Main.spacing("Date")+Main.spacing("Transaction ID")+Main.spacing("Description")+Main.spacing("Reference Transaction ID")+Main.spacing("Debit")+Main.spacing("Credit")+Main.spacing("Balance"));
		while(rs.next()) {
			i++;
			String sno = Main.spacing(Integer.toString(i));
			String date = Main.spacing(rs.getString(1));
			String transactionID = Main.spacing(Integer.toString(rs.getInt(2)));
			String des = spacing(rs.getString(3));
			String referenceTransactionID = Main.spacing(Integer.toString(rs.getInt(4)));
			String debit = Main.spacing(Integer.toString(rs.getInt(5)));
			String credit = Main.spacing(Integer.toString(rs.getInt(6)));
			String balance = Main.spacing(Integer.toString(rs.getInt(7)));
			System.out.println(sno+date+transactionID+des+referenceTransactionID+debit+credit+balance);
		}
		cm.getConnection().close();
		redirecting();
	}
	private String spacing(String str) {
		for(int i=str.length();i<40;i++) {
			str += " ";
		}
		return str;
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
