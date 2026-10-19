package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.net.msg.MichaelShifuruMeteorVfxPayload;
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class MichaelShifuruMeteorVfxClient {
   private static final List<MichaelShifuruMeteorVfxClient.Effect> ACTIVE = new CopyOnWriteArrayList<>();
   private static boolean initialized;

   private MichaelShifuruMeteorVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MichaelShifuruMeteorVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(MichaelShifuruMeteorVfxClient::render);
      }
   }

   public static void play(MichaelShifuruMeteorVfxPayload payload) {
      init();
      if (payload.vfxType() == 0) {
         ACTIVE.removeIf(effect -> effect.type == 0 && effect.casterId == payload.casterId());
      }

      ACTIVE.add(new MichaelShifuruMeteorVfxClient.Effect(payload));

      while (ACTIVE.size() > 128) {
         ACTIVE.remove(0);
      }
   }

   private static void tick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         for (MichaelShifuruMeteorVfxClient.Effect effect : ACTIVE) {
            effect.age++;
            if (effect.age > effect.life) {
               ACTIVE.remove(effect);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && !ACTIVE.isEmpty()) {
            Vec3 cam = MichaelShifuruMeteorVfxClient.H.camera();
            Matrix4f matrix = event.getPoseStack().last().pose();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            BufferBuilder buffer = MichaelShifuruMeteorVfxClient.H.begin();
            boolean wrote = false;

            for (MichaelShifuruMeteorVfxClient.Effect effect : ACTIVE) {
               wrote |= effect.render(buffer, matrix, cam, partial);
            }

            MichaelShifuruMeteorVfxClient.H.finish(buffer, wrote);
         }
      }
   }

   private static float impactGlow(float partial) {
      float max = 0.0F;

      for (MichaelShifuruMeteorVfxClient.Effect effect : ACTIVE) {
         if (effect.type == 2 && !(effect.power > 0.5F)) {
            float t = effect.age + partial;
            float flash = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, t / 3.0F))
               * (1.0F - MichaelShifuruMeteorVfxClient.H.smooth(Mth.clamp((t - 8.0F) / 16.0F, 0.0F, 1.0F)));
            max = Math.max(max, flash);
         }
      }

      return max;
   }

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private static final class Effect {
      private final byte type;
      private final int casterId;
      private final Vec3 ground;
      private final Vec3 sky;
      private final float radius;
      private final float power;
      private final int life;
      private final long seed;
      private int age;

      private Effect(MichaelShifuruMeteorVfxPayload payload) {
         this.type = payload.vfxType();
         this.casterId = payload.casterId();
         this.ground = new Vec3(payload.x(), payload.y(), payload.z());
         this.sky = new Vec3(payload.x2(), payload.y2(), payload.z2());
         this.radius = payload.radius();
         this.power = payload.power();
         this.life = Math.max(this.type == 1 ? 1500 : (this.type == 2 ? (this.power > 0.5F ? 460 : 180) : 260), payload.duration());
         this.seed = payload.seed();
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         float t = this.age + partial;
         float appear = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, t / 22.0F));
         float fade = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, (this.life - t) / 90.0F));
         float alpha = appear * fade;
         if (alpha <= 0.002F) {
            return false;
         }

         Vec3 groundRel = MichaelShifuruMeteorVfxClient.H.rel(this.ground, cam);
         Vec3 skyRel = MichaelShifuruMeteorVfxClient.H.rel(this.sky, cam);
         Vec3 forward = groundRel.subtract(skyRel);
         if (forward.lengthSqr() < 0.001) {
            forward = new Vec3(0.0, -1.0, 0.0);
         }

         MichaelShifuruMeteorVfxClient.Basis basis = MichaelShifuruMeteorVfxClient.H.basis(forward.normalize());
         boolean impact = this.type == 2;
         boolean meteor = this.type == 1;
         boolean big = this.power > 0.5F;
         if (impact) {
            return this.renderImpact(buffer, matrix, groundRel, MichaelShifuruMeteorVfxClient.H.basis(new Vec3(0.0, 1.0, 0.0)), t, alpha, big);
         }

         boolean wrote = false;
         wrote |= this.renderStarDome(buffer, matrix, t, alpha, meteor, big);
         wrote |= this.renderHighAuraStardust(buffer, matrix, t, alpha, meteor, big);
         wrote |= this.renderWishShowers(buffer, matrix, t, alpha, meteor, big);
         if (meteor) {
            wrote |= this.renderPortalWall(buffer, matrix, skyRel, groundRel, basis, t, alpha, big, true);
            wrote |= this.renderMainPortal(buffer, matrix, skyRel, groundRel, basis, t, alpha, big, true);
            wrote |= this.renderPhainonStarPreview(buffer, matrix, skyRel, groundRel, basis, t, alpha, big);
         } else {
            float charge = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, this.power / 200.0F));
            wrote |= this.renderPortalWall(buffer, matrix, skyRel, groundRel, basis, t, alpha, charge > 0.75F, false);
            wrote |= this.renderMainPortal(buffer, matrix, skyRel, groundRel, basis, t, alpha * (0.55F + charge * 0.55F), charge > 0.75F, false);
         }

         return wrote;
      }

      private boolean renderBlackSky(BufferBuilder buffer, Matrix4f matrix, float t, float alpha, boolean meteor, boolean big, float impactGlow) {
         boolean wrote = false;
         float night = alpha * (meteor ? 0.46F : 0.3F);
         double size = meteor ? 1680.0 : 1240.0;
         double y = meteor ? 420.0 : 320.0;

         for (int layer = 0; layer < 8; layer++) {
            double s = size * (1.0 + layer * 0.08);
            double yy = y + layer * 0.14;
            MichaelShifuruMeteorVfxClient.H.face(
               buffer, matrix, new Vec3(-s, yy, -s), new Vec3(s, yy, -s), new Vec3(s, yy, s), new Vec3(-s, yy, s), 2, 3, 10, night * (0.18F - layer * 0.012F)
            );
            wrote = true;
         }

         if (impactGlow > 0.001F) {
            float glow = impactGlow * alpha * 0.42F;
            double s = 1750.0;
            double yy = y + 1.2;
            MichaelShifuruMeteorVfxClient.H.face(
               buffer, matrix, new Vec3(-s, yy, -s), new Vec3(s, yy, -s), new Vec3(s, yy, s), new Vec3(-s, yy, s), 104, 32, 160, glow
            );
            MichaelShifuruMeteorVfxClient.H.face(
               buffer,
               matrix,
               new Vec3(-s * 0.92, yy + 0.06, -s * 0.92),
               new Vec3(s * 0.92, yy + 0.06, -s * 0.92),
               new Vec3(s * 0.92, yy + 0.06, s * 0.92),
               new Vec3(-s * 0.92, yy + 0.06, s * 0.92),
               176,
               96,
               255,
               glow * 0.58F
            );
            wrote = true;
         }

         return wrote;
      }

      private boolean renderStarDome(BufferBuilder buffer, Matrix4f matrix, float t, float alpha, boolean meteor, boolean big) {
         boolean wrote = false;
         int count = meteor ? (big ? 2800 : 2200) : 1100;
         double spread = meteor ? 1240.0 : 900.0;

         for (int i = 0; i < count; i++) {
            float h0 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 100000 + i * 13);
            float h1 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 110000 + i * 17);
            float h2 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 120000 + i * 19);
            double x = (h0 - 0.5) * spread;
            double z = (h1 - 0.5) * spread;
            double y = 126.0 + h2 * 146.0;
            float twinkle = 0.18F + 0.82F * (0.5F + 0.5F * Mth.sin(t * (0.102F + h0 * 0.21F) + i * 0.61F));
            float hue = h0 + t * 0.0075F + h2 * 0.22F;
            int r = MichaelShifuruMeteorVfxClient.H.rgbR(hue);
            int g = MichaelShifuruMeteorVfxClient.H.rgbG(hue);
            int b = MichaelShifuruMeteorVfxClient.H.rgbB(hue);
            float size = 0.055F + h2 * (meteor ? 0.44F : 0.22F);
            if (i % 37 == 0) {
               size *= 3.8F;
            }

            wrote |= MichaelShifuruMeteorVfxClient.H.star(buffer, matrix, new Vec3(x, y, z), size, r, g, b, alpha * twinkle * (meteor ? 1.14F : 0.86F));
            if (i % 59 == 0) {
               wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(buffer, matrix, new Vec3(x, y, z), size * 2.2F, 255, 248, 255, alpha * twinkle * 0.34F);
            }
         }

         return wrote;
      }

      private boolean renderHighAuraStardust(BufferBuilder buffer, Matrix4f matrix, float t, float alpha, boolean meteor, boolean big) {
         boolean wrote = false;
         int count = meteor ? (big ? 680 : 540) : 270;
         double spread = meteor ? 1120.0 : 790.0;

         for (int i = 0; i < count; i++) {
            float h0 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 121000 + i * 29);
            float h1 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 122000 + i * 31);
            float h2 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 123000 + i * 37);
            double x = (h0 - 0.5) * spread;
            double z = (h1 - 0.5) * spread;
            double y = 180.0 + h2 * 150.0;
            float pulse = 0.16F + 0.84F * (0.5F + 0.5F * Mth.sin(t * (0.144F + h2 * 0.21F) + i * 1.77F));
            float size = 0.95F + h2 * (meteor ? 4.1F : 2.2F);
            float hue = h0 * 1.2F + t * 0.0105F;
            int r = MichaelShifuruMeteorVfxClient.H.rgbR(hue);
            int g = MichaelShifuruMeteorVfxClient.H.rgbG(hue);
            int b = MichaelShifuruMeteorVfxClient.H.rgbB(hue);
            Vec3 p = new Vec3(x, y, z);
            wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(buffer, matrix, p, size * pulse, r, g, b, alpha * pulse * (meteor ? 0.92F : 0.56F));
            if (i % 7 == 0) {
               wrote |= MichaelShifuruMeteorVfxClient.H.star(buffer, matrix, p.add(0.0, size * 0.18, 0.0), size * 0.5F, 255, 248, 255, alpha * pulse * 0.38F);
            }
         }

         return wrote;
      }

      private boolean renderWishShowers(BufferBuilder buffer, Matrix4f matrix, float t, float alpha, boolean meteor, boolean big) {
         boolean wrote = false;
         int count = meteor ? (big ? 280 : 210) : 100;

         for (int i = 0; i < count; i++) {
            float h0 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 130000 + i * 31);
            float h1 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 140000 + i * 37);
            float h2 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 150000 + i * 41);
            float cycle = 150.0F + h2 * 90.0F;
            float local = (t * 3.0F + h0 * cycle) % cycle;
            if (!(local > 112.0F)) {
               float p = MichaelShifuruMeteorVfxClient.H.smooth(local / 112.0F);
               float live = alpha
                  * MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, local / 18.0F))
                  * MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, (112.0F - local) / 28.0F));
               Vec3 start = new Vec3(-470.0 + h0 * 940.0, 106.0 + h2 * 68.0, -380.0 + h1 * 760.0);
               Vec3 end = start.add(140.0 + h1 * 220.0, -12.0 - h2 * 16.0, 92.0 + h0 * 150.0);
               Vec3 pos = start.lerp(end, p);
               Vec3 tail = pos.subtract(end.subtract(start).normalize().scale(24.0 + h2 * 38.0));
               float hue = h0 + t * 0.009F;
               int r = MichaelShifuruMeteorVfxClient.H.rgbR(hue);
               int g = MichaelShifuruMeteorVfxClient.H.rgbG(hue);
               int b = MichaelShifuruMeteorVfxClient.H.rgbB(hue);
               wrote |= MichaelShifuruMeteorVfxClient.H.ribbon(buffer, matrix, tail, pos, 0.034F + h2 * 0.066F, r, g, b, live * 1.04F);
               if (i % 3 == 0) {
                  wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(buffer, matrix, pos, 0.12F + h2 * 0.28F, 255, 242, 255, live * 0.82F);
               }
            }
         }

         return wrote;
      }

      private boolean renderPortalWall(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 sky,
         Vec3 target,
         MichaelShifuruMeteorVfxClient.Basis basis,
         float t,
         float alpha,
         boolean big,
         boolean meteor
      ) {
         boolean wrote = false;
         int count = meteor ? (big ? 108 : 86) : 56;

         for (int i = 0; i < count; i++) {
            MichaelShifuruMeteorVfxClient.Portal portal = this.portal(sky, target, basis, i, t, meteor, false, big ? 1.0F : 0.72F);
            float born = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, (t - i * (meteor ? 0.22F : 0.3F)) / 24.0F));
            if (!(born <= 0.001F)) {
               float live = alpha * born;
               wrote |= MichaelShifuruMeteorVfxClient.H.lataibPortal(
                  buffer, matrix, portal.pos, portal.normal, portal.side, portal.up, portal.radius, this.seed, i, t, live
               );
            }
         }

         return wrote;
      }

      private boolean renderMainPortal(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 sky,
         Vec3 target,
         MichaelShifuruMeteorVfxClient.Basis basis,
         float t,
         float alpha,
         boolean big,
         boolean meteor
      ) {
         MichaelShifuruMeteorVfxClient.Portal portal = this.portal(sky, target, basis, 999, t, meteor, true, big ? 1.0F : 0.78F);
         boolean wrote = MichaelShifuruMeteorVfxClient.H.lataibPortal(
            buffer, matrix, portal.pos, portal.normal, portal.side, portal.up, portal.radius, this.seed, 999, t, alpha
         );
         return MichaelShifuruMeteorVfxClient.H.mainVoid(
               buffer, matrix, portal.pos, portal.normal, portal.side, portal.up, portal.radius, this.seed, t, alpha, meteor
            )
            || wrote;
      }

      private boolean renderPhainonStarPreview(
         BufferBuilder buffer, Matrix4f matrix, Vec3 sky, Vec3 ground, MichaelShifuruMeteorVfxClient.Basis basis, float t, float alpha, boolean big
      ) {
         boolean wrote = false;
         float delay = big ? 84.0F : 98.0F;
         float local = Math.max(0.0F, t - delay);
         float p = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, local / 120.0F));
         float live = alpha * MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, local / 36.0F));
         Vec3 start = sky.add(basis.forward.scale(-24.0)).add(basis.up.scale(18.0));
         Vec3 end = ground.add(basis.forward.scale(10.0));
         Vec3 pos = start.lerp(end, p);
         Vec3 dir = end.subtract(start).normalize();
         Vec3 tail = pos.subtract(dir.scale(big ? 180.0 : 136.0));
         wrote |= MichaelShifuruMeteorVfxClient.H.ribbon(buffer, matrix, tail, pos, big ? 0.7F : 0.42F, 255, 160, 255, live * 0.88F);
         wrote |= MichaelShifuruMeteorVfxClient.H.ribbon(buffer, matrix, tail.lerp(pos, 0.24), pos, big ? 0.26F : 0.16F, 142, 148, 255, live * 0.88F);
         return wrote | MichaelShifuruMeteorVfxClient.H.bigStar(buffer, matrix, pos, big ? 9.4F : 7.2F, 255, 176, 255, live);
      }

      private boolean renderImpact(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelShifuruMeteorVfxClient.Basis basis, float t, float alpha, boolean big
      ) {
         return !big ? this.renderSmallImpact(buffer, matrix, center, basis, t, alpha) : this.renderBigImpact(buffer, matrix, center, basis, t, alpha);
      }

      private boolean renderSmallImpact(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelShifuruMeteorVfxClient.Basis basis, float t, float alpha) {
         boolean wrote = false;
         float expand = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, t / 15.0F));
         float fade = 1.0F - MichaelShifuruMeteorVfxClient.H.smooth(Mth.clamp((t - 55.0F) / 82.0F, 0.0F, 1.0F));
         float live = alpha * fade;
         float base = Math.max(10.0F, this.radius * 2.7F);
         float orb = base * (0.32F + expand * 3.05F);
         Vec3 c = center.add(basis.forward.scale(0.75));
         wrote |= MichaelShifuruMeteorVfxClient.H.orb(buffer, matrix, c, basis, orb, 255, 72, 245, live * 0.54F, 18);
         wrote |= MichaelShifuruMeteorVfxClient.H.orb(buffer, matrix, c.add(basis.forward.scale(0.28)), basis, orb * 0.64F, 255, 210, 255, live * 0.4F, 14);
         wrote |= MichaelShifuruMeteorVfxClient.H.orb(buffer, matrix, c.add(basis.forward.scale(0.55)), basis, orb * 0.32F, 255, 248, 255, live * 0.34F, 10);
         int sheets = 22;

         for (int i = 0; i < sheets; i++) {
            float h0 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 700000 + i * 17);
            float h1 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 710000 + i * 19);
            float h2 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 720000 + i * 23);
            float phase = t * (0.034F + h2 * 0.028F) + h0 * (float) (Math.PI * 2);
            double angle = h0 * Math.PI * 2.0;
            double sweep = 0.72 + h1 * 0.82;
            double r0 = orb * (0.28 + h2 * 0.36);
            double r1 = orb * (0.7 + h1 * 0.86);
            Vec3 radial = basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle))).normalize();
            Vec3 tangent = basis.right.scale(-Math.sin(angle)).add(basis.up.scale(Math.cos(angle))).normalize();
            Vec3 p0 = c.add(radial.scale(r0)).add(basis.forward.scale(Math.sin(phase) * base * 0.28));
            Vec3 p1 = c.add(radial.scale((r0 + r1) * 0.5))
               .add(tangent.scale(Math.sin(phase + 1.2) * orb * 0.24))
               .add(basis.forward.scale(1.4 + Math.cos(phase * 0.8) * base * 0.38));
            Vec3 p2 = c.add(radial.scale(r1))
               .add(tangent.scale(Math.sin(phase + sweep) * orb * 0.34))
               .add(basis.forward.scale(2.4 + Math.sin(phase + 2.0) * base * 0.45));
            float hue = 0.84F + h0 * 0.1F + t * 0.006F;
            int r = MichaelShifuruMeteorVfxClient.H.rgbR(hue);
            int g = MichaelShifuruMeteorVfxClient.H.rgbG(hue);
            int b = MichaelShifuruMeteorVfxClient.H.rgbB(hue);
            wrote |= MichaelShifuruMeteorVfxClient.H.curveSheet(
               buffer, matrix, p0, p1, p2, basis.forward, 0.95F + h1 * 2.1F, r, g, b, live * (0.22F + h2 * 0.3F)
            );
            if (i % 5 == 0) {
               wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(buffer, matrix, p2, 0.44F + h2 * 1.25F, 255, 176, 255, live * 0.48F);
            }
         }

         int sparks = 90;

         for (int i = 0; i < sparks; i++) {
            float h0 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 760000 + i * 17);
            float h1 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 770000 + i * 19);
            float h2 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 780000 + i * 23);
            double yaw = h0 * Math.PI * 2.0;
            double lift = (h1 - 0.34) * 0.95;
            Vec3 dir = basis.right.scale(Math.cos(yaw)).add(basis.up.scale(Math.sin(yaw))).add(basis.forward.scale(lift));
            if (dir.lengthSqr() < 0.001) {
               dir = basis.forward;
            }

            dir = dir.normalize();
            Vec3 p = c.add(dir.scale(orb * (0.28 + h2 * 0.92) * expand));
            float hue = h0 + t * 0.012F;
            if (i % 3 == 0) {
               wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(
                  buffer,
                  matrix,
                  p,
                  0.26F + h2 * 0.74F,
                  MichaelShifuruMeteorVfxClient.H.rgbR(hue),
                  MichaelShifuruMeteorVfxClient.H.rgbG(hue),
                  MichaelShifuruMeteorVfxClient.H.rgbB(hue),
                  live * (0.24F + h1 * 0.34F)
               );
            } else {
               wrote |= MichaelShifuruMeteorVfxClient.H.star(buffer, matrix, p, 0.12F + h2 * 0.28F, 255, 164, 255, live * (0.24F + h1 * 0.3F));
            }
         }

         return wrote;
      }

      private boolean renderBigImpact(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelShifuruMeteorVfxClient.Basis basis, float t, float alpha) {
         boolean wrote = false;
         float in = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, t / 18.0F));
         float sustain = 1.0F - MichaelShifuruMeteorVfxClient.H.smooth(Mth.clamp((t - 300.0F) / 150.0F, 0.0F, 1.0F));
         float live = alpha * sustain;
         float height = 460.0F + in * 560.0F;
         float core = 18.0F + in * 52.0F;
         wrote |= MichaelShifuruMeteorVfxClient.H.beam(
            buffer, matrix, center, basis.forward, basis.right, basis.up, height, core * 1.85F, 255, 92, 255, live * 0.22F, 30
         );
         wrote |= MichaelShifuruMeteorVfxClient.H.beam(
            buffer, matrix, center, basis.forward, basis.right, basis.up, height, core * 1.16F, 255, 244, 255, live * 0.78F, 22
         );
         wrote |= MichaelShifuruMeteorVfxClient.H.beam(
            buffer, matrix, center, basis.forward, basis.right, basis.up, height, core * 0.62F, 142, 154, 255, live * 0.78F, 16
         );
         wrote |= MichaelShifuruMeteorVfxClient.H.beam(
            buffer, matrix, center, basis.forward, basis.right, basis.up, height * 0.82F, core * 2.7F, 150, 88, 255, live * 0.12F, 34
         );
         int streams = 14;

         for (int stream = 0; stream < streams; stream++) {
            float h = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 890000 + stream * 41);
            float hue = h + t * 0.0022F;
            int r = MichaelShifuruMeteorVfxClient.H.rgbR(hue);
            int g = MichaelShifuruMeteorVfxClient.H.rgbG(hue);
            int b = MichaelShifuruMeteorVfxClient.H.rgbB(hue);
            int segs = 86;

            for (int i = 0; i < segs; i++) {
               float p0 = (float)i / segs;
               float p1 = (float)(i + 1) / segs;
               double twist0 = t * (0.018 + h * 0.022) + p0 * Math.PI * (4.5 + h * 4.0) + stream * Math.PI * 2.0 / streams;
               double twist1 = t * (0.018 + h * 0.022) + p1 * Math.PI * (4.5 + h * 4.0) + stream * Math.PI * 2.0 / streams;
               double wave0 = Math.sin(t * 0.035 + p0 * 18.0 + stream) * core * (0.2 + h * 0.28);
               double wave1 = Math.sin(t * 0.035 + p1 * 18.0 + stream) * core * (0.2 + h * 0.28);
               double rad0 = core * (1.35 + h * 2.8) * (0.25 + p0 * 0.95) + wave0;
               double rad1 = core * (1.35 + h * 2.8) * (0.25 + p1 * 0.95) + wave1;
               Vec3 a = center.add(basis.forward.scale(height * p0))
                  .add(basis.right.scale(Math.cos(twist0) * rad0))
                  .add(basis.up.scale(Math.sin(twist0) * rad0));
               Vec3 bpos = center.add(basis.forward.scale(height * p1))
                  .add(basis.right.scale(Math.cos(twist1) * rad1))
                  .add(basis.up.scale(Math.sin(twist1) * rad1));
               wrote |= MichaelShifuruMeteorVfxClient.H.ribbon(buffer, matrix, a, bpos, 0.48F + h * 0.54F, r, g, b, live * (0.34F + h * 0.22F));
            }
         }

         int helixSegments = 124;
         float helixRadius = core * 3.6F;

         for (int i = 0; i < helixSegments; i++) {
            float p0 = (float)i / helixSegments;
            float p1 = (float)(i + 1) / helixSegments;
            double a0 = t * 0.07 + p0 * Math.PI * 10.0;
            double a1 = t * 0.07 + p1 * Math.PI * 10.0;
            Vec3 s0 = center.add(basis.forward.scale(height * p0))
               .add(basis.right.scale(Math.cos(a0) * helixRadius))
               .add(basis.up.scale(Math.sin(a0) * helixRadius));
            Vec3 s1 = center.add(basis.forward.scale(height * p1))
               .add(basis.right.scale(Math.cos(a1) * helixRadius))
               .add(basis.up.scale(Math.sin(a1) * helixRadius));
            Vec3 q0 = center.add(basis.forward.scale(height * p0))
               .add(basis.right.scale(Math.cos(a0 + Math.PI) * helixRadius))
               .add(basis.up.scale(Math.sin(a0 + Math.PI) * helixRadius));
            Vec3 q1 = center.add(basis.forward.scale(height * p1))
               .add(basis.right.scale(Math.cos(a1 + Math.PI) * helixRadius))
               .add(basis.up.scale(Math.sin(a1 + Math.PI) * helixRadius));
            wrote |= MichaelShifuruMeteorVfxClient.H.ribbon(buffer, matrix, s0, s1, 0.7F, 255, 142, 255, live * 0.9F);
            wrote |= MichaelShifuruMeteorVfxClient.H.ribbon(buffer, matrix, q0, q1, 0.54F, 146, 150, 255, live * 0.9F);
            if (i % 5 == 0) {
               wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(buffer, matrix, s1, 1.8F, 255, 238, 255, live * 0.48F);
               wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(buffer, matrix, q1, 1.5F, 160, 140, 255, live * 0.48F);
            }
         }

         wrote |= MichaelShifuruMeteorVfxClient.H.orb(
            buffer, matrix, center.add(basis.forward.scale(18.0)), basis, 84.0F + in * 120.0F, 255, 96, 255, live * 0.22F, 12
         );
         wrote |= MichaelShifuruMeteorVfxClient.H.orb(
            buffer, matrix, center.add(basis.forward.scale(26.0)), basis, 58.0F + in * 86.0F, 150, 142, 255, live * 0.22F, 10
         );
         wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(
            buffer, matrix, center.add(basis.forward.scale(12.0)), 82.0F + in * 120.0F, 255, 178, 255, live * 0.68F
         );
         wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(
            buffer, matrix, center.add(basis.forward.scale(24.0)), 124.0F + in * 168.0F, 255, 122, 255, live * 0.42F
         );
         int sparks = 520;

         for (int i = 0; i < sparks; i++) {
            float h0 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 930000 + i * 17);
            float h1 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 940000 + i * 19);
            float h2 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 950000 + i * 23);
            float delay = h2 * 70.0F;
            float local = Math.max(0.0F, t - delay);
            float travel = MichaelShifuruMeteorVfxClient.H.smooth(Math.min(1.0F, local / 130.0F));
            float fade = 1.0F - MichaelShifuruMeteorVfxClient.H.smooth(Mth.clamp((local - 190.0F) / 140.0F, 0.0F, 1.0F));
            if (!(travel <= 0.001F) && !(fade <= 0.001F)) {
               double yaw = h0 * Math.PI * 2.0;
               double lift = (h1 - 0.28) * 1.8;
               Vec3 dir = basis.right.scale(Math.cos(yaw)).add(basis.up.scale(Math.sin(yaw))).add(basis.forward.scale(lift));
               if (dir.lengthSqr() < 0.001) {
                  dir = basis.forward;
               }

               dir = dir.normalize();
               Vec3 pos = center.add(dir.scale((80.0 + h2 * 360.0) * travel)).add(basis.forward.scale(Math.sin(t * 0.022 + i) * 12.0));
               float hue = h0 + t * 0.009F;
               float particleLive = live * fade * (0.28F + h1 * 0.44F);
               wrote |= MichaelShifuruMeteorVfxClient.H.bigStar(
                  buffer,
                  matrix,
                  pos,
                  0.8F + h2 * 3.8F,
                  MichaelShifuruMeteorVfxClient.H.rgbR(hue),
                  MichaelShifuruMeteorVfxClient.H.rgbG(hue),
                  MichaelShifuruMeteorVfxClient.H.rgbB(hue),
                  particleLive * 1.08F
               );
            }
         }

         return wrote;
      }

      private MichaelShifuruMeteorVfxClient.Portal portal(
         Vec3 sky, Vec3 target, MichaelShifuruMeteorVfxClient.Basis basis, int i, float t, boolean meteor, boolean main, float charge
      ) {
         if (main) {
            Vec3 pos = sky.add(basis.forward.scale(meteor ? -360.0 : -150.0)).add(basis.up.scale(meteor ? 32.0 : 14.0));
            Vec3 normal = target.subtract(pos);
            if (normal.lengthSqr() < 0.001) {
               normal = basis.forward;
            }

            normal = normal.normalize();
            MichaelShifuruMeteorVfxClient.Basis pb = MichaelShifuruMeteorVfxClient.H.basis(normal);
            double spin = t * 0.006;
            Vec3 sideAxis = pb.right.scale(Math.cos(spin)).add(pb.up.scale(Math.sin(spin))).normalize();
            Vec3 upAxis = sideAxis.cross(normal).normalize();
            return new MichaelShifuruMeteorVfxClient.Portal(pos, normal, sideAxis, upAxis, (meteor ? 176.0 : 42.0) + charge * (meteor ? 112.0 : 32.0));
         } else {
            float h0 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 210000 + i * 71);
            float h1 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 220000 + i * 79);
            float h2 = MichaelShifuruMeteorVfxClient.H.hash(this.seed, 230000 + i * 83);
            int columns = meteor ? 14 : 10;
            int verticalBands = meteor ? 7 : 4;
            double row = i % columns - (columns - 1) * 0.5;
            double band = Math.floor((double)i / columns);
            double layer = band % verticalBands - (verticalBands - 1) * 0.5;
            double depthBand = Math.floor(band / verticalBands);
            double sideSpacing = meteor ? 66.0 : 38.0;
            double side = row * sideSpacing + (h0 - 0.5) * (meteor ? 34.0 : 18.0);
            double up = layer * (meteor ? 27.0 : 13.5) + (h1 - 0.5) * (meteor ? 18.0 : 6.0);
            double depth = -128.0 - depthBand * (meteor ? 68.0 : 30.0) - h2 * (meteor ? 340.0 : 150.0);
            Vec3 pos = sky.add(basis.right.scale(side)).add(basis.up.scale(up)).add(basis.forward.scale(depth));
            Vec3 normal = target.subtract(pos);
            if (normal.lengthSqr() < 0.001) {
               normal = basis.forward;
            }

            normal = normal.normalize();
            MichaelShifuruMeteorVfxClient.Basis pb = MichaelShifuruMeteorVfxClient.H.basis(normal);
            double spin = h0 * Math.PI * 2.0 + t * (0.006 + h1 * 0.01) * ((i & 1) == 0 ? 1.0 : -1.0);
            Vec3 sideAxis = pb.right.scale(Math.cos(spin)).add(pb.up.scale(Math.sin(spin))).normalize();
            Vec3 upAxis = sideAxis.cross(normal).normalize();
            double pr = (meteor ? 13.0 : 6.2) + h2 * (meteor ? 8.6 : 4.2) + charge * (meteor ? 4.2 : 2.2);
            return new MichaelShifuruMeteorVfxClient.Portal(pos, normal, sideAxis, upAxis, pr);
         }
      }
   }

   private static final class H {
      public static BufferBuilder begin() {
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         return Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
      }

      public static void finish(BufferBuilder buffer, boolean wrote) {
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

      public static Vec3 camera() {
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         return camera.getPosition();
      }

      public static Vec3 rel(Vec3 p, Vec3 cam) {
         return p.subtract(cam);
      }

      public static float smooth(float x) {
         x = Mth.clamp(x, 0.0F, 1.0F);
         return x * x * (3.0F - 2.0F * x);
      }

      public static float hash(long seed, int salt) {
         long x = seed + salt * -7046029254386353131L;
         x ^= x >>> 30;
         x *= -4658895280553007687L;
         x ^= x >>> 27;
         x *= -7723592293110705685L;
         x ^= x >>> 31;
         return (float)(x & 16777215L) / 1.6777216E7F;
      }

      public static MichaelShifuruMeteorVfxClient.Basis basis(Vec3 forward) {
         if (forward.lengthSqr() < 0.001) {
            forward = new Vec3(0.0, 0.0, 1.0);
         }

         forward = forward.normalize();
         Vec3 ref = Math.abs(forward.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         Vec3 right = forward.cross(ref);
         if (right.lengthSqr() < 0.001) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = right.cross(forward);
         if (up.lengthSqr() < 0.001) {
            up = new Vec3(0.0, 1.0, 0.0);
         }

         up = up.normalize();
         return new MichaelShifuruMeteorVfxClient.Basis(forward, right, up);
      }

      public static boolean lataibPortal(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, Vec3 normal, Vec3 side, Vec3 up, double radius, long seed, int portal, float time, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         int segments = 92;

         for (int layer = 0; layer < 8; layer++) {
            double layerBase = radius * (0.82 + layer * 0.05 + Math.sin(time * 0.028 + portal + layer) * 0.014);
            float width = 0.022F + layer * 0.006F;
            float la = alpha * (0.34F + layer * 0.046F);
            int r = layer < 2 ? 124 : (layer < 5 ? 186 : 255);
            int g = layer < 2 ? 34 : (layer < 5 ? 74 : 196);
            int b = 255;

            for (int s = 0; s < segments; s++) {
               float hs = hash(seed, portal * 4000 + layer * 541 + s * 17);
               double wobble0 = Math.sin(time * (0.06 + hs * 0.035) + s * 0.79 + portal) * (0.02 + hs * 0.05);
               double wobble1 = Math.sin(time * (0.064 + hs * 0.035) + (s + 1) * 0.79 + portal) * (0.02 + hs * 0.05);
               double a0 = s * Math.PI * 2.0 / segments;
               double a1 = (s + 1) * Math.PI * 2.0 / segments;
               Vec3 p0 = c.add(side.scale(Math.cos(a0) * (layerBase + wobble0)))
                  .add(up.scale(Math.sin(a0) * (layerBase + wobble0)))
                  .add(normal.scale((hs - 0.5) * 0.035));
               Vec3 p1 = c.add(side.scale(Math.cos(a1) * (layerBase + wobble1)))
                  .add(up.scale(Math.sin(a1) * (layerBase + wobble1)))
                  .add(normal.scale((hash(seed, portal * 4100 + layer * 557 + s * 19) - 0.5) * 0.035));
               wrote |= ribbon(buffer, matrix, p0, p1, width, r, g, b, la * (0.72F + hs * 0.28F));
            }
         }

         for (int layer = 0; layer < 4; layer++) {
            double r0 = radius * (1.06 + layer * 0.075 + Math.sin(time * 0.032 + portal + layer) * 0.025);
            wrote |= ring(
               buffer,
               matrix,
               c.add(normal.scale(-0.02 - layer * 0.014)),
               side,
               up,
               r0,
               0.012F + layer * 0.006F,
               layer == 0 ? 255 : 164,
               layer == 0 ? 174 : 74,
               255,
               alpha * (0.26F - layer * 0.04F),
               96
            );
         }

         for (int i = 0; i < 18; i++) {
            float h0 = hash(seed, portal * 2000 + i * 61);
            float h1 = hash(seed, portal * 2100 + i * 67);
            double a = h0 * Math.PI * 2.0 + time * (0.018 + h1 * 0.025) * ((i & 1) == 0 ? 1.0 : -1.0);
            double d = Math.sqrt(h1) * radius * 0.68;
            Vec3 p = c.add(side.scale(Math.cos(a) * d)).add(up.scale(Math.sin(a) * d)).add(normal.scale((hash(seed, portal * 2200 + i * 71) - 0.5) * 0.1));
            float twinkle = 0.5F + 0.5F * Mth.sin(time * (0.14F + h0 * 0.08F) + i);
            wrote |= star(buffer, matrix, p, 0.016F + h1 * 0.04F, i % 3 == 0 ? 255 : 168, i % 3 == 0 ? 142 : 124, 255, alpha * 0.52F * twinkle);
         }

         return wrote;
      }

      public static boolean mainVoid(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, Vec3 normal, Vec3 side, Vec3 up, double radius, long seed, float time, float alpha, boolean giant
      ) {
         boolean wrote = false;
         wrote |= ring(buffer, matrix, c.subtract(normal.scale(0.05)), side, up, radius * 0.84, 0.018F, 255, 190, 255, alpha * 0.14F, 128);
         wrote |= ring(buffer, matrix, c.subtract(normal.scale(0.07)), side, up, radius * 0.58, 0.016F, 148, 126, 255, alpha * 0.12F, 96);
         int edgeStars = giant ? 260 : 120;

         for (int i = 0; i < edgeStars; i++) {
            float h0 = hash(seed, 240000 + i * 53);
            float h1 = hash(seed, 250000 + i * 59);
            double a = h0 * Math.PI * 2.0 + time * (0.006 + h1 * 0.014);
            double d = radius * (0.76 + h1 * 0.3);
            Vec3 p = c.add(side.scale(Math.cos(a) * d)).add(up.scale(Math.sin(a) * d)).add(normal.scale((hash(seed, 260000 + i) - 0.5) * 1.6));
            wrote |= star(buffer, matrix, p, 0.12F + h1 * 0.4F, i % 2 == 0 ? 255 : 172, i % 2 == 0 ? 164 : 122, 255, alpha * (giant ? 0.76F : 0.6F));
         }

         if (!giant) {
            wrote |= disk(buffer, matrix, c.subtract(normal.scale(0.12)), side, up, radius * 0.72, 14, 0, 30, alpha * 0.62F, 120);
            wrote |= disk(buffer, matrix, c.subtract(normal.scale(0.02)), side, up, radius * 0.22, 255, 180, 255, alpha * 0.14F, 80);
         }

         return wrote;
      }

      public static boolean orb(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, MichaelShifuruMeteorVfxClient.Basis basis, double radius, int r, int g, int bl, float alpha, int bands
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         int slices = 64;
         int shells = Math.max(6, bands);

         for (int i = 0; i < shells; i++) {
            double k = (double)i / Math.max(1, shells - 1);
            double off = (k - 0.5) * radius * 1.68;
            double rr = radius * Math.sqrt(Math.max(0.0, 1.0 - Math.pow(off / (radius * 0.84), 2.0)));
            float a = alpha * (float)(0.1 + 0.18 * (1.0 - Math.abs(k - 0.5) * 2.0));
            wrote |= disk(buffer, matrix, c.add(basis.forward.scale(off)), basis.right, basis.up, rr, r, g, bl, a, slices);
         }

         for (int i = 0; i < shells; i++) {
            double k = (double)i / Math.max(1, shells - 1);
            double off = (k - 0.5) * radius * 1.46;
            double rr = radius * Math.sqrt(Math.max(0.0, 1.0 - Math.pow(off / (radius * 0.73), 2.0)));
            float a = alpha * (float)(0.06 + 0.1 * (1.0 - Math.abs(k - 0.5) * 2.0));
            wrote |= disk(buffer, matrix, c.add(basis.right.scale(off)), basis.forward, basis.up, rr, r, g, bl, a, slices);
         }

         wrote |= disk(buffer, matrix, c, basis.right, basis.up, radius * 0.62, 255, 170, 255, alpha * 0.22F, slices);
         return wrote
            | disk(buffer, matrix, c.add(basis.forward.scale(radius * 0.06)), basis.right, basis.up, radius * 0.34, 255, 198, 255, alpha * 0.2F, slices);
      }

      public static boolean curveSheet(
         BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 normal, float width, int r, int g, int bl, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         Vec3 last = a;
         int segments = 9;

         for (int i = 1; i <= segments; i++) {
            double t = (double)i / segments;
            double inv = 1.0 - t;
            Vec3 p = a.scale(inv * inv).add(b.scale(2.0 * inv * t)).add(c.scale(t * t));
            float localWidth = width * (float)(0.72 + Math.sin(t * Math.PI) * 0.72);
            wrote |= ribbon(buffer, matrix, last, p, localWidth, r, g, bl, alpha * (float)(0.38 + Math.sin(t * Math.PI) * 0.62));
            last = p;
         }

         return wrote;
      }

      public static int rgbR(float hue) {
         return Mth.clamp((int)(214.0F + 41.0F * Mth.sin(hue * (float) (Math.PI * 2) + 0.15F)), 0, 255);
      }

      public static int rgbG(float hue) {
         return Mth.clamp((int)(92.0F + 34.0F * Mth.sin(hue * (float) (Math.PI * 2) + 1.45F)), 0, 255);
      }

      public static int rgbB(float hue) {
         return Mth.clamp((int)(228.0F + 27.0F * Mth.sin(hue * (float) (Math.PI * 2) + 3.65F)), 0, 255);
      }

      public static boolean beam(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 base,
         Vec3 forward,
         Vec3 right,
         Vec3 up,
         float height,
         float radius,
         int r,
         int g,
         int b,
         float alpha,
         int slices
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < slices; i++) {
            double a0 = i * Math.PI * 2.0 / slices;
            double a1 = (i + 1) * Math.PI * 2.0 / slices;
            Vec3 o0 = right.scale(Math.cos(a0) * radius).add(up.scale(Math.sin(a0) * radius));
            Vec3 o1 = right.scale(Math.cos(a1) * radius).add(up.scale(Math.sin(a1) * radius));
            Vec3 p0 = base.add(o0);
            Vec3 p1 = base.add(o1);
            Vec3 p2 = base.add(forward.scale(height)).add(o1);
            Vec3 p3 = base.add(forward.scale(height)).add(o0);
            face(buffer, matrix, p0, p1, p2, p3, r, g, b, alpha);
            wrote = true;
         }

         return wrote;
      }

      public static boolean ring(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, Vec3 side, Vec3 up, double radius, float width, int r, int g, int bl, float alpha, int segments
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < segments; i++) {
            double a0 = i * Math.PI * 2.0 / segments;
            double a1 = (i + 1) * Math.PI * 2.0 / segments;
            Vec3 p0 = c.add(side.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
            Vec3 p1 = c.add(side.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
            wrote |= ribbon(buffer, matrix, p0, p1, width, r, g, bl, alpha);
         }

         return wrote;
      }

      public static boolean disk(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, Vec3 side, Vec3 up, double radius, int r, int g, int bl, float alpha, int segments
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         for (int i = 0; i < segments; i++) {
            double a0 = i * Math.PI * 2.0 / segments;
            double a1 = (i + 1) * Math.PI * 2.0 / segments;
            Vec3 p0 = c.add(side.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
            Vec3 p1 = c.add(side.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
            tri(buffer, matrix, c, p0, p1, r, g, bl, alpha);
         }

         return true;
      }

      public static boolean ribbon(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, float width, int r, int g, int bl, float alpha) {
         if (!(alpha <= 0.001F) && !(a.distanceToSqr(b) <= 1.0E-4)) {
            Vec3 mid = a.add(b).scale(0.5);
            Vec3 toCam = mid.scale(-1.0);
            Vec3 dir = b.subtract(a).normalize();
            Vec3 side = dir.cross(toCam);
            if (side.lengthSqr() < 1.0E-4) {
               side = dir.cross(new Vec3(0.0, 1.0, 0.0));
            }

            if (side.lengthSqr() < 1.0E-4) {
               side = new Vec3(1.0, 0.0, 0.0);
            }

            side = side.normalize().scale(width);
            Vec3 p0 = a.add(side);
            Vec3 p1 = b.add(side);
            Vec3 p2 = b.subtract(side);
            Vec3 p3 = a.subtract(side);
            tri(buffer, matrix, p0, p1, p2, r, g, bl, alpha);
            tri(buffer, matrix, p2, p3, p0, r, g, bl, alpha);
            return true;
         } else {
            return false;
         }
      }

      public static boolean star(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int bl, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 right = new Vec3(size, 0.0, 0.0);
         Vec3 up = new Vec3(0.0, size, 0.0);
         Vec3 f = new Vec3(0.0, 0.0, size * 0.32);
         tri(buffer, matrix, center.add(up), center.add(right.scale(0.2)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(right), center.add(up.scale(-0.2)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(-1.0)), center.add(right.scale(-0.2)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(right.scale(-1.0)), center.add(up.scale(0.2)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(0.45)), center.add(right.scale(0.45)), center.add(up.scale(-0.45)), r, g, bl, alpha * 0.42F);
         tri(buffer, matrix, center.add(up.scale(-0.45)), center.add(right.scale(-0.45)), center.add(up.scale(0.45)), r, g, bl, alpha * 0.42F);
         return true;
      }

      public static boolean bigStar(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int bl, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         wrote |= star(buffer, matrix, center, size, r, g, bl, alpha);
         wrote |= star(buffer, matrix, center.add(0.0, size * 0.06, 0.0), size * 0.58F, 255, 186, 255, alpha * 0.4F);
         Vec3 right = new Vec3(size * 1.42, 0.0, 0.0);
         Vec3 up = new Vec3(0.0, size * 1.42, 0.0);
         tri(buffer, matrix, center.add(up), center.add(right.scale(0.1)), center.add(right.scale(-0.1)), r, g, bl, alpha * 0.28F);
         tri(buffer, matrix, center.add(right), center.add(up.scale(-0.1)), center.add(up.scale(0.1)), r, g, bl, alpha * 0.28F);
         return wrote;
      }

      public static void face(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, float alpha) {
         tri(buffer, matrix, a, b, c, r, g, bl, alpha);
         tri(buffer, matrix, c, d, a, r, g, bl, alpha);
      }

      public static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
         int aa = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         if (aa > 0) {
            buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), aa);
            buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), aa);
            buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), aa);
         }
      }
   }

   private record Portal(Vec3 pos, Vec3 normal, Vec3 side, Vec3 up, double radius) {
   }
}
