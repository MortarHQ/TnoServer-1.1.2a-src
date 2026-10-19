package net.regions_unexplored.world.level.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.cave.HangingPrismariteBlock;
import net.regions_unexplored.world.level.block.state.properties.HangingPrismariteShape;

public class HangingPrismariteFeature extends Feature<NoneFeatureConfiguration> {
   public HangingPrismariteFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      if (level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN)) {
         this.placeBlob(level, pos);
         return true;
      } else {
         return false;
      }
   }

   public boolean placeBlob(LevelAccessor level, BlockPos pos) {
      Random random = new Random();
      if (pos.getY() <= level.getMinBuildHeight() + 3) {
         return false;
      }

      for (int l = 0; l < 3; l++) {
         int i = random.nextInt(4) + 2;
         int j = random.nextInt(4) + 2;
         int k = random.nextInt(4) + 2;
         float f = (i + j + k) * 0.333F + 0.5F;

         for (BlockPos blockpos1 : BlockPos.betweenClosed(pos.offset(-i, -j, -k), pos.offset(i, j, k))) {
            if (blockpos1.distSqr(pos) <= f * f
               && level.getBlockState(blockpos1.above()).isFaceSturdy(level, pos.above(), Direction.DOWN)
               && random.nextInt(10) == 0) {
               this.placePillar(level, blockpos1);
            }
         }

         pos = pos.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
      }

      return true;
   }

   public boolean placePillar(LevelAccessor level, BlockPos pos) {
      Random random = new Random();
      int size = random.nextInt(5) + 1;
      if (level.isEmptyBlock(pos)
         && (
            level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN)
               || level.getBlockState(pos.above()).is(RUBlocks.HANGING_PRISMARITE.get())
         )) {
         MutableBlockPos placePos = pos.mutable();

         for (int count = 0; level.isEmptyBlock(placePos); count++) {
            if (count == size) {
               return true;
            }

            if (level.isOutsideBuildHeight(placePos)) {
               return true;
            }

            if (level.getBlockState(placePos.above()).is(RUBlocks.HANGING_PRISMARITE.get())) {
               level.setBlock(
                  placePos.above(),
                  (BlockState)RUBlocks.HANGING_PRISMARITE.get().defaultBlockState().setValue(HangingPrismariteBlock.SHAPE, HangingPrismariteShape.FRUSTUM),
                  2
               );
            }

            if (level.getBlockState(placePos.above().above()).is(RUBlocks.HANGING_PRISMARITE.get())) {
               level.setBlock(
                  placePos.above().above(),
                  (BlockState)RUBlocks.HANGING_PRISMARITE.get().defaultBlockState().setValue(HangingPrismariteBlock.SHAPE, HangingPrismariteShape.COLUMN),
                  2
               );
            }

            level.setBlock(placePos, RUBlocks.HANGING_PRISMARITE.get().defaultBlockState(), 2);
            placePos.move(Direction.DOWN);
         }

         return true;
      } else {
         return true;
      }
   }
}
