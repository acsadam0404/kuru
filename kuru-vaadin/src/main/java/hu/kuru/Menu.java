package hu.kuru;

import hu.kuru.vaadin.article.ArticleView;
import hu.kuru.vaadin.bill.BillView;
import hu.kuru.vaadin.component.button.MenuButton;
import hu.kuru.vaadin.security.Authentication;
import hu.kuru.vaadin.summary.SummaryView;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class Menu extends CustomComponent {

	private static final long serialVersionUID = 1L;
	private Authentication authentication;

	public Menu() {
		authentication = new Authentication();
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
		Button article = MenuButton.fromCaptionViewIcon("Cikkek", ArticleView.NAME, FontAwesome.BARS);
		Button clients = MenuButton.fromCaptionViewIcon("Számlák", BillView.NAME, FontAwesome.BANK);
		Button summary = MenuButton.fromCaptionViewIcon("Összesítő", SummaryView.NAME, FontAwesome.BAR_CHART_O);
		Button logout = MenuButton.fromCaptionViewIcon("Kilépés", null, FontAwesome.POWER_OFF);
		logout.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				authentication.logout();
			}
		});
		btnLayout.addComponents(article, clients, summary, logout);
		btnLayout.setComponentAlignment(article, Alignment.MIDDLE_CENTER);
		btnLayout.setComponentAlignment(clients, Alignment.MIDDLE_CENTER);
		btnLayout.setComponentAlignment(summary, Alignment.MIDDLE_CENTER);
		btnLayout.setComponentAlignment(logout, Alignment.MIDDLE_CENTER);
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
