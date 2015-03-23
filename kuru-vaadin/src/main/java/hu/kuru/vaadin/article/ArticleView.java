package hu.kuru.vaadin.article;

import hu.kuru.AbstractView;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;

import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

@Scope("prototype")
@VaadinView(ArticleView.NAME)
@org.springframework.stereotype.Component
public class ArticleView extends AbstractView implements View {

	public static final String NAME = "article";

	@Override
	public void enter(ViewChangeEvent event) {
		Component comp = new ArticleComp();
		comp.addAttachListener(new EventBusAttachListener(comp));
		comp.addDetachListener(new EventBusDetachListener(comp));
		setCompositionRoot(comp);
	}

}
