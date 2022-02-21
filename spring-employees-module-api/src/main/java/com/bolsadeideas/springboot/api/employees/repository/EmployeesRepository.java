package com.bolsadeideas.springboot.api.employees.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.api.employees.model.Employee;

@Repository
public interface EmployeesRepository extends PagingAndSortingRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{
	Optional<Employee> findByUniqueKey(String uniqueKey);
	Optional<Employee> findByWorkEmail(String workEmail);
}
