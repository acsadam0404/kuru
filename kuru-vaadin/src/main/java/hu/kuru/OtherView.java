package hu.kuru;

import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;

public class OtherView extends AbstractView implements View {
	public static final String NAME = "other";


	public OtherView() {

	}


	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(new Label("other view"));
	}
}
