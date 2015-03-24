package hu.kuru.ui.view;

import hu.kuru.ui.component.BillComponent;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class BillsViewForWaiter extends NavigationView {

	private NavigationManager manager;
	private VerticalLayout contentLayout;
	private long customerId;
	
	public BillsViewForWaiter(NavigationManager manager, long customerId) {
		super();
		this.manager = manager;
		this.customerId = customerId;
		this.setRightComponent(createBackButton());
		this.setLeftComponent(new VerticalLayout());
		this.setContent(buildContent());
	}
	
	private Component buildContent() {
		contentLayout = new VerticalLayout();
		contentLayout.setSizeFull();
		contentLayout.addComponent(new BillComponent(customerId));
		return this.contentLayout;
	}
	
	//TODO: máshol is van ugyanez a gomb használva közösbe kivenni!!
	private Component createBackButton() {
		Button backButton = new Button("Vissza");
		
		backButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				BillsViewForWaiter.this.manager.navigateBack();
			}
		});
		
		return backButton;
	}
}
