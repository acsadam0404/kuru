package hu.kuru;

import hu.kuru.vaadin.security.Authentication;
import hu.kuru.vaadin.security.LoginEvent;
import hu.kuru.vaadin.summary.SummaryView;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("kurutheme")
@Scope("prototype")
@Widgetset("hu.kuru.AppWidgetSet")
@org.springframework.stereotype.Component("MainUI")
public class MainUI extends UI {
	private static final Logger logger = LoggerFactory.getLogger(MainUI.class);

	private final HorizontalLayout main = new HorizontalLayout();
	private final UIEventBus eventbus = new UIEventBus();
	private Authentication authentication;

	public MainUI() {
		super();
		Locale locale = new Locale("hu", "HU");
		setLocale(locale);
	}

	@Override
	protected void init(VaadinRequest request) {
		UIEventBus.register(this);
		authentication = new Authentication();
		init();
	}

	private void init() {
		if (!authentication.isAuthenticated()) {
			setupLoginScreen();
		} else {
			setupMainScreen();
		}
	}

	private void setupMainScreen() {
		main.setSizeFull();
		setSizeFull();

		Panel contentPanel = new Panel();
		contentPanel.setSizeFull();
		VerticalLayout content = new VerticalLayout();
		setNavigator(setupNavigator(content));
		content.setSizeFull();
		Menu menu = new Menu();
		main.addComponent(menu);
		contentPanel.setContent(content);
		main.addComponent(contentPanel);
		menu.setSizeFull();
		menu.setWidth("300px");
		main.setExpandRatio(contentPanel, 1.0f);

		setErrorHandler(new DefaultErrorHandler() {
			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				UIExceptionHandler.handleException(event.getThrowable());
			}
		});

		getNavigator().navigateTo(SummaryView.NAME);
		setContent(main);
	}

	@Subscribe
	public void handleLoginEvent(LoginEvent loginEvent) {
		init();
	}

	private void setupLoginScreen() {
		setContent(new LoginScreen(authentication));
	}

	private Navigator setupNavigator(ComponentContainer content) {
		Navigator navigator = new Navigator(this, content);
		return navigator;
	}

	@Override
	public Navigator getNavigator() {
		return (Navigator) super.getNavigator();
	}

	public static UIEventBus getEventbus() {
		return ((MainUI) getCurrent()).eventbus;
	}

}
