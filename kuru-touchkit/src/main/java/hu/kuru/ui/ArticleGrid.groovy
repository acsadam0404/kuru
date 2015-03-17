package hu.kuru.ui

import hu.kuru.article.Article

import com.vaadin.ui.Component
import com.vaadin.ui.CustomComponent
import com.vaadin.ui.GridLayout

class ArticleGrid extends CustomComponent{
	private GridLayout root
	
	ArticleGrid() {
		setCompositionRoot(build())
		refresh()
	}
	
	private Component build() {
		root = new GridLayout(2,1)
		return root
	}
	
	private void refresh() {
		def articles = Article.findAll()
		articles?.each {
			root.addComponent(new ArticleItem(it))
		}
	}
}
