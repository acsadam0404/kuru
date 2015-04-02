package hu.kuru.ui.view;

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

public class BillsViewForWaiter extends CustomComponent implements View {

	private long customerId;
	
	public BillsViewForWaiter(long customerId) {
		this.customerId = customerId;
	}
	
	private Component build() {
		VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		HorizontalLayout actions = new HorizontalLayout();
		actions.addComponent(createBackButton());
		root.addComponent(actions);
		root.addComponent(new BillComponent(customerId));
		return root;
	}
	
	//TODO: máshol is van ugyanez a gomb használva közösbe kivenni!!
	private Component createBackButton() {
		Button backButton = new Button("Vissza");
		
		backButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		
		return backButton;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(build());
	}
	
	
}
