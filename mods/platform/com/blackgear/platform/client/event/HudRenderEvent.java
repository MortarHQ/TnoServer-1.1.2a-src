package com.blackgear.platform.client.event;

import com.blackgear.platform.core.util.event.Event;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HudRenderEvent {
   public static final Event<HudRenderEvent.RenderHud> RENDER_HUD = Event.create(HudRenderEvent.RenderHud.class);

   public enum ElementType {
      DEFAULT,
      HEALTH,
      EXPERIENCE,
      FIRST_PERSON,
      VIGNETTE;
   }

   public interface RenderContext {
      default Window window() {
         return this.minecraft().getWindow();
      }

      default int screenWidth() {
         return this.window().getGuiScaledWidth();
      }

      default int screenHeight() {
         return this.window().getGuiScaledHeight();
      }

      default Minecraft minecraft() {
         return Minecraft.getInstance();
      }

      default LocalPlayer player() {
         return this.minecraft().player;
      }

      default Gui gui() {
         return this.minecraft().gui;
      }

      default void renderTextureOverlay(ResourceLocation texture, float alpha) {
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
         RenderSystem.setShaderTexture(0, texture);
         Tesselator tesselator = Tesselator.getInstance();
         BufferBuilder bufferBuilder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
         bufferBuilder.addVertex(0.0F, this.screenHeight(), -90.0F).setUv(0.0F, 1.0F);
         bufferBuilder.addVertex(this.screenWidth(), this.screenHeight(), -90.0F).setUv(0.0F, 1.0F);
         bufferBuilder.addVertex(this.screenWidth(), 0.0F, -90.0F).setUv(1.0F, 0.0F);
         bufferBuilder.addVertex(0.0F, 0.0F, -90.0F).setUv(0.0F, 0.0F);
         BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   @FunctionalInterface
   public interface RenderHud {
      void render(GuiGraphics var1, float var2, HudRenderEvent.ElementType var3, HudRenderEvent.RenderContext var4);
   }
}
