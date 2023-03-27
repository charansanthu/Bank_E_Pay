package com.wipro.fundtransfer.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wipro.fundtransfer.exception.FixedDepositExistException;
import com.wipro.fundtransfer.exception.InsufficientFundsException;
import com.wipro.fundtransfer.exception.PinDoesNotMatchException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.FixedDeposit;
import com.wipro.fundtransfer.service.DepositService;

@RestController
@CrossOrigin
public class DepositController {
	@Autowired
	private DepositService depositService;
	@Autowired
	private RestTemplate restTemplate;
	private ResponseEntity responseEntity;
	@PostMapping("/deposit/createdeposit")
	public ResponseEntity<FixedDeposit> getacc(@RequestBody FixedDeposit fixedDeposit) throws Exception {
		try {
		FixedDeposit FD= depositService.createFD(fixedDeposit);
//		System.out.println(FD.getAdharnumber());
		responseEntity = new ResponseEntity<FixedDeposit>(FD, HttpStatus.OK);
		
		}catch(InsufficientFundsException e) {
			responseEntity = new ResponseEntity<>("Insufficient Funds", HttpStatus.CONFLICT);
		}catch(FixedDepositExistException e) {
			responseEntity = new ResponseEntity<>("Already FD created", HttpStatus.CONFLICT);
		}catch(PinDoesNotMatchException e) {
			responseEntity = new ResponseEntity<>("Wrong Pin", HttpStatus.CONFLICT);
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Account Not Found", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	@GetMapping("/deposit/getFDaccount/{adharnumber}")
	public ResponseEntity<?> getinterest(@PathVariable String adharnumber){
		try {
			List<FixedDeposit> i = depositService.calculateInterest(adharnumber);
			responseEntity = new ResponseEntity<>(i, HttpStatus.OK);
		}catch(FixedDepositExistException e) {
			responseEntity = new ResponseEntity<>("Account Not Found", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	@DeleteMapping("/deposit/breakDeposit/{accountnumber}")
	public ResponseEntity<Account> breakDeposit(@PathVariable long accountnumber){
		try {
			Account ac=depositService.breakDeposit(accountnumber);
			responseEntity = new ResponseEntity<>(ac, HttpStatus.OK);
		}catch(FixedDepositExistException e) {
			responseEntity = new ResponseEntity<>("Account Not Found", HttpStatus.CONFLICT);
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Account Not Found", HttpStatus.CONFLICT);
			
		}
		return responseEntity;
	}
}
