package com.bolsadeideas.springboot.api.employees.config;

//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	/*@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TypeOfElementConverter());
        registry.addConverter(new GenderConverter());
        registry.addConverter(new PositionConverter());
    }
	
}
