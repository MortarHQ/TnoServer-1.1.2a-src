package xaero.map.world;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import org.apache.commons.io.FileUtils;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.MapFullReloader;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.file.RegionDetection;
import xaero.map.highlight.DimensionHighlighterHandler;
import xaero.map.highlight.HighlighterRegistry;
import xaero.map.region.LayeredRegionManager;
import xaero.map.region.LeveledRegion;
import xaero.map.region.MapLayer;
import xaero.map.region.MapRegion;
import xaero.map.util.linked.LinkedChain;

public class MapDimension {
   private final MapWorld mapWorld;
   private final ResourceKey<Level> dimId;
   private final List<String> multiworldIds;
   private final Hashtable<String, String> multiworldNames;
   private final Hashtable<String, String> autoMultiworldBindings;
   private final DimensionHighlighterHandler highlightHandler;
   private ResourceLocation dimensionTypeId;
   private DimensionType dimensionType;
   private DimensionSpecialEffects dimensionEffects;
   private float shadowR = 1.0F;
   private float shadowG = 1.0F;
   private float shadowB = 1.0F;
   private String futureAutoMultiworldBinding;
   private String futureCustomSelectedMultiworld;
   public boolean futureMultiworldWritable;
   public boolean futureMultiworldServerBased;
   private String currentMultiworld;
   public boolean currentMultiworldWritable;
   private String confirmedMultiworld;
   private final LayeredRegionManager mapRegions;
   private List<MapRegion> regionBackCompList;
   private final Hashtable<Integer, Hashtable<Integer, RegionDetection>> worldSaveDetectedRegions;
   private final LinkedChain<RegionDetection> worldSaveDetectedRegionsLinked;
   private boolean doneRegionDetection;
   public final ArrayList<LeveledRegion<?>> regionsToCache;
   private MapFullReloader fullReloader;
   private int caveModeType;
   private static final int CAVE_MODE_TYPES = 3;

   public MapDimension(MapWorld mapWorld, ResourceKey<Level> dimId, HighlighterRegistry highlighterRegistry) {
      this.mapWorld = mapWorld;
      this.dimId = dimId;
      this.multiworldIds = new ArrayList<>();
      this.multiworldNames = new Hashtable<>();
      this.mapRegions = new LayeredRegionManager(this);
      this.autoMultiworldBindings = new Hashtable<>();
      this.regionsToCache = new ArrayList<>();
      this.regionBackCompList = new ArrayList<>();
      this.highlightHandler = new DimensionHighlighterHandler(this, dimId, highlighterRegistry);
      this.worldSaveDetectedRegions = new Hashtable<>();
      this.worldSaveDetectedRegionsLinked = new LinkedChain<>();
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      this.caveModeType = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_CAVE_MODE_TYPE);
   }

   public String getCurrentMultiworld() {
      return this.currentMultiworld;
   }

   public boolean isUsingWorldSave() {
      return !this.mapWorld.isMultiplayer() && (this.currentMultiworld == null || this.currentMultiworld.isEmpty());
   }

   public boolean isFutureUsingWorldSaveUnsynced() {
      return !this.mapWorld.isMultiplayer() && (this.getFutureMultiworldUnsynced() == null || this.getFutureMultiworldUnsynced().isEmpty());
   }

   public List<String> getMultiworldIdsCopy() {
      synchronized (this.multiworldIds) {
         return new ArrayList<>(this.multiworldIds);
      }
   }

   public void updateFutureAutomaticUnsynced(Minecraft mc, Object baseObject) {
      if (!this.mapWorld.isMultiplayer()) {
         this.futureAutoMultiworldBinding = "";
         this.futureMultiworldServerBased = false;
      } else if (baseObject != null) {
         if (baseObject instanceof BlockPos dimSpawn) {
            this.futureAutoMultiworldBinding = "mw" + (dimSpawn.getX() >> 6) + "," + (dimSpawn.getY() >> 6) + "," + (dimSpawn.getZ() >> 6);
            this.futureMultiworldServerBased = false;
         } else if (baseObject instanceof Integer) {
            int levelId = (Integer)baseObject;
            this.futureAutoMultiworldBinding = "mw$" + levelId;
            this.futureMultiworldServerBased = true;
         }
      } else {
         this.futureAutoMultiworldBinding = "unknown";
      }
   }

   public String getFutureCustomSelectedMultiworld() {
      return this.futureCustomSelectedMultiworld;
   }

   public String getFutureMultiworldUnsynced() {
      return this.futureCustomSelectedMultiworld == null ? this.getFutureAutoMultiworld() : this.futureCustomSelectedMultiworld;
   }

   public void switchToFutureUnsynced() {
      this.currentMultiworld = this.getFutureMultiworldUnsynced();
      this.addMultiworldChecked(this.currentMultiworld);
   }

   public void switchToFutureMultiworldWritableValueUnsynced() {
      this.currentMultiworldWritable = this.futureMultiworldWritable;
   }

   public LayeredRegionManager getLayeredMapRegions() {
      return this.mapRegions;
   }

   public void clear() {
      this.regionsToCache.clear();
      this.mapRegions.clear();
      this.regionBackCompList.clear();
      this.worldSaveDetectedRegions.clear();
      this.worldSaveDetectedRegionsLinked.reset();
      this.doneRegionDetection = false;
      this.clearFullMapReload();
   }

   public void preDetection() {
      this.doneRegionDetection = true;
      this.mapRegions.preDetection();
   }

   public Path getMainFolderPath() {
      return this.mapWorld
         .getMapProcessor()
         .getMapSaveLoad()
         .getMainFolder(this.mapWorld.getMainId(), this.mapWorld.getMapProcessor().getDimensionName(this.dimId));
   }

   public Path getOldFolderPath() {
      return this.mapWorld
         .getMapProcessor()
         .getMapSaveLoad()
         .getOldFolder(this.mapWorld.getOldUnfixedMainId(), this.mapWorld.getMapProcessor().getDimensionName(this.dimId));
   }

   public void saveConfigUnsynced() {
      Path dimensionSavePath = this.getMainFolderPath();

      try (
         BufferedOutputStream bufferedOutput = new BufferedOutputStream(new FileOutputStream(dimensionSavePath.resolve("dimension_config.txt").toFile()));
         PrintWriter writer = new PrintWriter(new OutputStreamWriter(bufferedOutput, StandardCharsets.UTF_8));
      ) {
         if (this.confirmedMultiworld != null) {
            writer.println("confirmedMultiworld:" + this.confirmedMultiworld);
         }

         for (Entry<String, String> bindingEntry : this.autoMultiworldBindings.entrySet()) {
            writer.println("autoMWBinding:" + bindingEntry.getKey() + ":" + bindingEntry.getValue());
         }

         for (Entry<String, String> bindingEntry : this.multiworldNames.entrySet()) {
            writer.println("MWName:" + bindingEntry.getKey() + ":" + bindingEntry.getValue().replace(":", "^col^"));
         }

         writer.println("caveModeType:" + this.caveModeType);
         if (this.dimensionTypeId != null) {
            writer.println("dimensionTypeId:" + this.dimensionTypeId);
         }
      } catch (IOException e) {
         WorldMap.LOGGER.error("suppressed exception", e);
      }
   }

   private void loadConfigUnsynced(int attempts) {
      Path dimensionSavePath = this.getMainFolderPath();
      BufferedReader reader = null;

      try {
         Path oldDimensionSavePath = this.getOldFolderPath();
         if (!Files.exists(dimensionSavePath) && Files.exists(oldDimensionSavePath)) {
            Files.move(oldDimensionSavePath, dimensionSavePath);
         }

         if (!Files.exists(dimensionSavePath)) {
            Files.createDirectories(dimensionSavePath);
         }

         this.loadMultiworldsList(dimensionSavePath);
         Path configFile = dimensionSavePath.resolve("dimension_config.txt");
         if (Files.exists(configFile)) {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile.toFile()), "UTF8"));

            String line;
            while ((line = reader.readLine()) != null) {
               String[] args = line.split(":");
               if (args[0].equals("confirmedMultiworld")) {
                  String savedMultiworld = args.length > 1 ? args[1] : "";
                  if (this.multiworldIds.contains(savedMultiworld)) {
                     this.confirmedMultiworld = savedMultiworld;
                  }
               } else if (args[0].equals("autoMWBinding")) {
                  this.bindAutoMultiworld(args[1], args[2]);
               } else if (args[0].equals("MWName")) {
                  this.setMultiworldName(args[1], args[2].replace("^col^", ":"));
               } else if (args[0].equals("dimensionTypeId")) {
                  this.dimensionTypeId = ResourceLocation.parse(line.substring(line.indexOf(58) + 1));
               }

               if (args[0].equals("caveModeType")) {
                  this.caveModeType = Integer.parseInt(args[1]);
               }
            }

            return;
         }

         this.saveConfigUnsynced();
         return;
      } catch (IOException e1) {
         if (attempts <= 1) {
            throw new RuntimeException(e1);
         }

         if (reader != null) {
            try {
               reader.close();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
         }

         WorldMap.LOGGER.warn("IO exception while loading world map dimension config. Retrying... " + attempts);

         try {
            Thread.sleep(20L);
         } catch (InterruptedException var19) {
         }

         this.loadConfigUnsynced(attempts - 1);
      } finally {
         if (reader != null) {
            try {
               reader.close();
            } catch (IOException e) {
               WorldMap.LOGGER.error("suppressed exception", e);
            }
         }
      }
   }

   public void pickDefaultCustomMultiworldUnsynced() {
      if (this.multiworldIds.isEmpty()) {
         this.futureCustomSelectedMultiworld = "mw$default";
         this.multiworldIds.add(this.futureCustomSelectedMultiworld);
         this.setMultiworldName(this.futureCustomSelectedMultiworld, "Default");
      } else {
         int indexOfAuto = this.multiworldIds.indexOf(this.getFutureAutoMultiworld());
         this.futureCustomSelectedMultiworld = this.multiworldIds.get(indexOfAuto != -1 ? indexOfAuto : 0);
      }
   }

   private void loadMultiworldsList(Path dimensionSavePath) {
      if (!this.mapWorld.isMultiplayer()) {
         this.multiworldIds.add("");
      }

      try {
         Stream<Path> subFolders = Files.list(dimensionSavePath);
         Iterator<Path> iter = subFolders.iterator();

         while (iter.hasNext()) {
            Path path = iter.next();
            if (path.toFile().isDirectory()) {
               String folderName = path.getFileName().toString();
               boolean autoMultiworldFormat = folderName.matches("^mw(-?\\d+),(-?\\d+),(-?\\d+)$");
               boolean levelIdMultiworldFormat = folderName.startsWith("mw$");
               boolean customMultiworldFormat = folderName.startsWith("cm$");
               if (autoMultiworldFormat || levelIdMultiworldFormat || customMultiworldFormat) {
                  this.multiworldIds.add(folderName);
               }
            }
         }

         subFolders.close();
      } catch (IOException e) {
         WorldMap.LOGGER.error("suppressed exception", e);
      }
   }

   public void confirmMultiworldUnsynced() {
      if (!this.futureMultiworldWritable) {
         this.futureMultiworldWritable = true;
         if (this.mapWorld.getFutureMultiworldType(this) == 2 && this.futureCustomSelectedMultiworld != null) {
            this.makeCustomSelectedMultiworldAutoUnsynced();
         }

         this.confirmedMultiworld = this.getFutureMultiworldUnsynced();
         this.saveConfigUnsynced();
      }
   }

   private void makeCustomSelectedMultiworldAutoUnsynced() {
      String currentAutoMultiworld = this.getFutureAutoMultiworld();
      boolean currentBindingFound = false;

      for (Entry<String, String> bindingEntry : this.autoMultiworldBindings.entrySet()) {
         if (bindingEntry.getValue().equals(this.futureCustomSelectedMultiworld)) {
            this.bindAutoMultiworld(bindingEntry.getKey(), currentAutoMultiworld);
            currentBindingFound = true;
            break;
         }
      }

      if (!currentBindingFound && !this.futureCustomSelectedMultiworld.startsWith("cm$")) {
         this.bindAutoMultiworld(this.futureCustomSelectedMultiworld, currentAutoMultiworld);
      }

      this.bindAutoMultiworld(this.futureAutoMultiworldBinding, this.futureCustomSelectedMultiworld);
      this.futureCustomSelectedMultiworld = null;
      this.saveConfigUnsynced();
   }

   private void bindAutoMultiworld(String binding, String multiworld) {
      if (binding.equals(multiworld)) {
         this.autoMultiworldBindings.remove(binding);
      } else {
         this.autoMultiworldBindings.put(binding, multiworld);
      }
   }

   public void resetCustomMultiworldUnsynced() {
      this.futureCustomSelectedMultiworld = this.mapWorld.getFutureMultiworldType(this) == 2 ? null : this.confirmedMultiworld;
      if (this.futureCustomSelectedMultiworld == null && this.mapWorld.isMultiplayer() && this.mapWorld.getFutureMultiworldType(this) < 2) {
         this.pickDefaultCustomMultiworldUnsynced();
      }

      this.futureMultiworldWritable = this.mapWorld.getFutureMultiworldType(this) != 1 && this.mapWorld.isFutureMultiworldTypeConfirmed(this);
   }

   public void setMultiworldUnsynced(String nextMW) {
      String cmw = this.futureCustomSelectedMultiworld == null ? this.getFutureMultiworldUnsynced() : this.futureCustomSelectedMultiworld;
      this.futureCustomSelectedMultiworld = nextMW;
      this.futureMultiworldWritable = false;
      WorldMap.LOGGER.info(cmw + " -> " + this.futureCustomSelectedMultiworld);
   }

   private boolean multiworldExists(String mw) {
      synchronized (this.multiworldIds) {
         return this.multiworldIds.contains(mw);
      }
   }

   public boolean addMultiworldChecked(String mw) {
      synchronized (this.multiworldIds) {
         if (!this.multiworldIds.contains(mw)) {
            this.multiworldIds.add(mw);
            return true;
         } else {
            return false;
         }
      }
   }

   public String getMultiworldName(String mwId) {
      if (mwId.isEmpty()) {
         return "gui.xaero_world_save";
      }

      String tableName = this.multiworldNames.get(mwId);
      if (tableName != null) {
         return tableName;
      }

      if (!this.multiworldExists(mwId)) {
         return mwId;
      }

      int index = 1;

      String automaticName;
      while (this.multiworldNames.containsValue(automaticName = "Map " + index++)) {
      }

      this.setMultiworldName(mwId, automaticName);
      synchronized (this.mapWorld.getMapProcessor().uiSync) {
         this.saveConfigUnsynced();
         return automaticName;
      }
   }

   public void setMultiworldName(String mwId, String mwName) {
      this.multiworldNames.put(mwId, mwName);
   }

   private String getFutureAutoMultiworld() {
      String futureAutoMultiworldBinding = this.futureAutoMultiworldBinding;
      if (futureAutoMultiworldBinding == null) {
         return null;
      }

      String boundMultiworld = this.autoMultiworldBindings.get(futureAutoMultiworldBinding);
      return boundMultiworld == null ? futureAutoMultiworldBinding : boundMultiworld;
   }

   public MapWorld getMapWorld() {
      return this.mapWorld;
   }

   public void deleteMultiworldMapDataUnsynced(String mwId) {
      try {
         Path currentDimFolder = this.getMainFolderPath();
         Path currentMWFolder = currentDimFolder.resolve(mwId);
         Path binFolder = currentDimFolder.resolve("last deleted");
         Path binMWFolder = binFolder.resolve(mwId);
         if (!Files.exists(binFolder)) {
            Files.createDirectories(binFolder);
         }

         FileUtils.cleanDirectory(binFolder.toFile());
         Files.move(currentMWFolder, binMWFolder);
      } catch (Exception e) {
         WorldMap.LOGGER.error("suppressed exception", e);
      }
   }

   public void deleteMultiworldId(String mwId) {
      synchronized (this.multiworldIds) {
         this.multiworldIds.remove(mwId);
         this.multiworldNames.remove(mwId);
         if (mwId.equals(this.confirmedMultiworld)) {
            this.confirmedMultiworld = null;
         }
      }
   }

   public ResourceKey<Level> getDimId() {
      return this.dimId;
   }

   public boolean hasConfirmedMultiworld() {
      return this.confirmedMultiworld != null;
   }

   public boolean isFutureMultiworldServerBased() {
      return this.futureMultiworldServerBased;
   }

   public DimensionHighlighterHandler getHighlightHandler() {
      return this.highlightHandler;
   }

   public void onClearCachedHighlightHash(int regionX, int regionZ) {
      this.mapRegions.onClearCachedHighlightHash(regionX, regionZ);
   }

   public void onClearCachedHighlightHashes() {
      this.mapRegions.onClearCachedHighlightHashes();
   }

   public boolean hasDoneRegionDetection() {
      return this.doneRegionDetection;
   }

   public void addWorldSaveRegionDetection(RegionDetection regionDetection) {
      synchronized (this.worldSaveDetectedRegions) {
         Hashtable<Integer, RegionDetection> column = this.worldSaveDetectedRegions.get(regionDetection.getRegionX());
         if (column == null) {
            this.worldSaveDetectedRegions.put(regionDetection.getRegionX(), column = new Hashtable<>());
         }

         column.put(regionDetection.getRegionZ(), regionDetection);
         this.worldSaveDetectedRegionsLinked.add(regionDetection);
      }
   }

   public RegionDetection getWorldSaveRegionDetection(int x, int z) {
      if (this.worldSaveDetectedRegions == null) {
         return null;
      }

      Hashtable<Integer, RegionDetection> column = this.worldSaveDetectedRegions.get(x);
      return column != null ? column.get(z) : null;
   }

   public int getCaveModeType() {
      return this.caveModeType;
   }

   public void toggleCaveModeType(boolean forward) {
      this.caveModeType += forward ? 1 : -1;
      if (forward) {
         if (this.caveModeType >= 3) {
            this.caveModeType = 0;
         }
      } else {
         if (this.caveModeType < 0) {
            this.caveModeType = 2;
         }
      }
   }

   public Iterable<Hashtable<Integer, RegionDetection>> getWorldSaveDetectedRegions() {
      return this.worldSaveDetectedRegions.values();
   }

   public Iterable<RegionDetection> getLinkedWorldSaveDetectedRegions() {
      return this.worldSaveDetectedRegionsLinked;
   }

   public MapFullReloader getFullReloader() {
      return this.fullReloader;
   }

   public void startFullMapReload(int caveLayer, boolean resave, MapProcessor mapProcessor) {
      MapLayer layer = this.mapRegions.getLayer(caveLayer);
      this.fullReloader = new MapFullReloader(caveLayer, resave, layer.getLinkedCompleteWorldSaveDetectedRegions().iterator(), this, mapProcessor);
   }

   public void clearFullMapReload() {
      this.fullReloader = null;
   }

   public DimensionType getDimensionType(Registry<DimensionType> dimensionTypes) {
      if (this.dimensionType != null) {
         return this.dimensionType;
      }

      this.dimensionType = getDimensionType(this.dimId, this.dimensionTypeId, dimensionTypes);
      return this.dimensionType;
   }

   private static DimensionType getDimensionType(ResourceKey<Level> dimId, ResourceLocation dimensionTypeId, Registry<DimensionType> dimensionTypes) {
      if (dimensionTypeId == null) {
         if (dimId == Level.NETHER) {
            dimensionTypeId = BuiltinDimensionTypes.NETHER_EFFECTS;
         } else if (dimId == Level.OVERWORLD) {
            dimensionTypeId = BuiltinDimensionTypes.OVERWORLD_EFFECTS;
         } else {
            if (dimId != Level.END) {
               IntegratedServer integratedServer = Minecraft.getInstance().getSingleplayerServer();
               if (integratedServer == null) {
                  return null;
               }

               ServerLevel serverLevel = integratedServer.getLevel(dimId);
               if (serverLevel == null) {
                  return null;
               }

               return serverLevel.dimensionType();
            }

            dimensionTypeId = BuiltinDimensionTypes.END_EFFECTS;
         }
      }

      return dimensionTypes == null ? null : (DimensionType)dimensionTypes.get(dimensionTypeId);
   }

   public static DimensionType getDimensionType(MapDimension dim, ResourceKey<Level> dimId, Registry<DimensionType> dimensionTypes) {
      return dim == null ? getDimensionType(dimId, null, dimensionTypes) : dim.getDimensionType(dimensionTypes);
   }

   public void onWorldChangeUnsynced(Level newWorld) {
      if (newWorld != null && this.dimId.equals(newWorld.dimension())) {
         ResourceKey<DimensionType> dimTypeId = (ResourceKey<DimensionType>)newWorld.dimensionTypeRegistration().unwrapKey().get();
         if (!dimTypeId.location().equals(this.dimensionTypeId)) {
            this.dimensionTypeId = dimTypeId.location();
            this.saveConfigUnsynced();
         }
      }

      this.dimensionType = null;
      this.dimensionEffects = null;
   }

   public boolean isUsingUnknownDimensionType(Registry<DimensionType> dimensionTypes) {
      return this.getDimensionType(dimensionTypes) == null;
   }

   public boolean isCacheOnlyMode(Registry<DimensionType> dimensionTypes) {
      return this.isUsingUnknownDimensionType(dimensionTypes);
   }

   public void onCreationUnsynced() {
      this.loadConfigUnsynced(10);
      if (this.dimId == Level.OVERWORLD || BuiltinDimensionTypes.OVERWORLD_EFFECTS.equals(this.dimensionTypeId)) {
         this.shadowR = 0.518F;
         this.shadowG = 0.678F;
         this.shadowB = 1.0F;
      } else if (this.dimId == Level.NETHER || BuiltinDimensionTypes.NETHER_EFFECTS.equals(this.dimensionTypeId)) {
         this.shadowR = 1.0F;
         this.shadowG = 0.0F;
         this.shadowB = 0.0F;
      }
   }

   public float getShadowR() {
      return this.shadowR;
   }

   public float getShadowG() {
      return this.shadowG;
   }

   public float getShadowB() {
      return this.shadowB;
   }

   public DimensionSpecialEffects getDimensionEffects(Registry<DimensionType> dimensionTypes) {
      if (this.dimensionEffects == null) {
         DimensionType type = this.getDimensionType(dimensionTypes);
         if (type == null) {
            return null;
         }

         this.dimensionEffects = DimensionSpecialEffects.forType(type);
      }

      return this.dimensionEffects;
   }

   public float getSkyDarken(float partial, ClientLevel world, Registry<DimensionType> dimensionTypes) {
      if (this.dimId == world.dimension()) {
         return world.getSkyDarken(1.0F);
      }

      DimensionType dimType = this.getDimensionType(dimensionTypes);
      if (dimType == null) {
         return 1.0F;
      }

      float timeOfDay = dimType.timeOfDay(world.dayTime());
      float brightness = 1.0F - (Mth.cos(timeOfDay * (float) (Math.PI * 2)) * 2.0F + 0.2F);
      brightness = 1.0F - Mth.clamp(brightness, 0.0F, 1.0F);
      return brightness * 0.8F + 0.2F;
   }

   public double calculateDimScale(Registry<DimensionType> dimensionTypes) {
      DimensionType dimType = this.getDimensionType(dimensionTypes);
      return dimType == null ? 1.0 : dimType.coordinateScale();
   }

   public double calculateDimDiv(Registry<DimensionType> dimensionTypes, DimensionType actualDimension) {
      return this.calculateDimScale(dimensionTypes) / (actualDimension == null ? 1.0 : actualDimension.coordinateScale());
   }

   public MapConnectionNode getPlayerMapKey() {
      String playerMW = this.mapWorld.getFutureMultiworldType(this) == 1 ? null : this.getFutureAutoMultiworld();
      if (playerMW == null) {
         playerMW = this.confirmedMultiworld;
      }

      return playerMW == null ? null : new MapConnectionNode(this.dimId, playerMW);
   }

   public MapConnectionNode getSelectedMapKeyUnsynced() {
      String selectedMW = this.getFutureMultiworldUnsynced();
      if (selectedMW == null) {
         selectedMW = this.getCurrentMultiworld();
      }

      return selectedMW == null ? null : new MapConnectionNode(this.dimId, selectedMW);
   }

   public boolean isAutoSelected() {
      String selectedMW = this.getFutureCustomSelectedMultiworld();
      return selectedMW == null || selectedMW.equals(this.getFutureAutoMultiworld());
   }

   public ResourceLocation getDimensionTypeId() {
      return this.dimensionTypeId;
   }
}
