package com.deltaa.superrduperr.bean.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ReminderBean implements Serializable {

	/**
	 * default value for serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Reminder Desc cannot be blank")
	private String desc;
	
	@NotNull(message = "Reminder Date cannot be null")
	private LocalDateTime datetime;
	
	@NotNull(message = "Item id cannot be null")
	private Long itemId;
	
	public String getDesc() {
		return desc;
	}
	public ReminderBean setDesc(String desc) {
		this.desc = desc;
		return this;
	}
	
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public ReminderBean setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
		return this;
	}
	
	public Long getItemId() {
		return itemId;
	}
	public ReminderBean setItemId(Long itemId) {
		this.itemId = itemId;
		return this;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
