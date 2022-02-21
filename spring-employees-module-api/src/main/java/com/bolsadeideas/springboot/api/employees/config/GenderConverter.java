package com.bolsadeideas.springboot.api.employees.config;

import org.springframework.core.convert.converter.Converter;

import com.bolsadeideas.springboot.api.employees.enums.Gender;

public class GenderConverter implements Converter<String, Gender>{

	@Override
	public Gender convert(String source) {
		try {
			return Gender.valueOf(source.toUpperCase());
		}catch(IllegalArgumentException e) {
			 return null;
		}
	}

}
