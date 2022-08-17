package com.dbs.paymentbank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbs.paymentbank.Model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>{

}
