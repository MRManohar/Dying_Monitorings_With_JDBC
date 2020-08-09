package model;

public class CusPaymentModel {
	private int amount;
	private String Description;
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public CusPaymentModel(int amount, String description) {
		this.amount = amount;
		Description = description;
	}
}
