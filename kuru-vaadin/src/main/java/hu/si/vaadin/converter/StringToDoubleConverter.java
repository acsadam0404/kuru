package hu.si.vaadin.converter;

import java.util.Locale;

import com.vaadin.server.VaadinSession;

public class StringToDoubleConverter extends AbstractCustomizableStringToNumberConverter<Double> {

	public StringToDoubleConverter(String formatString) {
		super(formatString);
	}

	public static StringToDoubleConverter instance() {
		return instance;
	}

	@Override
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value != null && value.contains(" ")) {
			value = value.replace(" ", "");
		}
		Number num = super.convertToNumber(value, targetType, locale);
		if (num != null && !(num instanceof Double)) {
			num = Double.parseDouble(num.toString());
		}

		return (Double) num;
	}

	public String convertToPresentation(Double value) throws ConversionException {
		return super.convertToPresentation(value, String.class, VaadinSession.getCurrent().getLocale());
	}
	
	@Override
	public Class<Double> getModelType() {
		return Double.class;
	}

	private static final StringToDoubleConverter instance = new StringToDoubleConverter();

	private StringToDoubleConverter() {
		/* singleton */
	}

	public static StringToDoubleConverter getInstance() {
		return instance;
	}

}
