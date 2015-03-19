package hu.kuru.security;

import java.util.List;

import com.vaadin.server.VaadinSession;

import hu.kuru.ui.UIEventBus;
import hu.kuru.ui.event.LoginEvent;
import hu.kuru.user.User;

public class Authentication {
	public Authentication() {
	}

	public void login(String username, String password) {
		List<User> users = User.findAll();
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
		return User.findByUsername(getUsernameFromSession());
	}
}
