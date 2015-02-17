package hu.kuru.vaadin.summary;

import hu.kuru.bill.Bill;
import hu.kuru.item.Item;

import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SummaryComp extends CustomComponent {

	public SummaryComp() {
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setContent(buildLayout());
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		setCompositionRoot(panel);
	}

	private Component buildLayout() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setSpacing(true);
		main.setMargin(true);
		Responsive.makeResponsive(main);
		main.addComponent(buildBoxLayout());
		main.addComponent(buildNewItemsLayout());
		return main;
	}

	private Component buildNewItemsLayout() {
		Panel panel = new Panel("Kiadásra váró tételek");
		panel.setSizeFull();
		panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
		panel.addStyleName("to-be-issued");
		panel.setContent(IssuedItemBox.createBoxItems(Item.findIssuedItems()));
		return panel;
	}

	private Component buildBoxLayout() {
		HorizontalLayout boxLayout = new HorizontalLayout();
		boxLayout.setSizeFull();
		boxLayout.setSpacing(true);
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Nyitott számlák száma", Bill.countOpenBills().toString()));
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Nyitott számlák értéke",
				(Item.countOpenBillSummary() != null ? Item.countOpenBillSummary() : 0) + " Ft"));
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Napi bevétel", (Item.countDailyIncome() != null ? Item.countDailyIncome() : 0)
				+ " Ft"));
		return boxLayout;
	}

}
