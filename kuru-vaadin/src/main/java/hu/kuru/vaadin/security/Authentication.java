package hu.kuru.vaadin.security;

import hu.kuru.UIEventBus;
import hu.kuru.user.User;

import com.vaadin.server.VaadinSession;

public class Authentication {
	public Authentication() {
	}

	public void login(String username, String password) {
		User user = User.findByUsername(username);
		if (user != null) {
			if (password.equals(user.getPassword())) {
				VaadinSession.getCurrent().setAttribute("username", username);
				UIEventBus.post(new LoginEvent(username));
			} else {
				System.out.println("wrong password");
			}
		} else {
			System.out.println("no such user");
		}
	}

	public void logout() {
		if (isAuthenticated()) {
			VaadinSession.getCurrent().setAttribute("username", null);
		}
		UIEventBus.post(new LoginEvent(null));
	}

	public boolean isAuthenticated() {
		return getUsernameFromSession() != null;
	}

	public static String getUsernameFromSession() {
		return (String) VaadinSession.getCurrent().getAttribute("username");
	}

	public static User getUser() {
		return User.get(getUsernameFromSession());
	}
}
