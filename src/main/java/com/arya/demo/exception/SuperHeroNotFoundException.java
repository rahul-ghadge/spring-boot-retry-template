package com.arya.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class SuperHeroNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SuperHeroNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
