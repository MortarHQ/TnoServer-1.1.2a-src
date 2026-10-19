package dev.ftb.mods.ftbchunks.client.map;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbchunks.ColorMapLoader;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.client.ClientTaskQueue;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.map.color.BlockColor;
import dev.ftb.mods.ftbchunks.client.map.color.BlockColors;
import dev.ftb.mods.ftbchunks.core.BiomeFTBC;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.Team;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class MapManager implements MapTask {
   public static final long MEMORY_PER_REGION = 4194304L;
   private static MapManager instance;
   public final Object lock = new Object();
   private boolean invalid;
   private final UUID serverId;
   private final Path directory;
   private final Map<ResourceKey<Level>, MapDimension> dimensions;
   private boolean needsSave;
   private MapDimension pendingRegionPurge = null;
   private final Int2ObjectOpenHashMap<ResourceLocation> blockColorIndexMap;
   private final Object2IntOpenHashMap<ResourceLocation> blockColorIndexMapReverse;
   private final Int2ObjectOpenHashMap<ResourceKey<Biome>> biomeColorIndexMap;
   private final Int2ObjectOpenHashMap<BlockColor> blockIdToColCache;
   private final List<BiomeFTBC> biomesToRelease;
   private final PendingUpdateEvents pendingUpdateEvents;

   private MapManager(UUID serverId, Path directory) {
      this.serverId = serverId;
      this.directory = directory;
      this.invalid = false;
      this.dimensions = new LinkedHashMap<>();
      this.needsSave = false;
      this.blockColorIndexMap = new Int2ObjectOpenHashMap();
      this.blockColorIndexMap.defaultReturnValue(ResourceLocation.fromNamespaceAndPath("minecraft", "air"));
      this.blockColorIndexMapReverse = new Object2IntOpenHashMap();
      this.blockColorIndexMapReverse.defaultReturnValue(0);
      this.biomeColorIndexMap = new Int2ObjectOpenHashMap();
      this.biomeColorIndexMap.defaultReturnValue(Biomes.PLAINS);
      this.blockIdToColCache = new Int2ObjectOpenHashMap();
      this.biomesToRelease = new ArrayList<>();
      this.pendingUpdateEvents = new PendingUpdateEvents();

      try {
         Path dimFile = this.directory.resolve("dimensions.txt");
         if (Files.exists(dimFile)) {
            for (String s : Files.readAllLines(dimFile)) {
               s = s.trim();
               if (s.length() >= 3) {
                  ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(s));
                  this.dimensions.put(key, new MapDimension(this, key, directory));
               }
            }
         } else {
            this.needsSave = true;
         }

         Path blockFile = this.directory.resolve("block_map.txt");
         if (Files.exists(blockFile)) {
            for (String s : Files.readAllLines(blockFile)) {
               s = s.trim();
               if (!s.isEmpty()) {
                  String[] s1 = s.split(" ", 2);
                  int i = Integer.decode(s1[0]);
                  ResourceLocation loc = ResourceLocation.tryParse(s1[1]);
                  this.blockColorIndexMap.put(i, loc);
                  this.blockColorIndexMapReverse.put(loc, i);
               }
            }
         } else {
            this.needsSave = true;
         }

         Path biomeFile = this.directory.resolve("biome_map.txt");
         if (Files.exists(biomeFile)) {
            for (String s : Files.readAllLines(biomeFile)) {
               s = s.trim();
               if (!s.isEmpty()) {
                  String[] s1 = s.split(" ", 2);
                  int i = Integer.decode(s1[0]);
                  ResourceLocation loc = ResourceLocation.tryParse(s1[1]);
                  ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, loc);
                  this.biomeColorIndexMap.put(i, key);
               }
            }
         } else {
            this.needsSave = true;
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public static Optional<MapManager> getInstance() {
      return Optional.ofNullable(instance);
   }

   public static void startUp(UUID serverId) {
      shutdown();
      Path dir = Platform.getGameFolder().resolve("local/ftbchunks/data/" + serverId);
      if (Files.notExists(dir)) {
         try {
            Files.createDirectories(dir);
         } catch (Exception ex) {
            throw new RuntimeException(ex);
         }
      }

      instance = new MapManager(serverId, dir);
   }

   public static void shutdown() {
      if (instance != null) {
         instance.saveAllRegions();
         instance.release();
         ClientTaskQueue.flushTasks();
         MapDimension.clearCurrentDimension();
         instance = null;
      }
   }

   public UUID getServerId() {
      return this.serverId;
   }

   public void saveAllRegions() {
      if (instance != null) {
         for (MapDimension dimension : instance.getDimensions().values()) {
            dimension.getLoadedRegions().forEach(MapRegion::saveIfChanged);
            dimension.saveIfChanged();
         }

         if (instance.needsSave) {
            ClientTaskQueue.queue(instance);
            instance.needsSave = false;
         }
      }
   }

   public Map<ResourceKey<Level>, MapDimension> getDimensions() {
      synchronized (this.lock) {
         return this.dimensions;
      }
   }

   @NotNull
   public synchronized MapDimension getDimension(ResourceKey<Level> dim) {
      return this.getDimensions().computeIfAbsent(dim, dimKey -> {
         this.needsSave = true;
         return new MapDimension(this, (ResourceKey<Level>)dimKey, this.directory);
      });
   }

   public boolean isInvalid() {
      return this.invalid;
   }

   public void release() {
      for (MapDimension dimension : this.getDimensions().values()) {
         dimension.release();
      }

      for (BiomeFTBC b : this.biomesToRelease) {
         b.setFTBCBiomeColorIndex(-1);
      }

      this.biomesToRelease.clear();
      this.blockIdToColCache.clear();
      this.invalid = true;
   }

   public void updateAllRegions(boolean save) {
      for (MapDimension dimension : this.getDimensions().values()) {
         for (MapRegion region : dimension.getRegions().values()) {
            region.update(save);
         }
      }

      FTBChunksClient.INSTANCE.scheduleMinimapUpdate();
   }

   @Override
   public void runMapTask() throws Exception {
      List<String> dimensionsList = this.dimensions.keySet().stream().map(key -> key.location().toString()).collect(Collectors.toList());
      List<String> blockColorIndexMapList = this.blockColorIndexMap
         .int2ObjectEntrySet()
         .stream()
         .sorted(java.util.Map.Entry.comparingByValue())
         .map(key -> String.format("#%06X %s", key.getIntKey(), key.getValue()))
         .collect(Collectors.toList());
      List<String> biomeColorIndexMapList = this.biomeColorIndexMap
         .int2ObjectEntrySet()
         .stream()
         .sorted(Comparator.comparing(o -> ((ResourceKey)o.getValue()).location()))
         .map(key -> String.format("#%03X %s", key.getIntKey(), ((ResourceKey)key.getValue()).location()))
         .collect(Collectors.toList());
      FTBChunksClient.MAP_EXECUTOR.execute(() -> {
         try {
            Files.write(this.directory.resolve("dimensions.txt"), dimensionsList);
            Files.write(this.directory.resolve("block_map.txt"), blockColorIndexMapList);
            Files.write(this.directory.resolve("biome_map.txt"), biomeColorIndexMapList);
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      });
   }

   public int getBlockColorIndex(ResourceLocation id) {
      int i = this.blockColorIndexMapReverse.getInt(id);
      if (i == 0) {
         Random random = new Random(id.getNamespace().hashCode() & 4294967295L | (id.getPath().hashCode() & 4294967295L) << 32);
         i = id.hashCode() & 16777215;

         while (i == 0 || this.blockColorIndexMap.containsKey(i)) {
            i = random.nextInt() & 16777215;
         }

         this.blockColorIndexMap.put(i, id);
         this.blockColorIndexMapReverse.put(id, i);
         this.needsSave = true;
      }

      return i;
   }

   public int getBiomeColorIndex(Registry<Biome> biomes, Biome biome, Object b0) {
      BiomeFTBC b = b0 instanceof BiomeFTBC ? (BiomeFTBC)b0 : null;
      if (b == null) {
         return 0;
      }

      int i = b.getFTBCBiomeColorIndex();
      if (i == -1) {
         ResourceKey<Biome> key = (ResourceKey<Biome>)biomes.getResourceKey(biome).orElse(null);
         if (key == null) {
            b.setFTBCBiomeColorIndex(0);
            return 0;
         }

         ObjectIterator random = this.biomeColorIndexMap.int2ObjectEntrySet().iterator();

         while (random.hasNext()) {
            Entry<ResourceKey<Biome>> entry = (Entry<ResourceKey<Biome>>)random.next();
            if (entry.getValue() == key) {
               i = entry.getIntKey();
               b.setFTBCBiomeColorIndex(i);
               return i;
            }
         }

         Random randomx = new Random(key.location().getNamespace().hashCode() & 4294967295L | (key.location().getPath().hashCode() & 4294967295L) << 32);
         i = key.location().hashCode() & 2047;

         while (i == 0 || this.biomeColorIndexMap.containsKey(i)) {
            i = randomx.nextInt() & 2047;
         }

         this.biomeColorIndexMap.put(i, key);
         b.setFTBCBiomeColorIndex(i);
         this.needsSave = true;
         this.biomesToRelease.add(b);
      }

      return i;
   }

   public Block getBlock(int id) {
      ResourceLocation rl = (ResourceLocation)this.blockColorIndexMap.get(id & 16777215);
      Block block = rl == null ? null : (Block)FTBChunks.BLOCK_REGISTRY.get(rl);
      return block == null ? Blocks.AIR : block;
   }

   public BlockColor getBlockColor(int id) {
      try {
         return (BlockColor)this.blockIdToColCache
            .computeIfAbsent(id & 16777215, i -> ColorMapLoader.getBlockColor((ResourceLocation)this.blockColorIndexMap.get(i)));
      } catch (Exception ex) {
         ex.printStackTrace();
         return BlockColors.ERROR;
      }
   }

   public ResourceKey<Biome> getBiomeKey(int id) {
      return (ResourceKey<Biome>)this.biomeColorIndexMap.get(id & 2047);
   }

   public void releaseStaleRegionData(long releaseIntervalMillis) {
      long now = System.currentTimeMillis();
      this.dimensions.values().forEach(dim -> dim.releaseStaleRegionData(now, releaseIntervalMillis));
   }

   public long estimateMemoryUsage() {
      long memory = 0L;

      for (MapDimension dim : this.dimensions.values()) {
         memory += dim.getLoadedRegions().stream().filter(MapRegion::isDataLoaded).mapToLong(region -> 4194304L).sum();
      }

      return memory;
   }

   public void scheduleRegionPurge(MapDimension toPurge) {
      this.pendingRegionPurge = toPurge;
   }

   public void checkForRegionPurge() {
      if (this.pendingRegionPurge != null) {
         synchronized (this.lock) {
            int autoRelease = (Integer)FTBChunksClientConfig.AUTORELEASE_ON_MAP_CLOSE.get();
            List<MapRegion> dataLoadedRegions = this.pendingRegionPurge.getLoadedRegions().stream().filter(MapRegion::isDataLoaded).toList();
            long nLoaded = dataLoadedRegions.size();
            autoRelease = Math.max(4, autoRelease);
            if (nLoaded > autoRelease) {
               dataLoadedRegions.stream()
                  .sorted(Comparator.comparingLong(MapRegion::getLastDataAccess))
                  .limit(nLoaded - autoRelease)
                  .forEach(r -> r.release(false));
            }

            this.pendingRegionPurge = null;
         }
      }
   }

   public void addPendingUpdateEvent(Team team, ChunkDimPos dim, MapChunk.DateInfo dateInfo) {
      this.pendingUpdateEvents.addPending(team, dim, dateInfo);
   }

   public void firePendingUpdateEvents() {
      this.pendingUpdateEvents.fireEvents();
   }
}
