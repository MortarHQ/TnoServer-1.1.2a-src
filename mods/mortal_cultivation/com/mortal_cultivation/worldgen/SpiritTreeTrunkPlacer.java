package com.mortal_cultivation.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class SpiritTreeTrunkPlacer extends TrunkPlacer {
   public static final MapCodec<SpiritTreeTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
      instance -> trunkPlacerParts(instance).apply(instance, SpiritTreeTrunkPlacer::new)
   );

   public SpiritTreeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
      super(baseHeight, heightRandA, heightRandB);
   }

   protected TrunkPlacerType<?> type() {
      return (TrunkPlacerType<?>)WorldgenRegistry.SPIRIT_TREE_TRUNK.get();
   }

   public List<FoliageAttachment> placeTrunk(
      LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, int freeTreeHeight, BlockPos origin, TreeConfiguration config
   ) {
      if (level instanceof LevelReader reader
         && reader.getBiome(origin).is(WorldgenRegistry.SPIRIT_FORESTS)
         && Feature.isDirt(reader.getBlockState(origin.below()))) {
         if (level instanceof WorldGenRegion world) {
            if (AncientCherryStructure.inClearing(world, origin)
               || QilinBambooGroveStructure.inClearing(world, origin)
               || SpiritForestLandscapeLayout.isOpenSpace(world.getSeed(), origin.getX(), origin.getZ())) {
               return List.of();
            }

            if (SpiritForestLandscapeLayout.clearingDistanceSquared(world.getSeed(), origin.getX(), origin.getZ()) < 484) {
               freeTreeHeight = Math.min(freeTreeHeight, 15 + random.nextInt(3));
            }
         }

         setDirtAt(level, setter, random, origin.below(), config);
         Set<BlockPos> ownLogs = new HashSet<>();
         BiConsumer<BlockPos, BlockState> logSetter = (pos, state) -> {
            setter.accept(pos, state);
            ownLogs.add(pos.immutable());
         };
         int stemHeight = Math.max(5, freeTreeHeight - 3);
         Direction cliff = reader.getBiome(origin).is(WorldgenRegistry.SKY_SPIRIT_FOREST) ? cliffDirection(reader, origin) : null;
         Direction lean = cliff != null ? cliff : Plane.HORIZONTAL.getRandomDirection(random);
         Direction upperLean = random.nextBoolean() ? lean : lean.getClockWise();
         int lowerBend = stemHeight / 3 + random.nextInt(2);
         int upperBend = stemHeight * 2 / 3 + random.nextInt(2);
         List<BlockPos> stem = new ArrayList<>();
         BlockPos cursor = origin;

         for (int y = 0; y < stemHeight; y++) {
            cursor = new BlockPos(cursor.getX(), origin.getY() + y, cursor.getZ());
            boolean thickSection = y < stemHeight - 2;
            if (!this.placeTrunkSection(level, logSetter, random, cursor, config, thickSection, ownLogs)) {
               break;
            }

            if (y == lowerBend || stemHeight >= 8 && y == upperBend) {
               BlockPos bent = cursor.relative(y == lowerBend ? lean : upperLean);
               if (this.placeTrunkSection(level, logSetter, random, bent, config, thickSection, ownLogs)) {
                  cursor = bent;
               }
            }

            stem.add(cursor);
         }

         if (stem.isEmpty()) {
            return List.of();
         }

         if (stem.size() >= 11) {
            this.placeButtress(level, reader, logSetter, random, config, origin, lean.getOpposite(), 2 + random.nextInt(2));
            this.placeButtress(level, reader, logSetter, random, config, origin, lean.getCounterClockWise(), 1);
         }

         if (cliff != null) {
            this.placeCliffRoots(level, reader, logSetter, random, config, origin, cliff, ownLogs);
         }

         List<FoliageAttachment> foliage = new ArrayList<>();
         BlockPos top = stem.getLast();
         foliage.add(new FoliageAttachment(top.above(), stem.size() >= 8 ? 1 : 0, false));
         if (stem.size() < 4) {
            return foliage;
         }

         Direction[] directions = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

         for (int i = directions.length - 1; i > 0; i--) {
            int other = random.nextInt(i + 1);
            Direction swap = directions[i];
            directions[i] = directions[other];
            directions[other] = swap;
         }

         int branchCount = stem.size() < 8 ? 2 : (stem.size() < 11 ? 3 : 4);

         for (int branch = 0; branch < branchCount; branch++) {
            double fraction = 0.45 + 0.38 * branch / (branchCount - 1);
            int branchLevel = Math.max(2, Math.min(stem.size() - 2, (int)(stem.size() * fraction) + random.nextInt(3) - 1));
            BlockPos start = stem.get(branchLevel);
            int length = (stem.size() < 12 ? 3 : (stem.size() < 25 ? 4 : 5)) + random.nextInt(2);
            BlockPos tip = this.placeBranch(level, logSetter, random, config, start, directions[branch], length, top.getY(), ownLogs);
            if (!tip.equals(start)) {
               foliage.add(new FoliageAttachment(tip.above(), branch == branchCount - 1 ? -1 : 0, false));
            }
         }

         return foliage;
      } else {
         return List.of();
      }
   }

   private static Direction cliffDirection(LevelReader level, BlockPos origin) {
      for (Direction direction : Plane.HORIZONTAL) {
         BlockPos edge = origin.relative(direction, 4).below();
         if (level.isEmptyBlock(edge) && level.isEmptyBlock(edge.below(3))) {
            return direction;
         }
      }

      return null;
   }

   private void placeCliffRoots(
      LevelSimulatedReader level,
      LevelReader reader,
      BiConsumer<BlockPos, BlockState> setter,
      RandomSource random,
      TreeConfiguration config,
      BlockPos origin,
      Direction outward,
      Set<BlockPos> ownLogs
   ) {
      for (Direction direction : new Direction[]{outward, outward.getClockWise()}) {
         BlockPos cursor = origin;

         for (int step = 0; step < 4; step++) {
            BlockPos next = cursor.relative(direction);
            if (!this.rootLog(level, reader, setter, random, config, next, direction.getAxis(), ownLogs)) {
               break;
            }

            cursor = next;
            next = cursor.below();
            if (!this.rootLog(level, reader, setter, random, config, next, Axis.Y, ownLogs)) {
               break;
            }

            cursor = next;
         }
      }
   }

   private boolean rootLog(
      LevelSimulatedReader level,
      LevelReader reader,
      BiConsumer<BlockPos, BlockState> setter,
      RandomSource random,
      TreeConfiguration config,
      BlockPos pos,
      Axis axis,
      Set<BlockPos> ownLogs
   ) {
      if (ownLogs.contains(pos)) {
         return true;
      } else if (Feature.isDirt(reader.getBlockState(pos))) {
         setter.accept(pos, (BlockState)config.trunkProvider.getState(random, pos).setValue(RotatedPillarBlock.AXIS, axis));
         return true;
      } else {
         return this.placeLog(level, setter, random, pos, config, state -> (BlockState)state.setValue(RotatedPillarBlock.AXIS, axis));
      }
   }

   private boolean placeTrunkSection(
      LevelSimulatedReader level,
      BiConsumer<BlockPos, BlockState> setter,
      RandomSource random,
      BlockPos center,
      TreeConfiguration config,
      boolean thick,
      Set<BlockPos> ownLogs
   ) {
      if (!ownLogs.contains(center) && !this.placeLog(level, setter, random, center, config)) {
         return false;
      }

      if (!thick) {
         return true;
      }

      this.placeLog(level, setter, random, center.east(), config);
      this.placeLog(level, setter, random, center.south(), config);
      this.placeLog(level, setter, random, center.east().south(), config);
      return true;
   }

   private void placeButtress(
      LevelSimulatedReader level,
      LevelReader reader,
      BiConsumer<BlockPos, BlockState> setter,
      RandomSource random,
      TreeConfiguration config,
      BlockPos origin,
      Direction direction,
      int height
   ) {
      BlockPos base = origin.relative(direction);
      if (Feature.isDirt(reader.getBlockState(base.below()))) {
         int y = 0;

         while (y < height && this.placeLog(level, setter, random, base.above(y), config)) {
            y++;
         }
      }
   }

   private BlockPos placeBranch(
      LevelSimulatedReader level,
      BiConsumer<BlockPos, BlockState> setter,
      RandomSource random,
      TreeConfiguration config,
      BlockPos start,
      Direction direction,
      int length,
      int maxY,
      Set<BlockPos> ownLogs
   ) {
      BlockPos cursor = start;
      Direction curve = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
      int turn = length >= 4 ? 1 + random.nextInt(length - 2) : -1;

      for (int step = 0; step < length; step++) {
         Direction stepDirection = step == turn ? curve : direction;
         BlockPos next = cursor.relative(stepDirection);
         if (!ownLogs.contains(next)
            && !this.placeLog(level, setter, random, next, config, state -> (BlockState)state.setValue(RotatedPillarBlock.AXIS, stepDirection.getAxis()))) {
            break;
         }

         cursor = next;
         if ((step == length / 2 || step == length - 1) && cursor.getY() < maxY) {
            next = cursor.above();
            if (!ownLogs.contains(next) && !this.placeLog(level, setter, random, next, config)) {
               break;
            }

            cursor = next;
         }
      }

      return cursor;
   }
}
