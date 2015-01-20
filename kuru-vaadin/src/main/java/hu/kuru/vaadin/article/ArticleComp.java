package hu.kuru.vaadin.article;

import hu.kuru.article.Article;
import hu.kuru.article.ArticleService;
import hu.kuru.vaadin.component.KWindow;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.alump.masonry.MasonryLayout;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Scope("prototype")
@org.springframework.stereotype.Component
public class ArticleComp extends CustomComponent {

	@Autowired
	private ArticleService articleService;

	private Map<Article, Component> articleMap;

	private MasonryLayout articlesLayout;
	private Button addBtn;
	private Button modifyBtn;
	private Button deleteBtn;

	private final EventBus eventBus;
	private Long articleId;

	public ArticleComp() {
		eventBus = new EventBus(ArticleComp.class.getSimpleName());
		eventBus.register(this);
		articleMap = new LinkedHashMap<Article, Component>();
		setCompositionRoot(buildLayout());
	}

	@Subscribe
	public void handleArticleSelect(ArticleSelectedEvent event) {
		articleId = event.getArticleId();
		boolean showBtn = articleId != null;
		modifyBtn.setEnabled(showBtn);
		deleteBtn.setEnabled(showBtn);
	}

	@Subscribe
	public void handleArticlesRefresh(ArticlesRefreshEvent event) {
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
			if (!marked && articleId != null && article.getId().intValue() == articleId) {
				marked = true;
			}
		}
		if (articleId != null && !marked) {
			eventBus.post(new ArticleSelectedEvent(null));
		}
	}

	private Component buildArticlesLayout() {
		List<Article> activeList = Article.findAllActive();
		articlesLayout = new MasonryLayout();
		articlesLayout.setSizeFull();
		for (Article article : activeList) {
			Component component = ArticleBoxFactory.buildArticleBoxes(eventBus, article);
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

		TextField searchField = new TextField();
		searchField.setInputPrompt("Keresés");
		searchField.setIcon(FontAwesome.SEARCH);
		searchField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		searchField.addTextChangeListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				regenerateArticleLayout(event.getText());
			}
		});

		addBtn = new Button("Hozzáadás");
		modifyBtn = new Button("Módosítás");
		deleteBtn = new Button("Törlés");

		modifyBtn.setEnabled(false);
		deleteBtn.setEnabled(false);

		addClickListeners();

		right.addComponent(searchField);
		right.addComponent(addBtn);
		right.addComponent(modifyBtn);
		right.addComponent(deleteBtn);

		header.addComponent(articleLabel);
		header.addComponent(right);
		header.setComponentAlignment(right, Alignment.MIDDLE_RIGHT);

		return header;
	}

	protected void regenerateArticleLayout(String value) {
		Map<Article, Component> articles = new LinkedHashMap<Article, Component>();
		for (Article article : articleMap.keySet()) {
			if (article.getName().startsWith(value) || article.getCode().startsWith(value)) {
				articles.put(article, articleMap.get(article));
			}
		}
		buildSoughtArticlesLayout(articles);
	}

	private void addClickListeners() {
		addBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final KWindow window = new KWindow("Cikk létrehozása");
				ArticleModComp comp = ArticleModComp.fromArticle(eventBus, new Article());
				window.setContent(comp);
				comp.addCloseListener(window);
				UI.getCurrent().addWindow(window);
			}
		});

		modifyBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final KWindow window = new KWindow("Cikk módosítása");
				ArticleModComp comp = ArticleModComp.fromArticle(eventBus, Article.findOne(articleId));
				window.setContent(comp);
				comp.addCloseListener(window);
				UI.getCurrent().addWindow(window);
			}
		});
		deleteBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				articleService.delete(articleId);
				setCompositionRoot(buildLayout());
				Notification.show("Sikeres törlés!");
			}
		});
	}
}
