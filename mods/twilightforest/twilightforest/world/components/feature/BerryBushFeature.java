package twilightforest.world.components.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import twilightforest.block.SnowLoggable;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.util.TFMathUtil;
import twilightforest.util.WorldUtil;
import twilightforest.world.components.feature.config.BerryBushConfig;

public class BerryBushFeature extends Feature<BerryBushConfig> {
   private static final float DEFAULT_RIPE_PROBABILITY = 0.2F;

   public BerryBushFeature(Codec<BerryBushConfig> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<BerryBushConfig> context) {
      WorldGenLevel level = context.level();
      BlockPos pos = context.origin();
      BlockState stateToPlace = ((BerryBushConfig)context.config()).bushState();
      RandomSource random = context.random();
      TagKey<Block> generatesOn = ((BerryBushConfig)context.config()).placesOn();
      if (!level.getBlockState(pos.below()).is(generatesOn)) {
         return false;
      }

      boolean isInSnowyBiome = ((BerryBushConfig)context.config()).canBeSnowy() && ((Biome)level.getBiome(pos).value()).shouldSnow(level, pos);

      return switch (this.chooseSize(random)) {
         case SMALL -> this.generateSmallNode(level, pos, stateToPlace, generatesOn, random, isInSnowyBiome);
         case MEDIUM -> this.generateMediumNode(level, pos, stateToPlace, generatesOn, random, isInSnowyBiome);
         case LARGE -> this.generateLargeNode(level, pos, stateToPlace, generatesOn, random, isInSnowyBiome);
         default -> this.setBush(level, pos, stateToPlace, generatesOn, random.nextInt(4), isInSnowyBiome);
      };
   }

   protected boolean generateLargeNode(
      WorldGenLevel level, BlockPos pos, BlockState state, TagKey<Block> generatesOn, RandomSource random, boolean isInSnowyBiome
   ) {
      boolean placed = false;

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            placed |= this.setBush(level, pos.offset(dx, -2, dz), state, generatesOn, random, isInSnowyBiome);
         }
      }

      for (int dx = -2; dx <= 2; dx++) {
         for (int dy = -1; dy <= 0; dy++) {
            for (int dz = -2; dz <= 2; dz++) {
               if (TFMathUtil.taxicabGeometryDistance(dx, dz) < 4) {
                  placed |= this.setBush(level, pos.offset(dx, dy, dz), state, generatesOn, random, isInSnowyBiome);
               }
            }
         }
      }

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            placed |= this.setBush(level, pos.offset(dx, 1, dz), state, generatesOn, random, isInSnowyBiome);
         }
      }

      return placed;
   }

   protected boolean generateMediumNode(
      WorldGenLevel level, BlockPos pos, BlockState state, TagKey<Block> generatesOn, RandomSource random, boolean isInSnowyBiome
   ) {
      boolean placed = false;

      for (int dy = -1; dy <= 2; dy++) {
         int maxTaxicabDistance = Math.min(2 - dy, 2);

         for (int dx = -maxTaxicabDistance; dx <= maxTaxicabDistance; dx++) {
            for (int dz = -maxTaxicabDistance; dz <= maxTaxicabDistance; dz++) {
               if (TFMathUtil.taxicabGeometryDistance(dx, dz) < 2 * maxTaxicabDistance || random.nextBoolean()) {
                  placed |= this.setBush(level, pos.offset(dx, dy, dz), state, generatesOn, random, isInSnowyBiome);
               }
            }
         }
      }

      return placed;
   }

   protected boolean generateSmallNode(
      WorldGenLevel level, BlockPos pos, BlockState state, TagKey<Block> generatesOn, RandomSource random, boolean isInSnowyBiome
   ) {
      boolean placed = this.setBush(level, pos.offset(0, 0, 0), state, generatesOn, random, isInSnowyBiome);

      for (int dx = -1; dx <= 1; dx++) {
         for (int dy = -1; dy <= 0; dy++) {
            for (int dz = -1; dz <= 1; dz++) {
               if (TFMathUtil.taxicabGeometryDistance(dx, dz) == 1 && random.nextBoolean()) {
                  placed |= this.setBush(level, pos.offset(dx, dy, dz), state, generatesOn, random.nextInt(4), isInSnowyBiome);
               }
            }
         }
      }

      return placed;
   }

   protected boolean setBush(WorldGenLevel level, BlockPos pos, BlockState state, TagKey<Block> generatesOn, RandomSource random, boolean isInSnowyBiome) {
      return this.setBush(level, pos, state, generatesOn, random.nextFloat() < 0.2F ? 3 : 2, isInSnowyBiome);
   }

   protected boolean setBush(WorldGenLevel level, BlockPos pos, BlockState state, TagKey<Block> generatesOn, int age, boolean isInSnowyBiome) {
      BlockState stateToReplace = level.getBlockState(pos);
      if (stateToReplace.is(BlockTagGenerator.TF_BERRY_BUSHES_REPLACE)
         && !stateToReplace.is(BlockTags.FEATURES_CANNOT_REPLACE)
         && stateToReplace.getFluidState().isEmpty()) {
         if (!level.getBlockState(pos.below()).is(generatesOn) && age < 2) {
            return false;
         }

         BlockState stateToPlace = (BlockState)state.trySetValue(BlockStateProperties.AGE_3, age);
         if (isInSnowyBiome && !level.getBlockState(pos.below()).is(state.getBlock())) {
            stateToPlace = (BlockState)stateToPlace.trySetValue(SnowLoggable.SNOW_LAYERS, 1);
         }

         level.setBlock(pos, stateToPlace, 3);
         this.markAboveForPostProcessing(level, pos);
         if (isInSnowyBiome && age >= 2) {
            level.setBlock(pos.above(), Blocks.SNOW.defaultBlockState(), 3);
         }

         return true;
      } else {
         return false;
      }
   }

   protected BerryBushFeature.BushNodeSizes chooseSize(RandomSource random) {
      List<Pair<BerryBushFeature.BushNodeSizes, Float>> weights = List.of(
         Pair.of(BerryBushFeature.BushNodeSizes.LARGE, 1.0F),
         Pair.of(BerryBushFeature.BushNodeSizes.MEDIUM, 2.0F),
         Pair.of(BerryBushFeature.BushNodeSizes.SMALL, 4.0F),
         Pair.of(BerryBushFeature.BushNodeSizes.TINY, 3.0F)
      );
      return WorldUtil.getRandomElementWithWeights(weights, random);
   }

   protected enum BushNodeSizes {
      TINY,
      SMALL,
      MEDIUM,
      LARGE;
   }
}
