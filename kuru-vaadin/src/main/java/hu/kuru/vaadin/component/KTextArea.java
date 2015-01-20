package hu.kuru.vaadin.component;

import com.vaadin.ui.TextArea;

public class KTextArea extends TextArea {

	public KTextArea(String caption) {
		super(caption);
		setNullRepresentation("");
		setSizeFull();
	}

}
