package com.bolsadeideas.springboot.api.employees.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users", schema = "public")
public class EmployeeUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;

	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name may not be empty")
	@Column(name="username", unique = true)
	@Size(min = 1, max = 45, message = "Name must be between 1 and 45 characters")
	private String username;

	@NotEmpty
	@Column(length = 60)
	private String password;

	@Column(name = "enabled")
	@NotNull(message = "Enabled cannot be null")
	private Boolean enabled;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "users_authorities", joinColumns = { @JoinColumn(name = "user_id") }, 
	inverseJoinColumns = {@JoinColumn(name = "authority_id") })
	@JsonIgnoreProperties
	private Set<Role> roles;

	@OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Employee employee;

	public EmployeeUser() {
		super();
	}

	public EmployeeUser(Long id,
			@NotNull(message = "Name cannot be null") @NotEmpty(message = "Name may not be empty") @Size(min = 1, max = 45, message = "Name must be between 1 and 45 characters") String username,
			@NotEmpty String password, @NotNull(message = "Enabled cannot be null") Boolean enabled, Set<Role> roles, Employee employee) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
