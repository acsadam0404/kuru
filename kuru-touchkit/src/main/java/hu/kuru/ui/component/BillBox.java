package hu.kuru.ui.component;

import hu.kuru.bean.ItemBean;
import hu.kuru.bill.Bill;
import hu.kuru.customer.Customer;
import hu.kuru.item.Item;

import java.text.DateFormat;
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
		Label openDate = new Label("Nyitás dátuma: " + bill.getOpenDate());
		openDate.setEnabled(false);
		layout.addComponent(openDate);
		return layout;
	}
	
	private Component buildTable() {
		Table table = new Table();
		table.setWidth("99%");
		table.setContainerDataSource(new BeanItemContainer<ItemBean>(ItemBean.class));
		table.setColumnHeader("name", "Név");
		table.setColumnHeader("code", "Kód");
		table.setColumnHeader("amount", "Mennyiség");
		table.setColumnAlignment("amount", Align.RIGHT);
		table.setVisibleColumns("code", "name", "amount");
		BeanItemContainer<ItemBean> container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(getItemList());
		return table;
	}
	
	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSizeUndefined();
		footer.setWidth("100%");
		HorizontalLayout priceLayout = new HorizontalLayout();

		int priceSum = 0;
		//TODO:
		List<Item> itemList = Item.findByBill(bill.getId());
		for (Item item : itemList) {
			priceSum += item.getArticle().getPrice();
		}
		
		Label price = new Label("Ár: " + priceSum + " " + bill.getCurrency());
		price.setStyleName(ValoTheme.LABEL_BOLD);
		priceLayout.addComponent(price);

		footer.addComponent(priceLayout);
		footer.setComponentAlignment(priceLayout, Alignment.MIDDLE_LEFT);
		return footer;
	}

	private List<ItemBean> getItemList() {
		List<ItemBean> beanList = new ArrayList<>();
		//TODO:
		List<Item> itemList = Item.findByBill(bill.getId());
		for (Item item : itemList) {
			beanList.add(new ItemBean(item.getArticle().getCode(), item.getArticle().getName(), item.getAmount() + " "
					+ item.getArticle().getUnit()));
		}
		return beanList;
	}
}
