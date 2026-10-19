package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.util.DistributionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CactusPatchFeature extends Feature<NoneFeatureConfiguration> {
   public static final int MAX_PLACEMENT_ATTEMPTS = 16;
   public static final float PLACEMENT_SKIP_CHANCE = 0.25F;
   public static final int MAX_TOTAL_PLACEMENTS = 4;
   public static final int PLACEMENT_RADIUS_XZ = 7;
   public static final int PLACEMENT_RADIUS_Y = 5;
   public static final int MAX_CACTUS_HEIGHT = 3;
   public static final float CACTUS_HEIGHT_GROWTH_CHANCE = 0.3F;

   public CactusPatchFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      RandomSource random = context.random();
      int cactiRemaining = 4;

      for (int j = 0; j < 16; j++) {
         if (cactiRemaining <= 0) {
            return true;
         }

         if (!(random.nextFloat() < 0.25F)) {
            BlockPos pos = DistributionUtils.ellipsoidCenterBiasedSpread(
               7.0F, 5.0F, random, (x, y, z) -> origin.offset(Math.round(x), Math.round(y), Math.round(z))
            );
            if (level.getBlockState(pos.below()).is((Block)BlockModule.ANCIENT_SAND.get())) {
               boolean placedCactus = false;

               for (int i = 0;
                  i < 3
                     && level.getBlockState(pos.above(i)).isAir()
                     && level.getBlockState(pos.above(i).north()).isAir()
                     && level.getBlockState(pos.above(i).east()).isAir()
                     && level.getBlockState(pos.above(i).west()).isAir()
                     && level.getBlockState(pos.above(i).south()).isAir();
                  i++
               ) {
                  level.setBlock(pos.above(i), Blocks.CACTUS.defaultBlockState(), 3);
                  placedCactus = true;
                  if (random.nextFloat() >= 0.3F) {
                     break;
                  }
               }

               if (placedCactus) {
                  cactiRemaining--;
               }
            }
         }
      }

      return cactiRemaining < 4;
   }
}
