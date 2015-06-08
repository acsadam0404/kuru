package hu.kuru.ui.event;

public class ArticleCategorySelectedEvent {
	private final Long articleCategoryId;

	public ArticleCategorySelectedEvent(Long articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}

	public Long getArticleCategoryId() {
		return articleCategoryId;
	}
}
