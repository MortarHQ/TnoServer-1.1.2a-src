package team.creative.creativecore.common.gui.control.parent;

import java.util.List;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.flow.GuiFlow;

public class GuiLabeledControl extends GuiParent {
   public final GuiLabel label;

   public GuiLabeledControl(Component text, GuiControl control) {
      this.flow = GuiFlow.STACK_X;
      this.valign = VAlign.CENTER;
      this.label = new GuiLabel("label");
      if (text != null) {
         this.label.setTitle(text);
      }

      this.add(this.label);
      this.add(control);
   }

   public GuiLabeledControl(String translate, GuiControl control) {
      this(GuiControl.translatable(translate), control);
   }

   public GuiLabeledControl(GuiControl control) {
      this((Component)null, control);
   }

   public <C extends GuiControl> C getInnerControl() {
      return (C)this.controls.get(1);
   }

   public GuiLabeledControl setTranslate(String translate) {
      this.label.setTranslate(translate);
      return this;
   }

   public GuiLabeledControl setTranslate(String translate, Object... params) {
      this.label.setTranslate(translate, params);
      return this;
   }

   public GuiLabeledControl setTitle(Component component) {
      this.label.setTitle(component);
      return this;
   }

   public GuiLabeledControl setTitle(List<Component> components) {
      this.label.setTitle(components);
      return this;
   }

   public GuiLabeledControl setTooltip(List<Component> tooltip) {
      this.label.setTooltip(tooltip);
      return this;
   }

   public GuiLabeledControl setTooltip(String text) {
      this.label.setTooltip(text);
      return this;
   }
}
