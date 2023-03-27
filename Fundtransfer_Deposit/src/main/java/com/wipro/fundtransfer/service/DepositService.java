package com.wipro.fundtransfer.service;

import java.util.List;

import com.wipro.fundtransfer.exception.FixedDepositExistException;
import com.wipro.fundtransfer.exception.InsufficientFundsException;
import com.wipro.fundtransfer.exception.PinDoesNotMatchException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.FixedDeposit;

public interface DepositService {
	public FixedDeposit createFD(FixedDeposit FD) throws InsufficientFundsException, PinDoesNotMatchException, Exception;
	public List<FixedDeposit> calculateInterest(String adharnumber) throws FixedDepositExistException;
	public Account breakDeposit(long accountnumber) throws FixedDepositExistException, Exception;
}
