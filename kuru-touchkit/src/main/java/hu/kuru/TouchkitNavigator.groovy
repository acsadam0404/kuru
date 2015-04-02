package hu.kuru;

import java.util.Map;

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
	
	
	void navigateTo(String navigationState, Map<String, Object> params) {
		navigateTo(navigationState + "/" + mapToParams(params))
	}
	
	static String mapToParams(Map<String, Object> params) {
		if (params && !params.empty) {
			String param = "?"
			params.each { k, v ->
				param += "$k=${v.toString()}&"
			}
			param -= '&'
			return param
		}
		return null
	}

	static Map<String, String> paramsToMap(String params) {
		Map map = [:]
		params -= '?'
		params.split("\\&").each { pair ->
			def (k, v) = pair.split("\\=")
			map.put(k, v)
		}
		return map
	}
}
