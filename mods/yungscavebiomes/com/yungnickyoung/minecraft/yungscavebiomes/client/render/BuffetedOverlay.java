package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BuffetedOverlay implements Layer {
   private static BuffetedOverlay INSTANCE;
   private static final ResourceLocation OVERLAY_TEXTURE = YungsCaveBiomesCommon.id("textures/overlay/buffeted_overlay.png");
   private static final int MAX_TICKS = 200;
   private static final float MAX_OPACITY = 1.0F;
   private static final float MIN_COLOR = 0.1F;
   private static final float MAX_COLOR = 0.9F;
   private int ticks;
   private float color = 0.5F;

   public static BuffetedOverlay getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new BuffetedOverlay();
      }

      return INSTANCE;
   }

   public void render(@NotNull GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(false);
      Minecraft client = Minecraft.getInstance();
      if (client.player != null) {
         if (!client.player.isAlive()) {
            this.ticks = 0;
         }

         this.ticks = client.player.hasEffect(MobEffectModule.BUFFETED_EFFECT.getHolder()) ? Math.min(this.ticks + 1, 200) : Math.max(this.ticks - 1, 0);
         if (!client.player.isSpectator() && this.ticks > 0) {
            float opacity = Mth.clamp(this.ticks / 200.0F, 0.0F, 1.0F);
            int packedLight = client.getEntityRenderDispatcher().getPackedLightCoords(client.player, partialTicks);
            int worldLight = Mth.clamp(Math.max(LightTexture.block(packedLight), LightTexture.sky(packedLight)), 0, 15);
            int currLight = (int)(this.color * 16.0F);
            int colorDiff = worldLight - currLight;
            this.color += 0.003F * colorDiff;
            this.color = Mth.clamp(this.color, 0.1F, 0.9F);
            renderTextureOverlay(guiGraphics, this.color, this.color, this.color, opacity);
         }
      }
   }

   private static void renderTextureOverlay(GuiGraphics guiGraphics, float r, float g, float b, float alpha) {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      guiGraphics.setColor(r, g, b, alpha);
      guiGraphics.blit(OVERLAY_TEXTURE, 0, 0, -90, 0.0F, 0.0F, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight());
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
