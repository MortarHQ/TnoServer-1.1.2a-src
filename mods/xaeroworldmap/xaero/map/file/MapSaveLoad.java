package xaero.map.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.lib.common.util.IOUtils;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.biome.BiomeGetter;
import xaero.map.cache.BlockStateShortShapeCache;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.file.export.PNGExportResult;
import xaero.map.file.export.PNGExporter;
import xaero.map.file.worldsave.WorldDataHandler;
import xaero.map.gui.ExportScreen;
import xaero.map.gui.MapTileSelection;
import xaero.map.region.BranchLeveledRegion;
import xaero.map.region.LayeredRegionManager;
import xaero.map.region.LeveledRegion;
import xaero.map.region.MapBlock;
import xaero.map.region.MapLayer;
import xaero.map.region.MapRegion;
import xaero.map.region.MapTile;
import xaero.map.region.MapTileChunk;
import xaero.map.region.Overlay;
import xaero.map.region.OverlayBuilder;
import xaero.map.region.OverlayManager;
import xaero.map.region.state.UnknownBlockState;
import xaero.map.task.MapRunnerTask;
import xaero.map.world.MapDimension;

public class MapSaveLoad {
   private static final int currentSaveMajorVersion = 6;
   private static final int currentSaveMinorVersion = 8;
   public static final int SAVE_TIME = 60000;
   public static final int currentCacheSaveMajorVersion = 1;
   public static final int currentCacheSaveMinorVersion = 24;
   private ArrayList<MapRegion> toSave = new ArrayList<>();
   private ArrayList<MapRegion> toLoad = new ArrayList<>();
   private ArrayList<BranchLeveledRegion> toLoadBranchCache = new ArrayList<>();
   private ArrayList<File> cacheToConvertFromTemp = new ArrayList<>();
   private LeveledRegion<?> nextToLoadByViewing;
   private boolean regionDetectionComplete;
   private Path lastRealmOwnerPath;
   public boolean loadingFiles;
   private OverlayBuilder overlayBuilder;
   private PNGExporter pngExporter;
   private OldFormatSupport oldFormatSupport;
   private HashMap<BlockState, Integer> regionSavePalette;
   private ArrayList<BlockState> regionLoadPalette;
   private HashMap<ResourceKey<Biome>, Integer> regionSaveBiomePalette;
   private ArrayList<ResourceKey<Biome>> regionLoadBiomePalette;
   private List<MapDimension> workingDimList;
   public boolean saveAll;
   private MapProcessor mapProcessor;
   public int mainTextureLevel;
   private BlockStateShortShapeCache blockStateShortShapeCache;
   private boolean exporting;

   public MapSaveLoad(
      OverlayManager overlayManager, PNGExporter pngExporter, OldFormatSupport oldFormatSupport, BlockStateShortShapeCache blockStateShortShapeCache
   ) {
      this.overlayBuilder = new OverlayBuilder(overlayManager);
      this.pngExporter = pngExporter;
      this.oldFormatSupport = oldFormatSupport;
      this.regionSavePalette = new HashMap<>();
      this.regionLoadPalette = new ArrayList<>();
      this.regionSaveBiomePalette = new HashMap<>();
      this.regionLoadBiomePalette = new ArrayList<>();
      this.workingDimList = new ArrayList<>();
      this.blockStateShortShapeCache = blockStateShortShapeCache;
   }

   public void setMapProcessor(MapProcessor mapProcessor) {
      this.mapProcessor = mapProcessor;
   }

   public boolean exportPNG(final ExportScreen destScreen, final MapTileSelection selection) {
      if (this.exporting) {
         return false;
      }

      this.exporting = true;
      WorldMap.mapRunner
         .addTask(
            new MapRunnerTask() {
               @Override
               public void run(MapProcessor mapProcessor) {
                  Minecraft.getInstance()
                     .submit(
                        () -> {
                           try {
                              PNGExportResult result = MapSaveLoad.this.pngExporter
                                 .export(
                                    mapProcessor,
                                    mapProcessor.worldBiomeRegistry,
                                    mapProcessor.getWorldDimensionTypeRegistry(),
                                    selection,
                                    MapSaveLoad.this.oldFormatSupport
                                 );
                              WorldMap.LOGGER.info(result.getMessage().getString());
                              if (destScreen != null) {
                                 destScreen.onExportDone(result);
                              }

                              if (result.getFolderToOpen() != null && Files.exists(result.getFolderToOpen())) {
                                 Util.getPlatform().openFile(result.getFolderToOpen().toFile());
                              }
                           } catch (Throwable e) {
                              WorldMap.LOGGER.error("Failed to export PNG with exception!", e);
                              WorldMap.crashHandler.setCrashedBy(e);
                           }

                           MapSaveLoad.this.exporting = false;
                           Minecraft.getInstance().setScreen(destScreen);
                        }
                     );

                  while (MapSaveLoad.this.exporting) {
                     try {
                        Thread.sleep(100L);
                     } catch (InterruptedException var3) {
                     }
                  }
               }
            }
         );
      return true;
   }

   private File getSecondaryFile(String extension, File realFile) {
      if (realFile == null) {
         return null;
      }

      String p = realFile.getPath();
      if (p.endsWith(".outdated")) {
         p = p.substring(0, p.length() - ".outdated".length());
      }

      return new File(p.substring(0, p.lastIndexOf(".")) + extension);
   }

   public File getTempFile(File realFile) {
      return this.getSecondaryFile(".zip.temp", realFile);
   }

   private Path getCacheFolder(Path subFolder) {
      return subFolder != null ? subFolder.resolve("cache_" + this.mapProcessor.getGlobalVersion()) : null;
   }

   public File getCacheFile(MapRegionInfo region, int caveLayer, boolean checkOutdated, boolean requestCache) throws IOException {
      Path subFolder = this.getMWSubFolder(region.getWorldId(), region.getDimId(), region.getMwId());
      Path layerFolder = this.getCaveLayerFolder(caveLayer, subFolder);
      Path latestCacheFolder = this.getCacheFolder(layerFolder);
      if (latestCacheFolder == null) {
         return null;
      }

      if (!Files.exists(latestCacheFolder)) {
         Files.createDirectories(latestCacheFolder);
      }

      Path cacheFile = latestCacheFolder.resolve(region.getRegionX() + "_" + region.getRegionZ() + ".xwmc");
      if (checkOutdated && !Files.exists(cacheFile)) {
         if (requestCache) {
            region.setShouldCache(true, "cache file");
         }

         Path outdatedCacheFile = cacheFile.resolveSibling(cacheFile.getFileName().toString() + ".outdated");
         return Files.exists(outdatedCacheFile) ? outdatedCacheFile.toFile() : cacheFile.toFile();
      } else {
         return cacheFile.toFile();
      }
   }

   public File getFile(MapRegion region) {
      if (region.getWorldId() == null) {
         return null;
      } else {
         File detectedFile = region.getRegionFile();
         boolean normalMapData = region.isNormalMapData();
         if (!normalMapData) {
            return detectedFile != null
               ? detectedFile
               : this.mapProcessor
                  .getWorldDataHandler()
                  .getWorldDir()
                  .resolve("region")
                  .resolve("r." + region.getRegionX() + "." + region.getRegionZ() + ".mca")
                  .toFile();
         } else {
            return this.getNormalFile(region);
         }
      }
   }

   public File getNormalFile(MapRegion region) {
      if (region.getWorldId() == null) {
         return null;
      }

      File detectedFile = region.isNormalMapData() ? region.getRegionFile() : null;
      boolean realms = MapProcessor.isWorldRealms(region.getWorldId());
      String mwId = region.isNormalMapData() ? region.getMwId() : "cm$converted";
      Path mainFolder = this.getMainFolder(region.getWorldId(), region.getDimId());
      Path subFolder = this.getMWSubFolder(region.getWorldId(), mainFolder, mwId);
      Path layerFolder = subFolder;
      if (region.getCaveLayer() != Integer.MAX_VALUE) {
         layerFolder = layerFolder.resolve("caves").resolve(region.getCaveLayer() + "");
      }

      try {
         File subFolderFile = layerFolder.toFile();
         if (!subFolderFile.exists()) {
            Files.createDirectories(subFolderFile.toPath());
            if (realms && WorldMap.events.getLatestRealm() != null) {
               Path ownerPath = mainFolder.resolve(WorldMap.events.getLatestRealm().owner + ".owner");
               if (!ownerPath.equals(this.lastRealmOwnerPath)) {
                  if (!Files.exists(ownerPath)) {
                     Files.createFile(ownerPath);
                  }

                  this.lastRealmOwnerPath = ownerPath;
               }
            }
         }
      } catch (IOException e1) {
         WorldMap.LOGGER.error("suppressed exception", e1);
      }

      if (detectedFile != null && detectedFile.getName().endsWith(".xaero")) {
         File zipFile = layerFolder.resolve(region.getRegionX() + "_" + region.getRegionZ() + ".zip").toFile();
         if (detectedFile.exists() && !zipFile.exists()) {
            this.xaeroToZip(detectedFile);
         }

         region.setRegionFile(zipFile);
         return zipFile;
      } else {
         return detectedFile == null ? layerFolder.resolve(region.getRegionX() + "_" + region.getRegionZ() + ".zip").toFile() : detectedFile;
      }
   }

   public static Path getRootFolder(String world) {
      return world == null ? null : WorldMap.saveFolder.toPath().resolve(world);
   }

   public Path getMainFolder(String world, String dim) {
      return world == null ? null : WorldMap.saveFolder.toPath().resolve(world).resolve(dim);
   }

   Path getMWSubFolder(String world, Path mainFolder, String mw) {
      if (world == null) {
         return null;
      } else {
         return mw == null ? mainFolder : mainFolder.resolve(mw);
      }
   }

   public Path getCaveLayerFolder(int caveLayer, Path subFolder) {
      Path layerFolder = subFolder;
      if (caveLayer != Integer.MAX_VALUE) {
         layerFolder = subFolder.resolve("caves").resolve(caveLayer + "");
      }

      return layerFolder;
   }

   public Path getMWSubFolder(String world, String dim, String mw) {
      return world == null ? null : this.getMWSubFolder(world, this.getMainFolder(world, dim), mw);
   }

   public Path getOldFolder(String oldUnfixedMainId, String dim) {
      return oldUnfixedMainId == null ? null : WorldMap.saveFolder.toPath().resolve(oldUnfixedMainId + "_" + dim);
   }

   private void xaeroToZip(File xaero) {
      File zipFile = xaero.toPath().getParent().resolve(xaero.getName().substring(0, xaero.getName().lastIndexOf(46)) + ".zip").toFile();

      try {
         BufferedInputStream in = new BufferedInputStream(new FileInputStream(xaero), 1024);
         ZipOutputStream zipOutput = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
         ZipEntry e = new ZipEntry("region.xaero");
         zipOutput.putNextEntry(e);
         byte[] bytes = new byte[1024];

         int got;
         while ((got = in.read(bytes)) > 0) {
            zipOutput.write(bytes, 0, got);
         }

         zipOutput.closeEntry();
         zipOutput.flush();
         zipOutput.close();
         in.close();
         Files.deleteIfExists(xaero.toPath());
      } catch (IOException e) {
         WorldMap.LOGGER.error("suppressed exception", e);
      }
   }

   public void detectRegions(int attempts) {
      MapDimension mapDimension = this.mapProcessor.getMapWorld().getCurrentDimension();
      mapDimension.preDetection();
      String worldId = this.mapProcessor.getCurrentWorldId();
      if (worldId != null && !this.mapProcessor.isCurrentMapLocked()) {
         String dimId = this.mapProcessor.getCurrentDimId();
         String mwId = this.mapProcessor.getCurrentMWId();
         boolean usingNormalMapData = !mapDimension.isUsingWorldSave();
         Path mapFolder = this.getMWSubFolder(worldId, dimId, mwId);
         boolean mapFolderExists = mapFolder.toFile().exists();
         String multiplayerMapRegex = "^(-?\\d+)_(-?\\d+)\\.(zip|xaero)$";
         MapLayer mainLayer = mapDimension.getLayeredMapRegions().getLayer(Integer.MAX_VALUE);
         if (usingNormalMapData) {
            if (mapFolderExists) {
               this.detectRegionsFromFiles(mapDimension, worldId, dimId, mwId, mapFolder, multiplayerMapRegex, 1, 2, 0, 20, mainLayer::addRegionDetection);
            }
         } else {
            Path worldDir = this.mapProcessor.getWorldDataHandler().getWorldDir();
            if (worldDir == null) {
               return;
            }

            Path worldFolder = worldDir.resolve("region");
            if (!worldFolder.toFile().exists()) {
               return;
            }

            this.detectRegionsFromFiles(
               mapDimension,
               worldId,
               dimId,
               mwId,
               worldFolder,
               "^r\\.(-{0,1}[0-9]+)\\.(-{0,1}[0-9]+)\\.mc[ar]$",
               1,
               2,
               8192,
               20,
               mapDimension::addWorldSaveRegionDetection
            );
         }

         if (mapFolderExists) {
            Path cavesFolder = mapFolder.resolve("caves");

            try {
               if (!Files.exists(cavesFolder)) {
                  Files.createDirectories(cavesFolder);
               }

               try (Stream<Path> cavesFolderStream = Files.list(cavesFolder)) {
                  cavesFolderStream.forEach(
                     layerFolder -> {
                        if (Files.isDirectory(layerFolder)) {
                           String folderName = layerFolder.getFileName().toString();

                           try {
                              int layerInt = Integer.parseInt(folderName);
                              MapLayer layer = mapDimension.getLayeredMapRegions().getLayer(layerInt);
                              if (usingNormalMapData) {
                                 this.detectRegionsFromFiles(
                                    mapDimension, worldId, dimId, mwId, layerFolder, multiplayerMapRegex, 1, 2, 0, 20, layer::addRegionDetection
                                 );
                              }
                           } catch (NumberFormatException var11x) {
                           }
                        }
                     }
                  );
               }
            } catch (IOException e) {
               WorldMap.LOGGER.error("IOException trying to detect map layers!");
               if (attempts > 1) {
                  WorldMap.LOGGER.error("Retrying... " + --attempts);

                  try {
                     Thread.sleep(30L);
                  } catch (InterruptedException var15) {
                  }

                  this.detectRegions(attempts);
                  return;
               }

               throw new RuntimeException("Couldn't detect map layers after multiple attempts.", e);
            }
         }
      }
   }

   public void detectRegionsFromFiles(
      MapDimension mapDimension,
      String worldId,
      String dimId,
      String mwId,
      Path folder,
      String regex,
      int xIndex,
      int zIndex,
      int emptySize,
      int attempts,
      Consumer<RegionDetection> detectionConsumer
   ) {
      int total = 0;
      Pattern fileRegexPattern = Pattern.compile(regex);
      long before = System.currentTimeMillis();

      try {
         Stream<Path> files = Files.list(folder);
         Iterator<Path> iter = files.iterator();
         int globalVersion = this.mapProcessor.getGlobalVersion();

         while (!this.mapProcessor.isFinalizing() && iter.hasNext()) {
            Path file = iter.next();
            String regionName = file.getFileName().toString();
            Matcher matcher = fileRegexPattern.matcher(regionName);
            if (matcher.matches()) {
               int x = Integer.parseInt(matcher.group(xIndex));
               int z = Integer.parseInt(matcher.group(zIndex));
               RegionDetection regionDetection = new RegionDetection(worldId, dimId, mwId, x, z, file.toFile(), globalVersion, true);
               detectionConsumer.accept(regionDetection);
               total++;
            }
         }

         files.close();
      } catch (IOException e) {
         WorldMap.LOGGER.error("IOException trying to detect map files!");
         if (attempts > 1) {
            WorldMap.LOGGER.error("Retrying... " + --attempts);

            try {
               Thread.sleep(30L);
            } catch (InterruptedException var25) {
            }

            this.detectRegionsFromFiles(mapDimension, worldId, dimId, mwId, folder, regex, xIndex, zIndex, emptySize, attempts, detectionConsumer);
            return;
         }

         throw new RuntimeException("Couldn't detect map files after multiple attempts.", e);
      }

      if (WorldMapClientConfigUtils.getDebug()) {
         WorldMap.LOGGER.info(String.format("%d regions detected in %d ms!", total, System.currentTimeMillis() - before));
      }
   }

   private boolean saveRegion(MapRegion region, boolean debugConfig, int extraAttempts) {
      try {
         if (!region.hasHadTerrain()) {
            if (debugConfig) {
               WorldMap.LOGGER.info("Save not required for highlight-only region: " + region + " " + region.getWorldId() + " " + region.getDimId());
            }

            return region.countChunks() > 0;
         } else if (!region.isResaving() && !region.isNormalMapData()) {
            if (debugConfig) {
               WorldMap.LOGGER.info("Save not required for world save map: " + region + " " + region.getWorldId() + " " + region.getDimId());
            }

            return region.countChunks() > 0;
         } else {
            File permFile = this.getNormalFile(region);
            if (!permFile.toPath().startsWith(WorldMap.saveFolder.toPath())) {
               throw new IllegalArgumentException();
            }

            File file = this.getTempFile(permFile);
            if (file == null) {
               return true;
            }

            if (!file.exists()) {
               file.createNewFile();
            }

            boolean hasAnything = false;
            boolean regionWasSavedEmpty = true;
            DataOutputStream out = null;

            try {
               ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
               out = new DataOutputStream(zipOut);
               ZipEntry e = new ZipEntry("region.xaero");
               zipOut.putNextEntry(e);
               int fullVersion = 393224;
               out.write(255);
               out.writeInt(fullVersion);
               this.regionSavePalette.clear();
               this.regionSaveBiomePalette.clear();
               Registry<Biome> biomeRegistry = region.getBiomeRegistry();

               for (int o = 0; o < 8; o++) {
                  for (int p = 0; p < 8; p++) {
                     MapTileChunk chunk = region.getChunk(o, p);
                     if (chunk != null) {
                        hasAnything = true;
                        if (!chunk.includeInSave()) {
                           if (!chunk.hasHighlightsIfUndiscovered()) {
                              region.uncountTextureBiomes(chunk.getLeafTexture());
                              region.setChunk(o, p, null);
                              synchronized (chunk) {
                                 chunk.getLeafTexture().deleteTexturesAndBuffers();
                              }
                           }

                           BranchLeveledRegion parentRegion = region.getParent();
                           if (parentRegion != null) {
                              parentRegion.setShouldCheckForUpdatesRecursive(true);
                           }
                        } else {
                           out.write(o << 4 | p);
                           boolean chunkIsEmpty = true;

                           for (int i = 0; i < 4; i++) {
                              for (int j = 0; j < 4; j++) {
                                 MapTile tile = chunk.getTile(i, j);
                                 if (tile != null && tile.isLoaded()) {
                                    chunkIsEmpty = false;

                                    for (int x = 0; x < 16; x++) {
                                       MapBlock[] c = tile.getBlockColumn(x);

                                       for (int z = 0; z < 16; z++) {
                                          this.savePixel(c[z], out, biomeRegistry);
                                       }
                                    }

                                    out.write(tile.getWorldInterpretationVersion());
                                    out.writeInt(tile.getWrittenCaveStart());
                                    out.write(tile.getWrittenCaveDepth());
                                 } else {
                                    out.writeInt(-1);
                                 }
                              }
                           }

                           if (!chunkIsEmpty) {
                              regionWasSavedEmpty = false;
                           }
                        }
                     }
                  }
               }

               zipOut.closeEntry();
            } finally {
               if (out != null) {
                  out.close();
               }
            }

            if (regionWasSavedEmpty) {
               this.safeDelete(permFile.toPath(), ".zip");
               this.safeDelete(file.toPath(), ".temp");
               if (debugConfig) {
                  WorldMap.LOGGER
                     .info(
                        "Save cancelled because the region would be saved empty: "
                           + region
                           + " "
                           + region.getWorldId()
                           + " "
                           + region.getDimId()
                           + " "
                           + region.getMwId()
                     );
               }

               return hasAnything;
            } else {
               this.safeMoveAndReplace(file.toPath(), permFile.toPath(), ".temp", ".zip");
               if (debugConfig) {
                  WorldMap.LOGGER
                     .info(
                        "Region saved: "
                           + region
                           + " "
                           + region.getWorldId()
                           + " "
                           + region.getDimId()
                           + " "
                           + region.getMwId()
                           + ", "
                           + this.mapProcessor.getMapWriter().getUpdateCounter()
                     );
               }

               return true;
            }
         }
      } catch (IOException ioe) {
         WorldMap.LOGGER.error("IO exception while trying to save " + region, ioe);
         if (extraAttempts > 0) {
            WorldMap.LOGGER.info("Retrying...");

            try {
               Thread.sleep(20L);
            } catch (InterruptedException var28) {
            }

            return this.saveRegion(region, debugConfig, extraAttempts - 1);
         } else {
            return true;
         }
      }
   }

   private Path getBackupFolder(Path filePath, int saveVersion, int backupVersion) {
      return filePath.getParent().resolve(saveVersion + "_backup_" + backupVersion);
   }

   public void backupFile(File file, int saveVersion) throws IOException {
      if (!file.getName().endsWith(".mca") && !file.getName().endsWith(".mcr")) {
         Path filePath = file.toPath();
         int backupVersion = 0;
         Path backupFolder = this.getBackupFolder(filePath, saveVersion, backupVersion);
         String backupName = filePath.getFileName().toString();

         Path backup;
         for (backup = backupFolder.resolve(backupName); Files.exists(backup); backup = backupFolder.resolve(backupName)) {
            backupFolder = this.getBackupFolder(filePath, saveVersion, ++backupVersion);
         }

         if (!Files.exists(backupFolder)) {
            Files.createDirectories(backupFolder);
         }

         Files.move(file.toPath(), backup);
         WorldMap.LOGGER.info("File " + file.getPath() + " backed up to " + backupFolder.toFile().getPath());
      } else {
         throw new RuntimeException("World save protected: " + file);
      }
   }

   public boolean loadRegion(
      MapRegion region,
      HolderLookup<Block> blockLookup,
      Registry<Block> blockRegistry,
      Registry<Fluid> fluidRegistry,
      BiomeGetter biomeGetter,
      boolean debugConfig,
      int extraAttempts
   ) {
      boolean multiplayer = region.isNormalMapData();
      int emptySize = multiplayer ? 0 : 8192;
      int minorSaveVersion = -1;
      int majorSaveVersion = 0;
      boolean versionReached = false;

      try {
         File file = this.getFile(region);
         if (region.hasHadTerrain() && file != null && file.exists() && Files.size(file.toPath()) > emptySize) {
            synchronized (region) {
               region.setLoadState((byte)1);
            }

            region.setSaveExists(true);
            region.restoreBufferUpdateObjects();
            int totalChunks = 0;
            if (multiplayer) {
               this.regionLoadPalette.clear();
               this.regionLoadBiomePalette.clear();
               DataInputStream in = null;

               try {
                  ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(file), 2048));
                  in = new DataInputStream(zipIn);
                  zipIn.getNextEntry();
                  int firstByte = in.read();
                  boolean is115not114 = false;
                  if (firstByte == 255) {
                     int fullVersion = in.readInt();
                     minorSaveVersion = fullVersion & 65535;
                     majorSaveVersion = fullVersion >> 16 & 65535;
                     if (majorSaveVersion == 2 && minorSaveVersion >= 5) {
                        is115not114 = in.read() == 1;
                     }

                     if (8 < minorSaveVersion || 6 < majorSaveVersion) {
                        zipIn.closeEntry();
                        in.close();
                        WorldMap.LOGGER.info("Trying to load a newer region " + region + " save using an older version of Xaero's World Map!");
                        this.backupFile(file, fullVersion);
                        region.setSaveExists(null);
                        return false;
                     }

                     firstByte = -1;
                  }

                  versionReached = true;
                  synchronized (region.getLevel() == 3 ? region : region.getParent()) {
                     synchronized (region) {
                        for (int o = 0; o < 8; o++) {
                           for (int p = 0; p < 8; p++) {
                              MapTileChunk chunk = region.getChunk(o, p);
                              if (chunk != null) {
                                 chunk.setLoadState((byte)1);
                              }
                           }
                        }
                     }
                  }

                  Registry<Biome> biomeRegistry = region.getBiomeRegistry();

                  while (true) {
                     int chunkCoords = firstByte == -1 ? in.read() : firstByte;
                     if (chunkCoords == -1) {
                        zipIn.closeEntry();
                        break;
                     }

                     firstByte = -1;
                     int o = chunkCoords >> 4;
                     int p = chunkCoords & 15;
                     MapTileChunk chunk = region.getChunk(o, p);
                     if (chunk == null) {
                        region.setChunk(o, p, chunk = new MapTileChunk(region, region.getRegionX() * 8 + o, region.getRegionZ() * 8 + p));
                     } else if (chunk.getLoadState() >= 2) {
                        throw new Exception("Map data for region " + region + " is probably corrupt! Has the same map tile chunk saved twice.");
                     }

                     if (region.isMetaLoaded()) {
                        chunk.getLeafTexture().setBufferedTextureVersion(region.getAndResetCachedTextureVersion(o, p));
                     }

                     chunk.resetHeights();

                     for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                           Integer nextTile = in.readInt();
                           if (nextTile != -1) {
                              MapTile tile = this.mapProcessor
                                 .getTilePool()
                                 .get(this.mapProcessor.getCurrentDimension(), chunk.getX() * 4 + i, chunk.getZ() * 4 + j);

                              for (int x = 0; x < 16; x++) {
                                 MapBlock[] c = tile.getBlockColumn(x);

                                 for (int z = 0; z < 16; z++) {
                                    if (c[z] == null) {
                                       c[z] = new MapBlock();
                                    } else {
                                       c[z].prepareForWriting(0);
                                    }

                                    this.loadPixel(nextTile, c[z], in, minorSaveVersion, majorSaveVersion, is115not114, blockLookup, biomeGetter, biomeRegistry);
                                    nextTile = null;
                                 }
                              }

                              if (minorSaveVersion >= 4) {
                                 tile.setWorldInterpretationVersion(in.read());
                              }

                              if (minorSaveVersion >= 6) {
                                 tile.setWrittenCave(in.readInt(), minorSaveVersion >= 7 ? in.read() : 32);
                              }

                              chunk.setTile(i, j, tile, this.blockStateShortShapeCache);
                              tile.setLoaded(true);
                           }
                        }
                     }

                     if (!chunk.includeInSave()) {
                        if (!chunk.hasHighlightsIfUndiscovered()) {
                           region.uncountTextureBiomes(chunk.getLeafTexture());
                           region.setChunk(o, p, null);
                           chunk.getLeafTexture().deleteTexturesAndBuffers();
                           MapTileChunk var59 = null;
                        }
                     } else {
                        region.pushWriterPause();
                        totalChunks++;
                        chunk.setToUpdateBuffers(true);
                        chunk.setLoadState((byte)2);
                        region.popWriterPause();
                     }
                  }
               } finally {
                  if (in != null) {
                     in.close();
                  }
               }

               if (totalChunks > 0) {
                  if (debugConfig) {
                     WorldMap.LOGGER
                        .info(
                           "Region loaded: "
                              + region
                              + " "
                              + region.getWorldId()
                              + " "
                              + region.getDimId()
                              + " "
                              + region.getMwId()
                              + ", "
                              + majorSaveVersion
                              + " "
                              + minorSaveVersion
                        );
                  }

                  return true;
               } else {
                  region.setSaveExists(null);
                  this.safeDelete(file.toPath(), ".zip");
                  if (debugConfig) {
                     WorldMap.LOGGER
                        .info(
                           "Cancelled loading an empty region: "
                              + region
                              + " "
                              + region.getWorldId()
                              + " "
                              + region.getDimId()
                              + " "
                              + region.getMwId()
                              + ", "
                              + majorSaveVersion
                              + " "
                              + minorSaveVersion
                        );
                  }

                  return false;
               }
            } else {
               int[] chunkCount = new int[1];
               WorldDataHandler.Result buildResult = this.mapProcessor
                  .getWorldDataHandler()
                  .buildRegion(region, blockLookup, blockRegistry, fluidRegistry, true, chunkCount);
               if (buildResult == WorldDataHandler.Result.CANCEL) {
                  if (region.hasHadTerrain()) {
                     RegionDetection restoredDetection = new RegionDetection(
                        region.getWorldId(),
                        region.getDimId(),
                        region.getMwId(),
                        region.getRegionX(),
                        region.getRegionZ(),
                        region.getRegionFile(),
                        this.mapProcessor.getGlobalVersion(),
                        true
                     );
                     restoredDetection.transferInfoFrom(region);
                     region.getDim().getLayeredMapRegions().getLayer(region.getCaveLayer()).addRegionDetection(restoredDetection);
                  }

                  this.mapProcessor.removeMapRegion(region);
                  WorldMap.LOGGER
                     .info("Region cancelled from world save: " + region + " " + region.getWorldId() + " " + region.getDimId() + " " + region.getMwId());
                  return false;
               } else {
                  region.setRegionFile(file);
                  boolean result = buildResult == WorldDataHandler.Result.SUCCESS && chunkCount[0] > 0;
                  if (!result) {
                     region.setSaveExists(null);
                     if (debugConfig) {
                        WorldMap.LOGGER
                           .info(
                              "Region failed to load from world save: " + region + " " + region.getWorldId() + " " + region.getDimId() + " " + region.getMwId()
                           );
                     }
                  } else if (debugConfig) {
                     WorldMap.LOGGER
                        .info("Region loaded from world save: " + region + " " + region.getWorldId() + " " + region.getDimId() + " " + region.getMwId());
                  }

                  return result;
               }
            }
         } else {
            if (region.getLoadState() == 4 || region.hasHadTerrain()) {
               region.setSaveExists(null);
            }

            if (region.hasHadTerrain()) {
               return false;
            }

            synchronized (region) {
               region.setLoadState((byte)1);
            }

            region.restoreBufferUpdateObjects();
            if (debugConfig) {
               WorldMap.LOGGER.info("Highlight region fake-loaded: " + region + " " + region.getWorldId() + " " + region.getDimId() + " " + region.getMwId());
            }

            return true;
         }
      } catch (IOException ioe) {
         WorldMap.LOGGER.error("IO exception while trying to load " + region, ioe);
         if (extraAttempts > 0) {
            synchronized (region) {
               region.setLoadState((byte)4);
            }

            WorldMap.LOGGER.info("Retrying...");

            try {
               Thread.sleep(20L);
            } catch (InterruptedException var42) {
            }

            return this.loadRegion(region, blockLookup, blockRegistry, fluidRegistry, biomeGetter, debugConfig, extraAttempts - 1);
         } else {
            region.setSaveExists(null);
            return false;
         }
      } catch (Throwable e) {
         region.setSaveExists(null);
         WorldMap.LOGGER.error("Region failed to load: " + region + (versionReached ? " " + majorSaveVersion + " " + minorSaveVersion : ""), e);
         return false;
      }
   }

   public boolean beingSaved(MapDimension dim, int regX, int regZ) {
      for (int i = 0; i < this.toSave.size(); i++) {
         MapRegion r = this.toSave.get(i);
         if (r != null && r.getDim() == dim && r.getRegionX() == regX && r.getRegionZ() == regZ) {
            return true;
         }
      }

      return false;
   }

   public void requestLoad(MapRegion region, String reason) {
      this.requestLoad(region, reason, true);
   }

   public void requestLoad(MapRegion region, String reason, boolean prioritize) {
      this.addToLoad(region, reason, prioritize);
   }

   public void requestBranchCache(BranchLeveledRegion region, String reason) {
      this.requestBranchCache(region, reason, true);
      if (reason != null) {
         if (WorldMapClientConfigUtils.getDebug()) {
            WorldMap.LOGGER.info("Requesting branch load for: " + region + ", " + reason);
         }
      }
   }

   public void requestBranchCache(BranchLeveledRegion region, String reason, boolean prioritize) {
      synchronized (this.toLoadBranchCache) {
         if (prioritize) {
            this.toLoadBranchCache.remove(region);
            this.toLoadBranchCache.add(0, region);
         } else if (!this.toLoadBranchCache.contains(region)) {
            this.toLoadBranchCache.add(region);
         }
      }
   }

   public void addToLoad(MapRegion region, String reason, boolean prioritize) {
      synchronized (this.toLoad) {
         if (prioritize) {
            region.setReloadHasBeenRequested(true, reason);
            this.toLoad.remove(region);
            this.toLoad.add(0, region);
            if (WorldMapClientConfigUtils.getDebug() && reason != null) {
               WorldMap.LOGGER
                  .info("Requesting load for: " + region + " " + region.getWorldId() + " " + region.getDimId() + " " + region.getMwId() + ", " + reason);
            }
         } else if (!this.loadingFiles && !this.toLoad.contains(region)) {
            region.setReloadHasBeenRequested(true, reason);
            this.toLoad.add(region);
            if (WorldMapClientConfigUtils.getDebug() && reason != null) {
               WorldMap.LOGGER
                  .info("Requesting load for: " + region + " " + region.getWorldId() + " " + region.getDimId() + " " + region.getMwId() + ", " + reason);
            }
         }
      }

      this.mapProcessor.getMapRegionHighlightsPreparer().prepare(region, false);
   }

   public void removeToLoad(MapRegion region) {
      synchronized (this.toLoad) {
         this.toLoad.remove(region);
      }
   }

   public void clearToLoad() {
      synchronized (this.toLoad) {
         this.toLoad.clear();
      }

      synchronized (this.toLoadBranchCache) {
         this.toLoadBranchCache.clear();
      }
   }

   public int getSizeOfToLoad() {
      return this.toLoad.size();
   }

   public boolean saveExists(MapRegion region) {
      if (region.getSaveExists() != null) {
         return region.getSaveExists();
      }

      boolean result = true;
      File file = this.getFile(region);
      if (file == null || !file.exists()) {
         result = false;
      }

      region.setSaveExists(result);
      return result;
   }

   public void updateSave(LeveledRegion<?> leveledRegion, long currentTime, int currentLayer) {
      if (leveledRegion.getLevel() == 0) {
         MapRegion region = (MapRegion)leveledRegion;
         int saveTime = 60000;
         if (region.getCaveLayer() != currentLayer) {
            saveTime /= 100;
         }

         if (region.getLoadState() == 2
            && region.isBeingWritten()
            && currentTime - region.getLastSaveTime() >= saveTime
            && !this.beingSaved(region.getDim(), region.getRegionX(), region.getRegionZ())) {
            this.toSave.add(region);
            region.setSaveExists(true);
            region.setLastSaveTime(currentTime);
         }
      } else {
         BranchLeveledRegion region = (BranchLeveledRegion)leveledRegion;
         if (region.eligibleForSaving(currentTime)) {
            region.startDownloadingTexturesForCache(this.mapProcessor);
         }
      }
   }

   public void run(
      HolderLookup<Block> blockLookup, Registry<Block> blockRegistry, Registry<Fluid> fluidRegistry, BiomeGetter biomeGetter, Registry<Biome> biomeRegistry
   ) throws Exception {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      boolean debugConfig = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DEBUG);
      int globalVersion = this.mapProcessor.getGlobalVersion();
      if (!this.toLoad.isEmpty()) {
         boolean loaded = false;
         this.mapProcessor.pushIsLoading();
         this.loadingFiles = true;
         boolean reloadEverything = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.RELOAD_VIEWED);
         int limit = this.toLoad.size();

         while (limit > 0 && !this.mapProcessor.isWaitingForWorldUpdate() && !loaded && !this.toLoad.isEmpty()) {
            limit--;
            MapRegion region;
            synchronized (this.toLoad) {
               if (this.toLoad.isEmpty()) {
                  break;
               }

               region = this.toLoad.get(0);
            }

            if (region.hasHadTerrain() && region.getCacheFile() == null && !region.hasLookedForCache()) {
               File potentialCacheFile = this.getCacheFile(region, region.getCaveLayer(), true, true);
               if (potentialCacheFile.exists()) {
                  region.setCacheFile(potentialCacheFile);
               }

               region.setLookedForCache(true);
            }

            int globalRegionCacheHashCode = WorldMap.settings.getRegionCacheHashCode();
            int globalReloadVersion = (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.RELOAD_VIEWED_VERSION);
            int globalCaveDepth = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.CAVE_MODE_DEPTH);
            boolean needsLoading;
            synchronized (region) {
               needsLoading = region.getLoadState() == 0 || region.getLoadState() == 4;
               if (needsLoading) {
                  if (region.hasVersion() && region.getVersion() != globalVersion
                     || !region.hasVersion() && region.getInitialVersion() != globalVersion
                     || region.getLoadState() == 4 && reloadEverything && region.getReloadVersion() != globalReloadVersion
                     || (region.getLoadState() == 4 || region.isMetaLoaded() && this.mainTextureLevel != region.getLevel())
                        && (globalRegionCacheHashCode != region.getCacheHashCode() || region.caveStartOutdated(region.getUpToDateCaveStart(), globalCaveDepth))
                     || region.getDim().getFullReloader() != null && region.getDim().getFullReloader().isPartOfReload(region)) {
                     region.setShouldCache(true, "loading");
                  }

                  region.setVersion(globalVersion);
               }
            }

            if (needsLoading) {
               synchronized (region) {
                  region.setAllCachePrepared(false);
               }

               boolean cacheOnlyMode = region.getDim().getMapWorld().isCacheOnlyMode();
               boolean fromNothing = region.getLoadState() == 0;
               boolean hasSomething = false;
               boolean justMetaData = false;
               boolean[] leafShouldAffectBranchesDest = new boolean[1];
               int targetHighlightsHash = region.getTargetHighlightsHash();
               boolean[] metaLoadedDest = new boolean[1];
               boolean[][] textureLoaded = null;
               if (cacheOnlyMode
                  || region.getLoadState() == 0 && (!region.shouldCache() || !region.isMetaLoaded() || this.mainTextureLevel == region.getLevel())
                  || !region.shouldCache() && region.getLoadState() == 4) {
                  textureLoaded = new boolean[8][8];
                  justMetaData = region.loadCacheTextures(
                     this.mapProcessor,
                     biomeRegistry,
                     !region.isMetaLoaded() && this.mainTextureLevel != region.getLevel(),
                     textureLoaded,
                     targetHighlightsHash,
                     leafShouldAffectBranchesDest,
                     metaLoadedDest,
                     10,
                     this.oldFormatSupport
                  );
               }

               if (justMetaData) {
                  hasSomething = this.cleanupLoadedCache(region, textureLoaded, justMetaData, hasSomething, targetHighlightsHash, metaLoadedDest[0]);
                  if (debugConfig) {
                     WorldMap.LOGGER.info("Loaded meta data for " + region);
                  }
               } else {
                  region.setHighlightsHash(targetHighlightsHash);
                  boolean shouldAddToLoaded = region.getLoadState() == 0;
                  boolean shouldLoadProperly;
                  synchronized (region) {
                     boolean goingToPrepareCache = region.shouldCache()
                        && (
                           region.isMetaLoaded() && this.mainTextureLevel != region.getLevel()
                              || region.getLoadState() == 4
                              || region.getCacheFile() == null
                              || !region.getCacheFile().exists()
                        );
                     if (!goingToPrepareCache) {
                        goingToPrepareCache = region.getDim().getFullReloader() != null && region.getDim().getFullReloader().isPartOfReload(region);
                     }

                     shouldLoadProperly = region.getLoadState() == 4 && region.isBeingWritten() || goingToPrepareCache;
                     if (cacheOnlyMode) {
                        shouldLoadProperly = false;
                     }

                     if (!shouldLoadProperly) {
                        if (leafShouldAffectBranchesDest[0]) {
                           region.setRecacheHasBeenRequested(true, "cache affects branches");
                           region.setShouldCache(true, "cache affects branches");
                        }

                        region.setLoadState((byte)3);
                     } else if (region.shouldCache()) {
                        region.setRecacheHasBeenRequested(true, "loading");
                     }
                  }

                  if (!shouldLoadProperly && textureLoaded != null) {
                     hasSomething = this.cleanupLoadedCache(region, textureLoaded, justMetaData, hasSomething, targetHighlightsHash, metaLoadedDest[0]);
                  }

                  this.mapProcessor.addToProcess(region);
                  if (shouldAddToLoaded) {
                     this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().addLoadedRegion(region);
                  }

                  if (shouldLoadProperly) {
                     region.setCacheHashCode(globalRegionCacheHashCode);
                     region.setReloadVersion(globalReloadVersion);
                     loaded = this.loadRegion(region, blockLookup, blockRegistry, fluidRegistry, biomeGetter, debugConfig, 10);
                     hasSomething = false;
                     if (!loaded) {
                        region.setShouldCache(false, "couldn't load");
                        region.setRecacheHasBeenRequested(false, "couldn't load");
                        if (region.getSaveExists() == null) {
                           synchronized (region) {
                              region.setLoadState((byte)4);
                           }

                           region.deleteTexturesAndBuffers();
                           this.mapProcessor.removeMapRegion(region);
                        }
                     } else {
                        for (int i = 0; i < 8; i++) {
                           for (int j = 0; j < 8; j++) {
                              MapTileChunk mapTileChunk = region.getChunk(i, j);
                              if (mapTileChunk != null) {
                                 if (!mapTileChunk.includeInSave()) {
                                    region.uncountTextureBiomes(mapTileChunk.getLeafTexture());
                                    mapTileChunk.getLeafTexture().resetBiomes();
                                    if (!mapTileChunk.hasHighlightsIfUndiscovered()) {
                                       region.setChunk(i, j, null);
                                       mapTileChunk.getLeafTexture().deleteTexturesAndBuffers();
                                    } else {
                                       mapTileChunk.setLoadState((byte)2);
                                       mapTileChunk.unsetHasHadTerrain();
                                       mapTileChunk.getLeafTexture().requestHighlightOnlyUpload();
                                       hasSomething = true;
                                       synchronized (region) {
                                          region.updateLeafTextureVersion(i, j, targetHighlightsHash);
                                       }
                                    }
                                 } else {
                                    hasSomething = true;
                                 }
                              } else if (region.leafTextureVersionSum[i][j] != 0) {
                                 synchronized (region) {
                                    region.updateLeafTextureVersion(i, j, 0);
                                 }
                              }
                           }
                        }

                        if (!hasSomething) {
                           synchronized (region) {
                              if (!region.isBeingWritten() && region.getLoadState() <= 1) {
                                 region.setLoadState((byte)3);
                              }
                           }

                           loaded = false;
                        }
                     }

                     synchronized (region) {
                        if (region.getLoadState() <= 1) {
                           region.setLoadState((byte)2);
                        }

                        region.setLastSaveTime(region.isResaving() ? -60000L : System.currentTimeMillis());
                     }

                     BranchLeveledRegion parentRegion = region.getParent();
                     if (parentRegion != null) {
                        parentRegion.setShouldCheckForUpdatesRecursive(true);
                     }
                  } else if (debugConfig) {
                     WorldMap.LOGGER.info("Loaded from cache only for " + region);
                  }

                  region.loadingNeededForBranchLevel = 0;
               }

               if (fromNothing && !hasSomething) {
                  BranchLeveledRegion parentRegion = region.getParent();
                  if (parentRegion != null) {
                     parentRegion.setShouldCheckForUpdatesRecursive(true);
                  }
               }
            }

            region.setReloadHasBeenRequested(false, "loading");
            this.removeToLoad(region);
         }

         this.loadingFiles = false;
         this.mapProcessor.popIsLoading();
      }

      int regionsToSave = 3;

      while (!this.toSave.isEmpty() && (this.saveAll || regionsToSave > 0)) {
         MapRegion region = this.toSave.get(0);
         boolean regionLoaded;
         synchronized (region) {
            regionLoaded = region.getLoadState() == 2;
         }

         if (regionLoaded) {
            if (!region.isBeingWritten()) {
               throw new Exception("Saving a weird region: " + region);
            }

            region.pushWriterPause();
            boolean notEmpty = this.saveRegion(region, debugConfig, 20);
            region.setResaving(false);
            if (notEmpty) {
               if (!region.isAllCachePrepared()) {
                  synchronized (region) {
                     if (!region.isAllCachePrepared()) {
                        region.requestRefresh(this.mapProcessor, false);
                     }
                  }
               }

               region.setRecacheHasBeenRequested(true, "saving");
               region.setShouldCache(true, "saving");
               region.setBeingWritten(false);
               regionsToSave--;
            } else {
               this.mapProcessor.removeMapRegion(region);
            }

            region.popWriterPause();
            if (region.getWorldId() == null || !this.mapProcessor.isEqual(region.getWorldId(), region.getDimId(), region.getMwId())) {
               if (region.getCacheFile() != null) {
                  region.convertCacheToOutdated(this, "is outdated");
                  if (debugConfig) {
                     WorldMap.LOGGER.info(String.format("Converting cache for region %s because it IS outdated.", region));
                  }
               }

               region.clearRegion(this.mapProcessor);
            }
         } else if (debugConfig) {
            WorldMap.LOGGER
               .info(
                  "Tried to save a weird region: "
                     + region
                     + " "
                     + region.getWorldId()
                     + " "
                     + region.getDimId()
                     + " "
                     + region.getMwId()
                     + " "
                     + region.getLoadState()
               );
         }

         this.toSave.remove(region);
      }

      this.saveAll = false;
      if (!this.toLoadBranchCache.isEmpty()) {
         int limit = this.toLoadBranchCache.size();
         this.mapProcessor.pushIsLoading();
         if (!this.mapProcessor.isWaitingForWorldUpdate()) {
            while (limit > 0) {
               limit--;
               BranchLeveledRegion region;
               synchronized (this.toLoadBranchCache) {
                  if (this.toLoadBranchCache.isEmpty()) {
                     break;
                  }

                  region = this.toLoadBranchCache.get(0);
               }

               region.preCacheLoad();
               LayeredRegionManager regionManager = this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions();
               regionManager.addLoadedRegion(region);
               region.setCacheFile(region.findCacheFile(this));
               boolean[] metaLoadedDest = new boolean[1];
               boolean[][] textureLoaded = new boolean[8][8];
               region.loadCacheTextures(this.mapProcessor, biomeRegistry, false, textureLoaded, 0, null, metaLoadedDest, 10, this.oldFormatSupport);
               if (metaLoadedDest[0]) {
                  region.confirmMetaLoaded();
               }

               this.mapProcessor.addToProcess(region);
               if (region.getCacheFile() == null) {
                  region.setShouldCheckForUpdatesRecursive(true);
               } else {
                  region.setShouldCheckForUpdatesSingle(true);
               }

               region.setShouldCache(false, "branch loading");
               region.setLoaded(true);
               if (debugConfig) {
                  WorldMap.LOGGER.info("Loaded cache for branch region " + region);
               }

               region.setReloadHasBeenRequested(false, "loading");
               synchronized (this.toLoadBranchCache) {
                  this.toLoadBranchCache.remove(region);
               }
            }
         }

         this.mapProcessor.popIsLoading();
      }

      if (this.mapProcessor.getMapWorld().getCurrentDimensionId() != null) {
         this.workingDimList.clear();
         this.mapProcessor.getMapWorld().getDimensions(this.workingDimList);

         for (int d = 0; d < this.workingDimList.size(); d++) {
            MapDimension dim = this.workingDimList.get(d);

            while (!dim.regionsToCache.isEmpty()) {
               LeveledRegion<?> region = this.removeToCache(dim, 0);
               region.preCache();
               boolean skipCaching = region.skipCaching(globalVersion);
               if (region.shouldCache() && region.recacheHasBeenRequested() && !skipCaching) {
                  if (!region.isAllCachePrepared()) {
                     throw new RuntimeException("Trying to save cache for a region with cache not prepared: " + region + " " + region.getExtraInfo());
                  }

                  if (region.getCacheFile() != null) {
                     this.removeTempCacheRequest(region.getCacheFile());
                  }

                  File permFile = region.findCacheFile(this);
                  File tempFile = this.getSecondaryFile(".xwmc.temp", permFile);
                  boolean successfullySaved = region.saveCacheTextures(tempFile, debugConfig, 10);
                  if (successfullySaved) {
                     this.cacheToConvertFromTemp.add(permFile);
                     region.setCacheFile(permFile);
                  }

                  region.setShouldCache(false, "toCache normal");
                  region.setRecacheHasBeenRequested(false, "toCache normal");
                  region.postCache(permFile, this, successfullySaved);
               } else {
                  if (WorldMap.detailed_debug) {
                     WorldMap.LOGGER
                        .info(
                           "toCache cancel: "
                              + region
                              + " "
                              + !region.shouldCache()
                              + " "
                              + !region.recacheHasBeenRequested()
                              + " "
                              + !region.isAllCachePrepared()
                              + " "
                              + skipCaching
                              + " "
                              + globalVersion
                        );
                  }

                  if (region.shouldCache()) {
                     region.deleteBuffers();
                  }

                  region.setShouldCache(false, "toCache cancel");
                  region.setRecacheHasBeenRequested(false, "toCache cancel");
                  region.postCache(null, this, false);
               }
            }
         }
      }

      for (int i = 0; i < this.cacheToConvertFromTemp.size(); i++) {
         File permFile = this.cacheToConvertFromTemp.get(i);
         File tempFile = this.getSecondaryFile(".xwmc.temp", permFile);

         try {
            if (Files.exists(tempFile.toPath())) {
               IOUtils.safeMoveAndReplace(tempFile.toPath(), permFile.toPath(), true);
            }

            this.cacheToConvertFromTemp.remove(i);
            i--;
         } catch (IOException var40) {
         }
      }
   }

   public boolean removeTempCacheRequest(File file) {
      boolean result = false;

      while (this.cacheToConvertFromTemp.remove(file)) {
         result = true;
      }

      return result;
   }

   public void addTempCacheRequest(File file) {
      this.cacheToConvertFromTemp.add(file);
   }

   private boolean cleanupLoadedCache(
      MapRegion region, boolean[][] textureLoaded, boolean justMetaData, boolean hasSomething, int targetHighlightsHash, boolean metaLoaded
   ) {
      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            boolean loaded = textureLoaded[i][j];
            if (!justMetaData && loaded) {
               hasSomething = true;
            } else {
               MapTileChunk mapTileChunk = region.getChunk(i, j);
               if (mapTileChunk != null) {
                  if (!justMetaData && mapTileChunk.hasHighlightsIfUndiscovered()) {
                     mapTileChunk.getLeafTexture().requestHighlightOnlyUpload();
                     hasSomething = true;
                  } else {
                     region.setChunk(i, j, null);
                     if (!justMetaData) {
                        mapTileChunk.getLeafTexture().deleteTexturesAndBuffers();
                     }
                  }

                  if (!loaded && mapTileChunk.hasHighlightsIfUndiscovered()) {
                     region.updateLeafTextureVersion(i, j, targetHighlightsHash);
                  }
               } else if (!loaded && region.leafTextureVersionSum[i][j] != 0) {
                  region.updateLeafTextureVersion(i, j, 0);
               }
            }
         }
      }

      if (metaLoaded) {
         region.confirmMetaLoaded();
      }

      return hasSomething;
   }

   private void savePixel(MapBlock pixel, DataOutputStream out, Registry<Biome> biomeRegistry) throws IOException {
      boolean isGrass = pixel.isGrass();
      boolean inPalette = false;
      boolean biomeInPalette = false;
      BlockState state = null;
      int parametres = pixel.getParametres();
      if (!isGrass) {
         state = pixel.getState();
         inPalette = this.regionSavePalette.containsKey(state);
         if (!inPalette) {
            parametres |= 2097152;
         }
      }

      ResourceKey<Biome> pixelBiome = pixel.getBiome();
      String pixelBiomeString = null;
      if (pixelBiome != null) {
         biomeInPalette = this.regionSaveBiomePalette.containsKey(pixelBiome);
         if (!biomeInPalette) {
            parametres |= 4194304;
            ResourceLocation biomeIdentifier = pixelBiome.location();
            pixelBiomeString = biomeIdentifier == null ? null : biomeIdentifier.toString();
            if (pixelBiomeString == null) {
               pixelBiomeString = Biomes.PLAINS.location().toString();
            }
         }
      }

      out.writeInt(parametres);
      if (!isGrass) {
         if (inPalette) {
            out.writeInt(this.regionSavePalette.get(state));
         } else {
            if (state instanceof UnknownBlockState) {
               ((UnknownBlockState)state).write(out);
            } else {
               NbtIo.write(NbtUtils.writeBlockState(state), out);
            }

            this.regionSavePalette.put(state, this.regionSavePalette.size());
         }
      }

      if ((parametres & 16777216) != 0) {
         out.write(pixel.getTopHeight());
      }

      if (pixel.getNumberOfOverlays() != 0) {
         out.write(pixel.getOverlays().size());

         for (int i = 0; i < pixel.getOverlays().size(); i++) {
            this.saveOverlay(pixel.getOverlays().get(i), out);
         }
      }

      if (pixelBiome != null) {
         if (biomeInPalette) {
            out.writeInt(this.regionSaveBiomePalette.get(pixelBiome));
         } else {
            out.writeUTF(pixelBiomeString);
            this.regionSaveBiomePalette.put(pixelBiome, this.regionSaveBiomePalette.size());
         }
      }
   }

   private void loadPixel(
      Integer next,
      MapBlock pixel,
      DataInputStream in,
      int minorSaveVersion,
      int majorSaveVersion,
      boolean is115not114,
      HolderLookup<Block> blockLookup,
      BiomeGetter biomeGetter,
      Registry<Biome> biomeRegistry
   ) throws IOException {
      int parametres;
      if (next != null) {
         parametres = next;
      } else {
         parametres = in.readInt();
      }

      if ((parametres & 1) != 0) {
         if (majorSaveVersion == 0) {
            int state = in.readInt();
            pixel.setState(this.oldFormatSupport.getStateForId(state));
         } else {
            boolean paletteNew = (parametres & 2097152) != 0;
            BlockState state;
            if (paletteNew) {
               CompoundTag nbt = NbtIo.read(in);
               if (majorSaveVersion < 6) {
                  this.oldFormatSupport.fixBlock(nbt, majorSaveVersion);
               }

               state = WorldMap.unknownBlockStateCache.getBlockStateFromNBT(blockLookup, nbt);
               this.regionLoadPalette.add(state);
            } else {
               int paletteIndex = in.readInt();
               state = this.regionLoadPalette.get(paletteIndex);
            }

            pixel.setState(state);
         }
      } else {
         pixel.setState(Blocks.GRASS_BLOCK.defaultBlockState());
      }

      if ((parametres & 64) != 0) {
         pixel.setHeight(in.read());
      } else {
         int heightSecondPartOffset = minorSaveVersion >= 4 ? 25 : 24;
         int heightBitsCombined = parametres >> 12 & 0xFF | (parametres >> heightSecondPartOffset & 15) << 8;
         int signedHeight12Bit = heightBitsCombined << 20 >> 20;
         pixel.setHeight(signedHeight12Bit);
      }

      boolean topHeightIsDifferent = minorSaveVersion < 4 ? false : (parametres & 16777216) != 0;
      if (topHeightIsDifferent) {
         pixel.setTopHeight(in.read());
      } else {
         pixel.setTopHeight(pixel.getHeight());
      }

      boolean stillUsesColorTypes = minorSaveVersion < 5 || majorSaveVersion <= 2 && !is115not114;
      this.overlayBuilder.startBuilding();
      if ((parametres & 2) != 0) {
         int amount = in.read();

         for (int i = 0; i < amount; i++) {
            this.loadOverlay(pixel, in, minorSaveVersion, majorSaveVersion, stillUsesColorTypes, blockLookup, biomeGetter);
         }
      }

      this.overlayBuilder.finishBuilding(pixel);
      int savedColourType = stillUsesColorTypes ? parametres >> 2 & 3 : 0;
      if (savedColourType == 3) {
         in.readInt();
      }

      ResourceKey<Biome> biomeKey = null;
      if (savedColourType != 0 && savedColourType != 3 || (parametres & 1048576) != 0) {
         if (majorSaveVersion < 4) {
            int biomeByte = in.read();
            int oldBiomeByte;
            if (minorSaveVersion >= 3 && biomeByte >= 255) {
               oldBiomeByte = in.readInt();
            } else {
               oldBiomeByte = biomeByte;
            }

            String biomeStringId = this.oldFormatSupport.fixBiome(oldBiomeByte, majorSaveVersion);
            biomeKey = ResourceKey.create(Registries.BIOME, ResourceLocation.parse(biomeStringId));
         } else {
            boolean paletteNew = (parametres & 4194304) != 0;
            if (paletteNew) {
               boolean biomeAsInt = (parametres & 8388608) != 0;
               String biomeIdentifier;
               if (biomeAsInt) {
                  int biomeId = in.readInt();
                  biomeIdentifier = this.oldFormatSupport.fixBiome(biomeId, majorSaveVersion);
               } else {
                  biomeIdentifier = this.oldFormatSupport.fixBiome(in.readUTF(), majorSaveVersion);
               }

               biomeKey = ResourceKey.create(Registries.BIOME, ResourceLocation.parse(biomeIdentifier));
               this.regionLoadBiomePalette.add(biomeKey);
            } else {
               int paletteIndex = in.readInt();
               biomeKey = this.regionLoadBiomePalette.get(paletteIndex);
            }
         }
      }

      pixel.setBiome(biomeKey);
      if (minorSaveVersion == 2) {
         boolean hasSlope = (parametres & 16) != 0;
         if (hasSlope) {
            pixel.setVerticalSlope((byte)in.read());
            pixel.setSlopeUnknown(false);
         }
      }

      pixel.setLight((byte)(parametres >> 8 & 15));
      pixel.setGlowing(this.mapProcessor.getMapWriter().isGlowing(pixel.getState()));
   }

   private void saveOverlay(Overlay o, DataOutputStream out) throws IOException {
      boolean isWater = o.isWater();
      boolean inPalette = false;
      BlockState state = null;
      int parametres = o.getParametres();
      if (!isWater) {
         state = o.getState();
         inPalette = this.regionSavePalette.containsKey(state);
         if (!inPalette) {
            parametres |= 1024;
         }
      }

      out.writeInt(parametres);
      if (!isWater) {
         if (inPalette) {
            out.writeInt(this.regionSavePalette.get(state));
         } else {
            if (state instanceof UnknownBlockState) {
               ((UnknownBlockState)state).write(out);
            } else {
               NbtIo.write(NbtUtils.writeBlockState(state), out);
            }

            this.regionSavePalette.put(state, this.regionSavePalette.size());
         }
      }
   }

   private void loadOverlay(
      MapBlock pixel,
      DataInputStream in,
      int minorSaveVersion,
      int majorSaveVersion,
      boolean stillUsesColorTypes,
      HolderLookup<Block> blockLookup,
      BiomeGetter biomeGetter
   ) throws IOException {
      int parametres = in.readInt();
      BlockState state;
      if ((parametres & 1) != 0) {
         if (majorSaveVersion == 0) {
            state = this.oldFormatSupport.getStateForId(in.readInt());
         } else {
            boolean paletteNew = (parametres & 1024) != 0;
            if (paletteNew) {
               CompoundTag nbt = NbtIo.read(in);
               state = WorldMap.unknownBlockStateCache.getBlockStateFromNBT(blockLookup, nbt);
               this.regionLoadPalette.add(state);
            } else {
               int paletteIndex = in.readInt();
               state = this.regionLoadPalette.get(paletteIndex);
            }
         }
      } else {
         state = Blocks.WATER.defaultBlockState();
      }

      int opacity = 1;
      if (minorSaveVersion < 1 && (parametres & 2) != 0) {
         in.readInt();
      }

      byte savedColourType = stillUsesColorTypes ? (byte)(parametres >> 8 & 3) : 0;
      if (savedColourType == 2 || (parametres & 4) != 0) {
         in.readInt();
      }

      if (minorSaveVersion < 8) {
         if ((parametres & 8) != 0) {
            opacity = in.readInt();
         }
      } else {
         opacity = parametres >> 11 & 15;
      }

      byte light = (byte)(parametres >> 4 & 15);
      this.overlayBuilder.build(state, opacity, light, this.mapProcessor, null);
   }

   public boolean isRegionDetectionComplete() {
      return this.regionDetectionComplete;
   }

   public void setRegionDetectionComplete(boolean regionDetectionComplete) {
      this.regionDetectionComplete = regionDetectionComplete;
   }

   public void requestCache(LeveledRegion<?> region) {
      if (!this.toCacheContains(region)) {
         synchronized (region.getDim().regionsToCache) {
            region.getDim().regionsToCache.add(region);
         }

         if (WorldMapClientConfigUtils.getDebug()) {
            WorldMap.LOGGER.info("Requesting cache! " + region);
         }
      }
   }

   public LeveledRegion<?> removeToCache(MapDimension mapDim, int index) {
      synchronized (mapDim.regionsToCache) {
         return mapDim.regionsToCache.remove(index);
      }
   }

   public void removeToCache(LeveledRegion<?> region) {
      synchronized (region.getDim().regionsToCache) {
         region.getDim().regionsToCache.remove(region);
      }
   }

   public boolean toCacheContains(LeveledRegion<?> region) {
      synchronized (region.getDim().regionsToCache) {
         return region.getDim().regionsToCache.contains(region);
      }
   }

   public ArrayList<MapRegion> getToSave() {
      return this.toSave;
   }

   public LeveledRegion<?> getNextToLoadByViewing() {
      return this.nextToLoadByViewing;
   }

   public void setNextToLoadByViewing(LeveledRegion<?> nextToLoadByViewing) {
      this.nextToLoadByViewing = nextToLoadByViewing;
   }

   public OldFormatSupport getOldFormatSupport() {
      return this.oldFormatSupport;
   }

   public void safeDelete(Path filePath, String extension) throws IOException {
      if (!filePath.getFileName().toString().endsWith(extension)) {
         throw new RuntimeException("Incorrect file extension: " + filePath);
      }

      Files.deleteIfExists(filePath);
   }

   public void safeMoveAndReplace(Path fromPath, Path toPath, String fromExtension, String toExtension) throws IOException {
      if (toPath.getFileName().toString().endsWith(toExtension) && fromPath.getFileName().toString().endsWith(fromExtension)) {
         IOUtils.safeMoveAndReplace(fromPath, toPath, true);
      } else {
         throw new RuntimeException("Incorrect file extension: " + fromPath + " " + toPath);
      }
   }

   public int getSizeOfToLoadBranchCache() {
      return this.toLoadBranchCache.size();
   }
}
