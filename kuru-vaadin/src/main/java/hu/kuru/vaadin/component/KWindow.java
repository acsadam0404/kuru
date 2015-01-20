package hu.kuru.vaadin.component;

import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class KWindow extends Window {

	public KWindow(String caption, Component comp) {
		super(caption, comp);
		setModal(true);
		setResizable(false);
		setDraggable(false);
	}

	public KWindow(String caption) {
		super(caption);
		setModal(true);
		setResizable(false);
		setDraggable(false);
	}

}
