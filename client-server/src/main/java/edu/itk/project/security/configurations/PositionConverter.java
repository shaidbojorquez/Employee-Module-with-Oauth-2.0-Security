package edu.itk.project.security.configurations;

import org.springframework.core.convert.converter.Converter;
import edu.itk.project.security.enums.Position;

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