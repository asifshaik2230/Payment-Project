package com.dbs.paymentbank.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SanctionList {
  @Id
  String accountNumber;
  String name;
public SanctionList() {
	super();
	// TODO Auto-generated constructor stub
}
public SanctionList(String accountNumber, String name) {
	super();
	this.accountNumber = accountNumber;
	this.name = name;
}
public String getAccountNumber() {
	return accountNumber;
}
public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
  
  
}
