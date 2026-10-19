package twilightforest.world.components.structures.darktower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class DarkTowerBridgeComponent extends TowerWingComponent {
   private int dSize;
   private int dHeight;

   public DarkTowerBridgeComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
   }

   public DarkTowerBridgeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTBri.get(), nbt);
   }

   protected DarkTowerBridgeComponent(StructurePieceType type, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
      super(type, i, x, y, z, 5, 5, direction);
      this.dSize = pSize;
      this.dHeight = pHeight;
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      this.makeTowerWing(list, rand, this.getGenDepth(), 4, 1, 2, this.dSize, this.dHeight, Rotation.NONE);
   }

   @Override
   public boolean makeTowerWing(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
      if (wingHeight < 6) {
         return false;
      }

      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, wingSize, direction);
      if (dx[1] + wingHeight > 255) {
         return false;
      }

      TowerWingComponent wing = new DarkTowerWingComponent(
         (StructurePieceType)TFStructurePieceTypes.TFDTWin.get(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction
      );
      StructurePiece intersect = list.findCollisionPiece(wing.getBoundingBox());
      if (intersect != null && intersect != this) {
         return false;
      }

      list.addPiece(wing);
      wing.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation);
      return true;
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.generateBox(world, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, this.deco.blockState, this.deco.blockState, false);

      for (int x = 0; x < this.size; x++) {
         this.placeBlock(world, this.deco.accentState, x, 0, 0, sbb);
         this.placeBlock(world, this.deco.accentState, x, this.height - 1, 0, sbb);
         this.placeBlock(world, this.deco.accentState, x, 0, this.size - 1, sbb);
         this.placeBlock(world, this.deco.accentState, x, this.height - 1, this.size - 1, sbb);
      }

      this.generateAirBox(world, sbb, 0, 1, 1, this.size - 1, this.height - 2, this.size - 2);
   }

   public BoundingBox getWingBB() {
      int[] dest = this.offsetTowerCoords(4, 1, 2, this.dSize, this.getOrientation());
      return BoundingBoxUtils.getComponentToAddBoundingBox(
         dest[0], dest[1], dest[2], 0, 0, 0, this.dSize - 1, this.dHeight - 1, this.dSize - 1, this.getOrientation(), false
      );
   }
}
