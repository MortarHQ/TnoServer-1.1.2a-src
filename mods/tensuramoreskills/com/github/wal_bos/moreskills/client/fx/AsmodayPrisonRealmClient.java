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

public final class AsmodayPrisonRealmClient {
   private static final Map<Integer, AsmodayPrisonRealmClient.PrisonFx> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;

   private AsmodayPrisonRealmClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AsmodayPrisonRealmClient::clientTick);
         NeoForge.EVENT_BUS.addListener(AsmodayPrisonRealmClient::renderLevel);
      }
   }

   public static void play(
      int id, int ownerId, int targetId, double centerX, double centerY, double centerZ, double size, int duration, int seed, int phase, boolean mastered
   ) {
      init();
      ACTIVE.put(id, new AsmodayPrisonRealmClient.PrisonFx(ownerId, targetId, new Vec3(centerX, centerY, centerZ), size, duration, seed, phase, mastered));
   }

   private static void clientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<Entry<Integer, AsmodayPrisonRealmClient.PrisonFx>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            AsmodayPrisonRealmClient.PrisonFx fx = iterator.next().getValue();
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

            for (AsmodayPrisonRealmClient.PrisonFx fx : ACTIVE.values()) {
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

   private static final class PrisonFx {
      private final int ownerId;
      private final int targetId;
      private final Vec3 fallbackCenter;
      private final double size;
      private final int duration;
      private final int seed;
      private final int phase;
      private final boolean mastered;
      private int age;

      private PrisonFx(int ownerId, int targetId, Vec3 fallbackCenter, double size, int duration, int seed, int phase, boolean mastered) {
         this.ownerId = ownerId;
         this.targetId = targetId;
         this.fallbackCenter = fallbackCenter;
         this.size = size;
         this.duration = duration;
         this.seed = seed;
         this.phase = phase;
         this.mastered = mastered;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         float t = this.age + partial;
         float life = Math.max(1.0F, this.duration);
         float progress = Mth.clamp(t / life, 0.0F, 1.0F);
         float appear = Mth.clamp(t / 6.0F, 0.0F, 1.0F);
         float fade = this.phase == 1 ? Mth.clamp((life - t) / 35.0F, 0.12F, 1.0F) : Mth.clamp((life - t) / 10.0F, 0.0F, 1.0F);
         float alpha = appear * fade;
         if (alpha <= 0.002F) {
            return false;
         }

         Minecraft mc = Minecraft.getInstance();
         Vec3 center = this.resolveTarget(mc, partial);
         double pulse = Math.sin(t * 0.12 + this.seed * 0.001) * 0.04;
         double currentSize = this.size;
         if (this.phase == 0) {
            currentSize = this.size * (0.45 + progress * 0.75);
         } else if (this.phase == 1) {
            currentSize = this.size * (1.25 - Mth.clamp(progress * 0.45F, 0.0F, 0.45F));
         } else if (this.phase == 2) {
            currentSize = this.size * (0.85 + pulse);
         }

         AsmodayPrisonRealmClient.PrisonFx.Basis basis = this.basis(center, t);
         boolean wrote = false;
         wrote |= this.renderMassiveOuterCube(buffer, matrix, cam, center, basis, currentSize, alpha);
         wrote |= this.renderInnerSentenceCube(buffer, matrix, cam, center, basis, currentSize * 0.62, alpha * 0.92F);
         wrote |= this.renderGlyphWalls(buffer, matrix, cam, center, basis, currentSize, alpha, t);
         wrote |= this.renderCoordinateChains(buffer, matrix, cam, center, basis, currentSize, alpha, t);
         wrote |= this.renderClosingPlanes(buffer, matrix, cam, center, basis, currentSize, alpha, progress);
         if (this.phase == 1 || this.phase == 2) {
            wrote |= this.renderSpatialImplosion(buffer, matrix, cam, center, basis, currentSize, alpha, t);
         }

         return wrote;
      }

      private Vec3 resolveTarget(Minecraft mc, float partial) {
         if (mc.level == null) {
            return this.fallbackCenter;
         }

         Entity target = mc.level.getEntity(this.targetId);
         if (target == null) {
            return this.fallbackCenter;
         }

         double x = Mth.lerp(partial, target.xOld, target.getX());
         double y = Mth.lerp(partial, target.yOld, target.getY()) + target.getBbHeight() * 0.52;
         double z = Mth.lerp(partial, target.zOld, target.getZ());
         return new Vec3(x, y, z);
      }

      private AsmodayPrisonRealmClient.PrisonFx.Basis basis(Vec3 center, float t) {
         Vec3 forward = new Vec3(Math.sin(t * 0.015), 0.1, Math.cos(t * 0.015)).normalize();
         Vec3 right = forward.cross(new Vec3(0.0, 1.0, 0.0));
         if (right.lengthSqr() < 0.001) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = right.cross(forward).normalize();
         double spin = this.phase == 1 ? t * 0.018 : t * 0.03;
         double c = Math.cos(spin);
         double s = Math.sin(spin);
         Vec3 spunRight = right.scale(c).add(up.scale(s)).normalize();
         Vec3 spunUp = spunRight.cross(forward).normalize();
         return new AsmodayPrisonRealmClient.PrisonFx.Basis(forward, spunRight, spunUp);
      }

      private boolean renderMassiveOuterCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayPrisonRealmClient.PrisonFx.Basis basis, double size, float alpha
      ) {
         boolean wrote = false;
         wrote |= this.renderCubeFaces(buffer, matrix, cam, center, basis, size, alpha * 0.16F, 42, 0, 8);
         wrote |= this.renderWireCube(buffer, matrix, cam, center, basis, size, size * 0.01, alpha, 255, 18, 10);
         wrote |= this.renderWireCube(buffer, matrix, cam, center, basis, size * 1.035, size * 0.005, alpha * 0.48F, 255, 90, 30);
         return wrote | this.renderWireCube(buffer, matrix, cam, center, basis, size * 1.1, size * 0.003, alpha * 0.28F, 140, 0, 10);
      }

      private boolean renderInnerSentenceCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayPrisonRealmClient.PrisonFx.Basis basis, double size, float alpha
      ) {
         boolean wrote = false;
         wrote |= this.renderCubeFaces(buffer, matrix, cam, center, basis, size, alpha * 0.28F, 72, 0, 10);
         wrote |= this.renderWireCube(buffer, matrix, cam, center, basis, size, size * 0.018, alpha, 255, 24, 16);
         return wrote | this.renderWireCube(buffer, matrix, cam, center, basis, size * 0.82, size * 0.01, alpha * 0.65F, 255, 120, 55);
      }

      private boolean renderGlyphWalls(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayPrisonRealmClient.PrisonFx.Basis basis, double size, float alpha, float t
      ) {
         boolean wrote = false;
         double half = size * 0.5;
         double glyphSize = size * 0.1;
         Vec3[] normals = new Vec3[]{basis.forward, basis.forward.scale(-1.0), basis.right, basis.right.scale(-1.0), basis.up, basis.up.scale(-1.0)};
         Vec3[] rights = new Vec3[]{basis.right, basis.right.scale(-1.0), basis.forward.scale(-1.0), basis.forward, basis.right, basis.right};
         Vec3[] ups = new Vec3[]{basis.up, basis.up, basis.up, basis.up, basis.forward.scale(-1.0), basis.forward};

         for (int face = 0; face < 6; face++) {
            Vec3 faceCenter = center.add(normals[face].scale(half + 0.012));

            for (int ix = -1; ix <= 1; ix++) {
               for (int iy = -1; iy <= 1; iy++) {
                  double wave = Math.sin(t * 0.08 + face * 1.7 + ix * 0.8 + iy * 1.3) * size * 0.008;
                  Vec3 p = faceCenter.add(rights[face].scale(ix * size * 0.2)).add(ups[face].scale(iy * size * 0.2)).add(normals[face].scale(wave));
                  wrote |= this.renderGlyph(buffer, matrix, cam, p, rights[face], ups[face], glyphSize, alpha * 0.88F, face + ix * 7 + iy * 13);
               }
            }
         }

         return wrote;
      }

      private boolean renderCoordinateChains(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayPrisonRealmClient.PrisonFx.Basis basis, double size, float alpha, float t
      ) {
         boolean wrote = false;
         double half = size * 0.5;

         for (int i = 0; i < 12; i++) {
            double a = (Math.PI * 2) * i / 12.0 + t * 0.018;
            Vec3 edge = basis.right.scale(Math.cos(a) * half).add(basis.up.scale(Math.sin(a) * half));
            Vec3 p0 = center.add(edge).subtract(basis.forward.scale(half));
            Vec3 p1 = center.add(edge.scale(0.64)).add(basis.forward.scale(half));
            wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, size * 0.004, alpha * 0.56F, true, 255, 30, 18);
            wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, size * 0.018, alpha * 0.14F, false, 130, 0, 14);
         }

         return wrote;
      }

      private boolean renderClosingPlanes(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayPrisonRealmClient.PrisonFx.Basis basis, double size, float alpha, float progress
      ) {
         boolean wrote = false;
         double half = size * 0.5;
         double close = half * (1.0 - Mth.clamp(progress, 0.0F, 1.0F) * 0.82);
         wrote |= this.renderPlane(buffer, matrix, cam, center.add(basis.forward.scale(close)), basis.right, basis.up, size, alpha * 0.12F);
         wrote |= this.renderPlane(buffer, matrix, cam, center.subtract(basis.forward.scale(close)), basis.right, basis.up, size, alpha * 0.12F);
         wrote |= this.renderPlane(buffer, matrix, cam, center.add(basis.right.scale(close)), basis.forward, basis.up, size, alpha * 0.1F);
         return wrote | this.renderPlane(buffer, matrix, cam, center.subtract(basis.right.scale(close)), basis.forward, basis.up, size, alpha * 0.1F);
      }

      private boolean renderSpatialImplosion(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayPrisonRealmClient.PrisonFx.Basis basis, double size, float alpha, float t
      ) {
         boolean wrote = false;

         for (int i = 0; i < 18; i++) {
            double a = (Math.PI * 2) * i / 18.0 + t * 0.06;
            Vec3 dir = basis.right.scale(Math.cos(a)).add(basis.up.scale(Math.sin(a))).normalize();
            Vec3 p0 = center.add(dir.scale(size * 0.88));
            Vec3 p1 = center.add(dir.scale(size * 0.12));
            wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, size * 0.012, alpha * 0.82F, true, 255, 20, 12);
         }

         return wrote;
      }

      private boolean renderGlyph(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 right, Vec3 up, double size, float alpha, int index) {
         double outer = size;
         double inner = size * 0.38;
         double w = size * 0.045;
         Vec3 a = center.add(right.scale(-outer)).add(up.scale(outer));
         Vec3 b = center.add(right.scale(outer)).add(up.scale(outer));
         Vec3 c = center.add(right.scale(outer)).add(up.scale(-outer));
         Vec3 d = center.add(right.scale(-outer)).add(up.scale(-outer));
         boolean wrote = false;
         wrote |= this.renderBeam(buffer, matrix, cam, a, b, w, alpha, true, 255, 44, 22);
         wrote |= this.renderBeam(buffer, matrix, cam, b, c, w, alpha, true, 255, 44, 22);
         wrote |= this.renderBeam(buffer, matrix, cam, c, d, w, alpha, true, 255, 44, 22);
         wrote |= this.renderBeam(buffer, matrix, cam, d, a, w, alpha, true, 255, 44, 22);
         wrote |= this.renderBeam(buffer, matrix, cam, center.add(right.scale(-inner)), center.add(right.scale(inner)), w * 0.65, alpha, true, 255, 90, 48);
         wrote |= this.renderBeam(buffer, matrix, cam, center.add(up.scale(-inner)), center.add(up.scale(inner)), w * 0.65, alpha, true, 255, 90, 48);
         if ((index & 1) == 0) {
            wrote |= this.renderBeam(buffer, matrix, cam, a, c, w * 0.45, alpha * 0.58F, true, 255, 24, 14);
         } else {
            wrote |= this.renderBeam(buffer, matrix, cam, b, d, w * 0.45, alpha * 0.58F, true, 255, 24, 14);
         }

         return wrote;
      }

      private boolean renderPlane(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 right, Vec3 up, double size, float alpha) {
         double s = size * 0.46;
         Vec3 a = center.add(right.scale(-s)).add(up.scale(s));
         Vec3 b = center.add(right.scale(s)).add(up.scale(s));
         Vec3 c = center.add(right.scale(s)).add(up.scale(-s));
         Vec3 d = center.add(right.scale(-s)).add(up.scale(-s));
         return this.quad(buffer, matrix, cam, a, b, c, d, 90, 0, 12, alpha);
      }

      private boolean renderCubeFaces(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         AsmodayPrisonRealmClient.PrisonFx.Basis basis,
         double size,
         float alpha,
         int rColor,
         int gColor,
         int bColor
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
         boolean wrote = this.quad(buffer, matrix, cam, p001, p101, p111, p011, rColor + 18, gColor, bColor + 4, alpha);
         wrote |= this.quad(buffer, matrix, cam, p100, p000, p010, p110, Math.max(4, rColor - 20), gColor, bColor, alpha);
         wrote |= this.quad(buffer, matrix, cam, p000, p001, p011, p010, rColor, gColor, bColor, alpha);
         wrote |= this.quad(buffer, matrix, cam, p101, p100, p110, p111, rColor + 8, gColor, bColor + 2, alpha);
         wrote |= this.quad(buffer, matrix, cam, p010, p011, p111, p110, rColor + 28, gColor, bColor + 8, alpha);
         return wrote | this.quad(buffer, matrix, cam, p000, p100, p101, p001, Math.max(4, rColor - 28), gColor, Math.max(2, bColor - 3), alpha);
      }

      private boolean renderWireCube(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         AsmodayPrisonRealmClient.PrisonFx.Basis basis,
         double size,
         double width,
         float alpha,
         int rColor,
         int gColor,
         int bColor
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
         boolean wrote = false;
         wrote |= this.edge(buffer, matrix, cam, p000, p001, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p010, p011, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p100, p101, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p110, p111, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p000, p010, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p001, p011, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p100, p110, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p101, p111, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p000, p100, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p001, p101, width, alpha, rColor, gColor, bColor);
         wrote |= this.edge(buffer, matrix, cam, p010, p110, width, alpha, rColor, gColor, bColor);
         return wrote | this.edge(buffer, matrix, cam, p011, p111, width, alpha, rColor, gColor, bColor);
      }

      private boolean edge(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, double width, float alpha, int rColor, int gColor, int bColor) {
         boolean wrote = this.renderBeam(buffer, matrix, cam, a, b, width * 4.2, alpha * 0.24F, false, 120, 0, 10);
         return wrote | this.renderBeam(buffer, matrix, cam, a, b, width, alpha, true, rColor, gColor, bColor);
      }

      private boolean renderBeam(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, double width, float alpha, boolean core, int rColor, int gColor, int bColor
      ) {
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
               buffer,
               matrix,
               cam,
               start.add(side),
               end.add(side.scale(0.34)),
               end.subtract(side.scale(0.34)),
               start.subtract(side),
               rColor,
               gColor,
               bColor,
               alpha
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
                  92,
                  54,
                  alpha * 0.76F
               );
         } else {
            boolean wrote = this.quad(
               buffer,
               matrix,
               cam,
               start.add(side),
               end.add(side.scale(0.34)),
               end.subtract(side.scale(0.34)),
               start.subtract(side),
               rColor,
               gColor,
               bColor,
               alpha * 0.8F
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
                  32,
                  0,
                  12,
                  alpha * 0.62F
               );
         }
      }

      private boolean quad(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bColor, float alpha) {
         int ia = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         if (ia <= 0) {
            return false;
         }

         this.vertex(buffer, matrix, cam, a, r, g, bColor, ia);
         this.vertex(buffer, matrix, cam, b, r, g, bColor, ia);
         this.vertex(buffer, matrix, cam, c, r, g, bColor, ia);
         this.vertex(buffer, matrix, cam, a, r, g, bColor, ia);
         this.vertex(buffer, matrix, cam, c, r, g, bColor, ia);
         this.vertex(buffer, matrix, cam, d, r, g, bColor, ia);
         return true;
      }

      private void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 p, int r, int g, int b, int a) {
         buffer.addVertex(matrix, (float)(p.x - cam.x), (float)(p.y - cam.y), (float)(p.z - cam.z)).setColor(r, g, b, a);
      }

      private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
      }
   }
}
