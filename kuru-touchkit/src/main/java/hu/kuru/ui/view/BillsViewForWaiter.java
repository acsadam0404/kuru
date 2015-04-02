package hu.kuru.ui.view;

import java.util.Map;

import org.vaadin.spring.navigator.annotation.VaadinView;

import hu.kuru.KuruUI;
import hu.kuru.TouchkitNavigator;
import hu.kuru.customer.Customer;
import hu.kuru.ui.component.BillComponent;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@VaadinView(name = BillsViewForWaiter.NAME)
public class BillsViewForWaiter extends CustomComponent implements View {
	public static final String NAME = "BillsViewForWaiter";
	private String customerCode;

	private Component build() {
		VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		HorizontalLayout actions = new HorizontalLayout();
		actions.addComponent(createBackButton());
		root.addComponent(actions);
		root.addComponent(new BillComponent(customerCode));
		return root;
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
