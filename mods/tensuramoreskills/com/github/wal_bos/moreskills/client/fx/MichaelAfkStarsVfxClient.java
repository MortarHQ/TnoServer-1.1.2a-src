package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.client.renderer.MichaelAfkAnimationRenderer;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class MichaelAfkStarsVfxClient {
   private static final float HAND_MOVE_START = 0.7F;
   private static final float HAND_MOVE_END = 3.34F;
   private static final float RAIN_START = 1.82F;
   private static final float EFFECT_END = 31.358F;
   private static final long HAND_SAMPLE_INTERVAL = 10L;
   private static final long HAND_TRAIL_LIFE = 920L;
   private static final long RAIN_INTERVAL = 306L;
   private static final int MAX_METEORS = 20;
   private static final MichaelAfkStarsVfxClient.LocalPoint[] HAND_PATH = new MichaelAfkStarsVfxClient.LocalPoint[]{
      new MichaelAfkStarsVfxClient.LocalPoint(0.26, 1.12, 0.07, 0.7F),
      new MichaelAfkStarsVfxClient.LocalPoint(0.46, 1.18, 0.11, 0.88F),
      new MichaelAfkStarsVfxClient.LocalPoint(0.62, 1.31, 0.12, 1.1F),
      new MichaelAfkStarsVfxClient.LocalPoint(0.68, 1.49, 0.1, 1.34F),
      new MichaelAfkStarsVfxClient.LocalPoint(0.48, 1.68, 0.05, 1.6F),
      new MichaelAfkStarsVfxClient.LocalPoint(0.12, 1.79, 0.0, 1.88F),
      new MichaelAfkStarsVfxClient.LocalPoint(-0.22, 1.7, -0.04, 2.16F),
      new MichaelAfkStarsVfxClient.LocalPoint(-0.33, 1.49, -0.01, 2.42F),
      new MichaelAfkStarsVfxClient.LocalPoint(-0.18, 1.29, 0.03, 2.72F),
      new MichaelAfkStarsVfxClient.LocalPoint(0.05, 1.19, 0.05, 3.04F),
      new MichaelAfkStarsVfxClient.LocalPoint(0.18, 1.18, 0.04, 3.34F)
   };
   private static final List<MichaelAfkStarsVfxClient.Meteor> METEORS = new ArrayList<>();
   private static final Map<UUID, MichaelAfkStarsVfxClient.PlayerFxState> PLAYER_STATES = new HashMap<>();
   private static boolean initialized;

   private MichaelAfkStarsVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MichaelAfkStarsVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(MichaelAfkStarsVfxClient::render);
      }
   }

   private static void tick(Post event) {
      if (!ClientVfxGate.allowed("michael_vfx")) {
         METEORS.clear();
         PLAYER_STATES.clear();
      } else {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level == null) {
            METEORS.clear();
            PLAYER_STATES.clear();
         } else {
            long now = Util.getMillis();
            Set<UUID> seen = new HashSet<>();
            Set<UUID> active = new HashSet<>();

            for (AbstractClientPlayer player : minecraft.level.players()) {
               UUID id = player.getUUID();
               seen.add(id);
               MichaelAfkStarsVfxClient.PlayerFxState state = PLAYER_STATES.computeIfAbsent(id, ignored -> new MichaelAfkStarsVfxClient.PlayerFxState());
               if (!MichaelAfkAnimationRenderer.isAfkAnimationActive(player)) {
                  state.reset();
               } else {
                  float animationTime = MichaelAfkAnimationRenderer.getAnimationTimeSeconds(player);
                  if (!(animationTime < 0.0F) && !(animationTime > 31.358F)) {
                     active.add(id);
                     MichaelAfkStarsVfxClient.Basis basis = MichaelAfkStarsVfxClient.Basis.of(player);
                     Vec3 hand = handAnchor(basis, animationTime);
                     updateHandTrail(state, hand, animationTime, now);
                     emitRain(player, state, basis, hand, animationTime, now);
                  } else {
                     state.reset();
                  }
               }
            }

            PLAYER_STATES.entrySet().removeIf(entry -> !seen.contains(entry.getKey()));
            Iterator<MichaelAfkStarsVfxClient.Meteor> iterator = METEORS.iterator();

            while (iterator.hasNext()) {
               MichaelAfkStarsVfxClient.Meteor meteor = iterator.next();
               if (meteor.tick(now, active.contains(meteor.ownerId))) {
                  iterator.remove();
               }
            }

            while (METEORS.size() > 20) {
               METEORS.remove(0);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (ClientVfxGate.allowed("michael_vfx") && event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && (!METEORS.isEmpty() || !PLAYER_STATES.isEmpty())) {
            long now = Util.getMillis();
            Vec3 camera = MichaelAfkStarsVfxClient.H.camera();
            Matrix4f matrix = event.getPoseStack().last().pose();
            BufferBuilder buffer = MichaelAfkStarsVfxClient.H.begin();
            boolean wrote = false;

            for (AbstractClientPlayer player : minecraft.level.players()) {
               if (MichaelAfkAnimationRenderer.isAfkAnimationActive(player)) {
                  float animationTime = MichaelAfkAnimationRenderer.getAnimationTimeSeconds(player);
                  if (!(animationTime < 0.0F) && !(animationTime > 31.358F)) {
                     wrote |= renderAreaGlow(player, animationTime, MichaelAfkStarsVfxClient.Basis.of(player), buffer, matrix, camera, now);
                  }
               }
            }

            for (MichaelAfkStarsVfxClient.Meteor meteor : METEORS) {
               wrote |= meteor.render(buffer, matrix, camera, now);
            }

            for (MichaelAfkStarsVfxClient.PlayerFxState state : PLAYER_STATES.values()) {
               wrote |= renderHandTrail(state, buffer, matrix, camera, now);
            }

            MichaelAfkStarsVfxClient.H.finish(buffer, wrote);
         }
      }
   }

   private static void updateHandTrail(MichaelAfkStarsVfxClient.PlayerFxState state, Vec3 hand, float animationTime, long now) {
      state.trimHand(now);
      state.animationTime = animationTime;
      if (animationTime < 0.7F) {
         state.handPoints.clear();
         state.lastHandPosition = null;
         state.sweepDirection = new Vec3(1.0, 0.0, 0.0);
         state.lastHandSample = 0L;
         state.releaseTriggered = false;
      } else if (!(animationTime > 3.34F)) {
         if (now - state.lastHandSample >= 10L) {
            state.lastHandSample = now;
            if (!state.handPoints.isEmpty()) {
               MichaelAfkStarsVfxClient.HandPoint last = state.handPoints.get(state.handPoints.size() - 1);
               if (last.position.distanceToSqr(hand) < 1.0E-5) {
                  return;
               }
            }

            if (state.lastHandPosition != null) {
               Vec3 movement = hand.subtract(state.lastHandPosition);
               if (movement.lengthSqr() > 1.0E-6) {
                  state.sweepDirection = movement.normalize();
               }
            }

            state.lastHandPosition = hand;
            state.handPoints.add(new MichaelAfkStarsVfxClient.HandPoint(hand, now));

            while (state.handPoints.size() > 42) {
               state.handPoints.remove(0);
            }
         }
      }
   }

   private static boolean renderAreaGlow(
      AbstractClientPlayer player, float animationTime, MichaelAfkStarsVfxClient.Basis basis, BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now
   ) {
      float fadeIn = MichaelAfkStarsVfxClient.H.smooth(Mth.clamp((animationTime - 1.6400001F) / 0.62F, 0.0F, 1.0F));
      float fadeOut = 1.0F - MichaelAfkStarsVfxClient.H.smooth(Mth.clamp((animationTime - 30.198F) / 1.04F, 0.0F, 1.0F));
      float strength = fadeIn * fadeOut;
      if (strength <= 0.001F) {
         return false;
      }

      long seed = player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits();
      Vec3 base = player.position();
      Vec3 lowCenter = base.add(0.0, 1.4, 0.0);
      Vec3 midCenter = base.add(0.0, 3.1, 0.0);
      Vec3 highCenter = base.add(0.0, 5.2, 0.0);
      boolean wrote = false;
      wrote |= MichaelAfkStarsVfxClient.H.glow(buffer, matrix, MichaelAfkStarsVfxClient.H.rel(lowCenter, camera), 6.8F, 0.0F, 92, 10, 208, strength * 0.034F);
      wrote |= MichaelAfkStarsVfxClient.H.glow(buffer, matrix, MichaelAfkStarsVfxClient.H.rel(midCenter, camera), 5.65F, 0.0F, 132, 18, 236, strength * 0.048F);
      wrote |= MichaelAfkStarsVfxClient.H.glow(buffer, matrix, MichaelAfkStarsVfxClient.H.rel(highCenter, camera), 4.3F, 0.0F, 255, 82, 238, strength * 0.05F);
      wrote |= MichaelAfkStarsVfxClient.H.glow(
         buffer, matrix, MichaelAfkStarsVfxClient.H.rel(base.add(0.0, 2.2, 0.0), camera), 4.3F, 0.0F, 255, 128, 255, strength * 0.03F
      );
      double outerRadius = 4.55;
      double innerRadius = 2.85;

      for (int i = 0; i < 8; i++) {
         double angle = now * 1.8E-4 + i * (Math.PI / 4);
         Vec3 radial = basis.right.scale(Math.cos(angle)).add(basis.forward.scale(Math.sin(angle)));
         Vec3 outerPoint = base.add(radial.scale(outerRadius)).add(0.0, 2.55 + Math.sin(angle * 1.6) * 0.25, 0.0);
         Vec3 innerPoint = base.add(radial.scale(innerRadius)).add(0.0, 4.85 + Math.cos(angle * 1.4) * 0.18, 0.0);
         wrote |= MichaelAfkStarsVfxClient.H.glow(
            buffer, matrix, MichaelAfkStarsVfxClient.H.rel(outerPoint, camera), 1.85F, 0.0F, 140, 24, 240, strength * 0.03F
         );
         wrote |= MichaelAfkStarsVfxClient.H.glow(
            buffer, matrix, MichaelAfkStarsVfxClient.H.rel(innerPoint, camera), 1.15F, 0.0F, 255, 92, 244, strength * 0.05F
         );
      }

      for (int i = 0; i < 30; i++) {
         float h0 = MichaelAfkStarsVfxClient.H.hash(seed, 1100 + i * 17);
         float h1 = MichaelAfkStarsVfxClient.H.hash(seed, 2100 + i * 19);
         float h2 = MichaelAfkStarsVfxClient.H.hash(seed, 3100 + i * 23);
         float h3 = MichaelAfkStarsVfxClient.H.hash(seed, 4100 + i * 29);
         float h4 = MichaelAfkStarsVfxClient.H.hash(seed, 5100 + i * 31);
         double angle = h0 * (float) (Math.PI * 2) + now * (5.0E-5 + h1 * 8.0E-5);
         double radius = 0.4 + Math.sqrt(h2) * 4.75;
         double height = 0.7 + h3 * 5.7;
         Vec3 radial = basis.right.scale(Math.cos(angle)).add(basis.forward.scale(Math.sin(angle)));
         Vec3 point = base.add(radial.scale(radius)).add(0.0, height, 0.0);
         Vec3 relativePoint = MichaelAfkStarsVfxClient.H.rel(point, camera);
         float flicker = 0.5F + 0.5F * Mth.sin((float)(now * (0.0016 + h4 * 0.0015) + i * 0.77 + h1 * 5.7));
         float appear = MichaelAfkStarsVfxClient.H.smooth(Mth.clamp((flicker - 0.66F) / 0.34F, 0.0F, 1.0F));
         if (!(appear <= 0.001F)) {
            int[] color = palette((int)(h0 * 192.0F) + i * 9);
            float moteSize = 0.018F + h2 * 0.02F;
            float glowSize = 0.1F + h3 * 0.08F;
            float alpha = strength * appear * (0.05F + h4 * 0.055F);
            wrote |= MichaelAfkStarsVfxClient.H.glow(buffer, matrix, relativePoint, glowSize, 0.0F, color[0], color[1], color[2], alpha * 0.9F);
            if (i % 3 == 0) {
               wrote |= MichaelAfkStarsVfxClient.H.shard(buffer, matrix, relativePoint, moteSize * 1.08F, 0.0F, color[0], color[1], color[2], alpha * 1.18F);
            } else if ((i & 1) == 0) {
               wrote |= MichaelAfkStarsVfxClient.H.star(buffer, matrix, relativePoint, moteSize * 0.96F, 0.0F, color[0], color[1], color[2], alpha * 1.02F);
            } else if (i % 5 == 0) {
               wrote |= MichaelAfkStarsVfxClient.H.shard(buffer, matrix, relativePoint, moteSize * 0.82F, 0.0F, color[0], color[1], color[2], alpha * 0.92F);
            }
         }
      }

      return wrote;
   }

   private static boolean renderHandTrail(MichaelAfkStarsVfxClient.PlayerFxState state, BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
      state.trimHand(now);
      List<MichaelAfkStarsVfxClient.HandPoint> points = state.handPoints;
      if (points.isEmpty()) {
         return false;
      }

      float castDistance = Math.abs(state.animationTime - 1.82F);
      float castStrength = 1.0F - MichaelAfkStarsVfxClient.H.smooth(Mth.clamp(castDistance / 0.52F, 0.0F, 1.0F));
      boolean wrote = false;
      if (points.size() >= 2) {
         wrote |= renderHandRibbon(points, buffer, matrix, camera, now, 0.0F, 0.0F, 0.116F + castStrength * 0.032F, 82, 8, 212, 0.16F + castStrength * 0.08F);
         wrote |= renderHandRibbon(points, buffer, matrix, camera, now, 0.0F, 0.0F, 0.07F + castStrength * 0.024F, 132, 18, 244, 0.34F + castStrength * 0.14F);
         wrote |= renderHandRibbon(points, buffer, matrix, camera, now, 0.0F, 0.0F, 0.034F + castStrength * 0.014F, 244, 74, 255, 0.72F + castStrength * 0.18F);
         wrote |= renderHandRibbon(points, buffer, matrix, camera, now, 0.0F, 0.0F, 0.012F, 255, 180, 255, 1.0F);
         wrote |= renderHandDetails(points, buffer, matrix, camera, now, castStrength);
         wrote |= renderHandSweepBurst(state, buffer, matrix, camera, now, castStrength);
      }

      MichaelAfkStarsVfxClient.HandPoint head = points.get(points.size() - 1);
      float headAge = Mth.clamp((float)(now - head.bornAtMillis) / 320.0F, 0.0F, 1.0F);
      float headFade = 1.0F - MichaelAfkStarsVfxClient.H.smooth(headAge);
      if (headFade > 0.001F) {
         float wave = 0.5F + 0.5F * Mth.sin((float)now * 0.026F + 1.2F);
         float pulse = 0.98F + wave * 0.56F + castStrength * 0.34F;
         Vec3 center = MichaelAfkStarsVfxClient.H.rel(head.position, camera);
         wrote |= MichaelAfkStarsVfxClient.H.glow(buffer, matrix, center, 0.39F * pulse, 0.0F, 112, 14, 244, headFade * (0.22F + castStrength * 0.14F));
         wrote |= MichaelAfkStarsVfxClient.H.glow(buffer, matrix, center, 0.254F * pulse, 0.0F, 255, 58, 236, headFade * (0.18F + castStrength * 0.1F));
         wrote |= MichaelAfkStarsVfxClient.H.starBurst(buffer, matrix, center, 0.17F * pulse, -0.08F, 255, 70, 242, headFade);
         wrote |= MichaelAfkStarsVfxClient.H.star(buffer, matrix, center, 0.086F * pulse, (float) (Math.PI / 4), 198, 112, 255, headFade * 0.78F);
         wrote |= MichaelAfkStarsVfxClient.H.diamond(buffer, matrix, center, 0.06F * pulse, 0.0F, 244, 140, 255, headFade * 0.94F);
      }

      return wrote;
   }

   private static boolean renderHandRibbon(
      List<MichaelAfkStarsVfxClient.HandPoint> points,
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 camera,
      long now,
      float phase,
      float amplitude,
      float width,
      int red,
      int green,
      int blue,
      float opacity
   ) {
      boolean wrote = false;
      int lastIndex = points.size() - 1;
      int subdivisions = 5;

      for (int i = 0; i < lastIndex; i++) {
         MichaelAfkStarsVfxClient.HandPoint first = points.get(i);
         MichaelAfkStarsVfxClient.HandPoint second = points.get(i + 1);
         float firstLifeBase = 1.0F - Mth.clamp((float)(now - first.bornAtMillis) / 920.0F, 0.0F, 1.0F);
         float secondLifeBase = 1.0F - Mth.clamp((float)(now - second.bornAtMillis) / 920.0F, 0.0F, 1.0F);

         for (int step = 0; step < subdivisions; step++) {
            float firstLocal = (float)step / subdivisions;
            float secondLocal = (float)(step + 1) / subdivisions;
            float firstProgress = (i + firstLocal) / Math.max(1, lastIndex);
            float secondProgress = (i + secondLocal) / Math.max(1, lastIndex);
            float firstLife = Mth.lerp(firstLocal, firstLifeBase, secondLifeBase);
            float secondLife = Mth.lerp(secondLocal, firstLifeBase, secondLifeBase);
            Vec3 firstPosition = sampleHandCurve(points, i, firstLocal, phase, amplitude);
            Vec3 secondPosition = sampleHandCurve(points, i, secondLocal, phase, amplitude);
            float firstAlpha = MichaelAfkStarsVfxClient.H.smooth(firstLife) * opacity * (0.06F + MichaelAfkStarsVfxClient.H.smooth(firstProgress) * 0.94F);
            float secondAlpha = MichaelAfkStarsVfxClient.H.smooth(secondLife) * opacity * (0.06F + MichaelAfkStarsVfxClient.H.smooth(secondProgress) * 0.94F);
            float firstTaper = 0.16F + MichaelAfkStarsVfxClient.H.smooth(firstProgress) * 0.84F;
            float secondTaper = 0.16F + MichaelAfkStarsVfxClient.H.smooth(secondProgress) * 0.84F;
            wrote |= MichaelAfkStarsVfxClient.H.ribbon(
               buffer,
               matrix,
               MichaelAfkStarsVfxClient.H.rel(firstPosition, camera),
               MichaelAfkStarsVfxClient.H.rel(secondPosition, camera),
               width * firstTaper,
               width * secondTaper,
               red,
               green,
               blue,
               firstAlpha,
               secondAlpha
            );
         }
      }

      return wrote;
   }

   private static boolean renderHandDetails(
      List<MichaelAfkStarsVfxClient.HandPoint> points, BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now, float castStrength
   ) {
      boolean wrote = false;
      int lastIndex = points.size() - 1;
      int firstIndex = Math.max(1, lastIndex - 22);

      for (int i = firstIndex; i < lastIndex; i += 2) {
         MichaelAfkStarsVfxClient.HandPoint point = points.get(i);
         float life = 1.0F - Mth.clamp((float)(now - point.bornAtMillis) / 920.0F, 0.0F, 1.0F);
         float progress = (float)i / Math.max(1, lastIndex);
         float alpha = MichaelAfkStarsVfxClient.H.smooth(life) * (0.12F + MichaelAfkStarsVfxClient.H.smooth(progress) * 0.8F) * (1.0F + castStrength * 0.24F);
         if (!(alpha <= 0.001F)) {
            long seed = point.bornAtMillis ^ i * 91313L;
            float h0 = MichaelAfkStarsVfxClient.H.hash(seed, 31);
            float h1 = MichaelAfkStarsVfxClient.H.hash(seed, 47);
            float size = 0.012F + h0 * 0.016F;
            Vec3 center = MichaelAfkStarsVfxClient.H.rel(point.position.add(0.0, (h1 - 0.5F) * 0.028, 0.0), camera);
            int[] color = palette(i + (int)(h0 * 12.0F));
            wrote |= MichaelAfkStarsVfxClient.H.glow(buffer, matrix, center, size * 2.9F, 0.0F, 116, 16, 244, alpha * 0.13F);
            if ((i & 1) == 0) {
               wrote |= MichaelAfkStarsVfxClient.H.star(buffer, matrix, center, size * 1.1F, 0.0F, color[0], color[1], color[2], alpha);
            } else {
               wrote |= MichaelAfkStarsVfxClient.H.shard(buffer, matrix, center, size * 1.04F, 0.0F, color[0], color[1], color[2], alpha * 0.92F);
            }
         }
      }

      return wrote;
   }

   private static boolean renderHandSweepBurst(
      MichaelAfkStarsVfxClient.PlayerFxState state, BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now, float castStrength
   ) {
      if (!(castStrength <= 0.04F) && !state.handPoints.isEmpty()) {
         MichaelAfkStarsVfxClient.HandPoint headPoint = state.handPoints.get(state.handPoints.size() - 1);
         Vec3 sweep = state.sweepDirection;
         if (sweep.lengthSqr() < 1.0E-6) {
            return false;
         }

         sweep = sweep.normalize();
         Vec3 head = MichaelAfkStarsVfxClient.H.rel(headPoint.position, camera);
         Vec3 back = MichaelAfkStarsVfxClient.H.rel(headPoint.position.subtract(sweep.scale(0.78 + castStrength * 0.84)), camera);
         Vec3 farBack = MichaelAfkStarsVfxClient.H.rel(headPoint.position.subtract(sweep.scale(1.28 + castStrength * 1.36)), camera);
         float pulse = 0.9F + 0.1F * Mth.sin((float)now * 0.03F);
         boolean wrote = MichaelAfkStarsVfxClient.H.ribbon(
            buffer, matrix, head, back, 0.136F + castStrength * 0.054F, 0.016F, 104, 12, 230, 0.18F * castStrength, 0.0F
         );
         wrote |= MichaelAfkStarsVfxClient.H.ribbon(
            buffer, matrix, head, farBack, 0.086F + castStrength * 0.04F, 0.0F, 210, 46, 255, 0.68F * castStrength, 0.0F
         );
         wrote |= MichaelAfkStarsVfxClient.H.ribbon(buffer, matrix, head, back, 0.034F + castStrength * 0.016F, 0.0F, 255, 122, 255, 0.98F * castStrength, 0.0F);
         wrote |= MichaelAfkStarsVfxClient.H.glow(
            buffer, matrix, head, 0.34F * pulse * (0.74F + castStrength * 0.42F), 0.0F, 166, 24, 250, 0.14F + castStrength * 0.12F
         );
         return wrote
            | MichaelAfkStarsVfxClient.H.starBurst(
               buffer, matrix, head, 0.082F * pulse * (0.94F + castStrength * 0.38F), 0.0F, 255, 76, 246, 0.64F + castStrength * 0.34F
            );
      } else {
         return false;
      }
   }

   private static Vec3 sampleHandCurve(List<MichaelAfkStarsVfxClient.HandPoint> points, int segment, float progress, float phase, float amplitude) {
      int lastIndex = points.size() - 1;
      int firstIndex = Math.max(0, segment - 1);
      int secondIndex = segment;
      int thirdIndex = Math.min(lastIndex, segment + 1);
      int fourthIndex = Math.min(lastIndex, segment + 2);
      Vec3 first = offsetHandPoint(points, firstIndex, (float)firstIndex / Math.max(1, lastIndex), phase, amplitude);
      Vec3 second = offsetHandPoint(points, secondIndex, (float)secondIndex / Math.max(1, lastIndex), phase, amplitude);
      Vec3 third = offsetHandPoint(points, thirdIndex, (float)thirdIndex / Math.max(1, lastIndex), phase, amplitude);
      Vec3 fourth = offsetHandPoint(points, fourthIndex, (float)fourthIndex / Math.max(1, lastIndex), phase, amplitude);
      return new Vec3(
         catmull(first.x, second.x, third.x, fourth.x, progress),
         catmull(first.y, second.y, third.y, fourth.y, progress),
         catmull(first.z, second.z, third.z, fourth.z, progress)
      );
   }

   private static Vec3 offsetHandPoint(List<MichaelAfkStarsVfxClient.HandPoint> points, int index, float progress, float phase, float amplitude) {
      Vec3 current = points.get(index).position;
      if (amplitude <= 1.0E-4F) {
         return current;
      }

      Vec3 previous = points.get(Math.max(0, index - 1)).position;
      Vec3 next = points.get(Math.min(points.size() - 1, index + 1)).position;
      Vec3 tangent = next.subtract(previous);
      if (tangent.lengthSqr() < 1.0E-8) {
         tangent = new Vec3(1.0, 0.0, 0.0);
      } else {
         tangent = tangent.normalize();
      }

      Vec3 side = new Vec3(-tangent.z, 0.0, tangent.x);
      if (side.lengthSqr() < 1.0E-8) {
         side = new Vec3(1.0, 0.0, 0.0);
      } else {
         side = side.normalize();
      }

      float envelope = Mth.sin((float) Math.PI * progress);
      float sideWave = Mth.sin(progress * (float) Math.PI * 1.6F + phase);
      float upWave = Mth.cos(progress * (float) Math.PI * 1.26F + phase * 0.8F);
      return current.add(side.scale(sideWave * amplitude * envelope)).add(0.0, upWave * amplitude * 0.58F * envelope, 0.0);
   }

   private static void emitRain(
      AbstractClientPlayer player, MichaelAfkStarsVfxClient.PlayerFxState state, MichaelAfkStarsVfxClient.Basis basis, Vec3 hand, float animationTime, long now
   ) {
      if (!(animationTime < 1.82F) && !(animationTime > 31.358F)) {
         if (!state.releaseTriggered) {
            state.releaseTriggered = true;
            state.lastRainEmit = now;
            emitOpeningBurst(player, state, basis, hand, now);
         }

         if (now - state.lastRainEmit >= 306L) {
            state.lastRainEmit = now;
            float fadeIn = MichaelAfkStarsVfxClient.H.smooth(Mth.clamp((animationTime - 1.82F) / 0.52F, 0.0F, 1.0F));
            float fadeOut = 1.0F - MichaelAfkStarsVfxClient.H.smooth(Mth.clamp((animationTime - 30.278F) / 1.02F, 0.0F, 1.0F));
            if (!(fadeIn * fadeOut <= 0.01F)) {
               long seed = player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits() ^ now / 13L;
               Vec3 center = player.position();
               Vec3 fallDirection = basis.right.scale(-0.32).add(basis.forward.scale(0.1)).add(0.0, -1.0, 0.0).normalize();
               Vec3 trailReference = basis.forward;
               int count = MichaelAfkStarsVfxClient.H.hash(seed, 17) > 0.74F ? 2 : 1;

               for (int i = 0; i < count; i++) {
                  float h0 = MichaelAfkStarsVfxClient.H.hash(seed, 1000 + i * 37);
                  float h1 = MichaelAfkStarsVfxClient.H.hash(seed, 2000 + i * 41);
                  float h2 = MichaelAfkStarsVfxClient.H.hash(seed, 3000 + i * 43);
                  float h3 = MichaelAfkStarsVfxClient.H.hash(seed, 4000 + i * 47);
                  float h4 = MichaelAfkStarsVfxClient.H.hash(seed, 5000 + i * 53);
                  float h5 = MichaelAfkStarsVfxClient.H.hash(seed, 6000 + i * 59);
                  float h6 = MichaelAfkStarsVfxClient.H.hash(seed, 7000 + i * 61);
                  double angle = h0 * (float) (Math.PI * 2);
                  double radius = 0.6 + Math.sqrt(h1) * 2.72;
                  Vec3 radial = basis.right.scale(Math.cos(angle)).add(basis.forward.scale(Math.sin(angle)));
                  Vec3 position = center.add(radial.scale(radius)).add(0.0, 4.55 + h2 * 2.05, 0.0);
                  boolean accent = h5 > 0.78F;
                  float size = accent ? 0.132F + h3 * 0.022F : 0.098F + h3 * 0.019F;
                  float trailLength = accent ? 3.72F + h1 * 0.56F : 3.04F + h1 * 0.54F;
                  long life = 3400L + (long)(h6 * 620.0F);
                  float speed = 1.18F + h4 * 0.24F;
                  float acceleration = 0.075F + h3 * 0.05F;
                  float drift = 0.018F + h6 * 0.022F;
                  int[] color = palette((int)(h5 * 192.0F) + i * 13);
                  METEORS.add(
                     new MichaelAfkStarsVfxClient.Meteor(
                        player.getUUID(),
                        position,
                        fallDirection,
                        trailReference,
                        size,
                        trailLength,
                        life,
                        speed,
                        acceleration,
                        drift,
                        color[0],
                        color[1],
                        color[2],
                        accent,
                        seed + i * 7919L,
                        now,
                        false
                     )
                  );
               }
            }
         }
      }
   }

   private static void emitOpeningBurst(
      AbstractClientPlayer player, MichaelAfkStarsVfxClient.PlayerFxState state, MichaelAfkStarsVfxClient.Basis basis, Vec3 hand, long now
   ) {
      Vec3 center = player.position();
      Vec3 fallDirection = basis.right.scale(-0.32).add(basis.forward.scale(0.1)).add(0.0, -1.0, 0.0).normalize();
      Vec3 trailReference = basis.forward;
      long seed = player.getUUID().getLeastSignificantBits() ^ now;

      for (int i = 0; i < 3; i++) {
         float h0 = MichaelAfkStarsVfxClient.H.hash(seed, 9000 + i * 29);
         float h1 = MichaelAfkStarsVfxClient.H.hash(seed, 9100 + i * 31);
         float h2 = MichaelAfkStarsVfxClient.H.hash(seed, 9200 + i * 37);
         float h3 = MichaelAfkStarsVfxClient.H.hash(seed, 9300 + i * 41);
         double lane = i - 1.0;
         Vec3 position = center.add(basis.right.scale(lane * 0.74 + (h0 - 0.5) * 0.34))
            .add(basis.forward.scale((h1 - 0.5) * 2.1))
            .add(0.0, 4.95 + h2 * 1.12, 0.0);
         int[] color = palette(30 + i * 17 + (int)(h0 * 48.0F));
         METEORS.add(
            new MichaelAfkStarsVfxClient.Meteor(
               player.getUUID(),
               position,
               fallDirection,
               trailReference,
               0.146F + h1 * 0.022F,
               3.66F + h0 * 0.48F,
               3600L + (long)(h2 * 420.0F),
               1.22F + h3 * 0.16F,
               0.09F,
               0.02F + h1 * 0.016F,
               color[0],
               color[1],
               color[2],
               true,
               seed + i * 677L,
               now,
               true
            )
         );
      }
   }

   private static Vec3 handAnchor(MichaelAfkStarsVfxClient.Basis basis, float animationTime) {
      MichaelAfkStarsVfxClient.LocalPoint point = animationTime <= 3.34F ? sampleHandPath(animationTime) : HAND_PATH[HAND_PATH.length - 1];
      return basis.origin.add(basis.right.scale(point.right)).add(0.0, point.up, 0.0).add(basis.forward.scale(point.forward));
   }

   private static MichaelAfkStarsVfxClient.LocalPoint sampleHandPath(float time) {
      if (time <= HAND_PATH[0].time) {
         return HAND_PATH[0];
      }

      for (int i = 1; i < HAND_PATH.length; i++) {
         MichaelAfkStarsVfxClient.LocalPoint previous = HAND_PATH[i - 1];
         MichaelAfkStarsVfxClient.LocalPoint current = HAND_PATH[i];
         if (time <= current.time) {
            float progress = Mth.clamp((time - previous.time) / Math.max(1.0E-4F, current.time - previous.time), 0.0F, 1.0F);
            MichaelAfkStarsVfxClient.LocalPoint p0 = HAND_PATH[Math.max(0, i - 2)];
            MichaelAfkStarsVfxClient.LocalPoint p1 = previous;
            MichaelAfkStarsVfxClient.LocalPoint p2 = current;
            MichaelAfkStarsVfxClient.LocalPoint p3 = HAND_PATH[Math.min(HAND_PATH.length - 1, i + 1)];
            return new MichaelAfkStarsVfxClient.LocalPoint(
               catmull(p0.right, p1.right, p2.right, p3.right, progress),
               catmull(p0.up, p1.up, p2.up, p3.up, progress),
               catmull(p0.forward, p1.forward, p2.forward, p3.forward, progress),
               time
            );
         }
      }

      return HAND_PATH[HAND_PATH.length - 1];
   }

   private static double catmull(double p0, double p1, double p2, double p3, float progress) {
      double t = MichaelAfkStarsVfxClient.H.smooth(progress);
      double t2 = t * t;
      double t3 = t2 * t;
      return 0.5 * (2.0 * p1 + (-p0 + p2) * t + (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3) * t2 + (-p0 + 3.0 * p1 - 3.0 * p2 + p3) * t3);
   }

   private static int[] palette(int index) {
      return switch (Math.floorMod(index, 16)) {
         case 0 -> new int[]{255, 34, 206};
         case 1 -> new int[]{255, 44, 224};
         case 2 -> new int[]{255, 58, 242};
         case 3 -> new int[]{248, 70, 255};
         case 4 -> new int[]{236, 76, 255};
         case 5 -> new int[]{224, 82, 255};
         case 6 -> new int[]{210, 88, 255};
         case 7 -> new int[]{198, 96, 255};
         case 8 -> new int[]{255, 82, 216};
         case 9 -> new int[]{255, 96, 228};
         case 10 -> new int[]{255, 112, 240};
         case 11 -> new int[]{246, 124, 255};
         case 12 -> new int[]{230, 118, 255};
         case 13 -> new int[]{214, 110, 255};
         case 14 -> new int[]{198, 102, 255};
         default -> new int[]{184, 92, 255};
      };
   }

   private static final class Basis {
      private final Vec3 origin;
      private final Vec3 forward;
      private final Vec3 right;

      private Basis(Vec3 origin, Vec3 forward, Vec3 right) {
         this.origin = origin;
         this.forward = forward;
         this.right = right;
      }

      private static MichaelAfkStarsVfxClient.Basis of(AbstractClientPlayer player) {
         float yaw = player.yBodyRot * (float) (Math.PI / 180.0);
         Vec3 forward = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
         if (forward.lengthSqr() < 1.0E-8) {
            forward = new Vec3(0.0, 0.0, 1.0);
         }

         forward = forward.normalize();
         Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
         return new MichaelAfkStarsVfxClient.Basis(player.position(), forward, right);
      }
   }

   private static final class H {
      private static BufferBuilder begin() {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         return Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
      }

      private static void finish(BufferBuilder buffer, boolean wrote) {
         if (wrote) {
            try {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            } catch (IllegalStateException var3) {
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }

      private static Vec3 camera() {
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         return camera.getPosition();
      }

      private static Vec3 rel(Vec3 point, Vec3 camera) {
         return point.subtract(camera);
      }

      private static float smooth(float value) {
         value = Mth.clamp(value, 0.0F, 1.0F);
         return value * value * (3.0F - 2.0F * value);
      }

      private static float hash(long seed, int salt) {
         long value = seed + salt * -7046029254386353131L;
         value ^= value >>> 30;
         value *= -4658895280553007687L;
         value ^= value >>> 27;
         value *= -7723592293110705685L;
         value ^= value >>> 31;
         return (float)(value & 16777215L) / 1.6777216E7F;
      }

      private static MichaelAfkStarsVfxClient.WorldAxes worldAxes(Vec3 forward) {
         if (forward.lengthSqr() < 1.0E-8) {
            forward = new Vec3(0.0, -1.0, 0.0);
         }

         forward = forward.normalize();
         Vec3 reference = Math.abs(forward.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         Vec3 right = forward.cross(reference);
         if (right.lengthSqr() < 1.0E-8) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = right.cross(forward);
         if (up.lengthSqr() < 1.0E-8) {
            up = new Vec3(0.0, 0.0, 1.0);
         }

         return new MichaelAfkStarsVfxClient.WorldAxes(right, up.normalize());
      }

      private static MichaelAfkStarsVfxClient.H.Axes axes(Vec3 center, float rotation) {
         Vec3 view = center.scale(-1.0);
         if (view.lengthSqr() < 1.0E-8) {
            view = new Vec3(0.0, 0.0, 1.0);
         }

         view = view.normalize();
         Vec3 reference = Math.abs(view.y) > 0.94 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
         Vec3 right = reference.cross(view);
         if (right.lengthSqr() < 1.0E-8) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = view.cross(right).normalize();
         double cosine = Mth.cos(rotation);
         double sine = Mth.sin(rotation);
         return new MichaelAfkStarsVfxClient.H.Axes(right.scale(cosine).add(up.scale(sine)), up.scale(cosine).subtract(right.scale(sine)));
      }

      private static boolean star(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, float rotation, int red, int green, int blue, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         MichaelAfkStarsVfxClient.H.Axes axes = axes(center, rotation);
         Vec3 right = axes.right;
         Vec3 up = axes.up;
         Vec3 top = center.add(up.scale(size * 1.34));
         Vec3 bottom = center.subtract(up.scale(size * 1.34));
         Vec3 left = center.subtract(right.scale(size * 0.84));
         Vec3 rightPoint = center.add(right.scale(size * 0.84));
         Vec3 innerTop = center.add(up.scale(size * 0.08));
         Vec3 innerBottom = center.subtract(up.scale(size * 0.08));
         Vec3 innerLeft = center.subtract(right.scale(size * 0.074));
         Vec3 innerRight = center.add(right.scale(size * 0.074));
         tri(buffer, matrix, top, innerRight, innerLeft, red, green, blue, alpha);
         tri(buffer, matrix, rightPoint, innerBottom, innerTop, red, green, blue, alpha);
         tri(buffer, matrix, bottom, innerLeft, innerRight, red, green, blue, alpha);
         tri(buffer, matrix, left, innerTop, innerBottom, red, green, blue, alpha);
         return true;
      }

      private static boolean starBurst(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, float rotation, int red, int green, int blue, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = star(buffer, matrix, center, size, rotation, red, green, blue, alpha);
         wrote |= star(
            buffer, matrix, center, size * 0.52F, rotation + (float) (Math.PI / 4), Math.max(120, red - 28), Math.min(255, green + 8), 255, alpha * 0.7F
         );
         return wrote | diamond(buffer, matrix, center, size * 0.235F, rotation, Math.min(255, red + 10), Math.min(255, green + 10), 255, alpha * 0.86F);
      }

      private static boolean diamond(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, float rotation, int red, int green, int blue, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         MichaelAfkStarsVfxClient.H.Axes axes = axes(center, rotation);
         Vec3 right = axes.right.scale(size * 0.62);
         Vec3 up = axes.up.scale(size);
         Vec3 top = center.add(up);
         Vec3 rightPoint = center.add(right);
         Vec3 bottom = center.subtract(up);
         Vec3 left = center.subtract(right);
         tri(buffer, matrix, top, rightPoint, center, red, green, blue, alpha);
         tri(buffer, matrix, rightPoint, bottom, center, red, green, blue, alpha);
         tri(buffer, matrix, bottom, left, center, red, green, blue, alpha);
         tri(buffer, matrix, left, top, center, red, green, blue, alpha);
         return true;
      }

      private static boolean shard(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, float rotation, int red, int green, int blue, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         MichaelAfkStarsVfxClient.H.Axes axes = axes(center, rotation);
         Vec3 right = axes.right;
         Vec3 up = axes.up;
         Vec3 tip = center.add(up.scale(size));
         Vec3 base = center.subtract(up.scale(size * 0.68));
         Vec3 left = center.subtract(right.scale(size * 0.24)).subtract(up.scale(size * 0.18));
         Vec3 rightPoint = center.add(right.scale(size * 0.24)).subtract(up.scale(size * 0.18));
         tri(buffer, matrix, tip, rightPoint, left, red, green, blue, alpha);
         tri(buffer, matrix, left, rightPoint, base, Math.max(0, red - 28), Math.max(0, green - 8), blue, alpha * 0.62F);
         return true;
      }

      private static boolean glow(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, float rotation, int red, int green, int blue, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         MichaelAfkStarsVfxClient.H.Axes axes = axes(center, rotation);
         Vec3 right = axes.right;
         Vec3 up = axes.up;
         Vec3 top = center.add(up.scale(size));
         Vec3 bottom = center.subtract(up.scale(size));
         Vec3 left = center.subtract(right.scale(size));
         Vec3 rightPoint = center.add(right.scale(size));
         triGradient(buffer, matrix, center, top, rightPoint, red, green, blue, alpha, 0.0F, 0.0F);
         triGradient(buffer, matrix, center, rightPoint, bottom, red, green, blue, alpha, 0.0F, 0.0F);
         triGradient(buffer, matrix, center, bottom, left, red, green, blue, alpha, 0.0F, 0.0F);
         triGradient(buffer, matrix, center, left, top, red, green, blue, alpha, 0.0F, 0.0F);
         return true;
      }

      private static boolean ribbon(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 first,
         Vec3 second,
         float firstWidth,
         float secondWidth,
         int red,
         int green,
         int blue,
         float firstAlpha,
         float secondAlpha
      ) {
         if (firstAlpha <= 0.001F && secondAlpha <= 0.001F) {
            return false;
         }

         Vec3 direction = second.subtract(first);
         if (direction.lengthSqr() < 1.0E-8) {
            return false;
         }

         Vec3 midpoint = first.add(second).scale(0.5);
         Vec3 view = midpoint.scale(-1.0);
         if (view.lengthSqr() < 1.0E-8) {
            view = new Vec3(0.0, 0.0, 1.0);
         }

         Vec3 side = direction.cross(view);
         if (side.lengthSqr() < 1.0E-8) {
            side = direction.cross(new Vec3(0.0, 1.0, 0.0));
         }

         if (side.lengthSqr() < 1.0E-8) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize();
         Vec3 firstLeft = first.add(side.scale(firstWidth));
         Vec3 firstRight = first.subtract(side.scale(firstWidth));
         Vec3 secondLeft = second.add(side.scale(secondWidth));
         Vec3 secondRight = second.subtract(side.scale(secondWidth));
         triGradient(buffer, matrix, firstLeft, firstRight, secondRight, red, green, blue, firstAlpha, firstAlpha, secondAlpha);
         triGradient(buffer, matrix, firstLeft, secondRight, secondLeft, red, green, blue, firstAlpha, secondAlpha, secondAlpha);
         return true;
      }

      private static boolean ribbonFixed(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 first,
         Vec3 second,
         Vec3 side,
         float firstWidth,
         float secondWidth,
         int red,
         int green,
         int blue,
         float firstAlpha,
         float secondAlpha
      ) {
         if (firstAlpha <= 0.001F && secondAlpha <= 0.001F) {
            return false;
         } else if (!(side.lengthSqr() < 1.0E-8) && !(first.distanceToSqr(second) < 1.0E-8)) {
            Vec3 normal = side.normalize();
            Vec3 firstLeft = first.add(normal.scale(firstWidth));
            Vec3 firstRight = first.subtract(normal.scale(firstWidth));
            Vec3 secondLeft = second.add(normal.scale(secondWidth));
            Vec3 secondRight = second.subtract(normal.scale(secondWidth));
            triGradient(buffer, matrix, firstLeft, firstRight, secondRight, red, green, blue, firstAlpha, firstAlpha, secondAlpha);
            triGradient(buffer, matrix, firstLeft, secondRight, secondLeft, red, green, blue, firstAlpha, secondAlpha, secondAlpha);
            return true;
         } else {
            return false;
         }
      }

      private static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 first, Vec3 second, Vec3 third, int red, int green, int blue, float alpha) {
         triGradient(buffer, matrix, first, second, third, red, green, blue, alpha, alpha, alpha);
      }

      private static void triGradient(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 first,
         Vec3 second,
         Vec3 third,
         int red,
         int green,
         int blue,
         float firstAlpha,
         float secondAlpha,
         float thirdAlpha
      ) {
         vertex(buffer, matrix, first, red, green, blue, firstAlpha);
         vertex(buffer, matrix, second, red, green, blue, secondAlpha);
         vertex(buffer, matrix, third, red, green, blue, thirdAlpha);
      }

      private static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 position, int red, int green, int blue, float alpha) {
         int packedAlpha = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         buffer.addVertex(matrix, (float)position.x, (float)position.y, (float)position.z)
            .setColor(Mth.clamp(red, 0, 255), Mth.clamp(green, 0, 255), Mth.clamp(blue, 0, 255), packedAlpha);
      }

      private static final class Axes {
         private final Vec3 right;
         private final Vec3 up;

         private Axes(Vec3 right, Vec3 up) {
            this.right = right;
            this.up = up;
         }
      }
   }

   private static final class HandPoint {
      private final Vec3 position;
      private final long bornAtMillis;

      private HandPoint(Vec3 position, long bornAtMillis) {
         this.position = position;
         this.bornAtMillis = bornAtMillis;
      }
   }

   private static final class LocalPoint {
      private final double right;
      private final double up;
      private final double forward;
      private final float time;

      private LocalPoint(double right, double up, double forward, float time) {
         this.right = right;
         this.up = up;
         this.forward = forward;
         this.time = time;
      }
   }

   private static final class Meteor {
      private static final int TRAIL_SEGMENTS = 7;
      private final UUID ownerId;
      private final Vec3 origin;
      private final Vec3 direction;
      private final Vec3 trailReference;
      private final Vec3 driftAxis;
      private final float size;
      private final float trailLength;
      private final long lifeMillis;
      private final float speed;
      private final float acceleration;
      private final float driftAmount;
      private final int red;
      private final int green;
      private final int blue;
      private final boolean accent;
      private final long seed;
      private final long bornAtMillis;
      private final boolean openingStar;
      private long stoppingAtMillis;

      private Meteor(
         UUID ownerId,
         Vec3 origin,
         Vec3 direction,
         Vec3 trailReference,
         float size,
         float trailLength,
         long lifeMillis,
         float speed,
         float acceleration,
         float driftAmount,
         int red,
         int green,
         int blue,
         boolean accent,
         long seed,
         long now,
         boolean openingStar
      ) {
         this.ownerId = ownerId;
         this.origin = origin;
         this.direction = direction.lengthSqr() < 1.0E-8 ? new Vec3(-0.28, -1.0, 0.08).normalize() : direction.normalize();
         Vec3 reference = trailReference.lengthSqr() < 1.0E-8 ? new Vec3(0.0, 0.0, 1.0) : trailReference.normalize();
         this.trailReference = reference;
         Vec3 drift = this.direction.cross(reference);
         if (drift.lengthSqr() < 1.0E-8) {
            drift = this.direction.cross(new Vec3(0.0, 1.0, 0.0));
         }

         this.driftAxis = drift.lengthSqr() < 1.0E-8 ? new Vec3(1.0, 0.0, 0.0) : drift.normalize();
         this.size = size;
         this.trailLength = trailLength;
         this.lifeMillis = Math.max(1000L, lifeMillis);
         this.speed = speed;
         this.acceleration = acceleration;
         this.driftAmount = driftAmount;
         this.red = red;
         this.green = green;
         this.blue = blue;
         this.accent = accent;
         this.seed = seed;
         this.bornAtMillis = now;
         this.openingStar = openingStar;
      }

      private boolean tick(long now, boolean ownerActive) {
         if (!ownerActive && this.stoppingAtMillis == 0L) {
            this.stoppingAtMillis = now;
         }

         return now - this.bornAtMillis >= this.lifeMillis || this.stoppingAtMillis != 0L && now - this.stoppingAtMillis >= 460L;
      }

      private Vec3 samplePosition(long now) {
         double seconds = Math.max(0.0, (now - this.bornAtMillis) / 1000.0);
         double distance = this.speed * seconds + 0.5 * this.acceleration * seconds * seconds;
         double phase = MichaelAfkStarsVfxClient.H.hash(this.seed, 211) * (float) (Math.PI * 2);
         double drift = (Math.sin(seconds * 1.08 + phase) - Math.sin(phase)) * this.driftAmount;
         return this.origin.add(this.direction.scale(distance)).add(this.driftAxis.scale(drift));
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
         float age = Mth.clamp((float)(now - this.bornAtMillis) / (float)this.lifeMillis, 0.0F, 1.0F);
         float born = MichaelAfkStarsVfxClient.H.smooth(Mth.clamp(age / (this.openingStar ? 0.07F : 0.05F), 0.0F, 1.0F));
         float fade = 1.0F - MichaelAfkStarsVfxClient.H.smooth(Mth.clamp((age - 0.78F) / 0.22F, 0.0F, 1.0F));
         double seconds = Math.max(0.0, (now - this.bornAtMillis) / 1000.0);
         float phase = MichaelAfkStarsVfxClient.H.hash(this.seed, 501) * (float) (Math.PI * 2);
         float wave = 0.5F + 0.5F * Mth.sin((float)seconds * (2.25F + MichaelAfkStarsVfxClient.H.hash(this.seed, 503) * 0.65F) + phase);
         float pulse = 0.98F + wave * 0.08F;
         float stopFade = this.stoppingAtMillis == 0L
            ? 1.0F
            : 1.0F - MichaelAfkStarsVfxClient.H.smooth(Mth.clamp((float)(now - this.stoppingAtMillis) / 460.0F, 0.0F, 1.0F));
         float alpha = born * fade * stopFade;
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 position = this.samplePosition(now);
         Vec3 relativeHead = MichaelAfkStarsVfxClient.H.rel(position, camera);
         Vec3 view = relativeHead.scale(-1.0);
         Vec3 side = this.direction.cross(view);
         if (side.lengthSqr() < 1.0E-8) {
            side = this.trailReference;
         } else {
            side = side.normalize();
         }

         if (side.dot(this.trailReference) < 0.0) {
            side = side.scale(-1.0);
         }

         boolean wrote = this.renderTrail(buffer, matrix, camera, position, side, alpha, wave);
         return wrote | this.renderCore(buffer, matrix, camera, position, alpha, pulse);
      }

      private boolean renderTrail(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 position, Vec3 side, float alpha, float wave) {
         float accentScale = this.accent ? 1.12F : 1.0F;
         int outerRed = Math.max(74, this.red - 128);
         int outerGreen = Math.max(6, this.green - 72);
         int mainRed = this.red;
         int mainGreen = this.green;
         int innerRed = Math.min(255, this.red + 14);
         int innerGreen = Math.min(255, this.green + 22);
         boolean wrote = MichaelAfkStarsVfxClient.H.glow(
            buffer,
            matrix,
            MichaelAfkStarsVfxClient.H.rel(position, camera),
            this.size * 3.48F * accentScale,
            0.0F,
            this.red,
            this.green,
            this.blue,
            alpha * 0.28F
         );
         wrote |= MichaelAfkStarsVfxClient.H.glow(
            buffer,
            matrix,
            MichaelAfkStarsVfxClient.H.rel(position, camera),
            this.size * 5.2F * accentScale,
            0.0F,
            Math.max(92, this.red - 70),
            Math.max(10, this.green - 24),
            244,
            alpha * 0.075F
         );
         wrote |= this.renderTrailLayer(
            buffer, matrix, camera, position, side, this.trailLength * 1.02F, this.size * 0.72F * accentScale, outerRed, outerGreen, 226, alpha * 0.24F
         );
         wrote |= this.renderTrailLayer(
            buffer, matrix, camera, position, side, this.trailLength, this.size * 0.44F * accentScale, mainRed, mainGreen, this.blue, alpha * 0.84F
         );
         wrote |= this.renderTrailLayer(
            buffer, matrix, camera, position, side, this.trailLength * 0.82F, this.size * 0.22F * accentScale, innerRed, innerGreen, 255, alpha * 0.98F
         );
         wrote |= this.renderTrailLayer(
            buffer,
            matrix,
            camera,
            position,
            side,
            this.trailLength * 0.58F,
            this.size * 0.075F * accentScale,
            255,
            Math.min(176, innerGreen + 18),
            255,
            alpha * 0.76F
         );
         int sparkleCount = this.accent ? 3 : 2;
         MichaelAfkStarsVfxClient.WorldAxes axes = MichaelAfkStarsVfxClient.H.worldAxes(this.direction);

         for (int i = 0; i < sparkleCount; i++) {
            float h0 = MichaelAfkStarsVfxClient.H.hash(this.seed, 8100 + i * 43);
            float h1 = MichaelAfkStarsVfxClient.H.hash(this.seed, 8200 + i * 47);
            float progress = 0.26F + h0 * 0.42F;
            Vec3 point = position.subtract(this.direction.scale(this.trailLength * progress)).add(axes.right.scale((h1 - 0.5) * this.size * 0.7));
            float sparkleSize = this.size * (0.15F + h1 * 0.08F);
            float sparkleAlpha = alpha * (0.22F + h0 * 0.18F) * (1.0F - progress * 0.68F);
            int[] color = MichaelAfkStarsVfxClient.palette((int)(MichaelAfkStarsVfxClient.H.hash(this.seed, 8300 + i * 53) * 192.0F));
            wrote |= MichaelAfkStarsVfxClient.H.star(
               buffer, matrix, MichaelAfkStarsVfxClient.H.rel(point, camera), sparkleSize, 0.0F, color[0], color[1], color[2], sparkleAlpha
            );
         }

         return wrote;
      }

      private boolean renderTrailLayer(
         BufferBuilder buffer,
         Matrix4f matrix,
         Vec3 camera,
         Vec3 position,
         Vec3 side,
         float length,
         float width,
         int layerRed,
         int layerGreen,
         int layerBlue,
         float layerAlpha
      ) {
         boolean wrote = false;

         for (int i = 0; i < 7; i++) {
            float firstProgress = i / 7.0F;
            float secondProgress = (i + 1) / 7.0F;
            float firstFade = 1.0F - MichaelAfkStarsVfxClient.H.smooth(firstProgress);
            float secondFade = 1.0F - MichaelAfkStarsVfxClient.H.smooth(secondProgress);
            Vec3 first = MichaelAfkStarsVfxClient.H.rel(position.subtract(this.direction.scale(length * firstProgress)), camera);
            Vec3 second = MichaelAfkStarsVfxClient.H.rel(position.subtract(this.direction.scale(length * secondProgress)), camera);
            float firstWidth = width * (0.025F + firstFade * 0.975F);
            float secondWidth = width * (0.025F + secondFade * 0.975F);
            wrote |= MichaelAfkStarsVfxClient.H.ribbonFixed(
               buffer, matrix, first, second, side, firstWidth, secondWidth, layerRed, layerGreen, layerBlue, layerAlpha * firstFade, layerAlpha * secondFade
            );
         }

         return wrote;
      }

      private boolean renderCore(BufferBuilder buffer, Matrix4f matrix, Vec3 camera, Vec3 position, float alpha, float pulse) {
         Vec3 head = MichaelAfkStarsVfxClient.H.rel(position, camera);
         float coreScale = this.accent ? 1.34F : 1.16F;
         int brightGreen = Math.min(158, this.green + 24);
         boolean wrote = MichaelAfkStarsVfxClient.H.glow(
            buffer, matrix, head, this.size * 3.62F * coreScale * pulse, 0.0F, this.red, this.green, this.blue, alpha * 0.34F
         );
         wrote |= MichaelAfkStarsVfxClient.H.glow(buffer, matrix, head, this.size * 2.18F * coreScale * pulse, 0.0F, 255, brightGreen, 255, alpha * 0.22F);
         wrote |= MichaelAfkStarsVfxClient.H.starBurst(
            buffer, matrix, head, this.size * 1.34F * coreScale * pulse, 0.0F, this.red, this.green, this.blue, alpha
         );
         wrote |= MichaelAfkStarsVfxClient.H.star(
            buffer, matrix, head, this.size * 0.82F * coreScale * pulse, (float) (Math.PI / 8), 255, Math.min(146, this.green + 18), 255, alpha * 0.82F
         );
         return wrote
            | MichaelAfkStarsVfxClient.H.diamond(buffer, matrix, head, this.size * 0.38F * coreScale * pulse, 0.0F, 255, brightGreen, 255, alpha * 0.96F);
      }
   }

   private static final class PlayerFxState {
      private final List<MichaelAfkStarsVfxClient.HandPoint> handPoints = new ArrayList<>();
      private Vec3 lastHandPosition;
      private Vec3 sweepDirection = new Vec3(1.0, 0.0, 0.0);
      private float animationTime;
      private long lastHandSample;
      private long lastRainEmit;
      private boolean releaseTriggered;

      private void trimHand(long now) {
         this.handPoints.removeIf(point -> now - point.bornAtMillis > 920L);
      }

      private void reset() {
         this.handPoints.clear();
         this.lastHandPosition = null;
         this.sweepDirection = new Vec3(1.0, 0.0, 0.0);
         this.animationTime = 0.0F;
         this.lastHandSample = 0L;
         this.lastRainEmit = 0L;
         this.releaseTriggered = false;
      }
   }

   private static final class WorldAxes {
      private final Vec3 right;
      private final Vec3 up;

      private WorldAxes(Vec3 right, Vec3 up) {
         this.right = right;
         this.up = up;
      }
   }
}
