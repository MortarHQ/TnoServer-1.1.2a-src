package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.worldgen.processors.BossRoomProtectionProcessor;
import com.chronodawn.worldgen.processors.CopyFluidLevelProcessor;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.TickEvent.Server;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class MasterClockBossRoomPlacer {
   private static final ResourceLocation MASTER_CLOCK_ID = CompatResourceLocation.create("chronodawn", "master_clock");
   private static final ResourceLocation BOSS_ROOM_TEMPLATE = CompatResourceLocation.create("chronodawn", "master_clock_boss_room");
   private static final ResourceLocation STAIRS_TEMPLATE = CompatResourceLocation.create("chronodawn", "master_clock_stairs");
   private static final ResourceLocation STAIRS_BOTTOM_TEMPLATE = CompatResourceLocation.create("chronodawn", "master_clock_stairs_bottom");
   private static final ResourceLocation CORRIDOR_TEMPLATE = CompatResourceLocation.create("chronodawn", "master_clock_corridor");
   private static final int BOSS_ROOM_Y = -50;
   private static final int CORRIDOR_Y = -30;
   private static final Map<ResourceLocation, Set<BlockPos>> processedStructures = new ConcurrentHashMap<>();
   private static final Map<BlockPos, MasterClockBossRoomPlacer.StructureProcessingState> processingStates = new ConcurrentHashMap<>();
   private static final int CHECK_INTERVAL = 600;
   private static final Map<ResourceLocation, Integer> tickCounters = new ConcurrentHashMap<>();

   private static boolean hasMasterClock(ServerLevel level, ChunkPos chunkPos) {
      StructureManager structureManager = level.structureManager();

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         if (structureLocation != null && structureLocation.equals(MASTER_CLOCK_ID)) {
            return true;
         }
      }

      return false;
   }

   private static BlockPos getStructureOrigin(ServerLevel level, ChunkPos chunkPos) {
      StructureManager structureManager = level.structureManager();

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         if (structureLocation != null && structureLocation.equals(MASTER_CLOCK_ID)) {
            LongSet structureStart = entry.getValue();
            if (!structureStart.isEmpty()) {
               long firstChunk = structureStart.iterator().nextLong();
               return new ChunkPos(firstChunk).getWorldPosition();
            }
         }
      }

      return null;
   }

   private static BlockPos findSurfaceDropperMarker(ServerLevel level, BlockPos structureOrigin) {
      int searchRadius = 50;
      int maxY = 150;
      int minY = -10;
      ChronoDawn.LOGGER.debug("Searching for Surface Dropper marker around {}", structureOrigin);

      for (int y = maxY; y >= minY; y--) {
         for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
               BlockPos checkPos = new BlockPos(structureOrigin.getX() + x, y, structureOrigin.getZ() + z);
               BlockState state = level.getBlockState(checkPos);
               if (state.is(Blocks.DROPPER)) {
                  ChronoDawn.LOGGER.debug("Found Surface Dropper marker at {}", checkPos);
                  return checkPos;
               }
            }
         }
      }

      ChronoDawn.LOGGER.warn("No Surface Dropper marker found near {}", structureOrigin);
      return null;
   }

   private static BlockPos findSurfaceJigsawBlock(ServerLevel level, BlockPos structureOrigin) {
      int searchRadius = 50;
      int maxY = 150;
      int minY = -10;
      ChronoDawn.LOGGER.debug("Searching for Surface Jigsaw Block around {}", structureOrigin);

      for (int y = maxY; y >= minY; y--) {
         for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
               BlockPos checkPos = new BlockPos(structureOrigin.getX() + x, y, structureOrigin.getZ() + z);
               BlockState state = level.getBlockState(checkPos);
               if (state.is(Blocks.JIGSAW)) {
                  BlockEntity blockEntity = level.getBlockEntity(checkPos);
                  if (blockEntity instanceof JigsawBlockEntity) {
                     ChronoDawn.LOGGER.debug("Found Surface Jigsaw Block at {}", checkPos);
                     return checkPos;
                  }
               }
            }
         }
      }

      ChronoDawn.LOGGER.warn("No Surface Jigsaw Block found near {}", structureOrigin);
      return null;
   }

   private static BlockPos findJigsawInTemplate(StructureTemplate template, ServerLevel level, Rotation rotation) {
      BlockPos tempPos = new BlockPos(0, 300, 0);
      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setIgnoreEntities(true);
      template.placeInWorld(level, tempPos, tempPos, settings, level.random, 2);
      Vec3i size = template.getSize();
      BlockPos jigsawOffset = null;
      int blocksChecked = 0;
      int jigsawBlocksFound = 0;

      for (BlockPos pos : BlockPos.betweenClosed(tempPos, tempPos.offset(size))) {
         BlockState state = level.getBlockState(pos);
         blocksChecked++;
         if (state.is(Blocks.JIGSAW)) {
            jigsawBlocksFound++;
            jigsawOffset = pos.subtract(tempPos);
            break;
         }
      }

      for (BlockPos pos : BlockPos.betweenClosed(tempPos, tempPos.offset(size))) {
         level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
      }

      return jigsawOffset;
   }

   private static Rotation getRotationFromDirection(Direction direction) {
      return switch (direction) {
         case SOUTH -> Rotation.CLOCKWISE_90;
         case WEST -> Rotation.CLOCKWISE_180;
         case NORTH -> Rotation.COUNTERCLOCKWISE_90;
         case EAST -> Rotation.NONE;
         default -> Rotation.NONE;
      };
   }

   private static Rotation getRotationFromJigsawFacing(Direction facing) {
      return switch (facing) {
         case SOUTH -> Rotation.CLOCKWISE_90;
         case WEST -> Rotation.CLOCKWISE_180;
         case NORTH -> Rotation.COUNTERCLOCKWISE_90;
         case EAST -> Rotation.NONE;
         default -> Rotation.NONE;
      };
   }

   private static MasterClockBossRoomPlacer.StairsPlacementResult placeStairsDynamically(
      ServerLevel level, BlockPos dropperPos, Direction horizontalDirection, int targetY
   ) {
      ChronoDawn.LOGGER
         .debug(
            "Placing stairs dynamically from Dropper at {} to Y={} (rotation for horizontal direction: {})",
            new Object[]{dropperPos, targetY, horizontalDirection}
         );
      StructureTemplateManager structureTemplateManager = level.getStructureManager();
      Optional<StructureTemplate> templateOptional = structureTemplateManager.get(STAIRS_TEMPLATE);
      if (templateOptional.isEmpty()) {
         ChronoDawn.LOGGER.error("Failed to load stairs template: {}", STAIRS_TEMPLATE);
         return null;
      }

      StructureTemplate template = templateOptional.get();
      Rotation rotation = getRotationFromDirection(horizontalDirection);
      Vec3i templateSize = template.getSize();
      int stairsHeight = templateSize.getY();
      ChronoDawn.LOGGER.debug("Stairs template size: {}", templateSize);
      Vec3i stairsTemplateSize = template.getSize(rotation);

      BlockPos stairsJigsawOffset = switch (rotation) {
         case NONE -> new BlockPos(7, 7, 7);
         case CLOCKWISE_90 -> new BlockPos(-7, 7, 7);
         case CLOCKWISE_180 -> new BlockPos(-7, 7, -7);
         case COUNTERCLOCKWISE_90 -> new BlockPos(7, 7, -7);
         default -> throw new MatchException(null, null);
      };
      ChronoDawn.LOGGER.debug("Stairs Jigsaw offset for rotation {}: {}", rotation, stairsJigsawOffset);
      BlockPos stairsPlacementPos = dropperPos.subtract(stairsJigsawOffset).below();
      ChronoDawn.LOGGER.debug("Stairs placement position (for Jigsaw-Dropper alignment): {}", stairsPlacementPos);
      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setIgnoreEntities(false);
      ResourceLocation processorListId = CompatResourceLocation.create("chronodawn", "convert_decorative_water");

      try {
         Registry<StructureProcessorList> processorListRegistry = level.registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);
         StructureProcessorList processorList = (StructureProcessorList)processorListRegistry.get(processorListId);
         if (processorList != null) {
            ChronoDawn.LOGGER.debug("Applying {} processors for stairs from {}", processorList.list().size(), processorListId);

            for (StructureProcessor processor : processorList.list()) {
               settings.addProcessor(processor);
            }
         } else {
            ChronoDawn.LOGGER.warn("Processor list {} not found for stairs", processorListId);
         }
      } catch (Exception e) {
         ChronoDawn.LOGGER.error("Failed to load processor list for stairs: {}", e.getMessage(), e);
      }

      BlockPos currentPos = stairsPlacementPos;
      int stairsPlaced = 0;
      ChronoDawn.LOGGER.debug("Starting stairs placement at Y={}, target Y={}, stairs height={}", new Object[]{currentPos.getY(), targetY, stairsHeight});
      Block decorativeWaterBlock = (Block)BuiltInRegistries.BLOCK.get(CompatResourceLocation.create("chronodawn", "decorative_water"));
      List<BlockPos> jigsawsToRemove = new ArrayList<>();
      List<BlockPos> stairsPositions = new ArrayList<>();
      List<BoundingBox> protectedAreas = new ArrayList<>();

      while (currentPos.getY() - stairsHeight > targetY) {
         ChronoDawn.LOGGER.debug("Placing stairs #{} at {}", stairsPlaced + 1, currentPos);
         int waterRemoved = removeAquiferWaterBeforePlacement(level, currentPos, stairsTemplateSize, decorativeWaterBlock, protectedAreas);
         if (waterRemoved > 0) {
            ChronoDawn.LOGGER.debug("Removed {} Aquifer water blocks before placing stairs #{}", waterRemoved, stairsPlaced + 1);
         }

         template.placeInWorld(level, currentPos, currentPos, settings, level.random, 3);
         ChronoDawn.LOGGER.debug("IMMEDIATE finalize for stairs #{} at {}", stairsPlaced + 1, currentPos);
         finalizeWaterloggingAfterPlacement(level, currentPos, templateSize, rotation);
         BossRoomProtectionProcessor.processPendingMarkersImmediate(level);
         BlockPos finalCurrentPos = currentPos.immutable();
         Vec3i finalTemplateSize = templateSize;
         Rotation finalRotation = rotation;
         int finalStairsNumber = stairsPlaced + 1;
         level.getServer()
            .tell(
               new TickTask(
                  level.getServer().getTickCount() + 2, () -> finalizeWaterloggingAfterPlacement(level, finalCurrentPos, finalTemplateSize, finalRotation)
               )
            );
         BoundingBox stairsBoundingBox = BoundingBox.fromCorners(
            currentPos, currentPos.offset(stairsTemplateSize.getX() - 1, stairsTemplateSize.getY() - 1, stairsTemplateSize.getZ() - 1)
         );
         protectedAreas.add(stairsBoundingBox);
         stairsPositions.add(new BlockPos(currentPos.getX(), currentPos.getY(), currentPos.getZ()));
         BlockPos topJigsawPos = currentPos.offset(stairsJigsawOffset);
         jigsawsToRemove.add(topJigsawPos);

         BlockPos bottomJigsawOffset = switch (rotation) {
            case NONE -> new BlockPos(7, 0, 7);
            case CLOCKWISE_90 -> new BlockPos(-7, 0, 7);
            case CLOCKWISE_180 -> new BlockPos(-7, 0, -7);
            case COUNTERCLOCKWISE_90 -> new BlockPos(7, 0, -7);
            default -> throw new MatchException(null, null);
         };
         BlockPos bottomJigsawPos = currentPos.offset(bottomJigsawOffset);
         jigsawsToRemove.add(bottomJigsawPos);
         stairsPlaced++;
         currentPos = currentPos.below(stairsHeight);
         if (stairsPlaced > 100) {
            ChronoDawn.LOGGER.error("Stairs placement limit reached (safety check)");
            break;
         }
      }

      ChronoDawn.LOGGER.debug("Placed {} stairs segments", stairsPlaced);
      ChronoDawn.LOGGER.debug("Final stairs position (for stairs_bottom placement): Y={}", currentPos.getY());
      BlockPos lastStairsPos = currentPos.above(stairsHeight);
      BlockPos stairsBottomPos = new BlockPos(currentPos.getX(), lastStairsPos.getY() - 4, currentPos.getZ());
      ChronoDawn.LOGGER
         .debug("Last stairs at Y={}, stairs_bottom adjusted to Y={} (4 blocks higher for Jigsaw alignment)", lastStairsPos.getY(), stairsBottomPos.getY());
      Optional<StructureTemplate> stairsBottomOptional = structureTemplateManager.get(STAIRS_BOTTOM_TEMPLATE);
      if (stairsBottomOptional.isEmpty()) {
         ChronoDawn.LOGGER.error("Failed to load stairs_bottom template: {}", STAIRS_BOTTOM_TEMPLATE);
         return null;
      }

      StructureTemplate stairsBottomTemplate = stairsBottomOptional.get();
      Vec3i stairsBottomTemplateSizeOriginal = stairsBottomTemplate.getSize();
      Vec3i stairsBottomTemplateSize = stairsBottomTemplate.getSize(rotation);
      StructurePlaceSettings stairsBottomSettings = new StructurePlaceSettings().setRotation(rotation).setIgnoreEntities(false);
      ResourceLocation processorListId2 = CompatResourceLocation.create("chronodawn", "convert_decorative_water");

      try {
         Registry<StructureProcessorList> processorListRegistry = level.registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);
         StructureProcessorList processorList = (StructureProcessorList)processorListRegistry.get(processorListId2);
         if (processorList != null) {
            ChronoDawn.LOGGER.debug("Applying {} processors for stairs_bottom from {}", processorList.list().size(), processorListId2);

            for (StructureProcessor processor : processorList.list()) {
               stairsBottomSettings.addProcessor(processor);
            }
         } else {
            ChronoDawn.LOGGER.warn("Processor list {} not found for stairs_bottom", processorListId2);
         }
      } catch (Exception e) {
         ChronoDawn.LOGGER.error("Failed to load processor list for stairs_bottom: {}", e.getMessage(), e);
      }

      int stairsBottomWaterRemoved = removeAquiferWaterBeforePlacement(level, stairsBottomPos, stairsBottomTemplateSize, decorativeWaterBlock, protectedAreas);
      if (stairsBottomWaterRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} Aquifer water blocks before placing stairs_bottom", stairsBottomWaterRemoved);
      }

      stairsBottomTemplate.placeInWorld(level, stairsBottomPos, stairsBottomPos, stairsBottomSettings, level.random, 3);
      ChronoDawn.LOGGER.debug("Placed stairs_bottom at {}", stairsBottomPos);
      finalizeWaterloggingAfterPlacement(level, stairsBottomPos, stairsBottomTemplateSizeOriginal, rotation);
      BossRoomProtectionProcessor.processPendingMarkersImmediate(level);
      BlockPos finalStairsBottomPos = stairsBottomPos.immutable();
      Vec3i finalStairsBottomTemplateSizeOriginal = stairsBottomTemplateSizeOriginal;
      Rotation finalRotation = rotation;
      level.getServer()
         .tell(
            new TickTask(
               level.getServer().getTickCount() + 2,
               () -> finalizeWaterloggingAfterPlacement(level, finalStairsBottomPos, finalStairsBottomTemplateSizeOriginal, finalRotation)
            )
         );
      BoundingBox stairsBottomBoundingBox = BoundingBox.fromCorners(
         stairsBottomPos, stairsBottomPos.offset(stairsBottomTemplateSize.getX() - 1, stairsBottomTemplateSize.getY() - 1, stairsBottomTemplateSize.getZ() - 1)
      );
      protectedAreas.add(stairsBottomBoundingBox);
      ChronoDawn.LOGGER.debug("Stairs_bottom bounding box: {}", stairsBottomBoundingBox);

      BlockPos stairsBottomTopJigsawOffset = switch (rotation) {
         case NONE -> new BlockPos(7, 11, 7);
         case CLOCKWISE_90 -> new BlockPos(-7, 11, 7);
         case CLOCKWISE_180 -> new BlockPos(-7, 11, -7);
         case COUNTERCLOCKWISE_90 -> new BlockPos(7, 11, -7);
         default -> throw new MatchException(null, null);
      };
      BlockPos stairsBottomTopJigsawPos = stairsBottomPos.offset(stairsBottomTopJigsawOffset);
      jigsawsToRemove.add(stairsBottomTopJigsawPos);

      BlockPos stairsBottomJigsawOffset = switch (rotation) {
         case NONE -> new BlockPos(14, 1, 7);
         case CLOCKWISE_90 -> new BlockPos(-7, 1, 14);
         case CLOCKWISE_180 -> new BlockPos(-14, 1, -7);
         case COUNTERCLOCKWISE_90 -> new BlockPos(7, 1, -14);
         default -> throw new MatchException(null, null);
      };
      BlockPos stairsBottomJigsawPos = stairsBottomPos.offset(stairsBottomJigsawOffset);
      ChronoDawn.LOGGER.debug("Stairs_bottom side Jigsaw Block at {} (offset: {})", stairsBottomJigsawPos, stairsBottomJigsawOffset);
      BlockState jigsawState = level.getBlockState(stairsBottomJigsawPos);
      if (!jigsawState.is(Blocks.JIGSAW)) {
         ChronoDawn.LOGGER.error("Expected Jigsaw Block at {}, but found: {}", stairsBottomJigsawPos, jigsawState.getBlock());
      } else {
         FrontAndTop jigsawOrientation = (FrontAndTop)jigsawState.getValue(JigsawBlock.ORIENTATION);
         Direction jigsawFacing = jigsawOrientation.front();
         ChronoDawn.LOGGER.debug("Stairs_bottom side Jigsaw Block facing: {}", jigsawFacing);
      }

      return new MasterClockBossRoomPlacer.StairsPlacementResult(
         stairsBottomJigsawPos, jigsawsToRemove, stairsPositions, templateSize, rotation, protectedAreas
      );
   }

   private static BlockPos findStairsBottomJigsawBlock(ServerLevel level, BlockPos stairsBottomStart, Vec3i size) {
      int sizeX = size.getX();
      int sizeY = size.getY();
      int sizeZ = size.getZ();

      for (BlockPos pos : BlockPos.betweenClosed(stairsBottomStart, stairsBottomStart.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.is(Blocks.JIGSAW)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof JigsawBlockEntity) {
               ChronoDawn.LOGGER.debug("Found stairs_bottom Jigsaw Block at {}", pos);
               return pos;
            }
         }
      }

      ChronoDawn.LOGGER.warn("No Jigsaw Block found in stairs_bottom");
      return null;
   }

   private static MasterClockBossRoomPlacer.CorridorPlacementResult placeCorridor(
      ServerLevel level,
      BlockPos stairsBottomJigsawPos,
      Direction direction,
      List<BlockPos> jigsawsToRemove,
      List<BlockPos> stairsPositions,
      Vec3i stairsTemplateSize,
      Rotation stairsRotation,
      List<BoundingBox> protectedAreas
   ) {
      ChronoDawn.LOGGER.debug("Placing corridor from stairs_bottom Jigsaw Block at {} (direction: {})", stairsBottomJigsawPos, direction);
      StructureTemplateManager structureTemplateManager = level.getStructureManager();
      Optional<StructureTemplate> templateOptional = structureTemplateManager.get(CORRIDOR_TEMPLATE);
      if (templateOptional.isEmpty()) {
         ChronoDawn.LOGGER.error("Failed to load corridor template: {}", CORRIDOR_TEMPLATE);
         return null;
      }

      StructureTemplate template = templateOptional.get();
      Rotation rotation = getRotationFromJigsawFacing(direction);
      ChronoDawn.LOGGER.debug("Corridor rotation from Jigsaw facing {}: {}", direction, rotation);

      BlockPos corridorStairsBottomJigsawOffset = switch (rotation) {
         case NONE -> new BlockPos(0, 10, 7);
         case CLOCKWISE_90 -> new BlockPos(-7, 10, 0);
         case CLOCKWISE_180 -> new BlockPos(0, 10, -7);
         case COUNTERCLOCKWISE_90 -> new BlockPos(7, 10, 0);
         default -> throw new MatchException(null, null);
      };

      BlockPos jigsawOffset = switch (rotation) {
         case NONE -> new BlockPos(1, 0, 0);
         case CLOCKWISE_90 -> new BlockPos(0, 0, 1);
         case CLOCKWISE_180 -> new BlockPos(-1, 0, 0);
         case COUNTERCLOCKWISE_90 -> new BlockPos(0, 0, -1);
         default -> throw new MatchException(null, null);
      };
      BlockPos corridorPlacementPos = stairsBottomJigsawPos.subtract(corridorStairsBottomJigsawOffset).offset(jigsawOffset);
      BlockPos calculatedCorridorJigsawPos = corridorPlacementPos.offset(corridorStairsBottomJigsawOffset);
      ChronoDawn.LOGGER.debug("Corridor placement position: {} (offset by {} to prevent overlap)", corridorPlacementPos, jigsawOffset);
      ChronoDawn.LOGGER
         .debug(
            "Alignment check: stairs_bottom Jigsaw={}, corridor Jigsaw will be at={}, distance={}",
            new Object[]{stairsBottomJigsawPos, calculatedCorridorJigsawPos, stairsBottomJigsawPos.distManhattan(calculatedCorridorJigsawPos)}
         );
      Block decorativeWaterBlock = (Block)BuiltInRegistries.BLOCK.get(CompatResourceLocation.create("chronodawn", "decorative_water"));
      Vec3i corridorSize = template.getSize();
      Vec3i corridorTemplateSize = template.getSize(rotation);
      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setIgnoreEntities(false);
      ResourceLocation processorListId = CompatResourceLocation.create("chronodawn", "convert_decorative_water");

      try {
         Registry<StructureProcessorList> processorListRegistry = level.registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);
         StructureProcessorList processorList = (StructureProcessorList)processorListRegistry.get(processorListId);
         if (processorList != null) {
            for (StructureProcessor processor : processorList.list()) {
               settings.addProcessor(processor);
            }
         }
      } catch (Exception e) {
         ChronoDawn.LOGGER.error("Failed to load processor list: {}", e.getMessage(), e);
      }

      int corridorWaterRemoved = removeAquiferWaterBeforePlacement(level, corridorPlacementPos, corridorTemplateSize, decorativeWaterBlock, protectedAreas);
      if (corridorWaterRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} Aquifer water blocks before placing corridor", corridorWaterRemoved);
      }

      template.placeInWorld(level, corridorPlacementPos, corridorPlacementPos, settings, level.random, 3);
      ChronoDawn.LOGGER.debug("Placed corridor at {}", corridorPlacementPos);
      BoundingBox corridorBoundingBox = BoundingBox.fromCorners(
         corridorPlacementPos, corridorPlacementPos.offset(corridorTemplateSize.getX() - 1, corridorTemplateSize.getY() - 1, corridorTemplateSize.getZ() - 1)
      );
      ChronoDawn.LOGGER.debug("Corridor bounding box: {}", corridorBoundingBox);
      boolean overlapsStairsBottom = false;

      for (BoundingBox protectedBox : protectedAreas) {
         if (corridorBoundingBox.intersects(protectedBox)) {
            ChronoDawn.LOGGER.warn("OVERLAP DETECTED: Corridor {} overlaps with protected area {}", corridorBoundingBox, protectedBox);
            overlapsStairsBottom = true;
         }
      }

      BlockState actualCorridorJigsawState = level.getBlockState(calculatedCorridorJigsawPos);
      if (actualCorridorJigsawState.is(Blocks.JIGSAW)) {
         ChronoDawn.LOGGER.debug("VERIFIED: Corridor's stairs_bottom side Jigsaw found at expected position {}", calculatedCorridorJigsawPos);
      } else {
         ChronoDawn.LOGGER
            .warn("MISALIGNMENT: Expected corridor Jigsaw at {}, but found: {}", calculatedCorridorJigsawPos, actualCorridorJigsawState.getBlock());

         for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
               for (int dz = -2; dz <= 2; dz++) {
                  BlockPos searchPos = calculatedCorridorJigsawPos.offset(dx, dy, dz);
                  if (level.getBlockState(searchPos).is(Blocks.JIGSAW)) {
                     ChronoDawn.LOGGER.warn("Found corridor Jigsaw at {} (offset from expected: {}, {}, {})", new Object[]{searchPos, dx, dy, dz});
                  }
               }
            }
         }
      }

      finalizeWaterloggingAfterPlacement(level, corridorPlacementPos, corridorSize, rotation);
      BossRoomProtectionProcessor.processPendingMarkersImmediate(level);
      BlockPos finalCorridorPos = corridorPlacementPos.immutable();
      Vec3i finalCorridorSize = corridorSize;
      Rotation finalRotation = rotation;
      level.getServer()
         .tell(
            new TickTask(
               level.getServer().getTickCount() + 2, () -> finalizeWaterloggingAfterPlacement(level, finalCorridorPos, finalCorridorSize, finalRotation)
            )
         );
      int corridorJigsawsRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(
         corridorPlacementPos, corridorPlacementPos.offset(corridorTemplateSize.getX() - 1, corridorTemplateSize.getY() - 1, corridorTemplateSize.getZ() - 1)
      )) {
         BlockState state = level.getBlockState(pos);
         if (state.is(Blocks.JIGSAW)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            corridorJigsawsRemoved++;
         }
      }

      if (corridorJigsawsRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} Jigsaw Blocks from corridor (range-based)", corridorJigsawsRemoved);
      }

      BlockState corridorStairsBottomJigsawState = level.getBlockState(calculatedCorridorJigsawPos);
      if (corridorStairsBottomJigsawState.is(Blocks.JIGSAW)) {
         level.setBlock(calculatedCorridorJigsawPos, Blocks.AIR.defaultBlockState(), 3);
         ChronoDawn.LOGGER.debug("Removed corridor's stairs_bottom side Jigsaw Block at {}", calculatedCorridorJigsawPos);
      } else {
         ChronoDawn.LOGGER
            .warn(
               "Corridor's stairs_bottom side Jigsaw Block not found at {} (found: {})",
               calculatedCorridorJigsawPos,
               corridorStairsBottomJigsawState.getBlock()
            );
      }

      BlockState stairsBottomJigsawState = level.getBlockState(stairsBottomJigsawPos);
      if (stairsBottomJigsawState.is(Blocks.JIGSAW)) {
         level.setBlock(stairsBottomJigsawPos, Blocks.AIR.defaultBlockState(), 3);
         ChronoDawn.LOGGER.debug("Removed stairs_bottom side Jigsaw Block at {}", stairsBottomJigsawPos);
      }

      int stairsJigsawsRemoved = 0;

      for (BlockPos jigsawPos : jigsawsToRemove) {
         BlockState state = level.getBlockState(jigsawPos);
         if (state.is(Blocks.JIGSAW)) {
            level.setBlock(jigsawPos, Blocks.AIR.defaultBlockState(), 3);
            stairsJigsawsRemoved++;
         }
      }

      if (stairsJigsawsRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} Jigsaw Blocks from stairs and stairs_bottom", stairsJigsawsRemoved);
      }
      BlockPos corridorBossRoomJigsawOffset = switch (rotation) {
         case NONE -> new BlockPos(14, 1, 7);
         case CLOCKWISE_90 -> new BlockPos(-7, 1, 14);
         case CLOCKWISE_180 -> new BlockPos(-14, 1, -7);
         case COUNTERCLOCKWISE_90 -> new BlockPos(7, 1, -14);
         default -> throw new MatchException(null, null);
      };
      BlockPos corridorJigsawPos = corridorPlacementPos.offset(corridorBossRoomJigsawOffset);
      ChronoDawn.LOGGER.debug("Corridor boss_room side Jigsaw Block at {} (offset: {})", corridorJigsawPos, corridorBossRoomJigsawOffset);
      BlockState jigsawState = level.getBlockState(corridorJigsawPos);
      if (!jigsawState.is(Blocks.JIGSAW)) {
         ChronoDawn.LOGGER.warn("Expected Jigsaw Block at {}, but found: {}", corridorJigsawPos, jigsawState.getBlock());
      }

      return new MasterClockBossRoomPlacer.CorridorPlacementResult(
         corridorJigsawPos, rotation, stairsPositions, stairsTemplateSize, stairsRotation, protectedAreas
      );
   }

   private static BlockPos findCorridorJigsawBlock(ServerLevel level, BlockPos corridorStart, Vec3i size) {
      int sizeX = size.getX();
      int sizeY = size.getY();
      int sizeZ = size.getZ();

      for (BlockPos pos : BlockPos.betweenClosed(corridorStart, corridorStart.offset(sizeX, sizeY, sizeZ))) {
         BlockState state = level.getBlockState(pos);
         if (state.is(Blocks.JIGSAW)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof JigsawBlockEntity) {
               ChronoDawn.LOGGER.debug("Found corridor Jigsaw Block at {}", pos);
               level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
               ChronoDawn.LOGGER.debug("Removed corridor Jigsaw Block");
               return pos;
            }
         }
      }

      ChronoDawn.LOGGER.warn("No Jigsaw Block found in corridor");
      return null;
   }

   private static int removeAquiferWaterBeforePlacement(
      ServerLevel level, BlockPos placementPos, Vec3i templateSize, Block decorativeWaterBlock, List<BoundingBox> protectedAreas
   ) {
      int waterRemoved = 0;
      int decorativeWaterFound = 0;
      int protectedWaterSkipped = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos, placementPos.offset(templateSize.getX() - 1, templateSize.getY() - 1, templateSize.getZ() - 1))) {
         boolean isProtected = false;

         for (BoundingBox protectedArea : protectedAreas) {
            if (protectedArea.isInside(pos)) {
               isProtected = true;
               break;
            }
         }

         if (isProtected) {
            BlockState state = level.getBlockState(pos);
            if (state.getFluidState().isSource()) {
               protectedWaterSkipped++;
            }
         } else {
            BlockState state = level.getBlockState(pos);
            if (state.is(decorativeWaterBlock)) {
               decorativeWaterFound++;
            } else if (state.getFluidState().isSource() && state.getBlock() == Blocks.WATER) {
               level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
               waterRemoved++;
            }
         }
      }

      return waterRemoved;
   }

   private static void finalizeWaterloggingAfterPlacement(ServerLevel level, BlockPos placementPos, Vec3i templateSize, Rotation rotation) {
      int totalWaterloggedRestored = 0;
      int totalWaterloggedRemoved = 0;
      int totalWaterloggedBeforeFinalize = 0;
      BlockPos minPos;
      BlockPos maxPos;
      switch (rotation) {
         case NONE:
            minPos = placementPos;
            maxPos = placementPos.offset(templateSize.getX() - 1, templateSize.getY() - 1, templateSize.getZ() - 1);
            break;
         case CLOCKWISE_90:
            minPos = placementPos.offset(-(templateSize.getZ() - 1), 0, 0);
            maxPos = placementPos.offset(0, templateSize.getY() - 1, templateSize.getX() - 1);
            break;
         case CLOCKWISE_180:
            minPos = placementPos.offset(-(templateSize.getX() - 1), 0, -(templateSize.getZ() - 1));
            maxPos = placementPos.offset(0, templateSize.getY() - 1, 0);
            break;
         case COUNTERCLOCKWISE_90:
            minPos = placementPos.offset(0, 0, -(templateSize.getX() - 1));
            maxPos = placementPos.offset(templateSize.getZ() - 1, templateSize.getY() - 1, 0);
            break;
         default:
            throw new IllegalStateException("Unexpected rotation: " + rotation);
      }

      ChronoDawn.LOGGER.debug("Finalize bounding box for rotation {}: {} to {}", new Object[]{rotation, minPos, maxPos});
      int totalBlocksChecked = 0;

      for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
         totalBlocksChecked++;
         BlockState state = level.getBlockState(pos);
         BlockPos immutablePos = pos.immutable();
         if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            boolean currentWaterlogged = (Boolean)state.getValue(BlockStateProperties.WATERLOGGED);
            if (currentWaterlogged) {
               totalWaterloggedBeforeFinalize++;
            }

            boolean shouldBeWaterlogged = CopyFluidLevelProcessor.INTENTIONAL_WATERLOGGING.contains(immutablePos);
            if (currentWaterlogged != shouldBeWaterlogged) {
               BlockState newState = (BlockState)state.setValue(BlockStateProperties.WATERLOGGED, shouldBeWaterlogged);
               level.setBlock(pos, newState, 2);
               if (shouldBeWaterlogged) {
                  totalWaterloggedRestored++;
                  CopyFluidLevelProcessor.INTENTIONAL_WATERLOGGING.remove(immutablePos);
               } else {
                  totalWaterloggedRemoved++;
               }
            } else if (shouldBeWaterlogged) {
               CopyFluidLevelProcessor.INTENTIONAL_WATERLOGGING.remove(immutablePos);
            }
         }
      }

      if (totalWaterloggedBeforeFinalize > 0) {
         ChronoDawn.LOGGER.debug("DEBUG: Found {} waterlogged blocks before finalize at {}", totalWaterloggedBeforeFinalize, placementPos);
      }

      if (totalWaterloggedRestored > 0) {
         ChronoDawn.LOGGER.debug("Restored {} intentional waterlogged blocks at {}", totalWaterloggedRestored, placementPos);
      }

      if (totalWaterloggedRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} unintentional waterlogged blocks at {}", totalWaterloggedRemoved, placementPos);
      }

      ChronoDawn.LOGGER
         .debug(
            "Finalize waterlogging summary at {}: checked {} blocks, {} waterlogged before, restored {}, removed {}",
            new Object[]{placementPos, totalBlocksChecked, totalWaterloggedBeforeFinalize, totalWaterloggedRestored, totalWaterloggedRemoved}
         );
   }

   private static void convertDecorativeWaterToVanilla(ServerLevel level, BlockPos placementPos, Vec3i templateSize, Rotation rotation) {
      int totalDecorativeWaterConverted = 0;
      BlockPos minPos;
      BlockPos maxPos;
      switch (rotation) {
         case NONE:
            minPos = placementPos;
            maxPos = placementPos.offset(templateSize.getX() - 1, templateSize.getY() - 1, templateSize.getZ() - 1);
            break;
         case CLOCKWISE_90:
            minPos = placementPos.offset(-(templateSize.getZ() - 1), 0, 0);
            maxPos = placementPos.offset(0, templateSize.getY() - 1, templateSize.getX() - 1);
            break;
         case CLOCKWISE_180:
            minPos = placementPos.offset(-(templateSize.getX() - 1), 0, -(templateSize.getZ() - 1));
            maxPos = placementPos.offset(0, templateSize.getY() - 1, 0);
            break;
         case COUNTERCLOCKWISE_90:
            minPos = placementPos.offset(0, 0, -(templateSize.getX() - 1));
            maxPos = placementPos.offset(templateSize.getZ() - 1, templateSize.getY() - 1, 0);
            break;
         default:
            throw new IllegalStateException("Unexpected rotation: " + rotation);
      }

      ChronoDawn.LOGGER.debug("Converting decorative_water for rotation {}: {} to {}", new Object[]{rotation, minPos, maxPos});

      for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
         BlockState state = level.getBlockState(pos);
         if (state.is((Block)ModBlocks.DECORATIVE_WATER.get())) {
            BlockState newState = Blocks.WATER.defaultBlockState();
            if (state.hasProperty(BlockStateProperties.LEVEL)) {
               int fluidLevel = (Integer)state.getValue(BlockStateProperties.LEVEL);
               newState = (BlockState)newState.setValue(BlockStateProperties.LEVEL, fluidLevel);
            }

            level.setBlock(pos, newState, 2);
            totalDecorativeWaterConverted++;
         }
      }

      if (totalDecorativeWaterConverted > 0) {
         ChronoDawn.LOGGER.debug("Converted {} decorative_water blocks to vanilla water at {}", totalDecorativeWaterConverted, placementPos);
      }
   }

   private static BlockPos findCorridorEndMarker(ServerLevel level, BlockPos structureOrigin) {
      int searchRadius = 100;
      int maxY = 150;
      int minY = -60;
      ChronoDawn.LOGGER.debug("Searching for Corridor end marker (Amethyst Block) around {}", structureOrigin);

      for (int y = maxY; y >= minY; y--) {
         for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
               BlockPos checkPos = new BlockPos(structureOrigin.getX() + x, y, structureOrigin.getZ() + z);
               BlockState state = level.getBlockState(checkPos);
               if (state.is(Blocks.AMETHYST_BLOCK)) {
                  ChronoDawn.LOGGER.debug("Found Corridor end marker at {}", checkPos);
                  return checkPos;
               }
            }
         }
      }

      ChronoDawn.LOGGER.warn("No Corridor end marker found near {}", structureOrigin);
      return null;
   }

   private static boolean placeBossRoom(
      ServerLevel level,
      BlockPos corridorJigsawPos,
      Rotation corridorRotation,
      List<BlockPos> stairsPositions,
      Vec3i stairsTemplateSize,
      Rotation stairsRotation,
      List<BoundingBox> protectedAreas
   ) {
      ChronoDawn.LOGGER.debug("Placing boss_room connected to corridor Jigsaw at {} (corridor rotation: {})", corridorJigsawPos, corridorRotation);
      StructureTemplateManager structureTemplateManager = level.getStructureManager();
      Optional<StructureTemplate> templateOptional = structureTemplateManager.get(BOSS_ROOM_TEMPLATE);
      if (templateOptional.isEmpty()) {
         ChronoDawn.LOGGER.error("Failed to load boss_room template: {}", BOSS_ROOM_TEMPLATE);
         return false;
      }

      StructureTemplate template = templateOptional.get();
      Rotation bossRoomRotation = corridorRotation;

      BlockPos bossRoomJigsawOffset = switch (bossRoomRotation) {
         case NONE -> new BlockPos(0, 1, 17);
         case CLOCKWISE_90 -> new BlockPos(-17, 1, 0);
         case CLOCKWISE_180 -> new BlockPos(0, 1, -17);
         case COUNTERCLOCKWISE_90 -> new BlockPos(17, 1, 0);
         default -> throw new MatchException(null, null);
      };

      BlockPos jigsawOffset = switch (bossRoomRotation) {
         case NONE -> new BlockPos(1, 0, 0);
         case CLOCKWISE_90 -> new BlockPos(0, 0, 1);
         case CLOCKWISE_180 -> new BlockPos(-1, 0, 0);
         case COUNTERCLOCKWISE_90 -> new BlockPos(0, 0, -1);
         default -> throw new MatchException(null, null);
      };
      BlockPos placementPos = corridorJigsawPos.subtract(bossRoomJigsawOffset).offset(jigsawOffset);
      ChronoDawn.LOGGER
         .debug(
            "Boss_room placement position: {} (Jigsaw offset: {}, rotation: {}, overlap prevention: {})",
            new Object[]{placementPos, bossRoomJigsawOffset, bossRoomRotation, jigsawOffset}
         );
      Block decorativeWaterBlock = (Block)BuiltInRegistries.BLOCK.get(CompatResourceLocation.create("chronodawn", "decorative_water"));
      Vec3i templateSize = template.getSize();
      int waterRemoved = removeAquiferWaterBeforePlacement(level, placementPos, templateSize, decorativeWaterBlock, protectedAreas);
      if (waterRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} Aquifer water blocks before placing boss_room", waterRemoved);
      }

      ResourceLocation processorListId = CompatResourceLocation.create("chronodawn", "convert_decorative_water");
      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(bossRoomRotation).setIgnoreEntities(false);

      try {
         Registry<StructureProcessorList> processorListRegistry = level.registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);
         StructureProcessorList processorList = (StructureProcessorList)processorListRegistry.get(processorListId);
         if (processorList != null) {
            for (StructureProcessor processor : processorList.list()) {
               settings.addProcessor(processor);
            }

            ChronoDawn.LOGGER.debug("Applied {} structure processors", processorList.list().size());
         } else {
            ChronoDawn.LOGGER.error("Processor list {} not found", processorListId);
         }
      } catch (Exception e) {
         ChronoDawn.LOGGER.error("Failed to load processor list: {}", e.getMessage(), e);
      }

      template.placeInWorld(level, placementPos, placementPos, settings, level.random, 3);
      finalizeWaterloggingAfterPlacement(level, placementPos, templateSize, bossRoomRotation);
      BossRoomProtectionProcessor.processPendingMarkersImmediate(level);
      BlockPos finalPlacementPos = placementPos.immutable();
      Vec3i finalTemplateSize = templateSize;
      Rotation finalBossRoomRotation = bossRoomRotation;
      level.getServer()
         .tell(
            new TickTask(
               level.getServer().getTickCount() + 2,
               () -> finalizeWaterloggingAfterPlacement(level, finalPlacementPos, finalTemplateSize, finalBossRoomRotation)
            )
         );
      int bossRoomJigsawsRemoved = 0;

      for (BlockPos pos : BlockPos.betweenClosed(placementPos, placementPos.offset(templateSize.getX() - 1, templateSize.getY() - 1, templateSize.getZ() - 1))) {
         BlockState state = level.getBlockState(pos);
         if (state.is(Blocks.JIGSAW)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            bossRoomJigsawsRemoved++;
         }
      }

      if (bossRoomJigsawsRemoved > 0) {
         ChronoDawn.LOGGER.debug("Removed {} Jigsaw Blocks from boss_room (range-based)", bossRoomJigsawsRemoved);
      }

      BlockPos bossRoomCorridorJigsawPos = placementPos.offset(bossRoomJigsawOffset);
      BlockState bossRoomCorridorJigsawState = level.getBlockState(bossRoomCorridorJigsawPos);
      if (bossRoomCorridorJigsawState.is(Blocks.JIGSAW)) {
         level.setBlock(bossRoomCorridorJigsawPos, Blocks.AIR.defaultBlockState(), 3);
         ChronoDawn.LOGGER.debug("Removed boss_room's corridor side Jigsaw Block at {}", bossRoomCorridorJigsawPos);
      } else {
         ChronoDawn.LOGGER
            .warn("Boss_room's corridor side Jigsaw Block not found at {} (found: {})", bossRoomCorridorJigsawPos, bossRoomCorridorJigsawState.getBlock());
      }

      BlockState corridorJigsawState = level.getBlockState(corridorJigsawPos);
      if (corridorJigsawState.is(Blocks.JIGSAW)) {
         level.setBlock(corridorJigsawPos, Blocks.AIR.defaultBlockState(), 3);
         ChronoDawn.LOGGER.debug("Removed corridor's boss_room side Jigsaw Block at {}", corridorJigsawPos);
      } else {
         ChronoDawn.LOGGER.warn("Corridor's boss_room side Jigsaw Block not found at {} (found: {})", corridorJigsawPos, corridorJigsawState.getBlock());
      }

      ChronoDawn.LOGGER.debug("Converting decorative_water to vanilla water for all structures");

      for (BlockPos stairsPos : stairsPositions) {
         convertDecorativeWaterToVanilla(level, stairsPos, stairsTemplateSize, stairsRotation);
      }

      convertDecorativeWaterToVanilla(level, placementPos, templateSize, bossRoomRotation);
      ChronoDawn.LOGGER.debug("Decorative water conversion completed for all structures");
      List<BlockPos> finalStairsPositions = new ArrayList<>(stairsPositions);
      Vec3i finalStairsTemplateSize = stairsTemplateSize;
      int[] checkDelays = new int[]{10, 40, 100, 200};
      Rotation finalStairsRotation = stairsRotation;

      for (int checkDelay : checkDelays) {
         int finalDelay = checkDelay;
         level.getServer()
            .tell(
               new TickTask(
                  level.getServer().getTickCount() + checkDelay,
                  () -> {
                     ChronoDawn.LOGGER.debug("Performing delayed finalize for all stairs ({} ticks after boss_room)", finalDelay);
                     int totalRemovedInFinal = 0;

                     for (BlockPos stairsPos : finalStairsPositions) {
                        BlockPos minPos;
                        BlockPos maxPos;
                        switch (finalStairsRotation) {
                           case NONE:
                              minPos = stairsPos;
                              maxPos = stairsPos.offset(
                                 finalStairsTemplateSize.getX() - 1, finalStairsTemplateSize.getY() - 1, finalStairsTemplateSize.getZ() - 1
                              );
                              break;
                           case CLOCKWISE_90:
                              minPos = stairsPos.offset(-(finalStairsTemplateSize.getZ() - 1), 0, 0);
                              maxPos = stairsPos.offset(0, finalStairsTemplateSize.getY() - 1, finalStairsTemplateSize.getX() - 1);
                              break;
                           case CLOCKWISE_180:
                              minPos = stairsPos.offset(-(finalStairsTemplateSize.getX() - 1), 0, -(finalStairsTemplateSize.getZ() - 1));
                              maxPos = stairsPos.offset(0, finalStairsTemplateSize.getY() - 1, 0);
                              break;
                           case COUNTERCLOCKWISE_90:
                              minPos = stairsPos.offset(0, 0, -(finalStairsTemplateSize.getX() - 1));
                              maxPos = stairsPos.offset(finalStairsTemplateSize.getZ() - 1, finalStairsTemplateSize.getY() - 1, 0);
                              break;
                           default:
                              throw new IllegalStateException("Unexpected rotation: " + finalStairsRotation);
                        }

                        int waterloggedBefore = 0;

                        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
                           BlockState state = level.getBlockState(pos);
                           if (state.hasProperty(BlockStateProperties.WATERLOGGED) && (Boolean)state.getValue(BlockStateProperties.WATERLOGGED)) {
                              waterloggedBefore++;
                           }
                        }

                        if (waterloggedBefore > 0) {
                           ChronoDawn.LOGGER
                              .warn(
                                 "Found {} late waterlogged blocks at {} before delayed finalize (delay={})",
                                 new Object[]{waterloggedBefore, stairsPos, finalDelay}
                              );
                           finalizeWaterloggingAfterPlacement(level, stairsPos, finalStairsTemplateSize, finalStairsRotation);
                           totalRemovedInFinal += waterloggedBefore;
                        }
                     }

                     if (totalRemovedInFinal > 0) {
                        ChronoDawn.LOGGER.debug("Delayed finalize (delay={}) removed {} late waterlogged blocks from stairs", finalDelay, totalRemovedInFinal);
                     } else {
                        ChronoDawn.LOGGER.debug("Delayed finalize (delay={}): no late waterlogging detected", finalDelay);
                     }
                  }
               )
            );
      }

      ChronoDawn.LOGGER.debug("Successfully placed boss_room via Jigsaw connection");
      return true;
   }

   public static void processStructure(ServerLevel level, ChunkPos chunkPos) {
      ResourceLocation dimensionId = level.dimension().location();
      processedStructures.putIfAbsent(dimensionId, ConcurrentHashMap.newKeySet());
      Set<BlockPos> dimensionProcessed = processedStructures.get(dimensionId);
      if (hasMasterClock(level, chunkPos)) {
         BlockPos structureOrigin = getStructureOrigin(level, chunkPos);
         if (structureOrigin != null) {
            if (!dimensionProcessed.contains(structureOrigin)) {
               if (!processingStates.containsKey(structureOrigin)) {
                  ChronoDawn.LOGGER
                     .debug("Found Master Clock structure at chunk {} (origin: {}), initializing multi-tick processing", chunkPos, structureOrigin);
                  MasterClockBossRoomPlacer.StructureProcessingState state = new MasterClockBossRoomPlacer.StructureProcessingState(
                     structureOrigin, chunkPos, dimensionId
                  );
                  processingStates.put(structureOrigin, state);
               }
            }
         }
      }
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
            if (!level.players().isEmpty()) {
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

   private static void progressAllProcessing(ServerLevel level) {
      if (!processingStates.isEmpty()) {
         List<BlockPos> completedStructures = new ArrayList<>();

         for (Entry<BlockPos, MasterClockBossRoomPlacer.StructureProcessingState> entry : processingStates.entrySet()) {
            BlockPos structureOrigin = entry.getKey();
            MasterClockBossRoomPlacer.StructureProcessingState state = entry.getValue();
            if (state.dimensionId.equals(level.dimension().location())) {
               switch (state.phase) {
                  case SEARCHING_SURFACE_MARKER:
                     progressSearchSurfaceMarker(level, state);
                     break;
                  case SEARCHING_JIGSAW:
                     progressSearchJigsaw(level, state);
                     break;
                  case PLACING_CORRIDOR:
                     progressPlacingCorridor(level, state);
                     break;
                  case PLACING_STAIRS:
                     progressPlacingStairs(level, state);
                     break;
                  case PLACING_BOSS_ROOM:
                     progressPlacingBossRoom(level, state);
                     break;
                  case FINALIZING:
                     progressFinalizing(level, state);
                     break;
                  case COMPLETED:
                     completedStructures.add(structureOrigin);
               }
            }
         }

         for (BlockPos structureOrigin : completedStructures) {
            processingStates.remove(structureOrigin);
            Set<BlockPos> dimensionProcessed = processedStructures.get(level.dimension().location());
            if (dimensionProcessed != null) {
               dimensionProcessed.add(structureOrigin);
            }

            ChronoDawn.LOGGER.debug("Completed boss_room placement for Master Clock at {}", structureOrigin);
         }
      }
   }

   private static void progressSearchSurfaceMarker(ServerLevel level, MasterClockBossRoomPlacer.StructureProcessingState state) {
      if (state.structureChunks.isEmpty()) {
         int searchRadius = 3;
         ChunkPos origin = new ChunkPos(state.structureOrigin);

         for (int cx = -searchRadius; cx <= searchRadius; cx++) {
            for (int cz = -searchRadius; cz <= searchRadius; cz++) {
               state.structureChunks.add(new ChunkPos(origin.x + cx, origin.z + cz));
            }
         }

         ChronoDawn.LOGGER.debug("Master Clock at {}: searching {} chunks for surface marker", state.structureOrigin, state.structureChunks.size());
      }

      if (state.currentChunkIndex < state.structureChunks.size()) {
         ChunkPos chunkPos = state.structureChunks.get(state.currentChunkIndex);
         BlockPos chunkMin = chunkPos.getWorldPosition();

         for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
               for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                  BlockPos pos = chunkMin.offset(x, y, z);
                  BlockState blockState = level.getBlockState(pos);
                  if (blockState.is(Blocks.DROPPER)) {
                     state.surfaceMarkerPos = pos;
                     state.surfaceMarkerDirection = (Direction)blockState.getValue(BlockStateProperties.FACING);
                     ChronoDawn.LOGGER.debug("Found surface Dropper marker at {} facing {}", pos, state.surfaceMarkerDirection);
                     state.phase = MasterClockBossRoomPlacer.ProcessingPhase.SEARCHING_JIGSAW;
                     return;
                  }
               }
            }
         }

         state.currentChunkIndex++;
      } else {
         state.searchRetryCount++;
         if (state.searchRetryCount > 3) {
            ChronoDawn.LOGGER.warn("Master Clock at {}: no surface marker found after {} retries, giving up", state.structureOrigin, state.searchRetryCount);
            state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
         } else {
            ChronoDawn.LOGGER.debug("Master Clock at {}: no surface marker found, retry {}/3", state.structureOrigin, state.searchRetryCount);
            state.currentChunkIndex = 0;
         }
      }
   }

   private static void progressSearchJigsaw(ServerLevel level, MasterClockBossRoomPlacer.StructureProcessingState state) {
      state.phase = MasterClockBossRoomPlacer.ProcessingPhase.PLACING_CORRIDOR;
   }

   private static void progressPlacingCorridor(ServerLevel level, MasterClockBossRoomPlacer.StructureProcessingState state) {
      if (state.surfaceMarkerPos == null || state.surfaceMarkerDirection == null) {
         ChronoDawn.LOGGER.error("Cannot place corridor: no surface marker or direction");
         state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
      } else if (state.surfaceMarkerDirection != Direction.UP && state.surfaceMarkerDirection != Direction.DOWN) {
         level.setBlock(state.surfaceMarkerPos, Blocks.AIR.defaultBlockState(), 3);
         ChronoDawn.LOGGER.debug("Removed dropper marker at {}", state.surfaceMarkerPos);
         state.phase = MasterClockBossRoomPlacer.ProcessingPhase.PLACING_STAIRS;
      } else {
         ChronoDawn.LOGGER.error("Dropper must face horizontally, not {}", state.surfaceMarkerDirection);
         state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
      }
   }

   private static void progressPlacingStairs(ServerLevel level, MasterClockBossRoomPlacer.StructureProcessingState state) {
      if (state.surfaceMarkerPos != null && state.surfaceMarkerDirection != null) {
         MasterClockBossRoomPlacer.StairsPlacementResult result = placeStairsDynamically(level, state.surfaceMarkerPos, state.surfaceMarkerDirection, -30);
         if (result == null) {
            ChronoDawn.LOGGER.error("Failed to place stairs");
            state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
         } else {
            state.stairsPositions = result.stairsPositions;
            state.protectedAreas = result.protectedAreas;
            BlockState jigsawState = level.getBlockState(result.stairsBottomJigsawPos);
            if (!jigsawState.is(Blocks.JIGSAW)) {
               ChronoDawn.LOGGER.error("Expected jigsaw at stairs end");
               state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
            } else {
               FrontAndTop jigsawOrientation = (FrontAndTop)jigsawState.getValue(JigsawBlock.ORIENTATION);
               Direction jigsawFacing = jigsawOrientation.front();
               MasterClockBossRoomPlacer.CorridorPlacementResult corridorResult = placeCorridor(
                  level,
                  result.stairsBottomJigsawPos,
                  jigsawFacing,
                  result.jigsawsToRemove,
                  result.stairsPositions,
                  result.stairsTemplateSize,
                  result.stairsRotation,
                  result.protectedAreas
               );
               if (corridorResult == null) {
                  ChronoDawn.LOGGER.error("Failed to place corridor");
                  state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
               } else {
                  state.bossRoomPos = corridorResult.jigsawPos;
                  state.bossRoomRotation = corridorResult.rotation;
                  state.stairsPositions = corridorResult.stairsPositions;
                  state.protectedAreas = corridorResult.protectedAreas;
                  state.stairsTemplateSize = result.stairsTemplateSize;
                  state.stairsRotation = result.stairsRotation;
                  state.phase = MasterClockBossRoomPlacer.ProcessingPhase.PLACING_BOSS_ROOM;
                  ChronoDawn.LOGGER.debug("Stairs and corridor placed, proceeding to boss room");
               }
            }
         }
      } else {
         state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
      }
   }

   private static void progressPlacingBossRoom(ServerLevel level, MasterClockBossRoomPlacer.StructureProcessingState state) {
      if (state.bossRoomPos == null) {
         state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
      } else {
         boolean success = placeBossRoom(
            level, state.bossRoomPos, state.bossRoomRotation, state.stairsPositions, state.stairsTemplateSize, state.stairsRotation, state.protectedAreas
         );
         if (success) {
            ChronoDawn.LOGGER.debug("Boss room placed successfully at {}", state.bossRoomPos);
            state.phase = MasterClockBossRoomPlacer.ProcessingPhase.FINALIZING;
         } else {
            ChronoDawn.LOGGER.error("Failed to place boss room");
            state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
         }
      }
   }

   private static void progressFinalizing(ServerLevel level, MasterClockBossRoomPlacer.StructureProcessingState state) {
      ChronoDawn.LOGGER.debug("Finalizing boss room placement for {}", state.structureOrigin);
      state.phase = MasterClockBossRoomPlacer.ProcessingPhase.COMPLETED;
   }

   public static void triggerBossRoomPlacementAtDoor(ServerLevel level, BlockPos doorPos) {
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         ChronoDawn.LOGGER.debug("Entrance door opened at {}, searching for Master Clock structure", doorPos);
         ChunkPos doorChunkPos = new ChunkPos(doorPos);

         for (int x = -10; x <= 10; x++) {
            for (int z = -10; z <= 10; z++) {
               ChunkPos chunkPos = new ChunkPos(doorChunkPos.x + x, doorChunkPos.z + z);
               if (hasMasterClock(level, chunkPos)) {
                  BlockPos structureOrigin = getStructureOrigin(level, chunkPos);
                  if (structureOrigin != null) {
                     ResourceLocation dimensionId = level.dimension().location();
                     processedStructures.putIfAbsent(dimensionId, ConcurrentHashMap.newKeySet());
                     Set<BlockPos> dimensionProcessed = processedStructures.get(dimensionId);
                     if (dimensionProcessed.contains(structureOrigin)) {
                        ChronoDawn.LOGGER.debug("Master Clock at {} already processed, skipping", structureOrigin);
                        return;
                     }

                     if (processingStates.containsKey(structureOrigin)) {
                        ChronoDawn.LOGGER.debug("Master Clock at {} already being processed, skipping", structureOrigin);
                        return;
                     }

                     ChronoDawn.LOGGER.debug("Found Master Clock structure at {} near entrance door, starting boss room placement", structureOrigin);
                     MasterClockBossRoomPlacer.StructureProcessingState state = new MasterClockBossRoomPlacer.StructureProcessingState(
                        structureOrigin, chunkPos, dimensionId
                     );
                     processingStates.put(structureOrigin, state);
                     return;
                  }
               }
            }
         }

         ChronoDawn.LOGGER.warn("No Master Clock structure found near entrance door at {}", doorPos);
      }
   }

   public static void register() {
      TickEvent.SERVER_POST.register((Server)server -> {
         for (ServerLevel level : server.getAllLevels()) {
            checkAndPlaceRooms(level);
         }
      });
      ChronoDawn.LOGGER.debug("Registered MasterClockBossRoomPlacer");
   }

   private static class CorridorPlacementResult {
      final BlockPos jigsawPos;
      final Rotation rotation;
      final List<BlockPos> stairsPositions;
      final Vec3i stairsTemplateSize;
      final Rotation stairsRotation;
      final List<BoundingBox> protectedAreas;

      CorridorPlacementResult(
         BlockPos jigsawPos,
         Rotation rotation,
         List<BlockPos> stairsPositions,
         Vec3i stairsTemplateSize,
         Rotation stairsRotation,
         List<BoundingBox> protectedAreas
      ) {
         this.jigsawPos = jigsawPos;
         this.rotation = rotation;
         this.stairsPositions = stairsPositions;
         this.stairsTemplateSize = stairsTemplateSize;
         this.stairsRotation = stairsRotation;
         this.protectedAreas = protectedAreas;
      }
   }

   private enum ProcessingPhase {
      SEARCHING_SURFACE_MARKER,
      SEARCHING_JIGSAW,
      PLACING_CORRIDOR,
      PLACING_STAIRS,
      PLACING_BOSS_ROOM,
      FINALIZING,
      COMPLETED;
   }

   private static class StairsPlacementResult {
      final BlockPos stairsBottomJigsawPos;
      final List<BlockPos> jigsawsToRemove;
      final List<BlockPos> stairsPositions;
      final Vec3i stairsTemplateSize;
      final Rotation stairsRotation;
      final List<BoundingBox> protectedAreas;

      StairsPlacementResult(
         BlockPos stairsBottomJigsawPos,
         List<BlockPos> jigsawsToRemove,
         List<BlockPos> stairsPositions,
         Vec3i stairsTemplateSize,
         Rotation stairsRotation,
         List<BoundingBox> protectedAreas
      ) {
         this.stairsBottomJigsawPos = stairsBottomJigsawPos;
         this.jigsawsToRemove = jigsawsToRemove;
         this.stairsPositions = stairsPositions;
         this.stairsTemplateSize = stairsTemplateSize;
         this.stairsRotation = stairsRotation;
         this.protectedAreas = protectedAreas;
      }
   }

   private static class StructureProcessingState {
      final BlockPos structureOrigin;
      final ChunkPos initialChunkPos;
      final ResourceLocation dimensionId;
      MasterClockBossRoomPlacer.ProcessingPhase phase;
      List<ChunkPos> structureChunks;
      int currentChunkIndex;
      BlockPos surfaceMarkerPos;
      Direction surfaceMarkerDirection;
      BlockPos surfaceJigsawPos;
      int searchRetryCount;
      List<BlockPos> stairsPositions;
      int currentStairsIndex;
      List<BoundingBox> protectedAreas;
      Vec3i stairsTemplateSize;
      Rotation stairsRotation;
      StructureTemplate corridorTemplate;
      StructureTemplate stairsTemplate;
      StructureTemplate stairsBottomTemplate;
      StructureTemplate bossRoomTemplate;
      BlockPos bossRoomPos;
      Rotation bossRoomRotation;

      StructureProcessingState(BlockPos origin, ChunkPos chunkPos, ResourceLocation dimId) {
         this.structureOrigin = origin;
         this.initialChunkPos = chunkPos;
         this.dimensionId = dimId;
         this.phase = MasterClockBossRoomPlacer.ProcessingPhase.SEARCHING_SURFACE_MARKER;
         this.structureChunks = new ArrayList<>();
         this.currentChunkIndex = 0;
         this.surfaceMarkerPos = null;
         this.surfaceJigsawPos = null;
         this.searchRetryCount = 0;
         this.stairsPositions = new ArrayList<>();
         this.currentStairsIndex = 0;
         this.protectedAreas = new ArrayList<>();
      }
   }
}
