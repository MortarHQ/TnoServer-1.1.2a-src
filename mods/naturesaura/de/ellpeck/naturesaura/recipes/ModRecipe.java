package de.ellpeck.naturesaura.recipes;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public abstract class ModRecipe implements Recipe<RecipeWrapper> {
   public boolean matches(RecipeWrapper inv, Level levelIn) {
      return true;
   }

   public ItemStack assemble(RecipeWrapper input, Provider registries) {
      return ItemStack.EMPTY;
   }

   public boolean canCraftInDimensions(int width, int height) {
      return false;
   }
}
