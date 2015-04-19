package hu.kuru.ui.component;

import hu.kuru.article.Article;
import hu.kuru.bill.Bill;
import hu.kuru.customer.Customer;
import hu.kuru.item.Item;
import hu.kuru.ui.interaction.ExtendedButton;
import hu.kuru.util.Pair;
import hu.si.touchkit.converter.AbstractCustomizableStringToNumberConverter;
import hu.si.touchkit.converter.StringToIntegerConverter;

import java.util.Date;
import java.util.Map;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Bevásárló kosarat reprezentáló osztály
 * 
 * @author
 *
 */
public class ShoppingCart extends Panel {

	private VerticalLayout root;
	private VerticalLayout vLayout;
	private Map<String, Pair<Article, Integer>> cartContent;
	private ClickListener removeButtonCL;
	private ClickListener increaseButtonCL;
	private ClickListener decreaseButtonCL;
	private ClickListener orderButtonClickListener;
	private Customer customer;

	/**
	 * Konstruktor a bevásárló kosarat megvalósító objektum létrehozásához
	 * 
	 * @param relativeTo
	 * @param cartContent
	 */
	public ShoppingCart(Map<String, Pair<Article, Integer>> cartContent, Customer customer) {
		setStyleName(ValoTheme.PANEL_BORDERLESS);
		setHeight("500px");
		setWidth("500px");
		this.cartContent = cartContent;
		this.customer = customer;
		root = build();
		this.refreshItemContent();
	}

	/**
	 * Bevásárló kosár felület felépítése
	 */
	private VerticalLayout build() {
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);
		createButtonHandlers();
		setVisible(true);
		return l;
	}

	private void refreshItemContent() {
		root.removeAllComponents();
		vLayout = new VerticalLayout();
		vLayout.setWidth("100%");;
		vLayout.setSpacing(true);

		int sum = 0;
		for (Map.Entry<String, Pair<Article, Integer>> entry : cartContent.entrySet()) {
			Article article = entry.getValue().getFirst();
			Integer quantity = entry.getValue().getSecond();
			
			HorizontalLayout rowLayout = new HorizontalLayout();
			rowLayout.setWidth("100%");
			HorizontalLayout buttonGroup = new HorizontalLayout();
			buttonGroup.setSpacing(true);
			ExtendedButton increaseButton = new ExtendedButton(null, FontAwesome.PLUS, article).withBiggerSize();
			ExtendedButton decreaseButton = new ExtendedButton(null, FontAwesome.MINUS, article).withBiggerSize();
			ExtendedButton removeButton = new ExtendedButton(null, FontAwesome.TRASH_O, article).withBiggerSize();
			increaseButton.addClickListener(increaseButtonCL);
			decreaseButton.addClickListener(decreaseButtonCL);
			removeButton.addClickListener(removeButtonCL);
			buttonGroup.addComponent(decreaseButton);
			buttonGroup.addComponent(increaseButton);
			buttonGroup.addComponent(removeButton);
			Label l = new Label(article.getName() + ": " + quantity + " " + article.getUnit());
			rowLayout.addComponent(l);
			rowLayout.setComponentAlignment(l, Alignment.MIDDLE_LEFT);
			rowLayout.addComponent(buttonGroup);
			rowLayout.setComponentAlignment(buttonGroup, Alignment.TOP_RIGHT);
			sum += quantity * article.getPrice();
			vLayout.addComponent(rowLayout);
		}

		Label sumLabel = new Label("Összesen: " + new StringToIntegerConverter(AbstractCustomizableStringToNumberConverter.FORMAT_MONETARY).convertToPresentation(sum) + " Ft");
		sumLabel.addStyleName(ValoTheme.LABEL_BOLD);
		sumLabel.setHeight("20px");

		Button orderButton = new Button("Megrendelés", orderButtonClickListener);
		orderButton.setWidth("200px");

		
		root.addComponent(vLayout);
		HorizontalLayout bottom = new HorizontalLayout(sumLabel, orderButton);
		bottom.setSizeFull();
		root.addComponent(bottom);
		bottom.setComponentAlignment(orderButton, Alignment.BOTTOM_RIGHT);
		bottom.setComponentAlignment(sumLabel, Alignment.BOTTOM_LEFT);

		setContent(root);
	}

	/**
	 * Handlerek létrehozását végző függvény
	 */
	private void createButtonHandlers() {

		// Törlés
		removeButtonCL = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// kikell törölni a mapből
				cartContent.remove(((ExtendedButton) event.getButton()).getArticle().getCode());
				ShoppingCart.this.refreshItemContent();
			}
		};

		// Növelés
		increaseButtonCL = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				String articleCode = ((ExtendedButton) event.getButton()).getArticle().getCode();
				cartContent.get(articleCode).setSecond(cartContent.get(articleCode).getSecond() + 1);
				ShoppingCart.this.refreshItemContent();
			}
		};

		// Csökkentés
		decreaseButtonCL = new ClickListener() {

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
				Notification.show("Megrendelés sikeres", "Kérjük várjon még rendelését feldolgozzuk", Type.TRAY_NOTIFICATION);
				
			}

		};
	}
}
