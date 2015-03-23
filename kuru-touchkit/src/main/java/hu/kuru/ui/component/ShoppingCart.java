package hu.kuru.ui.component;

import hu.kuru.article.Article;
import hu.kuru.ui.interaction.ExtendedButton;
import hu.kuru.ui.view.MainViewForCustomer;
import hu.kuru.util.Pair;

import java.util.Map;

import org.apache.tomcat.util.http.fileupload.MultipartStream.ItemInputStream;

import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Bevásárló kosarat reprezentáló osztály
 * 
 * @author
 *
 */
public class ShoppingCart extends Popover {

	private VerticalLayout contentLayout;
	private VerticalLayout vLayout;
	private Component relativeTo;
	private Map<String, Pair<Article, Integer>> cartContent;
	private ClickListener removeButtonClickListener;
	private ClickListener increaseButtonClickListener;
	private ClickListener decreaseButtonClickListener;

	/**
	 * Konstruktor a bevásárló kosarat megvalósító objektum létrehozásához
	 * 
	 * @param relativeTo
	 * @param cartContent
	 */
	public ShoppingCart(Component relativeTo,
			Map<String, Pair<Article, Integer>> cartContent) {
		super();
		this.setHeight("300px");
		this.setWidth("300px");
		this.relativeTo = relativeTo;
		this.cartContent = cartContent;

		buildContent();
	}

	/**
	 * Bevásárló kosár felület felépítése
	 */
	private void buildContent() {
		contentLayout = new VerticalLayout();
		contentLayout.setSizeFull();

		createButtonHandlers();

		this.refreshItemContent();

		this.showRelativeTo(relativeTo);
	}

	private void refreshItemContent() {
		contentLayout.removeAllComponents();
		vLayout = new VerticalLayout();

		long sum = 0;
		for (Map.Entry<String, Pair<Article, Integer>> entry : cartContent
				.entrySet()) {
			HorizontalLayout rowLayout = new HorizontalLayout();
			rowLayout.setWidth("100%");
			HorizontalButtonGroup buttonGroup = new HorizontalButtonGroup();
			// + gomb
			ExtendedButton increaseItemNumberButton = new ExtendedButton("+",
					null, entry.getValue().getFirst());
			// - gomb
			ExtendedButton decreaseItemNumberButton = new ExtendedButton("-",
					null, entry.getValue().getFirst());
			// Törlés gomb
			ExtendedButton removeArticleButton = new ExtendedButton("Törlés",
					null, entry.getValue().getFirst());
			increaseItemNumberButton
					.addClickListener(increaseButtonClickListener);
			decreaseItemNumberButton
					.addClickListener(decreaseButtonClickListener);
			removeArticleButton.addClickListener(removeButtonClickListener);
			buttonGroup.addComponent(decreaseItemNumberButton);
			buttonGroup.addComponent(increaseItemNumberButton);
			buttonGroup.addComponent(removeArticleButton);
			Label l = new Label(entry.getValue().getFirst().getName() + " "
					+ entry.getValue().getSecond());
			rowLayout.addComponent(l);
			rowLayout.addComponent(buttonGroup);
			rowLayout.setComponentAlignment(buttonGroup, Alignment.TOP_RIGHT);
			sum += entry.getValue().getSecond()
					* entry.getValue().getFirst().getPrice();
			vLayout.addComponent(rowLayout);
		}
		Label sumLabel = new Label("Összesen: " + sum);
		sumLabel.setHeight("20px");

		contentLayout.addComponent(vLayout);
		contentLayout.addComponent(sumLabel);
		contentLayout.setComponentAlignment(sumLabel, Alignment.BOTTOM_LEFT);
		
		this.setContent(contentLayout);
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
				cartContent.remove(((ExtendedButton) event.getButton())
						.getArticle().getCode());
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
				//ha már csak egy van benne és csökkenteni szeretnénk
				if(new Integer(1).equals(cartContent.get(articleCode).getSecond())) {
					cartContent.remove(articleCode);
				} else {					
					cartContent.get(articleCode).setSecond(cartContent.get(articleCode).getSecond() - 1);
				}
				ShoppingCart.this.refreshItemContent();
			}
		};
	}
}
