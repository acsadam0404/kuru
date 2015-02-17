package hu.kuru.vaadin.component;

import com.vaadin.ui.TextField;

/**
 * @deprecated aspectel lesz megoldva, ne készüljön több ilyen osztály
 */
@Deprecated
public class KTextField extends TextField {

	public KTextField(String caption) {
		super(caption);
		setNullRepresentation("");
		setImmediate(true);
	}

}
