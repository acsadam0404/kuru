package hu.kuru.vaadin.customer;

import hu.kuru.ServiceLocator;
import hu.kuru.UIEventBus;
import hu.kuru.UIExceptionHandler;
import hu.kuru.customer.Customer;
import hu.kuru.customer.CustomerService;
import hu.kuru.eventbus.AddCustomerEvent;
import hu.kuru.vaadin.KFieldGroup;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class CustomerMaintComp extends CustomComponent {

	private TextField name;
	private TextField code;
	private KFieldGroup<Customer> fg;

	private Window window;

	private CustomerMaintComp(Customer customer) {
		init();
		setCompositionRoot(buildLayout());
		load(customer);
	}

	private void load(Customer customer) {
		fg = new KFieldGroup<>(Customer.class);
		fg.setBuffered(true);
		fg.bindMemberFields(this);
		fg.setItemDataSource(customer);
		code.setEnabled(customer.getId() == null);
	}

	public static CustomerMaintComp createNew() {
		return new CustomerMaintComp(new Customer());
	}

	public static CustomerMaintComp fromCustomer(Customer customer) {
		return new CustomerMaintComp(customer);
	}

	private void init() {
		name = new TextField("Név");
		code = new TextField("Kód");
		name.setImmediate(true);
		code.setImmediate(true);

		name.setNullRepresentation("");
		code.setNullRepresentation("");
		setValidationVisible(false);
	}

	private void setValidationVisible(boolean visible) {
		name.setValidationVisible(visible);
		code.setValidationVisible(visible);
	}

	private Component buildLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		Button save = new SaveButton();
		layout.addComponent(name);
		layout.addComponent(code);
		layout.addComponent(save);
		layout.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);

		return layout;
	}

	private class SaveButton extends Button {
		public SaveButton() {
			super("Mentés");
			addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						fg.commit();
						Customer customer = fg.getItemDataSource().getBean();
						boolean isNew = customer.getId() == null;
						ServiceLocator.getBean(CustomerService.class).saveCustomer(customer);
						window.close();
						UIEventBus.post(new AddCustomerEvent(customer, isNew));
					} catch (Exception e) {
						setValidationVisible(true);
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
