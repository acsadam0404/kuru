package hu.kuru.ui;


import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.server.ClientConnector.DetachListener;

public class EventBusDetachListener implements DetachListener {

	private final Object object;

	public EventBusDetachListener(Object object) {
		this.object = object;
	}

	@Override
	public void detach(DetachEvent event) {
		UIEventBus.unregister(object);
	}

}
