package twilightforest.world.components.structures.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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

public class StrongholdCrossingComponent extends KnightStrongholdComponent {
   public StrongholdCrossingComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFSCr.get(), nbt);
   }

   public StrongholdCrossingComponent(int i, Direction facing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFSCr.get(), i, facing, x, y, z);
   }

   @Override
   public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
      return KnightStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -13, -1, 0, 18, 7, 18, facing);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(parent, list, random);
      this.addDoor(13, 1, 0);
      this.addNewComponent(parent, list, random, Rotation.NONE, 4, 1, 18);
      this.addNewComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 1, 13);
      this.addNewComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 18, 1, 4);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.placeStrongholdWalls(world, sbb, 0, 0, 0, 17, 6, 17, rand, this.deco.randomBlocks);
      this.placeCornerStatue(world, 2, 1, 2, 0, sbb);
      this.placeCornerStatue(world, 15, 1, 15, 3, sbb);
      this.fillBlocksRotated(world, sbb, 8, 1, 8, 9, 5, 9, this.deco.pillarState, Rotation.NONE);
      this.placeWallStatue(world, 8, 1, 7, Rotation.NONE, sbb);
      this.placeWallStatue(world, 7, 1, 9, Rotation.COUNTERCLOCKWISE_90, sbb);
      this.placeWallStatue(world, 9, 1, 10, Rotation.CLOCKWISE_180, sbb);
      this.placeWallStatue(world, 10, 1, 8, Rotation.CLOCKWISE_90, sbb);
      this.placeTableAndChairs(world, sbb, Rotation.NONE);
      this.placeTableAndChairs(world, sbb, Rotation.CLOCKWISE_90);
      this.placeTableAndChairs(world, sbb, Rotation.CLOCKWISE_180);
      this.placeTableAndChairs(world, sbb, Rotation.COUNTERCLOCKWISE_90);
      this.placeDoors(world, sbb);
   }

   private void placeTableAndChairs(WorldGenLevel world, BoundingBox sbb, Rotation rotation) {
      BlockState oakStairs = Blocks.OAK_STAIRS.defaultBlockState();
      this.setBlockStateRotated(world, getStairState(oakStairs, Rotation.NONE.rotate(Direction.WEST), true), 5, 1, 3, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(oakStairs, Rotation.COUNTERCLOCKWISE_90.rotate(Direction.WEST), true), 5, 1, 4, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(oakStairs, Rotation.CLOCKWISE_90.rotate(Direction.WEST), true), 6, 1, 3, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(oakStairs, Rotation.CLOCKWISE_180.rotate(Direction.WEST), true), 6, 1, 4, rotation, sbb);
      this.setBlockStateRotated(
         world,
         (BlockState)Blocks.SPRUCE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Rotation.COUNTERCLOCKWISE_90.rotate(Direction.WEST)),
         5,
         1,
         2,
         rotation,
         sbb
      );
      this.setBlockStateRotated(
         world, (BlockState)Blocks.SPRUCE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Rotation.NONE.rotate(Direction.WEST)), 7, 1, 3, rotation, sbb
      );
      this.setBlockStateRotated(
         world,
         (BlockState)Blocks.SPRUCE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Rotation.CLOCKWISE_90.rotate(Direction.WEST)),
         6,
         1,
         5,
         rotation,
         sbb
      );
      this.setBlockStateRotated(
         world,
         (BlockState)Blocks.SPRUCE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Rotation.CLOCKWISE_180.rotate(Direction.WEST)),
         4,
         1,
         4,
         rotation,
         sbb
      );
   }
}
