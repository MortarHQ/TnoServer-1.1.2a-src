package com.blamejared.crafttweaker.api.action.entity;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.entity.INameTagFunction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker.platform.services.IClientHelper;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import org.apache.logging.log4j.Logger;

public class ActionSetNameTag extends CraftTweakerAction implements IUndoableAction {
   private final Predicate<Entity> predicate;
   private final INameTagFunction function;

   public ActionSetNameTag(Predicate<Entity> predicate, INameTagFunction function) {
      this.predicate = predicate;
      this.function = function;
   }

   @Override
   public void apply() {
      IClientHelper.NAMETAGS.put(this.predicate, this.function);
   }

   @Override
   public void undo() {
      IClientHelper.NAMETAGS.remove(this.predicate);
   }

   @Override
   public String describe() {
      return "Adding a custom name tag for an entity!";
   }

   @Override
   public String describeUndo() {
      return "Undoing addition of a custom name tag for an entity!";
   }

   @Override
   public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
      return Services.DISTRIBUTION.isClient();
   }
}
