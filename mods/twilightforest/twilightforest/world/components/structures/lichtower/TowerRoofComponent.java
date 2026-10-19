package twilightforest.world.components.structures.lichtower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class TowerRoofComponent extends TFStructureComponentOld {
   protected int size;
   protected int height;

   public TowerRoofComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this((StructurePieceType)TFStructurePieceTypes.TFLTRoo.get(), nbt);
   }

   public TowerRoofComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
      this.size = nbt.getInt("roofSize");
      this.height = nbt.getInt("roofHeight");
   }

   public TowerRoofComponent(StructurePieceType type, int i, int x, int y, int z) {
      super(type, i, x, y, z);
      this.spawnListIndex = -1;
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putInt("roofSize", this.size);
      tagCompound.putInt("roofHeight", this.height);
   }

   protected void makeAttachedOverhangBB(TowerWingComponent wing) {
      switch (this.getOrientation()) {
         case SOUTH:
            this.boundingBox = new BoundingBox(
               wing.getBoundingBox().minX(),
               wing.getBoundingBox().maxY(),
               wing.getBoundingBox().minZ() - 1,
               wing.getBoundingBox().maxX() + 1,
               wing.getBoundingBox().maxY() + this.height - 1,
               wing.getBoundingBox().maxZ() + 1
            );
            break;
         case WEST:
            this.boundingBox = new BoundingBox(
               wing.getBoundingBox().minX() - 1,
               wing.getBoundingBox().maxY(),
               wing.getBoundingBox().minZ(),
               wing.getBoundingBox().maxX() + 1,
               wing.getBoundingBox().maxY() + this.height - 1,
               wing.getBoundingBox().maxZ() + 1
            );
            break;
         case EAST:
            this.boundingBox = new BoundingBox(
               wing.getBoundingBox().minX() - 1,
               wing.getBoundingBox().maxY(),
               wing.getBoundingBox().minZ() - 1,
               wing.getBoundingBox().maxX(),
               wing.getBoundingBox().maxY() + this.height - 1,
               wing.getBoundingBox().maxZ() + 1
            );
            break;
         case NORTH:
            this.boundingBox = new BoundingBox(
               wing.getBoundingBox().minX() - 1,
               wing.getBoundingBox().maxY(),
               wing.getBoundingBox().minZ() - 1,
               wing.getBoundingBox().maxX() + 1,
               wing.getBoundingBox().maxY() + this.height - 1,
               wing.getBoundingBox().maxZ()
            );
      }
   }

   protected void makeCapBB(TowerWingComponent wing) {
      this.boundingBox = new BoundingBox(
         wing.getBoundingBox().minX(),
         wing.getBoundingBox().maxY(),
         wing.getBoundingBox().minZ(),
         wing.getBoundingBox().maxX(),
         wing.getBoundingBox().maxY() + this.height,
         wing.getBoundingBox().maxZ()
      );
   }

   protected void makeOverhangBB(TowerWingComponent wing) {
      this.boundingBox = new BoundingBox(
         wing.getBoundingBox().minX() - 1,
         wing.getBoundingBox().maxY(),
         wing.getBoundingBox().minZ() - 1,
         wing.getBoundingBox().maxX() + 1,
         wing.getBoundingBox().maxY() + this.height - 1,
         wing.getBoundingBox().maxZ() + 1
      );
   }

   public void postProcess(
      WorldGenLevel worldIn,
      StructureManager manager,
      ChunkGenerator generator,
      RandomSource randomIn,
      BoundingBox structureBoundingBoxIn,
      ChunkPos chunkPosIn,
      BlockPos blockPos
   ) {
   }

   public boolean fits(TowerWingComponent parent, StructurePieceAccessor list) {
      return list.findCollisionPiece(this.boundingBox) == parent;
   }
}
