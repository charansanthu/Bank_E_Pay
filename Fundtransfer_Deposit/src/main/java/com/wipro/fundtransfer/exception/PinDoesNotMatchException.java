package com.wipro.fundtransfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT , reason="Wrong Pin")
public class PinDoesNotMatchException extends Exception {

}
