package com.doctoranywhere.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AddressNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AddressNotFoundException(String message) {
		super(message);
	}

}
