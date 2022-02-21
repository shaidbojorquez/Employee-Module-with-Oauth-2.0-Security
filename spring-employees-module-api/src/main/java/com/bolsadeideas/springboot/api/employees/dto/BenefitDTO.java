package com.bolsadeideas.springboot.api.employees.dto;

public class BenefitDTO {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private boolean availability;
	
	private boolean byLaw;

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
	
	
}
