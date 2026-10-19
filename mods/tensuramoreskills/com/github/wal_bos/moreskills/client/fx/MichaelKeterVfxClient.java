package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.net.msg.MichaelKeterVfxPayload;
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
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class MichaelKeterVfxClient {
   private static final List<MichaelKeterVfxClient.Keter> ACTIVE = new CopyOnWriteArrayList<>();
   private static boolean initialized;
   private static long lastKeterEruptionShakeMillis = -999999L;

   private MichaelKeterVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MichaelKeterVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(MichaelKeterVfxClient::render);
      }
   }

   public static void play(MichaelKeterVfxPayload payload) {
      init();
      if (payload.vfxType() != 0) {
         for (MichaelKeterVfxClient.Keter active : ACTIVE) {
            if (active.sameBeam(payload)) {
               active.refreshBeam(payload);
               return;
            }
         }

         ACTIVE.removeIf(activex -> activex.isChargeFrom(payload.casterId()));
      }

      ACTIVE.add(new MichaelKeterVfxClient.Keter(payload));

      while (ACTIVE.size() > 20) {
         ACTIVE.remove(0);
      }
   }

   private static void tick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         for (MichaelKeterVfxClient.Keter k : ACTIVE) {
            k.age++;
            if (k.deadByClientTime()) {
               ACTIVE.remove(k);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES || event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && !ACTIVE.isEmpty()) {
            Vec3 cam = MichaelKeterVfxClient.H.camera();
            Matrix4f matrix = event.getPoseStack().last().pose();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            BufferBuilder buffer = MichaelKeterVfxClient.H.begin();
            boolean wrote = false;

            for (MichaelKeterVfxClient.Keter k : ACTIVE) {
               wrote |= k.render(mc, buffer, matrix, cam, partial);
            }

            MichaelKeterVfxClient.H.finish(buffer, wrote);
         }
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

      public static float smooth(double x) {
         x = Math.max(0.0, Math.min(1.0, x));
         return (float)(x * x * (3.0 - 2.0 * x));
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

      public static MichaelKeterVfxClient.Basis basis(Vec3 forward) {
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
         return new MichaelKeterVfxClient.Basis(forward, right, up);
      }

      public static boolean beamRibbonCross(
         BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 dir, float width, int r, int g, int b, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 ref = Math.abs(dir.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         Vec3 side = dir.cross(ref);
         if (side.lengthSqr() < 0.001) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize().scale(width);
         Vec3 up = dir.cross(side);
         if (up.lengthSqr() < 0.001) {
            up = new Vec3(0.0, width, 0.0);
         }

         up = up.normalize().scale(width);
         boolean wrote = false;
         wrote |= quad(buffer, matrix, start.add(side), end.add(side), end.subtract(side), start.subtract(side), r, g, b, alpha);
         wrote |= quad(buffer, matrix, start.add(up), end.add(up), end.subtract(up), start.subtract(up), r, g, b, alpha * 0.96F);
         wrote |= quad(
            buffer,
            matrix,
            start.add(side.scale(0.7)).add(up.scale(0.7)),
            end.add(side.scale(0.7)).add(up.scale(0.7)),
            end.subtract(side.scale(0.7)).subtract(up.scale(0.7)),
            start.subtract(side.scale(0.7)).subtract(up.scale(0.7)),
            r,
            g,
            b,
            alpha * 0.52F
         );
         return wrote
            | quad(
               buffer,
               matrix,
               start.add(side.scale(0.7)).subtract(up.scale(0.7)),
               end.add(side.scale(0.7)).subtract(up.scale(0.7)),
               end.subtract(side.scale(0.7)).add(up.scale(0.7)),
               start.subtract(side.scale(0.7)).add(up.scale(0.7)),
               r,
               g,
               b,
               alpha * 0.52F
            );
      }

      public static boolean softBloom(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 normal, float radius, int r, int g, int b, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         MichaelKeterVfxClient.Basis basis = basis(normal.lengthSqr() < 0.001 ? new Vec3(0.0, 0.0, 1.0) : normal);
         Vec3 right = basis.right.scale(radius);
         Vec3 up = basis.up.scale(radius);
         Vec3 rightSmall = basis.right.scale(radius * 0.34);
         Vec3 upSmall = basis.up.scale(radius * 0.34);
         boolean wrote = false;
         wrote |= tri(buffer, matrix, center.add(up), center.add(rightSmall), center.add(right.scale(0.1)), r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.add(right), center.add(upSmall.scale(-1.0)), center.add(up.scale(0.1)), r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.subtract(up), center.subtract(rightSmall), center.subtract(right.scale(0.1)), r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.subtract(right), center.add(upSmall), center.subtract(up.scale(0.1)), r, g, b, alpha);
         wrote |= tri(
            buffer,
            matrix,
            center.add(up.scale(0.64)).add(right.scale(0.64)),
            center,
            center.add(up.scale(-0.64)).add(right.scale(-0.64)),
            r,
            g,
            b,
            alpha * 0.42F
         );
         return wrote
            | tri(
               buffer,
               matrix,
               center.add(up.scale(0.64)).add(right.scale(-0.64)),
               center,
               center.add(up.scale(-0.64)).add(right.scale(0.64)),
               r,
               g,
               b,
               alpha * 0.42F
            );
      }

      public static boolean sparkDot(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int b, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 right = new Vec3(size, 0.0, 0.0);
         Vec3 up = new Vec3(0.0, size, 0.0);
         return quad(buffer, matrix, center.add(up), center.add(right), center.subtract(up), center.subtract(right), r, g, b, alpha);
      }

      public static boolean star(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int b, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 right = new Vec3(size, 0.0, 0.0);
         Vec3 up = new Vec3(0.0, size, 0.0);
         Vec3 f = new Vec3(0.0, 0.0, size * 0.3);
         boolean wrote = false;
         wrote |= tri(buffer, matrix, center.add(up), center.add(right.scale(0.22)), center.add(f), r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.add(right), center.add(up.scale(-0.22)), center.add(f), r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.subtract(up), center.subtract(right.scale(0.22)), center.add(f), r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.subtract(right), center.add(up.scale(0.22)), center.add(f), r, g, b, alpha);
         wrote |= tri(
            buffer,
            matrix,
            center.add(up.scale(0.46)).add(right.scale(0.46)),
            center.add(up.scale(-0.46)),
            center.add(right.scale(-0.46)),
            r,
            g,
            b,
            alpha * 0.44F
         );
         return wrote
            | tri(
               buffer,
               matrix,
               center.add(up.scale(-0.46)).add(right.scale(0.46)),
               center.add(up.scale(0.46)),
               center.add(right.scale(-0.46)),
               r,
               g,
               b,
               alpha * 0.44F
            );
      }

      public static Vec3 lockToGround(Minecraft mc, Vec3 point) {
         if (mc != null && mc.level != null) {
            int x = Mth.floor(point.x);
            int z = Mth.floor(point.z);
            int y = mc.level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            if (y <= mc.level.getMinBuildHeight()) {
               MutableBlockPos pos = new MutableBlockPos(x, Mth.floor(point.y), z);

               while (pos.getY() > mc.level.getMinBuildHeight() && mc.level.getBlockState(pos).isAir()) {
                  pos.move(0, -1, 0);
               }

               y = pos.getY() + 1;
            }

            return new Vec3(point.x, y + 0.08, point.z);
         } else {
            return point;
         }
      }

      public static boolean bigStar(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float size, int r, int g, int b, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 up = basis.up.scale(size);
         Vec3 right = basis.right.scale(size);
         Vec3 f = basis.forward.scale(size * 0.35);
         Vec3 upLong = basis.up.scale(size * 1.92);
         Vec3 rightLong = basis.right.scale(size * 1.92);
         boolean wrote = false;
         wrote |= tri(buffer, matrix, center.add(upLong), center.add(right.scale(0.2)), center.add(f), r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.add(rightLong), center.subtract(up.scale(0.2)), center.add(f), r, g, b, alpha * 0.95F);
         wrote |= tri(buffer, matrix, center.subtract(upLong), center.subtract(right.scale(0.2)), center.add(f), r, g, b, alpha * 0.95F);
         wrote |= tri(buffer, matrix, center.subtract(rightLong), center.add(up.scale(0.2)), center.add(f), r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.add(up).add(right), center.add(up.scale(-0.58)), center.add(right.scale(-0.58)), r, g, b, alpha * 0.42F);
         return wrote
            | tri(buffer, matrix, center.add(up.scale(-1.0)).add(right), center.add(up.scale(0.58)), center.add(right.scale(-0.58)), r, g, b, alpha * 0.42F);
      }

      public static boolean texturedBeamShell(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 start,
         Vec3 end,
         Vec3 dir,
         float radius,
         int r,
         int g,
         int b,
         float alpha,
         float time,
         long seed,
         int layer
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         MichaelKeterVfxClient.Basis basis = basis(dir);
         double length = start.distanceTo(end);
         int slices = 22;
         int stacks = 16;
         boolean wrote = false;

         for (int y = 0; y < stacks; y++) {
            double v0 = (double)y / stacks;
            double v1 = (double)(y + 1) / stacks;
            Vec3 c0 = start.add(dir.scale(length * v0));
            Vec3 c1 = start.add(dir.scale(length * v1));
            double twist0 = time * (0.018 + layer * 0.004) + v0 * (float) (Math.PI * 2) * (1.15 + layer * 0.16) + layer * 0.77;
            double twist1 = time * (0.018 + layer * 0.004) + v1 * (float) (Math.PI * 2) * (1.15 + layer * 0.16) + layer * 0.77;

            for (int s = 0; s < slices; s++) {
               double u0 = (double)s / slices;
               double u1 = (double)(s + 1) / slices;
               float h0 = hash(seed, 50000 + layer * 900 + y * 37 + s * 11);
               float h1 = hash(seed, 52000 + layer * 900 + y * 41 + s * 13);
               double a0 = u0 * (float) (Math.PI * 2) + twist0;
               double a1 = u1 * (float) (Math.PI * 2) + twist1;
               double n0 = 0.88 + Math.sin(u0 * (float) (Math.PI * 2) * 5.0 + v0 * 8.0 + time * 0.055 + h0 * 6.28) * 0.1 + (h0 - 0.5) * 0.1;
               double n1 = 0.88 + Math.sin(u1 * (float) (Math.PI * 2) * 5.0 + v1 * 8.0 + time * 0.055 + h1 * 6.28) * 0.1 + (h1 - 0.5) * 0.1;
               Vec3 p00 = c0.add(basis.right.scale(Math.cos(a0) * radius * n0)).add(basis.up.scale(Math.sin(a0) * radius * n0));
               Vec3 p01 = c1.add(basis.right.scale(Math.cos(a0) * radius * n0)).add(basis.up.scale(Math.sin(a0) * radius * n0));
               Vec3 p11 = c1.add(basis.right.scale(Math.cos(a1) * radius * n1)).add(basis.up.scale(Math.sin(a1) * radius * n1));
               Vec3 p10 = c0.add(basis.right.scale(Math.cos(a1) * radius * n1)).add(basis.up.scale(Math.sin(a1) * radius * n1));
               float seam = 0.48F + 0.52F * Mth.sin((float)(u0 * (float) (Math.PI * 2) * 7.0 + v0 * 13.0 + time * 0.09 + layer));
               wrote |= quad(buffer, matrix, p00, p01, p11, p10, r, g, b, alpha * (0.52F + seam * 0.38F));
            }
         }

         return wrote;
      }

      public static boolean beamMuzzleDisk(BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 normal, float radius, int r, int g, int b, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         MichaelKeterVfxClient.Basis basis = basis(normal);
         boolean wrote = false;
         int slices = 56;

         for (int i = 0; i < slices; i++) {
            double a0 = i * (float) (Math.PI * 2) / slices;
            double a1 = (i + 1) * (float) (Math.PI * 2) / slices;
            Vec3 p0 = center.add(basis.right.scale(Math.cos(a0) * radius)).add(basis.up.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(basis.right.scale(Math.cos(a1) * radius)).add(basis.up.scale(Math.sin(a1) * radius));
            wrote |= tri(buffer, matrix, center, p0, p1, r, g, b, alpha * (0.28F + 0.22F * Mth.sin(i * 0.6F)));
         }

         return wrote;
      }

      public static boolean diamond(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float size, int r, int g, int b, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 up = basis.up.scale(size);
         Vec3 right = basis.right.scale(size * 0.72);
         boolean wrote = false;
         wrote |= tri(buffer, matrix, center.add(up), center.add(right), center, r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.add(right), center.subtract(up), center, r, g, b, alpha * 0.76F);
         wrote |= tri(buffer, matrix, center.subtract(up), center.subtract(right), center, r, g, b, alpha * 0.76F);
         return wrote | tri(buffer, matrix, center.subtract(right), center.add(up), center, r, g, b, alpha);
      }

      public static boolean orientedRing(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         double inner,
         double outer,
         double twist,
         int r,
         int g,
         int b,
         float alpha
      ) {
         if (!(alpha <= 0.001F) && !(outer <= inner)) {
            boolean wrote = false;
            int slices = 96;

            for (int i = 0; i < slices; i++) {
               double a0 = i * (float) (Math.PI * 2) / slices + twist;
               double a1 = (i + 1) * (float) (Math.PI * 2) / slices + twist;
               float pulse0 = 0.82F + 0.18F * Mth.sin((float)(i * 0.46 + twist));
               float pulse1 = 0.82F + 0.18F * Mth.sin((float)((i + 1) * 0.46 + twist));
               Vec3 p0 = center.add(basis.right.scale(Math.cos(a0) * inner)).add(basis.up.scale(Math.sin(a0) * inner));
               Vec3 p1 = center.add(basis.right.scale(Math.cos(a1) * inner)).add(basis.up.scale(Math.sin(a1) * inner));
               Vec3 p2 = center.add(basis.right.scale(Math.cos(a1) * outer)).add(basis.up.scale(Math.sin(a1) * outer));
               Vec3 p3 = center.add(basis.right.scale(Math.cos(a0) * outer)).add(basis.up.scale(Math.sin(a0) * outer));
               wrote |= quad(buffer, matrix, p0, p1, p2, p3, r, g, b, alpha * 0.5F * (pulse0 + pulse1));
            }

            return wrote;
         } else {
            return false;
         }
      }

      public static boolean orientedDiamond(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float size, float roll, int r, int g, int b, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 axisA = basis.right.scale(Math.cos(roll)).add(basis.up.scale(Math.sin(roll))).normalize();
         Vec3 axisB = basis.right.scale(Math.cos(roll + (float) (Math.PI / 2))).add(basis.up.scale(Math.sin(roll + (float) (Math.PI / 2)))).normalize();
         Vec3 up = axisA.scale(size);
         Vec3 right = axisB.scale(size * 0.72);
         boolean wrote = false;
         wrote |= tri(buffer, matrix, center.add(up), center.add(right), center, r, g, b, alpha);
         wrote |= tri(buffer, matrix, center.add(right), center.subtract(up), center, r, g, b, alpha * 0.76F);
         wrote |= tri(buffer, matrix, center.subtract(up), center.subtract(right), center, r, g, b, alpha * 0.76F);
         return wrote | tri(buffer, matrix, center.subtract(right), center.add(up), center, r, g, b, alpha);
      }

      public static boolean quad(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, float alpha) {
         boolean wrote = false;
         wrote |= tri(buffer, matrix, a, b, c, r, g, bl, alpha);
         return wrote | tri(buffer, matrix, c, d, a, r, g, bl, alpha);
      }

      public static boolean tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
         int aa = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         if (aa <= 0) {
            return false;
         }

         buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(r, g, bl, aa);
         buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(r, g, bl, aa);
         buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(r, g, bl, aa);
         return true;
      }
   }

   private static final class Keter {
      private final byte type;
      private final int casterId;
      private final Vec3 fallbackA;
      private Vec3 fallbackB;
      private float range;
      private float power;
      private int life;
      private boolean eruptionShakeTriggered;
      private boolean finalExplosionShakeTriggered;
      private Vec3 finalExplosionWorld;
      private MichaelKeterVfxClient.Basis finalExplosionBasis;
      private Vec3 lastBeamEndWorld;
      private MichaelKeterVfxClient.Basis lastBeamBasis;
      private final long seed;
      private final long createdMillis;
      private int age;

      private Keter(MichaelKeterVfxPayload payload) {
         this.type = payload.vfxType();
         this.casterId = payload.casterId();
         this.fallbackA = new Vec3(payload.x(), payload.y(), payload.z());
         this.fallbackB = new Vec3(payload.x2(), payload.y2(), payload.z2());
         this.range = payload.range();
         this.power = payload.power();
         this.life = Math.max(this.type == 0 ? 10 : 18, payload.duration());
         this.seed = payload.seed();
         this.createdMillis = Util.getMillis();
      }

      private int afterLife() {
         return this.type == 0 ? 0 : 190;
      }

      private boolean isChargeFrom(int id) {
         return this.type == 0 && this.casterId == id;
      }

      private boolean sameBeam(MichaelKeterVfxPayload payload) {
         return this.type != 0 && payload.vfxType() != 0 && this.casterId == payload.casterId();
      }

      private void refreshBeam(MichaelKeterVfxPayload payload) {
         this.fallbackB = new Vec3(payload.x2(), payload.y2(), payload.z2());
         this.range = payload.range();
         this.power = payload.power();
         this.life = Math.max(this.life, (int)Math.ceil(this.clientAgeTicks()) + Math.max(18, payload.duration()));
      }

      private double clientAgeTicks() {
         return Math.max(0.0, (Util.getMillis() - this.createdMillis) / 50.0);
      }

      private boolean deadByClientTime() {
         return this.clientAgeTicks() > this.life + this.afterLife();
      }

      private boolean render(Minecraft mc, BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         Entity caster = this.casterId == -1 ? null : mc.level.getEntity(this.casterId);
         Vec3 start = caster == null ? this.fallbackA : MichaelKeterVfxClient.H.entityCenter(caster, partial, 0.76);
         Vec3 dir = caster == null ? this.fallbackB.subtract(this.fallbackA) : caster.getLookAngle();
         if (dir.lengthSqr() < 0.001) {
            dir = new Vec3(0.0, 0.0, 1.0);
         }

         dir = dir.normalize();
         Vec3 end = caster == null ? this.fallbackB : start.add(dir.scale(this.range));
         MichaelKeterVfxClient.Basis basis = MichaelKeterVfxClient.H.basis(dir);
         Vec3 rs = MichaelKeterVfxClient.H.rel(start, cam);
         Vec3 re = MichaelKeterVfxClient.H.rel(end, cam);
         float t = (float)this.clientAgeTicks();
         return this.type == 0 ? this.renderCharge(buffer, matrix, rs, basis, t) : this.renderBeam(mc, buffer, matrix, rs, re, end, cam, basis, t);
      }

      private boolean renderCharge(BufferBuilder buffer, Matrix4f matrix, Vec3 start, MichaelKeterVfxClient.Basis basis, float t) {
         float progress = Mth.clamp(this.power / 50.0F, 0.0F, 1.0F);
         float appear = MichaelKeterVfxClient.H.smooth(Mth.clamp(progress / 0.18F, 0.0F, 1.0F));
         float pull = MichaelKeterVfxClient.H.smooth(Mth.clamp((progress - 0.18F) / 0.6F, 0.0F, 1.0F));
         float vanish = MichaelKeterVfxClient.H.smooth(Mth.clamp((progress - 0.78F) / 0.12F, 0.0F, 1.0F));
         float silent = MichaelKeterVfxClient.H.smooth(Mth.clamp((progress - 0.9F) / 0.07F, 0.0F, 1.0F));
         float fade = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, (this.life - t) / 5.0F));
         float visible = appear * fade * (1.0F - vanish) * (1.0F - silent);
         if (visible <= 0.002F) {
            return false;
         }

         boolean wrote = false;
         Vec3 focus = start.add(basis.up.scale(0.06));
         double field = 9.0 + appear * 7.0;
         float alpha = visible * (0.42F + pull * 0.36F);
         int particles = 260 + (int)(pull * 230.0F);

         for (int i = 0; i < particles; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 1000 + i * 31);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 2000 + i * 37);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 3000 + i * 41);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 4000 + i * 43);
            float h4 = MichaelKeterVfxClient.H.hash(this.seed, 5000 + i * 47);
            float h5 = MichaelKeterVfxClient.H.hash(this.seed, 6000 + i * 53);
            double yaw = h0 * (float) (Math.PI * 2) + h5 * 0.35;
            double pitch = (h1 - 0.5) * Math.PI * 1.16;
            double sourceShell = field * (0.4 + h2 * 1.1);
            double personalDelay = h4 * 0.22;
            float localPull = MichaelKeterVfxClient.H.smooth(Mth.clamp((pull - personalDelay) / 0.78F, 0.0, 1.0));
            localPull = Math.max(localPull, vanish * (0.92F + h5 * 0.06F));
            double inward = Math.max(0.015, 1.0 - localPull * (0.975 + h5 * 0.02));
            inward *= Math.max(0.01, 1.0 - vanish * (0.9 + h3 * 0.08));
            Vec3 far = basis.forward
               .scale(Math.cos(pitch) * Math.cos(yaw) * sourceShell)
               .add(basis.right.scale(Math.sin(yaw) * Math.cos(pitch) * sourceShell))
               .add(basis.up.scale(Math.sin(pitch) * sourceShell));
            Vec3 spiral = basis.right
               .scale(Math.sin(t * 0.055 + i * 0.77) * sourceShell * 0.055 * (1.0 - localPull))
               .add(basis.up.scale(Math.cos(t * 0.05 + i * 0.69) * sourceShell * 0.045 * (1.0 - localPull)))
               .add(basis.forward.scale(Math.sin(t * 0.04 + i) * 0.18 * (1.0 - vanish)));
            Vec3 p = focus.add(far.scale(inward)).add(spiral);
            int palette = i % 7;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 155 : (palette == 4 ? 255 : (palette == 5 ? 88 : 255)))));
            int g = palette == 0 ? 78 : (palette == 1 ? 232 : (palette == 2 ? 58 : (palette == 3 ? 44 : (palette == 4 ? 122 : (palette == 5 ? 205 : 142)))));
            int b = palette == 4 ? 178 : (palette == 6 ? 214 : 255);
            float twinkle = 0.74F + 0.26F * (0.5F + 0.5F * Mth.sin(t * (0.03F + h2 * 0.026F) + i * 1.4F));
            float size = 0.014F + h3 * 0.034F + localPull * 0.014F + vanish * 0.025F;
            wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, size, r, g, b, alpha * twinkle);
            if (pull > 0.45F && i % 19 == 0) {
               Vec3 inner = focus.add(far.scale(Math.max(0.03, inward * 0.42)));
               wrote |= MichaelKeterVfxClient.H.sparkDot(buffer, matrix, inner, size * 0.72F, 255, 94, 236, alpha * 0.56F);
            }
         }

         if (pull > 0.35F && vanish < 0.78F) {
            int collapse = 86;

            for (int i = 0; i < collapse; i++) {
               float h0 = MichaelKeterVfxClient.H.hash(this.seed, 12000 + i * 29);
               float h1 = MichaelKeterVfxClient.H.hash(this.seed, 13000 + i * 31);
               float h2 = MichaelKeterVfxClient.H.hash(this.seed, 14000 + i * 37);
               double ring = (0.22 + h1 * 1.85) * (1.0 - vanish * 0.94) * (1.0 - pull * 0.68);
               double a = h0 * (float) (Math.PI * 2) - t * (0.02 + h2 * 0.01);
               Vec3 p = focus.add(basis.right.scale(Math.cos(a) * ring))
                  .add(basis.up.scale(Math.sin(a) * ring))
                  .add(basis.forward.scale((h2 - 0.5) * 0.28 * (1.0 - vanish)));
               wrote |= MichaelKeterVfxClient.H.star(
                  buffer, matrix, p, 0.024F + h1 * 0.056F + pull * 0.016F, i % 2 == 0 ? 255 : 116, i % 2 == 0 ? 72 : 236, 255, alpha * (0.4F + pull * 0.48F)
               );
            }
         }

         return wrote;
      }

      private boolean renderBeam(
         Minecraft mc, BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, Vec3 endWorld, Vec3 cam, MichaelKeterVfxClient.Basis basis, float t
      ) {
         Vec3 dir = end.subtract(start);
         if (dir.lengthSqr() < 0.001) {
            dir = basis.forward;
         }

         dir = dir.normalize();
         MichaelKeterVfxClient.Basis beamBasis = MichaelKeterVfxClient.H.basis(dir);
         double length = start.distanceTo(end);
         float silentTicks = 8.0F;
         if (t > this.life) {
            if (this.finalExplosionWorld == null) {
               this.finalExplosionWorld = MichaelKeterVfxClient.H.lockToGround(mc, this.lastBeamEndWorld == null ? endWorld : this.lastBeamEndWorld);
               this.finalExplosionBasis = MichaelKeterVfxClient.H.basis(new Vec3(0.0, 1.0, 0.0));
            }

            return this.renderFinalStopExplosion(
               buffer,
               matrix,
               MichaelKeterVfxClient.H.rel(this.finalExplosionWorld, cam),
               this.finalExplosionBasis == null ? beamBasis : this.finalExplosionBasis,
               t - this.life
            );
         } else {
            this.lastBeamEndWorld = endWorld;
            this.lastBeamBasis = beamBasis;
            if (t < silentTicks) {
               return false;
            }

            float bt = t - silentTicks;
            if (!this.eruptionShakeTriggered) {
               this.eruptionShakeTriggered = true;
               long now = Util.getMillis();
               if (now - MichaelKeterVfxClient.lastKeterEruptionShakeMillis > 1000L) {
                  MichaelKeterVfxClient.lastKeterEruptionShakeMillis = now;
                  PathsClientState.triggerManualShake(110, 20.0F, 20.0F);
               }
            }

            float born = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, bt / 1.65F));
            float fade = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, (this.life - t) / 8.0F));
            float alpha = born * fade;
            boolean wrote = false;
            float eruption = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, bt / 1.2F))
               * (1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (bt - 9.0F) / 12.0F)));
            wrote |= this.renderStartExplosion(buffer, matrix, start, beamBasis, bt, alpha * eruption * 1.75F);
            wrote |= this.renderImpactExplosion(buffer, matrix, end, beamBasis, bt, alpha * Math.max(0.22F, eruption * 1.05F));
            wrote |= MichaelKeterVfxClient.H.texturedBeamShell(buffer, matrix, start, end, dir, 6.85F, 18, 0, 72, alpha * 0.12F, bt, this.seed, 9);
            wrote |= MichaelKeterVfxClient.H.texturedBeamShell(buffer, matrix, start, end, dir, 5.72F, 92, 0, 210, alpha * 0.16F, bt, this.seed, 10);
            wrote |= MichaelKeterVfxClient.H.texturedBeamShell(buffer, matrix, start, end, dir, 4.85F, 24, 0, 86, alpha * 0.22F, bt, this.seed, 0);
            wrote |= MichaelKeterVfxClient.H.texturedBeamShell(buffer, matrix, start, end, dir, 3.7F, 78, 12, 180, alpha * 0.35F, bt, this.seed, 1);
            wrote |= MichaelKeterVfxClient.H.texturedBeamShell(buffer, matrix, start, end, dir, 2.42F, 178, 36, 255, alpha * 0.56F, bt, this.seed, 2);
            wrote |= MichaelKeterVfxClient.H.texturedBeamShell(buffer, matrix, start, end, dir, 1.34F, 106, 232, 255, alpha * 0.78F, bt, this.seed, 3);
            wrote |= MichaelKeterVfxClient.H.texturedBeamShell(buffer, matrix, start, end, dir, 0.52F, 255, 116, 240, alpha * 1.0F, bt, this.seed, 4);
            wrote |= MichaelKeterVfxClient.H.texturedBeamShell(buffer, matrix, start, end, dir, 0.2F, 255, 224, 255, alpha * 0.92F, bt, this.seed, 11);
            wrote |= MichaelKeterVfxClient.H.beamRibbonCross(buffer, matrix, start, end, dir, 2.7F, 38, 0, 130, alpha * 0.12F);
            wrote |= MichaelKeterVfxClient.H.beamRibbonCross(buffer, matrix, start, end, dir, 1.32F, 205, 46, 255, alpha * 0.19F);
            wrote |= MichaelKeterVfxClient.H.beamRibbonCross(buffer, matrix, start, end, dir, 0.54F, 118, 236, 255, alpha * 0.3F);
            wrote |= this.renderBeamCosmicLattice(buffer, matrix, start, end, beamBasis, bt, alpha, length);
            wrote |= this.renderBeamGiantStardust(buffer, matrix, start, end, beamBasis, bt, alpha, length);
            wrote |= this.renderBeamRadialPanels(buffer, matrix, start, end, beamBasis, bt, alpha, length);
            int bandCount = 160;

            for (int i = 0; i < bandCount; i++) {
               float u = (i + 0.3F) / bandCount;
               float h0 = MichaelKeterVfxClient.H.hash(this.seed, 30000 + i * 37);
               float h1 = MichaelKeterVfxClient.H.hash(this.seed, 31000 + i * 41);
               float h2 = MichaelKeterVfxClient.H.hash(this.seed, 32000 + i * 43);
               Vec3 c = start.add(dir.scale(length * u));
               double twist = bt * 0.016 + i * 0.31 + h0 * (float) (Math.PI * 2);
               double radiusA = 0.78 + h1 * 1.92;
               double radiusB = 0.4 + h2 * 0.82;
               Vec3 side = beamBasis.right.scale(Math.cos(twist) * radiusA).add(beamBasis.up.scale(Math.sin(twist) * radiusA));
               Vec3 side2 = beamBasis.right
                  .scale(Math.cos(twist + (float) (Math.PI / 2)) * radiusB)
                  .add(beamBasis.up.scale(Math.sin(twist + (float) (Math.PI / 2)) * radiusB));
               int palette = i % 5;
               int r = palette == 0 ? 255 : (palette == 1 ? 118 : (palette == 2 ? 190 : (palette == 3 ? 255 : 92)));
               int g = palette == 0 ? 78 : (palette == 1 ? 236 : (palette == 2 ? 54 : (palette == 3 ? 126 : 204)));
               int b = 255;
               float a = alpha * (0.12F + h2 * 0.18F);
               wrote |= MichaelKeterVfxClient.H.quad(
                  buffer,
                  matrix,
                  c.add(side).add(side2),
                  c.add(side.scale(-0.6)).add(side2.scale(0.78)),
                  c.add(side.scale(-0.9)).subtract(side2),
                  c.add(side.scale(0.56)).subtract(side2.scale(0.8)),
                  r,
                  g,
                  b,
                  a
               );
               if (i % 3 == 0) {
                  wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, c.add(side.scale(0.5)), dir, 0.2F + h1 * 0.34F, r, g, b, alpha * 0.16F);
               }
            }

            int orbitCount = 760;

            for (int i = 0; i < orbitCount; i++) {
               float h0 = MichaelKeterVfxClient.H.hash(this.seed, 10000 + i * 17);
               float h1 = MichaelKeterVfxClient.H.hash(this.seed, 11000 + i * 19);
               float h2 = MichaelKeterVfxClient.H.hash(this.seed, 12000 + i * 23);
               float h3 = MichaelKeterVfxClient.H.hash(this.seed, 13000 + i * 29);
               double along = h0 * length;
               Vec3 base = start.add(dir.scale(along));
               double spin = h1 * (float) (Math.PI * 2) + bt * (0.028 + h2 * 0.024) + along * 0.072;
               double radius = 0.62 + h2 * 2.42;
               Vec3 p = base.add(beamBasis.right.scale(Math.cos(spin) * radius)).add(beamBasis.up.scale(Math.sin(spin) * radius));
               float twinkle = 0.8F + 0.2F * (0.5F + 0.5F * Mth.sin(bt * (0.044F + h3 * 0.03F) + i * 0.9F));
               int palette = i % 5;
               int r = palette == 0 ? 255 : (palette == 1 ? 120 : (palette == 2 ? 210 : (palette == 3 ? 255 : 92)));
               int g = palette == 0 ? 82 : (palette == 1 ? 238 : (palette == 2 ? 66 : (palette == 3 ? 126 : 204)));
               int b = palette == 4 ? 255 : 252;
               wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, 0.026F + h3 * 0.056F, r, g, b, alpha * 0.8F * twinkle);
            }

            int glyphs = 100;

            for (int i = 0; i < glyphs; i++) {
               float h = MichaelKeterVfxClient.H.hash(this.seed, 15000 + i * 61);
               Vec3 c = start.lerp(end, (i + 0.5) / glyphs);
               double spin = bt * 0.012 + i * 0.48;
               double radius = 1.02 + h * 1.95;
               Vec3 p = c.add(beamBasis.right.scale(Math.cos(spin) * radius)).add(beamBasis.up.scale(Math.sin(spin) * radius));
               wrote |= MichaelKeterVfxClient.H.softBloom(
                  buffer, matrix, p, dir, 0.16F + h * 0.26F, i % 2 == 0 ? 255 : 116, i % 2 == 0 ? 82 : 236, 255, alpha * 0.17F
               );
               wrote |= MichaelKeterVfxClient.H.diamond(
                  buffer, matrix, p, beamBasis, 0.1F + h * 0.2F, i % 3 == 0 ? 255 : 128, i % 3 == 0 ? 68 : 236, 255, alpha * 0.44F
               );
            }

            wrote |= MichaelKeterVfxClient.H.beamMuzzleDisk(buffer, matrix, start, dir, 11.8F + eruption * 7.2F, 255, 70, 232, alpha * 0.62F);
            wrote |= MichaelKeterVfxClient.H.beamMuzzleDisk(
               buffer, matrix, start.add(dir.scale(0.38)), dir, 6.8F + eruption * 4.8F, 116, 236, 255, alpha * 0.46F
            );
            wrote |= MichaelKeterVfxClient.H.beamMuzzleDisk(
               buffer, matrix, start.add(dir.scale(0.76)), dir, 3.4F + eruption * 2.6F, 255, 220, 255, alpha * 0.34F
            );
            return wrote | MichaelKeterVfxClient.H.beamMuzzleDisk(buffer, matrix, start.add(dir.scale(length * 0.97)), dir, 4.6F, 116, 236, 255, alpha * 0.38F);
         }
      }

      private boolean renderBeamCosmicLattice(
         BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, MichaelKeterVfxClient.Basis basis, float t, float alpha, double length
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         Vec3 dir = basis.forward;
         int nodes = 96;

         for (int i = 0; i < nodes; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 180000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 181000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 182000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 183000 + i * 29);
            double u = (i + 0.5) / nodes;
            Vec3 c = start.add(dir.scale(length * u));
            double baseSpin = u * (float) (Math.PI * 2) * 6.0 + t * 0.026 + h0 * (float) (Math.PI * 2);
            double radius = 1.15 + h1 * 3.1;
            Vec3 p0 = c.add(basis.right.scale(Math.cos(baseSpin) * radius)).add(basis.up.scale(Math.sin(baseSpin) * radius));
            Vec3 p1 = c.add(basis.right.scale(Math.cos(baseSpin + 2.094) * radius * (0.72 + h2 * 0.4)))
               .add(basis.up.scale(Math.sin(baseSpin + 2.094) * radius * (0.72 + h2 * 0.4)));
            Vec3 p2 = c.add(basis.right.scale(Math.cos(baseSpin + 4.188) * radius * (0.72 + h3 * 0.4)))
               .add(basis.up.scale(Math.sin(baseSpin + 4.188) * radius * (0.72 + h3 * 0.4)));
            int palette = i % 6;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 92 : (palette == 4 ? 255 : 160))));
            int g = palette == 0 ? 76 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 204 : (palette == 4 ? 132 : 44))));
            int b = palette == 5 ? 255 : 255;
            float a = alpha * (0.1F + h2 * 0.2F);
            wrote |= MichaelKeterVfxClient.H.tri(buffer, matrix, p0, p1, p2, r, g, b, a);
            if ((i & 1) == 0) {
               wrote |= MichaelKeterVfxClient.H.bigStar(
                  buffer, matrix, c.add(p0.subtract(c).scale(0.82)), basis, 0.13F + h1 * 0.3F, r, g, b, alpha * (0.28F + h3 * 0.25F)
               );
            }
         }

         return wrote;
      }

      private boolean renderBeamGiantStardust(
         BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, MichaelKeterVfxClient.Basis basis, float t, float alpha, double length
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         Vec3 dir = basis.forward;

         for (int i = 0; i < 84; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 184000 + i * 31);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 185000 + i * 37);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 186000 + i * 41);
            double u = h0;
            Vec3 c = start.add(dir.scale(length * u));
            double spin = h1 * (float) (Math.PI * 2) + t * (0.02 + h2 * 0.025) + u * 12.0;
            double radius = 2.25 + h2 * 3.7;
            Vec3 p = c.add(basis.right.scale(Math.cos(spin) * radius)).add(basis.up.scale(Math.sin(spin) * radius));
            int palette = i % 5;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 255 : 92)));
            int g = palette == 0 ? 72 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 128 : 204)));
            int b = 255;
            float size = 0.22F + h1 * 0.48F;
            wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size, r, g, b, alpha * (0.42F + h2 * 0.34F));
            wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, dir, size * (1.2F + h0 * 0.9F), r, g, b, alpha * 0.16F);
         }

         return wrote;
      }

      private boolean renderBeamRadialPanels(
         BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, MichaelKeterVfxClient.Basis basis, float t, float alpha, double length
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         Vec3 dir = basis.forward;

         for (int i = 0; i < 64; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 187000 + i * 43);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 188000 + i * 47);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 189000 + i * 53);
            double u = (i + h0) / 64.0;
            Vec3 c = start.add(dir.scale(length * u));
            double a = i * (float) (Math.PI * 2) / 16.0 + t * 0.014 + h1 * 0.4;
            double inner = 1.1 + h1 * 1.2;
            double outer = 3.4 + h2 * 2.8;
            Vec3 p0 = c.add(basis.right.scale(Math.cos(a) * inner)).add(basis.up.scale(Math.sin(a) * inner));
            Vec3 p1 = c.add(basis.right.scale(Math.cos(a) * outer)).add(basis.up.scale(Math.sin(a) * outer));
            Vec3 side = basis.right
               .scale(Math.cos(a + (float) (Math.PI / 2)) * (0.12 + h0 * 0.16))
               .add(basis.up.scale(Math.sin(a + (float) (Math.PI / 2)) * (0.12 + h0 * 0.16)));
            int r = i % 3 == 0 ? 255 : (i % 3 == 1 ? 116 : 210);
            int g = i % 3 == 0 ? 82 : (i % 3 == 1 ? 236 : 58);
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer, matrix, p0.add(side), p1.add(side.scale(0.35)), p1.subtract(side.scale(0.35)), p0.subtract(side), r, g, b, alpha * (0.2F + h2 * 0.22F)
            );
         }

         return wrote;
      }

      private boolean renderFinalStopExplosion(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t) {
         if (!this.finalExplosionShakeTriggered) {
            this.finalExplosionShakeTriggered = true;
            PathsClientState.triggerManualShake(18, 8.5F, 10.0F);
         }

         float born = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 0.7F));
         float fade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - 152.0F) / 58.0F));
         float alpha = born * fade;
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float shock = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 5.0F));
         float ignition = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - 12.0F) / 22.0F));
         float afterglow = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - 112.0F) / 78.0F));
         float clientPulse = 0.86F + 0.14F * Mth.sin((float)(Util.getMillis() & 8191L) * 0.004F);
         alpha *= clientPulse;
         wrote |= MichaelKeterVfxClient.H.softBloom(
            buffer, matrix, center, basis.forward, 34.0F + shock * 42.0F, 255, 70, 232, alpha * (0.88F + ignition * 0.35F)
         );
         wrote |= MichaelKeterVfxClient.H.softBloom(
            buffer, matrix, center.add(basis.forward.scale(0.16)), basis.forward, 21.0F + shock * 26.0F, 116, 236, 255, alpha * 0.82F
         );
         wrote |= MichaelKeterVfxClient.H.softBloom(
            buffer, matrix, center.subtract(basis.forward.scale(0.18)), basis.forward, 56.0F + shock * 68.0F, 82, 0, 255, alpha * 0.38F
         );
         wrote |= MichaelKeterVfxClient.H.softBloom(
            buffer, matrix, center.add(basis.up.scale(0.16)), basis.forward, 13.0F + ignition * 21.0F, 255, 142, 220, alpha * ignition * 0.7F
         );
         wrote |= MichaelKeterVfxClient.H.softBloom(
            buffer, matrix, center.add(basis.forward.scale(3.4 + shock * 7.0)), basis.forward, 25.0F + shock * 30.0F, 255, 228, 255, alpha * ignition * 0.46F
         );
         wrote |= this.renderFinalEventHorizon(buffer, matrix, center, basis, t, alpha, shock, ignition);
         wrote |= this.renderFinalHertaPanels(buffer, matrix, center, basis, t, alpha, shock);
         wrote |= this.renderFinalNebulaPetals(buffer, matrix, center, basis, t, alpha, shock, ignition);
         wrote |= this.renderFinalCometDust(buffer, matrix, center, basis, t, alpha, shock, afterglow);
         wrote |= this.renderFinalConstellationShards(buffer, matrix, center, basis, t, alpha, shock);
         wrote |= this.renderFinalCoreCollapse(buffer, matrix, center, basis, t, alpha, ignition);
         wrote |= this.renderFinalInstantUumph(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalLongLivingSuperBoom(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalLayeredPhoenixPressure(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalHertaMechanicalHalo(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalDelayedSecondaryBlooms(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalLingeringStardustAftermath(buffer, matrix, center, basis, t, alpha, shock, afterglow);
         wrote |= this.renderFinalElfariaMitamaPrismBloom(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalHertaGrandStardustShell(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalWorldCrackAscension(buffer, matrix, center, basis, t, alpha, shock, ignition);
         wrote |= this.renderFinalGroundQuake(buffer, matrix, center, basis, t, alpha, shock, ignition);
         wrote |= this.renderFinalGiantStardust(buffer, matrix, center, basis, t, alpha, shock, afterglow);
         wrote |= this.renderFinalRadiantStardustStorm(buffer, matrix, center, basis, t, alpha, shock, afterglow);
         wrote |= this.renderFinalDeepSpaceAfterimage(buffer, matrix, center, basis, t, alpha, shock);
         wrote |= this.renderFinalWorldStardustCrown(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalUltra3DApotheosis(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalHertaStardustSupernova(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalMitamaElfariaPressureBloom(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalHertaCubeGalaxy(buffer, matrix, center, basis, t, alpha, shock, afterglow);
         wrote |= this.renderFinalSparklyVoidComets(buffer, matrix, center, basis, t, alpha, shock, afterglow);
         wrote |= this.renderFinalVerticalWorldPillars(buffer, matrix, center, basis, t, alpha, shock, ignition, afterglow);
         wrote |= this.renderFinalThreeDimensionalShockSpheres(buffer, matrix, center, basis, t, alpha, shock, ignition);
         wrote |= this.renderGeneratedUltraFinalLayers(buffer, matrix, center, basis, t, alpha * 0.42F);
         return wrote | this.renderGeneratedFinalLayers(buffer, matrix, center, basis, t, alpha * 0.32F);
      }

      private boolean renderFinalEventHorizon(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float ignition
      ) {
         boolean wrote = false;

         for (int ring = 0; ring < 14; ring++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 60000 + ring * 19);
            double radius = (1.8 + ring * 1.55 + h * 1.35) * (0.2 + shock * 1.42);
            double inner = Math.max(0.12, radius - (0.1 + ring * 0.02));
            double outer = radius + 0.2 + ring * 0.04;
            double twist = t * (0.01 + ring * 0.0018) + h * (float) (Math.PI * 2);
            int palette = ring % 5;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 92 : 255)));
            int g = palette == 0 ? 74 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 204 : 132)));
            int b = palette == 4 ? 210 : 255;
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               center.add(basis.forward.scale((ring - 6.0) * 0.025)),
               basis,
               inner,
               outer,
               twist,
               r,
               g,
               b,
               alpha * (0.2F + ignition * 0.16F) * (1.0F - ring * 0.042F)
            );
         }

         for (int ring = 0; ring < 5; ring++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 62000 + ring * 43);
            double radius = (7.0 + ring * 4.2 + h * 2.5) * shock;
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               center.add(basis.forward.scale(-0.18 + ring * 0.08)),
               basis,
               radius,
               radius + 0.32 + ring * 0.08,
               -t * (0.006 + ring * 0.002),
               116,
               236,
               255,
               alpha * (0.11F + ignition * 0.08F)
            );
         }

         return wrote;
      }

      private boolean renderFinalHertaPanels(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock
      ) {
         boolean wrote = false;

         for (int i = 0; i < 72; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 63000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 64000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 65000 + i * 23);
            double orbit = (3.5 + h1 * 23.0) * shock;
            double angle = h0 * (float) (Math.PI * 2) + t * (0.004 + h2 * 0.006) + i * 0.05;
            double height = (h2 - 0.5) * (5.0 + shock * 14.0);
            Vec3 p = center.add(basis.right.scale(Math.cos(angle) * orbit))
               .add(basis.up.scale(Math.sin(angle) * orbit + height))
               .add(basis.forward.scale((h0 - 0.5) * orbit * 0.58));
            float size = 0.22F + h1 * 0.52F + shock * 0.24F;
            float roll = (float)(t * (0.01 + h2 * 0.018) + h0 * (float) (Math.PI * 2));
            int palette = i % 6;
            int r = palette == 0 ? 255 : (palette == 1 ? 118 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : 160))));
            int g = palette == 0 ? 76 : (palette == 1 ? 236 : (palette == 2 ? 64 : (palette == 3 ? 132 : (palette == 4 ? 204 : 44))));
            int b = palette == 5 ? 255 : 255;
            wrote |= MichaelKeterVfxClient.H.orientedDiamond(buffer, matrix, p, basis, size, roll, r, g, b, alpha * (0.18F + h2 * 0.3F));
            if (i % 5 == 0) {
               wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, basis.forward, size * (1.6F + h0 * 1.4F), r, g, b, alpha * 0.12F);
            }
         }

         return wrote;
      }

      private boolean renderFinalNebulaPetals(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float ignition
      ) {
         boolean wrote = false;

         for (int petal = 0; petal < 36; petal++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 67000 + petal * 31);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 68000 + petal * 37);
            double base = petal * (float) (Math.PI * 2) / 36.0 + h0 * 0.12 + t * 0.004;
            double length = (8.0 + h1 * 24.0) * shock;
            double width = 0.48 + h0 * 1.2;
            Vec3 root = center.add(basis.right.scale(Math.cos(base) * 1.2)).add(basis.up.scale(Math.sin(base) * 1.2));
            Vec3 tip = center.add(basis.right.scale(Math.cos(base) * length))
               .add(basis.up.scale(Math.sin(base) * length))
               .add(basis.forward.scale((h0 - 0.5) * length * 0.4));
            Vec3 side = basis.right.scale(Math.cos(base + (float) (Math.PI / 2)) * width).add(basis.up.scale(Math.sin(base + (float) (Math.PI / 2)) * width));
            int r = petal % 3 == 0 ? 255 : (petal % 3 == 1 ? 116 : 210);
            int g = petal % 3 == 0 ? 78 : (petal % 3 == 1 ? 236 : 58);
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer,
               matrix,
               root.add(side),
               tip.add(side.scale(0.22)),
               tip.subtract(side.scale(0.22)),
               root.subtract(side),
               r,
               g,
               b,
               alpha * (0.11F + ignition * 0.14F)
            );
            wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, tip, 0.06F + h1 * 0.12F, r, g, b, alpha * (0.45F + h0 * 0.25F));
         }

         return wrote;
      }

      private boolean renderFinalCometDust(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float afterglow
      ) {
         boolean wrote = false;
         int count = 620;

         for (int i = 0; i < count; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 70000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 71000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 72000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 73000 + i * 29);
            float h4 = MichaelKeterVfxClient.H.hash(this.seed, 74000 + i * 31);
            double yaw = h0 * (float) (Math.PI * 2);
            double pitch = (h1 - 0.5) * Math.PI;
            double speed = 3.0 + h2 * 33.0;
            double travel = speed * MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (8.0F + h3 * 18.0F)));
            double curl = Math.sin(t * (0.022 + h4 * 0.025) + i * 0.57) * (0.18 + h3 * 0.68);
            Vec3 dir = basis.forward
               .scale(Math.cos(pitch) * Math.cos(yaw))
               .add(basis.right.scale(Math.sin(yaw) * Math.cos(pitch)))
               .add(basis.up.scale(Math.sin(pitch)));
            if (dir.lengthSqr() < 0.001) {
               dir = basis.forward;
            }

            dir = dir.normalize();
            Vec3 swirl = basis.right.scale(Math.cos(t * 0.032 + i) * curl).add(basis.up.scale(Math.sin(t * 0.03 + i * 0.7) * curl));
            Vec3 p = center.add(dir.scale(travel)).add(swirl);
            int palette = i % 7;
            int r = palette == 0 ? 255 : (palette == 1 ? 118 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 90 : (palette == 5 ? 255 : 160)))));
            int g = palette == 0 ? 76 : (palette == 1 ? 236 : (palette == 2 ? 64 : (palette == 3 ? 132 : (palette == 4 ? 204 : (palette == 5 ? 92 : 44)))));
            int b = palette == 5 ? 210 : 255;
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (28.0F + h4 * 28.0F)) / 24.0F));
            float localAlpha = alpha * afterglow * localFade * (0.42F + h2 * 0.46F);
            float size = 0.034F + h3 * 0.092F;
            wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, size, r, g, b, localAlpha);
            if (i % 11 == 0) {
               wrote |= MichaelKeterVfxClient.H.diamond(buffer, matrix, p, basis, 0.12F + h2 * 0.25F, r, g, b, localAlpha * 0.6F);
            }

            if (i % 17 == 0) {
               wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, basis.forward, 0.18F + h4 * 0.46F, r, g, b, localAlpha * 0.2F);
            }
         }

         return wrote;
      }

      private boolean renderFinalConstellationShards(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock
      ) {
         boolean wrote = false;

         for (int cluster = 0; cluster < 18; cluster++) {
            float ch0 = MichaelKeterVfxClient.H.hash(this.seed, 76000 + cluster * 53);
            float ch1 = MichaelKeterVfxClient.H.hash(this.seed, 77000 + cluster * 59);
            double a = ch0 * (float) (Math.PI * 2) + t * 0.003;
            double d = (5.0 + ch1 * 26.0) * shock;
            Vec3 anchor = center.add(basis.right.scale(Math.cos(a) * d)).add(basis.up.scale(Math.sin(a) * d)).add(basis.forward.scale((ch0 - 0.5) * d * 0.5));

            for (int i = 0; i < 9; i++) {
               float h0 = MichaelKeterVfxClient.H.hash(this.seed, 78000 + cluster * 97 + i * 7);
               float h1 = MichaelKeterVfxClient.H.hash(this.seed, 78500 + cluster * 97 + i * 11);
               Vec3 p = anchor.add(basis.right.scale((h0 - 0.5) * 2.8)).add(basis.up.scale((h1 - 0.5) * 2.8)).add(basis.forward.scale((h0 - h1) * 1.4));
               int r = i % 3 == 0 ? 255 : (i % 3 == 1 ? 116 : 210);
               int g = i % 3 == 0 ? 86 : (i % 3 == 1 ? 236 : 58);
               int b = 255;
               wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, 0.034F + h1 * 0.08F, r, g, b, alpha * (0.36F + h0 * 0.34F));
               if ((i & 1) == 0) {
                  wrote |= MichaelKeterVfxClient.H.orientedDiamond(
                     buffer, matrix, p.add(basis.forward.scale(0.04)), basis, 0.08F + h0 * 0.12F, (float)(t * 0.015 + h1), r, g, b, alpha * 0.34F
                  );
               }
            }
         }

         return wrote;
      }

      private boolean renderFinalCoreCollapse(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float ignition
      ) {
         boolean wrote = false;
         float core = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - 12.0F) / 18.0F));
         wrote |= MichaelKeterVfxClient.H.beamMuzzleDisk(buffer, matrix, center, basis.forward, 4.0F + ignition * 9.0F, 255, 86, 236, alpha * core * 0.46F);
         wrote |= MichaelKeterVfxClient.H.beamMuzzleDisk(
            buffer, matrix, center.add(basis.forward.scale(0.12)), basis.forward, 2.2F + ignition * 5.0F, 116, 236, 255, alpha * core * 0.42F
         );

         for (int i = 0; i < 96; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 79000 + i * 13);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 80000 + i * 17);
            double a = i * (float) (Math.PI * 2) / 96.0 + h0 * 0.16 - t * 0.018;
            double r = (0.2 + h1 * 2.2) * core;
            Vec3 p = center.add(basis.right.scale(Math.cos(a) * r)).add(basis.up.scale(Math.sin(a) * r)).add(basis.forward.scale((h0 - 0.5) * 0.35));
            wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, 0.06F + h1 * 0.12F, 255, 92, 236, alpha * core * (0.46F + h0 * 0.42F));
         }

         return wrote;
      }

      private boolean renderFinalGroundQuake(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float ignition
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int ring = 0; ring < 18; ring++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 190000 + ring * 31);
            double radius = (2.5 + ring * 2.35 + h * 2.5) * (0.12 + shock * 1.58);
            double inner = Math.max(0.08, radius - (0.08 + ring * 0.035));
            double outer = radius + 0.4 + ring * 0.07;
            int palette = ring % 5;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 92 : 255)));
            int g = palette == 0 ? 76 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 204 : 132)));
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               center.add(basis.up.scale(0.02 + ring * 0.004)),
               basis,
               inner,
               outer,
               t * (0.01 + h * 0.01),
               r,
               g,
               b,
               alpha * (0.24F + ignition * 0.24F) * (1.0F - ring * 0.03F)
            );
         }

         for (int i = 0; i < 120; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 191000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 192000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 193000 + i * 23);
            double a = h0 * (float) (Math.PI * 2);
            double d0 = (2.0 + h1 * 35.0) * shock;
            double d1 = d0 + 0.8 + h2 * 4.0;
            Vec3 p0 = center.add(basis.right.scale(Math.cos(a) * d0)).add(basis.up.scale(Math.sin(a) * d0));
            Vec3 p1 = center.add(basis.right.scale(Math.cos(a) * d1)).add(basis.up.scale(Math.sin(a) * d1));
            Vec3 side = basis.right
               .scale(Math.cos(a + (float) (Math.PI / 2)) * (0.12 + h2 * 0.28))
               .add(basis.up.scale(Math.sin(a + (float) (Math.PI / 2)) * (0.12 + h2 * 0.28)));
            int r = i % 4 == 0 ? 255 : (i % 4 == 1 ? 116 : (i % 4 == 2 ? 210 : 92));
            int g = i % 4 == 0 ? 72 : (i % 4 == 1 ? 236 : (i % 4 == 2 ? 58 : 204));
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer,
               matrix,
               p0.add(side),
               p1.add(side.scale(0.25)),
               p1.subtract(side.scale(0.25)),
               p0.subtract(side),
               r,
               g,
               b,
               alpha * (0.14F + ignition * 0.16F)
            );
         }

         return wrote;
      }

      private boolean renderFinalGiantStardust(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < 260; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 194000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 195000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 196000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 197000 + i * 29);
            double yaw = h0 * (float) (Math.PI * 2);
            double pitch = (h1 - 0.5) * Math.PI * 0.92;
            double dist = (4.5 + h2 * 62.0) * MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (7.0F + h3 * 22.0F)));
            Vec3 dir = basis.forward
               .scale(Math.cos(pitch) * Math.cos(yaw))
               .add(basis.right.scale(Math.sin(yaw) * Math.cos(pitch)))
               .add(basis.up.scale(Math.sin(pitch)));
            if (dir.lengthSqr() < 0.001) {
               dir = basis.forward;
            }

            dir = dir.normalize();
            double curl = Math.sin(t * (0.018 + h2 * 0.02) + i * 0.4) * (0.55 + h1 * 1.8);
            Vec3 p = center.add(dir.scale(dist)).add(basis.right.scale(Math.cos(yaw + t * 0.012) * curl)).add(basis.up.scale(Math.sin(yaw + t * 0.01) * curl));
            int palette = i % 7;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : (palette == 5 ? 255 : 160)))));
            int g = palette == 0 ? 72 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 132 : (palette == 4 ? 204 : (palette == 5 ? 92 : 44)))));
            int b = palette == 5 ? 214 : 255;
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (36.0F + h3 * 24.0F)) / 26.0F));
            float size = 0.24F + h2 * 0.92F + shock * 0.26F;
            float a = alpha * afterglow * localFade * (0.48F + h1 * 0.38F);
            wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size, r, g, b, a);
            wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, basis.forward, size * (1.15F + h0 * 1.4F), r, g, b, a * 0.24F);
         }

         return wrote;
      }

      private boolean renderFinalRadiantStardustStorm(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < 820; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 198000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 199000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 200000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 201000 + i * 29);
            double yaw = h0 * (float) (Math.PI * 2);
            double pitch = (h1 - 0.5) * Math.PI;
            double wave = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (10.0F + h2 * 30.0F)));
            double dist = (4.0 + h2 * 60.0) * wave;
            Vec3 dir = basis.forward
               .scale(Math.cos(pitch) * Math.cos(yaw))
               .add(basis.right.scale(Math.sin(yaw) * Math.cos(pitch)))
               .add(basis.up.scale(Math.sin(pitch)));
            if (dir.lengthSqr() < 0.001) {
               dir = basis.forward;
            }

            dir = dir.normalize();
            Vec3 swirl = basis.right
               .scale(Math.sin(t * 0.021 + i * 0.31) * (0.2 + h3 * 1.2))
               .add(basis.up.scale(Math.cos(t * 0.018 + i * 0.27) * (0.2 + h1 * 1.2)));
            Vec3 p = center.add(dir.scale(dist)).add(swirl);
            int palette = i % 8;
            int r = palette == 0
               ? 255
               : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : (palette == 5 ? 255 : (palette == 6 ? 160 : 120))))));
            int g = palette == 0
               ? 76
               : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 132 : (palette == 4 ? 204 : (palette == 5 ? 92 : (palette == 6 ? 44 : 220))))));
            int b = palette == 5 ? 214 : 255;
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (28.0F + h3 * 36.0F)) / 32.0F));
            float size = 0.03F + h2 * 0.11F + (i % 23 == 0 ? 0.18F : 0.0F);
            float a = alpha * afterglow * localFade * (0.34F + h1 * 0.42F);
            if (i % 23 == 0) {
               wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size * 2.4F, r, g, b, a * 0.9F);
            } else {
               wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, size, r, g, b, a);
            }
         }

         return wrote;
      }

      private boolean renderFinalDeepSpaceAfterimage(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < 52; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 202000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 203000 + i * 19);
            double a = i * (float) (Math.PI * 2) / 52.0 + h0 * 0.22 + t * 0.003;
            double inner = (10.0 + h1 * 20.0) * shock;
            double outer = inner + 1.0 + h0 * 2.8;
            Vec3 p0 = center.add(basis.right.scale(Math.cos(a) * inner)).add(basis.up.scale(Math.sin(a) * inner));
            Vec3 p1 = center.add(basis.right.scale(Math.cos(a) * outer)).add(basis.up.scale(Math.sin(a) * outer));
            Vec3 side = basis.right
               .scale(Math.cos(a + (float) (Math.PI / 2)) * (0.18 + h1 * 0.42))
               .add(basis.up.scale(Math.sin(a + (float) (Math.PI / 2)) * (0.18 + h1 * 0.42)));
            int r = i % 2 == 0 ? 255 : 116;
            int g = i % 2 == 0 ? 72 : 236;
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer, matrix, p0.add(side), p1.add(side.scale(0.15)), p1.subtract(side.scale(0.15)), p0.subtract(side), r, g, b, alpha * (0.12F + h0 * 0.15F)
            );
         }

         return wrote;
      }

      private boolean renderFinalWorldStardustCrown(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float birth = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 6.0F));
         float live = alpha * birth * (0.7F + afterglow * 0.45F);

         for (int ring = 0; ring < 10; ring++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 204000 + ring * 23);
            double radius = (5.0 + ring * 5.6 + h * 4.4) * (0.16 + shock * 1.24);
            double thickness = 0.42 + ring * 0.1 + ignition * 0.62;
            int palette = ring % 5;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 92 : 255)));
            int g = palette == 0 ? 74 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 204 : 132)));
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               center.add(basis.forward.scale(0.035 + ring * 0.01)),
               basis,
               Math.max(0.04, radius - thickness),
               radius + thickness,
               t * (0.006 + h * 0.01),
               r,
               g,
               b,
               live * (0.18F + ignition * 0.16F) * (1.0F - ring * 0.045F)
            );
         }

         for (int i = 0; i < 120; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 205000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 206000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 207000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 208000 + i * 29);
            double a = h0 * (float) (Math.PI * 2) + t * (0.004 + h3 * 0.008);
            double lift = (h1 - 0.2) * (4.0 + shock * 24.0);
            double radius = (7.0 + h2 * 68.0) * MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (5.0F + h3 * 18.0F)));
            Vec3 p = center.add(basis.right.scale(Math.cos(a) * radius)).add(basis.up.scale(Math.sin(a) * radius)).add(basis.forward.scale(lift));
            int palette = i % 8;
            int r = palette == 0
               ? 255
               : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : (palette == 5 ? 255 : (palette == 6 ? 160 : 120))))));
            int g = palette == 0
               ? 76
               : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 132 : (palette == 4 ? 204 : (palette == 5 ? 92 : (palette == 6 ? 44 : 220))))));
            int b = palette == 5 ? 214 : 255;
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (34.0F + h3 * 34.0F)) / 30.0F));
            float size = i % 9 == 0 ? 0.9F + h2 * 1.2F : 0.18F + h2 * 0.42F;
            float aAlpha = live * localFade * (0.36F + h1 * 0.48F);
            wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size, r, g, b, aAlpha);
            if (i % 4 == 0) {
               wrote |= MichaelKeterVfxClient.H.orientedDiamond(
                  buffer,
                  matrix,
                  p.add(basis.forward.scale(0.08)),
                  basis,
                  size * 0.48F,
                  (float)(t * 0.018 + h0 * (float) (Math.PI * 2)),
                  r,
                  g,
                  b,
                  aAlpha * 0.58F
               );
            }

            if (i % 13 == 0) {
               wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, basis.forward, size * 2.8F, r, g, b, aAlpha * 0.2F);
            }
         }

         return wrote;
      }

      private boolean renderFinalInstantUumph(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float impact = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - 4.0F) / 12.0F));
         float slam = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 2.0F));
         float live = alpha * (0.55F + ignition * 0.55F + impact * 0.3F);
         MichaelKeterVfxClient.Basis bx = MichaelKeterVfxClient.H.basis(basis.right);
         MichaelKeterVfxClient.Basis by = MichaelKeterVfxClient.H.basis(basis.up);
         MichaelKeterVfxClient.Basis bz = MichaelKeterVfxClient.H.basis(basis.forward);

         for (int i = 0; i < 18; i++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 510000 + i * 31);
            double radius = (2.2 + i * 4.15 + h * 3.0) * (0.08 + slam * 1.78);
            double thick = 0.55 + h * 1.1 + impact * 1.2;
            MichaelKeterVfxClient.Basis chosen = i % 3 == 0 ? bx : (i % 3 == 1 ? by : bz);
            int palette = i % 6;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : 170))));
            int g = palette == 0 ? 78 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 132 : (palette == 4 ? 204 : 44))));
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               center,
               chosen,
               Math.max(0.08, radius - thick),
               radius + thick,
               t * (0.02 + h * 0.02) + i * 0.37,
               r,
               g,
               b,
               live * (0.42F + impact * 0.22F) * (1.0F - i * 0.026F)
            );
         }

         for (int i = 0; i < 42; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 512000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 513000 + i * 19);
            double a = i * (float) (Math.PI * 2) / 42.0 + h0 * 0.1;
            double len = (10.0 + h1 * 82.0) * slam;
            double width = 1.1 + h0 * 4.2 + impact * 1.6;
            Vec3 root = center.add(basis.right.scale(Math.cos(a) * 0.8)).add(basis.up.scale(Math.sin(a) * 0.8)).add(basis.forward.scale(0.22 + h1 * 0.8));
            Vec3 tip = center.add(basis.right.scale(Math.cos(a) * len))
               .add(basis.up.scale(Math.sin(a) * len))
               .add(basis.forward.scale((0.12 + h0 * 0.86) * len));
            Vec3 side = basis.right.scale(Math.cos(a + (float) (Math.PI / 2)) * width).add(basis.up.scale(Math.sin(a + (float) (Math.PI / 2)) * width));
            int r = i % 4 == 0 ? 255 : (i % 4 == 1 ? 116 : (i % 4 == 2 ? 210 : 92));
            int g = i % 4 == 0 ? 72 : (i % 4 == 1 ? 236 : (i % 4 == 2 ? 58 : 204));
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer,
               matrix,
               root.add(side),
               tip.add(side.scale(0.1)),
               tip.subtract(side.scale(0.1)),
               root.subtract(side),
               r,
               g,
               b,
               live * (0.22F + ignition * 0.2F)
            );
            wrote |= MichaelKeterVfxClient.H.bigStar(
               buffer, matrix, tip, basis, 0.55F + h1 * 1.85F + impact * 0.8F, r, g, b, live * afterglow * (0.5F + h0 * 0.34F)
            );
         }

         return wrote
            | MichaelKeterVfxClient.H.softBloom(
               buffer, matrix, center.add(basis.forward.scale(6.0 + shock * 14.0)), basis.forward, 18.0F + shock * 38.0F, 255, 236, 255, live * impact * 0.44F
            );
      }

      private boolean renderFinalElfariaMitamaPrismBloom(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float grow = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 8.5F));
         float live = alpha * (0.42F + ignition * 0.38F + afterglow * 0.3F);

         for (int layer = 0; layer < 16; layer++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 520000 + layer * 41);
            double height = (6.0 + layer * 4.2 + h * 10.0) * grow;
            double core = (2.8 + layer * 1.3 + h * 4.8) * (0.32 + shock * 1.3);
            Vec3 layerCenter = center.add(basis.forward.scale(height * 0.55));
            int palette = layer % 5;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 92 : 255)));
            int g = palette == 0 ? 86 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 204 : 132)));
            int b = 255;

            for (int sideIndex = 0; sideIndex < 8; sideIndex++) {
               double a = sideIndex * (float) (Math.PI * 2) / 8.0 + t * (0.01 + h * 0.012) + layer * 0.16;
               Vec3 radial = basis.right.scale(Math.cos(a)).add(basis.up.scale(Math.sin(a)));
               Vec3 tangent = basis.right.scale(Math.cos(a + (float) (Math.PI / 2))).add(basis.up.scale(Math.sin(a + (float) (Math.PI / 2))));
               Vec3 base = center.add(radial.scale(core * (0.35 + h * 0.2)));
               Vec3 top = layerCenter.add(radial.scale(core * (1.15 + shock * 0.5)));
               double w = 0.55 + h * 1.25 + ignition * 1.3;
               wrote |= MichaelKeterVfxClient.H.quad(
                  buffer,
                  matrix,
                  base.add(tangent.scale(w)),
                  top.add(tangent.scale(w * 0.2)),
                  top.subtract(tangent.scale(w * 0.2)),
                  base.subtract(tangent.scale(w)),
                  r,
                  g,
                  b,
                  live * (0.16F + ignition * 0.18F) * (1.0F - layer * 0.026F)
               );
               if ((sideIndex & 1) == 0) {
                  wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, top, basis.forward, (float)(1.2 + core * 0.12), r, g, b, live * 0.16F);
               }
            }
         }

         for (int i = 0; i < 150; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 521000 + i * 13);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 522000 + i * 17);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 523000 + i * 19);
            double a = h0 * (float) (Math.PI * 2) + t * (0.008 + h2 * 0.014);
            double rOrbit = (4.0 + h1 * 76.0) * grow;
            double y = (h2 * 58.0 + ignition * 12.0) * grow;
            Vec3 p = center.add(basis.right.scale(Math.cos(a) * rOrbit)).add(basis.up.scale(Math.sin(a) * rOrbit)).add(basis.forward.scale(y));
            int r = i % 3 == 0 ? 255 : (i % 3 == 1 ? 116 : 210);
            int g = i % 3 == 0 ? 90 : (i % 3 == 1 ? 236 : 58);
            int b = 255;
            float size = 0.18F + h2 * 0.6F + (i % 10 == 0 ? 1.2F + h1 * 1.3F : 0.0F);
            wrote |= MichaelKeterVfxClient.H.orientedDiamond(
               buffer, matrix, p, basis, size, (float)(t * (0.018 + h1 * 0.02) + h0 * (float) (Math.PI * 2)), r, g, b, live * afterglow * (0.42F + h1 * 0.34F)
            );
         }

         return wrote;
      }

      private boolean renderFinalHertaGrandStardustShell(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float waveFast = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 5.0F));
         float waveSlow = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 20.0F));
         float live = alpha * afterglow;

         for (int i = 0; i < 420; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 530000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 531000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 532000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 533000 + i * 29);
            double yaw = h0 * (float) (Math.PI * 2);
            double v = h1 * 2.0 - 1.0;
            double radial = Math.sqrt(Math.max(0.0, 1.0 - v * v));
            Vec3 dir = basis.right.scale(Math.cos(yaw) * radial).add(basis.up.scale(Math.sin(yaw) * radial)).add(basis.forward.scale(v * 0.88 + 0.22));
            if (dir.lengthSqr() < 0.001) {
               dir = basis.forward;
            }

            dir = dir.normalize();
            double dist = (9.0 + h2 * 108.0) * (0.18 + waveFast * 0.82);
            double lag = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (4.0F + h3 * 28.0F)));
            Vec3 p = center.add(dir.scale(dist * lag));
            p = p.add(basis.right.scale(Math.sin(t * (0.016 + h3 * 0.02) + i) * (0.3 + h2 * 2.2)));
            p = p.add(basis.up.scale(Math.cos(t * (0.014 + h2 * 0.017) + i * 0.7) * (0.3 + h1 * 2.2)));
            int palette = i % 8;
            int r = palette == 0
               ? 255
               : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : (palette == 5 ? 255 : (palette == 6 ? 170 : 120))))));
            int g = palette == 0
               ? 76
               : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 132 : (palette == 4 ? 204 : (palette == 5 ? 92 : (palette == 6 ? 44 : 220))))));
            int b = palette == 5 ? 214 : 255;
            float size = 0.04F + h2 * 0.18F + (i % 19 == 0 ? 0.65F + h1 * 1.8F : 0.0F) + (i % 47 == 0 ? 2.1F + h3 * 1.8F : 0.0F);
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (34.0F + h3 * 46.0F)) / 34.0F));
            float a = live * localFade * (0.42F + h1 * 0.44F + ignition * 0.18F);
            if (i % 19 != 0 && i % 47 != 0) {
               wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, size, r, g, b, a);
            } else {
               wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size, r, g, b, a);
               wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, basis.forward, size * (2.6F + h0 * 2.2F), r, g, b, a * 0.16F);
            }
         }

         for (int ring = 0; ring < 8; ring++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 534000 + ring * 43);
            double radius = (20.0 + ring * 14.0 + h * 12.0) * (0.18 + waveSlow * 0.92);
            double thick = 0.7 + ring * 0.22 + ignition * 1.2;
            MichaelKeterVfxClient.Basis chosen = ring % 3 == 0
               ? MichaelKeterVfxClient.H.basis(basis.forward)
               : (ring % 3 == 1 ? MichaelKeterVfxClient.H.basis(basis.right) : MichaelKeterVfxClient.H.basis(basis.up));
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               center.add(basis.forward.scale(ring * 1.15)),
               chosen,
               Math.max(0.1, radius - thick),
               radius + thick,
               t * (0.006 + h * 0.01),
               ring % 2 == 0 ? 255 : 116,
               ring % 2 == 0 ? 82 : 236,
               255,
               live * (0.18F + ignition * 0.1F)
            );
         }

         return wrote;
      }

      private boolean renderFinalWorldCrackAscension(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float ignition
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float grow = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 5.5F));
         float live = alpha * (0.34F + ignition * 0.34F);

         for (int i = 0; i < 96; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 540000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 541000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 542000 + i * 23);
            double a = h0 * (float) (Math.PI * 2);
            double start = 1.0 + h1 * 6.0;
            double end = (12.0 + h1 * 112.0) * grow;
            double lift = (h2 * 6.0 + ignition * 7.0) * grow;
            Vec3 p0 = center.add(basis.right.scale(Math.cos(a) * start)).add(basis.up.scale(Math.sin(a) * start)).add(basis.forward.scale(0.04));
            Vec3 p1 = center.add(basis.right.scale(Math.cos(a) * end)).add(basis.up.scale(Math.sin(a) * end)).add(basis.forward.scale(lift));
            Vec3 side = basis.right
               .scale(Math.cos(a + (float) (Math.PI / 2)) * (0.18 + h2 * 0.62))
               .add(basis.up.scale(Math.sin(a + (float) (Math.PI / 2)) * (0.18 + h2 * 0.62)));
            int r = i % 4 == 0 ? 255 : (i % 4 == 1 ? 116 : (i % 4 == 2 ? 210 : 92));
            int g = i % 4 == 0 ? 82 : (i % 4 == 1 ? 236 : (i % 4 == 2 ? 58 : 204));
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer, matrix, p0.add(side), p1.add(side.scale(0.18)), p1.subtract(side.scale(0.18)), p0.subtract(side), r, g, b, live * (0.22F + h0 * 0.16F)
            );
            if (i % 8 == 0) {
               wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p1, basis.forward, 1.2F + h1 * 3.8F, r, g, b, live * 0.18F);
            }
         }

         return wrote;
      }

      private boolean renderFinalUltra3DApotheosis(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         Vec3 upAxis = basis.forward;
         MichaelKeterVfxClient.Basis horizontal = MichaelKeterVfxClient.H.basis(upAxis);
         float rise = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 18.0F));
         float live = alpha * (0.62F + afterglow * 0.52F);

         for (int layer = 0; layer < 28; layer++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 430000 + layer * 37);
            double y = 0.2 + layer * 1.34 + shock * (2.5 + h * 6.0);
            double radius = (4.0 + layer * 1.26 + h * 4.0) * (0.36 + shock * 1.42);
            double thickness = 0.34 + h * 0.64 + ignition * 0.7;
            Vec3 c = center.add(upAxis.scale(y * rise));
            int palette = layer % 6;
            int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : 170))));
            int g = palette == 0 ? 76 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 132 : (palette == 4 ? 204 : 48))));
            int b = 255;
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               c,
               horizontal,
               Math.max(0.1, radius - thickness),
               radius + thickness,
               t * (0.012 + h * 0.012) + layer * 0.11,
               r,
               g,
               b,
               live * (0.26F + ignition * 0.2F) * (1.0F - layer * 0.018F)
            );
            wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, c, upAxis, (float)(radius * 0.16 + 2.4), r, g, b, live * 0.1F);
         }

         for (int i = 0; i < 240; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 431000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 432000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 433000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 434000 + i * 29);
            double spiral = h0 * (float) (Math.PI * 2) + t * (0.009 + h3 * 0.02) + shock * 3.4;
            double radius = (2.0 + h1 * 48.0) * MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (7.0F + h2 * 21.0F)));
            double y = (h2 * 42.0 + shock * 18.0) * (0.22 + rise * 0.78);
            Vec3 p = center.add(horizontal.right.scale(Math.cos(spiral) * radius)).add(horizontal.up.scale(Math.sin(spiral) * radius)).add(upAxis.scale(y));
            int palette = i % 7;
            int r = palette == 0 ? 255 : (palette == 1 ? 118 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : (palette == 5 ? 255 : 160)))));
            int g = palette == 0 ? 76 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 132 : (palette == 4 ? 204 : (palette == 5 ? 92 : 44)))));
            int b = palette == 5 ? 214 : 255;
            float size = 0.11F + h2 * 0.42F + (i % 17 == 0 ? 0.92F + h1 : 0.0F);
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (48.0F + h3 * 48.0F)) / 42.0F));
            wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, horizontal, size, r, g, b, live * localFade * (0.42F + h1 * 0.44F));
         }

         return wrote;
      }

      private boolean renderFinalHertaStardustSupernova(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float wave = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 12.0F));
         float live = alpha * afterglow;

         for (int i = 0; i < 540; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 440000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 441000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 442000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 443000 + i * 29);
            float h4 = MichaelKeterVfxClient.H.hash(this.seed, 444000 + i * 31);
            double yaw = h0 * (float) (Math.PI * 2);
            double pitch = (h1 - 0.36) * Math.PI * 1.1;
            double dist = (3.0 + h2 * 86.0) * MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (5.5F + h3 * 26.0F)));
            Vec3 dir = basis.forward
               .scale(Math.sin(pitch))
               .add(basis.right.scale(Math.cos(yaw) * Math.cos(pitch)))
               .add(basis.up.scale(Math.sin(yaw) * Math.cos(pitch)));
            if (dir.lengthSqr() < 0.001) {
               dir = basis.forward;
            }

            dir = dir.normalize();
            Vec3 p = center.add(dir.scale(dist)).add(basis.forward.scale(wave * (h4 * 18.0)));
            int palette = i % 8;
            int r = palette == 0
               ? 255
               : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 255 : (palette == 4 ? 92 : (palette == 5 ? 255 : (palette == 6 ? 170 : 120))))));
            int g = palette == 0
               ? 76
               : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 132 : (palette == 4 ? 204 : (palette == 5 ? 92 : (palette == 6 ? 44 : 220))))));
            int b = palette == 5 ? 214 : 255;
            float size = 0.045F + h3 * 0.155F;
            if (i % 29 == 0) {
               size += 1.4F + h4 * 1.6F;
            } else if (i % 11 == 0) {
               size += 0.34F + h4 * 0.6F;
            }

            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (38.0F + h4 * 58.0F)) / 44.0F));
            float a = live * localFade * (0.38F + h2 * 0.52F);
            if (i % 11 == 0) {
               wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size, r, g, b, a);
            } else {
               wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, size, r, g, b, a);
            }

            if (i % 37 == 0) {
               wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, basis.forward, size * (3.2F + h1 * 2.5F), r, g, b, a * 0.18F);
            }
         }

         return wrote;
      }

      private boolean renderFinalMitamaElfariaPressureBloom(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         MichaelKeterVfxClient.Basis b1 = MichaelKeterVfxClient.H.basis(basis.forward);
         MichaelKeterVfxClient.Basis b2 = MichaelKeterVfxClient.H.basis(basis.right);
         MichaelKeterVfxClient.Basis b3 = MichaelKeterVfxClient.H.basis(basis.up);
         float live = alpha * (0.56F + ignition * 0.44F);

         for (int i = 0; i < 18; i++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 450000 + i * 41);
            double radius = (3.0 + i * 3.4 + h * 3.2) * (0.24 + shock * 1.35);
            double thick = 0.42 + h * 0.65 + ignition * 0.85;
            MichaelKeterVfxClient.Basis chosen = i % 3 == 0 ? b1 : (i % 3 == 1 ? b2 : b3);
            int r = i % 4 == 0 ? 255 : (i % 4 == 1 ? 116 : (i % 4 == 2 ? 210 : 92));
            int g = i % 4 == 0 ? 76 : (i % 4 == 1 ? 236 : (i % 4 == 2 ? 58 : 204));
            int bl = 255;
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               center,
               chosen,
               Math.max(0.1, radius - thick),
               radius + thick,
               t * (0.01 + h * 0.015) + i * 0.33,
               r,
               g,
               bl,
               live * (0.3F - i * 0.008F)
            );
         }

         for (int i = 0; i < 96; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 451000 + i * 13);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 452000 + i * 17);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 453000 + i * 19);
            double a = h0 * (float) (Math.PI * 2) + t * (0.006 + h2 * 0.008);
            double length = (7.0 + h1 * 68.0) * shock;
            double width = 0.7 + h2 * 2.2;
            Vec3 root = center.add(basis.right.scale(Math.cos(a) * 1.4)).add(basis.up.scale(Math.sin(a) * 1.4)).add(basis.forward.scale(h0 * 2.0));
            Vec3 tip = center.add(basis.right.scale(Math.cos(a) * length))
               .add(basis.up.scale(Math.sin(a) * length))
               .add(basis.forward.scale((h1 - 0.15) * length * 0.72));
            Vec3 side = basis.right.scale(Math.cos(a + (float) (Math.PI / 2)) * width).add(basis.up.scale(Math.sin(a + (float) (Math.PI / 2)) * width));
            int r = i % 3 == 0 ? 255 : (i % 3 == 1 ? 116 : 210);
            int g = i % 3 == 0 ? 88 : (i % 3 == 1 ? 236 : 58);
            int bl = 255;
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer,
               matrix,
               root.add(side),
               tip.add(side.scale(0.12)),
               tip.subtract(side.scale(0.12)),
               root.subtract(side),
               r,
               g,
               bl,
               alpha * (0.16F + ignition * 0.18F)
            );
            if (i % 4 == 0) {
               wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, tip, basis, 0.42F + h2 * 1.2F, r, g, bl, alpha * afterglow * 0.52F);
            }
         }

         return wrote;
      }

      private boolean renderFinalHertaCubeGalaxy(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < 180; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 460000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 461000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 462000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 463000 + i * 29);
            double a = h0 * (float) (Math.PI * 2) + t * (0.005 + h3 * 0.01);
            double r = (3.0 + h1 * 72.0) * MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (8.0F + h2 * 28.0F)));
            double y = (h2 - 0.22) * (10.0 + shock * 48.0);
            Vec3 p = center.add(basis.right.scale(Math.cos(a) * r)).add(basis.up.scale(Math.sin(a) * r)).add(basis.forward.scale(y));
            float size = 0.2F + h1 * 0.7F + (i % 12 == 0 ? 1.1F : 0.0F);
            int palette = i % 6;
            int red = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 210 : (palette == 3 ? 92 : (palette == 4 ? 255 : 160))));
            int green = palette == 0 ? 76 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 204 : (palette == 4 ? 132 : 44))));
            int blue = 255;
            float roll = (float)(t * (0.016 + h3 * 0.026) + h0 * (float) (Math.PI * 2));
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (44.0F + h2 * 50.0F)) / 42.0F));
            wrote |= MichaelKeterVfxClient.H.orientedDiamond(
               buffer, matrix, p, basis, size, roll, red, green, blue, alpha * afterglow * localFade * (0.4F + h2 * 0.4F)
            );
            wrote |= MichaelKeterVfxClient.H.orientedDiamond(
               buffer,
               matrix,
               p.add(basis.forward.scale(size * 0.24)),
               MichaelKeterVfxClient.H.basis(basis.right),
               size * 0.74F,
               roll + 0.9F,
               red,
               green,
               blue,
               alpha * afterglow * localFade * 0.22F
            );
         }

         return wrote;
      }

      private boolean renderFinalSparklyVoidComets(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < 360; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 470000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 471000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 472000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 473000 + i * 29);
            double yaw = h0 * (float) (Math.PI * 2);
            double pitch = (h1 - 0.5) * Math.PI;
            Vec3 dir = basis.forward
               .scale(Math.sin(pitch))
               .add(basis.right.scale(Math.cos(yaw) * Math.cos(pitch)))
               .add(basis.up.scale(Math.sin(yaw) * Math.cos(pitch)));
            if (dir.lengthSqr() < 0.001) {
               dir = basis.forward;
            }

            dir = dir.normalize();
            double dist = (6.0 + h2 * 96.0) * MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (6.0F + h3 * 32.0F)));
            Vec3 head = center.add(dir.scale(dist));
            Vec3 tail = center.add(dir.scale(Math.max(0.0, dist - (2.0 + h1 * 9.0))));
            Vec3 side = basis.right.scale(Math.sin(yaw + t * 0.018) * (0.1 + h0 * 0.42)).add(basis.up.scale(Math.cos(yaw + t * 0.016) * (0.1 + h1 * 0.42)));
            int red = i % 4 == 0 ? 255 : (i % 4 == 1 ? 116 : (i % 4 == 2 ? 210 : 92));
            int green = i % 4 == 0 ? 84 : (i % 4 == 1 ? 236 : (i % 4 == 2 ? 58 : 204));
            int blue = 255;
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (36.0F + h3 * 62.0F)) / 48.0F));
            float a = alpha * afterglow * localFade * (0.32F + h2 * 0.42F);
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer, matrix, tail.add(side), head.add(side.scale(0.2)), head.subtract(side.scale(0.2)), tail.subtract(side), red, green, blue, a * 0.36F
            );
            wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, head, basis, 0.08F + h2 * 0.28F + (i % 31 == 0 ? 1.2F : 0.0F), red, green, blue, a);
         }

         return wrote;
      }

      private boolean renderFinalVerticalWorldPillars(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < 108; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 480000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 481000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 482000 + i * 23);
            double a = h0 * (float) (Math.PI * 2);
            double ring = (4.0 + h1 * 66.0) * shock;
            double height = 6.0 + h2 * 58.0 + ignition * 20.0;
            Vec3 base = center.add(basis.right.scale(Math.cos(a) * ring)).add(basis.up.scale(Math.sin(a) * ring));
            Vec3 top = base.add(basis.forward.scale(height));
            Vec3 side = basis.right
               .scale(Math.cos(a + (float) (Math.PI / 2)) * (0.16 + h2 * 0.7))
               .add(basis.up.scale(Math.sin(a + (float) (Math.PI / 2)) * (0.16 + h2 * 0.7)));
            int red = i % 5 == 0 ? 255 : (i % 5 == 1 ? 116 : (i % 5 == 2 ? 210 : (i % 5 == 3 ? 92 : 255)));
            int green = i % 5 == 0 ? 76 : (i % 5 == 1 ? 236 : (i % 5 == 2 ? 58 : (i % 5 == 3 ? 204 : 132)));
            int blue = 255;
            float local = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (28.0F + h2 * 54.0F)) / 42.0F));
            float aAlpha = alpha * afterglow * local * (0.18F + h1 * 0.24F);
            wrote |= MichaelKeterVfxClient.H.quad(
               buffer, matrix, base.add(side), top.add(side.scale(0.25)), top.subtract(side.scale(0.25)), base.subtract(side), red, green, blue, aAlpha
            );
            if (i % 6 == 0) {
               wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, top, basis.forward, 1.0F + h2 * 3.0F, red, green, blue, aAlpha * 0.26F);
            }
         }

         return wrote;
      }

      private boolean renderFinalThreeDimensionalShockSpheres(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float ignition
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         MichaelKeterVfxClient.Basis bx = MichaelKeterVfxClient.H.basis(basis.right);
         MichaelKeterVfxClient.Basis by = MichaelKeterVfxClient.H.basis(basis.up);
         MichaelKeterVfxClient.Basis bz = MichaelKeterVfxClient.H.basis(basis.forward);

         for (int i = 0; i < 24; i++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 490000 + i * 43);
            double radius = (5.0 + i * 2.7 + h * 4.0) * (0.18 + shock * 1.55);
            double thick = 0.38 + h * 0.74 + ignition * 0.9;
            MichaelKeterVfxClient.Basis chosen = i % 3 == 0 ? bx : (i % 3 == 1 ? by : bz);
            int red = i % 6 == 0 ? 255 : (i % 6 == 1 ? 116 : (i % 6 == 2 ? 210 : (i % 6 == 3 ? 92 : (i % 6 == 4 ? 255 : 170))));
            int green = i % 6 == 0 ? 76 : (i % 6 == 1 ? 236 : (i % 6 == 2 ? 58 : (i % 6 == 3 ? 204 : (i % 6 == 4 ? 132 : 44))));
            int blue = 255;
            wrote |= MichaelKeterVfxClient.H.orientedRing(
               buffer,
               matrix,
               center,
               chosen,
               Math.max(0.1, radius - thick),
               radius + thick,
               t * (0.01 + h * 0.012) + i * 0.19,
               red,
               green,
               blue,
               alpha * (0.28F + ignition * 0.16F) * (1.0F - i * 0.02F)
            );
         }

         return wrote;
      }

      private boolean renderFinalLongLivingSuperBoom(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float first = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / 4.0F));
         float second = MichaelKeterVfxClient.H.smooth(Mth.clamp((t - 24.0F) / 22.0F, 0.0F, 1.0F));
         float third = MichaelKeterVfxClient.H.smooth(Mth.clamp((t - 64.0F) / 36.0F, 0.0F, 1.0F));
         float live = alpha * (0.54F + afterglow * 0.58F);
         MichaelKeterVfxClient.Basis horizontal = MichaelKeterVfxClient.H.basis(basis.forward);

         for (int shell = 0; shell < 16; shell++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 610000 + shell * 41);
            float wave = shell < 6 ? first : (shell < 11 ? second : third);
            if (!(wave <= 0.001F)) {
               double radius = (10.0 + shell * 8.5 + h * 11.0) * (0.12 + wave * 1.38);
               double thick = 0.55 + shell * 0.11 + ignition * 1.3;
               MichaelKeterVfxClient.Basis chosen = shell % 4 == 0
                  ? MichaelKeterVfxClient.H.basis(basis.forward)
                  : (shell % 4 == 1 ? MichaelKeterVfxClient.H.basis(basis.right) : (shell % 4 == 2 ? MichaelKeterVfxClient.H.basis(basis.up) : horizontal));
               int r = shell % 5 == 0 ? 255 : (shell % 5 == 1 ? 116 : (shell % 5 == 2 ? 210 : (shell % 5 == 3 ? 92 : 255)));
               int g = shell % 5 == 0 ? 76 : (shell % 5 == 1 ? 236 : (shell % 5 == 2 ? 58 : (shell % 5 == 3 ? 204 : 132)));
               int b = 255;
               wrote |= MichaelKeterVfxClient.H.orientedRing(
                  buffer,
                  matrix,
                  center.add(basis.forward.scale(shell * 0.22)),
                  chosen,
                  Math.max(0.1, radius - thick),
                  radius + thick,
                  t * (0.007 + h * 0.014),
                  r,
                  g,
                  b,
                  live * (0.24F + ignition * 0.18F) * (1.0F - shell * 0.03F)
               );
               wrote |= MichaelKeterVfxClient.H.orientedRing(
                  buffer,
                  matrix,
                  center.add(basis.forward.scale(shell * 0.34 + wave * 5.0)),
                  chosen,
                  Math.max(0.1, radius * 0.42 - thick * 0.6),
                  radius * 0.42 + thick,
                  t * -(0.01 + h * 0.012),
                  255,
                  226,
                  255,
                  live * 0.085F
               );
            }
         }

         for (int i = 0; i < 210; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 611000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 612000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 613000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 614000 + i * 29);
            float delay = h3 * 56.0F;
            float wave = MichaelKeterVfxClient.H.smooth(Mth.clamp((t - delay) / (24.0F + h2 * 40.0F), 0.0F, 1.0F));
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Mth.clamp((t - (120.0F + h3 * 58.0F)) / 48.0F, 0.0F, 1.0F));
            if (!(wave <= 0.001F) && !(localFade <= 0.001F)) {
               double yaw = h0 * (float) (Math.PI * 2);
               double pitch = (h1 - 0.42) * Math.PI * 0.92;
               Vec3 dir = basis.right
                  .scale(Math.cos(yaw) * Math.cos(pitch))
                  .add(basis.up.scale(Math.sin(yaw) * Math.cos(pitch)))
                  .add(basis.forward.scale(Math.sin(pitch) * 0.92 + 0.18));
               if (dir.lengthSqr() < 0.001) {
                  dir = basis.forward;
               }

               dir = dir.normalize();
               double dist = (8.0 + h2 * 142.0) * wave;
               Vec3 p = center.add(dir.scale(dist)).add(basis.forward.scale(Math.sin(t * 0.018 + i) * (1.2 + h1 * 4.6)));
               int r = i % 6 == 0 ? 255 : (i % 6 == 1 ? 116 : (i % 6 == 2 ? 210 : (i % 6 == 3 ? 255 : (i % 6 == 4 ? 92 : 170))));
               int g = i % 6 == 0 ? 78 : (i % 6 == 1 ? 236 : (i % 6 == 2 ? 58 : (i % 6 == 3 ? 132 : (i % 6 == 4 ? 204 : 44))));
               int b = 255;
               float size = 0.22F + h2 * 0.72F + (i % 7 == 0 ? 1.4F + h1 * 2.4F : 0.0F);
               float a = live * localFade * (0.36F + h0 * 0.46F);
               wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size, r, g, b, a);
               if (i % 5 == 0) {
                  wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, basis.forward, size * (2.2F + h2 * 2.0F), r, g, b, a * 0.18F);
               }
            }
         }

         return wrote;
      }

      private boolean renderFinalLayeredPhoenixPressure(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float live = alpha * (0.42F + ignition * 0.28F + afterglow * 0.48F);

         for (int wing = -1; wing <= 1; wing += 2) {
            for (int feather = 0; feather < 38; feather++) {
               float h0 = MichaelKeterVfxClient.H.hash(this.seed, 620000 + feather * 17 + wing * 991);
               float h1 = MichaelKeterVfxClient.H.hash(this.seed, 621000 + feather * 19 + wing * 997);
               float delay = feather * 0.55F + h0 * 18.0F;
               float open = MichaelKeterVfxClient.H.smooth(Mth.clamp((t - delay) / 36.0F, 0.0F, 1.0F));
               float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Mth.clamp((t - (112.0F + h1 * 56.0F)) / 58.0F, 0.0F, 1.0F));
               if (!(open <= 0.001F) && !(localFade <= 0.001F)) {
                  double sweep = (0.26 + feather / 38.0 * 1.18) * Math.PI * wing;
                  double radius = (10.0 + feather * 2.45 + h1 * 16.0) * open;
                  Vec3 root = center.add(basis.right.scale(wing * (1.2 + h0 * 2.8))).add(basis.forward.scale(0.4 + h1 * 2.8));
                  Vec3 mid = center.add(basis.right.scale(Math.cos(sweep) * radius))
                     .add(basis.up.scale(Math.sin(sweep) * radius * 0.46))
                     .add(basis.forward.scale(3.0 + open * (10.0 + h0 * 18.0)));
                  Vec3 tip = center.add(basis.right.scale(Math.cos(sweep) * radius * 1.38))
                     .add(basis.up.scale(Math.sin(sweep) * radius * 0.78))
                     .add(basis.forward.scale(6.0 + open * (20.0 + h1 * 30.0)));
                  Vec3 side = basis.up.scale((0.55 + h0 * 1.4) * open).add(basis.right.scale(wing * (0.25 + h1 * 0.75)));
                  int r = feather % 3 == 0 ? 255 : (feather % 3 == 1 ? 116 : 210);
                  int g = feather % 3 == 0 ? 86 : (feather % 3 == 1 ? 236 : 58);
                  int b = 255;
                  float a = live * localFade * (0.18F + h0 * 0.24F);
                  wrote |= MichaelKeterVfxClient.H.quad(
                     buffer, matrix, root.add(side), mid.add(side.scale(0.55)), tip, mid.subtract(side.scale(0.55)), r, g, b, a
                  );
                  wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, tip, basis, 0.28F + h1 * 1.2F, r, g, b, a * 1.35F);
               }
            }
         }

         return wrote;
      }

      private boolean renderFinalHertaMechanicalHalo(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float live = alpha * (0.35F + afterglow * 0.45F);

         for (int ring = 0; ring < 12; ring++) {
            float h = MichaelKeterVfxClient.H.hash(this.seed, 630000 + ring * 37);
            float open = MichaelKeterVfxClient.H.smooth(Mth.clamp((t - ring * 4.0F) / 26.0F, 0.0F, 1.0F));
            if (!(open <= 0.001F)) {
               double radius = (8.0 + ring * 6.0 + h * 10.0) * (0.35 + open * 1.12);
               double y = (ring - 4.5) * (1.25 + shock * 0.8) + open * ring * 0.5;
               MichaelKeterVfxClient.Basis chosen = ring % 3 == 0
                  ? MichaelKeterVfxClient.H.basis(basis.forward)
                  : (ring % 3 == 1 ? MichaelKeterVfxClient.H.basis(basis.right) : MichaelKeterVfxClient.H.basis(basis.up));
               wrote |= MichaelKeterVfxClient.H.orientedRing(
                  buffer,
                  matrix,
                  center.add(basis.forward.scale(y)),
                  chosen,
                  radius - 0.35 - ignition,
                  radius + 0.72 + ignition,
                  t * (0.016 + h * 0.012),
                  ring % 2 == 0 ? 116 : 255,
                  ring % 2 == 0 ? 236 : 92,
                  255,
                  live * (0.2F + ignition * 0.12F)
               );

               for (int node = 0; node < 18; node++) {
                  float hn = MichaelKeterVfxClient.H.hash(this.seed, 631000 + ring * 211 + node * 13);
                  double a = node * (float) (Math.PI * 2) / 18.0 + t * (0.01 + h * 0.018);
                  Vec3 p = center.add(chosen.right.scale(Math.cos(a) * radius)).add(chosen.up.scale(Math.sin(a) * radius)).add(basis.forward.scale(y));
                  int r = node % 4 == 0 ? 255 : (node % 4 == 1 ? 116 : (node % 4 == 2 ? 210 : 92));
                  int g = node % 4 == 0 ? 80 : (node % 4 == 1 ? 236 : (node % 4 == 2 ? 58 : 204));
                  wrote |= MichaelKeterVfxClient.H.orientedDiamond(
                     buffer, matrix, p, chosen, 0.28F + hn * 0.75F, (float)(t * 0.028 + hn * (float) (Math.PI * 2)), r, g, 255, live * (0.32F + hn * 0.25F)
                  );
               }
            }
         }

         return wrote;
      }

      private boolean renderFinalDelayedSecondaryBlooms(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         float shock,
         float ignition,
         float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float[] starts = new float[]{18.0F, 42.0F, 76.0F, 112.0F};

         for (int waveIndex = 0; waveIndex < starts.length; waveIndex++) {
            float local = MichaelKeterVfxClient.H.smooth(Mth.clamp((t - starts[waveIndex]) / 12.0F, 0.0F, 1.0F));
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Mth.clamp((t - starts[waveIndex] - 44.0F) / 38.0F, 0.0F, 1.0F));
            if (!(local <= 0.001F) && !(localFade <= 0.001F)) {
               float live = alpha * local * localFade * (0.34F + afterglow * 0.42F);
               double radius = (18.0 + waveIndex * 18.0) * local;
               wrote |= MichaelKeterVfxClient.H.softBloom(
                  buffer,
                  matrix,
                  center.add(basis.forward.scale(2.0 + waveIndex * 4.0)),
                  basis.forward,
                  (float)(22.0 + radius * 0.62),
                  waveIndex % 2 == 0 ? 255 : 116,
                  waveIndex % 2 == 0 ? 78 : 236,
                  255,
                  live * 0.35F
               );

               for (int i = 0; i < 74; i++) {
                  float h0 = MichaelKeterVfxClient.H.hash(this.seed, 640000 + waveIndex * 1000 + i * 17);
                  float h1 = MichaelKeterVfxClient.H.hash(this.seed, 641000 + waveIndex * 1000 + i * 19);
                  double a = h0 * (float) (Math.PI * 2);
                  double d = radius * (0.32 + h1 * 1.55);
                  Vec3 p = center.add(basis.right.scale(Math.cos(a) * d))
                     .add(basis.up.scale(Math.sin(a) * d))
                     .add(basis.forward.scale((h1 - 0.22) * (18.0 + waveIndex * 10.0) * local));
                  int r = i % 5 == 0 ? 255 : (i % 5 == 1 ? 116 : (i % 5 == 2 ? 210 : (i % 5 == 3 ? 92 : 255)));
                  int g = i % 5 == 0 ? 74 : (i % 5 == 1 ? 236 : (i % 5 == 2 ? 58 : (i % 5 == 3 ? 204 : 132)));
                  float size = 0.12F + h1 * 0.34F + (i % 9 == 0 ? 0.95F + h0 : 0.0F);
                  wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size, r, g, 255, live * (0.42F + h0 * 0.36F));
               }
            }
         }

         return wrote;
      }

      private boolean renderFinalLingeringStardustAftermath(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha, float shock, float afterglow
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float live = alpha * afterglow;

         for (int i = 0; i < 520; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 650000 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 651000 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 652000 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 653000 + i * 29);
            float drift = MichaelKeterVfxClient.H.smooth(Mth.clamp((t - h3 * 80.0F) / (70.0F + h2 * 90.0F), 0.0F, 1.0F));
            float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Mth.clamp((t - (138.0F + h3 * 62.0F)) / 64.0F, 0.0F, 1.0F));
            if (!(localFade <= 0.001F)) {
               double yaw = h0 * (float) (Math.PI * 2) + drift * (0.8 + h1 * 2.4);
               double pitch = (h1 - 0.5) * Math.PI;
               double dist = 10.0 + h2 * 118.0 + drift * (18.0 + h0 * 42.0);
               Vec3 dir = basis.right
                  .scale(Math.cos(yaw) * Math.cos(pitch))
                  .add(basis.up.scale(Math.sin(yaw) * Math.cos(pitch)))
                  .add(basis.forward.scale(Math.sin(pitch) * 0.55 + 0.2));
               if (dir.lengthSqr() < 0.001) {
                  dir = basis.forward;
               }

               dir = dir.normalize();
               Vec3 p = center.add(dir.scale(dist)).add(basis.forward.scale(Math.sin(t * 0.011 + i) * (1.0 + h3 * 5.0)));
               int r = i % 8 == 0
                  ? 255
                  : (i % 8 == 1 ? 116 : (i % 8 == 2 ? 210 : (i % 8 == 3 ? 255 : (i % 8 == 4 ? 92 : (i % 8 == 5 ? 255 : (i % 8 == 6 ? 170 : 120))))));
               int g = i % 8 == 0
                  ? 76
                  : (i % 8 == 1 ? 236 : (i % 8 == 2 ? 58 : (i % 8 == 3 ? 132 : (i % 8 == 4 ? 204 : (i % 8 == 5 ? 92 : (i % 8 == 6 ? 44 : 220))))));
               int b = i % 8 == 5 ? 214 : 255;
               float size = 0.045F + h2 * 0.16F + (i % 37 == 0 ? 1.85F + h1 * 2.4F : 0.0F) + (i % 13 == 0 ? 0.35F + h3 * 0.65F : 0.0F);
               float a = live * localFade * (0.28F + h1 * 0.42F);
               if (i % 13 != 0 && i % 37 != 0) {
                  wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, size, r, g, b, a);
               } else {
                  wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, size, r, g, b, a);
               }
            }
         }

         return wrote;
      }

      private boolean renderGeneratedUltraFinalLayers(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha
      ) {
         boolean wrote = false;
         wrote |= this.renderUltraFinalLayer0(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer1(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer2(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer3(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer4(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer5(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer6(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer7(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer8(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer9(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer10(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer11(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer12(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer13(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer14(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer15(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer16(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer17(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer18(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer19(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer20(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer21(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer22(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer23(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer24(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer25(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer26(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer27(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer28(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer29(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer30(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer31(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer32(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer33(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer34(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer35(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer36(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer37(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer38(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer39(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer40(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer41(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer42(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer43(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer44(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer45(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer46(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer47(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer48(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer49(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer50(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer51(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer52(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer53(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer54(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer55(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer56(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer57(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer58(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer59(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer60(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer61(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer62(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer63(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer64(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer65(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer66(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer67(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer68(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer69(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer70(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer71(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer72(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer73(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer74(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer75(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer76(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer77(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer78(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer79(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer80(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer81(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer82(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer83(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer84(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer85(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer86(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer87(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer88(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer89(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer90(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer91(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer92(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer93(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer94(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer95(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer96(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer97(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer98(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer99(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer100(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer101(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer102(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer103(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer104(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer105(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer106(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer107(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer108(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer109(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer110(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer111(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer112(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer113(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer114(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer115(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer116(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer117(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer118(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer119(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer120(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer121(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer122(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer123(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer124(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer125(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer126(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer127(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer128(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer129(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer130(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer131(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer132(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer133(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer134(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer135(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer136(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer137(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer138(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer139(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer140(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer141(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer142(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer143(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer144(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer145(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer146(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer147(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer148(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer149(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer150(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer151(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer152(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer153(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer154(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer155(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer156(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer157(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer158(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer159(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer160(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer161(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer162(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer163(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer164(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer165(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer166(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer167(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer168(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer169(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer170(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer171(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer172(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer173(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer174(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer175(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer176(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer177(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer178(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer179(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer180(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer181(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer182(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer183(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer184(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer185(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer186(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer187(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer188(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer189(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer190(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer191(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer192(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer193(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer194(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer195(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer196(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer197(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer198(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer199(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer200(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer201(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer202(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer203(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer204(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer205(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer206(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer207(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer208(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer209(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer210(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer211(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer212(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer213(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer214(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer215(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer216(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer217(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer218(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer219(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer220(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer221(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer222(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer223(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer224(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer225(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer226(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer227(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer228(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer229(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer230(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer231(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer232(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer233(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer234(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer235(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer236(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer237(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer238(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer239(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer240(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer241(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer242(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer243(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer244(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer245(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer246(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer247(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer248(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer249(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer250(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer251(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer252(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer253(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer254(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer255(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer256(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer257(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer258(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer259(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer260(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer261(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer262(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer263(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer264(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer265(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer266(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer267(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer268(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer269(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer270(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer271(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer272(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer273(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer274(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer275(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer276(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer277(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer278(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderUltraFinalLayer279(buffer, matrix, center, basis, t, alpha);
         return wrote | this.renderUltraFinalLayer280(buffer, matrix, center, basis, t, alpha);
      }

      private boolean renderUltraFinalBurstLayer(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         int index,
         float radiusScale,
         float heightScale,
         float starScale,
         float spinScale,
         int red,
         int green,
         int blue
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float open = MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (6.0F + index % 7)));
         float localFade = 1.0F - MichaelKeterVfxClient.H.smooth(Math.max(0.0F, (t - (42.0F + index % 23 * 1.7F)) / 48.0F));
         float live = alpha * open * localFade * (1.0F - Math.min(0.62F, index * 0.0014F));
         int count = 5 + index % 4;

         for (int i = 0; i < count; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 500000 + index * 277 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 510000 + index * 277 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 520000 + index * 277 + i * 23);
            float h3 = MichaelKeterVfxClient.H.hash(this.seed, 530000 + index * 277 + i * 29);
            double angle = h0 * (float) (Math.PI * 2) + t * spinScale + index * 0.071;
            double radius = (2.0 + index * 0.16 + h1 * 14.0) * radiusScale * open;
            double height = ((h2 - 0.32) * heightScale + Math.sin(t * 0.017 + index + i) * heightScale * 0.18) * open;
            Vec3 p = center.add(basis.right.scale(Math.cos(angle) * radius)).add(basis.up.scale(Math.sin(angle) * radius)).add(basis.forward.scale(height));
            float star = starScale * (0.1F + h2 * 0.34F + ((index + i) % 19 == 0 ? 0.88F : 0.0F));
            float a = live * (0.18F + h3 * 0.34F);
            if ((index + i & 3) == 0) {
               wrote |= MichaelKeterVfxClient.H.bigStar(buffer, matrix, p, basis, star, red, green, blue, a);
            } else {
               wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, star, red, green, blue, a);
            }

            if ((index + i) % 13 == 0) {
               wrote |= MichaelKeterVfxClient.H.orientedDiamond(
                  buffer,
                  matrix,
                  p.add(basis.forward.scale(star * 0.24)),
                  basis,
                  star * 0.44F,
                  (float)(t * spinScale * 3.0 + h0 * (float) (Math.PI * 2)),
                  red,
                  green,
                  blue,
                  a * 0.56F
               );
            }

            if ((index + i) % 29 == 0) {
               wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, p, basis.forward, star * 2.8F, red, green, blue, a * 0.16F);
            }
         }

         return wrote;
      }

      private boolean renderUltraFinalLayer0(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 0, 0.95F, 8.0F, 0.55F, 0.0045F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer1(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 1, 1.005F, 8.65F, 0.595F, 0.0058F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer2(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 2, 1.06F, 9.3F, 0.64F, 0.0071F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer3(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 3, 1.115F, 9.95F, 0.685F, 0.0084F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer4(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 4, 1.17F, 10.6F, 0.73F, 0.0097F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer5(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 5, 1.225F, 11.25F, 0.775F, 0.011F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer6(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 6, 1.28F, 11.9F, 0.82F, 0.0123F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer7(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 7, 1.335F, 12.55F, 0.865F, 0.0136F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer8(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 8, 1.39F, 13.2F, 0.91F, 0.0149F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer9(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 9, 1.445F, 13.85F, 0.955F, 0.0162F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer10(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 10, 1.5F, 14.5F, 1.0F, 0.0175F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer11(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 11, 1.555F, 15.15F, 0.55F, 0.0188F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer12(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 12, 1.61F, 15.8F, 0.595F, 0.0201F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer13(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 13, 1.665F, 16.45F, 0.64F, 0.0045F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer14(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 14, 1.72F, 17.1F, 0.685F, 0.0058F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer15(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 15, 1.775F, 17.75F, 0.73F, 0.0071F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer16(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 16, 1.83F, 18.4F, 0.775F, 0.0084F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer17(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 17, 0.95F, 19.05F, 0.82F, 0.0097F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer18(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 18, 1.005F, 19.7F, 0.865F, 0.011F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer19(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 19, 1.06F, 20.35F, 0.91F, 0.0123F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer20(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 20, 1.115F, 21.0F, 0.955F, 0.0136F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer21(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 21, 1.17F, 21.65F, 1.0F, 0.0149F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer22(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 22, 1.225F, 22.3F, 0.55F, 0.0162F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer23(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 23, 1.28F, 8.0F, 0.595F, 0.0175F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer24(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 24, 1.335F, 8.65F, 0.64F, 0.0188F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer25(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 25, 1.39F, 9.3F, 0.685F, 0.0201F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer26(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 26, 1.445F, 9.95F, 0.73F, 0.0045F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer27(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 27, 1.5F, 10.6F, 0.775F, 0.0058F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer28(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 28, 1.555F, 11.25F, 0.82F, 0.0071F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer29(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 29, 1.61F, 11.9F, 0.865F, 0.0084F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer30(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 30, 1.665F, 12.55F, 0.91F, 0.0097F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer31(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 31, 1.72F, 13.2F, 0.955F, 0.011F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer32(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 32, 1.775F, 13.85F, 1.0F, 0.0123F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer33(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 33, 1.83F, 14.5F, 0.55F, 0.0136F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer34(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 34, 0.95F, 15.15F, 0.595F, 0.0149F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer35(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 35, 1.005F, 15.8F, 0.64F, 0.0162F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer36(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 36, 1.06F, 16.45F, 0.685F, 0.0175F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer37(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 37, 1.115F, 17.1F, 0.73F, 0.0188F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer38(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 38, 1.17F, 17.75F, 0.775F, 0.0201F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer39(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 39, 1.225F, 18.4F, 0.82F, 0.0045F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer40(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 40, 1.28F, 19.05F, 0.865F, 0.0058F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer41(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 41, 1.335F, 19.7F, 0.91F, 0.0071F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer42(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 42, 1.39F, 20.35F, 0.955F, 0.0084F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer43(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 43, 1.445F, 21.0F, 1.0F, 0.0097F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer44(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 44, 1.5F, 21.65F, 0.55F, 0.011F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer45(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 45, 1.555F, 22.3F, 0.595F, 0.0123F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer46(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 46, 1.61F, 8.0F, 0.64F, 0.0136F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer47(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 47, 1.665F, 8.65F, 0.685F, 0.0149F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer48(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 48, 1.72F, 9.3F, 0.73F, 0.0162F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer49(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 49, 1.775F, 9.95F, 0.775F, 0.0175F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer50(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 50, 1.83F, 10.6F, 0.82F, 0.0188F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer51(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 51, 0.95F, 11.25F, 0.865F, 0.0201F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer52(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 52, 1.005F, 11.9F, 0.91F, 0.0045F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer53(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 53, 1.06F, 12.55F, 0.955F, 0.0058F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer54(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 54, 1.115F, 13.2F, 1.0F, 0.0071F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer55(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 55, 1.17F, 13.85F, 0.55F, 0.0084F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer56(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 56, 1.225F, 14.5F, 0.595F, 0.0097F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer57(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 57, 1.28F, 15.15F, 0.64F, 0.011F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer58(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 58, 1.335F, 15.8F, 0.685F, 0.0123F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer59(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 59, 1.39F, 16.45F, 0.73F, 0.0136F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer60(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 60, 1.445F, 17.1F, 0.775F, 0.0149F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer61(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 61, 1.5F, 17.75F, 0.82F, 0.0162F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer62(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 62, 1.555F, 18.4F, 0.865F, 0.0175F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer63(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 63, 1.61F, 19.05F, 0.91F, 0.0188F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer64(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 64, 1.665F, 19.7F, 0.955F, 0.0201F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer65(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 65, 1.72F, 20.35F, 1.0F, 0.0045F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer66(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 66, 1.775F, 21.0F, 0.55F, 0.0058F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer67(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 67, 1.83F, 21.65F, 0.595F, 0.0071F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer68(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 68, 0.95F, 22.3F, 0.64F, 0.0084F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer69(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 69, 1.005F, 8.0F, 0.685F, 0.0097F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer70(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 70, 1.06F, 8.65F, 0.73F, 0.011F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer71(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 71, 1.115F, 9.3F, 0.775F, 0.0123F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer72(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 72, 1.17F, 9.95F, 0.82F, 0.0136F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer73(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 73, 1.225F, 10.6F, 0.865F, 0.0149F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer74(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 74, 1.28F, 11.25F, 0.91F, 0.0162F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer75(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 75, 1.335F, 11.9F, 0.955F, 0.0175F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer76(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 76, 1.39F, 12.55F, 1.0F, 0.0188F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer77(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 77, 1.445F, 13.2F, 0.55F, 0.0201F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer78(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 78, 1.5F, 13.85F, 0.595F, 0.0045F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer79(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 79, 1.555F, 14.5F, 0.64F, 0.0058F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer80(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 80, 1.61F, 15.15F, 0.685F, 0.0071F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer81(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 81, 1.665F, 15.8F, 0.73F, 0.0084F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer82(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 82, 1.72F, 16.45F, 0.775F, 0.0097F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer83(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 83, 1.775F, 17.1F, 0.82F, 0.011F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer84(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 84, 1.83F, 17.75F, 0.865F, 0.0123F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer85(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 85, 0.95F, 18.4F, 0.91F, 0.0136F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer86(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 86, 1.005F, 19.05F, 0.955F, 0.0149F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer87(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 87, 1.06F, 19.7F, 1.0F, 0.0162F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer88(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 88, 1.115F, 20.35F, 0.55F, 0.0175F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer89(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 89, 1.17F, 21.0F, 0.595F, 0.0188F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer90(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 90, 1.225F, 21.65F, 0.64F, 0.0201F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer91(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 91, 1.28F, 22.3F, 0.685F, 0.0045F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer92(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 92, 1.335F, 8.0F, 0.73F, 0.0058F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer93(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 93, 1.39F, 8.65F, 0.775F, 0.0071F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer94(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 94, 1.445F, 9.3F, 0.82F, 0.0084F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer95(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 95, 1.5F, 9.95F, 0.865F, 0.0097F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer96(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 96, 1.555F, 10.6F, 0.91F, 0.011F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer97(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 97, 1.61F, 11.25F, 0.955F, 0.0123F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer98(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 98, 1.665F, 11.9F, 1.0F, 0.0136F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer99(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 99, 1.72F, 12.55F, 0.55F, 0.0149F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer100(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 100, 1.775F, 13.2F, 0.595F, 0.0162F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer101(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 101, 1.83F, 13.85F, 0.64F, 0.0175F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer102(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 102, 0.95F, 14.5F, 0.685F, 0.0188F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer103(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 103, 1.005F, 15.15F, 0.73F, 0.0201F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer104(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 104, 1.06F, 15.8F, 0.775F, 0.0045F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer105(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 105, 1.115F, 16.45F, 0.82F, 0.0058F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer106(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 106, 1.17F, 17.1F, 0.865F, 0.0071F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer107(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 107, 1.225F, 17.75F, 0.91F, 0.0084F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer108(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 108, 1.28F, 18.4F, 0.955F, 0.0097F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer109(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 109, 1.335F, 19.05F, 1.0F, 0.011F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer110(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 110, 1.39F, 19.7F, 0.55F, 0.0123F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer111(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 111, 1.445F, 20.35F, 0.595F, 0.0136F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer112(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 112, 1.5F, 21.0F, 0.64F, 0.0149F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer113(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 113, 1.555F, 21.65F, 0.685F, 0.0162F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer114(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 114, 1.61F, 22.3F, 0.73F, 0.0175F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer115(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 115, 1.665F, 8.0F, 0.775F, 0.0188F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer116(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 116, 1.72F, 8.65F, 0.82F, 0.0201F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer117(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 117, 1.775F, 9.3F, 0.865F, 0.0045F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer118(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 118, 1.83F, 9.95F, 0.91F, 0.0058F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer119(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 119, 0.95F, 10.6F, 0.955F, 0.0071F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer120(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 120, 1.005F, 11.25F, 1.0F, 0.0084F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer121(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 121, 1.06F, 11.9F, 0.55F, 0.0097F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer122(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 122, 1.115F, 12.55F, 0.595F, 0.011F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer123(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 123, 1.17F, 13.2F, 0.64F, 0.0123F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer124(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 124, 1.225F, 13.85F, 0.685F, 0.0136F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer125(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 125, 1.28F, 14.5F, 0.73F, 0.0149F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer126(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 126, 1.335F, 15.15F, 0.775F, 0.0162F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer127(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 127, 1.39F, 15.8F, 0.82F, 0.0175F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer128(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 128, 1.445F, 16.45F, 0.865F, 0.0188F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer129(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 129, 1.5F, 17.1F, 0.91F, 0.0201F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer130(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 130, 1.555F, 17.75F, 0.955F, 0.0045F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer131(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 131, 1.61F, 18.4F, 1.0F, 0.0058F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer132(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 132, 1.665F, 19.05F, 0.55F, 0.0071F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer133(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 133, 1.72F, 19.7F, 0.595F, 0.0084F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer134(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 134, 1.775F, 20.35F, 0.64F, 0.0097F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer135(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 135, 1.83F, 21.0F, 0.685F, 0.011F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer136(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 136, 0.95F, 21.65F, 0.73F, 0.0123F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer137(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 137, 1.005F, 22.3F, 0.775F, 0.0136F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer138(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 138, 1.06F, 8.0F, 0.82F, 0.0149F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer139(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 139, 1.115F, 8.65F, 0.865F, 0.0162F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer140(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 140, 1.17F, 9.3F, 0.91F, 0.0175F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer141(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 141, 1.225F, 9.95F, 0.955F, 0.0188F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer142(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 142, 1.28F, 10.6F, 1.0F, 0.0201F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer143(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 143, 1.335F, 11.25F, 0.55F, 0.0045F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer144(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 144, 1.39F, 11.9F, 0.595F, 0.0058F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer145(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 145, 1.445F, 12.55F, 0.64F, 0.0071F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer146(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 146, 1.5F, 13.2F, 0.685F, 0.0084F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer147(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 147, 1.555F, 13.85F, 0.73F, 0.0097F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer148(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 148, 1.61F, 14.5F, 0.775F, 0.011F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer149(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 149, 1.665F, 15.15F, 0.82F, 0.0123F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer150(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 150, 1.72F, 15.8F, 0.865F, 0.0136F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer151(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 151, 1.775F, 16.45F, 0.91F, 0.0149F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer152(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 152, 1.83F, 17.1F, 0.955F, 0.0162F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer153(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 153, 0.95F, 17.75F, 1.0F, 0.0175F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer154(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 154, 1.005F, 18.4F, 0.55F, 0.0188F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer155(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 155, 1.06F, 19.05F, 0.595F, 0.0201F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer156(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 156, 1.115F, 19.7F, 0.64F, 0.0045F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer157(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 157, 1.17F, 20.35F, 0.685F, 0.0058F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer158(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 158, 1.225F, 21.0F, 0.73F, 0.0071F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer159(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 159, 1.28F, 21.65F, 0.775F, 0.0084F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer160(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 160, 1.335F, 22.3F, 0.82F, 0.0097F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer161(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 161, 1.39F, 8.0F, 0.865F, 0.011F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer162(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 162, 1.445F, 8.65F, 0.91F, 0.0123F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer163(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 163, 1.5F, 9.3F, 0.955F, 0.0136F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer164(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 164, 1.555F, 9.95F, 1.0F, 0.0149F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer165(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 165, 1.61F, 10.6F, 0.55F, 0.0162F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer166(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 166, 1.665F, 11.25F, 0.595F, 0.0175F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer167(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 167, 1.72F, 11.9F, 0.64F, 0.0188F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer168(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 168, 1.775F, 12.55F, 0.685F, 0.0201F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer169(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 169, 1.83F, 13.2F, 0.73F, 0.0045F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer170(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 170, 0.95F, 13.85F, 0.775F, 0.0058F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer171(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 171, 1.005F, 14.5F, 0.82F, 0.0071F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer172(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 172, 1.06F, 15.15F, 0.865F, 0.0084F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer173(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 173, 1.115F, 15.8F, 0.91F, 0.0097F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer174(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 174, 1.17F, 16.45F, 0.955F, 0.011F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer175(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 175, 1.225F, 17.1F, 1.0F, 0.0123F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer176(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 176, 1.28F, 17.75F, 0.55F, 0.0136F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer177(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 177, 1.335F, 18.4F, 0.595F, 0.0149F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer178(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 178, 1.39F, 19.05F, 0.64F, 0.0162F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer179(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 179, 1.445F, 19.7F, 0.685F, 0.0175F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer180(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 180, 1.5F, 20.35F, 0.73F, 0.0188F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer181(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 181, 1.555F, 21.0F, 0.775F, 0.0201F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer182(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 182, 1.61F, 21.65F, 0.82F, 0.0045F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer183(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 183, 1.665F, 22.3F, 0.865F, 0.0058F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer184(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 184, 1.72F, 8.0F, 0.91F, 0.0071F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer185(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 185, 1.775F, 8.65F, 0.955F, 0.0084F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer186(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 186, 1.83F, 9.3F, 1.0F, 0.0097F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer187(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 187, 0.95F, 9.95F, 0.55F, 0.011F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer188(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 188, 1.005F, 10.6F, 0.595F, 0.0123F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer189(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 189, 1.06F, 11.25F, 0.64F, 0.0136F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer190(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 190, 1.115F, 11.9F, 0.685F, 0.0149F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer191(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 191, 1.17F, 12.55F, 0.73F, 0.0162F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer192(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 192, 1.225F, 13.2F, 0.775F, 0.0175F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer193(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 193, 1.28F, 13.85F, 0.82F, 0.0188F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer194(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 194, 1.335F, 14.5F, 0.865F, 0.0201F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer195(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 195, 1.39F, 15.15F, 0.91F, 0.0045F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer196(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 196, 1.445F, 15.8F, 0.955F, 0.0058F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer197(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 197, 1.5F, 16.45F, 1.0F, 0.0071F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer198(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 198, 1.555F, 17.1F, 0.55F, 0.0084F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer199(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 199, 1.61F, 17.75F, 0.595F, 0.0097F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer200(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 200, 1.665F, 18.4F, 0.64F, 0.011F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer201(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 201, 1.72F, 19.05F, 0.685F, 0.0123F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer202(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 202, 1.775F, 19.7F, 0.73F, 0.0136F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer203(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 203, 1.83F, 20.35F, 0.775F, 0.0149F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer204(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 204, 0.95F, 21.0F, 0.82F, 0.0162F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer205(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 205, 1.005F, 21.65F, 0.865F, 0.0175F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer206(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 206, 1.06F, 22.3F, 0.91F, 0.0188F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer207(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 207, 1.115F, 8.0F, 0.955F, 0.0201F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer208(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 208, 1.17F, 8.65F, 1.0F, 0.0045F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer209(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 209, 1.225F, 9.3F, 0.55F, 0.0058F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer210(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 210, 1.28F, 9.95F, 0.595F, 0.0071F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer211(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 211, 1.335F, 10.6F, 0.64F, 0.0084F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer212(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 212, 1.39F, 11.25F, 0.685F, 0.0097F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer213(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 213, 1.445F, 11.9F, 0.73F, 0.011F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer214(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 214, 1.5F, 12.55F, 0.775F, 0.0123F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer215(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 215, 1.555F, 13.2F, 0.82F, 0.0136F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer216(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 216, 1.61F, 13.85F, 0.865F, 0.0149F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer217(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 217, 1.665F, 14.5F, 0.91F, 0.0162F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer218(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 218, 1.72F, 15.15F, 0.955F, 0.0175F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer219(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 219, 1.775F, 15.8F, 1.0F, 0.0188F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer220(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 220, 1.83F, 16.45F, 0.55F, 0.0201F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer221(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 221, 0.95F, 17.1F, 0.595F, 0.0045F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer222(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 222, 1.005F, 17.75F, 0.64F, 0.0058F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer223(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 223, 1.06F, 18.4F, 0.685F, 0.0071F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer224(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 224, 1.115F, 19.05F, 0.73F, 0.0084F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer225(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 225, 1.17F, 19.7F, 0.775F, 0.0097F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer226(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 226, 1.225F, 20.35F, 0.82F, 0.011F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer227(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 227, 1.28F, 21.0F, 0.865F, 0.0123F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer228(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 228, 1.335F, 21.65F, 0.91F, 0.0136F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer229(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 229, 1.39F, 22.3F, 0.955F, 0.0149F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer230(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 230, 1.445F, 8.0F, 1.0F, 0.0162F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer231(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 231, 1.5F, 8.65F, 0.55F, 0.0175F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer232(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 232, 1.555F, 9.3F, 0.595F, 0.0188F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer233(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 233, 1.61F, 9.95F, 0.64F, 0.0201F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer234(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 234, 1.665F, 10.6F, 0.685F, 0.0045F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer235(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 235, 1.72F, 11.25F, 0.73F, 0.0058F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer236(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 236, 1.775F, 11.9F, 0.775F, 0.0071F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer237(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 237, 1.83F, 12.55F, 0.82F, 0.0084F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer238(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 238, 0.95F, 13.2F, 0.865F, 0.0097F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer239(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 239, 1.005F, 13.85F, 0.91F, 0.011F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer240(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 240, 1.06F, 14.5F, 0.955F, 0.0123F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer241(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 241, 1.115F, 15.15F, 1.0F, 0.0136F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer242(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 242, 1.17F, 15.8F, 0.55F, 0.0149F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer243(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 243, 1.225F, 16.45F, 0.595F, 0.0162F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer244(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 244, 1.28F, 17.1F, 0.64F, 0.0175F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer245(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 245, 1.335F, 17.75F, 0.685F, 0.0188F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer246(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 246, 1.39F, 18.4F, 0.73F, 0.0201F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer247(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 247, 1.445F, 19.05F, 0.775F, 0.0045F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer248(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 248, 1.5F, 19.7F, 0.82F, 0.0058F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer249(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 249, 1.555F, 20.35F, 0.865F, 0.0071F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer250(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 250, 1.61F, 21.0F, 0.91F, 0.0084F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer251(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 251, 1.665F, 21.65F, 0.955F, 0.0097F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer252(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 252, 1.72F, 22.3F, 1.0F, 0.011F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer253(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 253, 1.775F, 8.0F, 0.55F, 0.0123F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer254(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 254, 1.83F, 8.65F, 0.595F, 0.0136F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer255(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 255, 0.95F, 9.3F, 0.64F, 0.0149F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer256(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 256, 1.005F, 9.95F, 0.685F, 0.0162F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer257(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 257, 1.06F, 10.6F, 0.73F, 0.0175F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer258(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 258, 1.115F, 11.25F, 0.775F, 0.0188F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer259(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 259, 1.17F, 11.9F, 0.82F, 0.0201F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer260(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 260, 1.225F, 12.55F, 0.865F, 0.0045F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer261(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 261, 1.28F, 13.2F, 0.91F, 0.0058F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer262(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 262, 1.335F, 13.85F, 0.955F, 0.0071F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer263(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 263, 1.39F, 14.5F, 1.0F, 0.0084F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer264(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 264, 1.445F, 15.15F, 0.55F, 0.0097F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer265(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 265, 1.5F, 15.8F, 0.595F, 0.011F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer266(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 266, 1.555F, 16.45F, 0.64F, 0.0123F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer267(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 267, 1.61F, 17.1F, 0.685F, 0.0136F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer268(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 268, 1.665F, 17.75F, 0.73F, 0.0149F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer269(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 269, 1.72F, 18.4F, 0.775F, 0.0162F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer270(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 270, 1.775F, 19.05F, 0.82F, 0.0175F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer271(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 271, 1.83F, 19.7F, 0.865F, 0.0188F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer272(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 272, 0.95F, 20.35F, 0.91F, 0.0201F, 255, 72, 232);
      }

      private boolean renderUltraFinalLayer273(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 273, 1.005F, 21.0F, 0.955F, 0.0045F, 116, 236, 255);
      }

      private boolean renderUltraFinalLayer274(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 274, 1.06F, 21.65F, 1.0F, 0.0058F, 210, 58, 255);
      }

      private boolean renderUltraFinalLayer275(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 275, 1.115F, 22.3F, 0.55F, 0.0071F, 255, 132, 180);
      }

      private boolean renderUltraFinalLayer276(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 276, 1.17F, 8.0F, 0.595F, 0.0084F, 92, 204, 255);
      }

      private boolean renderUltraFinalLayer277(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 277, 1.225F, 8.65F, 0.64F, 0.0097F, 255, 96, 210);
      }

      private boolean renderUltraFinalLayer278(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 278, 1.28F, 9.3F, 0.685F, 0.011F, 170, 44, 255);
      }

      private boolean renderUltraFinalLayer279(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 279, 1.335F, 9.95F, 0.73F, 0.0123F, 255, 210, 248);
      }

      private boolean renderUltraFinalLayer280(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderUltraFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 280, 1.39F, 10.6F, 0.775F, 0.0136F, 255, 72, 232);
      }

      private boolean renderStartExplosion(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float pulse = 1.0F + 0.38F * Mth.sin(t * 0.65F);
         wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, center, basis.forward, 4.8F * pulse, 255, 70, 232, alpha * 0.86F);
         wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, center, basis.forward, 2.9F * pulse, 116, 236, 255, alpha * 0.74F);

         for (int i = 0; i < 180; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 20000 + i * 13);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 21000 + i * 17);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 22000 + i * 19);
            double yaw = h0 * (float) (Math.PI * 2);
            double pitch = (h1 - 0.5) * Math.PI;
            double dist = (0.8 + h2 * 5.2) * (0.28 + t * 0.42);
            Vec3 off = basis.forward
               .scale(Math.cos(pitch) * Math.cos(yaw) * dist)
               .add(basis.right.scale(Math.sin(yaw) * Math.cos(pitch) * dist))
               .add(basis.up.scale(Math.sin(pitch) * dist));
            wrote |= MichaelKeterVfxClient.H.star(
               buffer, matrix, center.add(off), 0.045F + h2 * 0.07F, i % 3 == 0 ? 255 : 118, i % 3 == 0 ? 82 : 236, 255, alpha * (0.5F + h2 * 0.34F)
            );
         }

         return wrote;
      }

      private boolean renderImpactExplosion(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float pulse = 1.0F + 0.3F * Mth.sin(t * 0.55F + 1.7F);
         wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, center, basis.forward, 1.85F * pulse, 255, 74, 224, alpha * 0.54F);
         wrote |= MichaelKeterVfxClient.H.softBloom(buffer, matrix, center, basis.forward, 1.12F * pulse, 116, 236, 255, alpha * 0.48F);

         for (int i = 0; i < 44; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 25000 + i * 31);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 26000 + i * 37);
            Vec3 p = center.add(basis.right.scale((h0 - 0.5) * 2.2)).add(basis.up.scale((h1 - 0.5) * 2.2));
            wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, 0.035F + h1 * 0.06F, i % 2 == 0 ? 255 : 118, i % 2 == 0 ? 74 : 236, 255, alpha * 0.58F);
         }

         return wrote;
      }

      private boolean renderGeneratedFinalLayers(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         boolean wrote = false;
         wrote |= this.renderFinalLayer0(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer1(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer2(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer3(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer4(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer5(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer6(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer7(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer8(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer9(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer10(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer11(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer12(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer13(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer14(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer15(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer16(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer17(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer18(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer19(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer20(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer21(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer22(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer23(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer24(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer25(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer26(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer27(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer28(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer29(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer30(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer31(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer32(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer33(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer34(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer35(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer36(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer37(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer38(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer39(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer40(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer41(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer42(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer43(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer44(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer45(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer46(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer47(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer48(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer49(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer50(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer51(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer52(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer53(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer54(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer55(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer56(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer57(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer58(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer59(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer60(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer61(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer62(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer63(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer64(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer65(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer66(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer67(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer68(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer69(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer70(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer71(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer72(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer73(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer74(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer75(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer76(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer77(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer78(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer79(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer80(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer81(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer82(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer83(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer84(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer85(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer86(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer87(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer88(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer89(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer90(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer91(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer92(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer93(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer94(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer95(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer96(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer97(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer98(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer99(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer100(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer101(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer102(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer103(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer104(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer105(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer106(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer107(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer108(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer109(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer110(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer111(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer112(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer113(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer114(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer115(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer116(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer117(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer118(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer119(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer120(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer121(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer122(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer123(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer124(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer125(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer126(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer127(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer128(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer129(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer130(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer131(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer132(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer133(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer134(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer135(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer136(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer137(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer138(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer139(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer140(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer141(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer142(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer143(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer144(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer145(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer146(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer147(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer148(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer149(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer150(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer151(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer152(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer153(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer154(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer155(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer156(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer157(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer158(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer159(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer160(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer161(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer162(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer163(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer164(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer165(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer166(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer167(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer168(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer169(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer170(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer171(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer172(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer173(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer174(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer175(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer176(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer177(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer178(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer179(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer180(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer181(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer182(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer183(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer184(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer185(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer186(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer187(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer188(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer189(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer190(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer191(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer192(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer193(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer194(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer195(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer196(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer197(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer198(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer199(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer200(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer201(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer202(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer203(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer204(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer205(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer206(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer207(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer208(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer209(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer210(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer211(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer212(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer213(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer214(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer215(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer216(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer217(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer218(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer219(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer220(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer221(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer222(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer223(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer224(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer225(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer226(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer227(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer228(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer229(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer230(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer231(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer232(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer233(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer234(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer235(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer236(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer237(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer238(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer239(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer240(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer241(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer242(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer243(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer244(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer245(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer246(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer247(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer248(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer249(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer250(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer251(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer252(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer253(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer254(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer255(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer256(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer257(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer258(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer259(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer260(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer261(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer262(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer263(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer264(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer265(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer266(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer267(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer268(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer269(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer270(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer271(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer272(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer273(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer274(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer275(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer276(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer277(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer278(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer279(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer280(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer281(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer282(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer283(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer284(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer285(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer286(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer287(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer288(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer289(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer290(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer291(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer292(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer293(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer294(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer295(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer296(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer297(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer298(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer299(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer300(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer301(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer302(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer303(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer304(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer305(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer306(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer307(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer308(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer309(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer310(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer311(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer312(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer313(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer314(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer315(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer316(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer317(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer318(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer319(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer320(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer321(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer322(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer323(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer324(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer325(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer326(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer327(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer328(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer329(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer330(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer331(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer332(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer333(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer334(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer335(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer336(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer337(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer338(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer339(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer340(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer341(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer342(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer343(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer344(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer345(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer346(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer347(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer348(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer349(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer350(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer351(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer352(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer353(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer354(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer355(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer356(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer357(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer358(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer359(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer360(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer361(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer362(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer363(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer364(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer365(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer366(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer367(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer368(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer369(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer370(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer371(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer372(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer373(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer374(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer375(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer376(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer377(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer378(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer379(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer380(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer381(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer382(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer383(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer384(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer385(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer386(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer387(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer388(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer389(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer390(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer391(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer392(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer393(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer394(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer395(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer396(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer397(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer398(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer399(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer400(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer401(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer402(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer403(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer404(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer405(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer406(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer407(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer408(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer409(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer410(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer411(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer412(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer413(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer414(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer415(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer416(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer417(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer418(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer419(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer420(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer421(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer422(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer423(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer424(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer425(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer426(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer427(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer428(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer429(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer430(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer431(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer432(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer433(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer434(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer435(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer436(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer437(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer438(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer439(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer440(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer441(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer442(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer443(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer444(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer445(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer446(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer447(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer448(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer449(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer450(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer451(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer452(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer453(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer454(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer455(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer456(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer457(buffer, matrix, center, basis, t, alpha);
         wrote |= this.renderFinalLayer458(buffer, matrix, center, basis, t, alpha);
         return wrote | this.renderFinalLayer459(buffer, matrix, center, basis, t, alpha);
      }

      private boolean renderFinalBurstLayer(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 center,
         MichaelKeterVfxClient.Basis basis,
         float t,
         float alpha,
         int index,
         float radiusScale,
         float heightScale,
         float spinScale,
         int red,
         int green,
         int blue
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         int count = 7;

         for (int i = 0; i < count; i++) {
            float h0 = MichaelKeterVfxClient.H.hash(this.seed, 84000 + index * 211 + i * 17);
            float h1 = MichaelKeterVfxClient.H.hash(this.seed, 85000 + index * 211 + i * 19);
            float h2 = MichaelKeterVfxClient.H.hash(this.seed, 86000 + index * 211 + i * 23);
            double a = h0 * (float) (Math.PI * 2) + t * spinScale + index * 0.137;
            double r = (1.2 + index * 0.06 + h1 * 7.0) * radiusScale * MichaelKeterVfxClient.H.smooth(Math.min(1.0F, t / (8.0F + h2 * 6.0F)));
            double y = (h2 - 0.5) * heightScale * (1.0 + t * 0.035);
            Vec3 p = center.add(basis.right.scale(Math.cos(a) * r)).add(basis.up.scale(Math.sin(a) * r + y)).add(basis.forward.scale((h0 - 0.5) * r * 0.34));
            float localAlpha = alpha * (0.18F + h1 * 0.38F) * (1.0F - Math.min(0.7F, index * 0.0025F));
            wrote |= MichaelKeterVfxClient.H.star(buffer, matrix, p, 0.03F + h2 * 0.072F, red, green, blue, localAlpha);
            if ((i & 2) == 0) {
               wrote |= MichaelKeterVfxClient.H.sparkDot(
                  buffer, matrix, p.add(basis.forward.scale(0.04)), 0.018F + h0 * 0.034F, 255, 126, 236, localAlpha * 0.74F
               );
            }
         }

         return wrote;
      }

      private boolean renderFinalLayer0(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 0, 0.72F, 2.0F, 0.006F, 255, 72, 232);
      }

      private boolean renderFinalLayer1(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 1, 0.765F, 2.18F, 0.0078F, 116, 236, 255);
      }

      private boolean renderFinalLayer2(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 2, 0.81F, 2.36F, 0.0096F, 210, 58, 255);
      }

      private boolean renderFinalLayer3(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 3, 0.855F, 2.54F, 0.0114F, 255, 132, 180);
      }

      private boolean renderFinalLayer4(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 4, 0.9F, 2.72F, 0.0132F, 92, 204, 255);
      }

      private boolean renderFinalLayer5(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 5, 0.945F, 2.9F, 0.015F, 255, 96, 210);
      }

      private boolean renderFinalLayer6(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 6, 0.99F, 3.08F, 0.0168F, 255, 72, 232);
      }

      private boolean renderFinalLayer7(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 7, 1.035F, 3.26F, 0.0186F, 116, 236, 255);
      }

      private boolean renderFinalLayer8(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 8, 1.08F, 3.44F, 0.0204F, 210, 58, 255);
      }

      private boolean renderFinalLayer9(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 9, 1.125F, 3.62F, 0.0222F, 255, 132, 180);
      }

      private boolean renderFinalLayer10(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 10, 1.17F, 3.8F, 0.024F, 92, 204, 255);
      }

      private boolean renderFinalLayer11(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 11, 1.215F, 3.98F, 0.006F, 255, 96, 210);
      }

      private boolean renderFinalLayer12(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 12, 1.26F, 4.16F, 0.0078F, 255, 72, 232);
      }

      private boolean renderFinalLayer13(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 13, 1.305F, 2.0F, 0.0096F, 116, 236, 255);
      }

      private boolean renderFinalLayer14(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 14, 1.35F, 2.18F, 0.0114F, 210, 58, 255);
      }

      private boolean renderFinalLayer15(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 15, 1.395F, 2.36F, 0.0132F, 255, 132, 180);
      }

      private boolean renderFinalLayer16(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 16, 1.44F, 2.54F, 0.015F, 92, 204, 255);
      }

      private boolean renderFinalLayer17(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 17, 0.72F, 2.72F, 0.0168F, 255, 96, 210);
      }

      private boolean renderFinalLayer18(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 18, 0.765F, 2.9F, 0.0186F, 255, 72, 232);
      }

      private boolean renderFinalLayer19(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 19, 0.81F, 3.08F, 0.0204F, 116, 236, 255);
      }

      private boolean renderFinalLayer20(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 20, 0.855F, 3.26F, 0.0222F, 210, 58, 255);
      }

      private boolean renderFinalLayer21(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 21, 0.9F, 3.44F, 0.024F, 255, 132, 180);
      }

      private boolean renderFinalLayer22(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 22, 0.945F, 3.62F, 0.006F, 92, 204, 255);
      }

      private boolean renderFinalLayer23(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 23, 0.99F, 3.8F, 0.0078F, 255, 96, 210);
      }

      private boolean renderFinalLayer24(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 24, 1.035F, 3.98F, 0.0096F, 255, 72, 232);
      }

      private boolean renderFinalLayer25(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 25, 1.08F, 4.16F, 0.0114F, 116, 236, 255);
      }

      private boolean renderFinalLayer26(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 26, 1.125F, 2.0F, 0.0132F, 210, 58, 255);
      }

      private boolean renderFinalLayer27(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 27, 1.17F, 2.18F, 0.015F, 255, 132, 180);
      }

      private boolean renderFinalLayer28(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 28, 1.215F, 2.36F, 0.0168F, 92, 204, 255);
      }

      private boolean renderFinalLayer29(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 29, 1.26F, 2.54F, 0.0186F, 255, 96, 210);
      }

      private boolean renderFinalLayer30(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 30, 1.305F, 2.72F, 0.0204F, 255, 72, 232);
      }

      private boolean renderFinalLayer31(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 31, 1.35F, 2.9F, 0.0222F, 116, 236, 255);
      }

      private boolean renderFinalLayer32(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 32, 1.395F, 3.08F, 0.024F, 210, 58, 255);
      }

      private boolean renderFinalLayer33(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 33, 1.44F, 3.26F, 0.006F, 255, 132, 180);
      }

      private boolean renderFinalLayer34(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 34, 0.72F, 3.44F, 0.0078F, 92, 204, 255);
      }

      private boolean renderFinalLayer35(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 35, 0.765F, 3.62F, 0.0096F, 255, 96, 210);
      }

      private boolean renderFinalLayer36(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 36, 0.81F, 3.8F, 0.0114F, 255, 72, 232);
      }

      private boolean renderFinalLayer37(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 37, 0.855F, 3.98F, 0.0132F, 116, 236, 255);
      }

      private boolean renderFinalLayer38(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 38, 0.9F, 4.16F, 0.015F, 210, 58, 255);
      }

      private boolean renderFinalLayer39(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 39, 0.945F, 2.0F, 0.0168F, 255, 132, 180);
      }

      private boolean renderFinalLayer40(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 40, 0.99F, 2.18F, 0.0186F, 92, 204, 255);
      }

      private boolean renderFinalLayer41(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 41, 1.035F, 2.36F, 0.0204F, 255, 96, 210);
      }

      private boolean renderFinalLayer42(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 42, 1.08F, 2.54F, 0.0222F, 255, 72, 232);
      }

      private boolean renderFinalLayer43(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 43, 1.125F, 2.72F, 0.024F, 116, 236, 255);
      }

      private boolean renderFinalLayer44(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 44, 1.17F, 2.9F, 0.006F, 210, 58, 255);
      }

      private boolean renderFinalLayer45(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 45, 1.215F, 3.08F, 0.0078F, 255, 132, 180);
      }

      private boolean renderFinalLayer46(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 46, 1.26F, 3.26F, 0.0096F, 92, 204, 255);
      }

      private boolean renderFinalLayer47(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 47, 1.305F, 3.44F, 0.0114F, 255, 96, 210);
      }

      private boolean renderFinalLayer48(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 48, 1.35F, 3.62F, 0.0132F, 255, 72, 232);
      }

      private boolean renderFinalLayer49(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 49, 1.395F, 3.8F, 0.015F, 116, 236, 255);
      }

      private boolean renderFinalLayer50(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 50, 1.44F, 3.98F, 0.0168F, 210, 58, 255);
      }

      private boolean renderFinalLayer51(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 51, 0.72F, 4.16F, 0.0186F, 255, 132, 180);
      }

      private boolean renderFinalLayer52(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 52, 0.765F, 2.0F, 0.0204F, 92, 204, 255);
      }

      private boolean renderFinalLayer53(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 53, 0.81F, 2.18F, 0.0222F, 255, 96, 210);
      }

      private boolean renderFinalLayer54(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 54, 0.855F, 2.36F, 0.024F, 255, 72, 232);
      }

      private boolean renderFinalLayer55(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 55, 0.9F, 2.54F, 0.006F, 116, 236, 255);
      }

      private boolean renderFinalLayer56(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 56, 0.945F, 2.72F, 0.0078F, 210, 58, 255);
      }

      private boolean renderFinalLayer57(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 57, 0.99F, 2.9F, 0.0096F, 255, 132, 180);
      }

      private boolean renderFinalLayer58(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 58, 1.035F, 3.08F, 0.0114F, 92, 204, 255);
      }

      private boolean renderFinalLayer59(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 59, 1.08F, 3.26F, 0.0132F, 255, 96, 210);
      }

      private boolean renderFinalLayer60(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 60, 1.125F, 3.44F, 0.015F, 255, 72, 232);
      }

      private boolean renderFinalLayer61(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 61, 1.17F, 3.62F, 0.0168F, 116, 236, 255);
      }

      private boolean renderFinalLayer62(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 62, 1.215F, 3.8F, 0.0186F, 210, 58, 255);
      }

      private boolean renderFinalLayer63(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 63, 1.26F, 3.98F, 0.0204F, 255, 132, 180);
      }

      private boolean renderFinalLayer64(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 64, 1.305F, 4.16F, 0.0222F, 92, 204, 255);
      }

      private boolean renderFinalLayer65(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 65, 1.35F, 2.0F, 0.024F, 255, 96, 210);
      }

      private boolean renderFinalLayer66(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 66, 1.395F, 2.18F, 0.006F, 255, 72, 232);
      }

      private boolean renderFinalLayer67(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 67, 1.44F, 2.36F, 0.0078F, 116, 236, 255);
      }

      private boolean renderFinalLayer68(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 68, 0.72F, 2.54F, 0.0096F, 210, 58, 255);
      }

      private boolean renderFinalLayer69(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 69, 0.765F, 2.72F, 0.0114F, 255, 132, 180);
      }

      private boolean renderFinalLayer70(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 70, 0.81F, 2.9F, 0.0132F, 92, 204, 255);
      }

      private boolean renderFinalLayer71(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 71, 0.855F, 3.08F, 0.015F, 255, 96, 210);
      }

      private boolean renderFinalLayer72(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 72, 0.9F, 3.26F, 0.0168F, 255, 72, 232);
      }

      private boolean renderFinalLayer73(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 73, 0.945F, 3.44F, 0.0186F, 116, 236, 255);
      }

      private boolean renderFinalLayer74(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 74, 0.99F, 3.62F, 0.0204F, 210, 58, 255);
      }

      private boolean renderFinalLayer75(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 75, 1.035F, 3.8F, 0.0222F, 255, 132, 180);
      }

      private boolean renderFinalLayer76(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 76, 1.08F, 3.98F, 0.024F, 92, 204, 255);
      }

      private boolean renderFinalLayer77(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 77, 1.125F, 4.16F, 0.006F, 255, 96, 210);
      }

      private boolean renderFinalLayer78(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 78, 1.17F, 2.0F, 0.0078F, 255, 72, 232);
      }

      private boolean renderFinalLayer79(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 79, 1.215F, 2.18F, 0.0096F, 116, 236, 255);
      }

      private boolean renderFinalLayer80(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 80, 1.26F, 2.36F, 0.0114F, 210, 58, 255);
      }

      private boolean renderFinalLayer81(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 81, 1.305F, 2.54F, 0.0132F, 255, 132, 180);
      }

      private boolean renderFinalLayer82(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 82, 1.35F, 2.72F, 0.015F, 92, 204, 255);
      }

      private boolean renderFinalLayer83(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 83, 1.395F, 2.9F, 0.0168F, 255, 96, 210);
      }

      private boolean renderFinalLayer84(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 84, 1.44F, 3.08F, 0.0186F, 255, 72, 232);
      }

      private boolean renderFinalLayer85(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 85, 0.72F, 3.26F, 0.0204F, 116, 236, 255);
      }

      private boolean renderFinalLayer86(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 86, 0.765F, 3.44F, 0.0222F, 210, 58, 255);
      }

      private boolean renderFinalLayer87(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 87, 0.81F, 3.62F, 0.024F, 255, 132, 180);
      }

      private boolean renderFinalLayer88(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 88, 0.855F, 3.8F, 0.006F, 92, 204, 255);
      }

      private boolean renderFinalLayer89(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 89, 0.9F, 3.98F, 0.0078F, 255, 96, 210);
      }

      private boolean renderFinalLayer90(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 90, 0.945F, 4.16F, 0.0096F, 255, 72, 232);
      }

      private boolean renderFinalLayer91(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 91, 0.99F, 2.0F, 0.0114F, 116, 236, 255);
      }

      private boolean renderFinalLayer92(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 92, 1.035F, 2.18F, 0.0132F, 210, 58, 255);
      }

      private boolean renderFinalLayer93(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 93, 1.08F, 2.36F, 0.015F, 255, 132, 180);
      }

      private boolean renderFinalLayer94(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 94, 1.125F, 2.54F, 0.0168F, 92, 204, 255);
      }

      private boolean renderFinalLayer95(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 95, 1.17F, 2.72F, 0.0186F, 255, 96, 210);
      }

      private boolean renderFinalLayer96(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 96, 1.215F, 2.9F, 0.0204F, 255, 72, 232);
      }

      private boolean renderFinalLayer97(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 97, 1.26F, 3.08F, 0.0222F, 116, 236, 255);
      }

      private boolean renderFinalLayer98(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 98, 1.305F, 3.26F, 0.024F, 210, 58, 255);
      }

      private boolean renderFinalLayer99(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 99, 1.35F, 3.44F, 0.006F, 255, 132, 180);
      }

      private boolean renderFinalLayer100(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 100, 1.395F, 3.62F, 0.0078F, 92, 204, 255);
      }

      private boolean renderFinalLayer101(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 101, 1.44F, 3.8F, 0.0096F, 255, 96, 210);
      }

      private boolean renderFinalLayer102(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 102, 0.72F, 3.98F, 0.0114F, 255, 72, 232);
      }

      private boolean renderFinalLayer103(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 103, 0.765F, 4.16F, 0.0132F, 116, 236, 255);
      }

      private boolean renderFinalLayer104(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 104, 0.81F, 2.0F, 0.015F, 210, 58, 255);
      }

      private boolean renderFinalLayer105(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 105, 0.855F, 2.18F, 0.0168F, 255, 132, 180);
      }

      private boolean renderFinalLayer106(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 106, 0.9F, 2.36F, 0.0186F, 92, 204, 255);
      }

      private boolean renderFinalLayer107(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 107, 0.945F, 2.54F, 0.0204F, 255, 96, 210);
      }

      private boolean renderFinalLayer108(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 108, 0.99F, 2.72F, 0.0222F, 255, 72, 232);
      }

      private boolean renderFinalLayer109(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 109, 1.035F, 2.9F, 0.024F, 116, 236, 255);
      }

      private boolean renderFinalLayer110(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 110, 1.08F, 3.08F, 0.006F, 210, 58, 255);
      }

      private boolean renderFinalLayer111(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 111, 1.125F, 3.26F, 0.0078F, 255, 132, 180);
      }

      private boolean renderFinalLayer112(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 112, 1.17F, 3.44F, 0.0096F, 92, 204, 255);
      }

      private boolean renderFinalLayer113(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 113, 1.215F, 3.62F, 0.0114F, 255, 96, 210);
      }

      private boolean renderFinalLayer114(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 114, 1.26F, 3.8F, 0.0132F, 255, 72, 232);
      }

      private boolean renderFinalLayer115(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 115, 1.305F, 3.98F, 0.015F, 116, 236, 255);
      }

      private boolean renderFinalLayer116(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 116, 1.35F, 4.16F, 0.0168F, 210, 58, 255);
      }

      private boolean renderFinalLayer117(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 117, 1.395F, 2.0F, 0.0186F, 255, 132, 180);
      }

      private boolean renderFinalLayer118(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 118, 1.44F, 2.18F, 0.0204F, 92, 204, 255);
      }

      private boolean renderFinalLayer119(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 119, 0.72F, 2.36F, 0.0222F, 255, 96, 210);
      }

      private boolean renderFinalLayer120(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 120, 0.765F, 2.54F, 0.024F, 255, 72, 232);
      }

      private boolean renderFinalLayer121(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 121, 0.81F, 2.72F, 0.006F, 116, 236, 255);
      }

      private boolean renderFinalLayer122(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 122, 0.855F, 2.9F, 0.0078F, 210, 58, 255);
      }

      private boolean renderFinalLayer123(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 123, 0.9F, 3.08F, 0.0096F, 255, 132, 180);
      }

      private boolean renderFinalLayer124(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 124, 0.945F, 3.26F, 0.0114F, 92, 204, 255);
      }

      private boolean renderFinalLayer125(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 125, 0.99F, 3.44F, 0.0132F, 255, 96, 210);
      }

      private boolean renderFinalLayer126(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 126, 1.035F, 3.62F, 0.015F, 255, 72, 232);
      }

      private boolean renderFinalLayer127(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 127, 1.08F, 3.8F, 0.0168F, 116, 236, 255);
      }

      private boolean renderFinalLayer128(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 128, 1.125F, 3.98F, 0.0186F, 210, 58, 255);
      }

      private boolean renderFinalLayer129(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 129, 1.17F, 4.16F, 0.0204F, 255, 132, 180);
      }

      private boolean renderFinalLayer130(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 130, 1.215F, 2.0F, 0.0222F, 92, 204, 255);
      }

      private boolean renderFinalLayer131(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 131, 1.26F, 2.18F, 0.024F, 255, 96, 210);
      }

      private boolean renderFinalLayer132(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 132, 1.305F, 2.36F, 0.006F, 255, 72, 232);
      }

      private boolean renderFinalLayer133(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 133, 1.35F, 2.54F, 0.0078F, 116, 236, 255);
      }

      private boolean renderFinalLayer134(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 134, 1.395F, 2.72F, 0.0096F, 210, 58, 255);
      }

      private boolean renderFinalLayer135(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 135, 1.44F, 2.9F, 0.0114F, 255, 132, 180);
      }

      private boolean renderFinalLayer136(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 136, 0.72F, 3.08F, 0.0132F, 92, 204, 255);
      }

      private boolean renderFinalLayer137(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 137, 0.765F, 3.26F, 0.015F, 255, 96, 210);
      }

      private boolean renderFinalLayer138(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 138, 0.81F, 3.44F, 0.0168F, 255, 72, 232);
      }

      private boolean renderFinalLayer139(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 139, 0.855F, 3.62F, 0.0186F, 116, 236, 255);
      }

      private boolean renderFinalLayer140(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 140, 0.9F, 3.8F, 0.0204F, 210, 58, 255);
      }

      private boolean renderFinalLayer141(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 141, 0.945F, 3.98F, 0.0222F, 255, 132, 180);
      }

      private boolean renderFinalLayer142(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 142, 0.99F, 4.16F, 0.024F, 92, 204, 255);
      }

      private boolean renderFinalLayer143(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 143, 1.035F, 2.0F, 0.006F, 255, 96, 210);
      }

      private boolean renderFinalLayer144(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 144, 1.08F, 2.18F, 0.0078F, 255, 72, 232);
      }

      private boolean renderFinalLayer145(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 145, 1.125F, 2.36F, 0.0096F, 116, 236, 255);
      }

      private boolean renderFinalLayer146(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 146, 1.17F, 2.54F, 0.0114F, 210, 58, 255);
      }

      private boolean renderFinalLayer147(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 147, 1.215F, 2.72F, 0.0132F, 255, 132, 180);
      }

      private boolean renderFinalLayer148(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 148, 1.26F, 2.9F, 0.015F, 92, 204, 255);
      }

      private boolean renderFinalLayer149(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 149, 1.305F, 3.08F, 0.0168F, 255, 96, 210);
      }

      private boolean renderFinalLayer150(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 150, 1.35F, 3.26F, 0.0186F, 255, 72, 232);
      }

      private boolean renderFinalLayer151(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 151, 1.395F, 3.44F, 0.0204F, 116, 236, 255);
      }

      private boolean renderFinalLayer152(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 152, 1.44F, 3.62F, 0.0222F, 210, 58, 255);
      }

      private boolean renderFinalLayer153(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 153, 0.72F, 3.8F, 0.024F, 255, 132, 180);
      }

      private boolean renderFinalLayer154(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 154, 0.765F, 3.98F, 0.006F, 92, 204, 255);
      }

      private boolean renderFinalLayer155(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 155, 0.81F, 4.16F, 0.0078F, 255, 96, 210);
      }

      private boolean renderFinalLayer156(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 156, 0.855F, 2.0F, 0.0096F, 255, 72, 232);
      }

      private boolean renderFinalLayer157(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 157, 0.9F, 2.18F, 0.0114F, 116, 236, 255);
      }

      private boolean renderFinalLayer158(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 158, 0.945F, 2.36F, 0.0132F, 210, 58, 255);
      }

      private boolean renderFinalLayer159(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 159, 0.99F, 2.54F, 0.015F, 255, 132, 180);
      }

      private boolean renderFinalLayer160(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 160, 1.035F, 2.72F, 0.0168F, 92, 204, 255);
      }

      private boolean renderFinalLayer161(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 161, 1.08F, 2.9F, 0.0186F, 255, 96, 210);
      }

      private boolean renderFinalLayer162(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 162, 1.125F, 3.08F, 0.0204F, 255, 72, 232);
      }

      private boolean renderFinalLayer163(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 163, 1.17F, 3.26F, 0.0222F, 116, 236, 255);
      }

      private boolean renderFinalLayer164(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 164, 1.215F, 3.44F, 0.024F, 210, 58, 255);
      }

      private boolean renderFinalLayer165(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 165, 1.26F, 3.62F, 0.006F, 255, 132, 180);
      }

      private boolean renderFinalLayer166(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 166, 1.305F, 3.8F, 0.0078F, 92, 204, 255);
      }

      private boolean renderFinalLayer167(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 167, 1.35F, 3.98F, 0.0096F, 255, 96, 210);
      }

      private boolean renderFinalLayer168(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 168, 1.395F, 4.16F, 0.0114F, 255, 72, 232);
      }

      private boolean renderFinalLayer169(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 169, 1.44F, 2.0F, 0.0132F, 116, 236, 255);
      }

      private boolean renderFinalLayer170(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 170, 0.72F, 2.18F, 0.015F, 210, 58, 255);
      }

      private boolean renderFinalLayer171(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 171, 0.765F, 2.36F, 0.0168F, 255, 132, 180);
      }

      private boolean renderFinalLayer172(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 172, 0.81F, 2.54F, 0.0186F, 92, 204, 255);
      }

      private boolean renderFinalLayer173(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 173, 0.855F, 2.72F, 0.0204F, 255, 96, 210);
      }

      private boolean renderFinalLayer174(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 174, 0.9F, 2.9F, 0.0222F, 255, 72, 232);
      }

      private boolean renderFinalLayer175(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 175, 0.945F, 3.08F, 0.024F, 116, 236, 255);
      }

      private boolean renderFinalLayer176(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 176, 0.99F, 3.26F, 0.006F, 210, 58, 255);
      }

      private boolean renderFinalLayer177(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 177, 1.035F, 3.44F, 0.0078F, 255, 132, 180);
      }

      private boolean renderFinalLayer178(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 178, 1.08F, 3.62F, 0.0096F, 92, 204, 255);
      }

      private boolean renderFinalLayer179(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 179, 1.125F, 3.8F, 0.0114F, 255, 96, 210);
      }

      private boolean renderFinalLayer180(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 180, 1.17F, 3.98F, 0.0132F, 255, 72, 232);
      }

      private boolean renderFinalLayer181(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 181, 1.215F, 4.16F, 0.015F, 116, 236, 255);
      }

      private boolean renderFinalLayer182(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 182, 1.26F, 2.0F, 0.0168F, 210, 58, 255);
      }

      private boolean renderFinalLayer183(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 183, 1.305F, 2.18F, 0.0186F, 255, 132, 180);
      }

      private boolean renderFinalLayer184(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 184, 1.35F, 2.36F, 0.0204F, 92, 204, 255);
      }

      private boolean renderFinalLayer185(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 185, 1.395F, 2.54F, 0.0222F, 255, 96, 210);
      }

      private boolean renderFinalLayer186(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 186, 1.44F, 2.72F, 0.024F, 255, 72, 232);
      }

      private boolean renderFinalLayer187(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 187, 0.72F, 2.9F, 0.006F, 116, 236, 255);
      }

      private boolean renderFinalLayer188(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 188, 0.765F, 3.08F, 0.0078F, 210, 58, 255);
      }

      private boolean renderFinalLayer189(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 189, 0.81F, 3.26F, 0.0096F, 255, 132, 180);
      }

      private boolean renderFinalLayer190(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 190, 0.855F, 3.44F, 0.0114F, 92, 204, 255);
      }

      private boolean renderFinalLayer191(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 191, 0.9F, 3.62F, 0.0132F, 255, 96, 210);
      }

      private boolean renderFinalLayer192(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 192, 0.945F, 3.8F, 0.015F, 255, 72, 232);
      }

      private boolean renderFinalLayer193(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 193, 0.99F, 3.98F, 0.0168F, 116, 236, 255);
      }

      private boolean renderFinalLayer194(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 194, 1.035F, 4.16F, 0.0186F, 210, 58, 255);
      }

      private boolean renderFinalLayer195(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 195, 1.08F, 2.0F, 0.0204F, 255, 132, 180);
      }

      private boolean renderFinalLayer196(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 196, 1.125F, 2.18F, 0.0222F, 92, 204, 255);
      }

      private boolean renderFinalLayer197(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 197, 1.17F, 2.36F, 0.024F, 255, 96, 210);
      }

      private boolean renderFinalLayer198(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 198, 1.215F, 2.54F, 0.006F, 255, 72, 232);
      }

      private boolean renderFinalLayer199(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 199, 1.26F, 2.72F, 0.0078F, 116, 236, 255);
      }

      private boolean renderFinalLayer200(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 200, 1.305F, 2.9F, 0.0096F, 210, 58, 255);
      }

      private boolean renderFinalLayer201(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 201, 1.35F, 3.08F, 0.0114F, 255, 132, 180);
      }

      private boolean renderFinalLayer202(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 202, 1.395F, 3.26F, 0.0132F, 92, 204, 255);
      }

      private boolean renderFinalLayer203(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 203, 1.44F, 3.44F, 0.015F, 255, 96, 210);
      }

      private boolean renderFinalLayer204(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 204, 0.72F, 3.62F, 0.0168F, 255, 72, 232);
      }

      private boolean renderFinalLayer205(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 205, 0.765F, 3.8F, 0.0186F, 116, 236, 255);
      }

      private boolean renderFinalLayer206(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 206, 0.81F, 3.98F, 0.0204F, 210, 58, 255);
      }

      private boolean renderFinalLayer207(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 207, 0.855F, 4.16F, 0.0222F, 255, 132, 180);
      }

      private boolean renderFinalLayer208(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 208, 0.9F, 2.0F, 0.024F, 92, 204, 255);
      }

      private boolean renderFinalLayer209(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 209, 0.945F, 2.18F, 0.006F, 255, 96, 210);
      }

      private boolean renderFinalLayer210(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 210, 0.99F, 2.36F, 0.0078F, 255, 72, 232);
      }

      private boolean renderFinalLayer211(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 211, 1.035F, 2.54F, 0.0096F, 116, 236, 255);
      }

      private boolean renderFinalLayer212(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 212, 1.08F, 2.72F, 0.0114F, 210, 58, 255);
      }

      private boolean renderFinalLayer213(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 213, 1.125F, 2.9F, 0.0132F, 255, 132, 180);
      }

      private boolean renderFinalLayer214(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 214, 1.17F, 3.08F, 0.015F, 92, 204, 255);
      }

      private boolean renderFinalLayer215(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 215, 1.215F, 3.26F, 0.0168F, 255, 96, 210);
      }

      private boolean renderFinalLayer216(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 216, 1.26F, 3.44F, 0.0186F, 255, 72, 232);
      }

      private boolean renderFinalLayer217(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 217, 1.305F, 3.62F, 0.0204F, 116, 236, 255);
      }

      private boolean renderFinalLayer218(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 218, 1.35F, 3.8F, 0.0222F, 210, 58, 255);
      }

      private boolean renderFinalLayer219(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 219, 1.395F, 3.98F, 0.024F, 255, 132, 180);
      }

      private boolean renderFinalLayer220(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 220, 1.44F, 4.16F, 0.006F, 92, 204, 255);
      }

      private boolean renderFinalLayer221(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 221, 0.72F, 2.0F, 0.0078F, 255, 96, 210);
      }

      private boolean renderFinalLayer222(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 222, 0.765F, 2.18F, 0.0096F, 255, 72, 232);
      }

      private boolean renderFinalLayer223(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 223, 0.81F, 2.36F, 0.0114F, 116, 236, 255);
      }

      private boolean renderFinalLayer224(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 224, 0.855F, 2.54F, 0.0132F, 210, 58, 255);
      }

      private boolean renderFinalLayer225(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 225, 0.9F, 2.72F, 0.015F, 255, 132, 180);
      }

      private boolean renderFinalLayer226(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 226, 0.945F, 2.9F, 0.0168F, 92, 204, 255);
      }

      private boolean renderFinalLayer227(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 227, 0.99F, 3.08F, 0.0186F, 255, 96, 210);
      }

      private boolean renderFinalLayer228(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 228, 1.035F, 3.26F, 0.0204F, 255, 72, 232);
      }

      private boolean renderFinalLayer229(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 229, 1.08F, 3.44F, 0.0222F, 116, 236, 255);
      }

      private boolean renderFinalLayer230(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 230, 1.125F, 3.62F, 0.024F, 210, 58, 255);
      }

      private boolean renderFinalLayer231(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 231, 1.17F, 3.8F, 0.006F, 255, 132, 180);
      }

      private boolean renderFinalLayer232(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 232, 1.215F, 3.98F, 0.0078F, 92, 204, 255);
      }

      private boolean renderFinalLayer233(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 233, 1.26F, 4.16F, 0.0096F, 255, 96, 210);
      }

      private boolean renderFinalLayer234(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 234, 1.305F, 2.0F, 0.0114F, 255, 72, 232);
      }

      private boolean renderFinalLayer235(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 235, 1.35F, 2.18F, 0.0132F, 116, 236, 255);
      }

      private boolean renderFinalLayer236(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 236, 1.395F, 2.36F, 0.015F, 210, 58, 255);
      }

      private boolean renderFinalLayer237(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 237, 1.44F, 2.54F, 0.0168F, 255, 132, 180);
      }

      private boolean renderFinalLayer238(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 238, 0.72F, 2.72F, 0.0186F, 92, 204, 255);
      }

      private boolean renderFinalLayer239(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 239, 0.765F, 2.9F, 0.0204F, 255, 96, 210);
      }

      private boolean renderFinalLayer240(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 240, 0.81F, 3.08F, 0.0222F, 255, 72, 232);
      }

      private boolean renderFinalLayer241(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 241, 0.855F, 3.26F, 0.024F, 116, 236, 255);
      }

      private boolean renderFinalLayer242(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 242, 0.9F, 3.44F, 0.006F, 210, 58, 255);
      }

      private boolean renderFinalLayer243(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 243, 0.945F, 3.62F, 0.0078F, 255, 132, 180);
      }

      private boolean renderFinalLayer244(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 244, 0.99F, 3.8F, 0.0096F, 92, 204, 255);
      }

      private boolean renderFinalLayer245(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 245, 1.035F, 3.98F, 0.0114F, 255, 96, 210);
      }

      private boolean renderFinalLayer246(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 246, 1.08F, 4.16F, 0.0132F, 255, 72, 232);
      }

      private boolean renderFinalLayer247(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 247, 1.125F, 2.0F, 0.015F, 116, 236, 255);
      }

      private boolean renderFinalLayer248(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 248, 1.17F, 2.18F, 0.0168F, 210, 58, 255);
      }

      private boolean renderFinalLayer249(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 249, 1.215F, 2.36F, 0.0186F, 255, 132, 180);
      }

      private boolean renderFinalLayer250(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 250, 1.26F, 2.54F, 0.0204F, 92, 204, 255);
      }

      private boolean renderFinalLayer251(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 251, 1.305F, 2.72F, 0.0222F, 255, 96, 210);
      }

      private boolean renderFinalLayer252(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 252, 1.35F, 2.9F, 0.024F, 255, 72, 232);
      }

      private boolean renderFinalLayer253(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 253, 1.395F, 3.08F, 0.006F, 116, 236, 255);
      }

      private boolean renderFinalLayer254(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 254, 1.44F, 3.26F, 0.0078F, 210, 58, 255);
      }

      private boolean renderFinalLayer255(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 255, 0.72F, 3.44F, 0.0096F, 255, 132, 180);
      }

      private boolean renderFinalLayer256(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 256, 0.765F, 3.62F, 0.0114F, 92, 204, 255);
      }

      private boolean renderFinalLayer257(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 257, 0.81F, 3.8F, 0.0132F, 255, 96, 210);
      }

      private boolean renderFinalLayer258(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 258, 0.855F, 3.98F, 0.015F, 255, 72, 232);
      }

      private boolean renderFinalLayer259(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 259, 0.9F, 4.16F, 0.0168F, 116, 236, 255);
      }

      private boolean renderFinalLayer260(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 260, 0.945F, 2.0F, 0.0186F, 210, 58, 255);
      }

      private boolean renderFinalLayer261(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 261, 0.99F, 2.18F, 0.0204F, 255, 132, 180);
      }

      private boolean renderFinalLayer262(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 262, 1.035F, 2.36F, 0.0222F, 92, 204, 255);
      }

      private boolean renderFinalLayer263(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 263, 1.08F, 2.54F, 0.024F, 255, 96, 210);
      }

      private boolean renderFinalLayer264(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 264, 1.125F, 2.72F, 0.006F, 255, 72, 232);
      }

      private boolean renderFinalLayer265(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 265, 1.17F, 2.9F, 0.0078F, 116, 236, 255);
      }

      private boolean renderFinalLayer266(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 266, 1.215F, 3.08F, 0.0096F, 210, 58, 255);
      }

      private boolean renderFinalLayer267(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 267, 1.26F, 3.26F, 0.0114F, 255, 132, 180);
      }

      private boolean renderFinalLayer268(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 268, 1.305F, 3.44F, 0.0132F, 92, 204, 255);
      }

      private boolean renderFinalLayer269(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 269, 1.35F, 3.62F, 0.015F, 255, 96, 210);
      }

      private boolean renderFinalLayer270(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 270, 1.395F, 3.8F, 0.0168F, 255, 72, 232);
      }

      private boolean renderFinalLayer271(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 271, 1.44F, 3.98F, 0.0186F, 116, 236, 255);
      }

      private boolean renderFinalLayer272(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 272, 0.72F, 4.16F, 0.0204F, 210, 58, 255);
      }

      private boolean renderFinalLayer273(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 273, 0.765F, 2.0F, 0.0222F, 255, 132, 180);
      }

      private boolean renderFinalLayer274(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 274, 0.81F, 2.18F, 0.024F, 92, 204, 255);
      }

      private boolean renderFinalLayer275(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 275, 0.855F, 2.36F, 0.006F, 255, 96, 210);
      }

      private boolean renderFinalLayer276(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 276, 0.9F, 2.54F, 0.0078F, 255, 72, 232);
      }

      private boolean renderFinalLayer277(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 277, 0.945F, 2.72F, 0.0096F, 116, 236, 255);
      }

      private boolean renderFinalLayer278(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 278, 0.99F, 2.9F, 0.0114F, 210, 58, 255);
      }

      private boolean renderFinalLayer279(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 279, 1.035F, 3.08F, 0.0132F, 255, 132, 180);
      }

      private boolean renderFinalLayer280(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 280, 1.08F, 3.26F, 0.015F, 92, 204, 255);
      }

      private boolean renderFinalLayer281(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 281, 1.125F, 3.44F, 0.0168F, 255, 96, 210);
      }

      private boolean renderFinalLayer282(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 282, 1.17F, 3.62F, 0.0186F, 255, 72, 232);
      }

      private boolean renderFinalLayer283(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 283, 1.215F, 3.8F, 0.0204F, 116, 236, 255);
      }

      private boolean renderFinalLayer284(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 284, 1.26F, 3.98F, 0.0222F, 210, 58, 255);
      }

      private boolean renderFinalLayer285(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 285, 1.305F, 4.16F, 0.024F, 255, 132, 180);
      }

      private boolean renderFinalLayer286(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 286, 1.35F, 2.0F, 0.006F, 92, 204, 255);
      }

      private boolean renderFinalLayer287(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 287, 1.395F, 2.18F, 0.0078F, 255, 96, 210);
      }

      private boolean renderFinalLayer288(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 288, 1.44F, 2.36F, 0.0096F, 255, 72, 232);
      }

      private boolean renderFinalLayer289(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 289, 0.72F, 2.54F, 0.0114F, 116, 236, 255);
      }

      private boolean renderFinalLayer290(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 290, 0.765F, 2.72F, 0.0132F, 210, 58, 255);
      }

      private boolean renderFinalLayer291(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 291, 0.81F, 2.9F, 0.015F, 255, 132, 180);
      }

      private boolean renderFinalLayer292(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 292, 0.855F, 3.08F, 0.0168F, 92, 204, 255);
      }

      private boolean renderFinalLayer293(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 293, 0.9F, 3.26F, 0.0186F, 255, 96, 210);
      }

      private boolean renderFinalLayer294(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 294, 0.945F, 3.44F, 0.0204F, 255, 72, 232);
      }

      private boolean renderFinalLayer295(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 295, 0.99F, 3.62F, 0.0222F, 116, 236, 255);
      }

      private boolean renderFinalLayer296(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 296, 1.035F, 3.8F, 0.024F, 210, 58, 255);
      }

      private boolean renderFinalLayer297(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 297, 1.08F, 3.98F, 0.006F, 255, 132, 180);
      }

      private boolean renderFinalLayer298(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 298, 1.125F, 4.16F, 0.0078F, 92, 204, 255);
      }

      private boolean renderFinalLayer299(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 299, 1.17F, 2.0F, 0.0096F, 255, 96, 210);
      }

      private boolean renderFinalLayer300(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 300, 1.215F, 2.18F, 0.0114F, 255, 72, 232);
      }

      private boolean renderFinalLayer301(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 301, 1.26F, 2.36F, 0.0132F, 116, 236, 255);
      }

      private boolean renderFinalLayer302(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 302, 1.305F, 2.54F, 0.015F, 210, 58, 255);
      }

      private boolean renderFinalLayer303(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 303, 1.35F, 2.72F, 0.0168F, 255, 132, 180);
      }

      private boolean renderFinalLayer304(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 304, 1.395F, 2.9F, 0.0186F, 92, 204, 255);
      }

      private boolean renderFinalLayer305(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 305, 1.44F, 3.08F, 0.0204F, 255, 96, 210);
      }

      private boolean renderFinalLayer306(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 306, 0.72F, 3.26F, 0.0222F, 255, 72, 232);
      }

      private boolean renderFinalLayer307(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 307, 0.765F, 3.44F, 0.024F, 116, 236, 255);
      }

      private boolean renderFinalLayer308(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 308, 0.81F, 3.62F, 0.006F, 210, 58, 255);
      }

      private boolean renderFinalLayer309(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 309, 0.855F, 3.8F, 0.0078F, 255, 132, 180);
      }

      private boolean renderFinalLayer310(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 310, 0.9F, 3.98F, 0.0096F, 92, 204, 255);
      }

      private boolean renderFinalLayer311(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 311, 0.945F, 4.16F, 0.0114F, 255, 96, 210);
      }

      private boolean renderFinalLayer312(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 312, 0.99F, 2.0F, 0.0132F, 255, 72, 232);
      }

      private boolean renderFinalLayer313(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 313, 1.035F, 2.18F, 0.015F, 116, 236, 255);
      }

      private boolean renderFinalLayer314(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 314, 1.08F, 2.36F, 0.0168F, 210, 58, 255);
      }

      private boolean renderFinalLayer315(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 315, 1.125F, 2.54F, 0.0186F, 255, 132, 180);
      }

      private boolean renderFinalLayer316(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 316, 1.17F, 2.72F, 0.0204F, 92, 204, 255);
      }

      private boolean renderFinalLayer317(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 317, 1.215F, 2.9F, 0.0222F, 255, 96, 210);
      }

      private boolean renderFinalLayer318(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 318, 1.26F, 3.08F, 0.024F, 255, 72, 232);
      }

      private boolean renderFinalLayer319(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 319, 1.305F, 3.26F, 0.006F, 116, 236, 255);
      }

      private boolean renderFinalLayer320(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 320, 1.35F, 3.44F, 0.0078F, 210, 58, 255);
      }

      private boolean renderFinalLayer321(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 321, 1.395F, 3.62F, 0.0096F, 255, 132, 180);
      }

      private boolean renderFinalLayer322(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 322, 1.44F, 3.8F, 0.0114F, 92, 204, 255);
      }

      private boolean renderFinalLayer323(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 323, 0.72F, 3.98F, 0.0132F, 255, 96, 210);
      }

      private boolean renderFinalLayer324(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 324, 0.765F, 4.16F, 0.015F, 255, 72, 232);
      }

      private boolean renderFinalLayer325(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 325, 0.81F, 2.0F, 0.0168F, 116, 236, 255);
      }

      private boolean renderFinalLayer326(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 326, 0.855F, 2.18F, 0.0186F, 210, 58, 255);
      }

      private boolean renderFinalLayer327(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 327, 0.9F, 2.36F, 0.0204F, 255, 132, 180);
      }

      private boolean renderFinalLayer328(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 328, 0.945F, 2.54F, 0.0222F, 92, 204, 255);
      }

      private boolean renderFinalLayer329(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 329, 0.99F, 2.72F, 0.024F, 255, 96, 210);
      }

      private boolean renderFinalLayer330(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 330, 1.035F, 2.9F, 0.006F, 255, 72, 232);
      }

      private boolean renderFinalLayer331(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 331, 1.08F, 3.08F, 0.0078F, 116, 236, 255);
      }

      private boolean renderFinalLayer332(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 332, 1.125F, 3.26F, 0.0096F, 210, 58, 255);
      }

      private boolean renderFinalLayer333(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 333, 1.17F, 3.44F, 0.0114F, 255, 132, 180);
      }

      private boolean renderFinalLayer334(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 334, 1.215F, 3.62F, 0.0132F, 92, 204, 255);
      }

      private boolean renderFinalLayer335(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 335, 1.26F, 3.8F, 0.015F, 255, 96, 210);
      }

      private boolean renderFinalLayer336(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 336, 1.305F, 3.98F, 0.0168F, 255, 72, 232);
      }

      private boolean renderFinalLayer337(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 337, 1.35F, 4.16F, 0.0186F, 116, 236, 255);
      }

      private boolean renderFinalLayer338(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 338, 1.395F, 2.0F, 0.0204F, 210, 58, 255);
      }

      private boolean renderFinalLayer339(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 339, 1.44F, 2.18F, 0.0222F, 255, 132, 180);
      }

      private boolean renderFinalLayer340(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 340, 0.72F, 2.36F, 0.024F, 92, 204, 255);
      }

      private boolean renderFinalLayer341(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 341, 0.765F, 2.54F, 0.006F, 255, 96, 210);
      }

      private boolean renderFinalLayer342(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 342, 0.81F, 2.72F, 0.0078F, 255, 72, 232);
      }

      private boolean renderFinalLayer343(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 343, 0.855F, 2.9F, 0.0096F, 116, 236, 255);
      }

      private boolean renderFinalLayer344(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 344, 0.9F, 3.08F, 0.0114F, 210, 58, 255);
      }

      private boolean renderFinalLayer345(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 345, 0.945F, 3.26F, 0.0132F, 255, 132, 180);
      }

      private boolean renderFinalLayer346(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 346, 0.99F, 3.44F, 0.015F, 92, 204, 255);
      }

      private boolean renderFinalLayer347(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 347, 1.035F, 3.62F, 0.0168F, 255, 96, 210);
      }

      private boolean renderFinalLayer348(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 348, 1.08F, 3.8F, 0.0186F, 255, 72, 232);
      }

      private boolean renderFinalLayer349(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 349, 1.125F, 3.98F, 0.0204F, 116, 236, 255);
      }

      private boolean renderFinalLayer350(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 350, 1.17F, 4.16F, 0.0222F, 210, 58, 255);
      }

      private boolean renderFinalLayer351(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 351, 1.215F, 2.0F, 0.024F, 255, 132, 180);
      }

      private boolean renderFinalLayer352(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 352, 1.26F, 2.18F, 0.006F, 92, 204, 255);
      }

      private boolean renderFinalLayer353(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 353, 1.305F, 2.36F, 0.0078F, 255, 96, 210);
      }

      private boolean renderFinalLayer354(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 354, 1.35F, 2.54F, 0.0096F, 255, 72, 232);
      }

      private boolean renderFinalLayer355(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 355, 1.395F, 2.72F, 0.0114F, 116, 236, 255);
      }

      private boolean renderFinalLayer356(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 356, 1.44F, 2.9F, 0.0132F, 210, 58, 255);
      }

      private boolean renderFinalLayer357(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 357, 0.72F, 3.08F, 0.015F, 255, 132, 180);
      }

      private boolean renderFinalLayer358(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 358, 0.765F, 3.26F, 0.0168F, 92, 204, 255);
      }

      private boolean renderFinalLayer359(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 359, 0.81F, 3.44F, 0.0186F, 255, 96, 210);
      }

      private boolean renderFinalLayer360(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 360, 0.855F, 3.62F, 0.0204F, 255, 72, 232);
      }

      private boolean renderFinalLayer361(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 361, 0.9F, 3.8F, 0.0222F, 116, 236, 255);
      }

      private boolean renderFinalLayer362(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 362, 0.945F, 3.98F, 0.024F, 210, 58, 255);
      }

      private boolean renderFinalLayer363(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 363, 0.99F, 4.16F, 0.006F, 255, 132, 180);
      }

      private boolean renderFinalLayer364(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 364, 1.035F, 2.0F, 0.0078F, 92, 204, 255);
      }

      private boolean renderFinalLayer365(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 365, 1.08F, 2.18F, 0.0096F, 255, 96, 210);
      }

      private boolean renderFinalLayer366(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 366, 1.125F, 2.36F, 0.0114F, 255, 72, 232);
      }

      private boolean renderFinalLayer367(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 367, 1.17F, 2.54F, 0.0132F, 116, 236, 255);
      }

      private boolean renderFinalLayer368(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 368, 1.215F, 2.72F, 0.015F, 210, 58, 255);
      }

      private boolean renderFinalLayer369(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 369, 1.26F, 2.9F, 0.0168F, 255, 132, 180);
      }

      private boolean renderFinalLayer370(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 370, 1.305F, 3.08F, 0.0186F, 92, 204, 255);
      }

      private boolean renderFinalLayer371(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 371, 1.35F, 3.26F, 0.0204F, 255, 96, 210);
      }

      private boolean renderFinalLayer372(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 372, 1.395F, 3.44F, 0.0222F, 255, 72, 232);
      }

      private boolean renderFinalLayer373(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 373, 1.44F, 3.62F, 0.024F, 116, 236, 255);
      }

      private boolean renderFinalLayer374(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 374, 0.72F, 3.8F, 0.006F, 210, 58, 255);
      }

      private boolean renderFinalLayer375(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 375, 0.765F, 3.98F, 0.0078F, 255, 132, 180);
      }

      private boolean renderFinalLayer376(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 376, 0.81F, 4.16F, 0.0096F, 92, 204, 255);
      }

      private boolean renderFinalLayer377(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 377, 0.855F, 2.0F, 0.0114F, 255, 96, 210);
      }

      private boolean renderFinalLayer378(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 378, 0.9F, 2.18F, 0.0132F, 255, 72, 232);
      }

      private boolean renderFinalLayer379(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 379, 0.945F, 2.36F, 0.015F, 116, 236, 255);
      }

      private boolean renderFinalLayer380(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 380, 0.99F, 2.54F, 0.0168F, 210, 58, 255);
      }

      private boolean renderFinalLayer381(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 381, 1.035F, 2.72F, 0.0186F, 255, 132, 180);
      }

      private boolean renderFinalLayer382(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 382, 1.08F, 2.9F, 0.0204F, 92, 204, 255);
      }

      private boolean renderFinalLayer383(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 383, 1.125F, 3.08F, 0.0222F, 255, 96, 210);
      }

      private boolean renderFinalLayer384(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 384, 1.17F, 3.26F, 0.024F, 255, 72, 232);
      }

      private boolean renderFinalLayer385(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 385, 1.215F, 3.44F, 0.006F, 116, 236, 255);
      }

      private boolean renderFinalLayer386(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 386, 1.26F, 3.62F, 0.0078F, 210, 58, 255);
      }

      private boolean renderFinalLayer387(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 387, 1.305F, 3.8F, 0.0096F, 255, 132, 180);
      }

      private boolean renderFinalLayer388(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 388, 1.35F, 3.98F, 0.0114F, 92, 204, 255);
      }

      private boolean renderFinalLayer389(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 389, 1.395F, 4.16F, 0.0132F, 255, 96, 210);
      }

      private boolean renderFinalLayer390(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 390, 1.44F, 2.0F, 0.015F, 255, 72, 232);
      }

      private boolean renderFinalLayer391(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 391, 0.72F, 2.18F, 0.0168F, 116, 236, 255);
      }

      private boolean renderFinalLayer392(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 392, 0.765F, 2.36F, 0.0186F, 210, 58, 255);
      }

      private boolean renderFinalLayer393(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 393, 0.81F, 2.54F, 0.0204F, 255, 132, 180);
      }

      private boolean renderFinalLayer394(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 394, 0.855F, 2.72F, 0.0222F, 92, 204, 255);
      }

      private boolean renderFinalLayer395(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 395, 0.9F, 2.9F, 0.024F, 255, 96, 210);
      }

      private boolean renderFinalLayer396(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 396, 0.945F, 3.08F, 0.006F, 255, 72, 232);
      }

      private boolean renderFinalLayer397(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 397, 0.99F, 3.26F, 0.0078F, 116, 236, 255);
      }

      private boolean renderFinalLayer398(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 398, 1.035F, 3.44F, 0.0096F, 210, 58, 255);
      }

      private boolean renderFinalLayer399(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 399, 1.08F, 3.62F, 0.0114F, 255, 132, 180);
      }

      private boolean renderFinalLayer400(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 400, 1.125F, 3.8F, 0.0132F, 92, 204, 255);
      }

      private boolean renderFinalLayer401(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 401, 1.17F, 3.98F, 0.015F, 255, 96, 210);
      }

      private boolean renderFinalLayer402(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 402, 1.215F, 4.16F, 0.0168F, 255, 72, 232);
      }

      private boolean renderFinalLayer403(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 403, 1.26F, 2.0F, 0.0186F, 116, 236, 255);
      }

      private boolean renderFinalLayer404(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 404, 1.305F, 2.18F, 0.0204F, 210, 58, 255);
      }

      private boolean renderFinalLayer405(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 405, 1.35F, 2.36F, 0.0222F, 255, 132, 180);
      }

      private boolean renderFinalLayer406(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 406, 1.395F, 2.54F, 0.024F, 92, 204, 255);
      }

      private boolean renderFinalLayer407(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 407, 1.44F, 2.72F, 0.006F, 255, 96, 210);
      }

      private boolean renderFinalLayer408(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 408, 0.72F, 2.9F, 0.0078F, 255, 72, 232);
      }

      private boolean renderFinalLayer409(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 409, 0.765F, 3.08F, 0.0096F, 116, 236, 255);
      }

      private boolean renderFinalLayer410(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 410, 0.81F, 3.26F, 0.0114F, 210, 58, 255);
      }

      private boolean renderFinalLayer411(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 411, 0.855F, 3.44F, 0.0132F, 255, 132, 180);
      }

      private boolean renderFinalLayer412(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 412, 0.9F, 3.62F, 0.015F, 92, 204, 255);
      }

      private boolean renderFinalLayer413(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 413, 0.945F, 3.8F, 0.0168F, 255, 96, 210);
      }

      private boolean renderFinalLayer414(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 414, 0.99F, 3.98F, 0.0186F, 255, 72, 232);
      }

      private boolean renderFinalLayer415(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 415, 1.035F, 4.16F, 0.0204F, 116, 236, 255);
      }

      private boolean renderFinalLayer416(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 416, 1.08F, 2.0F, 0.0222F, 210, 58, 255);
      }

      private boolean renderFinalLayer417(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 417, 1.125F, 2.18F, 0.024F, 255, 132, 180);
      }

      private boolean renderFinalLayer418(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 418, 1.17F, 2.36F, 0.006F, 92, 204, 255);
      }

      private boolean renderFinalLayer419(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 419, 1.215F, 2.54F, 0.0078F, 255, 96, 210);
      }

      private boolean renderFinalLayer420(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 420, 1.26F, 2.72F, 0.0096F, 255, 72, 232);
      }

      private boolean renderFinalLayer421(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 421, 1.305F, 2.9F, 0.0114F, 116, 236, 255);
      }

      private boolean renderFinalLayer422(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 422, 1.35F, 3.08F, 0.0132F, 210, 58, 255);
      }

      private boolean renderFinalLayer423(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 423, 1.395F, 3.26F, 0.015F, 255, 132, 180);
      }

      private boolean renderFinalLayer424(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 424, 1.44F, 3.44F, 0.0168F, 92, 204, 255);
      }

      private boolean renderFinalLayer425(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 425, 0.72F, 3.62F, 0.0186F, 255, 96, 210);
      }

      private boolean renderFinalLayer426(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 426, 0.765F, 3.8F, 0.0204F, 255, 72, 232);
      }

      private boolean renderFinalLayer427(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 427, 0.81F, 3.98F, 0.0222F, 116, 236, 255);
      }

      private boolean renderFinalLayer428(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 428, 0.855F, 4.16F, 0.024F, 210, 58, 255);
      }

      private boolean renderFinalLayer429(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 429, 0.9F, 2.0F, 0.006F, 255, 132, 180);
      }

      private boolean renderFinalLayer430(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 430, 0.945F, 2.18F, 0.0078F, 92, 204, 255);
      }

      private boolean renderFinalLayer431(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 431, 0.99F, 2.36F, 0.0096F, 255, 96, 210);
      }

      private boolean renderFinalLayer432(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 432, 1.035F, 2.54F, 0.0114F, 255, 72, 232);
      }

      private boolean renderFinalLayer433(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 433, 1.08F, 2.72F, 0.0132F, 116, 236, 255);
      }

      private boolean renderFinalLayer434(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 434, 1.125F, 2.9F, 0.015F, 210, 58, 255);
      }

      private boolean renderFinalLayer435(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 435, 1.17F, 3.08F, 0.0168F, 255, 132, 180);
      }

      private boolean renderFinalLayer436(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 436, 1.215F, 3.26F, 0.0186F, 92, 204, 255);
      }

      private boolean renderFinalLayer437(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 437, 1.26F, 3.44F, 0.0204F, 255, 96, 210);
      }

      private boolean renderFinalLayer438(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 438, 1.305F, 3.62F, 0.0222F, 255, 72, 232);
      }

      private boolean renderFinalLayer439(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 439, 1.35F, 3.8F, 0.024F, 116, 236, 255);
      }

      private boolean renderFinalLayer440(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 440, 1.395F, 3.98F, 0.006F, 210, 58, 255);
      }

      private boolean renderFinalLayer441(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 441, 1.44F, 4.16F, 0.0078F, 255, 132, 180);
      }

      private boolean renderFinalLayer442(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 442, 0.72F, 2.0F, 0.0096F, 92, 204, 255);
      }

      private boolean renderFinalLayer443(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 443, 0.765F, 2.18F, 0.0114F, 255, 96, 210);
      }

      private boolean renderFinalLayer444(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 444, 0.81F, 2.36F, 0.0132F, 255, 72, 232);
      }

      private boolean renderFinalLayer445(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 445, 0.855F, 2.54F, 0.015F, 116, 236, 255);
      }

      private boolean renderFinalLayer446(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 446, 0.9F, 2.72F, 0.0168F, 210, 58, 255);
      }

      private boolean renderFinalLayer447(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 447, 0.945F, 2.9F, 0.0186F, 255, 132, 180);
      }

      private boolean renderFinalLayer448(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 448, 0.99F, 3.08F, 0.0204F, 92, 204, 255);
      }

      private boolean renderFinalLayer449(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 449, 1.035F, 3.26F, 0.0222F, 255, 96, 210);
      }

      private boolean renderFinalLayer450(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 450, 1.08F, 3.44F, 0.024F, 255, 72, 232);
      }

      private boolean renderFinalLayer451(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 451, 1.125F, 3.62F, 0.006F, 116, 236, 255);
      }

      private boolean renderFinalLayer452(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 452, 1.17F, 3.8F, 0.0078F, 210, 58, 255);
      }

      private boolean renderFinalLayer453(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 453, 1.215F, 3.98F, 0.0096F, 255, 132, 180);
      }

      private boolean renderFinalLayer454(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 454, 1.26F, 4.16F, 0.0114F, 92, 204, 255);
      }

      private boolean renderFinalLayer455(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 455, 1.305F, 2.0F, 0.0132F, 255, 96, 210);
      }

      private boolean renderFinalLayer456(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 456, 1.35F, 2.18F, 0.015F, 255, 72, 232);
      }

      private boolean renderFinalLayer457(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 457, 1.395F, 2.36F, 0.0168F, 116, 236, 255);
      }

      private boolean renderFinalLayer458(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 458, 1.44F, 2.54F, 0.0186F, 210, 58, 255);
      }

      private boolean renderFinalLayer459(BufferBuilder buffer, Matrix4f matrix, Vec3 center, MichaelKeterVfxClient.Basis basis, float t, float alpha) {
         return this.renderFinalBurstLayer(buffer, matrix, center, basis, t, alpha, 459, 0.72F, 2.72F, 0.0204F, 255, 132, 180);
      }
   }
}
