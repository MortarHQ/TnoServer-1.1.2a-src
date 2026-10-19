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
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class AsmodayFallingCubeClient {
   private static final Map<Integer, AsmodayFallingCubeClient.Judgement> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;
   private static final int SINGLE_FALL_DURATION = 105;
   private static final int RAIN_FALL_DURATION = 145;
   private static final int GROUND_DESPAWN_TICKS = 20;
   private static final int NO_GROUND_DESPAWN_TICKS = 400;

   private AsmodayFallingCubeClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AsmodayFallingCubeClient::clientTick);
         NeoForge.EVENT_BUS.addListener(AsmodayFallingCubeClient::renderLevel);
      }
   }

   public static void play(
      int id,
      int casterId,
      int targetId,
      double originX,
      double originY,
      double originZ,
      double centerX,
      double centerY,
      double centerZ,
      int cubes,
      int seed,
      int duration,
      boolean mastered,
      boolean shifted
   ) {
      init();
      ACTIVE.put(
         id,
         new AsmodayFallingCubeClient.Judgement(
            targetId,
            new Vec3(originX, originY, originZ),
            new Vec3(centerX, centerY, centerZ),
            Mth.clamp(cubes, shifted ? 24 : 1, shifted ? 52 : 1),
            seed,
            Mth.clamp(duration, 120, 680),
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
         Iterator<Entry<Integer, AsmodayFallingCubeClient.Judgement>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            AsmodayFallingCubeClient.Judgement judgement = iterator.next().getValue();
            judgement.age++;
            if (judgement.age > judgement.duration) {
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

            for (AsmodayFallingCubeClient.Judgement judgement : ACTIVE.values()) {
               wrote |= judgement.render(buffer, matrix, cam, partial);
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

   private static final class Judgement {
      private final int targetId;
      private final Vec3 origin;
      private final Vec3 center;
      private final int cubes;
      private final int seed;
      private final int duration;
      private final boolean mastered;
      private final boolean shifted;
      private final int fallDuration;
      private int age;
      private final Map<Integer, AsmodayFallingCubeClient.Judgement.GroundInfo> groundCache = new ConcurrentHashMap<>();

      private Judgement(int targetId, Vec3 origin, Vec3 center, int cubes, int seed, int duration, boolean mastered, boolean shifted) {
         this.targetId = targetId;
         this.origin = origin;
         this.center = center;
         this.cubes = cubes;
         this.seed = seed;
         this.duration = duration;
         this.mastered = mastered;
         this.shifted = shifted;
         this.fallDuration = shifted ? 145 : 105;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         float t = this.age + partial;
         float appear = Mth.clamp(t / 8.0F, 0.0F, 1.0F);
         float fade = Mth.clamp((this.duration - t) / 24.0F, 0.0F, 1.0F);
         float globalAlpha = appear * fade;
         if (globalAlpha <= 0.002F) {
            return false;
         }

         boolean wrote = false;
         if (!this.shifted) {
            Vec3 target = this.resolveTarget(partial);
            double size = this.mastered ? 8.6 : 7.2;
            AsmodayFallingCubeClient.Judgement.GroundInfo ground = this.groundInfo(0, target, size);
            float progress = Mth.clamp(t / this.fallDuration, 0.0F, 1.0F);
            float localAfterGround = t - this.fallDuration;
            if (ground.hasGround && localAfterGround > 20.0F) {
               return false;
            }

            if (!ground.hasGround && localAfterGround > 400.0F) {
               return false;
            }

            Vec3 p = this.fallingPoint(target, 0, progress, this.seed, false, this.mastered, ground, size);
            AsmodayFallingCubeClient.Judgement.Basis basis = this.fixedCubeBasis(0);
            wrote |= this.renderJudgementCube(buffer, matrix, cam, p, basis, size, globalAlpha, 0, true);
            if (progress < 1.0F) {
               wrote |= this.renderDropColumn(buffer, matrix, cam, target, p, size, globalAlpha);
               wrote |= this.renderGroundWarning(buffer, matrix, cam, target, size * 0.58, globalAlpha, t);
            } else {
               wrote |= this.renderGroundCrack(buffer, matrix, cam, p, size, globalAlpha, localAfterGround, true);
               wrote |= this.renderLingeringAura(buffer, matrix, cam, p, size, globalAlpha, t, true);
            }

            return wrote;
         } else {
            for (int i = 0; i < this.cubes; i++) {
               float delay = i * 2.0F + this.hash(this.seed, i * 61) * 8.0F;
               float local = t - delay;
               if (!(local < 0.0F)) {
                  Vec3 base = this.rainBase(this.center, i, this.seed, this.mastered);
                  double size = this.rainCubeSize(i, this.mastered);
                  AsmodayFallingCubeClient.Judgement.GroundInfo ground = this.groundInfo(i, base, size);
                  float progress = Mth.clamp(local / this.fallDuration, 0.0F, 1.0F);
                  float localAfterGround = local - this.fallDuration;
                  if ((!ground.hasGround || !(localAfterGround > 20.0F)) && (ground.hasGround || !(localAfterGround > 400.0F))) {
                     Vec3 p = this.fallingPoint(base, i, progress, this.seed, true, this.mastered, ground, size);
                     AsmodayFallingCubeClient.Judgement.Basis basis = this.fixedCubeBasis(i);
                     wrote |= this.renderJudgementCube(buffer, matrix, cam, p, basis, size, globalAlpha, i, i % 3 == 0);
                     if (progress < 1.0F) {
                        wrote |= this.renderDropColumn(buffer, matrix, cam, base, p, size, globalAlpha * 0.34F);
                        if (i % 3 == 0) {
                           wrote |= this.renderGroundWarning(buffer, matrix, cam, base, size * 0.46, globalAlpha * 0.65F, t + i);
                        }
                     } else {
                        wrote |= this.renderGroundCrack(buffer, matrix, cam, p, size, globalAlpha, localAfterGround, false);
                        if (i % 4 == 0) {
                           wrote |= this.renderLingeringAura(buffer, matrix, cam, p, size, globalAlpha, t + i, false);
                        }
                     }
                  }
               }
            }

            return wrote;
         }
      }

      private Vec3 resolveTarget(float partial) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && this.targetId != -1) {
            Entity targetEntity = mc.level.getEntity(this.targetId);
            if (targetEntity != null) {
               double x = Mth.lerp(partial, targetEntity.xOld, targetEntity.getX());
               double y = Mth.lerp(partial, targetEntity.yOld, targetEntity.getY());
               double z = Mth.lerp(partial, targetEntity.zOld, targetEntity.getZ());
               return new Vec3(x, y + targetEntity.getBbHeight() * 0.5, z);
            }
         }

         return this.center;
      }

      private AsmodayFallingCubeClient.Judgement.GroundInfo groundInfo(int index, Vec3 base, double size) {
         AsmodayFallingCubeClient.Judgement.GroundInfo cached = this.groundCache.get(index);
         if (cached != null) {
            return cached;
         }

         Minecraft mc = Minecraft.getInstance();
         if (mc.level == null) {
            AsmodayFallingCubeClient.Judgement.GroundInfo none = new AsmodayFallingCubeClient.Judgement.GroundInfo(false, base.y);
            this.groundCache.put(index, none);
            return none;
         }

         int x = Mth.floor(base.x);
         int z = Mth.floor(base.z);
         int top = Mth.floor(base.y + 64.0);
         int bottom = Math.max(mc.level.getMinBuildHeight(), top - 96);

         for (int y = top; y >= bottom; y--) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = mc.level.getBlockState(pos);
            if (!state.isAir() && state.getFluidState().isEmpty() && state.isCollisionShapeFullBlock(mc.level, pos)) {
               AsmodayFallingCubeClient.Judgement.GroundInfo info = new AsmodayFallingCubeClient.Judgement.GroundInfo(true, y + 1.0 + size * 0.5);
               this.groundCache.put(index, info);
               return info;
            }
         }

         AsmodayFallingCubeClient.Judgement.GroundInfo none = new AsmodayFallingCubeClient.Judgement.GroundInfo(false, base.y);
         this.groundCache.put(index, none);
         return none;
      }

      private Vec3 rainBase(Vec3 center, int index, int seed, boolean mastered) {
         double radius = mastered ? 31.0 : 25.0;
         double a = (Math.PI * 2) * this.hash(seed, index * 91 + 7);
         double r = radius * Math.sqrt(this.hash(seed, index * 67 + 17));
         return center.add(Math.cos(a) * r, 0.0, Math.sin(a) * r);
      }

      private Vec3 fallingPoint(
         Vec3 base, int index, float progress, int seed, boolean rain, boolean mastered, AsmodayFallingCubeClient.Judgement.GroundInfo ground, double size
      ) {
         float slow = progress * progress * (3.0F - 2.0F * progress);
         double startHeight = rain ? (mastered ? 43.0 : 36.0) : (mastered ? 49.0 : 41.0);
         double fallbackEndY = base.y + (rain ? (mastered ? 1.55 : 1.25) : (mastered ? 2.25 : 1.75));
         double endY = ground.hasGround ? ground.centerY : fallbackEndY;
         double sway = progress >= 1.0F ? 0.0 : Math.sin(progress * Math.PI) * (rain ? 1.55 : 0.65);
         double ax = (this.hash(seed, index * 41 + 1) - 0.5) * sway;
         double az = (this.hash(seed, index * 43 + 9) - 0.5) * sway;
         return new Vec3(base.x + ax, Mth.lerp(slow, base.y + startHeight, endY), base.z + az);
      }

      private double rainCubeSize(int index, boolean mastered) {
         double base = mastered ? 4.8 : 4.0;

         return base + switch (index % 6) {
            case 0 -> mastered ? 2.0 : 1.5;
            case 1 -> mastered ? 1.4 : 1.0;
            case 2 -> 0.7;
            default -> 0.0;
         };
      }

      private AsmodayFallingCubeClient.Judgement.Basis fixedCubeBasis(int index) {
         int variant = index % 4;

         return switch (variant) {
            case 0 -> new AsmodayFallingCubeClient.Judgement.Basis(new Vec3(0.0, 0.0, 1.0), new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0));
            case 1 -> new AsmodayFallingCubeClient.Judgement.Basis(new Vec3(1.0, 0.0, 0.0), new Vec3(0.0, 0.0, -1.0), new Vec3(0.0, 1.0, 0.0));
            case 2 -> new AsmodayFallingCubeClient.Judgement.Basis(new Vec3(0.0, 0.0, -1.0), new Vec3(-1.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0));
            default -> new AsmodayFallingCubeClient.Judgement.Basis(new Vec3(-1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), new Vec3(0.0, 1.0, 0.0));
         };
      }

      private boolean renderJudgementCube(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         AsmodayFallingCubeClient.Judgement.Basis basis,
         double size,
         float alpha,
         int index,
         boolean crown
      ) {
         boolean wrote = false;
         wrote |= this.renderSolidCube(buffer, matrix, cam, center, basis, size, alpha, index);
         wrote |= this.renderWireCube(buffer, matrix, cam, center, basis, size * 1.015, alpha, true);
         wrote |= this.renderFaceGlyphs(buffer, matrix, cam, center, basis, size * 1.018, alpha, index);
         if (crown) {
            wrote |= this.renderWireCube(buffer, matrix, cam, center, basis, size * 1.12, alpha * 0.26F, false);
         }

         return wrote;
      }

      private boolean renderDropColumn(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 target, Vec3 cube, double size, float alpha) {
         Vec3 top = cube.add(0.0, size * 0.62, 0.0);
         Vec3 bottom = target.add(0.0, 0.12, 0.0);
         boolean wrote = false;
         wrote |= this.renderBeam(buffer, matrix, cam, top, bottom, size * 0.012, alpha * 0.4F, true);
         wrote |= this.renderBeam(buffer, matrix, cam, top.add(size * 0.14, 0.0, 0.0), bottom.add(size * 0.14, 0.0, 0.0), size * 0.007, alpha * 0.2F, false);
         return wrote
            | this.renderBeam(buffer, matrix, cam, top.add(-size * 0.14, 0.0, 0.0), bottom.add(-size * 0.14, 0.0, 0.0), size * 0.007, alpha * 0.2F, false);
      }

      private boolean renderGroundWarning(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double radius, float alpha, float t) {
         double pulse = radius * (0.84 + Math.sin(t * 0.12) * 0.05);
         Vec3 p = new Vec3(center.x, center.y + 0.07, center.z);
         boolean wrote = false;

         for (int i = 0; i < 4; i++) {
            double a0 = (Math.PI / 2) * i + (Math.PI / 4);
            double a1 = (Math.PI / 2) * (i + 1) + (Math.PI / 4);
            Vec3 v0 = p.add(Math.cos(a0) * pulse, 0.0, Math.sin(a0) * pulse);
            Vec3 v1 = p.add(Math.cos(a1) * pulse, 0.0, Math.sin(a1) * pulse);
            wrote |= this.renderBeam(buffer, matrix, cam, v0, v1, 0.034, alpha * 0.66F, true);
         }

         return wrote;
      }

      private boolean renderGroundCrack(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double size, float alpha, float impactAge, boolean single) {
         if (!(impactAge < 0.0F) && !(impactAge > 20.0F)) {
            boolean wrote = false;
            float burst = Mth.clamp(1.0F - impactAge / 20.0F, 0.0F, 1.0F);
            double baseRadius = size * (single ? 1.2 : 0.78);
            double shockRadius = baseRadius + (1.0F - burst) * size * (single ? 1.25 : 0.76);
            Vec3 ground = new Vec3(center.x, center.y - size * 0.47 + 0.045, center.z);

            for (int i = 0; i < 16; i++) {
               double a = (Math.PI * 2) * i / 16.0;
               Vec3 dir = new Vec3(Math.cos(a), 0.0, Math.sin(a));
               Vec3 p0 = ground.add(dir.scale(shockRadius * 0.1));
               Vec3 p1 = ground.add(dir.scale(shockRadius));
               wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, single ? 0.08 : 0.052, alpha * burst * 0.95F, true);
            }

            for (int i = 0; i < 4; i++) {
               double a0 = (Math.PI / 2) * i + (Math.PI / 4);
               double a1 = (Math.PI / 2) * (i + 1) + (Math.PI / 4);
               Vec3 p0 = ground.add(Math.cos(a0) * shockRadius, 0.0, Math.sin(a0) * shockRadius);
               Vec3 p1 = ground.add(Math.cos(a1) * shockRadius, 0.0, Math.sin(a1) * shockRadius);
               wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, single ? 0.105 : 0.066, alpha * burst, true);
            }

            return wrote;
         } else {
            return false;
         }
      }

      private boolean renderLingeringAura(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double size, float alpha, float t, boolean single) {
         boolean wrote = false;
         float pulse = 0.5F + 0.15F * Mth.sin(t * 0.16F);
         Vec3 low = center.add(0.0, -size * 0.42, 0.0);

         for (int i = 0; i < 4; i++) {
            double a0 = (Math.PI / 2) * i + (Math.PI / 4);
            double a1 = (Math.PI / 2) * (i + 1) + (Math.PI / 4);
            double r = size * (single ? 0.7 : 0.44);
            Vec3 p0 = low.add(Math.cos(a0) * r, 0.0, Math.sin(a0) * r);
            Vec3 p1 = low.add(Math.cos(a1) * r, 0.0, Math.sin(a1) * r);
            wrote |= this.renderBeam(buffer, matrix, cam, p0, p1, single ? 0.038 : 0.024, alpha * pulse, true);
         }

         return wrote;
      }

      private boolean renderSolidCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayFallingCubeClient.Judgement.Basis basis, double size, float alpha, int index
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
         int cr = 34 + index % 3 * 5;
         int cg = 0;
         int cb = 6;
         boolean wrote = this.quad(buffer, matrix, cam, p001, p101, p111, p011, cr + 10, cg, cb + 3, alpha);
         wrote |= this.quad(buffer, matrix, cam, p100, p000, p010, p110, cr - 8, cg, cb, alpha);
         wrote |= this.quad(buffer, matrix, cam, p000, p001, p011, p010, cr - 4, cg, cb, alpha);
         wrote |= this.quad(buffer, matrix, cam, p101, p100, p110, p111, cr + 6, cg, cb + 2, alpha);
         wrote |= this.quad(buffer, matrix, cam, p010, p011, p111, p110, cr + 14, cg, cb + 4, alpha);
         return wrote | this.quad(buffer, matrix, cam, p000, p100, p101, p001, Math.max(6, cr - 14), cg, Math.max(2, cb - 2), alpha);
      }

      private boolean renderFaceGlyphs(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayFallingCubeClient.Judgement.Basis basis, double size, float alpha, int index
      ) {
         boolean wrote = false;
         float a = alpha * 0.82F;
         wrote |= this.renderGlyph(buffer, matrix, cam, center.add(basis.forward.scale(size * 0.512)), basis.right, basis.up, size, a, index);
         wrote |= this.renderGlyph(
            buffer, matrix, cam, center.add(basis.right.scale(size * 0.512)), basis.forward.scale(-1.0), basis.up, size, a * 0.68F, index + 13
         );
         return wrote
            | this.renderGlyph(buffer, matrix, cam, center.subtract(basis.right.scale(size * 0.512)), basis.forward, basis.up, size, a * 0.62F, index + 27);
      }

      private boolean renderGlyph(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 faceCenter, Vec3 r, Vec3 u, double size, float alpha, int index) {
         double outer = size * 0.19;
         double inner = size * 0.078;
         double mark = size * 0.135;
         double w = size * 0.011;
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
               w * 0.38,
               alpha * 0.62F,
               true
            );
         }

         return wrote;
      }

      private boolean renderWireCube(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, AsmodayFallingCubeClient.Judgement.Basis basis, double size, float alpha, boolean hot
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
         double w = this.shifted ? size * 0.009 : size * 0.011;
         float glow = hot ? alpha : alpha * 0.4F;
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
         boolean wrote = this.renderBeam(buffer, matrix, cam, a, b, w * 3.5, alpha * 0.26F, false);
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
               buffer, matrix, cam, start.add(side), end.add(side.scale(0.34)), end.subtract(side.scale(0.34)), start.subtract(side), 255, 24, 18, alpha
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
                  72,
                  42,
                  alpha * 0.86F
               );
         } else {
            boolean wrote = this.quad(
               buffer, matrix, cam, start.add(side), end.add(side.scale(0.34)), end.subtract(side.scale(0.34)), start.subtract(side), 160, 0, 12, alpha * 0.86F
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
                  40,
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

      private float hash(int seed, int index) {
         int x = seed ^ index * 1103515245;
         x ^= x << 13;
         x ^= x >>> 17;
         x ^= x << 5;
         return (x & 1023) / 1023.0F;
      }

      private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
      }

      private record GroundInfo(boolean hasGround, double centerY) {
      }
   }
}
