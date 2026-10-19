package com.github.ysbbbbbb.kaleidoscopecookery.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class OilItem extends WithTooltipsItem {
   public OilItem() {
      super(new Properties(), "oil");
   }

   public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
      return 1600;
   }
}
