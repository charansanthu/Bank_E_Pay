package com.wipro.fundtransfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT , reason="Account Not Found")
public class ResourcenotFoundException extends Exception{

}
