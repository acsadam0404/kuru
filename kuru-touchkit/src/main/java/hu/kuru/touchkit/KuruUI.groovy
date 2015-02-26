package hu.kuru.touchkit

import org.springframework.beans.factory.annotation.Autowired
import org.vaadin.spring.annotation.VaadinUI
import org.vaadin.spring.navigator.SpringViewProvider

import com.vaadin.annotations.Theme
import com.vaadin.annotations.Widgetset
import com.vaadin.navigator.Navigator
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Button
import com.vaadin.ui.CssLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme

@VaadinUI
@Theme("valo")
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
		root.addComponent(new Label("hello world"))
		setContent(root)
	}


	static KuruUI getCurrent() {
		super.getCurrent()
	}
}
