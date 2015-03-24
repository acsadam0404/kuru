package hu.kuru.ui.component;

import hu.kuru.bean.ItemBean;
import hu.kuru.bill.Bill;
import hu.kuru.item.Item;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BillBox extends CustomComponent {

	private Bill bill;
	private Panel panel;
	
	public BillBox(Bill bill) {
		this.bill = bill;
		this.setCompositionRoot(buildComponent());
	}
	
	private Component buildComponent() {
		VerticalLayout box = new VerticalLayout();
		box.setSizeFull();
		box.setSpacing(true);
		box.setMargin(true);
		box.addComponent(buildHeader());
		box.addComponent(buildTable());
		box.addComponent(buildFooter());

		panel = new Panel();
		panel.setSizeFull();
		panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
		panel.addStyleName("billbox");
		panel.setContent(box);
		return panel;
	}
	
	private Component buildHeader() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeUndefined();
		layout.setWidth("100%");
		layout.setSpacing(true);
		DateField openDate = new DateField("Nyitás dátuma");
		DateField closeDate = new DateField("Zárás dátuma");
		openDate.setEnabled(false);
		closeDate.setEnabled(false);
		openDate.setResolution(Resolution.SECOND);
		closeDate.setResolution(Resolution.SECOND);
		openDate.setValue(bill.getOpenDate());
		closeDate.setValue(bill.getCloseDate());
		layout.addComponent(openDate);
		layout.addComponent(closeDate);
		return layout;
	}
	
	private Component buildTable() {
		Table table = new Table();
		table.setWidth("99%");
		table.setContainerDataSource(new BeanItemContainer<>(ItemBean.class, getItemList()));
		table.setColumnHeader("name", "Név");
		table.setColumnHeader("code", "Kód");
		table.setColumnHeader("amount", "Mennyiség");
		table.setColumnAlignment("amount", Align.RIGHT);
		table.setVisibleColumns("code", "name", "amount");
		return table;
	}
	
	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSizeUndefined();
		footer.setWidth("100%");
		HorizontalLayout priceLayout = new HorizontalLayout();

		Label price = new Label("Ár: ");
		price.setStyleName(ValoTheme.LABEL_BOLD);
		priceLayout.addComponent(price);

		footer.addComponent(priceLayout);
		footer.setComponentAlignment(priceLayout, Alignment.MIDDLE_RIGHT);
		return footer;
	}

	private List<ItemBean> getItemList() {
		List<ItemBean> beanList = new ArrayList<>();
		for (Item item : bill.getItems()) {
			beanList.add(new ItemBean(item.getArticle().getCode(), item.getArticle().getName(), item.getAmount() + " "
					+ item.getArticle().getUnit()));
		}
		return beanList;
	}
}
