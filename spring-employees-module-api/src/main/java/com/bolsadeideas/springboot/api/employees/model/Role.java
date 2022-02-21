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
import javax.validation.constraints.Size;

@Entity
@Table(name="authorities", schema="public")
public class Role implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="authority_id")
	private Long id;
	
	@NotNull(message = "Authority cannot be null")
	@NotEmpty(message = "Authority may not be empty")
	@Size(min = 1, max = 40, message = "Authority must be between 1 and 45 characters")
	private String authority;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(Long id,
			@NotNull(message = "Authority cannot be null") @NotEmpty(message = "Authority may not be empty") @Size(min = 1, max = 40, message = "Authority must be between 1 and 45 characters") String authority) {
		super();
		this.id = id;
		this.authority = authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
