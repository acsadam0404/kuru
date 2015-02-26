package hu.kuru.touchkit;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionContext;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionHandler;

public class UIEventBus {

	private final EventBus eventBus = new EventBus(new SubscriberExceptionHandler() {
		@Override
		public final void handleException(final Throwable exception, final SubscriberExceptionContext context) {
			exception.printStackTrace();
		}
	});

	public static void post(final Object event) {
		KuruUI.getCurrent().getEventbus().eventBus.post(event);
	}

	public static void register(final Object object) {
		KuruUI.getCurrent().getEventbus().eventBus.register(object);
	}

	public static void unregister(final Object object) {
		KuruUI.getCurrent().getEventbus().eventBus.unregister(object);
	}

}