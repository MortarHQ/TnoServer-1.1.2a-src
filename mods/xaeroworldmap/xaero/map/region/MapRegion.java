package xaero.map.region;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.biome.Biome;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.file.MapRegionInfo;
import xaero.map.file.MapSaveLoad;
import xaero.map.file.OldFormatSupport;
import xaero.map.file.RegionDetection;
import xaero.map.misc.Misc;
import xaero.map.region.texture.LeafRegionTexture;
import xaero.map.world.MapDimension;

public class MapRegion extends LeveledRegion<LeafRegionTexture> implements MapRegionInfo {
   public static final int SIDE_LENGTH = 8;
   private Boolean saveExists;
   private File regionFile;
   private boolean beingWritten;
   private long lastVisited;
   private byte loadState;
   private int version = -1;
   private int initialVersion;
   private int reloadVersion;
   private final boolean normalMapData;
   private MapTileChunk[][] chunks = new MapTileChunk[8][8];
   private boolean isRefreshing;
   public final Object writerThreadPauseSync = new Object();
   private int pauseWriting;
   public boolean loadingPrioritized;
   public int loadingNeededForBranchLevel;
   private int cacheHashCode;
   private int caveStart;
   private int caveDepth;
   private Registry<Biome> biomeRegistry;
   private int highlightsHash;
   private int targetHighlightsHash;
   private boolean hasHadTerrain;
   private boolean lookedForCache;
   private boolean outdatedWithOtherLayers;
   private boolean resaving;
   private int[] pixelResultBuffer = new int[4];
   private MutableBlockPos mutableGlobalPos = new MutableBlockPos();

   public MapRegion(
      String worldId,
      String dimId,
      String mwId,
      MapDimension dim,
      int x,
      int z,
      int caveLayer,
      int initialVersion,
      boolean normalMapData,
      Registry<Biome> biomeRegistry
   ) {
      super(worldId, dimId, mwId, dim, 0, x, z, caveLayer, null);
      this.initialVersion = initialVersion;
      this.normalMapData = normalMapData;
      this.biomeRegistry = biomeRegistry;
      this.lastSaveTime = System.currentTimeMillis();
   }

   public void setParent(BranchLeveledRegion parent) {
      this.parent = parent;
   }

   public void destroyBufferUpdateObjects() {
      this.pixelResultBuffer = null;
      this.mutableGlobalPos = null;
   }

   public void restoreBufferUpdateObjects() {
      this.pixelResultBuffer = new int[4];
      this.mutableGlobalPos = new MutableBlockPos();
   }

   public void requestRefresh(MapProcessor mapProcessor) {
      this.requestRefresh(mapProcessor, true);
   }

   public void requestRefresh(MapProcessor mapProcessor, boolean prepareHighlights) {
      if (!this.isRefreshing) {
         this.isRefreshing = true;
         mapProcessor.addToRefresh(this, prepareHighlights);
         if (WorldMapClientConfigUtils.getDebug()) {
            WorldMap.LOGGER.info(String.format("Requesting refresh for region %s.", this));
         }
      }
   }

   public void cancelRefresh(MapProcessor mapProcessor) {
      if (this.isRefreshing) {
         this.isRefreshing = false;
         mapProcessor.removeToRefresh(this);
         if (WorldMapClientConfigUtils.getDebug()) {
            WorldMap.LOGGER.info(String.format("Canceling refresh for region %s.", this));
         }
      }
   }

   @Override
   protected int distanceFromPlayer() {
      return this.leafDistanceFromPlayer();
   }

   @Override
   protected int leafDistanceFromPlayer() {
      return super.leafDistanceFromPlayer() + (comparisonLevel == 0 ? this.loadState * 3 / 2 : 0);
   }

   public void clean(MapProcessor mapProcessor) {
      for (int i = 0; i < this.chunks.length; i++) {
         for (int j = 0; j < this.chunks.length; j++) {
            MapTileChunk c = this.chunks[i][j];
            if (c != null) {
               c.clean(mapProcessor);
               this.chunks[i][j] = null;
            }
         }
      }

      this.resetBiomePalette();
   }

   @Override
   protected void writeCacheMetaData(DataOutputStream output, byte[] usableBuffer, byte[] integerByteBuffer) throws IOException {
      output.writeInt(this.cacheHashCode);
      output.writeInt(this.reloadVersion);
      output.writeInt(this.getHighlightsHash());
      output.writeInt(this.getCaveStart());
      output.writeInt(this.caveDepth);
      super.writeCacheMetaData(output, usableBuffer, integerByteBuffer);
   }

   @Override
   protected void readCacheMetaData(
      DataInputStream input,
      int minorSaveVersion,
      int majorSaveVersion,
      byte[] usableBuffer,
      byte[] integerByteBuffer,
      boolean[][] textureLoaded,
      MapProcessor mapProcessor
   ) throws IOException {
      if (minorSaveVersion >= 9) {
         int saveHashCode = input.readInt();
         this.setCacheHashCode(saveHashCode);
      }

      if (minorSaveVersion >= 11) {
         this.reloadVersion = input.readInt();
      }

      if (minorSaveVersion >= 18) {
         this.setHighlightsHash(input.readInt());
      }

      if (minorSaveVersion >= 23) {
         this.setCaveStart(input.readInt());
      }

      if (minorSaveVersion >= 24) {
         this.caveDepth = input.readInt();
      }

      super.readCacheMetaData(input, minorSaveVersion, majorSaveVersion, usableBuffer, integerByteBuffer, textureLoaded, mapProcessor);
   }

   public void clearRegion(MapProcessor mapProcessor) {
      this.setRecacheHasBeenRequested(false, "clearing");
      this.cancelRefresh(mapProcessor);

      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            MapTileChunk c = this.getChunk(i, j);
            if (c != null) {
               c.setLoadState((byte)3);
               this.setLoadState((byte)3);
               c.clean(mapProcessor);
            }
         }
      }

      if (!mapProcessor.getMapSaveLoad().toCacheContains(this)) {
         this.deleteBuffers();
      }

      this.deleteGLBuffers();
      this.setLoadState((byte)4);
      if (WorldMapClientConfigUtils.getDebug()) {
         WorldMap.LOGGER.info("Cleared region! " + this + " " + this.getWorldId() + " " + this.getDimId() + " " + this.getMwId());
      }
   }

   public boolean isResting() {
      return this.loadState != 3 && this.loadState != 1 && !this.recacheHasBeenRequested;
   }

   @Override
   public String getWorldId() {
      return this.worldId;
   }

   @Override
   public String getDimId() {
      return this.dimId;
   }

   @Override
   public String getMwId() {
      return this.mwId;
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
      if (WorldMap.detailed_debug) {
         WorldMap.LOGGER.info("Version set to " + version + " by for " + this);
      }
   }

   public boolean isBeingWritten() {
      return this.beingWritten;
   }

   public void setBeingWritten(boolean beingWritten) {
      this.beingWritten = beingWritten;
   }

   public byte getLoadState() {
      return this.loadState;
   }

   public void setLoadState(byte loadState) {
      this.loadState = loadState;
   }

   public MapTileChunk getChunk(int x, int z) {
      return this.chunks[x][z];
   }

   public void setChunk(int x, int z, MapTileChunk chunk) {
      this.chunks[x][z] = chunk;
   }

   public int getInitialVersion() {
      return this.initialVersion;
   }

   public void setInitialVersion(int initialVersion) {
      this.initialVersion = initialVersion;
   }

   public int[] getPixelResultBuffer() {
      return this.pixelResultBuffer;
   }

   public MutableBlockPos getMutableGlobalPos() {
      return this.mutableGlobalPos;
   }

   @Override
   public File getRegionFile() {
      return this.regionFile;
   }

   public void setRegionFile(File loadedFromFile) {
      this.regionFile = loadedFromFile;
   }

   public Boolean getSaveExists() {
      return this.saveExists;
   }

   public void setSaveExists(Boolean saveExists) {
      this.saveExists = saveExists;
   }

   public long getLastSaveTime() {
      return this.lastSaveTime;
   }

   public void setLastSaveTime(long lastSaveTime) {
      this.lastSaveTime = lastSaveTime;
   }

   @Override
   public boolean isRefreshing() {
      return this.isRefreshing;
   }

   public void setRefreshing(boolean isRefreshing) {
      this.isRefreshing = isRefreshing;
   }

   public boolean isWritingPaused() {
      return this.pauseWriting > 0;
   }

   public void pushWriterPause() {
      synchronized (this.writerThreadPauseSync) {
         this.pauseWriting++;
      }
   }

   public void popWriterPause() {
      synchronized (this.writerThreadPauseSync) {
         this.pauseWriting--;
      }
   }

   public boolean hasVersion() {
      return this.version != -1;
   }

   public boolean isNormalMapData() {
      return this.normalMapData;
   }

   public long getLastVisited() {
      return this.lastVisited;
   }

   public long getTimeSinceVisit() {
      return System.currentTimeMillis() - this.lastVisited;
   }

   public void registerVisit() {
      this.lastVisited = System.currentTimeMillis();
   }

   public int countChunks() {
      int count = 0;

      for (int i = 0; i < this.chunks.length; i++) {
         for (int j = 0; j < this.chunks.length; j++) {
            MapTileChunk chunk = this.chunks[i][j];
            if (chunk != null) {
               count++;
            }
         }
      }

      return count;
   }

   public Registry<Biome> getBiomeRegistry() {
      return this.biomeRegistry;
   }

   @Override
   protected void putLeaf(int X, int Z, MapRegion leaf) {
   }

   public void putTexture(int x, int y, LeafRegionTexture texture) {
      throw new RuntimeException(new IllegalAccessException());
   }

   public LeafRegionTexture getTexture(int x, int y) {
      MapTileChunk chunk = this.chunks[x][y];
      return chunk != null ? chunk.getLeafTexture() : null;
   }

   @Override
   protected LeveledRegion<?> get(int leveledX, int leveledZ, int level) {
      if (level == 0) {
         return this;
      } else {
         throw new RuntimeException(new IllegalArgumentException());
      }
   }

   @Override
   protected boolean remove(int leveledX, int leveledZ, int level) {
      throw new RuntimeException(new IllegalAccessException());
   }

   @Override
   public boolean loadingAnimation() {
      return this.loadState < 2;
   }

   @Override
   public boolean cleanAndCacheRequestsBlocked() {
      return this.isRefreshing;
   }

   @Override
   public boolean shouldBeProcessed() {
      return this.loadState > 0 && this.loadState < 4;
   }

   @Override
   public boolean isLoaded() {
      return this.loadState >= 2;
   }

   @Override
   public boolean shouldEndProcessingAfterUpload() {
      return this.loadState == 3;
   }

   @Override
   public void onProcessingEnd() {
      this.loadState = 4;
      this.destroyBufferUpdateObjects();
   }

   @Override
   public void preCache() {
      this.pushWriterPause();
   }

   @Override
   public void postCache(File permFile, MapSaveLoad mapSaveLoad, boolean successfullySaved) throws IOException {
      this.popWriterPause();
      if (permFile != null && successfullySaved) {
         Path outdatedCacheFile = permFile.toPath().resolveSibling(permFile.getName() + ".outdated");
         if (Files.exists(outdatedCacheFile)) {
            Files.deleteIfExists(outdatedCacheFile);
         }
      }
   }

   @Override
   public boolean skipCaching(int globalVersion) {
      return this.getVersion() != globalVersion || !this.hasHadTerrain;
   }

   @Override
   public File findCacheFile(MapSaveLoad mapSaveLoad) throws IOException {
      return mapSaveLoad.getCacheFile(this, this.caveLayer, false, false);
   }

   @Override
   public void onCurrentDimFinish(MapSaveLoad mapSaveLoad, MapProcessor mapProcessor) {
      if (this.getLoadState() == 2) {
         if (this.isBeingWritten()) {
            mapSaveLoad.getToSave().add(this);
         } else {
            this.clearRegion(mapProcessor);
         }
      } else {
         this.setBeingWritten(false);
         if (this.isRefreshing()) {
            throw new RuntimeException("Detected non-loadstate 2 region with refreshing value being true.");
         }
      }
   }

   @Override
   public void onLimiterRemoval(MapProcessor mapProcessor) {
      this.pushWriterPause();
      RegionDetection restoredDetection = new RegionDetection(
         this.getWorldId(),
         this.getDimId(),
         this.getMwId(),
         this.getRegionX(),
         this.getRegionZ(),
         this.getRegionFile(),
         mapProcessor.getGlobalVersion(),
         this.hasHadTerrain
      );
      restoredDetection.transferInfoFrom(this);
      this.dim.getLayeredMapRegions().getLayer(this.caveLayer).addRegionDetection(restoredDetection);
      mapProcessor.removeMapRegion(this);
   }

   @Override
   public void afterLimiterRemoval(MapProcessor mapProcessor) {
      mapProcessor.getMapSaveLoad().removeToLoad(this);
      this.popWriterPause();
   }

   @Override
   public void addDebugLines(List<String> debugLines, MapProcessor mapProcessor, int textureX, int textureY) {
      super.addDebugLines(debugLines, mapProcessor, textureX, textureY);
      debugLines.add("paused: " + this.isWritingPaused() + " loadingNeededForBranchLevel: " + this.loadingNeededForBranchLevel);
      debugLines.add(String.format("writing: %s refreshing: %s", this.isBeingWritten(), this.isRefreshing()));
      debugLines.add("saveExists: " + this.getSaveExists());
      int targetRegionHighlightsHash = this.getDim().getHighlightHandler().getRegionHash(this.getRegionX(), this.getRegionZ());
      debugLines.add(
         String.format(
            "reg loadState: %s version: %d/%d hash: %d reloadVersion: %d highlights: %d/%d terrain: %s",
            this.getLoadState(),
            this.getVersion(),
            mapProcessor.getGlobalVersion(),
            this.getCacheHashCode(),
            this.getReloadVersion(),
            this.getHighlightsHash(),
            targetRegionHighlightsHash,
            this.hasHadTerrain
         )
      );
      debugLines.add(
         String.format("caveStart: %s caveDepth: %s outdatedWithOtherLayers: %s", this.getCaveStart(), this.caveDepth, this.outdatedWithOtherLayers)
      );
   }

   @Override
   public String getExtraInfo() {
      return this.getLoadState() + " " + this.countChunks();
   }

   public LeafRegionTexture createTexture(int x, int y) {
      return (this.chunks[x][y] = this.createTileChunk(x, y)).getLeafTexture();
   }

   protected MapTileChunk createTileChunk(int x, int y) {
      return new MapTileChunk(this, this.regionX * 8 + x, this.regionZ * 8 + y);
   }

   @Override
   public void checkForUpdates(
      MapProcessor mapProcessor,
      boolean prevWaitingForBranchCache,
      boolean[] waitingForBranchCache,
      ArrayList<BranchLeveledRegion> branchRegionBuffer,
      int viewedLevel,
      int minViewedLeafX,
      int minViewedLeafZ,
      int maxViewedLeafX,
      int maxViewedLeafZ
   ) {
      super.checkForUpdates(
         mapProcessor,
         prevWaitingForBranchCache,
         waitingForBranchCache,
         branchRegionBuffer,
         viewedLevel,
         minViewedLeafX,
         minViewedLeafZ,
         maxViewedLeafX,
         maxViewedLeafZ
      );
      synchronized (this) {
         if (!this.isLoaded()) {
            this.loadingNeededForBranchLevel = viewedLevel;
         }
      }
   }

   public int getReloadVersion() {
      return this.reloadVersion;
   }

   public void setReloadVersion(int reloadVersion) {
      this.reloadVersion = reloadVersion;
   }

   public void setCacheHashCode(int cacheHashCode) {
      this.cacheHashCode = cacheHashCode;
   }

   public int getCacheHashCode() {
      return this.cacheHashCode;
   }

   public void setCaveStart(int caveStart) {
      this.caveStart = caveStart;
   }

   public int getCaveStart() {
      return this.caveLayer == Integer.MAX_VALUE ? Integer.MAX_VALUE : this.caveStart;
   }

   @Override
   public void processWhenLoadedChunksExist(int globalRegionCacheHashCode) {
      super.processWhenLoadedChunksExist(globalRegionCacheHashCode);
      if (this.getCacheHashCode() != 0 && this.getCacheHashCode() != globalRegionCacheHashCode) {
         this.setCacheHashCode(0);
      }

      if (this.getHighlightsHash() != 0) {
         this.updateTargetHighlightsHash();
         if (this.getHighlightsHash() != this.getTargetHighlightsHash()) {
            this.setHighlightsHash(0);
         }
      }
   }

   @Override
   public void updateLeafTextureVersion(int localTextureX, int localTextureZ, int newVersion) {
      int oldVersion = this.leafTextureVersionSum[localTextureX][localTextureZ];
      int globalTextureX = 0;
      int globalTextureZ = 0;
      if (oldVersion != newVersion) {
         globalTextureX = (this.regionX << 3) + localTextureX;
         globalTextureZ = (this.regionZ << 3) + localTextureZ;
         this.leafTextureVersionSum[localTextureX][localTextureZ] = newVersion;
      }

      BranchLeveledRegion parentRegion = this.getParent();
      if (parentRegion != null) {
         parentRegion.setShouldCheckForUpdatesRecursive(true);
      }

      if (oldVersion != newVersion) {
         int diff = newVersion - oldVersion;

         while (parentRegion != null) {
            int parentLevel = parentRegion.getLevel();
            int parentTextureX = globalTextureX >> parentLevel & 7;
            int parentTextureY = globalTextureZ >> parentLevel & 7;
            parentRegion.leafTextureVersionSum[parentTextureX][parentTextureY] = parentRegion.leafTextureVersionSum[parentTextureX][parentTextureY] + diff;
            parentRegion = parentRegion.getParent();
         }
      }
   }

   @Override
   public void onDimensionClear(MapProcessor mapProcessor) {
      super.onDimensionClear(mapProcessor);
      if (this.loadState == 3) {
         this.clean(mapProcessor);
      }
   }

   public void restoreMetaData(
      int[][] cachedTextureVersions,
      int cacheHashCode,
      int reloadVersion,
      int highlightsHash,
      int caveStart,
      boolean outdatedWithOtherLayers,
      MapProcessor mapProcessor
   ) {
      if (cachedTextureVersions != null) {
         this.setVersion(mapProcessor.getGlobalVersion());
         this.cacheHashCode = cacheHashCode;
         this.reloadVersion = reloadVersion;
         this.highlightsHash = highlightsHash;
         this.caveStart = caveStart;
         this.outdatedWithOtherLayers = outdatedWithOtherLayers;

         for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
               int storedVersion = cachedTextureVersions[i][j];
               this.cachedTextureVersions[i][j] = storedVersion;
               this.updateLeafTextureVersion(i, j, storedVersion);
            }
         }

         this.metaLoaded = true;
      }
   }

   @Override
   public boolean loadCacheTextures(
      MapProcessor mapProcessor,
      Registry<Biome> biomeRegistry,
      boolean justMetaData,
      boolean[][] textureLoaded,
      int targetHighlightsHash,
      boolean[] leafShouldAffectBranchesDest,
      boolean[] metaLoadedDest,
      int extraAttempts,
      OldFormatSupport oldFormatSupport
   ) {
      if (!this.hasHadTerrain) {
         this.setHighlightsHash(targetHighlightsHash);
         metaLoadedDest[0] = true;
         return justMetaData;
      } else {
         return super.loadCacheTextures(
            mapProcessor,
            biomeRegistry,
            justMetaData,
            textureLoaded,
            targetHighlightsHash,
            leafShouldAffectBranchesDest,
            metaLoadedDest,
            extraAttempts,
            oldFormatSupport
         );
      }
   }

   @Override
   protected void onCacheLoadFailed(boolean[][] textureLoaded) {
   }

   public void convertCacheToOutdated(MapSaveLoad mapSaveLoad, String reason) {
      try {
         Path outdatedPath = Misc.convertToOutdated(this.getCacheFile().toPath(), 5);
         if (outdatedPath != null) {
            if (mapSaveLoad.removeTempCacheRequest(this.getCacheFile().toPath().toFile())) {
               mapSaveLoad.addTempCacheRequest(outdatedPath.toFile());
            }

            this.setCacheFile(outdatedPath.toFile());
            this.setShouldCache(true, reason);
         }
      } catch (IOException e) {
         WorldMap.LOGGER.error("suppressed exception", e);
      }
   }

   @Override
   public boolean shouldAffectLoadingRequestFrequency() {
      return this.loadState > 2 && super.shouldAffectLoadingRequestFrequency();
   }

   @Override
   public boolean hasRemovableSourceData() {
      return this.loadState == 2 && !this.beingWritten;
   }

   public int getHighlightsHash() {
      return this.highlightsHash;
   }

   public void setHighlightsHash(int highlightsHash) {
      this.highlightsHash = highlightsHash;
   }

   @Override
   protected boolean shouldLeafAffectCache(int targetHighlightsHash) {
      return (!this.shouldCache || this.dim.getMapWorld().isCacheOnlyMode()) && this.highlightsHash != targetHighlightsHash && !this.isBeingWritten();
   }

   public void updateTargetHighlightsHash() {
      this.targetHighlightsHash = this.getDim().getHighlightHandler().getRegionHash(this.getRegionX(), this.getRegionZ());
   }

   public int getTargetHighlightsHash() {
      return this.targetHighlightsHash;
   }

   public void setHasHadTerrain() {
      this.hasHadTerrain = true;
   }

   public void unsetHasHadTerrain() {
      this.hasHadTerrain = false;
   }

   public boolean hasHadTerrain() {
      return this.hasHadTerrain;
   }

   @Override
   public boolean hasLookedForCache() {
      return this.lookedForCache;
   }

   public void setLookedForCache(boolean lookedForCache) {
      this.lookedForCache = lookedForCache;
   }

   public boolean caveStartOutdated(int currentCaveStart, int currentCaveDepth) {
      return !this.normalMapData
         && (
            this.outdatedWithOtherLayers
               || currentCaveStart != this.getCaveStart()
               || currentCaveStart != Integer.MAX_VALUE && this.caveDepth != currentCaveDepth
         );
   }

   public void updateCaveMode() {
      this.caveStart = this.dim.getLayeredMapRegions().getLayer(this.caveLayer).getCaveStart();
      this.caveDepth = (Integer)WorldMap.INSTANCE.getConfigs().getClientConfigManager().getEffective(WorldMapProfiledConfigOptions.CAVE_MODE_DEPTH);
      this.setOutdatedWithOtherLayers(false);
   }

   public int getUpToDateCaveStart() {
      return this.dim.getLayeredMapRegions().getLayer(this.caveLayer).getCaveStart();
   }

   public boolean shouldConvertCacheToOutdatedOnFinishDim() {
      return this.recacheHasBeenRequested() || this.isOutdatedWithOtherLayers();
   }

   public void setOutdatedWithOtherLayers(boolean outdatedWithOtherLayers) {
      this.outdatedWithOtherLayers = outdatedWithOtherLayers;
   }

   public boolean isOutdatedWithOtherLayers() {
      return this.outdatedWithOtherLayers;
   }

   public int getCaveDepth() {
      return this.caveDepth;
   }

   public boolean canRequestReload_unsynced() {
      return !this.reloadHasBeenRequested()
         && !this.recacheHasBeenRequested()
         && !this.isRefreshing()
         && (this.getLoadState() == 0 || this.getLoadState() == 4 || this.getLoadState() == 2 && this.isBeingWritten());
   }

   public boolean isResaving() {
      return this.resaving;
   }

   public void setResaving(boolean resaving) {
      this.resaving = resaving;
   }
}
