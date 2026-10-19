package team.creative.creativecore.common.config.gui;

import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.control.simple.GuiTextfield;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiConfigSubControl extends GuiParent {
   public GuiTextfield nameField;
   public GuiLabel nameLabel;
   public boolean defaultHolder;

   public GuiConfigSubControl(String name) {
      super(name, GuiFlow.STACK_X);
      this.setExpandableX();
   }

   public String getName() {
      if (this.nameLabel != null) {
         return this.nameLabel.name;
      } else {
         return this.nameField != null ? this.nameField.getText() : "";
      }
   }

   public void addNameUnmodifieable(String name) {
      this.nameLabel = new GuiLabel("title").setTitle(Component.literal(name));
      this.add(this.nameLabel);
   }

   public void addNameTextfield(String name) {
      this.add(this.nameField = new GuiTextfield("title", name).setDim(50, 8));
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.NESTED;
   }
}
