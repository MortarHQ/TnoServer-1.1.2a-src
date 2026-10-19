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

public class StrongholdSmallHallwayComponent extends KnightStrongholdComponent {
   public StrongholdSmallHallwayComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFSSH.get(), nbt);
   }

   public StrongholdSmallHallwayComponent(int i, Direction facing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFSSH.get(), i, facing, x, y, z);
   }

   @Override
   public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
      return KnightStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 7, 18, facing);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(parent, list, random);
      this.addDoor(4, 1, 0);
      this.addNewComponent(parent, list, random, Rotation.NONE, 4, 1, 18);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.placeStrongholdWalls(world, sbb, 0, 0, 0, 8, 6, 17, rand, this.deco.randomBlocks);
      this.placeWallStatue(world, 1, 1, 9, Rotation.CLOCKWISE_90, sbb);
      this.placeWallStatue(world, 7, 1, 9, Rotation.COUNTERCLOCKWISE_90, sbb);
      this.placeDoors(world, sbb);
   }
}
