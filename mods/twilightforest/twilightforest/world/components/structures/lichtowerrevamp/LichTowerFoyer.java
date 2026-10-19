package twilightforest.world.components.structures.lichtowerrevamp;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
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
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.DirectionUtil;
import twilightforest.util.RotationUtil;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.SpawnIndexProvider;
import twilightforest.world.components.structures.TwilightJigsawPiece;

public final class LichTowerFoyer extends TwilightJigsawPiece implements PieceBeardifierModifier, SpawnIndexProvider {
   private final boolean putChest;
   private final boolean chestSide;
   private final List<BlockPos> shelfPositions = new ArrayList<>();

   public LichTowerFoyer(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_TOWER_FOYER.get(), compoundTag, ctx, readSettings(compoundTag));
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
      this.putChest = compoundTag.getBoolean("put_chest");
      this.chestSide = compoundTag.getBoolean("chest_side");
   }

   public LichTowerFoyer(StructureTemplateManager structureManager, JigsawPlaceContext placeContext, boolean putChest, boolean chestSide) {
      super(
         (StructurePieceType)TFStructurePieceTypes.LICH_TOWER_FOYER.get(),
         0,
         structureManager,
         TwilightForestMod.prefix("lich_tower/tower_foyer"),
         placeContext
      );
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
      this.putChest = putChest;
      this.chestSide = chestSide;
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag structureTag) {
      super.addAdditionalSaveData(ctx, structureTag);
      structureTag.putBoolean("put_chest", this.putChest);
      structureTag.putBoolean("chest_side", this.chestSide);
   }

   @Override
   protected void processJigsaw(
      TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context, JigsawRecord connection, int jigsawIndex
   ) {
      if ("twilightforest:lich_tower/tower_base".equals(connection.target())) {
         JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
            this.templatePosition(),
            connection.pos(),
            connection.orientation(),
            this.structureManager,
            TwilightForestMod.prefix("lich_tower/tower_base"),
            "twilightforest:lich_tower/tower_base",
            context.random()
         );
         if (placeableJunction == null) {
            return;
         }

         LichTowerBase towerBase = new LichTowerBase(this.structureManager, placeableJunction);
         pieceAccessor.addPiece(towerBase);
         towerBase.addJigsaws(this, pieceAccessor, context);
      } else if ("twilightforest:shelf".equals(connection.target()) && context.random().nextFloat() <= 0.5F) {
         JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
            this.templatePosition(),
            connection.pos(),
            connection.orientation(),
            this.structureManager,
            TwilightForestMod.prefix("lich_tower/foyer_decor"),
            "twilightforest:shelf",
            context.random()
         );
         if (placeableJunction == null || this.hasShelfNeighbor(connection.pos())) {
            return;
         }

         LichTowerFoyerDecor towerBase = new LichTowerFoyerDecor(this.genDepth + 1, this.structureManager, placeableJunction);
         pieceAccessor.addPiece(towerBase);
         towerBase.addJigsaws(this, pieceAccessor, context);
         this.shelfPositions.add(connection.pos());
      }
   }

   private boolean hasShelfNeighbor(BlockPos pos) {
      for (BlockPos occupied : this.shelfPositions) {
         if (occupied.distManhattan(pos) < 1.5F) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void handleDataMarker(
      String label, BlockPos pos, WorldGenLevel level, RandomSource random, BoundingBox chunkBounds, ChunkGenerator chunkGen, Rotation rotation
   ) {
      String[] directionSplit = label.split("@");
      if (directionSplit.length != 0) {
         Rotation dataRotation = directionSplit.length == 1
            ? Rotation.CLOCKWISE_180
            : RotationUtil.getRelativeRotation(Direction.NORTH, DirectionUtil.fromStringOrElse(directionSplit[1], Direction.SOUTH));
         if (this.putChest) {
            String toMatch = this.chestSide ? "chest_a" : "chest_b";
            if (toMatch.equals(directionSplit[0])) {
               level.removeBlock(pos, false);
               Rotation stateRotation = this.placeSettings.getRotation().getRotated(dataRotation);
               level.setBlock(pos, Blocks.CHEST.defaultBlockState().rotate(stateRotation), 2);
               if (level.getBlockEntity(pos) instanceof RandomizableContainer lootBlock) {
                  lootBlock.setLootTable(TFLootTables.TOWER_ROOM, random.nextLong());
               }

               level.setBlock(pos.below(), ((Block)TFBlocks.CANOPY_PLANKS.value()).defaultBlockState(), 2);
            }
         }
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
      return 2;
   }
}
