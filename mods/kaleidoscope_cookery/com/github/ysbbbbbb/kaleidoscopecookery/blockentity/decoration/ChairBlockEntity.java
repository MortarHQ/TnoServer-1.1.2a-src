package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

public class ChairBlockEntity extends BaseBlockEntity {
   private static final String COLOR_TAG = "CarpetColor";
   private DyeColor color = DyeColor.WHITE;

   public ChairBlockEntity(BlockPos pos, BlockState blockState) {
      super(ModBlocks.CHAIR_BE.get(), pos, blockState);
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putInt("CarpetColor", this.color.getId());
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.color = DyeColor.byId(tag.getInt("CarpetColor"));
   }

   public DyeColor getColor() {
      return this.color;
   }

   public void setColor(DyeColor color) {
      this.color = color;
   }
}
