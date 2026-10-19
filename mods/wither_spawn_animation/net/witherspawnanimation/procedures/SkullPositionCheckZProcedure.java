package net.witherspawnanimation.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class SkullPositionCheckZProcedure {
   public static double execute(LevelAccessor world, double x, double y, double z) {
      if (Blocks.WITHER_SKELETON_SKULL == world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock()
         && Blocks.WITHER_SKELETON_SKULL == world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock()) {
         return x;
      } else if (Blocks.WITHER_SKELETON_SKULL == world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock()) {
         return x + 1.0;
      } else {
         return Blocks.WITHER_SKELETON_SKULL == world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() ? x - 1.0 : x;
      }
   }
}
