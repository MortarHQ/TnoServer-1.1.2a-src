package com.blackgear.vanillabackport.common.level.features;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WeightedRandomSelectorFeature extends Feature<WeightedRandomFeatureConfiguration> {
   public WeightedRandomSelectorFeature(Codec<WeightedRandomFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<WeightedRandomFeatureConfiguration> context) {
      WeightedRandomFeatureConfiguration config = (WeightedRandomFeatureConfiguration)context.config();
      RandomSource random = context.random();
      WorldGenLevel level = context.level();
      ChunkGenerator generator = context.chunkGenerator();
      BlockPos origin = context.origin();
      Optional<Holder<PlacedFeature>> feature = config.features().getRandomValue(random);
      return feature.<Boolean>map(holder -> ((PlacedFeature)holder.value()).place(level, generator, random, origin)).orElse(false);
   }
}
