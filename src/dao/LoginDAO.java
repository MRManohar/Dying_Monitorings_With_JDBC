package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Login;
import utility.ConnectionManager;

public class LoginDAO {

	public boolean validate(Login login) throws ClassNotFoundException, SQLException {
		String userName = login.getUserName();
		String password = login.getPassword();
		int t = 0;
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		
		ResultSet rs = st.executeQuery("SELECT * FROM cus_details");
		
		while(rs.next()) {
			t=0;
			if(userName.equals(rs.getString(4))) {
				if(password.equals(rs.getString(7))){
					t = 1;
					cm.getConnection().close();
					break;
				}
			}
		}
		if(t==1)
			return true;
		else
			return false;
	}

	public String findCusID(Login login) throws ClassNotFoundException, SQLException {
		String userName = login.getUserName();
		String cusID = null;
		int t = 0;
		ConnectionManager cm = new ConnectionManager();
		Statement st = cm.getConnection().createStatement();
		
		ResultSet rs = st.executeQuery("SELECT * FROM cus_details");
		
		while(rs.next()) {
			t=0;
			if(userName.equals(rs.getString(4))) {
				cusID = rs.getString(2);
				t = 1;
				cm.getConnection().close();
				break;
			}
		}
		if(t==1)
			return cusID;
		else
			return null;
		}
	

}
