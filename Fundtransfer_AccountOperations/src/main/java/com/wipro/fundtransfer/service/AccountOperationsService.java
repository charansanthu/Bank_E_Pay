package com.wipro.fundtransfer.service;

import com.wipro.fundtransfer.exception.ResourceNotFoundException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.PinChange;

public interface AccountOperationsService {
	public double checkbalance(long accountnumber) throws ResourceNotFoundException;
	public Account editpin(PinChange pin,long accountnumber) throws ResourceNotFoundException;
}
