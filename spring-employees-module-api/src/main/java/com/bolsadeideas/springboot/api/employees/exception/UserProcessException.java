package com.bolsadeideas.springboot.api.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserProcessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserProcessException() {
        
    }

    public UserProcessException(String message) {
        super(message);
    }
}
