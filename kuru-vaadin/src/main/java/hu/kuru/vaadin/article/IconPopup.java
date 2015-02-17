package hu.kuru.vaadin.article;

import hu.kuru.Icon;
import hu.kuru.UIEventBus;
import hu.kuru.eventbus.IconChoosedEvent;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Window;

public class IconPopup extends Window {

	public IconPopup() {
		super();
		setModal(true);
		setResizable(false);
		setWidth("650px");
		setHeight("450px");
		setClosable(true);
		setContent(buildLayout());
	}

	private GridLayout buildLayout() {
		GridLayout grid = new GridLayout(4, 3);
		grid.setSizeFull();
		grid.setMargin(true);
		grid.setSpacing(true);
		for (Icon icon : Icon.values()) {
			grid.addComponent(new IconImage(this, icon.getResource()));
		}
		return grid;
	}

	private static class IconImage extends Image {
		public IconImage(final Window popup, final String resource) {
			super(null, new ThemeResource(resource));
			addClickListener(new ClickListener() {
				@Override
				public void click(ClickEvent event) {
					UIEventBus.post(new IconChoosedEvent(resource));
					popup.close();
				}
			});
		}
	}

}
