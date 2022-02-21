package com.bolsadeideas.springboot.api.employees.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.bolsadeideas.springboot.api.employees.enums.Gender;
import com.bolsadeideas.springboot.api.employees.enums.Position;
import com.bolsadeideas.springboot.api.employees.enums.TypeOfElement;


public class EmployeeDTO {
	
	private Long id;
	
	private String name;
	
	private String lastName;
	
	private LocalDate dateOfBirth;
	
    private String email;
	
	private String telephoneNumber;
	
	private LocalDate hiringDate;
	
    private String workEmail;
	
	private boolean active;
	
	private Position position;
	
	private Gender gender;
	
	private TypeOfElement typeOfElement;
	
	private String uniqueKey;

	private Set<BenefitDTO> benefits = new HashSet<>();

	private AddressDTO addressDTO = new AddressDTO();
	
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

	public LocalDate getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(LocalDate hiringDate) {
		this.hiringDate = hiringDate;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public TypeOfElement getTypeOfElement() {
		return typeOfElement;
	}

	public void setTypeOfElement(TypeOfElement typeOfElement) {
		this.typeOfElement = typeOfElement;
	}

	public Set<BenefitDTO> getBenefits() {
		return benefits;
	}

	public void setBenefits(Set<BenefitDTO> benefits) {
		this.benefits = benefits;
	}

	public AddressDTO getAddress() {
		return addressDTO;
	}

	public void setAddress(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	
}
