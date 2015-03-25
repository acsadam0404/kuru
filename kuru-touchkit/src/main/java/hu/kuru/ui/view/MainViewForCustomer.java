package hu.kuru.ui.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.kuru.article.Article;
import hu.kuru.customer.Customer;
import hu.kuru.security.Authentication;
import hu.kuru.ui.component.ShoppingCart;
import hu.kuru.ui.interaction.ExtendedButton;
import hu.kuru.ui.layout.ArticleLayout;
import hu.kuru.util.Pair;

import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

/**
 * Fő képernyő a vendég felhasználóknak
 * 
 * @author
 *
 */
public class MainViewForCustomer extends NavigationView {

	public static final String NAME = "MainViewForCustomer";

	private Authentication authentication;
	private Map<String, Pair<Article, Integer>> cartContent;
	private Customer customer;

	public MainViewForCustomer() {
		super();
		authentication = new Authentication();
		customer = Customer.findByCode(
				String.valueOf(VaadinSession.getCurrent().getAttribute(
						"customerCode")));
		this.setCaption(customer.getName());
		
		this.setLeftComponent(createShoppingBasketComponent());
		this.setRightComponent(createLogoutButton());

		cartContent = new HashMap<String, Pair<Article, Integer>>();
		setContent(buildContent());
	}

	/**
	 * Tartalom felépítését végző függvény
	 * 
	 * @return
	 */
	private Component buildContent() {
		return new ArticleLayout(cartContent);
	}

	/**
	 * Kosár felépítését végző függvény
	 * 
	 * @return
	 */
	private Component createShoppingBasketComponent() {
		final Button basketButton = new Button("Kosár");

		basketButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				new ShoppingCart(basketButton, cartContent, MainViewForCustomer.this.customer);
			}
		});

		return basketButton;
	}

	/**
	 * Logout gomb felépítését végző függvény
	 * 
	 * @return
	 */
	private Component createLogoutButton() {
		Button logoutButton = new Button("Kijelentkezés");

		logoutButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				authentication.logout("customerCode");
			}
		});

		return logoutButton;
	}
}
