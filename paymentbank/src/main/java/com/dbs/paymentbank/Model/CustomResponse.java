package com.dbs.paymentbank.Model;

public class CustomResponse {
	String message;
	String description;
	public CustomResponse(String message, String description) {
		super();
		this.message = message;
		this.description = description;
	}
	public CustomResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}