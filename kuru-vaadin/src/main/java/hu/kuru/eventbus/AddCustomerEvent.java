package hu.kuru.eventbus;

import hu.kuru.customer.Customer;

public class AddCustomerEvent {

	private final Customer customer;

	public AddCustomerEvent(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

}
