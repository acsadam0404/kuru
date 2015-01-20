package hu.kuru.vaadin.article;

import hu.kuru.AbstractView;
import hu.kuru.ServiceLocator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@Component
@Scope("prototype")
@VaadinView(ArticleView.NAME)
public class ArticleView extends AbstractView implements View {

	public static final String NAME = "article";

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(ServiceLocator.getBean(ArticleComp.class));
	}

}
