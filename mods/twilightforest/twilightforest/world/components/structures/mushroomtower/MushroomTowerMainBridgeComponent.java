package twilightforest.world.components.structures.mushroomtower;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import twilightforest.init.TFStructurePieceTypes;

public class MushroomTowerMainBridgeComponent extends MushroomTowerBridgeComponent {
   public MushroomTowerMainBridgeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMTMB.get(), nbt);
   }

   protected MushroomTowerMainBridgeComponent(int i, int x, int y, int z, int pHeight, Direction direction) {
      super((StructurePieceType)TFStructurePieceTypes.TFMTMB.get(), i, x, y, z, 11, pHeight, direction);
   }

   @Override
   public boolean makeTowerWing(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 15, direction);
      dx = this.adjustCoordinates(dx[0], dx[1], dx[2], 15, direction, list);
      MushroomTowerMainComponent wing = new MushroomTowerMainComponent(index, dx[0], dx[1], dx[2], 15, wingHeight, direction);
      list.addPiece(wing);
      if (list instanceof StructurePiecesBuilder start) {
         wing.addChildren((StructurePiece)start.pieces.get(0), list, rand);
      }

      this.addOpening(x, y, z, rotation);
      return true;
   }
}
