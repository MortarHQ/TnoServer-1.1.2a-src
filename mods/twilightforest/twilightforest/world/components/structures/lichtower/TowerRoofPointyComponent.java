package twilightforest.world.components.structures.lichtower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class TowerRoofPointyComponent extends TowerRoofComponent {
   public TowerRoofPointyComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRP.get(), nbt);
   }

   public TowerRoofPointyComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
   }

   public TowerRoofPointyComponent(StructurePieceType piece, int i, TowerWingComponent wing, int x, int y, int z) {
      super(piece, i, x, y, z);
      this.setOrientation(wing.getOrientation());
      this.size = wing.size;
      this.height = this.size;
      this.makeCapBB(wing);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      BlockState birchSlab = Blocks.BIRCH_SLAB.defaultBlockState();
      BlockState birchPlanks = Blocks.BIRCH_PLANKS.defaultBlockState();

      for (int y = 0; y <= this.height; y++) {
         int slopeChange = this.slopeChangeForSize();
         int min;
         int max;
         if (y < slopeChange) {
            min = y;
            max = this.size - y - 1;
         } else {
            min = (y + slopeChange) / 2;
            max = this.size - (y + slopeChange) / 2 - 1;
         }

         int mid = min + (max - min) / 2;

         for (int x = min; x <= max; x++) {
            for (int z = min; z <= max; z++) {
               this.placeBlock(world, birchPlanks, x, y, z, sbb);
               if (x == min && (z == min || z == max) || x == max && (z == min || z == max)) {
                  this.placeBlock(world, birchSlab, x, y + 1, z, sbb);
               }

               if (((x == min || x == max) && z == mid && x % 2 == 0 || (z == min || z == max) && x == mid && z % 2 == 0) && mid != min + 1) {
                  this.placeBlock(world, birchSlab, x, y + 1, z, sbb);
               }
            }
         }
      }
   }

   public int slopeChangeForSize() {
      if (this.size > 10) {
         return 3;
      } else {
         return this.size > 6 ? 2 : 1;
      }
   }
}
