package hu.kuru.ui;

import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

@VaadinView(name = MainView.NAME)
public class MainView extends NavigationView {

	public static final String  NAME = "MainView";
	
	private VerticalComponentGroup content;
	
	public MainView() {
		setContent(build());
	}
	
	private Component build() {
		content = new VerticalComponentGroup();
		content.addComponent(new Label("Main"));
		return content;
	}
	
}
