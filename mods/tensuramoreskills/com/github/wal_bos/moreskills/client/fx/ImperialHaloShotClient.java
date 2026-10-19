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

public final class ImperialHaloShotClient {
   private static final Map<Integer, ImperialHaloShotClient.Volley> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;

   private ImperialHaloShotClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ImperialHaloShotClient::clientTick);
         NeoForge.EVENT_BUS.addListener(ImperialHaloShotClient::renderLevel);
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
      int rings,
      int seed,
      int duration
   ) {
      init();
      ACTIVE.put(
         id,
         new ImperialHaloShotClient.Volley(
            casterId,
            targetId,
            new Vec3(casterX, casterY, casterZ),
            new Vec3(targetX, targetY, targetZ),
            Mth.clamp(rings, 5, 9),
            seed,
            Mth.clamp(duration, 88, 150)
         )
      );
   }

   private static void clientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<Entry<Integer, ImperialHaloShotClient.Volley>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            ImperialHaloShotClient.Volley volley = iterator.next().getValue();
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
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            boolean wrote = false;

            for (ImperialHaloShotClient.Volley volley : ACTIVE.values()) {
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
      private static final int FIRE_SPACING = 5;
      private static final int BASE_FIRE_START = 12;
      private static final int TRAIL_POINTS = 24;
      private static final int MISSILE_LIFE = 74;
      private final int casterId;
      private final int targetId;
      private final Vec3 fallbackCaster;
      private final Vec3 fallbackTarget;
      private final int rings;
      private final int seed;
      private final int life;
      private final ImperialHaloShotClient.Volley.Missile[] missiles;
      private int age;

      private Volley(int casterId, int targetId, Vec3 fallbackCaster, Vec3 fallbackTarget, int rings, int seed, int life) {
         this.casterId = casterId;
         this.targetId = targetId;
         this.fallbackCaster = fallbackCaster;
         this.fallbackTarget = fallbackTarget;
         this.rings = rings;
         this.seed = seed;
         this.life = life;
         this.missiles = new ImperialHaloShotClient.Volley.Missile[rings];

         for (int i = 0; i < rings; i++) {
            this.missiles[i] = new ImperialHaloShotClient.Volley.Missile();
         }
      }

      private void tick(Minecraft mc) {
         this.age++;
         Vec3 caster = this.resolveCaster(mc, 1.0F);
         Vec3 target = this.resolveTarget(mc, 1.0F);
         ImperialHaloShotClient.Volley.Basis basis = this.basis(caster, target);
         float t = this.age;

         for (int i = 0; i < this.rings; i++) {
            ImperialHaloShotClient.Volley.Missile missile = this.missiles[i];
            int fireStart = 12 + i * 5;
            if (this.age >= fireStart) {
               Vec3 socket = this.socket(caster, basis, t, i);
               Vec3 liveTarget = this.homingTarget(target, basis, t, i);
               if (!missile.active) {
                  missile.active = true;
                  missile.age = 0;
                  missile.pos = socket;
                  missile.prevPos = socket;
                  missile.vel = basis.forward
                     .scale(0.5)
                     .add(basis.right.scale((this.hash(this.seed, i + 41) - 0.5F) * 0.42))
                     .add(basis.up.scale((this.hash(this.seed, i + 83) - 0.5F) * 0.36));

                  for (int j = 0; j < 24; j++) {
                     missile.trail[j] = socket;
                  }
               } else {
                  missile.age++;
                  missile.prevPos = missile.pos;
                  if (missile.burstAge > 0) {
                     missile.burstAge++;
                     missile.pos = missile.pos.add(missile.vel.scale(0.2));
                     missile.vel = missile.vel.scale(0.78);
                     missile.pushTrail(missile.pos);
                  } else if (missile.age > 74) {
                     missile.burstAge = 1;
                     missile.missed = true;
                     missile.impactPos = missile.pos;
                     missile.vel = missile.vel.scale(0.18);
                     missile.pushTrail(missile.pos);
                  } else {
                     Vec3 toTarget = liveTarget.subtract(missile.pos);
                     double dist = toTarget.length();
                     if (dist < 0.42 && missile.age > 9) {
                        missile.burstAge = 1;
                        missile.impactPos = liveTarget;
                        missile.pos = liveTarget;
                        missile.prevPos = missile.pos;
                        missile.vel = basis.forward.scale(0.035);
                        missile.pushTrail(missile.pos);
                     } else {
                        if (dist > 0.001) {
                           Vec3 wanted = toTarget.normalize();
                           double speed = Mth.clamp(0.66 + missile.age * 0.026, 0.66, 1.72);
                           double blend = Mth.clamp(0.07 + missile.age * 0.0032, 0.07, 0.23);
                           Vec3 swirl = basis.right
                              .scale(Math.sin((this.age + i * 13.0) * 0.37) * 0.052)
                              .add(basis.up.scale(Math.cos((this.age + i * 17.0) * 0.33) * 0.045));
                           Vec3 desiredVelocity = wanted.scale(speed).add(swirl);
                           missile.vel = missile.vel.scale(1.0 - blend).add(desiredVelocity.scale(blend));
                           if (missile.vel.length() > speed) {
                              missile.vel = missile.vel.normalize().scale(speed);
                           }

                           missile.pos = missile.pos.add(missile.vel);
                        }

                        missile.pushTrail(missile.pos);
                     }
                  }
               }
            }
         }
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         Minecraft mc = Minecraft.getInstance();
         Vec3 caster = this.resolveCaster(mc, partial);
         Vec3 target = this.resolveTarget(mc, partial);
         ImperialHaloShotClient.Volley.Basis basis = this.basis(caster, target);
         float t = this.age + partial;
         float alpha = Mth.clamp(t / 6.0F, 0.0F, 1.0F) * Mth.clamp((this.life - t) / 16.0F, 0.0F, 1.0F);
         if (alpha <= 0.002F) {
            return false;
         }

         boolean wrote = false;
         wrote |= this.renderSolarField(buffer, matrix, cam, caster, target, basis, alpha, t);
         wrote |= this.renderGuideLines(buffer, matrix, cam, caster, target, basis, alpha, t);
         wrote |= this.renderTargetWarning(buffer, matrix, cam, target, basis, alpha, t);

         for (int i = 0; i < this.rings; i++) {
            int fireStart = 12 + i * 5;
            ImperialHaloShotClient.Volley.Missile missile = this.missiles[i];
            Vec3 socket = this.socket(caster, basis, t, i);
            float preCharge = Mth.clamp((t - fireStart + 12.0F) / 12.0F, 0.0F, 1.0F);
            float ringFade = 1.0F - Mth.clamp((t - fireStart) / 28.0F, 0.0F, 1.0F) * 0.76F;
            if (ringFade > 0.05F) {
               wrote |= this.renderRing(buffer, matrix, cam, socket, basis, 0.3 + preCharge * 0.28, 0.04, alpha * ringFade, i);
               wrote |= this.renderRing(
                  buffer, matrix, cam, socket.add(basis.forward.scale(0.035)), basis, 0.48 + preCharge * 0.44, 0.016, alpha * ringFade * 0.44F, i + 23
               );
               wrote |= this.renderSunStar(buffer, matrix, cam, socket, basis, 0.38 + preCharge * 0.26, alpha * ringFade * 0.8F, i);
               wrote |= this.renderAimChevron(buffer, matrix, cam, socket, this.homingTarget(target, basis, t, i), basis, alpha * ringFade * preCharge, i);
            }

            Vec3 liveTarget = this.homingTarget(target, basis, t, i);
            if (t < fireStart) {
               float wait = Mth.clamp((fireStart - t) / 12.0F, 0.0F, 1.0F);
               wrote |= this.renderLance(buffer, matrix, cam, socket, liveTarget, 0.006, alpha * wait * 0.15F, false);
               wrote |= this.renderLance(buffer, matrix, cam, socket, liveTarget, 0.018, alpha * wait * 0.055F, false);
            } else if (missile.active) {
               Vec3 head = missile.prevPos.lerp(missile.pos, partial);
               float missileLifeFade = 1.0F - Mth.clamp((missile.age + partial - 74.0F + 12.0F) / 12.0F, 0.0F, 1.0F);
               float missileAlpha = alpha * missileLifeFade;

               for (int j = 0; j < 23; j++) {
                  Vec3 a = missile.trail[j];
                  Vec3 b = missile.trail[j + 1];
                  if (a != null && b != null) {
                     float fade = 1.0F - j / 24.0F;
                     double width = 0.048 * fade + 0.006;
                     wrote |= this.renderLance(buffer, matrix, cam, b, a, width * 2.15, missileAlpha * fade * 0.125F, false);
                     wrote |= this.renderLance(buffer, matrix, cam, b, a, width, missileAlpha * fade * 0.88F, false);
                     wrote |= this.renderLance(buffer, matrix, cam, b, a, width * 0.34, missileAlpha * fade, true);
                  }
               }

               Vec3 dir = missile.vel.lengthSqr() > 0.001 ? missile.vel.normalize() : basis.forward;
               Vec3 tail = head.subtract(dir.scale(1.05));
               Vec3 longTail = head.subtract(dir.scale(3.1));
               Vec3 flareTail = head.subtract(dir.scale(4.8));
               wrote |= this.renderLance(buffer, matrix, cam, flareTail, head, 0.094, missileAlpha * 0.105F, false);
               wrote |= this.renderLance(buffer, matrix, cam, longTail, head, 0.03, missileAlpha * 0.48F, false);
               wrote |= this.renderLance(buffer, matrix, cam, tail, head, 0.06, missileAlpha, false);
               wrote |= this.renderLance(buffer, matrix, cam, tail, head, 0.019, missileAlpha, true);
               wrote |= this.renderNeedleHead(buffer, matrix, cam, head, dir, basis, 0.32, missileAlpha);
               wrote |= this.renderSunStar(buffer, matrix, cam, head, basis, 0.24 + Mth.sin((t + i) * 0.8F) * 0.035, missileAlpha * 0.74F, i + 31);
               if (missile.burstAge > 0 && missile.impactPos != null) {
                  float burst = Mth.clamp(1.0F - (missile.burstAge + partial) / 14.0F, 0.0F, 1.0F);
                  double size = missile.missed ? 0.38 + (1.0F - burst) * 0.38 : 0.72 + (1.0F - burst) * 0.88;
                  float burstAlpha = missile.missed ? missileAlpha * burst * 0.42F : missileAlpha * burst;
                  wrote |= this.renderImpact(buffer, matrix, cam, missile.impactPos, basis.right, basis.up, size, burstAlpha);
                  wrote |= this.renderRing(buffer, matrix, cam, missile.impactPos, basis, size * 0.86, 0.03, burstAlpha * 0.95F, i + 19);
                  wrote |= this.renderRing(
                     buffer, matrix, cam, missile.impactPos.add(basis.forward.scale(0.04)), basis, size * 1.24, 0.018, burstAlpha * 0.55F, i + 43
                  );
                  wrote |= this.renderSunStar(buffer, matrix, cam, missile.impactPos, basis, size * 0.74, burstAlpha, i + 59);
               }
            }
         }

         return wrote;
      }

      private Vec3 resolveCaster(Minecraft mc, float partial) {
         Vec3 caster = this.fallbackCaster;
         if (mc.level != null) {
            Entity casterEntity = mc.level.getEntity(this.casterId);
            if (casterEntity != null) {
               caster = this.lerpEntity(casterEntity, partial).add(0.0, casterEntity.getBbHeight() * 0.62, 0.0);
            }
         }

         return caster;
      }

      private Vec3 resolveTarget(Minecraft mc, float partial) {
         Vec3 target = this.fallbackTarget;
         if (mc.level != null) {
            Entity targetEntity = mc.level.getEntity(this.targetId);
            if (targetEntity != null) {
               target = this.lerpEntity(targetEntity, partial).add(0.0, targetEntity.getBbHeight() * 0.55, 0.0);
            }
         }

         return target;
      }

      private Vec3 lerpEntity(Entity entity, float partial) {
         double x = Mth.lerp(partial, entity.xOld, entity.getX());
         double y = Mth.lerp(partial, entity.yOld, entity.getY());
         double z = Mth.lerp(partial, entity.zOld, entity.getZ());
         return new Vec3(x, y, z);
      }

      private ImperialHaloShotClient.Volley.Basis basis(Vec3 caster, Vec3 target) {
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
         return new ImperialHaloShotClient.Volley.Basis(forward, right, up);
      }

      private Vec3 socket(Vec3 caster, ImperialHaloShotClient.Volley.Basis basis, float t, int i) {
         float base = (float)((Math.PI * 2) * i / this.rings);
         float wobble = this.hash(this.seed, i) * 0.7F;
         float spin = t * 0.145F * ((i & 1) == 0 ? 1.0F : -1.0F);
         float a = base + wobble + spin;
         double orbit = 1.88 + i % 4 * 0.35;
         double yWave = Math.sin(t * 0.18 + i * 1.41) * 0.42;
         return caster.add(basis.right.scale(Math.cos(a) * orbit))
            .add(basis.up.scale(Math.sin(a) * 0.92 + yWave))
            .subtract(basis.forward.scale(0.32 + i % 3 * 0.12));
      }

      private Vec3 homingTarget(Vec3 target, ImperialHaloShotClient.Volley.Basis basis, float t, int i) {
         double r = 0.4 + i % 3 * 0.12;
         return target.add(basis.right.scale(Math.sin(t * 0.075 + i * 1.8) * r)).add(basis.up.scale(Math.cos(t * 0.085 + i * 1.3) * r * 0.72));
      }

      private float hash(int seed, int index) {
         int x = seed ^ index * 1103515245;
         x ^= x << 13;
         x ^= x >>> 17;
         x ^= x << 5;
         return (x & 1023) / 1023.0F;
      }

      private boolean renderSolarField(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 caster, Vec3 target, ImperialHaloShotClient.Volley.Basis basis, float alpha, float t
      ) {
         boolean wrote = false;
         Vec3 center = caster.lerp(target, 0.24).add(basis.up.scale(Math.sin(t * 0.08) * 0.22));

         for (int i = 0; i < 6; i++) {
            double a = (Math.PI * 2) * i / 6.0 + t * 0.045;
            double r = 1.15 + i * 0.1;
            Vec3 p0 = center.add(basis.right.scale(Math.cos(a) * r)).add(basis.up.scale(Math.sin(a) * r * 0.46));
            Vec3 p1 = center.add(basis.right.scale(Math.cos(a + 0.34) * (r + 0.8)))
               .add(basis.up.scale(Math.sin(a + 0.34) * (r + 0.8) * 0.46))
               .add(basis.forward.scale(0.4));
            wrote |= this.renderLance(buffer, matrix, cam, p0, p1, 0.01, alpha * 0.14F, false);
         }

         return wrote;
      }

      private boolean renderGuideLines(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 caster, Vec3 target, ImperialHaloShotClient.Volley.Basis basis, float alpha, float t
      ) {
         boolean wrote = false;

         for (int i = 0; i < 6; i++) {
            double side = (i - 2.5) * 0.24;
            double wave = Math.sin(t * 0.15 + i * 1.7) * 0.16;
            Vec3 a = caster.add(basis.right.scale(side)).add(basis.up.scale(wave));
            Vec3 b = target.add(basis.right.scale(-side * 0.22)).add(basis.up.scale(-wave * 0.22));
            wrote |= this.renderLance(buffer, matrix, cam, a, b, 0.004, alpha * 0.13F, i % 2 == 0);
         }

         return wrote;
      }

      private boolean renderTargetWarning(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 target, ImperialHaloShotClient.Volley.Basis basis, float alpha, float t
      ) {
         boolean wrote = false;
         double pulse = 0.7 + Math.sin(t * 0.6) * 0.13;
         float a = alpha * (0.5F + 0.24F * Mth.sin(t * 0.6F));
         wrote |= this.renderRing(buffer, matrix, cam, target, basis, pulse, 0.02, a, 7);
         wrote |= this.renderRing(buffer, matrix, cam, target.add(basis.forward.scale(0.1)), basis, pulse * 0.6, 0.015, a * 0.82F, 11);
         wrote |= this.renderRing(buffer, matrix, cam, target.subtract(basis.forward.scale(0.08)), basis, pulse * 1.28, 0.01, a * 0.3F, 15);
         wrote |= this.renderSunStar(buffer, matrix, cam, target, basis, pulse * 0.8, a * 0.62F, 71);
         Vec3 p0 = target.add(basis.right.scale(pulse));
         Vec3 p1 = target.subtract(basis.right.scale(pulse));
         Vec3 p2 = target.add(basis.up.scale(pulse));
         Vec3 p3 = target.subtract(basis.up.scale(pulse));
         wrote |= this.renderLance(buffer, matrix, cam, p0, p1, 0.005, a * 0.88F, true);
         return wrote | this.renderLance(buffer, matrix, cam, p2, p3, 0.005, a * 0.88F, true);
      }

      private boolean renderAimChevron(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 socket, Vec3 target, ImperialHaloShotClient.Volley.Basis basis, float alpha, int index
      ) {
         Vec3 dir = target.subtract(socket);
         if (dir.lengthSqr() < 0.001) {
            return false;
         }

         dir = dir.normalize();
         double d = 0.62 + index % 3 * 0.18;
         Vec3 center = socket.add(dir.scale(d));
         Vec3 back = center.subtract(dir.scale(0.28));
         Vec3 left = back.add(basis.right.scale(0.14));
         Vec3 right = back.subtract(basis.right.scale(0.14));
         Vec3 high = back.add(basis.up.scale(0.13));
         Vec3 low = back.subtract(basis.up.scale(0.13));
         boolean wrote = this.renderLance(buffer, matrix, cam, left, center, 0.011, alpha, true);
         wrote |= this.renderLance(buffer, matrix, cam, right, center, 0.011, alpha, true);
         wrote |= this.renderLance(buffer, matrix, cam, high, center, 0.008, alpha * 0.72F, false);
         return wrote | this.renderLance(buffer, matrix, cam, low, center, 0.008, alpha * 0.72F, false);
      }

      private boolean renderRing(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 cam,
         Vec3 center,
         ImperialHaloShotClient.Volley.Basis basis,
         double radius,
         double width,
         float alpha,
         int index
      ) {
         boolean wrote = false;
         int segments = 42;

         for (int i = 0; i < segments; i++) {
            double a0 = (Math.PI * 2) * i / segments;
            double a1 = (Math.PI * 2) * (i + 1) / segments;
            Vec3 outer0 = center.add(basis.right.scale(Math.cos(a0) * (radius + width))).add(basis.up.scale(Math.sin(a0) * (radius + width)));
            Vec3 outer1 = center.add(basis.right.scale(Math.cos(a1) * (radius + width))).add(basis.up.scale(Math.sin(a1) * (radius + width)));
            Vec3 inner1 = center.add(basis.right.scale(Math.cos(a1) * (radius - width))).add(basis.up.scale(Math.sin(a1) * (radius - width)));
            Vec3 inner0 = center.add(basis.right.scale(Math.cos(a0) * (radius - width))).add(basis.up.scale(Math.sin(a0) * (radius - width)));
            if ((i + index) % 6 == 0) {
               wrote |= this.quad(buffer, matrix, cam, outer0, outer1, inner1, inner0, 255, 174, 36, alpha * 0.92F);
            } else {
               wrote |= this.quad(buffer, matrix, cam, outer0, outer1, inner1, inner0, 255, 252, 218, alpha * 0.94F);
            }

            if (i % 7 == 0) {
               Vec3 spike0 = center.add(basis.right.scale(Math.cos(a0) * (radius + width * 2.0)))
                  .add(basis.up.scale(Math.sin(a0) * (radius + width * 2.0)))
                  .add(basis.forward.scale(0.025));
               Vec3 spike1 = center.add(basis.right.scale(Math.cos(a1) * (radius + width * 2.0)))
                  .add(basis.up.scale(Math.sin(a1) * (radius + width * 2.0)))
                  .add(basis.forward.scale(0.025));
               Vec3 spike2 = center.add(basis.right.scale(Math.cos(a1) * (radius + width * 5.2)))
                  .add(basis.up.scale(Math.sin(a1) * (radius + width * 5.2)))
                  .add(basis.forward.scale(0.025));
               Vec3 spike3 = center.add(basis.right.scale(Math.cos(a0) * (radius + width * 5.2)))
                  .add(basis.up.scale(Math.sin(a0) * (radius + width * 5.2)))
                  .add(basis.forward.scale(0.025));
               wrote |= this.quad(buffer, matrix, cam, spike0, spike1, spike2, spike3, 255, 255, 245, alpha * 0.42F);
            }
         }

         return wrote;
      }

      private boolean renderSunStar(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, ImperialHaloShotClient.Volley.Basis basis, double size, float alpha, int index
      ) {
         boolean wrote = false;
         int rays = 8;

         for (int i = 0; i < rays; i++) {
            double a = (Math.PI * 2) * i / rays + index * 0.17;
            Vec3 dir = basis.right.scale(Math.cos(a)).add(basis.up.scale(Math.sin(a)));
            if (!(dir.lengthSqr() < 0.001)) {
               dir = dir.normalize();
               Vec3 inner = center.add(dir.scale(size * 0.22));
               Vec3 outer = center.add(dir.scale(size));
               wrote |= this.renderLance(buffer, matrix, cam, inner, outer, 0.014, alpha * 0.62F, true);
               wrote |= this.renderLance(buffer, matrix, cam, inner, outer, 0.038, alpha * 0.16F, false);
            }
         }

         return wrote;
      }

      private boolean renderNeedleHead(
         BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 head, Vec3 dir, ImperialHaloShotClient.Volley.Basis basis, double size, float alpha
      ) {
         Vec3 tip = head.add(dir.scale(size * 0.82));
         Vec3 base = head.subtract(dir.scale(size));
         Vec3 l = base.add(basis.right.scale(size * 0.26));
         Vec3 r = base.subtract(basis.right.scale(size * 0.26));
         Vec3 u = base.add(basis.up.scale(size * 0.26));
         Vec3 d = base.subtract(basis.up.scale(size * 0.26));
         boolean wrote = this.tri(buffer, matrix, cam, tip, l, u, 255, 255, 252, alpha);
         wrote |= this.tri(buffer, matrix, cam, tip, u, r, 255, 244, 148, alpha * 0.96F);
         wrote |= this.tri(buffer, matrix, cam, tip, r, d, 255, 255, 252, alpha);
         wrote |= this.tri(buffer, matrix, cam, tip, d, l, 255, 184, 38, alpha * 0.9F);
         return wrote | this.renderRing(buffer, matrix, cam, head, basis, size * 0.72, 0.01, alpha * 0.34F, 91);
      }

      private boolean renderLance(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, double width, float alpha, boolean core) {
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
         boolean wrote;
         if (core) {
            wrote = this.quad(
               buffer, matrix, cam, start.add(side), end.add(side.scale(0.34)), end.subtract(side.scale(0.34)), start.subtract(side), 255, 255, 252, alpha
            );
            wrote |= this.quad(
               buffer,
               matrix,
               cam,
               start.add(side2),
               end.add(side2.scale(0.34)),
               end.subtract(side2.scale(0.34)),
               start.subtract(side2),
               255,
               255,
               232,
               alpha * 0.9F
            );
         } else {
            wrote = this.quad(
               buffer,
               matrix,
               cam,
               start.add(side),
               end.add(side.scale(0.34)),
               end.subtract(side.scale(0.34)),
               start.subtract(side),
               255,
               214,
               62,
               alpha * 0.78F
            );
            wrote |= this.quad(
               buffer,
               matrix,
               cam,
               start.add(side2),
               end.add(side2.scale(0.34)),
               end.subtract(side2.scale(0.34)),
               start.subtract(side2),
               255,
               144,
               24,
               alpha * 0.45F
            );
         }

         return wrote;
      }

      private boolean renderImpact(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, Vec3 right, Vec3 up, double size, float alpha) {
         Vec3 a = center.add(right.scale(size));
         Vec3 b = center.add(up.scale(size * 0.22));
         Vec3 c = center.subtract(right.scale(size));
         Vec3 d = center.subtract(up.scale(size * 0.22));
         boolean wrote = this.quad(buffer, matrix, cam, a, b, c, d, 255, 255, 232, alpha * 0.92F);
         Vec3 e = center.add(up.scale(size));
         Vec3 f = center.add(right.scale(size * 0.22));
         Vec3 g = center.subtract(up.scale(size));
         Vec3 h = center.subtract(right.scale(size * 0.22));
         wrote |= this.quad(buffer, matrix, cam, e, f, g, h, 255, 176, 34, alpha * 0.78F);
         Vec3 i = center.add(right.scale(size * 0.72)).add(up.scale(size * 0.72));
         Vec3 j = center.subtract(right.scale(size * 0.72)).add(up.scale(size * 0.72));
         Vec3 k = center.subtract(right.scale(size * 0.72)).subtract(up.scale(size * 0.72));
         Vec3 l = center.add(right.scale(size * 0.72)).subtract(up.scale(size * 0.72));
         return wrote | this.quad(buffer, matrix, cam, i, j, k, l, 255, 244, 118, alpha * 0.2F);
      }

      private boolean tri(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
         int ia = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         if (ia <= 0) {
            return false;
         }

         this.vertex(buffer, matrix, cam, a, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, b, r, g, bl, ia);
         this.vertex(buffer, matrix, cam, c, r, g, bl, ia);
         return true;
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

      private static final class Missile {
         private boolean active;
         private boolean missed;
         private int age;
         private int burstAge;
         private Vec3 pos = Vec3.ZERO;
         private Vec3 prevPos = Vec3.ZERO;
         private Vec3 vel = Vec3.ZERO;
         private Vec3 impactPos;
         private final Vec3[] trail = new Vec3[24];

         private void pushTrail(Vec3 point) {
            for (int i = this.trail.length - 1; i > 0; i--) {
               this.trail[i] = this.trail[i - 1];
            }

            this.trail[0] = point;
         }
      }
   }
}
