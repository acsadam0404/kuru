package hu.kuru.vaadin.article;

import hu.kuru.ServiceLocator;
import hu.kuru.UIEventBus;
import hu.kuru.UIExceptionHandler;
import hu.kuru.article.ArticleCategory;
import hu.kuru.article.ArticleCategoryService;
import hu.kuru.eventbus.ArticleCategoriesRefreshEvent;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;
import hu.kuru.util.ArticleImageUploaderComp;
import hu.kuru.vaadin.KFieldGroup;
import hu.kuru.vaadin.component.KNotification;
import hu.kuru.vaadin.component.KTextField;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleCategoryMaintComp extends CustomComponent {

	private TextField code;
	private TextField name;
	private ArticleImageUploaderComp iconUpload;

	private KFieldGroup<ArticleCategory> fg;
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
						ArticleCategory articleCategory = fg
								.getItemDataSource().getBean();
						if (iconUpload.getImageName() != null) {
							articleCategory.setIcon(iconUpload.getImageName());
						}
						ServiceLocator.getBean(ArticleCategoryService.class)
								.save(fg.getItemDataSource().getBean());
						window.close();
						new KNotification("Sikeres mentés!").showSuccess();
						UIEventBus.post(new ArticleCategoriesRefreshEvent());
					} catch (Exception e) {
						setValidationVisible(true);
						UIExceptionHandler.handleException(e);
					}
				}
			});
		}
	}

	public ArticleCategoryMaintComp(ArticleCategory articleCategory) {
		fg = new KFieldGroup<>(ArticleCategory.class);
		init();
		fg.bindMemberFields(this);
		fg.setItemDataSource(articleCategory);
		setCompositionRoot(build());
		addAttachListener(new EventBusAttachListener(this));
		addDetachListener(new EventBusDetachListener(this));
	}

	private void init() {
		code = new KTextField("Cikkcsoport kód");
		name = new KTextField("Cikkcsoport név");
		iconUpload = new ArticleImageUploaderComp();

		name.setSizeFull();
		code.setSizeFull();
		iconUpload.setSizeFull();
		setValidationVisible(false);
	}

	private void setValidationVisible(boolean visible) {
		name.setValidationVisible(visible);
		code.setValidationVisible(visible);
		iconUpload.setSizeFull();
	}

	private Component build() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setMargin(true);
		main.setSpacing(true);

		VerticalLayout details = new VerticalLayout();
		details.setSpacing(true);
		details.addComponent(code);
		details.addComponent(name);
		details.addComponent(iconUpload);

		main.addComponent(details);
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
