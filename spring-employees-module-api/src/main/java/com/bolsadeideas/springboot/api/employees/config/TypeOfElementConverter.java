package com.bolsadeideas.springboot.api.employees.config;

import org.springframework.core.convert.converter.Converter;

import com.bolsadeideas.springboot.api.employees.enums.TypeOfElement;

public class TypeOfElementConverter implements Converter<String, TypeOfElement> {

	@Override
	public TypeOfElement convert(String source) {
		try {
			return TypeOfElement.valueOf(source.toUpperCase());
		}catch(IllegalArgumentException e) {
			 return null;
		}
	}

}
