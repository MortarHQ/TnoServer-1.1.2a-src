package toni.chunkactivitytracker;

import java.nio.file.Path;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStopped;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartTick;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toni.chunkactivitytracker.data.ChunkActivityInfo;
import toni.chunkactivitytracker.data.ChunkActivityMap;
import toni.chunkactivitytracker.foundation.config.AllConfigs;

@Mod("chunkactivitytracker")
public class ChunkActivityTracker {
   public static final String MODNAME = "Chunk Activity Tracker";
   public static final String ID = "chunkactivitytracker";
   public static final Logger LOGGER = LogManager.getLogger("Chunk Activity Tracker");
   private static MinecraftServer currentServer;
   private static long lastTime = System.nanoTime();
   private static final float NANOSECONDS_PER_TICK = 5.0E7F;

   public ChunkActivityTracker(IEventBus modEventBus, ModContainer modContainer) {
      ServerLifecycleEvents.SERVER_STARTING.register((ServerStarting)server -> currentServer = server);
      modEventBus.addListener(this::commonSetup);
      modEventBus.addListener(this::clientSetup);
      AllConfigs.register((type, spec) -> modContainer.registerConfig(type, spec));
   }

   public static long getSecondsInCurrentChunk(Player player) {
      Level level = player.level();
      if (level == null) {
         return 0L;
      }

      ChunkActivityInfo chunkInfo = ChunkActivityMap.getChunkInfo(level.dimension(), player.chunkPosition());
      return chunkInfo == null ? 0L : chunkInfo.getPlayerTime(player.getUUID());
   }

   public static long getTotalTimeInChunk(Level level, ChunkPos chunkPos) {
      if (level == null) {
         return 0L;
      }

      ChunkActivityInfo chunkInfo = ChunkActivityMap.getChunkInfo(level.dimension(), chunkPos);
      if (chunkInfo == null) {
         return 0L;
      }

      long sum = 0L;

      for (Long kvp : chunkInfo.getPlayerTimeMap().values()) {
         sum += kvp;
      }

      return sum;
   }

   public static Path getWorldPath(LevelResource resource) {
      if (currentServer == null) {
         System.out.println("No MinecraftServer instance available.");
         return null;
      } else {
         return currentServer.getWorldPath(resource);
      }
   }

   public void onInitialize() {
      ServerLifecycleEvents.SERVER_STOPPED.register((ServerStopped)server -> {
         currentServer = null;
         ChunkActivityMap.clear();
      });
      UseBlockCallback.EVENT.register((UseBlockCallback)(player, block, hand, hit) -> {
         if (player.level().isClientSide) {
            return InteractionResult.PASS;
         }

         ChunkActivityInfo chunkInfo = ChunkActivityMap.getChunkInfo(player.level().dimension(), player.chunkPosition());
         if (chunkInfo != null) {
            chunkInfo.incrementBlocksPlaced(player.getUUID());
         }

         return InteractionResult.PASS;
      });
      ServerTickEvents.START_SERVER_TICK.register((StartTick)event -> {
         long currentTime = System.nanoTime();
         float delta = (float)(currentTime - lastTime) / 5.0E7F;
         if (!(delta < 20.0F)) {
            lastTime = currentTime;
            event.getPlayerList().getPlayers().forEach(player -> {
               Level level = player.level();
               if (level != null) {
                  ChunkActivityInfo chunkInfo = ChunkActivityMap.getChunkInfo(level.dimension(), player.chunkPosition());
                  if (chunkInfo == null) {
                     if ((Boolean)AllConfigs.server().storeHeightmaps.get()) {
                        LevelChunk chunk = level.getChunkAt(player.blockPosition());
                        if (chunk == null) {
                           return;
                        }

                        chunkInfo = ChunkActivityMap.createChunkInfo(level.dimension(), chunk);
                     } else {
                        chunkInfo = ChunkActivityMap.createChunkInfo(level.dimension(), player.chunkPosition());
                     }
                  }

                  chunkInfo.updatePlayerTime(player.getUUID());
               }
            });
         }
      });
   }

   public void onInitializeClient() {
   }

   public void commonSetup(FMLCommonSetupEvent event) {
      this.onInitialize();
   }

   public void clientSetup(FMLClientSetupEvent event) {
      this.onInitializeClient();
   }
}
