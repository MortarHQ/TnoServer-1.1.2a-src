package net.jadenxgamer.netherexp.core.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class JNEHugeFungusFeature extends Feature<JNEHugeFungusFeature.Config> {
   public JNEHugeFungusFeature(Codec<JNEHugeFungusFeature.Config> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<JNEHugeFungusFeature.Config> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      RandomSource random = context.random();
      ChunkGenerator chunkGenerator = context.chunkGenerator();
      JNEHugeFungusFeature.Config config = (JNEHugeFungusFeature.Config)context.config();
      Block baseBlock = config.validBaseState().getBlock();
      BlockPos placePos = null;
      BlockState baseBlockState = level.getBlockState(origin.below());
      if (baseBlockState.is(baseBlock)) {
         placePos = origin;
      }

      if (placePos == null) {
         return false;
      }

      int height = Mth.nextInt(random, config.minHeight(), config.maxHeight());
      if (random.nextInt(12) == 0) {
         height *= config.bonusHeight();
      }

      if (!config.planted()) {
         int maxGenDepth = chunkGenerator.getGenDepth();
         if (placePos.getY() + height + 1 >= maxGenDepth) {
            return false;
         }
      }

      boolean largeStem = !config.planted() && random.nextFloat() < 0.06F;
      level.setBlock(origin, Blocks.AIR.defaultBlockState(), 4);
      this.placeStem(level, random, config, placePos, height, largeStem);
      this.placeHat(level, random, config, placePos, height, largeStem);
      return true;
   }

   private static boolean isReplaceable(WorldGenLevel level, BlockPos pos, JNEHugeFungusFeature.Config config, boolean checkReplaceableBlocks) {
      return level.isStateAtPosition(pos, BlockStateBase::canBeReplaced) ? true : checkReplaceableBlocks && config.replaceableBlocks().test(level, pos);
   }

   private void placeStem(WorldGenLevel level, RandomSource random, JNEHugeFungusFeature.Config config, BlockPos pos, int height, boolean largeStem) {
      MutableBlockPos mPos = new MutableBlockPos();
      BlockState stemState = config.stemState();
      int stemRadius = largeStem ? 1 : 0;

      for (int x = -stemRadius; x <= stemRadius; x++) {
         for (int z = -stemRadius; z <= stemRadius; z++) {
            boolean isCorner = largeStem && Mth.abs(x) == stemRadius && Mth.abs(z) == stemRadius;

            for (int y = 0; y < height; y++) {
               mPos.setWithOffset(pos, x, y, z);
               if (isReplaceable(level, mPos, config, true)) {
                  if (config.planted()) {
                     if (!level.getBlockState(mPos.below()).isAir()) {
                        level.destroyBlock(mPos, true);
                     }

                     level.setBlock(mPos, stemState, 3);
                  } else if (isCorner) {
                     if (random.nextFloat() < 0.1F) {
                        this.setBlock(level, mPos, stemState);
                     }
                  } else {
                     this.setBlock(level, mPos, stemState);
                  }
               }
            }
         }
      }
   }

   private void placeHat(WorldGenLevel level, RandomSource random, JNEHugeFungusFeature.Config config, BlockPos pos, int height, boolean largeStem) {
      MutableBlockPos mPos = new MutableBlockPos();
      int hatHeight = Math.min(random.nextInt(1 + height / 3) + 5, height);
      int stemHeight = height - hatHeight;

      for (int y = stemHeight; y <= height; y++) {
         int hatRadius = y < height - random.nextInt(3) ? 2 : 1;
         if (hatHeight > 8 && y < stemHeight + 4) {
            hatRadius = 3;
         }

         if (largeStem) {
            hatRadius++;
         }

         for (int x = -hatRadius; x <= hatRadius; x++) {
            for (int z = -hatRadius; z <= hatRadius; z++) {
               boolean isEdgeX = x == -hatRadius || x == hatRadius;
               boolean isEdgeZ = z == -hatRadius || z == hatRadius;
               boolean isCenter = !isEdgeX && !isEdgeZ && y != height;
               boolean isCorner = isEdgeX && isEdgeZ;
               boolean lowHat = y < stemHeight + 3;
               mPos.setWithOffset(pos, x, y, z);
               if (isReplaceable(level, mPos, config, false)) {
                  if (config.planted() && !level.getBlockState(mPos.below()).isAir()) {
                     level.destroyBlock(mPos, true);
                  }

                  if (lowHat) {
                     if (!isCenter) {
                        this.placeHatDropBlock(level, random, config, mPos, config.hatState());
                     }
                  } else if (isCenter) {
                     this.placeHatBlock(level, random, config, mPos, 0.1F, 0.2F);
                  } else if (isCorner) {
                     this.placeHatBlock(level, random, config, mPos, 0.01F, 0.7F);
                  } else {
                     this.placeHatBlock(level, random, config, mPos, 5.0E-4F, 0.98F);
                  }
               }
            }
         }
      }
   }

   private void placeHatBlock(
      LevelAccessor level, RandomSource random, JNEHugeFungusFeature.Config config, MutableBlockPos pos, float probability1, float probability2
   ) {
      if (random.nextFloat() < probability1) {
         this.setBlock(level, pos, config.decorState());
      } else if (random.nextFloat() < probability2) {
         this.setBlock(level, pos, config.hatState());
         tryPlaceBeard(pos, level, config);
      }
   }

   private void placeHatDropBlock(LevelAccessor level, RandomSource random, JNEHugeFungusFeature.Config config, BlockPos pos, BlockState state) {
      if (level.getBlockState(pos.below()).is(state.getBlock())) {
         this.setBlock(level, pos, state);
      } else if (random.nextFloat() < 0.15) {
         this.setBlock(level, pos, state);
      } else if (level.getBlockState(pos.above()).is(state.getBlock())) {
         tryPlaceBeard(pos, level, config);
      }
   }

   private static void tryPlaceBeard(BlockPos pos, LevelAccessor level, JNEHugeFungusFeature.Config config) {
      if (!config.beardState().isEmpty()) {
         MutableBlockPos mutableBlockPos = pos.mutable().move(Direction.DOWN);
         if (level.isEmptyBlock(mutableBlockPos)
            && level.isEmptyBlock(mutableBlockPos.below())
            && level.getBlockState(mutableBlockPos.above()).is(config.hatState().getBlock())) {
            level.setBlock(mutableBlockPos, config.beardState().get(), 2);
         }
      }
   }

   public record Config(
      BlockState validBaseState,
      BlockState stemState,
      BlockState hatState,
      BlockState decorState,
      Optional<BlockState> beardState,
      BlockPredicate replaceableBlocks,
      boolean planted,
      int minHeight,
      int maxHeight,
      int bonusHeight
   ) implements FeatureConfiguration {
      public static final Codec<JNEHugeFungusFeature.Config> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BlockState.CODEC.fieldOf("valid_base_block").forGetter(JNEHugeFungusFeature.Config::validBaseState),
               BlockState.CODEC.fieldOf("stem_state").forGetter(JNEHugeFungusFeature.Config::stemState),
               BlockState.CODEC.fieldOf("hat_state").forGetter(JNEHugeFungusFeature.Config::hatState),
               BlockState.CODEC.fieldOf("decor_state").forGetter(JNEHugeFungusFeature.Config::decorState),
               BlockState.CODEC.optionalFieldOf("beard_state").forGetter(JNEHugeFungusFeature.Config::beardState),
               BlockPredicate.CODEC.fieldOf("replaceable_blocks").forGetter(JNEHugeFungusFeature.Config::replaceableBlocks),
               Codec.BOOL.fieldOf("planted").forGetter(JNEHugeFungusFeature.Config::planted),
               Codec.INT.fieldOf("min_height").forGetter(JNEHugeFungusFeature.Config::minHeight),
               Codec.INT.fieldOf("max_height").forGetter(JNEHugeFungusFeature.Config::maxHeight),
               Codec.INT.fieldOf("bonus_height").forGetter(JNEHugeFungusFeature.Config::bonusHeight)
            )
            .apply(instance, JNEHugeFungusFeature.Config::new)
      );
   }
}
