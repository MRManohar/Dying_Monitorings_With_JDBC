package model;

import java.time.LocalDate;

public class Worker {
	private LocalDate date;
	private String id;
	private String name;
	private String category;
	private String mobileNumber;
	private String address;
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Worker(LocalDate date,String id, String name, String category, String mobileNumber, String address) {
		this.date=date;
		this.id = id;
		this.name = name;
		this.category = category;
		this.mobileNumber = mobileNumber;
		this.address = address;
	}
	
	
}
