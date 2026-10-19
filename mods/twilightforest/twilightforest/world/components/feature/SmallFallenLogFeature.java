package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import twilightforest.block.HorizontalHollowLogBlock;
import twilightforest.enums.HollowLogVariants;
import twilightforest.init.TFBlocks;
import twilightforest.util.features.FeatureUtil;
import twilightforest.world.components.feature.config.HollowLogConfig;

public class SmallFallenLogFeature extends Feature<HollowLogConfig> {
   public SmallFallenLogFeature(Codec<HollowLogConfig> configIn) {
      super(configIn);
   }

   public boolean place(FeaturePlaceContext<HollowLogConfig> ctx) {
      WorldGenLevel world = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource rand = ctx.random();
      HollowLogConfig config = (HollowLogConfig)ctx.config();
      boolean shouldMakeAllHollow = rand.nextBoolean();
      boolean goingX = rand.nextBoolean();
      int length = rand.nextInt(4) + 3;
      if (goingX) {
         if (!FeatureUtil.isAreaSuitable(world, pos, length, 2, 2, true)) {
            return false;
         }
      } else if (!FeatureUtil.isAreaSuitable(world, pos, 2, 2, length, true)) {
         return false;
      }

      BlockState logState = config.normal();
      BlockState hollowLogState = config.hollow();
      if (config.hollow().isAir()) {
         hollowLogState = null;
      }

      if (rand.nextInt(5) == 0 && world.getBlockState(pos).liquid()) {
         MutableBlockPos floatingPos = pos.mutable();

         for (int i = 0; i < 10; i++) {
            if (world.getBlockState(floatingPos.above()).isAir()) {
               pos = floatingPos.immutable();
               break;
            }

            floatingPos.move(0, 1, 0);
         }
      }

      BlockState branchState;
      if (goingX) {
         logState = (BlockState)logState.setValue(RotatedPillarBlock.AXIS, Axis.X);
         if (hollowLogState != null) {
            hollowLogState = (BlockState)((BlockState)hollowLogState.setValue(HorizontalHollowLogBlock.HORIZONTAL_AXIS, Axis.X))
               .setValue(HorizontalHollowLogBlock.VARIANT, this.determineHollowProperties(world, pos, rand));
         }

         branchState = (BlockState)logState.setValue(RotatedPillarBlock.AXIS, Axis.Z);

         for (int lx = 0; lx < length; lx++) {
            world.setBlock(pos.offset(lx, 0, 1), this.hollowOrNormal(world, shouldMakeAllHollow, hollowLogState, logState), 3);
            if (rand.nextInt(3) > 0) {
               world.setBlock(pos.offset(lx, 1, 1), this.mossOrSeagrass(world, pos.offset(lx, 1, 1)), 3);
               this.markAboveForPostProcessing(world, pos.offset(lx, 0, 1));
            }
         }
      } else {
         logState = (BlockState)logState.setValue(RotatedPillarBlock.AXIS, Axis.Z);
         if (hollowLogState != null) {
            hollowLogState = (BlockState)((BlockState)hollowLogState.setValue(HorizontalHollowLogBlock.HORIZONTAL_AXIS, Axis.Z))
               .setValue(HorizontalHollowLogBlock.VARIANT, this.determineHollowProperties(world, pos, rand));
         }

         branchState = (BlockState)logState.setValue(RotatedPillarBlock.AXIS, Axis.X);

         for (int lz = 0; lz < length; lz++) {
            world.setBlock(pos.offset(1, 0, lz), this.hollowOrNormal(world, shouldMakeAllHollow, hollowLogState, logState), 3);
            if (rand.nextInt(3) > 0) {
               world.setBlock(pos.offset(1, 1, lz), this.mossOrSeagrass(world, pos.offset(1, 1, lz)), 3);
               this.markAboveForPostProcessing(world, pos.offset(1, 0, lz));
            }
         }
      }

      if (rand.nextInt(3) > 0) {
         int bx;
         int bz;
         if (goingX) {
            bx = rand.nextInt(length);
            bz = rand.nextBoolean() ? 2 : 0;
         } else {
            bx = rand.nextBoolean() ? 2 : 0;
            bz = rand.nextInt(length);
         }

         world.setBlock(pos.offset(bx, 0, bz), branchState, 3);
         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(bx, 1, bz), this.mossOrSeagrass(world, pos.offset(bx, 1, bz)), 3);
            this.markAboveForPostProcessing(world, pos.offset(bx, 0, bz));
         }
      }

      return true;
   }

   private BlockState mossOrSeagrass(WorldGenLevel level, BlockPos pos) {
      if (level.getBlockState(pos.below(2)).is(BlockTags.SNOW)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         return level.getBlockState(pos).is(Blocks.WATER) ? Blocks.SEAGRASS.defaultBlockState() : ((Block)TFBlocks.MOSS_PATCH.get()).defaultBlockState();
      }
   }

   private BlockState hollowOrNormal(WorldGenLevel level, boolean shouldBeHollow, BlockState hollow, BlockState normal) {
      return (shouldBeHollow || level.getRandom().nextInt(3) == 0) && !hollow.isAir() ? hollow : normal;
   }

   private HollowLogVariants.Horizontal determineHollowProperties(WorldGenLevel world, BlockPos pos, RandomSource rand) {
      return world.getBlockState(pos).is(Blocks.WATER)
         ? HollowLogVariants.Horizontal.WATERLOGGED
         : (
            world.getBlockState(pos).is(BlockTags.SNOW)
               ? HollowLogVariants.Horizontal.SNOW
               : (
                  rand.nextInt(5) == 0
                     ? HollowLogVariants.Horizontal.MOSS_AND_GRASS
                     : (rand.nextInt(3) == 0 ? HollowLogVariants.Horizontal.MOSS : HollowLogVariants.Horizontal.EMPTY)
               )
         );
   }
}
