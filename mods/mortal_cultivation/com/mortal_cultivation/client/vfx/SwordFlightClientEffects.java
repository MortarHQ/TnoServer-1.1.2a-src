package com.mortal_cultivation.client.vfx;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mortal_cultivation.entity.FlyingSwordEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFov;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME, value = Dist.CLIENT)
public final class SwordFlightClientEffects {
   private static final Vec3 UP = new Vec3(0.0, 1.0, 0.0);
   private static final int TRAIL_TICKS = 12;
   private static final int MAX_TRACKED_SWORDS = 24;
   private static final int MAX_STREAKS_PER_SWORD = 32;
   private static final double EFFECT_RANGE = 64.0;
   private static final double TELEPORT_DISTANCE_SQR = 64.0;
   private static final float MAX_FOV_INCREASE = 18.0F;
   private static final RenderType AIRFLOW = RenderType.create(
      "mortal_cultivation_sword_airflow",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      16384,
      false,
      true,
      CompositeState.builder()
         .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
         .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
         .setCullState(RenderStateShard.NO_CULL)
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .setOutputState(RenderStateShard.PARTICLES_TARGET)
         .createCompositeState(false)
   );
   private static final Map<Integer, SwordFlightClientEffects.Wake> WAKES = new HashMap<>();
   private static ClientLevel currentLevel;
   private static int clock;
   private static float previousFov;
   private static float currentFov;

   private SwordFlightClientEffects() {
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != currentLevel) {
         clear();
         currentLevel = minecraft.level;
      }

      if (currentLevel != null && minecraft.player != null && !minecraft.isPaused()) {
         clock++;
         WAKES.values().forEach(wake -> wake.prune(clock));
         WAKES.values().removeIf(wake -> clock - wake.lastSeenTick > 20);
         FlyingSwordEntity riddenSword = minecraft.player.getVehicle() instanceof FlyingSwordEntity sword ? sword : null;
         if (riddenSword != null) {
            updateWake(riddenSword, minecraft);
         }

         Vec3 observer = minecraft.getCameraEntity() == null ? minecraft.player.position() : minecraft.getCameraEntity().position();
         AABB nearby = new AABB(observer, observer).inflate(64.0);

         for (FlyingSwordEntity sword : currentLevel.getEntitiesOfClass(
            FlyingSwordEntity.class, nearby, swordx -> !swordx.isRemoved() && (swordx.isVehicle() || swordx.isReturningToOwner())
         )) {
            if (sword != riddenSword) {
               updateWake(sword, minecraft);
            }
         }

         previousFov = currentFov;
         float target = riddenSword == null ? 0.0F : flightStrength(riddenSword.getDeltaMovement().length());
         currentFov = Mth.lerp(target > currentFov ? 0.22F : 0.15F, currentFov, target);
         if (target == 0.0F && currentFov < 0.001F) {
            currentFov = 0.0F;
         }
      }
   }

   private static void updateWake(FlyingSwordEntity sword, Minecraft minecraft) {
      SwordFlightClientEffects.Wake wake = WAKES.get(sword.getId());
      if (wake == null || wake.sword != sword) {
         if (wake == null && WAKES.size() >= 24) {
            if (minecraft.player == null || minecraft.player.getVehicle() != sword) {
               return;
            }

            SwordFlightClientEffects.Wake furthest = null;
            double furthestDistance = -1.0;

            for (SwordFlightClientEffects.Wake candidate : WAKES.values()) {
               double distance = candidate.lastPosition.distanceToSqr(sword.position());
               if (distance > furthestDistance) {
                  furthest = candidate;
                  furthestDistance = distance;
               }
            }

            if (furthest != null) {
               WAKES.remove(furthest.sword.getId());
            }
         }

         wake = new SwordFlightClientEffects.Wake(sword);
         WAKES.put(sword.getId(), wake);
      }

      wake.update(minecraft, clock);
   }

   @SubscribeEvent
   public static void onComputeFov(ComputeFov event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (event.usedConfiguredFov()
         && minecraft.player != null
         && event.getCamera().getEntity() == minecraft.player
         && !minecraft.player.isScoping()
         && (!(previousFov <= 0.0F) || !(currentFov <= 0.0F))) {
         float partialTick = Mth.clamp((float)event.getPartialTick(), 0.0F, 1.0F);
         double strength = Mth.lerp(partialTick, previousFov, currentFov) * (Double)minecraft.options.fovEffectScale().get();
         event.setFOV(event.getFOV() + 18.0 * strength);
      }
   }

   @SubscribeEvent
   public static void onRenderLevel(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES && !WAKES.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.level == currentLevel) {
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            double renderTime = clock - 1.0 + partialTick;
            Vec3 camera = event.getCamera().getPosition();
            PoseStack poses = event.getPoseStack();
            BufferSource buffers = minecraft.renderBuffers().bufferSource();
            VertexConsumer consumer = null;
            poses.pushPose();

            try {
               for (SwordFlightClientEffects.Wake wake : WAKES.values()) {
                  if (!(wake.lastPosition.distanceToSqr(camera) > 4096.0) && event.getFrustum().isVisible(wake.bounds())) {
                     if (consumer == null) {
                        consumer = buffers.getBuffer(AIRFLOW);
                     }

                     wake.render(consumer, poses.last(), camera, partialTick, renderTime);
                  }
               }

               if (consumer != null) {
                  buffers.endBatch(AIRFLOW);
               }
            } finally {
               poses.popPose();
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLogout(LoggingOut event) {
      clear();
   }

   private static void clear() {
      WAKES.clear();
      currentLevel = null;
      clock = 0;
      previousFov = 0.0F;
      currentFov = 0.0F;
   }

   private static float flightStrength(double speed) {
      float amount = Mth.clamp((float)((speed - 0.08) / 1.22), 0.0F, 1.0F);
      return amount * amount * (3.0F - 2.0F * amount);
   }

   private static Vec3 sideways(Vec3 direction, FlyingSwordEntity sword) {
      Vec3 side = direction.cross(UP);
      if (side.lengthSqr() < 1.0E-5) {
         side = Vec3.directionFromRotation(0.0F, sword.getYRot()).cross(UP);
      }

      return side.normalize();
   }

   private static void segment(
      VertexConsumer consumer, Pose pose, Vec3 camera, Vec3 start, Vec3 end, float startWidth, float endWidth, float startAlpha, float endAlpha
   ) {
      Vec3 tangent = end.subtract(start);
      if (!(tangent.lengthSqr() < 1.0E-8) && !(Math.max(startAlpha, endAlpha) < 0.001F)) {
         Vec3 side = tangent.cross(camera.subtract(start.add(end).scale(0.5)));
         if (side.lengthSqr() < 1.0E-8) {
            side = tangent.cross(Math.abs(tangent.normalize().y) > 0.95 ? new Vec3(1.0, 0.0, 0.0) : UP);
         }

         side = side.normalize();
         Vec3 a = start.subtract(camera);
         Vec3 b = end.subtract(camera);
         Vec3 leftA = a.add(side.scale(startWidth));
         Vec3 leftB = b.add(side.scale(endWidth));
         Vec3 rightA = a.subtract(side.scale(startWidth));
         Vec3 rightB = b.subtract(side.scale(endWidth));
         vertex(consumer, pose, leftA, 0.0F);
         vertex(consumer, pose, leftB, 0.0F);
         vertex(consumer, pose, b, endAlpha);
         vertex(consumer, pose, a, startAlpha);
         vertex(consumer, pose, a, startAlpha);
         vertex(consumer, pose, b, endAlpha);
         vertex(consumer, pose, rightB, 0.0F);
         vertex(consumer, pose, rightA, 0.0F);
      }
   }

   private static void vertex(VertexConsumer consumer, Pose pose, Vec3 point, float alpha) {
      consumer.addVertex(pose, (float)point.x, (float)point.y, (float)point.z).setColor(0.78F, 0.94F, 1.0F, alpha);
   }

   private record TrailPoint(Vec3 position, Vec3 side, float strength, double tick) {
   }

   private static final class Wake {
      private final FlyingSwordEntity sword;
      private final List<SwordFlightClientEffects.TrailPoint> trail = new ArrayList<>(14);
      private final List<SwordFlightClientEffects.WindStreak> streaks = new ArrayList<>(32);
      private Vec3 lastPosition;
      private AABB renderBounds;
      private int lastSeenTick;
      private float strength;

      private Wake(FlyingSwordEntity sword) {
         this.sword = sword;
         this.lastPosition = sword.position();
         this.lastSeenTick = SwordFlightClientEffects.clock;
      }

      private void prune(int now) {
         this.renderBounds = null;
         this.trail.removeIf(point -> now - point.tick > 13.0);
         this.streaks.removeIf(streak -> now - streak.born > streak.lifetime + 1);
      }

      private void update(Minecraft minecraft, int now) {
         this.renderBounds = null;
         Vec3 position = this.sword.position();
         Vec3 movement = position.subtract(this.lastPosition);
         this.lastSeenTick = now;
         this.lastPosition = position;
         if (movement.lengthSqr() > 64.0) {
            this.trail.clear();
            this.streaks.clear();
            this.strength = 0.0F;
         } else {
            double speed = movement.length();
            float target = SwordFlightClientEffects.flightStrength(speed);
            this.strength = Mth.lerp(0.3F, this.strength, target);
            if (!(speed < 0.025) && !(this.strength < 0.015F)) {
               Vec3 direction = movement.normalize();
               Vec3 side = SwordFlightClientEffects.sideways(direction, this.sword);
               this.trail.add(0, new SwordFlightClientEffects.TrailPoint(position.add(0.0, 0.13, 0.0), side, this.strength, now));

               while (this.trail.size() > 14) {
                  this.trail.remove(this.trail.size() - 1);
               }

               ParticleStatus particles = (ParticleStatus)minecraft.options.particles().get();
               int count = particles == ParticleStatus.ALL
                  ? 1 + (int)(this.strength * 3.0F)
                  : (particles == ParticleStatus.DECREASED ? 1 + (int)this.strength : (now % 3 == 0 ? 1 : 0));
               if (minecraft.player != null && this.sword.distanceToSqr(minecraft.player) > 1024.0) {
                  count = now % 2 == 0 ? 1 : 0;
               }

               Vec3 vertical = side.cross(direction).normalize();

               for (int i = 0; i < count && this.streaks.size() < 32; i++) {
                  float angle = SwordFlightClientEffects.currentLevel.random.nextFloat() * (float) (Math.PI * 2);
                  double radius = 0.85 + SwordFlightClientEffects.currentLevel.random.nextDouble() * 1.1;
                  Vec3 radial = side.scale(Math.cos(angle)).add(vertical.scale(Math.sin(angle)));
                  Vec3 origin = position.add(0.0, 1.05, 0.0)
                     .add(direction.scale(1.5 + SwordFlightClientEffects.currentLevel.random.nextDouble() * 3.5))
                     .add(radial.scale(radius));
                  this.streaks
                     .add(
                        new SwordFlightClientEffects.WindStreak(
                           origin,
                           direction,
                           radial,
                           0.65F + this.strength * 1.9F,
                           0.01F + this.strength * 0.013F,
                           0.13F + this.strength * 0.24F,
                           now,
                           10 + SwordFlightClientEffects.currentLevel.random.nextInt(5)
                        )
                     );
               }
            }
         }
      }

      private AABB bounds() {
         if (this.renderBounds != null) {
            return this.renderBounds;
         }

         AABB bounds = new AABB(this.lastPosition, this.lastPosition);

         for (SwordFlightClientEffects.TrailPoint point : this.trail) {
            bounds = bounds.minmax(new AABB(point.position, point.position));
         }

         for (SwordFlightClientEffects.WindStreak streak : this.streaks) {
            bounds = bounds.minmax(new AABB(streak.origin, streak.origin));
         }

         this.renderBounds = bounds.inflate(4.0);
         return this.renderBounds;
      }

      private void render(VertexConsumer consumer, Pose pose, Vec3 camera, float partialTick, double renderTime) {
         if (this.trail.size() >= 2) {
            SwordFlightClientEffects.TrailPoint newest = this.trail.getFirst();
            SwordFlightClientEffects.TrailPoint previous = this.trail.get(1);
            boolean liveHead = newest.tick == SwordFlightClientEffects.clock && previous.tick == SwordFlightClientEffects.clock - 1;
            SwordFlightClientEffects.TrailPoint head = liveHead
               ? new SwordFlightClientEffects.TrailPoint(
                  previous.position.lerp(newest.position, partialTick),
                  previous.side.lerp(newest.side, partialTick),
                  Mth.lerp(partialTick, previous.strength, newest.strength),
                  renderTime
               )
               : newest;

            for (int lane = -1; lane <= 1; lane++) {
               SwordFlightClientEffects.TrailPoint start = head;

               for (int i = 1; i < this.trail.size(); i++) {
                  SwordFlightClientEffects.TrailPoint end = this.trail.get(i);
                  float a = trailAlpha(start, renderTime);
                  float b = trailAlpha(end, renderTime);
                  float width = lane == 0 ? 0.055F : 0.032F;
                  SwordFlightClientEffects.segment(
                     consumer,
                     pose,
                     camera,
                     this.lanePosition(start, lane, renderTime),
                     this.lanePosition(end, lane, renderTime),
                     width * (0.5F + start.strength),
                     width * (0.5F + end.strength),
                     a,
                     b
                  );
                  start = end;
               }
            }
         }

         for (SwordFlightClientEffects.WindStreak streak : this.streaks) {
            double age = renderTime - streak.born;
            if (!(age < 0.0) && !(age >= streak.lifetime)) {
               float fade = (float)Math.sin(Math.PI * age / streak.lifetime);
               Vec3 center = streak.origin.add(streak.direction.scale(-age * 0.07));
               Vec3 bend = streak.radial.scale(0.1 * fade);
               Vec3 start = center.add(streak.direction.scale(streak.length * 0.5));
               Vec3 end = center.subtract(streak.direction.scale(streak.length * 0.5));
               center = center.add(bend);
               SwordFlightClientEffects.segment(consumer, pose, camera, start, center, streak.width * 0.15F, streak.width, 0.0F, streak.alpha * fade);
               SwordFlightClientEffects.segment(consumer, pose, camera, center, end, streak.width, streak.width * 0.15F, streak.alpha * fade, 0.0F);
            }
         }
      }

      private Vec3 lanePosition(SwordFlightClientEffects.TrailPoint point, int lane, double renderTime) {
         double age = Math.max(0.0, renderTime - point.tick);
         double spread = (this.sword.isOversizedSword() ? 0.62 : 0.31) + age * 0.015;
         return point.position.add(point.side.scale(lane * spread)).add(0.0, lane == 0 ? 0.0 : Math.sin(age * 0.45) * 0.035, 0.0);
      }

      private static float trailAlpha(SwordFlightClientEffects.TrailPoint point, double renderTime) {
         float remaining = Mth.clamp(1.0F - (float)(renderTime - point.tick) / 12.0F, 0.0F, 1.0F);
         return point.strength * remaining * remaining * 0.46F;
      }
   }

   private record WindStreak(Vec3 origin, Vec3 direction, Vec3 radial, float length, float width, float alpha, int born, int lifetime) {
   }
}
