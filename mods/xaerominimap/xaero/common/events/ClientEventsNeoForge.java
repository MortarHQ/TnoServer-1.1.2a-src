package xaero.common.events;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Post;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Pre;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.ScreenEvent.Opening;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;
import xaero.common.HudMod;
import xaero.common.core.XaeroMinimapCore;

public class ClientEventsNeoForge extends ClientEvents {
   public ClientEventsNeoForge(HudMod modMain) {
      super(modMain);
   }

   @SubscribeEvent
   public void handleGuiOpenEvent(Opening event) {
      Screen guiBefore = event.getNewScreen();
      Screen gui = this.handleGuiOpen(guiBefore);
      if (guiBefore != null && gui == null) {
         event.setCanceled(true);
      } else {
         event.setNewScreen(gui);
      }
   }

   @SubscribeEvent
   public void handleRenderGameOverlayEventPre(Pre event) {
      this.handleRenderGameOverlayEventPre(event.getGuiGraphics(), event.getPartialTick().getGameTimeDeltaPartialTick(true));
   }

   @SubscribeEvent
   public void handleRenderGameOverlayEventPost(Post event) {
      this.handleRenderGameOverlayEventPost();
   }

   @SubscribeEvent
   protected void handleRenderGameOverlayEventPreLayer(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Pre event) {
      if (event.getName().equals(VanillaGuiLayers.EFFECTS) && this.handleRenderStatusEffectOverlay(event.getGuiGraphics())) {
         event.setCanceled(true);
      }

      if (event.getName().equals(VanillaGuiLayers.CROSSHAIR) && this.handleRenderCrosshairOverlay(event.getGuiGraphics())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   protected void handleRenderGameOverlayEventPostLayer(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Post event) {
      if (event.getName().equals(VanillaGuiLayers.EFFECTS)) {
         XaeroMinimapCore.onRenderStatusEffectOverlayPost(event.getGuiGraphics());
      }
   }

   @SubscribeEvent
   public void handleClientSendChatEvent(ClientChatEvent e) {
      if (this.handleClientSendChatEvent(e.getMessage())) {
         e.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void handleClientChatReceivedEvent(ClientChatReceivedEvent e) {
      if (e.getMessage() != null) {
         if (Minecraft.getInstance().getConnection() != null) {
            Component text = e.getMessage();
            PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(e.getSender());
            GameProfile gameProfile = playerInfo == null ? null : playerInfo.getProfile();
            if (gameProfile != null && this.handleClientPlayerChatReceivedEvent(e.getBoundChatType(), text, gameProfile)) {
               e.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent
   public void handleRenderSolidBlocks(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_SOLID_BLOCKS) {
         XaeroMinimapCore.onProjectionMatrix(event.getProjectionMatrix());
         XaeroMinimapCore.onWorldModelViewMatrix(event.getModelViewMatrix());
      }
   }

   @SubscribeEvent
   public void handleDrawScreenEventPost(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post event) {
      this.handleDrawScreenEventPost(event.getScreen());
   }

   @SubscribeEvent
   public void worldUnload(Unload event) {
      this.worldUnload(event.getLevel());
   }

   @SubscribeEvent
   public void handleClientTickEventStart(net.neoforged.neoforge.client.event.ClientTickEvent.Pre event) {
      this.handleClientTickStart();
   }

   @SubscribeEvent
   public void handlePlayerTickEventStart(net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre event) {
      this.handlePlayerTickStart(event.getEntity());
   }

   @SubscribeEvent
   public void handleRenderTickEventStart(net.neoforged.neoforge.client.event.RenderFrameEvent.Pre event) {
      this.handleRenderTickStart();
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void handleBossHealthRender(BossEventProgress event) {
      XaeroMinimapCore.onBossHealthRender(event.getY() + event.getIncrement());
   }
}
