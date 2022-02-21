package com.bolsadeideas.springboot.api.employees.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.api.employees.model.Benefit;

@Repository
public interface BenefitsRepository extends PagingAndSortingRepository<Benefit, Long>, JpaSpecificationExecutor<Benefit>{
	Optional<Benefit> findByName(String name);
}
