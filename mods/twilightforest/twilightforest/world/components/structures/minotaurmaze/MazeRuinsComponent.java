package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class MazeRuinsComponent extends TFStructureComponentOld {
   public MazeRuinsComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMRuins.get(), nbt);
   }

   public MazeRuinsComponent(int i, int x, int y, int z) {
      super(
         (StructurePieceType)TFStructurePieceTypes.TFMMRuins.get(),
         i,
         BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 0, 0, 0, Direction.SOUTH, false)
      );
      this.setOrientation(Direction.SOUTH);
   }

   public void addChildren(StructurePiece structurecomponent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(structurecomponent, list, random);
      MinotaurMazeComponent maze = new MinotaurMazeComponent(1, this.boundingBox.minX(), this.boundingBox.minY() - 14, this.boundingBox.minZ(), 1, random);
      list.addPiece(maze);
      maze.addChildren(this, list, random);
      MazeEntranceShaftComponent mazeEnter = new MazeEntranceShaftComponent(
         2, random, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1
      );
      list.addPiece(mazeEnter);
      mazeEnter.addChildren(this, list, random);
      MazeMoundComponent mazeAbove = new MazeMoundComponent(2, random, this.boundingBox.minX() - 14, this.boundingBox.maxY(), this.boundingBox.minZ() - 14);
      list.addPiece(mazeAbove);
      mazeAbove.addChildren(this, list, random);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
   }
}
