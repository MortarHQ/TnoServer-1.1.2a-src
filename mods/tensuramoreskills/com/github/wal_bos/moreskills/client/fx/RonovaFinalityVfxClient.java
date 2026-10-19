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
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class RonovaFinalityVfxClient {
   private static final List<RonovaFinalityVfxClient.Burst> BURSTS = new ArrayList<>();
   private static boolean initialized = false;

   private RonovaFinalityVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(RonovaFinalityVfxClient::render);
      }
   }

   public static void spawn(double x, double y, double z, float radius, long seed, boolean passive) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 115600.0)) {
            BURSTS.add(new RonovaFinalityVfxClient.Burst(new Vec3(x, y, z), Math.max(10.0F, radius), seed, passive, Util.getMillis()));
            if (BURSTS.size() > 4) {
               BURSTS.remove(0);
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
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = poseStack.last().pose();
            Iterator<RonovaFinalityVfxClient.Burst> iterator = BURSTS.iterator();

            while (iterator.hasNext()) {
               RonovaFinalityVfxClient.Burst burst = iterator.next();
               if (burst.dead(now)) {
                  iterator.remove();
               } else {
                  burst.render(matrix, now);
               }
            }

            poseStack.popPose();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
         } else {
            BURSTS.clear();
         }
      }
   }

   private static final class Burst {
      private static final int BOLTS = 96;
      private static final int BLACK_BOLTS = 72;
      private static final int SHARDS = 180;
      private static final int RUNES = 96;
      private static final int CHAINS = 72;
      private static final int ECLIPSES = 18;
      private static final int PILLARS = 64;
      private static final int SCARS = 88;
      private static final int ASH = 360;
      private static final int CROWS = 96;
      private static final int NEEDLES = 144;
      private static final int SIGILS = 64;
      private static final int RIBBONS = 72;
      private final Vec3 origin;
      private final float radius;
      private final long seed;
      private final boolean passive;
      private final long created;
      private final float[] bolts = new float[96];
      private final float[] blackBolts = new float[72];
      private final float[] shards = new float[180];
      private final float[] runes = new float[96];
      private final float[] chains = new float[72];
      private final float[] eclipses = new float[18];
      private final float[] pillars = new float[64];
      private final float[] scars = new float[88];
      private final float[] ash = new float[360];
      private final float[] crows = new float[96];
      private final float[] needles = new float[144];
      private final float[] sigils = new float[64];
      private final float[] ribbons = new float[72];

      private Burst(Vec3 origin, float radius, long seed, boolean passive, long created) {
         this.origin = origin;
         this.radius = radius;
         this.seed = seed;
         this.passive = passive;
         this.created = created;

         for (int i = 0; i < 96; i++) {
            this.bolts[i] = hash(seed, 1000 + i);
         }

         for (int i = 0; i < 72; i++) {
            this.blackBolts[i] = hash(seed, 2000 + i);
         }

         for (int i = 0; i < 180; i++) {
            this.shards[i] = hash(seed, 3000 + i);
         }

         for (int i = 0; i < 96; i++) {
            this.runes[i] = hash(seed, 4000 + i);
         }

         for (int i = 0; i < 72; i++) {
            this.chains[i] = hash(seed, 5000 + i);
         }

         for (int i = 0; i < 18; i++) {
            this.eclipses[i] = hash(seed, 6000 + i);
         }

         for (int i = 0; i < 64; i++) {
            this.pillars[i] = hash(seed, 7000 + i);
         }

         for (int i = 0; i < 88; i++) {
            this.scars[i] = hash(seed, 8000 + i);
         }

         for (int i = 0; i < 360; i++) {
            this.ash[i] = hash(seed, 9000 + i);
         }

         for (int i = 0; i < 96; i++) {
            this.crows[i] = hash(seed, 10000 + i);
         }

         for (int i = 0; i < 144; i++) {
            this.needles[i] = hash(seed, 11000 + i);
         }

         for (int i = 0; i < 64; i++) {
            this.sigils[i] = hash(seed, 12000 + i);
         }

         for (int i = 0; i < 72; i++) {
            this.ribbons[i] = hash(seed, 13000 + i);
         }
      }

      private boolean dead(long now) {
         return (now - this.created) / 50.0 > this.life();
      }

      private double life() {
         return this.passive ? 150.0 : 190.0;
      }

      private void render(Matrix4f matrix, long now) {
         double age = Math.max(0.0, (now - this.created) / 50.0);
         float t = Mth.clamp((float)(age / this.life()), 0.0F, 1.0F);
         float birth = smooth(Mth.clamp(t / 0.1F, 0.0F, 1.0F));
         float summon = phase(t, 0.0F, 0.28F);
         float eclipse = phase(t, 0.08F, 0.48F);
         float storm = phase(t, 0.2F, 0.72F);
         float verdict = phase(t, 0.36F, 0.86F);
         float execute = phase(t, 0.5F, 0.94F);
         float collapse = phase(t, 0.68F, 1.0F);
         float fade = 1.0F - smooth(Mth.clamp((t - 0.88F) / 0.12F, 0.0F, 1.0F));
         float live = birth * fade;
         if (!(live <= 0.002F)) {
            double x = this.origin.x;
            double y = this.origin.y + 0.55;
            double z = this.origin.z;
            RenderSystem.blendFuncSeparate(770, 771, 1, 771);
            this.renderBlackDominion(matrix, x, y, z, age, live, summon, eclipse, storm, verdict, execute, collapse);
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            this.renderCrimsonDivinity(matrix, x, y, z, age, live, summon, eclipse, storm, verdict, execute, collapse);
         }
      }

      private void renderBlackDominion(
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double time,
         float live,
         float summon,
         float eclipse,
         float storm,
         float verdict,
         float execute,
         float collapse
      ) {
         this.renderNightDome(matrix, x, y + this.radius * 0.18, z, time, live, eclipse, storm, collapse);
         this.renderAbyssMaw(matrix, x, y + 0.04, z, time, live, summon, eclipse, collapse);
         this.renderEclipseBodies(matrix, x, y + this.radius * 0.42, z, time, live, eclipse, verdict, collapse);
         this.renderBlackJudgementGate(matrix, x, y + this.radius * 0.3, z, time, live, verdict, execute, collapse);
         this.renderBlackChains(matrix, x, y + this.radius * 0.2, z, time, live, verdict, execute, collapse);
         this.renderBlackLightning(matrix, x, y + this.radius * 0.3, z, time, live, storm, execute, collapse);
         this.renderRealityTears(matrix, x, y + this.radius * 0.36, z, time, live, storm, verdict, collapse);
         this.renderObsidianNeedles(matrix, x, y + this.radius * 0.12, z, time, live, storm, execute, collapse);
         this.renderCrowSilhouettes(matrix, x, y + this.radius * 0.44, z, time, live, storm, verdict, collapse);
         this.renderBlackAsh(matrix, x, y + this.radius * 0.12, z, time, live, storm, collapse);
         this.renderCollapseVoid(matrix, x, y + this.radius * 0.3, z, time, live, execute, collapse);
      }

      private void renderCrimsonDivinity(
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double time,
         float live,
         float summon,
         float eclipse,
         float storm,
         float verdict,
         float execute,
         float collapse
      ) {
         this.renderSummoningScript(matrix, x, y + 0.08, z, time, live, summon, eclipse, collapse);
         this.renderCrimsonShockwaves(matrix, x, y + 0.1, z, time, live, summon, storm, collapse);
         this.renderRedLightning(matrix, x, y + this.radius * 0.25, z, time, live, storm, execute, collapse);
         this.renderExecutionPillars(matrix, x, y + 0.08, z, time, live, verdict, execute, collapse);
         this.renderBloodHalo(matrix, x, y + this.radius * 0.58, z, time, live, eclipse, verdict, collapse);
         this.renderRunicCourt(matrix, x, y + this.radius * 0.16, z, time, live, verdict, execute, collapse);
         this.renderCrimsonRibbons(matrix, x, y + this.radius * 0.26, z, time, live, storm, verdict, collapse);
         this.renderShards(matrix, x, y + this.radius * 0.22, z, time, live, storm, execute, collapse);
         this.renderFinalSentence(matrix, x, y + this.radius * 0.28, z, time, live, execute, collapse);
         this.renderAfterPulse(matrix, x, y + this.radius * 0.12, z, time, live, collapse);
      }

      private void renderNightDome(Matrix4f matrix, double x, double y, double z, double time, float live, float eclipse, float storm, float collapse) {
         float pulse = 0.5F + 0.5F * Mth.sin((float)time * 0.11F);
         float r1 = this.radius * (0.62F + eclipse * 0.76F + storm * 0.38F + pulse * 0.03F);
         float r2 = this.radius * (0.32F + eclipse * 0.42F + storm * 0.2F);
         float a1 = live * (0.34F + eclipse * 0.28F + storm * 0.18F) * (1.0F - collapse * 0.38F);
         renderSphere(matrix, x, y, z, r1, r1 * 0.56F, 0, 0, 0, a1, 10, 72, time * 0.01, 0.82F);
         renderSphere(matrix, x, y + this.radius * 0.22, z, r2, r2 * 0.96F, 0, 0, 0, live * (0.48F + eclipse * 0.25F), 9, 64, -time * 0.021, 0.92F);
         renderSphere(matrix, x, y - this.radius * 0.1, z, r1 * 1.18F, r1 * 0.22F, 0, 0, 0, live * 0.26F * (1.0F - collapse * 0.2F), 8, 72, time * 0.018, 0.72F);
      }

      private void renderAbyssMaw(Matrix4f matrix, double x, double y, double z, double time, float live, float summon, float eclipse, float collapse) {
         double outer = this.radius * (0.32 + summon * 0.62 + eclipse * 0.52);
         double mid = outer * 0.54;
         double inner = this.radius * (0.045 + summon * 0.06);
         renderDisc(matrix, x, y, z, 0.0, outer, time * 0.018, 0, 0, 0, live * (0.56F + eclipse * 0.2F) * (1.0F - collapse * 0.2F), 168);
         renderRing(matrix, x, y + 0.03, z, inner, mid, this.radius * 0.018, -time * 0.058, 0, 0, 0, live * 0.66F, 144);
         renderRing(matrix, x, y + 0.06, z, mid * 0.76, outer * 0.92, this.radius * 0.03, time * 0.035, 18, 0, 4, live * 0.46F, 152);
      }

      private void renderEclipseBodies(Matrix4f matrix, double x, double y, double z, double time, float live, float eclipse, float verdict, float collapse) {
         for (int i = 0; i < 18; i++) {
            float s = this.eclipses[i];
            float s2 = hash(this.seed, 6100 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.012 + s2 * 0.018) * (i % 2 == 0 ? 1.0 : -1.0);
            double d = this.radius * (0.18 + eclipse * (0.38 + s2 * 0.45) + verdict * 0.12);
            double yy = y + (s2 - 0.45) * this.radius * 0.7 + Math.sin(time * 0.04 + i) * this.radius * 0.04;
            float size = this.radius * (0.055F + s * 0.08F + verdict * 0.035F);
            float alpha = live * eclipse * (0.44F + s2 * 0.24F) * (1.0F - collapse * 0.5F);
            renderSphere(matrix, x + Math.cos(a) * d, yy, z + Math.sin(a) * d, size, size * 1.06F, 0, 0, 0, alpha, 6, 36, -time * 0.06, 0.95F);
         }
      }

      private void renderBlackJudgementGate(
         Matrix4f matrix, double x, double y, double z, double time, float live, float verdict, float execute, float collapse
      ) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float open = Mth.clamp(verdict + execute * 0.5F, 0.0F, 1.0F);
         double h = this.radius * (0.72 + execute * 0.58);
         double w = this.radius * (0.24 + open * 0.42);
         double thick = this.radius * (0.018 + execute * 0.01);

         for (int i = 0; i < 4; i++) {
            double a = i * (float) (Math.PI / 2) + time * 0.01;
            double sx = Math.cos(a);
            double sz = Math.sin(a);
            double px = Math.cos(a + (float) (Math.PI / 2));
            double pz = Math.sin(a + (float) (Math.PI / 2));
            double cx = x + sx * w;
            double cz = z + sz * w;
            float alpha = live * (0.34F + verdict * 0.32F + execute * 0.28F) * (1.0F - collapse * 0.42F);
            drew |= addBeam(buffer, matrix, cx - px * thick, y - h * 0.45, cz - pz * thick, cx + px * thick, y + h, cz + pz * thick, 0, 0, 0, alpha);
            drew |= addBeam(buffer, matrix, x - sx * w, y + h * 0.48, z - sz * w, x + sx * w, y + h * 0.57, z + sz * w, 0, 0, 0, alpha * 0.72F);
            drew |= addBeam(
               buffer, matrix, x - sx * w * 0.55, y - h * 0.24, z - sz * w * 0.55, x + sx * w * 0.55, y - h * 0.14, z + sz * w * 0.55, 0, 0, 0, alpha * 0.62F
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderBlackChains(Matrix4f matrix, double x, double y, double z, double time, float live, float verdict, float execute, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float strength = live * verdict * (0.4F + execute * 0.32F) * (1.0F - collapse * 0.45F);

         for (int i = 0; i < 72; i++) {
            float s = this.chains[i];
            float s2 = hash(this.seed, 5200 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.012 + s2 * 0.022) * (i % 2 == 0 ? 1.0 : -1.0);
            double ring = this.radius * (0.34 + verdict * 0.54 + s2 * 0.16);
            double y0 = y + (s2 - 0.5) * this.radius * 0.72 + execute * this.radius * 0.08;
            double len = this.radius * (0.035 + s * 0.04);
            double wid = this.radius * (0.012 + s2 * 0.01);
            double cx = x + Math.cos(a) * ring;
            double cz = z + Math.sin(a) * ring;
            drew |= addDiamond(buffer, matrix, cx, y0, cz, len, wid, a + time * 0.03, 0, 0, 0, strength * (0.48F + s * 0.24F));
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderBlackLightning(Matrix4f matrix, double x, double y, double z, double time, float live, float storm, float execute, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float strength = live * storm * (0.65F + execute * 0.4F) * (1.0F - collapse * 0.32F);

         for (int i = 0; i < 72; i++) {
            float s = this.blackBolts[i];
            float s2 = hash(this.seed, 2200 + i);
            float s3 = hash(this.seed, 2400 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.026 + s2 * 0.034) * (i % 2 == 0 ? 1.0 : -1.0);
            double start = this.radius * (0.08 + s2 * 0.18);
            double end = this.radius * (0.44 + storm * (0.5 + s3 * 0.42));
            double y0 = y + (s3 - 0.5) * this.radius * 0.48;
            double y1 = y + (s2 - 0.5) * this.radius * 0.9;
            double lx = x + Math.cos(a) * start;
            double ly = y0;
            double lz = z + Math.sin(a) * start;
            int segments = 5 + (int)(s3 * 5.0F);

            for (int seg = 1; seg <= segments; seg++) {
               double p = (double)seg / segments;
               double bend = Math.sin(time * 0.35 + i * 3.1 + seg) * 0.32;
               double aa = a + bend + (s2 - 0.5) * p * 1.2;
               double d = Mth.lerp(p, start, end);
               double nx = x + Math.cos(aa) * d;
               double ny = Mth.lerp(p, y0, y1) + Math.sin(time * 0.22 + seg * 2.0 + i) * this.radius * 0.05;
               double nz = z + Math.sin(aa) * d;
               float alpha = strength * (0.4F + s * 0.26F) * (1.0F - (float)p * 0.24F);
               drew |= addRibbon(buffer, matrix, lx, ly, lz, nx, ny, nz, 4.0F + s2 * 4.0F + execute * 2.0F, 0, 0, 0, alpha);
               lx = nx;
               ly = ny;
               lz = nz;
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderRealityTears(Matrix4f matrix, double x, double y, double z, double time, float live, float storm, float verdict, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;

         for (int i = 0; i < 88; i++) {
            float s = this.scars[i];
            float s2 = hash(this.seed, 8200 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.01 + s2 * 0.016);
            double d = this.radius * (0.18 + storm * (0.5 + s2 * 0.58));
            double yy = y + (s - 0.5) * this.radius * (0.84 + verdict * 0.4);
            double len = this.radius * (0.07 + s2 * 0.16);
            double cx = x + Math.cos(a) * d;
            double cz = z + Math.sin(a) * d;
            double x1 = cx - Math.cos(a + (float) (Math.PI / 2)) * len;
            double z1 = cz - Math.sin(a + (float) (Math.PI / 2)) * len;
            double x2 = cx + Math.cos(a + (float) (Math.PI / 2)) * len;
            double z2 = cz + Math.sin(a + (float) (Math.PI / 2)) * len;
            float alpha = live * storm * (0.26F + verdict * 0.16F) * (1.0F - collapse * 0.42F);
            drew |= addRibbon(buffer, matrix, x1, yy - len * 0.3, z1, x2, yy + len * 0.3, z2, 4.0F + s * 5.0F, 0, 0, 0, alpha);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderObsidianNeedles(Matrix4f matrix, double x, double y, double z, double time, float live, float storm, float execute, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;

         for (int i = 0; i < 144; i++) {
            float s = this.needles[i];
            float s2 = hash(this.seed, 11200 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.012 + s2 * 0.03);
            double ring = this.radius * (0.12 + storm * (0.28 + s2 * 0.7));
            double len = this.radius * (0.08 + s * 0.26 + execute * 0.12);
            double w = this.radius * (0.01 + s2 * 0.02);
            double yy = y + (s2 - 0.35) * this.radius * (0.48 + execute * 0.45);
            float alpha = live * storm * (0.32F + execute * 0.24F) * (1.0F - collapse * 0.48F);
            drew |= addTriangleSpike(buffer, matrix, x, yy, z, a, ring, len, w, 0, 0, 0, alpha);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderCrowSilhouettes(Matrix4f matrix, double x, double y, double z, double time, float live, float storm, float verdict, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;

         for (int i = 0; i < 96; i++) {
            float s = this.crows[i];
            float s2 = hash(this.seed, 10200 + i);
            float s3 = hash(this.seed, 10400 + i);
            double orbit = time * (0.03 + s2 * 0.04) + s * (float) (Math.PI * 2);
            double d = this.radius * (0.24 + storm * (0.44 + s2 * 0.6));
            double flap = Math.sin(time * (0.3 + s3 * 0.25) + i) * this.radius * 0.025;
            double yy = y + (s3 - 0.15) * this.radius * 0.85 + flap + verdict * this.radius * 0.12;
            float size = this.radius * (0.02F + s3 * 0.025F);
            float alpha = live * storm * (0.24F + verdict * 0.16F) * (1.0F - collapse * 0.58F);
            drew |= addBird(buffer, matrix, x + Math.cos(orbit) * d, yy, z + Math.sin(orbit) * d, size, orbit + (float) (Math.PI / 2), 0, 0, 0, alpha);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderBlackAsh(Matrix4f matrix, double x, double y, double z, double time, float live, float storm, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;

         for (int i = 0; i < 360; i++) {
            float s = this.ash[i];
            float s2 = hash(this.seed, 9200 + i);
            float s3 = hash(this.seed, 9400 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.006 + s2 * 0.012);
            double d = this.radius * (0.06 + s2 * 1.22) * (0.35 + storm * 0.92);
            double rise = (time * (0.32 + s * 0.94) + i * 0.73) % (this.radius * 1.5 + 8.0);
            double yy = y - this.radius * 0.35 + rise + (s3 - 0.5) * this.radius * 0.36 - collapse * this.radius * 0.38;
            float size = this.radius * (0.004F + s3 * 0.01F);
            float alpha = live * (0.2F + storm * 0.24F) * (1.0F - collapse * 0.42F);
            drew |= addSquare(buffer, matrix, x + Math.cos(a) * d, yy, z + Math.sin(a) * d, size, 0, 0, 0, alpha);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderCollapseVoid(Matrix4f matrix, double x, double y, double z, double time, float live, float execute, float collapse) {
         if (!(collapse <= 0.001F)) {
            float crush = smooth(collapse);
            float r = this.radius * (0.2F + execute * 0.2F + crush * 0.36F);
            renderSphere(matrix, x, y, z, r * 1.35F, r * 1.24F, 0, 0, 0, live * crush * 0.86F, 10, 72, time * 0.08, 1.0F);
            renderSphere(matrix, x, y + this.radius * 0.04, z, r * 0.66F, r * 1.7F, 0, 0, 0, live * crush * 0.9F, 8, 56, -time * 0.14, 0.95F);
         }
      }

      private void renderSummoningScript(Matrix4f matrix, double x, double y, double z, double time, float live, float summon, float eclipse, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float strength = live * (summon * 0.65F + eclipse * 0.28F) * (1.0F - collapse * 0.5F);

         for (int i = 0; i < 64; i++) {
            float s = this.sigils[i];
            float s2 = hash(this.seed, 12200 + i);
            double layer = i % 4;
            double a = s * (float) (Math.PI * 2) + time * (0.03 + layer * 0.018) * (i % 2 == 0 ? 1.0 : -1.0);
            double ring = this.radius * (0.18 + layer * 0.15 + summon * 0.32 + s2 * 0.05);
            double sx = x + Math.cos(a) * ring;
            double sz = z + Math.sin(a) * ring;
            float len = this.radius * (0.018F + s2 * 0.032F);
            float wid = this.radius * (0.004F + s * 0.01F);
            int red = i % 6 == 0 ? 255 : 155;
            int blue = i % 6 == 0 ? 40 : 14;
            drew |= addDiamond(buffer, matrix, sx, y + layer * 0.016, sz, len, wid, a + (float) (Math.PI / 2), red, 0, blue, strength * (0.32F + s * 0.22F));
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }

         for (int i = 0; i < 7; i++) {
            double p = i / 6.0;
            double inner = this.radius * (0.12 + p * 0.12 + summon * 0.08);
            double outer = inner + this.radius * (0.01 + p * 0.004);
            renderRing(
               matrix,
               x,
               y + 0.02 + i * 0.018,
               z,
               inner,
               outer,
               this.radius * (0.006 + summon * 0.014),
               time * (0.06 + p * 0.035) * (i % 2 == 0 ? 1.0 : -1.0),
               255,
               0,
               24,
               strength * (0.22F + (float)p * 0.06F),
               128
            );
         }
      }

      private void renderCrimsonShockwaves(Matrix4f matrix, double x, double y, double z, double time, float live, float summon, float storm, float collapse) {
         for (int i = 0; i < 12; i++) {
            float p = i / 11.0F;
            double base = this.radius * (0.1 + summon * 0.2 + storm * (0.18 + p * 0.88));
            double thick = this.radius * (0.01 + p * 0.02);
            float alpha = live * storm * (0.24F - p * 0.01F) * (1.0F - collapse * 0.44F);
            renderRing(
               matrix,
               x,
               y + 0.06 + p * this.radius * 0.06,
               z,
               base,
               base + thick,
               this.radius * (0.018 + p * 0.03),
               time * (0.045 + p * 0.04),
               255,
               0,
               32,
               alpha,
               144
            );
         }
      }

      private void renderRedLightning(Matrix4f matrix, double x, double y, double z, double time, float live, float storm, float execute, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float strength = live * storm * (0.72F + execute * 0.7F) * (1.0F - collapse * 0.35F);

         for (int i = 0; i < 96; i++) {
            float s = this.bolts[i];
            float s2 = hash(this.seed, 1200 + i);
            float s3 = hash(this.seed, 1400 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.042 + s2 * 0.06) * (i % 2 == 0 ? 1.0 : -1.0);
            double start = this.radius * (0.04 + s2 * 0.16);
            double end = this.radius * (0.42 + storm * (0.62 + s3 * 0.58));
            double y0 = y + (s3 - 0.5) * this.radius * 0.3;
            double y1 = y + (s2 - 0.5) * this.radius * 1.12 + execute * this.radius * 0.2;
            double lx = x + Math.cos(a) * start;
            double ly = y0;
            double lz = z + Math.sin(a) * start;
            int segments = 5 + (int)(s2 * 7.0F);

            for (int seg = 1; seg <= segments; seg++) {
               double p = (double)seg / segments;
               double jag = Math.sin(time * 0.72 + i * 8.2 + seg * 4.4) * 0.34;
               double aa = a + jag + (s2 - 0.5) * p * 1.6;
               double d = Mth.lerp(p, start, end);
               double nx = x + Math.cos(aa) * d;
               double ny = Mth.lerp(p, y0, y1) + Math.cos(time * 0.65 + i + seg) * this.radius * (0.02 + p * 0.05);
               double nz = z + Math.sin(aa) * d;
               float alpha = strength * (0.52F + s3 * 0.34F) * (1.0F - (float)p * 0.22F);
               drew |= addRibbon(buffer, matrix, lx, ly, lz, nx, ny, nz, 2.8F + s * 3.8F + execute * 2.0F, 120, 0, 12, alpha * 0.48F);
               drew |= addRibbon(buffer, matrix, lx, ly, lz, nx, ny, nz, 1.05F + s2 * 1.35F, 255, 0, 30, alpha);
               drew |= addRibbon(buffer, matrix, lx, ly, lz, nx, ny, nz, 0.28F + s3 * 0.32F, 255, 140, 140, alpha * 0.72F);
               lx = nx;
               ly = ny;
               lz = nz;
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderExecutionPillars(Matrix4f matrix, double x, double y, double z, double time, float live, float verdict, float execute, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float strength = live * (verdict * 0.32F + execute * 0.74F) * (1.0F - collapse * 0.42F);

         for (int i = 0; i < 64; i++) {
            float s = this.pillars[i];
            float s2 = hash(this.seed, 7200 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.01 + s2 * 0.012);
            double d = this.radius * (0.12 + s2 * 0.95);
            double h = this.radius * (0.45 + verdict * 0.55 + execute * 1.25 + s * 0.36);
            double bx = x + Math.cos(a) * d;
            double bz = z + Math.sin(a) * d;
            double lean = this.radius * (0.015 + s * 0.04);
            float alpha = strength * (0.16F + s2 * 0.3F);
            drew |= addRibbon(
               buffer, matrix, bx, y - this.radius * 0.12, bz, bx + Math.cos(a) * lean, y + h, bz + Math.sin(a) * lean, 2.4F + s * 4.0F, 255, 0, 28, alpha
            );
            drew |= addRibbon(
               buffer,
               matrix,
               bx,
               y - this.radius * 0.12,
               bz,
               bx + Math.cos(a) * lean,
               y + h,
               bz + Math.sin(a) * lean,
               0.55F + s2,
               255,
               160,
               160,
               alpha * 0.54F
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderBloodHalo(Matrix4f matrix, double x, double y, double z, double time, float live, float eclipse, float verdict, float collapse) {
         float strength = live * (eclipse * 0.26F + verdict * 0.5F) * (1.0F - collapse * 0.32F);

         for (int i = 0; i < 10; i++) {
            float p = i / 9.0F;
            double inner = this.radius * (0.25 + p * 0.025 + verdict * 0.22);
            double outer = inner + this.radius * (0.01 + p * 0.006);
            renderRing(
               matrix,
               x,
               y + p * this.radius * 0.018,
               z,
               inner,
               outer,
               this.radius * (0.008 + p * 0.015),
               time * (0.09 + p * 0.06) * (i % 2 == 0 ? 1.0 : -1.0),
               255,
               0,
               36,
               strength * (0.18F + p * 0.05F),
               128
            );
         }

         renderSphere(
            matrix,
            x,
            y,
            z,
            this.radius * (0.28F + eclipse * 0.12F + verdict * 0.18F),
            this.radius * (0.08F + verdict * 0.06F),
            255,
            0,
            26,
            strength * 0.18F,
            7,
            56,
            time * 0.08,
            0.7F
         );
      }

      private void renderRunicCourt(Matrix4f matrix, double x, double y, double z, double time, float live, float verdict, float execute, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float strength = live * verdict * (0.38F + execute * 0.36F) * (1.0F - collapse * 0.48F);

         for (int i = 0; i < 96; i++) {
            float s = this.runes[i];
            float s2 = hash(this.seed, 4200 + i);
            double layer = i % 5;
            double a = s * (float) (Math.PI * 2) + time * (0.032 + layer * 0.012) * (i % 2 == 0 ? 1.0 : -1.0);
            double ring = this.radius * (0.28 + verdict * 0.58 + layer * 0.075 + s2 * 0.05);
            double yy = y + layer * this.radius * 0.055 + Math.sin(time * 0.06 + i) * this.radius * 0.025;
            float len = this.radius * (0.018F + s2 * 0.04F);
            float wid = this.radius * (0.005F + s * 0.012F);
            int red = i % 4 == 0 ? 255 : 180;
            int blue = i % 4 == 0 ? 42 : 18;
            drew |= addDiamond(
               buffer,
               matrix,
               x + Math.cos(a) * ring,
               yy,
               z + Math.sin(a) * ring,
               len,
               wid,
               a + (float) (Math.PI / 2),
               red,
               0,
               blue,
               strength * (0.36F + s2 * 0.22F)
            );
            drew |= addDiamond(
               buffer,
               matrix,
               x + Math.cos(a + 0.018) * ring,
               yy + len * 0.3F,
               z + Math.sin(a + 0.018) * ring,
               len * 0.42F,
               wid * 0.65F,
               a,
               255,
               110,
               110,
               strength * 0.18F
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderCrimsonRibbons(Matrix4f matrix, double x, double y, double z, double time, float live, float storm, float verdict, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float strength = live * storm * (0.32F + verdict * 0.28F) * (1.0F - collapse * 0.4F);

         for (int i = 0; i < 72; i++) {
            float s = this.ribbons[i];
            float s2 = hash(this.seed, 13200 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.018 + s2 * 0.026) * (i % 2 == 0 ? 1.0 : -1.0);
            double base = this.radius * (0.08 + s2 * 0.24);
            double out = this.radius * (0.28 + storm * (0.38 + s * 0.36));
            double lx = x + Math.cos(a) * base;
            double ly = y + (s2 - 0.4) * this.radius * 0.32;
            double lz = z + Math.sin(a) * base;

            for (int seg = 1; seg <= 7; seg++) {
               double p = seg / 7.0;
               double aa = a + p * (1.2 + s2 * 1.2) + Math.sin(time * 0.09 + seg + i) * 0.22;
               double d = base + out * p;
               double nx = x + Math.cos(aa) * d;
               double ny = y + Math.sin(p * Math.PI) * this.radius * (0.12 + s * 0.22) + (s2 - 0.5) * this.radius * 0.42;
               double nz = z + Math.sin(aa) * d;
               float alpha = strength * (0.18F + s * 0.26F) * (1.0F - (float)p * 0.4F);
               drew |= addRibbon(buffer, matrix, lx, ly, lz, nx, ny, nz, 1.2F + s2 * 2.4F, 255, 0, 30, alpha);
               lx = nx;
               ly = ny;
               lz = nz;
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderShards(Matrix4f matrix, double x, double y, double z, double time, float live, float storm, float execute, float collapse) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float strength = live * storm * (0.3F + execute * 0.28F) * (1.0F - collapse * 0.42F);

         for (int i = 0; i < 180; i++) {
            float s = this.shards[i];
            float s2 = hash(this.seed, 3200 + i);
            float s3 = hash(this.seed, 3400 + i);
            double a = s * (float) (Math.PI * 2) + time * (0.02 + s2 * 0.03) * (i % 2 == 0 ? 1.0 : -1.0);
            double d = this.radius * (0.12 + storm * (0.34 + s2 * 0.88));
            double yy = y + (s3 - 0.35) * this.radius * (0.72 + execute * 0.5);
            double len = this.radius * (0.04 + s * 0.12 + execute * 0.04);
            double w = this.radius * (0.008 + s2 * 0.022);
            int red = i % 5 == 0 ? 255 : 190;
            int blue = i % 5 == 0 ? 36 : 12;
            drew |= addTriangleShard(
               buffer, matrix, x + Math.cos(a) * d, yy, z + Math.sin(a) * d, a + time * 0.06, len, w, red, 0, blue, strength * (0.3F + s3 * 0.26F)
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private void renderFinalSentence(Matrix4f matrix, double x, double y, double z, double time, float live, float execute, float collapse) {
         if (!(execute <= 0.001F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean drew = false;
            double h = this.radius * (0.86 + execute * 1.16);
            double w = this.radius * (0.045 + execute * 0.04);
            float alpha = live * execute * (0.62F + collapse * 0.24F);

            for (int i = 0; i < 8; i++) {
               double a = i * (float) Math.PI / 4.0 + time * 0.012;
               double sx = Math.cos(a) * w;
               double sz = Math.sin(a) * w;
               drew |= addRibbon(buffer, matrix, x - sx, y - h * 0.5, z - sz, x + sx, y + h, z + sz, 5.0F + execute * 6.0F, 255, 0, 30, alpha * 0.28F);
               drew |= addRibbon(
                  buffer,
                  matrix,
                  x - sx * 0.42,
                  y - h * 0.38,
                  z - sz * 0.42,
                  x + sx * 0.42,
                  y + h * 0.84,
                  z + sz * 0.42,
                  1.1F + execute * 2.0F,
                  255,
                  140,
                  140,
                  alpha * 0.36F
               );
            }

            double cross = this.radius * (0.38 + execute * 0.38);

            for (int i = 0; i < 4; i++) {
               double a = i * (float) (Math.PI / 2) + time * 0.018;
               drew |= addRibbon(
                  buffer,
                  matrix,
                  x - Math.cos(a) * cross,
                  y + h * 0.24,
                  z - Math.sin(a) * cross,
                  x + Math.cos(a) * cross,
                  y + h * 0.24,
                  z + Math.sin(a) * cross,
                  2.4F + execute * 3.0F,
                  255,
                  0,
                  30,
                  alpha * 0.42F
               );
            }

            if (drew) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }

            renderSphere(
               matrix,
               x,
               y + this.radius * 0.4,
               z,
               this.radius * (0.16F + execute * 0.12F + collapse * 0.14F),
               this.radius * (0.18F + execute * 0.3F),
               255,
               0,
               32,
               alpha * 0.2F,
               8,
               56,
               time * 0.12,
               0.75F
            );
         }
      }

      private void renderAfterPulse(Matrix4f matrix, double x, double y, double z, double time, float live, float collapse) {
         if (!(collapse <= 0.001F)) {
            float c = smooth(collapse);
            double size = this.radius * (0.22 + c * 1.55);
            renderRing(matrix, x, y - this.radius * 0.1, z, size * 0.1, size, this.radius * (0.03 + c * 0.08), time * 0.08, 255, 0, 30, live * c * 0.52F, 160);
            renderRing(matrix, x, y, z, size * 0.02, size * 0.68, this.radius * (0.05 + c * 0.1), -time * 0.14, 255, 120, 120, live * c * 0.18F, 160);
         }
      }

      private static void renderSphere(
         Matrix4f matrix,
         double x,
         double y,
         double z,
         float radius,
         float height,
         int red,
         int green,
         int blue,
         float alpha,
         int stacks,
         int slices,
         double rotation,
         float rimPower
      ) {
         if (!(alpha <= 5.0E-4F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            for (int stack = 0; stack < stacks; stack++) {
               double v0 = (double)stack / stacks;
               double v1 = (double)(stack + 1) / stacks;
               double phi0 = (float) (-Math.PI / 2) + Math.PI * v0;
               double phi1 = (float) (-Math.PI / 2) + Math.PI * v1;

               for (int slice = 0; slice < slices; slice++) {
                  double u0 = (double)slice / slices;
                  double u1 = (double)(slice + 1) / slices;
                  double theta0 = (float) (Math.PI * 2) * u0 + rotation;
                  double theta1 = (float) (Math.PI * 2) * u1 + rotation;
                  sphereVertex(buffer, matrix, x, y, z, radius, height, theta0, phi0, red, green, blue, alpha, rimPower, rotation);
                  sphereVertex(buffer, matrix, x, y, z, radius, height, theta0, phi1, red, green, blue, alpha, rimPower, rotation);
                  sphereVertex(buffer, matrix, x, y, z, radius, height, theta1, phi1, red, green, blue, alpha, rimPower, rotation);
                  sphereVertex(buffer, matrix, x, y, z, radius, height, theta1, phi0, red, green, blue, alpha, rimPower, rotation);
               }
            }

            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }

      private static void sphereVertex(
         BufferBuilder buffer,
         Matrix4f matrix,
         double cx,
         double cy,
         double cz,
         float radius,
         float height,
         double theta,
         double phi,
         int red,
         int green,
         int blue,
         float alpha,
         float rimPower,
         double rotation
      ) {
         float px = (float)(Math.cos(phi) * Math.cos(theta)) * radius;
         float py = (float)Math.sin(phi) * height;
         float pz = (float)(Math.cos(phi) * Math.sin(theta)) * radius;
         float rim = Mth.clamp((float)Math.sqrt(px * px + pz * pz) / Math.max(0.001F, radius), 0.0F, 1.0F);
         float wave = 0.68F + 0.32F * (float)Math.sin(theta * 6.0 + phi * 4.0 + rotation * 4.0);
         float a = alpha * Mth.clamp((float)Math.pow(rim, rimPower), 0.0F, 1.0F) * wave;
         buffer.addVertex(matrix, (float)cx + px, (float)cy + py, (float)cz + pz).setColor(red, green, blue, alpha(a));
      }

      private static void renderDisc(
         Matrix4f matrix, double x, double y, double z, double inner, double outer, double rotation, int red, int green, int blue, float alpha, int slices
      ) {
         if (!(alpha <= 5.0E-4F)) {
            if (!(outer <= inner)) {
               BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

               for (int i = 0; i < slices; i++) {
                  double u0 = (double)i / slices;
                  double u1 = (double)(i + 1) / slices;
                  double a0 = u0 * (float) (Math.PI * 2) + rotation;
                  double a1 = u1 * (float) (Math.PI * 2) + rotation;
                  ringVertex(buffer, matrix, x, y, z, inner, a0, red, green, blue, alpha);
                  ringVertex(buffer, matrix, x, y, z, inner, a1, red, green, blue, alpha);
                  ringVertex(buffer, matrix, x, y, z, outer, a1, red, green, blue, alpha * 0.22F);
                  ringVertex(buffer, matrix, x, y, z, outer, a0, red, green, blue, alpha * 0.22F);
               }

               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }
         }
      }

      private static void renderRing(
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double inner,
         double outer,
         double wobble,
         double rotation,
         int red,
         int green,
         int blue,
         float alpha,
         int slices
      ) {
         if (!(alpha <= 5.0E-4F)) {
            if (!(outer <= inner)) {
               BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

               for (int i = 0; i < slices; i++) {
                  double u0 = (double)i / slices;
                  double u1 = (double)(i + 1) / slices;
                  double a0 = u0 * (float) (Math.PI * 2) + rotation;
                  double a1 = u1 * (float) (Math.PI * 2) + rotation;
                  double y0 = y + Math.sin(a0 * 7.0 + rotation * 3.0) * wobble;
                  double y1 = y + Math.sin(a1 * 7.0 + rotation * 3.0) * wobble;
                  ringVertex(buffer, matrix, x, y0, z, inner, a0, red, green, blue, 0.0F);
                  ringVertex(buffer, matrix, x, y1, z, inner, a1, red, green, blue, alpha);
                  ringVertex(buffer, matrix, x, y1, z, outer, a1, red, green, blue, alpha * 0.62F);
                  ringVertex(buffer, matrix, x, y0, z, outer, a0, red, green, blue, 0.0F);
               }

               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }
         }
      }

      private static void ringVertex(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, double radius, double angle, int red, int green, int blue, float alpha
      ) {
         buffer.addVertex(matrix, (float)(x + Math.cos(angle) * radius), (float)y, (float)(z + Math.sin(angle) * radius))
            .setColor(red, green, blue, alpha(alpha));
      }

      private static boolean addRibbon(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x1,
         double y1,
         double z1,
         double x2,
         double y2,
         double z2,
         float width,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         Vec3 dir = new Vec3(x2 - x1, y2 - y1, z2 - z1);
         Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
         if (side.lengthSqr() < 1.0E-4) {
            side = new Vec3(1.0, 0.0, 0.0);
         } else {
            side = side.normalize();
         }

         side = side.scale(width);
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x1 - side.x), (float)y1, (float)(z1 - side.z)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x1 + side.x), (float)y1, (float)(z1 + side.z)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x2 + side.x), (float)y2, (float)(z2 + side.z)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x2 - side.x), (float)y2, (float)(z2 - side.z)).setColor(red, green, blue, 0);
         return true;
      }

      private static boolean addBeam(
         BufferBuilder buffer, Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, int red, int green, int blue, float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)x1, (float)y1, (float)z1).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)x2, (float)y1, (float)z2).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)x2, (float)y2, (float)z2).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)x1, (float)y2, (float)z1).setColor(red, green, blue, 0);
         return true;
      }

      private static boolean addDiamond(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double length,
         double width,
         double angle,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         double fx = Math.cos(angle) * length;
         double fz = Math.sin(angle) * length;
         double sx = Math.cos(angle + (float) (Math.PI / 2)) * width;
         double sz = Math.sin(angle + (float) (Math.PI / 2)) * width;
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x - fx), (float)y, (float)(z - fz)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x + sx), (float)(y + width), (float)(z + sz)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + fx), (float)y, (float)(z + fz)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x - sx), (float)(y - width), (float)(z - sz)).setColor(red, green, blue, 0);
         return true;
      }

      private static boolean addSquare(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, double size, int red, int green, int blue, float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x - size), (float)(y - size), (float)z).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x + size), (float)(y - size), (float)z).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + size), (float)(y + size), (float)z).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x - size), (float)(y + size), (float)z).setColor(red, green, blue, 0);
         return true;
      }

      private static boolean addTriangleShard(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double angle,
         double length,
         double width,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         double fx = Math.cos(angle) * length;
         double fz = Math.sin(angle) * length;
         double sx = Math.cos(angle + (float) (Math.PI / 2)) * width;
         double sz = Math.sin(angle + (float) (Math.PI / 2)) * width;
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x - fx * 0.45 - sx), (float)y, (float)(z - fz * 0.45 - sz)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x - fx * 0.45 + sx), (float)y, (float)(z - fz * 0.45 + sz)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + fx), (float)(y + length * 0.18), (float)(z + fz)).setColor(red, green, blue, a);
         return true;
      }

      private static boolean addTriangleSpike(
         BufferBuilder buffer,
         Matrix4f matrix,
         double x,
         double y,
         double z,
         double angle,
         double ring,
         double length,
         double width,
         int red,
         int green,
         int blue,
         float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         double bx = x + Math.cos(angle) * ring;
         double bz = z + Math.sin(angle) * ring;
         double tx = x + Math.cos(angle) * (ring + length);
         double tz = z + Math.sin(angle) * (ring + length);
         double sx = Math.cos(angle + (float) (Math.PI / 2)) * width;
         double sz = Math.sin(angle + (float) (Math.PI / 2)) * width;
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(bx - sx), (float)y, (float)(bz - sz)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(bx + sx), (float)y, (float)(bz + sz)).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)tx, (float)(y + length * 0.2), (float)tz).setColor(red, green, blue, a);
         return true;
      }

      private static boolean addBird(
         BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, double size, double angle, int red, int green, int blue, float alpha
      ) {
         if (alpha <= 5.0E-4F) {
            return false;
         }

         double fx = Math.cos(angle);
         double fz = Math.sin(angle);
         double sx = Math.cos(angle + (float) (Math.PI / 2));
         double sz = Math.sin(angle + (float) (Math.PI / 2));
         int a = alpha(alpha);
         buffer.addVertex(matrix, (float)(x - sx * size * 1.7), (float)y, (float)(z - sz * size * 1.7)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)x, (float)(y + size * 0.42), (float)z).setColor(red, green, blue, a);
         buffer.addVertex(matrix, (float)(x + sx * size * 1.7), (float)y, (float)(z + sz * size * 1.7)).setColor(red, green, blue, 0);
         buffer.addVertex(matrix, (float)(x + fx * size * 0.3), (float)(y - size * 0.22), (float)(z + fz * size * 0.3)).setColor(red, green, blue, a);
         return true;
      }

      private static int alpha(float alpha) {
         return Mth.clamp((int)(alpha * 255.0F), 0, 255);
      }

      private static float phase(float t, float start, float end) {
         float up = smooth(Mth.clamp((t - start) / 0.13F, 0.0F, 1.0F));
         float down = 1.0F - smooth(Mth.clamp((t - end) / 0.16F, 0.0F, 1.0F));
         return Mth.clamp(up * down, 0.0F, 1.0F);
      }

      private static float smooth(float value) {
         return value * value * (3.0F - 2.0F * value);
      }

      private static float hash(long seed, int index) {
         long value = seed ^ index * 341873128712L;
         value ^= value >>> 33;
         value *= -49064778989728563L;
         value ^= value >>> 33;
         value *= -4265267296055464877L;
         value ^= value >>> 33;
         return (float)(value & 16777215L) / 1.6777215E7F;
      }
   }
}
