package twilightforest.world.components.structures.lichtowerrevamp;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.jetbrains.annotations.Nullable;
import twilightforest.beanification.Autowired;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.TwilightJigsawPiece;
import twilightforest.world.components.structures.util.SortablePiece;

public final class LichTowerWingBridge extends TwilightJigsawPiece implements PieceBeardifierModifier, SortablePiece {
   @Autowired
   private static LichTowerUtil lichTowerUtil;
   private final boolean fromCentral;

   public LichTowerWingBridge(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_WING_BRIDGE.get(), compoundTag, ctx, readSettings(compoundTag));
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
      this.fromCentral = compoundTag.getBoolean("from_central");
   }

   public LichTowerWingBridge(
      StructureTemplateManager structureManager, int genDepth, JigsawPlaceContext jigsawContext, ResourceLocation templateLocation, boolean fromCentral
   ) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_WING_BRIDGE.get(), genDepth, structureManager, templateLocation, jigsawContext);
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
      this.fromCentral = fromCentral;
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag structureTag) {
      super.addAdditionalSaveData(ctx, structureTag);
      structureTag.putBoolean("from_central", this.fromCentral);
   }

   @Override
   protected void processJigsaw(
      TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context, JigsawRecord record, int jigsawIndex
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
      if (this.fromCentral) {
         JigsawRecord sourceJigsaw = this.getSourceJigsaw();
         BlockPos sourcePos = this.templatePosition.offset(sourceJigsaw.pos());
         BlockPos leftPos = sourcePos.relative(sourceJigsaw.orientation().front().getClockWise(Axis.Y));
         BlockPos rightPos = sourcePos.relative(sourceJigsaw.orientation().front().getCounterClockWise(Axis.Y));
         removeIfBanister(level, leftPos, chunkBounds);
         removeIfBanister(level, leftPos.above(), chunkBounds);
         removeIfBanister(level, rightPos, chunkBounds);
         removeIfBanister(level, rightPos.below(), chunkBounds);
      }
   }

   private static void removeIfBanister(WorldGenLevel level, BlockPos pos, BoundingBox chunkBounds) {
      if (chunkBounds.isInside(pos) && level.getBlockState(pos).is(BlockTagGenerator.BANISTERS)) {
         level.removeBlock(pos, false);
      }
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
      return 1;
   }

   public static void tryRoomAndBridge(
      TwilightJigsawPiece parent,
      StructurePieceAccessor pieceAccessor,
      GenerationContext context,
      JigsawRecord connection,
      StructureTemplateManager structureManager,
      boolean fromCentralTower,
      int roomMaxSize,
      boolean generateGround,
      int newDepth,
      @Nullable ResourceLocation override
   ) {
      if (!generateGround) {
         if (fromCentralTower) {
            for (ResourceLocation bridgeId : lichTowerUtil.shuffledCenterBridges(context.random())) {
               if (tryBridge(
                  parent,
                  pieceAccessor,
                  context,
                  connection.pos(),
                  connection.orientation(),
                  structureManager,
                  true,
                  roomMaxSize,
                  false,
                  newDepth,
                  bridgeId,
                  true,
                  override,
                  false
               )) {
                  return;
               }
            }
         } else {
            for (ResourceLocation bridgeId : lichTowerUtil.shuffledRoomBridges(context.random())) {
               if (tryBridge(
                  parent,
                  pieceAccessor,
                  context,
                  connection.pos(),
                  connection.orientation(),
                  structureManager,
                  false,
                  roomMaxSize,
                  false,
                  newDepth,
                  bridgeId,
                  false,
                  override,
                  false
               )) {
                  return;
               }
            }

            for (ResourceLocation bridgeId : lichTowerUtil.shuffledEndBridges(context.random())) {
               if (tryBridge(
                  parent,
                  pieceAccessor,
                  context,
                  connection.pos(),
                  connection.orientation(),
                  structureManager,
                  false,
                  0,
                  false,
                  newDepth,
                  bridgeId,
                  false,
                  override,
                  true
               )) {
                  return;
               }
            }
         }
      }

      if (fromCentralTower) {
         tryBridge(
            parent,
            pieceAccessor,
            context,
            connection.pos(),
            connection.orientation(),
            structureManager,
            true,
            roomMaxSize,
            generateGround,
            newDepth,
            lichTowerUtil.getEnclosedCentralBridge(context.random()),
            true,
            override,
            false
         );
      } else if (!tryBridge(
         parent,
         pieceAccessor,
         context,
         connection.pos(),
         connection.orientation(),
         structureManager,
         false,
         roomMaxSize,
         generateGround,
         newDepth,
         lichTowerUtil.getDirectRoomAttachment(context.random()),
         true,
         override,
         true
      )) {
         putCover(parent, pieceAccessor, context, connection.pos(), connection.orientation(), structureManager, generateGround, newDepth);
      }
   }

   private static boolean tryBridge(
      TwilightJigsawPiece parent,
      StructurePieceAccessor pieceAccessor,
      GenerationContext context,
      BlockPos sourceJigsawPos,
      FrontAndTop sourceOrientation,
      StructureTemplateManager structureManager,
      boolean fromCentralTower,
      int roomMaxSize,
      boolean generateGround,
      int newDepth,
      @Nullable ResourceLocation bridgeId,
      boolean allowClipping,
      @Nullable ResourceLocation override,
      boolean tiny
   ) {
      JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
         parent.templatePosition(),
         sourceJigsawPos,
         sourceOrientation,
         structureManager,
         bridgeId,
         fromCentralTower ? "twilightforest:lich_tower/bridge_center" : "twilightforest:lich_tower/bridge",
         context.random()
      );
      if (placeableJunction != null) {
         LichTowerWingBridge bridge = new LichTowerWingBridge(structureManager, newDepth, placeableJunction, bridgeId, fromCentralTower);
         if ((allowClipping || pieceAccessor.findCollisionPiece(bridge.boundingBox) == null)
            && bridge.tryGenerateRoom(context, pieceAccessor, roomMaxSize, generateGround, override, tiny)) {
            pieceAccessor.addPiece(bridge);
            bridge.addJigsaws(parent, pieceAccessor, context);
            return true;
         }
      }

      return false;
   }

   public static void putCover(
      TwilightJigsawPiece parent,
      StructurePieceAccessor pieceAccessor,
      GenerationContext context,
      BlockPos sourceJigsawPos,
      FrontAndTop sourceOrientation,
      StructureTemplateManager structureManager,
      boolean noWindow,
      int newDepth
   ) {
      BlockPos parentTemplatePos = parent.templatePosition();
      BoundingBox clearance = BoundingBox.fromCorners(
         parentTemplatePos.offset(sourceJigsawPos.relative(sourceOrientation.front(), 1)),
         parentTemplatePos.offset(sourceJigsawPos.relative(sourceOrientation.front(), 3))
      );
      boolean onlyCobbleStopper = noWindow || pieceAccessor.findCollisionPiece(clearance) != null;
      ResourceLocation bridgeCoverLocation = onlyCobbleStopper
         ? lichTowerUtil.getDefaultBridgeStopper(context.random())
         : lichTowerUtil.rollRandomCover(context.random());
      JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
         parentTemplatePos, sourceJigsawPos, sourceOrientation, structureManager, bridgeCoverLocation, "twilightforest:lich_tower/bridge", context.random()
      );
      if (placeableJunction != null) {
         LichTowerWingBridge bridgeCoverPiece = new LichTowerWingBridge(structureManager, newDepth, placeableJunction, bridgeCoverLocation, false);
         pieceAccessor.addPiece(bridgeCoverPiece);
         bridgeCoverPiece.addJigsaws(parent, pieceAccessor, context);
      }
   }

   public boolean tryGenerateRoom(
      GenerationContext context,
      StructurePieceAccessor structureStart,
      int roomMaxSize,
      boolean generateGround,
      @Nullable ResourceLocation override,
      boolean tiny
   ) {
      List<JigsawRecord> spareJigsaws = this.getSpareJigsaws();
      if (this.getSpareJigsaws().isEmpty()) {
         return false;
      }

      if (override != null) {
         return tryPlaceRoom(
            context,
            structureStart,
            override,
            spareJigsaws.getFirst(),
            3,
            generateGround,
            false,
            this,
            this.genDepth + 1,
            this.structureManager,
            "twilightforest:lich_tower/room"
         );
      }

      int minSize = tiny ? 0 : 1;

      for (JigsawRecord generatingPoint : spareJigsaws) {
         for (int roomSize = Math.max(0, roomMaxSize - 1); roomSize >= minSize; roomSize--) {
            boolean roomSuccess = tryPlaceRoom(
               context,
               structureStart,
               lichTowerUtil.rollRandomRoom(context.random(), roomSize),
               generatingPoint,
               roomSize,
               generateGround,
               false,
               this,
               this.genDepth + 1,
               this.structureManager,
               "twilightforest:lich_tower/room"
            );
            if (roomSuccess) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean tryPlaceRoom(
      GenerationContext context,
      StructurePieceAccessor pieceAccessor,
      @Nullable ResourceLocation roomId,
      JigsawRecord connection,
      int roomSize,
      boolean canPutGround,
      boolean allowClipping,
      TwilightJigsawPiece parent,
      int newDepth,
      StructureTemplateManager structureManager,
      String jigsawLabel
   ) {
      JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
         parent.templatePosition(), connection.pos(), connection.orientation(), structureManager, roomId, jigsawLabel, context.random()
      );
      if (placeableJunction == null) {
         return false;
      }

      boolean generateGround = canPutGround && connection.pos().getY() < 4;
      boolean doLadder = placeableJunction.isWithoutCollision(
         structureManager, pieceAccessor, box -> BoundingBoxUtils.extrusionFrom(box, Direction.UP, Mth.ceil(box.getYSpan() * 1.5F))
      );
      LichTowerWingRoom room = new LichTowerWingRoom(
         structureManager, newDepth, placeableJunction, roomId, roomSize, generateGround, doLadder, context.random()
      );
      if (!allowClipping && pieceAccessor.findCollisionPiece(room.getBoundingBox()) != null) {
         return false;
      }

      pieceAccessor.addPiece(room);
      room.addJigsaws(parent, pieceAccessor, context);
      return true;
   }

   @Override
   public int getSortKey() {
      return 2;
   }
}
