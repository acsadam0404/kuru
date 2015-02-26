package hu.kuru.eventbus;

import hu.kuru.customer.Customer;

public class AddCustomerEvent {

	private final Customer customer;
	private final boolean isNew;

	public AddCustomerEvent(Customer customer, boolean isNew) {
		this.customer = customer;
		this.isNew = isNew;
	}

	public Customer getCustomer() {
		return customer;
	}

	public boolean isNew() {
		return isNew;
	}

}
