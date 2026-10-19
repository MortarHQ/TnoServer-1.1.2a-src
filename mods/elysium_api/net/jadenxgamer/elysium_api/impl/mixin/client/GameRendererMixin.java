package net.jadenxgamer.elysium_api.impl.mixin.client;

import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
   @Shadow
   @Final
   private LightTexture lightTexture;

   @Inject(
      method = "render",
      at = @At(
         value = "NEW",
         target = "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)Lnet/minecraft/client/gui/GuiGraphics;"
      )
   )
   private void elysium_api$setupGUILightmap(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci) {
      if (!ModList.get().isLoaded("polytone")) {
         ElysiumAPI.LIGHTMAP_SETTINGS.setupForGUI(true);
         this.lightTexture.turnOnLightLayer();
      }
   }

   @Inject(method = "render", at = @At("TAIL"))
   private void elysium_api$resetGUILightmap(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci) {
      if (!ModList.get().isLoaded("polytone")) {
         ElysiumAPI.LIGHTMAP_SETTINGS.setupForGUI(false);
         this.lightTexture.turnOnLightLayer();
      }
   }
}
