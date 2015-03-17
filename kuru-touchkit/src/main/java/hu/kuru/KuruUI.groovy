package hu.kuru

import hu.kuru.ui.UIEventBus;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Widgetset
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout

import org.vaadin.spring.annotation.VaadinUI
import org.vaadin.spring.navigator.SpringViewProvider
import org.vaadin.spring.touchkit.annotation.TouchKitUI

@VaadinUI
@Theme("valo")
@Widgetset("hu.kuru.Widgetset")
@TouchKitUI
class KuruUI extends UI{
	private final SpringViewProvider viewProvider
	
	final UIEventBus eventbus = new UIEventBus()

	@Override
	protected void init(VaadinRequest request) {
		UIEventBus.register(this);
		def root = new VerticalLayout();
		this.getPage().setTitle("KURU")
		root.with {
			setMargin(true)
			setSpacing(true)
			setSizeFull();
		}
		//TODO ez nem itt lesz!
		def guestCompGroup = new VerticalComponentGroup("Bejelentkezés vendégként")
		def guestCodeField = new TextField("Kód: ")
		def guestLoginButton = new Button("Bejelentkezés");
		guestCompGroup.addComponents(guestCodeField, guestLoginButton)
		def waiterCompGroup = new VerticalComponentGroup("Bejelentkezés pincérként")
		def waiterUserName = new TextField("Felhasználónév:")
		def waiterPassword = new PasswordField("Jelszó:")
		def waiterLoginButton = new Button("Bejelentkezés")
		waiterCompGroup.addComponents(waiterUserName,waiterPassword,waiterLoginButton)
		root.addComponents(guestCompGroup, waiterCompGroup)
		root.setComponentAlignment(guestCompGroup, Alignment.MIDDLE_CENTER)
		root.setComponentAlignment(waiterCompGroup, Alignment.MIDDLE_CENTER)
		setContent(root)
	}


	static KuruUI getCurrent() {
		super.getCurrent()
	}
}
