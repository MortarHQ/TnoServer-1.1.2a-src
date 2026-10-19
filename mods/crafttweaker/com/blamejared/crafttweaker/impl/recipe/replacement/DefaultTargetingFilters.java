package com.blamejared.crafttweaker.impl.recipe.replacement;

import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

public final class DefaultTargetingFilters {
   private static final Supplier<Set<RecipeType<?>>> VANILLA_RECIPE_TYPES = Suppliers.memoize(
      () -> ImmutableSet.of(
         RecipeType.CRAFTING,
         RecipeType.SMELTING,
         RecipeType.BLASTING,
         RecipeType.SMOKING,
         RecipeType.CAMPFIRE_COOKING,
         RecipeType.STONECUTTING,
         new RecipeType[]{RecipeType.SMITHING}
      )
   );

   private DefaultTargetingFilters() {
   }

   public static Stream<RecipeHolder<?>> vanillaSpecial(Stream<RecipeHolder<?>> allRecipes) {
      return allRecipes.filter(it -> !VANILLA_RECIPE_TYPES.get().contains(it.value().getType()) || !it.value().isSpecial());
   }

   public static Stream<RecipeHolder<?>> scripts(Stream<RecipeHolder<?>> allRecipes) {
      return allRecipes.filter(it -> it.value().getType() != ScriptRecipeType.INSTANCE);
   }
}
