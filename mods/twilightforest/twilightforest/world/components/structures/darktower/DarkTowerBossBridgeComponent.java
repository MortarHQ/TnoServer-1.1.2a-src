package twilightforest.world.components.structures.darktower;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class DarkTowerBossBridgeComponent extends DarkTowerBridgeComponent {
   public DarkTowerBossBridgeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTBB.get(), nbt);
   }

   protected DarkTowerBossBridgeComponent(int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTBB.get(), i, x, y, z, pSize, pHeight, direction);
   }

   @Override
   public boolean makeTowerWing(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, wingSize, direction);
      DarkTowerBossTrapComponent wing = new DarkTowerBossTrapComponent(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
      list.addPiece(wing);
      wing.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation);
      return true;
   }
}
