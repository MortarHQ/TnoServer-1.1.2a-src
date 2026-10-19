package twilightforest.world.components.feature.trees;

import com.mojang.serialization.Codec;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFBlocks;
import twilightforest.util.RootPlacer;
import twilightforest.util.features.FeaturePlacers;
import twilightforest.util.features.FeatureUtil;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;

public class TimeTreeFeature extends HollowTreeFeature {
   public TimeTreeFeature(Codec<TFTreeFeatureConfig> config) {
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
      int height = 8;
      int radius = 1;
      if (!world.isOutsideBuildHeight(pos.getY() + 1)
         && !world.isOutsideBuildHeight(pos.getY() + 8 + 1)
         && !FeatureUtil.isAnyMatchInArea(
            pos.subtract(new Vec3i(1, 4, 1)), 3, 4, 3, blockPos -> world.getBlockState(blockPos).is(BlockTags.FEATURES_CANNOT_REPLACE)
         )
         && !FeatureUtil.isAnyMatchInArea(
            pos.subtract(new Vec3i(1, 0, 1)), 3, 16, 3, blockPos -> !FeaturePlacers.validTreePos(world, blockPos) || blockPos == pos
         )) {
         if (world.getBlockState(pos.below())
            .canSustainPlant(world, pos.below(), Direction.UP, ((SaplingBlock)TFBlocks.TIME_SAPLING.get()).defaultBlockState())
            .isFalse()) {
            return false;
         }

         buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, 1, 1, 0, 12, 0.75, 3, 5, 3, false, config);
         buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, 1, 1, 2, 18, 0.9, 3, 5, 3, false, config);
         buildTrunk(world, trunkPlacer, decorationPlacer, random, pos, 1, 8, config);
         this.buildTinyCrown(world, trunkPlacer, leavesPlacer, random, pos, 1, 8, config);
         BlockPos corePos = pos.offset(-1, 2, 0);
         BlockState stateAtCorePos = world.getBlockState(pos.above());
         if (stateAtCorePos.is(BlockTags.LOGS) || stateAtCorePos.canBeReplaced()) {
            world.setBlock(corePos, (BlockState)((Block)TFBlocks.TIME_LOG_CORE.get()).defaultBlockState().setValue(RotatedPillarBlock.AXIS, Axis.Y), 3);
            world.scheduleTick(corePos, (Block)TFBlocks.TIME_LOG_CORE.get(), 20);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void buildTinyCrown(
      WorldGenLevel world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos pos,
      int radius,
      int height,
      TFTreeFeatureConfig config
   ) {
      int crownRadius = 4;
      int bvar = 1;
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height - 4, 0, 4, 0.35, 1, 3, 1, true, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height - 2, 0, 4, 0.28, 1, 3, 1, true, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height, 0, 4, 0.15, 2, 4, 0, true, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height, 0, 2, 0.05, 1, 3, 0, true, config);
   }
}
