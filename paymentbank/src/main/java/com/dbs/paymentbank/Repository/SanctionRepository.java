package com.dbs.paymentbank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbs.paymentbank.Model.SanctionList;

public interface SanctionRepository extends JpaRepository<SanctionList, String>{

}
