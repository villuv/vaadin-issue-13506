package com.profitsoftware.vaadintest;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("reindeer")
@Push(transport= Transport.STREAMING,value= PushMode.AUTOMATIC)
public class AppUI extends UI {

  private static final long serialVersionUID = 1L;


  public AppUI() {
    super();
  }

  @Override
  protected void init(VaadinRequest vaadinRequest) {
    Table table = new Table() {
      private static final long serialVersionUID = 1L;
      {
        this.alwaysRecalculateColumnWidths = true;
      }
    };
    table.setSizeFull();
    table.setPageLength(0);

    table.setTableFieldFactory(new TableFieldFactory() {
      @Override
      public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        if ("myType".equals(propertyId)) {
          return new MyCustomField((MyCustomType) container.getItem(itemId).getItemProperty(propertyId).getValue());
        } else {
          return DefaultFieldFactory.get().createField(container, itemId, propertyId, uiContext);
        }
      }
    });

    table.addContainerProperty("test1", String.class, "");
    table.addContainerProperty("myType", MyCustomType.class, new MyCustomType("Narrow", "A"));
    table.addContainerProperty("test2", String.class, "");

    for (int i = 0; i < 10; i++) {
      table.addItem(new Object[]{
          "ZZZZZZZ" + i,
          new MyCustomType("Narrow", Integer.toString(i)),
          "AAAAAAA" + i},
        Integer.valueOf(i));
    }

    table.setEditable(true);
  
    VerticalLayout layout = new VerticalLayout();
    layout.setSizeFull();
    layout.addComponent(table);
    setContent(layout);
  }

}
