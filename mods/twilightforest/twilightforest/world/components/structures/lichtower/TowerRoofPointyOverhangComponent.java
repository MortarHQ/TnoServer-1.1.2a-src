package twilightforest.world.components.structures.lichtower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class TowerRoofPointyOverhangComponent extends TowerRoofPointyComponent {
   public TowerRoofPointyOverhangComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRPO.get(), nbt);
   }

   public TowerRoofPointyOverhangComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRPO.get(), i, wing, x, y, z);
      this.setOrientation(wing.getOrientation());
      this.size = wing.size + 2;
      this.height = this.size;
      this.makeOverhangBB(wing);
   }
}
