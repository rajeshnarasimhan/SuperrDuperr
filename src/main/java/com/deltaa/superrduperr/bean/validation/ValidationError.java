package com.deltaa.superrduperr.bean.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
	
	private List<FieldError> fieldErrors;
	 
    public ValidationError() {
    	fieldErrors = new ArrayList<FieldError>();
    }

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
	
	public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }
}
