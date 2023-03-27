package com.wipro.fundtransfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT , reason="Fixed Deposit Doesn't Created")
public class FixedDepositExistException  extends Exception{

}
