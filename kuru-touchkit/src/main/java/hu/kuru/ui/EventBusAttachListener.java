package hu.kuru.ui;

import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;

public class EventBusAttachListener implements AttachListener {

	private final Object object;

	public EventBusAttachListener(Object object) {
		this.object = object;
	}

	@Override
	public void attach(AttachEvent event) {
		UIEventBus.register(object);
	}
}
