package edu.itk.project.security.dto.request;

import java.time.LocalDate;
import java.util.Set;

import edu.itk.project.security.enums.*;

public class EmployeeRequest {
	
	private String name;
	
	private String lastName;
	
	private LocalDate dateOfBirth;
	
    private String email;
	
	private String telephoneNumber;
	
	private LocalDate hiringDate;

    private String workEmail;
	
	private Boolean active;
	
	private Position position;
	
	private Gender gender;
	
	private TypeOfElement typeOfElement;
	
	private String uniqueKey;
	
	private Set<Long> benefits;
	
	private AddressRequest addressRequest;

	public EmployeeRequest(String name, String lastName, LocalDate dateOfBirth, String email, String telephoneNumber,
			LocalDate hiringDate, String workEmail, Boolean active, Position position, Gender gender,
			TypeOfElement typeOfElement, String uniqueKey, Set<Long> benefits, AddressRequest addressRequest) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.telephoneNumber = telephoneNumber;
		this.hiringDate = hiringDate;
		this.workEmail = workEmail;
		this.active = active;
		this.position = position;
		this.gender = gender;
		this.typeOfElement = typeOfElement;
		this.uniqueKey = uniqueKey;
		this.benefits = benefits;
		this.addressRequest = addressRequest;
	}

	public EmployeeRequest() {
		
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

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
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

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Set<Long> getBenefits() {
		return benefits;
	}

	public void setBenefits(Set<Long> benefits) {
		this.benefits = benefits;
	}

	public AddressRequest getAddressRequest() {
		return addressRequest;
	}

	public void setAddressRequest(AddressRequest addressRequest) {
		this.addressRequest = addressRequest;
	}
	
}
