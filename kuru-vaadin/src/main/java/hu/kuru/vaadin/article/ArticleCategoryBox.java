package hu.kuru.vaadin.article;

import hu.kuru.UIEventBus;
import hu.kuru.article.ArticleCategory;
import hu.kuru.eventbus.ArticleCategorySelectedEvent;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;
import hu.kuru.util.KuruUtils;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ArticleCategoryBox extends CustomComponent {
	private VerticalLayout main;
	private boolean selected;

	private ArticleCategoryBox(ArticleCategory articleCategory) {
		setCompositionRoot(buildBox(articleCategory));
	}

	private Component buildBox(final ArticleCategory articleCategory) {
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
					UIEventBus.post(new ArticleCategorySelectedEvent(
							articleCategory.getId()));
					selected = true;
				} else {
					main.setStyleName(null);
					UIEventBus.post(new ArticleCategorySelectedEvent(null));
					selected = false;
				}
			}
		});
		main.addComponent(buildHeader(articleCategory));
		return main;
	}

	@Subscribe
	public void handleSelectedEvent(ArticleCategorySelectedEvent event) {
		if (selected) {
			main.setStyleName(null);
			selected = false;
		}
	}

	private Component buildHeader(ArticleCategory articleCategory) {
		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.setSizeFull();
		headerLayout.setSpacing(true);

		VerticalLayout nameLayout = new VerticalLayout();
		nameLayout.setSizeUndefined();

		Label codeLabel = new Label(articleCategory.getCode());
		codeLabel.setStyleName(ValoTheme.LABEL_BOLD);
		Label nameLabel = new Label(articleCategory.getName());
		nameLabel.setStyleName(ValoTheme.LABEL_BOLD);

		nameLayout.addComponent(codeLabel);
		nameLayout.addComponent(nameLabel);

		nameLayout.setComponentAlignment(nameLabel, Alignment.MIDDLE_RIGHT);
		nameLayout.setComponentAlignment(codeLabel, Alignment.MIDDLE_RIGHT);

		Image icon = KuruUtils.getArticleImageFromFileSystem(articleCategory.getIcon());
		headerLayout.addComponent(icon);
		headerLayout.addComponent(nameLayout);
		headerLayout.setComponentAlignment(nameLayout, Alignment.TOP_RIGHT);
		return headerLayout;
	}

	public static Component buildArticleCategoryBox(
			ArticleCategory articleCategory) {
		Component comp = new ArticleCategoryBox(articleCategory);
		comp.addAttachListener(new EventBusAttachListener(comp));
		comp.addDetachListener(new EventBusDetachListener(comp));
		return comp;
	}
}
