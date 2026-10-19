package com.mortal_cultivation.client.vfx;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mortal_cultivation.myriadswords.MyriadSwordsStatePayload;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.NeoForgeRenderTypes;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME, value = Dist.CLIENT)
public final class MyriadSwordsClientEffects {
   private static final ResourceLocation IRON_SWORD_TEXTURE = ResourceLocation.withDefaultNamespace("textures/item/iron_sword.png");
   private static final RenderType SWORD_RENDER_TYPE = NeoForgeRenderTypes.getUnlitTranslucent(IRON_SWORD_TEXTURE, true);
   private static final Vec3 WORLD_UP = new Vec3(0.0, 1.0, 0.0);
   private static final int MISSING_CASTER_GRACE_TICKS = 40;
   private static final float START_FADE_PER_TICK = 0.12F;
   private static final float STOP_FADE_PER_TICK = 0.16F;
   private static final double AIM_SMOOTHING = 0.45;
   private static final double MAX_AIM_RAY_DISTANCE = 64.0;
   private static final double DEFAULT_AIM_DISTANCE = 26.0;
   private static final double MAX_LEADER_SPEED = 4.8;
   private static final double MAX_FOLLOWER_SPEED = 5.2;
   private static final Map<UUID, MyriadSwordsClientEffects.EffectState> ACTIVE_EFFECTS = new HashMap<>();

   private MyriadSwordsClientEffects() {
   }

   public static void handleState(MyriadSwordsStatePayload payload) {
      acceptState(payload.casterId(), payload.active(), payload.seed(), payload.startGameTime(), payload.targetEntityId());
   }

   public static void acceptState(UUID casterId, boolean active, int seed, long startGameTime, int targetEntityId) {
      if (!active) {
         MyriadSwordsClientEffects.EffectState state = ACTIVE_EFFECTS.get(casterId);
         if (state != null) {
            state.stopping = true;
         }
      } else {
         MyriadSwordsClientEffects.EffectState state = ACTIVE_EFFECTS.get(casterId);
         if (state != null && state.seed == seed && state.startGameTime == startGameTime) {
            state.stopping = false;
            state.setTargetEntityId(targetEntityId);
            state.missingCasterTicks = 0;
         } else {
            ACTIVE_EFFECTS.put(casterId, new MyriadSwordsClientEffects.EffectState(seed, startGameTime, targetEntityId));
         }
      }
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level == null) {
         ACTIVE_EFFECTS.clear();
      } else {
         Iterator<Entry<UUID, MyriadSwordsClientEffects.EffectState>> iterator = ACTIVE_EFFECTS.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, MyriadSwordsClientEffects.EffectState> entry = iterator.next();
            MyriadSwordsClientEffects.EffectState state = entry.getValue();
            Player caster = level.getPlayerByUUID(entry.getKey());
            state.previousFade = state.fade;
            if (caster != null && !caster.isRemoved()) {
               state.missingCasterTicks = 0;
               state.tick(caster, level);
            } else {
               state.missingCasterTicks++;
               if (state.missingCasterTicks > 40) {
                  state.stopping = true;
               }
            }

            if (state.stopping) {
               state.fade = Math.max(0.0F, state.fade - 0.16F);
            } else if (state.initialized) {
               state.fade = Math.min(1.0F, state.fade + 0.12F);
            }

            if (state.stopping && state.fade <= 0.0F) {
               iterator.remove();
            }
         }
      }
   }

   @SubscribeEvent
   public static void onRenderLevel(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES && !ACTIVE_EFFECTS.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         ClientLevel level = minecraft.level;
         if (level != null) {
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            Vec3 cameraPosition = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            BufferSource buffers = minecraft.renderBuffers().bufferSource();
            VertexConsumer consumer = null;
            poseStack.pushPose();
            poseStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
            Pose pose = poseStack.last();

            for (MyriadSwordsClientEffects.EffectState state : ACTIVE_EFFECTS.values()) {
               if (state.initialized && state.bounds != null && event.getFrustum().isVisible(state.bounds)) {
                  if (consumer == null) {
                     consumer = buffers.getBuffer(SWORD_RENDER_TYPE);
                  }

                  float stateAlpha = lerp(partialTick, state.previousFade, state.fade);
                  double age = Math.max(0.0, (float)level.getGameTime() + partialTick - (float)state.startGameTime);
                  int globalIndex = 0;

                  for (MyriadSwordsClientEffects.SwordStream stream : state.streams) {
                     for (MyriadSwordsClientEffects.SwordAgent sword : stream.swords) {
                        Vec3 position = sword.previousPosition.lerp(sword.position, partialTick);
                        Vec3 direction = sword.previousDirection.lerp(sword.direction, partialTick);
                        direction = safeNormalize(direction, sword.direction);
                        float pulse = 0.92F + 0.08F * Mth.sin((float)age * sword.pulseSpeed + sword.pulsePhase);
                        renderCrossedSword(consumer, pose, position, direction, sword.scale, stateAlpha * sword.opacity, pulse, globalIndex++);
                     }
                  }
               }
            }

            if (consumer != null) {
               buffers.endBatch(SWORD_RENDER_TYPE);
            }

            poseStack.popPose();
         }
      }
   }

   @SubscribeEvent
   public static void onClientLogout(LoggingOut event) {
      ACTIVE_EFFECTS.clear();
   }

   private static Vec3 resolveAimPoint(MyriadSwordsClientEffects.EffectState state, Player caster, ClientLevel level, Vec3 origin, Vec3 look) {
      Vec3 rayEnd = origin.add(look.scale(64.0));
      Entity target = state.targetEntityId >= 0 ? level.getEntity(state.targetEntityId) : null;
      if (target != null && !target.isRemoved() && target.isAlive() && target != caster) {
         return target.getBoundingBox().clip(origin, rayEnd).orElse(target.getBoundingBox().getCenter());
      }

      BlockHitResult blockHit = level.clip(new ClipContext(origin, rayEnd, Block.COLLIDER, Fluid.NONE, caster));
      return blockHit.getType() != Type.MISS ? blockHit.getLocation() : origin.add(look.scale(26.0));
   }

   private static Vec3 horizontalDirection(Vec3 direction, Vec3 fallback) {
      Vec3 horizontal = new Vec3(direction.x, 0.0, direction.z);
      return horizontal.lengthSqr() <= 1.0E-8 ? safeNormalize(fallback, new Vec3(0.0, 0.0, 1.0)) : horizontal.normalize();
   }

   private static Vec3 horizontalRight(Vec3 direction, Vec3 fallback) {
      Vec3 horizontal = horizontalDirection(direction, fallback);
      Vec3 right = new Vec3(-horizontal.z, 0.0, horizontal.x);
      return safeNormalize(right, fallback);
   }

   private static Vec3 safeNormalize(Vec3 value, Vec3 fallback) {
      if (value.lengthSqr() > 1.0E-8) {
         return value.normalize();
      } else {
         return fallback.lengthSqr() > 1.0E-8 ? fallback.normalize() : new Vec3(0.0, 0.0, 1.0);
      }
   }

   private static Vec3 clampLength(Vec3 value, double maximum) {
      double lengthSqr = value.lengthSqr();
      return lengthSqr <= maximum * maximum ? value : value.scale(maximum / Math.sqrt(lengthSqr));
   }

   private static double clamp(double value, double minimum, double maximum) {
      return Math.max(minimum, Math.min(maximum, value));
   }

   private static float lerp(float partialTick, float previous, float current) {
      return previous + (current - previous) * partialTick;
   }

   private static void renderCrossedSword(VertexConsumer consumer, Pose pose, Vec3 position, Vec3 direction, float length, float alpha, float pulse, int index) {
      float tangentX = (float)direction.x;
      float tangentY = (float)direction.y;
      float tangentZ = (float)direction.z;
      float referenceX = 0.0F;
      float referenceY = Math.abs(tangentY) > 0.94F ? 0.0F : 1.0F;
      float referenceZ = Math.abs(tangentY) > 0.94F ? 1.0F : 0.0F;
      float sideX = tangentY * referenceZ - tangentZ * referenceY;
      float sideY = tangentZ * referenceX - tangentX * referenceZ;
      float sideZ = tangentX * referenceY - tangentY * referenceX;
      float sideLength = Mth.sqrt(sideX * sideX + sideY * sideY + sideZ * sideZ);
      if (sideLength < 1.0E-4F) {
         sideX = 1.0F;
         sideY = 0.0F;
         sideZ = 0.0F;
      } else {
         sideX /= sideLength;
         sideY /= sideLength;
         sideZ /= sideLength;
      }

      float secondSideX = tangentY * sideZ - tangentZ * sideY;
      float secondSideY = tangentZ * sideX - tangentX * sideZ;
      float secondSideZ = tangentX * sideY - tangentY * sideX;
      float halfLength = length * 0.5F;
      float colorVariation = 0.94F + index % 7 * 0.008F;
      float red = 0.9F * pulse * colorVariation;
      float green = 0.68F * pulse * colorVariation;
      float blue = pulse;
      emitSwordPlane(
         consumer,
         pose,
         position,
         tangentX,
         tangentY,
         tangentZ,
         sideX,
         sideY,
         sideZ,
         secondSideX,
         secondSideY,
         secondSideZ,
         halfLength,
         red,
         green,
         blue,
         alpha
      );
      emitSwordPlane(
         consumer,
         pose,
         position,
         tangentX,
         tangentY,
         tangentZ,
         secondSideX,
         secondSideY,
         secondSideZ,
         -sideX,
         -sideY,
         -sideZ,
         halfLength,
         red,
         green,
         blue,
         alpha * 0.88F
      );
   }

   private static void emitSwordPlane(
      VertexConsumer consumer,
      Pose pose,
      Vec3 position,
      float tangentX,
      float tangentY,
      float tangentZ,
      float sideX,
      float sideY,
      float sideZ,
      float normalX,
      float normalY,
      float normalZ,
      float halfLength,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      float x = (float)position.x;
      float y = (float)position.y;
      float z = (float)position.z;
      emitVertex(
         consumer,
         pose,
         x - tangentX * halfLength,
         y - tangentY * halfLength,
         z - tangentZ * halfLength,
         0.0F,
         1.0F,
         normalX,
         normalY,
         normalZ,
         red,
         green,
         blue,
         alpha
      );
      emitVertex(
         consumer, pose, x + sideX * halfLength, y + sideY * halfLength, z + sideZ * halfLength, 1.0F, 1.0F, normalX, normalY, normalZ, red, green, blue, alpha
      );
      emitVertex(
         consumer,
         pose,
         x + tangentX * halfLength,
         y + tangentY * halfLength,
         z + tangentZ * halfLength,
         1.0F,
         0.0F,
         normalX,
         normalY,
         normalZ,
         red,
         green,
         blue,
         alpha
      );
      emitVertex(
         consumer, pose, x - sideX * halfLength, y - sideY * halfLength, z - sideZ * halfLength, 0.0F, 0.0F, normalX, normalY, normalZ, red, green, blue, alpha
      );
   }

   private static void emitVertex(
      VertexConsumer consumer,
      Pose pose,
      float x,
      float y,
      float z,
      float u,
      float v,
      float normalX,
      float normalY,
      float normalZ,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      consumer.addVertex(pose, x, y, z)
         .setColor(red, green, blue, alpha)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, normalX, normalY, normalZ);
   }

   private static int mix(int value) {
      value ^= value >>> 16;
      value *= 2146121005;
      value ^= value >>> 15;
      value *= -2073254261;
      return value ^ value >>> 16;
   }

   private static double unsignedUnit(int value) {
      return (value >>> 8) * 5.9604645E-8F;
   }

   private static final class EffectState {
      private final int seed;
      private final long startGameTime;
      private final MyriadSwordsClientEffects.SwordStream[] streams = new MyriadSwordsClientEffects.SwordStream[6];
      private int targetEntityId;
      private int missingCasterTicks;
      private boolean initialized;
      private boolean stopping;
      private boolean retargetPending;
      private float previousFade;
      private float fade;
      private Vec3 aim = Vec3.ZERO;
      private AABB bounds;

      private EffectState(int seed, long startGameTime, int targetEntityId) {
         this.seed = seed;
         this.startGameTime = startGameTime;
         this.targetEntityId = targetEntityId;
      }

      private void setTargetEntityId(int targetEntityId) {
         if (this.targetEntityId != targetEntityId) {
            this.targetEntityId = targetEntityId;
            this.retargetPending = true;
         }
      }

      private void tick(Player caster, ClientLevel level) {
         Vec3 origin = caster.getEyePosition();
         Vec3 look = MyriadSwordsClientEffects.safeNormalize(caster.getLookAngle(), new Vec3(0.0, 0.0, 1.0));
         Vec3 desiredAim = MyriadSwordsClientEffects.resolveAimPoint(this, caster, level, origin, look);
         long age = Math.max(0L, level.getGameTime() - this.startGameTime);
         if (!this.initialized) {
            this.aim = desiredAim;
            this.initializeStreams(origin, look, desiredAim);
            this.initialized = true;
         } else {
            this.aim = this.aim.lerp(desiredAim, 0.45);
         }

         if (this.retargetPending) {
            for (MyriadSwordsClientEffects.SwordStream stream : this.streams) {
               stream.retarget(age);
            }

            this.retargetPending = false;
         }

         for (MyriadSwordsClientEffects.SwordStream stream : this.streams) {
            stream.tick(this.aim, age);
         }

         this.updateBounds();
      }

      private void initializeStreams(Vec3 origin, Vec3 look, Vec3 initialAim) {
         Vec3 horizontalForward = MyriadSwordsClientEffects.horizontalDirection(look, new Vec3(0.0, 0.0, 1.0));
         Vec3 right = MyriadSwordsClientEffects.horizontalRight(horizontalForward, new Vec3(1.0, 0.0, 0.0));

         for (int streamIndex = 0; streamIndex < this.streams.length; streamIndex++) {
            this.streams[streamIndex] = new MyriadSwordsClientEffects.SwordStream(
               MyriadSwordsClientEffects.mix(this.seed + streamIndex * 1831565813), streamIndex, origin, look, horizontalForward, right, initialAim
            );
         }
      }

      private void updateBounds() {
         MyriadSwordsClientEffects.SwordAgent first = this.streams[0].swords[0];
         double minX = first.position.x;
         double minY = first.position.y;
         double minZ = first.position.z;
         double maxX = minX;
         double maxY = minY;
         double maxZ = minZ;

         for (MyriadSwordsClientEffects.SwordStream stream : this.streams) {
            for (MyriadSwordsClientEffects.SwordAgent sword : stream.swords) {
               minX = Math.min(minX, sword.position.x);
               minY = Math.min(minY, sword.position.y);
               minZ = Math.min(minZ, sword.position.z);
               maxX = Math.max(maxX, sword.position.x);
               maxY = Math.max(maxY, sword.position.y);
               maxZ = Math.max(maxZ, sword.position.z);
            }
         }

         this.bounds = new AABB(minX, minY, minZ, maxX, maxY, maxZ).inflate(2.0);
      }
   }

   private static final class RandomSequence {
      private int state;

      private RandomSequence(int seed) {
         this.state = seed;
      }

      private double next() {
         this.state = MyriadSwordsClientEffects.mix(this.state + 1831565813);
         return MyriadSwordsClientEffects.unsignedUnit(this.state);
      }
   }

   private static final class SwordAgent {
      private Vec3 previousPosition;
      private Vec3 position;
      private Vec3 velocity;
      private Vec3 previousDirection;
      private Vec3 direction;
      private Vec3 fallbackRight;
      private final double spacing;
      private final double sideOffset;
      private final double verticalOffset;
      private final double waveAmplitude;
      private final double waveSpeed;
      private final double wavePhase;
      private final double response;
      private final float scale;
      private final float opacity;
      private final float pulseSpeed;
      private final float pulsePhase;

      private SwordAgent(
         Vec3 position,
         Vec3 velocity,
         double spacing,
         double sideOffset,
         double verticalOffset,
         double waveAmplitude,
         double waveSpeed,
         double wavePhase,
         double response,
         float scale,
         float opacity,
         float pulseSpeed,
         float pulsePhase,
         Vec3 fallbackRight
      ) {
         this.previousPosition = position;
         this.position = position;
         this.velocity = velocity;
         this.previousDirection = MyriadSwordsClientEffects.safeNormalize(velocity, new Vec3(0.0, -1.0, 0.0));
         this.direction = this.previousDirection;
         this.spacing = spacing;
         this.sideOffset = sideOffset;
         this.verticalOffset = verticalOffset;
         this.waveAmplitude = waveAmplitude;
         this.waveSpeed = waveSpeed;
         this.wavePhase = wavePhase;
         this.response = response;
         this.scale = scale;
         this.opacity = opacity;
         this.pulseSpeed = pulseSpeed;
         this.pulsePhase = pulsePhase;
         this.fallbackRight = fallbackRight;
      }

      private void capturePreviousState() {
         this.previousPosition = this.position;
         this.previousDirection = this.direction;
      }
   }

   private static final class SwordStream {
      private final int streamIndex;
      private final MyriadSwordsClientEffects.SwordAgent[] swords = new MyriadSwordsClientEffects.SwordAgent[10];
      private final double speedMultiplier;
      private final double overshootDistance;
      private final double turnRadius;
      private final double turnLift;
      private final int turnDuration;
      private Vec3 fallbackRight;
      private Vec3 turnTarget = Vec3.ZERO;
      private boolean turning;
      private int turnTicks;
      private double turnSign;
      private long nextTurnAllowedTick;

      private SwordStream(int seed, int streamIndex, Vec3 origin, Vec3 look, Vec3 horizontalForward, Vec3 right, Vec3 initialAim) {
         this.streamIndex = streamIndex;
         MyriadSwordsClientEffects.RandomSequence random = new MyriadSwordsClientEffects.RandomSequence(seed);
         double centeredLane = streamIndex - 2.5;
         double lane = centeredLane * 2.15 + (random.next() - 0.5) * 1.6;
         double spawnDistance = 7.0 + random.next() * 9.0;
         double spawnHeight = 10.0 + random.next() * 8.0;
         Vec3 spawnPosition = origin.add(horizontalForward.scale(spawnDistance))
            .add(right.scale(lane))
            .add(MyriadSwordsClientEffects.WORLD_UP.scale(spawnHeight));
         Vec3 initialDirection = MyriadSwordsClientEffects.safeNormalize(initialAim.subtract(spawnPosition), look);
         double initialDistance = Math.sqrt(spawnPosition.distanceToSqr(initialAim));
         double initialSpeed = MyriadSwordsClientEffects.clamp(initialDistance / 16.0, 1.0, 4.8);
         this.speedMultiplier = 0.88 + random.next() * 0.24;
         this.overshootDistance = 2.0 + random.next() * 2.2;
         this.turnRadius = 7.0 + random.next() * 6.0;
         this.turnLift = 2.5 + random.next() * 5.0;
         this.turnDuration = 13 + (int)Math.floor(random.next() * 11.0);
         this.turnSign = random.next() < 0.5 ? -1.0 : 1.0;
         this.fallbackRight = right;
         this.nextTurnAllowedTick = 16 + streamIndex * 2;
         double accumulatedSpacing = 0.0;

         for (int swordIndex = 0; swordIndex < this.swords.length; swordIndex++) {
            double spacing = swordIndex == 0 ? 0.0 : 0.75 + random.next();
            accumulatedSpacing += spacing;
            double sideOffset = (random.next() - 0.5) * 2.4;
            double verticalOffset = (random.next() - 0.5) * 1.6;
            double waveAmplitude = 0.15 + random.next() * 0.6;
            double waveSpeed = 0.09 + random.next() * 0.08;
            double wavePhase = random.next() * (float) (Math.PI * 2);
            double response = 0.13 + random.next() * 0.17;
            float scale = (float)(0.78 + random.next() * 0.47);
            float opacity = (float)(0.72 + random.next() * 0.25);
            float pulseSpeed = (float)(0.38 + random.next() * 0.22);
            float pulsePhase = (float)(random.next() * (float) (Math.PI * 2));
            Vec3 position = spawnPosition.subtract(initialDirection.scale(accumulatedSpacing))
               .add(right.scale(sideOffset * 0.55))
               .add(MyriadSwordsClientEffects.WORLD_UP.scale(verticalOffset));
            Vec3 velocity = initialDirection.scale(initialSpeed * (0.78 + random.next() * 0.2));
            this.swords[swordIndex] = new MyriadSwordsClientEffects.SwordAgent(
               position,
               velocity,
               spacing,
               sideOffset,
               verticalOffset,
               waveAmplitude,
               waveSpeed,
               wavePhase,
               response,
               scale,
               opacity,
               pulseSpeed,
               pulsePhase,
               right
            );
         }
      }

      private void retarget(long age) {
         this.turning = false;
         this.turnTicks = 0;
         this.nextTurnAllowedTick = age + 16L + this.streamIndex * 2;
      }

      private void tick(Vec3 aim, long age) {
         for (MyriadSwordsClientEffects.SwordAgent sword : this.swords) {
            sword.capturePreviousState();
         }

         this.tickLeader(aim, age);

         for (int index = 1; index < this.swords.length; index++) {
            this.tickFollower(this.swords[index], this.swords[index - 1], age);
         }
      }

      private void tickLeader(Vec3 aim, long age) {
         MyriadSwordsClientEffects.SwordAgent leader = this.swords[0];
         Vec3 toAim = aim.subtract(leader.position);
         double aimDistance = Math.sqrt(toAim.lengthSqr());
         boolean passedAim = aimDistance < 4.0 && toAim.dot(leader.velocity) <= 0.0;
         if (!this.turning && age >= this.nextTurnAllowedTick && (passedAim || aimDistance < 0.65)) {
            this.beginTurn(leader, age);
         }

         if (this.turning) {
            this.turnTicks--;
            if (this.turnTicks <= 0 || leader.position.distanceToSqr(this.turnTarget) < 2.25) {
               this.turning = false;
               this.nextTurnAllowedTick = age + 6L;
            }
         }

         Vec3 target;
         double desiredSpeed;
         double steering;
         if (this.turning) {
            target = this.turnTarget;
            double distance = Math.sqrt(leader.position.distanceToSqr(target));
            desiredSpeed = MyriadSwordsClientEffects.clamp(0.75 + distance * 0.18, 1.15, 4.8);
            steering = 0.11;
         } else {
            long remaining = this.nextTurnAllowedTick - age;
            if (remaining > 0L) {
               target = aim;
               desiredSpeed = MyriadSwordsClientEffects.clamp(aimDistance / Math.max(1L, remaining), 0.25, 4.8);
               steering = 0.3;
            } else {
               Vec3 attackDirection = MyriadSwordsClientEffects.safeNormalize(leader.velocity, MyriadSwordsClientEffects.safeNormalize(toAim, leader.direction));
               target = aim.add(attackDirection.scale(this.overshootDistance));
               desiredSpeed = MyriadSwordsClientEffects.clamp(1.0 + aimDistance * 0.16, 1.25, 4.8);
               steering = 0.14;
            }
         }

         desiredSpeed = Math.min(4.8, desiredSpeed * this.speedMultiplier);
         Vec3 desiredVelocity = MyriadSwordsClientEffects.safeNormalize(target.subtract(leader.position), leader.direction).scale(desiredSpeed);
         leader.velocity = MyriadSwordsClientEffects.clampLength(leader.velocity.lerp(desiredVelocity, steering), 4.8);
         leader.position = leader.position.add(leader.velocity);
         leader.direction = MyriadSwordsClientEffects.safeNormalize(leader.velocity, leader.direction);
      }

      private void beginTurn(MyriadSwordsClientEffects.SwordAgent leader, long age) {
         Vec3 travelDirection = MyriadSwordsClientEffects.safeNormalize(leader.velocity, leader.direction);
         Vec3 side = MyriadSwordsClientEffects.horizontalRight(travelDirection, this.fallbackRight).scale(this.turnSign);
         double variation = 0.88 + 0.12 * Math.sin(age * 0.17 + this.streamIndex * 1.31);
         this.turnTarget = leader.position
            .add(travelDirection.scale(4.5 + this.overshootDistance))
            .add(side.scale(this.turnRadius * variation))
            .add(MyriadSwordsClientEffects.WORLD_UP.scale(this.turnLift));
         this.fallbackRight = side.scale(this.turnSign);
         this.turnTicks = this.turnDuration;
         this.turning = true;
         this.turnSign = -this.turnSign;
      }

      private void tickFollower(MyriadSwordsClientEffects.SwordAgent follower, MyriadSwordsClientEffects.SwordAgent predecessor, long age) {
         Vec3 predecessorDirection = MyriadSwordsClientEffects.safeNormalize(predecessor.velocity, predecessor.direction);
         Vec3 side = MyriadSwordsClientEffects.horizontalRight(predecessorDirection, follower.fallbackRight);
         follower.fallbackRight = side;
         double wave = Math.sin(age * follower.waveSpeed + follower.wavePhase);
         double liftWave = Math.cos(age * follower.waveSpeed * 0.83 + follower.wavePhase);
         Vec3 target = predecessor.position
            .subtract(predecessorDirection.scale(follower.spacing))
            .add(side.scale(follower.sideOffset + wave * follower.waveAmplitude))
            .add(MyriadSwordsClientEffects.WORLD_UP.scale(follower.verticalOffset + liftWave * follower.waveAmplitude * 0.45));
         Vec3 offset = target.subtract(follower.position);
         double distance = Math.sqrt(offset.lengthSqr());
         double predecessorSpeed = Math.sqrt(predecessor.velocity.lengthSqr());
         double desiredSpeed = MyriadSwordsClientEffects.clamp(distance * 0.68 + predecessorSpeed * 0.58, 0.2, 5.2);
         double response = distance > 8.0 ? Math.min(0.48, follower.response + 0.18) : follower.response;
         Vec3 desiredVelocity = MyriadSwordsClientEffects.safeNormalize(offset, predecessorDirection).scale(desiredSpeed);
         follower.velocity = MyriadSwordsClientEffects.clampLength(follower.velocity.lerp(desiredVelocity, response), 5.2);
         follower.position = follower.position.add(follower.velocity);
         follower.direction = MyriadSwordsClientEffects.safeNormalize(follower.velocity, predecessorDirection);
      }
   }
}
