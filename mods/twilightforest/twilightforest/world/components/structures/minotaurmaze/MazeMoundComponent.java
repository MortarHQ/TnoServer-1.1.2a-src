package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Plane;
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
import twilightforest.world.components.structures.TFStructureComponentOld;

public class MazeMoundComponent extends TFStructureComponentOld {
   public static final int DIAMETER = 35;
   private MazeUpperEntranceComponent mazeAbove;

   public MazeMoundComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMMound.get(), nbt);
   }

   public MazeMoundComponent(int i, RandomSource rand, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMMound.get(), i, new BoundingBox(x, y, z, x + 35, y + 12, z + 35));
      this.setOrientation(Plane.HORIZONTAL.getRandomDirection(rand));
   }

   public void addChildren(StructurePiece structurecomponent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(structurecomponent, list, random);
      this.mazeAbove = new MazeUpperEntranceComponent(3, random, this.boundingBox.minX() + 10, this.boundingBox.minY(), this.boundingBox.minZ() + 10);
      list.addPiece(this.mazeAbove);
      this.mazeAbove.addChildren(this, list, random);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
   }
}
