package hu.kuru.vaadin.article;

import hu.kuru.article.Article;
import hu.kuru.article.ArticleCategory;
import hu.kuru.vaadin.component.KWindow;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

public class ArticleModifyButton extends Button {
	
	private Long selectedArticleId;
	private Long selectedArticleCategoryId;

	public ArticleModifyButton(String caption, final boolean isNew,
			Long selectedArticleId) {
		super(caption);
		this.selectedArticleId=selectedArticleId;

		addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final KWindow window = new KWindow(getCaption());
				Article article;
				if(isNew) {
					article = new Article();
					if(ArticleModifyButton.this.selectedArticleCategoryId != null) {
						article.setArticleCategory(ArticleCategory.findOne(ArticleModifyButton.this.selectedArticleCategoryId));
					}
				} else {
					article = Article.findOne(ArticleModifyButton.this.selectedArticleId);
				}
				ArticleMaintComp comp = new ArticleMaintComp(article);
				comp.setWindow(window);
				window.setContent(comp);
				UI.getCurrent().addWindow(window);
			}
		});
	}
	
	public ArticleModifyButton(String caption, final boolean isNew,
			Long selectedArticleId, Long selectedArticleCategoryId) {
		this(caption,isNew,selectedArticleId);
		this.selectedArticleCategoryId = selectedArticleCategoryId;
	}
	
	public void setSelectedArticleId(Long selectedArticleId) {
		this.selectedArticleId = selectedArticleId;
	}

}