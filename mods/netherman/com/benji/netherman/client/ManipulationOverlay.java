package com.benji.netherman.client;

import com.benji.netherman.NetherExp;
import com.benji.netherman.init.ModEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.resources.ResourceLocation;

public class ManipulationOverlay {
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/misc/manipulation_overlay.png");
   public static final Layer HUD_OVERLAY = (guiGraphics, deltaTracker) -> {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null && minecraft.player.hasEffect(ModEffects.MANIPULATION)) {
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         int width = guiGraphics.guiWidth();
         int height = guiGraphics.guiHeight();
         guiGraphics.blit(TEXTURE, 0, 0, 0, 0.0F, 0.0F, width, height, width, height);
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
      }
   };
}
