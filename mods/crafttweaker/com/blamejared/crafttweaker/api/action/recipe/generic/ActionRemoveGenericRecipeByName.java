package com.blamejared.crafttweaker.api.action.recipe.generic;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ActionRemoveGenericRecipeByName extends ActionRemoveGenericRecipeBase {
   private final Set<ResourceLocation> names;

   public ActionRemoveGenericRecipeByName(String name) {
      this.names = Set.of(ResourceLocation.parse(name));
   }

   public ActionRemoveGenericRecipeByName(ResourceLocation[] names) {
      this.names = Set.of(names);
   }

   @Override
   public String describe() {
      return "Removing all recipe(s) with name(s): '"
         + this.names.stream().<CharSequence>map(ResourceLocation::toString).collect(Collectors.joining(", ", "[", "]"))
         + "'";
   }

   @Override
   protected boolean shouldRemove(RecipeHolder<?> holder) {
      return this.names.contains(holder.id());
   }
}
