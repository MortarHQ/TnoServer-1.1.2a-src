package com.cupboard.util;

import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class BlockSearch {
   final BiPredicate<BlockGetter, BlockPos> DOUBLE_AIR = (world, pos) -> world.getBlockState(pos).isAir() && world.getBlockState(pos.above()).isAir();
   final BiPredicate<BlockGetter, BlockPos> DOUBLE_AIR_GROUND = this.DOUBLE_AIR.and((world, pos) -> world.getBlockState(pos.below()).isSolid());

   public static BlockPos findAround(
      BlockGetter world, BlockPos start, int verticalRange, int horizontalRange, int yStep, BiPredicate<BlockGetter, BlockPos> predicate
   ) {
      if (horizontalRange < 1 && verticalRange < 1) {
         return null;
      }

      MutableBlockPos temp = new MutableBlockPos();
      int y = 0;
      int y_offset = yStep;
      boolean checkLoaded = world instanceof Level;
      Level level = checkLoaded ? (Level)world : null;

      for (int i = 0; i < verticalRange + 2; i++) {
         for (int steps = 1; steps <= horizontalRange; steps++) {
            temp.set(start.getX() - steps, start.getY() + y, start.getZ() - steps);

            for (int x = 0; x <= steps; x++) {
               temp.set(temp.getX() + 1, temp.getY(), temp.getZ());
               if (checkLoaded) {
                  if (level.hasChunk(temp.getX() >> 4, temp.getZ() >> 4) && predicate.test(world, temp)) {
                     return temp;
                  }
               } else if (predicate.test(world, temp)) {
                  return temp;
               }
            }

            for (int z = 0; z <= steps; z++) {
               temp.set(temp.getX(), temp.getY(), temp.getZ() + 1);
               if (checkLoaded) {
                  if (level.hasChunk(temp.getX() >> 4, temp.getZ() >> 4) && predicate.test(world, temp)) {
                     return temp;
                  }
               } else if (predicate.test(world, temp)) {
                  return temp;
               }
            }

            for (int x = 0; x <= steps; x++) {
               temp.set(temp.getX() - 1, temp.getY(), temp.getZ());
               if (checkLoaded) {
                  if (level.hasChunk(temp.getX() >> 4, temp.getZ() >> 4) && predicate.test(world, temp)) {
                     return temp;
                  }
               } else if (predicate.test(world, temp)) {
                  return temp;
               }
            }

            for (int z = 0; z <= steps; z++) {
               temp.set(temp.getX(), temp.getY(), temp.getZ() - 1);
               if (checkLoaded) {
                  if (level.hasChunk(temp.getX() >> 4, temp.getZ() >> 4) && predicate.test(world, temp)) {
                     return temp;
                  }
               } else if (predicate.test(world, temp)) {
                  return temp;
               }
            }
         }

         y += y_offset;
         y_offset = y_offset > 0 ? y_offset + yStep : y_offset - yStep;
         y_offset *= -1;
      }

      return null;
   }
}
