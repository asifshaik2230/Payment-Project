package com.dbs.paymentbank.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
	@Id
	String accountNumber;
	String customerName;
	Double clearBalance;
	Boolean overDraft;
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(String accountNumber, String customerName, Double clearBalance, Boolean overDraft) {
		super();
		this.accountNumber = accountNumber;
		this.customerName = customerName;
		this.clearBalance = clearBalance;
		this.overDraft = overDraft;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Double getClearBalance() {
		return clearBalance;
	}
	public void setClearBalance(Double clearBalance) {
		this.clearBalance = clearBalance;
	}
	public Boolean getOverDraft() {
		return overDraft;
	}
	public void setOverDraft(Boolean overDraft) {
		this.overDraft = overDraft;
	}
	
	
}
