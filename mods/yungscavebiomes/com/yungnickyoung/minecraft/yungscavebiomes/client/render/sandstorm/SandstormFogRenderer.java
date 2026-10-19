package com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.util.Mth;

public class SandstormFogRenderer {
   private double fogLevel = 0.0;

   public void render(FogMode mode, float renderDistance) {
      if (mode == FogMode.FOG_TERRAIN) {
         LocalPlayer localPlayer = Minecraft.getInstance().player;
         ClientLevel clientLevel = Minecraft.getInstance().level;
         if (localPlayer == null || clientLevel == null) {
            return;
         }

         if (clientLevel.getBiome(localPlayer.blockPosition()).is(BiomeModule.LOST_CAVES)
            && ((ISandstormClientDataProvider)clientLevel).getSandstormClientData().isSandstormActive()) {
            this.fogLevel = Mth.clamp(this.fogLevel + 0.002, 0.0, 1.0);
         } else {
            this.fogLevel = Mth.clamp(this.fogLevel - 0.002, 0.0, 1.0);
         }

         if (!localPlayer.isAlive()) {
            this.fogLevel = 0.0;
         }

         if (this.fogLevel > 0.0) {
            RenderSystem.setShaderFogStart((float)Mth.lerp(this.fogLevel, RenderSystem.getShaderFogStart(), -4.0));
            RenderSystem.setShaderFogEnd((float)Mth.lerp(this.fogLevel, RenderSystem.getShaderFogEnd(), Math.min(renderDistance * 0.45F, 64.0F)));
         }
      }
   }

   public double getFogLevel() {
      return this.fogLevel;
   }
}
