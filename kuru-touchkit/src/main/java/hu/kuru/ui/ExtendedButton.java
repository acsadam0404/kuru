package hu.kuru.ui;

import hu.kuru.article.Article;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

public class ExtendedButton extends Button {

	//TODO: object vagy osztály átnevezés
	private Article article;
	
	public ExtendedButton(String caption, Resource imageResource, Article article) {
		super(caption,imageResource);
		this.article = article;
	}
	
	public Article getArticle() {
		return this.article;
	}
}
