package twilightforest.world.components.structures.lichtower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class TowerBeardAttachedComponent extends TowerBeardComponent {
   public TowerBeardAttachedComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTBA.get(), nbt);
   }

   public TowerBeardAttachedComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTBA.get(), i, wing, x, y, z);
      this.boundingBox = new BoundingBox(
         wing.getBoundingBox().minX(),
         wing.getBoundingBox().minY() - this.height - 1,
         wing.getBoundingBox().minZ(),
         wing.getBoundingBox().maxX(),
         wing.getBoundingBox().maxY() - 1,
         wing.getBoundingBox().maxZ()
      );
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.makeAttachedBeard(world, rand, sbb);
   }

   private void makeAttachedBeard(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      for (int y = 0; y <= this.height; y++) {
         int min = y + 1;
         int max = this.size - y;
         this.generateBox(world, sbb, 0, this.height - y, min, max, this.height - y, max, false, rand, TFStructureComponentOld.getStrongholdStones());
      }
   }
}
