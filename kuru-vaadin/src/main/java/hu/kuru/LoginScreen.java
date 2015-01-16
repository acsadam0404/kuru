package hu.kuru;

import hu.kuru.vaadin.security.Authentication;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public class LoginScreen extends CustomComponent {

	private Authentication authentication;

	public LoginScreen(Authentication authentication) {
		this.authentication = authentication;
		setSizeFull();
		
		Component loginForm = buildLoginForm();
		VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.addComponent(loginForm);
		root.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		setCompositionRoot(root);
	}

	private Component buildLoginForm() {
		final VerticalLayout loginPanel = new VerticalLayout();
		loginPanel.setSizeUndefined();
		loginPanel.setSpacing(true);
		Responsive.makeResponsive(loginPanel);
		loginPanel.addStyleName("login-panel");

		CheckBox box = new CheckBox("Emlékezz rám", true);
		
		loginPanel.addComponent(buildFields());
		loginPanel.addComponent(box);
		loginPanel.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		return loginPanel;
	}

	private Component buildFields() {
		VerticalLayout fields = new VerticalLayout();
		fields.setSpacing(true);
		fields.addStyleName("fields");

		final TextField username = new TextField();
		username.setInputPrompt("Felhasználónév");
		username.setIcon(FontAwesome.USER);
		username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final PasswordField password = new PasswordField();
		password.setInputPrompt("Jelszó");
		password.setIcon(FontAwesome.LOCK);
		password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final Button signin = new Button("Bejelentkezés");
		signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
		signin.setClickShortcut(KeyCode.ENTER);
		signin.focus();

		fields.addComponents(username, password, signin);
		fields.setComponentAlignment(signin, Alignment.MIDDLE_CENTER);

		signin.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				authentication.login(username.getValue(), password.getValue());
			}
		});
		return fields;
	}
}
