package hu.kuru.vaadin.article;

import hu.kuru.ServiceLocator;
import hu.kuru.UIEventBus;
import hu.kuru.UIExceptionHandler;
import hu.kuru.article.Article;
import hu.kuru.article.ArticleCategory;
import hu.kuru.article.ArticleCategoryRepo;
import hu.kuru.article.ArticleService;
import hu.kuru.eventbus.ArticlesRefreshEvent;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;
import hu.kuru.util.ArticleImageUploaderComp;
import hu.kuru.vaadin.KFieldGroup;
import hu.kuru.vaadin.component.KNotification;
import hu.kuru.vaadin.component.KTextArea;
import hu.kuru.vaadin.component.KTextField;
import hu.kuru.valueset.ValueSet;
import hu.kuru.valueset.ValueSetRepo;

import com.vaadin.data.util.converter.StringToLongConverter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleMaintComp extends CustomComponent {

	private TextField code;
	private TextField name;
	private TextField price;
	private ComboBox unit;
	private ComboBox articleCategory;
	private TextArea description;
	private ArticleImageUploaderComp artiUploaderComp;

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
						article.setActive(true);
						if (artiUploaderComp.getImageName() != null) {
							article.setIcon(artiUploaderComp.getImageName());
						}
						article.setArticleCategory((ArticleCategory) articleCategory
								.getValue());
						ServiceLocator.getBean(ArticleService.class).save(
								fg.getItemDataSource().getBean());
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
		if(code.getValue() != null) {
			code.setReadOnly(true);
		}
		setCompositionRoot(build());
		articleCategory.setValue(article.getArticleCategory());
		articleCategory.setReadOnly(true);
		addAttachListener(new EventBusAttachListener(this));
		addDetachListener(new EventBusDetachListener(this));
	}

	private void init(String iconPath) {
		articleCategory = new ComboBox("Cikkcsoport");
		articleCategory.addItems(ServiceLocator.getBean(
				ArticleCategoryRepo.class).findAll());
		code = new KTextField("Cikk kód");
		name = new KTextField("Cikk név");
		price = new KTextField("Cikk ár (forintban)");
		unit = new ComboBox("Mértékegység");
		unit.addItems(ServiceLocator.getBean(ValueSetRepo.class)
				.findByName(ValueSet.QUANTITY).getValues());
		description = new KTextArea("Cikk leírás");
		price.setConverter(new StringToLongConverter());
		price.setConversionError("Csak számot lehet megadni!");

		articleCategory.setSizeFull();
		name.setSizeFull();
		code.setSizeFull();
		price.setSizeFull();
		unit.setSizeFull();

		setValidationVisible(false);
	}

	private void setValidationVisible(boolean visible) {
		articleCategory.setValidationVisible(visible);
		name.setValidationVisible(visible);
		code.setValidationVisible(visible);
		unit.setValidationVisible(visible);
	}

	private Component build() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setMargin(true);
		main.setSpacing(true);

		VerticalLayout details = new VerticalLayout();
		details.setSpacing(true);
		details.addComponent(articleCategory);
		details.addComponent(code);
		details.addComponent(name);
		details.addComponent(unit);
		details.addComponent(price);

		main.addComponent(details);
		main.addComponent(description);
		main.addComponent(createImageUploadComp());
		Button saveBtn = new SaveButton();
		saveBtn.setSizeUndefined();
		main.addComponent(saveBtn);
		main.setComponentAlignment(saveBtn, Alignment.BOTTOM_RIGHT);
		return main;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	private Component createImageUploadComp() {
		artiUploaderComp = new ArticleImageUploaderComp();
		return artiUploaderComp;
	}

}
