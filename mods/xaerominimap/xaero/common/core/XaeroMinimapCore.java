package xaero.common.core;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.RenderType;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import org.joml.Matrix4f;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.XaeroMinimapSession;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.radar.icon.creator.render.trace.EntityRenderTracer;
import xaero.hud.minimap.world.container.MinimapWorldContainerUtil;
import xaero.hud.pushbox.BuiltInPushBoxes;
import xaero.hud.pushbox.boss.IBossHealthPushBox;
import xaero.hud.pushbox.effect.IPotionEffectsPushBox;
import xaero.lib.common.util.IOUtils;

public class XaeroMinimapCore {
   public static IXaeroMinimap modMain;
   public static Field chunkCleanField = null;
   public static XaeroMinimapSession currentSession;
   public static Matrix4f waypointsProjection = new Matrix4f();
   public static Matrix4f waypointModelView = new Matrix4f();

   public static void ensureField() {
      if (chunkCleanField == null) {
         try {
            chunkCleanField = LevelChunk.class.getDeclaredField("xaero_chunkClean");
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

   private static void onChunkLightData(int x, int z) {
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
      IXaeroMinimapSMultiBlockChangePacket packetAccess = (IXaeroMinimapSMultiBlockChangePacket)packetIn;
      chunkUpdateCallback(packetAccess.xaero_mm_getSectionPos().getX(), packetAccess.xaero_mm_getSectionPos().getZ());
   }

   public static void onPlayNetHandler(ClientPacketListener netHandler, ClientboundLoginPacket packet) {
      if (HudMod.INSTANCE != null) {
         HudMod.INSTANCE.tryLoadLater();
      }

      if (isModLoaded()) {
         if (modMain.getInterfaces().getMinimapInterface().getCrashedWith() == null) {
            try {
               IXaeroMinimapClientPlayNetHandler netHandlerAccess = (IXaeroMinimapClientPlayNetHandler)netHandler;
               if (netHandlerAccess.getXaero_minimapSession() != null) {
                  return;
               }

               if (currentSession != null) {
                  MinimapLogs.LOGGER.info("Previous hud session still active. Probably using MenuMobs. Forcing it to end...");
                  cleanupCurrentSession();
               }

               XaeroMinimapSession minimapSession = modMain.createSession();
               currentSession = minimapSession;
               minimapSession.init(netHandler);
               netHandlerAccess.setXaero_minimapSession(minimapSession);
            } catch (Throwable e) {
               if (currentSession != null) {
                  cleanupCurrentSession();
               }

               RuntimeException wrappedException = new RuntimeException("Exception initializing Xaero's Minimap! ", e);
               modMain.getInterfaces().getMinimapInterface().setCrashedWith(wrappedException);
            }
         }
      }
   }

   private static void cleanupCurrentSession() {
      try {
         currentSession.tryCleanup();
      } catch (Throwable supressed) {
         MinimapLogs.LOGGER.error("suppressed exception", supressed);
      } finally {
         currentSession = null;
      }
   }

   public static void onPlayNetHandlerCleanup(ClientPacketListener netHandler) {
      if (isModLoaded()) {
         try {
            XaeroMinimapSession netHandlerSession = ((IXaeroMinimapClientPlayNetHandler)netHandler).getXaero_minimapSession();
            if (netHandlerSession == null) {
               return;
            }

            try {
               netHandlerSession.tryCleanup();
            } finally {
               if (netHandlerSession == currentSession) {
                  currentSession = null;
               }

               ((IXaeroMinimapClientPlayNetHandler)netHandler).setXaero_minimapSession(null);
            }
         } catch (Throwable e) {
            RuntimeException wrappedException = new RuntimeException("Exception finalizing Xaero's Minimap! ", e);
            modMain.getInterfaces().getMinimapInterface().setCrashedWith(wrappedException);
         }
      }
   }

   public static void beforeRespawn(Player player) {
      if (isModLoaded()) {
         if (player == Minecraft.getInstance().player) {
            MinimapSession minimapSession = BuiltInHudModules.MINIMAP.getCurrentSession();
            if (minimapSession != null) {
               minimapSession.getWaypointSession().getDeathpointHandler().createDeathpoint(player);
            }
         }
      }
   }

   public static void onProjectionMatrix(Matrix4f matrixIn) {
      waypointsProjection.identity();
      waypointsProjection.mul(matrixIn);
   }

   public static void onWorldModelViewMatrix(Matrix4f matrix) {
      waypointModelView.identity();
      waypointModelView.mul(matrix);
   }

   public static void beforeIngameGuiRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      if (isModLoaded()) {
         HudMod.INSTANCE.getEvents().handleRenderGameOverlayEventPre(guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(true));
      }
   }

   public static void afterIngameGuiRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      if (isModLoaded()) {
         HudMod.INSTANCE.getEvents().handleRenderGameOverlayEventPost();
      }
   }

   public static void onRenderStatusEffectOverlayPost(GuiGraphics guiGraphics) {
      if (isModLoaded()) {
         IPotionEffectsPushBox potionEffectsPushBox = BuiltInPushBoxes.getPotionEffectPushBox(modMain);
         if (potionEffectsPushBox != null) {
            potionEffectsPushBox.setActive(true);
         }
      }
   }

   public static void onBossHealthRender(int h) {
      if (isModLoaded()) {
         IBossHealthPushBox bossHealthPushBox = BuiltInPushBoxes.getBossHealthPushBox(modMain);
         if (bossHealthPushBox != null) {
            bossHealthPushBox.setActive(true);
            bossHealthPushBox.setLastBossHealthHeight(h);
         }
      }
   }

   public static void onEntityIconsModelRenderDetection(EntityModel<?> model, VertexConsumer vertexConsumer, int color) {
      if (EntityRenderTracer.TRACING_MODEL_RENDERS) {
         modMain.getInterfaces().getMinimapInterface().getMinimapFBORenderer().onRadarIconModelRenderTrace(model, vertexConsumer, color);
      }
   }

   public static void onEntityIconsModelPartRenderDetection(ModelPart modelRenderer, int color) {
      if (EntityRenderTracer.TRACING_MODEL_RENDERS) {
         modMain.getInterfaces().getMinimapInterface().getMinimapFBORenderer().onEntityIconModelPartRenderTrace(modelRenderer, color);
      }
   }

   public static void onDeleteWorld(LevelStorageAccess levelStorageAccess) {
      if (isModLoaded()) {
         String worldFolder = levelStorageAccess.getLevelPath(LevelResource.ROOT).getParent().getFileName().toString();
         if (!worldFolder.isEmpty()) {
            String minimapWorldFolder = MinimapWorldContainerUtil.convertWorldFolderToContainerNode(worldFolder);
            Path minimapWorldFolderPath = HudMod.INSTANCE.getMinimapFolder().resolve(minimapWorldFolder);
            if (minimapWorldFolderPath.toFile().exists()) {
               try {
                  IOUtils.deleteFile(minimapWorldFolderPath, 20);
                  MinimapLogs.LOGGER.info(String.format("Deleted minimap world data at %s", minimapWorldFolderPath));
               } catch (IOException e) {
                  MinimapLogs.LOGGER.error(String.format("Failed to delete minimap world data at %s!", minimapWorldFolderPath), e);
               }
            }
         }
      }
   }

   public static void onSpawn(ClientboundSetDefaultSpawnPositionPacket packetIn) {
      if (isModLoaded()) {
         modMain.getEvents().handlePlayerSetSpawnEvent(packetIn.getPos(), Minecraft.getInstance().level);
      }
   }

   public static boolean onLocalPlayerCommand(String command) {
      return !isModLoaded() ? false : modMain.getEvents().handleClientSendChatEvent(command);
   }

   public static boolean onSystemChat(Component component) {
      return !isModLoaded() ? false : modMain.getEvents().handleClientSystemChatReceivedEvent(component);
   }

   public static boolean onHandleDisguisedChatMessage(Bound chatType, Component component) {
      return !isModLoaded() ? true : !modMain.getEvents().handleClientPlayerChatReceivedEvent(chatType, component, null);
   }

   public static boolean isModLoaded() {
      return modMain != null && modMain.isLoadedClient();
   }

   public static boolean onRenderStatusEffectOverlay(GuiGraphics guiGraphics) {
      return !isModLoaded() ? false : modMain.getEvents().handleRenderStatusEffectOverlay(guiGraphics);
   }

   public static boolean onRenderCrosshair(GuiGraphics guiGraphics) {
      return !isModLoaded() ? false : modMain.getEvents().handleRenderCrosshairOverlay(guiGraphics);
   }

   public static void handleRenderModOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      if (isModLoaded()) {
         modMain.getModClientEvents().handleRenderModOverlay(guiGraphics, deltaTracker);
      }
   }

   public static void onBufferSourceGetBuffer(IBufferSource mixinBufferSource, RenderType renderType) {
      if (EntityRenderTracer.TRACING_MODEL_RENDERS) {
         mixinBufferSource.setXaero_lastRenderType(renderType);
      }
   }

   public static boolean onToggleKeyIsDown(ToggleKeyMapping keyMapping) {
      if (!isModLoaded()) {
         return false;
      } else {
         return Minecraft.getInstance().options == null ? false : modMain.getEvents().handleForceToggleKeyMapping(keyMapping);
      }
   }
}
