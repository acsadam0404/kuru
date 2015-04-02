package hu.kuru.ui.component;

import hu.kuru.article.Article;
import hu.kuru.bill.Bill;
import hu.kuru.customer.Customer;
import hu.kuru.item.Item;
import hu.kuru.ui.interaction.ExtendedButton;
import hu.kuru.util.Pair;

import java.util.Date;
import java.util.Map;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Bevásárló kosarat reprezentáló osztály
 * 
 * @author
 *
 */
public class ShoppingCart extends CustomComponent {

	private VerticalLayout root;
	private VerticalLayout vLayout;
	private Map<String, Pair<Article, Integer>> cartContent;
	private ClickListener removeButtonClickListener;
	private ClickListener increaseButtonClickListener;
	private ClickListener decreaseButtonClickListener;
	private ClickListener orderButtonClickListener;
	private Customer customer;

	/**
	 * Konstruktor a bevásárló kosarat megvalósító objektum létrehozásához
	 * 
	 * @param relativeTo
	 * @param cartContent
	 */
	public ShoppingCart(Map<String, Pair<Article, Integer>> cartContent, Customer customer) {
		setHeight("300px");
		setWidth("420px");
		this.cartContent = cartContent;
		this.customer = customer;

		root = build();
	}

	/**
	 * Bevásárló kosár felület felépítése
	 */
	private VerticalLayout build() {
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSizeFull();

		createButtonHandlers();

		this.refreshItemContent();

		setVisible(true);
		return l;
	}

	private void refreshItemContent() {
		root.removeAllComponents();
		vLayout = new VerticalLayout();
		vLayout.setSizeFull();

		long sum = 0;
		for (Map.Entry<String, Pair<Article, Integer>> entry : cartContent.entrySet()) {
			HorizontalLayout rowLayout = new HorizontalLayout();
			rowLayout.setWidth("100%");
			HorizontalLayout buttonGroup = new HorizontalLayout();
			buttonGroup.setSpacing(true);
			// + gomb
			ExtendedButton increaseItemNumberButton = new ExtendedButton(null, FontAwesome.PLUS, entry.getValue().getFirst());
			// - gomb
			ExtendedButton decreaseItemNumberButton = new ExtendedButton(null, FontAwesome.MINUS, entry.getValue().getFirst());
			// Törlés gomb
			ExtendedButton removeArticleButton = new ExtendedButton(null, FontAwesome.TRASH_O, entry.getValue().getFirst());
			increaseItemNumberButton.addClickListener(increaseButtonClickListener);
			decreaseItemNumberButton.addClickListener(decreaseButtonClickListener);
			removeArticleButton.addClickListener(removeButtonClickListener);
			buttonGroup.addComponent(decreaseItemNumberButton);
			buttonGroup.addComponent(increaseItemNumberButton);
			buttonGroup.addComponent(removeArticleButton);
			Label l = new Label(entry.getValue().getFirst().getName() + " " + entry.getValue().getSecond());
			rowLayout.addComponent(l);
			rowLayout.addComponent(buttonGroup);
			rowLayout.setComponentAlignment(buttonGroup, Alignment.TOP_RIGHT);
			sum += entry.getValue().getSecond() * entry.getValue().getFirst().getPrice();
			vLayout.addComponent(rowLayout);
		}

		// TODO: ezt nem biztos hogy itt kéne
		Label sumLabel = new Label("Összesen: " + sum + " Ft");
		sumLabel.setHeight("20px");

		Button orderButton = new Button("Megrendelés", orderButtonClickListener);
		orderButton.setWidth("200px");

		root.addComponent(vLayout);
		root.addComponent(sumLabel);
		root.addComponent(orderButton);
		root.setComponentAlignment(orderButton, Alignment.BOTTOM_LEFT);
		root.setComponentAlignment(sumLabel, Alignment.BOTTOM_LEFT);

		setCompositionRoot(root);
	}

	/**
	 * Handlerek létrehozását végző függvény
	 */
	private void createButtonHandlers() {

		// Törlés
		removeButtonClickListener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// kikell törölni a mapből
				cartContent.remove(((ExtendedButton) event.getButton()).getArticle().getCode());
				ShoppingCart.this.refreshItemContent();
			}
		};

		// Növelés
		increaseButtonClickListener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				String articleCode = ((ExtendedButton) event.getButton()).getArticle().getCode();
				cartContent.get(articleCode).setSecond(cartContent.get(articleCode).getSecond() + 1);
				ShoppingCart.this.refreshItemContent();
			}
		};

		// Csökkentés
		decreaseButtonClickListener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				String articleCode = ((ExtendedButton) event.getButton()).getArticle().getCode();
				// ha már csak egy van benne és csökkenteni szeretnénk
				if (new Integer(1).equals(cartContent.get(articleCode).getSecond())) {
					cartContent.remove(articleCode);
				} else {
					cartContent.get(articleCode).setSecond(cartContent.get(articleCode).getSecond() - 1);
				}
				ShoppingCart.this.refreshItemContent();
			}
		};

		// Megrendelés
		orderButtonClickListener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Bill openBillForCustomer = Bill.getOpenBillByCustomerId(ShoppingCart.this.customer.getId());
				for (Map.Entry<String, Pair<Article, Integer>> element : cartContent.entrySet()) {
					Item item = new Item();
					item.setBill(openBillForCustomer);
					item.setArticle(element.getValue().getFirst());
					item.setAmount(element.getValue().getSecond());
					item.setCreateDate(new Date());
					item.save();
				}
				cartContent.clear();
				ShoppingCart.this.refreshItemContent();
			}

		};
	}
}
