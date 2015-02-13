package hu.kuru.eventbus;

import hu.kuru.customer.Customer;

public class BillClosedEvent {

	private final Customer customer;

	public BillClosedEvent(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

}
