package hu.kuru.vaadin.component;

import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class KWindow extends Window {
	{
		setModal(true);
		setResizable(false);
		setDraggable(false);
	}
	
	public KWindow(String caption, Component comp) {
		super(caption, comp);
	}

	public KWindow(String caption) {
		super(caption);
	}

}
