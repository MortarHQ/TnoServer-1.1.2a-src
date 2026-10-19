package com.github.L_Ender.cataclysm.crafting;

import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

public interface WeaponfusionRecipeInterface extends Recipe<WeaponfusionRecipeInput> {
   default RecipeType<?> getType() {
      return (RecipeType<?>)ModRecipeTypes.WEAPON_FUSION.get();
   }

   default boolean canCraftInDimensions(int p_44528_, int p_44529_) {
      return p_44528_ * p_44529_ >= 2;
   }

   default ItemStack getToastSymbol() {
      return new ItemStack((ItemLike)ModBlocks.MECHANICAL_FUSION_ANVIL.get());
   }

   boolean isBaseIngredient(ItemStack var1);

   boolean isAdditionIngredient(ItemStack var1);
}
