package hu.kuru.ui.view;

import hu.kuru.article.Article;
import hu.kuru.bill.Bill;
import hu.kuru.customer.Customer;
import hu.kuru.ui.component.ShoppingCart;
import hu.kuru.ui.layout.ArticleLayout;
import hu.kuru.util.Pair;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class ArticleViewForWaiter extends NavigationView {

	// TODO: közösbe kivenni a managert nem jó átadogatva
	private NavigationManager manager;
	private Map<String, Pair<Article, Integer>> cartContent;
	private Customer customer;

	public ArticleViewForWaiter(NavigationManager manager, Customer customer) {
		super();
		this.manager = manager;
		this.customer = customer;
		this.cartContent = new HashMap<String, Pair<Article, Integer>>();
		this.setRightComponent(createBackButton());
		this.setLeftComponent(createShoppingBasketComponent());
		this.setContent(buildContent());
	}

	private Component buildContent() {
		return new ArticleLayout(customer, cartContent);
	}

	private Component createShoppingBasketComponent() {
		final Button basketButton = new Button("Kosár");

		basketButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (Bill.hasOpenBillByCustomer(customer.getId()))
					new ShoppingCart(basketButton, cartContent, customer);
				else
					Notification.show("Önnek nincs nyitott számlája!", "Kérjen segítséget a pincérektől!", Type.ERROR_MESSAGE);
			}
		});

		return basketButton;
	}

	private Component createBackButton() {
		Button backButton = new Button("Vissza");

		backButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ArticleViewForWaiter.this.manager.navigateBack();
			}
		});

		return backButton;
	}
}
