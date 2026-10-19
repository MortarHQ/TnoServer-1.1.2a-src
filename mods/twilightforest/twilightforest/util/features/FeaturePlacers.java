package twilightforest.util.features;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.init.TFBlocks;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.RootPlacer;
import twilightforest.util.iterators.VoxelBresenhamIterator;

public final class FeaturePlacers {
   public static final BiFunction<LevelSimulatedReader, BlockPos, Boolean> VALID_TREE_POS = TreeFeature::validTreePos;

   public static <T extends Mob> void placeEntity(EntityType<T> entityType, BlockPos pos, ServerLevelAccessor levelAccessor) {
      Mob mob = (Mob)entityType.create(levelAccessor.getLevel());
      if (mob != null) {
         mob.setPersistenceRequired();
         mob.moveTo(pos, 0.0F, 0.0F);
         EventHooks.finalizeMobSpawn(mob, levelAccessor, levelAccessor.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
         if (mob instanceof EnforcedHomePoint home) {
            home.setRestrictionPoint(GlobalPos.of(levelAccessor.getLevel().dimension(), pos));
         }

         levelAccessor.addFreshEntityWithPassengers(mob);
         levelAccessor.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
      }
   }

   public static void drawBresenhamBranch(
      LevelAccessor world, BiConsumer<BlockPos, BlockState> trunkPlacer, RandomSource random, BlockPos start, BlockPos end, BlockStateProvider config
   ) {
      for (BlockPos pixel : new VoxelBresenhamIterator(start, end)) {
         placeIfValidTreePos(world, trunkPlacer, random, pixel, config);
      }
   }

   public static void buildRoot(LevelAccessor world, RootPlacer placer, RandomSource rand, BlockPos start, double offset, int b, BlockStateProvider config) {
      BlockPos dest = FeatureLogic.translate(start.below(b + 2), 5.0, 0.3 * b + offset, 0.8);

      for (BlockPos coord : new VoxelBresenhamIterator(start.below(), dest)) {
         if (!placeIfValidRootPos(world, placer, rand, coord, config)) {
            return;
         }
      }
   }

   public static void drawBresenhamTree(
      LevelSimulatedReader world,
      BiConsumer<BlockPos, BlockState> placer,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      BlockPos from,
      BlockPos to,
      BlockStateProvider config,
      RandomSource random
   ) {
      for (BlockPos pixel : new VoxelBresenhamIterator(from, to)) {
         placeProvidedBlock(world, placer, predicate, pixel, config, random);
      }
   }

   public static void placeProvidedBlock(
      LevelSimulatedReader world,
      BiConsumer<BlockPos, BlockState> worldPlacer,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      BlockPos pos,
      BlockStateProvider config,
      RandomSource random
   ) {
      if (predicate.apply(world, pos)) {
         worldPlacer.accept(pos, config.getState(random, pos));
      }
   }

   public static void placeLeaf(
      LevelSimulatedReader world,
      FoliageSetter setter,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      BlockPos pos,
      BlockStateProvider config,
      RandomSource random
   ) {
      if (predicate.apply(world, pos)) {
         setter.set(pos, config.getState(random, pos));
      }
   }

   public static void placeCircleOdd(
      LevelSimulatedReader world,
      BiConsumer<BlockPos, BlockState> placer,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      RandomSource random,
      BlockPos centerPos,
      float radius,
      BlockStateProvider config
   ) {
      placeCircleOdd(world, placer, predicate, random, centerPos, radius, config, false);
   }

   public static void placeCircleOdd(
      LevelSimulatedReader world,
      BiConsumer<BlockPos, BlockState> placer,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      RandomSource random,
      BlockPos centerPos,
      float radius,
      BlockStateProvider config,
      boolean useLegacyDistance
   ) {
      placeProvidedBlock(world, placer, predicate, centerPos, config, random);

      for (int x = 0; x <= radius; x++) {
         for (int z = 1; z <= radius; z++) {
            if (isWithinCircle(x, z, radius, useLegacyDistance)) {
               placeProvidedBlock(world, placer, predicate, centerPos.offset(x, 0, z), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(-x, 0, -z), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(-z, 0, x), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(z, 0, -x), config, random);
            }
         }
      }
   }

   public static void placeCircleEven(
      LevelSimulatedReader world,
      BiConsumer<BlockPos, BlockState> placer,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      RandomSource random,
      BlockPos centerPos,
      float radius,
      BlockStateProvider config
   ) {
      placeCircleEven(world, placer, predicate, random, centerPos, radius, config, false);
   }

   public static void placeCircleEven(
      LevelSimulatedReader world,
      BiConsumer<BlockPos, BlockState> placer,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      RandomSource random,
      BlockPos centerPos,
      float radius,
      BlockStateProvider config,
      boolean useLegacyDistance
   ) {
      placeProvidedBlock(world, placer, predicate, centerPos, config, random);

      for (int x = 0; x <= radius; x++) {
         for (int z = 0; z <= radius; z++) {
            if (isWithinCircle(x, z, radius, useLegacyDistance)) {
               placeProvidedBlock(world, placer, predicate, centerPos.offset(1 + x, 0, 1 + z), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(-x, 0, -z), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(-x, 0, 1 + z), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(1 + x, 0, -z), config, random);
            }
         }
      }
   }

   public static boolean isWithinCircle(int x, int z, float radius, boolean useLegacyDistance) {
      if (!useLegacyDistance) {
         return x * x + z * z <= radius * radius;
      }

      int absX = Math.abs(x);
      int absZ = Math.abs(z);
      int legacyDistance = absX == 3 && absZ == 3 ? 6 : (int)(Math.max(absX, absZ) + Math.min(absX, absZ) * 0.5F);
      return legacyDistance <= radius;
   }

   public static void placeSpheroid(
      LevelSimulatedReader world,
      FoliageSetter setter,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      RandomSource random,
      BlockPos centerPos,
      float xzRadius,
      float yRadius,
      float verticalBias,
      BlockStateProvider config
   ) {
      float xzRadiusSquared = xzRadius * xzRadius;
      float yRadiusSquared = yRadius * yRadius;
      float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
      placeLeaf(world, setter, predicate, centerPos, config, random);

      for (int y = 0; y <= yRadius; y++) {
         if (!(y > yRadius)) {
            placeLeaf(world, setter, predicate, centerPos.offset(0, y, 0), config, random);
            placeLeaf(world, setter, predicate, centerPos.offset(0, -y, 0), config, random);
         }
      }

      for (int x = 0; x <= xzRadius; x++) {
         for (int z = 1; z <= xzRadius; z++) {
            if (!(x * x + z * z > xzRadiusSquared)) {
               placeLeaf(world, setter, predicate, centerPos.offset(x, 0, z), config, random);
               placeLeaf(world, setter, predicate, centerPos.offset(-x, 0, -z), config, random);
               placeLeaf(world, setter, predicate, centerPos.offset(-z, 0, x), config, random);
               placeLeaf(world, setter, predicate, centerPos.offset(z, 0, -x), config, random);

               for (int y = 1; y <= yRadius; y++) {
                  float xzSquare = (x * x + z * z) * yRadiusSquared;
                  if (xzSquare + (y - verticalBias) * (y - verticalBias) * xzRadiusSquared <= superRadiusSquared) {
                     placeLeaf(world, setter, predicate, centerPos.offset(x, y, z), config, random);
                     placeLeaf(world, setter, predicate, centerPos.offset(-x, y, -z), config, random);
                     placeLeaf(world, setter, predicate, centerPos.offset(-z, y, x), config, random);
                     placeLeaf(world, setter, predicate, centerPos.offset(z, y, -x), config, random);
                  }

                  if (xzSquare + (y + verticalBias) * (y + verticalBias) * xzRadiusSquared <= superRadiusSquared) {
                     placeLeaf(world, setter, predicate, centerPos.offset(x, -y, z), config, random);
                     placeLeaf(world, setter, predicate, centerPos.offset(-x, -y, -z), config, random);
                     placeLeaf(world, setter, predicate, centerPos.offset(-z, -y, x), config, random);
                     placeLeaf(world, setter, predicate, centerPos.offset(z, -y, -x), config, random);
                  }
               }
            }
         }
      }
   }

   public static void placeSpheroid(
      LevelSimulatedReader world,
      BiConsumer<BlockPos, BlockState> placer,
      BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate,
      RandomSource random,
      BlockPos centerPos,
      float xzRadius,
      float yRadius,
      BlockStateProvider config
   ) {
      float xzRadiusSquared = xzRadius * xzRadius;
      float yRadiusSquared = yRadius * yRadius;
      float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
      placeProvidedBlock(world, placer, predicate, centerPos, config, random);

      for (int y = 0; y <= yRadius; y++) {
         if (!(y > yRadius)) {
            placeProvidedBlock(world, placer, predicate, centerPos.offset(0, y, 0), config, random);
            placeProvidedBlock(world, placer, predicate, centerPos.offset(0, -y, 0), config, random);
         }
      }

      for (int x = 0; x <= xzRadius; x++) {
         for (int z = 1; z <= xzRadius; z++) {
            if (!(x * x + z * z > xzRadiusSquared)) {
               placeProvidedBlock(world, placer, predicate, centerPos.offset(x, 0, z), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(-x, 0, -z), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(-z, 0, x), config, random);
               placeProvidedBlock(world, placer, predicate, centerPos.offset(z, 0, -x), config, random);

               for (int y = 1; y <= yRadius; y++) {
                  float ySquare = y * y * xzRadiusSquared;
                  if ((x * x + z * z) * yRadiusSquared + ySquare <= superRadiusSquared) {
                     if (((x + 1) * (x + 1) + z * z) * yRadiusSquared + ySquare > superRadiusSquared
                        && (x * x + (z + 1) * (z + 1)) * yRadiusSquared + ySquare > superRadiusSquared) {
                        if (random.nextInt(3) != 0) {
                           placeProvidedBlock(world, placer, predicate, centerPos.offset(x, y, z), config, random);
                        }

                        if (random.nextInt(3) != 0) {
                           placeProvidedBlock(world, placer, predicate, centerPos.offset(-x, y, -z), config, random);
                        }

                        if (random.nextInt(3) != 0) {
                           placeProvidedBlock(world, placer, predicate, centerPos.offset(-z, y, x), config, random);
                        }

                        if (random.nextInt(3) != 0) {
                           placeProvidedBlock(world, placer, predicate, centerPos.offset(z, y, -x), config, random);
                        }

                        if (random.nextInt(3) != 0) {
                           placeProvidedBlock(world, placer, predicate, centerPos.offset(x, -y, z), config, random);
                        }

                        if (random.nextInt(3) != 0) {
                           placeProvidedBlock(world, placer, predicate, centerPos.offset(-x, -y, -z), config, random);
                        }

                        if (random.nextInt(3) != 0) {
                           placeProvidedBlock(world, placer, predicate, centerPos.offset(-z, -y, x), config, random);
                        }

                        if (random.nextInt(3) != 0) {
                           placeProvidedBlock(world, placer, predicate, centerPos.offset(z, -y, -x), config, random);
                        }
                     } else {
                        placeProvidedBlock(world, placer, predicate, centerPos.offset(x, y, z), config, random);
                        placeProvidedBlock(world, placer, predicate, centerPos.offset(-x, y, -z), config, random);
                        placeProvidedBlock(world, placer, predicate, centerPos.offset(-z, y, x), config, random);
                        placeProvidedBlock(world, placer, predicate, centerPos.offset(z, y, -x), config, random);
                        placeProvidedBlock(world, placer, predicate, centerPos.offset(x, -y, z), config, random);
                        placeProvidedBlock(world, placer, predicate, centerPos.offset(-x, -y, -z), config, random);
                        placeProvidedBlock(world, placer, predicate, centerPos.offset(-z, -y, x), config, random);
                        placeProvidedBlock(world, placer, predicate, centerPos.offset(z, -y, -x), config, random);
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean placeIfValidTreePos(
      LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> placer, RandomSource random, BlockPos pos, BlockStateProvider config
   ) {
      if (validTreePos(world, pos)) {
         placer.accept(pos, config.getState(random, pos));
         return true;
      } else {
         return false;
      }
   }

   public static boolean placeIfValidRootPos(LevelSimulatedReader world, RootPlacer placer, RandomSource random, BlockPos pos, BlockStateProvider config) {
      if (!FeatureUtil.anyBelowMatch(pos, placer.getRootPenetrability() - 1, blockPos -> !FeatureLogic.canRootGrowIn(world, blockPos))) {
         placer.getPlacer().accept(pos, config.getState(random, pos));
         return true;
      } else {
         return false;
      }
   }

   public static boolean validTreePos(LevelSimulatedReader world, BlockPos pos) {
      return (R)(TreeFeature.validTreePos(world, pos) || world.isStateAtPosition(pos, state -> state.is(BlockTags.FLOWERS)));
   }

   public static void addFirefly(LevelAccessor world, BlockPos pos, int height, double angle) {
      int iAngle = (int)(angle * 4.0);
      if (iAngle == 0) {
         setIfEmpty(
            world, pos.offset(1, height, 0), (BlockState)((Block)TFBlocks.FIREFLY.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST)
         );
      } else if (iAngle == 1) {
         setIfEmpty(
            world, pos.offset(-1, height, 0), (BlockState)((Block)TFBlocks.FIREFLY.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST)
         );
      } else if (iAngle == 2) {
         setIfEmpty(
            world, pos.offset(0, height, 1), (BlockState)((Block)TFBlocks.FIREFLY.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH)
         );
      } else if (iAngle == 3) {
         setIfEmpty(
            world,
            pos.offset(0, height, -1),
            (BlockState)((Block)TFBlocks.FIREFLY.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH)
         );
      }
   }

   private static void setIfEmpty(LevelAccessor world, BlockPos pos, BlockState state) {
      if (world.isEmptyBlock(pos)) {
         world.setBlock(pos, state, 3);
      }
   }

   public static BlockState transferAllStateKeys(BlockState stateIn, Block blockOut) {
      return transferAllStateKeys(stateIn, blockOut.defaultBlockState());
   }

   public static BlockState transferAllStateKeys(BlockState stateIn, BlockState stateOut) {
      for (Property<?> property : stateOut.getProperties()) {
         stateOut = transferStateKey(stateIn, stateOut, property);
      }

      return stateOut;
   }

   public static <T extends Comparable<T>> BlockState transferStateKey(BlockState stateIn, BlockState stateOut, Property<T> property) {
      return stateIn.hasProperty(property) && stateOut.hasProperty(property) ? (BlockState)stateOut.setValue(property, stateIn.getValue(property)) : stateOut;
   }

   public static void traceRoot(
      LevelSimulatedReader worldReader, RootPlacer worldPlacer, RandomSource random, BlockStateProvider dirtRoot, Iterable<BlockPos> posTracer
   ) {
      for (BlockPos rootPos : posTracer) {
         if (FeatureUtil.anyBelowMatch(
            rootPos, worldPlacer.getRootPenetrability() - 1, blockPos -> worldReader.isStateAtPosition(blockPos, FeatureLogic.ROOT_SHOULD_SKIP)
         )) {
            return;
         }

         if (!placeIfValidRootPos(worldReader, worldPlacer, random, rootPos, dirtRoot)) {
            return;
         }
      }
   }

   public static void traceExposedRoot(
      LevelSimulatedReader worldReader,
      RootPlacer worldPlacer,
      RandomSource random,
      BlockStateProvider exposedRoot,
      BlockStateProvider dirtRoot,
      Iterable<BlockPos> posTracer
   ) {
      for (BlockPos exposedPos : posTracer) {
         if (!worldReader.isStateAtPosition(exposedPos, FeatureLogic.ROOT_SHOULD_SKIP)) {
            if (!FeatureLogic.hasEmptyNeighborExceptBelow(worldReader, exposedPos)) {
               if (placeIfValidRootPos(worldReader, worldPlacer, random, exposedPos, dirtRoot)) {
                  traceRoot(worldReader, worldPlacer, random, dirtRoot, posTracer);
               }

               return;
            }

            if (FeatureUtil.anyBelowMatch(
               exposedPos,
               worldPlacer.getRootPenetrability() - 1,
               blockPos -> worldReader.isStateAtPosition(
                  blockPos, state -> !FeatureLogic.worldGenReplaceable(state) && state != exposedRoot.getState(random, exposedPos)
               )
            )) {
               return;
            }

            worldPlacer.getPlacer().accept(exposedPos, exposedRoot.getState(random, exposedPos));
         }
      }
   }

   @Deprecated
   public static void replaceBlocksDome(
      WorldGenLevel level, BlockPos centerPos, float radius, float squish, BoundingBox chunkBox, BoundingBox structureBox, Block target, BlockState replacement
   ) {
      float radiusSquared = radius * radius;
      BoundingBox commonRegion = BoundingBoxUtils.getIntersectionOfSBBs(chunkBox, structureBox);
      if (commonRegion != null) {
         processDomeColumn(level, centerPos, radiusSquared, commonRegion, target, replacement);

         for (int z = 1; z <= radius; z++) {
            for (int x = 0; x <= radius; x++) {
               int distSq = x * x + z * z;
               if (distSq <= radiusSquared) {
                  float height = (radiusSquared - distSq) * squish;
                  processDomeColumn(level, centerPos.offset(x, 0, z), height, commonRegion, target, replacement);
                  processDomeColumn(level, centerPos.offset(-x, 0, -z), height, commonRegion, target, replacement);
                  processDomeColumn(level, centerPos.offset(-z, 0, x), height, commonRegion, target, replacement);
                  processDomeColumn(level, centerPos.offset(z, 0, -x), height, commonRegion, target, replacement);
               }
            }
         }
      }
   }

   public static void replaceBlocksDome(
      WorldGenLevel level,
      BlockPos centerPos,
      float radius,
      float squish,
      BoundingBox chunkBox,
      BoundingBox structureBox,
      HolderSet<Block> target,
      BlockState replacement
   ) {
      float radiusSquared = radius * radius;
      BoundingBox commonRegion = BoundingBoxUtils.getIntersectionOfSBBs(chunkBox, structureBox);
      if (commonRegion != null) {
         processDomeColumn(level, centerPos, radiusSquared, commonRegion, target, replacement);

         for (int z = 1; z <= radius; z++) {
            for (int x = 0; x <= radius; x++) {
               int distSq = x * x + z * z;
               if (distSq <= radiusSquared) {
                  float height = (radiusSquared - distSq) * squish;
                  processDomeColumn(level, centerPos.offset(x, 0, z), height, commonRegion, target, replacement);
                  processDomeColumn(level, centerPos.offset(-x, 0, -z), height, commonRegion, target, replacement);
                  processDomeColumn(level, centerPos.offset(-z, 0, x), height, commonRegion, target, replacement);
                  processDomeColumn(level, centerPos.offset(z, 0, -x), height, commonRegion, target, replacement);
               }
            }
         }
      }
   }

   public static void processDomeColumn(WorldGenLevel level, BlockPos pos, float height, BoundingBox mask, HolderSet<Block> target, BlockState replacement) {
      if (mask.isInside(pos)) {
         for (int dY = 0; dY < height; dY++) {
            BlockPos posElevated = pos.above(dY);
            if (level.getBlockState(posElevated).is(target)) {
               level.setBlock(posElevated, replacement, 3);
            }
         }
      }
   }

   public static void processDomeColumn(WorldGenLevel level, BlockPos pos, float height, BoundingBox mask, Block target, BlockState replacement) {
      if (mask.isInside(pos)) {
         for (int dY = 0; dY < height; dY++) {
            BlockPos posElevated = pos.above(dY);
            if (level.getBlockState(posElevated).is(target)) {
               level.setBlock(posElevated, replacement, 3);
            }
         }
      }
   }
}
