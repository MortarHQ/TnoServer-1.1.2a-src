package twilightforest.world.components.structures.finalcastle;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class FinalCastleEntranceSideTowerComponent extends FinalCastleMazeTower13Component {
   public FinalCastleEntranceSideTowerComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFFCEnSiTo.get(), nbt);
   }

   public FinalCastleEntranceSideTowerComponent(int i, int x, int y, int z, int floors, int entranceFloor, Direction direction) {
      super(
         (StructurePieceType)TFStructurePieceTypes.TFFCEnSiTo.get(),
         i,
         x,
         y,
         z,
         floors,
         entranceFloor,
         ((Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get()).defaultBlockState(),
         direction
      );
      this.addOpening(0, 1, this.size / 2, Rotation.CLOCKWISE_180);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      FinalCastleFoundation13Component foundation = new FinalCastleFoundation13Component(
         (StructurePieceType)TFStructurePieceTypes.TFFCToF13.get(),
         4,
         this,
         this.getLocatorPosition().getX(),
         this.getLocatorPosition().getY(),
         this.getLocatorPosition().getZ()
      );
      list.addPiece(foundation);
      foundation.addChildren(this, list, rand);
      TFStructureComponentOld roof = new FinalCastleRoof13PeakedComponent(
         4, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(roof);
      roof.addChildren(this, list, rand);
   }
}
