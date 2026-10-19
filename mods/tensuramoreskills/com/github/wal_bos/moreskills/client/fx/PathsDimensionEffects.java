package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class PathsDimensionEffects extends DimensionSpecialEffects {
   private static final ResourceLocation STAR_SKY = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/environment/starsky.png");

   public PathsDimensionEffects() {
      super(256.0F, true, SkyType.NONE, false, false);
   }

   public Vec3 getBrightnessDependentFogColor(Vec3 base, float sunHeight) {
      float power = getAwakeningPower();
      return new Vec3(0.2 + power * 0.16, 0.34 + power * 0.06, 0.46 + power * 0.28);
   }

   public boolean isFoggyAt(int x, int z) {
      return true;
   }

   public boolean renderSky(
      ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog
   ) {
      PoseStack poseStack = new PoseStack();
      poseStack.mulPose(modelViewMatrix);
      PathsDimensionEffects.AwakeningSky awakening = findAwakening(level, camera.getPosition());
      float power = awakening == null ? 0.0F : awakening.power;
      float climax = awakening == null ? 0.0F : awakening.climax;
      float impact = awakening == null ? 0.0F : awakening.impact;
      float time = ticks + partialTick;
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, STAR_SKY);
      float red = 1.0F + climax * 0.1F;
      float green = 1.0F + power * 0.05F;
      float blue = 1.0F + power * 0.28F + climax * 0.22F;
      RenderSystem.setShaderColor(red, green, blue, 1.0F);
      drawSkyCube(poseStack, 180.0F + power * 20.0F);
      if (awakening != null && power > 0.02F) {
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         RenderSystem.disableCull();
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         drawTransitionOverlays(poseStack, time, power, climax, impact);
         drawSkyTear(poseStack, time, power, climax, impact);
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      return true;
   }

   private static void drawSkyCube(PoseStack poseStack, float s) {
      Matrix4f mat = poseStack.last().pose();
      drawQuad(mat, -s, -s, -s, s, -s, -s, s, s, -s, -s, s, -s);
      drawQuad(mat, s, -s, s, -s, -s, s, -s, s, s, s, s, s);
      drawQuad(mat, -s, -s, s, -s, -s, -s, -s, s, -s, -s, s, s);
      drawQuad(mat, s, -s, -s, s, -s, s, s, s, s, s, s, -s);
      drawQuad(mat, -s, s, s, -s, s, -s, s, s, -s, s, s, s);
      drawQuad(mat, -s, -s, -s, -s, -s, s, s, -s, s, s, -s, -s);
   }

   private static void drawQuad(
      Matrix4f mat, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4
   ) {
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      bb.addVertex(mat, x1, y1, z1).setUv(0.0F, 1.0F);
      bb.addVertex(mat, x2, y2, z2).setUv(1.0F, 1.0F);
      bb.addVertex(mat, x3, y3, z3).setUv(1.0F, 0.0F);
      bb.addVertex(mat, x4, y4, z4).setUv(0.0F, 0.0F);
      BufferUploader.drawWithShader(bb.buildOrThrow());
   }

   private static void drawTransitionOverlays(PoseStack poseStack, float time, float power, float climax, float impact) {
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      int rings = 6;
      int segments = 96;

      for (int ring = 0; ring < rings; ring++) {
         float y = 130.0F - ring * 8.0F;
         float radius = 34.0F + ring * 18.0F + power * 22.0F + impact * 34.0F;
         float width = 1.8F + climax * 4.0F;
         float spin = time * (0.01F + ring * 0.002F) * (ring % 2 == 0 ? 1.0F : -1.0F);
         float alpha = (0.025F + power * 0.045F + climax * 0.06F) * (1.0F - ring * 0.1F);

         for (int i = 0; i < segments; i++) {
            float a1 = (float) (Math.PI * 2) * i / segments + spin;
            float a2 = (float) (Math.PI * 2) * (i + 1) / segments + spin;
            addSkyRingSegment(bb, mat, y, radius, width, a1, a2, ring % 2 == 0 ? 255 : 150, ring % 2 == 0 ? 240 : 80, ring % 2 == 0 ? 210 : 255, alpha);
         }
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
   }

   private static void drawSkyTear(PoseStack poseStack, float time, float power, float climax, float impact) {
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
      int segments = 84;
      float height = 190.0F;
      float width = 4.0F + power * 18.0F + climax * 22.0F + impact * 34.0F;

      for (int i = 0; i <= segments; i++) {
         float t = (float)i / segments;
         float y = 150.0F - t * height;
         float wave = Mth.sin(t * 17.0F + time * 0.055F) * (4.0F + climax * 16.0F);
         float localWidth = width * (0.18F + Mth.sin(t * (float) Math.PI) * 0.82F);
         float alpha = (0.02F + power * 0.04F + climax * 0.08F + impact * 0.1F) * Mth.sin(t * (float) Math.PI);
         bb.addVertex(mat, wave - localWidth, y, -170.0F).setColor(80, 20, 255, 0);
         bb.addVertex(mat, wave + localWidth, y, -170.0F).setColor(255, 248, 220, Mth.clamp((int)(alpha * 255.0F), 0, 255));
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
   }

   private static void addSkyRingSegment(
      BufferBuilder bb, Matrix4f mat, float y, float radius, float width, float a1, float a2, int red, int green, int blue, float alpha
   ) {
      float inner = radius - width;
      float outer = radius + width;
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      bb.addVertex(mat, Mth.cos(a1) * inner, y, Mth.sin(a1) * inner).setColor(red, green, blue, 0);
      bb.addVertex(mat, Mth.cos(a1) * outer, y, Mth.sin(a1) * outer).setColor(red, green, blue, a);
      bb.addVertex(mat, Mth.cos(a2) * outer, y, Mth.sin(a2) * outer).setColor(red, green, blue, a);
      bb.addVertex(mat, Mth.cos(a2) * inner, y, Mth.sin(a2) * inner).setColor(red, green, blue, 0);
   }

   private static float getAwakeningPower() {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         PathsDimensionEffects.AwakeningSky awakening = findAwakening(minecraft.level, minecraft.player.position());
         return awakening == null ? 0.0F : awakening.power;
      } else {
         return 0.0F;
      }
   }

   private static PathsDimensionEffects.AwakeningSky findAwakening(ClientLevel level, Vec3 cameraPos) {
      FeldwayAwakeningEntity best = null;
      double bestDist = Double.MAX_VALUE;

      for (Entity entity : level.entitiesForRendering()) {
         if (entity instanceof FeldwayAwakeningEntity awakening && awakening.isAlive()) {
            double dist = awakening.position().distanceToSqr(cameraPos);
            if (dist < bestDist && dist <= 230400.0) {
               bestDist = dist;
               best = awakening;
            }
         }
      }

      if (best == null) {
         return null;
      }

      float progress = best.getAwakeningProgress();
      float power = smooth(Mth.clamp((progress - 0.16F) / 0.84F, 0.0F, 1.0F));
      float climax = smooth(Mth.clamp((progress - 0.68F) / 0.32F, 0.0F, 1.0F));
      float impact = smooth(Mth.clamp((progress - 0.88F) / 0.12F, 0.0F, 1.0F));
      return new PathsDimensionEffects.AwakeningSky(power, climax, impact);
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private record AwakeningSky(float power, float climax, float impact) {
   }
}
