package twilightforest.world.components.structures.icetower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TFStructureComponent;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class IceTowerBridgeComponent extends TFStructureComponentOld {
   private final int length;
   private final int extraZlength;

   public IceTowerBridgeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFITBri.get(), nbt);
      this.length = nbt.getInt("bridgeLength");
      this.extraZlength = nbt.getInt("extraZlength");
   }

   public IceTowerBridgeComponent(int index, int x, int y, int z, int length, int zLength, Direction direction) {
      super((StructurePieceType)TFStructurePieceTypes.TFITBri.get(), index, x, y, z);
      this.length = length;
      this.setOrientation(direction);
      this.extraZlength = zLength;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, 0, 0, -zLength, length, 6, 5 + zLength, direction, false);
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putInt("bridgeLength", this.length);
      tagCompound.putInt("extraZlength", this.extraZlength);
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponent tfStructureComponent) {
         this.deco = tfStructureComponent.deco;
      }
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.generateAirBox(world, sbb, 0, 1, 0, this.length, 5, 4);
      this.generateBox(world, sbb, 0, 0, this.extraZlength, this.length, 0, this.extraZlength + 4, this.deco.blockState, this.deco.blockState, false);
      this.generateBox(world, sbb, 0, 6, this.extraZlength, this.length, 6, this.extraZlength + 4, this.deco.blockState, this.deco.blockState, false);

      for (int x = 2; x < this.length; x += 3) {
         this.generateBox(world, sbb, x, 1, this.extraZlength, x, 5, this.extraZlength, this.deco.pillarState, this.deco.pillarState, false);
         this.generateBox(world, sbb, x, 1, this.extraZlength + 4, x, 5, this.extraZlength + 4, this.deco.pillarState, this.deco.pillarState, false);
      }
   }
}
