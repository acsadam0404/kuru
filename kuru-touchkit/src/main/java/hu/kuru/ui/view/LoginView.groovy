package hu.kuru.ui.view

import groovy.transform.TypeChecked
import hu.kuru.security.Authentication

import org.springframework.context.annotation.Scope
import org.vaadin.spring.navigator.annotation.VaadinView

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.server.Responsive
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.CustomComponent
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.themes.ValoTheme

@Scope("prototype")
@TypeChecked
@VaadinView(name = LoginView.NAME)
class LoginView extends CustomComponent implements View {
	public static final String NAME = "LoginView"

	LoginView() {
		setCompositionRoot(build())
		Responsive.makeResponsive(this)
		setSizeFull()
	}

	private Component build() {
		def panel = new Panel()
		panel.setSizeFull()

		def layout = new VerticalLayout()
		layout.setSizeFull()
		layout.setHeight("80%")

		def guestComp = buildGuestComp()
		layout.addComponent(guestComp)
		def waiterComp = buildWaiterComp()
		layout.addComponent(waiterComp)

		layout.setComponentAlignment(guestComp, Alignment.MIDDLE_CENTER)
		layout.setComponentAlignment(waiterComp, Alignment.MIDDLE_CENTER)

		panel.setContent(layout)
		return panel
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
			Authentication.loginByCustomer(customerCodeField.getValue())
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
			Authentication.login(waiterUserNameField.value, waiterPasswordField.value)
		});
		l.addComponent(loginButton)
		for (Component c : l) {
			c.setSizeFull()
		}
		return l
	}

	@Override
	public void enter(ViewChangeEvent event) {
		//		setCompositionRoot(build())
		//		Responsive.makeResponsive(this)
		//		setSizeFull()
	}
}
