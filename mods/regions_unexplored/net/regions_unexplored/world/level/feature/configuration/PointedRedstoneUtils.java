package net.regions_unexplored.world.level.feature.configuration;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.cave.PointedRedstoneBlock;

public class PointedRedstoneUtils {
   public static double getRedstoneHeight(double v, double v1, double v2, double v3) {
      if (v < v3) {
         v = v3;
      }

      double d0 = 0.384;
      double d1 = v / v1 * 0.384;
      double d2 = 0.75 * Math.pow(d1, 1.3333333333333333);
      double d3 = Math.pow(d1, 0.6666666666666666);
      double d4 = 0.3333333333333333 * Math.log(d1);
      double d5 = v2 * (d2 - d3 - d4);
      d5 = Math.max(d5, 0.0);
      return d5 / 0.384 * v1;
   }

   public static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel level, BlockPos pos, int i1) {
      if (isEmptyOrWaterOrLava(level, pos)) {
         return false;
      }

      float f = 6.0F;
      float f1 = 6.0F / i1;

      for (float f2 = 0.0F; f2 < (float) (Math.PI * 2); f2 += f1) {
         int i = (int)(Mth.cos(f2) * i1);
         int j = (int)(Mth.sin(f2) * i1);
         if (isEmptyOrWaterOrLava(level, pos.offset(i, 0, j))) {
            return false;
         }
      }

      return true;
   }

   public static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos) {
      return level.isStateAtPosition(pos, PointedRedstoneUtils::isEmptyOrWater);
   }

   public static boolean isEmptyOrWaterOrLava(LevelAccessor level, BlockPos pos) {
      return level.isStateAtPosition(pos, PointedRedstoneUtils::isEmptyOrWaterOrLava);
   }

   public static void buildBaseToTipColumn(Direction direction, int i1, boolean bool, Consumer<BlockState> consumer) {
      if (i1 >= 3) {
         consumer.accept(createPointedRedstone(direction, DripstoneThickness.BASE));

         for (int i = 0; i < i1 - 3; i++) {
            consumer.accept(createPointedRedstone(direction, DripstoneThickness.MIDDLE));
         }
      }

      if (i1 >= 2) {
         consumer.accept(createPointedRedstone(direction, DripstoneThickness.FRUSTUM));
      }

      if (i1 >= 1) {
         consumer.accept(createPointedRedstone(direction, bool ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
      }
   }

   public static void growPointedRedstone(LevelAccessor level, BlockPos pos, Direction direction, int i, boolean bool) {
      if (isRedstoneBase(level.getBlockState(pos.relative(direction.getOpposite())))) {
         MutableBlockPos pos1 = pos.mutable();
         buildBaseToTipColumn(direction, i, bool, p_190846_ -> {
            if (p_190846_.is(RUBlocks.POINTED_REDSTONE.get())) {
               p_190846_ = (BlockState)p_190846_.setValue(PointedRedstoneBlock.WATERLOGGED, level.isWaterAt(pos1));
            }

            level.setBlock(pos1, p_190846_, 2);
            pos1.move(direction);
         });
      }
   }

   public static boolean placeRedstoneBlockIfPossible(LevelAccessor level, BlockPos pos) {
      BlockState blockstate = level.getBlockState(pos);
      if (blockstate.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
         level.setBlock(pos, RUBlocks.RAW_REDSTONE_BLOCK.get().defaultBlockState(), 2);
         return true;
      } else {
         return false;
      }
   }

   public static BlockState createPointedRedstone(Direction direction, DripstoneThickness thickness) {
      return (BlockState)((BlockState)RUBlocks.POINTED_REDSTONE.get().defaultBlockState().setValue(PointedRedstoneBlock.TIP_DIRECTION, direction))
         .setValue(PointedRedstoneBlock.THICKNESS, thickness);
   }

   public static boolean isRedstoneBaseOrLava(BlockState state) {
      return isRedstoneBase(state) || state.is(Blocks.LAVA);
   }

   public static boolean isRedstoneBase(BlockState state) {
      return state.is(RUBlocks.RAW_REDSTONE_BLOCK.get()) || state.is(BlockTags.DRIPSTONE_REPLACEABLE);
   }

   public static boolean isEmptyOrWater(BlockState state) {
      return state.isAir() || state.is(Blocks.WATER);
   }

   public static boolean isNeitherEmptyNorWater(BlockState state) {
      return !state.isAir() && !state.is(Blocks.WATER);
   }

   public static boolean isEmptyOrWaterOrLava(BlockState state) {
      return state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA);
   }
}
