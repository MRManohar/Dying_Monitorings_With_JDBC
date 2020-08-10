package utility;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import controller.AdminControl;
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
			billing(cusID);
		}
		catch(Exception e) {
			System.out.println("Error in bill generating in admin controller");
		}
	}
	private void billing(String cusID) throws Exception{
		System.out.println("Please enter your chocice\n1.Display Bill\n2.Print Bill");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			toDisplayBill(cusID);
			break;
		case 2:
			toPrintBill(cusID);
			break;
		default:
			System.out.println("Please enter valid choice");
			billing(cusID);
		}
	}
	private void toPrintBill(String cusID) throws Exception{
		try {
			Document doc = new Document();
			PdfWriter wr = PdfWriter.getInstance(doc, new FileOutputStream(cusID+"-Bill.pdf"));
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
			
			doc.add(new Paragraph("\n                                                            "+cusID+"\'s-Bill\n"+"\n                             ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"));
			
			PdfPCell cell0 = new PdfPCell(new Paragraph("S.No"));
			PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Order ID"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Varpulu"));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Sapuri"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Dupin"));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Amount"));
		    
		    table.addCell(cell0);
		    table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
	        
	        int n1 = 0;
	        Long sum = (long) 0;
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM "+cusID);
			while(rs.next()) {
				n1++;
				String n2 = Integer.toString(n1);
				Long amt = amount(rs.getLong(3),rs.getLong(4),rs.getLong(5));
				sum += amt;
				cell0 = new PdfPCell(new Paragraph(n2));
				cell1 = new PdfPCell(new Paragraph(rs.getString(1)));
				cell2 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(2))));
				cell3 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(3))));
				cell4 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(4))));
				cell5 = new PdfPCell(new Paragraph(Integer.toString(rs.getInt(5))));
				cell6 = new PdfPCell(new Paragraph(Long.toString(amt)));
				   
				table.addCell(cell0);
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
				
			}
			String sumStr = Long.toString(sum);
	        PdfPCell cell23 = new PdfPCell(new Phrase("Total"));
	        cell23.setColspan(6);
	        cell23.setRowspan(1);
	        table.addCell(cell23);
	        cell5 = new PdfPCell(new Paragraph(sumStr));
	        table.addCell(cell5);
	        
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
			System.out.println("Error in printing bill for customer in Bill");
		}
	}
	private void toDisplayBill(String cusID) throws Exception{
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		Long sum=(long) 0;
		ResultSet rs = st.executeQuery("SELECT * FROM "+cusID);
		System.out.println(Main.spacing("Date")+Main.spacing("Order ID")+Main.spacing("No of Varpulu")+Main.spacing("KGs of Sapuri")+Main.spacing("KGs of Dupin")+Main.spacing("Amount"));
		System.out.println("\n\n======================================================================================================================================\n");
		while(rs.next()) {
			Long varpulu = rs.getLong(3);
			Long sapuri = rs.getLong(3);
			Long dupin = rs.getLong(3);
			
			Long amt = amount(varpulu,sapuri,dupin);
			System.out.println(Main.spacing(rs.getString(1))+Main.spacing(Integer.toString(rs.getInt(2)))+Main.spacing(Integer.toString(rs.getInt(3)))+Main.spacing(Integer.toString(rs.getInt(4)))+Main.spacing(Integer.toString(rs.getInt(5)))+Main.spacing(Long.toString(amt)));
			sum+=amt;
		}
		System.out.println("\n\n======================================================================================================================================\n");
		System.out.println(Main.spacing(" ")+Main.spacing(" ")+Main.spacing(" ")+Main.spacing(" ")+Main.spacing("Total")+Main.spacing(Long.toString(sum))+"\n");
		
		cm.getConnection().close();
		redirecting();
	}
	private Long amount(Long varpulu, Long sapuri, Long dupin) {
		return (varpulu*450)+(sapuri*200)+(dupin*120);
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