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
@Table(name="addresses", schema="public")
public class Address implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 40, message = "Country must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters")
	private String country;
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 40, message = "City must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters")
	private String city;
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 40, message = "State must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters")
	private String state;
	
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 90, message = "Address must be between 1 and 90 characters")
	private String street;
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 5, message = "Zip code must be between 1 and 5 characters")
	@Column(name="zip_code")
	private String zipCode;
	
	
	
	public Address() {
		
	}

	public Address(Long id,
			@NotNull @NotEmpty @Size(min = 1, max = 40, message = "Country must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters") String country,
			@NotNull @NotEmpty @Size(min = 1, max = 40, message = "City must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters") String city,
			@NotNull @NotEmpty @Size(min = 1, max = 40, message = "State must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z ]+\\.?", message = "Country name must contain only letters") String state,
			@NotNull @NotEmpty @Size(min = 1, max = 90, message = "Address must be between 1 and 90 characters") String street,
			@NotNull @NotEmpty @Size(min = 1, max = 5, message = "Zip code must be between 1 and 5 characters") String zipCode) {
		super();
		this.id = id;
		this.country = country;
		this.city = city;
		this.state = state;
		this.street = street;
		this.zipCode = zipCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Address [id=" + id + ", country=" + country + ", street=" + street + ", zipCode=" + zipCode + "]";
	}
	
}
