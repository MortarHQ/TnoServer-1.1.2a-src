package xaero.map.events;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Post;
import net.neoforged.neoforge.client.event.ScreenEvent.Opening;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;

public class ClientEventsNeoForge extends ClientEvents {
   @SubscribeEvent
   public void guiOpen(Opening event) {
      Screen guiBefore = event.getNewScreen();
      Screen gui = this.handleGuiOpen(guiBefore);
      if (guiBefore != null && gui == null) {
         event.setCanceled(true);
      } else {
         event.setNewScreen(gui);
      }
   }

   @SubscribeEvent
   public void renderTickPost(Post event) throws Exception {
      this.handleRenderTick(false);
   }

   @SubscribeEvent
   public void screenRenderPost(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post event) {
      super.handleDrawScreen(event.getScreen());
   }

   @SubscribeEvent
   public void worldUnload(Unload event) {
      super.handleWorldUnload(event.getLevel());
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
   public void clientTickStart(Pre event) throws Exception {
      super.handleClientTickStart();
   }
}
