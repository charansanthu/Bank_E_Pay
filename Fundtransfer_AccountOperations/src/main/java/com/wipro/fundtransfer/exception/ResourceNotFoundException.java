package com.wipro.fundtransfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT , reason="Resource Not Found")
public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = 1890930289700525692L;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}
	public ResourceNotFoundException() {
		super();
	}
}
