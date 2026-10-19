package xaero.map.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ChatType.Bound;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import xaero.lib.common.util.IOUtils;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.file.MapSaveLoad;
import xaero.map.world.MapWorld;

public class XaeroWorldMapCore {
   public static Field chunkCleanField = null;
   public static WorldMapSession currentSession;

   public static void ensureField() {
      if (chunkCleanField == null) {
         try {
            chunkCleanField = LevelChunk.class.getDeclaredField("xaero_wm_chunkClean");
         } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public static void chunkUpdateCallback(int chunkX, int chunkZ) {
      ensureField();
      Level world = Minecraft.getInstance().level;
      if (world != null) {
         try {
            for (int x = chunkX - 1; x < chunkX + 2; x++) {
               for (int z = chunkZ - 1; z < chunkZ + 2; z++) {
                  LevelChunk chunk = world.getChunk(x, z);
                  if (chunk != null) {
                     chunkCleanField.set(chunk, false);
                  }
               }
            }
         } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public static void onChunkData(int x, int z, ClientboundLevelChunkPacketData packetIn) {
      chunkUpdateCallback(x, z);
   }

   public static void onChunkLightData(int x, int z) {
      chunkUpdateCallback(x, z);
   }

   public static void onHandleLevelChunkWithLight(ClientboundLevelChunkWithLightPacket packet) {
      onChunkLightData(packet.getX(), packet.getZ());
   }

   public static void onHandleLightUpdatePacket(ClientboundLightUpdatePacket packet) {
      onChunkLightData(packet.getX(), packet.getZ());
   }

   public static void onQueueLightRemoval(ClientboundForgetLevelChunkPacket packet) {
      onChunkLightData(packet.pos().x, packet.pos().z);
   }

   public static void onBlockChange(ClientboundBlockUpdatePacket packetIn) {
      chunkUpdateCallback(packetIn.getPos().getX() >> 4, packetIn.getPos().getZ() >> 4);
   }

   public static void onMultiBlockChange(ClientboundSectionBlocksUpdatePacket packetIn) {
      IWorldMapSMultiBlockChangePacket packetAccess = (IWorldMapSMultiBlockChangePacket)packetIn;
      chunkUpdateCallback(packetAccess.xaero_wm_getSectionPos().getX(), packetAccess.xaero_wm_getSectionPos().getZ());
   }

   public static void onPlayNetHandler(ClientPacketListener netHandler, ClientboundLoginPacket packet) {
      if (WorldMap.loaded) {
         try {
            IWorldMapClientPlayNetHandler netHandlerAccess = (IWorldMapClientPlayNetHandler)netHandler;
            if (netHandlerAccess.getXaero_worldmapSession() != null) {
               return;
            }

            if (currentSession != null) {
               WorldMap.LOGGER.info("Previous world map session still active. Probably using MenuMobs. Forcing it to end...");
               cleanupCurrentSession();
            }

            WorldMapSession worldmapSession = new WorldMapSession();
            currentSession = worldmapSession;
            worldmapSession.init(netHandler, packet.commonPlayerSpawnInfo().seed());
            netHandlerAccess.setXaero_worldmapSession(worldmapSession);
            WorldMap.settings.updateRegionCacheHashCode();
         } catch (Throwable e) {
            if (currentSession != null) {
               cleanupCurrentSession();
            }

            RuntimeException wrappedException = new RuntimeException("Exception initializing Xaero's World Map! ", e);
            WorldMap.crashHandler.setCrashedBy(wrappedException);
         }
      }
   }

   private static void cleanupCurrentSession() {
      try {
         currentSession.cleanup();
      } catch (Throwable supressed) {
         WorldMap.LOGGER.error("suppressed exception", supressed);
      } finally {
         currentSession = null;
      }
   }

   public static void onPlayNetHandlerCleanup(ClientPacketListener netHandler) {
      if (WorldMap.loaded) {
         try {
            WorldMapSession netHandlerSession = ((IWorldMapClientPlayNetHandler)netHandler).getXaero_worldmapSession();
            if (netHandlerSession == null) {
               return;
            }

            try {
               netHandlerSession.cleanup();
            } finally {
               if (netHandlerSession == currentSession) {
                  currentSession = null;
               }

               ((IWorldMapClientPlayNetHandler)netHandler).setXaero_worldmapSession(null);
            }
         } catch (Throwable e) {
            RuntimeException wrappedException = new RuntimeException("Exception finalizing Xaero's World Map! ", e);
            WorldMap.crashHandler.setCrashedBy(wrappedException);
         }
      }
   }

   public static void onDeleteWorld(LevelStorageAccess levelStorageAccess) {
      if (WorldMap.loaded) {
         String folderName = levelStorageAccess.getLevelPath(LevelResource.ROOT).getParent().getFileName().toString();
         String worldRootId = MapWorld.convertWorldFolderToRootId(5, folderName);
         if (!worldRootId.isEmpty()) {
            Path worldMapCacheFolder = MapSaveLoad.getRootFolder(worldRootId);
            if (worldMapCacheFolder.toFile().exists()) {
               try {
                  IOUtils.deleteFileIf(worldMapCacheFolder, path -> {
                     String pathString = worldMapCacheFolder.relativize(path).toString().replace('\\', '/');
                     return pathString.contains("/cache/") || pathString.endsWith("/cache") || pathString.contains("/cache_");
                  }, 20);
                  WorldMap.LOGGER.info(String.format("Deleted world map cache at %s", worldMapCacheFolder));
               } catch (IOException e) {
                  WorldMap.LOGGER.error(String.format("Failed to delete world map cache at %s!", worldMapCacheFolder), e);
               }
            }
         }
      }
   }

   public static void onMinecraftRunTick() {
      if (WorldMap.events != null) {
         WorldMap.events.handleClientRunTickStart();
      }
   }

   public static boolean onSystemChat(Component component) {
      return !WorldMap.loaded ? false : WorldMap.events.handleClientSystemChatReceivedEvent(component);
   }

   public static boolean onHandleDisguisedChatMessage(Bound chatType, Component component) {
      return !WorldMap.loaded ? true : !WorldMap.events.handleClientPlayerChatReceivedEvent(chatType, component, null);
   }

   public static boolean onRenderCall(boolean renderingInGame) {
      if (!WorldMap.loaded) {
         return renderingInGame;
      } else {
         return WorldMap.events.handleRenderTick(true) ? false : renderingInGame;
      }
   }

   public static void handlePlayerSetSpawnPacket(ClientboundSetDefaultSpawnPositionPacket packet) {
      if (WorldMap.loaded) {
         WorldMap.events.handlePlayerSetSpawnEvent(packet.getPos(), Minecraft.getInstance().level);
      }
   }

   public static boolean onRenderCrosshair(GuiGraphics guiGraphics) {
      return !WorldMap.loaded ? false : WorldMap.events.handleRenderCrosshairOverlay(guiGraphics);
   }
}
