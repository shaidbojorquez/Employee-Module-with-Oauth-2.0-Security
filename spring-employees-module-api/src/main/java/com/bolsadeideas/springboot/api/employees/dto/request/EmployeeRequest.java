package com.bolsadeideas.springboot.api.employees.dto.request;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.bolsadeideas.springboot.api.employees.enums.Gender;
import com.bolsadeideas.springboot.api.employees.enums.Position;
import com.bolsadeideas.springboot.api.employees.enums.TypeOfElement;

public class EmployeeRequest {
	
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z]+\\.?", message = "Name must contain only letters")
	private String name;
	
	@NotNull(message = "last name cannot be null")
	@NotEmpty(message = "Lastname cannot  be empty")
	@Size(min = 1, max = 40, message = "Last name must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z]+\\.?", message = "Lastname must contain only letters")
	private String lastName;
	
	@Past
	@NotNull(message = "Date of birth cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	
	@NotNull(message = "Email cannot be null")
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Email should be valid")
    private String email;
	
	@NotEmpty(message = "Telephone number cannot be empty")
	@NotNull(message = "Telephone number cannot be null")
	@Pattern(regexp = "^(\\d{3}[-]?){2}\\d{4}$", message = "phoneNumber must have the format ddd-ddd-dddd")
	private String telephoneNumber;
	
	@Past
	@NotNull(message = "Hiring date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate hiringDate;
	
	@NotEmpty(message = "Work email cannot be empty")
	@Email(message = "Email should be valid")
    private String workEmail;
	
	@NotNull(message = "Active cannot be null")
	//@Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
	private Boolean active;
	
	@NotNull(message = "Position cannot be null")
	@Enumerated(EnumType.STRING)
	private Position position;
	
	@NotNull(message = "Gender cannot be null")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@NotNull(message = "Type of element cannot be null")
	@Enumerated(EnumType.STRING)
	private TypeOfElement typeOfElement;
	
	@NotNull(message = "Unique key cannot be null")
	@NotEmpty(message = "Unique key cannot be empty")
	@Size(min = 1, max = 18, message = "Unique key must be between 1 and 18 characters")
	@Pattern(regexp = "[A-Za-z0-9]+$", message = "Unique key must contain only letters and numbers")
	private String uniqueKey;
	
	private Set<Long> benefits;
	
	@Valid
	@NotNull(message = "Address cannot be null")
	private AddressRequest addressRequest;
	
	public EmployeeRequest() {
		
	}

	public EmployeeRequest(
			@NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty") @Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z]+\\.?", message = "Name must contain only letters") String name,
			@NotNull(message = "last name cannot be null") @NotEmpty(message = "Lastname cannot  be empty") @Size(min = 1, max = 40, message = "Last name must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z]+\\.?", message = "Lastname must contain only letters") String lastName,
			@Past @NotNull(message = "Date of birth cannot be null") LocalDate dateOfBirth,
			@NotNull(message = "Email cannot be null") @NotEmpty(message = "Email cannot be empty") @Email(message = "Email should be valid") String email,
			@NotEmpty(message = "Telephone number cannot be empty") @NotNull(message = "Telephone number cannot be null") @Pattern(regexp = "^(\\d{3}[-]?){2}\\d{4}$", message = "phoneNumber must have the format ddd-ddd-dddd") String telephoneNumber,
			@Past @NotNull(message = "Hiring date cannot be null") LocalDate hiringDate,
			@NotEmpty(message = "Work email cannot be empty") @Email(message = "Email should be valid") String workEmail,
			@NotNull(message = "Active cannot be null") Boolean active,
			@NotNull(message = "Position cannot be null") Position position,
			@NotNull(message = "Gender cannot be null") Gender gender,
			@NotNull(message = "Type of element cannot be null") TypeOfElement typeOfElement,
			@NotNull(message = "Unique key cannot be null") @NotEmpty(message = "Unique key cannot be empty") @Size(min = 1, max = 18, message = "Unique key must be between 1 and 18 characters") @Pattern(regexp = "[A-Za-z0-9]+$", message = "Unique key must contain only letters and numbers") String uniqueKey,
			Set<Long> benefits, @Valid @NotNull(message = "Address cannot be null") AddressRequest addressRequest) {
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

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	@Override
	public String toString() {
		return "EmployeeRequest [name=" + name + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
				+ ", email=" + email + ", telephoneNumber=" + telephoneNumber + ", hiring_date=" + hiringDate
				+ ", workEmail=" + workEmail + ", active=" + active + ", position=" + position + ", gender=" + gender
				+ ", typeOfElement=" + typeOfElement + "]";
	}
	
	
}
