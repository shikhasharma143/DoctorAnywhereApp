package com.doctoranywhere.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.doctoranywhere.exception.ValidationErrorCode;
import com.doctoranywhere.exception.ValidationErrorResponse;
import com.doctoranywhere.exception.ValidationException;
import com.doctoranywhere.model.Address;
import com.doctoranywhere.model.Error;
import com.doctoranywhere.model.Gender;
import com.doctoranywhere.model.Patient;

@Component
public class PatientValidator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	List<String> isoCodes = Arrays.asList("AF", "AX", "AL", "DZ", "AS", "AD", "AO", "AI", "AQ", "AG", "AR", "AM", "AW",
			"AU", "AT", "AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ", "BJ", "BM", "BT", "BO", "BQ", "BA", "BW", "BV",
			"BR", "IO", "BN", "BG", "BF", "BI", "KH", "CM", "CA", "CV", "KY", "CF", "TD", "CL", "CN", "CX", "CC", "CO",
			"KM", "CG", "CD", "CK", "CR", "CI", "HR", "CU", "CW", "CY", "CZ", "DK", "DJ", "DM", "DO", "EC", "EG", "SV",
			"GQ", "ER", "EE", "ET", "FK", "FO", "FJ", "FI", "FR", "GF", "PF", "TF", "GA", "GM", "GE", "DE", "GH", "GI",
			"GR", "GL", "GD", "GP", "GU", "GT", "GG", "GN", "GW", "GY", "HT", "HM", "VA", "HN", "HK", "HU", "IS", "IN",
			"ID", "IR", "IQ", "IE", "IM", "IL", "IT", "JM", "JP", "JE", "JO", "KZ", "KE", "KI", "KP", "KR", "KW", "KG",
			"LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MO", "MK", "MG", "MW", "MY", "MV", "ML", "MT", "MH",
			"MQ", "MR", "MU", "YT", "MX", "FM", "MD", "MC", "MN", "ME", "MS", "MA", "MZ", "MM", "NA", "NR", "NP", "NL",
			"NC", "NZ", "NI", "NE", "NG", "NU", "NF", "MP", "NO", "OM", "PK", "PW", "PS", "PA", "PG", "PY", "PE", "PH",
			"PN", "PL", "PT", "PR", "QA", "RE", "RO", "RU", "RW", "BL", "SH", "KN", "LC", "MF", "PM", "VC", "WS", "SM",
			"ST", "SA", "SN", "RS", "SC", "SL", "SG", "SX", "SK", "SI", "SB", "SO", "ZA", "GS", "SS", "ES", "LK", "SD",
			"SR", "SJ", "SZ", "SE", "CH", "SY", "TW", "TJ", "TZ", "TH", "TL", "TG", "TK", "TO", "TT", "TN", "TR", "TM",
			"TC", "TV", "UG", "UA", "AE", "GB", "US", "UM", "UY", "UZ", "VU", "VE", "VN", "VG", "VI", "WF", "EH", "YE",
			"ZM", "ZW");

	public boolean validate(Patient patient) throws ValidationException {
		List<Error> errors = new ArrayList<>();
		return validatePatientDetails(patient, errors);
	}

	public boolean validatePatientDetails(Patient patient, List<Error> errors) throws ValidationException {
		validateName("First name", patient.getFirstName(), errors, 30);
		validateName("Last name", patient.getLastName(), errors, 30);
		validateAge("Age", patient.getAge(), errors, 1, 100);
		validateGender("Gender", patient.getGender(), errors, Arrays.asList(Gender.values()));
		validateAddress(patient, errors);
		if (errors != null && errors.isEmpty()) {
			return true;
		} else {
			logger.error("Request has validation failures", errors);
			throw new ValidationException(createValidationResponse(errors));
		}
	}

	private void validateGender(String name, Gender gender, List<Error> errors, List<Gender> values) {
		if (gender == null) {
			errors.add(createNewValidationError(ValidationErrorCode.MANDATORY_FIELD, name, name + " must be provided"));
			logger.error("Mandatory Field '" + name + "' is missing");
		}
		if (!values.contains(gender)) {
			String validGender = values.stream().map(g -> g.name()).collect(Collectors.joining(","));
			errors.add(createNewValidationError(ValidationErrorCode.INVALID_VALUE, name,
					name + " value is not correct, allowed values are  " + validGender));
			logger.error("Mandatory Field '" + name + "' is not correct");
		}
	}

	/**
	 * 
	 * @param address
	 * @param errors
	 */
	public void validateAddress(Patient patient, List<Error> errors) throws ValidationException {
		if (patient != null) {
			validateAddressLine1(patient.getAddressLine1(), errors, 200);
			validateCity(patient.getCity(), errors, 100);
			validatePostalCode(patient.getPostalCode(), errors, 100);
			validateCountry(patient.getCountry(), errors, 2);

			/*
			 * Address contains optional field Address Line 2 and state which may contain
			 * any character and support a maximum length of 200.
			 */
			if (!StringUtils.isBlank(patient.getAddressLine2()))
				validateTextLength("Address Line 2", patient.getAddressLine2(), errors, 200);

			if (!StringUtils.isBlank(patient.getState()))
				validateTextLength("State", patient.getState(), errors, 100);
		}

	}

	private ValidationErrorResponse createValidationResponse(List<Error> errorList) {
		ValidationErrorResponse validationResponse = new ValidationErrorResponse();
		validationResponse.setCode(ValidationErrorCode.ERROR.getErrorCode());
		validationResponse.setMesage("Validation Exception");
		validationResponse.setErrors(errorList);
		return validationResponse;
	}

	/**
	 * Address contains mandatory field Address Line 1 which may contain any
	 * character and support a maximum length of 200.
	 * 
	 * @param addressLine1
	 * @param errors
	 * @param maxLength
	 */
	private void validateAddressLine1(String addressLine1, List<Error> errors, int maxLength) {
		validateName("Adress Line 1", addressLine1, errors, maxLength);
	}

	/**
	 * @param name
	 * @param value
	 * @param errors
	 * @param minAge
	 * @param maxAge
	 */
	private void validateAge(String name, int value, List<Error> errors, int minAge, int maxAge) {
		if (value < minAge) {
			errors.add(createNewValidationError(ValidationErrorCode.INVALID_VALUE, name,
					name + " must be of minimum " + minAge));
			logger.error("Mandatory Field '" + name + "' is not correct");
		}
	}

	/**
	 * 
	 * @param name
	 * @param value
	 * @param errors
	 * @param maxLength
	 */
	private void validateName(String name, String value, List<Error> errors, int maxLength) {

		validateMandatoryField(name, value, errors);

		if (!validateText(value))
			validateTextLength(name, value, errors, maxLength);

	}

	/**
	 * 
	 * @param name
	 * @param value
	 * @param errors
	 */
	private boolean validateMandatoryField(String name, String value, List<Error> errors) {

		if (validateText(value)) {
			errors.add(createNewValidationError(ValidationErrorCode.MANDATORY_FIELD, name, name + " must be provided"));
			logger.error("Mandatory Field '" + name + "' is missing");
			return false;
		}
		return true;

	}

	/**
	 * 
	 * @param name
	 * @param value
	 * @param errors
	 * @param maxLength
	 */
	private void validateTextLength(String name, String value, List<Error> errors, int maxLength) {

		if (value.length() > maxLength) {
			errors.add(createNewValidationError(ValidationErrorCode.INVALID_FORMAT, name,
					"Name may only contain only a maximum length of " + maxLength));
			logger.error(
					name + ": " + value + " may only contain alphabetics, including accented characters and hyphens");
		}
	}

	private boolean validateText(String data) {
		return data == null || data.trim().length() == 0;
	}

	/**
	 * 
	 * @param code
	 * @param field
	 * @param message
	 * @return
	 */
	private Error createNewValidationError(ValidationErrorCode code, String field, String message) {
		Error error = new Error();
		error.setCode(code.getErrorCode());
		error.setFields(field);
		error.setMessage(message);
		return error;
	}

	private void validateCity(String city, List<Error> errors, int maxLength) {
		validateName("City", city, errors, maxLength);
	}

	/**
	 * Address contains mandatory field ZIP/Postal Code which may contain any
	 * character and support a maximum length of 100.
	 * 
	 * @param postalCode
	 * @param errors
	 * @param maxLength
	 */
	private void validatePostalCode(String postalCode, List<Error> errors, int maxLength) {
		validateName("Postal Code", postalCode, errors, maxLength);

	}

	/**
	 * Address contains mandatory field Country which must be a valid 2-character
	 * ISO Country Code.
	 * 
	 * @param country
	 * @param errors
	 * @param maxLength
	 */
	private void validateCountry(String country, List<Error> errors, int maxLength) {
		validateName("Country", country, errors, maxLength);
		if (!isoCodes.contains(country)) {
			errors.add(createNewValidationError(ValidationErrorCode.INVALID_VALUE, "Country",
					country + " should be a valid ISO codes"));
			logger.error(country + "' should be a valid ISO codes");
		}
	}
}
