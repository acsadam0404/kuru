package hu.kuru;

public enum Icon {

	DEFAULT("img/default-icon.png"), //
	CAKE("img/cake-icon.png"), //
	CHEESE("img/cheese-icon.png"), //
	CHICKEN("img/chicken-icon.png"), //
	COCKTAIL("img/cocktail-icon.png"), //
	GRAPES("img/grapes-icon.png"), //
	TEA("img/tea-icon.png"), //
	LOBSTER("img/lobster-icon.png"), //
	PIZZA("img/pizza-icon.png"), //
	SALAD("img/salad-icon.png"), //
	STEAK("img/steak-icon.png"), //
	;

	String resource;

	private Icon(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

}
