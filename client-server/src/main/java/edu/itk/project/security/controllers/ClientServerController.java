package edu.itk.project.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import edu.itk.project.security.dto.request.*;
import edu.itk.project.security.enums.*;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class ClientServerController {

	@Autowired
	private WebClient webClient;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/")
	public Map<String, String> setSecurityContextHolder(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {		
		String user = oauth2AuthorizedClient.getPrincipalName();
		
		String query = "SELECT authorities.authority FROM users, authorities WHERE users.username = authorities.username AND users.username = ?;";
		String role = (String) jdbcTemplate.queryForObject(query, new Object[] { user }, String.class);
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(role));

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);  
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Map<String, String> json = new LinkedHashMap<>();
		json.put("user", user);
		json.put("role", role);
		
		return json;
	}
	
	//Employees
	private static final String EMPLOYEE_MODULE_HOST = "http://10.5.0.7:8090";
	
	private static final String GET_ALL_EMPLOYEES = EMPLOYEE_MODULE_HOST + "/api/employees";
	
	private static final String GET_ALL_EMPLOYEES_BASIC_INFO = EMPLOYEE_MODULE_HOST + "/api/employees/basic-info";
	
	private static final String GET_EMPLOYEE_BY_ID = EMPLOYEE_MODULE_HOST + "/api/employees/{id}";
	
	private static final String GET_USER_INFO_BY_USERNAME = EMPLOYEE_MODULE_HOST + "/api/employees/user-info/{username}";
	
	private static final String GET_USER_BENEFITS_BY_USERNAME = EMPLOYEE_MODULE_HOST + "/api/benefits/user-benefits/{username}";
	
	private static final String GET_EMPLOYEE_BASIC_INFO_BY_ID = EMPLOYEE_MODULE_HOST + "/api/employees/basic-info/{id}";
	
	private static final String POST_EMPLOYEE = EMPLOYEE_MODULE_HOST + "/api/employees";
	
	private static final String PUT_EMPLOYEE = EMPLOYEE_MODULE_HOST + "/api/employees/{id}";
	
	private static final String PATCH_EMPLOYEE = EMPLOYEE_MODULE_HOST + "/api/employees/partial-update/{id}";
	
	private static final String DISABLE_EMPLOYEE = EMPLOYEE_MODULE_HOST + "/api/employees/{id}";

	private static final String ADD_BENEFIT_TO_EMPLOYEES = EMPLOYEE_MODULE_HOST + "/api/employees/add-benefit";
	
	private static final String REMOVE_BENEFIT_FROM_EMPLOYEES = EMPLOYEE_MODULE_HOST + "/api/employees/remove-benefit";
	
	//Benefits
	private static final String GET_ALL_BENEFITS = EMPLOYEE_MODULE_HOST + "/api/benefits";
	
	private static final String GET_BENEFIT_BY_ID = EMPLOYEE_MODULE_HOST + "/api/benefits/{id}";
	
	private static final String POST_BENEFIT = EMPLOYEE_MODULE_HOST + "/api/benefits";
	
	private static final String PUT_BENEFIT = EMPLOYEE_MODULE_HOST + "/api/benefits/{id}";
	
	private static final String PATCH_BENEFIT = EMPLOYEE_MODULE_HOST + "/api/benefits/partial-update/{id}";
	
	private static final String DISABLE_BENEFIT = EMPLOYEE_MODULE_HOST + "/api/benefits/{id}";
	
	// GET all employees
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/api/employees")
	public Object getAllEmployees(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@RequestParam(name = "search", required = false) Optional<String> search,
			@RequestParam(name = "active", required = false) Optional<Boolean> active,
			@RequestParam(name = "fromBirthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> fromBirthDate,
			@RequestParam(name = "toBirthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> toBirthDate,
			@RequestParam(name = "fromHiringDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> fromHiringDate,
			@RequestParam(name = "toHiringDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> toHiringDate,
			@RequestParam(name = "typeOfElement", required = false) Optional<TypeOfElement> typeOfElement,
			@RequestParam(name = "position", required = false) Optional<Position> position,
			@RequestParam(name = "gender", required = false) Optional<Gender> gender,
			@PageableDefault(sort = { "id" }) Pageable pageable) {
		return this.webClient.get().uri(GET_ALL_EMPLOYEES,
				uri -> uri.queryParamIfPresent("search", search).queryParamIfPresent("active", active)
				.queryParamIfPresent("fromBirthDate", fromBirthDate).queryParamIfPresent("toBirthDate", toBirthDate)
				.queryParamIfPresent("fromHiringDate", fromHiringDate).queryParamIfPresent("toHiringDate", toHiringDate)
				.queryParamIfPresent("typeOfElement", typeOfElement).queryParamIfPresent("position", position)
				.queryParamIfPresent("gender", gender)
				.build())
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(Object.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}
	
	//GET ALL BENEFITS
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/api/benefits")
	public Object getAllBenefits(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@RequestParam(name = "search", required = false) Optional<String> search,
			@RequestParam(name = "byLaw", required = false) Optional<Boolean> byLaw,
			@RequestParam(name = "availability", required = false) Optional<Boolean> availability,
			@PageableDefault(sort = { "id" }) Pageable pageable) {
		return this.webClient.get().uri(GET_ALL_BENEFITS,
				uri -> uri.queryParamIfPresent("search", search).queryParamIfPresent("byLaw", byLaw).
				queryParamIfPresent("availability", availability).build())
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(Object.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}
	
	
	
	//GET EMPLOYEES BASIC INFO
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/api/employees/basic-info")
	public Object getAllEmployeesBasicInfo(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@RequestParam(name = "search", required = false) Optional<String> search,
			@RequestParam(name = "fromBirthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> fromBirthDate,
			@RequestParam(name = "toBirthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> toBirthDate,
			@PageableDefault(sort = { "id" }) Pageable pageable) {
		return this.webClient.get().uri(GET_ALL_EMPLOYEES_BASIC_INFO,
				uri -> uri.queryParamIfPresent("search", search).queryParamIfPresent("fromBirthDate", fromBirthDate)
				.queryParamIfPresent("toBirthDate", toBirthDate)
				.build())
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(Object.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}
	
	
	
	//GET EMPLOYEE BY ID
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/api/employees/{id}")
	public Object findEmployeeById(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("id") long id) {
		return this.webClient.get().uri(GET_EMPLOYEE_BY_ID, id)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(Object.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}
	
	
	//GET USER INFO
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/api/employees/user-info")
	public Object findUserEmployeeInfo(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		String username = oauth2AuthorizedClient.getPrincipalName();
		
		return this.webClient.get().uri(GET_USER_INFO_BY_USERNAME, username)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(Object.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}
	
	//GET USER BENEFITS
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/api/benefits/user-benefits")
	public Object findUserBenefits(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		String username = oauth2AuthorizedClient.getPrincipalName();
		
		return this.webClient.get().uri(GET_USER_BENEFITS_BY_USERNAME, username)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(Object.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}
	
	//GET BENEFIT BY ID
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/api/benefits/{id}")
	public Object findBenefitById(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("id") long id) {
		return this.webClient.get().uri(GET_BENEFIT_BY_ID, id)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(Object.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}
	
	//GET EMPLOYEE BASIC INFO BY ID
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@GetMapping("/api/employees/basic-info/{id}")
		public Object findEmployeeBasicInfoById(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@PathVariable("id") long id) {
			return this.webClient.get().uri(GET_EMPLOYEE_BASIC_INFO_BY_ID, id)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().isError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		//POST EMPLOYEE
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PostMapping("/api/employees")
		public Object addEmployee(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @RequestBody EmployeeRequest employeeRequest) {
			return this.webClient.post().uri(POST_EMPLOYEE).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).body(Mono.just(employeeRequest), EmployeeRequest.class)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		
		//POST BENEFIT
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PostMapping("/api/benefits")
		public Object addBenefit(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @RequestBody BenefitRequest benefitRequest) {
			return this.webClient.post().uri(POST_BENEFIT).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).body(Mono.just(benefitRequest), BenefitRequest.class)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		//PUT EMPLOYEE
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PutMapping("/api/employees/{id}")
		public Object editEmployee(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@PathVariable("id") long id, @RequestBody EmployeeRequest employeeRequest) {
			return this.webClient.put().uri(PUT_EMPLOYEE, id).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).body(Mono.just(employeeRequest), EmployeeRequest.class)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		
		//PUT BENEFIT
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PutMapping("/api/benefits/{id}")
		public Object editBenefit(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@PathVariable("id") long id, @RequestBody BenefitRequest benefitRequest) {
			return this.webClient.put().uri(PUT_BENEFIT, id).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).body(Mono.just(benefitRequest), BenefitRequest.class)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		//PATCH EMPLOYEE
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PatchMapping("/api/employees/partial-update/{id}")
		public Object editPartialEmployee(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@PathVariable("id") long id, @RequestBody EmployeeRequest employeeRequest) {
			return this.webClient.patch().uri(PATCH_EMPLOYEE, id).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).body(Mono.just(employeeRequest), EmployeeRequest.class)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		//PATCH BENEFIT
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PatchMapping("/api/benefits/partial-update/{id}")
		public Object editPartialBenefit(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@PathVariable("id") long id, @RequestBody BenefitRequest benefitRequest) {
			return this.webClient.patch().uri(PATCH_BENEFIT, id).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).body(Mono.just(benefitRequest), BenefitRequest.class)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		//PATCH EMPLOYEE - DISABLE EMPLOYEE
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PatchMapping("/api/employees/{id}")
		public Object disableEmployeeById(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@PathVariable("id") long id) {
			return this.webClient.patch().uri(DISABLE_EMPLOYEE, id)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		//PATCH BENEFIT - DISABLE BENEFIT
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PatchMapping("/api/benefits/{id}")
		public Object disableBenefitById(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@PathVariable("id") long id) {
			return this.webClient.patch().uri(DISABLE_BENEFIT, id)
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		//PATCH EMPLOYEE - ADD BENEFI TO ALL EMPLOYEES
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PatchMapping("/api/employees/add-benefit")
		public Object addBenefitToAllEmployees(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@RequestParam(name = "benefit", required = true) Optional<String> benefit) {
			return this.webClient.patch().uri(ADD_BENEFIT_TO_EMPLOYEES,
					uri -> uri.queryParam("benefit", benefit).build())
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
		
		//PATCH EMPLOYEE - REMOVE BENEFI FROM ALL EMPLOYEES
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@PatchMapping("/api/employees/remove-benefit")
		public Object removeBenefitFromAllEmployees(
				@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
				@RequestParam(name = "benefit", required = true) Optional<String> benefit) {
			return this.webClient.patch().uri(REMOVE_BENEFIT_FROM_EMPLOYEES,
					uri -> uri.queryParam("benefit", benefit).build())
					.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
						if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
							return response.bodyToMono(Object.class);
						} else {
							return response.bodyToMono(Object.class);
						}
					}).block();
		}
		
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	public String admin(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/")
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@GetMapping("/manager")
	public String manager(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/")
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user")
	public String user(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/")
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
	
}
