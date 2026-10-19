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
import net.regions_unexplored.world.level.block.plant.other.SaguaroCactusBlock;
import net.regions_unexplored.world.level.block.state.properties.SaguaroCactusShape;
import net.regions_unexplored.world.level.feature.configuration.RUTreeConfiguration;

public class SaguaroCactusFeature extends Feature<RUTreeConfiguration> {
   public SaguaroCactusFeature(Codec<RUTreeConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<RUTreeConfiguration> context) {
      RUTreeConfiguration treeConfiguration = (RUTreeConfiguration)context.config();
      BlockPos pos = context.origin();
      RandomSource randomSource = context.random();
      WorldGenLevel level = context.level();
      int height_main = context.random().nextInt(treeConfiguration.sizeVariation()) + treeConfiguration.minimumSize();
      BlockPos checkCactus = pos.above(3);
      if (!level.getBlockState(checkCactus.north()).is(RUBlocks.SAGUARO_CACTUS.get())
         && !level.getBlockState(checkCactus.south()).is(RUBlocks.SAGUARO_CACTUS.get())
         && !level.getBlockState(checkCactus.east()).is(RUBlocks.SAGUARO_CACTUS.get())
         && !level.getBlockState(checkCactus.west()).is(RUBlocks.SAGUARO_CACTUS.get())
         && !level.getBlockState(checkCactus.north().east()).is(RUBlocks.SAGUARO_CACTUS.get())
         && !level.getBlockState(checkCactus.north().west()).is(RUBlocks.SAGUARO_CACTUS.get())
         && !level.getBlockState(checkCactus.south().east()).is(RUBlocks.SAGUARO_CACTUS.get())
         && !level.getBlockState(checkCactus.south().west()).is(RUBlocks.SAGUARO_CACTUS.get())) {
         int check = 0;
         MutableBlockPos checkPos = pos.mutable();

         while (check <= height_main) {
            if (!this.checkReplaceable(level, checkPos)) {
               return false;
            }

            checkPos.move(Direction.UP);
            check++;
         }

         int placeCheck = 0;
         MutableBlockPos placePos = pos.mutable();

         while (placeCheck <= height_main) {
            this.placeCactusBlock(level, placePos, randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
            if (placeCheck == 0) {
               this.placeRoot(level, placePos, randomSource, treeConfiguration);
               this.placeLimbs(level, placePos, randomSource, treeConfiguration);
            }

            if (placeCheck == height_main) {
               this.placeChanceFlowerBlock(level, placePos.above(), randomSource, treeConfiguration);
            }

            placePos.move(Direction.UP);
            placeCheck++;
         }

         return true;
      } else {
         return false;
      }
   }

   public void placeLimbs(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      int bpx1 = randomSource.nextInt(2) + 3;
      int bpx2 = randomSource.nextInt(2) + 3;
      int bpx3 = randomSource.nextInt(2) + 3;
      int bpx4 = randomSource.nextInt(2) + 3;
      BlockPos newpos = new BlockPos(pos.getX() + 1, pos.getY() + bpx1, pos.getZ());
      BlockPos newpos1 = new BlockPos(pos.getX() - 1, pos.getY() + bpx2, pos.getZ());
      BlockPos newpos2 = new BlockPos(pos.getX(), pos.getY() + bpx3, pos.getZ() - 1);
      BlockPos newpos3 = new BlockPos(pos.getX(), pos.getY() + bpx4, pos.getZ() + 1);
      this.placeCactusBlock(level, newpos, randomSource, treeConfiguration, SaguaroCactusShape.EAST_WEST);
      this.placeCactusBlock(level, newpos.east(), randomSource, treeConfiguration, SaguaroCactusShape.WEST_UP);
      this.placeCactusBlock(level, newpos.east().above(), randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
      this.placeCactusBlock(level, newpos.east().above().above(), randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
      this.placeChanceFlowerBlock(level, newpos.east().above().above().above(), randomSource, treeConfiguration);
      this.placeCactusBlock(level, newpos1, randomSource, treeConfiguration, SaguaroCactusShape.EAST_WEST);
      this.placeCactusBlock(level, newpos1.west(), randomSource, treeConfiguration, SaguaroCactusShape.EAST_UP);
      this.placeCactusBlock(level, newpos1.west().above(), randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
      this.placeCactusBlock(level, newpos1.west().above().above(), randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
      this.placeChanceFlowerBlock(level, newpos1.west().above().above().above(), randomSource, treeConfiguration);
      this.placeCactusBlock(level, newpos2, randomSource, treeConfiguration, SaguaroCactusShape.NORTH_SOUTH);
      this.placeCactusBlock(level, newpos2.north(), randomSource, treeConfiguration, SaguaroCactusShape.SOUTH_UP);
      this.placeCactusBlock(level, newpos2.north().above(), randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
      this.placeCactusBlock(level, newpos2.north().above().above(), randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
      this.placeChanceFlowerBlock(level, newpos2.north().above().above().above(), randomSource, treeConfiguration);
      this.placeCactusBlock(level, newpos3, randomSource, treeConfiguration, SaguaroCactusShape.NORTH_SOUTH);
      this.placeCactusBlock(level, newpos3.south(), randomSource, treeConfiguration, SaguaroCactusShape.NORTH_UP);
      this.placeCactusBlock(level, newpos3.south().above(), randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
      this.placeCactusBlock(level, newpos3.south().above().above(), randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
      this.placeChanceFlowerBlock(level, newpos3.south().above().above().above(), randomSource, treeConfiguration);
   }

   public boolean placeCactusBlock(
      LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration, SaguaroCactusShape shape
   ) {
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

         level.setBlock(pos, (BlockState)treeConfiguration.trunkProvider().getState(randomSource, pos).setValue(SaguaroCactusBlock.SHAPE, shape), 2);
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
      MutableBlockPos placePos = pos.mutable();

      while (i <= rd) {
         if (level.getBlockState(placePos).canBeReplaced() && level.getBlockState(placePos.above()).is(BlockTags.DIRT)) {
            level.setBlock(placePos, Blocks.HANGING_ROOTS.defaultBlockState(), 2);
            break;
         }

         if (!level.getBlockState(placePos).is(BlockTags.DIRT)
            && !level.getBlockState(placePos).is(BlockTags.REPLACEABLE_BY_TREES)
            && !level.isEmptyBlock(placePos)) {
            break;
         }

         this.placeCactusBlock(level, placePos, randomSource, treeConfiguration, SaguaroCactusShape.UP_DOWN);
         placePos.move(Direction.DOWN);
         i++;
      }
   }

   public boolean placeChanceFlowerBlock(LevelAccessor level, BlockPos pos, RandomSource randomSource, RUTreeConfiguration treeConfiguration) {
      if (level.isOutsideBuildHeight(pos)) {
         return true;
      }

      if (level.getBlockState(pos).canBeReplaced() && randomSource.nextInt(4) == 0) {
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
      return reader.isStateAtPosition(pos, SaguaroCactusFeature::isReplaceableDirtBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, SaguaroCactusFeature::isReplaceableBlock);
   }
}
