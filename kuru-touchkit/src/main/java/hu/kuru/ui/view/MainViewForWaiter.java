package hu.kuru.ui.view;

import hu.kuru.security.Authentication;

import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

/**
 * Főképernyő a dolgozóknak
 * 
 * @author 
 *
 */
@VaadinView(name = MainViewForWaiter.NAME)
public class MainViewForWaiter extends NavigationView {

	public static final String NAME = "MainViewForWaiter";
	
	private VerticalComponentGroup content;
	private Authentication authentication;
	
	public MainViewForWaiter() {
		setContent(build());
		authentication = new Authentication();
		this.setRightComponent(createLogoutButton());
	}
	
	private Component build() {
		content = new VerticalComponentGroup();
		return content;
	}
	
	/**
	 * Logout gomb felépítését végző függvény
	 * 
	 * @return
	 */
	private Component createLogoutButton() {
		Button logoutButton = new Button("Kijelentkezés");
		
		logoutButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				authentication.logout("username");
			}
		});
		
		return logoutButton;
	}

}
