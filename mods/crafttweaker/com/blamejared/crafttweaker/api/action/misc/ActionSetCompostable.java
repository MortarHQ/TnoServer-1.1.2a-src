package com.blamejared.crafttweaker.api.action.misc;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import java.util.Optional;
import net.minecraft.world.level.block.ComposterBlock;
import org.apache.logging.log4j.Logger;

public class ActionSetCompostable extends CraftTweakerAction implements IUndoableAction {
   private final IItemStack stack;
   private final float newValue;
   private final float oldValue;

   public ActionSetCompostable(IItemStack stack, float newValue) {
      this.stack = stack;
      this.newValue = newValue;
      this.oldValue = ComposterBlock.COMPOSTABLES.getOrDefault(stack.getInternal().getItem(), -1.0F);
   }

   @Override
   public void apply() {
      Services.PLATFORM.setCompostable(this.stack, Optional.of(this.newValue).filter(aFloat -> aFloat > 0.0F), false);
   }

   @Override
   public String describe() {
      return "Setting Composter value of: " + this.stack.getCommandString() + ", to: " + this.newValue + ", from: " + this.oldValue;
   }

   @Override
   public void undo() {
      Services.PLATFORM.setCompostable(this.stack, Optional.of(this.oldValue).filter(aFloat -> aFloat > 0.0F), true);
   }

   @Override
   public String describeUndo() {
      return "Undoing setting Composter value of: " + this.stack.getCommandString() + ", to: " + this.newValue + ", reverting to: " + this.oldValue;
   }

   @Override
   public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
      return true;
   }
}
