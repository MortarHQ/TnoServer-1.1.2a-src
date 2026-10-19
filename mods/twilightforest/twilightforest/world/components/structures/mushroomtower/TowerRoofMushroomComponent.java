package twilightforest.world.components.structures.mushroomtower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
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
import twilightforest.world.components.structures.lichtower.TowerRoofComponent;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class TowerRoofMushroomComponent extends TowerRoofComponent {
   public TowerRoofMushroomComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMTRoofMush.get(), nbt);
   }

   public TowerRoofMushroomComponent(int i, TowerWingComponent wing, float pHang, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFMTRoofMush.get(), i, x, y, z);
      this.height = wing.size;
      int overhang = (int)(this.height * pHang);
      this.size = this.height + overhang * 2;
      this.setOrientation(Direction.SOUTH);
      this.boundingBox = new BoundingBox(
         wing.getBoundingBox().minX() - overhang,
         wing.getBoundingBox().maxY() + 2,
         wing.getBoundingBox().minZ() - overhang,
         wing.getBoundingBox().maxX() + overhang,
         wing.getBoundingBox().maxY() + this.height + 1,
         wing.getBoundingBox().maxZ() + overhang
      );
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      for (int y = 0; y <= this.height; y++) {
         int radius = (int)(Mth.sin((y + this.height / 1.2F) / (this.height * 2.05F) * 3.14F) * this.size / 2.0F);
         int hollow = Mth.floor(radius * 0.9F);
         if (this.height - y < 3) {
            hollow = -1;
         }

         this.makeCircle(world, y, radius, hollow, sbb);
      }
   }

   private void makeCircle(WorldGenLevel world, int y, int radius, int hollow, BoundingBox sbb) {
      int cx = this.size / 2;
      int cz = this.size / 2;

      for (int dx = -radius; dx <= radius; dx++) {
         for (int dz = -radius; dz <= radius; dz++) {
            float dist = Mth.sqrt(dx * dx + dz * dz);
            if (dist <= radius + 0.5F) {
               if (dist > hollow) {
                  this.placeBlock(world, this.deco.accentState, dx + cx, y, dz + cz, sbb);
               } else {
                  this.placeBlock(world, this.deco.accentState.getBlock().defaultBlockState(), dx + cx, y, dz + cz, sbb);
               }
            }
         }
      }
   }
}
