package twilightforest.world.components.feature.trees;

import com.mojang.serialization.Codec;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.loot.TFLootTables;
import twilightforest.util.RootPlacer;
import twilightforest.util.features.FeatureLogic;
import twilightforest.util.features.FeaturePlacers;
import twilightforest.util.features.FeatureUtil;
import twilightforest.util.iterators.VoxelBresenhamIterator;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;

public abstract class HollowTreeFeature extends TFTreeFeature<TFTreeFeatureConfig> {
   private static final int LEAF_DUNGEON_CHANCE = 8;

   public HollowTreeFeature(Codec<TFTreeFeatureConfig> config) {
      super(config);
   }

   public static void makeHollowTree(
      WorldGenLevel world,
      RandomSource random,
      BlockPos pos,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RootPlacer decorationPlacer,
      TFTreeFeatureConfig config
   ) {
      int radius = random.nextInt(4) + 1;
      int height = random.nextInt(64) + 32;
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, 3, 2, 6, 0.75, 3, 5, 3, false, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, 1, 2, 8, 0.9, 3, 5, 3, false, config);
      buildTrunk(world, trunkPlacer, decorationPlacer, random, pos, radius, height, config);
      int numFireflies = random.nextInt(6 * radius) + 5;

      for (int i = 0; i <= numFireflies; i++) {
         int fHeight = (int)(height * random.nextDouble() * 0.9) + height / 10;
         double fAngle = random.nextDouble();
         addBug(world, (Block)TFBlocks.FIREFLY.get(), pos, radius, fHeight, fAngle);
      }

      int numCicadas = random.nextInt(3 * radius) + 5;

      for (int i = 0; i <= numCicadas; i++) {
         int fHeight = (int)(height * random.nextDouble() * 0.9) + height / 10;
         double fAngle = random.nextDouble();
         addBug(world, (Block)TFBlocks.CICADA.get(), pos, radius, fHeight, fAngle);
      }

      buildFullCrown(world, trunkPlacer, leavesPlacer, random, pos, radius, height, config);
      int numBranches = random.nextInt(3) + 3;

      for (int i = 0; i <= numBranches; i++) {
         int branchHeight = (int)(height * random.nextDouble() * 0.9) + height / 10;
         double branchRotation = random.nextDouble();
         makeSmallBranch(world, trunkPlacer, leavesPlacer, random, pos, radius, branchHeight, 4.0, branchRotation, 0.35, true, config);
      }
   }

   protected static void buildFullCrown(
      WorldGenLevel world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos pos,
      int radius,
      int height,
      TFTreeFeatureConfig config
   ) {
      int crownRadius = radius * 4 + 4;
      int bvar = radius + 2;
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height - crownRadius, 0, crownRadius, 0.35, bvar, bvar + 2, 2, true, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height - crownRadius / 2, 0, crownRadius, 0.28, bvar, bvar + 2, 1, true, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height, 0, crownRadius, 0.15, 2, 4, 2, true, config);
      buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height, 0, crownRadius / 2, 0.05, bvar, bvar + 2, 1, true, config);
   }

   protected static void buildBranchRing(
      WorldGenLevel world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos pos,
      int radius,
      int branchHeight,
      int heightVar,
      int length,
      double tilt,
      int minBranches,
      int maxBranches,
      int size,
      boolean leafy,
      TFTreeFeatureConfig config
   ) {
      int numBranches = random.nextInt(maxBranches - minBranches) + minBranches;
      double branchRotation = 1.0 / (numBranches + 1);
      double branchOffset = random.nextDouble();

      for (int i = 0; i <= numBranches; i++) {
         int dHeight;
         if (heightVar > 0) {
            dHeight = branchHeight - heightVar + random.nextInt(2 * heightVar);
         } else {
            dHeight = branchHeight;
         }

         if (size == 2) {
            makeLargeBranch(world, trunkPlacer, leavesPlacer, random, pos, radius, dHeight, length, i * branchRotation + branchOffset, tilt, leafy, config);
         } else if (size == 1) {
            makeMedBranch(world, trunkPlacer, leavesPlacer, random, pos, radius, dHeight, length, i * branchRotation + branchOffset, tilt, leafy, config);
         } else if (size == 3) {
            makeRoot(world, random, pos, radius, dHeight, length, i * branchRotation + branchOffset, tilt, config);
         } else {
            makeSmallBranch(world, trunkPlacer, leavesPlacer, random, pos, radius, dHeight, length, i * branchRotation + branchOffset, tilt, leafy, config);
         }
      }
   }

   protected static void buildTrunk(
      LevelAccessor world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      RootPlacer decoPlacer,
      RandomSource random,
      BlockPos pos,
      int radius,
      int height,
      TFTreeFeatureConfig config
   ) {
      int hollow = radius >> 1;

      for (int dx = -radius; dx <= radius; dx++) {
         for (int dz = -radius; dz <= radius; dz++) {
            int ax = Math.abs(dx);
            int az = Math.abs(dz);
            int dist = Math.max(ax, az) + (Math.min(ax, az) >> 1);
            if (dist <= radius) {
               for (int dy = -4; dy < 0; dy++) {
                  BlockPos dPos = pos.offset(dx, dy, dz);
                  if (FeatureUtil.hasAirAround(world, dPos)) {
                     if (dist > hollow) {
                        trunkPlacer.accept(dPos, config.trunkProvider.getState(random, dPos));
                     } else {
                        trunkPlacer.accept(dPos, config.branchProvider.getState(random, dPos));
                     }
                  } else {
                     FeaturePlacers.placeIfValidRootPos(world, decoPlacer, random, dPos, config.rootsProvider);
                  }
               }
            }
         }
      }

      for (int dx = -radius; dx <= radius; dx++) {
         for (int dz = -radius; dz <= radius; dz++) {
            for (int dy = 0; dy <= height; dy++) {
               BlockPos dPos = pos.offset(dx, dy, dz);
               int ax = Math.abs(dx);
               int az = Math.abs(dz);
               int dist = (int)(Math.max(ax, az) + Math.min(ax, az) * 0.5);
               if (dist <= radius && dist > hollow) {
                  FeaturePlacers.placeIfValidTreePos(world, trunkPlacer, random, dPos, config.trunkProvider);
               }

               if (dist <= hollow) {
               }

               if (dist == hollow && dx == hollow) {
                  world.setBlock(dPos, (BlockState)Blocks.VINE.defaultBlockState().setValue(VineBlock.EAST, true), 3);
               }
            }
         }
      }
   }

   protected static void makeMedBranch(
      LevelAccessor world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos pos,
      int diameter,
      int branchHeight,
      double length,
      double angle,
      double tilt,
      boolean leafy,
      TFTreeFeatureConfig config
   ) {
      BlockPos src = FeatureLogic.translate(pos.above(branchHeight), diameter, angle, 0.5);
      makeMedBranch(world, trunkPlacer, leavesPlacer, random, src, length, angle, tilt, leafy, config);
   }

   protected static void makeMedBranch(
      LevelAccessor world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos src,
      double length,
      double angle,
      double tilt,
      boolean leafy,
      TFTreeFeatureConfig config
   ) {
      BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);
      FeaturePlacers.drawBresenhamBranch(world, trunkPlacer, random, src, dest, config.branchProvider);
      if (leafy) {
         FeaturePlacers.placeSpheroid(world, leavesPlacer, FeaturePlacers.VALID_TREE_POS, random, dest, 2.5F, 2.5F, config.leavesProvider);
      }

      int numShoots = random.nextInt(2) + 1;
      double angleInc = 0.8 / numShoots;

      for (int i = 0; i <= numShoots; i++) {
         double angleVar = angleInc * i - 0.4;
         double outVar = random.nextDouble() * 0.8 + 0.2;
         double tiltVar = random.nextDouble() * 0.75 + 0.15;
         BlockPos bsrc = FeatureLogic.translate(src, length * outVar, angle, tilt);
         double slength = length * 0.4;
         makeSmallBranch(world, trunkPlacer, leavesPlacer, random, bsrc, slength, angle + angleVar, tilt * tiltVar, leafy, config);
      }
   }

   protected static void makeSmallBranch(
      LevelAccessor world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos src,
      double length,
      double angle,
      double tilt,
      boolean leafy,
      TFTreeFeatureConfig config
   ) {
      BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);
      FeaturePlacers.drawBresenhamBranch(world, trunkPlacer, random, src, dest, config.branchProvider);
      if (leafy) {
         float leafRad = random.nextInt(2) + 1.5F;
         FeaturePlacers.placeSpheroid(world, leavesPlacer, FeaturePlacers.VALID_TREE_POS, random, dest, leafRad, leafRad, config.leavesProvider);
      }
   }

   protected static void makeSmallBranch(
      LevelAccessor world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos pos,
      int diameter,
      int branchHeight,
      double length,
      double angle,
      double tilt,
      boolean leafy,
      TFTreeFeatureConfig config
   ) {
      BlockPos src = FeatureLogic.translate(pos.above(branchHeight), diameter, angle, 0.5);
      makeSmallBranch(world, trunkPlacer, leavesPlacer, random, src, length, angle, tilt, leafy, config);
   }

   protected static void makeRoot(
      LevelAccessor world,
      RandomSource random,
      BlockPos pos,
      int diameter,
      int branchHeight,
      double length,
      double angle,
      double tilt,
      TFTreeFeatureConfig config
   ) {
      BlockPos src = FeatureLogic.translate(pos.above(branchHeight), diameter, angle, 0.5);
      BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);
      FeaturePlacers.traceExposedRoot(world, new RootPlacer((checkedPos, state) -> {
         world.setBlock(checkedPos, state, 3);
         world.setBlock(checkedPos.below(), state, 3);
      }, 2), random, config.branchProvider, config.rootsProvider, new VoxelBresenhamIterator(src, dest));
   }

   protected static void makeLargeBranch(
      WorldGenLevel world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos src,
      double length,
      double angle,
      double tilt,
      boolean leafy,
      TFTreeFeatureConfig config
   ) {
      BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);
      FeaturePlacers.drawBresenhamBranch(world, trunkPlacer, random, src, dest, config.branchProvider);
      int reinforcements = random.nextInt(3);

      for (int i = 0; i <= reinforcements; i++) {
         int vx = (i & 2) == 0 ? 1 : 0;
         int vy = (i & 1) == 0 ? 1 : -1;
         int vz = (i & 2) == 0 ? 0 : 1;
         FeaturePlacers.drawBresenhamBranch(world, trunkPlacer, random, src.offset(vx, vy, vz), dest, config.branchProvider);
      }

      if (leafy) {
         FeaturePlacers.placeSpheroid(world, leavesPlacer, FeaturePlacers.VALID_TREE_POS, random, dest.above(), 3.5F, 3.5F, config.leavesProvider);
      }

      int numMedBranches = random.nextInt(Math.max((int)(length / 6.0), 1)) + random.nextInt(2) + 1;

      for (int i = 0; i <= numMedBranches; i++) {
         double outVar = random.nextDouble() * 0.3 + 0.3;
         double angleVar = random.nextDouble() * 0.225 * ((i & 1) == 0 ? 1.0 : -1.0);
         BlockPos bsrc = FeatureLogic.translate(src, length * outVar, angle, tilt);
         makeMedBranch(world, trunkPlacer, leavesPlacer, random, bsrc, length * 0.6, angle + angleVar, tilt, leafy, config);
      }

      int numSmallBranches = random.nextInt(2) + 1;

      for (int i = 0; i <= numSmallBranches; i++) {
         double outVar = random.nextDouble() * 0.25 + 0.25;
         double angleVar = random.nextDouble() * 0.25 * ((i & 1) == 0 ? 1.0 : -1.0);
         BlockPos bsrc = FeatureLogic.translate(src, length * outVar, angle, tilt);
         makeSmallBranch(world, trunkPlacer, leavesPlacer, random, bsrc, Math.max(length * 0.3, 2.0), angle + angleVar, tilt, leafy, config);
      }

      if (random.nextInt(8) == 0) {
         makeLeafDungeon(world, leavesPlacer, random, dest.above(), config);
      }
   }

   private static void makeLeafDungeon(
      WorldGenLevel world, BiConsumer<BlockPos, BlockState> leavesPlacer, RandomSource random, BlockPos pos, TFTreeFeatureConfig config
   ) {
      FeaturePlacers.placeSpheroid(world, leavesPlacer, FeaturePlacers.VALID_TREE_POS, random, pos, 4.5F, 4.5F, config.leavesProvider);
      FeatureUtil.drawBlob(world, pos, 3, config.branchProvider.getState(random, pos));
      FeatureUtil.drawBlob(world, pos, 2, Blocks.AIR.defaultBlockState());
      world.setBlock(pos.above(), Blocks.SPAWNER.defaultBlockState(), 18);
      SpawnerBlockEntity ms = (SpawnerBlockEntity)world.getBlockEntity(pos.above());
      if (ms != null) {
         ms.setEntityId((EntityType)TFEntities.SWARM_SPIDER.get(), random);
      }

      makeLeafDungeonChest(world, random, pos);
   }

   private static void makeLeafDungeonChest(WorldGenLevel world, RandomSource random, BlockPos pos) {
      Direction chestDir = Plane.HORIZONTAL.getRandomDirection(random);
      pos = pos.relative(chestDir, 2);
      TFLootTables.generateChest(world, pos.below(), chestDir.getOpposite(), false, TFLootTables.TREE_CACHE);
   }

   protected static void makeLargeBranch(
      WorldGenLevel world,
      BiConsumer<BlockPos, BlockState> trunkPlacer,
      BiConsumer<BlockPos, BlockState> leavesPlacer,
      RandomSource random,
      BlockPos pos,
      int radius,
      int branchHeight,
      double length,
      double angle,
      double tilt,
      boolean leafy,
      TFTreeFeatureConfig config
   ) {
      BlockPos src = FeatureLogic.translate(pos.above(branchHeight), radius, angle, 0.5);
      makeLargeBranch(world, trunkPlacer, leavesPlacer, random, src, length, angle, tilt, leafy, config);
   }

   public static void addBug(LevelAccessor level, Block bug, BlockPos pos, int diameter, int fHeight, double fAngle) {
      BlockPos src = FeatureLogic.translate(pos.above(fHeight), diameter + 1, fAngle, 0.5);
      fAngle %= 1.0;
      Direction facing = Direction.EAST;
      if (fAngle > 0.875 || fAngle <= 0.125) {
         facing = Direction.SOUTH;
      } else if (fAngle > 0.375 && fAngle <= 0.625) {
         facing = Direction.NORTH;
      } else if (fAngle > 0.625) {
         facing = Direction.WEST;
      }

      if (((BlockState)bug.defaultBlockState().setValue(DirectionalBlock.FACING, facing)).canSurvive(level, src)) {
         level.setBlock(src, (BlockState)bug.defaultBlockState().setValue(DirectionalBlock.FACING, facing), 3);
      }
   }
}
