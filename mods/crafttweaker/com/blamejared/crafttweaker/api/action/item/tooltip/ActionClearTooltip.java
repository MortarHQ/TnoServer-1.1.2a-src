package com.blamejared.crafttweaker.api.action.item.tooltip;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;

public class ActionClearTooltip extends ActionTooltipBase {
   private final ITooltipFunction function;

   public ActionClearTooltip(IIngredient stack, boolean leaveName) {
      super(stack);
      this.function = (stack1, tooltip, context, flag) -> {
         if (leaveName) {
            tooltip.subList(1, tooltip.size()).clear();
         } else {
            tooltip.clear();
         }
      };
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
      return "Clearing the tooltip for: " + this.stack.getCommandString();
   }

   @Override
   public String describeUndo() {
      return "Undoing clearing the tooltip for: " + this.stack.getCommandString();
   }
}
