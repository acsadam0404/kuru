package hu.kuru.ui.view;

import hu.kuru.security.Authentication;

import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

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
		VerticalLayout l = new VerticalLayout();
		HorizontalLayout actions = new HorizontalLayout();
		actions.setSizeFull();
		actions.setMargin(true);
		Button logoutButton = new Button("Kijelentkezés", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Authentication.logout("username");
			}
		});
		actions.addComponent(logoutButton);
		actions.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
		l.addComponent(actions);
		l.addComponent(new UserListForWaiter());
		return l;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(build());
		setSizeFull();
	}

}
