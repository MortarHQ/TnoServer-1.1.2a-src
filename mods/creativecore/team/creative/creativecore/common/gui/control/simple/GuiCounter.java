package team.creative.creativecore.common.gui.control.simple;

import net.minecraft.util.Mth;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.event.GuiEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiCounter extends GuiParent {
   public int min;
   public int max;
   public final GuiParent buttons = new GuiParent(GuiFlow.STACK_Y);
   public GuiTextfield textfield;
   public final ControlFormatting buttonsFormatting;

   public GuiCounter(String name, int value) {
      this(name, value, Integer.MIN_VALUE, Integer.MAX_VALUE);
   }

   public GuiCounter(String name, int value, int min, int max) {
      this(name, value, min, max, ControlFormatting.TRANSPARENT);
   }

   public GuiCounter(String name, int value, int min, int max, ControlFormatting buttonsFormatting) {
      super(name);
      this.min = min;
      this.max = max;
      this.flow = GuiFlow.STACK_X;
      this.setSpacing(1);
      this.textfield = new GuiTextfield("value", Mth.clamp(value, min, max) + "").setDim(20, 10).setNumbersIncludingNegativeOnly();
      this.buttons.spacing = 0;
      this.buttonsFormatting = buttonsFormatting;
      this.createButtons();
      this.add(this.textfield.setExpandableX());
      this.add(this.buttons);
   }

   protected void createButtons() {
      this.buttons.add(new GuiButtonHoldSlim("+", x -> {
         this.textfield.setText(this.stepUp(this.textfield.parseInteger()) + "");
         this.raiseEvent(new GuiControlChangedEvent<>(this));
      }).setTranslate("gui.plus").setDim(6, 3));
      this.buttons.add(new GuiButtonHoldSlim("-", x -> {
         this.textfield.setText(this.stepDown(this.textfield.parseInteger()) + "");
         this.raiseEvent(new GuiControlChangedEvent<>(this));
      }).setTranslate("gui.minus").setDim(6, 4));
   }

   public GuiCounter setSpacing(int spacing) {
      this.spacing = spacing;
      return this;
   }

   public GuiCounter add(GuiControl control) {
      return (GuiCounter)super.add(control);
   }

   public GuiButtonHoldSlim getPlusButton() {
      return this.buttons.get("+");
   }

   public GuiButtonHoldSlim getMinusButton() {
      return this.buttons.get("-");
   }

   @Override
   public boolean isExpandableX() {
      return this.expandableX;
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

   public int stepUp(int value) {
      return Math.min(this.max, value + 1);
   }

   public int stepDown(int value) {
      return Math.max(this.min, value - 1);
   }

   public int getValue() {
      return Mth.clamp(this.textfield.parseInteger(), this.min, this.max);
   }

   public void setValue(int value) {
      this.textfield.setText(Mth.clamp(value, this.min, this.max) + "");
   }
}
