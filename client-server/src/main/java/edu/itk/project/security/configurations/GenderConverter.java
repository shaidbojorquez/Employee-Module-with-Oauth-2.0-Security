package edu.itk.project.security.configurations;

import org.springframework.core.convert.converter.Converter;
import edu.itk.project.security.enums.Gender;

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