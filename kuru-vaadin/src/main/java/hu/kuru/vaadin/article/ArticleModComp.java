package hu.kuru.vaadin.article;

import hu.kuru.ServiceLocator;
import hu.kuru.UIEventBus;
import hu.kuru.article.Article;
import hu.kuru.article.ArticleService;
import hu.kuru.eventbus.ArticlesRefreshEvent;
import hu.kuru.vaadin.component.KTextArea;
import hu.kuru.vaadin.component.KTextField;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.converter.StringToLongConverter;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleModComp extends CustomComponent {

	private TextField code;
	private TextField name;
	private TextField price;
	private Image icon;
	private TextArea description;

	private BeanFieldGroup<Article> fg;
	private Window window;

	private class SaveButton extends Button {
		private SaveButton() {
			super("Mentés");
			setSizeUndefined();
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						fg.commit();
						Article article = fg.getItemDataSource().getBean();
						article.setIcon(((ThemeResource) icon.getSource()).getResourceId());
						ServiceLocator.getBean(ArticleService.class).save(fg.getItemDataSource().getBean());
						window.close();
						UIEventBus.post(new ArticlesRefreshEvent());
						Notification.show("Sikeres mentés!");
					} catch (CommitException e) {
						Notification.show("Sikertelen mentés!");
					} catch (IllegalArgumentException ie) {
						Notification.show(ie.getMessage());
					}
				}
			});
		}
	}

	private ArticleModComp(Article article) {
		init(article.getIcon());
		bind(article);
		setCompositionRoot(buildLayout());
	}

	private void bind(Article article) {
		fg.bindMemberFields(this);
		fg.setItemDataSource(article);
	}

	private void init(String iconPath) {
		fg = new BeanFieldGroup<Article>(Article.class);
		code = new KTextField("Cikk kód");
		name = new KTextField("Cikk név");
		price = new KTextField("Cikk ár");
		price.setConverter(new StringToLongConverter());
		icon = new Image(null, new ThemeResource(iconPath != null ? iconPath : "img/default-icon.png"));
		description = new KTextArea("Cikk leírás");
	}

	private Component buildLayout() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setMargin(true);
		main.setSpacing(true);

		HorizontalLayout upperLayout = new HorizontalLayout();
		upperLayout.setSpacing(true);

		VerticalLayout details = new VerticalLayout();
		details.setSpacing(true);
		details.addComponent(code);
		details.addComponent(name);
		details.addComponent(price);

		upperLayout.addComponent(icon);
		upperLayout.addComponent(details);
		main.addComponent(upperLayout);
		main.addComponent(description);
		Button saveBtn = new SaveButton();
		saveBtn.setSizeUndefined();
		main.addComponent(saveBtn);
		main.setComponentAlignment(saveBtn, Alignment.BOTTOM_RIGHT);
		return main;
	}

	public static ArticleModComp fromArticle(Article article) {
		return new ArticleModComp(article);
	}

	public void setWindow(Window window) {
		this.window = window;
	}

}
