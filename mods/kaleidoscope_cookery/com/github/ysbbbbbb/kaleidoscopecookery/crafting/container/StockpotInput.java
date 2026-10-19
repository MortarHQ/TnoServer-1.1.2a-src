package com.github.ysbbbbbb.kaleidoscopecookery.crafting.container;

import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class StockpotInput extends SimpleInput {
   private final ResourceLocation soupBase;

   public StockpotInput(List<ItemStack> items, ResourceLocation soupBase) {
      super(items);
      this.soupBase = soupBase;
   }

   public ResourceLocation getSoupBase() {
      return this.soupBase;
   }
}
