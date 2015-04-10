package hu.kuru.ui.view;

import hu.kuru.KuruUI;
import hu.kuru.TouchkitNavigator;
import hu.kuru.customer.Customer;
import hu.kuru.ui.component.BillComponent;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Scope("prototype")
@VaadinView(name = BillsViewForWaiter.NAME)
public class BillsViewForWaiter extends CustomComponent implements View {
	public static final String NAME = "BillsViewForWaiter";
	private String customerCode;

	private Component build() {
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.setSpacing(true);
		root.setMargin(true);
		HorizontalLayout actions = new HorizontalLayout();
		actions.setSizeFull();
		Component backBtn = createBackButton();
		actions.addComponent(backBtn);
		actions.setComponentAlignment(backBtn, Alignment.MIDDLE_RIGHT);
		root.addComponent(actions);
		root.addComponent(new BillComponent(customerCode));
		panel.setContent(root);
		return panel;
	}

	// TODO: máshol is van ugyanez a gomb használva közösbe kivenni!!
	private Component createBackButton() {
		Button backButton = new Button("Vissza");

		backButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				KuruUI.getCurrent().getNavigator().navigateTo(MainViewForWaiter.NAME);
			}
		});

		return backButton;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Map<String, String> params = TouchkitNavigator.paramsToMap(event.getParameters());
		customerCode = params.get(Customer.CODE);
		setCompositionRoot(build());
	}

}
