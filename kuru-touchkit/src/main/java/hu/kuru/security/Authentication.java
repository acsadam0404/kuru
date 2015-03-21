package hu.kuru.security;

import java.util.List;

import org.springframework.ui.context.support.UiApplicationContextUtils;
import org.vaadin.spring.annotation.VaadinUI;

import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import hu.kuru.customer.Customer;
import hu.kuru.ui.UIEventBus;
import hu.kuru.ui.event.LoginEvent;
import hu.kuru.user.User;

public class Authentication {
	public Authentication() {
	}

	public void login(String username, String password) {
		User user = User.findByUsername(username);
		if (user != null) {
			if (password.equals(user.getPassword())) {
				VaadinSession.getCurrent().setAttribute("username", username);
				UIEventBus.post(new LoginEvent(username, null));
			} else {
				System.out.println("wrong password");
			}
		} else {
			System.out.println("no such user");
		}
	}
	
	public void loginByCustomer(String customerCode) {
		Customer customer = Customer.findByCode(customerCode);
		if(customer != null) {
			VaadinSession.getCurrent().setAttribute("customerCode", customerCode);
			UIEventBus.post(new LoginEvent(null,customerCode));
		} else {
			System.out.println("no such customer");
		}
	}
	
	public void logout(String attribute) {
		if (isAuthenticated()) {
			VaadinSession.getCurrent().setAttribute(attribute, null);
		}
		UIEventBus.post(new LoginEvent(null, null));
	}

	public boolean isAuthenticated() {
		return getUsernameFromSession() != null;
	}
	
	public boolean isAuthenticatedByCustomer() {
		return getCustomerCodeFromSession() != null;
	}

	public static String getUsernameFromSession() {
		return (String) VaadinSession.getCurrent().getAttribute("username");
	}
	
	public static String getCustomerCodeFromSession() {
		return (String) VaadinSession.getCurrent().getAttribute("customerCode");
	}

	public static User getUser() {
		return User.findByUsername(getUsernameFromSession());
	}
}
