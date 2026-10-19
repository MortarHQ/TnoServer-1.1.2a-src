package twilightforest.world.components.structures.lichtowerrevamp;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import twilightforest.TwilightForestMod;
import twilightforest.block.AbstractSkullCandleBlock;
import twilightforest.block.CandelabraBlock;
import twilightforest.block.LightableBlock;
import twilightforest.block.SkullCandleBlock;
import twilightforest.block.entity.CandelabraBlockEntity;
import twilightforest.components.item.CandelabraData;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.TwilightJigsawPiece;

public class LichTowerFoyerDecor extends TwilightJigsawPiece implements PieceBeardifierModifier {
   public LichTowerFoyerDecor(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_FOYER_DECORATION.get(), compoundTag, ctx, readSettings(compoundTag));
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
   }

   public LichTowerFoyerDecor(int genDepth, StructureTemplateManager structureManager, JigsawPlaceContext jigsawContext) {
      super(
         (StructurePieceType)TFStructurePieceTypes.LICH_FOYER_DECORATION.get(),
         genDepth,
         structureManager,
         TwilightForestMod.prefix("lich_tower/foyer_decor"),
         jigsawContext
      );
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
   }

   @Override
   public BoundingBox getBeardifierBox() {
      return this.boundingBox;
   }

   @Override
   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.NONE;
   }

   @Override
   public int getGroundLevelDelta() {
      return 0;
   }

   @Override
   protected void processJigsaw(
      TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context, JigsawRecord connection, int jigsawIndex
   ) {
   }

   @Override
   public void postProcess(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGen,
      RandomSource random,
      BoundingBox chunkBounds,
      ChunkPos chunkPos,
      BlockPos structureCenterPos
   ) {
      super.postProcess(level, structureManager, chunkGen, random, chunkBounds, chunkPos, structureCenterPos);
      BlockPos placePos = this.getSourceJigsaw().pos().offset(this.templatePosition);
      if (chunkBounds.isInside(placePos)) {
         Rotation rotation = this.placeSettings.getRotation();
         random.setSeed(placePos.asLong() + placePos.getY());
         int noChest = level.getBlockState(placePos.above()).is(Blocks.CHISELED_STONE_BRICKS) ? -1 : 0;
         switch (random.nextInt(5) + noChest) {
            case 3:
               BlockState candelabra = (BlockState)((Block)TFBlocks.CANDELABRA.value())
                  .defaultBlockState()
                  .rotate(rotation)
                  .setValue(CandelabraBlock.LIGHTING, LightableBlock.Lighting.DIM);

               for (BooleanProperty prop : CandelabraBlock.CANDLES) {
                  candelabra = (BlockState)candelabra.setValue(prop, true);
               }

               level.setBlock(placePos, candelabra, 3);
               if (level.getBlockEntity(placePos) instanceof CandelabraBlockEntity candelabraBE) {
                  candelabraBE.setData(new CandelabraData(List.of(Blocks.CANDLE, Blocks.CANDLE, Blocks.CANDLE)));
               }
               break;
            case 4:
               BlockState chest = Blocks.CHEST.defaultBlockState().rotate(rotation.getRotated(Rotation.CLOCKWISE_180));
               level.setBlock(placePos, chest, 3);
               if (level.getBlockEntity(placePos) instanceof ChestBlockEntity chestBE) {
                  chestBE.setLootTable(TFLootTables.TOWER_FOYER, random.nextLong());
               }

               BlockState chestGap = ((BlockState)Blocks.STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.HALF, Half.TOP))
                  .rotate(rotation.getRotated(Rotation.CLOCKWISE_180));
               level.setBlock(placePos.above(), chestGap, 3);
               break;
            default:
               BlockState decorBlock = switch (random.nextInt(5)) {
                  case 1, 2 -> (BlockState)Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, random.nextIntBetweenInclusive(7, 9));
                  case 3 -> (BlockState)((BlockState)((BlockState)((AbstractSkullCandleBlock)TFBlocks.SKELETON_SKULL_CANDLE.value())
                           .defaultBlockState()
                           .setValue(SkullCandleBlock.LIGHTING, LightableBlock.Lighting.NORMAL))
                        .setValue(SkullCandleBlock.CANDLES, random.nextIntBetweenInclusive(1, 3)))
                     .setValue(SkullCandleBlock.ROTATION, random.nextIntBetweenInclusive(7, 9));
                  default -> (BlockState)((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.CANDLES, random.nextIntBetweenInclusive(1, 3)))
                     .setValue(CandleBlock.LIT, true);
               };
               level.setBlock(placePos, decorBlock.rotate(rotation), 3);
         }
      }

      BlockPos behind = placePos.relative(this.getSourceJigsaw().orientation().front().getOpposite());
      if (chunkBounds.isInside(placePos)) {
         level.getChunk(behind).markPosForPostprocessing(behind);
      }
   }
}
