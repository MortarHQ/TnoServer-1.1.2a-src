package com.mortal_cultivation.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mortal_cultivation.block.SpiritLeavesBlock;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;

public class SpiritTreeFoliagePlacer extends FoliagePlacer {
   public static final MapCodec<SpiritTreeFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
      instance -> foliagePlacerParts(instance).apply(instance, SpiritTreeFoliagePlacer::new)
   );
   private static final float NATURAL_FRUIT_CHANCE = 0.01F;

   public SpiritTreeFoliagePlacer(IntProvider radius, IntProvider offset) {
      super(radius, offset);
   }

   protected FoliagePlacerType<?> type() {
      return (FoliagePlacerType<?>)WorldgenRegistry.SPIRIT_TREE_FOLIAGE.get();
   }

   public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
      return 4;
   }

   protected void createFoliage(
      final LevelSimulatedReader level,
      final FoliageSetter setter,
      final RandomSource random,
      TreeConfiguration config,
      int maxFreeTreeHeight,
      FoliageAttachment attachment,
      int foliageHeight,
      int foliageRadius,
      int offset
   ) {
      final int day = level instanceof WorldGenLevel world ? SpiritLeavesBlock.fruitingDay(world.getLevel().getDayTime()) : 0;
      final boolean naturallyGrown = level instanceof WorldGenRegion;
      final Set<BlockPos> exposedLeaves = new HashSet<>();
      FoliageSetter datedSetter = new FoliageSetter() {
         public void set(BlockPos pos, BlockState state) {
            if (!setter.isSet(pos)) {
               if (state.hasProperty(SpiritLeavesBlock.FRUITING_DAY)) {
                  state = (BlockState)state.setValue(SpiritLeavesBlock.FRUITING_DAY, day);
                  if (naturallyGrown && exposedLeaves.contains(pos) && random.nextFloat() < 0.01F && SpiritLeavesBlock.canGrowFruit(level, pos)) {
                     state = SpiritLeavesBlock.withFruit(state, day);
                  }
               }

               setter.set(pos, state);
            }
         }

         public boolean isSet(BlockPos pos) {
            return setter.isSet(pos);
         }
      };
      BlockPos center = attachment.pos().above(offset);
      int radius = Math.max(2, Math.min(5, foliageRadius + attachment.radiusOffset()));
      double radiusX = radius + random.nextDouble() * 0.45;
      double radiusZ = radius - 0.65 + random.nextDouble() * 0.65;
      if (random.nextBoolean()) {
         double swap = radiusX;
         radiusX = radiusZ;
         radiusZ = swap;
      }

      double verticalRadius = 1.6 + radius * 0.22;
      double phase = random.nextDouble() * Math.PI * 2.0;
      Set<BlockPos> candidates = new HashSet<>();

      for (int y = -2; y <= 2; y++) {
         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               double lobe = 1.0 + 0.12 * Math.sin(Math.atan2(z, x) * 3.0 + phase);
               double horizontal = (x * x / (radiusX * radiusX) + z * z / (radiusZ * radiusZ)) / (lobe * lobe);
               double vertical = (y + 0.15) / verticalRadius;
               double shape = horizontal + vertical * vertical;
               if (shape <= 1.0 && (shape < 0.86 || random.nextFloat() >= 0.16F)) {
                  candidates.add(center.offset(x, y, z));
               }
            }
         }
      }

      for (Direction direction : Plane.HORIZONTAL) {
         if (random.nextFloat() < 0.45F) {
            BlockPos fringe = center.relative(direction, radius - 1).below();
            if (candidates.contains(fringe)) {
               candidates.add(fringe.below());
               if (random.nextBoolean()) {
                  candidates.add(fringe.below(2));
               }
            }
         }
      }

      for (BlockPos pos : candidates) {
         if (!candidates.contains(pos.below())) {
            exposedLeaves.add(pos);
         } else {
            for (Direction direction : Plane.HORIZONTAL) {
               if (!candidates.contains(pos.relative(direction))) {
                  exposedLeaves.add(pos);
                  break;
               }
            }
         }
      }

      BlockPos support = attachment.pos().below();
      candidates.remove(support);
      List<BlockPos> frontier = List.of(support);

      for (int distance = 1; distance < 7 && !frontier.isEmpty(); distance++) {
         List<BlockPos> next = new ArrayList<>();

         for (BlockPos previous : frontier) {
            for (Direction direction : Direction.values()) {
               BlockPos pos = previous.relative(direction);
               if (candidates.remove(pos)
                  && (tryPlaceLeaf(level, datedSetter, random, config, pos) || level.isStateAtPosition(pos, state -> state.is(BlockTags.LOGS)))) {
                  next.add(pos);
               }
            }
         }

         frontier = next;
      }
   }

   protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
      return localX * localX + localZ * localZ > range * range + 1;
   }
}
