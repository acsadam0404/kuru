package hu.kuru.vaadin.article;

import hu.kuru.ServiceLocator;
import hu.kuru.article.ArticleCategory;
import hu.kuru.article.ArticleCategoryService;
import hu.kuru.eventbus.ArticleCategoriesRefreshEvent;
import hu.kuru.eventbus.ArticleCategorySelectedEvent;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;
import hu.kuru.vaadin.component.KNotification;
import hu.kuru.vaadin.component.KWindow;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.alump.masonry.MasonryLayout;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ArticleCategoryComp extends CustomComponent {
	
	private MasonryLayout mainLayout;
	private Map<ArticleCategory, Component> categoryMap;
	
	private Long selectedArticleCategoryId;
	Button modifyBtn;
	Button deleteBtn;
	Button articlesBtn;
	
	public ArticleCategoryComp() {
		categoryMap = new LinkedHashMap<ArticleCategory, Component>();
		setSizeFull();
		setCompositionRoot(build());
	}
	
	private Component build() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setSpacing(true);
		main.setMargin(true);
		Responsive.makeResponsive(main);
		main.addComponent(buildHeader());
		Component body = buildArticleCategoryLayout();
		main.addComponent(body);
		main.setExpandRatio(body, 1f);
		return main;
	}
	
	private Component buildHeader() {
		HorizontalLayout header = new HorizontalLayout();
		HorizontalLayout spacer = new HorizontalLayout();
		header.setSizeUndefined();
		header.setWidth("100%");
		header.setSpacing(true);

		Button addBtn = new ModifyButton("Cikkcsoport hozzáadása", true);
		articlesBtn = new ArticlesButton("Cikkek");
		modifyBtn = new ModifyButton("Módosítás", false);
		deleteBtn = new DeleteButton();

		articlesBtn.setEnabled(false);
		modifyBtn.setEnabled(false);
		deleteBtn.setEnabled(false);

		header.addComponent(spacer);
		header.addComponent(addBtn);
		header.addComponent(articlesBtn);
		header.addComponent(modifyBtn);
		header.addComponent(deleteBtn);
		header.setExpandRatio(spacer, 1.0f);

		return header;
	}
	
	private class ModifyButton extends Button {
		private ModifyButton(final String caption, final boolean isNew) {
			super(caption);
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					final KWindow window = new KWindow(caption);
					ArticleCategoryMaintComp comp = new ArticleCategoryMaintComp(isNew ? new ArticleCategory() : ArticleCategory.findOne(selectedArticleCategoryId));
					comp.setWindow(window);
					window.setContent(comp);
					UI.getCurrent().addWindow(window);
				}
			});
		}
	}
	
	private class ArticlesButton extends Button {
		private ArticlesButton(final String caption) {
			super(caption);
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					Component articleComp = new ArticleComp(selectedArticleCategoryId);
					articleComp.addAttachListener(new EventBusAttachListener(articleComp));
					articleComp.addDetachListener(new EventBusDetachListener(articleComp));
					setCompositionRoot(articleComp);
				}
			});
		}
	}
	
	private class DeleteButton extends Button {
		private DeleteButton() {
			super("Törlés");
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					ServiceLocator.getBean(ArticleCategoryService.class).delete(selectedArticleCategoryId);
					setCompositionRoot(build());
					new KNotification("Sikeres törlés!").showSuccess();
				}
			});
		}
	}
	
	@Subscribe
	public void handleArticleCategorySelect(ArticleCategorySelectedEvent event) {
		selectedArticleCategoryId = event.getArticleCategoryId();
		boolean showBtn = selectedArticleCategoryId != null;
		articlesBtn.setEnabled(showBtn);
		modifyBtn.setEnabled(showBtn);
		deleteBtn.setEnabled(showBtn);
	}
	
	@Subscribe
	public void handleArticleCategoriesRefresh(ArticleCategoriesRefreshEvent event) {
		categoryMap = new LinkedHashMap<ArticleCategory, Component>();
		setCompositionRoot(build());
	}
	
	private Component buildArticleCategoryLayout() {
		List<ArticleCategory> categoryList = ArticleCategory.findAllValidCategory();
		mainLayout = new MasonryLayout();
		mainLayout.setSizeFull();
		for (ArticleCategory category : categoryList) {
			Component component = ArticleCategoryBox.buildArticleCategoryBox(category);
			categoryMap.put(category, component);
			if (shouldBeGreater(category.getCode(), category.getName())) {
				mainLayout.addComponent(component, MasonryLayout.DOUBLE_WIDE_STYLENAME);
			} else {
				mainLayout.addComponent(component);
			}
		}
		Panel articlesPanel = new Panel(mainLayout);
		articlesPanel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		articlesPanel.setSizeFull();
		return articlesPanel;
	}
	
	private boolean shouldBeGreater(String code, String name) {
		return code.length() > 12 || name.length() > 12;
	}
}
