package twilightforest.world.components.structures.darktower;

import net.minecraft.core.BlockPos;
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
import twilightforest.world.components.structures.lichtower.TowerRoofComponent;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class DarkTowerRoofComponent extends TowerRoofComponent {
   public DarkTowerRoofComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this((StructurePieceType)TFStructurePieceTypes.TFDTRooS.get(), nbt);
   }

   public DarkTowerRoofComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
   }

   public DarkTowerRoofComponent(StructurePieceType piece, int i, TowerWingComponent wing, int x, int y, int z) {
      super(piece, i, x, y, z);
      this.setOrientation(wing.getOrientation());
      this.size = wing.size;
      this.height = 12;
      this.makeCapBB(wing);
      this.spawnListIndex = 1;
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
      for (int x = 0; x <= this.size - 1; x++) {
         for (int z = 0; z <= this.size - 1; z++) {
            if (x == 0 || x == this.size - 1 || z == 0 || z == this.size - 1) {
               this.placeBlock(world, this.deco.fenceState, x, 1, z, sbb);
            }
         }
      }

      this.placeBlock(world, this.deco.accentState, 0, 1, 0, sbb);
      this.placeBlock(world, this.deco.accentState, this.size - 1, 1, 0, sbb);
      this.placeBlock(world, this.deco.accentState, 0, 1, this.size - 1, sbb);
      this.placeBlock(world, this.deco.accentState, this.size - 1, 1, this.size - 1, sbb);
   }
}
