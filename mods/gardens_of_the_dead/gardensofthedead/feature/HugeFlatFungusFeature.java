package gardensofthedead.feature;

import com.mojang.serialization.Codec;
import gardensofthedead.feature.configuration.HugeFlatFungusConfiguration;
import gardensofthedead.registry.ModBlocks;
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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class HugeFlatFungusFeature extends Feature<HugeFlatFungusConfiguration> {
   public HugeFlatFungusFeature(Codec<HugeFlatFungusConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<HugeFlatFungusConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos pos = context.origin();
      RandomSource randomsource = context.random();
      ChunkGenerator chunkgenerator = context.chunkGenerator();
      HugeFlatFungusConfiguration configuration = (HugeFlatFungusConfiguration)context.config();
      Block validBaseBlock = configuration.validBaseState().getBlock();
      BlockState supportingBlock = level.getBlockState(pos.below());
      if (!supportingBlock.is(validBaseBlock)) {
         return false;
      }

      int height = Mth.nextInt(randomsource, 4, 9);
      if (!configuration.planted()) {
         int maxHeight = chunkgenerator.getGenDepth();
         if (pos.getY() + height + 1 >= maxHeight) {
            return false;
         }
      }

      level.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
      this.placeStem(level, randomsource, configuration, pos, height);
      this.placeHat(level, randomsource, configuration, pos, height);
      return true;
   }

   private static boolean isReplaceable(WorldGenLevel level, BlockPos pos, HugeFlatFungusConfiguration configuration, boolean isTrunk) {
      return level.isStateAtPosition(pos, BlockStateBase::canBeReplaced) ? true : isTrunk && configuration.replaceableBlocks().test(level, pos);
   }

   private void placeStem(WorldGenLevel level, RandomSource randomSource, HugeFlatFungusConfiguration configuration, BlockPos origin, int height) {
      MutableBlockPos pos = new MutableBlockPos();
      BlockState stemState = configuration.stemState();

      for (int y = 0; y < height; y++) {
         pos.setWithOffset(origin, 0, y, 0);
         if (isReplaceable(level, pos, configuration, true)) {
            level.setBlock(pos, stemState, 3);
         }
      }

      for (int x = -1; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            if (x != 0 || z != 0) {
               boolean isCorner = x != 0 && z != 0;
               if (!isCorner && randomSource.nextFloat() <= 0.75F || isCorner && randomSource.nextFloat() < 0.25F) {
                  pos.setWithOffset(origin, x, -1, z);
                  if (!level.isEmptyBlock(pos)) {
                     pos.move(Direction.UP);
                     boolean placed = this.tryPlaceStem(level, pos, configuration);
                     if (!isCorner && placed && randomSource.nextFloat() < 0.25F) {
                        pos.setWithOffset(origin, x, 1, z);
                        this.tryPlaceStem(level, pos, configuration);
                     }
                  }
               }

               if (!isCorner && randomSource.nextFloat() <= 0.75F || isCorner && randomSource.nextFloat() < 0.25F) {
                  pos.setWithOffset(origin, x, height - 1, z);
                  boolean placed = this.tryPlaceStem(level, pos, configuration);
                  if (height > 4 && !isCorner && placed && randomSource.nextFloat() < 0.25F) {
                     pos.setWithOffset(origin, x, height - 2, z);
                     this.tryPlaceStem(level, pos, configuration);
                  }
               }
            }
         }
      }
   }

   private boolean tryPlaceStem(WorldGenLevel level, MutableBlockPos pos, HugeFlatFungusConfiguration configuration) {
      if (isReplaceable(level, pos, configuration, true)) {
         if (configuration.planted() && !level.getBlockState(pos.below()).isAir()) {
            level.destroyBlock(pos, true);
         }

         level.setBlock(pos, configuration.stemState(), 3);
         return true;
      } else {
         return false;
      }
   }

   private void placeHat(WorldGenLevel level, RandomSource randomSource, HugeFlatFungusConfiguration configuration, BlockPos origin, int height) {
      float wobbliness = 0.1F + randomSource.nextFloat() * 0.15F;
      float wobbleAngle = randomSource.nextFloat() * (float) Math.PI * 2.0F;
      float size = (6.0F + randomSource.nextFloat() * 3.0F) / 2.0F;
      int maxSize = Mth.ceil(size * 1.25);
      MutableBlockPos pos = new MutableBlockPos();
      boolean placeGlowingSpores = randomSource.nextFloat() < 0.4F;

      for (int x = -maxSize; x <= maxSize; x++) {
         for (int z = -maxSize; z <= maxSize; z++) {
            float angle = x == 0 && z == 0 ? 0.0F : (float)Mth.atan2(x, z);
            float wobble = 1.0F + wobbliness * Mth.cos(angle - wobbleAngle);
            float r = x * x + z * z;
            if (Mth.square(wobble) * r <= Mth.square(size)) {
               boolean isEdge = Mth.square(wobble) * r > Mth.square(size - 1.5);
               if (isEdge) {
                  pos.setWithOffset(origin, x, height - 1, z);
               } else {
                  pos.setWithOffset(origin, x, height, z);
               }

               if (isReplaceable(level, pos, configuration, false)) {
                  if (configuration.planted() && !level.getBlockState(pos.below()).isAir()) {
                     level.destroyBlock(pos, true);
                  }

                  this.placeHatBlock(level, randomSource, configuration, pos, isEdge, placeGlowingSpores);
               }
            }
         }
      }
   }

   private void placeHatBlock(
      WorldGenLevel level,
      RandomSource randomSource,
      HugeFlatFungusConfiguration configuration,
      MutableBlockPos pos,
      boolean isEdge,
      boolean placeGlowingSpores
   ) {
      this.setBlock(level, pos, configuration.hatState());
      float shroomlightChance = isEdge ? 0.0F : 0.05F;
      float hangingVineChance = isEdge ? 0.3F : 0.75F;
      float sproutChance = 0.2F;
      if (randomSource.nextFloat() < shroomlightChance) {
         if (level.isEmptyBlock(pos.below())) {
            this.setBlock(level, pos.below(), Blocks.SHROOMLIGHT.defaultBlockState());
         }
      } else if (randomSource.nextFloat() < hangingVineChance) {
         tryPlaceHangingSoulSpore(pos, level, randomSource, isEdge, placeGlowingSpores);
      }

      if (randomSource.nextFloat() < sproutChance && level.isEmptyBlock(pos.above())) {
         this.setBlock(level, pos.above(), ((Block)ModBlocks.SOULBLIGHT_SPROUTS.get()).defaultBlockState());
      }
   }

   private static void tryPlaceHangingSoulSpore(BlockPos pos, LevelAccessor level, RandomSource randomSource, boolean isEdge, boolean placeGlowingSpores) {
      MutableBlockPos below = pos.mutable().move(Direction.DOWN);
      if (level.isEmptyBlock(below)) {
         int length = Mth.nextInt(randomSource, 2, 3) + (isEdge ? 0 : 1);
         SoulSporeColumnFeature.placeSoulSporeColumn(level, randomSource, below, length, Direction.DOWN, isEdge && placeGlowingSpores ? 1.0F : 0.0F);
      }
   }
}
