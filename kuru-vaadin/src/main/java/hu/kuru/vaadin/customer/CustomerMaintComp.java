package hu.kuru.vaadin.customer;

import hu.kuru.UIEventBus;
import hu.kuru.customer.Customer;
import hu.kuru.eventbus.AddCustomerEvent;
import hu.kuru.vaadin.KFieldGroup;
import hu.kuru.vaadin.component.KNotification;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
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
		bind(customer);
	}

	private void bind(Customer customer) {
		fg.bindMemberFields(this);
		fg.setItemDataSource(customer);
	}

	public static CustomerMaintComp createNew() {
		return new CustomerMaintComp(new Customer());
	}

	public static CustomerMaintComp fromCustomer(Customer customer) {
		return new CustomerMaintComp(customer);
	}

	private void init() {
		fg = new KFieldGroup<>(Customer.class);
		name = new TextField("Név");
		code = new TextField("Kód");
		name.setImmediate(true);
		code.setImmediate(true);
		
		//TODO nullrepresentation aspectből kell majd
		name.setNullRepresentation("");
		code.setNullRepresentation("");
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
						customer.save();
						window.close();
						UIEventBus.post(new AddCustomerEvent(customer));
					} catch (CommitException e) {
						new KNotification("Minden mező kitöltése kötelező!").showError();
					}
				}
			});
		}
	}

	public void setWindow(Window window) {
		this.window = window;
	}

}
