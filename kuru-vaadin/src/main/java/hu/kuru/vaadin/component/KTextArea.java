package hu.kuru.vaadin.component;

import com.vaadin.ui.TextArea;

/**
 * @deprecated aspectel lesz megoldva, ne készüljön több ilyen osztály
 */
@Deprecated
public class KTextArea extends TextArea {

	public KTextArea(String caption) {
		super(caption);
		setNullRepresentation("");
		setSizeFull();
	}

}
