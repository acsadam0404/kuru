package hu.kuru.vaadin.component.button;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public class MenuButton extends Button {
	private MenuButton(String caption, final String viewName, FontAwesome icon) {
		super(caption);
		setWidth("200px");
		setIcon(icon);
		setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		addStyleName(ValoTheme.BUTTON_HUGE);
		addStyleName("menubutton");
		addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(viewName);
			}
		});
	}

	public static MenuButton fromCaptionViewIcon(String caption, String viewName, FontAwesome icon) {
		return new MenuButton(caption, viewName, icon);
	}

}
