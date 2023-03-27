package com.wipro.fundtransfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.fundtransfer.model.Transactions;



@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

//	public Optional<Transactions> findAll(long accountnumber);
//	public Optional<Transactions> findByUpiId(String upi);
//	public Optional<Transactions> findBymobilenumber(String number);
	public List<Transactions> findAllByfromaccount(String accountnumber);
}
