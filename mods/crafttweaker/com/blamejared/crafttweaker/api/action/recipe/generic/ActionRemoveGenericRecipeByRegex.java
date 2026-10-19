package com.blamejared.crafttweaker.api.action.recipe.generic;

import java.util.regex.Pattern;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ActionRemoveGenericRecipeByRegex extends ActionRemoveGenericRecipeBase {
   private final Pattern pattern;

   public ActionRemoveGenericRecipeByRegex(String regex) {
      this.pattern = Pattern.compile(regex);
   }

   @Override
   public String describe() {
      return String.format("Removing all recipes that match the regex '%s'", this.pattern.pattern());
   }

   @Override
   protected boolean shouldRemove(RecipeHolder<?> holder) {
      String id = holder.id().toString();
      return this.pattern.matcher(id).matches();
   }
}
