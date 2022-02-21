package edu.itk.project.security.dto.request;

public class BenefitRequest {
	
	private String name;
	
	private String description;
	
	private Boolean availability;
	
	private Boolean byLaw;

	public BenefitRequest(String name, String description, Boolean availability, Boolean byLaw) {
		super();
		this.name = name;
		this.description = description;
		this.availability = availability;
		this.byLaw = byLaw;
	}

	public BenefitRequest() {
	
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
