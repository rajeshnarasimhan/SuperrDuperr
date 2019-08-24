package com.deltaa.superrduperr.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity class for Tag data
 * @author rajesh
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

	/**
	 * default value for serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "item_id")
	@JsonBackReference
    private Item item;
	
	@NotBlank
	private String tagDesc;

	public Long getId() {
		return id;
	}
	public Tag setId(Long id) {
		this.id = id;
		return this;
	}
	
	public Item getItem() {
		return item;
	}
	public Tag setItem(Item item) {
		this.item = item;
		return this;
	}
	
	public String getTagDesc() {
		return tagDesc;
	}
	public Tag setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
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
        Tag entity = (Tag) o;
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