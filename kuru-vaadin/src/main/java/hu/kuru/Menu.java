package hu.kuru;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextField;


public class Menu extends CustomComponent {

	private static final long serialVersionUID = 1L;

	public Menu() {
		VerticalLayout main = new VerticalLayout();
		main.addComponent(new Label("menü"));
		main.setStyleName("menu");
		setCompositionRoot(main);
	}
}
