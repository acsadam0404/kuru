package hu.kuru.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.kuru.article.Article;
import hu.kuru.customer.Customer;
import hu.kuru.security.Authentication;
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
	// TODO: közös layout pincérnek és vendégnek
	private GridLayout articleLayout;
	private Authentication authentication;
	private Map<String, Pair<Article, Integer>> cartContent;

	public MainViewForCustomer() {
		super();
		authentication = new Authentication();
		String customerName = Customer.findByCode(
				String.valueOf(VaadinSession.getCurrent().getAttribute(
						"customerCode"))).getName();
		this.setCaption(customerName);
		this.setLeftComponent(createShoppingBasketComponent());

		// TODO: kosár
		this.setRightComponent(createLogoutButton());

		cartContent = new HashMap<String, Pair<Article, Integer>>();
		setContent(build());
	}

	/**
	 * Tartalom felépítését végző függvény
	 * 
	 * @return
	 */
	private Component build() {
		articleLayout = new GridLayout();
		articleLayout.setSizeFull();
		articleLayout.setColumns(3);
		articleLayout.setMargin(true);
		articleLayout.setImmediate(true);
		List<Article> articleList = Article.findAll();
		for (Article article : articleList) {
			VerticalLayout vLayout = new VerticalLayout();
			vLayout.addComponent(new Image(article.getName(), new ThemeResource(
					article.getIcon())));
			Label price = new Label("Ár: " + article.getPrice() + " / " + article.getUnit());
			vLayout.addComponent(price);
			ExtendedButton cartButton = new ExtendedButton("", new ThemeResource("img/cart.png"), article);
			cartButton.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Article article = ((ExtendedButton) event.getButton()).getArticle();
					cartContent.put(article.getCode(), new Pair<Article, Integer>(article, 1));
					Notification.show("A cikk bekerült a kosárba!", Type.WARNING_MESSAGE);
				}
			});
			cartButton.setWidth("120px");
			vLayout.addComponent(cartButton);
			
			articleLayout.addComponent(vLayout);
		}
		return articleLayout;
	}

	/**
	 * Kosár felépítését végző függvény
	 * 
	 * @return
	 */
	private Component createShoppingBasketComponent() {
		// TODO: img+clickListener
		final Button basketButton = new Button("Kosár");

		basketButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				VerticalLayout popoverContent = new VerticalLayout();
				Popover cartContent = new Popover(popoverContent);
				cartContent.setHeight("300px");
				cartContent.setWidth("300px");
				for (Map.Entry<String, Pair<Article,Integer>> entry : MainViewForCustomer.this.cartContent.entrySet()) {
					Label l = new Label(entry.getValue().getFirst().getName() + " " +entry.getValue().getSecond());
					popoverContent.addComponent(l);
				}
				cartContent.showRelativeTo(basketButton);
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
