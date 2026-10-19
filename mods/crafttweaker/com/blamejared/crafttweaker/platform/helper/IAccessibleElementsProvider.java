package com.blamejared.crafttweaker.platform.helper;

import com.blamejared.crafttweaker.api.registry.TagAddingRegistryLookup;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeManager;

public interface IAccessibleElementsProvider {
   RecipeManager recipeManager();

   AccessRecipeManager accessibleRecipeManager();

   void recipeManager(RecipeManager var1);

   RegistryAccess registryAccess();

   default <T> T registryAccess(Function<RegistryAccess, T> func) {
      return func.apply(this.registryAccess());
   }

   default <T> RegistryLookup<T> lookupOrThrow(ResourceKey<? extends Registry<T>> key) {
      return this.registryAccess().lookupOrThrow(key);
   }

   boolean hasRegistryAccess();

   IAccessibleClientElementsProvider client();

   IAccessibleServerElementsProvider server();

   TagAddingRegistryLookup tagAddingRegistryLookup();
}
