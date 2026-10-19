package com.blamejared.crafttweaker.api.action.item.tooltip;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import net.minecraft.network.chat.Component;

public class ActionAddTooltip extends ActionTooltipBase {
   private final Component content;
   private final ITooltipFunction function;

   public ActionAddTooltip(IIngredient stack, Component content) {
      super(stack);
      this.content = content;
      this.function = (stack1, tooltip, context, flag) -> tooltip.add(content);
   }

   @Override
   public void apply() {
      this.getTooltip().add(this.function);
   }

   @Override
   public void undo() {
      this.getTooltip().remove(this.function);
   }

   @Override
   public String describe() {
      return "Adding \"" + this.content.getString() + "\" to the tooltip for: " + this.stack.getCommandString();
   }

   @Override
   public String describeUndo() {
      return "Undoing addition of \"" + this.content.getString() + "\" to the tooltip for: " + this.stack.getCommandString();
   }
}
