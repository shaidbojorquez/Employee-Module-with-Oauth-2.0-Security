package com.bolsadeideas.springboot.api.employees.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.api.employees.model.EmployeeUser;

@Repository
public interface EmployeeUsersRepository extends PagingAndSortingRepository<EmployeeUser, Long>, JpaSpecificationExecutor<EmployeeUser>{
	Optional<EmployeeUser> findByUsername(String username);
}
