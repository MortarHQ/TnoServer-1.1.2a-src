package com.blamejared.crafttweaker.api.action.item;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import java.util.List;
import net.minecraft.world.item.crafting.RecipeType;
import org.apache.logging.log4j.Logger;

public class ActionSetBurnTime extends CraftTweakerAction implements IUndoableAction {
   private final IIngredient ingredient;
   private final int newBurnTime;
   private final RecipeType<?> recipeType;

   public ActionSetBurnTime(IIngredient ingredient, int newBurnTime) {
      this.ingredient = ingredient;
      this.newBurnTime = newBurnTime;
      this.recipeType = RecipeType.SMELTING;
   }

   public ActionSetBurnTime(IIngredient ingredient, int newBurnTime, RecipeType<?> type) {
      this.ingredient = ingredient;
      this.newBurnTime = newBurnTime;
      this.recipeType = type;
   }

   @Override
   public void apply() {
      Services.EVENT.setBurnTime(this.ingredient, this.newBurnTime, this.recipeType);
   }

   @Override
   public String describe() {
      return String.format("Setting burn time of: %s to %s for type %s", this.ingredient.getCommandString(), this.newBurnTime, this.recipeType);
   }

   @Override
   public void undo() {
      Services.EVENT.getBurnTimes().getOrDefault(this.recipeType, List.of()).removeIf(pair -> this.ingredient.contains((IIngredient)pair.getFirst()));
   }

   @Override
   public String describeUndo() {
      return "Undoing setting of burn time of stack %s for type %s".formatted(this.ingredient.getCommandString(), this.recipeType);
   }

   @Override
   public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
      return true;
   }
}
