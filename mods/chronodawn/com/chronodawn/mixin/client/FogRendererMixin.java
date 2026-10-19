package com.chronodawn.mixin.client;

import com.chronodawn.core.dimension.ChronoDawnBiomeProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
   @Unique
   private static final float DARK_FOREST_FOG_START = 5.0F;
   @Unique
   private static final float DARK_FOREST_FOG_END = 60.0F;

   @Inject(method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", at = @At("TAIL"))
   private static void modifyDarkForestFog(Camera camera, FogMode fogMode, float viewDistance, boolean thickFog, float partialTick, CallbackInfo ci) {
      if (fogMode == FogMode.FOG_TERRAIN) {
         if (!thickFog) {
            if (camera.getEntity().level() instanceof ClientLevel clientLevel) {
               BlockPos var9 = camera.getBlockPosition();
               Holder<Biome> currentBiome = clientLevel.getBiome(var9);
               if (currentBiome.is(ChronoDawnBiomeProvider.CHRONO_DAWN_DARK_FOREST)) {
                  RenderSystem.setShaderFogStart(5.0F);
                  RenderSystem.setShaderFogEnd(60.0F);
               }
            }
         }
      }
   }
}
