package com.wipro.fundtransfer.model;

import java.time.LocalDate;
import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transactions {

	@Id
	private long transactionid;
	private String fromaccount;
	private String toaccount;
	private double amount;
	private String pin;
	LocalDate transactiondate = LocalDate.now();
	private String transactiontype;
	
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	public LocalDate getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(LocalDate transactiondate) {
		this.transactiondate = transactiondate;
	}
	public long getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(long transactionid) {
		Random random = new Random();
		long id = 100000 + random.nextLong(999999999);
		this.transactionid = id;
	}
	public String getFromaccount() {
		return fromaccount;
	}
	public void setFromaccount(String fromaccount) {
		this.fromaccount = fromaccount;
	}
	public String getToaccount() {
		return toaccount;
	}
	public void setToaccount(String toaccount) {
		this.toaccount = toaccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Transactions(long transactionid, String fromaccount, String toaccount, double amount, String pin,
			LocalDate transactiondate, String transactiontype) {
		super();
		Random random = new Random();
		long id = 100000 + random.nextLong(999999999);
		this.transactionid = id;
		this.fromaccount = fromaccount;
		this.toaccount = toaccount;
		this.amount = amount;
		this.pin = pin;
		this.transactiondate = LocalDate.now();
		this.transactiontype = transactiontype;
	}
	public Transactions() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
