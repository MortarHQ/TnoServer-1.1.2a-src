package net.regions_unexplored.worldgen.foliageplacer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;

public class RUFoliagePlacerUtils {
   public static void placeDiamond(RUFoliagePlacerUtils.Context context, int radius, int y, boolean doubleTrunk) {
      placeDiamond(context, radius, radius, y, doubleTrunk);
   }

   public static void placeDiamond(RUFoliagePlacerUtils.Context context, int radius, int maxDistance, int y, boolean doubleTrunk) {
      int offset = doubleTrunk ? 1 : 0;
      MutableBlockPos pos = new MutableBlockPos();

      for (int dx = -radius; dx <= radius + offset; dx++) {
         for (int dz = -radius; dz <= radius + offset; dz++) {
            if (Math.abs(dx) + Math.abs(dz) <= maxDistance) {
               pos.setWithOffset(context.origin, dx, y, dz);
               placeSingle(context, pos);
            }
         }
      }
   }

   public static void placeSquare(RUFoliagePlacerUtils.Context context, int radius, int y, boolean doubleTrunk) {
      placeSquare(context, radius, y, doubleTrunk, 1.0F);
   }

   public static void placeSquare(RUFoliagePlacerUtils.Context context, int radius, int y, boolean doubleTrunk, float chance) {
      int offset = doubleTrunk ? 1 : 0;
      MutableBlockPos pos = new MutableBlockPos();

      for (int dx = -radius; dx <= radius + offset; dx++) {
         for (int dz = -radius; dz <= radius + offset; dz++) {
            if (Math.abs(dx) != radius || Math.abs(dz) != radius || !(chance < context.random.nextFloat())) {
               pos.setWithOffset(context.origin, dx, y, dz);
               placeSingle(context, pos);
            }
         }
      }
   }

   public static void placeSingle(RUFoliagePlacerUtils.Context context, BlockPos pos, float chance) {
      if (chance >= 1.0F || chance < context.random.nextFloat()) {
         placeSingle(context, pos);
      }
   }

   public static void placeSingle(RUFoliagePlacerUtils.Context context, BlockPos pos) {
      placeSingle(context, pos, context.foliageProvider);
   }

   public static void placeSingle(RUFoliagePlacerUtils.Context context, BlockPos pos, BlockStateProvider foliageProvider) {
      pos = pos.above(context.offset);
      boolean isPersistent = context.level.isStateAtPosition(pos, state -> state.getOptionalValue(BlockStateProperties.PERSISTENT).orElse(false));
      if (!isPersistent && TreeFeature.validTreePos(context.level, pos)) {
         BlockState foliageState = foliageProvider.getState(context.random, pos);
         if (foliageState.hasProperty(BlockStateProperties.WATERLOGGED)) {
            foliageState = (BlockState)foliageState.setValue(
               BlockStateProperties.WATERLOGGED, context.level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER))
            );
         }

         context.foliageSetter.set(pos, foliageState);
      }
   }

   public record Context(
      LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, BlockStateProvider foliageProvider, BlockPos origin, int offset
   ) {
   }
}
