package com.deltaa.superrduperr.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity class for Reminder data
 * @author rajesh
 */
@Entity
@Table(name = "reminder")
public class Reminder implements Serializable {

	/**
	 * default value for serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
	@JsonBackReference
    private Item item;
	
	@NotBlank
	private String desc;
	
	@NotNull
	private LocalDateTime datetime;
	
	public Long getId() {
		return id;
	}
	public Reminder setId(Long id) {
		this.id = id;
		return this;
	}
	
	public Item getItem() {
		return item;
	}
	public Reminder setItem(Item item) {
		this.item = item;
		return this;
	}
	
	public String getDesc() {
		return desc;
	}
	public Reminder setDesc(String desc) {
		this.desc = desc;
		return this;
	}
	
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public Reminder setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
		return this;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hashCode(getId());
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reminder entity = (Reminder) o;
        if (entity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entity.getId());
    }
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}