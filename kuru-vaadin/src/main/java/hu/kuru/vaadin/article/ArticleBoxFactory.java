package hu.kuru.vaadin.article;

import hu.kuru.article.Article;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ArticleBoxFactory extends CustomComponent {

	private VerticalLayout main;
	private boolean selected;
	private EventBus eventBus;

	private ArticleBoxFactory(EventBus eventBus, Article article) {
		this.eventBus = eventBus;
		eventBus.register(this);
		setCompositionRoot(buildBox(article));
	}

	private Component buildBox(final Article article) {
		main = new VerticalLayout();
		main.setMargin(true);
		main.addStyleName("articlebox");
		main.setSizeFull();
		main.setSpacing(true);
		main.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!selected) {
					main.setStyleName(ValoTheme.LAYOUT_WELL);
					eventBus.post(new ArticleSelectedEvent(article.getId()));
					selected = true;
				} else {
					main.setStyleName(null);
					eventBus.post(new ArticleSelectedEvent(null));
					selected = false;
				}
			}
		});
		main.addComponent(buildHeader(article));
		Label desc = new Label(article.getDescription());
		desc.setStyleName("justify");
		main.addComponent(desc);
		Component footer = buildFooter(article);
		main.addComponent(footer);
		main.setComponentAlignment(footer, Alignment.MIDDLE_RIGHT);
		return main;
	}

	@Subscribe
	public void handleSelectedEvent(ArticleSelectedEvent event) {
		if (selected) {
			main.setStyleName(null);
			selected = false;
		}
	}

	private Component buildFooter(Article article) {
		HorizontalLayout footer = new HorizontalLayout();
		Label price = new Label("Ár: " + article.getPrice() + " Ft");
		price.setStyleName(ValoTheme.LABEL_BOLD);
		footer.addComponent(price);
		return footer;
	}

	private Component buildHeader(Article article) {
		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.setSizeFull();
		headerLayout.setSpacing(true);

		VerticalLayout nameLayout = new VerticalLayout();
		nameLayout.setSizeUndefined();

		Label codeLabel = new Label(article.getCode());
		codeLabel.setStyleName(ValoTheme.LABEL_BOLD);
		Label nameLabel = new Label(article.getName());
		nameLabel.setStyleName(ValoTheme.LABEL_BOLD);

		nameLayout.addComponent(codeLabel);
		nameLayout.addComponent(nameLabel);

		nameLayout.setComponentAlignment(nameLabel, Alignment.MIDDLE_RIGHT);
		nameLayout.setComponentAlignment(codeLabel, Alignment.MIDDLE_RIGHT);

		Image icon = new Image(null, new ThemeResource(article.getIcon()));
		headerLayout.addComponent(icon);
		headerLayout.addComponent(nameLayout);
		headerLayout.setComponentAlignment(nameLayout, Alignment.TOP_RIGHT);
		return headerLayout;
	}

	public static Component buildArticleBoxes(EventBus eventBus, Article article) {
		return new ArticleBoxFactory(eventBus, article);
	}

}
