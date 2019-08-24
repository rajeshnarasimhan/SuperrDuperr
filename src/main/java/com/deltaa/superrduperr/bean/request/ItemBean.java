package com.deltaa.superrduperr.bean.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ItemBean implements Serializable {

	/**
	 * default value for serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Item name cannot be blank")
	private String name;
	
	@NotBlank(message = "Item status cannot be blank")
	private String status;
	
	public String getName() {
		return name;
	}
	public ItemBean setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getStatus() {
		return status;
	}
	public ItemBean setStatus(String status) {
		this.status = status;
		return this;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}