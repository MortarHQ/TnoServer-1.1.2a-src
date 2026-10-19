package xaero.map.events;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import xaero.map.WorldMap;
import xaero.map.controls.ControlsRegister;
import xaero.map.gui.CrosshairMessageOverlay;

public class ModClientEventsNeoForge extends ModClientEvents {
   @SubscribeEvent
   public void handleTextureStitchEventPost(TextureAtlasStitchedEvent event) {
      super.handleTextureStitchEventPost(event.getAtlas());
   }

   @SubscribeEvent
   public void handleRegisterClientResourceReloadListeners(RegisterClientReloadListenersEvent event) {
   }

   @SubscribeEvent
   public void onRegisterOverlays(RegisterGuiLayersEvent event) {
      event.registerAbove(
         VanillaGuiLayers.CROSSHAIR, ResourceLocation.fromNamespaceAndPath("xaeroworldmap", "crosshair_message"), new CrosshairMessageOverlay()
      );
   }

   @SubscribeEvent
   public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
      if (WorldMap.controlsRegister == null) {
         WorldMap.controlsRegister = new ControlsRegister();
      }

      WorldMap.controlsRegister.register(event::register);
   }
}
