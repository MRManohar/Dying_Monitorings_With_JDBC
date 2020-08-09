package model;

import java.time.LocalDate;

public class Registrations {
	private LocalDate dateTime;
	private String cus_ID;
	private String name;
	private String userName;
	private String mobileNumber;
	private String email;
	private String password;
	
	public String getCus_ID() {
		return cus_ID;
	}
	public void setCus_ID(String cus_ID) {
		this.cus_ID = cus_ID;
	}
	public LocalDate getDate() {
		return dateTime;
	}
	public void setDate(LocalDate dateTime) {
		this.dateTime = dateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public Registrations(LocalDate dateTime,String cus_ID,String name, String userName, String email, String mobileNumber, String password) {
		this.dateTime = dateTime;
		this.cus_ID = cus_ID;
		this.name = name;
		this.userName = userName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.password = password;	
	}
}
