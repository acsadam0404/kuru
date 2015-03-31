package hu.si.touchkit.converter;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;


public class StringToClassConverter implements Converter<String, Class> {

	@Override
	public Class convertToModel(String value, Class<? extends Class> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value == null) {
			return null;
		}
		try {
			return Class.forName(value);
		}
		catch (ClassNotFoundException e) {
			throw new ConversionException("Class not found: \"" + value + "\"");
		}
	}


	@Override
	public String convertToPresentation(Class value, Class<? extends String> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value == null) {
			return null;
		}
		return value.getName();
	}


	@Override
	public Class<Class> getModelType() {
		return Class.class;
	}


	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}
	
	private static final StringToClassConverter instance = new StringToClassConverter();

	private StringToClassConverter() {
		/* singleton */
	}

	public static StringToClassConverter getInstance() {
		return instance;
	}


}
