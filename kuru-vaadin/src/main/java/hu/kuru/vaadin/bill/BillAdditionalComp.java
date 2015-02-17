package hu.kuru.vaadin.bill;

import hu.kuru.UIEventBus;
import hu.kuru.UIExceptionHandler;
import hu.kuru.bill.Bill;
import hu.kuru.customer.Customer;
import hu.kuru.enums.Currency;
import hu.kuru.eventbus.AddBillEvent;

import java.util.Date;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

public class BillAdditionalComp extends CustomComponent {

	private Window window;
	private ComboBox currency;

	private BillAdditionalComp(Customer customer) {
		setCompositionRoot(buildLayout(customer));
	}

	public static BillAdditionalComp fromCustomer(Customer customer) {
		return new BillAdditionalComp(customer);
	}

	private Component buildLayout(Customer customer) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		currency = new ComboBox("Pénznem");
		currency.addValidator(new NullValidator("Kötelező kitölteni!", false));
		currency.addItems(Currency.values());

		Button save = new SaveButton(customer);

		layout.addComponent(currency);
		layout.addComponent(save);
		layout.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
		return layout;
	}

	private class SaveButton extends Button {
		public SaveButton(final Customer customer) {
			super("Mentés");
			addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						currency.validate();
						Bill bill = new Bill();
						bill.setCustomer(customer);
						bill.setCurrency(((Currency) currency.getValue()).name());
						bill.setOpenDate(new Date());
						bill.save();
						UIEventBus.post(new AddBillEvent(bill));
						window.close();
					} catch (Exception e) {
						UIExceptionHandler.handleException(e);
					}
				}
			});
		}
	}

	public void setWindow(Window window) {
		this.window = window;
	}

}
