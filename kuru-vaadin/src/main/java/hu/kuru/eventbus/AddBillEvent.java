package hu.kuru.eventbus;

import hu.kuru.bill.Bill;

public class AddBillEvent {

	private final Bill bill;

	public AddBillEvent(Bill bill) {
		this.bill = bill;
	}

	public Bill getBill() {
		return bill;
	}

}
