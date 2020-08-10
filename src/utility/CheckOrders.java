package utility;

import java.io.FileOutputStream;
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
			displayTodaysOrders();
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

	private void displayTodaysOrders() throws Exception {
		System.out.println("Please enter your option\n1. To display today orders\n2. To print today orders");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			toDisplayTodayOrders();
			break;
		case 2:
			toPrintTodayOrders();
			break;
		default:
			System.out.println("Please enter valid option");
			displayTodaysOrders();
		}
	}

	private void toDisplayTodayOrders() throws Exception{
		try {
			LocalDate presentDate = LocalDate.now();
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM orders");
			System.out.println(Main.spacing("Date")+Main.spacing("tOrder ID")+Main.spacing("tCustomer ID")+Main.spacing("No of Varpulu")+Main.spacing("KGs of Sapuri")+Main.spacing("KGs of Dupin"));
			while(rs.next()) {
				LocalDate pastDate = LocalDate.parse(rs.getString(1));
				if(pastDate.equals(presentDate)) {
					System.out.println(Main.spacing(rs.getString(1))+Main.spacing(Integer.toString(rs.getInt(2)))+Main.spacing(rs.getString(3))+Main.spacing(Integer.toString(rs.getInt(4)))+Main.spacing(Integer.toString(rs.getInt(5)))+Main.spacing(Integer.toString(rs.getInt(6))));
				}
			}
			cm.getConnection().close();
			}	
		catch(Exception e) {
			System.out.println("Error in checking orders");
		}
		redirecting();
	}

	private void toPrintTodayOrders() throws Exception{
		try {
			Document doc = new Document();
			PdfWriter wr = PdfWriter.getInstance(doc, new FileOutputStream("Today-Orders.pdf"));
			Font boldFontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			Font boldFontSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12);
			
			float [] pointColumnWidths = {150F,150F,150F,150F,150F,150F,150F};   
			PdfPTable table = new PdfPTable(7);
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
			
			doc.add(new Paragraph("\n                                                            "+"Today orders\n"+"\n                             ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"));
			
			PdfPCell cell0 = new PdfPCell(new Paragraph("S.No"));
			PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Order ID"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Customer ID"));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Varpulu"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Sapuri"));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Dupin"));
		    
		    table.addCell(cell0);
		    table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
	        
	        int n1 = 0;
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM orders");
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
				   
				table.addCell(cell0);
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
				
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
		catch(Exception e){
			System.out.println("Error in printing todays order in check orders");
		}

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
			printPerticularDayOrders();
			break;
		default:
			System.out.println("Please enter valid choice");
			ordersForPerticularDay();
		}		
	}

	private void printPerticularDayOrders() throws Exception{
		try {
			int i=1;
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
					System.out.println((++i)+" "+rst.getString(1));
					pastDate = rst.getString(1);
					orderDate = orderDate.concat(","+rst.getString(1));
				}
			}
			String[] orderArr = orderDate.split(",");
			int n = Integer.parseInt(numberValidation.enterNumber());
			String date = orderArr[n-1];
			Document doc = new Document();
			PdfWriter wr = PdfWriter.getInstance(doc, new FileOutputStream(date+"\'s-Orders.pdf"));
			Font boldFontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			Font boldFontSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12);
			
			float [] pointColumnWidths = {150F,150F,150F,150F,150F,150F,150F};   
			PdfPTable table = new PdfPTable(7);
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
			
			doc.add(new Paragraph("\n                                                            "+date+"\'s-orders\n"+"\n                             ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"));
			
			PdfPCell cell0 = new PdfPCell(new Paragraph("S.No"));
			PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Order ID"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Customer ID"));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Varpulu"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Sapuri"));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Dupin"));
		    
		    table.addCell(cell0);
		    table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
	        
	        int n1 = 0;
			
			ResultSet rSet = st.executeQuery("SELECT * FROM orders");
			while(rSet.next()) {
				if(date.equals(rSet.getString(1))) {
					n1++;
					String n2 = Integer.toString(n1);
					cell0 = new PdfPCell(new Paragraph(n2));
					cell1 = new PdfPCell(new Paragraph(rSet.getString(1)));
					cell2 = new PdfPCell(new Paragraph(Integer.toString(rSet.getInt(2))));
					cell3 = new PdfPCell(new Paragraph(rSet.getString(3)));
					cell4 = new PdfPCell(new Paragraph(Integer.toString(rSet.getInt(4))));
					cell5 = new PdfPCell(new Paragraph(Integer.toString(rSet.getInt(5))));
					cell6 = new PdfPCell(new Paragraph(Integer.toString(rSet.getInt(6))));
					   
					table.addCell(cell0);
					table.addCell(cell1);
					table.addCell(cell2);
					table.addCell(cell3);
					table.addCell(cell4);
					table.addCell(cell5);
					table.addCell(cell6);
					
				}
			}
		    doc.add(table);
		        
			doc.close();
			wr.close();
			System.out.println("\n=========================================\n");
			System.out.println("PDF generated..");
			System.out.println("\n=========================================\n");
			cm.getConnection().close();

			
		}
		catch(Exception e) {
			System.out.println("Error in printing perticular day orders");
		}
		redirecting();
	}

	private void displayPerticularDayOrders() throws Exception {
		try {
			int i=1;
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
					System.out.println((++i)+" "+rst.getString(1));
					pastDate = rst.getString(1);
					orderDate = orderDate.concat(","+rst.getString(1));
				}
			}
			String[] orderArr = orderDate.split(",");
			int n = Integer.parseInt(numberValidation.enterNumber());
			String date = orderArr[n-1];
			System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+"\n "+date+" Orders..!\n"+"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
			ResultSet rs = st.executeQuery("SELECT * FROM orders");
			System.out.println(Main.spacing("Date")+Main.spacing("tOrder ID")+Main.spacing("tCustomer ID")+Main.spacing("No of Varpulu")+Main.spacing("KGs of Sapuri")+Main.spacing("KGs of Dupin"));
			while(rs.next()) {
				if(date.equals(rs.getString(1)))
					System.out.println(Main.spacing(rs.getString(1))+Main.spacing(Integer.toString(rs.getInt(2)))+Main.spacing(rs.getString(3))+Main.spacing(Integer.toString(rs.getInt(4)))+Main.spacing(Integer.toString(rs.getInt(5)))+Main.spacing(Integer.toString(rs.getInt(6))));
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
			printOrdersTillYesterday();
			break;
		default:
			System.out.println("Please enter valid choice");
			ordersTillYesterday();
		}
	}

	private void printOrdersTillYesterday() throws Exception {
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		
		Document doc = new Document();
		PdfWriter wr = PdfWriter.getInstance(doc, new FileOutputStream("Orders-Till-Yesterday.pdf"));
		Font boldFontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		Font boldFontSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12);
		
		float [] pointColumnWidths = {150F,150F,150F,150F,150F,150F,150F};   
		PdfPTable table = new PdfPTable(7);
		table.setWidths(pointColumnWidths);
		
		doc.open();
		
		Image image1 = Image.getInstance("LNS_Dyings_Logo.png");
		//Fixed Positioning
		image1.setAbsolutePosition(350f,720f);
		//Scale to new height and new width of image
		image1.scaleAbsolute(200, 65);
		doc.add(image1);
		
//		doc.add(new Paragraph("RETAIL INVOICE").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
		doc.add(new Paragraph("Lakshmi Narasimhas Swami Dyings              ",boldFontTitle));
		doc.add(new Paragraph("Venkateswara Kottalu, Proddatur,Kadapa",boldFontSubTitle));
		doc.add(new Paragraph("Email :- LNSDYINGS@GMAIL.COM",boldFontSubTitle));
		doc.add(new Paragraph("Contact No:- +91 - 9876543210",boldFontSubTitle));
		doc.add(new Paragraph("   ============================================================================",boldFontSubTitle));
		doc.add(new Paragraph("\n                                                                                                                          Date :- "+LocalDate.now()));
		
		doc.add(new Paragraph("\n                                                            "+"Orders-Till-Yesterday\n"+"\n                             ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"));
		
		PdfPCell cell0 = new PdfPCell(new Paragraph("S.No"));
		PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Order ID"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Customer ID"));
		PdfPCell cell4 = new PdfPCell(new Paragraph("Varpulu"));
		PdfPCell cell5 = new PdfPCell(new Paragraph("Sapuri"));
		PdfPCell cell6 = new PdfPCell(new Paragraph("Dupin"));
	    
	    table.addCell(cell0);
	    table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        
        int n1 = 0;
		
		ResultSet rSet = st.executeQuery("SELECT * FROM orders");
		while(rSet.next()) {
			LocalDate presentDate = LocalDate.now();
			LocalDate actualDate = LocalDate.parse(rSet.getString(1));
			if(actualDate.isBefore(presentDate)) {
				n1++;
				String n2 = Integer.toString(n1);
				cell0 = new PdfPCell(new Paragraph(n2));
				cell1 = new PdfPCell(new Paragraph(rSet.getString(1)));
				cell2 = new PdfPCell(new Paragraph(Integer.toString(rSet.getInt(2))));
				cell3 = new PdfPCell(new Paragraph(rSet.getString(3)));
				cell4 = new PdfPCell(new Paragraph(Integer.toString(rSet.getInt(4))));
				cell5 = new PdfPCell(new Paragraph(Integer.toString(rSet.getInt(5))));
				cell6 = new PdfPCell(new Paragraph(Integer.toString(rSet.getInt(6))));
				   
				table.addCell(cell0);
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
				
			}
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

	private void displayOrdersTillYesterday() throws Exception {
		try {
			LocalDate todaysDate = LocalDate.now();
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM orders");
			System.out.println(Main.spacing("Date")+Main.spacing("tOrder ID")+Main.spacing("tCustomer ID")+Main.spacing("No of Varpulu")+Main.spacing("KGs of Sapuri")+Main.spacing("KGs of Dupin"));
			while(rs.next()) {
				LocalDate pastDate = LocalDate.parse(rs.getString(1));
				if(pastDate.isBefore(todaysDate)) {
					System.out.println(Main.spacing(rs.getString(1))+Main.spacing(Integer.toString(rs.getInt(2)))+Main.spacing(rs.getString(3))+Main.spacing(Integer.toString(rs.getInt(4)))+Main.spacing(Integer.toString(rs.getInt(5)))+Main.spacing(Integer.toString(rs.getInt(6))));
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
