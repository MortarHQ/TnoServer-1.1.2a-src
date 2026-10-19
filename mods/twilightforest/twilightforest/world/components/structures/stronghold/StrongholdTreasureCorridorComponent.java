package twilightforest.world.components.structures.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;

public class StrongholdTreasureCorridorComponent extends KnightStrongholdComponent {
   public StrongholdTreasureCorridorComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFSTC.get(), nbt);
   }

   public StrongholdTreasureCorridorComponent(int i, Direction facing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFSTC.get(), i, facing, x, y, z);
   }

   @Override
   public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
      return KnightStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 7, 27, facing);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(parent, list, random);
      this.addDoor(4, 1, 0);
      this.addNewComponent(parent, list, random, Rotation.NONE, 4, 1, 27);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.placeStrongholdWalls(world, sbb, 0, 0, 0, 8, 6, 26, rand, this.deco.randomBlocks);
      this.placeWallStatue(world, 1, 1, 9, Rotation.CLOCKWISE_90, sbb);
      this.placeWallStatue(world, 1, 1, 17, Rotation.CLOCKWISE_90, sbb);
      this.placeWallStatue(world, 7, 1, 9, Rotation.COUNTERCLOCKWISE_90, sbb);
      this.placeWallStatue(world, 7, 1, 17, Rotation.COUNTERCLOCKWISE_90, sbb);
      Rotation rotation = (this.boundingBox.minX() ^ this.boundingBox.minZ()) % 2 == 0 ? Rotation.NONE : Rotation.CLOCKWISE_180;
      this.placeTreasureRotated(
         world,
         8,
         2,
         13,
         rotation == Rotation.NONE ? this.getOrientation().getClockWise() : this.getOrientation().getCounterClockWise(),
         rotation,
         TFLootTables.STRONGHOLD_CACHE,
         sbb
      );
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, true), 8, 3, 12, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, true), 8, 3, 13, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, true), 8, 3, 14, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.fenceState, 8, 2, 12, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.fenceState, 8, 2, 14, rotation, sbb);
      this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.SOUTH), 7, 1, 12, rotation, sbb);
      this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.WEST), 7, 1, 13, rotation, sbb);
      this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.NORTH), 7, 1, 14, rotation, sbb);
      this.placeDoors(world, sbb);
   }
}
