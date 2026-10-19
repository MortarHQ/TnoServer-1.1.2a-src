package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class RecipeBlockEntity extends BaseBlockEntity {
   private static final String SHOW_ITEMS = "ShowItems";
   private ItemStackHandler items = new ItemStackHandler(1);

   public RecipeBlockEntity(BlockPos pos, BlockState blockState) {
      super(ModBlocks.RECIPE_BLOCK_BE.get(), pos, blockState);
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.put("ShowItems", this.items.serializeNBT(registries));
   }

   public void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      if (tag.contains("ShowItems")) {
         this.items.deserializeNBT(registries, tag.getCompound("ShowItems"));
      }
   }

   public ItemStackHandler getItems() {
      return this.items;
   }
}
