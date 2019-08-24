package com.deltaa.superrduperr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.deltaa.superrduperr.enums.ItemStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity class for Item data
 * @author rajesh
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {

	/**
	 * default value for serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	
	@Enumerated(EnumType.STRING)
	private ItemStatus status;
	
	@Column(name = "is_deleted")
	private boolean isDeleted;
	
	@ManyToOne
    @JoinColumn(name = "super_duper_id")
	@JsonBackReference
    private SuperDuper superDuper;
	
	@OneToMany(mappedBy="item", cascade = {CascadeType.ALL})
	@JsonManagedReference
	private List<Tag> tags;
	
	@OneToMany(mappedBy="item", cascade = {CascadeType.ALL}, fetch =  FetchType.LAZY)
	@JsonManagedReference
	private List<Reminder> reminders;
	
	public Item() {
		super();
		tags = new ArrayList<Tag>();
		reminders = new ArrayList<Reminder>();
	}
	
	public Long getId() {
		return id;
	}
	public Item setId(Long id) {
		this.id = id;
		return this;
	}
	
	public String getName() {
		return name;
	}
	public Item setName(String name) {
		this.name = name;
		return this;
	}
	
	public ItemStatus getStatus() {
		return status;
	}
	public Item setStatus(ItemStatus status) {
		this.status = status;
		return this;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}
	public Item setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}
	
	
	public SuperDuper getSuperDuper() {
		return superDuper;
	}
	public Item setSuperDuper(SuperDuper superDuper) {
		this.superDuper = superDuper;
		return this;
	}
	
	public List<Tag> getTags() {
		return tags;
	}
	public Item setTags(List<Tag> tags) {
		this.tags = tags;
		return this;
	}
	public Item addTag(Tag tag) {
		this.getTags().add(tag);
		return this;
	}
	
	public List<Reminder> getReminders() {
		return reminders;
	}
	public Item setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
		return this;
	}
	public Item addReminder(Reminder reminder) {
		this.getReminders().add(reminder);
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
        Item entity = (Item) o;
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