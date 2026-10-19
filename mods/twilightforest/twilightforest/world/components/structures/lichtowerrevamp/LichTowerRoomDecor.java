package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.TFStructureHelper;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.TwilightJigsawPiece;
import twilightforest.world.components.structures.util.SortablePiece;

public class LichTowerRoomDecor extends TwilightJigsawPiece implements PieceBeardifierModifier, SortablePiece {
   @Autowired
   private static LichTowerUtil lichTowerUtil;

   public LichTowerRoomDecor(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_TOWER_DECOR.value(), compoundTag, ctx, readSettings(compoundTag));
      LichTowerUtil.addDefaultProcessors(this.placeSettings.addProcessor(lichTowerUtil.getRoomSpawnerProcessor()));
   }

   public LichTowerRoomDecor(int genDepth, StructureTemplateManager structureManager, ResourceLocation templateLocation, JigsawPlaceContext jigsawContext) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_TOWER_DECOR.value(), genDepth, structureManager, templateLocation, jigsawContext);
      LichTowerUtil.addDefaultProcessors(this.placeSettings.addProcessor(lichTowerUtil.getRoomSpawnerProcessor()));
   }

   public static void addDecor(
      TwilightJigsawPiece parent,
      StructurePieceAccessor pieceAccessor,
      GenerationContext context,
      JigsawRecord connection,
      int newDepth,
      StructureTemplateManager structureManager
   ) {
      ResourceLocation decorId = lichTowerUtil.rollRandomDecor(context.random(), false);
      JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
         parent.templatePosition(), connection.pos(), connection.orientation(), structureManager, decorId, "twilightforest:lich_tower/decor", context.random()
      );
      if (placeableJunction != null) {
         LichTowerRoomDecor decor = new LichTowerRoomDecor(newDepth, structureManager, decorId, placeableJunction);
         pieceAccessor.addPiece(decor);
         decor.addJigsaws(parent, pieceAccessor, context);
      }
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag structureTag) {
      super.addAdditionalSaveData(ctx, structureTag);
   }

   @Override
   protected void handleDataMarker(
      String label, BlockPos pos, WorldGenLevel level, RandomSource random, BoundingBox chunkBounds, ChunkGenerator chunkGen, Rotation rotation
   ) {
      level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
      switch (label) {
         case "sapling":
            level.setBlock(pos, TFStructureHelper.randomPlant(random), 2);
            break;
         case "tree":
            ResourceKey<ConfiguredFeature<?, ?>> randomTree = TFStructureHelper.randomTree(random.nextInt(4));
            Registry<ConfiguredFeature<?, ?>> featureRegistry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
            if (!((ConfiguredFeature)featureRegistry.get(randomTree)).place(level, chunkGen, random, pos)) {
               level.setBlock(pos, TFStructureHelper.randomPlant(random), 2);
            }
      }
   }

   @Override
   protected void processJigsaw(
      TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context, JigsawRecord connection, int jigsawIndex
   ) {
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
   public int getSortKey() {
      return 2;
   }
}
