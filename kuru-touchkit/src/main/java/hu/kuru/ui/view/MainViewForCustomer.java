package hu.kuru.ui.view;

import hu.kuru.article.Article;
import hu.kuru.bill.Bill;
import hu.kuru.customer.Customer;
import hu.kuru.security.Authentication;
import hu.kuru.ui.EventBusAttachListener;
import hu.kuru.ui.EventBusDetachListener;
import hu.kuru.ui.component.ShoppingCart;
import hu.kuru.ui.event.ArticleCategorySelectedEvent;
import hu.kuru.ui.layout.ArticleCategoryLayout;
import hu.kuru.ui.layout.ArticleLayout;
import hu.kuru.util.Pair;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Scope("prototype")
@VaadinView(name = MainViewForCustomer.NAME)
public class MainViewForCustomer extends CustomComponent implements View {

	public static final String NAME = "MainViewForCustomer";

	private Map<String, Pair<Article, Integer>> cartContent = new HashMap<>();
	private Customer customer;

	private PopupView cartPopup;
	private ArticleCategoryLayout categoryLayout;

	private Component buildArticleLayout(Long selectedCategoryId) {
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);
		l.setSpacing(true);

		HorizontalLayout actions = new HorizontalLayout();
		actions.setSizeFull();
		actions.addComponent(createShoppingBasketComponent());
		Component logoutButton = createBackToCategoriesButton();
		actions.addComponent(logoutButton);
		actions.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);

		l.addComponent(actions);
		cartPopup = new PopupView(new Content() {

			@Override
			public String getMinimizedValueAsHTML() {
				return "";
			}

			@Override
			public Component getPopupComponent() {
				return new ShoppingCart(cartContent, customer);
			}
		});
		l.addComponent(cartPopup);
		l.addComponent(new ArticleLayout(customer, cartContent,selectedCategoryId));
		panel.setContent(l);
		return panel;
	}
	
	private Component createBackToCategoriesButton() {
		Button backToCategoriesButton = new Button("Vissza");

		backToCategoriesButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				MainViewForCustomer.this.setCompositionRoot(MainViewForCustomer.this.buildArticleCategoryLayout());
			}
		});

		return backToCategoriesButton;
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
				if (Bill.hasOpenBillByCustomer(customer.getId())) {
					cartPopup.setPopupVisible(true);
				} else {
					Notification.show("Önnek nincs nyitott számlája!", "Kérjen segítséget a pincérektől!", Type.ERROR_MESSAGE);
				}
			}
		});

		return basketButton;
	}

	private Component createLogoutButton() {
		Button logoutButton = new Button("Kijelentkezés");

		logoutButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Authentication.logout("customerCode");
			}
		});

		return logoutButton;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		customer = Customer.findByCode(String.valueOf(VaadinSession.getCurrent().getAttribute("customerCode")));
		setCaption(customer.getName());
		setCompositionRoot(buildArticleCategoryLayout());
	}
	
	private Component buildArticleCategoryLayout() {
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);
		l.setSpacing(true);

		HorizontalLayout actions = new HorizontalLayout();
		actions.setSizeFull();
		Component logoutButton = createLogoutButton();
		actions.addComponent(logoutButton);
		actions.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);

		l.addComponent(actions);
		categoryLayout = new ArticleCategoryLayout(customer, cartContent);
		
		categoryLayout.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				MainViewForCustomer.this.setCompositionRoot(buildArticleLayout(categoryLayout.getSelectedArticleCategoryId()));
			}
		});
		l.addComponent(categoryLayout);
		panel.setContent(l);
		return panel;
	}
	
}
