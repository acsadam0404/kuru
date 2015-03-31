package hu.kuru.ui.component;

import hu.kuru.bean.ItemBean;
import hu.kuru.bill.Bill;
import hu.kuru.item.Item;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
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
		Component header = buildHeader();
		Component table = buildTable();

		box.addComponent(header);
		box.addComponent(table);
		box.setComponentAlignment(header, Alignment.TOP_CENTER);
		box.setComponentAlignment(table, Alignment.TOP_CENTER);

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
		HorizontalLayout priceLayout = new HorizontalLayout();
		priceLayout.setSizeUndefined();
		Label priceLabel = new Label("Ár: " + getPrice() + " " + bill.getCurrency());
		priceLayout.addComponent(priceLabel);
		layout.addComponent(openDate);
		layout.addComponent(priceLayout);
		layout.setComponentAlignment(priceLayout, Alignment.MIDDLE_RIGHT);
		return layout;
	}

	private int getPrice() {
		int priceSum = 0;
		// TODO:
		List<Item> itemList = Item.findByBill(bill.getId());
		for (Item item : itemList) {
			priceSum += item.getArticle().getPrice();
		}
		return priceSum;
	}

	private Component buildTable() {
		Table table = new Table();
		table.setSizeFull();
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

	private List<ItemBean> getItemList() {
		List<ItemBean> beanList = new ArrayList<>();
		// TODO:
		List<Item> itemList = Item.findByBill(bill.getId());
		for (Item item : itemList) {
			beanList.add(new ItemBean(item.getArticle().getCode(), item.getArticle().getName(), item.getAmount() + " "
					+ item.getArticle().getUnit()));
		}
		return beanList;
	}
}
