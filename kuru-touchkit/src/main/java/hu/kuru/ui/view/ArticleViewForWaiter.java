package hu.kuru.ui.view;

import java.util.HashMap;
import java.util.Map;

import hu.kuru.article.Article;
import hu.kuru.ui.component.ShoppingCart;
import hu.kuru.ui.layout.ArticleLayout;
import hu.kuru.util.Pair;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ArticleViewForWaiter extends NavigationView{
	
	//TODO: közösbe kivenni a managert nem jó átadogatva
	private NavigationManager manager;
	private Map<String, Pair<Article,Integer>> cartContent;

	public ArticleViewForWaiter(NavigationManager manager) {
		super();
		this.manager = manager;
		this.cartContent = new HashMap<String, Pair<Article,Integer>>();
		this.setRightComponent(createBackButton());
		this.setLeftComponent(createShoppingBasketComponent());
		this.setContent(buildContent());
	}
	
	private Component buildContent() {
		return new ArticleLayout(cartContent);
	}
	
	private Component createShoppingBasketComponent() {
		final Button basketButton = new Button("Kosár");

		basketButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				new ShoppingCart(basketButton, cartContent);
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
