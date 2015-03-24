package hu.kuru.vaadin.bill;

import hu.kuru.UIEventBus;
import hu.kuru.article.Article;
import hu.kuru.bill.Bill;
import hu.kuru.eventbus.ItemAddedEvent;
import hu.kuru.item.Item;
import hu.kuru.vaadin.component.KNotification;

import java.util.Date;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToLongConverter;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class ItemAdditionalComp extends CustomComponent {

	private Window window;
	private ComboBox article;
	private TextField amount;

	public ItemAdditionalComp(Bill bill) {
		setCompositionRoot(buildLayout(bill));
	}

	private Component buildLayout(Bill bill) {
		HorizontalLayout main = new HorizontalLayout();
		main.setSpacing(true);
		main.setMargin(true);
		article = new ComboBox("Cikk név");
		article.addValidator(new NullValidator("Kötelező kitölteni!", false));
		article.setItemCaptionPropertyId("name");
		article.setContainerDataSource(new BeanItemContainer<>(Article.class, Article.findAllActive()));
		amount = new TextField("Mennyiség");
		amount.setNullRepresentation("");
		amount.setImmediate(true);
		amount.addValidator(new NullValidator("Kötelező kitölteni!", false));
		amount.setConverter(new StringToLongConverter());
		main.addComponent(article);
		main.addComponent(amount);
		Button save = new SaveButton(bill);
		main.addComponent(save);
		main.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
		return main;
	}

	public static ItemAdditionalComp fromBill(Bill bill) {
		return new ItemAdditionalComp(bill);
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	private class SaveButton extends Button {
		public SaveButton(final Bill bill) {
			super("Mentés");
			addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						article.validate();
						amount.validate();
						Item item = new Item();
						item.setBill(bill);
						item.setArticle((Article) article.getValue());
						item.setAmount((long) amount.getConvertedValue());
						item.setCreateDate(new Date());
						item.save();
						window.close();
						UIEventBus.post(new ItemAddedEvent(bill.getId()));
					} catch (InvalidValueException e) {
						new KNotification("Minden mező kitöltése kötelező!").showError();
					}
				}
			});
		}
	}

}
