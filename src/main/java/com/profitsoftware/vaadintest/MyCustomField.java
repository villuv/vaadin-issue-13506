package com.profitsoftware.vaadintest;

import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class MyCustomField extends CustomField<MyCustomType> {
  private MyCustomType value;
  private ComboBox typeField;
  private TextField valueField;
  private HorizontalLayout fieldLayout;

  public MyCustomField(MyCustomType value) {
    this.value = value;
  }

  @Override
  protected Component initContent() {
    fieldLayout = new HorizontalLayout();
    fieldLayout.setSizeUndefined();
    typeField = new ComboBox();
    typeField.addItem("Narrow");
    typeField.addItem("Wide");
    typeField.setWidth("90px");
    typeField.setImmediate(true);
    typeField.addValueChangeListener(new ValueChangeListener() {
      @Override
      public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
        String type = (String) typeField.getValue();
        if ("Narrow".equals(type)) {
          valueField.setWidth("20px");
        } else {
          valueField.setWidth("120px");
        }
      }
    });
    valueField = new TextField();
    valueField.setImmediate(true);
    typeField.setValue(value.getType());
    valueField.setValue(value.getValue());
    fieldLayout.addComponents(typeField, valueField);
    return fieldLayout;
  }

  @Override
  public Class<? extends MyCustomType> getType() {
    return MyCustomType.class;
  }

  @Override
  public MyCustomType getValue() {
    return value;
  }
}
