package twilightforest.world.components.structures.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class StrongholdUpperLeftTurnComponent extends KnightStrongholdComponent {
   public StrongholdUpperLeftTurnComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFSULT.get(), nbt);
   }

   public StrongholdUpperLeftTurnComponent(int i, Direction facing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFSULT.get(), i, facing, x, y, z);
   }

   @Override
   public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
      return BoundingBox.orientBox(x, y, z, -2, -1, 0, 5, 5, 5, facing);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(parent, list, random);
      this.addNewUpperComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 5, 1, 2);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.placeUpperStrongholdWalls(world, sbb, 0, 0, 0, 4, 4, 4, rand, this.deco.randomBlocks);
      this.placeSmallDoorwayAt(world, 2, 2, 1, 0, sbb);
      this.placeSmallDoorwayAt(world, 3, 4, 1, 2, sbb);
   }

   @Override
   public boolean isComponentProtected() {
      return false;
   }
}
