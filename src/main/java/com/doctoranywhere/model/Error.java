package com.doctoranywhere.model;

import java.util.Date;

import lombok.Data;

@Data
public class Error {
	private Integer code;
	private String fields;
	private String message;
	private String details;
	private Date timestamp;

	public Error() {
		
	}
	public Error(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
}
