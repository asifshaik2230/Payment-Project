package com.dbs.paymentbank.Model;

public class TransactionRequest {

	  String customerId;
	  String receiverBIC;
	  String receiverAccountNumber;
	  String receiverAccountName;
	  String transferTypeCode;
      String messageCode;
	  Double amount;
	public TransactionRequest(String customerId, String receiverBIC, String receiverAccountNumber,
			String receiverAccountName, String transferTypeCode, String messageCode, Double amount) {
		super();
		this.customerId = customerId;
		this.receiverBIC = receiverBIC;
		this.receiverAccountNumber = receiverAccountNumber;
		this.receiverAccountName = receiverAccountName;
		this.transferTypeCode = transferTypeCode;
		this.messageCode = messageCode;
		this.amount = amount;
	}
	public TransactionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getReceiverBIC() {
		return receiverBIC;
	}
	public void setReceiverBIC(String receiverBIC) {
		this.receiverBIC = receiverBIC;
	}
	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}
	public void setReceiverAccountNumber(String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}
	public String getReceiverAccountName() {
		return receiverAccountName;
	}
	public void setReceiverAccountName(String receiverAccountName) {
		this.receiverAccountName = receiverAccountName;
	}
	public String getTransferTypeCode() {
		return transferTypeCode;
	}
	public void setTransferTypeCode(String transferTypeCode) {
		this.transferTypeCode = transferTypeCode;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
