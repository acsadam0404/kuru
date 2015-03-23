package hu.kuru.vaadin.summary;

import hu.kuru.AbstractView;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@Component
@Scope("prototype")
@VaadinView(SummaryView.NAME)
public class SummaryView extends AbstractView implements View {

	public static final String NAME = "summary";

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(new SummaryComp());
	}
}
