package com.deltaa.superrduperr.bean.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SuperDuperBean implements Serializable {

	/**
	 * default value for serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "SuperDuper id cannot be null")
	private Long superDuperId;
	
	@NotEmpty(message = "SuperDuper id cannot be empty")
	private List<@Valid ItemBean> items;

	public Long getSuperDuperId() {
		return superDuperId;
	}

	public void setSuperDuperId(Long superDuperId) {
		this.superDuperId = superDuperId;
	}

	public List<ItemBean> getItems() {
		return items;
	}

	public void setItems(List<ItemBean> items) {
		this.items = items;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
