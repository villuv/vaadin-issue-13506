package com.profitsoftware.vaadintest;

import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@Push(transport= Transport.STREAMING,value= PushMode.AUTOMATIC)
public class AppUI extends UI {

  private static final long serialVersionUID = 1L;

  public AppUI() {
    super();
  }

  @Override
  protected void init(VaadinRequest vaadinRequest) {
      Label label = new Label("Hello");
    setContent(label);
  }

}
