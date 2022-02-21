package com.bolsadeideas.springboot.api.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class DuplicatedElementException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DuplicatedElementException() {
        super("The entity could not be found.");
    }

    public DuplicatedElementException(String entidad) {
        super("The entity "+entidad+" already exist");
    }
}
