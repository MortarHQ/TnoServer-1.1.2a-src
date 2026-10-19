package twilightforest.world.components.structures.darktower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class DarkTowerBeardComponent extends TFStructureComponentOld {
   protected final int size;
   protected final int height;

   public DarkTowerBeardComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTBea.get(), nbt);
      this.size = nbt.getInt("beardSize");
      this.height = nbt.getInt("beardHeight");
   }

   public DarkTowerBeardComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTBea.get(), i, x, y, z);
      this.setOrientation(wing.getOrientation());
      this.size = wing.size;
      this.height = this.size / 2;
      this.boundingBox = new BoundingBox(
         wing.getBoundingBox().minX(),
         wing.getBoundingBox().minY() - this.height,
         wing.getBoundingBox().minZ(),
         wing.getBoundingBox().maxX(),
         wing.getBoundingBox().maxY(),
         wing.getBoundingBox().maxZ()
      );
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putInt("beardSize", this.size);
      tagCompound.putInt("beardHeight", this.height);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.makeDarkBeard(world, sbb, 0, 0, this.size - 1, this.height - 1, this.size - 1);
   }

   protected void makeDarkBeard(WorldGenLevel world, BoundingBox sbb, int minX, int minZ, int maxX, int maxY, int maxZ) {
      BlockState frameState = ((Block)TFBlocks.ENCASED_TOWERWOOD.get()).defaultBlockState();

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            if (x == minX || x == maxX || z == minZ || z == maxZ) {
               int length = Math.min(Math.abs(x - this.height) - 1, Math.abs(z - this.height) - 1);
               if (length == this.height - 1) {
                  length++;
               }

               if (length == -1) {
                  length = 1;
               }

               for (int y = maxY; y >= this.height - length; y--) {
                  this.placeBlock(world, frameState, x, y, z, sbb);
               }
            }
         }
      }
   }
}
