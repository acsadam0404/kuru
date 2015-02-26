package com.vaadin.addon.touchkit.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class Android2NativeSelectReplacement_Bundle_default_InlineClientBundleGenerator implements com.vaadin.addon.touchkit.gwt.client.Android2NativeSelectReplacement.Bundle {
  private static Android2NativeSelectReplacement_Bundle_default_InlineClientBundleGenerator _instance0 = new Android2NativeSelectReplacement_Bundle_default_InlineClientBundleGenerator();
  private void cssInitializer() {
    css = new com.vaadin.addon.touchkit.gwt.client.Android2NativeSelectReplacement.Styles() {
      private boolean injected;
      public boolean ensureInjected() {
        if (!injected) {
          injected = true;
          com.google.gwt.dom.client.StyleInjector.inject(getText());
          return true;
        }
        return false;
      }
      public String getName() {
        return "css";
      }
      public String getText() {
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GFQOVTFGI{background-color:" + ("rgba(" + "0"+ ","+ " " +"0"+ ","+ " " +"0"+ ","+ " " +"0.4" + ")")  + ";}.GFQOVTFEI{border:" + ("1px"+ " " +"solid"+ " " +"#666")  + ";border-radius:" + ("2px")  + ";background:" + ("#ddd")  + ";position:" + ("relative")  + ";max-width:" + ("100%")  + ";}.GFQOVTFEI>div{height:" + ("30px")  + ";min-width:" + ("100px")  + ";line-height:" + ("30px")  + ";padding:" + ("0"+ " " +"10px"+ " " +"0"+ " " +"26px")  + ";white-space:") + (("nowrap")  + ";text-overflow:" + ("ellipsis")  + ";overflow:" + ("hidden")  + ";}.GFQOVTFEI>div:after{content:" + ("\"▼\"")  + ";position:" + ("absolute")  + ";left:" + ("0")  + ";width:" + ("20px")  + ";}.GFQOVTFFI{max-height:" + ("80%")  + ";max-width:" + ("80%")  + ";overflow:" + ("auto")  + " !important;}.GFQOVTFFI table{background:" + ("white") ) + (";border-collapse:" + ("collapse")  + ";}.GFQOVTFFI td{border:" + ("1px"+ " " +"solid"+ " " +"#666")  + ";}.GFQOVTFFI td>div{padding:" + ("5px"+ " " +"20px")  + ";}.GFQOVTFHI:before{content:" + ("\"✓\"")  + ";position:" + ("absolute")  + ";margin-right:" + ("-15px")  + ";}")) : ((".GFQOVTFGI{background-color:" + ("rgba(" + "0"+ ","+ " " +"0"+ ","+ " " +"0"+ ","+ " " +"0.4" + ")")  + ";}.GFQOVTFEI{border:" + ("1px"+ " " +"solid"+ " " +"#666")  + ";border-radius:" + ("2px")  + ";background:" + ("#ddd")  + ";position:" + ("relative")  + ";max-width:" + ("100%")  + ";}.GFQOVTFEI>div{height:" + ("30px")  + ";min-width:" + ("100px")  + ";line-height:" + ("30px")  + ";padding:" + ("0"+ " " +"26px"+ " " +"0"+ " " +"10px")  + ";white-space:") + (("nowrap")  + ";text-overflow:" + ("ellipsis")  + ";overflow:" + ("hidden")  + ";}.GFQOVTFEI>div:after{content:" + ("\"▼\"")  + ";position:" + ("absolute")  + ";right:" + ("0")  + ";width:" + ("20px")  + ";}.GFQOVTFFI{max-height:" + ("80%")  + ";max-width:" + ("80%")  + ";overflow:" + ("auto")  + " !important;}.GFQOVTFFI table{background:" + ("white") ) + (";border-collapse:" + ("collapse")  + ";}.GFQOVTFFI td{border:" + ("1px"+ " " +"solid"+ " " +"#666")  + ";}.GFQOVTFFI td>div{padding:" + ("5px"+ " " +"20px")  + ";}.GFQOVTFHI:before{content:" + ("\"✓\"")  + ";position:" + ("absolute")  + ";margin-left:" + ("-15px")  + ";}"));
      }
      public java.lang.String android23Select() {
        return "GFQOVTFEI";
      }
      public java.lang.String android23SelectOverlay() {
        return "GFQOVTFFI";
      }
      public java.lang.String android23SelectOverlayGlass() {
        return "GFQOVTFGI";
      }
      public java.lang.String android23SelectSelectedItem() {
        return "GFQOVTFHI";
      }
    }
    ;
  }
  private static class cssInitializer {
    static {
      _instance0.cssInitializer();
    }
    static com.vaadin.addon.touchkit.gwt.client.Android2NativeSelectReplacement.Styles get() {
      return css;
    }
  }
  public com.vaadin.addon.touchkit.gwt.client.Android2NativeSelectReplacement.Styles css() {
    return cssInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.vaadin.addon.touchkit.gwt.client.Android2NativeSelectReplacement.Styles css;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      css(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("css", css());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'css': return this.@com.vaadin.addon.touchkit.gwt.client.Android2NativeSelectReplacement.Bundle::css()();
    }
    return null;
  }-*/;
}
