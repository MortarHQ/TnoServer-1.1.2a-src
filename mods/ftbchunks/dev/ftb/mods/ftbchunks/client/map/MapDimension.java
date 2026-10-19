package dev.ftb.mods.ftbchunks.client.map;

import com.google.common.collect.ImmutableList;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.event.WaypointManagerEvent;
import dev.ftb.mods.ftbchunks.client.ClientTaskQueue;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftblibrary.math.XZ;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.DeflaterOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class MapDimension implements MapTask, Comparable<MapDimension> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static MapDimension currentDimension;
   private final MapManager manager;
   public final ResourceKey<Level> dimension;
   private final String safeDimensionId;
   public final Path directory;
   private Map<XZ, MapRegion> regions;
   private WaypointManagerImpl waypointManager;
   private boolean needsSave;
   private Long2IntMap loadedChunkView;

   MapDimension(MapManager manager, ResourceKey<Level> dimension, Path directory) {
      this.manager = manager;
      this.dimension = dimension;
      this.safeDimensionId = this.dimension.location().toString().replace(':', '_');
      this.directory = directory.resolve(this.safeDimensionId);
      this.needsSave = false;
      this.loadedChunkView = new Long2IntOpenHashMap();
   }

   public MapManager getManager() {
      return this.manager;
   }

   public static void clearCurrentDimension() {
      currentDimension = null;
   }

   public static Optional<MapDimension> getCurrent() {
      if (currentDimension == null) {
         if (MapManager.getInstance().isEmpty()) {
            LOGGER.warn("Attempted to access MapManager before it was setup!");
            return Optional.empty();
         }

         ClientLevel level = Minecraft.getInstance().level;
         if (level == null) {
            return Optional.empty();
         }

         currentDimension = MapManager.getInstance().map(m -> m.getDimension(level.dimension())).orElse(null);
         if (currentDimension != null) {
            ((WaypointManagerEvent)WaypointManagerEvent.AVAILABLE.invoker()).onAvailable(currentDimension.getWaypointManager());
         }
      }

      return Optional.ofNullable(currentDimension);
   }

   @Override
   public String toString() {
      return this.safeDimensionId;
   }

   public Collection<MapRegion> getLoadedRegions() {
      return this.regions == null ? Collections.emptyList() : this.regions.values();
   }

   public int getLoadedView(MapRegion region, int cx, int cz) {
      return this.loadedChunkView.get(ChunkPos.asLong((region.pos.x() << 5) + cx, (region.pos.z() << 5) + cz));
   }

   public Map<XZ, MapRegion> getRegions() {
      synchronized (this.manager.lock) {
         if (this.regions == null) {
            this.regions = new HashMap<>();
            if (Files.notExists(this.directory)) {
               try {
                  Files.createDirectories(this.directory);
               } catch (Exception ex) {
                  throw new RuntimeException(ex);
               }
            }

            if (!MapIOUtils.read(this.directory.resolve("dimension.regions"), stream -> {
               stream.readByte();
               int version = stream.readByte();
               int s = stream.readShort();

               for (int i = 0; i < s; i++) {
                  int x = stream.readByte();
                  int z = stream.readByte();
                  MapRegion c = new MapRegion(this, XZ.of(x, z));
                  this.regions.put(c.pos, c);
               }
            })) {
               this.needsSave = true;
            }
         }

         return this.regions;
      }
   }

   public int[] getColors(int x, int z, ColorsFromRegion colors) {
      MapRegion region = this.getRegions().get(XZ.of(x, z));
      if (region == null) {
         return null;
      }

      MapRegionData data = region.getDataBlocking();
      return colors.getColors(data);
   }

   public MapRegion getRegion(XZ pos) {
      synchronized (this.manager.lock) {
         Map<XZ, MapRegion> map = this.getRegions();
         MapRegion region = map.get(pos);
         if (region == null) {
            region = new MapRegion(this, pos);
            region.created();
            map.put(pos, region);
         }

         return region;
      }
   }

   public WaypointManagerImpl getWaypointManager() {
      if (this.waypointManager == null) {
         this.waypointManager = WaypointManagerImpl.fromJson(this);
         FTBChunksAPI.clientApi().requestMinimapIconRefresh();
      }

      return this.waypointManager;
   }

   public void release() {
      for (MapRegion region : this.getLoadedRegions()) {
         region.release(true);
      }

      this.regions = null;
      this.waypointManager = null;
   }

   @Override
   public void runMapTask() throws Exception {
      List<WaypointImpl> waypoints = ImmutableList.copyOf(this.getWaypointManager());
      List<MapRegion> regionList = ImmutableList.copyOf(this.getRegions().values());
      if (!waypoints.isEmpty() || !regionList.isEmpty()) {
         FTBChunksClient.MAP_EXECUTOR.execute(() -> {
            try {
               this.writeData(waypoints, regionList);
            } catch (Exception ex) {
               FTBChunks.LOGGER.error("Failed to write map dimension " + this + ":");
               ex.printStackTrace();
            }
         });
      }
   }

   private void writeData(List<WaypointImpl> waypoints, List<MapRegion> regionList) throws IOException {
      WaypointManagerImpl.writeJson(this, waypoints);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try (DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(new DeflaterOutputStream(baos)))) {
         stream.writeByte(0);
         stream.writeByte(1);
         stream.writeShort(regionList.size());

         for (MapRegion region : regionList) {
            stream.writeByte(region.pos.x());
            stream.writeByte(region.pos.z());
         }
      }

      Files.write(this.directory.resolve("dimension.regions"), baos.toByteArray());
   }

   public void sync() {
      long now = System.currentTimeMillis();
      this.getRegions()
         .values()
         .stream()
         .sorted(Comparator.comparingDouble(MapRegion::distToPlayer))
         .forEach(region -> ClientTaskQueue.queue(new SyncTXTask(region, now)));
   }

   public void releaseStaleRegionData(long now, long releaseIntervalMillis) {
      if (this.regions != null) {
         this.regions.values().forEach(region -> region.releaseIfStale(now, releaseIntervalMillis));
      }
   }

   public void saveIfChanged() {
      if (this.needsSave) {
         ClientTaskQueue.queue(this);
         this.needsSave = false;
      }
   }

   public void markDirty() {
      this.needsSave = true;
   }

   public void updateLoadedChunkView(Long2IntMap chunks) {
      this.loadedChunkView = chunks;
   }

   public int compareTo(@NotNull MapDimension o) {
      return this.dimension.location().toString().compareTo(o.dimension.location().toString());
   }
}
