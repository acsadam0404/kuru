package hu.kuru.vaadin.component;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class KNotification extends Notification {

	private static final int DEFAULT_DELAY = 2000;

	private static final long serialVersionUID = 1L;

	public KNotification(String caption) {
		super(caption);
	}

	public void show() {
		this.show(Page.getCurrent());
	}

	public void showError() {
		this.setDelayMsec(-1);
		this.setStyleName(ValoTheme.NOTIFICATION_CLOSABLE + " " + ValoTheme.NOTIFICATION_FAILURE);
		this.setPosition(Position.MIDDLE_CENTER);
		this.show(Page.getCurrent());
	}

	public void showSuccess() {
		this.setDelayMsec(KNotification.DEFAULT_DELAY);
		this.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
		this.setPosition(Position.MIDDLE_CENTER);
		this.show(Page.getCurrent());
	}

	public KNotification withDelay(int ms) {
		this.setDelayMsec(ms);
		return this;
	}

	public KNotification withDescription(String desc) {
		this.setDescription(desc);
		return this;
	}

	public KNotification withPosition(Position pos) {
		this.setPosition(pos);
		return this;
	}

	public KNotification withStyle(String style) {
		this.setStyleName(style);
		return this;
	}

}
