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

public class TowerRoofSlabForwardsComponent extends TowerRoofSlabComponent {
   public TowerRoofSlabForwardsComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRSF.get(), nbt);
   }

   public TowerRoofSlabForwardsComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRSF.get(), i, wing, x, y, z);
      this.setOrientation(wing.getOrientation());
      this.size = wing.size + 2;
      this.height = this.size / 2;
      this.makeAttachedOverhangBB(wing);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      BlockState birchSlab = Blocks.BIRCH_SLAB.defaultBlockState();
      BlockState birchDoubleSlab = (BlockState)Blocks.BIRCH_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE);

      for (int y = 0; y <= this.height; y++) {
         int min = 2 * y;
         int max = this.size - 2 * y - 1;

         for (int x = 0; x <= max - 1; x++) {
            for (int z = min; z <= max; z++) {
               if (x != max - 1 && z != min && z != max) {
                  this.placeBlock(world, birchDoubleSlab, x, y, z, sbb);
               } else {
                  this.placeBlock(world, birchSlab, x, y, z, sbb);
               }
            }
         }
      }
   }
}
