package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import model.FirmReceipts;
import utility.ConnectionManager;

public class FirmReceiptsDAO {
	public void firmReceipts(String id,FirmReceipts fr) throws Exception {
		ConnectionManager cm = new ConnectionManager();
		
		int amt = fr.getAmount();
		String des = fr.getDescription();
		
		int balance =0,pastBalance = 0,pastFirmBalance = 0,cusTransactionID = 0;
		Statement smt = cm.getConnection().createStatement();
		String sql1 = "select * from "+id+"_account";
		ResultSet rst = smt.executeQuery(sql1);
		while(rst.next()) {
			pastBalance = rst.getInt(6);
		}		
		balance = pastBalance+amt;
		
		String sql = "insert into "+id+"_account VALUES(\'"+LocalDate.now().toString()+"\',cus_transaction_ID.nextval,?,?,?,?)";
		PreparedStatement pst = cm.getConnection().prepareStatement(sql);
		
		pst.setString(1, "Amount Paid to dying");
		pst.setInt(2, 0);
		pst.setInt(3, amt);
		pst.setInt(4, balance);
		pst.executeUpdate();
		
		
		String sql2 = "select * from "+id+"_account";
		ResultSet rst1 = smt.executeQuery(sql2);
		while(rst1.next()) {
			cusTransactionID = rst1.getInt(2);
		}
		String sql1Firm = "select * from firm_account";
		ResultSet rst1Firm = smt.executeQuery(sql1Firm);
		while(rst1Firm.next()) {
			pastFirmBalance = rst1Firm.getInt(7);
		}		
		balance = pastFirmBalance+amt;
		
		String sqlFirm = "insert into firm_account VALUES(\'"+LocalDate.now().toString()+"\',transaction_ID.nextval,?,?,?,?,?)";
		PreparedStatement ptFirm = cm.getConnection().prepareStatement(sqlFirm);
		ptFirm.setString(1, des);
		ptFirm.setInt(2, cusTransactionID);
		ptFirm.setInt(3, 0);
		ptFirm.setInt(4, amt);
		ptFirm.setInt(5, balance);
		
		ptFirm.executeUpdate();
		cm.getConnection().close();
		System.out.println("\n\n==============================\n");
		System.out.println("Succesfully Received.");
		System.out.println("\n\n==============================\n");
	}
}
