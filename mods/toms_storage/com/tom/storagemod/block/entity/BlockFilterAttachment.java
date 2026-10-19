package com.tom.storagemod.block.entity;

import com.tom.storagemod.inventory.BlockFilter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class BlockFilterAttachment implements INBTSerializable<CompoundTag> {
   private final BlockFilter filter;

   public BlockFilterAttachment(IAttachmentHolder holder) {
      this.filter = new BlockFilter(((BlockEntity)holder).getBlockPos());
   }

   public CompoundTag serializeNBT(Provider provider) {
      return this.filter.serializeNBT(provider);
   }

   public void deserializeNBT(Provider provider, CompoundTag nbt) {
      this.filter.deserializeNBT(provider, nbt);
   }

   public BlockFilter getFilter() {
      return this.filter;
   }
}
