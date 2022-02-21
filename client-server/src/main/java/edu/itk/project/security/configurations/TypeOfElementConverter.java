package edu.itk.project.security.configurations;

import org.springframework.core.convert.converter.Converter;
import edu.itk.project.security.enums.TypeOfElement;

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