package com.blamejared.crafttweaker.api.action.item;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.attribute.ItemAttributeModifierBase;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.apache.logging.log4j.Logger;

public class ActionModifyAttribute extends CraftTweakerAction implements IUndoableAction {
   private final IIngredient ingredient;
   private final Consumer<ItemAttributeModifierBase> consumer;

   public ActionModifyAttribute(IIngredient ingredient, Consumer<ItemAttributeModifierBase> consumer) {
      this.ingredient = ingredient;
      this.consumer = consumer;
   }

   @Override
   public void apply() {
      Services.EVENT.getAttributeModifiers().computeIfAbsent(this.ingredient, ingredient1 -> new ArrayList<>()).add(this.consumer);
   }

   @Override
   public String describe() {
      return String.format("Modifying Attributes of: %s", this.ingredient.getCommandString());
   }

   @Override
   public void undo() {
      Services.EVENT
         .getAttributeModifiers()
         .keySet()
         .stream()
         .filter(this.ingredient::contains)
         .forEach(key -> Services.EVENT.getAttributeModifiers().getOrDefault(key, new ArrayList<>()).removeIf(this.consumer::equals));
   }

   @Override
   public String describeUndo() {
      return String.format("Undoing modification of Attributes on: %s", this.ingredient.getCommandString());
   }

   @Override
   public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
      return true;
   }
}
