package team.creative.creativecore.common.gui.control.simple;

import org.apache.commons.lang3.ArrayUtils;

public class GuiArraySlider extends GuiSteppedSlider {
   public String[] values;

   public GuiArraySlider(String name, String value, String... values) {
      super(name, ArrayUtils.indexOf(values, value), 0, values.length - 1);
      this.values = values;
   }

   public GuiArraySlider(String name) {
      this(name, "", "");
   }

   @Override
   public String getTextByValue() {
      return this.getIntValue() > this.values.length ? "" : this.values[this.getIntValue()];
   }

   public void select(String value) {
      this.setValue(ArrayUtils.indexOf(this.values, value));
   }

   public void setValues(String[] values) {
      this.setMinValue(0.0);
      this.setMaxValue(values.length - 1);
      this.values = values;
      this.setValue(0.0);
   }

   public String get() {
      return this.values[this.getIntValue()];
   }
}
