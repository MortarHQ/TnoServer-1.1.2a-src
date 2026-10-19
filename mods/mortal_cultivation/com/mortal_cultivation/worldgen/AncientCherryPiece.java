package com.mortal_cultivation.worldgen;

import com.mortal_cultivation.block.SpiritLogBlock;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public final class AncientCherryPiece extends StructurePiece {
   private final BlockPos origin;
   private final long layoutSeed;
   private volatile Map<Long, int[]> chunkVoxels;

   public AncientCherryPiece(BlockPos origin, long layoutSeed) {
      super(
         (StructurePieceType)WorldgenRegistry.ANCIENT_CHERRY_TREE_PIECE.get(),
         0,
         new BoundingBox(origin.getX() - 48, origin.getY() + -16, origin.getZ() - 48, origin.getX() + 48, origin.getY() + 104, origin.getZ() + 48)
      );
      this.origin = origin.immutable();
      this.layoutSeed = layoutSeed;
   }

   public AncientCherryPiece(CompoundTag tag) {
      super((StructurePieceType)WorldgenRegistry.ANCIENT_CHERRY_TREE_PIECE.get(), tag);
      this.origin = new BlockPos(tag.getInt("OriginX"), tag.getInt("OriginY"), tag.getInt("OriginZ"));
      this.layoutSeed = tag.getLong("LayoutSeed");
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putInt("OriginX", this.origin.getX());
      tag.putInt("OriginY", this.origin.getY());
      tag.putInt("OriginZ", this.origin.getZ());
      tag.putLong("LayoutSeed", this.layoutSeed);
   }

   public BlockPos getLocatorPosition() {
      return this.origin;
   }

   private synchronized Map<Long, int[]> voxels() {
      if (this.chunkVoxels == null) {
         AncientCherryLayout.Tree tree = AncientCherryLayout.create(this.layoutSeed);
         Map<Long, List<Integer>> grouped = new HashMap<>();
         tree.logs().forEach((point, axis) -> this.add(grouped, point, axis.ordinal()));
         tree.leaves().forEach((point, distance) -> this.add(grouped, point, distance + 2));
         tree.vines()
            .entrySet()
            .stream()
            .sorted(Entry.comparingByKey(Comparator.comparingInt(AncientCherryLayout.Point::y).reversed()))
            .forEach(entry -> this.add(grouped, entry.getKey(), entry.getValue() ? 10 : 9));
         Map<Long, int[]> compact = new HashMap<>();
         grouped.forEach((chunk, values) -> compact.put(chunk, values.stream().mapToInt(Integer::intValue).toArray()));
         this.chunkVoxels = Map.copyOf(compact);
      }

      return this.chunkVoxels;
   }

   private void add(Map<Long, List<Integer>> grouped, AncientCherryLayout.Point point, int kind) {
      long chunk = ChunkPos.asLong(Math.floorDiv(this.origin.getX() + point.x(), 16), Math.floorDiv(this.origin.getZ() + point.z(), 16));
      int packed = point.x() + 48 | point.z() + 48 << 7 | point.y() + 16 << 14 | kind << 21;
      grouped.computeIfAbsent(chunk, ignored -> new ArrayList<>()).add(packed);
   }

   public void postProcess(
      WorldGenLevel level, StructureManager structures, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot
   ) {
      int[] voxels = this.voxels().get(chunkPos.toLong());
      if (voxels != null) {
         BlockState bark = (BlockState)((SpiritLogBlock)BlockRegistry.SPIRIT_LOG.get()).defaultBlockState().setValue(SpiritLogBlock.NATURAL, true);
         Map<Long, Integer> rootBottoms = new HashMap<>();
         MutableBlockPos pos = new MutableBlockPos();

         for (int voxel : voxels) {
            int x = (voxel & 127) - 48;
            int z = (voxel >>> 7 & 127) - 48;
            int y = (voxel >>> 14 & 127) - 16;
            int kind = voxel >>> 21;
            pos.set(this.origin.getX() + x, this.origin.getY() + y, this.origin.getZ() + z);
            BlockState state;
            if (kind < 3) {
               Axis axis = switch (kind) {
                  case 0 -> Axis.X;
                  case 2 -> Axis.Z;
                  default -> Axis.Y;
               };
               state = (BlockState)bark.setValue(RotatedPillarBlock.AXIS, axis);
            } else if (kind <= 8) {
               state = (BlockState)((BlockState)Blocks.CHERRY_LEAVES.defaultBlockState().setValue(LeavesBlock.DISTANCE, kind - 2))
                  .setValue(LeavesBlock.PERSISTENT, false);
            } else {
               state = kind == 10
                  ? (BlockState)Blocks.CAVE_VINES.defaultBlockState().setValue(BlockStateProperties.AGE_25, 25)
                  : Blocks.CAVE_VINES_PLANT.defaultBlockState();
               if (!chunkBox.isInside(pos) || !state.canSurvive(level, pos)) {
                  continue;
               }
            }

            if (place(level, chunkBox, pos, state, kind < 3 && y <= 8) && kind < 3 && y <= 2) {
               rootBottoms.merge(ChunkPos.asLong(pos.getX(), pos.getZ()), pos.getY(), Math::min);
            }
         }

         rootBottoms.forEach((column, yx) -> {
            int xx = ChunkPos.getX(column);
            int zx = ChunkPos.getZ(column);

            for (int depth = yx - 1; depth >= this.origin.getY() + -16; depth--) {
               pos.set(xx, depth, zx);
               if (!chunkBox.isInside(pos) || !level.getBlockState(pos).canBeReplaced() || !place(level, chunkBox, pos, bark, false)) {
                  break;
               }
            }
         });
         this.petals(level, chunkBox);
      }
   }

   private void petals(WorldGenLevel level, BoundingBox chunkBox) {
      MutableBlockPos pos = new MutableBlockPos();

      for (int x = Math.max(chunkBox.minX(), this.origin.getX() - 30); x <= Math.min(chunkBox.maxX(), this.origin.getX() + 30); x++) {
         for (int z = Math.max(chunkBox.minZ(), this.origin.getZ() - 30); z <= Math.min(chunkBox.maxZ(), this.origin.getZ() + 30); z++) {
            int dx = x - this.origin.getX();
            int dz = z - this.origin.getZ();
            long grain = SpiritSkyIslandLayout.mix(this.layoutSeed ^ x * 73428767L ^ z * 19349663L);
            if (dx * dx + dz * dz <= 900 && Math.floorMod(grain, 7) == 0) {
               for (int y = this.origin.getY() + 7; y >= this.origin.getY() - 7; y--) {
                  pos.set(x, y, z);
                  if (chunkBox.isInside(pos) && level.isEmptyBlock(pos) && Blocks.PINK_PETALS.defaultBlockState().canSurvive(level, pos)) {
                     level.setBlock(pos, Blocks.PINK_PETALS.defaultBlockState(), 2);
                     break;
                  }
               }
            }
         }
      }
   }

   private static boolean place(WorldGenLevel level, BoundingBox box, BlockPos pos, BlockState state, boolean ground) {
      if (box.isInside(pos) && !level.isOutsideBuildHeight(pos)) {
         BlockState previous = level.getBlockState(pos);
         return !previous.hasBlockEntity()
               && previous.getFluidState().isEmpty()
               && (
                  previous.isAir()
                     || previous.is(BlockTags.REPLACEABLE_BY_TREES)
                     || previous.is(BlockTags.LEAVES)
                     || ground && (previous.is(BlockTags.DIRT) || previous.is(BlockTags.BASE_STONE_OVERWORLD))
               )
            ? level.setBlock(pos, state, 2)
            : false;
      } else {
         return false;
      }
   }
}
