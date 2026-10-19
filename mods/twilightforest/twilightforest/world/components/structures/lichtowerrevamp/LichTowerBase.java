package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.SpawnIndexProvider;
import twilightforest.world.components.structures.TwilightJigsawPiece;
import twilightforest.world.components.structures.util.SortablePiece;

public final class LichTowerBase extends TwilightJigsawPiece implements PieceBeardifierModifier, SpawnIndexProvider, SortablePiece {
   @Autowired
   private static LichTowerUtil lichTowerUtil;
   private final int casketWingIndex;

   public LichTowerBase(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_TOWER_BASE.get(), compoundTag, ctx, readSettings(compoundTag));
      this.casketWingIndex = compoundTag.getInt("CasketWingIdx");
      LichTowerUtil.addDefaultProcessors(this.placeSettings.addProcessor(LichTowerBase.TrimProcessor.INSTANCE));
   }

   public LichTowerBase(StructureTemplateManager structureManager, JigsawPlaceContext jigsawContext) {
      super(
         (StructurePieceType)TFStructurePieceTypes.LICH_TOWER_BASE.get(), 1, structureManager, TwilightForestMod.prefix("lich_tower/tower_base"), jigsawContext
      );
      this.boundingBox = BoundingBoxUtils.cloneWithAdjustments(this.boundingBox, 0, 0, 0, 0, 30, 0);
      this.casketWingIndex = this.firstMatchIndex(r -> "twilightforest:lich_tower/bridge".equals(r.target()));
      LichTowerUtil.addDefaultProcessors(this.placeSettings.addProcessor(LichTowerBase.TrimProcessor.INSTANCE));
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag structureTag) {
      super.addAdditionalSaveData(ctx, structureTag);
      structureTag.putInt("CasketWingIdx", this.casketWingIndex);
   }

   @Override
   protected void processJigsaw(
      TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context, JigsawRecord connection, int jigsawIndex
   ) {
      switch (connection.target()) {
         case "twilightforest:lich_tower/tower_below":
            LichTowerSegment.buildTowerBySegments(
               pieceAccessor,
               context,
               connection.pos(),
               connection.orientation(),
               this,
               this.structureManager,
               context.random().nextIntBetweenInclusive(12, 15)
            );
            break;
         case "twilightforest:lich_tower/bridge":
            ResourceLocation room;
            if (jigsawIndex == this.casketWingIndex) {
               room = lichTowerUtil.getKeepsakeCasketRoom(context.random());
            } else {
               room = null;
            }

            if (room != null || connection.pos().getY() < 6) {
               LichTowerWingBridge.tryRoomAndBridge(this, pieceAccessor, context, connection, this.structureManager, true, 4, true, this.genDepth + 1, room);
            }
            break;
         case "twilightforest:lich_tower/decor":
            ResourceLocation decorIdx = lichTowerUtil.rollRandomDecor(context.random(), true);
            JigsawPlaceContext placeableJunctionx = JigsawPlaceContext.pickPlaceableJunction(
               this.templatePosition(),
               connection.pos(),
               connection.orientation(),
               this.structureManager,
               decorIdx,
               "twilightforest:lich_tower/decor",
               context.random()
            );
            if (placeableJunctionx != null) {
               LichTowerRoomDecor decor = new LichTowerRoomDecor(this.genDepth + 1, this.structureManager, decorIdx, placeableJunctionx);
               pieceAccessor.addPiece(decor);
               decor.addJigsaws(this, pieceAccessor, context);
            }
            break;
         case "twilightforest:lich_tower/tower_trim":
            ResourceLocation decorId = TwilightForestMod.prefix("lich_tower/central_trim");
            JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
               this.templatePosition(),
               connection.pos(),
               connection.orientation(),
               this.structureManager,
               decorId,
               "twilightforest:lich_tower/tower_trim",
               context.random()
            );
            if (placeableJunction != null) {
               LichTowerBaseTrim decor = new LichTowerBaseTrim(this.structureManager, placeableJunction);
               pieceAccessor.addPiece(decor);
               decor.addJigsaws(this, pieceAccessor, context);
            }
      }
   }

   @Override
   protected void handleDataMarker(
      String label, BlockPos pos, WorldGenLevel level, RandomSource random, BoundingBox chunkBounds, ChunkGenerator chunkGen, Rotation rotation
   ) {
      String[] splitLabel = label.split(":");
      if (splitLabel.length == 2 && "candle".equals(splitLabel[0]) && StringUtils.isNumeric(splitLabel[1])) {
         level.removeBlock(pos, false);
         boolean majorCandle = Integer.parseInt(splitLabel[1]) == 2;
         if (!majorCandle && random.nextInt(3) != 0) {
            return;
         }

         int candleCount = majorCandle ? 3 : 1 + random.nextInt(2);
         BlockState candleBlock = (BlockState)((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true))
            .setValue(CandleBlock.CANDLES, candleCount);
         level.setBlock(pos, candleBlock, 3);
      }
   }

   @Override
   public BoundingBox getBeardifierBox() {
      return this.boundingBox;
   }

   @Override
   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.BEARD_BOX;
   }

   @Override
   public int getGroundLevelDelta() {
      return 1;
   }

   @Override
   public int getSpawnIndex() {
      return 1;
   }

   @Override
   public int getSortKey() {
      return 1;
   }

   private static class TrimProcessor extends StructureProcessor {
      private static final LichTowerBase.TrimProcessor INSTANCE = new LichTowerBase.TrimProcessor();

      @Nullable
      public StructureBlockInfo process(
         LevelReader level,
         BlockPos origin,
         BlockPos centerBottom,
         StructureBlockInfo originalBlockInfo,
         StructureBlockInfo modifiedBlockInfo,
         StructurePlaceSettings settings,
         @Nullable StructureTemplate template
      ) {
         return modifiedBlockInfo.state().is(Blocks.POLISHED_ANDESITE_STAIRS) && level.getBlockState(modifiedBlockInfo.pos()).is(BlockTags.STONE_BRICKS)
            ? null
            : super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
      }

      protected StructureProcessorType<?> getType() {
         return null;
      }
   }
}
