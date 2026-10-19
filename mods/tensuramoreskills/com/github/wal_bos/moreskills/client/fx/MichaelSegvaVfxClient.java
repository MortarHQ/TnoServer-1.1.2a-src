package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.net.msg.MichaelSegvaVfxPayload;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class MichaelSegvaVfxClient {
   private static final List<MichaelSegvaVfxClient.Segva> ACTIVE = new CopyOnWriteArrayList<>();
   private static boolean initialized;

   private MichaelSegvaVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MichaelSegvaVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(MichaelSegvaVfxClient::render);
      }
   }

   public static void play(MichaelSegvaVfxPayload payload) {
      init();
      if (payload.vfxType() == 2) {
         ACTIVE.removeIf(effect -> effect.type == 0 && effect.targetId == payload.targetId());
      } else {
         if (payload.vfxType() == 0) {
            ACTIVE.removeIf(effect -> effect.type == 0 && effect.targetId == payload.targetId());
         }

         ACTIVE.add(new MichaelSegvaVfxClient.Segva(payload));

         while (ACTIVE.size() > 48) {
            ACTIVE.remove(0);
         }
      }
   }

   private static void tick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         for (MichaelSegvaVfxClient.Segva effect : ACTIVE) {
            effect.age++;
            if (effect.age > effect.life || effect.targetGone(mc)) {
               ACTIVE.remove(effect);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES && !ACTIVE.isEmpty()) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null) {
            Vec3 camera = MichaelSegvaVfxClient.H.camera();
            Matrix4f matrix = event.getPoseStack().last().pose();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            BufferBuilder buffer = MichaelSegvaVfxClient.H.begin();
            boolean wrote = false;

            for (MichaelSegvaVfxClient.Segva effect : ACTIVE) {
               wrote |= effect.render(mc, buffer, matrix, camera, partial);
            }

            MichaelSegvaVfxClient.H.finish(buffer, wrote);
         }
      }
   }

   private static final class H {
      private static BufferBuilder begin() {
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         return Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
      }

      private static void finish(BufferBuilder buffer, boolean wrote) {
         if (wrote) {
            try {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            } catch (IllegalStateException var3) {
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }

      private static Vec3 camera() {
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         return camera.getPosition();
      }

      private static Vec3 rel(Vec3 position, Vec3 camera) {
         return position.subtract(camera);
      }

      private static float smooth(float value) {
         value = Mth.clamp(value, 0.0F, 1.0F);
         return value * value * (3.0F - 2.0F * value);
      }

      private static float hash(long seed, int salt) {
         long value = seed + salt * -7046029254386353131L;
         value ^= value >>> 30;
         value *= -4658895280553007687L;
         value ^= value >>> 27;
         value *= -7723592293110705685L;
         value ^= value >>> 31;
         return (float)(value & 16777215L) / 1.6777216E7F;
      }

      private static boolean star(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int b, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 right = new Vec3(size, 0.0, 0.0);
         Vec3 up = new Vec3(0.0, size, 0.0);
         Vec3 forward = new Vec3(0.0, 0.0, size * 0.42);
         tri(buffer, matrix, center.add(up), center.add(right.scale(0.22)), center.add(forward), r, g, b, alpha);
         tri(buffer, matrix, center.add(right), center.add(up.scale(-0.22)), center.add(forward), r, g, b, alpha);
         tri(buffer, matrix, center.add(up.scale(-1.0)), center.add(right.scale(-0.22)), center.add(forward), r, g, b, alpha);
         tri(buffer, matrix, center.add(right.scale(-1.0)), center.add(up.scale(0.22)), center.add(forward), r, g, b, alpha);
         tri(buffer, matrix, center.add(up), center.add(right.scale(-0.22)), center.add(forward.scale(-1.0)), r, g, b, alpha);
         tri(buffer, matrix, center.add(right.scale(-1.0)), center.add(up.scale(-0.22)), center.add(forward.scale(-1.0)), r, g, b, alpha);
         tri(buffer, matrix, center.add(up.scale(-1.0)), center.add(right.scale(0.22)), center.add(forward.scale(-1.0)), r, g, b, alpha);
         tri(buffer, matrix, center.add(right), center.add(up.scale(0.22)), center.add(forward.scale(-1.0)), r, g, b, alpha);
         return true;
      }

      private static boolean spark(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int b, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 up = new Vec3(0.0, size, 0.0);
         Vec3 right = new Vec3(size, 0.0, 0.0);
         tri(buffer, matrix, center.add(up), center.add(right.scale(0.42)), center.add(right.scale(-0.42)), r, g, b, alpha);
         tri(buffer, matrix, center.add(up.scale(-1.0)), center.add(right.scale(-0.42)), center.add(right.scale(0.42)), r, g, b, alpha * 0.82F);
         return true;
      }

      private static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int blue, float alpha) {
         int aa = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(r, g, blue, aa);
         buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(r, g, blue, aa);
         buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(r, g, blue, aa);
      }
   }

   private static final class Segva {
      private final byte type;
      private final int targetId;
      private final Vec3 fallbackCenter;
      private final int marks;
      private final int life;
      private final long seed;
      private int age;

      private Segva(MichaelSegvaVfxPayload payload) {
         this.type = payload.vfxType();
         this.targetId = payload.targetId();
         this.fallbackCenter = new Vec3(payload.x2(), payload.y2(), payload.z2());
         this.marks = Mth.clamp(payload.marks(), 1, 3);
         this.life = Math.max(1, payload.duration());
         this.seed = payload.seed();
      }

      private boolean targetGone(Minecraft mc) {
         if (this.targetId >= 0 && mc.level != null) {
            Entity target = mc.level.getEntity(this.targetId);
            return target != null && !target.isAlive();
         } else {
            return false;
         }
      }

      private boolean render(Minecraft mc, BufferBuilder buffer, Matrix4f matrix, Vec3 camera, float partial) {
         Vec3 center = MichaelSegvaVfxClient.H.rel(this.resolveCenter(mc, partial), camera);
         return this.type == 0 ? this.renderMarks(buffer, matrix, center, partial) : this.renderLock(buffer, matrix, center, partial);
      }

      private Vec3 resolveCenter(Minecraft mc, float partial) {
         if (mc.level != null && this.targetId >= 0) {
            Entity target = mc.level.getEntity(this.targetId);
            if (target == null) {
               return this.fallbackCenter;
            }

            double x = Mth.lerp(partial, target.xo, target.getX());
            double y = Mth.lerp(partial, target.yo, target.getY()) + target.getBbHeight() * 0.62;
            double z = Mth.lerp(partial, target.zo, target.getZ());
            return new Vec3(x, y, z);
         } else {
            return this.fallbackCenter;
         }
      }

      private boolean renderMarks(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float partial) {
         float t = this.age + partial;
         float born = MichaelSegvaVfxClient.H.smooth(Math.min(1.0F, t / 8.0F));
         float fade = this.life - t < 14.0F ? MichaelSegvaVfxClient.H.smooth(Math.max(0.0F, (this.life - t) / 14.0F)) : 1.0F;
         float alpha = born * fade;
         float pulse = 0.88F + 0.12F * Mth.sin(t * 0.24F);
         double radius = 0.72 + this.marks * 0.08;
         boolean wrote = false;

         for (int i = 0; i < this.marks; i++) {
            double angle = t * 0.075 + i * (float) (Math.PI * 2) / this.marks + MichaelSegvaVfxClient.H.hash(this.seed, i * 97) * 0.45;
            double y = Math.sin(t * 0.11 + i * 2.1) * 0.18;
            Vec3 position = center.add(Math.cos(angle) * radius, y, Math.sin(angle) * radius);
            float size = (0.115F + i * 0.006F) * pulse;
            wrote |= MichaelSegvaVfxClient.H.star(buffer, matrix, position, size * 1.8F, 118, 72, 255, alpha * 0.16F);
            wrote |= MichaelSegvaVfxClient.H.star(buffer, matrix, position, size, 238, 102, 255, alpha);
            wrote |= MichaelSegvaVfxClient.H.spark(buffer, matrix, position, size * 0.72F, 102, 214, 255, alpha * 0.86F);
         }

         int specks = 3 + this.marks * 3;

         for (int i = 0; i < specks; i++) {
            float hash = MichaelSegvaVfxClient.H.hash(this.seed, 5000 + i * 71);
            double angle = t * (0.035 + hash * 0.025) + i * (float) (Math.PI * 2) / specks;
            double distance = 0.38 + hash * 0.72;
            double y = -0.34 + MichaelSegvaVfxClient.H.hash(this.seed, 7000 + i * 89) * 0.72;
            Vec3 position = center.add(Math.cos(angle) * distance, y, Math.sin(angle) * distance);
            float size = 0.018F + hash * 0.025F;
            wrote |= MichaelSegvaVfxClient.H.spark(buffer, matrix, position, size, 160, 106, 255, alpha * (0.35F + hash * 0.35F));
         }

         return wrote;
      }

      private boolean renderLock(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float partial) {
         float t = this.age + partial;
         float born = MichaelSegvaVfxClient.H.smooth(Math.min(1.0F, t / 6.0F));
         float fade = this.life - t < 16.0F ? MichaelSegvaVfxClient.H.smooth(Math.max(0.0F, (this.life - t) / 16.0F)) : 1.0F;
         float alpha = born * fade;
         float pulse = 0.82F + 0.18F * Mth.sin(t * 0.38F);
         boolean wrote = false;

         for (int i = 0; i < 3; i++) {
            double angle = -t * 0.11 + i * (float) (Math.PI * 2) / 3.0;
            Vec3 position = center.add(Math.cos(angle) * 1.05, Math.sin(t * 0.09 + i) * 0.24, Math.sin(angle) * 1.05);
            float size = 0.15F * pulse;
            wrote |= MichaelSegvaVfxClient.H.star(buffer, matrix, position, size * 2.0F, 114, 70, 255, alpha * 0.18F);
            wrote |= MichaelSegvaVfxClient.H.star(buffer, matrix, position, size, 255, 92, 238, alpha);
         }

         for (int i = 0; i < 28; i++) {
            float h0 = MichaelSegvaVfxClient.H.hash(this.seed, 1000 + i * 31);
            float h1 = MichaelSegvaVfxClient.H.hash(this.seed, 2000 + i * 37);
            float h2 = MichaelSegvaVfxClient.H.hash(this.seed, 3000 + i * 41);
            double angle = t * (0.025 + h0 * 0.035) + h1 * (float) (Math.PI * 2);
            double radius = 0.35 + h2 * 1.25;
            double y = -0.55 + h0 * 1.1;
            Vec3 position = center.add(Math.cos(angle) * radius, y, Math.sin(angle) * radius);
            float size = 0.018F + h1 * 0.04F;
            int r = i % 2 == 0 ? 255 : 108;
            int g = i % 2 == 0 ? 80 : 206;
            wrote |= MichaelSegvaVfxClient.H.spark(buffer, matrix, position, size, r, g, 255, alpha * (0.42F + h2 * 0.45F));
         }

         return wrote;
      }
   }
}
