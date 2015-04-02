package hu.kuru.ui.view;

import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

/**
 * Fő képernyő dolgozóknak
 * 
 * @author 
 *
 */
@VaadinView(name = MainViewForWaiter.NAME)
public class MainViewForWaiter extends CustomComponent implements View {

	public static final String NAME = "MainViewForWaiter";
	
	private Component build() {
		return new UserListViewForWaiter();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(build());
	}

}
