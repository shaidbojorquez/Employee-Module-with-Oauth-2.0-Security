package com.bolsadeideas.springboot.api.employees.repository.impl;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.api.employees.repository.EmployeesBenefitsRepository;

@Repository
public class EmployeesBenefitsRepositoryImpl implements EmployeesBenefitsRepository{
	private NamedParameterJdbcTemplate namedTemplate;
	
	public EmployeesBenefitsRepositoryImpl(@Autowired DataSource dataSource) {
		this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	
	
	@Override
	public void deleteBenefitsAuthorsById(long id) {
		String query = "DELETE FROM employee_benefits WHERE benefit_id = :id";
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", id);
		namedTemplate.update(query, params);
	}

}
