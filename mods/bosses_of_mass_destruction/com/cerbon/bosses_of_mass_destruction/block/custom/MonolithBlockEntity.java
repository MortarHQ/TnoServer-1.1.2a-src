package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MonolithBlockEntity extends ChunkCacheBlockEntity {
   public MonolithBlockEntity(BlockPos pos, BlockState blockState) {
      super((Block)BMDBlocks.MONOLITH_BLOCK.get(), (BlockEntityType<?>)BMDBlockEntities.MONOLITH_BLOCK_ENTITY.get(), pos, blockState);
   }
}
