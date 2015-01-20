package hu.kuru.vaadin.component;

import com.vaadin.ui.TextField;

public class KTextField extends TextField {

	public KTextField(String caption) {
		super(caption);
		setNullRepresentation("");
		setImmediate(true);
	}

}
