package net.witherspawnanimation.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.witherspawnanimation.WitherSpawnAnimationMod;

public class TickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      String registryname = "";
      registryname = "bygonenether:soul_stone";
      if (!registryname.equals(BuiltInRegistries.BLOCK.getKey(world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock()).toString())
         && world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() != Blocks.SOUL_SAND
         && world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() != Blocks.SOUL_SOIL
         && !registryname.equals(BuiltInRegistries.BLOCK.getKey(world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock()).toString())
         && world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() != Blocks.SOUL_SAND
         && world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() != Blocks.SOUL_SOIL
         && !registryname.equals(BuiltInRegistries.BLOCK.getKey(world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock()).toString())
         && world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() != Blocks.SOUL_SAND
         && world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() != Blocks.SOUL_SOIL
         && !registryname.equals(BuiltInRegistries.BLOCK.getKey(world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock()).toString())
         && world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() != Blocks.SOUL_SAND
         && world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() != Blocks.SOUL_SOIL) {
         WitherSpawnAnimationMod.queueServerWork(1, () -> world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3));
      }
   }
}
