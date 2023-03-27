package com.wipro.fundtransfer.model;

import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Account {
	@Id
	private long accountnumber;
	private double balance;
	@NotNull
	private String accounttype;
	@NotNull
	private String bankname;
	@NotNull
	private long mobilenumber;
	@NotNull
	private String upiId;
	@NotNull
	private String pin;
	
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getUpiId() {
		return upiId;
	}
	public void setUpiId(String upiId) {
//		this.upiId = upiId;
		this.upiId=this.mobilenumber+"@"+this.bankname;
	}
	public long getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(long mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public long getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(long accountnumber) {
		Random random = new Random();
		long id = 100000 + random.nextLong(999999999);
		this.accountnumber = id;
	}

	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
}
