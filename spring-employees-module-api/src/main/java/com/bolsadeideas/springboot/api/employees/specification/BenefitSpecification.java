package com.bolsadeideas.springboot.api.employees.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.bolsadeideas.springboot.api.employees.model.Benefit;
import com.bolsadeideas.springboot.api.employees.model.Benefit_;

public class BenefitSpecification implements Specification<Benefit> {
	
	private static final long serialVersionUID = 1L;
	
	private SearchCriteria criteria;

	public BenefitSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}



	@Override
	public Predicate toPredicate(Root<Benefit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if(criteria.getOperation().equals("~")) {
			return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
		}else if(criteria.getOperation().equals("=")) {
			if(criteria.getKey().equals(Benefit_.BY_LAW)) {
				return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
			}else if(criteria.getKey().equals(Benefit_.AVAILABILITY)) {
				return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
			}
		}
		return null;
	}

}
