package com.doctoranywhere.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.doctoranywhere.controller.PatientNotFoundException;
import com.doctoranywhere.exception.ValidationErrorCode;
import com.doctoranywhere.exception.ValidationException;
import com.doctoranywhere.model.Error;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(PatientNotFoundException ex, WebRequest request) {
		Error errorDetails = new Error(new Date(), ex.getMessage(), request.getDescription(false));
		errorDetails.setCode(ValidationErrorCode.INVALID_VALUE.getErrorCode());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> validationException(ValidationException ex, WebRequest request) {
		return new ResponseEntity<>(ex.getValidationResponse().getErrors(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		Error errorDetails = new Error(new Date(), ex.getMessage(), request.getDescription(false));
		errorDetails.setCode(ValidationErrorCode.INVALID_VALUE.getErrorCode());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		String mapAsString = errors.keySet().stream().map(key -> key + "=" + errors.get(key))
				.collect(Collectors.joining(", ", "{", "}"));
		Error errorDetails = new Error(new Date(), mapAsString, request.getDescription(false));
		errorDetails.setCode(ValidationErrorCode.INVALID_VALUE.getErrorCode());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}