package com.deltaa.superrduperr.bean.request;

import com.deltaa.superrduperr.validator.NumberFormatValidator;

public class IdBean {
	
	@NumberFormatValidator(message = "Invalid id value/format")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}