package hu.kuru.eventbus;

public class ArticleSelectedEvent {

	private final Long articleId;

	public ArticleSelectedEvent(Long articleId) {
		this.articleId = articleId;
	}

	public Long getArticleId() {
		return articleId;
	}

}
