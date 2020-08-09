package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.Registrations;
import utility.ConnectionManager;

public class RegistrationDAO {
	public void addUser(Registrations registration) throws ClassNotFoundException, SQLException {
		LocalDate date = registration.getDate();
		String dateStr = date.toString();
		String cus_id = registration.getCus_ID();
		String name = registration.getName();
		String userName = registration.getUserName();
		String mobileNumber = registration.getMobileNumber();
		String password = registration.getPassword();
		String email = registration.getEmail();
		try {
		ConnectionManager cm = new ConnectionManager();
		//Install all the details into database
		String sql = "insert into cus_details VALUES(?,?,?,?,?,?,?)";
		
		//Creating individual order account to new customer
		String sqlCreatingCusTable = "CREATE TABLE "+cus_id+" as (select * from cus_ids)";
		Statement statement = cm.getConnection().createStatement();
		statement.executeUpdate(sqlCreatingCusTable);

		//Creating individual bill account to new customer
		String sqlCreatingBillTable = "CREATE TABLE "+cus_id+"_account as (select * from cus_ids_account)";
		Statement statementBillTable = cm.getConnection().createStatement();
		statementBillTable.executeUpdate(sqlCreatingBillTable);
		
		//create statement object
		PreparedStatement st = cm.getConnection().prepareStatement(sql);
		
		st.setString(1, dateStr);
		st.setString(2, cus_id);
		st.setString(3, name);
		st.setString(4, userName);
		st.setString(5, email);
		st.setString(6, mobileNumber);
		st.setString(7, password);
		
		st.executeUpdate();
		
		String sqlZeroTransaction = "INSERT INTO "+cus_id+"_account VALUES(\'"+LocalDate.now().toString()+"\',cus_transaction_ID.nextval,\'Zero Transaction\',0,0,0)";
		Statement statementZeroTransaction = cm.getConnection().createStatement();
		statementZeroTransaction.executeUpdate(sqlZeroTransaction);
		
		cm.getConnection().close();
		}
		catch(Exception e) {
			System.out.println("Error in stroing data in registrations in sql");
		}
	}
}
