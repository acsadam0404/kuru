package hu.kuru.ui

import com.vaadin.ui.Button.ClickEvent;

import org.apache.catalina.connector.ResponseFacade.SetContentTypePrivilegedAction;
import org.codehaus.groovy.transform.trait.SuperCallTraitTransformer;
import org.vaadin.spring.navigator.annotation.VaadinView

import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup
import com.vaadin.addon.touchkit.ui.NavigationView
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup
import com.vaadin.ui.Button
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component
import com.vaadin.ui.Label
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout

import hu.kuru.security.Authentication

@VaadinView(name = LoginView.NAME)
class LoginView extends NavigationView {
	public static final String NAME = "LoginView"
	
	private Authentication authentication
	
	LoginView() {
		authentication = new Authentication()
		setContent(build())
	}
	
	
	private Component build() {
		setSizeFull()
		VerticalLayout layout = new VerticalLayout() 
		VerticalComponentGroup vertCompGroupGuest = new VerticalComponentGroup()
		vertCompGroupGuest.addComponent(new Label("Ügyfél bejelentkezés"))
		vertCompGroupGuest.addComponent(new TextField("Kód:"))
		HorizontalButtonGroup buttons = new HorizontalButtonGroup()
		Button guestLoginButton = new Button("Bejelentkezés")
		guestLoginButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				authentication.login(username.getValue(), password.getValue())
			}
		});
		buttons.addComponent(guestLoginButton)
		vertCompGroupGuest.addComponent(buttons)
		VerticalComponentGroup vertCompGroupWaiter = new VerticalComponentGroup()
		vertCompGroupWaiter.addComponent(new Label("Alkalmazott bejelentkezés"))
		TextField waiterUserNameField = new TextField("Felhasználónév:")
		vertCompGroupWaiter.addComponent(waiterUserNameField)
		PasswordField waiterPasswordField = new PasswordField("Jelszó:")
		vertCompGroupWaiter.addComponent(waiterPasswordField)
		layout.addComponent(vertCompGroupGuest)
		layout.addComponent(vertCompGroupWaiter)
		HorizontalButtonGroup buttonsForWaiter = new HorizontalButtonGroup()
		Button waiterLoginButton = new Button("Bejelentkezés")
		buttonsForWaiter.addComponent(waiterLoginButton)
		waiterLoginButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				authentication.login(waiterUserNameField.getValue(), waiterPasswordField.getValue())
			}
		});
		vertCompGroupWaiter.addComponent(buttonsForWaiter)
		return layout
	}
}
