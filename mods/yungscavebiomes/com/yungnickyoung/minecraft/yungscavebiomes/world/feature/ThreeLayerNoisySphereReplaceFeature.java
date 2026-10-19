package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util.NoisySphereUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ThreeLayerNoisySphereReplaceFeature extends Feature<ThreeLayerNoisySphereReplaceConfig> {
   private static final double NOISE_FREQUENCY_XZ = 0.15;
   private static final double NOISE_FREQUENCY_Y = 0.15;
   private static final long NOISE_SEED_FLIP_MASK = -7005860059682304583L;

   public ThreeLayerNoisySphereReplaceFeature(Codec<ThreeLayerNoisySphereReplaceConfig> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<ThreeLayerNoisySphereReplaceConfig> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      int radiusMin = ((ThreeLayerNoisySphereReplaceConfig)context.config()).radiusMin;
      int radiusMax = ((ThreeLayerNoisySphereReplaceConfig)context.config()).radiusMax;
      List<Block> matches = ((ThreeLayerNoisySphereReplaceConfig)context.config()).matches;
      long noiseSeed = context.level().getSeed() ^ -7005860059682304583L;
      BlockState floor = ((ThreeLayerNoisySphereReplaceConfig)context.config()).floor.orElse(null);
      BlockState ceiling = ((ThreeLayerNoisySphereReplaceConfig)context.config()).ceiling.orElse(null);
      int floorWidth = ((ThreeLayerNoisySphereReplaceConfig)context.config()).floorWidth;
      int ceilingWidth = ((ThreeLayerNoisySphereReplaceConfig)context.config()).ceilingWidth;

      for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(origin, noiseSeed, 0.15, 0.15, radiusMin, radiusMax)) {
         if (matches.contains(level.getBlockState(here).getBlock())) {
            boolean placed = false;
            if (floor != null) {
               for (int yOffset = 1; yOffset <= floorWidth; yOffset++) {
                  if (level.getBlockState(here.above(yOffset)).isAir()) {
                     level.setBlock(here, floor, 3);
                     placed = true;
                     break;
                  }
               }
            }

            if (!placed && ceiling != null) {
               for (int yOffset = 1; yOffset <= ceilingWidth; yOffset++) {
                  if (level.getBlockState(here.below(yOffset)).isAir()) {
                     level.setBlock(here, ceiling, 3);
                     placed = true;
                     break;
                  }
               }
            }

            if (!placed) {
               level.setBlock(here, ((ThreeLayerNoisySphereReplaceConfig)context.config()).regular, 3);
            }
         }
      }

      return true;
   }
}
