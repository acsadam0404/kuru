package hu.kuru.vaadin.article;

import hu.kuru.Icon;
import hu.kuru.ServiceLocator;
import hu.kuru.UIEventBus;
import hu.kuru.UIExceptionHandler;
import hu.kuru.article.Article;
import hu.kuru.article.ArticleService;
import hu.kuru.eventbus.ArticlesRefreshEvent;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;
import hu.kuru.eventbus.IconChoosedEvent;
import hu.kuru.vaadin.component.KNotification;
import hu.kuru.vaadin.component.KTextArea;
import hu.kuru.vaadin.component.KTextField;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.converter.StringToLongConverter;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
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
						new KNotification("Sikeres mentés!").showSuccess();
					} catch (Exception e) {
						UIExceptionHandler.handleException(e);
					}
				}
			});
		}
	}

	private ArticleModComp(Article article) {
		init(article.getIcon());
		fg.bindMemberFields(this);
		fg.setItemDataSource(article);
		setCompositionRoot(buildLayout());
		addAttachListener(new EventBusAttachListener(this));
		addDetachListener(new EventBusDetachListener(this));
	}


	@Subscribe
	public void onIconChoosed(IconChoosedEvent event) {
		icon.setSource(new ThemeResource(event.getResource()));
	}

	private void init(String iconPath) {
		fg = new BeanFieldGroup<Article>(Article.class);
		code = new KTextField("Cikk kód");
		name = new KTextField("Cikk név");
		price = new KTextField("Cikk ár");
		price.setConverter(new StringToLongConverter());
		icon = new Image(null, new ThemeResource(iconPath != null ? iconPath : Icon.DEFAULT.getResource()));
		icon.addClickListener(new MouseEvents.ClickListener() {

			@Override
			public void click(ClickEvent event) {
				UI.getCurrent().addWindow(new IconPopup());
			}
		});
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
