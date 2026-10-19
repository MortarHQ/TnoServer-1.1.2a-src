package com.blackgear.vanillabackport.common.level.features;

import com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.core.data.tags.ModBlockTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LeafLitterFeature extends Feature<NoneFeatureConfiguration> {
   public LeafLitterFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      RandomSource random = context.random();
      MutableBlockPos mutable = new MutableBlockPos();

      for (int xRange = 0; xRange < 16; xRange++) {
         if (!random.nextBoolean()) {
            for (int zRange = 0; zRange < 16; zRange++) {
               if (!random.nextBoolean()) {
                  int x = origin.getX() + xRange;
                  int z = origin.getZ() + zRange;
                  mutable.set(x, level.getHeight(Types.MOTION_BLOCKING, x, z) - 1, z);
                  if (level.getBlockState(mutable).is(BlockTags.LEAVES) && level.getBlockState(mutable).is(ModBlockTags.ALLOWS_LEAF_LITTER)) {
                     mutable.set(x, level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
                     BlockState state = (BlockState)((BlockState)ModBlocks.LEAF_LITTER
                           .get()
                           .defaultBlockState()
                           .setValue(LeafLitterBlock.AMOUNT, random.nextIntBetweenInclusive(1, 4)))
                        .setValue(LeafLitterBlock.FACING, Plane.HORIZONTAL.getRandomDirection(random));
                     if (!level.getBlockState(mutable).is(ModBlocks.LEAF_LITTER.get())
                        && level.getBlockState(mutable).isAir()
                        && state.canSurvive(level, mutable)) {
                        level.setBlock(mutable, state, 1);
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
