package com.bolsadeideas.springboot.api.employees.dto.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BenefitPatchRequest {

	@Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Name must contain only letters")
	private String name;

	@Size(min = 1, max = 40,  message = "Description must be between 1 and 40 characters")
	private String description;

	//@Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
	private Boolean availability;

	//@Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
	private Boolean byLaw;

	public BenefitPatchRequest() {
		super();
		
	}

	public BenefitPatchRequest(
			@Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z]+\\.?", message = "firstName must contain only letters") String name,
			@Size(min = 1, max = 40, message = "Description must be between 1 and 40 characters") String description,
			Boolean availability, Boolean byLaw) {
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

}
