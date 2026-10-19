package xaero.map.world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.file.MapSaveLoad;
import xaero.map.gui.GuiDimensionOptions;
import xaero.map.gui.GuiMapSwitching;

public class MapWorld {
   private MapProcessor mapProcessor;
   private boolean isMultiplayer;
   private String mainId;
   private String oldUnfixedMainId;
   private Hashtable<ResourceKey<Level>, MapDimension> dimensions;
   private ResourceKey<Level> currentDimensionId;
   private ResourceKey<Level> futureDimensionId;
   private ResourceKey<Level> customDimensionId;
   private int futureMultiworldType;
   private int currentMultiworldType;
   private boolean futureMultiworldTypeConfirmed = true;
   private boolean currentMultiworldTypeConfirmed = false;
   private boolean ignoreServerLevelId;
   private boolean ignoreHeightmaps;
   private String playerTeleportCommandFormat = "/tp @s {name}";
   private String normalTeleportCommandFormat = "/tp @s {x} {y} {z}";
   private String dimensionTeleportCommandFormat = "/execute as @s in {d} run tp {x} {y} {z}";
   private boolean useDefaultPlayerTeleport;
   private boolean useDefaultMapTeleport;
   private MapConnectionManager mapConnections;

   public MapWorld(String mainId, String oldUnfixedMainId, MapProcessor mapProcessor) {
      this.mainId = mainId;
      this.oldUnfixedMainId = oldUnfixedMainId;
      this.mapProcessor = mapProcessor;
      this.isMultiplayer = MapProcessor.isWorldMultiplayer(MapProcessor.isWorldRealms(mainId), mainId);
      this.dimensions = new Hashtable<>();
      this.futureMultiworldType = this.currentMultiworldType = 0;
      this.useDefaultPlayerTeleport = true;
      this.useDefaultMapTeleport = true;
   }

   public MapDimension getDimension(ResourceKey<Level> dimId) {
      if (dimId == null) {
         return null;
      }

      synchronized (this.dimensions) {
         return this.dimensions.get(dimId);
      }
   }

   public MapDimension createDimensionUnsynced(ResourceKey<Level> dimId) {
      synchronized (this.dimensions) {
         MapDimension result = this.dimensions.get(dimId);
         if (result == null) {
            this.dimensions.put(dimId, result = new MapDimension(this, dimId, this.mapProcessor.getHighlighterRegistry()));
            result.onCreationUnsynced();
         }

         return result;
      }
   }

   public String getMainId() {
      return this.mainId;
   }

   public String getOldUnfixedMainId() {
      return this.oldUnfixedMainId;
   }

   public String getCurrentMultiworld() {
      MapDimension container = this.getDimension(this.currentDimensionId);
      return container.getCurrentMultiworld();
   }

   public String getFutureMultiworldUnsynced() {
      MapDimension container = this.getDimension(this.futureDimensionId);
      return container.getFutureMultiworldUnsynced();
   }

   public MapDimension getCurrentDimension() {
      ResourceKey<Level> dimId = this.currentDimensionId;
      return dimId == null ? null : this.getDimension(dimId);
   }

   public MapDimension getFutureDimension() {
      ResourceKey<Level> dimId = this.futureDimensionId;
      return dimId == null ? null : this.getDimension(dimId);
   }

   public ResourceKey<Level> getCurrentDimensionId() {
      return this.currentDimensionId;
   }

   public ResourceKey<Level> getFutureDimensionId() {
      return this.futureDimensionId;
   }

   public void setFutureDimensionId(ResourceKey<Level> dimension) {
      this.futureDimensionId = dimension;
   }

   public ResourceKey<Level> getCustomDimensionId() {
      return this.customDimensionId;
   }

   public void setCustomDimensionId(ResourceKey<Level> dimension) {
      this.customDimensionId = dimension;
   }

   public void switchToFutureUnsynced() {
      this.currentDimensionId = this.futureDimensionId;
      this.getDimension(this.currentDimensionId).switchToFutureUnsynced();
   }

   public List<MapDimension> getDimensionsList() {
      List<MapDimension> destList = new ArrayList<>();
      this.getDimensions(destList);
      return destList;
   }

   public void getDimensions(List<MapDimension> dest) {
      synchronized (this.dimensions) {
         dest.addAll(this.dimensions.values());
      }
   }

   public int getCurrentMultiworldType() {
      return this.currentMultiworldType;
   }

   public boolean isMultiplayer() {
      return this.isMultiplayer;
   }

   public boolean isCurrentMultiworldTypeConfirmed() {
      return this.currentMultiworldTypeConfirmed;
   }

   public int getFutureMultiworldType(MapDimension dim) {
      return dim.isFutureMultiworldServerBased() ? 2 : this.futureMultiworldType;
   }

   public void toggleMultiworldTypeUnsynced() {
      this.unconfirmMultiworldTypeUnsynced();
      this.futureMultiworldType = (this.futureMultiworldType + 1) % 3;
      this.getCurrentDimension().resetCustomMultiworldUnsynced();
      this.saveConfig();
   }

   public void unconfirmMultiworldTypeUnsynced() {
      this.futureMultiworldTypeConfirmed = false;
   }

   public void confirmMultiworldTypeUnsynced() {
      this.futureMultiworldTypeConfirmed = true;
   }

   public boolean isFutureMultiworldTypeConfirmed(MapDimension dim) {
      return dim.isFutureMultiworldServerBased() ? true : this.futureMultiworldTypeConfirmed;
   }

   public void switchToFutureMultiworldTypeUnsynced() {
      MapDimension futureDim = this.getFutureDimension();
      this.currentMultiworldType = this.getFutureMultiworldType(this.getFutureDimension());
      this.currentMultiworldTypeConfirmed = this.isFutureMultiworldTypeConfirmed(futureDim);
   }

   public void load() {
      this.mapConnections = this.isMultiplayer ? new MapConnectionManager() : new MapConnectionManager() {
         @Override
         public boolean isConnected(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
            return true;
         }

         @Override
         public void save(PrintWriter writer) {
         }
      };
      Path rootSavePath = MapSaveLoad.getRootFolder(this.mainId);
      this.loadConfig(rootSavePath, 10);

      try (Stream<Path> stream = Files.list(rootSavePath)) {
         stream.forEach(folder -> {
            if (Files.isDirectory(folder)) {
               String folderName = folder.getFileName().toString();
               ResourceKey<Level> folderDimensionId = this.mapProcessor.getDimensionIdForFolder(folderName);
               if (folderDimensionId != null) {
                  this.createDimensionUnsynced(folderDimensionId);
               }
            }
         });
      } catch (IOException e) {
         WorldMap.LOGGER.error("suppressed exception", e);
      }
   }

   private void loadConfig(Path rootSavePath, int attempts) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      String defaultMapTeleportCommand = (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_MAP_TELEPORT_FORMAT);
      String defaultMapTeleportDimensionCommand = (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_MAP_TELEPORT_DIMENSION_FORMAT);
      String defaultPlayerTeleportCommand = (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_PLAYER_TELEPORT_FORMAT);
      MapProcessor mp = this.mapProcessor;
      BufferedReader reader = null;

      try {
         if (!Files.exists(rootSavePath)) {
            Files.createDirectories(rootSavePath);
         }

         Path configFile = rootSavePath.resolve("server_config.txt");
         Path oldOverworldSavePath = mp.getMapSaveLoad().getOldFolder(this.oldUnfixedMainId, "null");
         Path oldConfigFile = oldOverworldSavePath.resolve("server_config.txt");
         if (!Files.exists(configFile) && Files.exists(oldConfigFile)) {
            Files.move(oldConfigFile, configFile);
         }

         if (!Files.exists(configFile)) {
            this.saveConfig();
         } else {
            reader = new BufferedReader(new FileReader(configFile.toFile()));
            boolean setUseDefaultMapTeleport = false;
            boolean setUseDefaultPlayerTeleport = false;

            String line;
            while ((line = reader.readLine()) != null) {
               String[] args = line.split(":");
               if (this.isMultiplayer) {
                  if (args[0].equals("multiworldType")) {
                     this.futureMultiworldType = Integer.parseInt(args[1]);
                  } else if (args[0].equals("ignoreServerLevelId")) {
                     this.ignoreServerLevelId = args[1].equals("true");
                  }
               }

               if (args[0].equals("ignoreHeightmaps")) {
                  this.ignoreHeightmaps = args[1].equals("true");
               } else if (args[0].equals("playerTeleportCommandFormat")) {
                  this.playerTeleportCommandFormat = line.substring(line.indexOf(58) + 1);
               } else if (args[0].equals("teleportCommandFormat")) {
                  this.normalTeleportCommandFormat = line.substring(line.indexOf(58) + 1);
                  this.dimensionTeleportCommandFormat = "/execute as @s in {d} run " + this.normalTeleportCommandFormat.substring(1);
               } else if (args[0].equals("dimensionTeleportCommandFormat")) {
                  this.dimensionTeleportCommandFormat = line.substring(line.indexOf(58) + 1);
               } else if (args[0].equals("normalTeleportCommandFormat")) {
                  this.normalTeleportCommandFormat = line.substring(line.indexOf(58) + 1);
               } else if (args[0].equals("useDefaultMapTeleport")) {
                  this.useDefaultMapTeleport = args[1].equals("true");
                  setUseDefaultMapTeleport = true;
               } else if (args[0].equals("useDefaultPlayerTeleport")) {
                  this.useDefaultPlayerTeleport = args[1].equals("true");
                  setUseDefaultPlayerTeleport = true;
               } else if (this.isMultiplayer && args[0].equals("connection")) {
                  String mapKey1 = args[1];
                  if (args.length > 2) {
                     String mapKey2 = args[2];
                     MapConnectionNode connectionNode1 = MapConnectionNode.fromString(mapKey1);
                     MapConnectionNode connectionNode2 = MapConnectionNode.fromString(mapKey2);
                     if (connectionNode1 != null && connectionNode2 != null) {
                        this.mapConnections.addConnection(connectionNode1, connectionNode2);
                     }
                  }
               }
            }

            if (!setUseDefaultMapTeleport) {
               this.useDefaultMapTeleport = this.normalTeleportCommandFormat.equals(defaultMapTeleportCommand)
                  && this.dimensionTeleportCommandFormat.equals(defaultMapTeleportDimensionCommand);
            }

            if (!setUseDefaultPlayerTeleport) {
               this.useDefaultPlayerTeleport = this.playerTeleportCommandFormat.equals(defaultPlayerTeleportCommand);
               return;
            }
         }

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

         WorldMap.LOGGER.warn("IO exception while loading world map config. Retrying... " + attempts);

         try {
            Thread.sleep(20L);
         } catch (InterruptedException var30) {
         }

         this.loadConfig(rootSavePath, attempts - 1);
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

   public void saveConfig() {
      Path rootSavePath = MapSaveLoad.getRootFolder(this.mainId);
      PrintWriter writer = null;

      try {
         writer = new PrintWriter(new FileWriter(rootSavePath.resolve("server_config.txt").toFile()));
         if (this.isMultiplayer) {
            writer.println("multiworldType:" + this.futureMultiworldType);
            writer.println("ignoreServerLevelId:" + this.ignoreServerLevelId);
         }

         writer.println("ignoreHeightmaps:" + this.ignoreHeightmaps);
         writer.println("playerTeleportCommandFormat:" + this.playerTeleportCommandFormat);
         writer.println("normalTeleportCommandFormat:" + this.normalTeleportCommandFormat);
         writer.println("dimensionTeleportCommandFormat:" + this.dimensionTeleportCommandFormat);
         writer.println("useDefaultMapTeleport:" + this.useDefaultMapTeleport);
         writer.println("useDefaultPlayerTeleport:" + this.useDefaultPlayerTeleport);
         if (this.isMultiplayer) {
            this.mapConnections.save(writer);
         }
      } catch (IOException e) {
         WorldMap.LOGGER.error("suppressed exception", e);
      } finally {
         if (writer != null) {
            writer.close();
         }
      }
   }

   public MapProcessor getMapProcessor() {
      return this.mapProcessor;
   }

   public boolean isIgnoreServerLevelId() {
      return this.ignoreServerLevelId;
   }

   public boolean isIgnoreHeightmaps() {
      return this.ignoreHeightmaps;
   }

   public void setIgnoreHeightmaps(boolean ignoreHeightmaps) {
      this.ignoreHeightmaps = ignoreHeightmaps;
   }

   public String getPlayerTeleportCommandFormat() {
      return this.playerTeleportCommandFormat;
   }

   public void setPlayerTeleportCommandFormat(String playerTeleportCommandFormat) {
      this.playerTeleportCommandFormat = playerTeleportCommandFormat;
   }

   public String getEffectivePlayerTeleportCommandFormat() {
      if (!this.useDefaultPlayerTeleport) {
         return this.getPlayerTeleportCommandFormat();
      }

      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      return (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_PLAYER_TELEPORT_FORMAT);
   }

   public String getTeleportCommandFormat() {
      return this.normalTeleportCommandFormat;
   }

   public void setTeleportCommandFormat(String teleportCommandFormat) {
      this.normalTeleportCommandFormat = teleportCommandFormat;
   }

   public String getEffectiveTeleportCommandFormat() {
      if (!this.useDefaultMapTeleport) {
         return this.getTeleportCommandFormat();
      }

      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      return (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_MAP_TELEPORT_FORMAT);
   }

   public String getDimensionTeleportCommandFormat() {
      return this.dimensionTeleportCommandFormat;
   }

   public void setDimensionTeleportCommandFormat(String dimensionTeleportCommandFormat) {
      this.dimensionTeleportCommandFormat = dimensionTeleportCommandFormat;
   }

   public String getEffectiveDimensionTeleportCommandFormat() {
      if (!this.useDefaultMapTeleport) {
         return this.getDimensionTeleportCommandFormat();
      }

      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      return (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_MAP_TELEPORT_DIMENSION_FORMAT);
   }

   public void clearAllCachedHighlightHashes() {
      synchronized (this.dimensions) {
         for (MapDimension dim : this.dimensions.values()) {
            dim.getHighlightHandler().clearCachedHashes();
         }
      }
   }

   public boolean isUsingCustomDimension() {
      Level world = this.mapProcessor.getWorld();
      return world != null && world.dimension() != this.getCurrentDimensionId();
   }

   public boolean isUsingUnknownDimensionType() {
      return this.getCurrentDimension().isUsingUnknownDimensionType(this.mapProcessor.getWorldDimensionTypeRegistry());
   }

   public boolean isCacheOnlyMode() {
      return this.getCurrentDimension().isCacheOnlyMode(this.mapProcessor.getWorldDimensionTypeRegistry());
   }

   public void onWorldChangeUnsynced(ClientLevel world) {
      synchronized (this.dimensions) {
         for (MapDimension dim : this.dimensions.values()) {
            dim.onWorldChangeUnsynced(world);
         }
      }
   }

   public ResourceKey<Level> getPotentialDimId() {
      ResourceKey<Level> customDimId = this.getCustomDimensionId();
      return customDimId == null ? this.mapProcessor.mainWorld.dimension() : customDimId;
   }

   public MapConnectionNode getPlayerMapKey() {
      this.mapProcessor.updateVisitedDimension(this.mapProcessor.mainWorld);
      ResourceKey<Level> dimId = this.mapProcessor.mainWorld.dimension();
      MapDimension dim = this.getDimension(dimId);
      return dim == null ? null : dim.getPlayerMapKey();
   }

   public MapConnectionManager getMapConnections() {
      return this.mapConnections;
   }

   public void toggleDimension(boolean forward) {
      MapDimension futureDimension = this.getFutureDimension();
      if (futureDimension != null) {
         GuiDimensionOptions dimOptions = GuiMapSwitching.getSortedDimensionOptions(futureDimension);
         int step = forward ? 1 : dimOptions.values.length - 1;
         int nextIndex = dimOptions.selected == -1 ? 0 : (dimOptions.selected + step) % dimOptions.values.length;
         ResourceKey<Level> nextDimId = dimOptions.values[nextIndex];
         if (nextDimId == Minecraft.getInstance().level.dimension()) {
            nextDimId = null;
         }

         this.setCustomDimensionId(nextDimId);
         this.mapProcessor.checkForWorldUpdate();
      }
   }

   public static String convertWorldFolderToRootId(int version, String worldFolder) {
      String rootId = worldFolder.replaceAll("_", "^us^");
      if (MapProcessor.isWorldMultiplayer(MapProcessor.isWorldRealms(rootId), rootId)) {
         rootId = "^e^" + rootId;
      }

      if (version >= 3) {
         rootId = rootId.replace("[", "%lb%").replace("]", "%rb%");
      }

      return rootId;
   }

   public boolean isUsingDefaultMapTeleport() {
      return this.useDefaultMapTeleport;
   }

   public boolean isUsingDefaultPlayerTeleport() {
      return this.useDefaultPlayerTeleport;
   }

   public void setUseDefaultMapTeleport(boolean useDefaultMapTeleport) {
      this.useDefaultMapTeleport = useDefaultMapTeleport;
   }

   public void setUseDefaultPlayerTeleport(boolean useDefaultPlayerTeleport) {
      this.useDefaultPlayerTeleport = useDefaultPlayerTeleport;
   }
}
