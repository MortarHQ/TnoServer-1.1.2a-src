package com.github.wal_bos.moreskills.handler.ronova;

import com.github.wal_bos.moreskills.ability.skill.shades.ronova.RonovaConfigAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class RonovaDeadLand {
   private RonovaDeadLand() {
   }

   public static void convert(ServerLevel level, BlockPos center, int radius, int attempts) {
      if ((Boolean)RonovaConfigAccess.cfg().deathFieldDeadLandEnabled.get()) {
         if (radius > 0 && attempts > 0) {
            int changed = 0;

            for (int i = 0; i < attempts * 2 && changed < attempts; i++) {
               BlockPos base = center.offset(
                  level.random.nextInt(radius * 2 + 1) - radius, level.random.nextInt(17) - 8, level.random.nextInt(radius * 2 + 1) - radius
               );
               BlockPos pos = findConvertible(level, base);
               if (pos != null) {
                  BlockState state = level.getBlockState(pos);
                  BlockState replacement = replacementFor(level, pos, state);
                  if (replacement != null) {
                     level.setBlock(pos, replacement, 3);
                     changed++;
                  }
               }
            }
         }
      }
   }

   private static BlockPos findConvertible(ServerLevel level, BlockPos base) {
      for (int y = 0; y <= 8; y++) {
         BlockPos up = base.above(y);
         if (level.isLoaded(up) && replacementFor(level, up, level.getBlockState(up)) != null) {
            return up;
         }

         BlockPos down = base.below(y);
         if (level.isLoaded(down) && replacementFor(level, down, level.getBlockState(down)) != null) {
            return down;
         }
      }

      return null;
   }

   private static BlockState replacementFor(ServerLevel level, BlockPos pos, BlockState state) {
      if (state.isAir()) {
         return null;
      } else if (state.hasBlockEntity()) {
         return null;
      } else if (!state.getFluidState().isEmpty()) {
         return null;
      } else if (state.is(BlockTags.LEAVES)) {
         return Blocks.AIR.defaultBlockState();
      } else if (state.is(BlockTags.FLOWERS)) {
         return Blocks.DEAD_BUSH.defaultBlockState();
      } else if (state.is(BlockTags.CROPS)) {
         return Blocks.DEAD_BUSH.defaultBlockState();
      } else if (state.is(BlockTags.SAPLINGS)) {
         return Blocks.DEAD_BUSH.defaultBlockState();
      } else if (state.is(Blocks.SHORT_GRASS)) {
         return Blocks.DEAD_BUSH.defaultBlockState();
      } else if (state.is(Blocks.TALL_GRASS)) {
         return Blocks.DEAD_BUSH.defaultBlockState();
      } else if (state.is(Blocks.FERN)) {
         return Blocks.DEAD_BUSH.defaultBlockState();
      } else if (state.is(Blocks.LARGE_FERN)) {
         return Blocks.DEAD_BUSH.defaultBlockState();
      } else if (state.is(Blocks.VINE)) {
         return Blocks.AIR.defaultBlockState();
      } else if (state.is(Blocks.MOSS_CARPET)) {
         return Blocks.AIR.defaultBlockState();
      } else if (state.is(Blocks.GRASS_BLOCK)) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else if (state.is(Blocks.DIRT)) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else if (state.is(Blocks.ROOTED_DIRT)) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else if (state.is(Blocks.PODZOL)) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else if (state.is(Blocks.MYCELIUM)) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else if (state.is(Blocks.DIRT_PATH)) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else if (state.is(Blocks.FARMLAND)) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else if (state.is(Blocks.MOSS_BLOCK)) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else if (state.is(Blocks.COARSE_DIRT)) {
         return Blocks.SOUL_SOIL.defaultBlockState();
      } else if (state.is(Blocks.MUD)) {
         return Blocks.PACKED_MUD.defaultBlockState();
      } else if (state.is(Blocks.CLAY)) {
         return Blocks.SOUL_SOIL.defaultBlockState();
      } else if (state.is(Blocks.SAND)) {
         return Blocks.SOUL_SAND.defaultBlockState();
      } else if (state.is(Blocks.RED_SAND)) {
         return Blocks.SOUL_SAND.defaultBlockState();
      } else if (state.is(Blocks.GRAVEL)) {
         return Blocks.SOUL_SOIL.defaultBlockState();
      } else if (state.is(Blocks.STONE)) {
         return Blocks.TUFF.defaultBlockState();
      } else if (state.is(Blocks.ANDESITE)) {
         return Blocks.TUFF.defaultBlockState();
      } else if (state.is(Blocks.GRANITE)) {
         return Blocks.TUFF.defaultBlockState();
      } else if (state.is(Blocks.DIORITE)) {
         return Blocks.TUFF.defaultBlockState();
      } else {
         return state.isCollisionShapeFullBlock(level, pos) ? Blocks.SOUL_SOIL.defaultBlockState() : null;
      }
   }
}
