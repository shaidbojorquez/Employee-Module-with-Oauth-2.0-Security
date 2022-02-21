package com.bolsadeideas.springboot.api.employees.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="benefits", schema="public")
public class Benefit implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name may not be empty")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Name must contain only letters")
	@Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters")
	private String name;
	
	@NotNull(message = "Description cannot be null")
	@NotEmpty(message = "Description cannot be empty")
	@Size(min = 1, max = 40,  message = "Description must be between 1 and 40 characters")
	private String description;
	
	@NotNull(message = "availability cannot be null")
	private boolean availability;
	
	@Column(name="by_law")
	@NotNull(message = "By law cannot be null")
	private boolean byLaw;
	
	public Benefit() {
		
	}

	public Benefit(Long id, @NotNull @NotEmpty @Size(min = 1, max = 40) String name,
			@NotNull @NotEmpty @Size(min = 1, max = 40) String description, @NotNull boolean availability,
			@NotNull boolean byLaw) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.availability = availability;
		this.byLaw = byLaw;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public boolean isByLaw() {
		return byLaw;
	}

	public void setByLaw(boolean byLaw) {
		this.byLaw = byLaw;
	}

	@Override
	public String toString() {
		return "Benefit [id=" + id + ", name=" + name + ", description=" + description + ", availability="
				+ availability + ", byLaw=" + byLaw + "]";
	}
	
	
	
}
