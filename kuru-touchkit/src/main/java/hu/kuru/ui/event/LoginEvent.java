package hu.kuru.ui.event;

public class LoginEvent {
	private final String username;
	private final String customerCode;

	public LoginEvent(String username, String customerCode) {
		this.username = username;
		this.customerCode = customerCode;
	}

	public String getUsername() {
		return username;
	}

	public String getCustomerCode() {
		return customerCode;
	}
	
}