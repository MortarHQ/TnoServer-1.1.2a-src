package twilightforest.world.components.structures.lichtower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class TowerRoofGableForwardsComponent extends TowerRoofComponent {
   public TowerRoofGableForwardsComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRGF.get(), nbt);
   }

   public TowerRoofGableForwardsComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRGF.get(), i, x, y, z);
      this.setOrientation(wing.getOrientation());
      this.size = wing.size + 2;
      this.height = this.size;
      this.makeAttachedOverhangBB(wing);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      BlockState birchSlab = Blocks.BIRCH_SLAB.defaultBlockState();
      BlockState birchPlanks = Blocks.BIRCH_PLANKS.defaultBlockState();
      int slopeChange = this.slopeChangeForSize();

      for (int y = 0; y <= this.height; y++) {
         int min;
         int max;
         if (y < slopeChange) {
            min = y;
            max = this.size - y - 1;
         } else {
            min = (y + slopeChange) / 2;
            max = this.size - (y + slopeChange) / 2 - 1;
         }

         for (int x = 0; x <= this.size - 2; x++) {
            for (int z = min; z <= max; z++) {
               if (z == min || z == max) {
                  this.placeBlock(world, birchPlanks, x, y, z, sbb);
               } else if (x < this.size - 2) {
                  this.placeBlock(world, birchPlanks, x, y, z, sbb);
               }
            }
         }
      }

      int top = this.size + 1 - slopeChange;
      int zMid = this.size / 2;
      this.placeBlock(world, (BlockState)birchSlab.setValue(SlabBlock.TYPE, SlabType.TOP), this.size - 1, top - 1, zMid, sbb);
      this.placeBlock(world, birchSlab, 0, top, zMid, sbb);
      this.placeBlock(world, birchSlab, this.size - 3, top, zMid, sbb);
      this.placeBlock(world, birchPlanks, this.size - 2, top, zMid, sbb);
      this.placeBlock(world, birchPlanks, this.size - 1, top, zMid, sbb);
      this.placeBlock(world, birchPlanks, this.size - 1, top + 1, zMid, sbb);
   }

   public int slopeChangeForSize() {
      if (this.size > 10) {
         return 3;
      } else {
         return this.size > 6 ? 2 : 1;
      }
   }
}
