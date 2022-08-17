package com.dbs.paymentbank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbs.paymentbank.Model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
