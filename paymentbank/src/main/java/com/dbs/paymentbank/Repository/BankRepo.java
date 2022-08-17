package com.dbs.paymentbank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbs.paymentbank.Model.BankBic;

public interface BankRepo extends JpaRepository<BankBic, String> {

}
