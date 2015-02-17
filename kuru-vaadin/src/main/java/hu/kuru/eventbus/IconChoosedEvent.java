package hu.kuru.eventbus;

public class IconChoosedEvent {

	private final String resource;

	public IconChoosedEvent(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

}
