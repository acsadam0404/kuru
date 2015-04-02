package hu.kuru;

import com.vaadin.navigator.Navigator
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.navigator.ViewDisplay
import com.vaadin.ui.ComponentContainer
import com.vaadin.ui.Notification
import com.vaadin.ui.SingleComponentContainer
import com.vaadin.ui.UI

class TouchkitNavigator extends Navigator {

    private String previousPage = null;

    public TouchkitNavigator(UI ui, ComponentContainer container) {
        super(ui, container);
    }

    public TouchkitNavigator(UI ui, SingleComponentContainer container) {
        super(ui, container);
    }


    public TouchkitNavigator(UI ui, ViewDisplay display) {
        super(ui, display);
    }

}
