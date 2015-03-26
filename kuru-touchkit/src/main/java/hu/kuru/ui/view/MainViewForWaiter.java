package hu.kuru.ui.view;

import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.ui.Component;

/**
 * Fő képernyő dolgozóknak
 * 
 * @author 
 *
 */
@VaadinView(name = MainViewForWaiter.NAME)
public class MainViewForWaiter extends NavigationManager {

	public static final String NAME = "MainViewForWaiter";
	
	private NavigationManager manager;
	
	public MainViewForWaiter() {
		navigateTo(build());
	}
	
	private Component build() {
		manager = new NavigationManager();
		manager.setSizeFull();
		UserListViewForWaiter mainView = new UserListViewForWaiter(manager);
		manager.navigateTo(mainView);
		return manager;
	}

}
