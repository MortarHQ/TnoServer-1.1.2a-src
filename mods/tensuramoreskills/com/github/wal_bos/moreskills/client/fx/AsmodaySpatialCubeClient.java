package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
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

public final class AsmodaySpatialCubeClient {
   private static final Map<Integer, AsmodaySpatialCubeClient.Volley> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;

   private AsmodaySpatialCubeClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AsmodaySpatialCubeClient::clientTick);
         NeoForge.EVENT_BUS.addListener(AsmodaySpatialCubeClient::renderLevel);
      }
   }

   public static void play(
      int id,
      int casterId,
      int targetId,
      double casterX,
      double casterY,
      double casterZ,
      double targetX,
      double targetY,
      double targetZ,
      int visualCubes,
      int seed,
      int duration,
      boolean mastered,
      boolean shifted
   ) {
      init();
      ACTIVE.put(
         id,
         new AsmodaySpatialCubeClient.Volley(
            targetId,
            new Vec3(casterX, casterY, casterZ),
            new Vec3(targetX, targetY, targetZ),
            Mth.clamp(visualCubes, shifted ? 950 : 680, shifted ? 2000 : 1350),
            seed,
            Mth.clamp(duration, 100, 180),
            mastered,
            shifted
         )
      );
   }

   private static void clientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<Entry<Integer, AsmodaySpatialCubeClient.Volley>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            AsmodaySpatialCubeClient.Volley volley = iterator.next().getValue();
            volley.tick(mc);
            if (volley.age > volley.life) {
               iterator.remove();
            }
         }
      }
   }

   private static void renderLevel(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && !ACTIVE.isEmpty()) {
            PoseStack poseStack = event.getPoseStack();
            Camera camera = mc.gameRenderer.getMainCamera();
            Vec3 cam = camera.getPosition();
            Matrix4f matrix = poseStack.last().pose();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            RenderSystem.depthMask(true);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;

            for (AsmodaySpatialCubeClient.Volley volley : ACTIVE.values()) {
               wrote |= volley.render(buffer, matrix, cam, partial);
            }

            if (wrote) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static final class Volley {
      private final int targetId;
      private final Vec3 origin;
      private final Vec3 fallbackTarget;
      private final int cubes;
      private final int seed;
      private final int life;
      private final boolean mastered;
      private final boolean shifted;
      private final int streams;
      private Vec3 targetPos;
      private Vec3 prevTargetPos;
      private int age;

      private Volley(int targetId, Vec3 origin, Vec3 target, int cubes, int seed, int life, boolean mastered, boolean shifted) {
         this.targetId = targetId;
         this.origin = origin;
         this.fallbackTarget = target;
         this.cubes = cubes;
         this.seed = seed;
         this.life = life;
         this.mastered = mastered;
         this.shifted = shifted;
         this.streams = shifted ? 7 : 4;
         this.targetPos = target;
         this.prevTargetPos = target;
      }

      private void tick(Minecraft mc) {
         this.age++;
         Vec3 rawTarget = this.resolveTarget(mc, 1.0F);
         this.prevTargetPos = this.targetPos;
         Vec3 diff = rawTarget.subtract(this.targetPos);
         double distance = diff.length();
         if (distance > 0.001) {
            double follow = this.shifted ? 0.145 : 0.115;
            double maxStep = this.shifted ? 0.95 : 0.72;
            double step = Math.min(distance, Math.min(maxStep, 0.08 + distance * follow));
            this.targetPos = this.targetPos.add(diff.normalize().scale(step));
         }
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         Vec3 liveTarget = this.prevTargetPos.lerp(this.targetPos, partial);
         AsmodaySpatialCubeClient.Volley.Basis basis = this.basis(this.origin, liveTarget);
         float t = this.age + partial;
         float alpha = Mth.clamp(t / 6.0F, 0.0F, 1.0F) * Mth.clamp((this.life - t) / 18.0F, 0.0F, 1.0F);
         if (alpha <= 0.002F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < this.cubes; i++) {
            int stream = i % this.streams;
            int step = i / this.streams;
            float delay = step * (this.shifted ? 0.24F : 0.34F);
            float localAge = t - delay;
            if (!(localAge < 0.0F)) {
               float progress = Mth.clamp(localAge / (this.shifted ? 54.0F : 66.0F), 0.0F, 1.0F);
               progress = this.smoothAggressive(progress);
               if (!(progress >= 1.0F)) {
                  Vec3 p = this.streamPoint(this.origin, liveTarget, basis, stream, this.streams, step, progress, this.seed, this.shifted);
                  Vec3 prev = this.streamPoint(
                     this.origin, liveTarget, basis, stream, this.streams, step, Math.max(0.0F, progress - 0.04F), this.seed, this.shifted
                  );
                  Vec3 dir = p.subtract(prev);
                  if (dir.lengthSqr() < 0.001) {
                     dir = basis.forward;
                  }

                  dir = dir.normalize();
                  double size = this.cubeSize(i, step, stream);
                  AsmodaySpatialCubeClient.Volley.Basis cubeBasis = this.cubeBasis(basis, dir, t, i);
                  float streamFade = Mth.clamp((1.0F - progress) * 1.6F, 0.0F, 1.0F);
                  float cubeAlpha = alpha * streamFade;
                  if (i % (this.shifted ? 2 : 3) == 0) {
                     wrote |= this.renderBeam(buffer, matrix, cam, prev, p, size * 0.34, cubeAlpha * 0.9F, false);
                     wrote |= this.renderBeam(buffer, matrix, cam, prev, p, size * 0.085, cubeAlpha, true);
                  }

                  wrote |= this.renderSolidCube(buffer, matrix, cam, p, cubeBasis, size, 1.0F, i, true);
                  wrote |= this.renderSmallGlyphs(buffer, matrix, cam, p, cubeBasis, size * 1.055, cubeAlpha, i);
                  wrote |= this.renderWireCube(buffer, matrix, cam, p, cubeBasis, size * 1.08, cubeAlpha, true);
                  if (this.mastered && i % 5 == 0) {
                     wrote |= this.renderWireCube(buffer, matrix, cam, p.add(dir.scale(size * 0.2)), cubeBasis, size * 1.34, cubeAlpha * 0.4F, false);
                  }

                  if (this.shifted && i % 4 == 0) {
                     Vec3 p2 = p.subtract(dir.scale(size * 0.72));
                     wrote |= this.renderSolidCube(buffer, matrix, cam, p2, cubeBasis, size * 0.56, cubeAlpha, i + 2000, false);
                     wrote |= this.renderSmallGlyphs(buffer, matrix, cam, p2, cubeBasis, size * 0.66, cubeAlpha * 0.95F, i + 2300);
                     wrote |= this.renderWireCube(buffer, matrix, cam, p2, cubeBasis, size * 0.66, cubeAlpha, true);
                  }
               }
            }
         }

         return wrote;
      }

      private Vec3 resolveTarget(Minecraft mc, float partial) {
         if (mc.level != null && this.targetId != -1) {
            Entity targetEntity = mc.level.getEntity(this.targetId);
            if (targetEntity != null) {
               double x = Mth.lerp(partial, targetEntity.xOld, targetEntity.getX());
               double y = Mth.lerp(partial, targetEntity.yOld, targetEntity.getY());
               double z = Mth.lerp(partial, targetEntity.zOld, targetEntity.getZ());
               return new Vec3(x, y + targetEntity.getBbHeight() * 0.62, z);
            }
         }

         return this.fallbackTarget;
      }

      private double cubeSize(int i, int step, int stream) {
         double base = this.shifted ? 0.48 : 0.4;

         double variance = switch ((i + stream * 3) % 10) {
            case 0 -> 0.4;
            case 1 -> 0.3;
            case 2 -> 0.18;
            case 3 -> 0.11;
            case 4 -> 0.34;
            case 5 -> 0.22;
            default -> 0.0;
         };
         double pulse = Math.sin(this.age * 0.11 + i * 0.21) * 0.04;
         if (this.mastered) {
            variance += 0.1;
         }

         return base + variance + pulse;
      }

      private Vec3 streamPoint(
         Vec3 origin, Vec3 target, AsmodaySpatialCubeClient.Volley.Basis basis, int stream, int streams, int step, float progress, int seed, boolean shifted
      ) {
         double sideSpread = shifted ? 10.4 : 6.2;
         double side = (stream - (streams - 1) * 0.5) * (sideSpread / Math.max(1.0, streams - 1.0));
         double back = shifted ? 10.8 : 7.8;
         Vec3 start = origin.subtract(basis.forward.scale(back + step * 0.01))
            .add(basis.right.scale(side))
            .add(basis.up.scale(this.streamHeight(stream, shifted)));
         Vec3 end = target.add(basis.right.scale(side * 0.035)).add(basis.up.scale(this.streamHeight(stream, shifted) * 0.06));
         double bend = Math.sin(progress * Math.PI);
         double curveLift = bend * (shifted ? 3.0 : 2.0);
         double curveSide = bend * side * (shifted ? 0.18 : 0.13);
         Vec3 mid = origin.lerp(target, 0.46)
            .add(basis.right.scale(side * 0.43 + curveSide))
            .add(basis.up.scale(curveLift + this.streamHeight(stream, shifted)));
         Vec3 a = start.lerp(mid, progress);
         Vec3 b = mid.lerp(end, progress);
         Vec3 p = a.lerp(b, progress);
         double packedJitter = (this.hash(seed, stream * 911 + step * 37) - 0.5) * (shifted ? 0.13 : 0.085);
         double packedUp = (this.hash(seed, stream * 733 + step * 53) - 0.5) * (shifted ? 0.13 : 0.08);
         double packedForward = (this.hash(seed, stream * 557 + step * 71) - 0.5) * (shifted ? 0.18 : 0.11);
         return p.add(basis.right.scale(packedJitter)).add(basis.up.scale(packedUp)).add(basis.forward.scale(packedForward));
      }

      private double streamHeight(int stream, boolean shifted) {
         return switch (stream % 4) {
            case 0 -> shifted ? 2.25 : 1.65;
            case 1 -> shifted ? 0.65 : 0.4;
            case 2 -> shifted ? 3.1 : 2.25;
            default -> shifted ? -0.05 : 0.0;
         };
      }

      private float smoothAggressive(float x) {
         x = Mth.clamp(x, 0.0F, 1.0F);
         x = x * x * (3.0F - 2.0F * x);
         return Mth.clamp(x * 1.18F, 0.0F, 1.0F);
      }

      private AsmodaySpatialCubeClient.Volley.Basis cubeBasis(AsmodaySpatialCubeClient.Volley.Basis basis, Vec3 dir, float t, int i) {
         Vec3 forward = dir.lengthSqr() > 0.001 ? dir.normalize() : basis.forward;
         Vec3 right = basis.right;
         Vec3 up = basis.up;
         double a = t * (this.shifted ? 0.19 : 0.15) + i * 0.43;
         double c = Math.cos(a);
         double s = Math.sin(a);
         Vec3 spunRight = right.scale(c).add(up.scale(s)).normalize();
         Vec3 spunUp = spunRight.cross(forward);
         if (spunUp.lengthSqr() < 0.001) {
            spunUp = up;
         }

         spunUp = spunUp.normalize();
         Vec3 fixedRight = forward.cross(spunUp);
         if (fixedRight.lengthSqr() < 0.001) {
            fixedRight = right;
         }

         fixedRight = fixedRight.normalize();
         return new AsmodaySpatialCubeClient.Volley.Basis(forward, fixedRight, spunUp);
      }

      private AsmodaySpatialCubeClient.Volley.Basis basis(Vec3 caster, Vec3 target) {
         Vec3 aim = target.subtract(caster);
         if (aim.lengthSqr() < 0.001) {
            aim = new Vec3(0.0, 0.0, 1.0);
         }

         Vec3 forward = aim.normalize();
         Vec3 worldUp = Math.abs(forward.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         Vec3 right = forward.cross(worldUp);
         if (right.lengthSqr() < 0.001) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = right.cross(forward);
         if (up.lengthSqr() < 0.001) {
            up = new Vec3(0.0, 1.0, 0.0);
         }

         up = up.normalize();
         return new AsmodaySpatialCubeClient.Volley.Basis(forward, right, up);
      }

      private float hash(int seed, int index) {
         int x = seed ^ index * 1103515245;
         x ^= x << 13;
         x ^= x >>> 17;
         x ^= x << 5;
         return (x & 1023) / 1023.0F;
      }

      private boolean renderSolidCube(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         AsmodaySpatialCubeClient.Volley.Basis basis,
         double size,
         float alpha,
         int index,
         boolean hot
      ) {
         double s = size * 0.5;
         Vec3 f = basis.forward.scale(s);
         Vec3 r = basis.right.scale(s);
         Vec3 u = basis.up.scale(s);
         Vec3 p000 = center.subtract(r).subtract(u).subtract(f);
         Vec3 p001 = center.subtract(r).subtract(u).add(f);
         Vec3 p010 = center.subtract(r).add(u).subtract(f);
         Vec3 p011 = center.subtract(r).add(u).add(f);
         Vec3 p100 = center.add(r).subtract(u).subtract(f);
         Vec3 p101 = center.add(r).subtract(u).add(f);
         Vec3 p110 = center.add(r).add(u).subtract(f);
         Vec3 p111 = center.add(r).add(u).add(f);
         int pulse = (int)(Mth.sin(index * 0.63F + this.age * 0.14F) * 16.0F);
         int cr = hot ? 90 + pulse : 46 + pulse / 2;
         int cg = 0;
         int cb = hot ? 20 : 10;
         boolean wrote = this.quad(buffer, matrix, cam, p001, p101, p111, p011, cr, cg, cb, alpha);
         wrote |= this.quad(buffer, matrix, cam, p100, p000, p010, p110, cr, cg, cb, alpha);
         wrote |= this.quad(buffer, matrix, cam, p000, p001, p011, p010, cr - 12, cg, cb, alpha);
         wrote |= this.quad(buffer, matrix, cam, p101, p100, p110, p111, cr + 10, cg, cb + 2, alpha);
         wrote |= this.quad(buffer, matrix, cam, p010, p011, p111, p110, cr + 26, cg, cb + 8, alpha);
         return wrote | this.quad(buffer, matrix, cam, p000, p100, p101, p001, Math.max(8, cr - 26), cg, Math.max(4, cb - 4), alpha);
      }

      private boolean renderSmallGlyphs(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodaySpatialCubeClient.Volley.Basis basis, double size, float alpha, int index
      ) {
         float a = alpha * 1.05F;
         boolean wrote = false;
         wrote |= this.renderGlyphOnFace(buffer, matrix, cam, center.add(basis.forward.scale(size * 0.52)), basis.right, basis.up, size, a, index);
         wrote |= this.renderGlyphOnFace(
            buffer, matrix, cam, center.add(basis.right.scale(size * 0.52)), basis.forward.scale(-1.0), basis.up, size, a * 0.78F, index + 17
         );
         wrote |= this.renderGlyphOnFace(
            buffer, matrix, cam, center.subtract(basis.right.scale(size * 0.52)), basis.forward, basis.up, size, a * 0.72F, index + 31
         );
         if ((index & 3) == 0) {
            wrote |= this.renderGlyphOnFace(
               buffer, matrix, cam, center.add(basis.up.scale(size * 0.52)), basis.right, basis.forward.scale(-1.0), size, a * 0.62F, index + 49
            );
         }

         return wrote;
      }

      private boolean renderGlyphOnFace(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 faceCenter, Vec3 r, Vec3 u, double size, float alpha, int index) {
         double outer = size * 0.215;
         double inner = size * 0.085;
         double mark = size * 0.155;
         double w = size * 0.018;
         boolean wrote = false;
         Vec3 a = faceCenter.add(r.scale(-outer)).add(u.scale(outer));
         Vec3 b = faceCenter.add(r.scale(outer)).add(u.scale(outer));
         Vec3 c = faceCenter.add(r.scale(outer)).add(u.scale(-outer));
         Vec3 d = faceCenter.add(r.scale(-outer)).add(u.scale(-outer));
         wrote |= this.renderBeam(buffer, matrix, cam, a, b, w, alpha * 0.92F, true);
         wrote |= this.renderBeam(buffer, matrix, cam, b, c, w, alpha * 0.92F, true);
         wrote |= this.renderBeam(buffer, matrix, cam, c, d, w, alpha * 0.92F, true);
         wrote |= this.renderBeam(buffer, matrix, cam, d, a, w, alpha * 0.92F, true);
         wrote |= this.renderBeam(buffer, matrix, cam, faceCenter.add(r.scale(-inner)), faceCenter.add(r.scale(inner)), w * 0.62, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, faceCenter.add(u.scale(-inner)), faceCenter.add(u.scale(inner)), w * 0.62, alpha, true);
         if ((index & 1) == 0) {
            wrote |= this.renderBeam(
               buffer,
               matrix,
               cam,
               faceCenter.add(r.scale(-mark)).add(u.scale(-mark)),
               faceCenter.add(r.scale(mark)).add(u.scale(mark)),
               w * 0.48,
               alpha * 0.76F,
               true
            );
            wrote |= this.renderBeam(
               buffer,
               matrix,
               cam,
               faceCenter.add(r.scale(-mark)).add(u.scale(mark * 0.2)),
               faceCenter.add(r.scale(mark * 0.08)).add(u.scale(mark)),
               w * 0.38,
               alpha * 0.62F,
               true
            );
         } else {
            wrote |= this.renderBeam(
               buffer,
               matrix,
               cam,
               faceCenter.add(r.scale(-mark)).add(u.scale(mark)),
               faceCenter.add(r.scale(mark)).add(u.scale(-mark)),
               w * 0.48,
               alpha * 0.76F,
               true
            );
            wrote |= this.renderBeam(
               buffer,
               matrix,
               cam,
               faceCenter.add(r.scale(mark * 0.2)).add(u.scale(-mark)),
               faceCenter.add(r.scale(mark)).add(u.scale(mark * 0.08)),
               w * 0.38,
               alpha * 0.62F,
               true
            );
         }

         double dot = size * 0.03;
         wrote |= this.renderQuadGlyphDot(buffer, matrix, cam, faceCenter.add(r.scale(mark)).add(u.scale(mark)), r, u, dot, alpha * 0.86F);
         return wrote | this.renderQuadGlyphDot(buffer, matrix, cam, faceCenter.add(r.scale(-mark)).add(u.scale(-mark)), r, u, dot, alpha * 0.6F);
      }

      private boolean renderQuadGlyphDot(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 r, Vec3 u, double size, float alpha) {
         Vec3 a = center.add(r.scale(-size)).add(u.scale(size));
         Vec3 b = center.add(r.scale(size)).add(u.scale(size));
         Vec3 c = center.add(r.scale(size)).add(u.scale(-size));
         Vec3 d = center.add(r.scale(-size)).add(u.scale(-size));
         return this.quad(buffer, matrix, cam, a, b, c, d, 255, 92, 66, alpha);
      }

      private boolean renderWireCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodaySpatialCubeClient.Volley.Basis basis, double size, float alpha, boolean hot
      ) {
         double s = size * 0.5;
         Vec3 f = basis.forward.scale(s);
         Vec3 r = basis.right.scale(s);
         Vec3 u = basis.up.scale(s);
         Vec3 p000 = center.subtract(r).subtract(u).subtract(f);
         Vec3 p001 = center.subtract(r).subtract(u).add(f);
         Vec3 p010 = center.subtract(r).add(u).subtract(f);
         Vec3 p011 = center.subtract(r).add(u).add(f);
         Vec3 p100 = center.add(r).subtract(u).subtract(f);
         Vec3 p101 = center.add(r).subtract(u).add(f);
         Vec3 p110 = center.add(r).add(u).subtract(f);
         Vec3 p111 = center.add(r).add(u).add(f);
         double w = this.shifted ? 0.042 : 0.034;
         if (this.mastered) {
            w += 0.007;
         }

         float glow = hot ? alpha : alpha * 0.58F;
         boolean wrote = false;
         wrote |= this.renderBeam(buffer, matrix, cam, p000, p001, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p010, p011, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p100, p101, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p110, p111, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p000, p010, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p001, p011, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p100, p110, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p101, p111, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p000, p100, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p001, p101, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p010, p110, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p011, p111, w * 2.6, glow * 0.28F, false);
         wrote |= this.renderBeam(buffer, matrix, cam, p000, p001, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p010, p011, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p100, p101, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p110, p111, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p000, p010, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p001, p011, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p100, p110, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p101, p111, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p000, p100, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p001, p101, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, p010, p110, w, alpha, true);
         return wrote | this.renderBeam(buffer, matrix, cam, p011, p111, w, alpha, true);
      }

      private boolean renderBeam(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, double width, float alpha, boolean core) {
         Vec3 dir = end.subtract(start);
         if (dir.lengthSqr() < 0.001) {
            return false;
         }

         dir = dir.normalize();
         Vec3 ref = Math.abs(dir.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         Vec3 side = dir.cross(ref);
         if (side.lengthSqr() < 0.001) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize().scale(width);
         Vec3 side2 = dir.cross(side);
         if (side2.lengthSqr() < 0.001) {
            side2 = new Vec3(0.0, 1.0, 0.0);
         }

         side2 = side2.normalize().scale(width * 0.72);
         if (core) {
            boolean wrote = this.quad(
               buffer, matrix, cam, start.add(side), end.add(side.scale(0.34)), end.subtract(side.scale(0.34)), start.subtract(side), 255, 42, 30, alpha
            );
            return wrote
               | this.quad(
                  buffer,
                  matrix,
                  cam,
                  start.add(side2),
                  end.add(side2.scale(0.34)),
                  end.subtract(side2.scale(0.34)),
                  start.subtract(side2),
                  255,
                  122,
                  76,
                  alpha * 0.94F
               );
         } else {
            boolean wrote = this.quad(
               buffer, matrix, cam, start.add(side), end.add(side.scale(0.34)), end.subtract(side.scale(0.34)), start.subtract(side), 230, 0, 18, alpha * 0.9F
            );
            return wrote
               | this.quad(
                  buffer,
                  matrix,
                  cam,
                  start.add(side2),
                  end.add(side2.scale(0.34)),
                  end.subtract(side2.scale(0.34)),
                  start.subtract(side2),
                  76,
                  0,
                  26,
                  alpha * 0.7F
               );
         }
      }

      private boolean quad(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, float alpha) {
         int ia = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         if (ia <= 0) {
            return false;
         }

         this.vertex(buffer, matrix, cam, a, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, b, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, c, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, a, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, c, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, d, r, g, bl, ia);
         return true;
      }

      private void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p, int r, int g, int b, int a) {
         buffer.addVertex(matrix, (float)(p.x - cam.x), (float)(p.y - cam.y), (float)(p.z - cam.z)).setColor(r, g, b, a);
      }

      private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
      }
   }
}
