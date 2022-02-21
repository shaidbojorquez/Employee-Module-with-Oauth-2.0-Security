package com.bolsadeideas.springboot.api.employees.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bolsadeideas.springboot.api.employees.dto.BenefitDTO;
import com.bolsadeideas.springboot.api.employees.dto.PagedResult;
import com.bolsadeideas.springboot.api.employees.dto.request.BenefitRequest;
import com.bolsadeideas.springboot.api.employees.dto.request.BenefitPatchRequest;
import com.bolsadeideas.springboot.api.employees.exception.DuplicatedElementException;
import com.bolsadeideas.springboot.api.employees.exception.NotFoundException;
import com.bolsadeideas.springboot.api.employees.model.Benefit;
import com.bolsadeideas.springboot.api.employees.model.Benefit_;
import com.bolsadeideas.springboot.api.employees.model.Employee;
import com.bolsadeideas.springboot.api.employees.repository.BenefitsRepository;
import com.bolsadeideas.springboot.api.employees.repository.EmployeesRepository;
import com.bolsadeideas.springboot.api.employees.service.BenefitsService;
import com.bolsadeideas.springboot.api.employees.specification.BenefitSpecification;
import com.bolsadeideas.springboot.api.employees.specification.SearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BenefitsServiceImpl implements BenefitsService {

	@Autowired
	private BenefitsRepository benefitsRepository;

	@Autowired
	private EmployeesRepository employeesRepository;

	@Override
	@Transactional(readOnly = true)
	public PagedResult<BenefitDTO> findAllBenefits(String search, Boolean byLaw, Boolean availability,
			Pageable pageable) {
		Specification<Benefit> benefitSpecifiction = Specification.where(null);

		if (search != null) {
			Specification<Benefit> nameSpecification = Specification
					.where(new BenefitSpecification(new SearchCriteria(Benefit_.NAME, "~", search)));
			benefitSpecifiction = benefitSpecifiction.or(nameSpecification);
		}

		if (byLaw != null) {
			Specification<Benefit> byLawSpecification = Specification
					.where(new BenefitSpecification(new SearchCriteria(Benefit_.BY_LAW, "=", byLaw)));
			benefitSpecifiction = benefitSpecifiction.or(byLawSpecification);
		}

		if (availability != null) {
			Specification<Benefit> availabilitySpecification = Specification
					.where(new BenefitSpecification(new SearchCriteria(Benefit_.AVAILABILITY, "=", availability)));
			benefitSpecifiction = benefitSpecifiction.or(availabilitySpecification);
		}

		Page<Benefit> pageBenefits = benefitsRepository.findAll(benefitSpecifiction, pageable);
		List<BenefitDTO> listBenefits = pageBenefits.getContent().stream().map(this::convertBenefitToDTO)
				.collect(Collectors.toList());
		PagedResult<BenefitDTO> benefitsDTO = new PagedResult<>();
		benefitsDTO.setData(listBenefits);
		benefitsDTO.setPageNumber(pageBenefits.getNumber());
		benefitsDTO.setPageSize(pageBenefits.getSize());
		benefitsDTO.setTotalElements(pageBenefits.getTotalElements());
		benefitsDTO.setTotalPages(pageBenefits.getTotalPages());

		return benefitsDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BenefitDTO> findUserBenefits(String username) {

		Optional<Employee> optEmployee = employeesRepository.findByWorkEmail(username);
		if (optEmployee.isPresent()) {
			Employee employee = optEmployee.get();
			List<BenefitDTO> listBenefitsDTO = employee.getBenefits().stream().map(this::convertBenefitToDTO)
					.collect(Collectors.toList());
			return listBenefitsDTO;
		}
		throw new NotFoundException("employee with username= " + username);

	}

	@Override
	@Transactional
	public BenefitDTO createBenefit(BenefitRequest benefitRequest) {
		Optional<Benefit> optExistingBenefit = benefitsRepository
				.findByName(StringUtils.capitalize(benefitRequest.getName().toLowerCase().trim()));
		if (!optExistingBenefit.isPresent()) {
			Benefit benefit = new Benefit();
			benefit.setName(StringUtils.capitalize(benefitRequest.getName().toLowerCase().trim()));
			benefit.setDescription(benefitRequest.getDescription());
			benefit.setAvailability(benefitRequest.isAvailability());
			benefit.setByLaw(benefitRequest.isByLaw());
			benefit = benefitsRepository.save(benefit);
			return convertBenefitToDTO(benefit);
		}
		throw new DuplicatedElementException(benefitRequest.getName());
	}

	@Override
	@Transactional(readOnly = true)
	public BenefitDTO findBenefitById(Long id) {
		Optional<Benefit> optBenefit = benefitsRepository.findById(id);
		if (optBenefit.isPresent()) {
			Benefit benefit = optBenefit.get();
			return convertBenefitToDTO(benefit);
		}
		throw new NotFoundException("benefit");
	}

	@Override
	@Transactional
	public BenefitDTO editBenefit(Long id, BenefitRequest benefitRequest) {
		/*
		 * Optional<Benefit> optExistingBenefit = benefitsRepository
		 * .findByName(StringUtils.capitalize(benefitRequest.getName().toLowerCase().
		 * trim())); if (!optExistingBenefit.isPresent()) {
		 */
		Optional<Benefit> optBenefit = benefitsRepository.findById(id);
		if (optBenefit.isPresent()) {
			Benefit benefit = optBenefit.get();
			benefit.setName(StringUtils.capitalize(benefitRequest.getName().toLowerCase().trim()));
			benefit.setDescription(benefitRequest.getDescription());
			benefit.setAvailability(benefitRequest.isAvailability());
			benefit.setByLaw(benefitRequest.isByLaw());
			benefit = benefitsRepository.save(benefit);
			return convertBenefitToDTO(benefit);
		}
		throw new NotFoundException("benefit");
		/*
		 * } throw new DuplicatedElementException(benefitRequest.getName());
		 */
	}

	@Override
	@Transactional
	public BenefitDTO patchBenefit(Long id, BenefitPatchRequest benefitRequestPatch) {
		Optional<Benefit> optBenefit = benefitsRepository.findById(id);

		ObjectMapper mapper = new ObjectMapper();

		@SuppressWarnings("unchecked")
		Map<String, Object> fields = mapper.convertValue(benefitRequestPatch, Map.class);
		fields.values().removeIf(v -> v == null);

		if (optBenefit.isPresent()) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findRequiredField(Benefit.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, optBenefit.get(), value);
			});
			Benefit benefit = benefitsRepository.save(optBenefit.get());
			return convertBenefitToDTO(benefit);
		}
		throw new NotFoundException("benefit");

	}

	@Override
	@Transactional
	public String deleteBenefitById(Long id) {
		Optional<Benefit> optBenefit = benefitsRepository.findById(id);
		if (optBenefit.isPresent()) {
			Benefit benefit = optBenefit.get();
			benefit.setAvailability(false);
			benefit = benefitsRepository.save(benefit);
			return "Benefit deactivated";
		}
		throw new NotFoundException("benefit");
	}

	private BenefitDTO convertBenefitToDTO(Benefit benefit) {
		BenefitDTO benefitDTO = new BenefitDTO();
		benefitDTO.setId(benefit.getId());
		benefitDTO.setName(benefit.getName());
		benefitDTO.setDescription(benefit.getDescription());
		benefitDTO.setAvailability(benefit.isAvailability());
		benefitDTO.setByLaw(benefit.isByLaw());
		return benefitDTO;
	}

}
