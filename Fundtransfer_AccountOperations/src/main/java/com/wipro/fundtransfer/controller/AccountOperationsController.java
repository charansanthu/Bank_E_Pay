package com.wipro.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wipro.fundtransfer.exception.ResourceNotFoundException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.PinChange;
import com.wipro.fundtransfer.service.AccountOperationsService;
@RestController
@CrossOrigin
@RequestMapping("/accountoperations")
public class AccountOperationsController {
	@Autowired
	private AccountOperationsService accountOperationsService;
	private ResponseEntity<?> responseEntity;
	
	@GetMapping("/checkbalance/{accountnumber}")
	public ResponseEntity<?> checkbalance(@PathVariable long accountnumber) throws ResourceNotFoundException {
		try{
			Double balance=accountOperationsService.checkbalance(accountnumber);
			responseEntity = new ResponseEntity<Double>(balance,HttpStatus.OK);
		}catch(ResourceNotFoundException e) {
			responseEntity= new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	@PostMapping("/editpin/{accountnumber}")
	public ResponseEntity<?> editpin(@PathVariable long accountnumber,@RequestBody PinChange pin) throws ResourceNotFoundException{
		try {
			Account ac= accountOperationsService.editpin(pin, accountnumber);
			responseEntity = new ResponseEntity<>(ac,HttpStatus.OK);
		}catch(ResourceNotFoundException e) {
			responseEntity= new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
}
