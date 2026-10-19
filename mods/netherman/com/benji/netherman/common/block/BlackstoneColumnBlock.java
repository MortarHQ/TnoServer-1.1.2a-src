package com.benji.netherman.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlackstoneColumnBlock extends Block {
   public BlackstoneColumnBlock(Properties properties) {
      super(properties);
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      BlockPos up = pos.above();
      if (up.getY() < level.getMaxBuildHeight()) {
         int trueSurfaceY = level.getMinBuildHeight();

         for (int y = level.getMaxBuildHeight() - 1; y >= level.getMinBuildHeight(); y--) {
            BlockState checkState = level.getBlockState(new BlockPos(pos.getX(), y, pos.getZ()));
            if (!checkState.isAir() && !checkState.is(this)) {
               trueSurfaceY = y;
               break;
            }
         }

         int extraHeight = 5 + Math.abs(pos.getX() * 3131 + pos.getZ() * 1717) % 11;
         if (pos.getY() < trueSurfaceY + extraHeight) {
            BlockState stateAbove = level.getBlockState(up);
            if (stateAbove.getDestroySpeed(level, up) >= 0.0F) {
               level.destroyBlock(up, true);
               level.setBlock(up, this.defaultBlockState(), 3);
               level.scheduleTick(up, this, 2);
            }
         }
      }
   }
}
