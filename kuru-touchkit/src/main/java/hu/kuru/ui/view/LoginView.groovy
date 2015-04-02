package hu.kuru.ui.view

import groovy.transform.TypeChecked
import hu.kuru.security.Authentication

import org.vaadin.spring.navigator.annotation.VaadinView

import com.vaadin.addon.touchkit.ui.NavigationView
import com.vaadin.server.Responsive
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.Label
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.themes.ValoTheme

@TypeChecked
@VaadinView(name = LoginView.NAME)
class LoginView extends NavigationView {
	public static final String NAME = "LoginView"

	private Authentication authentication

	LoginView() {
		super("KURU")
		Responsive.makeResponsive(this)
		setSizeFull()
		authentication = new Authentication()
		setContent(build())
	}
	
	private Component build() {
		def layout = new VerticalLayout()
		layout.setMargin(true)
		layout.setSizeFull()

		def guestComp = buildGuestComp()
		layout.addComponent(guestComp)
		def waiterComp = buildWaiterComp()
		layout.addComponent(waiterComp)

		layout.setComponentAlignment(guestComp, Alignment.MIDDLE_CENTER)
		layout.setComponentAlignment(waiterComp, Alignment.MIDDLE_CENTER)

		return layout
	}

	private Component buildGuestComp() {
		//Vendég bejelentkezés
		def l = new VerticalLayout()
		l.setWidth("80%")
		l.setSpacing(true)
		def customerLoginLabel = new Label("Vendég bejelentkezés")
		customerLoginLabel.setStyleName(ValoTheme.LABEL_H1)

		l.addComponent(customerLoginLabel)
		TextField customerCodeField = new TextField("Kód")
		customerCodeField.setStyleName(ValoTheme.TEXTFIELD_HUGE)
		l.addComponent(customerCodeField)
		Button loginButton = new Button("Bejelentkezés")
		loginButton.addClickListener((ClickListener){ e ->
			authentication.loginByCustomer(customerCodeField.getValue())
		});
		l.addComponent(loginButton)

		for (Component c : l) {
			c.setSizeFull()
		}
		return l
	}

	private Component buildWaiterComp() {
		def l = new VerticalLayout()
		l.setWidth("80%")
		l.setSpacing(true)
		
		def waiterLabel = new Label("Alkalmazott bejelentkezés")
		waiterLabel.setStyleName(ValoTheme.LABEL_H1)
		l.addComponent(waiterLabel)
		def waiterUserNameField = new TextField("Felhasználónév")
		waiterUserNameField.setStyleName(ValoTheme.TEXTFIELD_HUGE)
		l.addComponent(waiterUserNameField)
		def waiterPasswordField = new PasswordField("Jelszó")
		waiterPasswordField.setStyleName(ValoTheme.TEXTFIELD_HUGE)
		l.addComponent(waiterPasswordField)

		Button loginButton = new Button("Bejelentkezés")
		loginButton.addClickListener((ClickListener) { e->
			authentication.login(waiterUserNameField.value, waiterPasswordField.value)
		});
		l.addComponent(loginButton)
		for (Component c : l) {
			c.setSizeFull()
		}
		return l
	}
}
