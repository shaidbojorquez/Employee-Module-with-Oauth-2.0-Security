package com.bolsadeideas.springboot.api.employees.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.api.employees.dto.BenefitDTO;
import com.bolsadeideas.springboot.api.employees.dto.PagedResult;
import com.bolsadeideas.springboot.api.employees.dto.request.BenefitRequest;
import com.bolsadeideas.springboot.api.employees.dto.request.BenefitPatchRequest;

public interface BenefitsService {
	
	//List<Benefit> findAll();
	
	PagedResult<BenefitDTO> findAllBenefits(String search, Boolean byLaw, Boolean availability, Pageable pageable);
	
	public BenefitDTO createBenefit(BenefitRequest benefitRequest);
	
	public List<BenefitDTO> findUserBenefits(String username);
	
	public BenefitDTO findBenefitById(Long id);
	
	public BenefitDTO editBenefit(Long id, BenefitRequest benefitRequest);
	
	public BenefitDTO patchBenefit(Long id, BenefitPatchRequest benefitRequestPatch);
	
	public String deleteBenefitById(Long id);
}
