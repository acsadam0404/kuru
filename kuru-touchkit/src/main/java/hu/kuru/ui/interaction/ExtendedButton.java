package hu.kuru.ui.interaction;

import hu.kuru.article.Article;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

/**
 * Kiterjesztett gomb, amely a bevásárló kosárhoz szükséges
 * 
 * @author tamas.mester
 *
 */
public class ExtendedButton extends Button {

	// TODO: object vagy osztály átnevezés
	private Article article;

	public ExtendedButton(String caption, Resource imageResource, Article article) {
		super(caption, imageResource);
		this.article = article;
	}

	public Article getArticle() {
		return this.article;
	}

	public ExtendedButton withBiggerSize() {
		setWidth("150%");
		setHeight("150%");
		return this;
	}

}
