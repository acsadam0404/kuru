package hu.kuru.touchkit

import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.CustomComponent
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Button.ClickListener

class LoginScreen extends CustomComponent {
	private TextField usernameField
	private PasswordField passwordField
	LoginScreen() {
		setCompositionRoot(build())
	}
	
	private Component build() {
		def l = new VerticalLayout()
		l.with {
			setSpacing(true)
			usernameField = new TextField("Felhasználó")
			passwordField = new PasswordField("Jelszó")
			addComponent(usernameField)
			addComponent(passwordField)
			addComponent(new Button("Login", (ClickListener) { e -> 
				print 'asd'
			}))
		}
		return l
	}
}
