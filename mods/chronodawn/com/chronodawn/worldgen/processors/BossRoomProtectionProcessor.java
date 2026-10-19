package com.chronodawn.worldgen.processors;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModStructureProcessorTypes;
import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import com.chronodawn.worldgen.protection.PermanentProtectionHandler;
import com.chronodawn.worldgen.spawning.ClockworkColossusSpawner;
import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;

public class BossRoomProtectionProcessor extends StructureProcessor {
   public static final MapCodec<BossRoomProtectionProcessor> CODEC = MapCodec.unit(BossRoomProtectionProcessor::new);
   private static final Map<BlockPos, BossRoomProtectionProcessor.MarkerData> PENDING_MARKERS = new ConcurrentHashMap<>();
   private static final Map<BlockPos, BlockPos> PAIRED_MARKERS = new ConcurrentHashMap<>();
   private static long lastCleanupTime = System.currentTimeMillis();
   private static final long CLEANUP_INTERVAL_MS = 60000L;
   private static final long MARKER_EXPIRY_MS = 300000L;
   private static final ThreadLocal<BossRoomProtectionProcessor.PlacementIdContext> PLACEMENT_ID_CONTEXT = ThreadLocal.withInitial(
      BossRoomProtectionProcessor.PlacementIdContext::new
   );
   private static final long PLACEMENT_ID_REUSE_WINDOW_MS = 1000L;

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)ModStructureProcessorTypes.BOSS_ROOM_PROTECTION.get();
   }

   @Nullable
   public StructureBlockInfo processBlock(
      LevelReader level,
      BlockPos structurePos,
      BlockPos piecePos,
      StructureBlockInfo originalBlockInfo,
      StructureBlockInfo currentBlockInfo,
      StructurePlaceSettings settings
   ) {
      if (!currentBlockInfo.state().is((Block)ModBlocks.BOSS_ROOM_BOUNDARY_MARKER.get())) {
         return currentBlockInfo;
      }

      BlockPos worldPos = currentBlockInfo.pos();
      CompoundTag nbt = currentBlockInfo.nbt();
      if (nbt == null) {
         ChronoDawn.LOGGER.warn("Boss Room Boundary Marker at {} has no NBT data", worldPos);
         return currentBlockInfo;
      }

      String markerType = nbt.getString("MarkerType");
      String replaceWith = nbt.getString("ReplaceWith");
      if (!markerType.isEmpty() && !replaceWith.isEmpty()) {
         boolean isBossRoomMin = "boss_room_min".equals(markerType);
         boolean isBossRoomMax = "boss_room_max".equals(markerType);
         boolean isPermanentMin = "permanent_protection_min".equals(markerType);
         boolean isPermanentMax = "permanent_protection_max".equals(markerType);
         long placementId = PLACEMENT_ID_CONTEXT.get().getOrCreatePlacementId(worldPos);
         BlockPos relativePos = worldPos.subtract(piecePos);
         if (isBossRoomMin || isBossRoomMax) {
            BlockPos immutablePos = worldPos.immutable();
            BossRoomProtectionProcessor.MarkerData existing = PENDING_MARKERS.put(
               immutablePos, new BossRoomProtectionProcessor.MarkerData(worldPos, isBossRoomMin, false, placementId, relativePos)
            );
            if (existing != null) {
               long ageMs = System.currentTimeMillis() - existing.addedTime;
               BlockPos pairedWith = PAIRED_MARKERS.get(immutablePos);
               if (pairedWith != null) {
                  ChronoDawn.LOGGER
                     .error(
                        "OVERWRITE PAIRED MARKER: Existing boss room {} marker at {} (old placementId={}, age={}ms) was ALREADY PAIRED with {} - This will ORPHAN the paired marker!",
                        new Object[]{existing.isMin ? "min" : "max", worldPos, existing.placementId, ageMs, pairedWith}
                     );
                  PAIRED_MARKERS.remove(immutablePos);
                  PAIRED_MARKERS.remove(pairedWith);
               }
            }
         } else if (isPermanentMin || isPermanentMax) {
            BlockPos immutablePos = worldPos.immutable();
            BossRoomProtectionProcessor.MarkerData existing = PENDING_MARKERS.put(
               immutablePos, new BossRoomProtectionProcessor.MarkerData(worldPos, isPermanentMin, true, placementId, relativePos)
            );
            if (existing != null) {
               long ageMs = System.currentTimeMillis() - existing.addedTime;
               BlockPos pairedWith = PAIRED_MARKERS.get(immutablePos);
               if (pairedWith != null) {
                  ChronoDawn.LOGGER
                     .error(
                        "OVERWRITE PAIRED MARKER: Existing permanent protection {} marker at {} (old placementId={}, age={}ms) was ALREADY PAIRED with {} - This will ORPHAN the paired marker!",
                        new Object[]{existing.isMin ? "min" : "max", worldPos, existing.placementId, ageMs, pairedWith}
                     );
                  PAIRED_MARKERS.remove(immutablePos);
                  PAIRED_MARKERS.remove(pairedWith);
               }
            }
         }

         Block replacementBlock = (Block)BuiltInRegistries.BLOCK.get(CompatResourceLocation.parse(replaceWith));
         BlockState replacementState = replacementBlock.defaultBlockState();
         ChronoDawn.LOGGER.debug("Replacing boss room marker at {} with {}", worldPos, replacementState);
         return new StructureBlockInfo(worldPos, replacementState, null);
      } else {
         ChronoDawn.LOGGER
            .warn("Boss Room Boundary Marker at {} has incomplete NBT (MarkerType={}, ReplaceWith={})", new Object[]{worldPos, markerType, replaceWith});
         return currentBlockInfo;
      }
   }

   public static void registerPendingProtections(ServerLevel level) {
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         if (!PENDING_MARKERS.isEmpty()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastCleanupTime > 60000L) {
               cleanupExpiredMarkers(currentTime);
               lastCleanupTime = currentTime;
            }

            List<BossRoomProtectionProcessor.MarkerData> bossRoomMinMarkers = new ArrayList<>();
            List<BossRoomProtectionProcessor.MarkerData> bossRoomMaxMarkers = new ArrayList<>();
            List<BossRoomProtectionProcessor.MarkerData> permanentMinMarkers = new ArrayList<>();
            List<BossRoomProtectionProcessor.MarkerData> permanentMaxMarkers = new ArrayList<>();
            int processedCount = 0;
            int MAX_PROCESS_PER_CALL = 20;

            for (Entry<BlockPos, BossRoomProtectionProcessor.MarkerData> entry : PENDING_MARKERS.entrySet()) {
               if (processedCount >= 20) {
                  break;
               }

               BossRoomProtectionProcessor.MarkerData marker = entry.getValue();
               if (level.hasChunk(marker.pos.getX() >> 4, marker.pos.getZ() >> 4)) {
                  processedCount++;
                  if (marker.isPermanent) {
                     if (marker.isMin) {
                        permanentMinMarkers.add(marker);
                     } else {
                        permanentMaxMarkers.add(marker);
                     }
                  } else if (marker.isMin) {
                     bossRoomMinMarkers.add(marker);
                  } else {
                     bossRoomMaxMarkers.add(marker);
                  }
               }
            }

            List<BlockPos> processedMarkers = new ArrayList<>();
            Map<Long, List<BossRoomProtectionProcessor.MarkerData>> bossRoomMinsByPlacement = new HashMap<>();
            Map<Long, List<BossRoomProtectionProcessor.MarkerData>> bossRoomMaxsByPlacement = new HashMap<>();

            for (BossRoomProtectionProcessor.MarkerData min : bossRoomMinMarkers) {
               bossRoomMinsByPlacement.computeIfAbsent(min.placementId, k -> new ArrayList<>()).add(min);
            }

            for (BossRoomProtectionProcessor.MarkerData max : bossRoomMaxMarkers) {
               bossRoomMaxsByPlacement.computeIfAbsent(max.placementId, k -> new ArrayList<>()).add(max);
            }

            for (Long placementId : bossRoomMinsByPlacement.keySet()) {
               List<BossRoomProtectionProcessor.MarkerData> mins = bossRoomMinsByPlacement.get(placementId);
               List<BossRoomProtectionProcessor.MarkerData> maxs = bossRoomMaxsByPlacement.get(placementId);
               if (maxs == null) {
                  ChronoDawn.LOGGER.warn("Found {} boss room min markers with placementId={} but no max markers", mins.size(), placementId);
               } else {
                  mins = removeDuplicateMarkers(mins);
                  maxs = removeDuplicateMarkers(maxs);
                  mins.sort(Comparator.<BossRoomProtectionProcessor.MarkerData>comparingInt(m -> m.structureRelativePos.getY()).reversed());
                  maxs.sort(Comparator.<BossRoomProtectionProcessor.MarkerData>comparingInt(m -> m.structureRelativePos.getY()).reversed());
                  int pairCount = Math.min(mins.size(), maxs.size());

                  for (int i = 0; i < pairCount; i++) {
                     BossRoomProtectionProcessor.MarkerData minMarker = mins.get(i);
                     BossRoomProtectionProcessor.MarkerData maxMarker = maxs.get(i);
                     BlockPos minPos = minMarker.pos;
                     BlockPos maxPos = maxMarker.pos;
                     BoundingBox bossRoomArea = new BoundingBox(
                        Math.min(minPos.getX(), maxPos.getX()),
                        Math.min(minPos.getY(), maxPos.getY()),
                        Math.min(minPos.getZ(), maxPos.getZ()),
                        Math.max(minPos.getX(), maxPos.getX()),
                        Math.max(minPos.getY(), maxPos.getY()),
                        Math.max(minPos.getZ(), maxPos.getZ())
                     );
                     BlockProtectionHandler.registerProtectedArea(level, bossRoomArea, minPos);
                     int width = bossRoomArea.maxX() - bossRoomArea.minX();
                     int depth = bossRoomArea.maxZ() - bossRoomArea.minZ();
                     int height = bossRoomArea.maxY() - bossRoomArea.minY();
                     if (width >= 20 && width <= 40 && depth >= 20 && depth <= 40 && height >= 10 && height <= 25) {
                        ClockworkColossusSpawner.registerEngineRoom(level, bossRoomArea);
                     }

                     double distance = Math.sqrt(minMarker.pos.distSqr(maxMarker.pos));
                     ChronoDawn.LOGGER
                        .debug(
                           "Registered boss room protection in dimension {}: min={} (placementId={}), max={} (placementId={}), distance={:.1f} blocks, bounds={}",
                           new Object[]{level.dimension().location(), minPos, minMarker.placementId, maxPos, maxMarker.placementId, distance, bossRoomArea}
                        );
                     PAIRED_MARKERS.put(minPos, maxPos);
                     PAIRED_MARKERS.put(maxPos, minPos);
                     processedMarkers.add(minPos);
                     processedMarkers.add(maxPos);
                  }

                  if (mins.size() > maxs.size()) {
                     for (int i = pairCount; i < mins.size(); i++) {
                        ChronoDawn.LOGGER.warn("Found boss room min marker at {} (placementId={}) but no matching max marker", mins.get(i).pos, placementId);
                     }
                  } else if (maxs.size() > mins.size()) {
                     for (int i = pairCount; i < maxs.size(); i++) {
                        ChronoDawn.LOGGER.warn("Found boss room max marker at {} (placementId={}) but no matching min marker", maxs.get(i).pos, placementId);
                     }
                  }
               }
            }

            Map<Long, List<BossRoomProtectionProcessor.MarkerData>> minMarkersByPlacement = new HashMap<>();
            Map<Long, List<BossRoomProtectionProcessor.MarkerData>> maxMarkersByPlacement = new HashMap<>();

            for (BossRoomProtectionProcessor.MarkerData min : permanentMinMarkers) {
               minMarkersByPlacement.computeIfAbsent(min.placementId, k -> new ArrayList<>()).add(min);
            }

            for (BossRoomProtectionProcessor.MarkerData max : permanentMaxMarkers) {
               maxMarkersByPlacement.computeIfAbsent(max.placementId, k -> new ArrayList<>()).add(max);
            }

            for (Long placementId : minMarkersByPlacement.keySet()) {
               List<BossRoomProtectionProcessor.MarkerData> mins = minMarkersByPlacement.get(placementId);
               List<BossRoomProtectionProcessor.MarkerData> maxs = maxMarkersByPlacement.get(placementId);
               if (maxs == null) {
                  ChronoDawn.LOGGER.warn("Found {} min markers with placementId={} but no max markers", mins.size(), placementId);
               } else {
                  mins = removeDuplicateMarkers(mins);
                  maxs = removeDuplicateMarkers(maxs);
                  mins.sort(Comparator.<BossRoomProtectionProcessor.MarkerData>comparingInt(m -> m.structureRelativePos.getY()).reversed());
                  maxs.sort(Comparator.<BossRoomProtectionProcessor.MarkerData>comparingInt(m -> m.structureRelativePos.getY()).reversed());
                  int pairCount = Math.min(mins.size(), maxs.size());

                  for (int i = 0; i < pairCount; i++) {
                     BossRoomProtectionProcessor.MarkerData minMarker = mins.get(i);
                     BossRoomProtectionProcessor.MarkerData maxMarker = maxs.get(i);
                     BlockPos minPos = minMarker.pos;
                     BlockPos maxPos = maxMarker.pos;
                     BoundingBox protectedArea = new BoundingBox(
                        Math.min(minPos.getX(), maxPos.getX()),
                        Math.min(minPos.getY(), maxPos.getY()),
                        Math.min(minPos.getZ(), maxPos.getZ()),
                        Math.max(minPos.getX(), maxPos.getX()),
                        Math.max(minPos.getY(), maxPos.getY()),
                        Math.max(minPos.getZ(), maxPos.getZ())
                     );
                     PermanentProtectionHandler.registerProtectedArea(level, protectedArea, minPos);
                     double distance = Math.sqrt(minMarker.pos.distSqr(maxMarker.pos));
                     ChronoDawn.LOGGER
                        .debug(
                           "Registered permanent protection in dimension {}: min={} (placementId={}), max={} (placementId={}), distance={:.1f} blocks, bounds={}",
                           new Object[]{level.dimension().location(), minPos, minMarker.placementId, maxPos, maxMarker.placementId, distance, protectedArea}
                        );
                     PAIRED_MARKERS.put(minPos, maxPos);
                     PAIRED_MARKERS.put(maxPos, minPos);
                     processedMarkers.add(minPos);
                     processedMarkers.add(maxPos);
                  }

                  if (mins.size() > maxs.size()) {
                     for (int i = pairCount; i < mins.size(); i++) {
                        ChronoDawn.LOGGER
                           .warn("Found permanent protection min marker at {} (placementId={}) but no matching max marker", mins.get(i).pos, placementId);
                     }
                  } else if (maxs.size() > mins.size()) {
                     for (int i = pairCount; i < maxs.size(); i++) {
                        ChronoDawn.LOGGER
                           .warn("Found permanent protection max marker at {} (placementId={}) but no matching min marker", maxs.get(i).pos, placementId);
                     }
                  }
               }
            }

            processedMarkers.forEach(pos -> {
               PENDING_MARKERS.remove(pos);
               PAIRED_MARKERS.remove(pos);
            });
            if (!PENDING_MARKERS.isEmpty() && (!bossRoomMinMarkers.isEmpty() || !permanentMinMarkers.isEmpty())) {
               ChronoDawn.LOGGER.warn("Still have {} unpaired markers after registration", PENDING_MARKERS.size());
            }
         }
      }
   }

   private static List<BossRoomProtectionProcessor.MarkerData> removeDuplicateMarkers(List<BossRoomProtectionProcessor.MarkerData> markers) {
      Map<BlockPos, BossRoomProtectionProcessor.MarkerData> uniqueMarkers = new HashMap<>();

      for (BossRoomProtectionProcessor.MarkerData marker : markers) {
         BossRoomProtectionProcessor.MarkerData existing = uniqueMarkers.get(marker.pos);
         if (existing == null || marker.addedTime > existing.addedTime) {
            uniqueMarkers.put(marker.pos, marker);
            if (existing != null) {
               ChronoDawn.LOGGER.debug("Removed duplicate marker at {} (kept newer one, time diff={}ms)", marker.pos, marker.addedTime - existing.addedTime);
            }
         }
      }

      return new ArrayList<>(uniqueMarkers.values());
   }

   private static void cleanupExpiredMarkers(long currentTime) {
      int removedCount = 0;
      Iterator<Entry<BlockPos, BossRoomProtectionProcessor.MarkerData>> iterator = PENDING_MARKERS.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<BlockPos, BossRoomProtectionProcessor.MarkerData> entry = iterator.next();
         BossRoomProtectionProcessor.MarkerData marker = entry.getValue();
         long age = currentTime - marker.addedTime;
         if (age > 300000L) {
            ChronoDawn.LOGGER
               .debug(
                  "Removing expired marker at {} (placementId={}, age={}s, type={})",
                  new Object[]{marker.pos, marker.placementId, age / 1000L, marker.isPermanent ? "permanent" : "boss_room"}
               );
            iterator.remove();
            PAIRED_MARKERS.remove(marker.pos);
            removedCount++;
         }
      }

      if (removedCount > 0) {
         ChronoDawn.LOGGER.debug("Cleaned up {} expired boss room markers (older than {} seconds)", removedCount, 300L);
      }
   }

   public static void processPendingMarkersImmediate(ServerLevel level) {
      if (!PENDING_MARKERS.isEmpty()) {
         List<BossRoomProtectionProcessor.MarkerData> bossRoomMinMarkers = new ArrayList<>();
         List<BossRoomProtectionProcessor.MarkerData> bossRoomMaxMarkers = new ArrayList<>();
         List<BossRoomProtectionProcessor.MarkerData> permanentMinMarkers = new ArrayList<>();
         List<BossRoomProtectionProcessor.MarkerData> permanentMaxMarkers = new ArrayList<>();

         for (BossRoomProtectionProcessor.MarkerData marker : PENDING_MARKERS.values()) {
            if (marker.isPermanent) {
               if (marker.isMin) {
                  permanentMinMarkers.add(marker);
               } else {
                  permanentMaxMarkers.add(marker);
               }
            } else if (marker.isMin) {
               bossRoomMinMarkers.add(marker);
            } else {
               bossRoomMaxMarkers.add(marker);
            }
         }

         int bossRoomPairCount = Math.min(bossRoomMinMarkers.size(), bossRoomMaxMarkers.size());

         for (int i = 0; i < bossRoomPairCount; i++) {
            BossRoomProtectionProcessor.MarkerData minMarker = bossRoomMinMarkers.get(i);
            BossRoomProtectionProcessor.MarkerData maxMarker = bossRoomMaxMarkers.get(i);
            BlockPos minPos = minMarker.pos;
            BlockPos maxPos = maxMarker.pos;
            BoundingBox bossRoomArea = new BoundingBox(
               Math.min(minPos.getX(), maxPos.getX()),
               Math.min(minPos.getY(), maxPos.getY()),
               Math.min(minPos.getZ(), maxPos.getZ()),
               Math.max(minPos.getX(), maxPos.getX()),
               Math.max(minPos.getY(), maxPos.getY()),
               Math.max(minPos.getZ(), maxPos.getZ())
            );
            BlockProtectionHandler.registerProtectedArea(level, bossRoomArea, minPos);
            int width = bossRoomArea.maxX() - bossRoomArea.minX();
            int depth = bossRoomArea.maxZ() - bossRoomArea.minZ();
            int height = bossRoomArea.maxY() - bossRoomArea.minY();
            if (width >= 20 && width <= 40 && depth >= 20 && depth <= 40 && height >= 10 && height <= 25) {
               ClockworkColossusSpawner.registerEngineRoom(level, bossRoomArea);
            }

            PAIRED_MARKERS.put(minPos, maxPos);
            PAIRED_MARKERS.put(maxPos, minPos);
         }

         if (bossRoomMinMarkers.size() != bossRoomMaxMarkers.size()) {
            ChronoDawn.LOGGER.warn("[IMMEDIATE] Boss room marker count mismatch: {} min, {} max", bossRoomMinMarkers.size(), bossRoomMaxMarkers.size());
         }

         int permanentPairCount = Math.min(permanentMinMarkers.size(), permanentMaxMarkers.size());

         for (int i = 0; i < permanentPairCount; i++) {
            BossRoomProtectionProcessor.MarkerData minMarker = permanentMinMarkers.get(i);
            BossRoomProtectionProcessor.MarkerData maxMarker = permanentMaxMarkers.get(i);
            BlockPos minPos = minMarker.pos;
            BlockPos maxPos = maxMarker.pos;
            BoundingBox protectedArea = new BoundingBox(
               Math.min(minPos.getX(), maxPos.getX()),
               Math.min(minPos.getY(), maxPos.getY()),
               Math.min(minPos.getZ(), maxPos.getZ()),
               Math.max(minPos.getX(), maxPos.getX()),
               Math.max(minPos.getY(), maxPos.getY()),
               Math.max(minPos.getZ(), maxPos.getZ())
            );
            PermanentProtectionHandler.registerProtectedArea(level, protectedArea, minPos);
            PAIRED_MARKERS.put(minPos, maxPos);
            PAIRED_MARKERS.put(maxPos, minPos);
         }

         if (permanentMinMarkers.size() != permanentMaxMarkers.size()) {
            ChronoDawn.LOGGER.warn("[IMMEDIATE] Permanent marker count mismatch: {} min, {} max", permanentMinMarkers.size(), permanentMaxMarkers.size());
         }

         PENDING_MARKERS.clear();
         ChronoDawn.LOGGER
            .debug("[IMMEDIATE] Processed {} boss room pairs, {} permanent pairs, cleared all pending markers", bossRoomPairCount, permanentPairCount);
      }
   }

   private static class MarkerData {
      final BlockPos pos;
      final boolean isMin;
      final boolean isPermanent;
      final long addedTime;
      final long placementId;
      final BlockPos structureRelativePos;

      MarkerData(BlockPos pos, boolean isMin, boolean isPermanent, long placementId, BlockPos structureRelativePos) {
         this.pos = pos.immutable();
         this.isMin = isMin;
         this.isPermanent = isPermanent;
         this.addedTime = System.currentTimeMillis();
         this.placementId = placementId;
         this.structureRelativePos = structureRelativePos;
      }
   }

   private static class PlacementIdContext {
      long lastPlacementId;
      long lastUpdateTime;
      BlockPos lastMarkerPos;

      long getOrCreatePlacementId(BlockPos currentMarkerPos) {
         long currentTime = System.currentTimeMillis();
         boolean isSameStructure = false;
         if (this.lastMarkerPos != null && currentTime - this.lastUpdateTime <= 1000L) {
            double distance = Math.sqrt(this.lastMarkerPos.distSqr(currentMarkerPos));
            isSameStructure = distance < 100.0;
         }

         if (isSameStructure) {
            return this.lastPlacementId;
         }

         long positionHash = currentMarkerPos.hashCode() & 4294967295L;
         this.lastPlacementId = currentTime << 32 | System.nanoTime() & 4294901760L | positionHash & 65535L;
         this.lastUpdateTime = currentTime;
         this.lastMarkerPos = currentMarkerPos.immutable();
         return this.lastPlacementId;
      }
   }
}
