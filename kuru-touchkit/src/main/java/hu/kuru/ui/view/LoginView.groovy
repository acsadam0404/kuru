package hu.kuru.ui.view

import hu.kuru.security.Authentication

import org.vaadin.spring.navigator.annotation.VaadinView

import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup
import com.vaadin.addon.touchkit.ui.NavigationManager
import com.vaadin.addon.touchkit.ui.NavigationView
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.Label
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener

/**
 * Bejelentkező felületet megvalósító osztály
 * 
 * @author 
 *
 */
@VaadinView(name = LoginView.NAME)
class LoginView extends NavigationView {
	public static final String NAME = "LoginView"

	private Authentication authentication

	/**
	 * Konstruktor a Login képernyőt megvalósító objektum létrehozásához
	 */
	LoginView() {
		super("KURU")
		authentication = new Authentication()
		setContent(build())
	}

	/**
	 * Tartalom létrehozását végző függvény
	 * 
	 * @author
	 *
	 */
	private Component build() {
		setSizeFull()

		def layout = new VerticalLayout()
		layout.setWidth("100%");
		layout.setMargin(true)

		//Vendég bejelentkezés
		VerticalComponentGroup vertCompGroupGuest = new VerticalComponentGroup()
		vertCompGroupGuest.setWidth("80%")
		Label customerLoginLabel = new Label("Vendég bejelentkezés")
		customerLoginLabel.setStyleName("loginTitle")
		vertCompGroupGuest.addComponent(customerLoginLabel)
		TextField customerCodeField = new TextField("Kód:")
		vertCompGroupGuest.addComponent(customerCodeField)
		HorizontalButtonGroup buttons = new HorizontalButtonGroup()
		Button guestLoginButton = new Button("Bejelentkezés")
		guestLoginButton.setStyleName("loginButton")
		guestLoginButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(final ClickEvent event) {
						authentication.loginByCustomer(customerCodeField.getValue())
					}
				});
		buttons.addComponent(guestLoginButton)
		vertCompGroupGuest.addComponent(buttons)

		//dolgozó bejelentkezés
		VerticalComponentGroup vertCompGroupWaiter = new VerticalComponentGroup()
		vertCompGroupWaiter.setWidth("80%")
		Label waiterLoginLabel = new Label("Alkalmazott bejelentkezés")
		waiterLoginLabel.setStyleName("loginTitle")
		vertCompGroupWaiter.addComponent(waiterLoginLabel)
		TextField waiterUserNameField = new TextField("Felhasználónév:")
		vertCompGroupWaiter.addComponent(waiterUserNameField)
		PasswordField waiterPasswordField = new PasswordField("Jelszó:")
		vertCompGroupWaiter.addComponent(waiterPasswordField)

		HorizontalButtonGroup buttonsForWaiter = new HorizontalButtonGroup()
		Button waiterLoginButton = new Button("Bejelentkezés")
		waiterLoginButton.setStyleName("loginButton")
		buttonsForWaiter.addComponent(waiterLoginButton)
		waiterLoginButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(final ClickEvent event) {
						authentication.login(waiterUserNameField.getValue(), waiterPasswordField.getValue())
					}
				});
		vertCompGroupWaiter.addComponent(buttonsForWaiter)

		layout.addComponent(vertCompGroupGuest)
		layout.addComponent(vertCompGroupWaiter)

		layout.setComponentAlignment(vertCompGroupGuest, Alignment.TOP_CENTER)
		layout.setComponentAlignment(vertCompGroupWaiter, Alignment.TOP_CENTER)

		return layout
	}
}
