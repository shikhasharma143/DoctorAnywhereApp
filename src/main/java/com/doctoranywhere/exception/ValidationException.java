package com.doctoranywhere.exception;

public class ValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private ValidationErrorResponse validationResponse;
	
	public ValidationException(ValidationErrorResponse validationResponse){
		super(validationResponse.getErrors().toString());
		this.setValidationResponse(validationResponse);
	}

	public ValidationException(String message){
		super(message);
	}

	public ValidationException(String message, Throwable cause){
		super(message, cause);
	}
	
	public void setValidationResponse(ValidationErrorResponse validationResponse) {
		this.validationResponse = validationResponse;
	}
	
	public ValidationErrorResponse getValidationResponse() {
		return validationResponse;
	}
	
}
