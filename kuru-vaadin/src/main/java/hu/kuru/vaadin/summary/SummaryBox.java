package hu.kuru.vaadin.summary;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SummaryBox extends Panel {

	private SummaryBox(String title, String value) {
		setSizeUndefined();
		setStyleName(ValoTheme.PANEL_WELL);
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		Label titleLabel = new Label(title);
		titleLabel.addStyleName("box");
		titleLabel.addStyleName("box-title");
		Label valueLabel = new Label(value);
		valueLabel.addStyleName("box");
		valueLabel.addStyleName("box-label");
		content.addComponent(titleLabel);
		content.addComponent(valueLabel);
		content.setComponentAlignment(titleLabel, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(valueLabel, Alignment.MIDDLE_CENTER);
		setContent(content);
	}

	public static SummaryBox fromTitleAndValue(String title, String value) {
		return new SummaryBox(title, value);
	}

}
