package com.wipro.fundtransfer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.fundtransfer.model.Account;


public interface AccountRepository extends JpaRepository<Account,Long>{

	public Optional<Account> findByUpiId(String upi);
	public Optional<Account> findBymobilenumber(long number);

}
