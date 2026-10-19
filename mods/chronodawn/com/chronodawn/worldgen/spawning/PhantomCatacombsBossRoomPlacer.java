package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModDimensions;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.TickEvent.Server;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class PhantomCatacombsBossRoomPlacer {
   private static final ResourceLocation PHANTOM_CATACOMBS_ID = CompatResourceLocation.create("chronodawn", "phantom_catacombs");
   private static final ResourceLocation ROOM_7_TEMPLATE = CompatResourceLocation.create("chronodawn", "phantom_catacombs_room_7");
   private static final ResourceLocation BOSS_ROOM_TEMPLATE = CompatResourceLocation.create("chronodawn", "phantom_catacombs_boss_room");
   private static final Map<ResourceLocation, Set<BlockPos>> processedStructures = new ConcurrentHashMap<>();
   private static final Map<BlockPos, PhantomCatacombsBossRoomPlacer.StructureProcessingState> processingStates = new ConcurrentHashMap<>();
   private static final int CHECK_INTERVAL = 600;
   private static final Map<ResourceLocation, Integer> tickCounters = new ConcurrentHashMap<>();
   private static Rotation lastRoom7Rotation = Rotation.NONE;

   private static boolean hasPhantomCatacombs(ServerLevel level, ChunkPos chunkPos) {
      StructureManager structureManager = level.structureManager();

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         if (structureLocation != null && structureLocation.equals(PHANTOM_CATACOMBS_ID)) {
            return true;
         }
      }

      return false;
   }

   private static List<BlockPos> findDeadEndMarkers(ServerLevel level, ChunkPos chunkPos, BoundingBox boundingBox) {
      List<BlockPos> markers = new ArrayList<>();
      int minY = boundingBox != null ? boundingBox.minY() - 10 : -64;
      int maxY = boundingBox != null ? boundingBox.maxY() + 10 : 320;
      StructureManager structureManager = level.structureManager();
      HashSet<ChunkPos> structureChunks = new HashSet<>();

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         if (structureLocation != null && structureLocation.equals(PHANTOM_CATACOMBS_ID)) {
            LongSet chunks = entry.getValue();
            LongIterator blockState = chunks.iterator();

            while (blockState.hasNext()) {
               long chunkLong = (Long)blockState.next();
               structureChunks.add(new ChunkPos(chunkLong));
            }
         }
      }

      if (structureChunks.isEmpty()) {
         ChronoDawn.LOGGER.warn("[Async] No structure chunks found for Phantom Catacombs at {}", chunkPos);
         return markers;
      }

      ChronoDawn.LOGGER
         .debug(
            "[Async] Searching for Crying Obsidian markers in {} structure chunks (Y: {} to {}){}",
            new Object[]{
               structureChunks.size(), minY, maxY, boundingBox != null ? " [based on structure bounding box: " + boundingBox + "]" : " [using default range]"
            }
         );
      int chunksScanned = 0;

      for (ChunkPos structureChunk : structureChunks) {
         chunksScanned++;

         for (BlockPos pos : BlockPos.betweenClosed(
            structureChunk.getMinBlockX(), minY, structureChunk.getMinBlockZ(), structureChunk.getMaxBlockX(), maxY, structureChunk.getMaxBlockZ()
         )) {
            BlockState blockState = level.getBlockState(pos);
            if (blockState.is(Blocks.CRYING_OBSIDIAN)) {
               markers.add(pos.immutable());
            }
         }
      }

      if (!markers.isEmpty()) {
         ChronoDawn.LOGGER.debug("[Async] Found {} Crying Obsidian markers after scanning {} chunks", markers.size(), chunksScanned);

         for (int i = 0; i < Math.min(markers.size(), 5); i++) {
            BlockPos marker = markers.get(i);
            ChronoDawn.LOGGER.debug("  Marker {}: {}", i + 1, marker);
         }

         if (markers.size() > 5) {
            ChronoDawn.LOGGER.debug("  ... and {} more", markers.size() - 5);
         }
      } else {
         ChronoDawn.LOGGER
            .warn("[Async] No Crying Obsidian markers found in {} structure chunks. Structure may not be fully loaded yet.", structureChunks.size());
      }

      return markers;
   }

   private static BlockPos findRoom7Marker(StructureTemplate template) {
      Iterator var1 = template.filterBlocks(BlockPos.ZERO, new StructurePlaceSettings(), Blocks.CRYING_OBSIDIAN).iterator();
      if (var1.hasNext()) {
         StructureBlockInfo blockInfo = (StructureBlockInfo)var1.next();
         return blockInfo.pos();
      } else {
         return null;
      }
   }

   private static BlockPos findRoom7Connector(StructureTemplate template) {
      Iterator var1 = template.filterBlocks(BlockPos.ZERO, new StructurePlaceSettings(), Blocks.AMETHYST_BLOCK).iterator();
      if (var1.hasNext()) {
         StructureBlockInfo blockInfo = (StructureBlockInfo)var1.next();
         return blockInfo.pos();
      } else {
         return null;
      }
   }

   private static BlockPos findBossRoomConnector(StructureTemplate template) {
      Iterator var1 = template.filterBlocks(BlockPos.ZERO, new StructurePlaceSettings(), Blocks.AMETHYST_BLOCK).iterator();
      if (var1.hasNext()) {
         StructureBlockInfo blockInfo = (StructureBlockInfo)var1.next();
         return blockInfo.pos();
      } else {
         return null;
      }
   }

   private static Direction detectEntranceDirection(ServerLevel level, BlockPos markerPos) {
      int maxAirBlocks = 0;
      Direction entranceDir = Direction.NORTH;

      for (Direction dir : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
         int airCount = 0;

         for (int dist = 1; dist <= 5; dist++) {
            BlockPos checkPos = markerPos.relative(dir, dist);
            BlockState state = level.getBlockState(checkPos);
            if (state.isAir() || !state.isSolidRender(level, checkPos)) {
               airCount++;
            }
         }

         if (airCount > maxAirBlocks) {
            maxAirBlocks = airCount;
            entranceDir = dir;
         }
      }

      ChronoDawn.LOGGER.debug("Detected entrance direction: {} (air blocks: {})", entranceDir, maxAirBlocks);
      return entranceDir;
   }

   private static Rotation getRotationFromDirection(Direction entranceDir) {
      return switch (entranceDir) {
         case NORTH -> Rotation.NONE;
         case SOUTH -> Rotation.CLOCKWISE_180;
         case EAST -> Rotation.CLOCKWISE_90;
         case WEST -> Rotation.COUNTERCLOCKWISE_90;
         default -> Rotation.NONE;
      };
   }

   private static Direction getExitDirectionFromRotation(Rotation rotation) {
      return switch (rotation) {
         case NONE -> Direction.EAST;
         case CLOCKWISE_90 -> Direction.SOUTH;
         case CLOCKWISE_180 -> Direction.WEST;
         case COUNTERCLOCKWISE_90 -> Direction.NORTH;
         default -> throw new MatchException(null, null);
      };
   }

   private static Rotation getBossRoomRotationFromExitDirection(Direction exitDir) {
      return switch (exitDir) {
         case NORTH -> Rotation.COUNTERCLOCKWISE_90;
         case SOUTH -> Rotation.CLOCKWISE_90;
         case EAST -> Rotation.NONE;
         case WEST -> Rotation.CLOCKWISE_180;
         default -> Rotation.NONE;
      };
   }

   private static BlockPos placeRoom7(ServerLevel level, BlockPos deadEndPos) {
      StructureTemplateManager structureTemplateManager = level.getStructureManager();
      Optional<StructureTemplate> templateOptional = structureTemplateManager.get(ROOM_7_TEMPLATE);
      if (templateOptional.isEmpty()) {
         ChronoDawn.LOGGER.error("Failed to load room_7 template: {}", ROOM_7_TEMPLATE);
         return null;
      }

      StructureTemplate template = templateOptional.get();
      BlockPos room7Marker = findRoom7Marker(template);
      if (room7Marker == null) {
         ChronoDawn.LOGGER.error("Room_7 template {} does not contain a Crying Obsidian marker", ROOM_7_TEMPLATE);
         return null;
      }

      BlockPos room7Connector = findRoom7Connector(template);
      if (room7Connector == null) {
         ChronoDawn.LOGGER.error("Room_7 template {} does not contain an Amethyst Block connector", ROOM_7_TEMPLATE);
         return null;
      }

      Direction entranceDir = detectEntranceDirection(level, deadEndPos);
      Rotation rotation = getRotationFromDirection(entranceDir);
      ChronoDawn.LOGGER.debug("Room_7 marker before rotation: {}, connector before rotation: {}", room7Marker, room7Connector);
      StructurePlaceSettings tempSettings = new StructurePlaceSettings().setRotation(rotation);
      BlockPos rotatedMarker = StructureTemplate.calculateRelativePosition(tempSettings, room7Marker);
      BlockPos rotatedConnectorRelative = StructureTemplate.calculateRelativePosition(tempSettings, room7Connector);
      ChronoDawn.LOGGER
         .debug(
            "Room_7 after rotation {}: marker {} → {}, connector {} → {}",
            new Object[]{rotation, room7Marker, rotatedMarker, room7Connector, rotatedConnectorRelative}
         );
      BlockPos placementPos = deadEndPos.subtract(rotatedMarker);
      ChronoDawn.LOGGER.debug("Room_7 placement: deadEndPos {} - rotatedMarker {} = placementPos {}", new Object[]{deadEndPos, rotatedMarker, placementPos});
      Vec3i templateSize = template.getSize();
      int sizeX = templateSize.getX();
      int sizeY = templateSize.getY();
      int sizeZ = templateSize.getZ();
      ChronoDawn.LOGGER.debug("Room_7 template size: {}x{}x{} (rotation: {})", new Object[]{sizeX, sizeY, sizeZ, rotation});
      int waterRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos, placementPos.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.getFluidState().isSource() && state.is(Blocks.WATER)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            waterRemoved++;
         }
      }

      if (waterRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} water blocks before room_7 placement", waterRemoved);
      }

      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setIgnoreEntities(false);
      level.setBlock(deadEndPos, Blocks.AIR.defaultBlockState(), 3);
      ChronoDawn.LOGGER.debug("Removed Crying Obsidian marker at {}", deadEndPos);
      template.placeInWorld(level, placementPos, placementPos, settings, level.random, 2);
      int waterloggedRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos, placementPos.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            boolean waterlogged = (Boolean)state.getValue(BlockStateProperties.WATERLOGGED);
            if (waterlogged) {
               level.setBlock(pos, (BlockState)state.setValue(BlockStateProperties.WATERLOGGED, false), 2);
               waterloggedRemoved++;
            }
         }
      }

      if (waterloggedRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} waterlogged blocks after room_7 placement", waterloggedRemoved);
      }

      BlockPos rotatedConnector = StructureTemplate.calculateRelativePosition(tempSettings, room7Connector);
      BlockPos connectorWorldPos = placementPos.offset(rotatedConnector);
      ChronoDawn.LOGGER
         .debug(
            "Placed room_7 at {} (replacing dead-end at {}), connector at {}, rotation: {}",
            new Object[]{placementPos, deadEndPos, connectorWorldPos, rotation}
         );
      lastRoom7Rotation = rotation;
      return connectorWorldPos;
   }

   private static BlockPos placeRoom7WithRotation(ServerLevel level, BlockPos deadEndPos, Rotation rotation, StructureTemplate template) {
      BlockPos room7Marker = findRoom7Marker(template);
      if (room7Marker == null) {
         ChronoDawn.LOGGER.error("Room_7 template {} does not contain a Crying Obsidian marker", ROOM_7_TEMPLATE);
         return null;
      }

      BlockPos room7Connector = findRoom7Connector(template);
      if (room7Connector == null) {
         ChronoDawn.LOGGER.error("Room_7 template {} does not contain an Amethyst Block connector", ROOM_7_TEMPLATE);
         return null;
      }

      ChronoDawn.LOGGER
         .debug(
            "Placing room_7 with specified rotation {}: marker before rotation: {}, connector before rotation: {}",
            new Object[]{rotation, room7Marker, room7Connector}
         );
      StructurePlaceSettings tempSettings = new StructurePlaceSettings().setRotation(rotation);
      BlockPos rotatedMarker = StructureTemplate.calculateRelativePosition(tempSettings, room7Marker);
      BlockPos rotatedConnectorRelative = StructureTemplate.calculateRelativePosition(tempSettings, room7Connector);
      ChronoDawn.LOGGER
         .debug(
            "Room_7 after rotation {}: marker {} → {}, connector {} → {}",
            new Object[]{rotation, room7Marker, rotatedMarker, room7Connector, rotatedConnectorRelative}
         );
      BlockPos placementPos = deadEndPos.subtract(rotatedMarker);
      ChronoDawn.LOGGER.debug("Room_7 placement: deadEndPos {} - rotatedMarker {} = placementPos {}", new Object[]{deadEndPos, rotatedMarker, placementPos});
      Vec3i templateSize = template.getSize();
      int sizeX = templateSize.getX();
      int sizeY = templateSize.getY();
      int sizeZ = templateSize.getZ();
      ChronoDawn.LOGGER.debug("Room_7 template size: {}x{}x{} (rotation: {})", new Object[]{sizeX, sizeY, sizeZ, rotation});
      int waterRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos, placementPos.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.getFluidState().isSource() && state.is(Blocks.WATER)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            waterRemoved++;
         }
      }

      if (waterRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} water blocks before room_7 placement", waterRemoved);
      }

      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setIgnoreEntities(false);
      level.setBlock(deadEndPos, Blocks.AIR.defaultBlockState(), 3);
      ChronoDawn.LOGGER.debug("Removed Crying Obsidian marker at {}", deadEndPos);
      template.placeInWorld(level, placementPos, placementPos, settings, level.random, 2);
      int waterloggedRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos, placementPos.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            boolean waterlogged = (Boolean)state.getValue(BlockStateProperties.WATERLOGGED);
            if (waterlogged) {
               level.setBlock(pos, (BlockState)state.setValue(BlockStateProperties.WATERLOGGED, false), 2);
               waterloggedRemoved++;
            }
         }
      }

      if (waterloggedRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} waterlogged blocks after room_7 placement", waterloggedRemoved);
      }

      BlockPos rotatedConnector = StructureTemplate.calculateRelativePosition(tempSettings, room7Connector);
      BlockPos connectorWorldPos = placementPos.offset(rotatedConnector);
      ChronoDawn.LOGGER
         .debug(
            "Placed room_7 at {} (replacing dead-end at {}), connector at {}, rotation: {}",
            new Object[]{placementPos, deadEndPos, connectorWorldPos, rotation}
         );
      return connectorWorldPos;
   }

   private static Direction detectExitDirection(ServerLevel level, BlockPos connectorPos) {
      int maxAirBlocks = 0;
      Direction bestDir = Direction.NORTH;
      ChronoDawn.LOGGER.debug("Detecting exit direction from connector at {}", connectorPos);

      for (Direction dir : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
         int airBlocks = 0;

         for (int i = 1; i <= 10; i++) {
            BlockPos checkPos = connectorPos.relative(dir, i);
            if (level.getBlockState(checkPos).isAir() || !level.getBlockState(checkPos).isSolidRender(level, checkPos)) {
               airBlocks++;
            }
         }

         ChronoDawn.LOGGER.debug("  Direction {}: {} air/non-solid blocks", dir, airBlocks);
         if (airBlocks > maxAirBlocks) {
            maxAirBlocks = airBlocks;
            bestDir = dir;
         }
      }

      ChronoDawn.LOGGER.debug("Detected exit direction: {} (air blocks: {})", bestDir, maxAirBlocks);
      return bestDir;
   }

   private static boolean placeBossRoom(ServerLevel level, BlockPos connectorPos) {
      StructureTemplateManager structureTemplateManager = level.getStructureManager();
      Optional<StructureTemplate> templateOptional = structureTemplateManager.get(BOSS_ROOM_TEMPLATE);
      if (templateOptional.isEmpty()) {
         ChronoDawn.LOGGER.error("Failed to load boss_room template: {}", BOSS_ROOM_TEMPLATE);
         return false;
      }

      StructureTemplate template = templateOptional.get();
      BlockPos bossRoomConnector = findBossRoomConnector(template);
      if (bossRoomConnector == null) {
         ChronoDawn.LOGGER.error("Boss_room template {} does not contain an Amethyst Block connector", BOSS_ROOM_TEMPLATE);
         return false;
      }

      Direction exitDir = getExitDirectionFromRotation(lastRoom7Rotation);
      Rotation rotation = getBossRoomRotationFromExitDirection(exitDir);
      ChronoDawn.LOGGER
         .debug(
            "Boss_room connector before rotation: {}, room_7 rotation: {}, exit direction: {}, boss_room rotation: {}",
            new Object[]{bossRoomConnector, lastRoom7Rotation, exitDir, rotation}
         );
      StructurePlaceSettings tempSettings = new StructurePlaceSettings().setRotation(rotation);
      BlockPos rotatedConnector = StructureTemplate.calculateRelativePosition(tempSettings, bossRoomConnector);
      ChronoDawn.LOGGER.debug("Boss_room connector after rotation {}: {} → {}", new Object[]{rotation, bossRoomConnector, rotatedConnector});
      BlockPos basePlacementPos = connectorPos.subtract(rotatedConnector);

      BlockPos exitOffset = switch (exitDir) {
         case NORTH -> new BlockPos(0, 0, -1);
         case SOUTH -> new BlockPos(0, 0, 1);
         case EAST -> new BlockPos(1, 0, 0);
         case WEST -> new BlockPos(-1, 0, 0);
         default -> BlockPos.ZERO;
      };
      BlockPos placementPos = basePlacementPos.offset(exitOffset);
      BlockPos bossRoomConnectorWorldPos = placementPos.offset(rotatedConnector);
      ChronoDawn.LOGGER
         .debug(
            "Boss_room placement calculation: connectorPos {} - rotatedConnector {} = basePlacementPos {} + exitOffset {} = placementPos {}",
            new Object[]{connectorPos, rotatedConnector, basePlacementPos, exitOffset, placementPos}
         );
      ChronoDawn.LOGGER
         .debug("Verification: boss_room Amethyst Block will be at {} (should match room_7 connector at {})", bossRoomConnectorWorldPos, connectorPos);
      Vec3i templateSize = template.getSize();
      int sizeX = templateSize.getX();
      int sizeY = templateSize.getY();
      int sizeZ = templateSize.getZ();
      ChronoDawn.LOGGER.debug("Boss_room template size: {}x{}x{} (rotation: {})", new Object[]{sizeX, sizeY, sizeZ, rotation});
      int waterRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos, placementPos.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.getFluidState().isSource() && state.is(Blocks.WATER)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            waterRemoved++;
         }
      }

      if (waterRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} water blocks before boss_room placement", waterRemoved);
      }

      ResourceLocation processorListId = CompatResourceLocation.create("chronodawn", "convert_decorative_water");
      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setIgnoreEntities(false);

      try {
         Registry<StructureProcessorList> processorListRegistry = level.registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);
         StructureProcessorList processorList = (StructureProcessorList)processorListRegistry.get(processorListId);
         if (processorList != null) {
            ChronoDawn.LOGGER.debug("Found processor list {}: {} processors", processorListId, processorList.list().size());

            for (StructureProcessor processor : processorList.list()) {
               settings.addProcessor(processor);
               ChronoDawn.LOGGER.debug("  Added processor: {}", processor.getClass().getSimpleName());
            }

            ChronoDawn.LOGGER.debug("Successfully applied {} structure processors to boss_room placement", processorList.list().size());
         } else {
            ChronoDawn.LOGGER.error("Processor list {} not found in registry! Boss_room WILL have waterlogging issues", processorListId);
            ChronoDawn.LOGGER.error("Available processor lists: {}", processorListRegistry.keySet());
         }
      } catch (Exception e) {
         ChronoDawn.LOGGER.error("Failed to load processor list {}: {}", new Object[]{processorListId, e.getMessage(), e});
      }

      template.placeInWorld(level, placementPos, placementPos, settings, level.random, 2);
      int waterRemovedAfter = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos.offset(-1, -1, -1), placementPos.offset(sizeX + 1, sizeY + 1, sizeZ + 1))) {
         BlockState state = level.getBlockState(pos);
         if (state.getFluidState().isSource() && state.is(Blocks.WATER)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            waterRemovedAfter++;
         }
      }

      if (waterRemovedAfter > 0) {
         ChronoDawn.LOGGER.debug("Removed {} water blocks after boss_room placement (flowed in from surroundings)", waterRemovedAfter);
      }

      int waterloggedRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos, placementPos.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            boolean waterlogged = (Boolean)state.getValue(BlockStateProperties.WATERLOGGED);
            if (waterlogged) {
               level.setBlock(pos, (BlockState)state.setValue(BlockStateProperties.WATERLOGGED, false), 2);
               waterloggedRemoved++;
            }
         }
      }

      if (waterloggedRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} waterlogged blocks after boss_room placement", waterloggedRemoved);
      }

      ChronoDawn.LOGGER
         .debug("Placed boss_room at {} (connector aligned to {}) in dimension {}", new Object[]{placementPos, connectorPos, level.dimension().location()});

      BlockPos centerOffset = switch (exitDir) {
         case NORTH -> new BlockPos(0, 4, -10);
         case SOUTH -> new BlockPos(0, 4, 10);
         case EAST -> new BlockPos(10, 4, 0);
         case WEST -> new BlockPos(-10, 4, 0);
         default -> new BlockPos(0, 4, 0);
      };
      BlockPos bossRoomCenter = connectorPos.offset(centerOffset);
      ChronoDawn.LOGGER
         .debug(
            "Registering boss_room for Temporal Phantom spawning: connector at {}, exit direction {}, center at {}",
            new Object[]{connectorPos, exitDir, bossRoomCenter}
         );
      TemporalPhantomSpawner.registerBossRoom(level, bossRoomCenter);
      return true;
   }

   private static BlockPos getStructureOrigin(ServerLevel level, ChunkPos chunkPos) {
      StructureManager structureManager = level.structureManager();

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         if (structureLocation != null && structureLocation.equals(PHANTOM_CATACOMBS_ID)) {
            LongSet structureStart = entry.getValue();
            if (!structureStart.isEmpty()) {
               long firstChunk = structureStart.iterator().nextLong();
               return new ChunkPos(firstChunk).getWorldPosition();
            }
         }
      }

      return null;
   }

   private static BoundingBox getStructureBoundingBox(ServerLevel level, ChunkPos chunkPos) {
      StructureManager structureManager = level.structureManager();
      int structureCount = 0;

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         structureCount++;
         if (structureLocation != null && structureLocation.equals(PHANTOM_CATACOMBS_ID)) {
            LongSet chunks = entry.getValue();
            ChronoDawn.LOGGER.debug("Found Phantom Catacombs structure with {} chunks", chunks.size());
            int validStarts = 0;
            LongIterator minY = chunks.iterator();

            while (minY.hasNext()) {
               long chunkLong = (Long)minY.next();
               ChunkPos structureChunk = new ChunkPos(chunkLong);
               StructureStart startForStructure = structureManager.getStructureAt(structureChunk.getWorldPosition(), structure);
               if (startForStructure != null && startForStructure.isValid()) {
                  validStarts++;
                  BoundingBox box = startForStructure.getBoundingBox();
                  if (box != null) {
                     ChronoDawn.LOGGER.debug("Successfully retrieved bounding box from StructureStart: {}", box);
                     return box;
                  }

                  ChronoDawn.LOGGER.debug("StructureStart is valid but bounding box is null for chunk {}", structureChunk);
               }
            }

            ChronoDawn.LOGGER.debug("Checked {} structure chunks, found {} valid StructureStarts, but no bounding box", chunks.size(), validStarts);
            ChronoDawn.LOGGER.warn("Could not get bounding box from StructureStart, scanning chunks for structure blocks...");
            int minYx = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minZ = Integer.MAX_VALUE;
            int maxZ = Integer.MIN_VALUE;
            boolean foundAny = false;
            int chunksScanned = 0;
            int chunksSkipped = 0;
            LongIterator scannedBox = chunks.iterator();

            while (scannedBox.hasNext()) {
               long chunkLong = (Long)scannedBox.next();
               ChunkPos structureChunk = new ChunkPos(chunkLong);
               if (!level.hasChunk(structureChunk.x, structureChunk.z)) {
                  ChronoDawn.LOGGER.debug("Chunk {} not loaded, skipping bounding box scan", structureChunk);
                  chunksSkipped++;
               } else {
                  chunksScanned++;
                  LevelChunk chunk = level.getChunk(structureChunk.x, structureChunk.z);

                  for (int x = 0; x < 16; x++) {
                     for (int z = 0; z < 16; z++) {
                        for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                           BlockPos pos = new BlockPos(structureChunk.getMinBlockX() + x, y, structureChunk.getMinBlockZ() + z);
                           BlockState state = chunk.getBlockState(pos);
                           if (state.is(Blocks.DEEPSLATE_BRICKS)
                              || state.is(Blocks.DEEPSLATE_BRICK_STAIRS)
                              || state.is(Blocks.DEEPSLATE_BRICK_SLAB)
                              || state.is(Blocks.DEEPSLATE_BRICK_WALL)
                              || state.is(Blocks.CRACKED_DEEPSLATE_BRICKS)
                              || state.is(Blocks.CRYING_OBSIDIAN)) {
                              foundAny = true;
                              minX = Math.min(minX, pos.getX());
                              maxX = Math.max(maxX, pos.getX());
                              minYx = Math.min(minYx, pos.getY());
                              maxY = Math.max(maxY, pos.getY());
                              minZ = Math.min(minZ, pos.getZ());
                              maxZ = Math.max(maxZ, pos.getZ());
                           }
                        }
                     }
                  }
               }
            }

            ChronoDawn.LOGGER.debug("Chunk scan results: {} scanned, {} skipped (not loaded)", chunksScanned, chunksSkipped);
            if (foundAny) {
               BoundingBox scannedBoxx = new BoundingBox(minX, minYx, minZ, maxX, maxY, maxZ);
               ChronoDawn.LOGGER.debug("Created bounding box from chunk scan: {}", scannedBoxx);
               return scannedBoxx;
            }

            ChronoDawn.LOGGER.warn("No structure blocks found in chunk scan (scanned: {}, skipped: {})", chunksScanned, chunksSkipped);
         }
      }

      if (structureCount == 0) {
         ChronoDawn.LOGGER.warn("No structures found at chunk position {}", chunkPos);
      }

      return null;
   }

   public static void processStructure(ServerLevel level, ChunkPos chunkPos) {
      ResourceLocation dimensionId = level.dimension().location();
      processedStructures.putIfAbsent(dimensionId, ConcurrentHashMap.newKeySet());
      Set<BlockPos> dimensionProcessed = processedStructures.get(dimensionId);
      if (hasPhantomCatacombs(level, chunkPos)) {
         BlockPos structureOrigin = getStructureOrigin(level, chunkPos);
         if (structureOrigin != null) {
            if (!dimensionProcessed.contains(structureOrigin) && !processingStates.containsKey(structureOrigin)) {
               BoundingBox boundingBox = getStructureBoundingBox(level, chunkPos);
               if (boundingBox == null) {
                  ChronoDawn.LOGGER
                     .warn(
                        "Could not determine bounding box for Phantom Catacombs at chunk {} (origin: {}). Will use default Y range (-64 to 320) for marker search.",
                        chunkPos,
                        structureOrigin
                     );
               }

               ChronoDawn.LOGGER
                  .debug(
                     "Found Phantom Catacombs at chunk {} (origin: {}) in dimension {}, initializing multi-tick processing",
                     new Object[]{chunkPos, structureOrigin, dimensionId}
                  );
               PhantomCatacombsBossRoomPlacer.StructureProcessingState state = new PhantomCatacombsBossRoomPlacer.StructureProcessingState(
                  structureOrigin, chunkPos, dimensionId, boundingBox
               );
               ChronoDawn.LOGGER.debug("Created processing state for {} (phase: {})", structureOrigin, state.phase);
               StructureManager structureManager = level.structureManager();
               StructureStart structureStart = null;

               for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
                  Structure structure = entry.getKey();
                  ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
                  if (structureLocation != null && structureLocation.equals(PHANTOM_CATACOMBS_ID)) {
                     structureStart = structureManager.getStructureAt(chunkPos.getWorldPosition(), structure);
                     break;
                  }
               }

               if (structureStart != null && structureStart.isValid()) {
                  List<StructurePiece> pieces = structureStart.getPieces();
                  ChronoDawn.LOGGER.debug("StructureStart has {} pieces", pieces.size());

                  for (StructurePiece piece : pieces) {
                     BoundingBox pieceBB = piece.getBoundingBox();
                     if (pieceBB != null) {
                        int minChunkX = pieceBB.minX() >> 4;
                        int maxChunkX = pieceBB.maxX() >> 4;
                        int minChunkZ = pieceBB.minZ() >> 4;
                        int maxChunkZ = pieceBB.maxZ() >> 4;

                        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
                           for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                              ChunkPos pieceChunk = new ChunkPos(cx, cz);
                              if (!state.structureChunks.contains(pieceChunk)) {
                                 state.structureChunks.add(pieceChunk);
                              }
                           }
                        }
                     }
                  }

                  ChronoDawn.LOGGER.debug("Collected {} chunks from {} pieces", state.structureChunks.size(), pieces.size());
               } else {
                  ChronoDawn.LOGGER.warn("Could not get valid StructureStart for Phantom Catacombs at {}", structureOrigin);
                  ChunkPos originChunkPos = new ChunkPos(structureOrigin);

                  for (int dx = -8; dx <= 8; dx++) {
                     for (int dz = -8; dz <= 8; dz++) {
                        state.structureChunks.add(new ChunkPos(originChunkPos.x + dx, originChunkPos.z + dz));
                     }
                  }

                  ChronoDawn.LOGGER.debug("Using fallback chunk search: {} chunks", state.structureChunks.size());
               }

               if (state.structureChunks.isEmpty()) {
                  ChronoDawn.LOGGER.warn("No structure chunks found for Phantom Catacombs at {}, cannot process", structureOrigin);
               } else {
                  StructureTemplateManager structureTemplateManager = level.getServer().getStructureManager();
                  Optional<StructureTemplate> room7TemplateOpt = structureTemplateManager.get(ROOM_7_TEMPLATE);
                  Optional<StructureTemplate> bossRoomTemplateOpt = structureTemplateManager.get(BOSS_ROOM_TEMPLATE);
                  if (!room7TemplateOpt.isEmpty() && !bossRoomTemplateOpt.isEmpty()) {
                     state.room7Template = room7TemplateOpt.get();
                     state.bossRoomTemplate = bossRoomTemplateOpt.get();
                     processingStates.put(structureOrigin, state);
                     ChronoDawn.LOGGER.debug("Registered processing state for {} with {} chunks", structureOrigin, state.structureChunks.size());
                  } else {
                     ChronoDawn.LOGGER.error("Failed to load templates for boss_room placement");
                     dimensionProcessed.add(structureOrigin);
                  }
               }
            }
         }
      }
   }

   public static void progressAllProcessing(ServerLevel level) {
      ResourceLocation currentDimension = level.dimension().location();
      List<BlockPos> activeStructures = new ArrayList<>();

      for (Entry<BlockPos, PhantomCatacombsBossRoomPlacer.StructureProcessingState> entry : processingStates.entrySet()) {
         PhantomCatacombsBossRoomPlacer.StructureProcessingState state = entry.getValue();
         if (state.dimensionId.equals(currentDimension)) {
            activeStructures.add(entry.getKey());
         }
      }

      for (BlockPos structureOrigin : activeStructures) {
         PhantomCatacombsBossRoomPlacer.StructureProcessingState state = processingStates.get(structureOrigin);
         if (state != null) {
            progressProcessing(level, state);
         }
      }
   }

   private static void progressProcessing(ServerLevel level, PhantomCatacombsBossRoomPlacer.StructureProcessingState state) {
      try {
         switch (state.phase) {
            case SEARCHING_MARKERS:
               progressMarkerSearch(level, state);
               break;
            case EVALUATING_CANDIDATES:
               progressCandidateEvaluation(level, state);
               break;
            case PLACING_ROOMS:
               progressRoomPlacement(level, state);
               break;
            case COMPLETED:
               processingStates.remove(state.structureOrigin);
               processedStructures.computeIfAbsent(state.dimensionId, k -> ConcurrentHashMap.newKeySet()).add(state.structureOrigin);
         }
      } catch (Exception e) {
         ChronoDawn.LOGGER
            .error("[PhaseExecution] ERROR processing structure {} in phase {}: {}", new Object[]{state.structureOrigin, state.phase, e.getMessage(), e});
         processingStates.remove(state.structureOrigin);
         processedStructures.computeIfAbsent(state.dimensionId, k -> ConcurrentHashMap.newKeySet()).add(state.structureOrigin);
      }
   }

   private static void progressMarkerSearch(ServerLevel level, PhantomCatacombsBossRoomPlacer.StructureProcessingState state) {
      if (state.currentChunkIndex >= state.structureChunks.size()) {
         if (state.foundMarkers.isEmpty()) {
            state.retryCount++;
            if (state.retryCount >= 5) {
               ChronoDawn.LOGGER
                  .warn(
                     "No Crying Obsidian markers found after {} retries for structure {}. Using fallback placement.", state.retryCount, state.structureOrigin
                  );
               state.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.PLACING_ROOMS;
            } else {
               state.currentChunkIndex = 0;
               state.foundMarkers.clear();
            }
         } else if (state.foundMarkers.size() < 1) {
            state.retryCount++;
            if (state.retryCount >= 5) {
               ChronoDawn.LOGGER
                  .warn(
                     "Only {} markers found after {} retries for structure {}. Using fallback placement.",
                     new Object[]{state.foundMarkers.size(), state.retryCount, state.structureOrigin}
                  );
               state.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.PLACING_ROOMS;
            } else {
               state.currentChunkIndex = 0;
               state.foundMarkers.clear();
            }
         } else {
            ChronoDawn.LOGGER.debug("Found {} Crying Obsidian markers for Phantom Catacombs at {}", state.foundMarkers.size(), state.structureOrigin);
            state.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.EVALUATING_CANDIDATES;
         }
      } else {
         ChunkPos currentChunk = state.structureChunks.get(state.currentChunkIndex);
         int minY = state.boundingBox != null ? state.boundingBox.minY() - 10 : -64;
         int maxY = state.boundingBox != null ? state.boundingBox.maxY() + 10 : 320;

         for (BlockPos pos : BlockPos.betweenClosed(
            currentChunk.getMinBlockX(), minY, currentChunk.getMinBlockZ(), currentChunk.getMaxBlockX(), maxY, currentChunk.getMaxBlockZ()
         )) {
            BlockState blockState = level.getBlockState(pos);
            if (blockState.is(Blocks.CRYING_OBSIDIAN)) {
               state.foundMarkers.add(pos.immutable());
            }
         }

         state.currentChunkIndex++;
      }
   }

   private static void progressCandidateEvaluation(ServerLevel level, PhantomCatacombsBossRoomPlacer.StructureProcessingState state) {
      if (state.candidates == null) {
         state.candidates = evaluateAllPlacements(level, state.foundMarkers, state.room7Template, state.bossRoomTemplate);
         if (state.candidates.isEmpty()) {
            ChronoDawn.LOGGER.error("No placement candidates generated for structure {}", state.structureOrigin);
            state.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.PLACING_ROOMS;
            return;
         }

         ChronoDawn.LOGGER.debug("Generated {} placement candidates for structure {}. Starting evaluation...", state.candidates.size(), state.structureOrigin);
      }

      if (state.currentCandidateIndex >= state.candidates.size()) {
         List<PhantomCatacombsBossRoomPlacer.PlacementCandidate> collisionFree = state.candidates.stream().filter(c -> c.collidingRoomCount == 0).toList();
         if (!collisionFree.isEmpty()) {
            state.selectedCandidate = collisionFree.get(level.random.nextInt(collisionFree.size()));
            ChronoDawn.LOGGER
               .debug(
                  "Phase 1: Found {} collision-free placements for {}. Selected dead_end at {}, rotation {}",
                  new Object[]{collisionFree.size(), state.structureOrigin, state.selectedCandidate.deadEndPos, state.selectedCandidate.room7Rotation}
               );
         } else {
            List<PhantomCatacombsBossRoomPlacer.PlacementCandidate> minimalCollision = state.candidates
               .stream()
               .filter(c -> c.collidingRoomCount == 1)
               .toList();
            if (!minimalCollision.isEmpty()) {
               state.selectedCandidate = minimalCollision.get(level.random.nextInt(minimalCollision.size()));
               ChronoDawn.LOGGER
                  .warn(
                     "Phase 2: No collision-free placement found for {}. Using minimal collision placement (1 room). Selected dead_end at {}, rotation {}",
                     new Object[]{state.structureOrigin, state.selectedCandidate.deadEndPos, state.selectedCandidate.room7Rotation}
                  );
            } else {
               ChronoDawn.LOGGER
                  .warn(
                     "Phase 3: No safe maze connection possible for {} (all candidates have 2+ room collisions). Will place boss_room as hidden chamber.",
                     state.structureOrigin
                  );
            }
         }

         state.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.PLACING_ROOMS;
      } else {
         state.currentCandidateIndex = state.candidates.size();
      }
   }

   private static void progressRoomPlacement(ServerLevel level, PhantomCatacombsBossRoomPlacer.StructureProcessingState state) {
      if (state.selectedCandidate == null) {
         ChronoDawn.LOGGER.error("No safe maze connection possible for {}. Placing boss_room as independent hidden chamber.", state.structureOrigin);
         boolean success = placeBossRoomIndependently(level, state.structureOrigin, state.boundingBox);
         if (success) {
            ChronoDawn.LOGGER.debug("Successfully placed boss_room as hidden chamber (fallback) for {}", state.structureOrigin);
         } else {
            ChronoDawn.LOGGER.error("Failed to place boss_room even as hidden chamber for {}", state.structureOrigin);
         }

         int markersRemoved = cleanupCryingObsidianMarkers(level, state.structureOrigin, state.boundingBox, state.initialChunkPos);
         if (markersRemoved > 0) {
            ChronoDawn.LOGGER.debug("Removed {} Crying Obsidian markers from structure {} (fallback)", markersRemoved, state.structureOrigin);
         }

         state.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.COMPLETED;
      } else {
         BlockPos room7ConnectorPos = placeRoom7WithRotation(
            level, state.selectedCandidate.deadEndPos, state.selectedCandidate.room7Rotation, state.room7Template
         );
         if (room7ConnectorPos == null) {
            ChronoDawn.LOGGER
               .error(
                  "Failed to place room_7 at {} with rotation {} for structure {}",
                  new Object[]{state.selectedCandidate.deadEndPos, state.selectedCandidate.room7Rotation, state.structureOrigin}
               );

            for (BlockPos markerPos : state.foundMarkers) {
               BlockState blockState = level.getBlockState(markerPos);
               if (blockState.is(Blocks.CRYING_OBSIDIAN)) {
                  level.setBlock(markerPos, Blocks.AIR.defaultBlockState(), 3);
               }
            }

            state.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.COMPLETED;
         } else {
            lastRoom7Rotation = state.selectedCandidate.room7Rotation;
            if (placeBossRoom(level, room7ConnectorPos)) {
               ChronoDawn.LOGGER
                  .debug(
                     "Successfully placed room_7 + boss_room for structure {} (dead_end at {}, room_7 connector at {}, collision count: {})",
                     new Object[]{state.structureOrigin, state.selectedCandidate.deadEndPos, room7ConnectorPos, state.selectedCandidate.collidingRoomCount}
                  );
            } else {
               ChronoDawn.LOGGER.error("Failed to place boss_room (room_7 connector at {}) for structure {}", room7ConnectorPos, state.structureOrigin);
            }

            int markersRemoved = 0;

            for (BlockPos markerPos : state.foundMarkers) {
               BlockState blockState = level.getBlockState(markerPos);
               if (blockState.is(Blocks.CRYING_OBSIDIAN)) {
                  level.setBlock(markerPos, Blocks.AIR.defaultBlockState(), 3);
                  markersRemoved++;
               }
            }

            if (markersRemoved > 0) {
               ChronoDawn.LOGGER.debug("Removed {} Crying Obsidian markers from structure {}", markersRemoved, state.structureOrigin);
            }

            state.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.COMPLETED;
         }
      }
   }

   @Deprecated
   private static List<BlockPos> findDeadEndMarkers_OLD(ServerLevel level, ChunkPos chunkPos, BoundingBox boundingBox) {
      return new ArrayList<>();
   }

   private static int cleanupCryingObsidianMarkers(ServerLevel level, BlockPos structureOrigin, BoundingBox boundingBox, ChunkPos chunkPos) {
      int markersRemoved = 0;
      StructureManager structureManager = level.structureManager();
      HashSet<ChunkPos> structureChunks = new HashSet<>();

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         if (structureLocation != null && structureLocation.equals(PHANTOM_CATACOMBS_ID)) {
            LongSet chunks = entry.getValue();
            LongIterator pos = chunks.iterator();

            while (pos.hasNext()) {
               long chunkLong = (Long)pos.next();
               structureChunks.add(new ChunkPos(chunkLong));
            }
         }
      }

      int minY = boundingBox != null ? boundingBox.minY() - 10 : -64;
      int maxY = boundingBox != null ? boundingBox.maxY() + 10 : 320;

      for (ChunkPos structureChunk : structureChunks) {
         for (BlockPos pos : BlockPos.betweenClosed(
            structureChunk.getMinBlockX(), minY, structureChunk.getMinBlockZ(), structureChunk.getMaxBlockX(), maxY, structureChunk.getMaxBlockZ()
         )) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.CRYING_OBSIDIAN)) {
               level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
               markersRemoved++;
            }
         }
      }

      return markersRemoved;
   }

   public static void clearDimension(ResourceLocation dimensionId) {
      processedStructures.remove(dimensionId);
      processingStates.entrySet().removeIf(entry -> entry.getValue().dimensionId.equals(dimensionId));
   }

   public static void checkAndPlaceRooms(ServerLevel level) {
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         ResourceLocation dimensionId = level.dimension().location();
         processedStructures.putIfAbsent(dimensionId, ConcurrentHashMap.newKeySet());
         tickCounters.putIfAbsent(dimensionId, 0);
         int currentTick = tickCounters.compute(dimensionId, (k, v) -> (v == null ? 0 : v) + 1);
         if (currentTick < 600) {
            progressAllProcessing(level);
         } else {
            tickCounters.put(dimensionId, 0);
            if (level.players().isEmpty()) {
               progressAllProcessing(level);
            } else {
               for (ServerPlayer player : level.players()) {
                  ChunkPos playerChunkPos = new ChunkPos(player.blockPosition());

                  for (int x = -8; x <= 8; x++) {
                     for (int z = -8; z <= 8; z++) {
                        ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + x, playerChunkPos.z + z);
                        processStructure(level, chunkPos);
                     }
                  }
               }

               progressAllProcessing(level);
            }
         }
      }
   }

   private static List<PhantomCatacombsBossRoomPlacer.PlacementCandidate> evaluateAllPlacements(
      ServerLevel level, List<BlockPos> deadEndMarkers, StructureTemplate room7Template, StructureTemplate bossRoomTemplate
   ) {
      List<PhantomCatacombsBossRoomPlacer.PlacementCandidate> candidates = new ArrayList<>();
      BlockPos room7Marker = findRoom7Marker(room7Template);
      BlockPos room7Connector = findRoom7Connector(room7Template);
      BlockPos bossRoomConnector = findBossRoomConnector(bossRoomTemplate);
      if (room7Marker != null && room7Connector != null && bossRoomConnector != null) {
         ChronoDawn.LOGGER.debug("Evaluating placement candidates for {} dead_ends (each with 3 boss_room directions)", deadEndMarkers.size());

         for (BlockPos deadEndPos : deadEndMarkers) {
            Direction entranceDir = detectEntranceDirection(level, deadEndPos);
            ChronoDawn.LOGGER.debug("Dead_end at {}: detected maze entrance direction {} (will exclude from boss_room placement)", deadEndPos, entranceDir);

            for (Rotation rotation : Rotation.values()) {
               Direction bossRoomDir = getExitDirectionFromRotation(rotation);
               if (bossRoomDir == entranceDir) {
                  ChronoDawn.LOGGER
                     .debug(
                        "  Rotation {}: Skipping (boss_room direction {} conflicts with maze entrance {})", new Object[]{rotation, bossRoomDir, entranceDir}
                     );
               } else {
                  ChronoDawn.LOGGER.debug("  Rotation {}: Evaluating (boss_room direction {} is safe)", rotation, bossRoomDir);
                  StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation);
                  BlockPos rotatedMarker = StructureTemplate.calculateRelativePosition(settings, room7Marker);
                  BlockPos rotatedConnector = StructureTemplate.calculateRelativePosition(settings, room7Connector);
                  BlockPos room7PlacementPos = deadEndPos.subtract(rotatedMarker);
                  BlockPos connectorWorldPos = room7PlacementPos.offset(rotatedConnector);
                  Direction exitDir = getExitDirectionFromRotation(rotation);
                  Rotation bossRoomRotation = getBossRoomRotationFromExitDirection(exitDir);
                  StructurePlaceSettings bossSettings = new StructurePlaceSettings().setRotation(bossRoomRotation);
                  BlockPos rotatedBossConnector = StructureTemplate.calculateRelativePosition(bossSettings, bossRoomConnector);

                  BlockPos exitOffset = switch (exitDir) {
                     case NORTH -> new BlockPos(0, 0, -1);
                     case SOUTH -> new BlockPos(0, 0, 1);
                     case EAST -> new BlockPos(1, 0, 0);
                     case WEST -> new BlockPos(-1, 0, 0);
                     default -> BlockPos.ZERO;
                  };
                  BlockPos bossRoomPlacementPos = connectorWorldPos.subtract(rotatedBossConnector).offset(exitOffset);
                  BoundingBox bossRoomBox = calculateBossRoomBoundingBox(bossRoomPlacementPos, bossRoomTemplate.getSize(), bossRoomRotation);
                  Set<BlockPos> collidingRooms = new HashSet<>();
                  int collidingCount = countCollidingRooms(level, bossRoomBox, collidingRooms);
                  candidates.add(
                     new PhantomCatacombsBossRoomPlacer.PlacementCandidate(
                        deadEndPos, rotation, room7PlacementPos, connectorWorldPos, bossRoomPlacementPos, bossRoomRotation, collidingCount, collidingRooms
                     )
                  );
               }
            }
         }

         ChronoDawn.LOGGER.debug("Evaluation complete: {} candidates generated", candidates.size());
         return candidates;
      } else {
         ChronoDawn.LOGGER.error("Cannot evaluate placements: missing markers in templates");
         return candidates;
      }
   }

   private static int countCollidingRooms(ServerLevel level, BoundingBox bossRoomBox, Set<BlockPos> outCollidingRooms) {
      Set<BlockPos> collidingRooms = new HashSet<>();

      for (BlockPos pos : BlockPos.betweenClosed(
         bossRoomBox.minX(), bossRoomBox.minY(), bossRoomBox.minZ(), bossRoomBox.maxX(), bossRoomBox.maxY(), bossRoomBox.maxZ()
      )) {
         BlockState state = level.getBlockState(pos);
         if (state.is(Blocks.DEEPSLATE_BRICKS)
            || state.is(Blocks.DEEPSLATE_BRICK_STAIRS)
            || state.is(Blocks.DEEPSLATE_BRICK_SLAB)
            || state.is(Blocks.DEEPSLATE_BRICK_WALL)
            || state.is(Blocks.CRYING_OBSIDIAN)
            || state.is(Blocks.CHEST)) {
            BlockPos roomOrigin = new BlockPos(Math.floorDiv(pos.getX(), 7) * 7, pos.getY(), Math.floorDiv(pos.getZ(), 7) * 7);
            collidingRooms.add(roomOrigin);
         }
      }

      if (outCollidingRooms != null) {
         outCollidingRooms.addAll(collidingRooms);
      }

      return collidingRooms.size();
   }

   private static BoundingBox calculateBossRoomBoundingBox(BlockPos placementPos, Vec3i templateSize, Rotation rotation) {
      int sizeX = templateSize.getX();
      int sizeY = templateSize.getY();
      int sizeZ = templateSize.getZ();
      int rotatedSizeX;
      int rotatedSizeZ;
      if (rotation != Rotation.CLOCKWISE_90 && rotation != Rotation.COUNTERCLOCKWISE_90) {
         rotatedSizeX = sizeX;
         rotatedSizeZ = sizeZ;
      } else {
         rotatedSizeX = sizeZ;
         rotatedSizeZ = sizeX;
      }

      return BoundingBox.fromCorners(placementPos, placementPos.offset(rotatedSizeX - 1, sizeY - 1, rotatedSizeZ - 1));
   }

   private static boolean placeBossRoomIndependently(ServerLevel level, BlockPos structureOrigin, BoundingBox boundingBox) {
      BlockPos hiddenPos = calculateHiddenChamberPosition(structureOrigin, boundingBox);
      ChronoDawn.LOGGER.warn("Placing boss_room as independent hidden chamber at {} (no safe maze connection possible)", hiddenPos);
      StructureTemplateManager structureManager = level.getStructureManager();
      Optional<StructureTemplate> templateOptional = structureManager.get(BOSS_ROOM_TEMPLATE);
      if (templateOptional.isEmpty()) {
         ChronoDawn.LOGGER.error("Boss_room template {} not found", BOSS_ROOM_TEMPLATE);
         return false;
      }

      StructureTemplate template = templateOptional.get();
      ResourceLocation processorListId = CompatResourceLocation.create("chronodawn", "convert_decorative_water");
      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(Rotation.NONE).setIgnoreEntities(false);

      try {
         Registry<StructureProcessorList> processorListRegistry = level.registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);
         StructureProcessorList processorList = (StructureProcessorList)processorListRegistry.get(processorListId);
         if (processorList != null) {
            ChronoDawn.LOGGER.debug("Found processor list {}: {} processors (hidden chamber)", processorListId, processorList.list().size());

            for (StructureProcessor processor : processorList.list()) {
               settings.addProcessor(processor);
            }

            ChronoDawn.LOGGER.debug("Successfully applied {} structure processors to hidden chamber placement", processorList.list().size());
         } else {
            ChronoDawn.LOGGER.error("Processor list {} not found for hidden chamber", processorListId);
         }
      } catch (Exception e) {
         ChronoDawn.LOGGER.error("Failed to load processor list for hidden chamber: {}", e.getMessage(), e);
      }

      template.placeInWorld(level, hiddenPos, hiddenPos, settings, level.random, 2);
      Vec3i templateSize = template.getSize();
      int sizeX = templateSize.getX();
      int sizeY = templateSize.getY();
      int sizeZ = templateSize.getZ();
      int waterRemovedAfter = 0;

      for (BlockPos pos : BlockPos.betweenClosed(hiddenPos.offset(-1, -1, -1), hiddenPos.offset(sizeX + 1, sizeY + 1, sizeZ + 1))) {
         BlockState state = level.getBlockState(pos);
         if (state.getFluidState().isSource() && state.is(Blocks.WATER)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            waterRemovedAfter++;
         }
      }

      if (waterRemovedAfter > 0) {
         ChronoDawn.LOGGER.debug("Removed {} water blocks after hidden chamber placement", waterRemovedAfter);
      }

      int waterloggedRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(hiddenPos, hiddenPos.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            boolean waterlogged = (Boolean)state.getValue(BlockStateProperties.WATERLOGGED);
            if (waterlogged) {
               level.setBlock(pos, (BlockState)state.setValue(BlockStateProperties.WATERLOGGED, false), 2);
               waterloggedRemoved++;
            }
         }
      }

      if (waterloggedRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} waterlogged blocks after hidden chamber placement", waterloggedRemoved);
      }

      ChronoDawn.LOGGER.debug("Successfully placed boss_room as hidden chamber at {}", hiddenPos);
      BlockPos bossRoomCenter = hiddenPos.offset(10, 4, 10);
      ChronoDawn.LOGGER.debug("Registering hidden chamber for Temporal Phantom spawning: placement at {}, center at {}", hiddenPos, bossRoomCenter);
      TemporalPhantomSpawner.registerBossRoom(level, bossRoomCenter);
      return true;
   }

   private static BlockPos calculateHiddenChamberPosition(BlockPos structureOrigin, BoundingBox boundingBox) {
      if (boundingBox == null) {
         ChronoDawn.LOGGER.warn("BoundingBox is null in calculateHiddenChamberPosition, using structure origin as fallback");
         return structureOrigin.offset(60, -10, 60);
      } else {
         int centerX = (boundingBox.minX() + boundingBox.maxX()) / 2;
         int centerZ = (boundingBox.minZ() + boundingBox.maxZ()) / 2;
         int baseY = boundingBox.minY();
         return new BlockPos(centerX + 60, baseY - 10, centerZ + 60);
      }
   }

   public static void register() {
      TickEvent.SERVER_POST.register((Server)server -> {
         for (ServerLevel level : server.getAllLevels()) {
            checkAndPlaceRooms(level);
         }
      });
      ChronoDawn.LOGGER.debug("Registered PhantomCatacombsBossRoomPlacer");
   }

   private static class PlacementCandidate {
      final BlockPos deadEndPos;
      final Rotation room7Rotation;
      final BlockPos room7PlacementPos;
      final BlockPos connectorWorldPos;
      final BlockPos bossRoomPlacementPos;
      final Rotation bossRoomRotation;
      final int collidingRoomCount;
      final Set<BlockPos> collidingRoomOrigins;

      PlacementCandidate(
         BlockPos deadEndPos,
         Rotation room7Rotation,
         BlockPos room7PlacementPos,
         BlockPos connectorWorldPos,
         BlockPos bossRoomPlacementPos,
         Rotation bossRoomRotation,
         int collidingRoomCount,
         Set<BlockPos> collidingRoomOrigins
      ) {
         this.deadEndPos = deadEndPos;
         this.room7Rotation = room7Rotation;
         this.room7PlacementPos = room7PlacementPos;
         this.connectorWorldPos = connectorWorldPos;
         this.bossRoomPlacementPos = bossRoomPlacementPos;
         this.bossRoomRotation = bossRoomRotation;
         this.collidingRoomCount = collidingRoomCount;
         this.collidingRoomOrigins = collidingRoomOrigins;
      }
   }

   private enum ProcessingPhase {
      SEARCHING_MARKERS,
      EVALUATING_CANDIDATES,
      PLACING_ROOMS,
      COMPLETED;
   }

   private static class StructureProcessingState {
      final BlockPos structureOrigin;
      final ChunkPos initialChunkPos;
      final ResourceLocation dimensionId;
      final BoundingBox boundingBox;
      PhantomCatacombsBossRoomPlacer.ProcessingPhase phase;
      List<ChunkPos> structureChunks;
      int currentChunkIndex;
      List<BlockPos> foundMarkers;
      int retryCount;
      List<PhantomCatacombsBossRoomPlacer.PlacementCandidate> candidates;
      int currentCandidateIndex;
      StructureTemplate room7Template;
      StructureTemplate bossRoomTemplate;
      PhantomCatacombsBossRoomPlacer.PlacementCandidate selectedCandidate;

      StructureProcessingState(BlockPos origin, ChunkPos chunkPos, ResourceLocation dimId, BoundingBox bbox) {
         this.structureOrigin = origin;
         this.initialChunkPos = chunkPos;
         this.dimensionId = dimId;
         this.boundingBox = bbox;
         this.phase = PhantomCatacombsBossRoomPlacer.ProcessingPhase.SEARCHING_MARKERS;
         this.structureChunks = new ArrayList<>();
         this.currentChunkIndex = 0;
         this.foundMarkers = new ArrayList<>();
         this.retryCount = 0;
         this.candidates = null;
         this.currentCandidateIndex = 0;
      }
   }
}
