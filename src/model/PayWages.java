package model;

import java.time.LocalDate;

public class PayWages {
	private LocalDate date;
	private String description;
	private int credit;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public PayWages(LocalDate date, String description, int credit) {
		this.date = date;
		this.description = description;
		this.credit = credit;
	}
}
