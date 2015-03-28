package hu.kuru.ui.component;

import com.vaadin.ui.TextField;

/**
 * Kereső mező
 * 
 * @author 
 */
public class SearchField extends TextField {
	
	public SearchField(String caption) {
		super(caption);
		setImmediate(true);
		setWidth("300px");
	}


}