package hu.kuru

import hu.kuru.security.Authentication
import hu.kuru.ui.LoginView
import hu.kuru.ui.MainViewForCustomer
import hu.kuru.ui.MainViewForWaiter
import hu.kuru.ui.UIEventBus;
import hu.kuru.ui.event.LoginEvent

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.addon.touchkit.ui.NavigationButton
import com.vaadin.addon.touchkit.ui.NavigationManager
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
@Theme("kurutickettheme")
@Widgetset("hu.kuru.Widgetset")
@TouchKitUI
class KuruUI extends UI{

	private final SpringViewProvider viewProvider
	final UIEventBus eventbus = new UIEventBus()
	private Authentication authentication

	@Override
	protected void init(VaadinRequest request) {
		UIEventBus.register(this);
		authentication = new Authentication()
		def root = new VerticalLayout();
		this.getPage().setTitle("KURU")
		root.with {
			setMargin(true)
			setSpacing(true)
			setSizeFull();
		}
		initContent();
	}

	static KuruUI getCurrent() {
		super.getCurrent()
	}

	/**
	 * Login event
	 * 
	 * @param loginEvent
	 */
	@Subscribe
	void handleLoginEventForWaiter(LoginEvent loginEvent) {
		if(loginEvent.getUsername() == null && loginEvent.getCustomerCode() == null) {
			this.setContent(new LoginView())
		} else {
			this.initContent()
		}
	}

	/**
	 * Tartalom inicializálását végző függvény
	 */
	void initContent() {
		if(!authentication.isAuthenticated() && !authentication.isAuthenticatedByCustomer()) {
			setContent(new LoginView())
		} else if(authentication.isAuthenticated()) {
			setContent(new MainViewForWaiter())
		} else if(authentication.isAuthenticatedByCustomer()) {
			setContent(new MainViewForCustomer())
		}
	}
}
