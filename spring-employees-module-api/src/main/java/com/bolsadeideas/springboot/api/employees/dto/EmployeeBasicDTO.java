package com.bolsadeideas.springboot.api.employees.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.bolsadeideas.springboot.api.employees.model.Address;

public class EmployeeBasicDTO {
	private Long id;

	private String name;

	private String lastName;

	private LocalDate dateOfBirth;

	private String email;

	private String telephoneNumber;
	
	private String uniqueKey;

	private Set<BenefitDTO> benefits = new HashSet<>();

	private Address address = new Address();

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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public Set<BenefitDTO> getBenefits() {
		return benefits;
	}

	public void setBenefits(Set<BenefitDTO> benefits) {
		this.benefits = benefits;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

}
