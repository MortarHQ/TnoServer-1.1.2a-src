package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;

public final class GearshiftBlueTrailClient {
   private static final Map<Integer, GearshiftBlueTrailClient.ActiveTrail> ACTIVE = new ConcurrentHashMap<>();
   private static final int MAX_NODES = 64;
   private static final int PHANTOM_COUNT = 10;
   private static final int PHANTOM_SPAWN_INTERVAL = 4;
   private static final int PHANTOM_ATTACK_START = 78;
   private static final int PHANTOM_ATTACK_DURATION = 10;
   private static final int PHANTOM_FADE_DURATION = 20;
   private static float time;

   private GearshiftBlueTrailClient() {
   }

   public static void enable(int entityId, int duration, int gear) {
      enable(entityId, -1, duration, gear);
   }

   public static void enable(int entityId, int targetId, int duration, int gear) {
      GearshiftBlueTrailClient.ActiveTrail trail = ACTIVE.computeIfAbsent(entityId, id -> new GearshiftBlueTrailClient.ActiveTrail());
      trail.life = Math.max(trail.life, duration);
      trail.gear = Mth.clamp(gear, 0, 5);
      trail.targetId = targetId;
      trail.seed = hash(entityId * 912.37F + targetId * 31.11F + duration * 17.31F + gear * 49.7F + time * 11.0F);
      if (trail.gear >= 5) {
         trail.phantomAge = 0;
         trail.phantomSequenceActive = true;
         trail.phantomsReady = false;

         for (int i = 0; i < 10; i++) {
            trail.phantoms[i] = null;
         }
      }
   }

   public static void disable(int entityId) {
      ACTIVE.remove(entityId);
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      time += 0.13F;
      if (minecraft.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<Entry<Integer, GearshiftBlueTrailClient.ActiveTrail>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<Integer, GearshiftBlueTrailClient.ActiveTrail> entry = iterator.next();
            Entity entity = minecraft.level.getEntity(entry.getKey());
            GearshiftBlueTrailClient.ActiveTrail trail = entry.getValue();
            trail.life--;
            if (trail.phantomSequenceActive) {
               trail.phantomAge++;
               if (trail.phantomAge > 120) {
                  trail.phantomSequenceActive = false;
               }
            }

            if (trail.life > 0 && entity instanceof LivingEntity living && living.isAlive()) {
               if (trail.gear >= 5 && trail.phantomSequenceActive) {
                  preparePhantoms(minecraft, living, trail);
               }

               trail.tickNodes();

               for (GearshiftBlueTrailClient.TrailPart part : GearshiftBlueTrailClient.TrailPart.values()) {
                  ArrayDeque<GearshiftBlueTrailClient.TrailNode> nodes = trail.nodes.get(part);

                  while (nodes.size() > getMaxNodes(trail.gear)) {
                     nodes.removeLast();
                  }
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   public static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES && !ACTIVE.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            Camera camera = minecraft.gameRenderer.getMainCamera();
            Vec3 cam = camera.getPosition();
            PoseStack poseStack = event.getPoseStack();
            Matrix4f matrix = poseStack.last().pose();
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);

            for (Entry<Integer, GearshiftBlueTrailClient.ActiveTrail> entry : ACTIVE.entrySet()) {
               if (minecraft.level.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
                  GearshiftBlueTrailClient.ActiveTrail trail = entry.getValue();
                  sampleEntity(living, trail, partialTick);
                  trimTrail(trail);
                  renderTrails(matrix, cam, trail, getGearIntensity(trail.gear), trail.gear);
                  renderPhantomSetupEffects(matrix, cam, trail, partialTick);
                  renderPhantomSlashes(matrix, cam, trail, partialTick);
               }
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            renderPhantoms(minecraft, poseStack, cam, partialTick);
         }
      }
   }

   private static void preparePhantoms(Minecraft minecraft, LivingEntity owner, GearshiftBlueTrailClient.ActiveTrail trail) {
      if (!trail.phantomsReady) {
         Vec3 ownerPos = owner.position();
         Vec3 targetPos = ownerPos.add(owner.getLookAngle().normalize().scale(18.0));
         if (minecraft.level != null && trail.targetId >= 0 && minecraft.level.getEntity(trail.targetId) instanceof LivingEntity living && living.isAlive()) {
            targetPos = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         }

         trail.executionCenter = targetPos;
         Vec3 ownerCenter = ownerPos.add(0.0, owner.getBbHeight() * 0.5, 0.0);
         Vec3 attackDir = targetPos.subtract(ownerCenter);
         if (attackDir.lengthSqr() < 0.001) {
            attackDir = owner.getLookAngle();
         }

         attackDir = new Vec3(attackDir.x, 0.0, attackDir.z);
         if (attackDir.lengthSqr() < 0.001) {
            attackDir = new Vec3(0.0, 0.0, 1.0);
         }

         attackDir = attackDir.normalize();
         Vec3 right = new Vec3(attackDir.z, 0.0, -attackDir.x).normalize();

         for (int i = 0; i < 10; i++) {
            float seed = hash(trail.seed * 199.0F + i * 43.0F);
            double angle = i * Math.PI * 2.0 / 10.0 + trail.seed * Math.PI * 2.0;
            double radius = 33.0 + i % 4 * 2.8 + hash(seed * 17.0F) * 4.0;
            Vec3 radial = right.scale(Math.cos(angle)).add(attackDir.scale(Math.sin(angle)));
            if (radial.lengthSqr() < 0.001) {
               radial = right;
            }

            radial = radial.normalize();
            Vec3 tangent = new Vec3(-radial.z, 0.0, radial.x);
            if (tangent.lengthSqr() < 0.001) {
               tangent = right;
            }

            tangent = tangent.normalize();
            double y = 1.0 + Math.sin(i * 1.42 + trail.seed) * 2.6;
            Vec3 spawn = targetPos.add(radial.scale(radius + 8.0)).add(tangent.scale((seed - 0.5) * 5.0)).add(0.0, y + 2.5, 0.0);
            Vec3 hold = targetPos.add(radial.scale(radius)).add(tangent.scale(Math.sin(i * 2.1) * 1.7)).add(0.0, y, 0.0);
            Vec3 strike = targetPos.add(radial.scale((i % 2 == 0 ? 1.0 : -1.0) * 1.35)).add(0.0, (hash(seed * 37.0F) - 0.5) * 2.2, 0.0);
            Vec3 exit = targetPos.subtract(radial.scale(19.0 + hash(seed * 47.0F) * 8.0))
               .add(tangent.scale((hash(seed * 53.0F) - 0.5) * 8.0))
               .add(0.0, -0.75, 0.0);
            Vec3 look = strike.subtract(hold);
            if (look.lengthSqr() < 0.001) {
               look = attackDir;
            }

            look = new Vec3(look.x, 0.0, look.z);
            if (look.lengthSqr() < 0.001) {
               look = attackDir;
            }

            look = look.normalize();
            float yRot = (float)(Mth.atan2(look.z, look.x) * 180.0F / (float)Math.PI) - 90.0F;
            trail.phantoms[i] = new GearshiftBlueTrailClient.PhantomState(spawn, hold, strike, exit, yRot, i * 4);
         }

         trail.phantomsReady = true;
      }
   }

   private static void sampleEntity(LivingEntity entity, GearshiftBlueTrailClient.ActiveTrail trail, float partialTick) {
      double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
      double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
      double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());
      Vec3 base = new Vec3(x, y, z);
      float height = entity.getBbHeight();
      float width = entity.getBbWidth();
      float yaw = (float)Math.toRadians(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()));
      Vec3 forward = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
      if (forward.lengthSqr() < 1.0E-5) {
         forward = new Vec3(0.0, 0.0, 1.0);
      }

      forward = forward.normalize();
      Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
      Vec3 up = new Vec3(0.0, 1.0, 0.0);
      Vec3 motion = entity.getDeltaMovement();
      Vec3 horizontalMotion = new Vec3(motion.x, 0.0, motion.z);
      double speed = horizontalMotion.length();
      double speedPush = Mth.clamp((float)speed * 3.35F, 0.0F, 0.48F);
      double gearPower = trail.gear / 5.0;
      Vec3 movementBack = Vec3.ZERO;
      if (horizontalMotion.lengthSqr() > 1.0E-6) {
         movementBack = horizontalMotion.normalize().scale(0.05 + speedPush * 0.135 + gearPower * 0.03);
      }

      double walk = Math.sin((entity.tickCount + partialTick + time) * 0.74) * (0.08 + speedPush * 0.27 + gearPower * 0.018);
      double armSwing = Math.sin((entity.tickCount + partialTick + time) * 0.62) * (0.024 + speedPush * 0.08 + gearPower * 0.014);
      Vec3 movementForward = Vec3.ZERO;
      if (horizontalMotion.lengthSqr() > 1.0E-6) {
         movementForward = horizontalMotion.normalize().scale(0.018 + speedPush * 0.035);
      }

      Vec3 chest = base.add(0.0, height * 0.655, 0.0).subtract(forward.scale(0.034 + gearPower * 0.014)).subtract(movementBack).add(movementForward);
      Vec3 hip = base.add(0.0, height * 0.31, 0.0).subtract(forward.scale(0.026)).subtract(movementBack.scale(0.6)).add(movementForward.scale(0.85));
      double armSide = width * (0.62 + gearPower * 0.03);
      double armForward = -0.042 + armSwing;
      double armDown = height * 0.118;
      Vec3 leftArm = chest.subtract(right.scale(armSide)).add(forward.scale(armForward)).subtract(up.scale(armDown));
      Vec3 rightArm = chest.add(right.scale(armSide)).add(forward.scale(-0.042 - armSwing)).subtract(up.scale(armDown));
      double legSide = width * (0.292 + gearPower * 0.016);
      double legForward = walk * (0.54 + gearPower * 0.07);
      Vec3 leftLeg = hip.subtract(right.scale(legSide)).add(forward.scale(legForward + 0.018)).subtract(up.scale(height * 0.245));
      Vec3 rightLeg = hip.add(right.scale(legSide)).add(forward.scale(-legForward + 0.018)).subtract(up.scale(height * 0.245));
      addNode(trail, GearshiftBlueTrailClient.TrailPart.LEFT_ARM, leftArm);
      addNode(trail, GearshiftBlueTrailClient.TrailPart.RIGHT_ARM, rightArm);
      addNode(trail, GearshiftBlueTrailClient.TrailPart.LEFT_LEG, leftLeg);
      addNode(trail, GearshiftBlueTrailClient.TrailPart.RIGHT_LEG, rightLeg);
   }

   private static void renderPhantoms(Minecraft minecraft, PoseStack poseStack, Vec3 cam, float partialTick) {
      if (minecraft.level != null) {
         EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
         BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);

         for (Entry<Integer, GearshiftBlueTrailClient.ActiveTrail> entry : ACTIVE.entrySet()) {
            if (minecraft.level.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
               GearshiftBlueTrailClient.ActiveTrail trail = entry.getValue();
               if (trail.gear >= 5 && trail.phantomSequenceActive && trail.phantomsReady) {
                  double oldX = living.getX();
                  double oldY = living.getY();
                  double oldZ = living.getZ();
                  double oldXOld = living.xOld;
                  double oldYOld = living.yOld;
                  double oldZOld = living.zOld;
                  float oldYRot = living.getYRot();
                  float oldXRot = living.getXRot();
                  float oldYRotO = living.yRotO;
                  float oldXRotO = living.xRotO;
                  float oldYBodyRot = living.yBodyRot;
                  float oldYBodyRotO = living.yBodyRotO;
                  float oldYHeadRot = living.yHeadRot;
                  float oldYHeadRotO = living.yHeadRotO;
                  int oldTickCount = living.tickCount;
                  GearshiftBlueTrailClient.WalkAnimationSnapshot oldWalk = captureWalkAnimation(living);

                  for (int i = 0; i < 10; i++) {
                     GearshiftBlueTrailClient.PhantomState state = trail.phantoms[i];
                     if (state != null) {
                        GearshiftBlueTrailClient.PhantomPose phantomPose = getPhantomPose(trail, state, partialTick);
                        if (!(phantomPose.alpha <= 0.015F)) {
                           living.setPos(phantomPose.position.x, phantomPose.position.y, phantomPose.position.z);
                           living.xOld = phantomPose.position.x;
                           living.yOld = phantomPose.position.y;
                           living.zOld = phantomPose.position.z;
                           living.setYRot(state.yRot);
                           living.setXRot(0.0F);
                           living.yRotO = state.yRot;
                           living.xRotO = 0.0F;
                           living.yBodyRot = state.yRot;
                           living.yBodyRotO = state.yRot;
                           living.yHeadRot = state.yRot;
                           living.yHeadRotO = state.yRot;
                           living.tickCount = oldTickCount + i * 7;
                           applyWalkAnimation(living, oldWalk);
                           float attack = getAttackProgress(trail, partialTick);
                           float r = 0.14F + phantomPose.alpha * 0.45F + attack * 0.2F;
                           float g = 0.52F + phantomPose.alpha * 0.38F;
                           float b = 1.0F;
                           RenderSystem.setShaderColor(r, g, b, phantomPose.alpha);
                           dispatcher.render(
                              living,
                              phantomPose.position.x - cam.x,
                              phantomPose.position.y - cam.y,
                              phantomPose.position.z - cam.z,
                              state.yRot,
                              0.0F,
                              poseStack,
                              bufferSource,
                              15728880
                           );
                        }
                     }
                  }

                  living.setPos(oldX, oldY, oldZ);
                  living.xOld = oldXOld;
                  living.yOld = oldYOld;
                  living.zOld = oldZOld;
                  living.setYRot(oldYRot);
                  living.setXRot(oldXRot);
                  living.yRotO = oldYRotO;
                  living.xRotO = oldXRotO;
                  living.yBodyRot = oldYBodyRot;
                  living.yBodyRotO = oldYBodyRotO;
                  living.yHeadRot = oldYHeadRot;
                  living.yHeadRotO = oldYHeadRotO;
                  living.tickCount = oldTickCount;
                  applyWalkAnimation(living, oldWalk);
               }
            }
         }

         bufferSource.endBatch();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
      }
   }

   private static void renderPhantomSetupEffects(Matrix4f matrix, Vec3 cam, GearshiftBlueTrailClient.ActiveTrail trail, float partialTick) {
      if (trail.gear >= 5 && trail.phantomSequenceActive && trail.phantomsReady) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         float age = trail.phantomAge + partialTick;
         float attack = getAttackProgress(trail, partialTick);
         float charge = Mth.clamp(age / 78.0F, 0.0F, 1.0F);

         for (int i = 0; i < 10; i++) {
            GearshiftBlueTrailClient.PhantomState state = trail.phantoms[i];
            if (state != null && !(age < state.spawnTick)) {
               float local = age - state.spawnTick;
               float appear = Mth.clamp(local / 8.0F, 0.0F, 1.0F);
               float holdAlpha = attack <= 0.0F ? appear * (0.22F + charge * 0.35F) : (1.0F - attack) * 0.28F;
               if (holdAlpha > 0.001F) {
                  float pulse = 0.75F + 0.25F * Mth.sin(time * 4.0F + i);
                  Vec3 center = state.hold.add(0.0, 0.9, 0.0);
                  drew |= addHorizontalRing(buffer, matrix, cam, center, 1.15 + pulse * 0.35, 0.035F, 0.15F, 0.58F, 1.0F, holdAlpha);
                  drew |= addHorizontalRing(buffer, matrix, cam, center, 2.15 + pulse * 0.55, 0.02F, 0.78F, 0.96F, 1.0F, holdAlpha * 0.62F);
                  drew |= addVerticalBeam(
                     buffer, matrix, cam, state.hold.add(0.0, -0.8, 0.0), state.hold.add(0.0, 4.4, 0.0), 0.08F, 0.1F, 0.38F, 1.0F, holdAlpha * 0.42F
                  );
                  drew |= addRibbon(buffer, matrix, cam, state.hold.add(0.0, 1.4, 0.0), state.strike, 0.02F, 0.45F, 0.88F, 1.0F, holdAlpha * 0.62F);
               }
            }
         }

         if (trail.executionCenter != null) {
            float circleAlpha = attack <= 0.0F ? charge * charge * 0.72F : Mth.sin(attack * (float) Math.PI) * 0.9F;
            double radiusA = 5.0 + charge * 7.0 + Mth.sin(time * 2.4F) * 0.6;
            double radiusB = 9.0 + charge * 13.0 + Mth.cos(time * 2.1F) * 0.8;
            drew |= addHorizontalRing(buffer, matrix, cam, trail.executionCenter, radiusA, 0.08F, 0.02F, 0.26F, 1.0F, circleAlpha * 0.5F);
            drew |= addHorizontalRing(buffer, matrix, cam, trail.executionCenter.add(0.0, 0.12, 0.0), radiusB, 0.05F, 0.88F, 0.98F, 1.0F, circleAlpha * 0.36F);
            drew |= addHorizontalRing(
               buffer, matrix, cam, trail.executionCenter.add(0.0, 0.25, 0.0), 2.0 + attack * 12.0, 0.11F, 1.0F, 0.96F, 0.68F, circleAlpha * attack
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static void renderPhantomSlashes(Matrix4f matrix, Vec3 cam, GearshiftBlueTrailClient.ActiveTrail trail, float partialTick) {
      if (trail.gear >= 5 && trail.phantomSequenceActive && trail.phantomsReady) {
         float attack = getAttackProgress(trail, partialTick);
         if (!(attack <= 0.0F)) {
            BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            boolean drew = false;
            float shock = Mth.sin(attack * (float) Math.PI);

            for (int i = 0; i < 10; i++) {
               GearshiftBlueTrailClient.PhantomState state = trail.phantoms[i];
               if (state != null) {
                  float seed = trail.seed + i * 31.0F;
                  float alpha = shock * 0.98F;
                  Vec3 a = state.hold.lerp(state.strike, attack);
                  Vec3 b = state.strike.lerp(state.exit, attack);
                  drew |= addJaggedSlash(buffer, matrix, cam, a, b, 11, 0.34F, 0.02F, 0.2F, 1.0F, alpha * 0.34F, seed + 11.0F);
                  drew |= addJaggedSlash(buffer, matrix, cam, a, b, 12, 0.135F, 0.25F, 0.82F, 1.0F, alpha * 0.76F, seed + 17.0F);
                  drew |= addJaggedSlash(buffer, matrix, cam, a, b, 13, 0.046F, 0.92F, 1.0F, 1.0F, alpha, seed + 23.0F);
                  Vec3 burstCenter = state.strike;
                  drew |= addHorizontalRing(buffer, matrix, cam, burstCenter, 1.2 + attack * 4.0, 0.05F, 0.85F, 0.98F, 1.0F, alpha * 0.38F);
               }
            }

            if (trail.executionCenter != null) {
               drew |= addHorizontalRing(buffer, matrix, cam, trail.executionCenter, 4.0 + attack * 18.0, 0.16F, 0.04F, 0.24F, 1.0F, shock * 0.34F);
               drew |= addHorizontalRing(
                  buffer, matrix, cam, trail.executionCenter.add(0.0, 0.22, 0.0), 2.0 + attack * 11.0, 0.09F, 1.0F, 0.96F, 0.62F, shock * 0.72F
               );
            }

            if (drew) {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }
         }
      }
   }

   private static GearshiftBlueTrailClient.PhantomPose getPhantomPose(
      GearshiftBlueTrailClient.ActiveTrail trail, GearshiftBlueTrailClient.PhantomState state, float partialTick
   ) {
      float age = trail.phantomAge + partialTick;
      if (age < state.spawnTick) {
         return new GearshiftBlueTrailClient.PhantomPose(Vec3.ZERO, 0.0F);
      } else {
         float attack = getAttackProgress(trail, partialTick);
         float local = age - state.spawnTick;
         if (attack <= 0.0F) {
            float appear = Mth.clamp(local / 8.0F, 0.0F, 1.0F);
            float settle = Mth.clamp(local / 13.0F, 0.0F, 1.0F);
            settle = settle * settle * (3.0F - 2.0F * settle);
            float pulse = 0.84F + 0.16F * Mth.sin(time * 2.8F + state.spawnTick * 0.17F);
            float alpha = Mth.clamp(appear * pulse * 0.78F, 0.0F, 0.78F);
            Vec3 pos = state.spawn.lerp(state.hold, settle);
            return new GearshiftBlueTrailClient.PhantomPose(pos, alpha);
         } else {
            Vec3 first = state.hold.lerp(state.strike, attack);
            Vec3 second = state.strike.lerp(state.exit, attack);
            Vec3 pos = attack < 0.58F ? first : second;
            float fade = attack < 0.82F ? 1.0F : Mth.clamp(1.0F - (attack - 0.82F) / 0.18F, 0.0F, 1.0F);
            float alpha = Mth.clamp((0.96F + 0.04F * Mth.sin(time * 9.0F + state.spawnTick)) * fade, 0.0F, 0.96F);
            return new GearshiftBlueTrailClient.PhantomPose(pos, alpha);
         }
      }
   }

   private static float getAttackProgress(GearshiftBlueTrailClient.ActiveTrail trail, float partialTick) {
      float age = trail.phantomAge + partialTick;
      if (age < 78.0F) {
         return 0.0F;
      }

      float attack = Mth.clamp((age - 78.0F) / 10.0F, 0.0F, 1.0F);
      return attack * attack * (3.0F - 2.0F * attack);
   }

   private static boolean addJaggedSlash(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, int bends, float width, float r, float g, float b, float alpha, float seed
   ) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         Vec3 main = end.subtract(start);
         if (main.lengthSqr() < 1.0E-6) {
            return false;
         }

         Vec3 normalA = main.normalize().cross(new Vec3(0.0, 1.0, 0.0));
         if (normalA.lengthSqr() < 1.0E-6) {
            normalA = new Vec3(1.0, 0.0, 0.0);
         }

         normalA = normalA.normalize();
         Vec3 normalB = main.normalize().cross(normalA).normalize();
         Vec3 previous = start;
         boolean drew = false;

         for (int i = 1; i <= bends; i++) {
            float t = (float)i / bends;
            Vec3 point = start.lerp(end, t);
            if (i < bends) {
               float strength = Mth.sin(t * (float) Math.PI);
               double a = (hash(seed * 13.0F + i * 17.0F + time * 8.0F) - 0.5) * 1.55 * strength;
               double b2 = (hash(seed * 19.0F + i * 23.0F - time * 6.0F) - 0.5) * 1.1 * strength;
               point = point.add(normalA.scale(a)).add(normalB.scale(b2));
            }

            drew |= addRibbon(buffer, matrix, cam, previous, point, width * (1.0F - t * 0.16F), r, g, b, alpha * (1.0F - t * 0.14F));
            previous = point;
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addHorizontalRing(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 center, double radius, float width, float r, float g, float b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F) && !(radius <= 0.001)) {
         boolean drew = false;
         int segments = 54;
         double spin = time * 1.35 + radius * 0.17;

         for (int i = 0; i < segments; i++) {
            if (i % 5 != 2) {
               double a0 = (double)i / segments * Math.PI * 2.0 + spin;
               double a1 = (i + 0.78) / segments * Math.PI * 2.0 + spin;
               Vec3 p0 = center.add(Math.cos(a0) * radius, 0.0, Math.sin(a0) * radius);
               Vec3 p1 = center.add(Math.cos(a1) * radius, 0.0, Math.sin(a1) * radius);
               drew |= addRibbon(buffer, matrix, cam, p0, p1, width, r, g, b, alpha);
            }
         }

         return drew;
      } else {
         return false;
      }
   }

   private static boolean addVerticalBeam(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, float width, float r, float g, float b, float alpha
   ) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         boolean drew = false;

         for (int i = 0; i < 4; i++) {
            double angle = i * Math.PI * 0.5 + time * 0.9;
            Vec3 offset = new Vec3(Math.cos(angle) * 0.35, 0.0, Math.sin(angle) * 0.35);
            drew |= addRibbon(buffer, matrix, cam, start.add(offset), end.add(offset.scale(0.35)), width, r, g, b, alpha * (0.55F + i * 0.1F));
         }

         return drew;
      } else {
         return false;
      }
   }

   private static GearshiftBlueTrailClient.WalkAnimationSnapshot captureWalkAnimation(LivingEntity entity) {
      GearshiftBlueTrailClient.WalkAnimationSnapshot snapshot = new GearshiftBlueTrailClient.WalkAnimationSnapshot();
      WalkAnimationState state = entity.walkAnimation;
      snapshot.speedOld = getWalkAnimationFloat(state, "speedOld");
      snapshot.speed = getWalkAnimationFloat(state, "speed");
      snapshot.position = getWalkAnimationFloat(state, "position");
      return snapshot;
   }

   private static void applyWalkAnimation(LivingEntity entity, GearshiftBlueTrailClient.WalkAnimationSnapshot snapshot) {
      if (snapshot != null) {
         WalkAnimationState state = entity.walkAnimation;
         setWalkAnimationFloat(state, "speedOld", snapshot.speedOld);
         setWalkAnimationFloat(state, "speed", snapshot.speed);
         setWalkAnimationFloat(state, "position", snapshot.position);
      }
   }

   private static float getWalkAnimationFloat(WalkAnimationState state, String fieldName) {
      try {
         Field field = WalkAnimationState.class.getDeclaredField(fieldName);
         field.setAccessible(true);
         return field.getFloat(state);
      } catch (Throwable ignored) {
         return 0.0F;
      }
   }

   private static void setWalkAnimationFloat(WalkAnimationState state, String fieldName, float value) {
      try {
         Field field = WalkAnimationState.class.getDeclaredField(fieldName);
         field.setAccessible(true);
         field.setFloat(state, value);
      } catch (Throwable var4) {
      }
   }

   private static void trimTrail(GearshiftBlueTrailClient.ActiveTrail trail) {
      for (GearshiftBlueTrailClient.TrailPart part : GearshiftBlueTrailClient.TrailPart.values()) {
         ArrayDeque<GearshiftBlueTrailClient.TrailNode> nodes = trail.nodes.get(part);

         while (nodes.size() > getMaxNodes(trail.gear)) {
            nodes.removeLast();
         }
      }
   }

   private static void addNode(GearshiftBlueTrailClient.ActiveTrail trail, GearshiftBlueTrailClient.TrailPart part, Vec3 position) {
      ArrayDeque<GearshiftBlueTrailClient.TrailNode> nodes = trail.nodes.get(part);
      GearshiftBlueTrailClient.TrailNode first = nodes.peekFirst();
      if (first == null || first.position.distanceToSqr(position) > 1.8E-5) {
         nodes.addFirst(new GearshiftBlueTrailClient.TrailNode(position));
      }
   }

   private static void renderTrails(Matrix4f matrix, Vec3 cam, GearshiftBlueTrailClient.ActiveTrail trail, float intensity, int gear) {
      float gearScale = getGearScale(gear);
      float overdrive = gear == 5 ? 1.08F : 1.0F;
      BufferBuilder outer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drewOuter = false;

      for (GearshiftBlueTrailClient.TrailPart part : GearshiftBlueTrailClient.TrailPart.values()) {
         drewOuter |= addTrailLayer(outer, matrix, cam, trail.nodes.get(part), 0.145F * intensity * gearScale, 0.0F, 0.12F, 1.0F, 0.3F + gear * 0.024F, gear, 0);
      }

      if (drewOuter) {
         BufferUploader.drawWithShader(outer.buildOrThrow());
      }

      BufferBuilder middle = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drewMiddle = false;

      for (GearshiftBlueTrailClient.TrailPart part : GearshiftBlueTrailClient.TrailPart.values()) {
         drewMiddle |= addTrailLayer(
            middle, matrix, cam, trail.nodes.get(part), 0.088F * intensity * gearScale, 0.03F, 0.48F, 1.0F, 0.52F + gear * 0.034F, gear, 1
         );
      }

      if (drewMiddle) {
         BufferUploader.drawWithShader(middle.buildOrThrow());
      }

      BufferBuilder core = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drewCore = false;

      for (GearshiftBlueTrailClient.TrailPart part : GearshiftBlueTrailClient.TrailPart.values()) {
         drewCore |= addTrailLayer(core, matrix, cam, trail.nodes.get(part), 0.037F * intensity * overdrive, 0.7F, 0.98F, 1.0F, 0.88F + gear * 0.038F, gear, 2);
      }

      if (drewCore) {
         BufferUploader.drawWithShader(core.buildOrThrow());
      }

      if (gear >= 2) {
         BufferBuilder streaks = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drewStreaks = false;

         for (GearshiftBlueTrailClient.TrailPart part : GearshiftBlueTrailClient.TrailPart.values()) {
            drewStreaks |= addTrailStreaks(streaks, matrix, cam, trail.nodes.get(part), intensity, gear);
         }

         if (drewStreaks) {
            BufferUploader.drawWithShader(streaks.buildOrThrow());
         }
      }
   }

   private static boolean addTrailLayer(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      ArrayDeque<GearshiftBlueTrailClient.TrailNode> nodes,
      float width,
      float red,
      float green,
      float blue,
      float alphaScale,
      int gear,
      int layer
   ) {
      if (nodes.size() < 2) {
         return false;
      }

      List<GearshiftBlueTrailClient.TrailNode> list = new ArrayList<>(nodes);
      boolean drew = false;
      int size = list.size();
      int maxAge = getMaxAge(gear);
      int smoothness = getSmoothness(gear);

      for (int i = 1; i < size; i++) {
         GearshiftBlueTrailClient.TrailNode p0 = list.get(Math.max(0, i - 2));
         GearshiftBlueTrailClient.TrailNode p1 = list.get(i - 1);
         GearshiftBlueTrailClient.TrailNode p2 = list.get(i);
         GearshiftBlueTrailClient.TrailNode p3 = list.get(Math.min(size - 1, i + 1));
         Vec3 last = p1.position;

         for (int s = 1; s <= smoothness; s++) {
            float step = (float)s / smoothness;
            float progress = (i - 1 + step) / Math.max(1, size - 1);
            float chain = 1.0F - progress;
            float nodeAge = Mth.lerp(step, p1.age, p2.age);
            float age = 1.0F - nodeAge / maxAge;
            float speed = 4.8F + gear * 1.55F;
            float gearPulse = 0.9F + 0.1F * Mth.sin(time * (8.8F + gear * 1.85F) + progress * 11.0F + layer);
            float ripple = 0.9F + 0.1F * Mth.sin(time * speed + progress * 8.0F + layer * 1.7F);
            float alpha = Mth.clamp(chain * age * alphaScale * gearPulse, 0.0F, 1.0F);
            float localWidth = width * (0.3F + chain * 0.7F) * ripple;
            if (layer == 2) {
               localWidth *= gear >= 4 ? 0.86F : 0.82F;
               alpha *= gear >= 4 ? 1.12F : 1.08F;
            }

            if (gear == 5) {
               alpha *= 1.12F;
               localWidth *= 0.84F;
            }

            Vec3 current = catmullRom(p0.position, p1.position, p2.position, p3.position, step);
            if (alpha > 0.001F && localWidth > 0.001F) {
               drew |= addRibbon(buffer, matrix, cam, last, current, localWidth, red, green, blue, alpha);
            }

            last = current;
         }
      }

      return drew;
   }

   private static boolean addTrailStreaks(
      BufferBuilder buffer, Matrix4f matrix, Vec3 cam, ArrayDeque<GearshiftBlueTrailClient.TrailNode> nodes, float intensity, int gear
   ) {
      if (nodes.size() < 4) {
         return false;
      }

      List<GearshiftBlueTrailClient.TrailNode> list = new ArrayList<>(nodes);
      boolean drew = false;
      int maxAge = getMaxAge(gear);
      int size = list.size();

      for (int i = 1; i < size; i += 2) {
         GearshiftBlueTrailClient.TrailNode previous = list.get(i - 1);
         GearshiftBlueTrailClient.TrailNode current = list.get(i);
         float flash = hash(Mth.floor(time * (8.5F + gear * 1.7F)) + i * 13.37F + gear * 9.0F);
         if (gear >= 5 || flash > 0.36F) {
            float chain = 1.0F - (float)i / Math.max(1, size - 1);
            float age = 1.0F - (float)current.age / maxAge;
            float alpha = chain * age * intensity * (gear >= 5 ? 0.58F : (gear >= 4 ? 0.46F : (gear >= 3 ? 0.36F : 0.22F))) * (0.45F + flash * 0.55F);
            float width = (gear >= 5 ? 0.014F : (gear >= 4 ? 0.012F : (gear >= 3 ? 0.01F : 0.008F))) * intensity;
            drew |= addRibbon(buffer, matrix, cam, previous.position, current.position, width, 0.82F, 0.98F, 1.0F, alpha);
         }
      }

      return drew;
   }

   private static boolean addRibbon(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, Vec3 a, Vec3 b, float width, float r, float g, float blue, float alpha) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         Vec3 dir = b.subtract(a);
         if (dir.lengthSqr() < 4.0E-7) {
            return false;
         }

         Vec3 mid = a.add(b).scale(0.5);
         Vec3 toCam = cam.subtract(mid);
         if (toCam.lengthSqr() < 1.0E-6) {
            return false;
         }

         Vec3 side = dir.normalize().cross(toCam.normalize());
         if (side.lengthSqr() < 1.0E-6) {
            side = new Vec3(0.0, 1.0, 0.0);
         }

         side = side.normalize().scale(width);
         Vec3 a0 = a.subtract(side).subtract(cam);
         Vec3 a1 = a.add(side).subtract(cam);
         Vec3 b1 = b.add(side).subtract(cam);
         Vec3 b0 = b.subtract(side).subtract(cam);
         buffer.addVertex(matrix, (float)a0.x, (float)a0.y, (float)a0.z).setColor(r, g, blue, alpha);
         buffer.addVertex(matrix, (float)a1.x, (float)a1.y, (float)a1.z).setColor(r, g, blue, alpha);
         buffer.addVertex(matrix, (float)b1.x, (float)b1.y, (float)b1.z).setColor(r, g, blue, alpha * 0.64F);
         buffer.addVertex(matrix, (float)b0.x, (float)b0.y, (float)b0.z).setColor(r, g, blue, alpha * 0.64F);
         return true;
      } else {
         return false;
      }
   }

   private static Vec3 catmullRom(Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, float t) {
      double t2 = t * t;
      double t3 = t2 * t;
      double x = 0.5 * (2.0 * p1.x + (-p0.x + p2.x) * t + (2.0 * p0.x - 5.0 * p1.x + 4.0 * p2.x - p3.x) * t2 + (-p0.x + 3.0 * p1.x - 3.0 * p2.x + p3.x) * t3);
      double y = 0.5 * (2.0 * p1.y + (-p0.y + p2.y) * t + (2.0 * p0.y - 5.0 * p1.y + 4.0 * p2.y - p3.y) * t2 + (-p0.y + 3.0 * p1.y - 3.0 * p2.y + p3.y) * t3);
      double z = 0.5 * (2.0 * p1.z + (-p0.z + p2.z) * t + (2.0 * p0.z - 5.0 * p1.z + 4.0 * p2.z - p3.z) * t2 + (-p0.z + 3.0 * p1.z - 3.0 * p2.z + p3.z) * t3);
      return new Vec3(x, y, z);
   }

   private static float getGearIntensity(int gear) {
      return switch (gear) {
         case 0 -> 0.72F;
         case 1 -> 0.9F;
         case 2 -> 1.1F;
         case 3 -> 1.22F;
         case 4 -> 1.34F;
         case 5 -> 1.46F;
         default -> 0.9F;
      };
   }

   private static float getGearScale(int gear) {
      return switch (gear) {
         case 0 -> 0.92F;
         case 1 -> 1.0F;
         case 2 -> 1.08F;
         case 3 -> 1.13F;
         case 4 -> 1.1F;
         case 5 -> 1.04F;
         default -> 1.0F;
      };
   }

   private static int getMaxAge(int gear) {
      return switch (gear) {
         case 0 -> 24;
         case 1 -> 28;
         case 2 -> 32;
         case 3 -> 36;
         case 4 -> 40;
         case 5 -> 44;
         default -> 28;
      };
   }

   private static int getMaxNodes(int gear) {
      return switch (gear) {
         case 0 -> 34;
         case 1 -> 40;
         case 2 -> 46;
         case 3 -> 52;
         case 4 -> 58;
         case 5 -> 64;
         default -> 64;
      };
   }

   private static int getSmoothness(int gear) {
      return switch (gear) {
         case 0 -> 3;
         case 1 -> 3;
         case 2 -> 4;
         case 3 -> 4;
         case 4 -> 5;
         case 5 -> 6;
         default -> 3;
      };
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }

   private static final class ActiveTrail {
      private final EnumMap<GearshiftBlueTrailClient.TrailPart, ArrayDeque<GearshiftBlueTrailClient.TrailNode>> nodes = new EnumMap<>(
         GearshiftBlueTrailClient.TrailPart.class
      );
      private final GearshiftBlueTrailClient.PhantomState[] phantoms = new GearshiftBlueTrailClient.PhantomState[10];
      private int life;
      private int gear;
      private int targetId = -1;
      private float seed;
      private int phantomAge;
      private boolean phantomSequenceActive;
      private boolean phantomsReady;
      private Vec3 executionCenter;

      private ActiveTrail() {
         for (GearshiftBlueTrailClient.TrailPart part : GearshiftBlueTrailClient.TrailPart.values()) {
            this.nodes.put(part, new ArrayDeque<>());
         }
      }

      private void tickNodes() {
         for (ArrayDeque<GearshiftBlueTrailClient.TrailNode> list : this.nodes.values()) {
            Iterator<GearshiftBlueTrailClient.TrailNode> iterator = list.iterator();

            while (iterator.hasNext()) {
               GearshiftBlueTrailClient.TrailNode node = iterator.next();
               if (node.age > GearshiftBlueTrailClient.getMaxAge(this.gear)) {
                  iterator.remove();
               } else {
                  node.age++;
               }
            }
         }
      }
   }

   private record PhantomPose(Vec3 position, float alpha) {
   }

   private static final class PhantomState {
      private final Vec3 spawn;
      private final Vec3 hold;
      private final Vec3 strike;
      private final Vec3 exit;
      private final float yRot;
      private final int spawnTick;

      private PhantomState(Vec3 spawn, Vec3 hold, Vec3 strike, Vec3 exit, float yRot, int spawnTick) {
         this.spawn = spawn;
         this.hold = hold;
         this.strike = strike;
         this.exit = exit;
         this.yRot = yRot;
         this.spawnTick = spawnTick;
      }
   }

   private static final class TrailNode {
      private final Vec3 position;
      private int age;

      private TrailNode(Vec3 position) {
         this.position = position;
      }
   }

   private enum TrailPart {
      LEFT_ARM,
      RIGHT_ARM,
      LEFT_LEG,
      RIGHT_LEG;
   }

   private static final class WalkAnimationSnapshot {
      private float speedOld;
      private float speed;
      private float position;
   }
}
