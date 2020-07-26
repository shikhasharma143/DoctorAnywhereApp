package com.doctoranywhere.exception;

public enum ValidationErrorCode {

	MANDATORY_FIELD(1001),
	INVALID_FORMAT(1002),
	INVALID_VALUE(1003),
	ALREADY_EXISTS(1004),
	MODIFICATION_NOT_ALLOWED(1005),
	OTHER(1007),
	EXCEEDED_LENGTH(1009), 
	ERROR(1010);
	Integer errorCode;
	
	public Integer getErrorCode() {
		return errorCode;
	}
	private ValidationErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	

}
