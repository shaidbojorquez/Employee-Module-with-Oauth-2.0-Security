package com.bolsadeideas.springboot.api.employees.specification;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.bolsadeideas.springboot.api.employees.model.Employee;
import com.bolsadeideas.springboot.api.employees.model.Employee_;

public class EmployeeSpecification implements Specification<Employee> {
	
	private static final long serialVersionUID = 1L;
	
	private SearchCriteria criteria;

	public EmployeeSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		if(criteria.getOperation().equals(">=")) {
			if(criteria.getKey().equals(Employee_.DATE_OF_BIRTH)) {
				return criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get(Employee_.DATE_OF_BIRTH),
						criteriaBuilder.literal(criteria.getValue()).as(LocalDate.class));
			}else if(criteria.getKey().equals(Employee_.HIRING_DATE)) {
				return criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get(Employee_.HIRING_DATE),
						criteriaBuilder.literal(criteria.getValue()).as(LocalDate.class));
			}
			return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
		}
		else if(criteria.getOperation().equals("<=")) {
			if(criteria.getKey().equals(Employee_.DATE_OF_BIRTH)) {
				return criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get(Employee_.DATE_OF_BIRTH),
						criteriaBuilder.literal(criteria.getValue()).as(LocalDate.class));
			}else if(criteria.getKey().equals(Employee_.HIRING_DATE)) {
				return criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get(Employee_.HIRING_DATE),
						criteriaBuilder.literal(criteria.getValue()).as(LocalDate.class));
			}
			return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
		}
		else if(criteria.getOperation().equals("~")) {
			return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
		}
		else if(criteria.getOperation().equals("=")) {
			if(criteria.getKey().equals(Employee_.ACTIVE)) {
				//Join<Employee, Benefit> userProd = root.join("benefits");
				//query.distinct(true);
	            return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
	            
			}else if(criteria.getKey().equals(Employee_.TYPE_OF_ELEMENT)) {
				
				return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
				
			}else if(criteria.getKey().equals(Employee_.GENDER)) {
				
				return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
				
			}else if(criteria.getKey().equals(Employee_.POSITION)) {
				
				return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
				
			}
		}
		return null;
	}

}
