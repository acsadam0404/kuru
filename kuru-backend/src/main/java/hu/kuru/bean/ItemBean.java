package hu.kuru.bean;

public class ItemBean {

	private String code;
	private String name;
	private String amount;

	public ItemBean(String code, String name, String amount) {
		this.code = code;
		this.name = name;
		this.amount = amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
