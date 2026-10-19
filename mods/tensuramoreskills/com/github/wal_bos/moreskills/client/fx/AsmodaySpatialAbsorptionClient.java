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

public final class AsmodaySpatialAbsorptionClient {
   private static final Map<Integer, AsmodaySpatialAbsorptionClient.AbsorbFx> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;

   private AsmodaySpatialAbsorptionClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AsmodaySpatialAbsorptionClient::clientTick);
         NeoForge.EVENT_BUS.addListener(AsmodaySpatialAbsorptionClient::renderLevel);
      }
   }

   public static void play(
      int id,
      int ownerId,
      int threatId,
      double startX,
      double startY,
      double startZ,
      double endX,
      double endY,
      double endZ,
      double size,
      int duration,
      int seed,
      boolean mastered
   ) {
      init();
      ACTIVE.put(
         id,
         new AsmodaySpatialAbsorptionClient.AbsorbFx(
            ownerId,
            threatId,
            new Vec3(startX, startY, startZ),
            new Vec3(endX, endY, endZ),
            Mth.clamp((float)size, 0.65F, mastered ? 4.2F : 3.2F),
            Mth.clamp(duration, 12, 80),
            seed,
            mastered
         )
      );
   }

   private static void clientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<Entry<Integer, AsmodaySpatialAbsorptionClient.AbsorbFx>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            AsmodaySpatialAbsorptionClient.AbsorbFx fx = iterator.next().getValue();
            fx.age++;
            if (fx.age > fx.duration) {
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

            for (AsmodaySpatialAbsorptionClient.AbsorbFx fx : ACTIVE.values()) {
               wrote |= fx.render(buffer, matrix, cam, partial);
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

   private static final class AbsorbFx {
      private final int ownerId;
      private final int threatId;
      private final Vec3 start;
      private final Vec3 fallbackEnd;
      private final double size;
      private final int duration;
      private final int seed;
      private final boolean mastered;
      private int age;

      private AbsorbFx(int ownerId, int threatId, Vec3 start, Vec3 fallbackEnd, double size, int duration, int seed, boolean mastered) {
         this.ownerId = ownerId;
         this.threatId = threatId;
         this.start = start;
         this.fallbackEnd = fallbackEnd;
         this.size = size;
         this.duration = duration;
         this.seed = seed;
         this.mastered = mastered;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         Minecraft mc = Minecraft.getInstance();
         float t = this.age + partial;
         float raw = Mth.clamp(t / Math.max(1.0F, this.duration), 0.0F, 1.0F);
         float progress = raw * raw * (3.0F - 2.0F * raw);
         float appear = Mth.clamp(t / 4.0F, 0.0F, 1.0F);
         float fade = Mth.clamp((this.duration - t) / 8.0F, 0.0F, 1.0F);
         float alpha = appear * fade;
         if (alpha <= 0.002F) {
            return false;
         }

         Vec3 threat = this.resolveThreat(mc, partial);
         Vec3 end = this.resolveOwnerPoint(mc, partial);
         Vec3 base = threat == null ? this.start.lerp(end, progress) : threat.lerp(end, progress * 0.42);
         Vec3 pulled = this.start.lerp(end, progress);
         Vec3 center = base.lerp(pulled, 0.55 + progress * 0.35);
         AsmodaySpatialAbsorptionClient.AbsorbFx.Basis basis = this.cubeBasis(end, center, t);
         double currentSize = this.size * (1.16 - progress * 0.34);
         boolean wrote = false;
         wrote |= this.renderAbsorptionCube(buffer, matrix, cam, center, basis, currentSize, alpha, this.seed);
         wrote |= this.renderRetractTethers(buffer, matrix, cam, center, end, basis, currentSize, alpha, t);
         wrote |= this.renderOwnerGate(buffer, matrix, cam, end, basis, currentSize * (this.mastered ? 1.15 : 0.95), alpha, t);
         if (progress > 0.72F) {
            wrote |= this.renderImplosion(buffer, matrix, cam, center, basis, currentSize, alpha * Mth.clamp((progress - 0.72F) / 0.28F, 0.0F, 1.0F), t);
         }

         return wrote;
      }

      private Vec3 resolveThreat(Minecraft mc, float partial) {
         if (mc.level == null) {
            return null;
         }

         Entity threat = mc.level.getEntity(this.threatId);
         if (threat == null) {
            return null;
         }

         double x = Mth.lerp(partial, threat.xOld, threat.getX());
         double y = Mth.lerp(partial, threat.yOld, threat.getY()) + threat.getBbHeight() * 0.5;
         double z = Mth.lerp(partial, threat.zOld, threat.getZ());
         return new Vec3(x, y, z);
      }

      private Vec3 resolveOwnerPoint(Minecraft mc, float partial) {
         if (mc.level != null) {
            Entity owner = mc.level.getEntity(this.ownerId);
            if (owner != null) {
               double x = Mth.lerp(partial, owner.xOld, owner.getX());
               double y = Mth.lerp(partial, owner.yOld, owner.getY()) + owner.getBbHeight() * 0.62;
               double z = Mth.lerp(partial, owner.zOld, owner.getZ());
               return new Vec3(x, y, z).lerp(this.fallbackEnd, 0.2);
            }
         }

         return this.fallbackEnd;
      }

      private AsmodaySpatialAbsorptionClient.AbsorbFx.Basis cubeBasis(Vec3 ownerPoint, Vec3 center, float t) {
         Vec3 forward = ownerPoint.subtract(center);
         if (forward.lengthSqr() < 0.001) {
            forward = new Vec3(0.0, 0.0, 1.0);
         }

         forward = forward.normalize();
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
         double angle = t * (this.mastered ? 0.18 : 0.12) + this.seed * 0.001;
         double c = Math.cos(angle);
         double s = Math.sin(angle);
         Vec3 spunRight = right.scale(c).add(up.scale(s)).normalize();
         Vec3 spunUp = spunRight.cross(forward).normalize();
         return new AsmodaySpatialAbsorptionClient.AbsorbFx.Basis(forward, spunRight, spunUp);
      }

      private boolean renderAbsorptionCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodaySpatialAbsorptionClient.AbsorbFx.Basis basis, double size, float alpha, int index
      ) {
         boolean wrote = false;
         wrote |= this.renderSolidCube(buffer, matrix, cam, center, basis, size, alpha * 0.72F, index);
         wrote |= this.renderWireCube(buffer, matrix, cam, center, basis, size * 1.02, alpha, true);
         wrote |= this.renderWireCube(buffer, matrix, cam, center, basis, size * 1.28, alpha * 0.34F, false);
         return wrote | this.renderFaceGlyphs(buffer, matrix, cam, center, basis, size * 1.04, alpha, index);
      }

      private boolean renderRetractTethers(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         Vec3 end,
         AsmodaySpatialAbsorptionClient.AbsorbFx.Basis basis,
         double size,
         float alpha,
         float t
      ) {
         boolean wrote = false;

         for (int i = 0; i < 6; i++) {
            double a = (Math.PI * 2) * i / 6.0 + t * 0.12;
            Vec3 offset = basis.right.scale(Math.cos(a) * size * 0.62).add(basis.up.scale(Math.sin(a) * size * 0.62));
            Vec3 p0 = center.add(offset);
            Vec3 p1 = end.add(offset.scale(0.1));
            wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, size * 0.018, alpha * 0.76F, true);
            wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, size * 0.06, alpha * 0.18F, false);
         }

         return wrote;
      }

      private boolean renderOwnerGate(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodaySpatialAbsorptionClient.AbsorbFx.Basis basis, double radius, float alpha, float t
      ) {
         boolean wrote = false;

         for (int i = 0; i < 4; i++) {
            double a0 = (Math.PI / 2) * i + t * 0.1;
            double a1 = (Math.PI / 2) * (i + 1) + t * 0.1;
            Vec3 p0 = center.add(basis.right.scale(Math.cos(a0) * radius)).add(basis.up.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(basis.right.scale(Math.cos(a1) * radius)).add(basis.up.scale(Math.sin(a1) * radius));
            wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, radius * 0.025, alpha * 0.72F, true);
         }

         return wrote;
      }

      private boolean renderImplosion(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodaySpatialAbsorptionClient.AbsorbFx.Basis basis, double size, float alpha, float t
      ) {
         boolean wrote = false;

         for (int i = 0; i < 8; i++) {
            double a = (Math.PI * 2) * i / 8.0 + t * 0.18;
            Vec3 dir = basis.right.scale(Math.cos(a)).add(basis.up.scale(Math.sin(a)));
            if (!(dir.lengthSqr() < 0.001)) {
               dir = dir.normalize();
               Vec3 p0 = center.add(dir.scale(size * 1.6));
               Vec3 p1 = center.add(dir.scale(size * 0.18));
               wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, size * 0.035, alpha, true);
            }
         }

         return wrote;
      }

      private boolean renderSolidCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodaySpatialAbsorptionClient.AbsorbFx.Basis basis, double size, float alpha, int index
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
         int cr = this.mastered ? 74 : 52;
         int cg = 0;
         int cb = 10;
         boolean wrote = this.quad(buffer, matrix, cam, p001, p101, p111, p011, cr + 12, cg, cb + 4, alpha);
         wrote |= this.quad(buffer, matrix, cam, p100, p000, p010, p110, Math.max(8, cr - 18), cg, cb, alpha);
         wrote |= this.quad(buffer, matrix, cam, p000, p001, p011, p010, cr - 8, cg, cb, alpha);
         wrote |= this.quad(buffer, matrix, cam, p101, p100, p110, p111, cr + 4, cg, cb + 2, alpha);
         wrote |= this.quad(buffer, matrix, cam, p010, p011, p111, p110, cr + 18, cg, cb + 6, alpha);
         return wrote | this.quad(buffer, matrix, cam, p000, p100, p101, p001, Math.max(6, cr - 24), cg, Math.max(2, cb - 2), alpha);
      }

      private boolean renderFaceGlyphs(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodaySpatialAbsorptionClient.AbsorbFx.Basis basis, double size, float alpha, int index
      ) {
         boolean wrote = false;
         wrote |= this.renderGlyph(buffer, matrix, cam, center.add(basis.forward.scale(size * 0.512)), basis.right, basis.up, size, alpha * 0.92F, index);
         wrote |= this.renderGlyph(
            buffer, matrix, cam, center.add(basis.right.scale(size * 0.512)), basis.forward.scale(-1.0), basis.up, size, alpha * 0.72F, index + 11
         );
         return wrote
            | this.renderGlyph(buffer, matrix, cam, center.subtract(basis.right.scale(size * 0.512)), basis.forward, basis.up, size, alpha * 0.66F, index + 23);
      }

      private boolean renderGlyph(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 faceCenter, Vec3 r, Vec3 u, double size, float alpha, int index) {
         double outer = size * 0.22;
         double inner = size * 0.082;
         double mark = size * 0.15;
         double w = size * 0.012;
         boolean wrote = false;
         Vec3 a = faceCenter.add(r.scale(-outer)).add(u.scale(outer));
         Vec3 b = faceCenter.add(r.scale(outer)).add(u.scale(outer));
         Vec3 c = faceCenter.add(r.scale(outer)).add(u.scale(-outer));
         Vec3 d = faceCenter.add(r.scale(-outer)).add(u.scale(-outer));
         wrote |= this.renderBeam(buffer, matrix, cam, a, b, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, b, c, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, c, d, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, d, a, w, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, faceCenter.add(r.scale(-inner)), faceCenter.add(r.scale(inner)), w * 0.58, alpha, true);
         wrote |= this.renderBeam(buffer, matrix, cam, faceCenter.add(u.scale(-inner)), faceCenter.add(u.scale(inner)), w * 0.58, alpha, true);
         if ((index & 1) == 0) {
            wrote |= this.renderBeam(
               buffer,
               matrix,
               cam,
               faceCenter.add(r.scale(-mark)).add(u.scale(-mark)),
               faceCenter.add(r.scale(mark)).add(u.scale(mark)),
               w * 0.4,
               alpha * 0.7F,
               true
            );
         } else {
            wrote |= this.renderBeam(
               buffer,
               matrix,
               cam,
               faceCenter.add(r.scale(-mark)).add(u.scale(mark)),
               faceCenter.add(r.scale(mark)).add(u.scale(-mark)),
               w * 0.4,
               alpha * 0.7F,
               true
            );
         }

         return wrote;
      }

      private boolean renderWireCube(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         AsmodaySpatialAbsorptionClient.AbsorbFx.Basis basis,
         double size,
         float alpha,
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
         double w = size * (this.mastered ? 0.016 : 0.013);
         float glow = hot ? alpha : alpha * 0.42F;
         boolean wrote = false;
         wrote |= this.edge(buffer, matrix, cam, p000, p001, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p010, p011, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p100, p101, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p110, p111, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p000, p010, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p001, p011, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p100, p110, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p101, p111, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p000, p100, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p001, p101, w, glow);
         wrote |= this.edge(buffer, matrix, cam, p010, p110, w, glow);
         return wrote | this.edge(buffer, matrix, cam, p011, p111, w, glow);
      }

      private boolean edge(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, double w, float alpha) {
         boolean wrote = this.renderBeam(buffer, matrix, cam, a, b, w * 3.2, alpha * 0.28F, false);
         return wrote | this.renderBeam(buffer, matrix, cam, a, b, w, alpha, true);
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
               buffer, matrix, cam, start.add(side), end.add(side.scale(0.34)), end.subtract(side.scale(0.34)), start.subtract(side), 255, 20, 16, alpha
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
                  76,
                  42,
                  alpha * 0.88F
               );
         } else {
            boolean wrote = this.quad(
               buffer, matrix, cam, start.add(side), end.add(side.scale(0.34)), end.subtract(side.scale(0.34)), start.subtract(side), 150, 0, 12, alpha * 0.86F
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
                  38,
                  0,
                  14,
                  alpha * 0.64F
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
