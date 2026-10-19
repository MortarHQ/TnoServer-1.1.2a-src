package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util.NoisySphereUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FloorReplaceFeature extends Feature<FloorReplaceConfig> {
   private static final double NOISE_FREQUENCY_XZ = 0.15;
   private static final double NOISE_FREQUENCY_Y = 0.15;
   private static final long NOISE_SEED_FLIP_MASK = -7005860059682304583L;

   public FloorReplaceFeature(Codec<FloorReplaceConfig> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<FloorReplaceConfig> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      int radiusMin = ((FloorReplaceConfig)context.config()).radiusMin;
      int radiusMax = ((FloorReplaceConfig)context.config()).radiusMax;
      List<Block> matches = ((FloorReplaceConfig)context.config()).matches;
      long noiseSeed = context.level().getSeed() ^ -7005860059682304583L;

      for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(origin, noiseSeed, 0.15, 0.15, radiusMin, radiusMax)) {
         if (matches.contains(level.getBlockState(here).getBlock())) {
            for (int yOffset = ((FloorReplaceConfig)context.config()).width; yOffset >= 1; yOffset--) {
               if (level.getBlockState(here.above(yOffset)).isAir()) {
                  level.setBlock(here, ((FloorReplaceConfig)context.config()).place, 3);
                  break;
               }
            }
         }
      }

      return true;
   }
}
