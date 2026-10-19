package cn.leolezury.eternalstarlight.common.item.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record DryingRecipeInput(ItemStack input, boolean fireBelow) implements RecipeInput {
   public ItemStack getItem(int i) {
      return this.input();
   }

   public int size() {
      return 1;
   }
}
