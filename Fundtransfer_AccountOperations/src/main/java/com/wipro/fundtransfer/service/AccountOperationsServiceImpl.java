package com.wipro.fundtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.fundtransfer.controller.BankRestController;
import com.wipro.fundtransfer.exception.ResourceNotFoundException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.PinChange;
import com.wipro.fundtransfer.repository.AccountRepository;

import feign.FeignException;

@Service
public class AccountOperationsServiceImpl implements AccountOperationsService{
	
	@Autowired
	BankRestController feignClient;
	@Autowired
	AccountRepository accountrepo;

	@Override
	public double checkbalance(long accountnumber) throws ResourceNotFoundException {
		ResponseEntity<Account> byAccountNumber;
		try {
			byAccountNumber = feignClient.getByAccountNumber(accountnumber);
		}catch(FeignException e) {
			throw new ResourceNotFoundException("Account not found");
		}
		return byAccountNumber.getBody().getBalance();
	}

	@Override
	public Account editpin(PinChange pin ,long accountnumber) throws ResourceNotFoundException {
		Account ac = accountrepo.findById(accountnumber).get();
//		System.out.println(ac.getPin()+"  "+pin.getOldpin());
		if(ac.getPin().equals(pin.getOldpin())) {
			ac.setPin(pin.getNewPin());
			return accountrepo.save(ac);
		}else {
			throw new ResourceNotFoundException("Wrong pin");
		}
		
	}
}
