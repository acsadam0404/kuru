package hu.si.vaadin.converter;

import java.util.Locale;

import com.vaadin.server.VaadinSession;

public class StringToIntegerConverter extends AbstractCustomizableStringToNumberConverter<Integer> {

	public StringToIntegerConverter(String formatString) {
		super(formatString);
	}

	public static StringToIntegerConverter instance() {
		return instance;
	}

	@Override
	public Integer convertToModel(String value, Class<? extends Integer> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value != null && value.contains(" ")) {
			value = value.replace(" ", "");
		}
		Number num = super.convertToNumber(value, targetType, locale);
		if (num != null && !(num instanceof Integer)) {
			num = Integer.parseInt(num.toString());
		}

		return (Integer) num;
	}

	public String convertToPresentation(Integer value) throws ConversionException {
		return super.convertToPresentation(value, String.class, VaadinSession.getCurrent().getLocale());
	}
	
	@Override
	public Class<Integer> getModelType() {
		return Integer.class;
	}

	private static final StringToIntegerConverter instance = new StringToIntegerConverter();

	private StringToIntegerConverter() {
		/* singleton */
	}

	public static StringToIntegerConverter getInstance() {
		return instance;
	}

}
