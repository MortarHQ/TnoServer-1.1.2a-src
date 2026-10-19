package dev.ftb.mods.ftbchunks.client.map;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.NativeImage.Format;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.client.ClientTaskQueue;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.math.XZ;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapRegion implements MapTask {
   public static final Color4I GRID_COLOR = Color4I.rgba(70, 70, 70, 50);
   public final MapDimension dimension;
   public final XZ pos;
   private MapRegionData data;
   private long lastDataAccess;
   private boolean isLoadingData;
   private boolean shouldSave;
   private NativeImage renderedMapImage;
   private boolean updateRenderedMapImage;
   private boolean updateRenderedMapTexture;
   private int renderedMapImageTextureId;
   private boolean mapImageLoaded;
   private boolean renderingMapImage;
   private final Map<XZ, MapChunk> chunks = new HashMap<>();

   public MapRegion(MapDimension d, XZ p) {
      this.dimension = d;
      this.pos = p;
      this.data = null;
      this.isLoadingData = false;
      this.shouldSave = false;
      this.renderedMapImage = null;
      this.updateRenderedMapImage = true;
      this.updateRenderedMapTexture = true;
      this.renderedMapImageTextureId = -1;
      this.mapImageLoaded = false;
   }

   public void created() {
      this.dimension.markDirty();
   }

   public boolean isDataLoaded() {
      return this.data != null;
   }

   public long getLastDataAccess() {
      return this.lastDataAccess;
   }

   public boolean isMapImageLoaded() {
      return this.mapImageLoaded;
   }

   @NotNull
   public MapRegionData getDataBlocking() {
      synchronized (this.dimension.getManager().lock) {
         return this.getDataBlockingNoSync();
      }
   }

   @NotNull
   public MapRegionData getDataBlockingNoSync() {
      if (this.data == null) {
         this.data = new MapRegionData(this);

         try {
            this.data.read();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      }

      this.lastDataAccess = System.currentTimeMillis();
      return this.data;
   }

   @Nullable
   public MapRegionData getData() {
      if (this.data == null && !this.isLoadingData) {
         this.isLoadingData = true;
         FTBChunksClient.MAP_EXECUTOR.execute(this::getDataBlocking);
      }

      if (this.data != null) {
         this.lastDataAccess = System.currentTimeMillis();
      }

      return this.data;
   }

   public NativeImage getRenderedMapImage() {
      synchronized (this.dimension.getManager().lock) {
         if (this.renderedMapImage == null) {
            this.renderedMapImage = new NativeImage(Format.RGBA, 512, 512, true);
            this.renderedMapImage.fillRect(0, 0, 512, 512, 0);
            this.update(false);
         }
      }

      if (this.updateRenderedMapImage && !this.renderingMapImage) {
         this.updateRenderedMapImage = false;
         this.mapImageLoaded = false;
         this.renderingMapImage = true;
         FTBChunksClient.MAP_EXECUTOR.execute(new RenderMapImageTask(this));
      }

      return this.renderedMapImage;
   }

   public int getRenderedMapImageTextureId() {
      if (this.renderedMapImageTextureId == -1) {
         this.renderedMapImageTextureId = FTBChunksClient.INSTANCE.generateTextureId(512, 512);
      }

      this.getRenderedMapImage();
      if (this.updateRenderedMapTexture) {
         this.mapImageLoaded = false;
         Minecraft.getInstance().submit(() -> {
            RenderSystem.bindTexture(this.renderedMapImageTextureId);
            this.uploadRenderedMapImage();
            this.mapImageLoaded = true;
            FTBChunksClient.INSTANCE.scheduleMinimapUpdate();
         });
         this.updateRenderedMapTexture = false;
      }

      return this.renderedMapImageTextureId;
   }

   public void release(boolean releaseMapChunks) {
      if (this.shouldSave && this.data != null) {
         try {
            this.data.write();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      }

      if (releaseMapChunks) {
         this.chunks.clear();
      }

      this.data = null;
      this.lastDataAccess = 0L;
      this.isLoadingData = false;
      this.releaseMapImage();
   }

   public void releaseIfStale(long now, long releaseIntervalMillis) {
      if (now - this.lastDataAccess > releaseIntervalMillis && this.data != null) {
         if (this.pos.equals(XZ.regionFromChunk(Minecraft.getInstance().player.chunkPosition()))) {
            FTBChunks.LOGGER.debug("not releasing region {} / {} - player present", this, this.pos);
            return;
         }

         for (ChunkPos cp : FTBChunksClient.INSTANCE.getPendingRerender()) {
            if (XZ.regionFromChunk(cp).equals(this.pos)) {
               FTBChunks.LOGGER.debug("not releasing region {} / {} - re-render pending", this, this.pos);
               return;
            }
         }

         FTBChunks.LOGGER.debug("releasing data for region {} / {} (not accessed in last {} seconds)", this, this.pos, releaseIntervalMillis / 1000L);
         this.release(false);
      }
   }

   public void releaseMapImage() {
      synchronized (this.dimension.getManager().lock) {
         if (this.renderedMapImage != null) {
            this.renderedMapImage.close();
            this.renderedMapImage = null;
         }
      }

      if (this.renderedMapImageTextureId != -1) {
         GlStateManager._deleteTexture(this.renderedMapImageTextureId);
         this.renderedMapImageTextureId = -1;
      }

      this.mapImageLoaded = false;
   }

   @Override
   public void runMapTask() throws Exception {
      if (this.data != null) {
         this.data.write();
      }
   }

   public void setRenderedMapImageRGBA(int x, int z, int col) {
      synchronized (this.dimension.getManager().lock) {
         this.getRenderedMapImage().setPixelRGBA(x, z, col);
      }
   }

   private void uploadRenderedMapImage() {
      synchronized (this.dimension.getManager().lock) {
         this.getRenderedMapImage().upload(0, 0, 0, false);
      }
   }

   public void afterImageRenderTask() {
      synchronized (this.dimension.getManager().lock) {
         this.updateRenderedMapTexture = true;
         FTBChunksClient.INSTANCE.scheduleMinimapUpdate();
         this.renderingMapImage = false;
      }
   }

   public void update(boolean save) {
      if (save) {
         this.markDirty();
         this.dimension.markDirty();
      }

      this.updateRenderedMapImage = true;
      this.updateRenderedMapTexture = true;
      FTBChunksClient.INSTANCE.scheduleMinimapUpdate();
   }

   public MapRegion offset(int x, int z) {
      return this.dimension.getRegion(this.pos.offset(x, z));
   }

   public RegionSyncKey getSyncKey() {
      return new RegionSyncKey(this.dimension.dimension, this.pos.x(), this.pos.z(), MathUtils.RAND.nextInt());
   }

   public double distToPlayer() {
      return MathUtils.distSq(
         this.pos.x() * 512.0 + 256.0, this.pos.z() * 512.0 + 256.0, Minecraft.getInstance().player.getX(), Minecraft.getInstance().player.getZ()
      );
   }

   @Override
   public String toString() {
      return this.pos.toRegionString();
   }

   public MapChunk getMapChunk(XZ xz) {
      return this.chunks.get(xz);
   }

   public MapChunk getOrCreateMapChunk(XZ xz) {
      return this.chunks.computeIfAbsent(xz, p -> new MapChunk(this, p).created());
   }

   public MapChunk getChunkForAbsoluteChunkPos(XZ pos) {
      XZ effectivePos = pos.x() == (pos.x() & 31) && pos.z() == (pos.z() & 31) ? pos : XZ.of(pos.x() & 31, pos.z() & 31);
      synchronized (this.dimension.getManager().lock) {
         return this.getOrCreateMapChunk(effectivePos);
      }
   }

   public void addMapChunk(MapChunk mapChunk) {
      this.chunks.put(mapChunk.getPos(), mapChunk);
   }

   public Collection<MapChunk> getModifiedChunks() {
      return this.chunks.values().stream().filter(c -> c.getModified() > 0L).toList();
   }

   public void saveIfChanged() {
      if (this.shouldSave) {
         ClientTaskQueue.queue(this);
         this.shouldSave = false;
      }
   }

   public void markDirty() {
      this.shouldSave = true;
   }
}
