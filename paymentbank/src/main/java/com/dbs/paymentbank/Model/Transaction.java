package com.dbs.paymentbank.Model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    Integer transactionID;
	
	@ManyToOne
    @JoinColumn(name = "customerId")
    Customer customer;

    String receiverName;

    String receiverAccountNumber;

    @ManyToOne
    @JoinColumn(name = "receiver_bic")
    BankBic receiverBIC;


    @ManyToOne
    @JoinColumn(name = "message_code")
    MessageCode messageCode;

    String transferCode;

    Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date timestamp;


    String status = "SUCCESS";
    
    String failureReason;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(Integer transactionID, Customer customer, String receiverName, String receiverAccountNumber,
			BankBic receiverBIC, MessageCode messageCode, String transferCode, Double amount, Date timestamp,
			String status, String failureReason) {
		super();
		this.transactionID = transactionID;
		this.customer = customer;
		this.receiverName = receiverName;
		this.receiverAccountNumber = receiverAccountNumber;
		this.receiverBIC = receiverBIC;
		this.messageCode = messageCode;
		this.transferCode = transferCode;
		this.amount = amount;
		this.timestamp = timestamp;
		this.status = status;
		this.failureReason = failureReason;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	public void setReceiverAccountNumber(String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public BankBic getReceiverBIC() {
		return receiverBIC;
	}

	public void setReceiverBIC(BankBic receiverBIC) {
		this.receiverBIC = receiverBIC;
	}

	public MessageCode getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(MessageCode messageCode) {
		this.messageCode = messageCode;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public java.util.Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.util.Date date) {
		this.timestamp = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
}
