package team.creative.creativecore.common.gui.control.simple;

import net.minecraft.client.gui.screens.Screen;
import team.creative.creativecore.common.gui.parser.IntValueParser;

public class GuiSteppedSlider extends GuiSlider {
   private final IntValueParser steppedParser;

   public GuiSteppedSlider(String name, int value, int min, int max) {
      this(name, value, min, max, IntValueParser.NONE);
   }

   public GuiSteppedSlider(String name, int value, int min, int max, IntValueParser parser) {
      super(name, value, min, max);
      this.steppedParser = parser;
   }

   @Override
   public String getTextByValue() {
      return this.steppedParser.parse(this.getIntValue(), this.getIntMaxValue());
   }

   @Override
   public String getTextfieldValue() {
      return this.getTextByValue();
   }

   public void stepUp() {
      this.setValue(this.getValue() + 1.0);
   }

   public void stepDown() {
      this.setValue(this.getValue() - 1.0);
   }

   @Override
   public boolean mouseScrolled(double x, double y, double scrolled) {
      this.setValue(this.getIntValue() + (Screen.hasShiftDown() ? 10 : 1) * (scrolled > 0.0 ? 1 : -1));
      return true;
   }

   @Override
   public void mouseMoved(double x, double y) {
      super.mouseMoved(x, y);
      this.value = (int)this.value;
   }

   @Override
   public void setValue(double value) {
      super.setValue((int)value);
   }

   @Override
   public void setMaxValue(double maxValue) {
      super.setMaxValue((int)maxValue);
   }

   @Override
   public void setMinValue(double minValue) {
      super.setMinValue((int)minValue);
   }

   @Override
   public double getValue() {
      return (int)super.getValue();
   }

   @Override
   public double getMinValue() {
      return (int)super.getMinValue();
   }

   @Override
   public double getMaxValue() {
      return (int)super.getMaxValue();
   }

   public int getIntValue() {
      return (int)this.value;
   }

   public int getIntMaxValue() {
      return (int)this.maxValue;
   }

   public int getIntMinValue() {
      return (int)this.minValue;
   }

   @Override
   protected GuiTextfield createTextfield() {
      return super.createTextfield().setNumbersIncludingNegativeOnly();
   }
}
