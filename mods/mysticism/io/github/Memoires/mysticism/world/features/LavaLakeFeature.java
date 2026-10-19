package io.github.Memoires.mysticism.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class LavaLakeFeature extends Feature<Configuration> {
   public LavaLakeFeature(Codec<Configuration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<Configuration> context) {
      try {
         return Feature.LAKE.place(context);
      } catch (Exception e) {
         System.err.println("Failed to place lake: " + e);
         return false;
      }
   }

   public static Configuration lavaLakeConfig() {
      return new Configuration(BlockStateProvider.simple(Blocks.LAVA.defaultBlockState()), BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()));
   }
}
