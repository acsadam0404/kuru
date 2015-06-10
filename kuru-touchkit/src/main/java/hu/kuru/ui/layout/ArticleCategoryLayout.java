package hu.kuru.ui.layout;

import hu.kuru.article.Article;
import hu.kuru.article.ArticleCategory;
import hu.kuru.customer.Customer;
import hu.kuru.ui.EventBusAttachListener;
import hu.kuru.ui.EventBusDetachListener;
import hu.kuru.ui.event.ArticleCategorySelectedEvent;
import hu.kuru.util.Pair;

import java.util.List;
import java.util.Map;

import org.vaadin.alump.masonry.MasonryLayout;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.server.Responsive;

public class ArticleCategoryLayout extends MasonryLayout {

	private Long selectedArticleCategoryId;

	public ArticleCategoryLayout(Customer customer,
			Map<String, Pair<Article, Integer>> cartContent) {
		setSizeFull();
		setImmediate(true);

		addAttachListener(new EventBusAttachListener(this));
		addDetachListener(new EventBusDetachListener(this));
		buildContent();
		Responsive.makeResponsive(this);
	}

	private void buildContent() {
		List<ArticleCategory> articleCategoryList = ArticleCategory
				.findAllValidCategory();
		for (ArticleCategory articleCategory : articleCategoryList) {
			addComponent(new ArticleCategoryBox(articleCategory));
		}
	}

	@Subscribe
	public void handleArticleCategorySelect(ArticleCategorySelectedEvent event) {
		selectedArticleCategoryId = event.getArticleCategoryId();
	}

	public Long getSelectedArticleCategoryId() {
		return selectedArticleCategoryId;
	}
}