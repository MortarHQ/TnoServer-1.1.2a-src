package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import twilightforest.block.WroughtIronFenceBlock;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;

public class LichYardLights extends StructurePiece implements PieceBeardifierModifier {
   private final Axis placeAxis;

   public LichYardLights(BoundingBox boundingBox, Axis placeAxis) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_YARD_LIGHTS.value(), 0, boundingBox);
      this.placeAxis = placeAxis;
   }

   public LichYardLights(StructurePieceSerializationContext ctx, CompoundTag tag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_YARD_LIGHTS.value(), tag);
      this.placeAxis = tag.contains("axis") ? Axis.values()[tag.getInt("axis")] : Axis.Y;
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putInt("axis", this.placeAxis.ordinal());
   }

   public void postProcess(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGen,
      RandomSource random,
      BoundingBox chunkBounds,
      ChunkPos chunkPos,
      BlockPos structureCenterPos
   ) {
      BoundingBox boxIntersection = BoundingBoxUtils.getIntersectionOfSBBs(this.boundingBox, chunkBounds.inflatedBy(-1));
      if (boxIntersection != null) {
         for (int z = boxIntersection.minZ(); z <= boxIntersection.maxZ(); z++) {
            for (int x = boxIntersection.minX(); x <= boxIntersection.maxX(); x++) {
               int y = level.getHeight(Types.WORLD_SURFACE_WG, x, z);
               BlockPos placeAt = new BlockPos(x, y, z);
               if (level.getBlockState(placeAt).is(Blocks.AIR)
                  && random.nextFloat() <= 0.125F
                  && level.getBlockState(placeAt.north()).is(Blocks.AIR)
                  && level.getBlockState(placeAt.south()).is(Blocks.AIR)
                  && level.getBlockState(placeAt.east()).is(Blocks.AIR)
                  && level.getBlockState(placeAt.west()).is(Blocks.AIR)
                  && (
                     this.placeAxis == Axis.Z
                        ? Math.min(x - this.boundingBox.minX(), this.boundingBox.maxX() - x) < 3
                        : Math.min(z - this.boundingBox.minZ(), this.boundingBox.maxZ() - z) < 3
                  )) {
                  level.setBlock(placeAt, ((WroughtIronFenceBlock)TFBlocks.WROUGHT_IRON_FENCE.value()).defaultBlockState(), 3);
                  level.setBlock(placeAt.above(), (BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true), 3);
               }
            }
         }
      }
   }

   public BoundingBox getBeardifierBox() {
      return this.boundingBox;
   }

   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.NONE;
   }

   public int getGroundLevelDelta() {
      return 0;
   }
}
