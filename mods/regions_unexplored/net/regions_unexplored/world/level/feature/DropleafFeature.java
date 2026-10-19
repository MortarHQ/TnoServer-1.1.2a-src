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
import net.regions_unexplored.world.level.block.plant.other.DropleafBlock;

public class DropleafFeature extends Feature<NoneFeatureConfiguration> {
   public DropleafFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      if (level.getBlockState(pos.above()).isSolid() && level.getBlockState(pos).isAir()) {
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
         int i = random.nextInt(4) + 4;
         int j = random.nextInt(4) + 4;
         int k = random.nextInt(4) + 4;
         float f = (i + j + k) * 0.333F + 0.5F;

         for (BlockPos blockpos1 : BlockPos.betweenClosed(pos.offset(-i, -j, -k), pos.offset(i, j, k))) {
            if (blockpos1.distSqr(pos) <= f * f
               && level.getBlockState(blockpos1.above()).isFaceSturdy(level, pos.above(), Direction.DOWN)
               && random.nextInt(12) == 0) {
               this.placePillar(level, blockpos1);
            }
         }

         pos = pos.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
      }

      return true;
   }

   public boolean placePillar(LevelAccessor level, BlockPos pos) {
      Random random = new Random();
      int size = 0;
      boolean done = false;
      MutableBlockPos pos1 = pos.mutable();

      while (level.isEmptyBlock(pos1)) {
         pos1.move(Direction.DOWN);
         size++;
         if (level.isOutsideBuildHeight(pos1) || pos1.getY() <= level.getMinBuildHeight() + 5 || pos1.getY() >= level.getMaxBuildHeight() - 5) {
            break;
         }
      }

      if (size < 3) {
         return true;
      }

      size = size / 2 + random.nextInt(size / 2);
      if (!level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN)
         && !level.getBlockState(pos.above()).is(RUBlocks.DROPLEAF.get())
         && !level.getBlockState(pos.above()).is(RUBlocks.DROPLEAF_PLANT.get())) {
         return true;
      }

      MutableBlockPos placePos = pos.mutable();

      for (int count = 0; level.isEmptyBlock(placePos) && count != size / 2; count++) {
         if (level.isOutsideBuildHeight(placePos)) {
            return true;
         }

         if (level.getBlockState(placePos.above()).is(RUBlocks.DROPLEAF.get())) {
            level.setBlock(placePos.above(), RUBlocks.DROPLEAF_PLANT.get().defaultBlockState(), 2);
         }

         level.setBlock(placePos, (BlockState)RUBlocks.DROPLEAF.get().defaultBlockState().setValue(DropleafBlock.AGE, 23 + random.nextInt(2)), 2);
         placePos.move(Direction.DOWN);
      }

      return true;
   }
}
