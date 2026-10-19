package team.creative.creativecore.common.gui.control.simple;

import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.Icon;
import team.creative.creativecore.common.util.type.Color;

public class GuiStateButtonIcon extends GuiButtonIcon {
   public final Icon[] states;
   private int index;

   public GuiStateButtonIcon(String name, Icon... icons) {
      super(name, icons[0], null);
      this.states = icons;
   }

   public GuiStateButtonIcon setState(int index) {
      this.index = index;
      this.icon = this.states[index];
      return this;
   }

   public int getState() {
      return this.index;
   }

   public GuiStateButtonIcon setShadow(Color shadow) {
      this.shadow = shadow;
      return this;
   }

   public GuiStateButtonIcon setColor(Color color) {
      this.color = color;
      return this;
   }

   public GuiStateButtonIcon setControlFormatting(ControlFormatting formatting) {
      super.setControlFormatting(formatting);
      return this;
   }

   public void previousState() {
      int state = this.getState();
      if (--state < 0) {
         state = this.states.length - 1;
      }

      if (state >= this.states.length) {
         state = 0;
      }

      this.setState(state);
      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   public void nextState() {
      int state = this.getState();
      if (++state < 0) {
         state = this.states.length - 1;
      }

      if (state >= this.states.length) {
         state = 0;
      }

      this.setState(state);
      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      this.nextState();
      return super.mouseClicked(x, y, button);
   }
}
