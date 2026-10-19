package net.jadenxgamer.netherexp.core.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class MoundFeature extends Feature<MoundFeature.Config> {
   public MoundFeature(Codec<MoundFeature.Config> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<MoundFeature.Config> context) {
      WorldGenLevel level = context.level();
      MoundFeature.Config config = (MoundFeature.Config)context.config();
      RandomSource random = context.random();
      BlockPos origin = config.hanging() ? context.origin().above(2) : context.origin().below(2);
      BlockState state = level.getBlockState(config.hanging() ? context.origin().above() : context.origin().below());
      if (config.validPlacements().contains(state.getBlockHolder())) {
         int height = config.height() + random.nextInt(config.bonusHeight());
         this.placeMound(config, level, origin, height, state, random);
         int extraMound = Mth.randomBetweenInclusive(random, config.minExtraMounds(), config.maxExtraMounds());

         for (int i = 0; i < extraMound; i++) {
            int x = (random.nextBoolean() ? 1 : -1) * config.radius();
            int z = (random.nextBoolean() ? 1 : -1) * config.radius();
            int extraMoundHeight = config.extraHeight() + random.nextInt(config.bonusExtraHeight());
            this.placeMound(config, level, origin.offset(x, 0, z), extraMoundHeight, state, random);
         }

         return true;
      } else {
         return false;
      }
   }

   private void placeMound(MoundFeature.Config config, WorldGenLevel level, BlockPos origin, int height, BlockState state, RandomSource random) {
      int radius = config.radius();
      boolean hanging = config.hanging();

      for (int y = 0; y < height; y++) {
         BlockPos center = hanging ? origin.below(y) : origin.above(y);

         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               double distanceSquared = (x + 0.5) * (x + 0.5) + (z + 0.5) * (z + 0.5);
               if (distanceSquared <= radius * radius) {
                  BlockPos pos = center.offset(x, 0, z);
                  BlockPos solidCheckPos = hanging ? pos.above() : pos.below();
                  if (level.getBlockState(solidCheckPos).isSolid() && level.getBlockState(pos).canBeReplaced()) {
                     level.setBlock(pos, state, 2);
                  }
               }
            }
         }
      }

      int terrainX = random.nextInt(9) - 4;
      int terrainZ = random.nextInt(9) - 4;
      BlockPos circleCenter = hanging ? origin.below(height).offset(terrainX, 0, terrainZ) : origin.above(height).offset(terrainX, 0, terrainZ);

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            double distanceSquared = (x + 0.5) * (x + 0.5) + (z + 0.5) * (z + 0.5);
            if (distanceSquared <= radius * radius) {
               BlockPos pos = circleCenter.offset(x, 0, z);
               BlockPos placementCheckPos = hanging ? pos.above() : pos.below();
               if (config.validPlacements().contains(level.getBlockState(placementCheckPos).getBlockHolder()) && level.getBlockState(pos).canBeReplaced()) {
                  level.setBlock(pos, state, 2);
               }
            }
         }
      }
   }

   public record Config(
      HolderSet<Block> validPlacements,
      int height,
      int bonusHeight,
      int extraHeight,
      int bonusExtraHeight,
      int minExtraMounds,
      int maxExtraMounds,
      int radius,
      boolean hanging
   ) implements FeatureConfiguration {
      public static final Codec<MoundFeature.Config> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_placements").forGetter(MoundFeature.Config::validPlacements),
               Codec.INT.fieldOf("height").forGetter(MoundFeature.Config::height),
               Codec.INT.fieldOf("bonus_height").forGetter(MoundFeature.Config::bonusHeight),
               Codec.INT.fieldOf("extra_height").forGetter(MoundFeature.Config::extraHeight),
               Codec.INT.fieldOf("bonus_extra_height").forGetter(MoundFeature.Config::bonusExtraHeight),
               Codec.INT.fieldOf("min_extra_mounds").forGetter(MoundFeature.Config::minExtraMounds),
               Codec.INT.fieldOf("max_extra_mounds").forGetter(MoundFeature.Config::maxExtraMounds),
               Codec.INT.fieldOf("radius").forGetter(MoundFeature.Config::radius),
               Codec.BOOL.optionalFieldOf("hanging", false).forGetter(MoundFeature.Config::hanging)
            )
            .apply(instance, MoundFeature.Config::new)
      );
   }
}
