package com.bolsadeideas.springboot.api.employees.dto.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddressPatchRequest {
	
	private Long id;
	
	@Size(min = 1, max = 40, message = "Country must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters")
	private String country;
	
	@Size(min = 1, max = 40, message = "City must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters")
	private String city;
	
	@Size(min = 1, max = 40, message = "State must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters")
	private String state;
	
	@Size(min = 1, max = 90, message = "Address must be between 1 and 90 characters")
	private String street;
	
	@Size(min = 1, max = 5, message = "Zip code must be between 1 and 5 characters")
	private String zipCode;

	public AddressPatchRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressPatchRequest(
			@Size(min = 1, max = 40, message = "Country must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters") String country,
			@Size(min = 1, max = 40, message = "City must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters") String city,
			@Size(min = 1, max = 40, message = "State must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters") String state,
			@Size(min = 1, max = 90, message = "Address must be between 1 and 90 characters") String street,
			@Size(min = 1, max = 5, message = "Zip code must be between 1 and 5 characters") String zipCode) {
		super();
		this.country = country;
		this.city = city;
		this.state = state;
		this.street = street;
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
