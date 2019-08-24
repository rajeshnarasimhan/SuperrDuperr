package com.deltaa.superrduperr.bean.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TagBean implements Serializable {

	/**
	 * default value for serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Tag Desc cannot be blank")
	private String tagDesc;
	
	@NotNull(message = "Item id cannot be null")
	private Long itemId;
	
	public String getTagDesc() {
		return tagDesc;
	}
	public TagBean setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
		return this;
	}
	
	public Long getItemId() {
		return itemId;
	}
	public TagBean setItemId(Long itemId) {
		this.itemId = itemId;
		return this;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
