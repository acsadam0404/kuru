package hu.si.touchkit.converter;

import java.math.BigDecimal;
import java.util.Date;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;

public class SIConverterFactory extends DefaultConverterFactory {
	@Override
	protected Converter<String, ?> createStringConverter(Class<?> sourceType) {
		if (BigDecimal.class.isAssignableFrom(sourceType)) {
			return StringToBigDecimalConverter.getInstance();
		}
		if (Boolean.class.isAssignableFrom(sourceType) || boolean.class.isAssignableFrom(sourceType)) {
			return StringToBooleanConverter.getInstance();
		}
		if (Date.class.isAssignableFrom(sourceType)) {
			return StringToDateConverter.MEDIUM;
		}
		return super.createStringConverter(sourceType);
	}
}
