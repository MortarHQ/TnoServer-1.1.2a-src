package com.blamejared.crafttweaker.api.action.recipe.generic;

import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ActionRemoveGenericRecipeByModId extends ActionRemoveGenericRecipeBase {
   private final String modId;
   private final Predicate<String> exclude;

   public ActionRemoveGenericRecipeByModId(String modId, Predicate<String> exclude) {
      this.modId = modId;
      this.exclude = exclude;
   }

   @Override
   public String describe() {
      String message = String.format("Removing all recipes from modId \"%s\"", this.modId);
      return this.exclude == null ? message : message + ", while retaining recipes matching a filter";
   }

   @Override
   protected boolean shouldRemove(RecipeHolder<?> holder) {
      ResourceLocation id = holder.id();
      return !id.getNamespace().equals(this.modId) ? false : this.exclude == null || !this.exclude.test(id.getPath());
   }
}
