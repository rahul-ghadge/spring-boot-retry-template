package com.arya.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AgeNotInRangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AgeNotInRangeException(String errorMessage) {
        super(errorMessage);
    }

}
