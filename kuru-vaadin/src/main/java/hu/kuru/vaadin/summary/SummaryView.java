package hu.kuru.vaadin.summary;

import hu.kuru.AbstractView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public class SummaryView extends AbstractView implements View {

	public static final String NAME = "summary";

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(new SummaryComp());
	}
}
