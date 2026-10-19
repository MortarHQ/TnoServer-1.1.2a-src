package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class AlbisVinaVfxClient {
   private static final List<AlbisVinaVfxClient.Impact> IMPACTS = new ArrayList<>();
   private static final List<AlbisVinaVfxClient.Beam> BEAMS = new ArrayList<>();
   private static boolean initialized = false;

   private AlbisVinaVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AlbisVinaVfxClient::render);
      }
   }

   public static void spawnFieldImpact(double x, double y, double z, float radius, long seed, boolean main) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 78400.0)) {
            Vec3 pos = new Vec3(x, y, z);
            float size = Mth.clamp(radius, main ? 4.8F : 3.6F, main ? 18.0F : 13.5F);
            long now = Util.getMillis();

            for (AlbisVinaVfxClient.Impact impact : IMPACTS) {
               if (impact.fieldMode && impact.origin.distanceTo(pos) <= impact.targetRadius + size + 2.0) {
                  impact.absorb(pos, size, seed, main, now);
                  return;
               }
            }

            IMPACTS.add(new AlbisVinaVfxClient.Impact(minecraft.level, pos, size, seed, main, true, now));

            while (IMPACTS.size() > 28) {
               IMPACTS.remove(0);
            }
         }
      }
   }

   public static void spawnSpikeImpact(double x, double y, double z, float radius, long seed, boolean main) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 48400.0)) {
            float size = Mth.clamp(radius, main ? 2.8F : 2.0F, main ? 5.8F : 4.2F);
            IMPACTS.add(new AlbisVinaVfxClient.Impact(minecraft.level, new Vec3(x, y, z), size, seed, main, false, Util.getMillis()));

            while (IMPACTS.size() > 28) {
               IMPACTS.remove(0);
            }
         }
      }
   }

   public static void spawnBeam(double x, double y, double z, double x2, double y2, double z2, float radius, long seed, boolean main) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         Vec3 start = new Vec3(x, y, z);
         Vec3 end = new Vec3(x2, y2, z2);
         Vec3 center = start.add(end).scale(0.5);
         if (!(minecraft.player.distanceToSqr(center.x, center.y, center.z) > 90000.0)) {
            BEAMS.add(new AlbisVinaVfxClient.Beam(start, end, Math.max(0.18F, radius), seed, main, Util.getMillis()));

            while (BEAMS.size() > 42) {
               BEAMS.remove(0);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            long now = Util.getMillis();
            Vec3 camera = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = poseStack.last().pose();
            Iterator<AlbisVinaVfxClient.Beam> beamIterator = BEAMS.iterator();

            while (beamIterator.hasNext()) {
               AlbisVinaVfxClient.Beam beam = beamIterator.next();
               if (beam.dead(now)) {
                  beamIterator.remove();
               } else {
                  beam.render(matrix, now);
               }
            }

            Iterator<AlbisVinaVfxClient.Impact> impactIterator = IMPACTS.iterator();

            while (impactIterator.hasNext()) {
               AlbisVinaVfxClient.Impact impact = impactIterator.next();
               if (impact.dead(now)) {
                  impactIterator.remove();
               } else {
                  impact.render(matrix, now);
               }
            }

            poseStack.popPose();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
         } else {
            IMPACTS.clear();
            BEAMS.clear();
         }
      }
   }

   private static void beamQuad(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 forward, Vec3 side, double length, double width, int red, int green, int blue, float alpha
   ) {
      Vec3 a = start.add(side.scale(width));
      Vec3 b = start.add(side.scale(-width));
      Vec3 c = start.add(forward.scale(length)).add(side.scale(-width));
      Vec3 d = start.add(forward.scale(length)).add(side.scale(width));
      int al = alpha(alpha);
      buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(red, green, blue, 0);
      buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(red, green, blue, al);
      buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(red, green, blue, al);
      buffer.addVertex(matrix, (float)d.x, (float)d.y, (float)d.z).setColor(red, green, blue, 0);
   }

   private static void horizontalDiamond(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double size, int red, int green, int blue, int alpha) {
      Vec3 a = center.add(0.0, 0.0, -size);
      Vec3 b = center.add(size, 0.0, 0.0);
      Vec3 c = center.add(0.0, 0.0, size);
      Vec3 d = center.add(-size, 0.0, 0.0);
      quad(buffer, matrix, a, b, c, d, red, green, blue, alpha);
   }

   private static void cameraBillboardDiamond(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double size, int red, int green, int blue, int alpha) {
      Minecraft minecraft = Minecraft.getInstance();
      Vec3 cam = minecraft.gameRenderer.getMainCamera().getPosition();
      Vec3 forward = cam.subtract(center);
      if (forward.lengthSqr() < 1.0E-4) {
         forward = new Vec3(0.0, 0.0, 1.0);
      }

      forward = forward.normalize();
      Vec3 up = Math.abs(forward.y) > 0.95 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
      Vec3 side = forward.cross(up).normalize();
      Vec3 vertical = side.cross(forward).normalize();
      Vec3 a = center.add(vertical.scale(size));
      Vec3 b = center.add(side.scale(size));
      Vec3 c = center.add(vertical.scale(-size));
      Vec3 d = center.add(side.scale(-size));
      quad(buffer, matrix, a, b, c, d, red, green, blue, alpha);
   }

   private static void ribbon(
      BufferBuilder buffer, Matrix4f matrix, Vec3 start, Vec3 end, double startWidth, double endWidth, int red, int green, int blue, int alpha
   ) {
      Vec3 side = tangentSide(start, end);
      Vec3 a = start.add(side.scale(startWidth));
      Vec3 b = start.add(side.scale(-startWidth));
      Vec3 c = end.add(side.scale(-endWidth));
      Vec3 d = end.add(side.scale(endWidth));
      quad(buffer, matrix, a, b, c, d, red, green, blue, alpha);
   }

   private static Vec3 tangentSide(Vec3 start, Vec3 end) {
      Vec3 dir = end.subtract(start);
      Vec3 side = new Vec3(-dir.z, 0.0, dir.x);
      if (side.lengthSqr() < 1.0E-6) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static void quad(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int red, int green, int blue, int alpha) {
      buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(red, green, blue, alpha);
      buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(red, green, blue, alpha);
      buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(red, green, blue, alpha);
      buffer.addVertex(matrix, (float)d.x, (float)d.y, (float)d.z).setColor(red, green, blue, alpha);
   }

   private static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int red, int green, int blue, int alpha) {
      buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(red, green, blue, alpha);
      buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(red, green, blue, alpha);
      buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(red, green, blue, alpha);
   }

   private static float smooth(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * (3.0F - 2.0F * value);
   }

   private static int alpha(float value) {
      return Mth.clamp(Math.round(value), 0, 255);
   }

   private static float hash(long seed, long salt) {
      long x = seed + salt * -7046029254386353131L;
      x ^= x >>> 30;
      x *= -4658895280553007687L;
      x ^= x >>> 27;
      x *= -7723592293110705685L;
      x ^= x >>> 31;
      return (float)((x & 16777215L) / 1.6777216E7);
   }

   private static final class Beam {
      private static final int STRANDS = 20;
      private static final int MOTES = 64;
      private static final int SHARDS = 20;
      private final Vec3 start;
      private final Vec3 end;
      private final float radius;
      private final long seed;
      private final boolean main;
      private final long created;
      private final float[] strands = new float[20];
      private final float[] motes = new float[64];
      private final float[] shards = new float[20];

      private Beam(Vec3 start, Vec3 end, float radius, long seed, boolean main, long created) {
         this.start = start;
         this.end = end;
         this.radius = radius;
         this.seed = seed;
         this.main = main;
         this.created = created;

         for (int i = 0; i < 20; i++) {
            this.strands[i] = AlbisVinaVfxClient.hash(seed, 100 + i);
         }

         for (int i = 0; i < 64; i++) {
            this.motes[i] = AlbisVinaVfxClient.hash(seed, 400 + i);
         }

         for (int i = 0; i < 20; i++) {
            this.shards[i] = AlbisVinaVfxClient.hash(seed, 700 + i);
         }
      }

      private boolean dead(long now) {
         return this.age(now) > this.life();
      }

      private double age(long now) {
         return (now - this.created) / 50.0;
      }

      private double life() {
         return this.main ? 34.0 : 26.0;
      }

      private void render(Matrix4f matrix, long now) {
         double age = this.age(now);
         float t = Mth.clamp((float)(age / this.life()), 0.0F, 1.0F);
         float birth = AlbisVinaVfxClient.smooth(Mth.clamp(t / 0.1F, 0.0F, 1.0F));
         float fade = 1.0F - AlbisVinaVfxClient.smooth(Mth.clamp((t - 0.66F) / 0.34F, 0.0F, 1.0F));
         float shatter = AlbisVinaVfxClient.smooth(Mth.clamp((t - 0.72F) / 0.28F, 0.0F, 1.0F));
         float live = birth * fade;
         if (!(live <= 0.002F)) {
            Vec3 axis = this.end.subtract(this.start);
            double length = axis.length();
            if (!(length < 0.001)) {
               Vec3 forward = axis.normalize();
               Vec3 up = Math.abs(forward.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
               Vec3 side = forward.cross(up).normalize();
               Vec3 normal = side.cross(forward).normalize();
               this.renderLance(matrix, forward, side, normal, length, live, t);
               this.renderThreads(matrix, forward, side, normal, length, live, t);
               this.renderMotes(matrix, forward, side, normal, length, live, t);
               this.renderEndBloom(matrix, live, t);
               this.renderEndShatter(matrix, live, shatter);
            }
         }
      }

      private void renderLance(Matrix4f matrix, Vec3 forward, Vec3 side, Vec3 normal, double length, float live, float t) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         double pulse = 0.86 + 0.14 * Mth.sin(t * 74.0F + AlbisVinaVfxClient.hash(this.seed, 8L) * 6.28318F);
         double w = (this.main ? 0.26 : 0.19) * this.radius * pulse;
         AlbisVinaVfxClient.beamQuad(buffer, matrix, this.start, forward, side, length, w * 1.65, 34, 185, 238, 104.0F * live);
         AlbisVinaVfxClient.beamQuad(buffer, matrix, this.start, forward, normal, length, w * 1.04, 150, 246, 255, 172.0F * live);
         AlbisVinaVfxClient.beamQuad(buffer, matrix, this.start, forward, side.add(normal).normalize(), length, w * 0.44, 255, 255, 255, 246.0F * live);
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderThreads(Matrix4f matrix, Vec3 forward, Vec3 side, Vec3 normal, double length, float live, float t) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

         for (int i = 0; i < 20; i++) {
            float h = this.strands[i];
            double spin = h * 6.28318 + t * (this.main ? 8.8 : 6.1);
            Vec3 radial = side.scale(Math.cos(spin)).add(normal.scale(Math.sin(spin))).normalize();
            double offset = (0.22 + h * 0.78) * this.radius;
            double len = length * (0.78 + AlbisVinaVfxClient.hash(this.seed, 900 + i) * 0.22);
            double width = (0.014 + h * 0.028) * this.radius;
            Vec3 s = this.start.add(radial.scale(offset));
            AlbisVinaVfxClient.beamQuad(buffer, matrix, s, forward, radial, len, width, 132, 244, 255, 118.0F * live);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderMotes(Matrix4f matrix, Vec3 forward, Vec3 side, Vec3 normal, double length, float live, float t) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

         for (int i = 0; i < 64; i++) {
            float h = this.motes[i];
            double along = length * ((h + t * 0.62) % 1.0);
            double angle = h * 22.0 + t * 5.4;
            Vec3 radial = side.scale(Math.cos(angle)).add(normal.scale(Math.sin(angle))).normalize();
            Vec3 center = this.start.add(forward.scale(along)).add(radial.scale((0.18 + AlbisVinaVfxClient.hash(this.seed, 1200 + i) * 1.05) * this.radius));
            double size = (0.018 + AlbisVinaVfxClient.hash(this.seed, 1400 + i) * 0.05) * this.radius;
            AlbisVinaVfxClient.cameraBillboardDiamond(buffer, matrix, center, size, 218, 252, 255, AlbisVinaVfxClient.alpha((84.0F + h * 102.0F) * live));
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderEndBloom(Matrix4f matrix, float live, float t) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         double size = (0.62 + AlbisVinaVfxClient.smooth(Mth.clamp(t / 0.18F, 0.0F, 1.0F)) * 1.4) * this.radius;
         AlbisVinaVfxClient.horizontalDiamond(buffer, matrix, this.end.add(0.0, 0.025, 0.0), size, 116, 232, 255, AlbisVinaVfxClient.alpha(148.0F * live));
         AlbisVinaVfxClient.horizontalDiamond(
            buffer, matrix, this.end.add(0.0, 0.045, 0.0), size * 0.42, 255, 255, 255, AlbisVinaVfxClient.alpha(234.0F * live)
         );
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderEndShatter(Matrix4f matrix, float live, float shatter) {
         if (!(shatter <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

            for (int i = 0; i < 20; i++) {
               float h = this.shards[i];
               double angle = h * (Math.PI * 2);
               double dist = shatter * (0.12 + AlbisVinaVfxClient.hash(this.seed, 1700 + i) * 0.9) * this.radius * 2.0;
               double height = 0.05 + shatter * (0.12 + AlbisVinaVfxClient.hash(this.seed, 1800 + i) * 0.45);
               double width = 0.02 + AlbisVinaVfxClient.hash(this.seed, 1900 + i) * 0.03;
               Vec3 base = this.end.add(Math.cos(angle) * dist, 0.03 + shatter * 0.18, Math.sin(angle) * dist);
               Vec3 side = new Vec3(-Math.sin(angle), 0.0, Math.cos(angle)).normalize();
               Vec3 top = base.add(0.0, height, 0.0);
               AlbisVinaVfxClient.tri(
                  buffer,
                  matrix,
                  base.add(side.scale(width)),
                  base.add(side.scale(-width)),
                  top,
                  245,
                  255,
                  255,
                  AlbisVinaVfxClient.alpha(120.0F * live * shatter)
               );
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static final class Impact {
      private static final int SEGMENTS = 256;
      private static final int RINGS = 22;
      private static final int FROST_FIBERS = 620;
      private static final int HAIRLINES = 880;
      private static final int SHEENS = 260;
      private static final int GLITTER = 1600;
      private static final int GRAIN_WEB = 1350;
      private static final int SUBSURFACE_BLOOMS = 520;
      private static final int SIDE_VEINS = 480;
      private static final int STAR_GLINTS = 260;
      private static final int EDGE_SPIKES = 210;
      private static final int MICRO_SPIKES = 330;
      private static final int SHATTER = 110;
      private static final int CRYSTALS = 9;
      private final ClientLevel level;
      private Vec3 origin;
      private float targetRadius;
      private float visualRadius;
      private long seed;
      private boolean main;
      private final boolean fieldMode;
      private final long created;
      private long boostedAt;
      private double expansionFrom;
      private double expansionTo;
      private double baseY;
      private int power;
      private final float[] edge = new float[256];
      private final float[] frost = new float[620];
      private final float[] hairlines = new float[880];
      private final float[] sheens = new float[260];
      private final float[] glitter = new float[1600];
      private final float[] grainWeb = new float[1350];
      private final float[] subsurfaceBlooms = new float[520];
      private final float[] sideVeins = new float[480];
      private final float[] starGlints = new float[260];
      private final float[] edgeSpikes = new float[210];
      private final float[] microSpikes = new float[330];
      private final float[] shatterBits = new float[110];
      private final float[] crystals = new float[9];

      private Impact(ClientLevel level, Vec3 origin, float radius, long seed, boolean main, boolean fieldMode, long created) {
         this.level = level;
         this.origin = origin;
         this.targetRadius = radius;
         this.visualRadius = fieldMode ? 0.25F : radius;
         this.seed = seed;
         this.main = main;
         this.fieldMode = fieldMode;
         this.created = created;
         this.boostedAt = created;
         this.expansionFrom = 0.0;
         this.expansionTo = radius;
         this.baseY = origin.y;
         this.power = 1;

         for (int i = 0; i < 256; i++) {
            this.edge[i] = AlbisVinaVfxClient.hash(seed, 1000 + i);
         }

         for (int i = 0; i < 620; i++) {
            this.frost[i] = AlbisVinaVfxClient.hash(seed, 2000 + i);
         }

         for (int i = 0; i < 880; i++) {
            this.hairlines[i] = AlbisVinaVfxClient.hash(seed, 3000 + i);
         }

         for (int i = 0; i < 260; i++) {
            this.sheens[i] = AlbisVinaVfxClient.hash(seed, 4000 + i);
         }

         for (int i = 0; i < 1600; i++) {
            this.glitter[i] = AlbisVinaVfxClient.hash(seed, 5000 + i);
         }

         for (int i = 0; i < 1350; i++) {
            this.grainWeb[i] = AlbisVinaVfxClient.hash(seed, 52000 + i);
         }

         for (int i = 0; i < 520; i++) {
            this.subsurfaceBlooms[i] = AlbisVinaVfxClient.hash(seed, 54000 + i);
         }

         for (int i = 0; i < 480; i++) {
            this.sideVeins[i] = AlbisVinaVfxClient.hash(seed, 56000 + i);
         }

         for (int i = 0; i < 260; i++) {
            this.starGlints[i] = AlbisVinaVfxClient.hash(seed, 58000 + i);
         }

         for (int i = 0; i < 210; i++) {
            this.edgeSpikes[i] = AlbisVinaVfxClient.hash(seed, 6000 + i);
         }

         for (int i = 0; i < 330; i++) {
            this.microSpikes[i] = AlbisVinaVfxClient.hash(seed, 7000 + i);
         }

         for (int i = 0; i < 110; i++) {
            this.shatterBits[i] = AlbisVinaVfxClient.hash(seed, 8000 + i);
         }

         for (int i = 0; i < 9; i++) {
            this.crystals[i] = AlbisVinaVfxClient.hash(seed, 9000 + i);
         }
      }

      private void absorb(Vec3 pos, float radius, long newSeed, boolean newMain, long now) {
         double oldWeight = Math.max(1.0, this.targetRadius);
         double newWeight = Math.max(1.0, radius * 0.72);
         this.origin = this.origin.scale(oldWeight).add(pos.scale(newWeight)).scale(1.0 / (oldWeight + newWeight));
         this.baseY = (this.baseY * oldWeight + pos.y * newWeight) / (oldWeight + newWeight);
         this.expansionFrom = this.visualRadius;
         this.targetRadius = Mth.clamp(this.targetRadius + radius * 0.56F, this.targetRadius, newMain ? 18.0F : 13.5F);
         this.expansionTo = this.targetRadius;
         this.seed = this.seed ^ newSeed + this.power * 7919L;
         this.main = this.main || newMain;
         this.power = Math.min(7, this.power + 1);
         this.boostedAt = now;
      }

      private boolean dead(long now) {
         return this.age(now) > this.life();
      }

      private double age(long now) {
         return (now - this.created) / 50.0;
      }

      private double life() {
         return this.fieldMode ? (this.main ? 126.0 : 100.0) : (this.main ? 64.0 : 48.0);
      }

      private void render(Matrix4f matrix, long now) {
         this.visualRadius = this.visualRadius + (this.targetRadius - this.visualRadius) * 0.115F;
         double age = this.age(now);
         float t = Mth.clamp((float)(age / this.life()), 0.0F, 1.0F);
         float birth = AlbisVinaVfxClient.smooth(Mth.clamp(t / 0.08F, 0.0F, 1.0F));
         float shatter = AlbisVinaVfxClient.smooth(Mth.clamp((t - 0.8F) / 0.2F, 0.0F, 1.0F));
         float fade = 1.0F - AlbisVinaVfxClient.smooth(Mth.clamp((t - 0.93F) / 0.07F, 0.0F, 1.0F));
         float live = birth * fade;
         if (!(live <= 0.002F)) {
            if (this.fieldMode) {
               this.renderCircularIceSkin(matrix, live, shatter);
               this.renderSubsurfaceIceBlooms(matrix, live, t, shatter);
               this.renderDenseCrystalGrain(matrix, live, t, shatter);
               this.renderCircularDepthRim(matrix, live, shatter);
               this.renderSideFrostVeins(matrix, live, t, shatter);
               this.renderExpansionRing(matrix, live, shatter, now);
               this.renderDeepFrostFibers(matrix, live, shatter);
               this.renderHairlineCracks(matrix, live, shatter);
               this.renderSpecularSheen(matrix, live, t, shatter);
               this.renderStarGlints(matrix, live, t, shatter);
               this.renderDistributedGlitter(matrix, live, t, shatter);
               this.renderCrystalCluster(matrix, live, t, shatter, 1.0 + this.power * 0.05);
               this.renderEdgeIceSpikes(matrix, live, shatter);
               this.renderMicroSpikeField(matrix, live, t, shatter);
            } else {
               this.renderSpikeFlash(matrix, live, t, shatter);
               this.renderSubsurfaceIceBlooms(matrix, live, t, shatter);
               this.renderDenseCrystalGrain(matrix, live, t, shatter);
               this.renderDeepFrostFibers(matrix, live, shatter);
               this.renderHairlineCracks(matrix, live, shatter);
               this.renderSpecularSheen(matrix, live, t, shatter);
               this.renderStarGlints(matrix, live, t, shatter);
               this.renderDistributedGlitter(matrix, live, t, shatter);
               this.renderCrystalCluster(matrix, live, t, shatter, 0.75);
               this.renderSpikeOnlyNeedles(matrix, live, t, shatter);
               this.renderMicroSpikeField(matrix, live, t, shatter);
            }

            this.renderShatterBurst(matrix, live, shatter);
         }
      }

      private void renderCircularIceSkin(Matrix4f matrix, float live, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         float sheetLive = live * (1.0F - shatter * 0.3F);

         for (int ring = 0; ring < 22; ring++) {
            double f0 = ring / 22.0;
            double f1 = (ring + 1) / 22.0;

            for (int i = 0; i < 256; i++) {
               double a0 = i * (Math.PI * 2) / 256.0;
               double a1 = (i + 1) * (Math.PI * 2) / 256.0;
               Vec3 p00 = this.icePoint(a0, f0, 0.018);
               Vec3 p01 = this.icePoint(a0, f1, 0.019);
               Vec3 p11 = this.icePoint(a1, f1, 0.019);
               Vec3 p10 = this.icePoint(a1, f0, 0.018);
               double n0 = this.softNoise(p00.x, p00.z, 0.16);
               double n1 = this.softNoise(p01.x, p01.z, 0.16);
               double radial = 1.0 - f0 * 0.26;
               int r0 = 54 + (int)(n0 * 18.0) + (int)(f0 * 18.0);
               int g0 = 174 + (int)(n0 * 30.0) + (int)(f0 * 26.0);
               int b0 = 226 + (int)(n1 * 20.0);
               int a = AlbisVinaVfxClient.alpha((float)(145.0 * radial * sheetLive));
               AlbisVinaVfxClient.tri(buffer, matrix, p00, p01, p11, r0, g0, b0, a);
               AlbisVinaVfxClient.tri(buffer, matrix, p00, p11, p10, r0, g0, b0, a);
               if ((ring & 1) == 0 && (i & 3) == 0) {
                  Vec3 mid0 = p00.lerp(p11, 0.45);
                  Vec3 mid1 = p00.lerp(p11, 0.55).add(0.0, 0.004, 0.0);
                  double w = 0.006 + 0.008 * AlbisVinaVfxClient.hash(this.seed, ring * 991L + i * 37L);
                  Vec3 side = AlbisVinaVfxClient.tangentSide(mid0, mid1);
                  AlbisVinaVfxClient.tri(
                     buffer, matrix, mid0.add(side.scale(w)), mid0.add(side.scale(-w)), mid1, 226, 250, 255, AlbisVinaVfxClient.alpha(20.0F * sheetLive)
                  );
               }
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderSubsurfaceIceBlooms(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float bloomLive = live * (1.0F - shatter * 0.66F);
         int count = Math.min(520, 120 + (int)(this.visualRadius * this.visualRadius * 7.0));

         for (int i = 0; i < count; i++) {
            float h = this.subsurfaceBlooms[i];
            double angle = h * (Math.PI * 2);
            double dist = this.visualRadius * Math.sqrt(AlbisVinaVfxClient.hash(this.seed, 60000 + i)) * 0.975;
            Vec3 center = this.worldPoint(angle, dist, 0.026 + AlbisVinaVfxClient.hash(this.seed, 60100 + i) * 0.018);
            if (this.inside(center.x, center.z)) {
               double stretch = 0.05 + AlbisVinaVfxClient.hash(this.seed, 60200 + i) * 0.18;
               double size = (0.045 + AlbisVinaVfxClient.hash(this.seed, 60300 + i) * 0.22) * (0.7 + this.visualRadius * 0.028);
               double spin = angle + AlbisVinaVfxClient.hash(this.seed, 60400 + i) * (Math.PI * 2) + t * 0.08;
               Vec3 dir = new Vec3(Math.cos(spin), 0.0, Math.sin(spin)).normalize();
               Vec3 side = new Vec3(-dir.z, 0.0, dir.x).normalize();
               Vec3 a = center.add(dir.scale(size * (1.0 + stretch)));
               Vec3 b = center.add(side.scale(size * 0.48));
               Vec3 c = center.add(dir.scale(-size * (0.72 + stretch)));
               Vec3 d = center.add(side.scale(-size * 0.48));
               int r = 34 + (int)(AlbisVinaVfxClient.hash(this.seed, 60500 + i) * 28.0);
               int g = 138 + (int)(AlbisVinaVfxClient.hash(this.seed, 60600 + i) * 62.0);
               int bl = 212 + (int)(AlbisVinaVfxClient.hash(this.seed, 60700 + i) * 40.0);
               AlbisVinaVfxClient.quad(buffer, matrix, a, b, c, d, r, g, bl, AlbisVinaVfxClient.alpha((18.0F + h * 34.0F) * bloomLive));
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderDenseCrystalGrain(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float grainLive = live * (1.0F - shatter * 0.7F);
         int count = Math.min(1350, 180 + (int)(this.visualRadius * this.visualRadius * 16.0));

         for (int i = 0; i < count; i++) {
            float h = this.grainWeb[i];
            double baseAngle = h * (Math.PI * 2);
            double dist = this.visualRadius * Math.sqrt(AlbisVinaVfxClient.hash(this.seed, 61000 + i)) * 0.99;
            double grainAngle = baseAngle + (AlbisVinaVfxClient.hash(this.seed, 61100 + i) - 0.5) * 1.9;
            double len = this.visualRadius * (0.018 + AlbisVinaVfxClient.hash(this.seed, 61200 + i) * 0.105);
            double split = (AlbisVinaVfxClient.hash(this.seed, 61300 + i) - 0.5) * 0.36;
            double width = 0.0018 + AlbisVinaVfxClient.hash(this.seed, 61400 + i) * 0.0058;
            Vec3 p0 = this.worldPoint(baseAngle, dist, 0.092 + AlbisVinaVfxClient.hash(this.seed, 61500 + i) * 0.018);
            Vec3 p1 = this.worldPoint(
               grainAngle + split, Math.min(this.visualRadius * 0.996, dist + len), 0.096 + AlbisVinaVfxClient.hash(this.seed, 61600 + i) * 0.018
            );
            if (this.inside(p0.x, p0.z) && this.inside(p1.x, p1.z)) {
               float pulse = 0.64F + 0.36F * Math.abs(Mth.sin(t * 40.0F + h * 19.0F));
               boolean bright = (i & 11) == 0;
               AlbisVinaVfxClient.ribbon(
                  buffer,
                  matrix,
                  p0,
                  p1,
                  width * (bright ? 2.2 : 1.0),
                  width * 0.32,
                  bright ? 255 : 176,
                  bright ? 255 : 232,
                  255,
                  AlbisVinaVfxClient.alpha((bright ? 52.0F : 20.0F) * grainLive * pulse)
               );
               if ((i & 9) == 0) {
                  Vec3 joint = p0.lerp(p1, 0.45 + AlbisVinaVfxClient.hash(this.seed, 61700 + i) * 0.28);
                  Vec3 branch = this.worldPoint(
                     grainAngle - split * 1.65,
                     Math.min(this.visualRadius * 0.996, dist + len * (0.45 + AlbisVinaVfxClient.hash(this.seed, 61800 + i) * 0.55)),
                     0.101
                  );
                  if (this.inside(branch.x, branch.z)) {
                     AlbisVinaVfxClient.ribbon(
                        buffer, matrix, joint, branch, width * 0.72, width * 0.18, 220, 250, 255, AlbisVinaVfxClient.alpha(22.0F * grainLive * pulse)
                     );
                  }
               }
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderCircularDepthRim(Matrix4f matrix, float live, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float rimLive = live * (1.0F - shatter * 0.5F);

         for (int i = 0; i < 256; i++) {
            double a0 = i * (Math.PI * 2) / 256.0;
            double a1 = (i + 1) * (Math.PI * 2) / 256.0;
            Vec3 p0 = this.icePoint(a0, 1.0, 0.016);
            Vec3 p1 = this.icePoint(a1, 1.0, 0.016);
            double drop0 = 0.22 + AlbisVinaVfxClient.hash(this.seed, 11000 + i) * 0.15;
            double drop1 = 0.22 + AlbisVinaVfxClient.hash(this.seed, 11100 + i) * 0.15;
            Vec3 p2 = p1.add(0.0, -drop1, 0.0);
            Vec3 p3 = p0.add(0.0, -drop0, 0.0);
            AlbisVinaVfxClient.quad(buffer, matrix, p0, p1, p2, p3, 38, 144, 210, AlbisVinaVfxClient.alpha(188.0F * rimLive));
            if ((i & 3) == 0) {
               Vec3 q0 = p0.lerp(p1, 0.18).add(0.0, 0.004, 0.0);
               Vec3 q1 = p0.lerp(p1, 0.82).add(0.0, 0.004, 0.0);
               Vec3 q2 = q1.add(0.0, -0.045, 0.0);
               Vec3 q3 = q0.add(0.0, -0.045, 0.0);
               AlbisVinaVfxClient.quad(buffer, matrix, q0, q1, q2, q3, 188, 242, 255, AlbisVinaVfxClient.alpha(68.0F * rimLive));
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderSideFrostVeins(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float sideLive = live * (1.0F - shatter * 0.68F);
         int count = Math.min(480, 90 + (int)(this.visualRadius * 28.0));

         for (int i = 0; i < count; i++) {
            float h = this.sideVeins[i];
            double angle = h * (Math.PI * 2);
            int edgeIndex = Mth.floor(h * 256.0F);
            double radius = this.visualRadius * this.organicEdge(edgeIndex) * (0.992 + AlbisVinaVfxClient.hash(this.seed, 62000 + i) * 0.025);
            double topYAdd = 0.036 + AlbisVinaVfxClient.hash(this.seed, 62100 + i) * 0.024;
            double drop = 0.07 + AlbisVinaVfxClient.hash(this.seed, 62200 + i) * 0.31;
            double slant = (AlbisVinaVfxClient.hash(this.seed, 62300 + i) - 0.5) * 0.095;
            double width = 0.006 + AlbisVinaVfxClient.hash(this.seed, 62400 + i) * 0.024;
            Vec3 p0 = this.worldPoint(angle, radius, topYAdd);
            Vec3 p1 = this.worldPoint(angle + slant, radius * (0.998 + AlbisVinaVfxClient.hash(this.seed, 62500 + i) * 0.025), topYAdd - drop);
            Vec3 side = new Vec3(-Math.sin(angle), 0.0, Math.cos(angle)).normalize();
            float pulse = 0.6F + 0.4F * Math.abs(Mth.sin(t * 35.0F + h * 17.0F));
            Vec3 a = p0.add(side.scale(width));
            Vec3 b = p0.add(side.scale(-width));
            Vec3 c = p1.add(side.scale(-width * 0.35));
            Vec3 d = p1.add(side.scale(width * 0.35));
            AlbisVinaVfxClient.quad(buffer, matrix, a, b, c, d, 218, 252, 255, AlbisVinaVfxClient.alpha((50.0F + h * 68.0F) * sideLive * pulse));
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderExpansionRing(Matrix4f matrix, float live, float shatter, long now) {
         float progress = Mth.clamp((float)(now - this.boostedAt) / 760.0F, 0.0F, 1.0F);
         if (!(progress >= 1.0F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            float ringLive = live * (1.0F - shatter) * (1.0F - AlbisVinaVfxClient.smooth(progress));
            double radius = this.expansionFrom + (this.expansionTo - this.expansionFrom) * AlbisVinaVfxClient.smooth(progress);
            double width = 0.035 + this.targetRadius * 0.01;

            for (int i = 0; i < 256; i++) {
               double a0 = i * (Math.PI * 2) / 256.0;
               double a1 = (i + 1) * (Math.PI * 2) / 256.0;
               double n0 = this.organicEdge(i);
               double n1 = this.organicEdge(i + 1);
               Vec3 p0 = this.worldPoint(a0, Math.max(0.0, radius - width) * n0, 0.06);
               Vec3 p1 = this.worldPoint(a0, (radius + width) * n0, 0.06);
               Vec3 p2 = this.worldPoint(a1, (radius + width) * n1, 0.06);
               Vec3 p3 = this.worldPoint(a1, Math.max(0.0, radius - width) * n1, 0.06);
               AlbisVinaVfxClient.quad(buffer, matrix, p0, p1, p2, p3, 132, 242, 255, AlbisVinaVfxClient.alpha(220.0F * ringLive));
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderDeepFrostFibers(Matrix4f matrix, float live, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float fiberLive = live * (1.0F - shatter * 0.64F);

         for (int i = 0; i < 620; i++) {
            float h = this.frost[i];
            double angle = h * (Math.PI * 2);
            double start = this.visualRadius * (0.02 + AlbisVinaVfxClient.hash(this.seed, 12000 + i) * 0.48);
            double len = this.visualRadius * (0.12 + AlbisVinaVfxClient.hash(this.seed, 12100 + i) * 0.38);
            double curve = (AlbisVinaVfxClient.hash(this.seed, 12200 + i) - 0.5) * 0.78;
            double width = 0.006 + AlbisVinaVfxClient.hash(this.seed, 12300 + i) * 0.016;
            Vec3 p0 = this.worldPoint(angle, start, 0.045);
            Vec3 p1 = this.worldPoint(angle + curve * 0.25, Math.min(this.visualRadius * 0.98, start + len * 0.45), 0.048);
            Vec3 p2 = this.worldPoint(angle + curve, Math.min(this.visualRadius * 0.98, start + len), 0.05);
            if (this.inside(p0.x, p0.z) && this.inside(p2.x, p2.z)) {
               AlbisVinaVfxClient.ribbon(buffer, matrix, p0, p1, width, width * 0.75, 210, 248, 255, AlbisVinaVfxClient.alpha((24.0F + h * 48.0F) * fiberLive));
               AlbisVinaVfxClient.ribbon(
                  buffer, matrix, p1, p2, width * 0.75, width * 0.16, 232, 254, 255, AlbisVinaVfxClient.alpha((32.0F + h * 56.0F) * fiberLive)
               );
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderHairlineCracks(Matrix4f matrix, float live, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float crackLive = live * (1.0F - shatter * 0.68F);

         for (int i = 0; i < 880; i++) {
            float h = this.hairlines[i];
            double angle = h * (Math.PI * 2);
            double start = this.visualRadius * (0.04 + AlbisVinaVfxClient.hash(this.seed, 13000 + i) * 0.72);
            double length = this.visualRadius * (0.045 + AlbisVinaVfxClient.hash(this.seed, 13100 + i) * 0.18);
            double bend = (AlbisVinaVfxClient.hash(this.seed, 13200 + i) - 0.5) * 0.42;
            double width = 0.0025 + AlbisVinaVfxClient.hash(this.seed, 13300 + i) * 0.0065;
            Vec3 p0 = this.worldPoint(angle, start, 0.07);
            Vec3 p1 = this.worldPoint(angle + bend, Math.min(this.visualRadius * 0.99, start + length), 0.073);
            if (this.inside(p0.x, p0.z) && this.inside(p1.x, p1.z)) {
               boolean bright = (i & 5) == 0;
               AlbisVinaVfxClient.ribbon(
                  buffer,
                  matrix,
                  p0,
                  p1,
                  width,
                  width * 0.35,
                  bright ? 255 : 180,
                  bright ? 255 : 232,
                  255,
                  AlbisVinaVfxClient.alpha((bright ? 38.0F : 20.0F) * crackLive)
               );
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderSpecularSheen(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float sheenLive = live * (1.0F - shatter * 0.72F);
         int count = Math.min(260, 24 + (int)(this.visualRadius * this.visualRadius * 0.7));

         for (int i = 0; i < count; i++) {
            float h = this.sheens[i];
            double angle = h * (Math.PI * 2);
            double start = this.visualRadius * (0.04 + AlbisVinaVfxClient.hash(this.seed, 14000 + i) * 0.72);
            double len = this.visualRadius * (0.12 + AlbisVinaVfxClient.hash(this.seed, 14100 + i) * 0.42);
            double slant = (AlbisVinaVfxClient.hash(this.seed, 14200 + i) - 0.5) * 0.92;
            Vec3 p0 = this.worldPoint(angle, start, 0.082);
            Vec3 p1 = this.worldPoint(angle + slant, Math.min(this.visualRadius * 0.98, start + len), 0.086);
            if (this.inside(p0.x, p0.z) && this.inside(p1.x, p1.z)) {
               float blink = 0.35F + 0.65F * Math.abs(Mth.sin(t * 88.0F + h * 22.0F));
               double width = 0.01 + AlbisVinaVfxClient.hash(this.seed, 14300 + i) * 0.026;
               AlbisVinaVfxClient.ribbon(
                  buffer,
                  matrix,
                  p0,
                  p1,
                  width,
                  width * 0.08,
                  244,
                  255,
                  255,
                  AlbisVinaVfxClient.alpha((52.0F + AlbisVinaVfxClient.hash(this.seed, 14400 + i) * 74.0F) * sheenLive * blink)
               );
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderStarGlints(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float glintLive = live * (1.0F - shatter * 0.8F);
         int count = Math.min(260, 38 + (int)(this.visualRadius * this.visualRadius * 2.6));

         for (int i = 0; i < count; i++) {
            float h = this.starGlints[i];
            double angle = h * (Math.PI * 2);
            double dist = this.visualRadius * Math.sqrt(AlbisVinaVfxClient.hash(this.seed, 63000 + i)) * 0.97;
            Vec3 center = this.worldPoint(angle, dist, 0.142 + AlbisVinaVfxClient.hash(this.seed, 63100 + i) * 0.035);
            if (this.inside(center.x, center.z)) {
               float blink = (float)Math.pow(Math.abs(Mth.sin(t * 130.0F + h * 43.0F)), 3.0);
               if (!(blink <= 0.08F)) {
                  double spin = AlbisVinaVfxClient.hash(this.seed, 63200 + i) * (Math.PI * 2);
                  double longSize = (0.055 + AlbisVinaVfxClient.hash(this.seed, 63300 + i) * 0.18) * (1.0 + this.visualRadius * 0.018);
                  double shortSize = longSize * (0.12 + AlbisVinaVfxClient.hash(this.seed, 63400 + i) * 0.1);
                  Vec3 dir = new Vec3(Math.cos(spin), 0.0, Math.sin(spin)).normalize();
                  Vec3 side = new Vec3(-dir.z, 0.0, dir.x).normalize();
                  Vec3 a = center.add(dir.scale(longSize));
                  Vec3 b = center.add(side.scale(shortSize));
                  Vec3 c = center.add(dir.scale(-longSize));
                  Vec3 d = center.add(side.scale(-shortSize));
                  AlbisVinaVfxClient.quad(buffer, matrix, a, b, c, d, 255, 255, 255, AlbisVinaVfxClient.alpha((90.0F + h * 150.0F) * glintLive * blink));
                  Vec3 e = center.add(side.scale(longSize * 0.55));
                  Vec3 f = center.add(dir.scale(shortSize * 0.72));
                  Vec3 g = center.add(side.scale(-longSize * 0.55));
                  Vec3 k = center.add(dir.scale(-shortSize * 0.72));
                  AlbisVinaVfxClient.quad(buffer, matrix, e, f, g, k, 228, 252, 255, AlbisVinaVfxClient.alpha((55.0F + h * 90.0F) * glintLive * blink));
               }
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderDistributedGlitter(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         float glitterLive = live * (1.0F - shatter * 0.74F);
         int count = Math.min(1600, 90 + (int)(this.visualRadius * this.visualRadius * 9.5));

         for (int i = 0; i < count; i++) {
            float h = this.glitter[i];
            double angle = h * (Math.PI * 2);
            double dist = this.visualRadius * Math.sqrt(AlbisVinaVfxClient.hash(this.seed, 15000 + i)) * 0.985;
            Vec3 p = this.worldPoint(angle, dist, 0.095 + AlbisVinaVfxClient.hash(this.seed, 15100 + i) * 0.045);
            if (this.inside(p.x, p.z)) {
               float blink = 0.22F + 0.78F * Math.abs(Mth.sin(t * 96.0F + h * 31.0F + AlbisVinaVfxClient.hash(this.seed, 15200 + i) * 6.0F));
               double size = 0.012 + AlbisVinaVfxClient.hash(this.seed, 15300 + i) * 0.045;
               if ((i & 15) == 0) {
                  size *= 2.4;
               }

               if ((i & 7) == 0) {
                  size *= 1.45;
               }

               AlbisVinaVfxClient.cameraBillboardDiamond(
                  buffer,
                  matrix,
                  p,
                  size,
                  248,
                  255,
                  255,
                  AlbisVinaVfxClient.alpha((48.0F + AlbisVinaVfxClient.hash(this.seed, 15400 + i) * 170.0F) * glitterLive * blink)
               );
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderCrystalCluster(Matrix4f matrix, float live, float t, float shatter, double scale) {
         float grow = AlbisVinaVfxClient.smooth(Mth.clamp(t / 0.22F, 0.0F, 1.0F));
         float crystalLive = live * (1.0F - shatter * 0.42F);
         double centerY = this.sampleSurfaceY(this.origin.x, this.origin.z, this.baseY) + 0.045;
         this.renderCrystalPrism(
            matrix,
            new Vec3(this.origin.x, centerY, this.origin.z),
            0.0,
            (this.main ? 3.2 : 2.0) * scale * grow,
            (this.main ? 0.82 : 0.56) * scale * grow,
            8,
            crystalLive,
            this.seed + 10L
         );

         for (int i = 0; i < 9; i++) {
            float h = this.crystals[i];
            double angle = h * (Math.PI * 2);
            double dist = (0.26 + AlbisVinaVfxClient.hash(this.seed, 16000 + i) * 0.88) * scale * grow;
            double height = (0.82 + AlbisVinaVfxClient.hash(this.seed, 16100 + i) * 1.55) * scale * grow * (this.main ? 1.15 : 0.9);
            double radius = (0.18 + AlbisVinaVfxClient.hash(this.seed, 16200 + i) * 0.24) * scale * grow;
            Vec3 base = new Vec3(
               this.origin.x + Math.cos(angle) * dist,
               this.sampleSurfaceY(this.origin.x + Math.cos(angle) * dist, this.origin.z + Math.sin(angle) * dist, this.baseY) + 0.045,
               this.origin.z + Math.sin(angle) * dist
            );
            this.renderCrystalPrism(
               matrix,
               base,
               angle + AlbisVinaVfxClient.hash(this.seed, 16300 + i) * 1.4,
               height,
               radius,
               6 + (i & 1),
               crystalLive * (0.76F + h * 0.24F),
               this.seed + 300L + i
            );
         }
      }

      private void renderCrystalPrism(Matrix4f matrix, Vec3 base, double rotation, double height, double radius, int faces, float live, long localSeed) {
         if (!(height <= 0.01) && !(radius <= 0.01)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            Vec3 top = base.add(0.0, height, 0.0);
            Vec3 mid = base.add(0.0, height * 0.38, 0.0);

            for (int i = 0; i < faces; i++) {
               double a0 = rotation + i * (Math.PI * 2) / faces;
               double a1 = rotation + (i + 1) * (Math.PI * 2) / faces;
               double r0 = radius * (0.82 + AlbisVinaVfxClient.hash(localSeed, 100 + i) * 0.26);
               double r1 = radius * (0.82 + AlbisVinaVfxClient.hash(localSeed, 200 + i) * 0.26);
               Vec3 p0 = base.add(Math.cos(a0) * r0, 0.0, Math.sin(a0) * r0);
               Vec3 p1 = base.add(Math.cos(a1) * r1, 0.0, Math.sin(a1) * r1);
               Vec3 m0 = mid.add(Math.cos(a0) * r0 * 0.48, 0.0, Math.sin(a0) * r0 * 0.48);
               Vec3 m1 = mid.add(Math.cos(a1) * r1 * 0.48, 0.0, Math.sin(a1) * r1 * 0.48);
               boolean bright = (i & 1) == 0;
               AlbisVinaVfxClient.tri(
                  buffer,
                  matrix,
                  p0,
                  p1,
                  m1,
                  bright ? 255 : 92,
                  bright ? 255 : 210,
                  bright ? 255 : 248,
                  AlbisVinaVfxClient.alpha((bright ? 185.0F : 118.0F) * live)
               );
               AlbisVinaVfxClient.tri(
                  buffer, matrix, p0, m1, m0, bright ? 230 : 58, bright ? 250 : 186, 255, AlbisVinaVfxClient.alpha((bright ? 145.0F : 96.0F) * live)
               );
               AlbisVinaVfxClient.tri(
                  buffer, matrix, m0, m1, top, bright ? 255 : 110, bright ? 255 : 224, 255, AlbisVinaVfxClient.alpha((bright ? 220.0F : 130.0F) * live)
               );
               Vec3 edge0 = p0.lerp(top, 0.52);
               Vec3 edge1 = p0.lerp(top, 0.88);
               Vec3 side = AlbisVinaVfxClient.tangentSide(edge0, edge1);
               AlbisVinaVfxClient.tri(
                  buffer, matrix, edge0.add(side.scale(0.016)), edge0.add(side.scale(-0.016)), edge1, 255, 255, 255, AlbisVinaVfxClient.alpha(120.0F * live)
               );
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderEdgeIceSpikes(Matrix4f matrix, float live, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         float spikeLive = live * (1.0F - shatter * 0.72F);

         for (int i = 0; i < 210; i++) {
            float h = this.edgeSpikes[i];
            double angle = h * (Math.PI * 2);
            int edgeIndex = Mth.floor(h * 256.0F);
            double edgeRadius = this.visualRadius * this.organicEdge(edgeIndex) * (0.985 + AlbisVinaVfxClient.hash(this.seed, 17000 + i) * 0.045);
            Vec3 base = this.worldPoint(angle, edgeRadius, 0.04);
            boolean accent = i % 7 == 0;
            double height = accent ? 0.54 + AlbisVinaVfxClient.hash(this.seed, 17100 + i) * 0.68 : 0.22 + AlbisVinaVfxClient.hash(this.seed, 17100 + i) * 0.32;
            double width = accent
               ? 0.052 + AlbisVinaVfxClient.hash(this.seed, 17200 + i) * 0.058
               : 0.024 + AlbisVinaVfxClient.hash(this.seed, 17200 + i) * 0.032;
            Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle)).normalize();
            Vec3 side = new Vec3(-radial.z, 0.0, radial.x).normalize();
            Vec3 top = base.add(radial.scale(0.03)).add(0.0, height * spikeLive, 0.0);
            AlbisVinaVfxClient.tri(
               buffer,
               matrix,
               base.add(side.scale(width)),
               base.add(side.scale(-width)),
               top,
               255,
               255,
               255,
               AlbisVinaVfxClient.alpha((accent ? 194.0F : 142.0F) * spikeLive)
            );
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderMicroSpikeField(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         float grow = AlbisVinaVfxClient.smooth(Mth.clamp(t / 0.16F, 0.0F, 1.0F));
         float spikeLive = live * (1.0F - shatter * 0.72F);

         for (int i = 0; i < 330; i++) {
            float h = this.microSpikes[i];
            double angle = h * (Math.PI * 2);
            double dist = this.visualRadius * Math.sqrt(AlbisVinaVfxClient.hash(this.seed, 18000 + i)) * 0.98;
            Vec3 base = this.worldPoint(angle, dist, 0.038);
            if (this.inside(base.x, base.z)) {
               double height = (0.12 + AlbisVinaVfxClient.hash(this.seed, 18100 + i) * 0.34) * grow * (1.0 - shatter * 0.5);
               double width = 0.012 + AlbisVinaVfxClient.hash(this.seed, 18200 + i) * 0.024;
               Vec3 radial = new Vec3(Math.cos(angle + (AlbisVinaVfxClient.hash(this.seed, 18300 + i) - 0.5) * 0.14), 0.0, Math.sin(angle)).normalize();
               Vec3 side = new Vec3(-radial.z, 0.0, radial.x).normalize();
               Vec3 top = base.add(radial.scale(0.028 + AlbisVinaVfxClient.hash(this.seed, 18400 + i) * 0.072)).add(0.0, height, 0.0);
               AlbisVinaVfxClient.tri(
                  buffer,
                  matrix,
                  base.add(side.scale(width)),
                  base.add(side.scale(-width)),
                  top,
                  i % 5 == 0 ? 255 : 214,
                  i % 5 == 0 ? 255 : 248,
                  255,
                  AlbisVinaVfxClient.alpha(126.0F * spikeLive)
               );
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderSpikeFlash(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         double size = this.visualRadius * (0.34 + AlbisVinaVfxClient.smooth(Mth.clamp(t / 0.18F, 0.0F, 1.0F)) * 0.34) * (1.0 - shatter * 0.25);
         int a = AlbisVinaVfxClient.alpha(140.0F * live);
         Vec3 center = new Vec3(this.origin.x, this.sampleSurfaceY(this.origin.x, this.origin.z, this.baseY) + 0.022, this.origin.z);

         for (int i = 0; i < 56; i++) {
            double a0 = i * (Math.PI * 2) / 56.0;
            double a1 = (i + 1) * (Math.PI * 2) / 56.0;
            Vec3 p0 = this.worldPoint(a0, size, 0.024);
            Vec3 p1 = this.worldPoint(a1, size, 0.024);
            AlbisVinaVfxClient.tri(buffer, matrix, center, p0, p1, 104, 222, 255, a);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderSpikeOnlyNeedles(Matrix4f matrix, float live, float t, float shatter) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         float grow = AlbisVinaVfxClient.smooth(Mth.clamp(t / 0.22F, 0.0F, 1.0F));

         for (int i = 0; i < 105; i++) {
            float h = this.edgeSpikes[i];
            double angle = h * (Math.PI * 2);
            double r = this.visualRadius * (0.25 + AlbisVinaVfxClient.hash(this.seed, 19000 + i) * 0.42);
            Vec3 base = this.worldPoint(angle, r, 0.035);
            double height = (0.32 + AlbisVinaVfxClient.hash(this.seed, 19100 + i) * 0.48) * grow * (1.0 - shatter * 0.5);
            double width = 0.05 + AlbisVinaVfxClient.hash(this.seed, 19200 + i) * 0.052;
            Vec3 radial = new Vec3(Math.cos(angle), 0.0, Math.sin(angle)).normalize();
            Vec3 side = new Vec3(-radial.z, 0.0, radial.x).normalize();
            Vec3 top = base.add(radial.scale(0.1)).add(0.0, height, 0.0);
            AlbisVinaVfxClient.tri(
               buffer, matrix, base.add(side.scale(width)), base.add(side.scale(-width)), top, 245, 255, 255, AlbisVinaVfxClient.alpha(122.0F * live)
            );
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }

      private void renderShatterBurst(Matrix4f matrix, float live, float shatter) {
         if (!(shatter <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

            for (int i = 0; i < 110; i++) {
               float h = this.shatterBits[i];
               double angle = h * (Math.PI * 2);
               double dist = this.visualRadius * (0.08 + AlbisVinaVfxClient.hash(this.seed, 20000 + i) * 0.92)
                  + shatter * AlbisVinaVfxClient.hash(this.seed, 20100 + i) * 1.6;
               Vec3 base = this.worldPoint(angle, dist, 0.07 + shatter * (0.04 + AlbisVinaVfxClient.hash(this.seed, 20200 + i) * 0.34));
               double height = 0.08 + AlbisVinaVfxClient.hash(this.seed, 20300 + i) * 0.26 + shatter * 0.14;
               double width = 0.018 + AlbisVinaVfxClient.hash(this.seed, 20400 + i) * 0.034;
               Vec3 side = new Vec3(-Math.sin(angle), 0.0, Math.cos(angle)).normalize();
               Vec3 top = base.add(0.0, height, 0.0);
               AlbisVinaVfxClient.tri(
                  buffer,
                  matrix,
                  base.add(side.scale(width)),
                  base.add(side.scale(-width)),
                  top,
                  255,
                  255,
                  255,
                  AlbisVinaVfxClient.alpha(116.0F * live * shatter)
               );
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private Vec3 icePoint(double angle, double fraction, double yAdd) {
         int idx = Mth.floor(angle / (Math.PI * 2) * 256.0);
         double radius = this.visualRadius * fraction * this.organicEdge(idx) * this.innerOrganic(idx, fraction);
         return this.worldPoint(
            angle, radius, yAdd + this.softNoise(this.origin.x + Math.cos(angle) * radius, this.origin.z + Math.sin(angle) * radius, 0.1) * 0.01
         );
      }

      private Vec3 worldPoint(double angle, double radius, double yAdd) {
         double x = this.origin.x + Math.cos(angle) * radius;
         double z = this.origin.z + Math.sin(angle) * radius;
         return new Vec3(x, this.sampleSurfaceY(x, z, this.baseY) + yAdd, z);
      }

      private boolean inside(double x, double z) {
         double dx = x - this.origin.x;
         double dz = z - this.origin.z;
         double dist = Math.sqrt(dx * dx + dz * dz);
         if (dist > this.visualRadius + 0.92) {
            return false;
         }

         double angle = Math.atan2(dz, dx);
         if (angle < 0.0) {
            angle += Math.PI * 2;
         }

         int edgeIndex = Mth.floor(angle / (Math.PI * 2) * 256.0);
         return dist <= this.visualRadius * this.organicEdge(edgeIndex) + 0.34;
      }

      private double sampleSurfaceY(double x, double z, double yHint) {
         int ix = Mth.floor(x);
         int iz = Mth.floor(z);
         MutableBlockPos pos = new MutableBlockPos();
         int startY = Mth.floor(yHint) + 10;
         int endY = Mth.floor(yHint) - 14;

         for (int y = startY; y >= endY; y--) {
            pos.set(ix, y, iz);
            BlockState state = this.level.getBlockState(pos);
            if (!state.isAir() && this.level.getBlockState(pos.above()).isAir()) {
               return y + 1.002;
            }
         }

         return yHint;
      }

      private double organicEdge(int index) {
         int i = Math.floorMod(index, 256);
         float a = this.edge[i];
         float b = this.edge[Math.floorMod(i + 37, 256)];
         float c = this.edge[Math.floorMod(i + 91, 256)];
         return 0.935 + a * 0.064 + Math.sin(i * 0.043 + b * 6.0) * 0.04 + Math.sin(i * 0.091 + c * 5.0) * 0.026;
      }

      private double innerOrganic(int index, double frac) {
         int i = Math.floorMod(index, 256);
         float a = this.edge[i];
         return 0.99 + Math.sin(i * 0.045 + frac * 2.7 + a * 5.2) * 0.008 + Math.sin(i * 0.093 + frac * 1.9) * 0.005;
      }

      private double softNoise(double x, double z, double scale) {
         long xi = Mth.floor(x / scale);
         long zi = Mth.floor(z / scale);
         double fx = x / scale - xi;
         double fz = z / scale - zi;
         double a = AlbisVinaVfxClient.hash(this.seed, xi * 73428767L ^ zi * 912931L);
         double b = AlbisVinaVfxClient.hash(this.seed, (xi + 1L) * 73428767L ^ zi * 912931L);
         double c = AlbisVinaVfxClient.hash(this.seed, xi * 73428767L ^ (zi + 1L) * 912931L);
         double d = AlbisVinaVfxClient.hash(this.seed, (xi + 1L) * 73428767L ^ (zi + 1L) * 912931L);
         double sx = fx * fx * (3.0 - 2.0 * fx);
         double sz = fz * fz * (3.0 - 2.0 * fz);
         return Mth.lerp(sz, Mth.lerp(sx, a, b), Mth.lerp(sx, c, d));
      }
   }
}
