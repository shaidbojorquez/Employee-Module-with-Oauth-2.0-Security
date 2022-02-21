package com.bolsadeideas.springboot.api.employees.config;

import org.springframework.core.convert.converter.Converter;

import com.bolsadeideas.springboot.api.employees.enums.Position;

public class PositionConverter implements Converter<String, Position>{

	@Override
	public Position convert(String source) {
		try {
			return Position.valueOf(source.toUpperCase());
		}catch(IllegalArgumentException e) {
			 return null;
		}
	}

}
