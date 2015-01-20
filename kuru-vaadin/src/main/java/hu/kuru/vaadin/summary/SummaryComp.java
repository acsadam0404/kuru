package hu.kuru.vaadin.summary;

import hu.kuru.bill.Bill;
import hu.kuru.item.Item;

import org.springframework.context.annotation.Scope;

import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Scope("prototype")
@org.springframework.stereotype.Component
public class SummaryComp extends CustomComponent {

	public SummaryComp() {
		setCompositionRoot(buildLayout());
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
		panel.setContent(IssuedItemBoxFactory.createBoxItems(Item.findIssuedItems()));
		return panel;
	}

	private Component buildBoxLayout() {
		HorizontalLayout boxLayout = new HorizontalLayout();
		boxLayout.setSizeFull();
		boxLayout.setSpacing(true);
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Nyitott számlák száma", Bill.countOpenBills().toString()));
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Nyitott számlák értéke", Item.countOpenBillSummary() + " Ft"));
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Napi bevétel", (Item.countDailyIncome() != null ? Item.countDailyIncome() : 0)
				+ " Ft"));
		return boxLayout;
	}

}
