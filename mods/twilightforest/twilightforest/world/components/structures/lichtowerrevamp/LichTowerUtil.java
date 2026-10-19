package twilightforest.world.components.structures.lichtowerrevamp;

import com.google.common.base.Suppliers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import org.jetbrains.annotations.Nullable;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.Component;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.world.components.processors.CobbleVariants;
import twilightforest.world.components.processors.InfestBlocksProcessor;
import twilightforest.world.components.processors.SpawnerProcessor;
import twilightforest.world.components.processors.StoneBricksVariants;
import twilightforest.world.components.processors.UpdateMarkingProcessor;
import twilightforest.world.components.processors.VerticalDecayProcessor;
import twilightforest.world.components.structures.util.StructureTemplateDefinitions;

@Component
public class LichTowerUtil {
   @Autowired
   private LichTowerPieces lichRoomPieces;
   @Autowired
   private StructureTemplateDefinitions structureTemplateDefinitions;
   private final Supplier<StructureProcessor> roomSpawners = Suppliers.memoize(
      () -> SpawnerProcessor.compile(2, 0.8F, Object2IntMaps.unmodifiable((Object2IntMap)Util.make(new Object2IntArrayMap(), map -> {
         map.put(EntityType.SPIDER, 1);
         map.put(EntityType.CAVE_SPIDER, 1);
         map.put((EntityType)TFEntities.SWARM_SPIDER.get(), 1);
         map.put((EntityType)TFEntities.HEDGE_SPIDER.get(), 1);
         map.put(EntityType.SKELETON, 4);
         map.put(EntityType.ZOMBIE, 4);
      })))
   );
   private final Supplier<StructureProcessor> centralSpawners = Suppliers.memoize(
      () -> SpawnerProcessor.compile(4, Object2IntMaps.unmodifiable((Object2IntMap)Util.make(new Object2IntArrayMap(), map -> {
         map.put(EntityType.SKELETON, 2);
         map.put(EntityType.ZOMBIE, 1);
         map.put((EntityType)TFEntities.SWARM_SPIDER.get(), 1);
      })))
   );
   private final Supplier<List<Block>> STAIR_DECAY_BLOCKS = Suppliers.memoize(
      () -> List.of(
         (Block)TFBlocks.TWILIGHT_OAK_SLAB.value(),
         (Block)TFBlocks.CANOPY_SLAB.value(),
         (Block)TFBlocks.TWILIGHT_OAK_BANISTER.value(),
         (Block)TFBlocks.CANOPY_BANISTER.value()
      )
   );
   private final Supplier<StructureProcessor[]> stairDecayProcessors = Suppliers.memoize(
      () -> {
         List<Block> filter = this.STAIR_DECAY_BLOCKS.get();
         return new StructureProcessor[]{
            new VerticalDecayProcessor(filter, 0.025F),
            new VerticalDecayProcessor(filter, 0.05F),
            new VerticalDecayProcessor(filter, 0.075F),
            new VerticalDecayProcessor(filter, 0.1F),
            new VerticalDecayProcessor(filter, 0.125F),
            new VerticalDecayProcessor(filter, 0.15F),
            new VerticalDecayProcessor(filter, 0.175F),
            new VerticalDecayProcessor(filter, 0.2F)
         };
      }
   );
   private static final Supplier<StructureProcessor> UPDATE_MARKER = Suppliers.memoize(
      () -> UpdateMarkingProcessor.forBlocks(
         Blocks.BIRCH_FENCE,
         Blocks.POLISHED_ANDESITE_STAIRS,
         Blocks.STONE_BRICK_WALL,
         Blocks.MOSSY_STONE_BRICK_WALL,
         Blocks.COBBLESTONE_WALL,
         Blocks.MOSSY_COBBLESTONE_WALL,
         (Block)TFBlocks.WROUGHT_IRON_FENCE.value(),
         (Block)TFBlocks.CANOPY_FENCE.value(),
         (Block)TFBlocks.TWISTED_STONE_PILLAR.value()
      )
   );

   public StructureProcessor getRoomSpawnerProcessor() {
      return this.roomSpawners.get();
   }

   public StructureProcessor getCentralBridgeSpawnerProcessor() {
      return this.centralSpawners.get();
   }

   public StructureProcessor[] getStairDecayProcessors() {
      return this.stairDecayProcessors.get();
   }

   @Nullable
   public ResourceLocation rollRandomRoom(RandomSource randomSource, int size) {
      ResourceLocation pool = switch (size) {
         case 0 -> LichTowerPieces.ROOM_3;
         case 1 -> LichTowerPieces.ROOM_5;
         case 2 -> LichTowerPieces.ROOM_7;
         case 3 -> LichTowerPieces.ROOM_9;
         default -> null;
      };
      return pool == null ? null : this.structureTemplateDefinitions.getRandomTemplate(randomSource, pool);
   }

   @Nullable
   public ResourceLocation rollTowerGallery(RandomSource randomSource) {
      return this.structureTemplateDefinitions.getRandomTemplate(randomSource, LichTowerPieces.GALLERY);
   }

   @Nullable
   public ResourceLocation rollGalleryRoof(RandomSource randomSource, BoundingBox box) {
      boolean odd = (Math.min(box.getXSpan(), box.getZSpan()) & 1) == 1;
      return this.structureTemplateDefinitions.getRandomTemplate(randomSource, odd ? LichTowerPieces.GALLERY_ROOF_ODD : LichTowerPieces.GALLERY_ROOF_EVEN);
   }

   @Nullable
   public ResourceLocation rollRandomMobBridge(RandomSource randomSource) {
      return this.structureTemplateDefinitions.getRandomTemplate(randomSource, LichTowerPieces.MOB_BRIDGE);
   }

   @Nullable
   public ResourceLocation rollRandomCover(RandomSource randomSource) {
      return this.structureTemplateDefinitions.getRandomTemplate(randomSource, LichTowerPieces.DOOR_STOPPER);
   }

   @Nullable
   public ResourceLocation rollRandomDecor(RandomSource randomSource, boolean inCentralTower) {
      return this.structureTemplateDefinitions.getRandomTemplate(randomSource, inCentralTower ? LichTowerPieces.CENTER_DECOR : LichTowerPieces.ROOM_DECOR);
   }

   public Iterable<ResourceLocation> shuffledCenterBridges(RandomSource randomSource) {
      return this.structureTemplateDefinitions.getShuffledSequence(randomSource, LichTowerPieces.BRIDGE_FROM_CENTRAL);
   }

   public Iterable<ResourceLocation> shuffledRoomBridges(RandomSource randomSource) {
      return this.structureTemplateDefinitions.getShuffledSequence(randomSource, LichTowerPieces.ROOM_BRIDGE);
   }

   public Iterable<ResourceLocation> shuffledEndBridges(RandomSource randomSource) {
      return this.structureTemplateDefinitions.getShuffledSequence(randomSource, LichTowerPieces.END_BRIDGE);
   }

   public Iterable<ResourceLocation> shuffledRoofs(RandomSource randomSource, int size, boolean sideRoof) {
      ResourceLocation pool = switch (size) {
         case 0 -> sideRoof ? LichTowerPieces.ROOM_3_SIDE_ROOF : LichTowerPieces.ROOM_3_ROOF;
         case 1 -> sideRoof ? LichTowerPieces.ROOM_5_SIDE_ROOF : LichTowerPieces.ROOM_5_ROOF;
         case 2 -> sideRoof ? LichTowerPieces.ROOM_7_SIDE_ROOF : LichTowerPieces.ROOM_7_ROOF;
         case 3 -> sideRoof ? LichTowerPieces.ROOM_9_SIDE_ROOF : LichTowerPieces.ROOM_9_ROOF;
         default -> null;
      };
      return pool == null ? List.of() : this.structureTemplateDefinitions.getShuffledSequence(randomSource, pool);
   }

   public Iterable<ResourceLocation> shuffledBeards(RandomSource randomSource, int size) {
      ResourceLocation pool = switch (size) {
         case 1 -> LichTowerPieces.ROOM_5_BEARD;
         case 2 -> LichTowerPieces.ROOM_7_BEARD;
         case 3 -> LichTowerPieces.ROOM_9_BEARD;
         default -> null;
      };
      return pool == null ? List.of() : this.structureTemplateDefinitions.getShuffledSequence(randomSource, pool);
   }

   @Nullable
   public ResourceLocation getTrim(RandomSource randomSource, int size) {
      ResourceLocation pool = switch (size) {
         case 1 -> LichTowerPieces.ROOM_5_TRIM;
         case 2 -> LichTowerPieces.ROOM_7_TRIM;
         case 3 -> LichTowerPieces.ROOM_9_TRIM;
         default -> null;
      };
      return pool == null ? null : this.structureTemplateDefinitions.getRandomTemplate(randomSource, pool);
   }

   public Set<String> getLadderPlacementsForSize(int size) {
      return switch (size) {
         case 1 -> this.lichRoomPieces.ladderPlacements1;
         case 2 -> this.lichRoomPieces.ladderPlacements2;
         case 3 -> this.lichRoomPieces.ladderPlacements3;
         default -> Collections.emptySet();
      };
   }

   @Nullable
   public ResourceLocation getRoomUpwards(RandomSource random, int size, int ladderOffset) {
      if (size > 0 && size <= 3) {
         Int2ObjectMap<ResourceLocation> roomsForSize = this.lichRoomPieces.ladderRooms.get(size - 1);
         ResourceLocation roomsForLadderPlacement = (ResourceLocation)roomsForSize.get(ladderOffset);
         return roomsForLadderPlacement == null ? null : this.structureTemplateDefinitions.getRandomTemplate(random, roomsForLadderPlacement);
      } else {
         return null;
      }
   }

   @Nullable
   public ResourceLocation getFallbackRoof(RandomSource random, int size, boolean sideAttachment) {
      ResourceLocation pool = switch (size) {
         case 0 -> sideAttachment ? LichTowerPieces.ROOM_3_SIDE_ROOF_FALLBACK : LichTowerPieces.ROOM_3_ROOF_FALLBACK;
         case 1 -> sideAttachment ? LichTowerPieces.ROOM_5_SIDE_ROOF_FALLBACK : LichTowerPieces.ROOM_5_ROOF_FALLBACK;
         case 2 -> sideAttachment ? LichTowerPieces.ROOM_7_SIDE_ROOF_FALLBACK : LichTowerPieces.ROOM_7_ROOF_FALLBACK;
         case 3 -> sideAttachment ? LichTowerPieces.ROOM_9_SIDE_ROOF_FALLBACK : LichTowerPieces.ROOM_9_ROOF_FALLBACK;
         default -> null;
      };
      return pool == null ? null : this.structureTemplateDefinitions.getRandomTemplate(random, pool);
   }

   @Nullable
   public ResourceLocation getFallbackBeard(RandomSource random, int size) {
      ResourceLocation pool = switch (size) {
         case 1 -> LichTowerPieces.ROOM_5_BEARD_FALLBACK;
         case 2 -> LichTowerPieces.ROOM_7_BEARD_FALLBACK;
         case 3 -> LichTowerPieces.ROOM_9_BEARD_FALLBACK;
         default -> null;
      };
      return pool == null ? null : this.structureTemplateDefinitions.getRandomTemplate(random, pool);
   }

   public static void addDefaultProcessors(StructurePlaceSettings settings) {
      settings.addProcessor(JigsawReplacementProcessor.INSTANCE)
         .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK)
         .addProcessor(StoneBricksVariants.INSTANCE)
         .addProcessor(CobbleVariants.INSTANCE)
         .addProcessor(InfestBlocksProcessor.INSTANCE)
         .addProcessor(UPDATE_MARKER.get());
   }

   @Nullable
   public ResourceLocation getKeepsakeCasketRoom(RandomSource random) {
      return this.structureTemplateDefinitions.getRandomTemplate(random, LichTowerPieces.ROOM_9_SPECIAL);
   }

   @Nullable
   public ResourceLocation getEnclosedCentralBridge(RandomSource random) {
      return this.structureTemplateDefinitions.getRandomTemplate(random, LichTowerPieces.BRIDGE_FROM_CENTRAL_FALLBACK);
   }

   @Nullable
   public ResourceLocation getDirectRoomAttachment(RandomSource random) {
      return this.structureTemplateDefinitions.getRandomTemplate(random, LichTowerPieces.ROOM_BRIDGE_FALLBACK);
   }

   @Nullable
   public ResourceLocation getDefaultBridgeStopper(RandomSource randomSource) {
      return this.structureTemplateDefinitions.getRandomTemplate(randomSource, LichTowerPieces.DOOR_STOPPER_FALLBACK);
   }

   @Nullable
   public ResourceLocation rollGrave(RandomSource randomSource) {
      return this.structureTemplateDefinitions.getRandomTemplate(randomSource, LichTowerPieces.YARD_GRAVE);
   }
}
