package hu.kuru.touchkit

import com.vaadin.annotations.Theme
import com.vaadin.annotations.Widgetset
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Label
import com.vaadin.ui.TextField
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import org.vaadin.spring.annotation.VaadinUI
import org.vaadin.spring.navigator.SpringViewProvider
import org.vaadin.spring.touchkit.annotation.TouchKitUI

@VaadinUI
@Theme("valo")
@Widgetset("hu.kuru.Widgetset")
@TouchKitUI
class KuruUI extends UI{
	private final SpringViewProvider viewProvider
	
	final UIEventBus eventbus = new UIEventBus()

	@Override
	protected void init(VaadinRequest request) {
		UIEventBus.register(this);
		def root = new VerticalLayout();
		root.with {
			setMargin(true)
			setSpacing(true)
			setSizeFull();
		}
		def c = new VerticalLayout()
		c.addComponent(new ArticleGrid())
		root.addComponent(c)
		setContent(root)
	}


	static KuruUI getCurrent() {
		super.getCurrent()
	}
}
