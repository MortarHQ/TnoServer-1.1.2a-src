package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyPeachCactusBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.util.DistributionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PricklyPeachCactusPatchFeature extends Feature<NoneFeatureConfiguration> {
   public static final int MAX_PLACEMENT_ATTEMPTS = 13;
   public static final float PLACEMENT_SKIP_CHANCE = 0.25F;
   public static final int MAX_TOTAL_PLACEMENTS = 4;
   public static final int PLACEMENT_RADIUS_XZ = 7;
   public static final int PLACEMENT_RADIUS_Y = 5;
   public static final float FRUIT_CHANCE = 0.4F;

   public PricklyPeachCactusPatchFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      RandomSource random = context.random();
      int cactiRemaining = 4;

      for (int i = 0; i < 13; i++) {
         if (cactiRemaining <= 0) {
            return true;
         }

         if (!(random.nextFloat() < 0.25F)) {
            BlockPos pos = DistributionUtils.ellipsoidCenterBiasedSpread(
               7.0F, 5.0F, random, (x, y, z) -> origin.offset(Math.round(x), Math.round(y), Math.round(z))
            );
            if (level.getBlockState(pos.below()).is((Block)BlockModule.ANCIENT_SAND.get())) {
               boolean isAdjacentCactus = false;
               int solidNeighbors = 0;

               for (Direction direction : Plane.HORIZONTAL) {
                  BlockState neighborState = level.getBlockState(pos.relative(direction));
                  Block neighborBlock = neighborState.getBlock();
                  if (neighborBlock == BlockModule.PRICKLY_PEACH_CACTUS.get() || neighborBlock == Blocks.CACTUS) {
                     isAdjacentCactus = true;
                     break;
                  }

                  if (!neighborState.isAir()) {
                     solidNeighbors++;
                  }

                  if (solidNeighbors > 2) {
                     break;
                  }
               }

               if (!isAdjacentCactus && solidNeighbors < 3) {
                  BlockState cactusBlockState = (BlockState)((Block)BlockModule.PRICKLY_PEACH_CACTUS.get())
                     .defaultBlockState()
                     .setValue(PricklyPeachCactusBlock.FRUIT, random.nextFloat() < 0.4F);
                  level.setBlock(pos, cactusBlockState, 3);
                  cactiRemaining--;
               }
            }
         }
      }

      return cactiRemaining < 4;
   }
}
