package hu.si.vaadin.converter;

public final class StringToBooleanConverter extends com.vaadin.data.util.converter.StringToBooleanConverter {
	@Override
	protected String getTrueString() {
		return "Igen";
	}

	@Override
	protected String getFalseString() {
		return "Nem";
	}
	
	private static final StringToBooleanConverter instance = new StringToBooleanConverter();

	private StringToBooleanConverter() {
		/* singleton */
	}

	public static StringToBooleanConverter getInstance() {
		return instance;
	}
}
