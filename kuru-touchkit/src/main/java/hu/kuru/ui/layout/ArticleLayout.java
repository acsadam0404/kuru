package hu.kuru.ui.layout;

import hu.kuru.article.Article;
import hu.kuru.ui.interaction.ExtendedButton;
import hu.kuru.util.Pair;

import java.util.List;
import java.util.Map;

import org.vaadin.alump.masonry.MasonryLayout;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

/**
 * Cikkek megjelenítését végző osztály
 * 
 * @author
 *
 */
public class ArticleLayout extends MasonryLayout {

	private Map<String, Pair<Article, Integer>> cartContent;

	public ArticleLayout(Map<String, Pair<Article, Integer>> cartContent) {
		super();
		this.setSizeFull();

		this.setImmediate(true);
		this.cartContent = cartContent;

		this.buildContent();
	}

	/**
	 * Tartalom felépítését végző függvény
	 */
	private void buildContent() {
		List<Article> articleList = Article.findAll();
		for (Article article : articleList) {
			VerticalLayout vLayout = new VerticalLayout();
			HorizontalLayout hLayoutForTitleAndPicture = new HorizontalLayout();
			hLayoutForTitleAndPicture.setSizeFull();
			Label articleName = new Label(article.getName());
			hLayoutForTitleAndPicture.addComponent(articleName);
			hLayoutForTitleAndPicture.setComponentAlignment(articleName, Alignment.TOP_CENTER);
			hLayoutForTitleAndPicture.addComponent(new Image("",
					new ThemeResource(article.getIcon())));
			vLayout.addComponent(hLayoutForTitleAndPicture);
			Label price = new Label("Ár: " + article.getPrice() + " / "
					+ article.getUnit());
			vLayout.addComponent(price);
			ExtendedButton cartButton = new ExtendedButton("",new ThemeResource("img/cart.png"), article);
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
			cartButton.setWidth("50px");
			cartButton.setStyleName("cartButton");
			vLayout.addComponent(cartButton);

			this.addComponent(vLayout);
		}
	}
}
