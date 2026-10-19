package net.regions_unexplored.world.level.feature.tree;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;
import net.regions_unexplored.world.level.feature.configuration.RUTreeConfiguration;

public class IceSpireFeature extends Feature<RUTreeConfiguration> {
   public IceSpireFeature(Codec<RUTreeConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<RUTreeConfiguration> context) {
      RUTreeConfiguration treeConfiguration = (RUTreeConfiguration)context.config();
      BlockPos pos = context.origin();
      RandomSource randomSource = context.random();
      WorldGenLevel level = context.level();
      int height_main = context.random().nextInt(treeConfiguration.sizeVariation()) + treeConfiguration.minimumSize();
      int height_2n = height_main > 10 ? height_main / 2 + randomSource.nextInt(3) : height_main / 2;
      int height_2s = height_main > 10 ? height_main / 2 + randomSource.nextInt(3) : height_main / 2;
      int height_2e = height_main > 10 ? height_main / 2 + randomSource.nextInt(3) : height_main / 2;
      int height_2w = height_main > 10 ? height_main / 2 + randomSource.nextInt(3) : height_main / 2;
      int height_ne = randomSource.nextInt(5);
      int height_nw = randomSource.nextInt(5);
      int height_se = randomSource.nextInt(5);
      int height_sw = randomSource.nextInt(5);
      int check = 0;
      MutableBlockPos checkPos = pos.mutable();

      while (check <= height_main) {
         if (!this.checkReplaceable(level, checkPos)) {
            return false;
         }

         checkPos.move(Direction.UP);
         check++;
      }

      if (randomSource.nextInt(2) == 0) {
         this.generateSmallSpire(level, pos, randomSource, treeConfiguration);
         return true;
      }

      int placeCheck = 0;
      MutableBlockPos placePos = pos.mutable();

      while (placeCheck <= height_main) {
         this.placeLog(level, placePos, randomSource, treeConfiguration);
         if (placeCheck <= height_ne) {
            if (placeCheck == height_ne) {
               this.placeLeavesBlob(level, placePos.north().east(), randomSource, treeConfiguration);
            }

            this.placeLog(level, placePos.north().east(), randomSource, treeConfiguration);
         }

         if (placeCheck <= height_nw) {
            if (placeCheck == height_nw) {
               this.placeLeavesBlob(level, placePos.north().west(), randomSource, treeConfiguration);
            }

            this.placeLog(level, placePos.north().west(), randomSource, treeConfiguration);
         }

         if (placeCheck <= height_se) {
            if (placeCheck == height_se) {
               this.placeLeavesBlob(level, placePos.south().east(), randomSource, treeConfiguration);
            }

            this.placeLog(level, placePos.south().east(), randomSource, treeConfiguration);
         }

         if (placeCheck <= height_sw) {
            if (placeCheck == height_sw) {
               this.placeLeavesBlob(level, placePos.south().west(), randomSource, treeConfiguration);
            }

            this.placeLog(level, placePos.south().west(), randomSource, treeConfiguration);
         }

         if (placeCheck <= height_2n) {
            if (placeCheck == height_2n) {
               this.placeLeavesBlob(level, placePos.north(), randomSource, treeConfiguration);
            }

            this.placeLog(level, placePos.north(), randomSource, treeConfiguration);
         }

         if (placeCheck <= height_2s) {
            if (placeCheck == height_2s) {
               this.placeLeavesBlob(level, placePos.south(), randomSource, treeConfiguration);
            }

            this.placeLog(level, placePos.south(), randomSource, treeConfiguration);
         }

         if (placeCheck <= height_2e) {
            if (placeCheck == height_2e) {
               this.placeLeavesBlob(level, placePos.east(), randomSource, treeConfiguration);
            }

            this.placeLog(level, placePos.east(), randomSource, treeConfiguration);
         }

         if (placeCheck <= height_2w) {
            if (placeCheck == height_2w) {
               this.placeLeavesBlob(level, placePos.west(), randomSource, treeConfiguration);
            }

            this.placeLog(level, placePos.west(), randomSource, treeConfiguration);
         }

         if (placeCheck == 0) {
            this.placeRoot(level, placePos.below(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.below().north(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.below().north().east(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.below().north().west(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.below().south(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.below().south().east(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.below().south().west(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.below().east(), randomSource, treeConfiguration);
            this.placeRoot(level, placePos.below().west(), randomSource, treeConfiguration);
            if (randomSource.nextInt(2) == 0) {
               this.placeLeavesBlob(level, placePos.north().north(), randomSource, treeConfiguration);
               this.placeRoot(level, placePos.north().north(), randomSource, treeConfiguration);
            }

            if (randomSource.nextInt(2) == 0) {
               this.placeLeavesBlob(level, placePos.south().south(), randomSource, treeConfiguration);
               this.placeRoot(level, placePos.south().south(), randomSource, treeConfiguration);
            }

            if (randomSource.nextInt(2) == 0) {
               this.placeLeavesBlob(level, placePos.east().east(), randomSource, treeConfiguration);
               this.placeRoot(level, placePos.east().east(), randomSource, treeConfiguration);
            }

            if (randomSource.nextInt(2) == 0) {
               this.placeLeavesBlob(level, placePos.west().west(), randomSource, treeConfiguration);
               this.placeRoot(level, placePos.west().west(), randomSource, treeConfiguration);
            }
         }

         if (placeCheck == height_main) {
            this.placeLeavesBlob(level, placePos, randomSource, treeConfiguration);
         }

         placePos.move(Direction.UP);
         placeCheck++;
      }

      return true;
   }

   public void generateSmallSpire(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      MutableBlockPos placePos = pos.mutable();
      int size = randomSource.nextInt(3) + 5;

      for (int i = 0; i <= size; i++) {
         if (i == 0) {
            this.placeRoot(level, pos, randomSource, treeConfiguration);
            this.placeRoot(level, pos.north(), randomSource, treeConfiguration);
            this.placeRoot(level, pos.south(), randomSource, treeConfiguration);
            this.placeRoot(level, pos.east(), randomSource, treeConfiguration);
            this.placeRoot(level, pos.west(), randomSource, treeConfiguration);
         }

         this.placeLog(level, placePos, randomSource, treeConfiguration);
         if (i == size) {
            this.placeLeavesBlob(level, placePos, randomSource, treeConfiguration);
         }

         placePos.move(Direction.UP);
      }

      this.placeLog(level, pos.north(), randomSource, treeConfiguration);
      this.placeLog(level, pos.north().above(), randomSource, treeConfiguration);
      this.placeLeavesBlob(level, pos.north().above(), randomSource, treeConfiguration);
      this.placeLog(level, pos.south(), randomSource, treeConfiguration);
      this.placeLog(level, pos.south().above(), randomSource, treeConfiguration);
      this.placeLeavesBlob(level, pos.south().above(), randomSource, treeConfiguration);
      this.placeLog(level, pos.east(), randomSource, treeConfiguration);
      this.placeLog(level, pos.east().above(), randomSource, treeConfiguration);
      this.placeLeavesBlob(level, pos.east().above(), randomSource, treeConfiguration);
      this.placeLog(level, pos.west(), randomSource, treeConfiguration);
      this.placeLog(level, pos.west().above(), randomSource, treeConfiguration);
      this.placeLeavesBlob(level, pos.west().above(), randomSource, treeConfiguration);
   }

   public boolean placeLog(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      new Random();
      if (level.isOutsideBuildHeight(pos)) {
         return true;
      }

      if (level.getBlockState(pos).is(RUBlocks.PEAT_GRASS_BLOCK.get())) {
         level.setBlock(pos, RUBlocks.PEAT_DIRT.get().defaultBlockState(), 2);
      } else if (level.getBlockState(pos).is(RUBlocks.SILT_GRASS_BLOCK.get())) {
         level.setBlock(pos, RUBlocks.SILT_DIRT.get().defaultBlockState(), 2);
      } else if (level.getBlockState(pos).is(RUBlocks.ALPHA_GRASS_BLOCK.get())) {
         level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
      } else if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK)) {
         level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
      } else {
         if (!isReplaceable(level, pos)) {
            return true;
         }

         level.setBlock(pos, treeConfiguration.trunkProvider().getState(randomSource, pos), 2);
      }

      if (level.getBlockState(pos.below()).is(RUBlocks.PEAT_GRASS_BLOCK.get())) {
         level.setBlock(pos.below(), RUBlocks.PEAT_DIRT.get().defaultBlockState(), 2);
      } else if (level.getBlockState(pos.below()).is(RUBlocks.SILT_GRASS_BLOCK.get())) {
         level.setBlock(pos.below(), RUBlocks.SILT_DIRT.get().defaultBlockState(), 2);
      } else if (level.getBlockState(pos.below()).is(RUBlocks.ALPHA_GRASS_BLOCK.get())) {
         level.setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 2);
      } else if (level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK)) {
         level.setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 2);
      }

      return true;
   }

   public void placeRoot(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      Random random = new Random();
      int rd = random.nextInt(2) + 4;
      int i = 0;

      for (MutableBlockPos placePos = pos.mutable();
         i <= rd && (!level.getBlockState(placePos).canBeReplaced() || !level.getBlockState(placePos.above()).is(BlockTags.DIRT));
         i++
      ) {
         this.placeLog(level, placePos, randomSource, treeConfiguration);
         placePos.move(Direction.DOWN);
      }
   }

   public boolean placeLeavesBlob(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      Random random = new Random();
      int n = random.nextInt(3);
      this.placeLeavesBlock(level, pos.above(), randomSource, treeConfiguration);
      this.placeLeavesBlock(level, pos.above(2), randomSource, treeConfiguration);
      if (randomSource.nextBoolean()) {
         this.placeLeavesBlock(level, pos.above(3), randomSource, treeConfiguration);
      }

      return true;
   }

   public boolean placeLeavesBlock(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      new Random();
      if (level.isOutsideBuildHeight(pos)) {
         return true;
      }

      if (level.getBlockState(pos).canBeReplaced()) {
         level.setBlock(pos, treeConfiguration.foliageProvider().getState(randomSource, pos), 2);
      }

      return true;
   }

   public boolean checkReplaceable(LevelAccessor level, BlockPos pos) {
      return level.isOutsideBuildHeight(pos) ? false : isReplaceable(level, pos);
   }

   public static boolean isReplaceableDirtBlock(BlockState state) {
      return state.is(RUBlockTags.TREE_GRASS_REPLACEABLES);
   }

   public static boolean isReplaceableDirt(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, IceSpireFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, IceSpireFeature::isReplaceableBlock);
   }
}
