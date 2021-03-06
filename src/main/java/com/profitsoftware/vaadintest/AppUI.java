package com.profitsoftware.vaadintest;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeListener;
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
	private Table table;

  public AppUI() {
    super();
  }

  private void initializeColumnWidth(Object property) {
    table.setColumnWidth(property, -1);
  }

  public void refreshColumnWidth(Object property) {
    initializeColumnWidth(property);
    triggerTableSizeChange();
  }

  /* This workaround was first introduced in ( http://dev.vaadin.com/ticket/7922 ) and it seems after all it is still needed in Vaadin 7.1.12>=  */
  private void triggerTableSizeChange() {
    if (table.getVisibleColumns().length > 0) {
      Object col = table.getVisibleColumns()[0];
      boolean collapseAllowed = table.isColumnCollapsingAllowed();
      boolean b = table.isColumnCollapsed(col);
      table.setColumnCollapsingAllowed(true);
      table.setColumnCollapsed(col, !b);
      table.setColumnCollapsed(col, b);//restore original setting
      table.setColumnCollapsingAllowed(collapseAllowed);//restore original setting
    }
  }

  public void refreshColumnWidths() {
    if (table.getVisibleColumns().length > 0) {
      for (Object property : table.getVisibleColumns()) {
        initializeColumnWidth(property);
      }
      triggerTableSizeChange();
    }
  }

  @Override
  protected void init(VaadinRequest vaadinRequest) {
    table = new Table() {
      private static final long serialVersionUID = 1L;
      {
        this.alwaysRecalculateColumnWidths = true;
      }
    };
    table.setWidth("555px");// Narrow the Table width to capture the 'need for re-adjusting column width'
    table.setHeight("100%");
    table.setPageLength(0);

    table.setTableFieldFactory(new TableFieldFactory() {
      @Override
      public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        if ("myType".equals(propertyId)) {
          MyCustomField myCustomField = new MyCustomField((MyCustomType) container.getItem(itemId).getItemProperty(propertyId).getValue());
          return myCustomField;
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
