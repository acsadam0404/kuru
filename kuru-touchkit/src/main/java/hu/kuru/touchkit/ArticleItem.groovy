package hu.kuru.touchkit

import com.vaadin.ui.Component
import com.vaadin.ui.CustomComponent
import com.vaadin.ui.Label
import hu.kuru.article.Article

class ArticleItem extends CustomComponent {
	private Article article;

	ArticleItem(Article article) {
		this.article = article;
		setCompositionRoot(build())
	}
	
	private Component build() {
		new Label(article.name)
	}
}
