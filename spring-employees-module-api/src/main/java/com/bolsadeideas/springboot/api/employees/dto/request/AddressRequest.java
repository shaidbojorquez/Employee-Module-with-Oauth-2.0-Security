package com.bolsadeideas.springboot.api.employees.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddressRequest {
	
	@NotNull(message = "Country cannot be null")
	@NotEmpty(message = "Country may not be empty")
	@Size(min = 1, max = 40, message = "Country must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters")
	private String country;
	
	@NotNull(message = "City cannot be null")
	@NotEmpty(message = "City may not be empty")
	@Size(min = 1, max = 40, message = "City must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "City name must contain only letters")
	private String city;
	
	@NotNull(message = "State cannot be null")
	@NotEmpty(message = "State may not be empty")
	@Size(min = 1, max = 40, message = "State must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "State name must contain only letters")
	private String state;
	
	@NotNull(message = "Street cannot be null")
	@NotEmpty(message = "Street may not be empty")
	@Size(min = 1, max = 90, message = "Street must be between 1 and 90 characters")
	private String street;
	
	@NotNull(message = "Zip code cannot be null")
	@NotEmpty(message = "Zip code may not be empty")
	@Size(min = 1, max = 5, message = "Zip code must be between 1 and 5 characters")
	private String zipCode;

	public AddressRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AddressRequest(
			@NotNull(message = "Country cannot be null") @NotEmpty(message = "Country may not be empty") @Size(min = 1, max = 40, message = "Country must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters") String country,
			@NotNull @NotEmpty @Size(min = 1, max = 40, message = "City must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "City name must contain only letters") String city,
			@NotNull @NotEmpty @Size(min = 1, max = 40, message = "State must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "State name must contain only letters") String state,
			@NotNull(message = "Street cannot be null") @NotEmpty(message = "Street may not be empty") @Size(min = 1, max = 90, message = "Street must be between 1 and 90 characters") String street,
			@NotNull(message = "Zip code cannot be null") @NotEmpty(message = "Zip code may not be empty") @Size(min = 1, max = 5, message = "Zip code must be between 1 and 5 characters") String zipCode) {
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
	
}
