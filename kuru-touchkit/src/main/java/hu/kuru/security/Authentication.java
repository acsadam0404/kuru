package hu.kuru.security;

import hu.kuru.customer.Customer;
import hu.kuru.ui.UIEventBus;
import hu.kuru.ui.event.LoginEvent;
import hu.kuru.user.User;

import com.vaadin.server.VaadinSession;

/**
 * Authentikációt megvalósító osztály
 * 
 * @author
 *
 */
public class Authentication {

	private Authentication() {
	}

	/**
	 * Bejelentkezés dolgozóként
	 * 
	 * @param username
	 * @param password
	 */
	public static void login(String username, String password) {
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

	/**
	 * Bejelentkezés vendégként
	 * 
	 * @param customerCode
	 */
	public static void loginByCustomer(String customerCode) {
		Customer customer = Customer.findByCode(customerCode);
		if (customer != null) {
			VaadinSession.getCurrent().setAttribute("customerCode",customerCode);
			UIEventBus.post(new LoginEvent(null, customerCode));
		} else {
			System.out.println("no such customer");
		}
	}

	/**
	 * Kijelentkezés
	 * 
	 * @param attribute
	 */
	public static void logout(String attribute) {
		VaadinSession.getCurrent().setAttribute(attribute, null);
		UIEventBus.post(new LoginEvent(null, null));
	}

	public static boolean isAuthenticated() {
		return getUsernameFromSession() != null;
	}

	public static boolean isAuthenticatedByCustomer() {
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
