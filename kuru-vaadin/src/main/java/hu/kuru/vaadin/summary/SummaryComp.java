package hu.kuru.vaadin.summary;

import hu.kuru.bill.Bill;
import hu.kuru.item.Item;
import hu.si.vaadin.converter.AbstractCustomizableStringToNumberConverter;
import hu.si.vaadin.converter.StringToLongConverter;

import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SummaryComp extends CustomComponent {

	public SummaryComp() {
		setSizeFull();
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setSpacing(true);
		main.setMargin(true);
		Responsive.makeResponsive(main);
		main.addComponent(buildBoxLayout());
		Component newItemsLayout = buildNewItemsLayout();
		main.addComponent(newItemsLayout);
		main.setExpandRatio(newItemsLayout, 1f);
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
		boxLayout.setSizeUndefined();
		boxLayout.setSpacing(true);
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Nyitott számlák száma", Bill.countOpenBills().toString()));
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Nyitott számlák értéke", new StringToLongConverter(AbstractCustomizableStringToNumberConverter.FORMAT_MONETARY).convertToPresentation(Item.countOpenBillSummary()) + " Ft"));
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Napi bevétel", new StringToLongConverter(AbstractCustomizableStringToNumberConverter.FORMAT_MONETARY).convertToPresentation(Item.countDailyIncome())+ " Ft"));
		return boxLayout;
	}

}
