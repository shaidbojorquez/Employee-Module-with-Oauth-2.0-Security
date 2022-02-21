package com.bolsadeideas.springboot.api.employees.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="employees", schema="public")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z]+\\.?", message = "Name must contain only letters")
	private String name;
	
	@Column(name="last_name")
	@NotNull(message = "Last name cannot be null")
	@NotEmpty(message = "Lastname cannot be empty")
	@Size(min = 1, max = 40, message = "Lastname must be between 1 and 40 characters")
	@Pattern(regexp = "[a-zA-Z]+\\.?", message = "Lastname must contain only letters")
	private String lastName;
	
	@Past
	@Column(name="date_of_birth")
	@NotNull(message = "Date of birth cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	
	@NotNull(message = "Email cannot be null")
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Email should be valid")
	@Size(min = 1, max = 40, message = "Email must be between 1 and 40 characters")
    private String email;
	
	@Column(name="telephone_number")
	@NotNull(message = "Telephone number cannot be null")
	@NotEmpty(message = "Telephone number cannot be empty")
	@Pattern(regexp = "^(\\d{3}[-]?){2}\\d{4}$", message = "phoneNumber must have the format ddd-ddd-dddd")
	private String telephoneNumber;
	
	@Past
	@Column(name="hiring_date")
	@NotNull(message = "Hiring date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate hiringDate;
	
	@Column(name="work_email")
	@NotEmpty(message = "Work email cannot be empty")
	@Email(message = "Email should be valid")
	@Size(min = 1, max = 40, message = "Work email must be between 1 and 40 characters")
    private String workEmail;
	
	@Column(name="active")
	@NotNull(message = "Active cannot be null")
	private boolean active;
	
	@NotNull(message = "Position cannot be null")
	@Enumerated(EnumType.STRING)
	private Position position;
	
	@NotNull(message = "Gender cannot be null")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name="type_of_element")
	@NotNull(message = "Type of element cannot be null")
	@Enumerated(EnumType.STRING)
	private TypeOfElement typeOfElement;
	
	@Column(name="unique_key")
	@NotNull(message = "Unique key cannot be null")
	@NotEmpty(message = "Unique key cannot be empty")
	@Size(min = 1, max = 18, message = "Unique key must be between 1 and 18 characters")
	@Pattern(regexp = "[A-Za-z0-9]+$", message = "Unique key must contain only letters and numbers")
	private String uniqueKey;
	
	@Valid
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="employee_Benefits", joinColumns = {@JoinColumn(name="employee_id")},
	inverseJoinColumns = {@JoinColumn(name = "benefit_id")})
	private Set<Benefit> benefits = new HashSet<>();
	
	@Valid
	@NotNull(message = "Address cannot be null")
	@OneToOne(optional = false,fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "address_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Address address;
	
	public Employee() {
		
	}

	public Employee(Long id,
			@NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty") @Size(min = 1, max = 40, message = "Name must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z]+\\.?", message = "Name must contain only letters") String name,
			@NotNull(message = "Last name cannot be null") @NotEmpty(message = "Lastname cannot be empty") @Size(min = 1, max = 40, message = "Lastname must be between 1 and 40 characters") @Pattern(regexp = "[a-zA-Z]+\\.?", message = "Lastname must contain only letters") String lastName,
			@Past @NotNull(message = "Date of birth cannot be null") LocalDate dateOfBirth,
			@NotNull(message = "Email cannot be null") @NotEmpty(message = "Email cannot be empty") @Email(message = "Email should be valid") @Size(min = 1, max = 40, message = "Email must be between 1 and 40 characters") String email,
			@NotNull(message = "Telephone number cannot be null") @NotEmpty(message = "Telephone number cannot be empty") @Pattern(regexp = "^(\\d{3}[-]?){2}\\d{4}$", message = "phoneNumber must have the format ddd-ddd-dddd") String telephoneNumber,
			@Past @NotNull(message = "Hiring date cannot be null") LocalDate hiringDate,
			@NotEmpty(message = "Work email cannot be empty") @Email(message = "Email should be valid") @Size(min = 1, max = 40, message = "Work email must be between 1 and 40 characters") String workEmail,
			@NotNull(message = "Active cannot be null") boolean active,
			@NotNull(message = "Position cannot be null") Position position,
			@NotNull(message = "Gender cannot be null") Gender gender,
			@NotNull(message = "Type of element cannot be null") TypeOfElement typeOfElement,
			@NotNull(message = "Unique key cannot be null") @NotEmpty(message = "Unique key cannot be empty") @Size(min = 1, max = 18, message = "Unique key must be between 1 and 18 characters") @Pattern(regexp = "[A-Za-z0-9]+$", message = "Unique key must contain only letters and numbers") String uniqueKey,
			@Valid Set<Benefit> benefits, @Valid @NotNull(message = "Address cannot be null") Address address) {
		super();
		this.id = id;
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
		this.address = address;
	}



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
	
	public Set<Benefit> getBenefits() {
		return benefits;
	}

	public void setBenefits(Set<Benefit> benefits) {
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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", lastName=" + lastName + "]";
	}
	
	
}
