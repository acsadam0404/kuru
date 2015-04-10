package hu.kuru.ui.component;

import hu.kuru.ServiceLocator;
import hu.kuru.bean.ItemBean;
import hu.kuru.bill.Bill;
import hu.kuru.enums.Currency;
import hu.kuru.external.mnb.ExchangeRate;
import hu.kuru.external.mnb.MNBExchangeRateService;
import hu.kuru.external.mnb.MNBServiceException;
import hu.kuru.item.Item;
import hu.si.touchkit.converter.AbstractCustomizableStringToNumberConverter;
import hu.si.touchkit.converter.StringToDoubleConverter;
import hu.si.touchkit.converter.StringToIntegerConverter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BillBox extends CustomComponent {

	private Bill bill;
	private Panel panel;

	public BillBox(Bill bill) {
		setSizeFull();
		this.bill = bill;
		this.setCompositionRoot(buildComponent());
	}

	private Component buildComponent() {
		VerticalLayout box = new VerticalLayout();
		box.setSizeFull();
		box.setSpacing(true);
		box.addComponent(buildHeader());
		Component table = buildTable();
		table.setSizeFull();
		box.addComponent(table);
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
		Label openDate = new Label("Nyitás dátuma: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bill.getOpenDate()));
		HorizontalLayout priceLayout = new HorizontalLayout();
		priceLayout.setSizeUndefined();
		Label priceLabel = new Label("Ár: " + getPrice());
		priceLayout.addComponent(priceLabel);
		layout.addComponent(openDate);
		layout.addComponent(priceLayout);
		layout.setComponentAlignment(priceLayout, Alignment.MIDDLE_RIGHT);
		return layout;
	}

	/**
	 * Összesített ár lekérdezése
	 * 
	 * @return
	 */
	private String getPrice() {
		int priceSum = 0;
		// TODO:
		List<Item> itemList = Item.findByBill(bill.getId());
		for (Item item : itemList) {
			priceSum += item.getArticle().getPrice();
		}

		return getChangedSum(priceSum);
	}

	private String getChangedSum(int sum) {

		DecimalFormat format = new DecimalFormat("#.##");
		double result = 0;
		String currency = bill.getCurrency();
		try {
			if (!Currency.HUF.name().equals(currency)) {
				List<ExchangeRate> list = ServiceLocator.getBean(MNBExchangeRateService.class).getExchangeRates();
				if (Currency.EUR.name().equals(currency)) {
					result = sum / getRate(list, Currency.EUR.name());
				} else if (Currency.GBP.name().equals(currency)) {
					result = sum / getRate(list, Currency.GBP.name());
				} else if (Currency.USD.name().equals(currency)) {
					result = sum / getRate(list, Currency.USD.name());
				}
				result = Double.valueOf(format.format(result).replace(",", "."));
			} else {
				return new StringToIntegerConverter(AbstractCustomizableStringToNumberConverter.FORMAT_MONETARY).convertToPresentation(sum)
						+ " Ft";
			}
		} catch (MNBServiceException e) {
			Notification.show("Sikertelen MNB árfolyam lekérdezés. Az árak forintban jelennek meg!");
			return "Sikertelen";
		}
		return new StringToDoubleConverter(AbstractCustomizableStringToNumberConverter.FORMAT_MONETARY).convertToPresentation(result) + " "
				+ currency;
	}

	private double getRate(List<ExchangeRate> list, String name) throws MNBServiceException {
		for (ExchangeRate exchangeRate : list) {
			if (exchangeRate.getCurr().equals(name)) {
				return Double.valueOf(exchangeRate.getValue().replace(",", "."));
			}
		}
		throw new MNBServiceException();
	}

	private Component buildTable() {
		Table table = new Table();
		table.setSizeFull();
		table.setPageLength(0);
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
