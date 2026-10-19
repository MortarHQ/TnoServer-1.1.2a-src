package com.blamejared.crafttweaker.api.action.item.tooltip;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;

public abstract class ActionTooltipBase extends CraftTweakerAction implements IUndoableAction {
   protected final IIngredient stack;
   private LinkedList<ITooltipFunction> functions;

   public ActionTooltipBase(IIngredient stack) {
      this.stack = stack;
      this.functions = null;
   }

   public LinkedList<ITooltipFunction> getTooltip() {
      if (this.functions == null) {
         this.functions = Services.CLIENT.getTooltips().computeIfAbsent(this.stack, iItemStack -> new LinkedList<>());
      }

      return this.functions;
   }

   @Override
   public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
      return true;
   }
}
