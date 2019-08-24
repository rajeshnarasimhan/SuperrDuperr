package com.deltaa.superrduperr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity class for Item data
 * @author rajesh
 */
@Entity
@Table(name = "super_duper")
public class SuperDuper implements Serializable {

	/**
	 * default value for serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	
	@OneToMany(mappedBy="superDuper", cascade = {CascadeType.ALL}, fetch =  FetchType.LAZY)
	@JsonManagedReference
	private List<Item> items;
	
	public SuperDuper() {
		super();
		items = new ArrayList<Item>();
	}
	
	public Long getId() {
		return id;
	}
	public SuperDuper setId(Long id) {
		this.id = id;
		return this;
	}
	
	public String getName() {
		return name;
	}
	public SuperDuper setName(String name) {
		this.name = name;
		return this;
	}
	
	public List<Item> getItems() {
		return items;
	}
	public SuperDuper setItems(List<Item> items) {
		this.items = items;
		return this;
	}
	public SuperDuper addItem(Item item) {
		this.getItems().add(item);
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
        SuperDuper entity = (SuperDuper) o;
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