package utility;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

import controller.AdminControl;
import controller.Main;
import dao.WorkersDAO;
import model.Worker;
import service.AddressValidation;
import service.MobileValidation;
import service.NameValidation;
import service.NumberValidation;

public class WorkersPerticulars {
	static NumberValidation numberValidation = new NumberValidation();
	public void workers() throws Exception {
		System.out.println("Please enter your choice");
		System.out.println("1. Add worker\n2. Available Workers\n3. Remove Worker");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			addWorker();
			break;
		case 2:
			worksInfo();
			break;
		case 3:
			removeWorker();
			break;
		default:
			System.out.println("Please enter valid choice");
			workers();
		}
	}

	private void worksInfo() throws Exception {
		System.out.println("Please enter your choice\n1. Display Available Workers\n2. Print Available Workers");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			displayAvailableWorkers();
			break;
		case 2: 
			printAvailableWorkers();
			break;
		default:
			System.out.println("Please enter valid choice");
			worksInfo();
		}
		
	}

	private void printAvailableWorkers() throws Exception {
		try {
//			int n=0;
//			File fileWorkers = new File("C:\\Users\\mlaks\\eclipse-workspace\\Dying_Monitroings\\BackEnd Data\\Workers.xls");
//			Document doc = new Document();
//			FileInputStream fis = new FileInputStream(fileWorkers);
//			Workbook workbook = new HSSFWorkbook(fis);
//			HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
//
//			PdfWriter wr = PdfWriter.getInstance(doc, new FileOutputStream("Available-Workers.pdf"));
//			Font boldFontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
//			Font boldFontSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//			
//			float [] pointColumnWidths = {100F,200F,180F, 180F,200F,230F,150F};   
//			PdfPTable table = new PdfPTable(7);
//			table.setWidths(pointColumnWidths);
//			
//			doc.open();
//			Image image1 = Image.getInstance("LNS_Dyings_Logo.png");
//			//Fixed Positioning
//			image1.setAbsolutePosition(350f,720f);
//			//Scale to new height and new width of image
//			image1.scaleAbsolute(200, 65);
//			doc.add(image1);
//			
////			doc.add(new Paragraph("RETAIL INVOICE").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
//			doc.add(new Paragraph("Lakshmi Narasimhas Swami Dyings              ",boldFontTitle));
//			doc.add(new Paragraph("Venkateswara Kottalu, Proddatur,Kadapa",boldFontSubTitle));
//			doc.add(new Paragraph("Email :- LNSDYINGS@GMAIL.COM",boldFontSubTitle));
//			doc.add(new Paragraph("Contact No:- +91 - 9876543210",boldFontSubTitle));
//			doc.add(new Paragraph("   ============================================================================",boldFontSubTitle));
//			doc.add(new Paragraph("\n                                                                                                                          Date :- "+LocalDate.now()));
//			doc.add(new Paragraph("\n                                                            Available-Workers\n"+"\n                             ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"));
//			
//			PdfPCell cell0 = new PdfPCell(new Paragraph("S.No"));
//			PdfPCell cell1 = new PdfPCell(new Paragraph("Date of Join"));
//		    PdfPCell cell2 = new PdfPCell(new Paragraph("ID of Worker"));
//		    PdfPCell cell3 = new PdfPCell(new Paragraph("Name of Worker"));
//		    PdfPCell cell4 = new PdfPCell(new Paragraph("Category"));
//		    PdfPCell cell5 = new PdfPCell(new Paragraph("Mobile Number"));
//		    PdfPCell cell6 = new PdfPCell(new Paragraph("Address"));
//		    
//		    table.addCell(cell0);
//		    table.addCell(cell1);
//	        table.addCell(cell2);
//	        table.addCell(cell3);
//	        table.addCell(cell4);
//	        table.addCell(cell5);
//	        table.addCell(cell6);
//			
//			Iterator<Row> itr = sheet.iterator();
//			while(itr.hasNext()) {
//				Row row = itr.next();
//				if(row.getRowNum()!=0) {
//					n++;
//					String t = Integer.toString(n);
//					
//					cell0 = new PdfPCell(new Paragraph(t));
//					cell1 = new PdfPCell(new Paragraph(row.getCell(0).getStringCellValue()));
//				    cell2 = new PdfPCell(new Paragraph(row.getCell(1).getStringCellValue()));
//				    cell3 = new PdfPCell(new Paragraph(row.getCell(2).getStringCellValue()));
//				    cell4 = new PdfPCell(new Paragraph(row.getCell(3).getStringCellValue()));
//				    cell5 = new PdfPCell(new Paragraph(row.getCell(4).getStringCellValue()));
//				    cell6 = new PdfPCell(new Paragraph(row.getCell(5).getStringCellValue()));
//				    
//				    table.addCell(cell0);
//				    table.addCell(cell1);
//			        table.addCell(cell2);
//			        table.addCell(cell3);
//			        table.addCell(cell4);
//			        table.addCell(cell5);
//			        table.addCell(cell6);
//			        
//				}
//			}
//			workbook.close();
//	        
//	        doc.add(table);
//	        
//			doc.close();
//			wr.close();
			System.out.println("PDF generated..");
		}
		catch(Exception e) {
			System.out.println("Error in printing available in printAvailableWorkers");
		}
	}

	private void displayAvailableWorkers() throws Exception {
		try {
			ConnectionManager cm = new ConnectionManager();
			Statement st = cm.getConnection().createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM workers");
			System.out.println("Available workers\n");
			System.out.println("ID\tName\tCategory\tMobile Number\tAddress");
			while(rs.next()) {
				System.out.println(rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6));
			}
			cm.getConnection().close();
		}
		catch(Exception e) {
			System.out.println("Error in workers info in workers perticulars");
		}
		System.out.println();
		redirecting();
	}

	private void removeWorker() throws Exception {
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
			String[] idArr = ids.split(",");
			
			System.out.println("\nPlease enter worker your choice to remove worker");
			int n = Integer.parseInt(numberValidation.enterNumber());
			Statement statement = cm.getConnection().createStatement();
			String sql = "DELETE from workers where worker_id = \'"+idArr[n]+"\'";
			statement.executeUpdate(sql);
			String sqlRemoveAccount = "DROP TABLE "+idArr[n]+"_account";
			statement.executeUpdate(sqlRemoveAccount);
			
			cm.getConnection().close();
			System.out.println("Successfully removed worker");
		}
		catch(Exception e) {
			System.out.println("Error in remove worker");
		}
		redirecting();
	}

	private void addWorker() throws Exception {
		LocalDate date = LocalDate.now();
		try {
		NameValidation nameValidation = new NameValidation();
		String name = nameValidation.enterName();
		System.out.println("Enter category");
		String category = enterCategory();
		MobileValidation mobileValidation = new MobileValidation();
		String mobileNumber = mobileValidation.enterMobileNumber();
		AddressValidation addressValidation = new AddressValidation();
		String address = addressValidation.enterAddress(); 
		String id = generateId(category);
		
		Worker worker = new Worker(date,id, name, category, mobileNumber, address);
		worker.setDate(date);
		worker.setId(id);
		worker.setName(name);
		worker.setCategory(category);
		worker.setMobileNumber(mobileNumber);
		worker.setAddress(address);
		
		//Print worker Info
		printWokerInfo(worker);

		WorkersDAO workersDAO = new WorkersDAO();
		workersDAO.addWorker(worker);
		System.out.println("\n=========================================\n"+"\nWorker added successfully...!\n"+"\n=========================================\n");
		
		}
		catch(Exception e) {
			System.out.println("Error in adding worker..!");
		}
		redirecting();
	}

	public static String enterCategory() {
		String cate = null;
		System.out.println("1.Master\n2.Master Supporter\n3.Writer\n4.Main Supporter\n5.Down Supporter");
		NumberValidation num =new NumberValidation();
		int choice = Integer.parseInt(num.enterNumber());
		switch(choice) {
		case 1:
			cate = "Master";
			break;
		case 2:
			cate = "Master Supporter";
			break;
		case 3:
			cate = "Writer";
			break;
		case 4:
			cate = "Main Supporter";
			break;
		case 5:
			cate = "Down Supporter";
			break;
		default:
			System.out.println("Please enter valid choice");
			enterCategory();
		}
		return cate;
	}

	private void printWokerInfo(Worker worker) {
		System.out.println("Worker datails");
		System.out.println("ID of worker			\t:\t "+worker.getId());
		System.out.println("Name of worker			\t:\t "+worker.getName());
		System.out.println("Category of worker		\t:\t "+worker.getCategory());
		System.out.println("Mobile Number of worker		\t:\t "+worker.getMobileNumber());
		System.out.println("Address of worker		\t:\t "+worker.getAddress());
	}
	
	private String generateId( String category) {
		Random rand = new Random();
		int idRandom = rand.nextInt(10000);
        String idStr = Integer.toString(idRandom);
		String subCategory=null,finalID=null;
		if(category.equals("Master"))
			subCategory = "MSR";
		else if(category.equals("Master Supporter"))
			subCategory = "MSS";
		else if(category.equals("Writer"))
			subCategory = "WTR";
		else if(category.equals("Main Supporter"))
			subCategory = "MNS";
		else if(category.equals("Down Supporter"))
			subCategory = "DNS";
		else
			subCategory = "#";
		if(idStr.length() == 3)
			finalID = "LNS0"+idStr+subCategory;
		else if(idStr.length() == 2)
			finalID = "LNS00"+idStr+subCategory;
		else if(idStr.length() == 1)
			finalID = "LNS000"+idStr+subCategory;
		else if(idStr.length() == 0)
			generateId(category);
		else
			finalID = "LNS"+idStr+subCategory;
		return finalID;
	}
	private void redirecting() throws Exception{
		System.out.println("\nPlease select your choice\n1. Workers Section\n2. Admin Section\n3. Main Section\n4. Exit\n");
		int choice = Integer.parseInt(numberValidation.enterNumber());
		switch(choice) {
		case 1:
			workers();
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
