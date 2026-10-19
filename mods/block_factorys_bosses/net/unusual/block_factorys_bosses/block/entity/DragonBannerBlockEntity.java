package net.unusual.block_factorys_bosses.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.unusual.block_factorys_bosses.init.BossesRiseBlockEntities;

public class DragonBannerBlockEntity extends BlockEntity {
   public DragonBannerBlockEntity(BlockPos position, BlockState state) {
      super((BlockEntityType)BossesRiseBlockEntities.DRAGON_BANNER.get(), position, state);
   }
}
