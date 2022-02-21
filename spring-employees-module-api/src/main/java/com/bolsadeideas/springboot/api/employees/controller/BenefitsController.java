package com.bolsadeideas.springboot.api.employees.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.bolsadeideas.springboot.api.employees.dto.BenefitDTO;
import com.bolsadeideas.springboot.api.employees.dto.ErrorDTO;
import com.bolsadeideas.springboot.api.employees.dto.PagedResult;
import com.bolsadeideas.springboot.api.employees.dto.request.BenefitRequest;
import com.bolsadeideas.springboot.api.employees.dto.request.BenefitPatchRequest;
import com.bolsadeideas.springboot.api.employees.exception.DuplicatedElementException;
import com.bolsadeideas.springboot.api.employees.exception.NotFoundException;
import com.bolsadeideas.springboot.api.employees.service.BenefitsService;
import com.bolsadeideas.springboot.api.employees.util.ErrorUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "Benefits controller")
public class BenefitsController {

	@Autowired
	BenefitsService benefitsServie;

	@GetMapping("/benefits")
	@Operation(summary = "Endpoint that retrieves all benefits, also can search based on request parameters", responses = {
			@ApiResponse(description = "Benefits found successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = PagedResult.class))),
			@ApiResponse(description = "Benefits not found", responseCode = "404", content = @Content(mediaType = "application/json", 
					schema = @Schema(implementation = ErrorDTO.class)))}, parameters = { 
			@Parameter(name = "search", required = false, description = "search by the benefit's name"),
			@Parameter(name = "byLaw", required = false, description = "search for the benefits that are by law or those that are not."),
			@Parameter(name = "availability", required = false, description = "search by benefit availability"),
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
	public ResponseEntity<Object> findAllBenefits(@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "byLaw", required = false) Boolean byLaw,
			@RequestParam(name = "availability", required = false) Boolean availability,
			@PageableDefault(sort = { "id" }) Pageable pageable) {

		PagedResult<BenefitDTO> benefitsDTO;

		benefitsDTO = benefitsServie.findAllBenefits(search, byLaw, availability, pageable);
		if (!benefitsDTO.getData().isEmpty()) {
			return new ResponseEntity<Object>(benefitsDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new ErrorDTO("Benefits list is empty"), HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/benefits/user-benefits/{username}")
	@Operation(summary = "Endpoint that retrieves the benefits of the logged user", responses = {
			@ApiResponse(description = "User benefits found successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = List.class))),
			@ApiResponse(description = "Benefits list is empty", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "User not found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> findUserBenefits(@PathVariable String username) {
		List<BenefitDTO> benefitsDTO;
		try {
			benefitsDTO = benefitsServie.findUserBenefits(username);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}

		if (!benefitsDTO.isEmpty()) {
			return new ResponseEntity<Object>(benefitsDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new ErrorDTO("Benefits list is empty"), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/benefits/{id}")
	@Operation(summary = "Endpoint that retrieves a single benefit", responses = {
			@ApiResponse(description = "Benefit found successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BenefitDTO.class))),
			@ApiResponse(description = "Benefit not found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> getBenefit(@PathVariable Long id) {
		BenefitDTO benefitDTO;
		try {
			benefitDTO = benefitsServie.findBenefitById(id);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(benefitDTO);
	}

	@PostMapping("/benefits")
	@Operation(summary = "Endpoint that creates a benefit", responses = {
			@ApiResponse(description = "Benefit created successfully", responseCode = "201", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BenefitDTO.class))),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> createBenefit(@RequestBody @Valid BenefitRequest benefitRequest, BindingResult result)
			throws URISyntaxException {

		if (result.hasErrors()) {
			return new ResponseEntity<>(new ErrorDTO(ErrorUtils.getErrorMessage(result)), HttpStatus.BAD_REQUEST);
		}

		BenefitDTO benefitDTO;

		try {
			benefitDTO = benefitsServie.createBenefit(benefitRequest);
		} catch (DuplicatedElementException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		//return new ResponseEntity<>(benefitDTO, HttpStatus.CREATED);
		return ResponseEntity.created(new URI("/benfits/" + benefitDTO.getId())).body(benefitDTO);
	}

	@PutMapping("/benefits/{id}")
	@Operation(summary = "Endpoint that updates a benefit", responses = {
			@ApiResponse(description = "Benefit updated successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BenefitDTO.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> editBenefit(@PathVariable Long id, @RequestBody @Valid BenefitRequest benefitRequest,
			BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(new ErrorDTO(ErrorUtils.getErrorMessage(result)), HttpStatus.BAD_REQUEST);
		}

		BenefitDTO benefitDTO;
		try {
			benefitDTO = benefitsServie.editBenefit(id, benefitRequest);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok().body(benefitDTO);
	}

	@PatchMapping("/benefits/partial-update/{id}")
	@Operation(summary = "Endpoint that patches a benefit", responses = {
			@ApiResponse(description = "Benefit updated successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BenefitDTO.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> patchBenefit(@PathVariable Long id,
			@RequestBody @Valid BenefitPatchRequest benefitPatchRequest, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(new ErrorDTO(ErrorUtils.getErrorMessage(result)), HttpStatus.BAD_REQUEST);
		}

		BenefitDTO benefitDTO;
		try {
			benefitDTO = benefitsServie.patchBenefit(id, benefitPatchRequest);
		} catch (NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok().body(benefitDTO);
	}

	@PatchMapping("/benefits/{id}")
	@Operation(summary = "Endpoint that disables a benefit", responses = {
			@ApiResponse(description = "Benefit updated successfully", responseCode = "200", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BenefitDTO.class))),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ErrorDTO.class)))
	})
	public ResponseEntity<Object> deleteBenefit(@PathVariable Long id) {
		String response = "";
		try {
			response = benefitsServie.deleteBenefitById(id);
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
