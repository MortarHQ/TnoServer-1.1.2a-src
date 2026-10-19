package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.net.msg.MichaelLataibBarrageVfxPayload;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
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

public final class MichaelLataibBarrageVfxClient {
   private static final Map<Integer, MichaelLataibBarrageVfxClient.Barrage> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;

   private MichaelLataibBarrageVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MichaelLataibBarrageVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(MichaelLataibBarrageVfxClient::render);
      }
   }

   public static void play(MichaelLataibBarrageVfxPayload payload) {
      init();
      ACTIVE.put(payload.casterId(), new MichaelLataibBarrageVfxClient.Barrage(payload));

      while (ACTIVE.size() > 16) {
         Integer key = ACTIVE.keySet().iterator().next();
         ACTIVE.remove(key);
      }
   }

   private static void tick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<Entry<Integer, MichaelLataibBarrageVfxClient.Barrage>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            MichaelLataibBarrageVfxClient.Barrage barrage = iterator.next().getValue();
            barrage.age++;
            if (barrage.age > barrage.life) {
               iterator.remove();
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES || event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && !ACTIVE.isEmpty()) {
            Vec3 cam = MichaelLataibBarrageVfxClient.H.camera();
            Matrix4f matrix = event.getPoseStack().last().pose();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            BufferBuilder buffer = MichaelLataibBarrageVfxClient.H.begin();
            boolean wrote = false;

            for (MichaelLataibBarrageVfxClient.Barrage barrage : ACTIVE.values()) {
               wrote |= barrage.render(mc, buffer, matrix, cam, partial);
            }

            MichaelLataibBarrageVfxClient.H.finish(buffer, wrote);
         }
      }
   }

   private static final class Barrage {
      private static final int PORTAL_COUNT = 28;
      private static final int BLOCKS_PER_PORTAL = 7;
      private final int casterId;
      private final int targetId;
      private final Vec3 origin;
      private final Vec3 fallback;
      private final MichaelLataibBarrageVfxClient.Basis lockedBasis;
      private final float radius;
      private final int life;
      private final long seed;
      private int age;

      private Barrage(MichaelLataibBarrageVfxPayload payload) {
         this.casterId = payload.casterId();
         this.targetId = payload.targetId();
         this.origin = new Vec3(payload.x(), payload.y(), payload.z());
         this.fallback = new Vec3(payload.x2(), payload.y2(), payload.z2());
         Vec3 initialAim = this.fallback.subtract(this.origin);
         if (initialAim.lengthSqr() < 0.001) {
            initialAim = new Vec3(0.0, 0.0, 1.0);
         }

         this.lockedBasis = MichaelLataibBarrageVfxClient.H.basis(initialAim.normalize());
         this.radius = Math.max(7.0F, payload.radius());
         this.life = Math.max(420, payload.duration());
         this.seed = payload.seed();
      }

      private boolean render(Minecraft mc, BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         Entity target = this.targetId == -1 ? null : mc.level.getEntity(this.targetId);
         Vec3 casterWorld = this.origin;
         Vec3 targetWorld = target == null ? this.fallback : MichaelLataibBarrageVfxClient.H.entityCenter(target, partial, 0.58);
         MichaelLataibBarrageVfxClient.Basis worldBasis = this.lockedBasis;
         Vec3 casterPos = MichaelLataibBarrageVfxClient.H.rel(casterWorld, cam);
         Vec3 targetPos = MichaelLataibBarrageVfxClient.H.rel(targetWorld, cam);
         float t = this.age + partial;
         float appear = MichaelLataibBarrageVfxClient.H.smooth(Math.min(1.0F, t / 12.0F));
         float vanish = MichaelLataibBarrageVfxClient.H.smooth(Math.min(1.0F, (this.life - t) / 26.0F));
         float alpha = appear * vanish;
         if (alpha <= 0.002F) {
            return false;
         }

         boolean wrote = false;
         wrote |= this.renderPortals(buffer, matrix, casterPos, targetPos, worldBasis, t, alpha);
         wrote |= this.renderBlocks(buffer, matrix, casterPos, targetPos, worldBasis, t, alpha);
         return wrote | this.renderVoidStars(buffer, matrix, casterPos, worldBasis, t, alpha);
      }

      private boolean renderPortals(
         BufferBuilder buffer, Matrix4f matrix, Vec3 caster, Vec3 target, MichaelLataibBarrageVfxClient.Basis basis, float t, float alpha
      ) {
         boolean wrote = false;

         for (int i = 0; i < 28; i++) {
            MichaelLataibBarrageVfxClient.Portal portal = this.portal(i, caster, target, basis, t);
            float born = MichaelLataibBarrageVfxClient.H.smooth(Math.min(1.0F, (t - i * 0.42F) / 9.0F));
            if (!(born <= 0.001F)) {
               float localAlpha = alpha * born;
               wrote |= MichaelLataibBarrageVfxClient.H.portalRim(
                  buffer, matrix, portal.pos, portal.normal, portal.side, portal.up, portal.radius, this.seed, i, t, localAlpha
               );
               wrote |= MichaelLataibBarrageVfxClient.H.portalVoidSparkles(
                  buffer, matrix, portal.pos, portal.normal, portal.side, portal.up, portal.radius, this.seed, i, t, localAlpha
               );
            }
         }

         return wrote;
      }

      private boolean renderBlocks(
         BufferBuilder buffer, Matrix4f matrix, Vec3 caster, Vec3 target, MichaelLataibBarrageVfxClient.Basis basis, float t, float alpha
      ) {
         boolean wrote = false;
         int total = 196;

         for (int i = 0; i < total; i++) {
            int portalIndex = i % 28;
            int lane = i / 28;
            MichaelLataibBarrageVfxClient.Portal portal = this.portal(portalIndex, caster, target, basis, t);
            float h0 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 8000 + i * 37);
            float h1 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 9000 + i * 41);
            float h2 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 10000 + i * 43);
            float h3 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 11000 + i * 47);
            float cycle = 34.0F + h1 * 13.0F;
            float offset = portalIndex * 1.3F + lane * 4.65F + h0 * 9.0F;
            if (!(t < offset)) {
               float local = (t - offset) % cycle;
               float launchWindow = 25.0F + h2 * 5.0F;
               if (!(local > launchWindow)) {
                  float progress = MichaelLataibBarrageVfxClient.H.smooth(Math.min(1.0F, local / launchWindow));
                  float born = MichaelLataibBarrageVfxClient.H.smooth(Math.min(1.0F, local / 3.0F));
                  float fade = MichaelLataibBarrageVfxClient.H.smooth(Math.min(1.0F, (launchWindow - local) / 6.0F));
                  float live = alpha * born * fade;
                  if (!(live <= 0.002F)) {
                     Vec3 scatter = portal.side.scale((h0 - 0.5) * portal.radius * 0.78).add(portal.up.scale((h1 - 0.5) * portal.radius * 0.72));
                     Vec3 start = portal.pos.add(portal.normal.scale(0.22)).add(scatter);
                     Vec3 end = target.add(basis.right.scale((h0 - 0.5) * 1.8)).add(basis.up.scale((h1 - 0.5) * 2.65));
                     Vec3 mid = caster.lerp(target, 0.46)
                        .add(basis.right.scale((portalIndex - 13.5) * 0.12 + (h0 - 0.5) * 1.6))
                        .add(basis.up.scale(0.9 + h2 * 2.35 + Math.sin(progress * Math.PI) * (0.85 + h3 * 1.2)));
                     Vec3 a0 = start.lerp(mid, progress);
                     Vec3 a1 = mid.lerp(end, progress);
                     Vec3 pos = a0.lerp(a1, progress);
                     float prevP = Math.max(0.0F, progress - 0.042F);
                     Vec3 p0 = start.lerp(mid, prevP).lerp(mid.lerp(end, prevP), prevP);
                     Vec3 dir = pos.subtract(p0);
                     if (dir.lengthSqr() < 0.001) {
                        dir = portal.normal;
                     }

                     dir = dir.normalize();
                     float size = 0.28F + h2 * 0.44F;
                     MichaelLataibBarrageVfxClient.Basis blockBasis = MichaelLataibBarrageVfxClient.H.spinningBasis(
                        dir, basis, t * (0.13 + h3 * 0.09) + i * 0.73
                     );
                     int kind = (i + (int)(h3 * 10.0F)) % 5;
                     int violetR = 112 + (int)(h0 * 68.0F);
                     int violetG = 24 + (int)(h1 * 34.0F);
                     int violetB = 196 + (int)(h2 * 58.0F);
                     int pinkR = 206 + (int)(h3 * 49.0F);
                     int pinkG = 42 + (int)(h0 * 36.0F);
                     int blueR = 82 + (int)(h1 * 42.0F);
                     int blueG = 86 + (int)(h2 * 46.0F);
                     wrote |= MichaelLataibBarrageVfxClient.H.afterImage(buffer, matrix, p0, pos, size * 0.055F, 150, 28, 225, live * 0.38F);
                     wrote |= MichaelLataibBarrageVfxClient.H.star(
                        buffer, matrix, start.subtract(portal.normal.scale(0.06)), 0.02F + h2 * 0.04F, 255, 70, 255, live * 0.72F
                     );
                     if (kind == 0) {
                        wrote |= MichaelLataibBarrageVfxClient.H.blockCube(
                           buffer, matrix, pos, blockBasis, size * 1.28F, violetR, violetG, violetB, live * 0.9F
                        );
                        wrote |= MichaelLataibBarrageVfxClient.H.wireCube(
                           buffer, matrix, pos.add(dir.scale(size * 0.06)), blockBasis, size * 1.45F, 236, 54, 255, live * 0.68F
                        );
                     } else if (kind == 1) {
                        wrote |= MichaelLataibBarrageVfxClient.H.rectBlock(
                           buffer, matrix, pos, blockBasis, size * 0.88F, size * 1.85F, pinkR, pinkG, 255, live * 0.88F
                        );
                        wrote |= MichaelLataibBarrageVfxClient.H.wireCube(buffer, matrix, pos, blockBasis, size * 1.18F, blueR, blueG, 255, live * 0.58F);
                     } else if (kind == 2) {
                        wrote |= MichaelLataibBarrageVfxClient.H.blockCluster(buffer, matrix, pos, blockBasis, size, violetR, pinkR, live * 0.84F, this.seed, i);
                     } else if (kind == 3) {
                        wrote |= MichaelLataibBarrageVfxClient.H.blockCube(
                           buffer, matrix, pos, blockBasis, size * 1.72F, 92 + (int)(h0 * 36.0F), 20 + (int)(h1 * 28.0F), 176 + (int)(h2 * 66.0F), live * 0.8F
                        );
                        wrote |= MichaelLataibBarrageVfxClient.H.star(buffer, matrix, pos.add(dir.scale(size * 0.55)), size * 0.22F, 255, 66, 248, live * 0.8F);
                     } else {
                        wrote |= MichaelLataibBarrageVfxClient.H.shardBlock(
                           buffer, matrix, pos, dir, blockBasis, size * 2.1F, size * 0.8F, pinkR, pinkG, 255, live * 0.88F
                        );
                        wrote |= MichaelLataibBarrageVfxClient.H.wireCube(
                           buffer, matrix, pos.subtract(dir.scale(size * 0.2)), blockBasis, size * 0.88F, 232, 62, 255, live * 0.52F
                        );
                     }

                     if (progress > 0.84F) {
                        float impact = MichaelLataibBarrageVfxClient.H.smooth(Math.min(1.0F, (progress - 0.84F) / 0.16F));
                        float ia = live * (1.0F - impact);
                        wrote |= MichaelLataibBarrageVfxClient.H.portalImpactDust(buffer, matrix, end, basis, size, this.seed, i, ia);
                     }
                  }
               }
            }
         }

         return wrote;
      }

      private boolean renderVoidStars(BufferBuilder buffer, Matrix4f matrix, Vec3 caster, MichaelLataibBarrageVfxClient.Basis basis, float t, float alpha) {
         boolean wrote = false;

         for (int i = 0; i < 160; i++) {
            float h0 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 30000 + i * 13);
            float h1 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 31000 + i * 17);
            float h2 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 32000 + i * 19);
            double side = (h0 - 0.5) * Math.max(24.0, this.radius * 2.35);
            double up = -1.4 + h1 * 12.75;
            double back = 5.2 + h2 * 10.85;
            Vec3 p = caster.subtract(basis.forward.scale(back)).add(basis.right.scale(side)).add(basis.up.scale(up));
            float twinkle = 0.46F + 0.54F * Mth.sin(t * (0.1F + h0 * 0.06F) + i * 1.73F);
            int r = i % 3 == 0 ? 236 : (i % 3 == 1 ? 134 : 255);
            int g = i % 3 == 0 ? 46 : (i % 3 == 1 ? 88 : 72);
            int b = 255;
            wrote |= MichaelLataibBarrageVfxClient.H.star(buffer, matrix, p, 0.012F + h2 * 0.032F, r, g, b, alpha * 0.25F * twinkle);
         }

         return wrote;
      }

      private MichaelLataibBarrageVfxClient.Portal portal(int i, Vec3 caster, Vec3 target, MichaelLataibBarrageVfxClient.Basis basis, float t) {
         float h0 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 100 + i * 71);
         float h1 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 200 + i * 79);
         float h2 = MichaelLataibBarrageVfxClient.H.hash(this.seed, 300 + i * 83);
         double row = i % 7;
         double col = i / 7;
         double sideSpacing = 3.7 + this.radius * 0.145;
         double heightSpacing = 2.85 + this.radius * 0.065;
         double depthSpacing = 1.55 + this.radius * 0.045;
         double side = (row - 3.0) * sideSpacing + (h0 - 0.5) * 1.35;
         double height = -0.48 + col * heightSpacing + ((i & 1) == 0 ? 0.72 : -0.28) + (h1 - 0.5) * 0.85;
         double back = 5.6 + h2 * 4.85 + col * depthSpacing;
         Vec3 pos = caster.subtract(basis.forward.scale(back)).add(basis.right.scale(side)).add(basis.up.scale(height));
         Vec3 normal = target.subtract(pos);
         if (normal.lengthSqr() < 0.001) {
            normal = basis.forward;
         }

         normal = normal.normalize();
         MichaelLataibBarrageVfxClient.Basis portalBasis = MichaelLataibBarrageVfxClient.H.basis(normal);
         double spin = h0 * (float) (Math.PI * 2) + t * (0.01 + h1 * 0.02) * ((i & 1) == 0 ? 1.0 : -1.0);
         Vec3 sideAxis = portalBasis.right.scale(Math.cos(spin)).add(portalBasis.up.scale(Math.sin(spin))).normalize();
         Vec3 upAxis = sideAxis.cross(normal).normalize();
         double portalRadius = 0.56 + h2 * 0.52 + Math.sin(t * (0.055 + h1 * 0.025) + i) * 0.025;
         return new MichaelLataibBarrageVfxClient.Portal(pos, normal, sideAxis, upAxis, portalRadius);
      }
   }

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private static final class H {
      public static BufferBuilder begin() {
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
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

      public static Vec3 entityCenter(Entity entity, float partial, double heightScale) {
         return entity.getPosition(partial).add(0.0, entity.getBbHeight() * heightScale, 0.0);
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

      public static MichaelLataibBarrageVfxClient.Basis basis(Vec3 forward) {
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
         return new MichaelLataibBarrageVfxClient.Basis(forward, right, up);
      }

      public static MichaelLataibBarrageVfxClient.Basis spinningBasis(Vec3 forward, MichaelLataibBarrageVfxClient.Basis fallback, double angle) {
         MichaelLataibBarrageVfxClient.Basis base = basis(forward);
         Vec3 right = base.right.scale(Math.cos(angle)).add(base.up.scale(Math.sin(angle))).normalize();
         Vec3 up = right.cross(base.forward);
         if (up.lengthSqr() < 0.001) {
            up = fallback.up;
         }

         up = up.normalize();
         return new MichaelLataibBarrageVfxClient.Basis(base.forward, right, up);
      }

      public static boolean portalRim(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, Vec3 normal, Vec3 side, Vec3 up, double radius, long seed, int portal, float time, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         int segments = 76;

         for (int layer = 0; layer < 7; layer++) {
            double layerBase = radius * (0.84 + layer * 0.045);
            float width = 0.018F + layer * 0.006F;
            float la = alpha * (0.34F + layer * 0.055F);
            int r = layer < 2 ? 130 : (layer < 5 ? 190 : 255);
            int g = layer < 2 ? 16 : (layer < 5 ? 36 : 78);
            int b = 255;

            for (int s = 0; s < segments; s++) {
               float hs = hash(seed, portal * 4000 + layer * 541 + s * 17);
               if (!(hs < 0.07F) || layer >= 3) {
                  double wobble0 = Math.sin(time * (0.06 + hs * 0.035) + s * 0.79 + portal) * (0.02 + hs * 0.05);
                  double wobble1 = Math.sin(time * (0.064 + hs * 0.035) + (s + 1) * 0.79 + portal) * (0.02 + hs * 0.05);
                  double a0 = s * (float) (Math.PI * 2) / segments;
                  double a1 = (s + 1) * (float) (Math.PI * 2) / segments;
                  Vec3 p0 = c.add(side.scale(Math.cos(a0) * (layerBase + wobble0)))
                     .add(up.scale(Math.sin(a0) * (layerBase + wobble0)))
                     .add(normal.scale((hs - 0.5) * 0.035));
                  Vec3 p1 = c.add(side.scale(Math.cos(a1) * (layerBase + wobble1)))
                     .add(up.scale(Math.sin(a1) * (layerBase + wobble1)))
                     .add(normal.scale((hash(seed, portal * 4100 + layer * 557 + s * 19) - 0.5) * 0.035));
                  wrote |= ribbon(buffer, matrix, p0, p1, width, r, g, b, la * (0.7F + hs * 0.3F));
               }
            }
         }

         for (int layer = 0; layer < 3; layer++) {
            double r0 = radius * (1.07 + layer * 0.075 + Math.sin(time * 0.035 + portal + layer) * 0.025);
            wrote |= ring(
               buffer,
               matrix,
               c.add(normal.scale(-0.02 - layer * 0.01)),
               side,
               up,
               r0,
               0.006F + layer * 0.004F,
               layer == 0 ? 255 : 166,
               layer == 0 ? 62 : 30,
               255,
               alpha * (0.28F - layer * 0.045F),
               88
            );
         }

         return wrote;
      }

      public static boolean portalVoidSparkles(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, Vec3 normal, Vec3 side, Vec3 up, double radius, long seed, int portal, float time, float alpha
      ) {
         boolean wrote = false;

         for (int i = 0; i < 13; i++) {
            float h0 = hash(seed, portal * 2000 + i * 61);
            float h1 = hash(seed, portal * 2100 + i * 67);
            double a = h0 * (float) (Math.PI * 2) + time * (0.018 + h1 * 0.025) * ((i & 1) == 0 ? 1.0 : -1.0);
            double d = Math.sqrt(h1) * radius * 0.68;
            Vec3 p = c.add(side.scale(Math.cos(a) * d)).add(up.scale(Math.sin(a) * d)).add(normal.scale((hash(seed, portal * 2200 + i * 71) - 0.5) * 0.1));
            float twinkle = 0.5F + 0.5F * Mth.sin(time * (0.14F + h0 * 0.08F) + i);
            wrote |= star(buffer, matrix, p, 0.012F + h1 * 0.036F, i % 3 == 0 ? 255 : 152, i % 3 == 0 ? 68 : 82, 255, alpha * 0.5F * twinkle);
         }

         return wrote;
      }

      public static boolean portalImpactDust(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelLataibBarrageVfxClient.Basis basis, float size, long seed, int index, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < 12; i++) {
            float h0 = hash(seed, index * 2600 + i * 53);
            float h1 = hash(seed, index * 2700 + i * 59);
            double a = h0 * (float) (Math.PI * 2);
            double r = size * (0.7 + h1 * 2.0);
            Vec3 p = center.add(basis.right.scale(Math.cos(a) * r)).add(basis.up.scale(Math.sin(a) * r));
            wrote |= blockCube(buffer, matrix, p, basis, size * (0.18F + h1 * 0.22F), 126, 38, 192, alpha * (0.4F + h0 * 0.35F));
            wrote |= star(
               buffer, matrix, p.add(basis.forward.scale(0.05)), size * (0.08F + h0 * 0.12F), i % 2 == 0 ? 255 : 150, i % 2 == 0 ? 66 : 88, 255, alpha * 0.5F
            );
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
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = c.add(side.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
            Vec3 p1 = c.add(side.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
            wrote |= ribbon(buffer, matrix, p0, p1, width, r, g, bl, alpha);
         }

         return wrote;
      }

      public static boolean afterImage(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, float width, int r, int g, int bl, float alpha) {
         return alpha <= 0.001F ? false : ribbon(buffer, matrix, a, b, width, r, g, bl, alpha);
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
         Vec3 f = new Vec3(0.0, 0.0, size * 0.35);
         tri(buffer, matrix, center.add(up), center.add(right.scale(0.22)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(right), center.add(up.scale(-0.22)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(-1.0)), center.add(right.scale(-0.22)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(right.scale(-1.0)), center.add(up.scale(0.22)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(0.45)), center.add(right.scale(0.45)), center.add(up.scale(-0.45)), r, g, bl, alpha * 0.42F);
         tri(buffer, matrix, center.add(up.scale(-0.45)), center.add(right.scale(-0.45)), center.add(up.scale(0.45)), r, g, bl, alpha * 0.42F);
         return true;
      }

      public static boolean blockCluster(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 c,
         MichaelLataibBarrageVfxClient.Basis basis,
         float size,
         int gray,
         int purple,
         float alpha,
         long seed,
         int index
      ) {
         boolean wrote = false;
         wrote |= blockCube(buffer, matrix, c, basis, size * 0.92F, gray, gray - 8, gray + 8, alpha);

         for (int i = 0; i < 4; i++) {
            float h0 = hash(seed, index * 120 + i * 17);
            float h1 = hash(seed, index * 130 + i * 19);
            float h2 = hash(seed, index * 140 + i * 23);
            Vec3 off = basis.right
               .scale((h0 - 0.5) * size * 0.95)
               .add(basis.up.scale((h1 - 0.5) * size * 0.95))
               .add(basis.forward.scale((h2 - 0.5) * size * 0.95));
            wrote |= blockCube(buffer, matrix, c.add(off), basis, size * (0.3F + h2 * 0.28F), Math.max(20, gray - 22), gray - 16, purple, alpha * 0.72F);
         }

         return wrote;
      }

      public static boolean blockCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, MichaelLataibBarrageVfxClient.Basis basis, float size, int r, int g, int bl, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         double s = size * 0.5;
         Vec3 f = basis.forward.scale(s);
         Vec3 right = basis.right.scale(s);
         Vec3 up = basis.up.scale(s);
         Vec3 p000 = c.subtract(right).subtract(up).subtract(f);
         Vec3 p001 = c.subtract(right).subtract(up).add(f);
         Vec3 p010 = c.subtract(right).add(up).subtract(f);
         Vec3 p011 = c.subtract(right).add(up).add(f);
         Vec3 p100 = c.add(right).subtract(up).subtract(f);
         Vec3 p101 = c.add(right).subtract(up).add(f);
         Vec3 p110 = c.add(right).add(up).subtract(f);
         Vec3 p111 = c.add(right).add(up).add(f);
         face(buffer, matrix, p001, p101, p111, p011, r, g, bl, alpha * 0.46F);
         face(buffer, matrix, p100, p000, p010, p110, Math.max(0, r - 22), Math.max(0, g - 18), Math.max(0, bl - 14), alpha * 0.34F);
         face(buffer, matrix, p000, p001, p011, p010, Math.max(0, r - 34), Math.max(0, g - 28), bl, alpha * 0.36F);
         face(buffer, matrix, p101, p100, p110, p111, Math.min(255, r + 20), Math.min(255, g + 14), Math.min(255, bl + 20), alpha * 0.42F);
         face(buffer, matrix, p010, p011, p111, p110, Math.min(255, r + 32), Math.min(255, g + 18), Math.min(255, bl + 28), alpha * 0.5F);
         face(buffer, matrix, p000, p100, p101, p001, Math.max(0, r - 44), Math.max(0, g - 32), Math.max(0, bl - 22), alpha * 0.3F);
         return true;
      }

      public static boolean rectBlock(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, MichaelLataibBarrageVfxClient.Basis basis, float width, float length, int r, int g, int bl, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         double sw = width * 0.5;
         double sl = length * 0.5;
         Vec3 f = basis.forward.scale(sl);
         Vec3 right = basis.right.scale(sw);
         Vec3 up = basis.up.scale(sw * 0.72);
         Vec3 p000 = c.subtract(right).subtract(up).subtract(f);
         Vec3 p001 = c.subtract(right).subtract(up).add(f);
         Vec3 p010 = c.subtract(right).add(up).subtract(f);
         Vec3 p011 = c.subtract(right).add(up).add(f);
         Vec3 p100 = c.add(right).subtract(up).subtract(f);
         Vec3 p101 = c.add(right).subtract(up).add(f);
         Vec3 p110 = c.add(right).add(up).subtract(f);
         Vec3 p111 = c.add(right).add(up).add(f);
         face(buffer, matrix, p001, p101, p111, p011, r, g, bl, alpha * 0.48F);
         face(buffer, matrix, p100, p000, p010, p110, Math.max(0, r - 34), Math.max(0, g - 30), bl, alpha * 0.33F);
         face(buffer, matrix, p000, p001, p011, p010, Math.max(0, r - 20), g, bl, alpha * 0.36F);
         face(buffer, matrix, p101, p100, p110, p111, Math.min(255, r + 24), Math.min(255, g + 12), Math.min(255, bl + 18), alpha * 0.45F);
         face(buffer, matrix, p010, p011, p111, p110, Math.min(255, r + 30), Math.min(255, g + 18), Math.min(255, bl + 24), alpha * 0.52F);
         face(buffer, matrix, p000, p100, p101, p001, Math.max(0, r - 48), Math.max(0, g - 36), Math.max(0, bl - 22), alpha * 0.31F);
         return true;
      }

      public static boolean shardBlock(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         Vec3 dir,
         MichaelLataibBarrageVfxClient.Basis basis,
         float length,
         float width,
         int r,
         int g,
         int bl,
         float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 tip = center.add(dir.scale(length * 0.55));
         Vec3 back = center.subtract(dir.scale(length * 0.45));
         Vec3 r0 = basis.right.scale(width);
         Vec3 u0 = basis.up.scale(width * 0.72);
         tri(buffer, matrix, tip, back.add(r0), back.add(u0), r, g, bl, alpha * 0.8F);
         tri(buffer, matrix, tip, back.add(u0), back.subtract(r0), Math.max(0, r - 22), g, bl, alpha * 0.7F);
         tri(buffer, matrix, tip, back.subtract(r0), back.subtract(u0), Math.max(0, r - 44), Math.max(0, g - 24), bl, alpha * 0.62F);
         tri(buffer, matrix, tip, back.subtract(u0), back.add(r0), Math.min(255, r + 20), Math.min(255, g + 12), Math.min(255, bl + 16), alpha * 0.76F);
         return true;
      }

      public static boolean wireCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, MichaelLataibBarrageVfxClient.Basis basis, float size, int r, int g, int bl, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         double s = size * 0.5;
         Vec3 f = basis.forward.scale(s);
         Vec3 right = basis.right.scale(s);
         Vec3 up = basis.up.scale(s);
         Vec3[] p = new Vec3[]{
            c.subtract(right).subtract(up).subtract(f),
            c.subtract(right).subtract(up).add(f),
            c.subtract(right).add(up).subtract(f),
            c.subtract(right).add(up).add(f),
            c.add(right).subtract(up).subtract(f),
            c.add(right).subtract(up).add(f),
            c.add(right).add(up).subtract(f),
            c.add(right).add(up).add(f)
         };
         float w = size * 0.024F;
         boolean wrote = false;
         wrote |= ribbon(buffer, matrix, p[0], p[1], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[2], p[3], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[4], p[5], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[6], p[7], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[0], p[2], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[1], p[3], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[4], p[6], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[5], p[7], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[0], p[4], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[1], p[5], w, r, g, bl, alpha);
         wrote |= ribbon(buffer, matrix, p[2], p[6], w, r, g, bl, alpha);
         return wrote | ribbon(buffer, matrix, p[3], p[7], w, r, g, bl, alpha);
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
