package net.witherspawnanimation.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.witherspawnanimation.init.WitherSpawnAnimationModBlocks;
import net.witherspawnanimation.init.WitherSpawnAnimationModGameRules;

@EventBusSubscriber
public class NoWitherSpawnNormalProcedure {
   @SubscribeEvent
   public static void onBlockPlace(EntityPlaceEvent event) {
      execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
   }

   public static void execute(LevelAccessor world, double x, double y, double z) {
      execute(null, world, x, y, z);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
      if (world.getLevelData().getGameRules().getBoolean(WitherSpawnAnimationModGameRules.WITHER_SPAWN_ANIMATION)
         && (
            world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == Blocks.SOUL_SOIL
               || world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == Blocks.SOUL_SAND
               || "bygonenether:soul_stone".equals(BuiltInRegistries.BLOCK.getKey(world.getBlockState(BlockPos.containing(x, y, z)).getBlock()).toString())
         )) {
         if (world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() == Blocks.AIR) {
            world.setBlock(BlockPos.containing(x + 1.0, y, z), ((Block)WitherSpawnAnimationModBlocks.NO_W.get()).defaultBlockState(), 3);
         }

         if (world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() == Blocks.AIR) {
            world.setBlock(BlockPos.containing(x - 1.0, y, z), ((Block)WitherSpawnAnimationModBlocks.NO_W.get()).defaultBlockState(), 3);
         }

         if (world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() == Blocks.AIR) {
            world.setBlock(BlockPos.containing(x, y, z + 1.0), ((Block)WitherSpawnAnimationModBlocks.NO_W.get()).defaultBlockState(), 3);
         }

         if (world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() == Blocks.AIR) {
            world.setBlock(BlockPos.containing(x, y, z - 1.0), ((Block)WitherSpawnAnimationModBlocks.NO_W.get()).defaultBlockState(), 3);
         }
      }
   }
}
