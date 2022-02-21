package com.bolsadeideas.springboot.api.employees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


@SpringBootApplication
public class SpringEmployeesModuleApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringEmployeesModuleApiApplication.class, args);
	}
	
	@Bean
	public OpenAPI openApiConfig() {
		return new OpenAPI().info(apiInfo());
	}
	
	public Info apiInfo() {
		Info info = new Info();
		info.setTitle("Employees-Benefits API");
		info.setDescription("Microservice to handle the employees and its benefits in a Company. Includes personal information and other related to the Company. Like the position, history of the employee in the company, the benefits that the Company provide.");
		info.setVersion("1.0");
		Contact contact = new Contact();
		contact.setEmail("info@theksquaregroup.com");
		contact.setName("The Ksquare Group");
		contact.setUrl("https://itk.mx/");
		info.setContact(contact);
		return info;
	}
	
}
