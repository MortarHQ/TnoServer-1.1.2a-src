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

public class DarkTowerRoofFourPostComponent extends DarkTowerRoofComponent {
   public DarkTowerRoofFourPostComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTRFP.get(), nbt);
   }

   public DarkTowerRoofFourPostComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTRFP.get(), i, wing, x, y, z);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);
      this.makeSmallAntenna(world, sbb, 4, this.size - 2, this.size - 2);
      this.makeSmallAntenna(world, sbb, 5, 1, this.size - 2);
      this.makeSmallAntenna(world, sbb, 6, this.size - 2, 1);
      this.makeSmallAntenna(world, sbb, 7, 1, 1);
   }

   private void makeSmallAntenna(WorldGenLevel world, BoundingBox sbb, int height, int x, int z) {
      for (int y = 1; y < height; y++) {
         this.placeBlock(world, this.deco.blockState, x, y, z, sbb);
      }

      this.placeBlock(world, this.deco.accentState, x, height, z, sbb);
      this.placeBlock(world, this.deco.accentState, x, height + 1, z, sbb);
      this.placeBlock(world, this.deco.accentState, x + 1, height + 1, z, sbb);
      this.placeBlock(world, this.deco.accentState, x - 1, height + 1, z, sbb);
      this.placeBlock(world, this.deco.accentState, x, height + 1, z + 1, sbb);
      this.placeBlock(world, this.deco.accentState, x, height + 1, z - 1, sbb);
      this.placeBlock(world, this.deco.accentState, x, height + 2, z, sbb);
   }
}
