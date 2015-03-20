package hu.kuru.ui;

import java.util.List;

import hu.kuru.article.Article;
import hu.kuru.customer.Customer;
import hu.kuru.security.Authentication;

import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;

/**
 * Fő képernyő a vendég felhasználóknak
 * 
 * @author
 *
 */
public class MainViewForCustomer extends NavigationView {

	public static final String NAME = "MainViewForCustomer";
	//TODO: közös layout pincérnek és vendégnek
	private GridLayout articleLayout;
	private Authentication authentication;

	public MainViewForCustomer() {
		super();
		authentication = new Authentication();
		String customerName = Customer.findByCode(String.valueOf(VaadinSession.getCurrent().getAttribute("customerCode"))).getName();
		this.setCaption(customerName);
		
		this.setLeftComponent(createShoppingBasketComponent());
		
		//TODO: kosár
		this.setRightComponent(createLogoutButton());
		
		setContent(build());
	}

	/**
	 * Tartalom felépítését végző függvény
	 * 
	 * @return
	 */
	private Component build() {
		articleLayout = new GridLayout();
		List<Article> articleList = Article.findAll();
		for(Article article : articleList) {
			articleLayout.addComponent(new Image(null,new ThemeResource(article.getIcon())));
		}
		return articleLayout;
	}
	
	/**
	 * Kosár felépítését végző függvény
	 * 
	 * @return
	 */
	private Component createShoppingBasketComponent() {
		//TODO: img+clickListener
		Label shpBasketLabel = new Label("Kosár");
		
		return shpBasketLabel;
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
