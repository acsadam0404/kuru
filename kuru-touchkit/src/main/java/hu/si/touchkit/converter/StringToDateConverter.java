package hu.si.touchkit.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.vaadin.server.VaadinSession;

public class StringToDateConverter extends com.vaadin.data.util.converter.StringToDateConverter {
	private int style;
	private SimpleDateFormat formatter;
	
	/*
	 * Ha a formatter null, akkor a style alapján fog létrehozni egyet
	 */
	@Override
	protected DateFormat getFormat(Locale locale) {
		if (locale == null) {
			locale = VaadinSession.getCurrent().getLocale();
		}

		if (formatter == null) {
			DateFormat f = DateFormat.getDateInstance(style, locale);
			f.setLenient(false);
			return f;
		}
		return formatter;
	}

	/**
	 * DateFormat konstansok megadásával
	 * @param style
	 */
	private StringToDateConverter(int style) {
		this.style = style;
	}
	
	public StringToDateConverter(SimpleDateFormat formatter) {
		this.formatter = formatter;
	}

	public static final StringToDateConverter MEDIUM = new StringToDateConverter(DateFormat.MEDIUM);
	public static final StringToDateConverter FULL = new StringToDateConverter(DateFormat.FULL);
	public static final StringToDateConverter YEAR_TO_MINUTE = new StringToDateConverter(new SimpleDateFormat("yyyy.MM.dd HH:mm"));
	public static final StringToDateConverter SHORT = new StringToDateConverter(DateFormat.SHORT);
}
