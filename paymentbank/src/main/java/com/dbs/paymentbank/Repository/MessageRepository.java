package com.dbs.paymentbank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbs.paymentbank.Model.MessageCode;

public interface MessageRepository extends JpaRepository<MessageCode, String> {

}
