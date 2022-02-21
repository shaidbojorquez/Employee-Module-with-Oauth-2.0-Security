package com.bolsadeideas.springboot.api.employees.service;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.api.employees.dto.EmployeeBasicDTO;
import com.bolsadeideas.springboot.api.employees.dto.EmployeeDTO;
import com.bolsadeideas.springboot.api.employees.dto.PagedResult;
import com.bolsadeideas.springboot.api.employees.dto.request.EmployeePatchRequest;
import com.bolsadeideas.springboot.api.employees.dto.request.EmployeeRequest;
import com.bolsadeideas.springboot.api.employees.enums.Gender;
import com.bolsadeideas.springboot.api.employees.enums.Position;
import com.bolsadeideas.springboot.api.employees.enums.TypeOfElement;

public interface EmployeesService {

	// List<Employee> findAll();

	PagedResult<EmployeeDTO> findAllEmployees(String search, Boolean active, LocalDate fromBirthDate,
			LocalDate toBirthDate, LocalDate fromHiringDate, LocalDate toHiringDate, TypeOfElement typeOfElement,
			Gender gender, Position position, Pageable pageable);

	PagedResult<EmployeeBasicDTO> findAllEmployeesBasicInformation(String search, LocalDate fromBirthDate,
			LocalDate toBirthDate, Pageable pageable);

	public EmployeeDTO createEmployee(EmployeeRequest employeeRequest);

	public EmployeeDTO findUserInfo(String username);

	public EmployeeDTO findEmployeeById(Long id);
	
	public EmployeeBasicDTO findEmployeeBasicInfo(Long id);

	public EmployeeDTO editEmployee(Long id, EmployeeRequest employeeRequest);
	
	public EmployeeDTO patchEmployee(Long id, EmployeePatchRequest employeePatchRequest);
	
	public String addBenefitToAllEmployees(String benefitName);
	
	public String removeBenefitFromAllEmployees(String benefitName);

	public String deleteEmployeeById(Long id);

}
