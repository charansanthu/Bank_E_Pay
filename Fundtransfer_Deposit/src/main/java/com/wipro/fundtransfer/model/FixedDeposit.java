package com.wipro.fundtransfer.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class FixedDeposit {
	private String adharnumber;
	private double amount;
	LocalDate transactiondate = LocalDate.now();
	@Id
	private long accountnumber;
	private double interest;
	private int interestrate;
	LocalDate timeperiod = LocalDate.now();
	private String pin;
	
	public int getInterestrate() {
		return interestrate;
	}
	public void setInterestrate(int interestrate) {
		this.interestrate = interestrate;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public LocalDate getTimeperiod() {
		return timeperiod;
	}
	public void setTimeperiod(LocalDate timeperiod) {
		this.timeperiod = timeperiod;
	}
	public String getAdharnumber() {
		return adharnumber;
	}
	public void setAdharnumber(String adharnumber) {
		this.adharnumber = adharnumber;
	}
	//	public long getTransactionid() {
//		return transactionid;
//	}
//	public void setTransactionid(long transactionid) {
//		Random random = new Random();
//		long id = 100000 + random.nextLong(999999999);
//		this.transactionid = id;
//	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDate getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(LocalDate transactiondate) {
		this.transactiondate = transactiondate;
	}
	public long getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(long accountnumber) {
		this.accountnumber = accountnumber;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	
}
