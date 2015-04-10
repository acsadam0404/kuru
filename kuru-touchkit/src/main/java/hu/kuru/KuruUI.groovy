package hu.kuru

import hu.kuru.security.Authentication
import hu.kuru.ui.UIEventBus
import hu.kuru.ui.event.LoginEvent
import hu.kuru.ui.view.LoginView
import hu.kuru.ui.view.MainViewForCustomer
import hu.kuru.ui.view.MainViewForWaiter

import org.springframework.beans.factory.annotation.Autowired
import org.vaadin.spring.annotation.VaadinUI
import org.vaadin.spring.navigator.SpringViewProvider

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Widgetset
import com.vaadin.navigator.Navigator
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Panel
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme

@VaadinUI
@Theme("valo")
@Widgetset("hu.kuru.Widgetset")
class KuruUI extends UI{

	private final SpringViewProvider viewProvider
	final UIEventBus eventbus = new UIEventBus()

	@Autowired
	KuruUI(SpringViewProvider viewProvider) {
		this.viewProvider = viewProvider
	}

	@Override
	protected void init(VaadinRequest request) {
		UIEventBus.register(this);
		getPage().setTitle("KURU")

		def panel = new Panel()
		panel.setSizeFull()
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS)
		def root = new VerticalLayout();
		root.with {
			setSpacing(true)
			setSizeFull();

			def viewContainer = new Panel();
			viewContainer.setSizeFull();
			addComponent(viewContainer);
			setExpandRatio(viewContainer, 1.0f);

			Navigator navigator = new TouchkitNavigator(this, viewContainer);
			navigator.addProvider(viewProvider);
			setNavigator(navigator)
		}

		panel.setContent(root)
		setContent(panel)
		initContent();
	}

	static KuruUI getCurrent() {
		super.getCurrent()
	}

	@Override
	TouchkitNavigator getNavigator() {
		return super.navigator
	}

	@Subscribe
	void handleLoginEventForWaiter(LoginEvent loginEvent) {
		if(loginEvent.getUsername() == null && loginEvent.getCustomerCode() == null) {
			navigator.navigateTo(LoginView.NAME)
		} else {
			initContent()
		}
	}

	void initContent() {
		if(!Authentication.isAuthenticated() && !Authentication.isAuthenticatedByCustomer()) {
			navigator.navigateTo(LoginView.NAME)
		}
		else if(Authentication.isAuthenticated()) {
			navigator.navigateTo(MainViewForWaiter.NAME)
		}
		else if(Authentication.isAuthenticatedByCustomer()) {
			navigator.navigateTo(MainViewForCustomer.NAME)
		}
	}
}
