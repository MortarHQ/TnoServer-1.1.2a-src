package xaero.map.file.worldsave;

import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.LevelResource;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.capabilities.CapabilityGetter;
import xaero.map.capabilities.ServerWorldCapabilities;
import xaero.map.executor.Executor;
import xaero.map.region.MapRegion;
import xaero.map.world.MapWorld;

public class WorldDataHandler {
   private final Executor renderExecutor;
   private WorldDataReader reader;
   private ServerLevel worldServer;
   private Path worldDir;

   public WorldDataHandler(WorldDataReader reader, Executor renderExecutor) throws NoSuchFieldException, SecurityException {
      this.reader = reader;
      this.renderExecutor = renderExecutor;
   }

   public void handleRenderExecutor() {
      this.renderExecutor.runAllTasks();
   }

   public void prepareSingleplayer(Level world, MapProcessor mapProcessor) {
      MapWorld mapWorld = mapProcessor.getMapWorld();
      if (world != null && mapWorld.getCurrentDimension().isUsingWorldSave()) {
         ResourceKey<Level> dimId = mapWorld.getCurrentDimensionId();
         this.worldServer = Minecraft.getInstance().getSingleplayerServer().getLevel(dimId);
         if (this.worldServer != null) {
            Path overworldDir = this.worldServer.getServer().getWorldPath(LevelResource.ROOT);
            this.worldDir = DimensionType.getStorageFolder(dimId, overworldDir);
         } else {
            this.worldDir = null;
         }
      } else {
         this.worldServer = null;
         this.worldDir = null;
      }
   }

   public WorldDataHandler.Result buildRegion(
      MapRegion region, HolderLookup<Block> blockLookup, Registry<Block> blockRegistry, Registry<Fluid> fluidRegistry, boolean loading, int[] chunkCountDest
   ) throws IOException {
      if (this.worldServer == null) {
         WorldMap.LOGGER.info("Tried loading a region for a null server world!");
         return WorldDataHandler.Result.CANCEL;
      }

      ServerWorldCapabilities serverCaps;
      try {
         serverCaps = CapabilityGetter.getServerWorldCapabilities(this.worldServer);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }

      boolean shouldCancel = false;
      synchronized (serverCaps) {
         if (serverCaps.loaded) {
            boolean buildResult = this.reader
               .buildRegion(region, this.worldServer, blockLookup, blockRegistry, fluidRegistry, loading, chunkCountDest, this.renderExecutor);
            return buildResult ? WorldDataHandler.Result.SUCCESS : WorldDataHandler.Result.FAIL;
         }

         shouldCancel = true;
      }

      if (shouldCancel) {
         WorldMap.LOGGER.info("Tried loading a region for an unloaded server world!");
         return WorldDataHandler.Result.CANCEL;
      } else {
         WorldMap.LOGGER.info("Server world capability required for Xaero's World Map not present!");
         return WorldDataHandler.Result.FAIL;
      }
   }

   public static void onServerWorldUnload(ServerLevel sw) {
      ServerWorldCapabilities serverCaps;
      try {
         serverCaps = CapabilityGetter.getServerWorldCapabilities(sw);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }

      synchronized (serverCaps) {
         serverCaps.loaded = false;
      }
   }

   public ServerLevel getWorldServer() {
      return this.worldServer;
   }

   public WorldDataReader getWorldDataReader() {
      return this.reader;
   }

   public Path getWorldDir() {
      return this.worldDir;
   }

   Executor getWorldDataRenderExecutor() {
      return this.renderExecutor;
   }

   public enum Result {
      SUCCESS,
      FAIL,
      CANCEL;
   }
}
