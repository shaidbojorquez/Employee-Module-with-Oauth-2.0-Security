package com.bolsadeideas.springboot.api.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
        super("The entity could not be found.");
    }

    public NotFoundException(String entidad) {
        super("The entity "+entidad+" could not be found");
    }
}