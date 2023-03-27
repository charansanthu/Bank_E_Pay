package com.wipro.fundtransfer.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.fundtransfer.model.Account;

@FeignClient("Fundtransfer-Bank/bankepay")
public interface BankRestController {
	@GetMapping("/getbyaccno/{accountnumber}")
	public ResponseEntity<Account> getByAccountNumber(@PathVariable long accountnumber);
}
