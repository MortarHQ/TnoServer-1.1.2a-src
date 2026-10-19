package com.chronodawn.client;

import com.chronodawn.blocks.TemporalGrassBlock;
import com.chronodawn.core.dimension.ChronoDawnBiomeProvider;
import com.chronodawn.registry.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class TemporalGrassEdgeTint {
   public static final int DEFAULT_FALLBACK = 5999300;
   public static final int EDGE_TINT = 9485287;
   public static final int SAND_NEIGHBOR_TINT = 15265781;
   public static final int BIOME_TINT_FADED = 16752704;
   public static final int SAND_NEIGHBOR_TINT_FADED = 16777215;
   private static final int RADIUS = 3;
   public static final int WET_TINT = 3823999;
   private static final int WATER_RADIUS = 2;

   private TemporalGrassEdgeTint() {
   }

   static boolean isInFadedPlains(BlockPos pos) {
      ClientLevel level = Minecraft.getInstance().level;
      return level == null ? false : level.getBiome(pos).is(ChronoDawnBiomeProvider.CHRONO_DAWN_FADED_PLAINS);
   }

   public static int provide(BlockState state, @Nullable BlockAndTintGetter world, @Nullable BlockPos pos, int tintIndex) {
      if (tintIndex != 0) {
         return -1;
      } else if (world != null && pos != null && world.getBlockState(pos).getBlock() instanceof TemporalGrassBlock) {
         int base = BiomeColors.getAverageGrassColor(world, pos);
         int gravelEdge = isInFadedPlains(pos) ? base : 9485287;
         return blend(world, pos, base, 9485287, gravelEdge);
      } else {
         return 5999300;
      }
   }

   static boolean scanForGrassNeighbor(BlockAndTintGetter world, BlockPos pos) {
      MutableBlockPos cur = new MutableBlockPos();

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            if (dx != 0 || dz != 0) {
               cur.set(pos.getX() + dx, pos.getY(), pos.getZ() + dz);
               if (world.getBlockState(cur).getBlock() instanceof TemporalGrassBlock) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static int provideForSandGravel(BlockState state, @Nullable BlockAndTintGetter world, @Nullable BlockPos pos, int tintIndex) {
      if (tintIndex != 0) {
         return -1;
      }

      if (world != null && pos != null) {
         boolean nearGrass = scanForGrassNeighbor(world, pos);
         boolean isSand = state.is((Block)ModBlocks.TEMPORAL_SAND.get()) || state.is(Blocks.SAND);
         boolean fadedGravel = !isSand && isInFadedPlains(pos);
         if (fadedGravel) {
            int result = 16752704;
            if (nearGrass) {
               result = multiplyRgb(result, 16777215);
            }

            return result;
         } else {
            return nearGrass ? 15265781 : 16777215;
         }
      } else {
         return 16777215;
      }
   }

   static int blend(BlockGetter world, BlockPos pos, int baseTint, int sandEdgeTint, int gravelEdgeTint) {
      int minDistSand = 4;
      int minDistGravel = 4;
      int minDistWater = 3;
      MutableBlockPos cur = new MutableBlockPos();

      label98:
      for (int dx = -3; dx <= 3; dx++) {
         for (int dz = -3; dz <= 3; dz++) {
            if (dx != 0 || dz != 0) {
               int d = Math.max(Math.abs(dx), Math.abs(dz));
               boolean canImproveSand = d < minDistSand;
               boolean canImproveGravel = d < minDistGravel;
               boolean canImproveWater = d <= 2 && d < minDistWater;
               if (canImproveSand || canImproveGravel || canImproveWater) {
                  cur.set(pos.getX() + dx, pos.getY(), pos.getZ() + dz);
                  BlockState neighbor = world.getBlockState(cur);
                  if (canImproveSand && isSandEdgeTrigger(neighbor)) {
                     minDistSand = d;
                  }

                  if (canImproveGravel && isGravelEdgeTrigger(neighbor)) {
                     minDistGravel = d;
                  }

                  if (canImproveWater) {
                     if (isWater(neighbor)) {
                        minDistWater = d;
                     } else {
                        cur.setY(pos.getY() - 1);
                        if (isWater(world.getBlockState(cur))) {
                           minDistWater = d;
                        }

                        cur.setY(pos.getY());
                     }
                  }

                  if (minDistSand == 1 && minDistGravel == 1 && minDistWater == 1) {
                     break label98;
                  }
               }
            }
         }
      }

      int result = baseTint;
      int minDistEdge = Math.min(minDistSand, minDistGravel);
      if (minDistEdge <= 3) {
         float t = (4 - minDistEdge) / 4.0F;
         int edgeTint = minDistSand <= minDistGravel ? sandEdgeTint : gravelEdgeTint;
         result = lerpRgb(result, edgeTint, t);
      }

      if (minDistWater <= 2) {
         float t = (3 - minDistWater) / 3.0F;
         result = lerpRgb(result, 3823999, t);
      }

      return result;
   }

   static boolean isSandEdgeTrigger(BlockState state) {
      return state.is((Block)ModBlocks.TEMPORAL_SAND.get()) || state.is(Blocks.SAND);
   }

   static boolean isGravelEdgeTrigger(BlockState state) {
      return state.is((Block)ModBlocks.TEMPORAL_GRAVEL.get()) || state.is(Blocks.GRAVEL);
   }

   static boolean isWater(BlockState state) {
      return state.getFluidState().is(FluidTags.WATER);
   }

   public static int lerpRgb(int a, int b, float t) {
      int ar = a >> 16 & 0xFF;
      int ag = a >> 8 & 0xFF;
      int ab = a & 0xFF;
      int br = b >> 16 & 0xFF;
      int bg = b >> 8 & 0xFF;
      int bb = b & 0xFF;
      int r = Math.round(ar + (br - ar) * t);
      int g = Math.round(ag + (bg - ag) * t);
      int bl = Math.round(ab + (bb - ab) * t);
      return r << 16 | g << 8 | bl;
   }

   public static int multiplyRgb(int a, int b) {
      int ar = a >> 16 & 0xFF;
      int ag = a >> 8 & 0xFF;
      int ab = a & 0xFF;
      int br = b >> 16 & 0xFF;
      int bg = b >> 8 & 0xFF;
      int bb = b & 0xFF;
      int r = ar * br / 255;
      int g = ag * bg / 255;
      int bl = ab * bb / 255;
      return r << 16 | g << 8 | bl;
   }
}
