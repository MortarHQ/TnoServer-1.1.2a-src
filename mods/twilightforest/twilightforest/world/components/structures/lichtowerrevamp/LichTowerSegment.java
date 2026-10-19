package twilightforest.world.components.structures.lichtowerrevamp;

import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.FrontAndTop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.data.tags.CustomTagGenerator;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.SpawnIndexProvider;
import twilightforest.world.components.structures.TwilightJigsawPiece;

public final class LichTowerSegment extends TwilightJigsawPiece implements PieceBeardifierModifier, SpawnIndexProvider {
   @Autowired
   private static LichTowerUtil lichTowerUtil;
   private final boolean putMobBridge;
   private final boolean putWings;
   private final boolean putGallery;

   public LichTowerSegment(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_TOWER_SEGMENT.get(), compoundTag, ctx, readSettings(compoundTag));
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
      stairDecay(this.genDepth, this.placeSettings);
      this.putMobBridge = compoundTag.getBoolean("put_bridge");
      this.putWings = compoundTag.getBoolean("put_wings");
      this.putGallery = compoundTag.getBoolean("put_gallery");
   }

   public LichTowerSegment(
      StructureTemplateManager structureManager,
      int genDepth,
      JigsawPlaceContext jigsawContext,
      boolean putMobBridge,
      boolean putWings,
      boolean putGallery,
      ResourceLocation template
   ) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_TOWER_SEGMENT.get(), genDepth, structureManager, template, jigsawContext);
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
      stairDecay(this.genDepth, this.placeSettings);
      this.putMobBridge = putMobBridge;
      this.putWings = putWings;
      this.putGallery = putGallery;
   }

   private static void stairDecay(int depth, StructurePlaceSettings settings) {
      int decayLevel = Mth.ceil((depth - 3) * 0.5);
      if (decayLevel >= 0) {
         StructureProcessor[] stairDecayProcessors = lichTowerUtil.getStairDecayProcessors();
         decayLevel = Math.min(decayLevel, stairDecayProcessors.length);
         settings.addProcessor(stairDecayProcessors[decayLevel]);
      }
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag structureTag) {
      super.addAdditionalSaveData(ctx, structureTag);
      structureTag.putBoolean("put_bridge", this.putMobBridge);
      structureTag.putBoolean("put_wings", this.putWings);
      structureTag.putBoolean("put_gallery", this.putGallery);
   }

   public static void buildTowerBySegments(
      StructurePieceAccessor pieceAccessor,
      GenerationContext context,
      BlockPos sourceJigsawPos,
      FrontAndTop sourceOrientation,
      TwilightJigsawPiece parentBase,
      StructureTemplateManager structureManager,
      int segments
   ) {
      ResourceLocation segmentId = TwilightForestMod.prefix("lich_tower/tower_slice");
      ArrayList<TwilightJigsawPiece> pieces = new ArrayList<>();
      TwilightJigsawPiece priorPiece = parentBase;
      BlockPos priorJigsawOffset = sourceJigsawPos;
      FrontAndTop priorOrientation = sourceOrientation;
      int mobBridge = context.random().nextIntBetweenInclusive(0, 5);

      for (int stackIndex = 0; stackIndex < segments; stackIndex++) {
         JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
            priorPiece.templatePosition(),
            priorJigsawOffset,
            priorOrientation,
            structureManager,
            segmentId,
            "twilightforest:lich_tower/tower_below",
            context.random()
         );
         if (placeableJunction != null) {
            boolean putWings = stackIndex > segments >> 1;
            boolean putGallery = stackIndex == segments - 1;
            LichTowerSegment towerSegment = new LichTowerSegment(
               structureManager,
               priorPiece.getGenDepth() + 1,
               placeableJunction,
               mobBridge == 0,
               putWings,
               putGallery,
               TwilightForestMod.prefix("lich_tower/tower_slice")
            );
            pieceAccessor.addPiece(towerSegment);
            pieces.add(towerSegment);
            JigsawRecord firstJunction = placeableJunction.findFirst("twilightforest:lich_tower/tower_above");
            if (firstJunction == null) {
               break;
            }

            priorPiece = towerSegment;
            priorJigsawOffset = firstJunction.pos();
            priorOrientation = firstJunction.orientation();
            mobBridge = mobBridge == 0 ? context.random().nextIntBetweenInclusive(2, 5) : mobBridge - 1;
         }
      }

      JigsawPlaceContext bossRoomJunction = JigsawPlaceContext.pickPlaceableJunction(
         priorPiece.templatePosition(),
         priorJigsawOffset,
         priorOrientation,
         structureManager,
         TwilightForestMod.prefix("lich_tower/tower_boss_room"),
         "twilightforest:lich_tower/tower_below",
         context.random()
      );
      if (bossRoomJunction != null) {
         LichBossRoom bossRoom = new LichBossRoom(structureManager, bossRoomJunction);
         pieceAccessor.addPiece(bossRoom);
         bossRoom.addJigsaws(priorPiece, pieceAccessor, context);
         LichTowerSegment boundary = new LichTowerSegment(
            structureManager,
            priorPiece.getGenDepth() + 1,
            bossRoomJunction.copy(),
            false,
            false,
            false,
            TwilightForestMod.prefix("lich_tower/tower_boss_boundary")
         );
         pieceAccessor.addPiece(boundary);
         boundary.addJigsaws(priorPiece, pieceAccessor, context);
      }

      if (!pieces.isEmpty()) {
         priorPiece = pieces.removeLast();
         priorPiece.addJigsaws(pieces.isEmpty() ? parentBase : pieces.getLast(), pieceAccessor, context);
         priorPiece = parentBase;

         for (TwilightJigsawPiece piece : pieces) {
            piece.addJigsaws(priorPiece, pieceAccessor, context);
            priorPiece = piece;
         }
      }
   }

   @Override
   protected void processJigsaw(
      TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context, JigsawRecord connection, int jigsawIndex
   ) {
      switch (connection.target()) {
         case "twilightforest:lich_tower/bridge":
            if (!this.putWings) {
               return;
            }

            if (this.putGallery) {
               if (jigsawIndex == 2 && context.random().nextInt(10) == 0) {
                  LichTowerMagicGallery.tryPlaceGallery(
                     context,
                     pieceAccessor,
                     lichTowerUtil.rollTowerGallery(context.random()),
                     connection,
                     this,
                     this.genDepth + 1,
                     this.structureManager,
                     "twilightforest:lich_tower/bridge_center"
                  );
               }
            } else {
               LichTowerWingBridge.tryRoomAndBridge(this, pieceAccessor, context, connection, this.structureManager, true, 4, false, this.genDepth + 1, null);
            }
            break;
         case "twilightforest:mob_bridge":
            if (this.putMobBridge) {
               FrontAndTop orientation = connection.orientation();
               FrontAndTop forPlacement = context.random().nextBoolean()
                  ? orientation
                  : FrontAndTop.fromFrontAndTop(orientation.front(), orientation.top().getOpposite());
               ResourceLocation mobBridgeLocation = lichTowerUtil.rollRandomMobBridge(context.random());
               JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
                  this.templatePosition(),
                  connection.pos(),
                  forPlacement,
                  this.structureManager,
                  mobBridgeLocation,
                  "twilightforest:mob_bridge",
                  context.random()
               );
               if (placeableJunction != null) {
                  LichTowerSpawnerBridge mobBridgePiece = new LichTowerSpawnerBridge(
                     this.genDepth + 1, this.structureManager, mobBridgeLocation, placeableJunction, context.random().nextBoolean()
                  );
                  pieceAccessor.addPiece(mobBridgePiece);
                  mobBridgePiece.addJigsaws(this, pieceAccessor, context);
               }
            }
      }
   }

   @Override
   protected void handleDataMarker(
      String label, BlockPos pos, WorldGenLevel level, RandomSource random, BoundingBox chunkBounds, ChunkGenerator chunkGen, Rotation rotation
   ) {
      LichBossRoom.placePainting(
         label, pos, level, random, chunkBounds, this.placeSettings.getRotation(), 2, 10, CustomTagGenerator.PaintingVariantTagGenerator.LICH_TOWER_PAINTINGS
      );
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
   public int getSpawnIndex() {
      return 1;
   }
}
