package hu.kuru.vaadin.bill;

import hu.kuru.ServiceLocator;
import hu.kuru.UIEventBus;
import hu.kuru.bean.ArticleForClosedBill;
import hu.kuru.bean.ClosedBill;
import hu.kuru.bean.ItemBean;
import hu.kuru.bean.ItemListForClosedBill;
import hu.kuru.bill.Bill;
import hu.kuru.enums.Currency;
import hu.kuru.eventbus.BillClosedEvent;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;
import hu.kuru.external.mnb.ExchangeRate;
import hu.kuru.external.mnb.MNBExchangeRateService;
import hu.kuru.external.mnb.MNBServiceException;
import hu.kuru.item.Item;
import hu.kuru.vaadin.component.KWindow;
import hu.si.birt.SIRenderOption;
import hu.si.birt.XmlReportExecutor;
import hu.si.vaadin.converter.AbstractCustomizableStringToNumberConverter;
import hu.si.vaadin.converter.StringToBigDecimalConverter;
import hu.si.vaadin.converter.StringToIntegerConverter;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BillBox extends CustomComponent {

	private static final Logger LOG = LoggerFactory.getLogger(BillBox.class);

	private Panel panel;
	private Bill currentBill;

	private BillBox(Bill bill) {
		currentBill = bill;
		setCompositionRoot(build());
	}

	private Component buildTable(List<Item> itemList) {
		Table table = new Table();
		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer<>(ItemBean.class, getItemList(itemList)));
		table.setColumnHeader("name", "Név");
		table.setColumnHeader("code", "Kód");
		table.setColumnHeader("amount", "Mennyiség");
		table.setColumnAlignment("amount", Align.RIGHT);
		table.setVisibleColumns("code", "name", "amount");
		table.setPageLength(itemList.size());
		return table;
	}

	private Component build() {
		VerticalLayout box = new VerticalLayout();
		box.addStyleName("articlebox");
		box.setSizeFull();
		box.setSpacing(true);
		box.setMargin(true);
		List<Item> itemList = currentBill.getItems();
		box.addComponent(buildHeader());
		box.addComponent(buildTable(itemList));
		box.addComponent(buildFooter(getSumInHUF(itemList)));

		panel = new Panel(currentBill.getCustomer().toString());
//		panel.setSizeFull();
		panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
		panel.addStyleName("billbox");
		panel.setContent(box);
		return panel;
	}

	public void setCaption(String caption) {
		panel.setCaption(currentBill.getCustomer().getCode() + " - " + caption);
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
		openDate.setValue(currentBill.getOpenDate());
		closeDate.setValue(currentBill.getCloseDate());
		layout.addComponent(openDate);
		layout.addComponent(closeDate);
		return layout;
	}

	private int getSumInHUF(List<Item> itemList) {
		int sum = 0;
		for (Item item : itemList) {
			sum += item.getAmount() * item.getArticle().getPrice();
		}
		return sum;
	}

	private Component buildFooter(int sum) {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSizeUndefined();
		footer.setWidth("100%");
		HorizontalLayout buttonLayout = new HorizontalLayout();
		HorizontalLayout priceLayout = new HorizontalLayout();

		buttonLayout.setSpacing(true);
		if (!currentBill.isClosed()) {
			buttonLayout.addComponent(new AddItemButton());
			buttonLayout.addComponent(new CloseButton());
		}

		Label price = new Label("Ár: " + getChangedSum(sum));
		price.setStyleName(ValoTheme.LABEL_BOLD);
		priceLayout.addComponent(price);

		footer.addComponent(buttonLayout);
		footer.addComponent(priceLayout);
		footer.setComponentAlignment(buttonLayout, Alignment.MIDDLE_LEFT);
		footer.setComponentAlignment(priceLayout, Alignment.MIDDLE_RIGHT);
		return footer;
	}

	private String getChangedSum(int sum) {

		BigDecimal result = BigDecimal.ZERO;
		String currency = currentBill.getCurrency();
		try {
			if (!Currency.HUF.name().equals(currency)) {
				List<ExchangeRate> list = ServiceLocator.getBean(
						MNBExchangeRateService.class).getExchangeRates();
				BigDecimal summa = new BigDecimal(sum);
				if (Currency.EUR.name().equals(currency)) {
					result = summa.divide(new BigDecimal(getRate(list, Currency.EUR.name())), 2, RoundingMode.HALF_UP);
				} else if (Currency.GBP.name().equals(currency)) {
					result = summa.divide(new BigDecimal(getRate(list, Currency.GBP.name())), 2, RoundingMode.HALF_UP);
				} else if (Currency.USD.name().equals(currency)) {
					result = summa.divide(new BigDecimal(getRate(list, Currency.USD.name())), 2, RoundingMode.HALF_UP);
				}
			} else {
				return new StringToIntegerConverter(AbstractCustomizableStringToNumberConverter.FORMAT_MONETARY).convertToPresentation(sum)+ " Ft";
			}
		} catch (MNBServiceException e) {
			Notification.show("Sikertelen MNB árfolyam lekérdezés.", Type.ERROR_MESSAGE);
			return "Sikertelen";
		}
		return new StringToBigDecimalConverter("#.00").convertToPresentation(result)+ " " + currency;
	}

	private double getRate(List<ExchangeRate> list, String name)
			throws MNBServiceException {
		for (ExchangeRate exchangeRate : list) {
			if (exchangeRate.getCurr().equals(name)) {
				return Double.valueOf(exchangeRate.getValue().replace(",", "."));
			}
		}
		throw new MNBServiceException();
	}

	private List<ItemBean> getItemList(List<Item> itemList) {
		List<ItemBean> beanList = new ArrayList<>();
		for (Item item : itemList) {
			beanList.add(new ItemBean(item.getArticle().getCode(), item
					.getArticle().getName(), item.getAmount() + " "
					+ item.getArticle().getUnit()));
		}
		return beanList;
	}

	public static BillBox buildBillBox(Bill bill) {
		BillBox comp = new BillBox(bill);
		comp.addAttachListener(new EventBusAttachListener(comp));
		comp.addDetachListener(new EventBusDetachListener(comp));
		return comp;
	}

	private class CloseButton extends Button {
		private CloseButton() {
			super("Lezárás");
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					currentBill.setCloseDate(new Date());
					currentBill.save();
					UIEventBus.post(new BillClosedEvent(currentBill.getCustomer(), BillBox.this));
					createBillPdf(currentBill);
				}
			});
		}
	}

	/**
	 * nyomtatható számla előállítását végző függvény
	 */
	private void createBillPdf(Bill currentBill) {
		ClosedBill closedBill = new ClosedBill();
		ItemListForClosedBill itemListPOJO = new ItemListForClosedBill();
		itemListPOJO.setArticles(new ArrayList<ArticleForClosedBill>());
		List<Item> itemList = currentBill.getItems();
		for (Item item : itemList) {
			ArticleForClosedBill article = new ArticleForClosedBill();
			article.setName(item.getArticle().getName());
			article.setAmount(item.getAmount());
			article.setPrice(item.getArticle().getPrice());
			itemListPOJO.getArticles().add(article);
		}
		closedBill.setItemListForClosedBill(itemListPOJO);
		try {
			StringWriter sw = new StringWriter();
			JAXBContext jaxbContext = JAXBContext.newInstance(ClosedBill.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			jaxbMarshaller.marshal(closedBill, sw);
			XmlReportExecutor reportExecutor = ServiceLocator.getBean(XmlReportExecutor.class);
			reportExecutor.execute(getClass().getResourceAsStream("/invoice.rptdesign"), new ByteArrayInputStream(sw.toString().getBytes("UTF-8")), SIRenderOption.PDF, getInvoiceName(currentBill) + ".pdf");
		} catch (Exception e) {
			LOG.error("Hiba az XML generálás során!", e);
		}
	}

	private String getInvoiceName(Bill currentBill) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return "SZLA_" + currentBill.getCustomer().getCode() + "_" + formatter.format(currentBill.getCloseDate());
	}

	private class AddItemButton extends Button {
		public AddItemButton() {
			super("Új tétel");
			addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					final KWindow window = new KWindow("Új tétel");
					ItemAdditionalComp comp = ItemAdditionalComp.fromBill(currentBill);
					comp.setWindow(window);
					window.setContent(comp);
					UI.getCurrent().addWindow(window);
				}
			});
		}
	}

	Bill getCurrentBill() {
		return currentBill;
	}
}
