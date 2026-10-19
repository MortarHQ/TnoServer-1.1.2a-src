package xaero.map.events;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.util.task.GetServerDetailsTask;
import com.mojang.realmsclient.util.task.LongRunningTask;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Queue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ChatType.Bound;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import xaero.lib.common.reflection.util.ReflectionUtils;
import xaero.lib.patreon.Patreon;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.file.worldsave.WorldDataHandler;
import xaero.map.mods.SupportMods;

public class ClientEvents {
   private RealmsServer latestRealm;
   private Field realmsTaskField;
   private Field realmsTaskServerField;

   public Screen handleGuiOpen(Screen gui) {
      if (gui instanceof RealmsLongRunningMcoTaskScreen) {
         try {
            if (this.realmsTaskField == null) {
               this.realmsTaskField = ReflectionUtils.getFieldReflection(
                  RealmsLongRunningMcoTaskScreen.class, "queuedTasks", "field_46707", "Ljava/util/List;", "f_302752_"
               );
               this.realmsTaskField.setAccessible(true);
            }

            if (this.realmsTaskServerField == null) {
               this.realmsTaskServerField = ReflectionUtils.getFieldReflection(
                  GetServerDetailsTask.class, "server", "field_20224", "Lnet/minecraft/class_4877;", "f_90327_"
               );
               this.realmsTaskServerField.setAccessible(true);
            }

            RealmsLongRunningMcoTaskScreen realmsTaskScreen = (RealmsLongRunningMcoTaskScreen)gui;

            for (LongRunningTask task : (List)this.realmsTaskField.get(realmsTaskScreen)) {
               if (task instanceof GetServerDetailsTask realmsTask) {
                  RealmsServer realm = (RealmsServer)this.realmsTaskServerField.get(realmsTask);
                  if (realm != null && (this.latestRealm == null || realm.id != this.latestRealm.id)) {
                     this.latestRealm = realm;
                  }
               }
            }
         } catch (Exception e) {
            WorldMap.LOGGER.error("suppressed exception", e);
         }
      }

      return gui;
   }

   public boolean handleRenderTick(boolean start) {
      if (!WorldMap.loaded) {
         return false;
      }

      Minecraft mc = Minecraft.getInstance();
      if (!start) {
         WorldMap.glObjectDeleter.work();
      }

      boolean shouldCancelGameRender = false;
      if (mc.player != null) {
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         if (worldmapSession != null) {
            MapProcessor mapProcessor = worldmapSession.getMapProcessor();
            if (!start) {
               mapProcessor.onRenderProcess(mc);
               mapProcessor.resetRenderStartTime();
               Queue<Runnable> minecraftScheduledTasks = mapProcessor.getMinecraftScheduledTasks();
               Runnable task = mapProcessor.getRenderStartTimeUpdater();
               Runnable[] currentTasks = minecraftScheduledTasks.toArray(new Runnable[0]);
               minecraftScheduledTasks.clear();
               minecraftScheduledTasks.add(task);

               for (Runnable t : currentTasks) {
                  minecraftScheduledTasks.add(t);
               }
            } else {
               if (!SupportMods.vivecraft && MapProcessor.shouldSkipWorldRender()) {
                  RenderSystem.enableDepthTest();
                  mc.level.pollLightUpdates();
                  mc.level.getChunkSource().getLightEngine().runLightUpdates();
                  shouldCancelGameRender = true;
               }

               if (mapProcessor != null) {
                  mapProcessor.setMainValues();
               }
            }
         }
      }

      return shouldCancelGameRender;
   }

   public void handleDrawScreen(Screen gui) {
      if (!Patreon.needsNotification() && WorldMap.isOutdated) {
         WorldMap.isOutdated = false;
      }
   }

   public void handlePlayerSetSpawnEvent(BlockPos spawn, ClientLevel world) {
      WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
      if (worldmapSession != null) {
         MapProcessor mapProcessor = worldmapSession.getMapProcessor();
         mapProcessor.updateWorldSpawn(spawn, world);
      }
   }

   public void handleWorldUnload(LevelAccessor world) {
      if (Minecraft.getInstance().player != null) {
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         if (worldmapSession != null) {
            MapProcessor mapProcessor = worldmapSession.getMapProcessor();
            if (world == mapProcessor.mainWorld) {
               mapProcessor.onWorldUnload();
            }
         }
      }

      if (world instanceof ServerLevel sw) {
         WorldDataHandler.onServerWorldUnload(sw);
      }
   }

   public RealmsServer getLatestRealm() {
      return this.latestRealm;
   }

   public boolean handleRenderCrosshairOverlay(GuiGraphics guiGraphics) {
      if (Minecraft.getInstance().options.hideGui) {
         return false;
      }

      WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
      MapProcessor mapProcessor = worldmapSession == null ? null : worldmapSession.getMapProcessor();
      String crosshairMessage = mapProcessor == null ? null : mapProcessor.getCrosshairMessage();
      if (crosshairMessage != null) {
         int messageWidth = Minecraft.getInstance().font.width(crosshairMessage);
         RenderSystem.disableBlend();
         Window window = Minecraft.getInstance().getWindow();
         guiGraphics.drawString(
            Minecraft.getInstance().font, crosshairMessage, window.getGuiScaledWidth() / 2 - messageWidth / 2, window.getGuiScaledHeight() / 2 + 60, -1
         );
         RenderSystem.enableBlend();
      }

      return false;
   }

   public boolean handleClientPlayerChatReceivedEvent(Bound chatType, Component component, GameProfile gameProfile) {
      return component == null ? false : this.handleChatMessage(gameProfile == null ? null : gameProfile.getName(), component);
   }

   public boolean handleClientSystemChatReceivedEvent(Component component) {
      if (component == null) {
         return false;
      }

      String textString = component.getString();
      if (textString.contains("§r§e§s§e§t§x§a§e§r§o")) {
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         worldmapSession.getMapProcessor().setConsideringNetherFairPlayMessage(false);
         worldmapSession.getMapProcessor().setFairplayMessageReceived(false);
      }

      if (textString.contains("§x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r")) {
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         worldmapSession.getMapProcessor().setConsideringNetherFairPlayMessage(true);
      }

      if (textString.contains("§f§a§i§r§x§a§e§r§o")) {
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         worldmapSession.getMapProcessor().setFairplayMessageReceived(true);
      }

      return this.handleChatMessage(I18n.get("gui.xaero_waypoint_server_shared", new Object[0]), component);
   }

   private boolean handleChatMessage(String playerName, Component text) {
      return false;
   }

   public void handlePlayerTickStart(Player player) {
      if (player == Minecraft.getInstance().player) {
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         if (worldmapSession != null) {
            worldmapSession.getControlsHandler().handleKeyEvents();
         }
      }
   }

   public void handleClientTickStart() {
      if (Minecraft.getInstance().player != null) {
         if (!WorldMap.loaded) {
            return;
         }

         WorldMap.crashHandler.checkForCrashes();
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         if (worldmapSession != null) {
            MapProcessor mapProcessor = worldmapSession.getMapProcessor();
            mapProcessor.onClientTickStart();
         }
      }
   }

   public void handleClientRunTickStart() {
      if (Minecraft.getInstance().player != null) {
         if (!WorldMap.loaded) {
            return;
         }

         WorldMap.crashHandler.checkForCrashes();
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         if (worldmapSession != null) {
            worldmapSession.getMapProcessor().getWorldDataHandler().handleRenderExecutor();
         }
      }
   }
}
