package xaero.map;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.reflection.util.ReflectionUtils;
import xaero.lib.common.util.ImageIOUtils;
import xaero.map.biome.BiomeColorCalculator;
import xaero.map.biome.BiomeGetter;
import xaero.map.biome.BlockTintProvider;
import xaero.map.cache.BlockStateShortShapeCache;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.core.XaeroWorldMapCore;
import xaero.map.exception.SilentException;
import xaero.map.gui.GuiMap;
import xaero.map.misc.CachedFunction;
import xaero.map.misc.Misc;
import xaero.map.mods.SupportMods;
import xaero.map.region.LeveledRegion;
import xaero.map.region.MapBlock;
import xaero.map.region.MapRegion;
import xaero.map.region.MapTile;
import xaero.map.region.MapTileChunk;
import xaero.map.region.MapUpdateFastConfig;
import xaero.map.region.OverlayBuilder;
import xaero.map.region.OverlayManager;

public abstract class MapWriter {
   public static final int NO_Y_VALUE = 32767;
   public static final int MAX_TRANSPARENCY_BLEND_DEPTH = 5;
   public static final String[] DEFAULT_RESOURCE = new String[]{"minecraft", ""};
   private int X;
   private int Z;
   private int playerChunkX;
   private int playerChunkZ;
   private int loadDistance;
   private int startTileChunkX;
   private int startTileChunkZ;
   private int endTileChunkX;
   private int endTileChunkZ;
   private int insideX;
   private int insideZ;
   private long updateCounter;
   private int caveStart;
   private int writingLayer = Integer.MAX_VALUE;
   private int writtenCaveStart = Integer.MAX_VALUE;
   private boolean clearCachedColours;
   private MapBlock loadingObject;
   private OverlayBuilder overlayBuilder;
   private final MutableBlockPos mutableLocalPos;
   private final MutableBlockPos mutableGlobalPos;
   protected final RandomSource usedRandom = RandomSource.create(0L);
   private long lastWrite = -1L;
   private long lastWriteTry = -1L;
   private int workingFrameCount;
   private long framesFreedTime = -1L;
   public long writeFreeSinceLastWrite = -1L;
   private int writeFreeSizeTiles;
   private int writeFreeFullUpdateTargetTime;
   private MapProcessor mapProcessor;
   private ArrayList<BlockState> buggedStates;
   private BlockStateShortShapeCache blockStateShortShapeCache;
   private int topH;
   private final CachedFunction<StateHolder<?, ?>, Boolean> transparentCache;
   private int firstTransparentStateY;
   private final MutableBlockPos mutableBlockPos3;
   private CachedFunction<FluidState, BlockState> fluidToBlock;
   private BiomeGetter biomeGetter;
   private ArrayList<MapRegion> regionBuffer = new ArrayList<>();
   private MapTileChunk rightChunk = null;
   private MapTileChunk bottomRightChunk = null;
   private HashMap<String, Integer> textureColours;
   private HashMap<BlockState, Integer> blockColours;
   private final Object2IntMap<BlockState> blockTintIndices;
   private long lastLayerSwitch;
   private BlockState lastBlockStateForTextureColor = null;
   private int lastBlockStateForTextureColorResult = -1;

   public MapWriter(OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache, BiomeGetter biomeGetter) {
      this.loadingObject = new MapBlock();
      this.textureColours = new HashMap<>();
      this.blockColours = new HashMap<>();
      this.overlayBuilder = new OverlayBuilder(overlayManager);
      this.mutableLocalPos = new MutableBlockPos();
      this.mutableGlobalPos = new MutableBlockPos();
      this.buggedStates = new ArrayList<>();
      this.blockStateShortShapeCache = blockStateShortShapeCache;
      this.transparentCache = new CachedFunction<>(state -> this.shouldOverlay(state));
      this.mutableBlockPos3 = new MutableBlockPos();
      this.fluidToBlock = new CachedFunction<>(FluidState::createLegacyBlock);
      this.biomeGetter = biomeGetter;
      this.blockTintIndices = new Object2IntOpenHashMap();
   }

   protected abstract boolean blockStateHasTranslucentRenderType(BlockState var1);

   public void onRender(BiomeColorCalculator biomeColorCalculator, OverlayManager overlayManager) {
      long before = System.nanoTime();

      try {
         if (WorldMap.crashHandler.getCrashedBy() == null) {
            synchronized (this.mapProcessor.renderThreadPauseSync) {
               if (!this.mapProcessor.isWritingPaused()
                  && !this.mapProcessor.isWaitingForWorldUpdate()
                  && this.mapProcessor.getMapSaveLoad().isRegionDetectionComplete()
                  && this.mapProcessor.isCurrentMultiworldWritable()) {
                  ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
                  boolean loadChunksConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.LOAD_NEW_CHUNKS);
                  boolean updateChunksConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.UPDATE_CHUNKS);
                  if (this.mapProcessor.getWorld() == null || this.mapProcessor.isCurrentMapLocked() || this.mapProcessor.getMapWorld().isCacheOnlyMode()) {
                     return;
                  }

                  if (this.mapProcessor.getCurrentWorldId() != null
                     && !this.mapProcessor.ignoreWorld(this.mapProcessor.getWorld())
                     && (updateChunksConfig || loadChunksConfig || this.mapProcessor.getMapWorld().getCurrentDimension().isUsingWorldSave())) {
                     double playerX;
                     double playerY;
                     double playerZ;
                     synchronized (this.mapProcessor.mainStuffSync) {
                        if (this.mapProcessor.mainWorld != this.mapProcessor.getWorld()) {
                           return;
                        }

                        if (this.mapProcessor.getWorld().dimension() != this.mapProcessor.getMapWorld().getCurrentDimensionId()) {
                           return;
                        }

                        playerX = this.mapProcessor.mainPlayerX;
                        playerY = this.mapProcessor.mainPlayerY;
                        playerZ = this.mapProcessor.mainPlayerZ;
                     }

                     XaeroWorldMapCore.ensureField();
                     int lengthX = this.endTileChunkX - this.startTileChunkX + 1;
                     int lengthZ = this.endTileChunkZ - this.startTileChunkZ + 1;
                     if (this.lastWriteTry == -1L) {
                        lengthX = 3;
                        lengthZ = 3;
                     }

                     int sizeTileChunks = lengthX * lengthZ;
                     int sizeTiles = sizeTileChunks * 4 * 4;
                     int sizeBasedTargetTime = sizeTiles * 1000 / 1500;
                     int fullUpdateTargetTime = Math.max(100, sizeBasedTargetTime);
                     long time = System.currentTimeMillis();
                     long passed = this.lastWrite == -1L ? 0L : time - this.lastWrite;
                     if (this.lastWriteTry == -1L
                        || this.writeFreeSizeTiles != sizeTiles
                        || this.writeFreeFullUpdateTargetTime != fullUpdateTargetTime
                        || this.workingFrameCount > 30) {
                        this.framesFreedTime = time;
                        this.writeFreeSizeTiles = sizeTiles;
                        this.writeFreeFullUpdateTargetTime = fullUpdateTargetTime;
                        this.workingFrameCount = 0;
                     }

                     long sinceLastWrite = Math.min(passed, this.writeFreeSinceLastWrite);
                     if (this.framesFreedTime != -1L) {
                        sinceLastWrite = time - this.framesFreedTime;
                     }

                     long tilesToUpdate = Math.min(sinceLastWrite * sizeTiles / fullUpdateTargetTime, 100L);
                     if (this.lastWrite == -1L || tilesToUpdate != 0L) {
                        this.lastWrite = time;
                     }

                     if (tilesToUpdate != 0L) {
                        if (this.framesFreedTime != -1L) {
                           this.writeFreeSinceLastWrite = sinceLastWrite;
                           this.framesFreedTime = -1L;
                        } else {
                           int timeLimit = (int)(Math.min(sinceLastWrite, 50L) * 86960L);
                           long writeStartNano = System.nanoTime();
                           Registry<Biome> biomeRegistry = this.mapProcessor.worldBiomeRegistry;
                           boolean loadChunks = loadChunksConfig || this.mapProcessor.getMapWorld().getCurrentDimension().isUsingWorldSave();
                           boolean updateChunks = updateChunksConfig || this.mapProcessor.getMapWorld().getCurrentDimension().isUsingWorldSave();
                           boolean ignoreHeightmaps = this.mapProcessor.getMapWorld().isIgnoreHeightmaps();
                           boolean flowers = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.FLOWERS);
                           boolean detailedDebug = WorldMap.detailed_debug;
                           int caveDepth = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.CAVE_MODE_DEPTH);
                           MutableBlockPos mutableBlockPos3 = this.mutableBlockPos3;
                           BlockTintProvider blockTintProvider = this.mapProcessor.getWorldBlockTintProvider();
                           MapUpdateFastConfig config = new MapUpdateFastConfig();
                           ClientLevel world = this.mapProcessor.getWorld();
                           Registry<Block> blockRegistry = this.mapProcessor.getWorldBlockRegistry();

                           for (int i = 0; i < tilesToUpdate; i++) {
                              if (this.writeMap(
                                 world,
                                 blockRegistry,
                                 playerX,
                                 playerY,
                                 playerZ,
                                 biomeRegistry,
                                 biomeColorCalculator,
                                 overlayManager,
                                 loadChunks,
                                 updateChunks,
                                 ignoreHeightmaps,
                                 flowers,
                                 detailedDebug,
                                 mutableBlockPos3,
                                 blockTintProvider,
                                 caveDepth,
                                 config
                              )) {
                                 i--;
                              }

                              if (System.nanoTime() - writeStartNano >= timeLimit) {
                                 break;
                              }
                           }

                           this.workingFrameCount++;
                        }
                     }

                     this.lastWriteTry = time;
                     int startRegionX = this.startTileChunkX >> 3;
                     int startRegionZ = this.startTileChunkZ >> 3;
                     int endRegionX = this.endTileChunkX >> 3;
                     int endRegionZ = this.endTileChunkZ >> 3;
                     boolean shouldRequestLoading = false;
                     LeveledRegion<?> nextToLoad = this.mapProcessor.getMapSaveLoad().getNextToLoadByViewing();
                     if (nextToLoad != null) {
                        shouldRequestLoading = nextToLoad.shouldAllowAnotherRegionToLoad();
                     } else {
                        shouldRequestLoading = true;
                     }

                     this.regionBuffer.clear();
                     int comparisonChunkX = this.playerChunkX - 16;
                     int comparisonChunkZ = this.playerChunkZ - 16;
                     LeveledRegion.setComparison(comparisonChunkX, comparisonChunkZ, 0, comparisonChunkX, comparisonChunkZ);

                     for (int visitRegionX = startRegionX; visitRegionX <= endRegionX; visitRegionX++) {
                        for (int visitRegionZ = startRegionZ; visitRegionZ <= endRegionZ; visitRegionZ++) {
                           MapRegion visitRegion = this.mapProcessor.getLeafMapRegion(this.writingLayer, visitRegionX, visitRegionZ, true);
                           if (visitRegion != null && visitRegion.getLoadState() == 2) {
                              visitRegion.registerVisit();
                           }

                           synchronized (visitRegion) {
                              if (visitRegion.isResting() && shouldRequestLoading && visitRegion.canRequestReload_unsynced() && visitRegion.getLoadState() != 2
                                 )
                               {
                                 visitRegion.calculateSortingChunkDistance();
                                 Misc.addToListOfSmallest(10, this.regionBuffer, visitRegion);
                              }
                           }
                        }
                     }

                     int toRequest = 1;
                     int counter = 0;

                     for (int i = 0; i < this.regionBuffer.size() && counter < toRequest; i++) {
                        MapRegion region = this.regionBuffer.get(i);
                        if (region != nextToLoad || this.regionBuffer.size() <= 1) {
                           synchronized (region) {
                              if (region.canRequestReload_unsynced() && region.getLoadState() != 2) {
                                 region.setBeingWritten(true);
                                 this.mapProcessor.getMapSaveLoad().requestLoad(region, "writing");
                                 if (counter == 0) {
                                    this.mapProcessor.getMapSaveLoad().setNextToLoadByViewing(region);
                                 }

                                 counter++;
                                 if (region.getLoadState() == 4) {
                                    return;
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               return;
            }
         }
      } catch (Throwable e) {
         WorldMap.crashHandler.setCrashedBy(e);
      }
   }

   private int getWriteDistance() {
      int limit = this.mapProcessor.getMapWorld().getCurrentDimension().isUsingWorldSave()
         ? Integer.MAX_VALUE
         : (Integer)WorldMap.INSTANCE.getConfigs().getClientConfigManager().getEffective(WorldMapProfiledConfigOptions.WRITING_DISTANCE);
      if (limit < 0) {
         limit = Integer.MAX_VALUE;
      }

      return Math.min(limit, Math.min(32, (Integer)Minecraft.getInstance().options.renderDistance().get()));
   }

   public boolean writeMap(
      Level world,
      Registry<Block> blockRegistry,
      double playerX,
      double playerY,
      double playerZ,
      Registry<Biome> biomeRegistry,
      BiomeColorCalculator biomeColorCalculator,
      OverlayManager overlayManager,
      boolean loadChunks,
      boolean updateChunks,
      boolean ignoreHeightmaps,
      boolean flowers,
      boolean detailedDebug,
      MutableBlockPos mutableBlockPos3,
      BlockTintProvider blockTintProvider,
      int caveDepth,
      MapUpdateFastConfig config
   ) {
      boolean onlyLoad = loadChunks && (!updateChunks || this.updateCounter % 5L != 0L);
      synchronized (world) {
         if (this.insideX == 0 && this.insideZ == 0) {
            if (this.X == 0 && this.Z == 0) {
               this.writtenCaveStart = this.caveStart;
            }

            this.mapProcessor.updateCaveStart();
            int newWritingLayer = this.mapProcessor.getCurrentCaveLayer();
            if (this.writingLayer != newWritingLayer && System.currentTimeMillis() - this.lastLayerSwitch > 300L) {
               this.writingLayer = newWritingLayer;
               this.lastLayerSwitch = System.currentTimeMillis();
            }

            this.loadDistance = this.getWriteDistance();
            if (this.writingLayer != Integer.MAX_VALUE && !(Minecraft.getInstance().screen instanceof GuiMap)) {
               this.loadDistance = Math.min(16, this.loadDistance);
            }

            this.caveStart = this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().getLayer(this.writingLayer).getCaveStart();
            if (this.caveStart != this.writtenCaveStart) {
               this.loadDistance = Math.min(4, this.loadDistance);
            }

            this.playerChunkX = (int)Math.floor(playerX) >> 4;
            this.playerChunkZ = (int)Math.floor(playerZ) >> 4;
            this.startTileChunkX = this.playerChunkX - this.loadDistance >> 2;
            this.startTileChunkZ = this.playerChunkZ - this.loadDistance >> 2;
            this.endTileChunkX = this.playerChunkX + this.loadDistance >> 2;
            this.endTileChunkZ = this.playerChunkZ + this.loadDistance >> 2;
         }

         int tileChunkX = this.startTileChunkX + this.X;
         int tileChunkZ = this.startTileChunkZ + this.Z;
         int tileChunkLocalX = tileChunkX & 7;
         int tileChunkLocalZ = tileChunkZ & 7;
         int chunkX = tileChunkX * 4 + this.insideX;
         int chunkZ = tileChunkZ * 4 + this.insideZ;
         boolean wasSkipped = this.writeChunk(
            world,
            blockRegistry,
            this.loadDistance,
            onlyLoad,
            biomeRegistry,
            overlayManager,
            loadChunks,
            updateChunks,
            ignoreHeightmaps,
            flowers,
            detailedDebug,
            mutableBlockPos3,
            blockTintProvider,
            caveDepth,
            this.caveStart,
            this.writingLayer,
            tileChunkX,
            tileChunkZ,
            tileChunkLocalX,
            tileChunkLocalZ,
            chunkX,
            chunkZ,
            config
         );
         return wasSkipped && (Math.abs(chunkX - this.playerChunkX) > 8 || Math.abs(chunkZ - this.playerChunkZ) > 8);
      }
   }

   public boolean writeChunk(
      Level world,
      Registry<Block> blockRegistry,
      int distance,
      boolean onlyLoad,
      Registry<Biome> biomeRegistry,
      OverlayManager overlayManager,
      boolean loadChunks,
      boolean updateChunks,
      boolean ignoreHeightmaps,
      boolean flowers,
      boolean detailedDebug,
      MutableBlockPos mutableBlockPos3,
      BlockTintProvider blockTintProvider,
      int caveDepth,
      int caveStart,
      int layerToWrite,
      int tileChunkX,
      int tileChunkZ,
      int tileChunkLocalX,
      int tileChunkLocalZ,
      int chunkX,
      int chunkZ,
      MapUpdateFastConfig updateConfig
   ) {
      int regionX = tileChunkX >> 3;
      int regionZ = tileChunkZ >> 3;
      MapTileChunk tileChunk = null;
      this.rightChunk = null;
      MapTileChunk bottomChunk = null;
      this.bottomRightChunk = null;
      int worldBottomY = world.getMinBuildHeight();
      int worldTopY = world.getMaxBuildHeight();
      MapRegion region = this.mapProcessor.getLeafMapRegion(layerToWrite, regionX, regionZ, true);
      boolean wasSkipped = true;
      synchronized (region.writerThreadPauseSync) {
         if (!region.isWritingPaused()) {
            boolean createdTileChunk = false;
            boolean regionIsResting;
            boolean isProperLoadState;
            synchronized (region) {
               isProperLoadState = region.getLoadState() == 2;
               if (isProperLoadState) {
                  region.registerVisit();
               }

               regionIsResting = region.isResting();
               if (regionIsResting) {
                  region.setBeingWritten(true);
                  tileChunk = region.getChunk(tileChunkLocalX, tileChunkLocalZ);
                  if (isProperLoadState && tileChunk == null) {
                     region.setChunk(tileChunkLocalX, tileChunkLocalZ, tileChunk = new MapTileChunk(region, tileChunkX, tileChunkZ));
                     tileChunk.setLoadState((byte)2);
                     region.setAllCachePrepared(false);
                     createdTileChunk = true;
                  }

                  if (!region.isNormalMapData()) {
                     region.getDim().getLayeredMapRegions().applyToEachLoadedLayer((i, layer) -> {
                        if (i != region.getCaveLayer()) {
                           MapRegion sameRegionAnotherLayer = this.mapProcessor.getLeafMapRegion(i, regionX, regionZ, true);
                           sameRegionAnotherLayer.setOutdatedWithOtherLayers(true);
                           sameRegionAnotherLayer.setHasHadTerrain();
                        }
                     });
                  }
               }
            }

            if (regionIsResting && isProperLoadState) {
               if (tileChunk != null && tileChunk.getLoadState() == 2) {
                  if (!tileChunk.getLeafTexture().shouldUpload()) {
                     boolean cave = caveStart != Integer.MAX_VALUE;
                     boolean fullCave = caveStart == Integer.MIN_VALUE;
                     int lowH = worldBottomY;
                     if (cave && !fullCave) {
                        lowH = caveStart + 1 - caveDepth;
                        if (lowH < worldBottomY) {
                           lowH = worldBottomY;
                        }
                     }

                     if (chunkX >= this.playerChunkX - distance
                        && chunkX <= this.playerChunkX + distance
                        && chunkZ >= this.playerChunkZ - distance
                        && chunkZ <= this.playerChunkZ + distance) {
                        LevelChunk chunk = (LevelChunk)world.getChunk(chunkX, chunkZ, ChunkStatus.FULL, false);
                        MapTile mapTile = tileChunk.getTile(this.insideX, this.insideZ);
                        boolean chunkUpdated = false;

                        try {
                           chunkUpdated = chunk != null
                              && (
                                 mapTile == null
                                    || mapTile.getWrittenCaveStart() != caveStart
                                    || mapTile.getWrittenCaveDepth() != caveDepth
                                    || !(Boolean)XaeroWorldMapCore.chunkCleanField.get(chunk)
                              );
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                           throw new RuntimeException(e);
                        }

                        if (chunkUpdated && !(chunk instanceof EmptyLevelChunk)) {
                           boolean edgeChunk = false;

                           label365:
                           for (int i = -1; i < 2; i++) {
                              for (int j = -1; j < 2; j++) {
                                 if (i != 0 || j != 0) {
                                    LevelChunk neighbor = world.getChunk(chunkX + i, chunkZ + j);
                                    if (neighbor == null || neighbor instanceof EmptyLevelChunk) {
                                       edgeChunk = true;
                                       break label365;
                                    }
                                 }
                              }
                           }

                           if (!edgeChunk
                              && (
                                 mapTile == null && loadChunks
                                    || mapTile != null
                                       && updateChunks
                                       && (!onlyLoad || mapTile.getWrittenCaveStart() != caveStart || mapTile.getWrittenCaveDepth() != caveDepth)
                              )) {
                              wasSkipped = false;
                              if (mapTile == null) {
                                 mapTile = this.mapProcessor.getTilePool().get(this.mapProcessor.getCurrentDimension(), chunkX, chunkZ);
                                 tileChunk.setChanged(true);
                              }

                              MapTileChunk prevTileChunk = tileChunk.getNeighbourTileChunk(0, -1, this.mapProcessor, false);
                              MapTileChunk prevTileChunkDiagonal = tileChunk.getNeighbourTileChunk(-1, -1, this.mapProcessor, false);
                              MapTileChunk prevTileChunkHorisontal = tileChunk.getNeighbourTileChunk(-1, 0, this.mapProcessor, false);
                              int sectionBasedHeight = this.getSectionBasedHeight(chunk, 64);
                              Types typeWorldSurface = Types.WORLD_SURFACE;
                              MapTile bottomTile = this.insideZ < 3 ? tileChunk.getTile(this.insideX, this.insideZ + 1) : null;
                              MapTile rightTile = this.insideX < 3 ? tileChunk.getTile(this.insideX + 1, this.insideZ) : null;
                              boolean triedFetchingBottomChunk = false;
                              boolean triedFetchingRightChunk = false;

                              for (int x = 0; x < 16; x++) {
                                 for (int z = 0; z < 16; z++) {
                                    int mappedHeight = chunk.getHeight(typeWorldSurface, x, z);
                                    int startHeight;
                                    if (cave && !fullCave) {
                                       startHeight = caveStart;
                                    } else if (!ignoreHeightmaps && mappedHeight >= worldBottomY) {
                                       startHeight = mappedHeight;
                                    } else {
                                       startHeight = sectionBasedHeight;
                                    }

                                    if (startHeight >= worldTopY) {
                                       startHeight = worldTopY - 1;
                                    }

                                    MapBlock currentPixel = mapTile.isLoaded() ? mapTile.getBlock(x, z) : null;
                                    this.loadPixel(
                                       world,
                                       blockRegistry,
                                       this.loadingObject,
                                       currentPixel,
                                       chunk,
                                       x,
                                       z,
                                       startHeight,
                                       lowH,
                                       cave,
                                       fullCave,
                                       mappedHeight,
                                       mapTile.wasWrittenOnce(),
                                       ignoreHeightmaps,
                                       biomeRegistry,
                                       flowers,
                                       worldBottomY,
                                       mutableBlockPos3
                                    );
                                    this.loadingObject
                                       .fixHeightType(
                                          x,
                                          z,
                                          mapTile,
                                          tileChunk,
                                          prevTileChunk,
                                          prevTileChunkDiagonal,
                                          prevTileChunkHorisontal,
                                          this.loadingObject.getEffectiveHeight(this.blockStateShortShapeCache, updateConfig),
                                          true,
                                          this.blockStateShortShapeCache,
                                          updateConfig
                                       );
                                    boolean equalsSlopesExcluded = this.loadingObject.equalsSlopesExcluded(currentPixel);
                                    boolean fullyEqual = this.loadingObject.equals(currentPixel, equalsSlopesExcluded);
                                    if (!fullyEqual) {
                                       MapBlock loadedBlock = this.loadingObject;
                                       mapTile.setBlock(x, z, loadedBlock);
                                       if (currentPixel != null) {
                                          this.loadingObject = currentPixel;
                                       } else {
                                          this.loadingObject = new MapBlock();
                                       }

                                       if (!equalsSlopesExcluded) {
                                          tileChunk.setChanged(true);
                                          boolean zEdge = z == 15;
                                          boolean xEdge = x == 15;
                                          if ((zEdge || xEdge)
                                             && (
                                                currentPixel == null
                                                   || currentPixel.getEffectiveHeight(this.blockStateShortShapeCache, updateConfig)
                                                      != loadedBlock.getEffectiveHeight(this.blockStateShortShapeCache, updateConfig)
                                             )) {
                                             if (zEdge) {
                                                if (!triedFetchingBottomChunk && bottomTile == null && this.insideZ == 3 && tileChunkLocalZ < 7) {
                                                   bottomChunk = region.getChunk(tileChunkLocalX, tileChunkLocalZ + 1);
                                                   triedFetchingBottomChunk = true;
                                                   bottomTile = bottomChunk != null ? bottomChunk.getTile(this.insideX, 0) : null;
                                                   if (bottomTile != null) {
                                                      bottomChunk.setChanged(true);
                                                   }
                                                }

                                                if (bottomTile != null && bottomTile.isLoaded()) {
                                                   bottomTile.getBlock(x, 0).setSlopeUnknown(true);
                                                   if (!xEdge) {
                                                      bottomTile.getBlock(x + 1, 0).setSlopeUnknown(true);
                                                   }
                                                }

                                                if (xEdge) {
                                                   this.updateBottomRightTile(region, tileChunk, bottomChunk, tileChunkLocalX, tileChunkLocalZ);
                                                }
                                             } else if (xEdge) {
                                                if (!triedFetchingRightChunk && rightTile == null && this.insideX == 3 && tileChunkLocalX < 7) {
                                                   this.rightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ);
                                                   triedFetchingRightChunk = true;
                                                   rightTile = this.rightChunk != null ? this.rightChunk.getTile(0, this.insideZ) : null;
                                                   if (rightTile != null) {
                                                      this.rightChunk.setChanged(true);
                                                   }
                                                }

                                                if (rightTile != null && rightTile.isLoaded()) {
                                                   rightTile.getBlock(0, z + 1).setSlopeUnknown(true);
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }

                              mapTile.setWorldInterpretationVersion(1);
                              if (mapTile.getWrittenCaveStart() != caveStart) {
                                 tileChunk.setChanged(true);
                              }

                              mapTile.setWrittenCave(caveStart, caveDepth);
                              tileChunk.setTile(this.insideX, this.insideZ, mapTile, this.blockStateShortShapeCache);
                              mapTile.setWrittenOnce(true);
                              mapTile.setLoaded(true);
                              ReflectionUtils.setReflectFieldValue(chunk, XaeroWorldMapCore.chunkCleanField, true);
                           }
                        }
                     }
                  }

                  if (createdTileChunk) {
                     if (tileChunk.includeInSave()) {
                        tileChunk.setHasHadTerrain();
                     }

                     this.mapProcessor.getMapRegionHighlightsPreparer().prepare(region, tileChunkLocalX, tileChunkLocalZ, false);
                     if (!tileChunk.includeInSave() && !tileChunk.hasHighlightsIfUndiscovered()) {
                        region.setChunk(tileChunkLocalX, tileChunkLocalZ, null);
                        tileChunk = null;
                     }
                  }
               }

               if (tileChunk != null && this.insideX == 3 && this.insideZ == 3 && tileChunk.wasChanged()) {
                  tileChunk.updateBuffers(this.mapProcessor, blockTintProvider, overlayManager, detailedDebug, this.blockStateShortShapeCache, updateConfig);
                  if (bottomChunk == null && tileChunkLocalZ < 7) {
                     bottomChunk = region.getChunk(tileChunkLocalX, tileChunkLocalZ + 1);
                  }

                  if (this.rightChunk == null && tileChunkLocalX < 7) {
                     this.rightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ);
                  }

                  if (this.bottomRightChunk == null && tileChunkLocalX < 7 && tileChunkLocalZ < 7) {
                     this.bottomRightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ + 1);
                  }

                  if (bottomChunk != null && bottomChunk.wasChanged()) {
                     bottomChunk.updateBuffers(
                        this.mapProcessor, blockTintProvider, overlayManager, detailedDebug, this.blockStateShortShapeCache, updateConfig
                     );
                     bottomChunk.setChanged(false);
                  }

                  if (this.rightChunk != null && this.rightChunk.wasChanged()) {
                     this.rightChunk.setToUpdateBuffers(true);
                     this.rightChunk.setChanged(false);
                  }

                  if (this.bottomRightChunk != null && this.bottomRightChunk.wasChanged()) {
                     this.bottomRightChunk.setToUpdateBuffers(true);
                     this.bottomRightChunk.setChanged(false);
                  }

                  tileChunk.setChanged(false);
               }
            }
         } else {
            this.insideX = 3;
            this.insideZ = 3;
         }
      }

      this.insideZ++;
      if (this.insideZ > 3) {
         this.insideZ = 0;
         this.insideX++;
         if (this.insideX > 3) {
            this.insideX = 0;
            this.Z++;
            if (this.Z > this.endTileChunkZ - this.startTileChunkZ) {
               this.Z = 0;
               this.X++;
               if (this.X > this.endTileChunkX - this.startTileChunkX) {
                  this.X = 0;
                  this.updateCounter++;
               }
            }
         }
      }

      return wasSkipped;
   }

   public void updateBottomRightTile(MapRegion region, MapTileChunk tileChunk, MapTileChunk bottomChunk, int tileChunkLocalX, int tileChunkLocalZ) {
      MapTile bottomRightTile = this.insideX < 3 && this.insideZ < 3 ? tileChunk.getTile(this.insideX + 1, this.insideZ + 1) : null;
      if (bottomRightTile == null) {
         if (this.insideX == 3 && tileChunkLocalX < 7) {
            if (this.insideZ == 3) {
               if (tileChunkLocalZ < 7) {
                  this.bottomRightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ + 1);
               }

               bottomRightTile = this.bottomRightChunk != null ? this.bottomRightChunk.getTile(0, 0) : null;
               if (bottomRightTile != null) {
                  this.bottomRightChunk.setChanged(true);
               }
            } else {
               if (this.rightChunk == null) {
                  this.rightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ);
               }

               bottomRightTile = this.rightChunk != null ? this.rightChunk.getTile(0, this.insideZ + 1) : null;
               if (bottomRightTile != null) {
                  this.rightChunk.setChanged(true);
               }
            }
         } else if (this.insideX != 3 && this.insideZ == 3 && tileChunkLocalZ < 7) {
            bottomRightTile = bottomChunk != null ? bottomChunk.getTile(this.insideX + 1, 0) : null;
            if (bottomRightTile != null) {
               bottomChunk.setChanged(true);
            }
         }
      }

      if (bottomRightTile != null && bottomRightTile.isLoaded()) {
         bottomRightTile.getBlock(0, 0).setSlopeUnknown(true);
      }
   }

   public int getSectionBasedHeight(LevelChunk bchunk, int startY) {
      LevelChunkSection[] sections = bchunk.getSections();
      if (sections.length == 0) {
         return 0;
      }

      int chunkBottomY = bchunk.getMinBuildHeight();
      int playerSection = Math.min(startY - chunkBottomY >> 4, sections.length - 1);
      if (playerSection < 0) {
         playerSection = 0;
      }

      int result = 0;

      for (int i = playerSection; i < sections.length; i++) {
         LevelChunkSection searchedSection = sections[i];
         if (!searchedSection.hasOnlyAir()) {
            result = chunkBottomY + (i << 4) + 15;
         }
      }

      if (playerSection > 0 && result == 0) {
         for (int i = playerSection - 1; i >= 0; i--) {
            LevelChunkSection searchedSection = sections[i];
            if (!searchedSection.hasOnlyAir()) {
               result = chunkBottomY + (i << 4) + 15;
               break;
            }
         }
      }

      return result;
   }

   public boolean isGlowing(BlockState state) {
      return state.getLightEmission() >= 0.5;
   }

   private boolean shouldOverlayCached(StateHolder<?, ?> state) {
      return this.transparentCache.apply(state);
   }

   public boolean shouldOverlay(StateHolder<?, ?> state) {
      if (state instanceof BlockState blockState) {
         return !(blockState.getBlock() instanceof AirBlock) && !(blockState.getBlock() instanceof TransparentBlock)
            ? this.blockStateHasTranslucentRenderType(blockState)
            : true;
      } else {
         FluidState fluidState = (FluidState)state;
         return ItemBlockRenderTypes.getRenderLayer(fluidState) == RenderType.translucent();
      }
   }

   public boolean isInvisible(BlockState state, Block b, boolean flowers) {
      if (!(b instanceof LiquidBlock) && state.getRenderShape() == RenderShape.INVISIBLE) {
         return true;
      }

      if (b == Blocks.TORCH) {
         return true;
      }

      if (b == Blocks.SHORT_GRASS) {
         return true;
      }

      if (b != Blocks.GLASS && b != Blocks.GLASS_PANE) {
         boolean isFlower = b instanceof PitcherCropBlock || b instanceof TallFlowerBlock || b instanceof FlowerBlock || state.is(BlockTags.FLOWERS);
         if (b instanceof DoublePlantBlock && !isFlower) {
            return true;
         }

         if (isFlower && !flowers) {
            return true;
         }

         synchronized (this.buggedStates) {
            return this.buggedStates.contains(state);
         }
      } else {
         return true;
      }
   }

   public boolean hasVanillaColor(BlockState state, Level world, Registry<Block> blockRegistry, BlockPos pos) {
      MapColor materialColor = null;

      try {
         materialColor = state.getMapColor(world, pos);
      } catch (Throwable t) {
         synchronized (this.buggedStates) {
            this.buggedStates.add(state);
         }

         WorldMap.LOGGER.info("Broken vanilla map color definition found: " + blockRegistry.getKey(state.getBlock()));
      }

      return materialColor != null && materialColor.col != 0;
   }

   private BlockState unpackFramedBlocks(BlockState original, Level world, BlockPos globalPos) {
      if (original.getBlock() instanceof AirBlock) {
         return original;
      }

      BlockState result = original;
      if (SupportMods.framedBlocks() && SupportMods.supportFramedBlocks.isFrameBlock(world, null, original)) {
         BlockEntity tileEntity = world.getBlockEntity(globalPos);
         if (tileEntity != null) {
            result = SupportMods.supportFramedBlocks.unpackFramedBlock(world, null, original, tileEntity);
            if (result == null || result.getBlock() instanceof AirBlock) {
               result = original;
            }
         }
      }

      return result;
   }

   public void loadPixel(
      Level world,
      Registry<Block> blockRegistry,
      MapBlock pixel,
      MapBlock currentPixel,
      LevelChunk bchunk,
      int insideX,
      int insideZ,
      int highY,
      int lowY,
      boolean cave,
      boolean fullCave,
      int mappedHeight,
      boolean canReuseBiomeColours,
      boolean ignoreHeightmaps,
      Registry<Biome> biomeRegistry,
      boolean flowers,
      int worldBottomY,
      MutableBlockPos mutableBlockPos3
   ) {
      pixel.prepareForWriting(worldBottomY);
      this.overlayBuilder.startBuilding();
      boolean underair = !cave || fullCave;
      boolean shouldEnterGround = fullCave;
      BlockState opaqueState = null;
      byte workingLight = -1;
      boolean worldHasSkyLight = world.dimensionType().hasSkyLight();
      byte workingSkyLight = (byte)(worldHasSkyLight ? 15 : 0);
      this.topH = lowY;
      this.mutableGlobalPos.set((bchunk.getPos().x << 4) + insideX, lowY - 1, (bchunk.getPos().z << 4) + insideZ);
      boolean shouldExtendTillTheBottom = false;
      int transparentSkipY = 0;

      int h;
      for (h = highY; h >= lowY; h = shouldExtendTillTheBottom ? transparentSkipY : h - 1) {
         this.mutableLocalPos.set(insideX, h, insideZ);
         BlockState state = bchunk.getBlockState(this.mutableLocalPos);
         if (state == null) {
            state = Blocks.AIR.defaultBlockState();
         }

         this.mutableGlobalPos.setY(h);
         state = this.unpackFramedBlocks(state, world, this.mutableGlobalPos);
         FluidState fluidFluidState = state.getFluidState();
         shouldExtendTillTheBottom = !shouldExtendTillTheBottom && !this.overlayBuilder.isEmpty() && this.firstTransparentStateY - h >= 5;
         if (shouldExtendTillTheBottom) {
            for (transparentSkipY = h - 1; transparentSkipY >= lowY; transparentSkipY--) {
               BlockState traceState = bchunk.getBlockState(mutableBlockPos3.set(insideX, transparentSkipY, insideZ));
               if (traceState == null) {
                  traceState = Blocks.AIR.defaultBlockState();
               }

               FluidState traceFluidState = traceState.getFluidState();
               if (!traceFluidState.isEmpty()) {
                  if (!this.shouldOverlayCached(traceFluidState)) {
                     break;
                  }

                  if (!(traceState.getBlock() instanceof AirBlock) && traceState.getBlock() == this.fluidToBlock.apply(traceFluidState).getBlock()) {
                     continue;
                  }
               }

               if (!this.shouldOverlayCached(traceState)) {
                  break;
               }
            }
         }

         this.mutableGlobalPos.setY(h + 1);
         workingLight = (byte)world.getBrightness(LightLayer.BLOCK, this.mutableGlobalPos);
         if (cave && workingLight < 15 && worldHasSkyLight) {
            if (!ignoreHeightmaps && !fullCave && highY >= mappedHeight) {
               workingSkyLight = 15;
            } else {
               workingSkyLight = (byte)world.getBrightness(LightLayer.SKY, this.mutableGlobalPos);
            }
         }

         this.mutableGlobalPos.setY(h);
         if (!fluidFluidState.isEmpty() && (!cave || !shouldEnterGround)) {
            underair = true;
            BlockState fluidState = this.fluidToBlock.apply(fluidFluidState);
            if (this.loadPixelHelp(
               pixel,
               currentPixel,
               world,
               blockRegistry,
               fluidState,
               workingLight,
               workingSkyLight,
               bchunk,
               insideX,
               insideZ,
               h,
               canReuseBiomeColours,
               cave,
               fluidFluidState,
               biomeRegistry,
               transparentSkipY,
               shouldExtendTillTheBottom,
               flowers,
               underair
            )) {
               opaqueState = state;
               break;
            }
         }

         Block b = state.getBlock();
         if (b instanceof AirBlock) {
            underair = true;
         } else if (underair && state.getBlock() != this.fluidToBlock.apply(fluidFluidState).getBlock()) {
            if (cave && shouldEnterGround) {
               if (!state.ignitedByLava()
                  && !state.canBeReplaced()
                  && state.getPistonPushReaction() != PushReaction.DESTROY
                  && !this.shouldOverlayCached(state)) {
                  underair = false;
                  shouldEnterGround = false;
               }
            } else if (this.loadPixelHelp(
               pixel,
               currentPixel,
               world,
               blockRegistry,
               state,
               workingLight,
               workingSkyLight,
               bchunk,
               insideX,
               insideZ,
               h,
               canReuseBiomeColours,
               cave,
               null,
               biomeRegistry,
               transparentSkipY,
               shouldExtendTillTheBottom,
               flowers,
               underair
            )) {
               opaqueState = state;
               break;
            }
         }
      }

      if (h < lowY) {
         h = lowY;
      }

      ResourceKey<Biome> blockBiome = null;
      BlockState state = opaqueState == null ? Blocks.AIR.defaultBlockState() : opaqueState;
      this.overlayBuilder.finishBuilding(pixel);
      byte light = 0;
      if (opaqueState != null) {
         light = workingLight;
         if (cave && light < 15 && pixel.getNumberOfOverlays() == 0 && workingSkyLight > light) {
            light = workingSkyLight;
         }
      } else {
         h = worldBottomY;
      }

      if (canReuseBiomeColours && currentPixel != null && currentPixel.getState() == state && currentPixel.getTopHeight() == this.topH) {
         blockBiome = currentPixel.getBiome();
      } else {
         this.mutableGlobalPos.setY(this.topH);
         blockBiome = this.biomeGetter.getBiome(world, this.mutableGlobalPos, biomeRegistry);
         this.mutableGlobalPos.setY(h);
      }

      if (this.overlayBuilder.getOverlayBiome() != null) {
         blockBiome = this.overlayBuilder.getOverlayBiome();
      }

      boolean glowing = this.isGlowing(state);
      pixel.write(state, h, this.topH, blockBiome, light, glowing, cave);
   }

   private boolean loadPixelHelp(
      MapBlock pixel,
      MapBlock currentPixel,
      Level world,
      Registry<Block> blockRegistry,
      BlockState state,
      byte light,
      byte skyLight,
      LevelChunk bchunk,
      int insideX,
      int insideZ,
      int h,
      boolean canReuseBiomeColours,
      boolean cave,
      FluidState fluidFluidState,
      Registry<Biome> biomeRegistry,
      int transparentSkipY,
      boolean shouldExtendTillTheBottom,
      boolean flowers,
      boolean underair
   ) {
      Block b = state.getBlock();
      if (this.isInvisible(state, b, flowers)) {
         return false;
      }

      if (this.shouldOverlayCached((StateHolder<?, ?>)(fluidFluidState == null ? state : fluidFluidState))) {
         if (cave && !underair) {
            return false;
         }

         if (h > this.topH) {
            this.topH = h;
         }

         byte overlayLight = light;
         if (this.overlayBuilder.isEmpty()) {
            this.firstTransparentStateY = h;
            if (cave && skyLight > overlayLight) {
               overlayLight = skyLight;
            }
         }

         if (shouldExtendTillTheBottom) {
            this.overlayBuilder
               .getCurrentOverlay()
               .increaseOpacity(this.overlayBuilder.getCurrentOverlay().getState().getLightBlock(world, this.mutableGlobalPos) * (h - transparentSkipY));
         } else {
            ResourceKey<Biome> overlayBiome = this.overlayBuilder.getOverlayBiome();
            if (overlayBiome == null) {
               if (canReuseBiomeColours
                  && currentPixel != null
                  && currentPixel.getNumberOfOverlays() > 0
                  && currentPixel.getOverlays().get(0).getState() == state) {
                  overlayBiome = currentPixel.getBiome();
               } else {
                  overlayBiome = this.biomeGetter.getBiome(world, this.mutableGlobalPos, biomeRegistry);
               }
            }

            this.overlayBuilder.build(state, state.getLightBlock(world, this.mutableGlobalPos), overlayLight, this.mapProcessor, overlayBiome);
         }

         return false;
      } else {
         if (!this.hasVanillaColor(state, world, blockRegistry, this.mutableGlobalPos)) {
            return false;
         }

         if (cave && !underair) {
            return true;
         }

         if (h > this.topH) {
            this.topH = h;
         }

         return true;
      }
   }

   protected abstract List<BakedQuad> getQuads(BakedModel var1, BlockState var2, Direction var3);

   protected abstract TextureAtlasSprite getParticleIcon(BlockModelShaper var1, BakedModel var2, BlockState var3);

   public int loadBlockColourFromTexture(BlockState state, boolean convert, Level world, Registry<Block> blockRegistry, BlockPos globalPos) {
      if (this.clearCachedColours) {
         this.textureColours.clear();
         this.blockColours.clear();
         this.blockTintIndices.clear();
         this.lastBlockStateForTextureColor = null;
         this.lastBlockStateForTextureColorResult = -1;
         this.clearCachedColours = false;
         if (WorldMapClientConfigUtils.getDebug()) {
            WorldMap.LOGGER.info("Xaero's World Map cache cleared!");
         }
      }

      if (state == this.lastBlockStateForTextureColor) {
         return this.lastBlockStateForTextureColorResult;
      }

      Integer c = this.blockColours.get(state);
      int red = 0;
      int green = 0;
      int blue = 0;
      int alpha = 0;
      Block b = state.getBlock();
      if (c == null) {
         String name = null;
         int tintIndex = -1;

         try {
            List<BakedQuad> upQuads = null;
            BlockModelShaper bms = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();
            BakedModel model = bms.getBlockModel(state);
            if (convert) {
               upQuads = this.getQuads(model, state, Direction.UP);
            }

            TextureAtlasSprite missingTexture = Minecraft.getInstance()
               .getModelManager()
               .getAtlas(TextureAtlas.LOCATION_BLOCKS)
               .getSprite(MissingTextureAtlasSprite.getLocation());
            TextureAtlasSprite texture;
            if (upQuads != null && !upQuads.isEmpty() && upQuads.get(0).getSprite() != missingTexture) {
               texture = upQuads.get(0).getSprite();
               tintIndex = upQuads.get(0).getTintIndex();
            } else {
               texture = this.getParticleIcon(bms, model, state);
               tintIndex = 0;
            }

            if (texture == null) {
               throw new SilentException("No texture for " + state);
            }

            c = -1;
            name = texture.contents().name() + ".png";
            String[] args = name.split(":");
            if (args.length < 2) {
               DEFAULT_RESOURCE[1] = args[0];
               args = DEFAULT_RESOURCE;
            }

            Integer cachedColour = this.textureColours.get(name);
            if (cachedColour == null) {
               ResourceLocation location = ResourceLocation.fromNamespaceAndPath(args[0], "textures/" + args[1]);
               Resource resource = (Resource)Minecraft.getInstance().getResourceManager().getResource(location).orElse(null);
               if (resource == null) {
                  throw new SilentException("No texture " + location);
               }

               InputStream input = resource.open();
               ImageInputStream imageInputStream = ImageIO.createImageInputStream(input);
               BufferedImage img = ImageIOUtils.getImageThroughZipError(imageInputStream, location.toString());
               imageInputStream.close();
               if (img == null) {
                  throw new SilentException("No image loaded " + location);
               }

               red = 0;
               green = 0;
               blue = 0;
               int total = 0;
               int ts = Math.min(img.getWidth(), img.getHeight());
               if (ts > 0) {
                  int diff = Math.max(1, Math.min(4, ts / 8));
                  int parts = ts / diff;
                  Raster raster = img.getData();
                  int[] colorHolder = null;

                  for (int i = 0; i < parts; i++) {
                     for (int j = 0; j < parts; j++) {
                        int rgb;
                        if (img.getColorModel().getNumComponents() < 3) {
                           colorHolder = raster.getPixel(i * diff, j * diff, colorHolder);
                           int sample = colorHolder[0] & 0xFF;
                           int a = 255;
                           if (colorHolder.length > 1) {
                              a = colorHolder[1];
                           }

                           rgb = a << 24 | sample << 16 | sample << 8 | sample;
                        } else {
                           rgb = img.getRGB(i * diff, j * diff);
                        }

                        int a = rgb >> 24 & 0xFF;
                        if (rgb != 0 && a != 0) {
                           red += rgb >> 16 & 0xFF;
                           green += rgb >> 8 & 0xFF;
                           blue += rgb & 0xFF;
                           alpha += a;
                           total++;
                        }
                     }
                  }
               }

               if (total == 0) {
                  total = 1;
               }

               red /= total;
               green /= total;
               blue /= total;
               alpha /= total;
               if (convert && red == 0 && green == 0 && blue == 0) {
                  throw new SilentException("Black texture " + ts);
               }

               c = alpha << 24 | red << 16 | green << 8 | blue;
               this.textureColours.put(name, c);
            } else {
               c = cachedColour;
            }
         } catch (FileNotFoundException e) {
            if (convert) {
               return this.loadBlockColourFromTexture(state, false, world, blockRegistry, globalPos);
            }

            WorldMap.LOGGER.info("Block file not found: " + blockRegistry.getKey(b));
            c = 0;
            if (state != null && state.getMapColor(world, globalPos) != null) {
               c = state.getMapColor(world, globalPos).col;
            }

            if (name != null) {
               this.textureColours.put(name, c);
            }
         } catch (Exception e) {
            WorldMap.LOGGER.info("Exception when loading " + blockRegistry.getKey(b) + " texture, using material colour.");
            c = 0;
            if (state.getMapColor(world, globalPos) != null) {
               c = state.getMapColor(world, globalPos).col;
            }

            if (name != null) {
               this.textureColours.put(name, c);
            }

            if (e instanceof SilentException) {
               WorldMap.LOGGER.info(e.getMessage());
            } else {
               WorldMap.LOGGER.error("suppressed exception", e);
            }
         }

         if (c != null) {
            this.blockColours.put(state, c);
            this.blockTintIndices.put(state, tintIndex);
         }
      }

      this.lastBlockStateForTextureColor = state;
      this.lastBlockStateForTextureColorResult = c;
      return c;
   }

   public long getUpdateCounter() {
      return this.updateCounter;
   }

   public void resetPosition() {
      this.X = 0;
      this.Z = 0;
      this.insideX = 0;
      this.insideZ = 0;
   }

   public void requestCachedColoursClear() {
      this.clearCachedColours = true;
   }

   public void setMapProcessor(MapProcessor mapProcessor) {
      this.mapProcessor = mapProcessor;
   }

   public MapProcessor getMapProcessor() {
      return this.mapProcessor;
   }

   public void setDirtyInWriteDistance(Player player, Level level) {
      int writeDistance = this.getWriteDistance();
      int playerChunkX = player.blockPosition().getX() >> 4;
      int playerChunkZ = player.blockPosition().getZ() >> 4;
      int startChunkX = playerChunkX - writeDistance;
      int startChunkZ = playerChunkZ - writeDistance;
      int endChunkX = playerChunkX + writeDistance;
      int endChunkZ = playerChunkZ + writeDistance;

      for (int x = startChunkX; x < endChunkX; x++) {
         for (int z = startChunkZ; z < endChunkZ; z++) {
            LevelChunk chunk = level.getChunk(x, z);
            if (chunk != null) {
               try {
                  XaeroWorldMapCore.chunkCleanField.set(chunk, false);
               } catch (IllegalArgumentException | IllegalAccessException e) {
                  throw new RuntimeException(e);
               }
            }
         }
      }
   }

   public int getBlockTintIndex(BlockState state) {
      return this.blockTintIndices.getInt(state);
   }
}
