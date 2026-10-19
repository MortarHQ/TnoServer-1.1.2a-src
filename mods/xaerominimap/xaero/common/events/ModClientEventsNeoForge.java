package xaero.common.events;

import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;
import net.neoforged.neoforge.client.event.ModelEvent.BakingCompleted;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import xaero.common.IXaeroMinimap;
import xaero.common.interfaces.InterfaceManager;

public class ModClientEventsNeoForge extends ModClientEvents {
   public ModClientEventsNeoForge(IXaeroMinimap modMain) {
      super(modMain);
   }

   @SubscribeEvent
   public void handleTextureStitchEventPost(TextureAtlasStitchedEvent event) {
      this.handleTextureStitchEventPost(event.getAtlas());
   }

   @SubscribeEvent
   public void onModelBakeEvent(BakingCompleted event) {
      InterfaceManager interfaceManager = this.modMain.getInterfaces();
      if (interfaceManager != null) {
         interfaceManager.getMinimapInterface().getMinimapFBORenderer().resetEntityIconsResources();
      }
   }

   @SubscribeEvent
   public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
      this.modMain.ensureControlsRegister();
      this.modMain.getControlsRegister().registerKeybindings(event::register);
   }

   @SubscribeEvent
   public void registerOverlay(RegisterGuiLayersEvent event) {
      Layer overlay = this::handleRenderModOverlay;
      event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, ResourceLocation.fromNamespaceAndPath("xaerohud", "hud"), overlay);
   }
}
