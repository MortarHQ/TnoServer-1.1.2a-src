package twilightforest.world.components.structures.hollowtree;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList.Builder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import twilightforest.block.CritterBlock;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.loot.TFLootTables;
import twilightforest.util.features.FeatureLogic;
import twilightforest.util.iterators.VoxelBresenhamIterator;

public abstract class HollowTreePiece extends StructurePiece {
   static final int PLACE_FLAG = 19;
   public static final IntProvider DEFAULT_HEIGHT = UniformInt.of(32, 95);
   public static final IntProvider DEFAULT_RADIUS = UniformInt.of(1, 4);
   public static final BlockStateProvider DEFAULT_LOG = BlockStateProvider.simple((Block)TFBlocks.TWILIGHT_OAK_LOG.value());
   public static final BlockStateProvider DEFAULT_WOOD = BlockStateProvider.simple((Block)TFBlocks.TWILIGHT_OAK_WOOD.value());
   public static final BlockStateProvider DEFAULT_ROOT = BlockStateProvider.simple((Block)TFBlocks.ROOT_BLOCK.value());
   public static final BlockStateProvider DEFAULT_LEAVES = BlockStateProvider.simple((Block)TFBlocks.TWILIGHT_OAK_LEAVES.value());
   public static final BlockStateProvider DEFAULT_VINE = BlockStateProvider.simple(Blocks.VINE.defaultBlockState());
   public static final BlockStateProvider DEFAULT_BUG = new WeightedStateProvider(
      new Builder()
         .add((BlockState)((Block)TFBlocks.FIREFLY.value()).defaultBlockState().setValue(CritterBlock.FACING, Direction.NORTH))
         .add((BlockState)((Block)TFBlocks.CICADA.value()).defaultBlockState().setValue(CritterBlock.FACING, Direction.NORTH))
   );
   public static final BlockStateProvider DEFAULT_DUNGEON_AIR = BlockStateProvider.simple(Blocks.AIR);
   public static final BlockStateProvider DEFAULT_DUNGEON_LOOT_BLOCK = BlockStateProvider.simple(
      (BlockState)Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST)
   );
   public static final ResourceKey<LootTable> DEFAULT_DUNGEON_LOOT_TABLE = TFLootTables.TREE_CACHE;
   public static final Holder<EntityType<?>> DEFAULT_DUNGEON_MONSTER = TFEntities.SWARM_SPIDER;

   protected HollowTreePiece(StructurePieceType type, int genDepth, BoundingBox boundingBox) {
      super(type, genDepth, boundingBox);
   }

   public HollowTreePiece(StructurePieceType type, CompoundTag tag) {
      super(type, tag);
   }

   protected void placeProvidedBlock(
      WorldGenLevel world,
      BlockStateProvider possibleBlocks,
      RandomSource random,
      int sx,
      int sy,
      int sz,
      BoundingBox sbb,
      BlockPos origin,
      boolean forcedPlace,
      boolean leafHack
   ) {
      BlockPos worldPos = this.getWorldPos(sx, sy, sz).immutable();
      if (sbb.isInside(worldPos) && (forcedPlace || FeatureLogic.treesReplaceable(world.getBlockState(worldPos)))) {
         BlockState state = possibleBlocks.getState(random, worldPos);
         if (state.hasProperty(LeavesBlock.DISTANCE)) {
            int distance = leafHack ? 1 : Mth.clamp(origin.distManhattan(worldPos), 1, 7);
            world.setBlock(worldPos, (BlockState)state.setValue(LeavesBlock.DISTANCE, distance), 19);
         } else {
            world.setBlock(worldPos, state, 19);
         }
      }
   }

   protected void fillColumnDown(WorldGenLevel pLevel, BlockStateProvider possibleBlocks, RandomSource random, int sx, int sy, int sz, BoundingBox pBox) {
      MutableBlockPos worldPos = this.getWorldPos(sx, sy, sz);
      if (pBox.isInside(worldPos)) {
         while (this.isReplaceableByStructures(pLevel.getBlockState(worldPos)) && worldPos.getY() > pLevel.getMinBuildHeight() + 1) {
            pLevel.setBlock(worldPos, possibleBlocks.getState(random, worldPos), 19);
            worldPos.move(Direction.DOWN);
         }
      }
   }

   protected void fillVineColumnDown(
      WorldGenLevel pLevel, BlockStateProvider possibleBlocks, RandomSource random, int sx, int sy, int sz, BoundingBox pBox, Direction direction
   ) {
      MutableBlockPos worldPos = this.getWorldPos(sx, sy, sz);
      if (pBox.isInside(worldPos)) {
         while (this.nonFluidAndReplaceableByStructures(pLevel, worldPos) && worldPos.getY() > pLevel.getMinBuildHeight() + 1) {
            pLevel.setBlock(worldPos, (BlockState)possibleBlocks.getState(random, worldPos).setValue(VineBlock.getPropertyForFace(direction), true), 19);
            worldPos.move(Direction.DOWN);
         }
      }
   }

   private boolean nonFluidAndReplaceableByStructures(WorldGenLevel pLevel, MutableBlockPos worldPos) {
      BlockState blockState = pLevel.getBlockState(worldPos);
      return blockState.getFluidState().isEmpty() && this.isReplaceableByStructures(blockState);
   }

   protected void drawBresehnam(
      WorldGenLevel level, BoundingBox writeableBounds, BlockPos startPos, BlockPos endPos, BlockStateProvider stateProvider, RandomSource random
   ) {
      for (BlockPos worldPos : new VoxelBresenhamIterator(startPos, endPos)) {
         if (writeableBounds.isInside(worldPos) && FeatureLogic.treesReplaceable(level.getBlockState(worldPos))) {
            level.setBlock(worldPos, stateProvider.getState(random, worldPos), 19);
         }
      }
   }

   protected void drawBlockBlob(
      WorldGenLevel world,
      BoundingBox sbb,
      int sx,
      int sy,
      int sz,
      int blobRadius,
      RandomSource random,
      BlockStateProvider stateProvider,
      boolean forcedPlace,
      boolean leafHack,
      boolean imperfect
   ) {
      BlockPos origin = this.getWorldPos(sx, sy, sz).immutable();

      for (byte dx = 0; dx <= blobRadius; dx++) {
         for (byte dy = 0; dy <= blobRadius; dy++) {
            for (byte dz = 0; dz <= blobRadius; dz++) {
               byte dist;
               if (dx >= dy && dx >= dz) {
                  dist = (byte)(dx + (byte)(Math.max(dy, dz) * 0.5 + Math.min(dy, dz) * 0.25));
               } else if (dy >= dx && dy >= dz) {
                  dist = (byte)(dy + (byte)(Math.max(dx, dz) * 0.5 + Math.min(dx, dz) * 0.25));
               } else {
                  dist = (byte)(dz + (byte)(Math.max(dx, dy) * 0.5 + Math.min(dx, dy) * 0.25));
               }

               if (dist <= blobRadius) {
                  if (imperfect && dist == blobRadius) {
                     if (dx == dy && dy == dz) {
                        continue;
                     }

                     if (dx == dy && dz > dx && dx > 0 || dy == dz && dx > dy && dy > 0 || dz == dx && dy > dz && dz > 0) {
                        if (random.nextInt(2) == 0) {
                           this.placeProvidedBlock(world, stateProvider, random, sx + dx, sy + dy, sz + dz, sbb, origin, forcedPlace, leafHack);
                        }

                        if (random.nextInt(2) == 0) {
                           this.placeProvidedBlock(world, stateProvider, random, sx + dx, sy + dy, sz - dz, sbb, origin, forcedPlace, leafHack);
                        }

                        if (random.nextInt(2) == 0) {
                           this.placeProvidedBlock(world, stateProvider, random, sx - dx, sy + dy, sz + dz, sbb, origin, forcedPlace, leafHack);
                        }

                        if (random.nextInt(2) == 0) {
                           this.placeProvidedBlock(world, stateProvider, random, sx - dx, sy + dy, sz - dz, sbb, origin, forcedPlace, leafHack);
                        }

                        if (random.nextInt(2) == 0) {
                           this.placeProvidedBlock(world, stateProvider, random, sx + dx, sy - dy, sz + dz, sbb, origin, forcedPlace, leafHack);
                        }

                        if (random.nextInt(2) == 0) {
                           this.placeProvidedBlock(world, stateProvider, random, sx + dx, sy - dy, sz - dz, sbb, origin, forcedPlace, leafHack);
                        }

                        if (random.nextInt(2) == 0) {
                           this.placeProvidedBlock(world, stateProvider, random, sx - dx, sy - dy, sz + dz, sbb, origin, forcedPlace, leafHack);
                        }

                        if (random.nextInt(2) == 0) {
                           this.placeProvidedBlock(world, stateProvider, random, sx - dx, sy - dy, sz - dz, sbb, origin, forcedPlace, leafHack);
                        }
                        continue;
                     }
                  }

                  this.placeProvidedBlock(world, stateProvider, random, sx + dx, sy + dy, sz + dz, sbb, origin, forcedPlace, leafHack);
                  this.placeProvidedBlock(world, stateProvider, random, sx + dx, sy + dy, sz - dz, sbb, origin, forcedPlace, leafHack);
                  this.placeProvidedBlock(world, stateProvider, random, sx - dx, sy + dy, sz + dz, sbb, origin, forcedPlace, leafHack);
                  this.placeProvidedBlock(world, stateProvider, random, sx - dx, sy + dy, sz - dz, sbb, origin, forcedPlace, leafHack);
                  this.placeProvidedBlock(world, stateProvider, random, sx + dx, sy - dy, sz + dz, sbb, origin, forcedPlace, leafHack);
                  this.placeProvidedBlock(world, stateProvider, random, sx + dx, sy - dy, sz - dz, sbb, origin, forcedPlace, leafHack);
                  this.placeProvidedBlock(world, stateProvider, random, sx - dx, sy - dy, sz + dz, sbb, origin, forcedPlace, leafHack);
                  this.placeProvidedBlock(world, stateProvider, random, sx - dx, sy - dy, sz - dz, sbb, origin, forcedPlace, leafHack);
               }
            }
         }
      }
   }

   @NotNull
   protected XoroshiroRandomSource getInterChunkDecoRNG(WorldGenLevel level) {
      return new XoroshiroRandomSource(level.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
   }
}
