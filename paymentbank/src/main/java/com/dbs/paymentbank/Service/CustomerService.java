package com.dbs.paymentbank.Service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.dbs.paymentbank.Model.BankBic;
import com.dbs.paymentbank.Model.CustomResponse;
import com.dbs.paymentbank.Model.Customer;
import com.dbs.paymentbank.Model.MessageCode;
import com.dbs.paymentbank.Model.SanctionList;
import com.dbs.paymentbank.Model.Transaction;
import com.dbs.paymentbank.Model.TransactionRequest;
import com.dbs.paymentbank.Repository.BankRepo;
import com.dbs.paymentbank.Repository.CustomerRepository;
import com.dbs.paymentbank.Repository.MessageRepository;
import com.dbs.paymentbank.Repository.SanctionRepository;
import com.dbs.paymentbank.Repository.TransactionRepository;


@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	BankRepo bankRepo;
	
	@Autowired
	MessageRepository messageCodeRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	SanctionRepository sanctionRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	
	private Transaction storeTransaction(Customer customer, Double totalAmount,TransactionRequest payload, BankBic bankbic,MessageCode messageCode, String status, String reason) {
		
		Transaction transactionItem =new Transaction();
		transactionItem.setCustomer(customer);
        transactionItem.setAmount(totalAmount);
        transactionItem.setTransferCode(payload.getTransferTypeCode());
        transactionItem.setTimestamp(new java.util.Date());
        transactionItem.setReceiverBIC(bankRepo.findById(payload.getReceiverBIC()).get());
        transactionItem.setMessageCode(messageCodeRepository.findById(payload.getMessageCode()).get());
        transactionItem.setReceiverAccountNumber(payload.getReceiverAccountNumber());
        transactionItem.setReceiverName(payload.getReceiverAccountName());
        transactionItem.setStatus(status);
        transactionItem.setFailureReason(reason);
        return transactionRepository.save(transactionItem);
		
	}
	 public Optional<Customer> getCustomerById(String cid) {
	        return customerRepository.findById(cid);
	    }

	public Optional<BankBic> getBankByBIC(String bic) {
		// TODO Auto-generated method stub
		return bankRepo.findById(bic);
	}
	public List<MessageCode> getMessageCodes() {
		// TODO Auto-generated method stub
		return messageCodeRepository.findAll();
	}

	public ResponseEntity<Object> processTransaction(TransactionRequest request) {
		System.out.println(request);
		 Customer customer = customerRepository.findById((request.getCustomerId())).get();
		  Double transferFee = 0.0025 * request.getAmount();
	        Double totalAmount = request.getAmount() + transferFee;
	      
	        
	        try {
                File sanctionedFile = ResourceUtils.getFile("classpath:SDNlist.txt");
                FileReader fileReader = new FileReader(sanctionedFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String s;
                String[] words = request.getReceiverAccountName().split(" ");
                Permutations putil = new Permutations(Arrays.asList(words));
                List<String> permutations = putil.getAllCombinations();
                String pattern = "(" + String.join("|", permutations) + ")";
                System.out.println(pattern);
                Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                while ((s = bufferedReader.readLine()) != null) {
                    Matcher m = p.matcher(s);
                    if (m.find()) {
                        storeTransaction(customer, totalAmount, request, bankRepo.findById(request.getReceiverBIC()).get()
                                , messageCodeRepository.findById(request.getMessageCode()).get(), "FAILED", "Name Found in SDN List");
                        return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(new CustomResponse("Red Alert",
                                "(Busted) The Account Holder name is found in SDNList... This will be reported to higher authority.... DANGER!!! DANGER!!!!"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
	        
	        //terrorist bro
//	        if(!sanctionRepository.findById(request.getReceiverAccountNumber()).isEmpty()) {
//	        	Optional<SanctionList> li= sanctionRepository.findById(request.getCustomerId());
//	        	storeTransaction(customer, totalAmount, request, bankRepo.findById(request.getReceiverBIC()).get()
//	                    , messageCodeRepository.findById(request.getMessageCode()).get(), "Failed", "Cannot Transfer amount to sanctionedList");
//	        	 return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(new CustomResponse("Red Alert",
//                         "(Busted) The Account Holder name is found in SDNList... This will be reported to higher authority.... DANGER!!! DANGER!!!!"));
//	        	
//	        }
	        //No Balance bro...
	        if (customer.getClearBalance() < totalAmount && !customer.getOverDraft()) {
	            storeTransaction(customer, totalAmount, request, bankRepo.findById(request.getReceiverBIC()).get(), messageCodeRepository.findById(request.getMessageCode()).get(), "Failed", "Insufficient Balance in Bank Account");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse("Transaction Failed",
	                    "Insufficient Balance in your Account"));
	        }
	        //No self bro..
	        if (customer.getAccountNumber().equals(request.getReceiverAccountNumber())) {
	        	storeTransaction(customer, totalAmount, request, bankRepo.findById(request.getReceiverBIC()).get()
	                    , messageCodeRepository.findById(request.getMessageCode()).get(), "Failed", "Cannot Transfer amount to itself");
	        	return ResponseEntity
	                    .status(HttpStatus.NOT_ACCEPTABLE)
	                    .body(new CustomResponse("Transaction Failed", "Cannot Transfer amount to itself"));
	        }
	        
	        //transaction type bro...
	        if (customer.getCustomerName().equals("HDFC BANK") && !request.getTransferTypeCode().equals("O")) {
	           
	                storeTransaction(customer, totalAmount, request, bankRepo.findById(request.getReceiverBIC()).get()
	                        , messageCodeRepository.findById(request.getMessageCode()).get(), "FAILED", "Receiver and Sender Account number must be an Internal HDFC bank");
	                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new CustomResponse("Transaction Failed: Invalid Receiver A/c",
	                        "Receiver and Sender Account number must be an Internal HDFC bank"));
	            
	           // request.setReceiverAccountName(receiver.get().getName());
	        }
	       //success case bro..
	      storeTransaction(customer, totalAmount, request, bankRepo.findById(request.getReceiverBIC()).get()
	                , messageCodeRepository.findById(request.getMessageCode()).get(), "SUCCESS", null);
	        customer.setClearBalance(customer.getClearBalance() - Math.abs(totalAmount));
	        customerRepository.save(customer);
	        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomResponse("Transaction Successful",
                    "Your Transaction is Successfully Completed"));
	}
	
	
	 
}
