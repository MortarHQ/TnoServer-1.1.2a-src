package io.github.Memoires.mysticism.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class WaterPodzolLakeFeature extends Feature<Configuration> {
   public WaterPodzolLakeFeature(Codec<Configuration> codec) {
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

   public static Configuration waterPodzolConfig() {
      return new Configuration(BlockStateProvider.simple(Blocks.WATER.defaultBlockState()), BlockStateProvider.simple(Blocks.PODZOL.defaultBlockState()));
   }
}
