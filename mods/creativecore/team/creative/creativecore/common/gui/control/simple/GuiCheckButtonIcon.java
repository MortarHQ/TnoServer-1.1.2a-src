package team.creative.creativecore.common.gui.control.simple;

import java.util.function.Consumer;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.flow.GuiSizeRule;
import team.creative.creativecore.common.gui.style.Icon;
import team.creative.creativecore.common.util.type.Color;

public class GuiCheckButtonIcon extends GuiButtonIcon {
   protected Icon on;
   protected Icon off;
   public boolean value;

   public GuiCheckButtonIcon(String name, Icon on, Icon off, boolean state) {
      this(name, on, off, state, null);
   }

   public GuiCheckButtonIcon(String name, Icon on, Icon off, boolean state, Consumer<Integer> pressed) {
      super(name, state ? on : off, pressed);
      this.value = state;
      this.on = on;
      this.off = off;
   }

   public GuiCheckButtonIcon setOnIcon(Icon icon) {
      this.on = icon;
      return this;
   }

   public GuiCheckButtonIcon setOffIcon(Icon icon) {
      this.off = icon;
      return this;
   }

   public GuiCheckButtonIcon setColor(Color color) {
      this.color = color;
      return this;
   }

   public GuiCheckButtonIcon setShadow(Color shadow) {
      this.shadow = shadow;
      return this;
   }

   public GuiCheckButtonIcon setSquared(boolean squared) {
      this.squared = squared;
      return this;
   }

   public GuiCheckButtonIcon setDim(GuiSizeRule dim) {
      super.setDim(dim);
      return this;
   }

   public GuiCheckButtonIcon setDim(int width, int height) {
      super.setDim(width, height);
      return this;
   }

   public boolean getState() {
      return this.value;
   }

   public void setState(boolean value) {
      if (this.value != value) {
         this.value = value;
         this.icon = value ? this.on : this.off;
         this.raiseEvent(new GuiControlChangedEvent<>(this));
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      this.setState(!this.value);
      return super.mouseClicked(x, y, button);
   }
}
