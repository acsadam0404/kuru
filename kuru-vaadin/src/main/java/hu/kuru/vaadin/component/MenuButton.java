package hu.kuru.vaadin.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

public class MenuButton extends Button {

	private static final long serialVersionUID = 1L;

	private MenuButton(String caption, final String viewName) {
		super(caption);
		setWidth("200px");
		addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(viewName);
			}
		});
	}

	public static MenuButton fromCaptionAndView(String caption, String viewName) {
		return new MenuButton(caption, viewName);
	}

}
