package hu.kuru.ui.view;

import hu.kuru.KuruUI;
import hu.kuru.TouchkitNavigator;
import hu.kuru.article.Article;
import hu.kuru.bill.Bill;
import hu.kuru.customer.Customer;
import hu.kuru.ui.component.ShoppingCart;
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
@VaadinView(name = ArticleViewForWaiter.NAME)
public class ArticleViewForWaiter extends CustomComponent implements View {

	public static final String NAME = "ArticleViewForWaiter";
	private Map<String, Pair<Article, Integer>> cartContent = new HashMap<>();
	private Customer customer;
	private PopupView cartPopup;
	private ArticleCategoryLayout articleCategoryLayout;

	private Component build(Long selectedArticleCategoryId) {
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);

		HorizontalLayout actions = new HorizontalLayout();
		actions.setSizeFull();
		Component backBtn = createBackToCategoriesButton();
		actions.addComponent(createShoppingBasketComponent());
		actions.addComponent(backBtn);
		actions.setComponentAlignment(backBtn, Alignment.MIDDLE_RIGHT);
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
		l.addComponent(new ArticleLayout(customer, cartContent, selectedArticleCategoryId));
		panel.setContent(l);
		return panel;
	}

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

	private Component createBackButton() {
		Button backButton = new Button("Vissza");

		backButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				KuruUI.getCurrent().getNavigator().navigateTo(MainViewForWaiter.NAME);
			}
		});

		return backButton;
	}
	
	private Component createBackToCategoriesButton() {
		Button backToCategoriesButton = new Button("Vissza");

		backToCategoriesButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ArticleViewForWaiter.this.setCompositionRoot(ArticleViewForWaiter.this.buildArticleCategoryLayout());
			}
		});

		return backToCategoriesButton;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Map<String, String> params = TouchkitNavigator.paramsToMap(event.getParameters());
		customer = Customer.findByCode(params.get("code"));

		setCompositionRoot(buildArticleCategoryLayout());
	}
	
	private Component buildArticleCategoryLayout() {
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);

		HorizontalLayout actions = new HorizontalLayout();
		actions.setSizeFull();
		Component backBtn = createBackButton();
		actions.addComponent(backBtn);
		actions.setComponentAlignment(backBtn, Alignment.MIDDLE_RIGHT);
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
		articleCategoryLayout = new ArticleCategoryLayout(customer, cartContent);
		articleCategoryLayout.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				ArticleViewForWaiter.this.setCompositionRoot(build(articleCategoryLayout.getSelectedArticleCategoryId()));
			}
		});
		l.addComponent(articleCategoryLayout);
		panel.setContent(l);
		return panel;
	}

}
