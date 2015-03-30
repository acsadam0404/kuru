package hu.kuru.ui.component;

import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

/**
 * Kereső mező
 * 
 * @author
 */
public class SearchField extends CustomComponent {

	TextField searchField;

	public SearchField() {
		searchField = new TextField();
		searchField.setImmediate(true);
		searchField.setWidth("300px");
		setCompositionRoot(build());
	}

	private Component build() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.addComponent(new Label("Kereső:"));
		layout.addComponent(searchField);
		return layout;
	}

	public void addTextChangeListener(TextChangeListener listener) {
		searchField.addTextChangeListener(listener);
	}

}