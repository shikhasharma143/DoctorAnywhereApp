package com.doctoranywhere.exception;

import java.util.List;

import lombok.Data;
import com.doctoranywhere.model.Error;
@Data
public class ValidationErrorResponse {
private Integer code;
private String mesage;
private List<Error> errors;
}
