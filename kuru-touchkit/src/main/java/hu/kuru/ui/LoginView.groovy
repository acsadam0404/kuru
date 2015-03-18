package hu.kuru.ui

import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup
import com.vaadin.addon.touchkit.ui.NavigationButton
import com.vaadin.addon.touchkit.ui.NavigationView
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup
import com.vaadin.ui.Button
import com.vaadin.ui.Label
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout

class LoginView extends NavigationView {
	
	@Override
	public void attach() {
		super.attach()
		buildContent()
	}
	
	private void buildContent() {
		setSizeFull()
		VerticalLayout layout = new VerticalLayout() 
		VerticalComponentGroup vertCompGroupGuest = new VerticalComponentGroup()
		vertCompGroupGuest.addComponent(new Label("Ügyfél bejelentkezés"))
		vertCompGroupGuest.addComponent(new TextField("Kód:"))
		HorizontalButtonGroup buttons = new HorizontalButtonGroup()
		buttons.addComponent(new Button("Bejelentkezés"))
		vertCompGroupGuest.addComponent(buttons)
		VerticalComponentGroup vertCompGroupWaiter = new VerticalComponentGroup()
		vertCompGroupWaiter.addComponent(new Label("Pincér bejelentkezés"))
		vertCompGroupWaiter.addComponent(new TextField("Felhasználónév:"))
		vertCompGroupWaiter.addComponent(new PasswordField("Jelszó:"))
		layout.addComponent(vertCompGroupGuest)
		layout.addComponent(vertCompGroupWaiter)
		HorizontalButtonGroup buttonsForWaiter = new HorizontalButtonGroup()
		buttonsForWaiter.addComponent(new Button("Bejelentkezés"))
		vertCompGroupWaiter.addComponent(buttonsForWaiter)
		setContent(layout)
	}
}
