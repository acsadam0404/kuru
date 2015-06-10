package hu.kuru.ui.layout;

import hu.kuru.article.ArticleCategory;
import hu.kuru.ui.EventBusAttachListener;
import hu.kuru.ui.EventBusDetachListener;
import hu.kuru.ui.UIEventBus;
import hu.kuru.ui.event.ArticleCategorySelectedEvent;
import hu.kuru.util.KuruUtils;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ArticleCategoryBox extends CustomComponent {

	private ArticleCategory articleCategory;
	VerticalLayout boxLayout;

	public ArticleCategoryBox(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
		addAttachListener(new EventBusAttachListener(this));
		addDetachListener(new EventBusDetachListener(this));
		this.setCompositionRoot(buildContent());
	}

	private Component buildContent() {
		boxLayout = new VerticalLayout();
		boxLayout.setSizeFull();
		HorizontalLayout hLayoutForTitleAndPicture = new HorizontalLayout();
		hLayoutForTitleAndPicture.setSizeFull();
		Label articleName = new Label(articleCategory.getName());
		hLayoutForTitleAndPicture.addComponent(articleName);
		hLayoutForTitleAndPicture.setComponentAlignment(articleName,
				Alignment.TOP_CENTER);
		hLayoutForTitleAndPicture.addComponent(KuruUtils
				.getArticleImageFromFileSystem(articleCategory.getIcon()));
		boxLayout.addComponent(hLayoutForTitleAndPicture);

		boxLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				boxLayout.setStyleName(ValoTheme.LAYOUT_WELL);
				UIEventBus.post(new ArticleCategorySelectedEvent(
						articleCategory.getId()));
			}
		});
		return boxLayout;
	}

}
