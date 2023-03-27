package com.wipro.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.fundtransfer.exception.AccountNotFoundException;
import com.wipro.fundtransfer.exception.InsufficientFundException;
import com.wipro.fundtransfer.exception.ResourcenotFoundException;
import com.wipro.fundtransfer.model.Transactions;
import com.wipro.fundtransfer.service.TransactionService;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {
	
	@Autowired
	private TransactionService service;
	private ResponseEntity responseEntity ;
	
	@PutMapping("/account")
	public ResponseEntity<Transactions> transferfund(@RequestBody Transactions transaction) throws Exception{
		try {
			Transactions c =service.transferfund(transaction);
			responseEntity = new ResponseEntity<Transactions>(c, HttpStatus.OK);
		}catch(InsufficientFundException e) {
			responseEntity = new ResponseEntity<>("Insufficient funds or Incoorect Pin", HttpStatus.CONFLICT);
		}catch(AccountNotFoundException e) {
			responseEntity = new ResponseEntity<>("Account Not Found", HttpStatus.CONFLICT);
		}catch(ResourcenotFoundException e) {
			responseEntity = new ResponseEntity<>("Account doesn't Found", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	@PostMapping("/Upi")
	public ResponseEntity<?> transferfundusingUpi(@RequestBody Transactions transaction) throws  Exception{
		try {
			Transactions c = service.fundtransferUpi(transaction);
			responseEntity = new ResponseEntity<Transactions>(c, HttpStatus.OK);
		}catch(InsufficientFundException e) {
			responseEntity = new ResponseEntity<>("wrong pin or insufficient funds", HttpStatus.CONFLICT);
		}catch(AccountNotFoundException e) {
			responseEntity = new ResponseEntity<>("Account Not Found", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	@PostMapping("/mobile")
	public ResponseEntity<?> transferfundusingmobile(@RequestBody Transactions transaction) throws  Exception{
		try {
			Transactions c = service.fundtransferUsingMobile(transaction);
			responseEntity = new ResponseEntity<Transactions>(c, HttpStatus.OK);
		}catch(InsufficientFundException e) {
			responseEntity = new ResponseEntity<>("Insufficient Funds or Incoorect Pin", HttpStatus.CONFLICT);
		}catch(AccountNotFoundException e) {
			responseEntity = new ResponseEntity<>("Account Not Found", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
}
