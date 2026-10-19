package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TableBlockEntity extends BaseBlockEntity {
   private static final String COLOR_TAG = "CarpetColor";
   private static final String SHOW_ITEMS = "ShowItems";
   private DyeColor color = DyeColor.WHITE;
   private ItemStackHandler items = new ItemStackHandler(4);

   public TableBlockEntity(BlockPos pos, BlockState blockState) {
      super(ModBlocks.TABLE_BE.get(), pos, blockState);
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putInt("CarpetColor", this.color.getId());
      tag.put("ShowItems", this.items.serializeNBT(registries));
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      if (tag.contains("CarpetColor")) {
         this.color = DyeColor.byId(tag.getInt("CarpetColor"));
      }

      if (tag.contains("ShowItems")) {
         this.items.deserializeNBT(registries, tag.getCompound("ShowItems"));
      }
   }

   public DyeColor getColor() {
      return this.color;
   }

   public void setColor(DyeColor color) {
      this.color = color;
   }

   public ItemStackHandler getItems() {
      return this.items;
   }
}
