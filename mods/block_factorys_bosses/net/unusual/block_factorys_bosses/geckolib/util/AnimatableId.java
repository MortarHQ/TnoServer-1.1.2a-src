package net.unusual.block_factorys_bosses.geckolib.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface AnimatableId {
   static long ofEntity(Entity entity) {
      return entity.getId();
   }

   static long ofBlockEntity(BlockEntity blockEntity) {
      return blockEntity.getBlockPos().asLong();
   }
}
