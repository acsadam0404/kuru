package hu.kuru.ui.layout;

import hu.kuru.article.Article;
import hu.kuru.bill.Bill;
import hu.kuru.customer.Customer;
import hu.kuru.ui.interaction.ExtendedButton;
import hu.kuru.util.Pair;

import java.util.List;
import java.util.Map;

import org.vaadin.alump.masonry.MasonryLayout;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Cikkek megjelenítését végző osztály
 * 
 * @author
 *
 */
public class ArticleLayout extends MasonryLayout {

	private Map<String, Pair<Article, Integer>> cartContent;
	private Customer customer;
	Long categoryId;

	public ArticleLayout(Customer customer,
			Map<String, Pair<Article, Integer>> cartContent, Long categoryId) {
		setSizeFull();
		setImmediate(true);
		this.cartContent = cartContent;
		this.customer = customer;
		this.categoryId = categoryId;

		buildContent();
		Responsive.makeResponsive(this);
	}

	/**
	 * Tartalom felépítését végző függvény
	 */
	private void buildContent() {
		List<Article> articleList = Article.findAllActiveByCategoryId(categoryId);
		for (Article article : articleList) {
			addComponent(new ArticleBox(createCartButton(article)));
		}
	}

	private ExtendedButton createCartButton(Article article) {
		ExtendedButton cartButton = new ExtendedButton("Kosárba",
				FontAwesome.SHOPPING_CART, article);
		cartButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (Bill.hasOpenBillByCustomer(customer.getId())) {
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
				} else {
					Notification.show("Önnek nincs nyitott számlája!",
							"Kérjen segítséget a pincérektől!",
							Type.ERROR_MESSAGE);
				}
			}
		});
		return cartButton;
	}
}
