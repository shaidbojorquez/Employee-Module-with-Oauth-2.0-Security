package com.bolsadeideas.springboot.api.employees.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BenefitRequest {
	
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name may not be empty")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Name must contain only letters")
	@Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters")
	private String name;
	
	@NotNull(message = "Description cannot be null")
	@NotEmpty(message = "Description may not be empty")
	@Size(min = 1, max = 40,  message = "Description must be between 1 and 40 characters")
	private String description;
	
	@NotNull(message = "Availability cannot be null")
	private Boolean availability;
	
	@NotNull(message = "By law cannot be null")
	private Boolean byLaw;
	
	public BenefitRequest() {
		
	}

	public BenefitRequest(
			@NotNull(message = "Name cannot be null") @NotEmpty(message = "Name may not be empty") @Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters") String name,
			@NotNull(message = "Description cannot be null") @NotEmpty(message = "Description may not be empty") @Size(min = 1, max = 40, message = "Description must be between 1 and 40 characters") String description,
			@NotNull(message = "Availability cannot be null") @NotEmpty(message = "Availability may not be empty") Boolean availability,
			@NotNull(message = "By law cannot be null") @NotEmpty(message = "By law may not be empty") Boolean byLaw) {
		super();
		this.name = name;
		this.description = description;
		this.availability = availability;
		this.byLaw = byLaw;
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

	public Boolean isAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Boolean isByLaw() {
		return byLaw;
	}

	public void setByLaw(Boolean byLaw) {
		this.byLaw = byLaw;
	}

	@Override
	public String toString() {
		return "BenefitRequest [name=" + name + ", description=" + description + ", availability=" + availability
				+ ", byLaw=" + byLaw + "]";
	}
	
	
}
