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
import hu.kuru.vaadin.KFieldGroup;
import hu.kuru.vaadin.component.KNotification;
import hu.kuru.vaadin.component.KTextArea;
import hu.kuru.vaadin.component.KTextField;
import hu.kuru.valueset.ValueSet;
import hu.kuru.valueset.ValueSetRepo;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.util.converter.StringToLongConverter;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleMaintComp extends CustomComponent {

	private TextField code;
	private TextField name;
	private TextField price;
	private ComboBox unit;
	private Image icon;
	private TextArea description;

	private KFieldGroup<Article> fg;
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
						setValidationVisible(true);
						UIExceptionHandler.handleException(e);
					}
				}
			});
		}
	}

	public ArticleMaintComp(Article article) {
		fg = new KFieldGroup<>(Article.class);
		init(article.getIcon());
		fg.bindMemberFields(this);
		fg.setItemDataSource(article);
		setCompositionRoot(build());
		addAttachListener(new EventBusAttachListener(this));
		addDetachListener(new EventBusDetachListener(this));
	}

	@Subscribe
	public void onIconChoosed(IconChoosedEvent event) {
		icon.setSource(new ThemeResource(event.getResource()));
	}

	private void init(String iconPath) {
		code = new KTextField("Cikk kód");
		name = new KTextField("Cikk név");
		price = new KTextField("Cikk ár (forintban)");
		unit = new ComboBox("Mértékegység");
		unit.addItems(ServiceLocator.getBean(ValueSetRepo.class).findByName(ValueSet.QUANTITY).getValues());
		description = new KTextArea("Cikk leírás");
		price.setConverter(new StringToLongConverter());
		price.setConversionError("Csak számot lehet megadni!");
		icon = new Image(null, new ThemeResource(iconPath != null ? iconPath : Icon.DEFAULT.getResource()));
		icon.addClickListener(new MouseEvents.ClickListener() {

			@Override
			public void click(ClickEvent event) {
				UI.getCurrent().addWindow(new IconPopup());
			}
		});
		name.setSizeFull();
		code.setSizeFull();
		price.setSizeFull();
		unit.setSizeFull();
		setValidationVisible(false);
	}

	private void setValidationVisible(boolean visible) {
		name.setValidationVisible(visible);
		code.setValidationVisible(visible);
		unit.setValidationVisible(visible);
	}

	private Component build() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setMargin(true);
		main.setSpacing(true);

		HorizontalLayout bottomLayout = new HorizontalLayout();
		bottomLayout.setSpacing(true);

		VerticalLayout details = new VerticalLayout();
		details.setSpacing(true);
		details.addComponent(code);
		details.addComponent(name);
		details.addComponent(unit);
		details.addComponent(price);

		bottomLayout.addComponent(icon);
		bottomLayout.addComponent(description);
		main.addComponent(details);
		main.addComponent(bottomLayout);
		Button saveBtn = new SaveButton();
		saveBtn.setSizeUndefined();
		main.addComponent(saveBtn);
		main.setComponentAlignment(saveBtn, Alignment.BOTTOM_RIGHT);
		return main;
	}


	public void setWindow(Window window) {
		this.window = window;
	}

}
