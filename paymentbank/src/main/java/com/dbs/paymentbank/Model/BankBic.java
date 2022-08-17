package com.dbs.paymentbank.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BankBic {
	@Id
	String bic;
	String name;
	public BankBic(String bic, String name) {
		super();
		this.bic = bic;
		this.name = name;
	}
	public BankBic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
