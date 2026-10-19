package team.creative.creativecore.common.gui.control.simple;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Consumer;
import net.minecraft.util.Mth;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.event.GuiEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiCounterDecimal extends GuiParent {
   public static final DecimalFormat FORMAT = new DecimalFormat("0.##");
   public double min;
   public double max;
   public final GuiParent buttons = new GuiParent(GuiFlow.STACK_Y);
   public GuiTextfield textfield;
   public double stepAmount;
   public final ControlFormatting buttonsFormatting;

   public GuiCounterDecimal(String name, double value) {
      this(name, value, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
   }

   public GuiCounterDecimal(String name, double value, double min, double max) {
      this(name, value, min, max, ControlFormatting.TRANSPARENT);
   }

   public GuiCounterDecimal(String name, double value, double min, double max, ControlFormatting buttonsFormatting) {
      super(name);
      this.min = min;
      this.max = max;
      this.stepAmount = 1.0;
      this.setSpacing(1);
      this.flow = GuiFlow.STACK_X;
      this.textfield = new GuiTextfield("value", Mth.clamp(value, min, max) + "").setDim(30, 10).setFloatOnly();
      this.buttons.spacing = 0;
      this.buttonsFormatting = buttonsFormatting;
      this.createButtons();
      this.add(this.textfield.setExpandableX());
      this.add(this.buttons);
   }

   protected void createButtons() {
      this.buttons.add((new GuiButtonHoldSlim("+", x -> this.stepUp()) {
         @Override
         public ControlFormatting getControlFormatting() {
            return GuiCounterDecimal.this.buttonsFormatting;
         }
      }).setTranslate("gui.plus").setDim(6, 3));
      this.buttons.add((new GuiButtonHoldSlim("-", x -> this.stepDown()) {
         @Override
         public ControlFormatting getControlFormatting() {
            return GuiCounterDecimal.this.buttonsFormatting;
         }
      }).setTranslate("gui.minus").setDim(6, 3));
   }

   public GuiCounterDecimal setSpacing(int spacing) {
      this.spacing = spacing;
      return this;
   }

   public GuiCounterDecimal add(GuiControl control) {
      return (GuiCounterDecimal)super.add(control);
   }

   public GuiButtonHoldSlim getPlusButton() {
      return this.buttons.get("+");
   }

   public GuiButtonHoldSlim getMinusButton() {
      return this.buttons.get("-");
   }

   public GuiCounterDecimal setStep(double amount) {
      this.stepAmount = amount;
      return this;
   }

   public DecimalFormat getFormat() {
      return FORMAT;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }

   public void resetTextfield() {
      this.textfield.setCursorPositionZero();
   }

   @Override
   public void raiseEvent(GuiEvent event) {
      if (event instanceof GuiControlChangedEvent controlEvent && controlEvent.control.is("value")) {
         super.raiseEvent(new GuiControlChangedEvent<>(this));
      } else {
         super.raiseEvent(event);
      }
   }

   @Override
   public boolean isExpandableX() {
      return this.expandableX;
   }

   public void stepUp() {
      this.setValue(this.getValue() + this.stepAmount);
   }

   public void stepDown() {
      this.setValue(this.getValue() - this.stepAmount);
   }

   public double getValue() {
      return Mth.clamp(this.textfield.parseDouble(), this.min, this.max);
   }

   public void setValue(double value) {
      this.textfield.setText(this.getFormat().format(Mth.clamp(value, this.min, this.max)));
      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   static {
      FORMAT.setRoundingMode(RoundingMode.HALF_UP);
   }
}
