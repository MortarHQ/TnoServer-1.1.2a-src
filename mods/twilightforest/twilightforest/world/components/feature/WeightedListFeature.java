package twilightforest.world.components.feature;

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
import twilightforest.world.components.feature.config.WeightedListFeatureConfig;

public class WeightedListFeature extends Feature<WeightedListFeatureConfig> {
   public WeightedListFeature(Codec<WeightedListFeatureConfig> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<WeightedListFeatureConfig> context) {
      WeightedListFeatureConfig weightedListFeatureConfig = (WeightedListFeatureConfig)context.config();
      RandomSource random = context.random();
      WorldGenLevel worldGenLevel = context.level();
      ChunkGenerator chunkGenerator = context.chunkGenerator();
      BlockPos blockPos = context.origin();
      Optional<Holder<PlacedFeature>> randomFeature = weightedListFeatureConfig.getRandomFeature(random);
      return randomFeature.isEmpty() ? false : ((PlacedFeature)randomFeature.get().value()).place(worldGenLevel, chunkGenerator, random, blockPos);
   }
}
