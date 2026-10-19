package twilightforest.world.components.feature.trees;

import com.mojang.serialization.Codec;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import twilightforest.util.RootPlacer;
import twilightforest.util.features.FeatureLogic;
import twilightforest.util.features.FeaturePlacers;
import twilightforest.util.features.FeatureUtil;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;

public class HollowStumpFeature extends HollowTreeFeature {
   public HollowStumpFeature(Codec<TFTreeFeatureConfig> config) {
      super(config);
   }

   @Override
   public boolean generate(
      WorldGenLevel world,
      RandomSource random,
      BlockPos pos,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RootPlacer decorationPlacer,
      TFTreeFeatureConfig config
   ) {
      int radius = random.nextInt(2) + 2;
      if (!FeatureUtil.isAreaSuitable(world, pos.offset(-radius, 0, -radius), 2 * radius, 6, 2 * radius)) {
         return false;
      }

      this.buildSmallTrunk(world, trunkPlacer, decorationPlacer, random, pos, radius, 6, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, 3, 2, 6, 0.75, 3, 5, 3, false, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, 1, 2, 8, 0.9, 3, 5, 3, false, config);
      return true;
   }

   protected void buildSmallTrunk(
      LevelAccessor world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      RootPlacer decoPlacer,
      RandomSource random,
      BlockPos pos,
      int diameter,
      int maxheight,
      TFTreeFeatureConfig config
   ) {
      int hollow = diameter >> 1;

      for (int dx = -diameter; dx <= diameter; dx++) {
         for (int dz = -diameter; dz <= diameter; dz++) {
            for (int dy = -4; dy < 0; dy++) {
               int ax = Math.abs(dx);
               int az = Math.abs(dz);
               int dist = (int)(Math.max(ax, az) + Math.min(ax, az) * 0.5);
               if (dist <= diameter) {
                  BlockPos dPos = pos.offset(dx, dy, dz);
                  if (FeatureLogic.hasEmptyNeighborExceptBelow(world, dPos)) {
                     BlockStateProvider trunkProvider = dist > hollow ? config.trunkProvider : config.branchProvider;
                     trunkPlacer.accept(dPos, trunkProvider.getState(random, dPos));
                  } else {
                     FeaturePlacers.placeIfValidRootPos(world, decoPlacer, random, dPos, config.rootsProvider);
                  }
               }
            }
         }
      }

      for (int dx = -diameter; dx <= diameter; dx++) {
         for (int dz = -diameter; dz <= diameter; dz++) {
            int height = 2 + random.nextInt(3) + random.nextInt(2);

            for (int dy = 0; dy <= height; dy++) {
               int ax = Math.abs(dx);
               int az = Math.abs(dz);
               int dist = Math.max(ax, az) + (Math.min(ax, az) >> 1);
               if (dist <= diameter && dist > hollow) {
                  FeaturePlacers.placeIfValidTreePos(world, trunkPlacer, random, pos.offset(dx, dy, dz), config.trunkProvider);
               }
            }
         }
      }
   }
}
