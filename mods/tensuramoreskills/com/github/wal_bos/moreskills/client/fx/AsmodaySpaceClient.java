package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import org.joml.Matrix4f;

public final class AsmodaySpaceClient {
   private static int activeTicks;
   private static int phainonTicks;
   private static int phainonMaxTicks;
   private static float time;
   private static float phainonTime;

   private AsmodaySpaceClient() {
   }

   public static void init() {
   }

   public static void enable(int duration) {
      activeTicks = Math.max(activeTicks, Math.max(1, duration));
   }

   public static void enablePhainon(int duration) {
      int safeDuration = Math.max(1, duration);
      phainonTicks = Math.max(phainonTicks, safeDuration);
      phainonMaxTicks = Math.max(phainonMaxTicks, safeDuration);
      activeTicks = Math.max(activeTicks, safeDuration);
   }

   public static boolean isActive() {
      return activeTicks > 0 || phainonTicks > 0;
   }

   public static boolean isPhainonActive() {
      return phainonTicks > 0;
   }

   public static void onClientTick(Post event) {
      if (activeTicks > 0) {
         activeTicks--;
      }

      if (phainonTicks > 0) {
         phainonTicks--;
      } else if (activeTicks <= 0) {
         phainonMaxTicks = 0;
      }

      time += 0.08F;
      phainonTime += 0.105F;
   }

   public static void render(PoseStack poseStack, Camera camera) {
      if (activeTicks > 0 || phainonTicks > 0) {
         float baseFade = Mth.clamp(activeTicks / 10.0F, 0.0F, 1.0F);
         float phainonFade = 0.0F;
         if (phainonTicks > 0) {
            int max = Math.max(phainonMaxTicks, phainonTicks);
            float elapsed = max - phainonTicks;
            float fadeIn = Mth.clamp(elapsed / 8.0F, 0.0F, 1.0F);
            float fadeOut = Mth.clamp(phainonTicks / 34.0F, 0.0F, 1.0F);
            phainonFade = fadeIn * fadeOut;
         }

         float fade = Math.max(baseFade, phainonFade);
         if (!(fade <= 5.0E-4F)) {
            poseStack.pushPose();
            poseStack.mulPose(camera.rotation());
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            Matrix4f matrix = poseStack.last().pose();
            if (phainonFade > 5.0E-4F) {
               renderPhainonBlackCurtain(matrix, phainonFade);
               renderPhainonSolarVoidVeil(matrix, phainonFade);
               renderPhainonDeepNebula(matrix, phainonFade);
               RenderSystem.blendFuncSeparate(770, 1, 1, 0);
               renderPhainonVoidDust(matrix, phainonFade);
               renderPhainonGoldDust(matrix, phainonFade);
               renderPhainonRedAbyssDust(matrix, phainonFade);
            } else {
               renderBlackCurtain(matrix, fade);
               renderNebula(matrix, fade);
               RenderSystem.blendFuncSeparate(770, 1, 1, 0);
               renderVoidDust(matrix, fade);
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            poseStack.popPose();
         }
      }
   }

   private static void renderBlackCurtain(Matrix4f matrix, float fade) {
      float z = -8.0F;
      float s = 80.0F;
      float a = Mth.clamp(1.0F * fade, 0.0F, 1.0F);
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      buffer.addVertex(matrix, -s, -s, z).setColor(0.0F, 0.0F, 0.0F, a);
      buffer.addVertex(matrix, -s, s, z).setColor(0.0F, 0.0F, 0.0F, a);
      buffer.addVertex(matrix, s, s, z).setColor(0.0F, 0.0F, 0.0F, a);
      buffer.addVertex(matrix, s, -s, z).setColor(0.0F, 0.0F, 0.0F, a);
      safeDraw(buffer);
   }

   private static void renderPhainonBlackCurtain(Matrix4f matrix, float fade) {
      float z = -8.12F;
      float s = 92.0F;
      float pulse = 0.96F + 0.04F * Mth.sin(phainonTime * 0.55F);
      float a = Mth.clamp(1.08F * fade * pulse, 0.0F, 1.0F);
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      buffer.addVertex(matrix, -s, -s, z).setColor(0.0F, 0.0F, 0.0F, a);
      buffer.addVertex(matrix, -s, s, z).setColor(0.0F, 0.0F, 0.0F, a);
      buffer.addVertex(matrix, s, s, z).setColor(0.0F, 0.0F, 0.0F, a);
      buffer.addVertex(matrix, s, -s, z).setColor(0.0F, 0.0F, 0.0F, a);
      safeDraw(buffer);
   }

   private static void renderPhainonSolarVoidVeil(Matrix4f matrix, float fade) {
      float z = -8.08F;
      float s = 92.0F;
      float a = Mth.clamp(0.24F * fade, 0.0F, 0.38F);
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      buffer.addVertex(matrix, -s, -s, z).setColor(0.08F, 0.0F, 0.0F, a);
      buffer.addVertex(matrix, -s, s, z).setColor(0.0F, 0.0F, 0.0F, a * 0.82F);
      buffer.addVertex(matrix, s, s, z).setColor(0.0F, 0.0F, 0.0F, a * 0.82F);
      buffer.addVertex(matrix, s, -s, z).setColor(0.08F, 0.0F, 0.0F, a);
      safeDraw(buffer);
   }

   private static void renderNebula(Matrix4f matrix, float fade) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      float z = -7.95F;
      boolean wrote = false;

      for (int i = 0; i < 36; i++) {
         float seed = i * 19.913F;
         float x = (hash(seed) - 0.5F) * 90.0F;
         float y = (hash(seed + 4.0F) - 0.5F) * 52.0F;
         float size = 8.0F + hash(seed + 8.0F) * 24.0F;
         float pulse = 0.45F + 0.55F * Mth.sin(time * 0.45F + seed);
         float alpha = fade * pulse * (0.015F + hash(seed + 12.0F) * 0.025F);
         wrote |= addSoftQuad(buffer, matrix, x, y, z, size, 0.1F, 0.015F, 0.2F, alpha);
      }

      safeDraw(buffer, wrote);
   }

   private static void renderPhainonDeepNebula(Matrix4f matrix, float fade) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      float z = -7.98F;
      boolean wrote = false;

      for (int i = 0; i < 58; i++) {
         float seed = i * 23.913F;
         float x = (hash(seed) - 0.5F) * 112.0F;
         float y = (hash(seed + 4.0F) - 0.5F) * 64.0F;
         float size = 9.0F + hash(seed + 8.0F) * 32.0F;
         float pulse = 0.4F + 0.6F * Mth.sin(phainonTime * (0.3F + hash(seed + 1.0F) * 0.3F) + seed);
         float alpha = fade * pulse * (0.014F + hash(seed + 12.0F) * 0.03F);
         wrote |= addSoftQuad(buffer, matrix, x, y, z, size, 0.055F, 0.004F, 0.008F, alpha);
      }

      safeDraw(buffer, wrote);
   }

   private static void renderVoidDust(Matrix4f matrix, float fade) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      float z = -7.85F;
      boolean wrote = false;

      for (int i = 0; i < 80; i++) {
         float seed = i * 28.41F;
         float drift = time * (0.06F + hash(seed + 1.0F) * 0.1F);
         float x = Mth.frac(hash(seed) + drift * 0.01F) * 120.0F - 60.0F;
         float y = (hash(seed + 5.0F) - 0.5F) * 70.0F;
         float size = 0.45F + hash(seed + 9.0F) * 1.6F;
         float alpha = fade * (0.018F + hash(seed + 13.0F) * 0.032F);
         wrote |= addSoftQuad(buffer, matrix, x, y, z, size, 0.22F, 0.055F, 0.02F, alpha);
      }

      safeDraw(buffer, wrote);
   }

   private static void renderPhainonVoidDust(Matrix4f matrix, float fade) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      float z = -7.82F;
      boolean wrote = false;

      for (int i = 0; i < 180; i++) {
         float seed = i * 28.41F;
         float drift = phainonTime * (0.08F + hash(seed + 1.0F) * 0.13F);
         float x = Mth.frac(hash(seed) + drift * 0.012F) * 132.0F - 66.0F;
         float y = (hash(seed + 5.0F) - 0.5F) * 78.0F;
         float size = 0.5F + hash(seed + 9.0F) * 2.1F;
         float alpha = fade * (0.022F + hash(seed + 13.0F) * 0.04F);
         wrote |= addSoftQuad(buffer, matrix, x, y, z, size, 0.22F, 0.018F, 0.01F, alpha);
      }

      safeDraw(buffer, wrote);
   }

   private static void renderPhainonGoldDust(Matrix4f matrix, float fade) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      float z = -7.78F;
      boolean wrote = false;

      for (int i = 0; i < 120; i++) {
         float seed = i * 37.117F;
         float drift = phainonTime * (0.035F + hash(seed + 1.0F) * 0.065F);
         float x = Mth.frac(hash(seed) + drift * 0.01F) * 118.0F - 59.0F;
         float y = (hash(seed + 5.0F) - 0.5F) * 70.0F;
         float size = 0.3F + hash(seed + 9.0F) * 1.25F;
         float alpha = fade * (0.012F + hash(seed + 13.0F) * 0.025F);
         wrote |= addSoftQuad(buffer, matrix, x, y, z, size, 1.0F, 0.76F, 0.08F, alpha);
      }

      safeDraw(buffer, wrote);
   }

   private static void renderPhainonRedAbyssDust(Matrix4f matrix, float fade) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      float z = -7.76F;
      boolean wrote = false;

      for (int i = 0; i < 140; i++) {
         float seed = i * 42.71F;
         float drift = phainonTime * (0.08F + hash(seed + 1.0F) * 0.1F);
         float x = Mth.frac(hash(seed) - drift * 0.012F) * 126.0F - 63.0F;
         float y = (hash(seed + 5.0F) - 0.5F) * 76.0F;
         float size = 0.35F + hash(seed + 9.0F) * 1.8F;
         float alpha = fade * (0.018F + hash(seed + 13.0F) * 0.034F);
         wrote |= addSoftQuad(buffer, matrix, x, y, z, size, 0.58F, 0.0F, 0.0F, alpha);
      }

      safeDraw(buffer, wrote);
   }

   private static boolean addSoftQuad(BufferBuilder buffer, Matrix4f matrix, float x, float y, float z, float size, float r, float g, float b, float a) {
      if (finite(x) && finite(y) && finite(z) && finite(size) && finite(a) && !(size <= 5.0E-4F) && !(a <= 5.0E-4F)) {
         buffer.addVertex(matrix, x - size, y - size, z).setColor(r, g, b, 0.0F);
         buffer.addVertex(matrix, x - size, y + size, z).setColor(r, g, b, a);
         buffer.addVertex(matrix, x + size, y + size, z).setColor(r, g, b, a);
         buffer.addVertex(matrix, x + size, y - size, z).setColor(r, g, b, 0.0F);
         return true;
      } else {
         return false;
      }
   }

   private static void safeDraw(BufferBuilder buffer) {
      try {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      } catch (IllegalStateException var2) {
      }
   }

   private static void safeDraw(BufferBuilder buffer, boolean wrote) {
      if (wrote) {
         safeDraw(buffer);
      }
   }

   private static boolean finite(float value) {
      return !Float.isNaN(value) && !Float.isInfinite(value);
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }
}
