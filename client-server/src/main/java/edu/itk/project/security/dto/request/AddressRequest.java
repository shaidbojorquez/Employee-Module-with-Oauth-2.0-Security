package edu.itk.project.security.dto.request;

public class AddressRequest {
	
	private String country;
	
	private String city;
	
	private String state;
	
	private String street;
	
	private String zipCode;

	public AddressRequest(String country, String city, String state, String street, String zipCode) {
		super();
		this.country = country;
		this.city = city;
		this.state = state;
		this.street = street;
		this.zipCode = zipCode;
	}

	public AddressRequest() {
		
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
	
}
