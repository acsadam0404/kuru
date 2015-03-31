package hu.si.touchkit.converter;


import java.util.Locale;

import com.vaadin.server.VaadinSession;

public class StringToLongConverter extends AbstractCustomizableStringToNumberConverter<Long> {

	public StringToLongConverter(String formatString) {
		super(formatString);
	}

	@Override
	public Long convertToModel(String value, Class<? extends Long> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value != null && value.contains(" ")) {
			value = value.replace(" ", "");
		}
		Number num = super.convertToNumber(value, targetType, locale);
		if (num != null && !(num instanceof Long)) {
			num = Long.parseLong(num.toString());
		}

		return (Long) num;
	}

	public String convertToPresentation(Long value) throws ConversionException {
		return super.convertToPresentation(value, String.class, VaadinSession.getCurrent().getLocale());
	}
	
	@Override
	public Class<Long> getModelType() {
		return Long.class;
	}

	private static final StringToLongConverter instance = new StringToLongConverter();

	private StringToLongConverter() {
		/* singleton */
	}

	public static StringToLongConverter getInstance() {
		return instance;
	}

}
