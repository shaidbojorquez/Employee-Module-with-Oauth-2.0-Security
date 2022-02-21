package com.bolsadeideas.springboot.api.employees.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.bolsadeideas.springboot.api.employees.dto.ErrorDTO;
import com.bolsadeideas.springboot.api.employees.dto.PagedResult;
import com.bolsadeideas.springboot.api.employees.dto.request.EmployeePatchRequest;
import com.bolsadeideas.springboot.api.employees.dto.request.EmployeeRequest;
import com.bolsadeideas.springboot.api.employees.enums.Gender;
import com.bolsadeideas.springboot.api.employees.enums.Position;
import com.bolsadeideas.springboot.api.employees.enums.TypeOfElement;
import com.bolsadeideas.springboot.api.employees.util.ErrorUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.bolsadeideas.springboot.api.employees.dto.EmployeeBasicDTO;
import com.bolsadeideas.springboot.api.employees.dto.EmployeeDTO;
import com.bolsadeideas.springboot.api.employees.exception.DuplicatedElementException;
import com.bolsadeideas.springboot.api.employees.exception.NotFoundException;
import com.bolsadeideas.springboot.api.employees.exception.UserProcessException;
import com.bolsadeideas.springboot.api.employees.service.EmployeesService;

@RestController
@RequestMapping("/api")
@Tag(name = "Employees controller")
public class EmployeesController {

	@Autowired
	private EmployeesService employeesService;

	
	@GetMapping("/employees")
	@Operation(summary = "Endpoint that retrieves all employees, also can search based on request parameters", responses = {
			@ApiResponse(description = "Employees found successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = PagedResult.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", 
					schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Benefits not found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))}, parameters = { 
			@Parameter(name = "search", required = false, description = "search by the employee´s name, lastname, phone number"),
			@Parameter(name = "active", required = false, description = "search by active and inactive employees"),
			@Parameter(name = "fromBirthDate", required = false, description = "search from brith date"),
			@Parameter(name = "toBirthDate", required = false, description = "search to brith date"),
			@Parameter(name = "fromHiringDate", required = false, description = "search from hiring date"),
			@Parameter(name = "toHiringDate", required = false, description = "search to hiring date"),
			@Parameter(name = "typeOfElement", required = false, description = "search by type of element"),
			@Parameter(name = "position", required = false, description = "search by position"),
			@Parameter(name = "gender", required = false, description = "search by gender"),
			@Parameter(
	        description = "Zero-based page index (0..N)"
	        , name = "page"
	        , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
			@Parameter(
	         description = "The size of the page to be returned"
	        , name = "size"
	        , content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
			@Parameter(in = ParameterIn.QUERY
	        , description = "Sorting criteria in the format: property(,asc|desc). "
	        + "Default sort order is ascending. " + "Multiple sort criteria are supported."
	        , name = "sort"
	        , content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))),
			@Parameter(name = "pageable", required = false, description = "The request query for sorting, pageSize, pageNumber, offset and paged", content = @Content(schema = @Schema(implementation = Pageable.class, required = false)))})
	public ResponseEntity<Object> findAllEmployees(@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "active", required = false) Boolean active,
			@RequestParam(name = "fromBirthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromBirthDate,
			@RequestParam(name = "toBirthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toBirthDate,
			@RequestParam(name = "fromHiringDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromHiringDate,
			@RequestParam(name = "toHiringDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toHiringDate,
			@RequestParam(name = "typeOfElement", required = false) TypeOfElement typeOfElement,
			@RequestParam(name = "position", required = false) Position position,
			@RequestParam(name = "gender", required = false) Gender gender, @PageableDefault(sort = { "id" }) Pageable pageable) {

		PagedResult<EmployeeDTO> employeesDTO;
		try {
			employeesDTO = employeesService.findAllEmployees(search, active, fromBirthDate, toBirthDate, fromHiringDate,
					toHiringDate, typeOfElement, gender, position, pageable);
		} catch (UserProcessException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}

		if (!employeesDTO.getData().isEmpty()) {
			return new ResponseEntity<Object>(employeesDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new ErrorDTO("Employees list is empty"), HttpStatus.NOT_FOUND);
		}
	}

	
	@GetMapping("/employees/user-info/{username}")
	@Operation(summary = "Endpoint that retrieves the logged user information", responses = {
			@ApiResponse(description = "User information found successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = EmployeeDTO.class))),
			@ApiResponse(description = "User not found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> findUserInfo(@PathVariable String username) {
		EmployeeDTO employeeDTO;
		try {
			employeeDTO = employeesService.findUserInfo(username);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(employeeDTO);
	}

	
	@GetMapping("/employees/basic-info")
	@Operation(summary = "Endpoint that retrieves all employees with basic information, also can search based on request parameters", responses = {
			@ApiResponse(description = "Employees found successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = PagedResult.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", 
					schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Benefits not found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))}, parameters = { 
			@Parameter(name = "search", required = false, description = "search by the employee´s name, lastname, phone number"),
			@Parameter(name = "fromBirthDate", required = false, description = "search from brith date"),
			@Parameter(name = "toBirthDate", required = false, description = "search to brith date"),
			@Parameter(
			description = "Zero-based page index (0..N)"
			, name = "page"
			, content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
			@Parameter(
			description = "The size of the page to be returned"
			, name = "size"
			, content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
			@Parameter(in = ParameterIn.QUERY
			, description = "Sorting criteria in the format: property(,asc|desc). "
			+ "Default sort order is ascending. " + "Multiple sort criteria are supported."
			, name = "sort"
			, content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))),
			@Parameter(name = "pageable", required = false, description = "The request query for sorting, pageSize, pageNumber, offset and paged", content = @Content(schema = @Schema(implementation = Pageable.class, required = false)))})
	public ResponseEntity<Object> findAllEmployeesBasicInfo(
			@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "fromBirthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromBirthDate,
			@RequestParam(name = "toBirthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toBirthDate,
			@PageableDefault(sort = { "id" }) Pageable pageable) {

		PagedResult<EmployeeBasicDTO> employeesBasicDTO;

		try {
			employeesBasicDTO = employeesService.findAllEmployeesBasicInformation(search, fromBirthDate, toBirthDate,
					pageable);
		} catch (UserProcessException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}

		if (!employeesBasicDTO.getData().isEmpty()) {
			return new ResponseEntity<Object>(employeesBasicDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new ErrorDTO("Employees list is empty"), HttpStatus.NOT_FOUND);
		}

	}

	
	@GetMapping("/employees/{id}")
	@Operation(summary = "Endpoint that retrieves a single employee", responses = {
			@ApiResponse(description = "Employee found successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = EmployeeDTO.class))),
			@ApiResponse(description = "Employee not found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> getEmployee(@PathVariable Long id) {
		EmployeeDTO employeeDTO;
		try {
			employeeDTO = employeesService.findEmployeeById(id);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(employeeDTO);
	}

	
	@GetMapping("/employees/basic-info/{id}")
	@Operation(summary = "Endpoint that retrieves a single employee with its basic information", responses = {
			@ApiResponse(description = "Employee found successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = EmployeeDTO.class))),
			@ApiResponse(description = "Employee not found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> getEmployeeBasicInfo(@PathVariable Long id) {
		EmployeeBasicDTO employeeBasicDTO;
		try {
			employeeBasicDTO = employeesService.findEmployeeBasicInfo(id);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(employeeBasicDTO);
	}
	
	@PostMapping("/employees")
	@Operation(summary = "Endpoint that creates an employee", responses = {
			@ApiResponse(description = "Employee created successfully", responseCode = "201", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = EmployeeDTO.class))),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			
	})
	public ResponseEntity<Object> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest,
			BindingResult result) throws URISyntaxException {
		if (result.hasErrors()) {
			return new ResponseEntity<>(new ErrorDTO(ErrorUtils.getErrorMessage(result)), HttpStatus.BAD_REQUEST);
		}

		EmployeeDTO employeeDTO;

		try {
			employeeDTO = employeesService.createEmployee(employeeRequest);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		} catch (UserProcessException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (DuplicatedElementException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		//return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
		return ResponseEntity.created(new URI("/employees/" + employeeDTO.getId())).body(employeeDTO);

	}

	@PutMapping("/employees/{id}")
	@Operation(summary = "Endpoint that updates an employee", responses = {
			@ApiResponse(description = "Employee updated successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = EmployeeDTO.class))),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			
	})
	public ResponseEntity<Object> editEmployee(@PathVariable Long id,
			@RequestBody @Valid EmployeeRequest employeeRequest, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(new ErrorDTO(ErrorUtils.getErrorMessage(result)), HttpStatus.BAD_REQUEST);
		}

		EmployeeDTO employeeDTO;
		try {
			employeeDTO = employeesService.editEmployee(id, employeeRequest);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		} catch (UserProcessException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (DuplicatedElementException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return ResponseEntity.ok().body(employeeDTO);
	}


	@PatchMapping("/employees/partial-update/{id}")
	@Operation(summary = "Endpoint that patches an employee", responses = {
			@ApiResponse(description = "Employee updated successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = EmployeeDTO.class))),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> patchEmployee(@PathVariable Long id,
			@RequestBody @Valid EmployeePatchRequest employeePatchRequest, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(new ErrorDTO(ErrorUtils.getErrorMessage(result)), HttpStatus.BAD_REQUEST);
		}

		EmployeeDTO employeeDTO;

		try {
			employeeDTO = employeesService.patchEmployee(id, employeePatchRequest);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		} catch (UserProcessException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}catch (DuplicatedElementException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return ResponseEntity.ok().body(employeeDTO);

	}

	@PatchMapping("/employees/{id}")
	@Operation(summary = "Endpoint that disables an employee", responses = {
			@ApiResponse(description = "Employee deactivated successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = String.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			
	})
	public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
		String response = "";
		try {
			response = employeesService.deleteEmployeeById(id);

		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(Collections.singletonMap("Response", response));
	}

	@PatchMapping("/employees/add-benefit")
	@Operation(summary = "Endpoint that adds a benefit to all employees", responses = {
			@ApiResponse(description = "Employees updated successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = String.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))}, parameters = {
			@Parameter(name = "benefit", required = true, description = "The benefit that will be removed")		
			})
	public ResponseEntity<Object> addBenefitToEmployees(
			@RequestParam(name = "benefit", required = true) String benefit) {
		String response = "";
		try {
			response = employeesService.addBenefitToAllEmployees(benefit);
		} catch (UserProcessException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(Collections.singletonMap("Response", response));
	}

	@PatchMapping("/employees/remove-benefit")
	@Operation(summary = "Endpoint that removes a benefit from all employees", responses = {
			@ApiResponse(description = "Employees updated successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = String.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))}, parameters = {
			@Parameter(name = "benefit", required = true, description = "The benefit that will be removed")
			})
	public ResponseEntity<Object> removeBenefitFromAllEmployees(
			@RequestParam(name = "benefit", required = true) String benefit) {
		String response = "";
		try {
			response = employeesService.removeBenefitFromAllEmployees(benefit);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(Collections.singletonMap("Response", response));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<?> handleMissingParams(MissingServletRequestParameterException ex) {
		String name = ex.getParameterName();
		Map<String, Object> response = new HashMap<>();
		response.put("message", name);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(com.fasterxml.jackson.core.JsonParseException.class)
	public ResponseEntity<?> handleException(com.fasterxml.jackson.core.JsonParseException ex) {
		String message = ex.getMessage();
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<?> handleExceptionNumber(JsonMappingException ex) {
		String message = ex.getMessage();
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<?> handleTypeMismatch(InvalidFormatException ex) {

		String name = ex.getValue().toString();
		String type = ex.getTargetType().getSimpleName();
		Object value = ex.getValue();
		String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);
		System.out.println(message);
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {

		String name = ex.getValue().toString();
		String type = ex.getRequiredType().getSimpleName();
		Object value = ex.getValue();
		String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

}
