package dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

import model.Worker;
import utility.ConnectionManager;

public class WorkersDAO {

	public void addWorker(Worker worker) {
		String date = worker.getDate().toString();
		String w_id = worker.getId();
		String w_name = worker.getName();
		String category = worker.getCategory();
		String mobile_number = worker.getMobileNumber();
		String address = worker.getAddress();
		try {
			ConnectionManager cm = new ConnectionManager();
			//Install all the details into database
			
			//Creating individual worker account
			String sqlCreatingBillTable = "CREATE TABLE "+w_id+"_account as (select * from worker_ids_account)";
			String sqlInsertingStartingValues = "insert into "+w_id+"_account values(\'"+LocalDate.now().toString()+"\',worker_transaction_ID.nextval,\'Zero Transaction\',0,0,0)"; 
			Statement statementBillTable = cm.getConnection().createStatement();
			statementBillTable.executeUpdate(sqlCreatingBillTable);
			statementBillTable.executeUpdate(sqlInsertingStartingValues);
			
			String sql = "insert into workers VALUES(?,?,?,?,?,?)";
			PreparedStatement st = cm.getConnection().prepareStatement(sql);
			
			st.setString(1, date);
			st.setString(2, w_id);
			st.setString(3, w_name);
			st.setString(4, category);
			st.setString(5, mobile_number);
			st.setString(6, address);
			
			st.executeUpdate();
			cm.getConnection().close();
			}
			catch(Exception e) {
				System.out.println("Error in stroing data in registrations in sql");
			}
	}
}
