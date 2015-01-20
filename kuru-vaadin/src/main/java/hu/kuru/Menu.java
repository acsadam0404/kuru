package hu.kuru;

import hu.kuru.vaadin.article.ArticleView;
import hu.kuru.vaadin.component.MenuButton;
import hu.kuru.vaadin.summary.SummaryView;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class Menu extends CustomComponent {

	private static final long serialVersionUID = 1L;

	public Menu() {
		Panel panel = new Panel("kuru");
		panel.setSizeFull();
		panel.addStyleName("menu");
		VerticalLayout main = new VerticalLayout();
		main.setSpacing(true);
		main.setMargin(true);
		main.addComponent(buildLogo());
		main.addComponent(buildMenuItems());
		panel.setContent(main);
		setCompositionRoot(panel);
	}

	private Component buildMenuItems() {
		VerticalLayout btnLayout = new VerticalLayout();
		btnLayout.setSpacing(true);
		Button article = MenuButton.fromCaptionAndView("Cikkek", ArticleView.NAME);
		Button clients = MenuButton.fromCaptionAndView("Ügyfelek", SummaryView.NAME);
		Button summary = MenuButton.fromCaptionAndView("Összesítő", SummaryView.NAME);
		btnLayout.addComponents(article, clients, summary);
		btnLayout.setComponentAlignment(article, Alignment.MIDDLE_CENTER);
		btnLayout.setComponentAlignment(clients, Alignment.MIDDLE_CENTER);
		btnLayout.setComponentAlignment(summary, Alignment.MIDDLE_CENTER);
		return btnLayout;
	}

	private Component buildLogo() {
		VerticalLayout logo = new VerticalLayout();
		Image image = new Image(null, new ThemeResource("img/pizza-icon.png"));
		logo.addComponent(image);
		logo.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		return logo;
	}

}
