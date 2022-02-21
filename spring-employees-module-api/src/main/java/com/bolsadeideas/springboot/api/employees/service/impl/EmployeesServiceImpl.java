package com.bolsadeideas.springboot.api.employees.service.impl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bolsadeideas.springboot.api.employees.exception.DuplicatedElementException;
import com.bolsadeideas.springboot.api.employees.exception.NotFoundException;
import com.bolsadeideas.springboot.api.employees.exception.UserProcessException;
import com.bolsadeideas.springboot.api.employees.dto.AddressDTO;
import com.bolsadeideas.springboot.api.employees.dto.BenefitDTO;
import com.bolsadeideas.springboot.api.employees.dto.EmployeeBasicDTO;
import com.bolsadeideas.springboot.api.employees.dto.EmployeeDTO;
import com.bolsadeideas.springboot.api.employees.dto.PagedResult;
import com.bolsadeideas.springboot.api.employees.dto.request.EmployeePatchRequest;
import com.bolsadeideas.springboot.api.employees.dto.request.EmployeeRequest;
import com.bolsadeideas.springboot.api.employees.enums.Gender;
import com.bolsadeideas.springboot.api.employees.enums.Position;
import com.bolsadeideas.springboot.api.employees.enums.TypeOfElement;
import com.bolsadeideas.springboot.api.employees.model.Address;
import com.bolsadeideas.springboot.api.employees.model.Benefit;
import com.bolsadeideas.springboot.api.employees.model.Employee;
import com.bolsadeideas.springboot.api.employees.model.Employee_;
import com.bolsadeideas.springboot.api.employees.repository.BenefitsRepository;
import com.bolsadeideas.springboot.api.employees.repository.EmployeesRepository;
import com.bolsadeideas.springboot.api.employees.service.EmployeesService;
import com.bolsadeideas.springboot.api.employees.specification.EmployeeSpecification;
import com.bolsadeideas.springboot.api.employees.specification.SearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class EmployeesServiceImpl implements EmployeesService {

	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private BenefitsRepository benefitsRepository;

	@Override
	@Transactional(readOnly = true)
	public PagedResult<EmployeeDTO> findAllEmployees(String search, Boolean active, LocalDate fromBirthDate,
			LocalDate toBirthDate, LocalDate fromHiringDate, LocalDate toHiringDate, TypeOfElement typeOfElement,
			Gender gender, Position position, Pageable pageable) {
		Specification<Employee> employeeSpecification = Specification.where(null);

		if (search != null) {
			Specification<Employee> nameSpecification = Specification
					.where(new EmployeeSpecification(new SearchCriteria(Employee_.NAME, "~", search)));
			employeeSpecification = employeeSpecification.or(nameSpecification);

			Specification<Employee> lastNameSpecification = Specification
					.where(new EmployeeSpecification(new SearchCriteria(Employee_.LAST_NAME, "~", search)));
			employeeSpecification = employeeSpecification.or(lastNameSpecification);

			Specification<Employee> phoneNumberSpecification = Specification
					.where(new EmployeeSpecification(new SearchCriteria(Employee_.TELEPHONE_NUMBER, "~", search)));
			employeeSpecification = employeeSpecification.or(phoneNumberSpecification);
		}

		if (active != null) {
			Specification<Employee> activeSpecification = Specification
					.where(new EmployeeSpecification(new SearchCriteria(Employee_.ACTIVE, "=", active)));
			employeeSpecification = employeeSpecification.or(activeSpecification);
		}

		if (typeOfElement != null) {
			/*
			 * Specification<Employee> typeOfElementSpecification = Specification. where(new
			 * EmployeeSpecification(new SearchCriteria(Employee_.TYPE_OF_ELEMENT, "=",
			 * typeOfElement))); employeeSpecification =
			 * employeeSpecification.or(typeOfElementSpecification);
			 */
			employeeSpecification = employeeSpecification
					.and(new EmployeeSpecification(new SearchCriteria(Employee_.TYPE_OF_ELEMENT, "=", typeOfElement)));
		}

		if (gender != null) {
			employeeSpecification = employeeSpecification
					.and(new EmployeeSpecification(new SearchCriteria(Employee_.GENDER, "=", gender)));
		}

		if (position != null) {
			employeeSpecification = employeeSpecification
					.and(new EmployeeSpecification(new SearchCriteria(Employee_.POSITION, "=", position)));
		}

		if (fromBirthDate != null) {
			employeeSpecification = employeeSpecification.and(new EmployeeSpecification(
					new SearchCriteria(Employee_.DATE_OF_BIRTH, ">=", fromBirthDate.format(dateFormat))));
		}

		if (toBirthDate != null) {
			employeeSpecification = employeeSpecification.and(new EmployeeSpecification(
					new SearchCriteria(Employee_.DATE_OF_BIRTH, "<=", toBirthDate.format(dateFormat))));
		}

		if (fromHiringDate != null) {
			employeeSpecification = employeeSpecification.and(new EmployeeSpecification(
					new SearchCriteria(Employee_.HIRING_DATE, ">=", fromHiringDate.format(dateFormat))));
		}

		if (toHiringDate != null) {
			employeeSpecification = employeeSpecification.and(new EmployeeSpecification(
					new SearchCriteria(Employee_.HIRING_DATE, "<=", toHiringDate.format(dateFormat))));
		}

		if (fromBirthDate != null && toBirthDate != null && fromBirthDate.isAfter(toBirthDate)) {
			throw new UserProcessException("fromBirthDate must be before than toBirthDate");
		}

		if (fromHiringDate != null && toHiringDate != null && fromHiringDate.isAfter(toHiringDate)) {
			throw new UserProcessException("fromHiringDate must be before than toHiringDate");
		}

		Page<Employee> pageEmployees = employeesRepository.findAll(employeeSpecification, pageable);
		List<EmployeeDTO> listEmployees = pageEmployees.getContent().stream().map(this::convertEmployeeToDTO)
				.collect(Collectors.toList());
		PagedResult<EmployeeDTO> employeesDTO = new PagedResult<>();
		employeesDTO.setData(listEmployees);
		employeesDTO.setPageNumber(pageEmployees.getNumber());
		employeesDTO.setPageSize(pageEmployees.getSize());
		employeesDTO.setTotalElements(pageEmployees.getTotalElements());
		employeesDTO.setTotalPages(pageEmployees.getTotalPages());

		return employeesDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeeDTO findUserInfo(String username) {
		
		Optional<Employee> optEmployee = employeesRepository.findByWorkEmail(username);
		if (optEmployee.isPresent()) {
			Employee employee = optEmployee.get();
			return convertEmployeeToDTO(employee);
		}
		throw new NotFoundException("employee with username = " + username);

	}

	@Override
	@Transactional(readOnly = true)
	public PagedResult<EmployeeBasicDTO> findAllEmployeesBasicInformation(String search, LocalDate fromBirthDate,
			LocalDate toBirthDate, Pageable pageable) {
		Specification<Employee> employeeSpecification = Specification.where(null);

		if (search != null) {
			Specification<Employee> nameSpecification = Specification
					.where(new EmployeeSpecification(new SearchCriteria(Employee_.NAME, "~", search)));
			employeeSpecification = employeeSpecification.or(nameSpecification);
			Specification<Employee> lastNameSpecification = Specification
					.where(new EmployeeSpecification(new SearchCriteria(Employee_.LAST_NAME, "~", search)));
			employeeSpecification = employeeSpecification.or(lastNameSpecification);
			Specification<Employee> phoneNumberSpecification = Specification
					.where(new EmployeeSpecification(new SearchCriteria(Employee_.TELEPHONE_NUMBER, "~", search)));
			employeeSpecification = employeeSpecification.or(phoneNumberSpecification);
		}

		if (fromBirthDate != null) {
			employeeSpecification = employeeSpecification.and(new EmployeeSpecification(
					new SearchCriteria(Employee_.DATE_OF_BIRTH, ">=", fromBirthDate.format(dateFormat))));
		}

		if (toBirthDate != null) {
			employeeSpecification = employeeSpecification.and(new EmployeeSpecification(
					new SearchCriteria(Employee_.DATE_OF_BIRTH, "<=", toBirthDate.format(dateFormat))));
		}

		if (fromBirthDate != null && toBirthDate != null && fromBirthDate.isAfter(toBirthDate)) {
			throw new UserProcessException("fromBrithDate must be before than toBirthDate");
		}

		Page<Employee> pageEmployees = employeesRepository.findAll(employeeSpecification, pageable);
		List<EmployeeBasicDTO> listEmployees = pageEmployees.getContent().stream().map(this::convertEmployeeToBasicDTO)
				.collect(Collectors.toList());
		PagedResult<EmployeeBasicDTO> employeesDTO = new PagedResult<>();
		employeesDTO.setData(listEmployees);
		employeesDTO.setPageNumber(pageEmployees.getNumber());
		employeesDTO.setPageSize(pageEmployees.getSize());
		employeesDTO.setTotalElements(pageEmployees.getTotalElements());
		employeesDTO.setTotalPages(pageEmployees.getTotalPages());

		return employeesDTO;
	}

	@Override
	@Transactional
	public EmployeeDTO createEmployee(EmployeeRequest employeeRequest) {
		
		Optional<Employee> existingEmployee = employeesRepository.findByUniqueKey(employeeRequest.getUniqueKey().trim());

		// Check if there is no employee with that name and lastname.
		if (!existingEmployee.isPresent()) {
			Employee employee = new Employee();
			employee.setName(StringUtils.capitalize(employeeRequest.getName().toLowerCase()));
			employee.setLastName(StringUtils.capitalize(employeeRequest.getLastName().toLowerCase()));
			employee.setDateOfBirth(employeeRequest.getDateOfBirth());
			employee.setEmail(employeeRequest.getEmail().trim());
			
			
			employee.setTelephoneNumber(employeeRequest.getTelephoneNumber());
			

			employee.setHiringDate(employeeRequest.getHiringDate());
			employee.setWorkEmail(employeeRequest.getWorkEmail().trim());
			employee.setActive(employeeRequest.isActive());
			employee.setPosition(employeeRequest.getPosition());
			employee.setGender(employeeRequest.getGender());
			employee.setTypeOfElement(employeeRequest.getTypeOfElement());
			employee.setUniqueKey(employeeRequest.getUniqueKey());

			Address address = new Address();
			address.setCountry(employeeRequest.getAddressRequest().getCountry().trim());
			address.setStreet(employeeRequest.getAddressRequest().getStreet().trim());
			address.setCity(employeeRequest.getAddressRequest().getCity().trim());
			address.setState(employeeRequest.getAddressRequest().getState().trim());
			address.setZipCode(employeeRequest.getAddressRequest().getZipCode());

			employee.setAddress(address);

			// Validates that the benefits exist in the database
			if (!employeeRequest.getBenefits().isEmpty()) {
				for (Long idBenefit : employeeRequest.getBenefits()) {
					Optional<Benefit> optBenefit = benefitsRepository.findById(idBenefit);
					if (!optBenefit.isPresent()) {
						throw new NotFoundException("benefit with id= " + idBenefit);
					}
				}
				
				// Validate that benefits are available
				for (Long idBenefit : employeeRequest.getBenefits()) {
					Optional<Benefit> optBenefit = benefitsRepository.findById(idBenefit);
					Benefit benefit = optBenefit.get();
					if (benefit.isAvailability() == false) {
						throw new UserProcessException(
								"Benefit " + benefit.getName() + " with id = " + benefit.getId() + " is unavailable");
					}
				}
				
				if (employee.isActive() == false) {
					throw new UserProcessException("Employee must be active to add benefits");
				} else {
					employee.setBenefits(employeeRequest.getBenefits().stream()
							.map(benefit -> benefitsRepository.findById(benefit).orElseGet(null)).filter(Objects::nonNull)
							.collect(Collectors.toSet()));
				}
			}
			
			employee = employeesRepository.save(employee);
			return convertEmployeeToDTO(employee);
		}
		throw new DuplicatedElementException(
				"with unique key: " + employeeRequest.getUniqueKey());
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeeBasicDTO findEmployeeBasicInfo(Long id) {
		Optional<Employee> optEmployee = employeesRepository.findById(id);
		if (optEmployee.isPresent()) {
			Employee employee = optEmployee.get();
			return convertEmployeeToBasicDTO(employee);
		}
		throw new NotFoundException("employee with id = " + id);
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeeDTO findEmployeeById(Long id) {
		Optional<Employee> optEmployee = employeesRepository.findById(id);
		if (optEmployee.isPresent()) {
			Employee employee = optEmployee.get();
			return convertEmployeeToDTO(employee);
		}
		throw new NotFoundException("employee with id = " + id);
	}

	@Override
	@Transactional
	public EmployeeDTO editEmployee(Long id, EmployeeRequest employeeRequest) {

		Optional<Employee> optEmployee = employeesRepository.findById(id);
		// Validates that the employee to be updated exists
		if (optEmployee.isPresent()) {
			Employee employee = optEmployee.get();
			
			List<Employee> employees = (List<Employee>) employeesRepository.findAll();
			employees.remove(employee);
			
			for(Employee existingEmployee : employees) {
				if(existingEmployee.getUniqueKey().equalsIgnoreCase(employeeRequest.getUniqueKey().trim())) {
					throw new DuplicatedElementException(
				"with unqiue key: " + employeeRequest.getUniqueKey());
				}
			}
			
			employee.setName(StringUtils.capitalize(employeeRequest.getName().toLowerCase()));
			employee.setLastName(StringUtils.capitalize(employeeRequest.getLastName().toLowerCase()));

			employee.setDateOfBirth(employeeRequest.getDateOfBirth());
			employee.setEmail(employeeRequest.getEmail().trim());
			
			employee.setTelephoneNumber(employeeRequest.getTelephoneNumber());
			

			employee.setHiringDate(employeeRequest.getHiringDate());
			employee.setWorkEmail(employeeRequest.getWorkEmail().trim());
			employee.setActive(employeeRequest.isActive());
			employee.setPosition(employeeRequest.getPosition());
			employee.setGender(employeeRequest.getGender());
			employee.setTypeOfElement(employeeRequest.getTypeOfElement());
			employee.setUniqueKey(employeeRequest.getUniqueKey());

			Address address = new Address();
			address.setId(employee.getAddress().getId());
			address.setCountry(employeeRequest.getAddressRequest().getCountry().trim());
			address.setStreet(employeeRequest.getAddressRequest().getStreet().trim());
			address.setCity(employeeRequest.getAddressRequest().getCity().trim());
			address.setState(employeeRequest.getAddressRequest().getState().trim());
			address.setZipCode(employeeRequest.getAddressRequest().getZipCode());
			// Validar address en un metodo aparte
			employee.setAddress(address);

			// Validates that the benefits exist in the database
			if (!employeeRequest.getBenefits().isEmpty()) {
				for (Long idBenefit : employeeRequest.getBenefits()) {
					Optional<Benefit> optBenefit = benefitsRepository.findById(idBenefit);
					if (!optBenefit.isPresent()) {
						throw new NotFoundException("benefit with id= " + idBenefit);
					}
				}
				
				// Validate that benefits are available
				for (Long idBenefit : employeeRequest.getBenefits()) {
					Optional<Benefit> optBenefit = benefitsRepository.findById(idBenefit);
					Benefit benefit = optBenefit.get();
					if (benefit.isAvailability() == false) {
						throw new UserProcessException(
								"Benefit " + benefit.getName() + " with id = " + benefit.getId() + " is unavailable");
					}
				}
				
				//Validate that the employee is active
				if (employee.isActive() == false) {
					throw new UserProcessException("Employee must be active to add benefits");
				} else {
					employee.setBenefits(employeeRequest.getBenefits().stream()
							.map(benefit -> benefitsRepository.findById(benefit).orElseGet(null)).filter(Objects::nonNull)
							.collect(Collectors.toSet()));
				}
			}

			employee = employeesRepository.save(employee);
			return convertEmployeeToDTO(employee);

		}
		throw new NotFoundException("employee with id= " + id);
	}

	@Override
	@Transactional
	public EmployeeDTO patchEmployee(Long id, EmployeePatchRequest employeePatchRequest) {
		Optional<Employee> optEmployee = employeesRepository.findById(id);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> fields = mapper.convertValue(employeePatchRequest, Map.class);
		fields.values().removeIf(v -> v == null);

		if (optEmployee.isPresent()) {
			
			if(employeePatchRequest.getUniqueKey() != null) {
				List<Employee> employees = (List<Employee>) employeesRepository.findAll();
				employees.remove(optEmployee.get());
				
				for(Employee existingEmployee : employees) {
					if(existingEmployee.getUniqueKey().equalsIgnoreCase(employeePatchRequest.getUniqueKey().trim())) {
						throw new DuplicatedElementException(
					"with unqiue key: " + employeePatchRequest.getUniqueKey());
					}
				}
				
			}
			
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findRequiredField(Employee.class, key);
				field.setAccessible(true);
				if (value instanceof LinkedHashMap) {
					@SuppressWarnings("unchecked")
					LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) value;
					object.values().removeIf(v -> v == null);
					object.forEach((objectKey, objectValue) -> {
						Field objectField = ReflectionUtils.findRequiredField(Address.class, objectKey);
						ReflectionUtils.setField(objectField, optEmployee.get().getAddress(), objectValue);
					});
				} else if (value instanceof ArrayList) {
					@SuppressWarnings("unchecked")
					ArrayList<Long> idsBenefits = (ArrayList<Long>) value;
					

					if (!idsBenefits.isEmpty()) {
						Set<Benefit> benefits = new HashSet<>();
						
						for (Long idBenefit : idsBenefits) {
							Optional<Benefit> optBenefit = benefitsRepository.findById(idBenefit);
							if (!optBenefit.isPresent()) {
								throw new NotFoundException("benefit with id= " + idBenefit);
							}
						}

						for (Long idBenefit : idsBenefits) {
							Optional<Benefit> optBenefit = benefitsRepository.findById(idBenefit);
							Benefit benefit = optBenefit.get();
							if (benefit.isAvailability() == false) {
								throw new UserProcessException("Benefit " + benefit.getName() + " with id = "
										+ benefit.getId() + " is unavailable");
							}
						}
						
						
						if (optEmployee.get().isActive() == false) {
							throw new UserProcessException("Employee must be active to add benefits");
						} else {
							benefits = idsBenefits.stream()
									.map(benefit -> benefitsRepository.findById(benefit).orElseGet(null))
									.filter(Objects::nonNull).collect(Collectors.toSet());
						}
						
						ReflectionUtils.setField(field, optEmployee.get(), benefits);
					}

				}else if(value instanceof Boolean) {
					ReflectionUtils.setField(field, optEmployee.get(), value);
				}
				else if(value instanceof String) {
					
					if(checkIfDateIsValid(value)) {
						 LocalDate dateOfBirth = LocalDate.parse((CharSequence) value, dateFormat);
						 ReflectionUtils.setField(field, optEmployee.get(), dateOfBirth);
					}else if(isValidEnum(Position.class , value)) {
						String validPosition = (String)value;
						Position position = Position.valueOf(validPosition);
						
						ReflectionUtils.setField(field, optEmployee.get(), position);
					}else if(isValidEnum(TypeOfElement.class , value)) {
						String validElement = (String)value;
						TypeOfElement typeOfElement = TypeOfElement.valueOf(validElement);
						
						ReflectionUtils.setField(field, optEmployee.get(), typeOfElement);
					}
					else if(isValidEnum(Gender.class , value)) {
						String validGender = (String)value;
						Gender gender = Gender.valueOf(validGender);
						
						ReflectionUtils.setField(field, optEmployee.get(), gender);
					}else {
						ReflectionUtils.setField(field, optEmployee.get(), value);
					}
					 
				}
			});
			
			Employee employee = employeesRepository.save(optEmployee.get());
			return convertEmployeeToDTO(employee);
		}
		throw new NotFoundException("benefit");
		
	}
	
	@SuppressWarnings("rawtypes")
	boolean isValidEnum(Class enumClass, Object value) {
         return Arrays.stream(enumClass.getEnumConstants()).anyMatch((e) -> value.equals(
                 e.toString()));
     }
	
	private static boolean checkIfDateIsValid(Object value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // With lenient parsing, the parser may use heuristics to interpret
        // inputs that do not precisely match this object's format.
        format.setLenient(false);
        try {
            format.parse((String) value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

	@Override
	@Transactional
	public String deleteEmployeeById(Long id) {
		Optional<Employee> optEmployee = employeesRepository.findById(id);
		if (optEmployee.isPresent()) {
			Employee employee = optEmployee.get();
			employee.setActive(false);
			employee = employeesRepository.save(employee);
			return "Employee deactivated";
		}
		throw new NotFoundException("employee");
	}

	@Override
	@Transactional
	public String addBenefitToAllEmployees(String benefitName) {
		Optional<Benefit> optBenefit = benefitsRepository.findByName(benefitName);
		if (optBenefit.isPresent()) {
			Benefit benefit = optBenefit.get();
			if (benefit.isAvailability() == false) {
				throw new UserProcessException(
						"Benefit" + benefit.getName() + "with id =" + benefit.getId() + " is unavailable");
			} else {

				List<Employee> employees = (List<Employee>) employeesRepository.findAll();

				for (Employee employee : employees) {
					employee.getBenefits().add(benefit);
					employee = employeesRepository.save(employee);
				}

				return "Benefit added to all employees";
			}
		} else {
			throw new NotFoundException("Benefit");
		}
	}

	@Override
	@Transactional
	public String removeBenefitFromAllEmployees(String benefitName) {
		Optional<Benefit> optBenefit = benefitsRepository.findByName(benefitName);
		if (optBenefit.isPresent()) {
			Benefit benefit = optBenefit.get();
			List<Employee> employees = (List<Employee>) employeesRepository.findAll();
			for (Employee employee : employees) {
				employee.getBenefits().remove(benefit);
				employee = employeesRepository.save(employee);
			}

			return "Benefit removed from all employees";

		} else {
			throw new NotFoundException("Benefit");
		}

	}

	private EmployeeDTO convertEmployeeToDTO(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(employee.getId());
		employeeDTO.setName(employee.getName());
		employeeDTO.setLastName(employee.getLastName());
		employeeDTO.setDateOfBirth(employee.getDateOfBirth());
		employeeDTO.setEmail(employee.getEmail());
		employeeDTO.setTelephoneNumber(employee.getTelephoneNumber());
		employeeDTO.setHiringDate(employee.getHiringDate());
		employeeDTO.setWorkEmail(employee.getWorkEmail());
		employeeDTO.setActive(employee.isActive());
		employeeDTO.setPosition(employee.getPosition());
		employeeDTO.setGender(employee.getGender());
		employeeDTO.setTypeOfElement(employee.getTypeOfElement());
		employeeDTO.setUniqueKey(employee.getUniqueKey());
		employeeDTO.setAddress(convertAddressToDTO(employee.getAddress()));
		employeeDTO.setBenefits(
				employee.getBenefits().stream().map(this::convertBenefitToDTO).collect(Collectors.toSet()));
		return employeeDTO;
	}

	private AddressDTO convertAddressToDTO(Address address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(address.getId());
		addressDTO.setCountry(address.getCountry());
		addressDTO.setStreet(address.getStreet());
		addressDTO.setCity(address.getCity());
		addressDTO.setState(address.getState());
		addressDTO.setZipCode(address.getZipCode());
		return addressDTO;
	}

	private EmployeeBasicDTO convertEmployeeToBasicDTO(Employee employee) {
		EmployeeBasicDTO employeeDTO = new EmployeeBasicDTO();
		employeeDTO.setId(employee.getId());
		employeeDTO.setName(employee.getName());
		employeeDTO.setLastName(employee.getLastName());
		employeeDTO.setDateOfBirth(employee.getDateOfBirth());
		employeeDTO.setEmail(employee.getEmail());
		employeeDTO.setTelephoneNumber(employee.getTelephoneNumber());
		employeeDTO.setUniqueKey(employee.getUniqueKey());
		employeeDTO.setAddress(employee.getAddress());
		employeeDTO.setBenefits(
				employee.getBenefits().stream().map(this::convertBenefitToDTO).collect(Collectors.toSet()));
		return employeeDTO;
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

	/*private boolean validatePhoneNumber(String phoneNumber) {
		String patterns = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
				+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
				+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

		Pattern pattern = Pattern.compile(patterns);

		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}*/

}
