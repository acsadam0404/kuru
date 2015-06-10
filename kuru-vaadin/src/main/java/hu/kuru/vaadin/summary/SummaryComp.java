package hu.kuru.vaadin.summary;

import hu.kuru.bill.Bill;
import hu.kuru.item.Item;
import hu.si.vaadin.converter.AbstractCustomizableStringToNumberConverter;
import hu.si.vaadin.converter.StringToLongConverter;

import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

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
		return main;
	}

	private Component buildBoxLayout() {
		VerticalLayout boxLayout = new VerticalLayout();
		boxLayout.setSizeFull();
		boxLayout.setSpacing(true);
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Nyitott számlák száma", Bill.countOpenBills().toString()));
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Nyitott számlák értéke", new StringToLongConverter(AbstractCustomizableStringToNumberConverter.FORMAT_MONETARY).convertToPresentation(Item.countOpenBillSummary()) + " Ft"));
		boxLayout.addComponent(SummaryBox.fromTitleAndValue("Napi bevétel", new StringToLongConverter(AbstractCustomizableStringToNumberConverter.FORMAT_MONETARY).convertToPresentation(Item.countDailyIncome())+ " Ft"));
		for (Component c : boxLayout) {
			boxLayout.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
		}
		return boxLayout;
	}

}
