package xaero.map;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.LevelResource;
import org.lwjgl.opengl.GL11;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.gui.IScreenBase;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.lib.common.reflection.util.ReflectionUtils;
import xaero.lib.common.util.IOUtils;
import xaero.map.biome.BiomeColorCalculator;
import xaero.map.biome.BiomeGetter;
import xaero.map.biome.BlockTintProvider;
import xaero.map.cache.BlockStateShortShapeCache;
import xaero.map.cache.BrokenBlockTintCache;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.controls.ControlsRegister;
import xaero.map.deallocator.ByteBufferDeallocator;
import xaero.map.exception.OpenGLException;
import xaero.map.file.MapSaveLoad;
import xaero.map.file.RegionDetection;
import xaero.map.file.worldsave.WorldDataHandler;
import xaero.map.graphics.CustomVertexConsumers;
import xaero.map.graphics.MapRenderHelper;
import xaero.map.graphics.TextureUploader;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.gui.GuiMap;
import xaero.map.gui.message.MessageBox;
import xaero.map.gui.message.render.MessageBoxRenderer;
import xaero.map.highlight.DimensionHighlighterHandler;
import xaero.map.highlight.HighlighterRegistry;
import xaero.map.highlight.MapRegionHighlightsPreparer;
import xaero.map.mcworld.WorldMapClientWorldData;
import xaero.map.mcworld.WorldMapClientWorldDataHelper;
import xaero.map.minimap.MinimapRenderListener;
import xaero.map.misc.CaveStartCalculator;
import xaero.map.mods.SupportMods;
import xaero.map.pool.MapTilePool;
import xaero.map.radar.tracker.synced.ClientSyncedTrackedPlayerManager;
import xaero.map.region.LayeredRegionManager;
import xaero.map.region.LeveledRegion;
import xaero.map.region.MapBlock;
import xaero.map.region.MapLayer;
import xaero.map.region.MapRegion;
import xaero.map.region.MapTile;
import xaero.map.region.MapTileChunk;
import xaero.map.region.MapUpdateFastConfig;
import xaero.map.region.OverlayManager;
import xaero.map.region.texture.BranchTextureRenderer;
import xaero.map.region.texture.RegionTexture;
import xaero.map.task.MapRunnerTask;
import xaero.map.world.MapDimension;
import xaero.map.world.MapWorld;

public class MapProcessor {
   public static final int ROOT_FOLDER_FORMAT = 5;
   public static final int DEFAULT_LIGHT_LEVELS = 4;
   private MapSaveLoad mapSaveLoad;
   private MapWriter mapWriter;
   private MapLimiter mapLimiter;
   private WorldDataHandler worldDataHandler;
   private ByteBufferDeallocator bufferDeallocator;
   private TextureUploader textureUploader;
   private BranchTextureRenderer branchTextureRenderer;
   private BiomeColorCalculator biomeColorCalculator;
   private final BlockStateShortShapeCache blockStateShortShapeCache;
   private final BiomeGetter biomeGetter;
   private final BrokenBlockTintCache brokenBlockTintCache;
   private final MapRegionHighlightsPreparer mapRegionHighlightsPreparer;
   private final CaveStartCalculator caveStartCalculator;
   private final ClientSyncedTrackedPlayerManager clientSyncedTrackedPlayerManager;
   private ClientLevel world;
   private HolderLookup<Block> worldBlockLookup;
   private Registry<Block> worldBlockRegistry;
   private Registry<Fluid> worldFluidRegistry;
   public Registry<Biome> worldBiomeRegistry;
   private Registry<DimensionType> worldDimensionTypeRegistry;
   private BlockTintProvider worldBlockTintProvider;
   private ClientLevel newWorld;
   private HolderLookup<Block> newWorldBlockLookup;
   public Registry<Block> newWorldBlockRegistry;
   private Registry<Fluid> newWorldFluidRegistry;
   public Registry<Biome> newWorldBiomeRegistry;
   public Registry<DimensionType> newWorldDimensionTypeRegistry;
   public final Object mainStuffSync;
   public ClientLevel mainWorld;
   private HolderLookup<Block> mainWorldBlockLookup;
   public Registry<Block> mainWorldBlockRegistry;
   private Registry<Fluid> mainWorldFluidRegistry;
   public Registry<Biome> mainWorldBiomeRegistry;
   public Registry<DimensionType> mainWorldDimensionTypeRegistry;
   public double mainPlayerX;
   public double mainPlayerY;
   public double mainPlayerZ;
   private boolean mainWorldUnloaded;
   private ArrayList<Double[]> footprints = new ArrayList<>();
   private int footprintsTimer;
   private boolean mapWorldUsable;
   private MapWorld mapWorld;
   private String currentWorldId;
   private String currentDimId;
   private String currentMWId;
   private FileLock mapLockToRelease;
   private FileChannel mapLockChannelToClose;
   private FileChannel currentMapLockChannel;
   private FileLock currentMapLock;
   private boolean mapWorldUsableRequest;
   public final Object renderThreadPauseSync = new Object();
   private int pauseUploading;
   private int pauseRendering;
   private int pauseWriting;
   public final Object processorThreadPauseSync = new Object();
   private int pauseProcessing;
   private final Object loadingSync = new Object();
   private boolean isLoading;
   public final Object uiSync = new Object();
   private boolean waitingForWorldUpdate;
   public final Object uiPauseSync = new Object();
   private boolean isUIPaused;
   private ArrayList<LeveledRegion<?>>[] toProcessLevels;
   private ArrayList<MapRegion> toRefresh = new ArrayList<>();
   private static final int SPAWNPOINT_TIMEOUT = 3000;
   private BlockPos spawnToRestore;
   private long mainWorldChangedTime = -1L;
   private MapTilePool tilePool;
   private int firstBranchLevel;
   private long lastRenderProcessTime = -1L;
   private int workingFramesCount;
   public long freeFramePeriod = -1L;
   private int testingFreeFrame = 1;
   private MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers;
   private CustomVertexConsumers cvc;
   private final MessageBox messageBox;
   private final MessageBoxRenderer messageBoxRenderer;
   private MinimapRenderListener minimapRenderListener;
   private boolean currentMapNeedsDeletion;
   private OverlayManager overlayManager;
   private long renderStartTime;
   private Field scheduledTasksField;
   private Runnable renderStartTimeUpdaterRunnable;
   private boolean finalizing;
   private int state;
   private HashSet<ResourceLocation> hardcodedNetherlike;
   private final HighlighterRegistry highlighterRegistry;
   private int currentCaveLayer = Integer.MAX_VALUE;
   private long lastLocalCaveModeToggle;
   private int nextLocalCaveMode = Integer.MAX_VALUE;
   private int localCaveMode = Integer.MAX_VALUE;
   private Item mapItem;
   private boolean consideringNetherFairPlayMessage;
   private boolean fairplayMessageReceived;
   private String[] dimensionsToIgnore = new String[]{"FZHammer"};
   public Field selectedField = null;

   public MapProcessor(
      MapSaveLoad mapSaveLoad,
      MapWriter mapWriter,
      MapLimiter mapLimiter,
      ByteBufferDeallocator bufferDeallocator,
      MapTilePool tilePool,
      OverlayManager overlayManager,
      TextureUploader textureUploader,
      WorldDataHandler worldDataHandler,
      BranchTextureRenderer branchTextureRenderer,
      MultiTextureRenderTypeRendererProvider mtrtrs,
      CustomVertexConsumers cvc,
      BiomeColorCalculator biomeColorCalculator,
      BlockStateShortShapeCache blockStateShortShapeCache,
      BiomeGetter biomeGetter,
      BrokenBlockTintCache brokenBlockTintCache,
      HighlighterRegistry highlighterRegistry,
      MapRegionHighlightsPreparer mapRegionHighlightsPreparer,
      MessageBox messageBox,
      MessageBoxRenderer messageBoxRenderer,
      CaveStartCalculator caveStartCalculator,
      ClientSyncedTrackedPlayerManager clientSyncedTrackedPlayerManager
   ) throws NoSuchFieldException {
      this.branchTextureRenderer = branchTextureRenderer;
      this.mapSaveLoad = mapSaveLoad;
      this.mapWriter = mapWriter;
      this.mapLimiter = mapLimiter;
      this.bufferDeallocator = bufferDeallocator;
      this.tilePool = tilePool;
      this.overlayManager = overlayManager;
      this.textureUploader = textureUploader;
      this.worldDataHandler = worldDataHandler;
      this.scheduledTasksField = ReflectionUtils.getFieldReflection(Minecraft.class, "progressTasks", "field_17404", "Ljava/util/Queue;", "f_91023_");
      this.renderStartTimeUpdaterRunnable = new Runnable() {
         @Override
         public void run() {
            MapProcessor.this.updateRenderStartTime();
         }
      };
      this.mainStuffSync = new Object();
      this.toProcessLevels = new ArrayList[4];

      for (int i = 0; i < this.toProcessLevels.length; i++) {
         this.toProcessLevels[i] = new ArrayList<>();
      }

      this.multiTextureRenderTypeRenderers = mtrtrs;
      this.cvc = cvc;
      this.biomeColorCalculator = biomeColorCalculator;
      this.blockStateShortShapeCache = blockStateShortShapeCache;
      this.hardcodedNetherlike = Sets.newHashSet(
         new ResourceLocation[]{BuiltinDimensionTypes.NETHER_EFFECTS, ResourceLocation.fromNamespaceAndPath("undergarden", "undergarden")}
      );
      this.biomeGetter = biomeGetter;
      this.brokenBlockTintCache = brokenBlockTintCache;
      this.highlighterRegistry = highlighterRegistry;
      this.mapRegionHighlightsPreparer = mapRegionHighlightsPreparer;
      this.messageBox = messageBox;
      this.messageBoxRenderer = messageBoxRenderer;
      this.caveStartCalculator = caveStartCalculator;
      this.clientSyncedTrackedPlayerManager = clientSyncedTrackedPlayerManager;
      this.minimapRenderListener = new MinimapRenderListener();
      this.updateMapItem();
   }

   public void onInit(ClientPacketListener connection) {
      String mainId = this.getMainId(5, connection);
      this.fixRootFolder(mainId, connection);
      this.mapWorld = new MapWorld(mainId, this.getMainId(0, connection), this);
      this.mapWorld.load();
   }

   public void run(MapRunner runner) {
      if (this.state < 2) {
         try {
            while (this.state < 2 && WorldMap.crashHandler.getCrashedBy() == null) {
               synchronized (this.processorThreadPauseSync) {
                  if (!this.isProcessingPaused()) {
                     this.updateWorld();
                     if (this.world != null) {
                        this.updateFootprints(Minecraft.getInstance().screen instanceof GuiMap ? 1 : 10);
                     }

                     if (this.mapWorldUsable) {
                        this.mapLimiter.applyLimit(this.mapWorld, this);
                        long currentTime = System.currentTimeMillis();

                        for (int l = 0; l < this.toProcessLevels.length; l++) {
                           ArrayList<LeveledRegion<?>> regionsToProcess = this.toProcessLevels[l];

                           for (int i = 0; i < regionsToProcess.size(); i++) {
                              LeveledRegion<?> leveledRegion;
                              synchronized (regionsToProcess) {
                                 if (i >= regionsToProcess.size()) {
                                    break;
                                 }

                                 leveledRegion = regionsToProcess.get(i);
                              }

                              this.mapSaveLoad.updateSave(leveledRegion, currentTime, this.currentCaveLayer);
                           }
                        }
                     }

                     this.mapSaveLoad.run(this.worldBlockLookup, this.worldBlockRegistry, this.worldFluidRegistry, this.biomeGetter, this.worldBiomeRegistry);
                     this.handleRefresh();
                     runner.doTasks(this);
                     this.releaseLocksIfNeeded();
                  }
               }

               try {
                  Thread.sleep(this.world != null && !shouldSkipWorldRender() && this.state <= 0 ? 100L : 40L);
               } catch (InterruptedException var12) {
               }
            }
         } catch (Throwable e) {
            WorldMap.crashHandler.setCrashedBy(e);
         }

         if (this.state < 2) {
            this.forceClean();
         }
      }

      if (this.state == 2) {
         this.state = 3;
      }
   }

   public static boolean shouldSkipWorldRender() {
      return !(Minecraft.getInstance().screen instanceof IScreenBase screenBase) ? false : screenBase.shouldSkipWorldRender();
   }

   public void onRenderProcess(Minecraft mc) throws RuntimeException {
      try {
         this.mapWriter.onRender(this.biomeColorCalculator, this.overlayManager);
         long renderProcessTime = System.nanoTime();
         if (this.testingFreeFrame == 1) {
            this.testingFreeFrame = 2;
         } else {
            synchronized (this.renderThreadPauseSync) {
               if (this.lastRenderProcessTime == -1L) {
                  this.lastRenderProcessTime = renderProcessTime;
               }

               long sinceLastProcessTime = renderProcessTime - this.lastRenderProcessTime;
               if (this.testingFreeFrame == 2) {
                  this.freeFramePeriod = sinceLastProcessTime;
                  this.testingFreeFrame = 0;
               }

               if (this.pauseUploading == 0 && this.mapWorldUsable && this.currentWorldId != null) {
                  mc.renderBuffers().bufferSource().endBatch();

                  while (GL11.glGetError() != 0) {
                  }

                  GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 0.0F);
                  GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                  RenderSystem.pixelStore(3317, 4);
                  RenderSystem.pixelStore(3316, 0);
                  RenderSystem.pixelStore(3315, 0);
                  RenderSystem.pixelStore(3314, 0);
                  RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                  OpenGLException.checkGLError();
                  this.updateCaveStart();
                  MapDimension currentDim = this.mapWorld.getCurrentDimension();
                  if (currentDim.getFullReloader() != null) {
                     currentDim.getFullReloader().onRenderProcess();
                  }

                  DimensionHighlighterHandler highlighterHandler = currentDim.getHighlightHandler();
                  ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
                  SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
                  int globalRegionCacheHashCode = WorldMap.settings.getRegionCacheHashCode();
                  boolean debugConfig = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DEBUG);
                  boolean detailedDebug = WorldMap.detailed_debug;
                  MapUpdateFastConfig updateConfig = new MapUpdateFastConfig();
                  long uploadStart = System.nanoTime();
                  long totalTime = Math.min(sinceLastProcessTime, this.freeFramePeriod);
                  long passed = uploadStart - this.renderStartTime;
                  long timeAvailable = Math.max(3000000L, totalTime - passed);
                  long uploadUntil = uploadStart + timeAvailable / 4L;
                  long gpuLimit = Math.max(
                     1000000L, Minecraft.getInstance().screen instanceof GuiMap ? totalTime * 5L / 12L : Math.min(totalTime / 5L, timeAvailable)
                  );
                  boolean noLimits = false;
                  if (Minecraft.getInstance().screen instanceof GuiMap) {
                     GuiMap guiMap = (GuiMap)Minecraft.getInstance().screen;
                     noLimits = guiMap.noUploadingLimits;
                     guiMap.noUploadingLimits = false;
                  }

                  int firstLevel = 0;
                  boolean branchesCatchup = (int)(Math.random() * 5.0) == 0;
                  if (branchesCatchup) {
                     firstLevel = 1 + this.firstBranchLevel;
                  }

                  this.firstBranchLevel = (this.firstBranchLevel + 1) % (this.toProcessLevels.length - 1);

                  for (int j = 0; j < this.toProcessLevels.length; j++) {
                     int level = (firstLevel + j) % this.toProcessLevels.length;
                     ArrayList<LeveledRegion<?>> toProcess = this.toProcessLevels[level];

                     for (int i = 0; i < toProcess.size(); i++) {
                        LeveledRegion<? extends RegionTexture<?>> region;
                        synchronized (toProcess) {
                           if (i >= toProcess.size()) {
                              break;
                           }

                           region = (LeveledRegion<? extends RegionTexture<?>>)toProcess.get(i);
                        }

                        if (region != null) {
                           synchronized (region) {
                              if (region.shouldBeProcessed()) {
                                 boolean cleanAndCacheRequestsBlocked = region.cleanAndCacheRequestsBlocked();
                                 boolean allCleaned = true;
                                 boolean allCached = true;
                                 boolean allUploaded = true;
                                 boolean hasLoadedTextures = false;

                                 for (int x = 0; x < 8; x++) {
                                    for (int z = 0; z < 8; z++) {
                                       RegionTexture texture = region.getTexture(x, z);
                                       if (texture != null) {
                                          if (texture.canUpload()) {
                                             hasLoadedTextures = true;
                                             if (noLimits || gpuLimit > 0L && System.nanoTime() < uploadUntil) {
                                                texture.preUpload(
                                                   this,
                                                   this.worldBlockTintProvider,
                                                   this.overlayManager,
                                                   region,
                                                   detailedDebug,
                                                   this.blockStateShortShapeCache,
                                                   updateConfig
                                                );
                                                if (texture.shouldUpload()) {
                                                   if (texture.getTimer() == 0) {
                                                      gpuLimit -= texture.uploadBuffer(
                                                         highlighterHandler, this.textureUploader, region, this.branchTextureRenderer, x, z
                                                      );
                                                   } else {
                                                      texture.decTimer();
                                                   }
                                                }
                                             }

                                             texture.postUpload(this, region, cleanAndCacheRequestsBlocked);
                                          }

                                          if (texture.hasSourceData()) {
                                             allCleaned = false;
                                          }

                                          if (texture.shouldIncludeInCache() && !texture.isCachePrepared()) {
                                             allCached = false;
                                          }

                                          if (!texture.isUploaded()) {
                                             allUploaded = false;
                                          }
                                       }
                                    }
                                 }

                                 if (hasLoadedTextures) {
                                    region.processWhenLoadedChunksExist(globalRegionCacheHashCode);
                                 }

                                 allUploaded = allUploaded && region.isLoaded() && !cleanAndCacheRequestsBlocked;
                                 allCached = allCached && allUploaded;
                                 if ((!region.shouldCache() || !region.recacheHasBeenRequested())
                                    && region.shouldEndProcessingAfterUpload()
                                    && allCleaned
                                    && allUploaded) {
                                    region.onProcessingEnd();
                                    region.deleteGLBuffers();
                                    synchronized (toProcess) {
                                       if (i < toProcess.size()) {
                                          toProcess.remove(i);
                                          i--;
                                       }
                                    }

                                    if (debugConfig) {
                                       WorldMap.LOGGER
                                          .info(
                                             "Region freed: "
                                                + region
                                                + " "
                                                + this.mapWriter.getUpdateCounter()
                                                + " "
                                                + this.currentWorldId
                                                + " "
                                                + this.currentDimId
                                          );
                                    }
                                 }

                                 if (allCached && !region.isAllCachePrepared()) {
                                    region.setAllCachePrepared(true);
                                 }

                                 if (region.shouldCache() && region.recacheHasBeenRequested() && region.isAllCachePrepared() && !cleanAndCacheRequestsBlocked) {
                                    this.getMapSaveLoad().requestCache(region);
                                 }
                              }
                           }
                        }
                     }
                  }

                  this.workingFramesCount++;
                  if (this.workingFramesCount >= 30) {
                     this.testingFreeFrame = 1;
                     this.workingFramesCount = 0;
                  }

                  this.textureUploader.uploadTextures();
               }
            }
         }

         this.mapLimiter.updateAvailableVRAM();
         this.lastRenderProcessTime = renderProcessTime;
      } catch (Throwable e) {
         WorldMap.crashHandler.setCrashedBy(e);
      }

      WorldMap.crashHandler.checkForCrashes();
      MapRenderHelper.restoreDefaultShaderBlendState();
   }

   public void updateCaveStart() {
      Minecraft mc = Minecraft.getInstance();
      MapDimension dimension = this.mapWorld.getCurrentDimension();
      boolean caveModeAllowed = WorldMapClientConfigUtils.getEffectiveCaveModeAllowed();
      int newCaveStart;
      if (caveModeAllowed && dimension.getCaveModeType() != 0) {
         ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
         SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
         int caveModeStartConfig = (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.CAVE_MODE_START);
         if (caveModeStartConfig == Integer.MAX_VALUE) {
            newCaveStart = Integer.MIN_VALUE;
         } else {
            newCaveStart = caveModeStartConfig;
         }

         boolean customDim = dimension.getDimId() != mc.level.dimension();
         boolean isMapScreen = mc.screen instanceof GuiMap || shouldSkipWorldRender();
         int autoCaveModeConfig = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.AUTO_CAVE_MODE);
         double caveModeToggleTimerConfig = (Double)configManager.getEffective(WorldMapProfiledConfigOptions.CAVE_MODE_TOGGLE_TIMER);
         if (SupportMods.minimap() && (!customDim && autoCaveModeConfig < 0 && newCaveStart == Integer.MIN_VALUE || !isMapScreen)) {
            newCaveStart = SupportMods.xaeroMinimap.getCaveStart(newCaveStart, isMapScreen);
         }

         if (newCaveStart == Integer.MIN_VALUE) {
            long currentTime = System.currentTimeMillis();
            int nextLocalCaveMode = customDim
               ? Integer.MAX_VALUE
               : this.caveStartCalculator.getCaving(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.level);
            boolean toggling = this.localCaveMode == Integer.MAX_VALUE != (nextLocalCaveMode == Integer.MAX_VALUE);
            if (!toggling || currentTime - this.lastLocalCaveModeToggle > (long)(caveModeToggleTimerConfig * 1000.0)) {
               if (toggling) {
                  this.lastLocalCaveModeToggle = currentTime;
               }

               this.localCaveMode = nextLocalCaveMode;
            }

            newCaveStart = this.localCaveMode;
         }

         if (newCaveStart != Integer.MAX_VALUE) {
            if (dimension.getCaveModeType() == 2) {
               newCaveStart = Integer.MIN_VALUE;
            } else {
               newCaveStart = Mth.clamp(newCaveStart, this.world.getMinBuildHeight(), this.world.getMaxBuildHeight() - 1);
            }
         }
      } else {
         newCaveStart = Integer.MAX_VALUE;
      }

      int newCaveLayer = this.getCaveLayer(newCaveStart);
      dimension.getLayeredMapRegions().getLayer(newCaveLayer).setCaveStart(newCaveStart);
      this.currentCaveLayer = newCaveLayer;
   }

   public boolean ignoreWorld(Level world) {
      for (int i = 0; i < this.dimensionsToIgnore.length; i++) {
         if (this.dimensionsToIgnore[i].equals(world.dimension().location().getPath())) {
            return true;
         }
      }

      return false;
   }

   public String getDimensionName(ResourceKey<Level> id) {
      if (id == Level.OVERWORLD) {
         return "null";
      }

      if (id == Level.NETHER) {
         return "DIM-1";
      }

      if (id == Level.END) {
         return "DIM1";
      }

      ResourceLocation identifier = id.location();
      String path = identifier.getPath().replace('/', '%');
      path = IOUtils.replaceTrailingDots(path, ',');
      return identifier.getNamespace() + "$" + path;
   }

   public ResourceKey<Level> getDimensionIdForFolder(String folderName) {
      if (folderName.equals("null")) {
         return Level.OVERWORLD;
      }

      if (folderName.equals("DIM-1")) {
         return Level.NETHER;
      }

      if (folderName.equals("DIM1")) {
         return Level.END;
      }

      int separatorIndex = folderName.indexOf(36);
      if (separatorIndex == -1) {
         return null;
      }

      String namespace = folderName.substring(0, separatorIndex);
      String path = folderName.substring(separatorIndex + 1).replace('%', '/');
      path = path.replace(',', '.');

      try {
         ResourceLocation dimensionId = ResourceLocation.fromNamespaceAndPath(namespace, path);
         return ResourceKey.create(Registries.DIMENSION, dimensionId);
      } catch (ResourceLocationException rse) {
         return null;
      }
   }

   public void waitForLoadingToFinish(Runnable onFinish) {
      while (true) {
         synchronized (this.loadingSync) {
            if (!this.isLoading) {
               onFinish.run();
               return;
            }

            this.blockStateShortShapeCache.supplyForIOThread();
            this.worldDataHandler.handleRenderExecutor();
         }
      }
   }

   public synchronized void changeWorld(
      ClientLevel world,
      HolderLookup<Block> blockLookup,
      Registry<Block> blockRegistry,
      Registry<Fluid> fluidRegistry,
      Registry<Biome> biomeRegistry,
      Registry<DimensionType> dimensionTypeRegistry
   ) {
      this.pushWriterPause();
      if (world != this.newWorld) {
         this.waitForLoadingToFinish(() -> this.waitingForWorldUpdate = true);
      }

      this.newWorld = world;
      this.newWorldBlockLookup = blockLookup;
      this.newWorldBlockRegistry = blockRegistry;
      this.newWorldFluidRegistry = fluidRegistry;
      this.newWorldBiomeRegistry = biomeRegistry;
      this.newWorldDimensionTypeRegistry = dimensionTypeRegistry;
      if (world == null) {
         this.mapWorldUsableRequest = false;
      } else {
         this.mapWorldUsableRequest = true;
         ResourceKey<Level> dimId = this.mapWorld.getPotentialDimId();
         this.mapWorld.setFutureDimensionId(dimId);
         this.updateDimension(world, dimId);
         this.mapWorld.getFutureDimension().resetCustomMultiworldUnsynced();
      }

      this.popWriterPause();
   }

   public void updateVisitedDimension(ClientLevel world) {
      this.updateDimension(world, world.dimension());
   }

   public synchronized void updateDimension(ClientLevel world, ResourceKey<Level> dimId) {
      if (world != null) {
         Object autoIdBase = this.getAutoIdBase(world);
         MapDimension mapDimension = this.mapWorld.getDimension(dimId);
         if (mapDimension == null) {
            mapDimension = this.mapWorld.createDimensionUnsynced(dimId);
         }

         mapDimension.updateFutureAutomaticUnsynced(Minecraft.getInstance(), autoIdBase);
      }
   }

   @Deprecated
   private String getMainId(boolean rootFolderFormat, boolean preIP6Fix, ClientPacketListener connection) {
      return !rootFolderFormat ? this.getMainId(0, connection) : this.getMainId(preIP6Fix ? 1 : 2, connection);
   }

   private String getMainId(int version, ClientPacketListener connection) {
      Minecraft mc = Minecraft.getInstance();
      String result = null;
      if (mc.getSingleplayerServer() != null) {
         result = MapWorld.convertWorldFolderToRootId(version, mc.getSingleplayerServer().getWorldPath(LevelResource.ROOT).getParent().getFileName().toString());
      } else {
         ServerData serverData = connection.getServerData();
         if (serverData != null && serverData.isRealm() && WorldMap.events.getLatestRealm() != null) {
            result = "Realms_" + WorldMap.events.getLatestRealm().ownerUUID + "." + WorldMap.events.getLatestRealm().id;
         } else if (serverData != null) {
            ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
            SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
            boolean differentiateByServerAddress = (Boolean)primaryConfigManager.getEffective(
               WorldMapPrimaryClientConfigOptions.DIFFERENTIATE_BY_SERVER_ADDRESS
            );
            String serverIP = differentiateByServerAddress ? serverData.ip : "Any Address";
            int portDivider;
            if (version >= 2 && serverIP.indexOf(":") != serverIP.lastIndexOf(":")) {
               portDivider = serverIP.lastIndexOf("]:") + 1;
            } else {
               portDivider = serverIP.indexOf(":");
            }

            if (portDivider > 0) {
               serverIP = serverIP.substring(0, portDivider);
            }

            while (version >= 1 && serverIP.endsWith(".")) {
               serverIP = serverIP.substring(0, serverIP.length() - 1);
            }

            if (version >= 3) {
               serverIP = serverIP.replace("[", "").replace("]", "");
            }

            serverIP = serverIP.replaceAll(":", version < 4 ? "§" : ".");
            if (version >= 5) {
               serverIP = serverIP.trim();
               serverIP = IOUtils.replaceTrailingDots(serverIP, ',');
            }

            if (serverIP.isEmpty()) {
               serverIP = "Empty Address";
            }

            result = "Multiplayer_" + serverIP;
         } else {
            result = "Multiplayer_Unknown";
         }
      }

      return result;
   }

   public synchronized void toggleMultiworldType(MapDimension dim) {
      if (this.mapWorldUsable && !this.waitingForWorldUpdate && this.mapWorld.isMultiplayer() && this.mapWorld.getCurrentDimension() == dim) {
         this.mapWorld.toggleMultiworldTypeUnsynced();
      }
   }

   public synchronized void quickConfirmMultiworld() {
      if (this.canQuickConfirmUnsynced() && this.mapWorld.getCurrentDimension().hasConfirmedMultiworld()) {
         this.confirmMultiworld(this.mapWorld.getCurrentDimension());
      }
   }

   public synchronized boolean confirmMultiworld(MapDimension dim) {
      if (this.mapWorldUsable
         && this.mainWorld != null
         && this.mapWorld.getPotentialDimId() == this.mapWorld.getCurrentDimensionId()
         && this.mapWorld.getCurrentDimension() == dim) {
         this.mapWorld.confirmMultiworldTypeUnsynced();
         this.mapWorld.getCurrentDimension().confirmMultiworldUnsynced();
         return true;
      } else {
         return false;
      }
   }

   public synchronized void setMultiworld(MapDimension dimToCompare, String customMW) {
      if (this.mapWorldUsable && dimToCompare.getMapWorld() == this.mapWorld) {
         dimToCompare.setMultiworldUnsynced(customMW);
      }
   }

   public boolean canQuickConfirmUnsynced() {
      return this.mapWorldUsable
         && !this.mapWorld.getCurrentDimension().futureMultiworldWritable
         && this.mapWorld.getPotentialDimId() == this.mapWorld.getCurrentDimensionId();
   }

   public String getCrosshairMessage() {
      synchronized (this.uiPauseSync) {
         if (this.isUIPaused) {
            return null;
         }

         if (this.canQuickConfirmUnsynced()) {
            String selectedMWName = this.mapWorld.getCurrentDimension().getMultiworldName(this.mapWorld.getCurrentDimension().getFutureMultiworldUnsynced());
            String message = "§2("
               + ControlsRegister.keyOpenMap.getTranslatedKeyMessage().getString().toUpperCase()
               + ")§r "
               + I18n.get("gui.xaero_map_unconfirmed", new Object[0]);
            if (this.mapWorld.getCurrentDimension().hasConfirmedMultiworld()) {
               message = message
                  + " §2"
                  + ControlsRegister.keyQuickConfirm.getTranslatedKeyMessage().getString().toUpperCase()
                  + "§r for map \""
                  + I18n.get(selectedMWName, new Object[0])
                  + "\"";
            }

            return message;
         } else {
            return null;
         }
      }
   }

   public synchronized void checkForWorldUpdate() {
      if (this.mainWorld != null) {
         Object autoIdBase = this.getAutoIdBase(this.mainWorld);
         if (autoIdBase != null) {
            boolean baseChanged = !autoIdBase.equals(this.getUsedAutoIdBase(this.mainWorld));
            ResourceKey<Level> potentialDimId = this.mapWorld.getPotentialDimId();
            if (baseChanged && this.mapWorldUsableRequest) {
               MapDimension mapDimension = this.mapWorld.getDimension(potentialDimId);
               if (mapDimension != null) {
                  boolean serverBasedBefore = mapDimension.isFutureMultiworldServerBased();
                  mapDimension.updateFutureAutomaticUnsynced(Minecraft.getInstance(), autoIdBase);
                  if (serverBasedBefore != mapDimension.isFutureMultiworldServerBased()) {
                     mapDimension.resetCustomMultiworldUnsynced();
                  }
               }
            }

            if (this.mainWorld != this.world || potentialDimId != this.mapWorld.getFutureDimensionId()) {
               this.changeWorld(
                  this.mainWorld,
                  this.mainWorldBlockLookup,
                  this.mainWorldBlockRegistry,
                  this.mainWorldFluidRegistry,
                  this.mainWorldBiomeRegistry,
                  this.mainWorldDimensionTypeRegistry
               );
            }

            Object updatedAutoIdBase = this.getAutoIdBase(this.mainWorld);
            if (updatedAutoIdBase != null) {
               this.setUsedAutoIdBase(this.mainWorld, updatedAutoIdBase);
            } else {
               this.removeUsedAutoIdBase(this.mainWorld);
            }

            if (potentialDimId != this.mainWorld.dimension()) {
               this.updateVisitedDimension(this.mainWorld);
            }
         }
      }
   }

   private void updateWorld() throws IOException, CommandSyntaxException {
      this.pushUIPause();
      this.updateWorldSynced();
      this.popUIPause();
      if (this.mapWorldUsable && !this.mapSaveLoad.isRegionDetectionComplete()) {
         this.mapSaveLoad.detectRegions(10);
         this.mapSaveLoad.setRegionDetectionComplete(true);
      }
   }

   private synchronized void updateWorldSynced() throws IOException, CommandSyntaxException {
      synchronized (this.uiSync) {
         boolean changedDimension = this.mapWorldUsable != this.mapWorldUsableRequest
            || !this.mapWorldUsableRequest
            || this.mapWorld.getFutureDimension() != this.mapWorld.getCurrentDimension();
         if (this.mapWorldUsable != this.mapWorldUsableRequest
            || this.mapWorldUsableRequest
               && (
                  changedDimension
                     || !this.mapWorld.getFutureDimension().getFutureMultiworldUnsynced().equals(this.mapWorld.getFutureDimension().getCurrentMultiworld())
               )) {
            ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
            SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
            boolean debugConfig = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DEBUG);
            String newMWId = !this.mapWorldUsableRequest ? null : this.mapWorld.getFutureMultiworldUnsynced();
            this.pushRenderPause(true, true);
            this.pushWriterPause();
            String newWorldId = !this.mapWorldUsableRequest ? null : this.mapWorld.getMainId();
            boolean shouldClearAllDimensions = this.state == 1;
            boolean shouldClearNewDimension = this.mapWorldUsableRequest
               && !this.mapWorld.getFutureMultiworldUnsynced().equals(this.mapWorld.getFutureDimension().getCurrentMultiworld());
            this.mapSaveLoad.getToSave().clear();
            if (this.currentMapLock != null) {
               this.mapLockToRelease = this.currentMapLock;
               this.mapLockChannelToClose = this.currentMapLockChannel;
               this.currentMapLock = null;
               this.currentMapLockChannel = null;
            }

            this.releaseLocksIfNeeded();
            if (this.mapWorld.getCurrentDimensionId() != null) {
               MapDimension currentDim = this.mapWorld.getCurrentDimension();
               MapDimension reqDim = !this.mapWorldUsableRequest ? null : this.mapWorld.getFutureDimension();
               boolean shouldFinishCurrentDim = this.mapWorldUsable && !this.currentMapNeedsDeletion;
               boolean currentDimChecked = false;
               if (shouldFinishCurrentDim) {
                  this.mapSaveLoad.saveAll = true;
               }

               if (shouldFinishCurrentDim || shouldClearNewDimension && reqDim == currentDim) {
                  for (LeveledRegion<?> region : currentDim.getLayeredMapRegions().getUnsyncedSet()) {
                     if (shouldFinishCurrentDim) {
                        if (region.getLevel() == 0) {
                           MapRegion leafRegion = (MapRegion)region;
                           if (!leafRegion.isNormalMapData() && !leafRegion.hasLookedForCache() && leafRegion.isOutdatedWithOtherLayers()) {
                              File potentialCacheFile = this.mapSaveLoad.getCacheFile(leafRegion, leafRegion.getCaveLayer(), false, false);
                              if (potentialCacheFile.exists()) {
                                 leafRegion.setCacheFile(potentialCacheFile);
                                 leafRegion.setLookedForCache(true);
                              }
                           }

                           if (leafRegion.shouldConvertCacheToOutdatedOnFinishDim() && leafRegion.getCacheFile() != null) {
                              leafRegion.convertCacheToOutdated(this.mapSaveLoad, "might be outdated");
                              if (debugConfig) {
                                 WorldMap.LOGGER.info(String.format("Converting cache for region %s because it might be outdated.", leafRegion));
                              }
                           }
                        }

                        region.setReloadHasBeenRequested(false, "world/dim change");
                        region.onCurrentDimFinish(this.mapSaveLoad, this);
                     }

                     if (shouldClearAllDimensions || shouldClearNewDimension && reqDim == currentDim) {
                        region.onDimensionClear(this);
                     }
                  }

                  currentDimChecked = true;
               }

               if (reqDim != currentDim && shouldClearNewDimension) {
                  for (LeveledRegion<?> region : reqDim.getLayeredMapRegions().getUnsyncedSet()) {
                     region.onDimensionClear(this);
                  }
               }

               if (shouldClearAllDimensions) {
                  for (MapDimension dim : this.mapWorld.getDimensionsList()) {
                     if (!currentDimChecked || dim != currentDim) {
                        for (LeveledRegion<?> region : dim.getLayeredMapRegions().getUnsyncedSet()) {
                           region.onDimensionClear(this);
                        }
                     }
                  }
               }

               if (this.currentMapNeedsDeletion) {
                  this.mapWorld.getCurrentDimension().deleteMultiworldMapDataUnsynced(this.mapWorld.getCurrentDimension().getCurrentMultiworld());
               }
            }

            this.currentMapNeedsDeletion = false;
            if (!shouldClearAllDimensions) {
               if (shouldClearNewDimension) {
                  this.mapWorld.getFutureDimension().clear();
                  if (debugConfig) {
                     WorldMap.LOGGER.info("Dimension map data cleared!");
                  }
               }
            } else {
               if (this.mapWorld.getCurrentDimensionId() != null) {
                  for (MapDimension dim : this.mapWorld.getDimensionsList()) {
                     dim.clear();
                  }
               }

               if (debugConfig) {
                  WorldMap.LOGGER.info("All map data cleared!");
               }

               if (this.state == 1) {
                  WorldMap.LOGGER.info("World map cleaned normally!");
                  this.state = 2;
               }
            }

            if (debugConfig) {
               WorldMap.LOGGER.info("World changed!");
            }

            this.mapWorldUsable = this.mapWorldUsableRequest;
            if (this.mapWorldUsableRequest) {
               this.mapWorld.switchToFutureUnsynced();
            }

            this.currentWorldId = newWorldId;
            this.currentDimId = !this.mapWorldUsableRequest ? null : this.getDimensionName(this.mapWorld.getFutureDimensionId());
            this.currentMWId = newMWId;
            Path mapPath = this.mapSaveLoad.getMWSubFolder(this.currentWorldId, this.currentDimId, this.currentMWId);
            if (this.mapWorldUsable) {
               Files.createDirectories(mapPath);
               Path mapLockPath = mapPath.resolve(".lock");
               int totalLockAttempts = 10;
               int lockAttempts = 10;

               while (lockAttempts-- > 0) {
                  if (lockAttempts < 9) {
                     WorldMap.LOGGER.info("Failed attempt to lock the current world map! Retrying in 50 ms... " + lockAttempts);

                     try {
                        Thread.sleep(50L);
                     } catch (InterruptedException var19) {
                     }
                  }

                  try {
                     FileChannel lockChannel = FileChannel.open(mapLockPath, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                     this.currentMapLock = lockChannel.tryLock();
                     if (this.currentMapLock != null) {
                        this.currentMapLockChannel = lockChannel;
                        break;
                     }
                  } catch (Exception e) {
                     WorldMap.LOGGER.error("suppressed exception", e);
                  }
               }
            }

            this.checkFootstepsReset(this.world, this.newWorld);
            this.mapSaveLoad.clearToLoad();
            this.mapSaveLoad.setNextToLoadByViewing((LeveledRegion<?>)null);
            this.clearToRefresh();

            for (int i = 0; i < this.toProcessLevels.length; i++) {
               this.toProcessLevels[i].clear();
            }

            if (this.mapWorldUsable && !this.isCurrentMapLocked()) {
               for (LeveledRegion<?> region : this.mapWorld.getCurrentDimension().getLayeredMapRegions().getUnsyncedSet()) {
                  if (region.shouldBeProcessed()) {
                     this.addToProcess(region);
                  }
               }
            }

            this.mapWriter.resetPosition();
            this.world = this.newWorld;
            this.worldBlockLookup = this.newWorldBlockLookup;
            this.worldBlockRegistry = this.newWorldBlockRegistry;
            this.worldFluidRegistry = this.newWorldFluidRegistry;
            this.worldBiomeRegistry = this.newWorldBiomeRegistry;
            this.worldDimensionTypeRegistry = this.newWorldDimensionTypeRegistry;
            this.worldBlockTintProvider = this.world == null
               ? null
               : new BlockTintProvider(this.worldBiomeRegistry, this.biomeColorCalculator, this, this.brokenBlockTintCache, this.mapWriter);
            if (SupportMods.framedBlocks()) {
               SupportMods.supportFramedBlocks.onWorldChange();
            }

            if (SupportMods.pac()) {
               SupportMods.xaeroPac.onMapChange(changedDimension);
               SupportMods.xaeroPac.resetDetection();
            }

            this.mapWorld.onWorldChangeUnsynced(this.world);
            if (debugConfig) {
               WorldMap.LOGGER.info("World/dimension changed to: " + this.currentWorldId + " " + this.currentDimId + " " + this.currentMWId);
            }

            this.worldDataHandler.prepareSingleplayer(this.world, this);
            if (this.worldDataHandler.getWorldDir() == null && this.currentWorldId != null && this.mapWorld.getCurrentDimension().isUsingWorldSave()) {
               this.currentWorldId = this.currentDimId = null;
            }

            boolean shouldDetect = this.mapWorldUsable && !this.mapWorld.getCurrentDimension().hasDoneRegionDetection();
            this.mapSaveLoad.setRegionDetectionComplete(!shouldDetect);
            this.popRenderPause(true, true);
            this.popWriterPause();
         } else if (this.newWorld != this.world) {
            this.pushRenderPause(false, true);
            this.pushWriterPause();
            this.checkFootstepsReset(this.world, this.newWorld);
            this.world = this.newWorld;
            this.worldBlockLookup = this.newWorldBlockLookup;
            this.worldBlockRegistry = this.newWorldBlockRegistry;
            this.worldFluidRegistry = this.newWorldFluidRegistry;
            this.worldBiomeRegistry = this.newWorldBiomeRegistry;
            this.worldDimensionTypeRegistry = this.newWorldDimensionTypeRegistry;
            this.worldBlockTintProvider = this.world == null
               ? null
               : new BlockTintProvider(this.worldBiomeRegistry, this.biomeColorCalculator, this, this.brokenBlockTintCache, this.mapWriter);
            if (SupportMods.framedBlocks()) {
               SupportMods.supportFramedBlocks.onWorldChange();
            }

            if (SupportMods.pac()) {
               SupportMods.xaeroPac.resetDetection();
            }

            this.mapWorld.onWorldChangeUnsynced(this.world);
            this.popRenderPause(false, true);
            this.popWriterPause();
         }

         if (this.mapWorldUsable) {
            this.mapWorld.getCurrentDimension().switchToFutureMultiworldWritableValueUnsynced();
            this.mapWorld.switchToFutureMultiworldTypeUnsynced();
         }

         this.waitingForWorldUpdate = false;
      }
   }

   public void updateFootprints(int step) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.FOOTSTEPS)) {
         if (this.footprintsTimer > 0) {
            this.footprintsTimer -= step;
         } else {
            Double[] coords = new Double[]{this.mainPlayerX, this.mainPlayerZ};
            synchronized (this.footprints) {
               this.footprints.add(coords);
               if (this.footprints.size() > 32) {
                  this.footprints.remove(0);
               }
            }

            this.footprintsTimer = 20;
         }
      }
   }

   public void addToRefresh(MapRegion region, boolean prepareHighlights) {
      synchronized (this.toRefresh) {
         if (!this.toRefresh.contains(region)) {
            this.toRefresh.add(0, region);
         }
      }

      if (prepareHighlights) {
         this.mapRegionHighlightsPreparer.prepare(region, false);
      }
   }

   public void removeToRefresh(MapRegion region) {
      synchronized (this.toRefresh) {
         this.toRefresh.remove(region);
      }
   }

   private void clearToRefresh() {
      synchronized (this.toRefresh) {
         this.toRefresh.clear();
      }
   }

   private void handleRefresh() throws RuntimeException {
      this.pushIsLoading();
      if (!this.waitingForWorldUpdate && !this.toRefresh.isEmpty()) {
         MapRegion region = this.toRefresh.get(0);
         if (!region.isRefreshing()) {
            throw new RuntimeException(String.format("Trying to refresh region %s, which is not marked as being refreshed!", region));
         }

         ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
         SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
         boolean debugConfig = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DEBUG);
         int globalVersion = (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.GLOBAL_VERSION);
         int globalReloadVersion = (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.RELOAD_VIEWED_VERSION);
         int globalCacheHashCode = WorldMap.settings.getRegionCacheHashCode();
         boolean regionLoaded;
         synchronized (region) {
            regionLoaded = region.getLoadState() == 2;
            if (regionLoaded) {
               region.setRecacheHasBeenRequested(true, "refresh handle");
               region.setShouldCache(true, "refresh handle");
               region.setVersion(globalVersion);
               region.setCacheHashCode(globalCacheHashCode);
               region.setReloadVersion(globalReloadVersion);
               region.setHighlightsHash(region.getTargetHighlightsHash());
            }
         }

         boolean isEmpty = true;
         if (regionLoaded) {
            synchronized (region) {
               region.setAllCachePrepared(false);
            }

            boolean skipRegularRefresh = false;
            int upToDateCaveStart = region.getUpToDateCaveStart();
            int caveModeDepth = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.CAVE_MODE_DEPTH);
            if (region.isBeingWritten() && region.caveStartOutdated(upToDateCaveStart, caveModeDepth)) {
               try {
                  this.getWorldDataHandler().buildRegion(region, this.worldBlockLookup, this.worldBlockRegistry, this.worldFluidRegistry, false, null);
                  skipRegularRefresh = true;
               } catch (Throwable e) {
                  WorldMap.LOGGER
                     .info("Region failed to refresh from world save: " + region + " " + region.getWorldId() + " " + region.getDimId() + " " + region.getMwId());
               }
            }

            for (int i = 0; i < 8; i++) {
               for (int j = 0; j < 8; j++) {
                  MapTileChunk chunk = region.getChunk(i, j);
                  if (chunk != null) {
                     if (chunk.hasHadTerrain()) {
                        if (!skipRegularRefresh && chunk.getLoadState() == 2) {
                           for (int tileX = 0; tileX < 4; tileX++) {
                              for (int tileZ = 0; tileZ < 4; tileZ++) {
                                 region.pushWriterPause();
                                 MapTile tile = chunk.getTile(tileX, tileZ);
                                 if (tile != null && tile.isLoaded()) {
                                    for (int o = 0; o < 16; o++) {
                                       MapBlock[] column = tile.getBlockColumn(o);

                                       for (int p = 0; p < 16; p++) {
                                          column[p].setSlopeUnknown(true);
                                       }
                                    }
                                 }

                                 chunk.setTile(tileX, tileZ, tile, this.blockStateShortShapeCache);
                                 region.popWriterPause();
                              }
                           }

                           chunk.setToUpdateBuffers(true);
                        }
                     } else {
                        region.pushWriterPause();
                        if (!chunk.hasHadTerrain() && !chunk.wasChanged() && !chunk.getToUpdateBuffers()) {
                           region.uncountTextureBiomes(chunk.getLeafTexture());
                           chunk.getLeafTexture().resetBiomes();
                           if (chunk.hasHighlightsIfUndiscovered()) {
                              chunk.getLeafTexture().requestHighlightOnlyUpload();
                           } else {
                              region.setChunk(i, j, null);
                              chunk.getLeafTexture().deleteTexturesAndBuffers();
                           }
                        }

                        region.popWriterPause();
                     }

                     isEmpty = false;
                  }
               }
            }

            if (debugConfig) {
               WorldMap.LOGGER.info("Region refreshed: " + region + " " + region + " " + this.mapWriter.getUpdateCounter());
            }
         }

         synchronized (region) {
            region.setRefreshing(false);
            if (isEmpty) {
               region.setShouldCache(false, "refresh handle");
               region.setRecacheHasBeenRequested(false, "refresh handle");
            }
         }

         if (region.isResaving()) {
            region.setLastSaveTime(-60000L);
         }

         this.removeToRefresh(region);
      }

      this.popIsLoading();
   }

   public boolean regionExists(int caveLayer, int x, int z) {
      return this.regionDetectionExists(caveLayer, x, z) || this.mapWorld.getCurrentDimension().getHighlightHandler().shouldApplyRegionHighlights(x, z, false);
   }

   public boolean regionDetectionExists(int caveLayer, int x, int z) {
      return !this.mapSaveLoad.isRegionDetectionComplete()
         ? false
         : this.mapWorld.getCurrentDimension().getLayeredMapRegions().getLayer(caveLayer).regionDetectionExists(x, z);
   }

   public void removeMapRegion(LeveledRegion<?> region) {
      MapDimension regionDim = region.getDim();
      LayeredRegionManager regions = regionDim.getLayeredMapRegions();
      if (region.getLevel() == 0) {
         regions.remove(region.getCaveLayer(), region.getRegionX(), region.getRegionZ(), region.getLevel());
         regions.removeListRegion(region);
      }

      regions.removeLoadedRegion(region);
      this.removeToProcess(region);
   }

   public LeveledRegion<?> getLeveledRegion(int caveLayer, int leveledRegX, int leveledRegZ, int level) {
      MapDimension mapDimension = this.mapWorld.getCurrentDimension();
      LayeredRegionManager regions = mapDimension.getLayeredMapRegions();
      return regions.get(caveLayer, leveledRegX, leveledRegZ, level);
   }

   public void initMinimapRender(int flooredMapCameraX, int flooredMapCameraZ) {
      this.minimapRenderListener.init(this, flooredMapCameraX, flooredMapCameraZ);
   }

   public void beforeMinimapRegionRender(MapRegion region) {
      this.minimapRenderListener.beforeMinimapRender(region);
   }

   public void finalizeMinimapRender() {
      this.minimapRenderListener.finalize(this);
   }

   public MapRegion getLeafMapRegion(int caveLayer, int regX, int regZ, boolean create) {
      if (!this.mapSaveLoad.isRegionDetectionComplete()) {
         return null;
      }

      MapDimension mapDimension = this.mapWorld.getCurrentDimension();
      LayeredRegionManager regions = mapDimension.getLayeredMapRegions();
      MapRegion region = regions.getLeaf(caveLayer, regX, regZ);
      if (region == null) {
         if (!create) {
            return null;
         }

         if (!Minecraft.getInstance().isSameThread()) {
            throw new IllegalAccessError();
         }

         ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
         SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
         int globalVersion = (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.GLOBAL_VERSION);
         region = new MapRegion(
            this.currentWorldId,
            this.currentDimId,
            this.currentMWId,
            mapDimension,
            regX,
            regZ,
            caveLayer,
            globalVersion,
            !mapDimension.isUsingWorldSave(),
            this.worldBiomeRegistry
         );
         MapLayer mapLayer = regions.getLayer(caveLayer);
         region.updateCaveMode();
         RegionDetection regionDetection = mapLayer.getRegionDetection(regX, regZ);
         if (regionDetection != null) {
            regionDetection.transferInfoTo(region);
            mapLayer.removeRegionDetection(regX, regZ);
         } else if (mapLayer.getCompleteRegionDetection(regX, regZ) == null) {
            RegionDetection perpetualRegionDetection = new RegionDetection(
               region.getWorldId(), region.getDimId(), region.getMwId(), region.getRegionX(), region.getRegionZ(), region.getRegionFile(), globalVersion, true
            );
            mapLayer.tryAddingToCompleteRegionDetection(perpetualRegionDetection);
            if (!region.isNormalMapData()) {
               mapLayer.removeRegionDetection(regX, regZ);
            }
         }

         if (!region.hasHadTerrain()) {
            regions.getLayer(caveLayer).getRegionHighlightExistenceTracker().stopTracking(regX, regZ);
            region.setVersion(globalVersion);
            region.setCacheHashCode(WorldMap.settings.getRegionCacheHashCode());
            region.setReloadVersion((Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.RELOAD_VIEWED_VERSION));
         }

         regions.putLeaf(regX, regZ, region);
         regions.addListRegion(region);
         if (regionDetection != null) {
            regionDetection.transferInfoPostAddTo(region, this);
         }
      }

      return region;
   }

   public MapRegion getMinimapMapRegion(int regX, int regZ) {
      int renderedCaveLayer = this.minimapRenderListener.getRenderedCaveLayer();
      return this.getLeafMapRegion(renderedCaveLayer, regX, regZ, this.regionExists(renderedCaveLayer, regX, regZ));
   }

   public MapTileChunk getMapChunk(int caveLayer, int chunkX, int chunkZ) {
      int regionX = chunkX >> 3;
      int regionZ = chunkZ >> 3;
      MapRegion region = this.getLeafMapRegion(caveLayer, regionX, regionZ, false);
      if (region == null) {
         return null;
      }

      int localChunkX = chunkX & 7;
      int localChunkZ = chunkZ & 7;
      return region.getChunk(localChunkX, localChunkZ);
   }

   public MapTile getMapTile(int caveLayer, int x, int z) {
      MapTileChunk tileChunk = this.getMapChunk(caveLayer, x >> 2, z >> 2);
      if (tileChunk == null) {
         return null;
      }

      int tileX = x & 3;
      int tileZ = z & 3;
      return tileChunk.getTile(tileX, tileZ);
   }

   public void updateWorldSpawn(BlockPos newSpawn, ClientLevel world) {
      boolean debugConfig = WorldMapClientConfigUtils.getDebug();
      ResourceKey<Level> dimId = world.dimension();
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(world);
      worldData.latestSpawn = newSpawn;
      if (debugConfig) {
         WorldMap.LOGGER.info("Updated spawn for dimension " + dimId + " " + newSpawn);
      }

      this.spawnToRestore = newSpawn;
      if (world == this.mainWorld) {
         this.mainWorldChangedTime = -1L;
         if (debugConfig) {
            WorldMap.LOGGER.info("Done waiting for main spawn.");
         }
      }

      this.checkForWorldUpdate();
   }

   public void onServerLevelId(int serverLevelId) {
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
      worldData.serverLevelId = serverLevelId;
      if (WorldMapClientConfigUtils.getDebug()) {
         WorldMap.LOGGER.info("Updated server level id " + serverLevelId);
      }

      this.checkForWorldUpdate();
   }

   public void onWorldUnload() {
      if (!this.mainWorldUnloaded) {
         if (WorldMapClientConfigUtils.getDebug()) {
            WorldMap.LOGGER.info("Changing worlds, pausing the world map...");
         }

         this.mainWorldUnloaded = true;
         this.mapWorld.clearAllCachedHighlightHashes();
         this.mainWorldChangedTime = -1L;
         this.changeWorld(null, null, null, null, null, null);
      }
   }

   public void onClientTickStart() throws RuntimeException {
      if (this.mainWorld != null
         && this.spawnToRestore != null
         && this.mainWorldChangedTime != -1L
         && System.currentTimeMillis() - this.mainWorldChangedTime >= 3000L) {
         if (WorldMapClientConfigUtils.getDebug()) {
            WorldMap.LOGGER.info("SPAWN SET TIME OUT");
         }

         this.updateWorldSpawn(this.spawnToRestore, this.mainWorld);
      }
   }

   private void updateRenderStartTime() {
      if (this.renderStartTime == -1L) {
         this.renderStartTime = System.nanoTime();
      }
   }

   public void pushWriterPause() {
      synchronized (this.renderThreadPauseSync) {
         this.pauseWriting++;
      }
   }

   public void popWriterPause() {
      synchronized (this.renderThreadPauseSync) {
         this.pauseWriting--;
      }
   }

   public void pushRenderPause(boolean rendering, boolean uploading) {
      synchronized (this.renderThreadPauseSync) {
         if (rendering) {
            this.pauseRendering++;
         }

         if (uploading) {
            this.pauseUploading++;
         }
      }
   }

   public void popRenderPause(boolean rendering, boolean uploading) {
      synchronized (this.renderThreadPauseSync) {
         if (rendering) {
            this.pauseRendering--;
         }

         if (uploading) {
            this.pauseUploading--;
         }
      }
   }

   public void pushIsLoading() {
      synchronized (this.loadingSync) {
         this.isLoading = true;
      }
   }

   public void popIsLoading() {
      synchronized (this.loadingSync) {
         this.isLoading = false;
      }
   }

   public void pushUIPause() {
      synchronized (this.uiPauseSync) {
         this.isUIPaused = true;
      }
   }

   public void popUIPause() {
      synchronized (this.uiPauseSync) {
         this.isUIPaused = false;
      }
   }

   public boolean isUIPaused() {
      return this.isUIPaused;
   }

   public boolean isWritingPaused() {
      return this.pauseWriting > 0;
   }

   public boolean isRenderingPaused() {
      return this.pauseRendering > 0;
   }

   public boolean isUploadingPaused() {
      return this.pauseUploading > 0;
   }

   public boolean isProcessingPaused() {
      return this.pauseProcessing > 0;
   }

   public boolean isProcessed(LeveledRegion<?> region) {
      ArrayList<LeveledRegion<?>> toProcess = this.toProcessLevels[region.getLevel()];
      synchronized (toProcess) {
         return toProcess.contains(region);
      }
   }

   public void addToProcess(LeveledRegion<?> region) {
      ArrayList<LeveledRegion<?>> toProcess = this.toProcessLevels[region.getLevel()];
      synchronized (toProcess) {
         toProcess.add(region);
      }
   }

   public void removeToProcess(LeveledRegion<?> region) {
      ArrayList<LeveledRegion<?>> toProcess = this.toProcessLevels[region.getLevel()];
      synchronized (toProcess) {
         toProcess.remove(region);
      }
   }

   public int getProcessedCount() {
      int total = 0;

      for (int i = 0; i < this.toProcessLevels.length; i++) {
         total += this.toProcessLevels[i].size();
      }

      return total;
   }

   public int getAffectingLoadingFrequencyCount() {
      int total = 0;

      for (int i = 0; i < this.toProcessLevels.length; i++) {
         ArrayList<LeveledRegion<?>> processed = this.toProcessLevels[i];

         for (int j = 0; j < processed.size(); j++) {
            synchronized (processed) {
               if (j >= processed.size()) {
                  break;
               }

               if (processed.get(j).shouldAffectLoadingRequestFrequency()) {
                  total++;
               }
            }
         }
      }

      return total;
   }

   public MapSaveLoad getMapSaveLoad() {
      return this.mapSaveLoad;
   }

   public ClientLevel getWorld() {
      return this.world;
   }

   public ClientLevel getNewWorld() {
      return this.newWorld;
   }

   public String getCurrentWorldId() {
      return this.currentWorldId;
   }

   public String getCurrentDimId() {
      return this.currentDimId;
   }

   public String getCurrentMWId() {
      return this.currentMWId;
   }

   public MapWriter getMapWriter() {
      return this.mapWriter;
   }

   public MapLimiter getMapLimiter() {
      return this.mapLimiter;
   }

   public ArrayList<Double[]> getFootprints() {
      return this.footprints;
   }

   public ByteBufferDeallocator getBufferDeallocator() {
      return this.bufferDeallocator;
   }

   public MapTilePool getTilePool() {
      return this.tilePool;
   }

   public OverlayManager getOverlayManager() {
      return this.overlayManager;
   }

   public int getGlobalVersion() {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      return (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.GLOBAL_VERSION);
   }

   public long getRenderStartTime() {
      return this.renderStartTime;
   }

   public void resetRenderStartTime() {
      this.renderStartTime = -1L;
   }

   public Queue<Runnable> getMinecraftScheduledTasks() {
      this.scheduledTasksField.setAccessible(true);

      Queue<Runnable> result;
      try {
         result = (Queue<Runnable>)this.scheduledTasksField.get(Minecraft.getInstance());
      } catch (IllegalArgumentException e) {
         result = null;
      } catch (IllegalAccessException e) {
         result = null;
      }

      this.scheduledTasksField.setAccessible(false);
      return result;
   }

   public Runnable getRenderStartTimeUpdater() {
      return this.renderStartTimeUpdaterRunnable;
   }

   public boolean isWaitingForWorldUpdate() {
      return this.waitingForWorldUpdate;
   }

   public WorldDataHandler getWorldDataHandler() {
      return this.worldDataHandler;
   }

   public void setMainValues() {
      synchronized (this.mainStuffSync) {
         Entity player = Minecraft.getInstance().getCameraEntity();
         if (player != null) {
            ClientLevel worldToChangeTo = !this.ignoreWorld(player.level()) && player.level() instanceof ClientLevel
               ? (ClientLevel)player.level()
               : this.mainWorld;
            boolean worldChanging = worldToChangeTo != this.mainWorld;
            if (worldChanging) {
               this.mainWorldChangedTime = -1L;
               if (this.spawnToRestore != null) {
                  WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(worldToChangeTo);
                  if (worldData.latestSpawn == null) {
                     this.mainWorldChangedTime = System.currentTimeMillis();
                  }
               }

               this.mainWorldUnloaded = false;
               this.mainWorldBlockLookup = worldToChangeTo == null ? null : worldToChangeTo.holderLookup(Registries.BLOCK);
               this.mainWorldBlockRegistry = worldToChangeTo == null ? null : worldToChangeTo.registryAccess().registryOrThrow(Registries.BLOCK);
               this.mainWorldFluidRegistry = worldToChangeTo == null ? null : worldToChangeTo.registryAccess().registryOrThrow(Registries.FLUID);
               this.mainWorldBiomeRegistry = worldToChangeTo == null ? null : worldToChangeTo.registryAccess().registryOrThrow(Registries.BIOME);
               this.mainWorldDimensionTypeRegistry = worldToChangeTo == null
                  ? null
                  : worldToChangeTo.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE);
            }

            this.mainWorld = worldToChangeTo;
            this.mainPlayerX = player.getX();
            this.mainPlayerY = player.getY();
            this.mainPlayerZ = player.getZ();
            if (worldChanging) {
               this.checkForWorldUpdate();
            }
         } else {
            if (this.mainWorld != null && !this.mainWorldUnloaded) {
               this.onWorldUnload();
            }

            this.mainWorld = null;
         }
      }
   }

   public float getBrightness() {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      return this.getBrightness((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.LIGHTING));
   }

   public float getBrightness(boolean lighting) {
      return this.getBrightness(this.currentCaveLayer, this.world, lighting);
   }

   public float getBrightness(int layer, ClientLevel world, boolean lighting) {
      if (world != null && world == this.world) {
         MapDimension dim = this.mapWorld.getCurrentDimension();
         DimensionType dimType = dim.getDimensionType(this.worldDimensionTypeRegistry);
         float sunBrightness;
         if (layer == Integer.MAX_VALUE || dimType != null && !dimType.hasSkyLight()) {
            if (!lighting) {
               return 1.0F;
            }

            if (dimType != null && dim.getDimensionEffects(this.worldDimensionTypeRegistry).forceBrightLightmap()) {
               return 1.0F;
            }

            sunBrightness = (dim.getSkyDarken(1.0F, world, this.worldDimensionTypeRegistry) - 0.2F) / 0.8F;
         } else {
            if (!lighting) {
               return 1.0F;
            }

            sunBrightness = 0.0F;
         }

         float ambient = this.getAmbientBrightness(dimType);
         return ambient + (1.0F - ambient) * Mth.clamp(sunBrightness, 0.0F, 1.0F);
      } else {
         return 1.0F;
      }
   }

   public float getAmbientBrightness(DimensionType dimType) {
      float result = 0.375F + (dimType == null ? 0.0F : dimType.ambientLight());
      if (result > 1.0F) {
         result = 1.0F;
      }

      return result;
   }

   public static boolean isWorldRealms(String world) {
      return world.startsWith("Realms_");
   }

   public static boolean isWorldMultiplayer(boolean realms, String world) {
      return realms || world.startsWith("Multiplayer_");
   }

   public MapWorld getMapWorld() {
      return this.mapWorld;
   }

   public boolean isCurrentMultiworldWritable() {
      return this.mapWorldUsable && this.mapWorld.getCurrentDimension().currentMultiworldWritable;
   }

   public String getCurrentDimension() {
      return "placeholder";
   }

   public void requestCurrentMapDeletion() {
      if (this.currentMapNeedsDeletion) {
         throw new RuntimeException("Requesting map deletion at a weird time!");
      }

      this.currentMapNeedsDeletion = true;
   }

   public boolean isFinalizing() {
      return this.finalizing;
   }

   public void stop() {
      this.finalizing = true;
      WorldMap.mapRunner.addTask(new MapRunnerTask() {
         @Override
         public void run(MapProcessor doNotUse) {
            if (MapProcessor.this.state == 0) {
               MapProcessor.this.state = 1;
               if (!MapProcessor.this.mapWorldUsable) {
                  MapProcessor.this.forceClean();
               } else {
                  MapProcessor.this.changeWorld(null, null, null, null, null, null);
               }
            }
         }
      });
   }

   private synchronized void forceClean() {
      this.pushRenderPause(true, true);
      this.pushWriterPause();
      if (this.mapWorld != null) {
         for (MapDimension dim : this.mapWorld.getDimensionsList()) {
            for (LeveledRegion<?> region : dim.getLayeredMapRegions().getUnsyncedSet()) {
               region.onDimensionClear(this);
            }
         }
      }

      this.popRenderPause(true, true);
      this.popWriterPause();
      if (this.currentMapLock != null) {
         if (this.mapLockToRelease != null) {
            this.releaseLocksIfNeeded();
         }

         this.mapLockToRelease = this.currentMapLock;
         this.mapLockChannelToClose = this.currentMapLockChannel;
         this.releaseLocksIfNeeded();
      }

      this.state = 2;
      WorldMap.LOGGER.info("World map force-cleaned!");
   }

   public boolean isMapWorldUsable() {
      return this.mapWorldUsable;
   }

   private Object getAutoIdBase(ClientLevel world) {
      return this.hasServerLevelId()
         ? WorldMapClientWorldDataHelper.getCurrentWorldData().serverLevelId
         : WorldMapClientWorldDataHelper.getWorldData(world).latestSpawn;
   }

   private Object getUsedAutoIdBase(ClientLevel world) {
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(world);
      return this.hasServerLevelId() ? WorldMapClientWorldDataHelper.getCurrentWorldData().usedServerLevelId : worldData.usedSpawn;
   }

   private void setUsedAutoIdBase(ClientLevel world, Object autoIdBase) {
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(world);
      if (this.hasServerLevelId()) {
         WorldMapClientWorldDataHelper.getCurrentWorldData().usedServerLevelId = (Integer)autoIdBase;
      } else {
         worldData.usedSpawn = (BlockPos)autoIdBase;
      }
   }

   private void removeUsedAutoIdBase(ClientLevel world) {
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(world);
      if (this.hasServerLevelId()) {
         WorldMapClientWorldDataHelper.getCurrentWorldData().usedServerLevelId = null;
      } else {
         worldData.usedSpawn = null;
      }
   }

   private boolean hasServerLevelId() {
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
      return worldData == null ? false : worldData.serverLevelId != null && !this.mapWorld.isIgnoreServerLevelId();
   }

   public boolean isEqual(String worldId, String dimId, String mwId) {
      return worldId.equals(this.currentWorldId)
         && dimId.equals(this.currentDimId)
         && (mwId == this.currentMWId || mwId != null && mwId.equals(this.currentMWId));
   }

   public boolean isFinished() {
      return this.state == 3;
   }

   public MultiTextureRenderTypeRendererProvider getMultiTextureRenderTypeRenderers() {
      return this.multiTextureRenderTypeRenderers;
   }

   public CustomVertexConsumers getCvc() {
      return this.cvc;
   }

   public boolean isCurrentMapLocked() {
      return this.currentMapLock == null;
   }

   private void releaseLocksIfNeeded() {
      if (this.mapLockToRelease != null) {
         int lockAttempts = 10;

         while (lockAttempts-- > 0) {
            try {
               if (this.mapLockToRelease.isValid()) {
                  this.mapLockToRelease.release();
               }

               this.mapLockChannelToClose.close();
               break;
            } catch (Exception e) {
               WorldMap.LOGGER.error("Failed attempt to release the lock for the world map! Retrying in 50 ms... " + lockAttempts, e);

               try {
                  Thread.sleep(50L);
               } catch (InterruptedException var3) {
               }
            }
         }

         this.mapLockToRelease = null;
         this.mapLockChannelToClose = null;
      }
   }

   private int getCaveLayer(int caveStart) {
      return caveStart != Integer.MAX_VALUE && caveStart != Integer.MIN_VALUE ? caveStart >> 4 : caveStart;
   }

   public int getCurrentCaveLayer() {
      return this.currentCaveLayer;
   }

   public BlockStateShortShapeCache getBlockStateShortShapeCache() {
      return this.blockStateShortShapeCache;
   }

   public BlockTintProvider getWorldBlockTintProvider() {
      return this.worldBlockTintProvider;
   }

   public HighlighterRegistry getHighlighterRegistry() {
      return this.highlighterRegistry;
   }

   public MapRegionHighlightsPreparer getMapRegionHighlightsPreparer() {
      return this.mapRegionHighlightsPreparer;
   }

   public MessageBox getMessageBox() {
      return this.messageBox;
   }

   public MessageBoxRenderer getMessageBoxRenderer() {
      return this.messageBoxRenderer;
   }

   public Registry<Block> getWorldBlockRegistry() {
      return this.worldBlockRegistry;
   }

   public HolderLookup<Block> getWorldBlockLookup() {
      return this.worldBlockLookup;
   }

   public boolean isConsideringNetherFairPlay() {
      return this.consideringNetherFairPlayMessage;
   }

   public void setConsideringNetherFairPlayMessage(boolean consideringNetherFairPlay) {
      this.consideringNetherFairPlayMessage = consideringNetherFairPlay;
   }

   public BiomeColorCalculator getBiomeColorCalculator() {
      return this.biomeColorCalculator;
   }

   public ClientSyncedTrackedPlayerManager getClientSyncedTrackedPlayerManager() {
      return this.clientSyncedTrackedPlayerManager;
   }

   public boolean serverHasMod() {
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
      return worldData != null && worldData.serverLevelId != null;
   }

   public void setServerModNetworkVersion(int networkVersion) {
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
      if (worldData != null) {
         worldData.setServerModNetworkVersion(networkVersion);
      }
   }

   public int getServerModNetworkVersion() {
      WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
      return worldData == null ? 0 : worldData.getServerModNetworkVersion();
   }

   public Registry<DimensionType> getWorldDimensionTypeRegistry() {
      return this.worldDimensionTypeRegistry;
   }

   private void checkFootstepsReset(Level oldWorld, Level newWorld) {
      ResourceKey<Level> oldDimId = oldWorld == null ? null : oldWorld.dimension();
      ResourceKey<Level> newDimId = newWorld == null ? null : newWorld.dimension();
      if (oldDimId != newDimId) {
         this.footprints.clear();
      }
   }

   private void fixRootFolder(String mainId, ClientPacketListener connection) {
      for (int format = 4; format >= 1; format--) {
         this.fixRootFolder(mainId, this.getMainId(format, connection));
      }
   }

   private void fixRootFolder(String mainId, String oldMainId) {
      if (!mainId.equals(oldMainId)) {
         Path oldFolder;
         try {
            oldFolder = WorldMap.saveFolder.toPath().resolve(oldMainId);
         } catch (InvalidPathException ipe) {
            return;
         }

         if (Files.exists(oldFolder)) {
            Path fixedFolder = WorldMap.saveFolder.toPath().resolve(mainId);
            if (!Files.exists(fixedFolder)) {
               try {
                  Files.move(oldFolder, fixedFolder);
               } catch (IOException e) {
                  throw new RuntimeException("failed to auto-restore old world map folder", e);
               }
            }
         }
      }
   }

   public boolean fairplayMessageWasReceived() {
      return this.fairplayMessageReceived;
   }

   public void setFairplayMessageReceived(boolean fairplayMessageReceived) {
      this.fairplayMessageReceived = fairplayMessageReceived;
   }

   public void updateMapItem() {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      String mapItemString = ((String)configManager.getEffective(WorldMapProfiledConfigOptions.MAP_ITEM)).trim();
      if (!mapItemString.isEmpty() && !mapItemString.equals("-")) {
         ResourceLocation mapItemRL;
         try {
            mapItemRL = ResourceLocation.parse(mapItemString);
         } catch (ResourceLocationException rle) {
            WorldMap.LOGGER.error("Tried setting the full screen map required item to a misformatted ID: {}, Error: {}", mapItemString, rle.getMessage());
            this.mapItem = null;
            return;
         }

         this.mapItem = (Item)BuiltInRegistries.ITEM.get(mapItemRL);
         if (this.mapItem == Items.AIR) {
            this.mapItem = null;
            WorldMap.LOGGER.error("Tried setting the full screen map required item to an invalid ID: {}", mapItemString);
         } else {
            WorldMap.LOGGER.info("Fullscreen map required item set to: {}", mapItemString);
         }
      } else {
         this.mapItem = null;
         WorldMap.LOGGER.info("Fullscreen map required item set to nothing.");
      }
   }

   public Item getMapItem() {
      return this.mapItem;
   }
}
