package twilightforest.world.components.structures.darktower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class DarkTowerRoofCactusComponent extends DarkTowerRoofComponent {
   public DarkTowerRoofCactusComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTRC.get(), nbt);
   }

   public DarkTowerRoofCactusComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTRC.get(), i, wing, x, y, z);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      for (int y = 1; y < 10; y++) {
         this.placeBlock(world, this.deco.blockState, this.size / 2, y, this.size / 2, sbb);
      }

      this.placeBlock(world, this.deco.accentState, this.size / 2, 10, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 - 1, 1, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 + 1, 1, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 1, this.size / 2 - 1, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 1, this.size / 2 + 1, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 + 1, 7, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 + 2, 7, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 + 2, 8, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 + 2, 9, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 + 3, 9, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 6, this.size / 2 + 1, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 6, this.size / 2 + 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 7, this.size / 2 + 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 8, this.size / 2 + 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 8, this.size / 2 + 3, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 - 1, 5, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 - 2, 5, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 - 2, 6, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 - 2, 7, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2 - 3, 7, this.size / 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 4, this.size / 2 - 1, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 4, this.size / 2 - 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 5, this.size / 2 - 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 6, this.size / 2 - 2, sbb);
      this.placeBlock(world, this.deco.accentState, this.size / 2, 6, this.size / 2 - 3, sbb);
   }
}
