package com.wipro.fundtransfer.service;

import com.wipro.fundtransfer.exception.AccountNotFoundException;
import com.wipro.fundtransfer.exception.InsufficientFundException;
import com.wipro.fundtransfer.exception.ResourcenotFoundException;
import com.wipro.fundtransfer.model.Transactions;

public interface TransactionService {
	public Transactions transferfund(Transactions transaction) throws ResourcenotFoundException, InsufficientFundException, AccountNotFoundException;
	public Transactions fundtransferUsingMobile(Transactions transaction) throws Exception;
	public Transactions fundtransferUpi(Transactions transaction) throws Exception;
}
