package net.witherspawnanimation.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class SkullPositionCheckXProcedure {
   public static double execute(LevelAccessor world, double x, double y, double z) {
      if (Blocks.WITHER_SKELETON_SKULL == world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock()
         && Blocks.WITHER_SKELETON_SKULL == world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock()) {
         return z;
      } else if (Blocks.WITHER_SKELETON_SKULL == world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock()) {
         return z + 1.0;
      } else {
         return Blocks.WITHER_SKELETON_SKULL == world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() ? z - 1.0 : z;
      }
   }
}
