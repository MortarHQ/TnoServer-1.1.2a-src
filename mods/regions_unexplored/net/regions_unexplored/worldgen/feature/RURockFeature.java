package net.regions_unexplored.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.regions_unexplored.worldgen.feature.config.RockFeatureConfig;

public class RURockFeature extends Feature<RockFeatureConfig> {
   public static final IntProvider BLOB_Y_OFFSET = new WeightedListInt(
      SimpleWeightedRandomList.builder().add(ConstantInt.of(-1), 3).add(ConstantInt.of(0), 3).add(ConstantInt.of(1), 1).build()
   );

   public RURockFeature() {
      super(RockFeatureConfig.CODEC);
   }

   public boolean place(FeaturePlaceContext<RockFeatureConfig> context) {
      BlockPos origin = context.origin();
      WorldGenLevel level = context.level();
      RandomSource random = context.random();
      RockFeatureConfig config = (RockFeatureConfig)context.config();

      for (int c = 0; c < config.blobCount().sample(random); c++) {
         int xr = Math.min(random.nextInt(3), 1);
         int yr = Math.min(random.nextInt(3), 1);
         int zr = Math.min(random.nextInt(3), 1);
         float tr = (xr + yr + zr) * 0.333F + 0.5F;

         for (BlockPos blockPos : BlockPos.betweenClosed(origin.offset(-xr, -yr, -zr), origin.offset(xr, yr, zr))) {
            if (blockPos.distSqr(origin) <= tr * tr) {
               level.setBlock(blockPos, config.stateProvider().getState(random, origin), 3);
            }
         }

         origin = origin.offset(config.blobOffsetXZ().sample(random), BLOB_Y_OFFSET.sample(random), config.blobOffsetXZ().sample(random));
      }

      return true;
   }
}
