package hu.si.touchkit.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.AbstractStringToNumberConverter;
import com.vaadin.server.VaadinSession;


public abstract class AbstractCustomizableStringToNumberConverter<T> extends AbstractStringToNumberConverter<T> {
	public static final String FORMAT_MONETARY = "#,##0";
	public static final String FORMAT_NONE = "#";
	public static final String FORMAT_QUANTITY = "#,##0.000";
	
	private DecimalFormat customFormat;

	
	public AbstractCustomizableStringToNumberConverter() {
		/* amikor ez példányosodik először, még nincs session és nem érjük el a locale-t, de a FORMAT_NONE esetén nem is kell */
		customFormat = new DecimalFormat(FORMAT_NONE);
	}


	public AbstractCustomizableStringToNumberConverter(String formatMask) {
		customFormat = new DecimalFormat(formatMask, new DecimalFormatSymbols(VaadinSession.getCurrent().getLocale()));
	}

	@Override
	protected NumberFormat getFormat(Locale locale) {
		if (locale == null) {
			locale = VaadinSession.getCurrent().getLocale();
		}
		if (customFormat != null) {
			return customFormat;
		}
		return super.getFormat(locale);
	}


	public void setFormatMask(String formatMask) {
		if (formatMask != null) {
			customFormat = new DecimalFormat(formatMask, new DecimalFormatSymbols(VaadinSession.getCurrent().getLocale()));
		}
	}

}
