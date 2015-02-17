package hu.kuru.eventbus;

import hu.kuru.customer.Customer;
import hu.kuru.vaadin.bill.BillBox;

public class BillClosedEvent {

	private final Customer customer;
	private final BillBox billBox;

	public BillClosedEvent(Customer customer, BillBox billBox) {
		this.customer = customer;
		this.billBox = billBox;
	}

	public BillBox getBillBox() {
		return billBox;
	}

	public Customer getCustomer() {
		return customer;
	}

}
