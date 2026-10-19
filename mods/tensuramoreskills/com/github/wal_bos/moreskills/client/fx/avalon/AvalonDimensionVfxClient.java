package com.github.wal_bos.moreskills.client.fx.avalon;

import com.github.wal_bos.moreskills.client.fx.ClientVfxGate;
import com.github.wal_bos.moreskills.handler.avalon.AvalonKingdomHandler;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class AvalonDimensionVfxClient {
   private static final DustParticleOptions GOLD = new DustParticleOptions(new Vector3f(1.0F, 0.72F, 0.08F), 1.35F);
   private static final DustParticleOptions WHITE_GOLD = new DustParticleOptions(new Vector3f(1.0F, 0.96F, 0.72F), 1.05F);
   private static boolean initialized;

   private AvalonDimensionVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AvalonDimensionVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(AvalonDimensionVfxClient::render);
      }
   }

   private static boolean active() {
      Minecraft minecraft = Minecraft.getInstance();
      return ClientVfxGate.allowed("avalon_dimension")
         && minecraft.level != null
         && minecraft.player != null
         && minecraft.level.dimension().equals(AvalonKingdomHandler.AVALON);
   }

   private static void tick(Post event) {
      if (active()) {
         Minecraft minecraft = Minecraft.getInstance();
         ClientLevel level = minecraft.level;
         LocalPlayer player = minecraft.player;
         RandomSource random = level.random;
         int count = player.tickCount % 2 == 0 ? 10 : 5;

         for (int i = 0; i < count; i++) {
            double angle = random.nextDouble() * (float) (Math.PI * 2);
            double distance = 3.0 + random.nextDouble() * 42.0;
            double x = player.getX() + Math.cos(angle) * distance;
            double y = player.getY() - 2.0 + random.nextDouble() * 24.0;
            double z = player.getZ() + Math.sin(angle) * distance;
            level.addParticle(i % 4 == 0 ? WHITE_GOLD : GOLD, x, y, z, 0.0, 0.012 + random.nextDouble() * 0.025, 0.0);
            if (i % 3 == 0) {
               level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.02, 0.0);
            }
         }

         if (player.tickCount % 3 == 0) {
            for (int i = 0; i < 4; i++) {
               double angle = random.nextDouble() * (float) (Math.PI * 2);
               double distance = 12.0 + random.nextDouble() * 54.0;
               level.addParticle(
                  ParticleTypes.FIREWORK,
                  player.getX() + Math.cos(angle) * distance,
                  player.getY() + 12.0 + random.nextDouble() * 36.0,
                  player.getZ() + Math.sin(angle) * distance,
                  0.0,
                  -0.05,
                  0.0
               );
            }
         }

         if (player.tickCount % 7 == 0) {
            double angle = random.nextDouble() * (float) (Math.PI * 2);
            double distance = 8.0 + random.nextDouble() * 28.0;
            level.addParticle(
               ParticleTypes.FLASH,
               player.getX() + Math.cos(angle) * distance,
               player.getY() + 2.0 + random.nextDouble() * 10.0,
               player.getZ() + Math.sin(angle) * distance,
               0.0,
               0.0,
               0.0
            );
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (active() && event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         Vec3 camera = event.getCamera().getPosition();
         float time = (float)minecraft.level.getGameTime() + event.getPartialTick().getGameTimeDeltaPartialTick(false);
         PoseStack poseStack = event.getPoseStack();
         RenderSystem.enableBlend();
         RenderSystem.disableCull();
         RenderSystem.depthMask(false);
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         poseStack.pushPose();
         poseStack.translate(-camera.x, -camera.y, -camera.z);
         Matrix4f matrix = poseStack.last().pose();
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         Vec3 center = camera.add(0.0, 34.0, 0.0);

         for (int ring = 0; ring < 7; ring++) {
            float radius = 34.0F + ring * 17.0F + Mth.sin(time * 0.018F + ring) * 5.0F;
            float y = ring * 4.5F + Mth.sin(time * 0.025F + ring * 0.7F) * 3.0F;
            ring(
               buffer,
               matrix,
               center.add(0.0, y, 0.0),
               radius,
               0.35F + ring * 0.04F,
               ring % 2 == 0 ? 255 : 255,
               ring % 2 == 0 ? 214 : 245,
               ring % 2 == 0 ? 55 : 180,
               28 + ring * 4
            );
         }

         for (int i = 0; i < 18; i++) {
            double angle = i * (float) (Math.PI * 2) / 18.0 + time * 0.0025;
            double radius = 24.0 + i % 6 * 12.0;
            Vec3 bottom = center.add(Math.cos(angle) * radius, -38.0, Math.sin(angle) * radius);
            Vec3 top = bottom.add(0.0, 96.0 + Mth.sin(time * 0.02F + i) * 18.0, 0.0);
            beam(buffer, matrix, bottom, top, 0.25F + i % 3 * 0.12F, 255, i % 2 == 0 ? 194 : 236, 45, 24 + i % 4 * 7);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
         poseStack.popPose();
         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      }
   }

   private static void ring(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float radius, float width, int red, int green, int blue, int alpha) {
      for (int i = 0; i < 96; i++) {
         double first = i * (float) (Math.PI * 2) / 96.0;
         double second = (i + 1) * (float) (Math.PI * 2) / 96.0;
         Vec3 a = center.add(Math.cos(first) * (radius + width), 0.0, Math.sin(first) * (radius + width));
         Vec3 b = center.add(Math.cos(second) * (radius + width), 0.0, Math.sin(second) * (radius + width));
         Vec3 c = center.add(Math.cos(second) * (radius - width), 0.0, Math.sin(second) * (radius - width));
         Vec3 d = center.add(Math.cos(first) * (radius - width), 0.0, Math.sin(first) * (radius - width));
         quad(buffer, matrix, a, b, c, d, red, green, blue, alpha);
      }
   }

   private static void beam(BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, float width, int red, int green, int blue, int alpha) {
      Vec3 side = new Vec3(width, 0.0, width);
      quad(buffer, matrix, start.add(side), end.add(side), end.subtract(side), start.subtract(side), red, green, blue, alpha);
   }

   private static void quad(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int red, int green, int blue, int alpha) {
      buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(red, green, blue, alpha);
      buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(red, green, blue, alpha);
      buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(red, green, blue, alpha);
      buffer.addVertex(matrix, (float)d.x, (float)d.y, (float)d.z).setColor(red, green, blue, alpha);
   }
}
