package hu.kuru;


import hu.kuru.vaadin.security.Authentication

import com.vaadin.navigator.Navigator
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.navigator.ViewDisplay
import com.vaadin.ui.ComponentContainer
import com.vaadin.ui.Notification
import com.vaadin.ui.SingleComponentContainer
import com.vaadin.ui.UI

class SINavigator extends Navigator {

    private String previousPage = null;

    public SINavigator(UI ui, ComponentContainer container) {
        super(ui, container);
    }

    public SINavigator(UI ui, SingleComponentContainer container) {
        super(ui, container);
    }


    public SINavigator(UI ui, ViewDisplay display) {
        super(ui, display);
    }

    public void navigateTo(String navigationState) {
        if (navigationState.contains("Admin") && !Authentication.user.hasRole("ADMIN")) {
			Notification.show("Hozzáférés megtagadva")
        } else {
            super.navigateTo(navigationState)
        }

    }

}