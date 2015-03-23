package hu.kuru.ui.layout;

import hu.kuru.article.Article;
import hu.kuru.ui.interaction.ExtendedButton;
import hu.kuru.util.Pair;

import java.util.List;
import java.util.Map;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

/**
 * Cikkek megjelenítését végző osztály
 * 
 * @author
 *
 */
public class ArticleLayout extends GridLayout {

	private Map<String, Pair<Article, Integer>> cartContent;

	public ArticleLayout(Map<String, Pair<Article, Integer>> cartContent) {
		super();
		this.setSizeFull();

		this.setColumns(3);
		this.setMargin(true);
		this.setImmediate(true);
		this.cartContent = cartContent;

		this.buildContent();
	}

	private void buildContent() {
		List<Article> articleList = Article.findAll();
		for (Article article : articleList) {
			VerticalLayout vLayout = new VerticalLayout();
			vLayout.addComponent(new Image(article.getName(),
					new ThemeResource(article.getIcon())));
			Label price = new Label("Ár: " + article.getPrice() + " / "
					+ article.getUnit());
			vLayout.addComponent(price);
			ExtendedButton cartButton = new ExtendedButton("",
					new ThemeResource("img/cart.png"), article);
			cartButton.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					Article article = ((ExtendedButton) event.getButton())
							.getArticle();
					if (cartContent.containsKey(article.getCode())) {
						cartContent.get(article.getCode())
								.setSecond(
										cartContent.get(article.getCode())
												.getSecond() + 1);
					} else {
						cartContent.put(article.getCode(),
								new Pair<Article, Integer>(article, 1));
					}
					Notification.show("A cikk bekerült a kosárba!",
							Type.WARNING_MESSAGE);
				}
			});
			cartButton.setWidth("120px");
			vLayout.addComponent(cartButton);

			this.addComponent(vLayout);
		}
	}
}
