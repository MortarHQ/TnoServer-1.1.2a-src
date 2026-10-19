package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class MazeEntranceShaftComponent extends TFStructureComponentOld {
   public MazeEntranceShaftComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMES.get(), nbt);
   }

   public MazeEntranceShaftComponent(int i, RandomSource rand, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMES.get(), i, new BoundingBox(x, y, z, x + 6 - 1, y, z + 6 - 1).encapsulate(new BlockPos(x, 0, z)));
      this.setOrientation(Plane.HORIZONTAL.getRandomDirection(rand));
   }

   public void addChildren(StructurePiece structurecomponent, StructurePieceAccessor list, RandomSource random) {
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      MutableBlockPos pos = chunkPosIn.getWorldPosition().mutable().setX(this.boundingBox.minX()).setZ(this.boundingBox.minZ());
      this.boundingBox.encapsulate(pos.setY(generator.getSeaLevel() - 9));
      this.generateBox(world, sbb, 0, 0, 0, 5, this.boundingBox.getYSpan(), 5, ((Block)TFBlocks.MAZESTONE_BRICK.get()).defaultBlockState(), AIR, true);
      this.generateAirBox(world, sbb, 1, 0, 1, 4, generator.getSeaLevel() + 11 - this.boundingBox.minY(), 4);
   }
}
