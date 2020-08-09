package model;

import java.time.LocalDate;

public class Orders {
	private LocalDate date;
	private int countOfVarpulu;
	private int sapuriKgs;
	private int dupinKgs;
	
	public int getCountOfVarpulu() {
		return countOfVarpulu;
	}
	public void setCountOfVarpulu(int countOfVarpulu) {
		this.countOfVarpulu = countOfVarpulu;
	}
	public int getSapuriKgs() {
		return sapuriKgs;
	}
	public void setSapuriKgs(int sapuriKgs) {
		this.sapuriKgs = sapuriKgs;
	}
	public int getDupinKgs() {
		return dupinKgs;
	}
	public void setDupinKgs(int dupinKgs) {
		this.dupinKgs = dupinKgs;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Orders(LocalDate date, int countOfVarpulu, int sapuriKgs, int dupinKgs) {
		this.date = date;
		this.countOfVarpulu = countOfVarpulu;
		this.sapuriKgs = sapuriKgs;
		this.dupinKgs = dupinKgs;
	}
}
