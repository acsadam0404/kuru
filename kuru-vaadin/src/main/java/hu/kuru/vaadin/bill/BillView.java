package hu.kuru.vaadin.bill;

import hu.kuru.AbstractView;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;

import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

@Scope("prototype")
@org.springframework.stereotype.Component
public class BillView extends AbstractView implements View {

	public static final String NAME = "bill";

	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();
		Component comp = new BillComp();
		comp.addAttachListener(new EventBusAttachListener(comp));
		comp.addDetachListener(new EventBusDetachListener(comp));
		setCompositionRoot(comp);
	}

}
