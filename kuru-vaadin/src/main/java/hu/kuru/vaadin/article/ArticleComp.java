package hu.kuru.vaadin.article;

import hu.kuru.ServiceLocator;
import hu.kuru.UIEventBus;
import hu.kuru.UIExceptionHandler;
import hu.kuru.article.Article;
import hu.kuru.article.ArticleService;
import hu.kuru.eventbus.ArticleSelectedEvent;
import hu.kuru.eventbus.ArticlesRefreshEvent;
import hu.kuru.vaadin.component.KNotification;
import hu.kuru.vaadin.component.KWindow;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.alump.masonry.MasonryLayout;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ArticleComp extends CustomComponent {

	private Map<Article, Component> articleMap;

	private MasonryLayout articlesLayout;
	private Button modifyBtn;
	private Button deleteBtn;

	private Long selectedArticleId;

	public ArticleComp() {
		articleMap = new LinkedHashMap<Article, Component>();
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setContent(buildLayout());
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		panel.addStyleName("overflow");
		setCompositionRoot(panel);
	}

	@Subscribe
	public void handleArticleSelect(ArticleSelectedEvent event) {
		selectedArticleId = event.getArticleId();
		boolean showBtn = selectedArticleId != null;
		modifyBtn.setEnabled(showBtn);
		deleteBtn.setEnabled(showBtn);
	}

	@Subscribe
	public void handleArticlesRefresh(ArticlesRefreshEvent event) {
		articleMap = new LinkedHashMap<Article, Component>();
		setCompositionRoot(buildLayout());
	}

	private Component buildLayout() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setSpacing(true);
		main.setMargin(true);
		Responsive.makeResponsive(main);
		main.addComponent(buildHeader());
		main.addComponent(buildArticlesLayout());
		return main;
	}

	private void buildSoughtArticlesLayout(Map<Article, Component> articleMap) {
		articlesLayout.removeAllComponents();
		boolean marked = false;
		for (Article article : articleMap.keySet()) {
			Component component = articleMap.get(article);
			if (shouldBeGreater(article.getCode(), article.getName())) {
				articlesLayout.addComponent(component, MasonryLayout.DOUBLE_WIDE_STYLENAME);
			} else {
				articlesLayout.addComponent(component);
			}
			if (!marked && selectedArticleId != null && article.getId().intValue() == selectedArticleId) {
				marked = true;
			}
		}
		if (selectedArticleId != null && !marked) {
			UIEventBus.post(new ArticleSelectedEvent(null));
		}
	}

	private Component buildArticlesLayout() {
		List<Article> activeList = Article.findAllActive();
		articlesLayout = new MasonryLayout();
		articlesLayout.setSizeFull();
		for (Article article : activeList) {
			Component component = ArticleBox.buildArticleBox(article);
			articleMap.put(article, component);
			if (shouldBeGreater(article.getCode(), article.getName())) {
				articlesLayout.addComponent(component, MasonryLayout.DOUBLE_WIDE_STYLENAME);
			} else {
				articlesLayout.addComponent(component);
			}
		}
		return articlesLayout;
	}

	private boolean shouldBeGreater(String code, String name) {
		return code.length() > 12 || name.length() > 12;
	}

	private Component buildHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.setSizeFull();
		header.setSpacing(true);

		HorizontalLayout right = new HorizontalLayout();
		right.setSpacing(true);

		Label articleLabel = new Label("Cikkek");
		articleLabel.setStyleName("title");

		Button addBtn = new ModifyButton("Hozzáadás", true);
		modifyBtn = new ModifyButton("Módosítás", false);
		deleteBtn = new DeleteButton();

		modifyBtn.setEnabled(false);
		deleteBtn.setEnabled(false);

		right.addComponent(new SearchField());
		right.addComponent(addBtn);
		right.addComponent(modifyBtn);
		right.addComponent(deleteBtn);

		header.addComponent(articleLabel);
		header.addComponent(right);
		header.setComponentAlignment(right, Alignment.MIDDLE_RIGHT);

		return header;
	}

	protected void searchInArticles(String value) {
		Map<Article, Component> articles = new LinkedHashMap<Article, Component>();
		for (Article article : articleMap.keySet()) {
			if (article.getName().toLowerCase().contains(value.toLowerCase())
					|| article.getCode().toLowerCase().contains(value.toLowerCase())) {
				articles.put(article, articleMap.get(article));
			}
		}
		buildSoughtArticlesLayout(articles);
	}

	private class SearchField extends TextField {
		private SearchField() {
			setInputPrompt("Keresés");
			setIcon(FontAwesome.SEARCH);
			addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
			addTextChangeListener(new TextChangeListener() {

				@Override
				public void textChange(TextChangeEvent event) {
					searchInArticles(event.getText());
				}
			});
		}
	}

	private class ModifyButton extends Button {
		private ModifyButton(final String caption, final boolean isNew) {
			super(caption);
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					final KWindow window = new KWindow(caption);
					ArticleModComp comp = ArticleModComp.fromArticle(isNew ? new Article() : Article.findOne(selectedArticleId));
					comp.setWindow(window);
					window.setContent(comp);
					UI.getCurrent().addWindow(window);
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
					try {
						ServiceLocator.getBean(ArticleService.class).delete(selectedArticleId);
						setCompositionRoot(buildLayout());
						new KNotification("Sikeres törlés!").showSuccess();
					} catch (Exception e) {
						UIExceptionHandler.handleException(e);
					}
				}
			});
		}
	}

}
