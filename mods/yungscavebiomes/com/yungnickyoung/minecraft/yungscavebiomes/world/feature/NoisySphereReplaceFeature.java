package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util.NoisySphereUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class NoisySphereReplaceFeature extends Feature<NoisySphereReplaceConfig> {
   private static final double NOISE_FREQUENCY_XZ = 0.15;
   private static final double NOISE_FREQUENCY_Y = 0.15;
   private static final long NOISE_SEED_FLIP_MASK = -7005860059682304583L;

   public NoisySphereReplaceFeature(Codec<NoisySphereReplaceConfig> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoisySphereReplaceConfig> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      int radiusMin = ((NoisySphereReplaceConfig)context.config()).radiusMin;
      int radiusMax = ((NoisySphereReplaceConfig)context.config()).radiusMax;
      List<Block> matches = ((NoisySphereReplaceConfig)context.config()).matches;
      long noiseSeed = context.level().getSeed() ^ -7005860059682304583L;

      for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(origin, noiseSeed, 0.15, 0.15, radiusMin, radiusMax)) {
         if (matches.contains(level.getBlockState(here).getBlock())) {
            level.setBlock(here, ((NoisySphereReplaceConfig)context.config()).place, 3);
         }
      }

      return true;
   }
}
