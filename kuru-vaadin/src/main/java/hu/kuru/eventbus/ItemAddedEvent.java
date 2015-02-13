package hu.kuru.eventbus;

public class ItemAddedEvent {

	private final Long billId;

	public ItemAddedEvent(Long billId) {
		this.billId = billId;
	}

	public Long getBillId() {
		return billId;
	}

}
