package hu.kuru.ui.layout;

import hu.kuru.article.Article;
import hu.kuru.ui.interaction.ExtendedButton;
import hu.kuru.util.KuruUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ArticleBox extends CustomComponent {

	private Article article;
	private ExtendedButton cartButton;
	
	public ArticleBox( ExtendedButton cartButton) {
		this.article = cartButton.getArticle();
		this.cartButton = cartButton;
		this.setCompositionRoot(buildContent());
	}
	
	private Component buildContent() {
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setSizeFull();
		HorizontalLayout hLayoutForTitleAndPicture = new HorizontalLayout();
		hLayoutForTitleAndPicture.setSizeFull();
		Label articleName = new Label(article.getName());
		hLayoutForTitleAndPicture.addComponent(articleName);
		hLayoutForTitleAndPicture.setComponentAlignment(articleName, Alignment.TOP_CENTER);
		hLayoutForTitleAndPicture.addComponent(KuruUtils.getArticleImageFromFileSystem(article.getIcon()));
		vLayout.addComponent(hLayoutForTitleAndPicture);
		Label price = new Label("Ár: " + article.getPrice() + " / " + article.getUnit());
		vLayout.addComponent(price);
		vLayout.addComponent(cartButton);

		
		return vLayout;
	}
}
