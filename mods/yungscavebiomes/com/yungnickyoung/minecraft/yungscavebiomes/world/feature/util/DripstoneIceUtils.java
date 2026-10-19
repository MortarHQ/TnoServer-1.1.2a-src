package com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class DripstoneIceUtils {
   public static double getDripstoneHeight(double radialDistance, double radius, double scale, double bluntness) {
      if (radialDistance < bluntness) {
         radialDistance = bluntness;
      }

      double h = 0.384;
      double i = radialDistance / radius * h;
      double j = 0.75 * Math.pow(i, 1.3333333333333333);
      double k = Math.pow(i, 0.6666666666666666);
      double l = 0.3333333333333333 * Math.log(i);
      double m = scale * (j - k - l);
      m = Math.max(m, 0.0);
      return m / h * radius;
   }

   public static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel worldGenLevel, BlockPos blockPos, int radius) {
      if (isEmptyOrWaterOrLava(worldGenLevel, blockPos)) {
         return false;
      }

      float f = 6.0F;
      float increment = 6.0F / radius;

      for (float theta = 0.0F; theta < (float) (Math.PI * 2); theta += increment) {
         int dx = (int)(Mth.cos(theta) * radius);
         int dz = (int)(Mth.sin(theta) * radius);
         if (isEmptyOrWaterOrLava(worldGenLevel, blockPos.offset(dx, 0, dz))) {
            return false;
         }
      }

      return true;
   }

   public static boolean isEmpty(LevelAccessor levelAccessor, BlockPos blockPos) {
      return levelAccessor.isStateAtPosition(blockPos, BlockStateBase::isAir);
   }

   public static boolean isEmptyOrWater(LevelAccessor levelAccessor, BlockPos blockPos) {
      return levelAccessor.isStateAtPosition(blockPos, DripstoneIceUtils::isEmptyOrWater);
   }

   public static boolean isEmptyOrWaterOrLava(LevelAccessor levelAccessor, BlockPos blockPos) {
      return levelAccessor.isStateAtPosition(blockPos, DripstoneIceUtils::isEmptyOrWaterOrLava);
   }

   public static void buildBaseToTipColumn(Direction direction, int i, boolean bl, Consumer<BlockState> consumer) {
      if (i >= 3) {
         consumer.accept(createIcicle(direction, DripstoneThickness.BASE));

         for (int j = 0; j < i - 3; j++) {
            consumer.accept(createIcicle(direction, DripstoneThickness.MIDDLE));
         }
      }

      if (i >= 2) {
         consumer.accept(createIcicle(direction, DripstoneThickness.FRUSTUM));
      }

      if (i >= 1) {
         consumer.accept(createIcicle(direction, bl ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
      }
   }

   public static void growIcicle(LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, int i, boolean bl) {
      if (isIcicleBase(levelAccessor.getBlockState(blockPos.relative(direction.getOpposite())))) {
         MutableBlockPos mutable = blockPos.mutable();
         buildBaseToTipColumn(direction, i, bl, blockState -> {
            levelAccessor.setBlock(mutable, blockState, 2);
            mutable.move(direction);
         });
      }
   }

   public static boolean placePackedIceIfPossible(LevelAccessor levelAccessor, BlockPos blockPos) {
      BlockState blockState = levelAccessor.getBlockState(blockPos);
      if (blockState.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
         levelAccessor.setBlock(blockPos, Blocks.PACKED_ICE.defaultBlockState(), 2);
         return true;
      } else {
         return false;
      }
   }

   private static BlockState createIcicle(Direction direction, DripstoneThickness dripstoneThickness) {
      return (BlockState)((Block)BlockModule.ICICLE.get()).defaultBlockState().setValue(PointedDripstoneBlock.THICKNESS, dripstoneThickness);
   }

   public static boolean isDripstoneBaseOrLava(BlockState blockState) {
      return isIcicleBase(blockState) || blockState.is(Blocks.LAVA);
   }

   public static boolean isIcicleBase(BlockState blockState) {
      return blockState.is(Blocks.PACKED_ICE) || blockState.is(BlockTags.DRIPSTONE_REPLACEABLE);
   }

   public static boolean isEmptyOrWater(BlockState blockState) {
      return blockState.isAir() || blockState.is(Blocks.WATER);
   }

   public static boolean isEmptyOrWaterOrLava(BlockState blockState) {
      return blockState.isAir() || blockState.is(Blocks.WATER) || blockState.is(Blocks.LAVA);
   }
}
