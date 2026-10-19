package twilightforest.world.components.structures.lichtower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class TowerRoofFenceComponent extends TowerRoofComponent {
   public TowerRoofFenceComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRF.get(), nbt);
   }

   public TowerRoofFenceComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRF.get(), i, x, y, z);
      this.setOrientation(wing.getOrientation());
      this.size = wing.size;
      this.height = 0;
      this.makeCapBB(wing);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      int y = this.height + 1;

      for (int x = 0; x <= this.size - 1; x++) {
         for (int z = 0; z <= this.size - 1; z++) {
            if (x == 0 || x == this.size - 1 || z == 0 || z == this.size - 1) {
               this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x, y, z, sbb);
            }
         }
      }
   }
}
