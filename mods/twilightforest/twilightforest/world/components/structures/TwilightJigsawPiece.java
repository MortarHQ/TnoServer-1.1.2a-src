package twilightforest.world.components.structures;

import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.markerhandler.TemplateMarkerHandler;
import twilightforest.world.components.structures.util.ProgressionPiece;
import twilightforest.world.components.structures.util.StructureTemplateDefinitions;
import twilightforest.world.components.structures.util.TemplateMarkerHandlerList;
import twilightforest.world.components.structures.util.TemplatePoolInstance;

public class TwilightJigsawPiece extends TwilightTemplateStructurePiece implements ProgressionPiece, PieceBeardifierModifier {
   private static final Logger LOGGER = LogManager.getLogger("twilightforest/TwilightJigsawPiece");
   @Autowired
   private static StructureTemplateDefinitions structureTemplateDefinitions;
   private static final String NBT_JIGSAW_SOURCE = "source";
   private static final String NBT_JIGSAW_CONNECTIONS = "connections";
   private static final String NBT_TERRAIN_ADAPT = "terrain_adaptation";
   private static final String NBT_TEMPLATE_PROCESSORS = "template_processors";
   private static final String NBT_PLACE_PROJECTION = "place_projection";
   private static final String NBT_GROUND_OFFSET = "ground_offset";
   private static final String NBT_IGNORE_WATERLOG = "ignore_waterlog";
   private static final String NBT_MARKER_HANDLERS = "marker_handlers";
   private static final String NBT_RANDOMIZED_PROCESSORS = "randomized_processors";
   private final JigsawRecord sourceJigsaw;
   private final List<JigsawRecord> spareJigsaws;
   private final TerrainAdjustment terrainAdjustment;
   private final Optional<Holder<StructureProcessorList>> processors;
   private final Projection projection;
   private final Optional<Holder<TemplateMarkerHandlerList>> markerHandlers;
   private final int beardifierGroundDelta;
   private final StructureProcessorList serializedProcessors;
   private final Map<String, String> poolAliases;

   public static TwilightJigsawPiece defaultDeserialize(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
      TwilightJigsawPiece twilightJigsawPiece = new TwilightJigsawPiece(
         (StructurePieceType)TFStructurePieceTypes.TFJigsawTemplate.value(), compoundTag, ctx, readSettings(compoundTag)
      );
      twilightJigsawPiece.placeSettings().addProcessor(JigsawReplacementProcessor.INSTANCE);
      twilightJigsawPiece.placeSettings().addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
      return twilightJigsawPiece;
   }

   public static TwilightJigsawPiece defaultForTemplate(
      int genDepth,
      StructureTemplateManager structureManager,
      ResourceLocation templateLocation,
      JigsawPlaceContext jigsawContext,
      TemplatePoolInstance templatePoolInstance,
      StructureProcessorList serializedProcessors
   ) {
      TwilightJigsawPiece twilightJigsawPiece = new TwilightJigsawPiece(
         (StructurePieceType)TFStructurePieceTypes.TFJigsawTemplate.value(),
         genDepth,
         structureManager,
         templateLocation,
         jigsawContext,
         templatePoolInstance,
         serializedProcessors,
         templatePoolInstance.poolAliases()
      );
      twilightJigsawPiece.placeSettings().addProcessor(JigsawReplacementProcessor.INSTANCE);
      twilightJigsawPiece.placeSettings().addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
      return twilightJigsawPiece;
   }

   public TwilightJigsawPiece(
      StructurePieceType structurePieceType, CompoundTag compoundTag, StructurePieceSerializationContext ctx, StructurePlaceSettings placeSettings
   ) {
      super(structurePieceType, compoundTag, ctx, placeSettings);
      this.sourceJigsaw = readSourceFromNBT(compoundTag);
      this.spareJigsaws = readConnectionsFromNBT(compoundTag);
      this.terrainAdjustment = compoundTag.contains("terrain_adaptation")
         ? TerrainAdjustment.valueOf(compoundTag.getString("terrain_adaptation"))
         : TerrainAdjustment.NONE;
      DynamicOps<Tag> dynamicOps = RegistryOps.create(NbtOps.INSTANCE, ctx.registryAccess());
      this.processors = compoundTag.contains("template_processors")
         ? StructureProcessorType.LIST_CODEC
            .parse(dynamicOps, compoundTag.get("template_processors"))
            .resultOrPartial(message -> LOGGER.error("Error deserializing template_processors: {}", message))
         : Optional.empty();
      this.projection = compoundTag.contains("place_projection") ? Projection.valueOf(compoundTag.getString("place_projection")) : Projection.RIGID;
      this.markerHandlers = compoundTag.contains("marker_handlers")
         ? TemplateMarkerHandlerList.HOLDER_CODEC
            .parse(dynamicOps, compoundTag.get("marker_handlers"))
            .resultOrPartial(message -> LOGGER.error("Error deserializing marker_handlers: {}", message))
         : Optional.empty();
      this.beardifierGroundDelta = compoundTag.getInt("ground_offset");
      if (compoundTag.getBoolean("ignore_waterlog")) {
         this.placeSettings.setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
      }

      this.processors.ifPresent(p -> ((StructureProcessorList)p.value()).list().forEach(this.placeSettings::addProcessor));
      this.serializedProcessors = compoundTag.contains("randomized_processors")
         ? StructureProcessorType.LIST_OBJECT_CODEC
            .parse(dynamicOps, compoundTag.get("randomized_processors"))
            .resultOrPartial(message -> LOGGER.error("Error deserializing randomized_processors: {}", message))
            .orElseGet(() -> new StructureProcessorList(List.of()))
         : new StructureProcessorList(List.of());
      this.serializedProcessors.list().forEach(this.placeSettings::addProcessor);
      this.poolAliases = Map.of();
   }

   public TwilightJigsawPiece(
      StructurePieceType type, int genDepth, StructureTemplateManager structureManager, ResourceLocation templateLocation, JigsawPlaceContext jigsawContext
   ) {
      super(type, genDepth, structureManager, templateLocation, jigsawContext.placementSettings(), jigsawContext.templatePos());
      this.sourceJigsaw = jigsawContext.seedJigsaw();
      this.spareJigsaws = Collections.unmodifiableList(jigsawContext.spareJigsaws());
      this.terrainAdjustment = TerrainAdjustment.NONE;
      this.processors = Optional.empty();
      this.projection = Projection.RIGID;
      this.markerHandlers = Optional.empty();
      this.beardifierGroundDelta = 0;
      this.serializedProcessors = new StructureProcessorList(List.of());
      this.poolAliases = Map.of();
   }

   public TwilightJigsawPiece(
      StructurePieceType type,
      int genDepth,
      StructureTemplateManager structureManager,
      ResourceLocation templateLocation,
      JigsawPlaceContext jigsawContext,
      TemplatePoolInstance templatePoolInstance,
      StructureProcessorList serializedProcessors,
      Map<String, String> poolAliases
   ) {
      super(type, genDepth, structureManager, templateLocation, jigsawContext.placementSettings(), jigsawContext.templatePos());
      this.sourceJigsaw = jigsawContext.seedJigsaw();
      this.spareJigsaws = Collections.unmodifiableList(jigsawContext.spareJigsaws());
      this.terrainAdjustment = templatePoolInstance.terrainAdjustment();
      this.processors = templatePoolInstance.processors();
      this.projection = templatePoolInstance.projection();
      this.markerHandlers = templatePoolInstance.markerHandlers();
      this.beardifierGroundDelta = templatePoolInstance.beardifierGroundDelta().map(TemplatePoolInstance.HeightAdjustment::beardifierGroundDelta).orElse(0);
      if (templatePoolInstance.ignoreWorldWaterlog()) {
         this.placeSettings.setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
      }

      this.processors.ifPresent(p -> ((StructureProcessorList)p.value()).list().forEach(this.placeSettings::addProcessor));
      this.serializedProcessors = serializedProcessors;
      this.serializedProcessors.list().forEach(this.placeSettings::addProcessor);
      this.poolAliases = poolAliases;
   }

   protected static JigsawRecord readSourceFromNBT(CompoundTag structureTag) {
      return JigsawRecord.fromTag(structureTag.getCompound("source"));
   }

   protected static List<JigsawRecord> readConnectionsFromNBT(CompoundTag structureTag) {
      ListTag connections = structureTag.getList("connections", 10);
      if (connections.isEmpty()) {
         return Collections.emptyList();
      }

      List<JigsawRecord> connectionsList = new ArrayList<>();

      for (Tag tagEntry : connections) {
         if (tagEntry instanceof CompoundTag tag) {
            connectionsList.add(JigsawRecord.fromTag(tag));
         }
      }

      return Collections.unmodifiableList(connectionsList);
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag structureTag) {
      super.addAdditionalSaveData(ctx, structureTag);
      structureTag.put("source", this.sourceJigsaw.toTag());
      ListTag tags = new ListTag();

      for (JigsawRecord record : this.spareJigsaws) {
         tags.add(record.toTag());
      }

      structureTag.put("connections", tags);
      if (this.terrainAdjustment != TerrainAdjustment.NONE) {
         structureTag.putString("terrain_adaptation", this.terrainAdjustment.toString());
      }

      RegistryOps<Tag> registryOps = RegistryOps.create(NbtOps.INSTANCE, ctx.registryAccess());
      if (this.processors.isPresent()) {
         Optional<Tag> processorsList = StructureProcessorType.LIST_CODEC.encodeStart(registryOps, this.processors.get()).resultOrPartial(LOGGER::error);
         if (processorsList.isPresent()) {
            structureTag.put("template_processors", processorsList.get());
         }
      }

      if (this.markerHandlers.isPresent()) {
         Optional<Tag> markerHandlersList = TemplateMarkerHandlerList.HOLDER_CODEC
            .encodeStart(registryOps, this.markerHandlers.get())
            .resultOrPartial(LOGGER::error);
         if (markerHandlersList.isPresent()) {
            structureTag.put("marker_handlers", markerHandlersList.get());
         }
      }

      Optional<Tag> markerHandlersList = StructureProcessorType.LIST_OBJECT_CODEC
         .encodeStart(registryOps, this.serializedProcessors)
         .resultOrPartial(LOGGER::error);
      if (markerHandlersList.isPresent()) {
         structureTag.put("randomized_processors", markerHandlersList.get());
      }

      structureTag.putInt("ground_offset", this.beardifierGroundDelta);
      if (!this.placeSettings.shouldApplyWaterlogging()) {
         structureTag.putBoolean("ignore_waterlog", true);
      }
   }

   @Deprecated(forRemoval = true)
   public void addChildren(StructurePiece parent, StructurePieceAccessor pieceAccessor, RandomSource random) {
   }

   public void addJigsaws(TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context) {
      WorldgenRandom random = context.random();
      random.setSeed(random.nextLong() ^ context.seed() * this.templatePosition.asLong());
      this.addChildren(parent, pieceAccessor, random);
      List<JigsawRecord> jigsaws = this.spareJigsaws;

      for (int i = 0; i < jigsaws.size(); i++) {
         this.processJigsaw(parent, pieceAccessor, context, jigsaws.get(i), i);
      }
   }

   protected void processJigsaw(
      TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context, JigsawRecord connection, int jigsawIndex
   ) {
      ResourceLocation templatePool = ResourceLocation.parse(this.poolAliases.getOrDefault(connection.pool(), connection.pool()));
      BlockPos parentJunctionPos = this.templatePosition.offset(connection.pos());
      TwilightJigsawPiece jigsawPiece = structureTemplateDefinitions.initializeTemplateFromPool(
         templatePool,
         parentJunctionPos,
         connection.orientation(),
         connection.target(),
         context,
         this.genDepth + 1,
         parent.projection == Projection.TERRAIN_MATCHING
      );
      if (jigsawPiece != null) {
         if (pieceAccessor.findCollisionPiece(jigsawPiece.boundingBox) == null) {
            pieceAccessor.addPiece(jigsawPiece);
            jigsawPiece.addJigsaws(this, pieceAccessor, context);
         }
      }
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
      ChunkAccess chunkAt = level.getChunk(chunkPos.getWorldPosition());

      for (StructureBlockInfo blockInfo : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW)) {
         BlockPos infoPos = blockInfo.pos();
         if (chunkBounds.isInside(infoPos)) {
            chunkAt.markPosForPostprocessing(infoPos);
         }
      }
   }

   @Override
   protected void handleDataMarker(
      String label, BlockPos pos, WorldGenLevel level, RandomSource random, BoundingBox chunkBounds, ChunkGenerator chunkGen, Rotation rotation
   ) {
      super.handleDataMarker(label, pos, level, random, chunkBounds, chunkGen, rotation);
      if (!this.markerHandlers.isEmpty()) {
         for (TemplateMarkerHandler handler : ((TemplateMarkerHandlerList)this.markerHandlers.get().value()).markerHandlers()) {
            if (handler.handleDataMarker(label, pos, level, random, chunkBounds, chunkGen, rotation)) {
               break;
            }
         }
      }
   }

   public JigsawRecord getSourceJigsaw() {
      return this.sourceJigsaw;
   }

   public BlockPos getSourcePosition() {
      return this.templatePosition.offset(this.sourceJigsaw.pos());
   }

   public List<JigsawRecord> getSpareJigsaws() {
      return this.spareJigsaws;
   }

   public List<JigsawRecord> matchSpareJigsaws(Predicate<JigsawRecord> filter) {
      List<JigsawRecord> jigsaws = new ArrayList<>();

      for (JigsawRecord record : this.spareJigsaws) {
         if (filter.test(record)) {
            jigsaws.add(record);
         }
      }

      return jigsaws;
   }

   public int firstMatchIndex(Predicate<JigsawRecord> filter) {
      for (int i = 0; i < this.spareJigsaws.size(); i++) {
         if (filter.test(this.spareJigsaws.get(i))) {
            return i;
         }
      }

      return -1;
   }

   public BoundingBox getBeardifierBox() {
      return this.boundingBox;
   }

   public TerrainAdjustment getTerrainAdjustment() {
      return this.terrainAdjustment;
   }

   public int getGroundLevelDelta() {
      return this.beardifierGroundDelta;
   }
}
