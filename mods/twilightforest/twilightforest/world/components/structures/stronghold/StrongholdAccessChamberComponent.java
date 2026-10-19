package twilightforest.world.components.structures.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;

public class StrongholdAccessChamberComponent extends KnightStrongholdComponent {
   public StrongholdAccessChamberComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFSAC.get(), nbt);
   }

   public StrongholdAccessChamberComponent(int i, Direction facing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFSAC.get(), i, facing, x, y, z);
   }

   @Override
   public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
      return BoundingBox.orientBox(x, y, z, -4, 1, 0, 9, 5, 9, facing);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(parent, list, random);
      this.addNewUpperComponent(parent, list, random, Rotation.NONE, 4, 1, 9);
      this.addNewUpperComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 1, 4);
      this.addNewUpperComponent(parent, list, random, Rotation.CLOCKWISE_180, 4, 1, -1);
      this.addNewUpperComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 9, 1, 4);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.generateBox(world, sbb, 0, 0, 0, 8, 4, 8, true, rand, this.deco.randomBlocks);
      this.placeSmallDoorwayAt(world, 0, 4, 1, 8, sbb);
      this.placeSmallDoorwayAt(world, 1, 0, 1, 4, sbb);
      this.placeSmallDoorwayAt(world, 2, 4, 1, 0, sbb);
      this.placeSmallDoorwayAt(world, 3, 8, 1, 4, sbb);
      BlockState defaultState = Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
      this.generateBox(world, sbb, 2, -2, 2, 6, 0, 6, defaultState, AIR, false);
      this.generateAirBox(world, sbb, 3, -2, 3, 5, 2, 5);
      this.generateBox(world, sbb, 2, 0, 3, 2, 0, 6, getStairState(this.deco.stairState, Rotation.CLOCKWISE_180.rotate(Direction.WEST), false), AIR, false);
      this.generateBox(world, sbb, 6, 0, 2, 6, 0, 6, getStairState(this.deco.stairState, Rotation.NONE.rotate(Direction.WEST), false), AIR, false);
      this.generateBox(
         world, sbb, 3, 0, 2, 5, 0, 2, getStairState(this.deco.stairState, Rotation.COUNTERCLOCKWISE_90.rotate(Direction.WEST), false), AIR, false
      );
      this.generateBox(world, sbb, 3, 0, 6, 5, 0, 6, getStairState(this.deco.stairState, Rotation.CLOCKWISE_90.rotate(Direction.WEST), false), AIR, false);
      this.placeBlock(world, this.deco.pillarState, 2, 0, 2, sbb);
      this.placeBlock(world, ((Block)TFBlocks.TROPHY_PEDESTAL.get()).defaultBlockState(), 2, 1, 2, sbb);
      this.generateBox(world, sbb, 2, -1, 2, 6, -1, 6, ((Block)TFBlocks.STRONGHOLD_SHIELD.get()).defaultBlockState(), AIR, false);
   }

   @Override
   public boolean isComponentProtected() {
      return false;
   }
}
