package com.github.wal_bos.moreskills.client.fx.arthur;

import com.github.wal_bos.moreskills.client.renderer.ArthurAfkAnimationRenderer;
import com.github.wal_bos.moreskills.net.msg.ArthurAfkVfxPayload;
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

public final class ArthurAfkGoldenOrbsVfxClient {
   private static final float SWORD_MOVE_START = 0.75F;
   private static final float SWORD_MOVE_END = 3.1F;
   private static final float ORBS_START = 1.7F;
   private static final float EFFECT_END = 19.075F;
   private static final long SWORD_SAMPLE_INTERVAL = 18L;
   private static final long SWORD_TRAIL_LIFE = 820L;
   private static final ArthurAfkGoldenOrbsVfxClient.LocalPoint[] SWORD_PATH = new ArthurAfkGoldenOrbsVfxClient.LocalPoint[]{
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(0.4, 1.1, 0.18, 0.75F),
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(0.58, 1.3, 0.22, 0.95F),
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(0.84, 1.6, 0.22, 1.22F),
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(1.1, 2.05, 0.18, 1.52F),
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(1.24, 2.55, 0.08, 1.84F),
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(1.1, 3.05, -0.04, 2.18F),
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(0.84, 3.46, -0.1, 2.48F),
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(0.56, 3.82, -0.08, 2.78F),
      new ArthurAfkGoldenOrbsVfxClient.LocalPoint(0.36, 4.05, -0.02, 3.1F)
   };
   private static final Map<UUID, ArthurAfkGoldenOrbsVfxClient.PlayerFxState> PLAYER_STATES = new HashMap<>();
   private static final Map<Integer, ArthurAfkGoldenOrbsVfxClient.PacketState> PACKET_STATES = new HashMap<>();
   private static boolean initialized;

   private ArthurAfkGoldenOrbsVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ArthurAfkGoldenOrbsVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(ArthurAfkGoldenOrbsVfxClient::render);
      }
   }

   public static void play(ArthurAfkVfxPayload payload) {
      init();
      if (payload.active()) {
         PACKET_STATES.put(payload.casterId(), new ArthurAfkGoldenOrbsVfxClient.PacketState(Util.getMillis(), payload.seed()));
      } else {
         PACKET_STATES.remove(payload.casterId());
      }
   }

   private static void tick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level == null) {
         PLAYER_STATES.clear();
         PACKET_STATES.clear();
      } else {
         long now = Util.getMillis();
         Set<UUID> seen = new HashSet<>();

         for (AbstractClientPlayer player : minecraft.level.players()) {
            UUID id = player.getUUID();
            seen.add(id);
            ArthurAfkGoldenOrbsVfxClient.PlayerFxState state = PLAYER_STATES.computeIfAbsent(id, ignored -> new ArthurAfkGoldenOrbsVfxClient.PlayerFxState());
            ArthurAfkGoldenOrbsVfxClient.PacketState packetState = PACKET_STATES.get(player.getId());
            boolean rendererActive = ArthurAfkAnimationRenderer.isAfkAnimationActive(player);
            if (!rendererActive && packetState == null) {
               state.reset();
            } else {
               float animationTime = rendererActive ? ArthurAfkAnimationRenderer.getAnimationTimeSeconds(player) : packetState.animationTime(now);
               if (!(animationTime < 0.0F) && !(animationTime > 19.075F)) {
                  ArthurAfkGoldenOrbsVfxClient.Basis basis = ArthurAfkGoldenOrbsVfxClient.Basis.of(player);
                  Vec3 sword = swordAnchor(basis, animationTime);
                  state.currentSword = sword;
                  updateSwordTrail(state, sword, animationTime, now);
               } else {
                  state.reset();
               }
            }
         }

         PLAYER_STATES.entrySet().removeIf(entry -> !seen.contains(entry.getKey()));
         PACKET_STATES.entrySet().removeIf(entry -> minecraft.level.getEntity(entry.getKey()) == null);
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            long now = Util.getMillis();
            Camera cameraObject = minecraft.gameRenderer.getMainCamera();
            Vec3 camera = cameraObject.getPosition();
            event.getPoseStack().pushPose();
            event.getPoseStack().translate(-camera.x, -camera.y, -camera.z);
            Matrix4f matrix = event.getPoseStack().last().pose();
            BufferBuilder buffer = begin();
            boolean wrote = false;

            for (AbstractClientPlayer player : minecraft.level.players()) {
               ArthurAfkGoldenOrbsVfxClient.PacketState packetState = PACKET_STATES.get(player.getId());
               boolean rendererActive = ArthurAfkAnimationRenderer.isAfkAnimationActive(player);
               if (rendererActive || packetState != null) {
                  float animationTime = rendererActive ? ArthurAfkAnimationRenderer.getAnimationTimeSeconds(player) : packetState.animationTime(now);
                  if (!(animationTime < 0.0F) && !(animationTime > 19.075F)) {
                     ArthurAfkGoldenOrbsVfxClient.Basis basis = ArthurAfkGoldenOrbsVfxClient.Basis.of(player);
                     ArthurAfkGoldenOrbsVfxClient.PlayerFxState state = PLAYER_STATES.get(player.getUUID());
                     Vec3 sword = state != null && state.currentSword != null ? state.currentSword : swordAnchor(basis, animationTime);
                     wrote |= renderGoldenArea(player, animationTime, buffer, matrix, camera, now);
                     wrote |= renderRisingLightOrbs(player, animationTime, buffer, matrix, camera, now);
                     wrote |= renderSwordLight(player, animationTime, sword, basis, buffer, matrix, now);
                     if (state != null) {
                        wrote |= renderSwordTrail(state, buffer, matrix, now);
                     }
                  }
               }
            }

            finish(buffer, wrote);
            event.getPoseStack().popPose();
         }
      }
   }

   private static void updateSwordTrail(ArthurAfkGoldenOrbsVfxClient.PlayerFxState state, Vec3 sword, float animationTime, long now) {
      state.trimSword(now);
      if (animationTime < 0.75F) {
         state.swordPoints.clear();
         state.lastSwordSample = 0L;
      } else if (!(animationTime > 3.1F)) {
         if (now - state.lastSwordSample >= 18L) {
            state.lastSwordSample = now;
            if (!state.swordPoints.isEmpty()) {
               ArthurAfkGoldenOrbsVfxClient.TrailPoint last = state.swordPoints.get(state.swordPoints.size() - 1);
               if (last.position.distanceToSqr(sword) < 8.0E-6) {
                  return;
               }
            }

            state.swordPoints.add(new ArthurAfkGoldenOrbsVfxClient.TrailPoint(sword, now));

            while (state.swordPoints.size() > 34) {
               state.swordPoints.remove(0);
            }
         }
      }
   }

   private static boolean renderGoldenArea(AbstractClientPlayer player, float animationTime, BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
      float fadeIn = smooth(Mth.clamp((animationTime - 1.46F) / 0.52F, 0.0F, 1.0F));
      float fadeOut = 1.0F - smooth(Mth.clamp((animationTime - 17.995F) / 0.94F, 0.0F, 1.0F));
      float live = fadeIn * fadeOut;
      if (live <= 0.001F) {
         return false;
      }

      Vec3 center = player.position().add(0.0, 0.032, 0.0);
      long seed = player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits();
      float time = (float)now * 0.001F;
      float pulse = 0.92F + 0.08F * pulse01(time * 1.55F);
      boolean wrote = false;
      wrote |= groundDisk(buffer, matrix, center, 5.65 * pulse, 42, 255, 190, 42, live * 0.155F);
      wrote |= kingsBattleOrb(buffer, matrix, center.add(0.0, 0.28, 0.0), 3.9F * pulse, 255, 167, 14, live * 0.034F);
      wrote |= lightDomOrb(buffer, matrix, center.add(0.0, 0.32, 0.0), 2.55F * pulse, 255, 220, 105, live * 0.042F);
      int motes = camera.distanceTo(center) < 96.0 ? 38 : 24;

      for (int i = 0; i < motes; i++) {
         float h0 = hash(seed, 21000 + i * 17);
         float h1 = hash(seed, 21100 + i * 19);
         float h2 = hash(seed, 21200 + i * 23);
         float h3 = hash(seed, 21300 + i * 29);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.02 + h3 * 0.015);
         double radial = 0.3 + Math.sqrt(h1) * 5.0;
         double cycle = (time * (0.045 + h2 * 0.03) + h0 * 8.0) % 1.0;
         Vec3 p = center.add(Math.cos(angle) * radial, 0.05 + cycle * (0.48 + h3 * 0.72), Math.sin(angle) * radial);
         float blink = 0.32F + 0.68F * pulse01(time * (5.2F + h1 * 4.5F) + i * 0.51F);
         float size = 0.025F + h2 * 0.045F;
         wrote |= kingsBattleOrb(buffer, matrix, p, size * 1.65F, 255, 178, 24, live * blink * 0.14F);
         wrote |= lightDomOrb(buffer, matrix, p, size, 255, 235 + (int)(h0 * 20.0F), 145 + (int)(h1 * 75.0F), live * blink * 0.68F);
      }

      return wrote;
   }

   private static boolean renderRisingLightOrbs(AbstractClientPlayer player, float animationTime, BufferBuilder buffer, Matrix4f matrix, Vec3 camera, long now) {
      float fadeIn = smooth(Mth.clamp((animationTime - 1.7F) / 0.42F, 0.0F, 1.0F));
      float fadeOut = 1.0F - smooth(Mth.clamp((animationTime - 17.995F) / 0.94F, 0.0F, 1.0F));
      float live = fadeIn * fadeOut;
      if (live <= 0.001F) {
         return false;
      }

      Vec3 center = player.position().add(0.0, 0.025, 0.0);
      double distance = camera.distanceTo(center);
      int detail = distance < 105.0 ? 3 : (distance < 210.0 ? 2 : 1);
      int count = detail >= 3 ? 46 : (detail == 2 ? 30 : 18);
      float time = (float)now * 0.001F;
      long seed = player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits();
      boolean wrote = false;

      for (int i = 0; i < count; i++) {
         float h0 = hash(seed, 14000 + i * 17);
         float h1 = hash(seed, 14100 + i * 19);
         float h2 = hash(seed, 14200 + i * 23);
         float h3 = hash(seed, 14300 + i * 29);
         double angle = h0 * (float) (Math.PI * 2) + time * (0.012 + h2 * 0.012);
         double radial = 5.05 * Math.sqrt(0.035 + h1 * 0.94);
         double cycle = (time * (0.09 + h0 * 0.06) + h2 * 8.0) % 1.0;
         double maxRise = 4.15 + h2 * 4.75;
         double rise = 0.045 + cycle * maxRise;
         double sway = Math.sin(time * (0.65 + h1 * 0.48) + i * 0.73) * (0.035 + h3 * 0.055);
         Vec3 p = center.add(
            Math.cos(angle) * radial + Math.cos(angle + (Math.PI / 2)) * sway, rise, Math.sin(angle) * radial + Math.sin(angle + (Math.PI / 2)) * sway
         );
         float blink = 0.18F + 0.82F * pulse01(time * (7.0F + h1 * 8.0F) + i * 0.37F);
         float fade = 1.0F - (float)cycle * 0.3F;
         float size = (0.085F + h3 * 0.105F) * (0.84F + blink * 0.34F);
         float orbLive = live * blink * fade;
         wrote |= kingsBattleOrb(buffer, matrix, p, size * 1.9F, 255, 171, 16, orbLive * 0.14F);
         wrote |= lightDomOrb(buffer, matrix, p, size, 255, 224 + (int)(h0 * 28.0F), 102 + (int)(h1 * 92.0F), orbLive * 0.88F);
         wrote |= kingsBattleOrb(buffer, matrix, p, size * 0.36F, 255, 255, 238, orbLive * 0.94F);
         int trailCount = detail >= 3 ? 18 : (detail == 2 ? 14 : 10);
         int gapOffset = (int)(h2 * 5.0F);
         double spacing = 0.105 + h3 * 0.04;

         for (int j = 1; j <= trailCount; j++) {
            if ((j + gapOffset) % 5 != 0) {
               double beadRise = rise - j * spacing;
               if (beadRise <= 0.035) {
                  break;
               }

               float trailFade = 1.0F - (float)j / (trailCount + 1);
               float noise = 0.78F + hash(seed ^ i * 131L, 23000 + j * 37) * 0.34F;
               float beadSize = size * (0.115F + trailFade * 0.105F) * noise;
               float beadAlpha = orbLive * trailFade * trailFade;
               double beadSway = Math.sin(time * (0.65 + h1 * 0.48) + i * 0.73 - j * 0.06) * (0.025 + h3 * 0.04);
               Vec3 bead = center.add(
                  Math.cos(angle) * radial + Math.cos(angle + (Math.PI / 2)) * beadSway,
                  beadRise,
                  Math.sin(angle) * radial + Math.sin(angle + (Math.PI / 2)) * beadSway
               );
               wrote |= kingsBattleOrb(buffer, matrix, bead, beadSize * 1.7F, 255, 174, 20, beadAlpha * 0.13F);
               wrote |= lightDomOrb(buffer, matrix, bead, beadSize, 255, 230 + (int)(h0 * 22.0F), 126 + (int)(h1 * 92.0F), beadAlpha * 0.78F);
            }
         }

         if (cycle < 0.13) {
            float birth = 1.0F - (float)(cycle / 0.13);
            Vec3 base = center.add(Math.cos(angle) * radial, 0.075, Math.sin(angle) * radial);
            wrote |= kingsBattleOrb(buffer, matrix, base, size * (1.3F + birth * 1.05F), 255, 177, 20, live * birth * 0.12F);
            wrote |= lightDomOrb(buffer, matrix, base, size * (0.56F + birth * 0.36F), 255, 246, 202, live * birth * 0.76F);
         }
      }

      return wrote;
   }

   private static boolean renderSwordLight(
      AbstractClientPlayer player, float animationTime, Vec3 sword, ArthurAfkGoldenOrbsVfxClient.Basis basis, BufferBuilder buffer, Matrix4f matrix, long now
   ) {
      float fadeIn = smooth(Mth.clamp((animationTime - 0.7F) / 0.34F, 0.0F, 1.0F));
      float fadeOut = 1.0F - smooth(Mth.clamp((animationTime - 18.225F) / 0.85F, 0.0F, 1.0F));
      float live = fadeIn * fadeOut;
      if (live <= 0.001F) {
         return false;
      }

      long seed = player.getUUID().getLeastSignificantBits();
      float time = (float)now * 0.001F;
      float pulse = 0.84F + 0.16F * pulse01(time * 5.8F);
      boolean wrote = false;
      wrote |= kingsBattleOrb(buffer, matrix, sword, 0.82F * pulse, 255, 173, 18, live * 0.1F);
      wrote |= lightDomOrb(buffer, matrix, sword, 0.38F * pulse, 255, 236, 166, live * 0.62F);
      wrote |= kingsBattleOrb(buffer, matrix, sword, 0.14F * pulse, 255, 255, 240, live * 0.94F);

      for (int i = 0; i < 12; i++) {
         float h0 = hash(seed, 26000 + i * 17);
         float h1 = hash(seed, 26100 + i * 19);
         float h2 = hash(seed, 26200 + i * 23);
         double angle = i * (float) (Math.PI * 2) / 12.0 + time * (0.42 + h2 * 0.3);
         double radius = 0.18 + h0 * 0.3;
         Vec3 p = sword.add(basis.right.scale(Math.cos(angle) * radius)).add(0.0, (h1 - 0.4) * 0.5, 0.0).add(basis.forward.scale(Math.sin(angle) * radius));
         float blink = 0.36F + 0.64F * pulse01(time * (6.0F + h1 * 5.0F) + i);
         float size = 0.022F + h2 * 0.042F;
         wrote |= kingsBattleOrb(buffer, matrix, p, size * 1.55F, 255, 178, 24, live * blink * 0.14F);
         wrote |= lightDomOrb(buffer, matrix, p, size, 255, 242, 194, live * blink * 0.78F);
      }

      return wrote;
   }

   private static boolean renderSwordTrail(ArthurAfkGoldenOrbsVfxClient.PlayerFxState state, BufferBuilder buffer, Matrix4f matrix, long now) {
      state.trimSword(now);
      if (state.swordPoints.size() < 2) {
         return false;
      }

      boolean wrote = false;

      for (int i = 1; i < state.swordPoints.size(); i++) {
         ArthurAfkGoldenOrbsVfxClient.TrailPoint previous = state.swordPoints.get(i - 1);
         ArthurAfkGoldenOrbsVfxClient.TrailPoint current = state.swordPoints.get(i);
         float currentLife = 1.0F - Mth.clamp((float)(now - current.bornAtMillis) / 820.0F, 0.0F, 1.0F);
         currentLife = smooth(currentLife);
         if (!(currentLife <= 0.001F)) {
            for (int s = 0; s < 3; s++) {
               double t = (s + 1.0) / 3.0;
               Vec3 p = previous.position.lerp(current.position, t);
               float taper = 0.55F + 0.45F * currentLife;
               float size = (0.022F + s * 0.004F) * taper;
               wrote |= kingsBattleOrb(buffer, matrix, p, size * 1.65F, 255, 174, 20, currentLife * 0.13F);
               wrote |= lightDomOrb(buffer, matrix, p, size, 255, 239, 180, currentLife * 0.76F);
            }
         }
      }

      return wrote;
   }

   private static Vec3 swordAnchor(ArthurAfkGoldenOrbsVfxClient.Basis basis, float animationTime) {
      ArthurAfkGoldenOrbsVfxClient.LocalPoint point = animationTime <= 3.1F ? sampleSwordPath(animationTime) : SWORD_PATH[SWORD_PATH.length - 1];
      return basis.origin.add(basis.right.scale(point.right)).add(0.0, point.up, 0.0).add(basis.forward.scale(point.forward));
   }

   private static ArthurAfkGoldenOrbsVfxClient.LocalPoint sampleSwordPath(float time) {
      if (time <= SWORD_PATH[0].time) {
         return SWORD_PATH[0];
      }

      for (int i = 1; i < SWORD_PATH.length; i++) {
         ArthurAfkGoldenOrbsVfxClient.LocalPoint previous = SWORD_PATH[i - 1];
         ArthurAfkGoldenOrbsVfxClient.LocalPoint current = SWORD_PATH[i];
         if (time <= current.time) {
            float progress = Mth.clamp((time - previous.time) / Math.max(1.0E-4F, current.time - previous.time), 0.0F, 1.0F);
            ArthurAfkGoldenOrbsVfxClient.LocalPoint p0 = SWORD_PATH[Math.max(0, i - 2)];
            ArthurAfkGoldenOrbsVfxClient.LocalPoint p1 = previous;
            ArthurAfkGoldenOrbsVfxClient.LocalPoint p2 = current;
            ArthurAfkGoldenOrbsVfxClient.LocalPoint p3 = SWORD_PATH[Math.min(SWORD_PATH.length - 1, i + 1)];
            return new ArthurAfkGoldenOrbsVfxClient.LocalPoint(
               catmull(p0.right, p1.right, p2.right, p3.right, progress),
               catmull(p0.up, p1.up, p2.up, p3.up, progress),
               catmull(p0.forward, p1.forward, p2.forward, p3.forward, progress),
               time
            );
         }
      }

      return SWORD_PATH[SWORD_PATH.length - 1];
   }

   private static double catmull(double p0, double p1, double p2, double p3, float progress) {
      double t = smooth(progress);
      double t2 = t * t;
      double t3 = t2 * t;
      return 0.5 * (2.0 * p1 + (-p0 + p2) * t + (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3) * t2 + (-p0 + 3.0 * p1 - 3.0 * p2 + p3) * t3);
   }

   private static boolean groundDisk(BufferBuilder buffer, Matrix4f matrix, Vec3 center, double radius, int segments, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && !(radius <= 0.001)) {
         int count = Math.min(42, Math.max(16, segments));

         for (int i = 0; i < count; i++) {
            double a0 = i * (float) (Math.PI * 2) / count;
            double a1 = (i + 1.0) * (float) (Math.PI * 2) / count;
            Vec3 p0 = center.add(Math.cos(a0) * radius, 0.0, Math.sin(a0) * radius);
            Vec3 p1 = center.add(Math.cos(a1) * radius, 0.0, Math.sin(a1) * radius);
            triGradient(buffer, matrix, center, p0, p1, r, g, b, alpha, 0.0F, 0.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean lightDomOrb(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.001F)) {
         Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
         Vec3 view = camera.subtract(center);
         if (view.lengthSqr() < 1.0E-8) {
            view = new Vec3(0.0, 0.0, 1.0);
         }

         view = view.normalize();
         Vec3 right = view.cross(new Vec3(0.0, 1.0, 0.0));
         if (right.lengthSqr() < 1.0E-8) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = right.cross(view).normalize();
         int segments = 12;

         for (int i = 0; i < segments; i++) {
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1.0) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = center.add(right.scale(Math.cos(a0) * size)).add(up.scale(Math.sin(a0) * size));
            Vec3 p1 = center.add(right.scale(Math.cos(a1) * size)).add(up.scale(Math.sin(a1) * size));
            triGradient(buffer, matrix, center, p0, p1, r, g, b, alpha, 0.0F, 0.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean kingsBattleOrb(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int b, float alpha) {
      if (!(alpha <= 0.001F) && !(size <= 0.001F)) {
         Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
         Vec3 view = camera.subtract(center);
         if (view.lengthSqr() < 1.0E-8) {
            view = new Vec3(0.0, 0.0, 1.0);
         }

         view = view.normalize();
         Vec3 right = view.cross(new Vec3(0.0, 1.0, 0.0));
         if (right.lengthSqr() < 1.0E-8) {
            right = new Vec3(1.0, 0.0, 0.0);
         }

         right = right.normalize();
         Vec3 up = right.cross(view).normalize();
         int segments = 10;

         for (int i = 0; i < segments; i++) {
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1.0) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = center.add(right.scale(Math.cos(a0) * size)).add(up.scale(Math.sin(a0) * size));
            Vec3 p1 = center.add(right.scale(Math.cos(a1) * size)).add(up.scale(Math.sin(a1) * size));
            triGradient(buffer, matrix, center, p0, p1, r, g, b, alpha, 0.0F, 0.0F);
         }

         return true;
      } else {
         return false;
      }
   }

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

   private static float smooth(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * (3.0F - 2.0F * value);
   }

   private static float pulse01(float value) {
      return 0.5F + 0.5F * Mth.sin(value);
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

   private static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
      triGradient(buffer, matrix, a, b, c, r, g, bl, alpha, alpha, alpha);
   }

   private static void triGradient(
      BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alphaA, float alphaB, float alphaC
   ) {
      vertex(buffer, matrix, a, r, g, bl, alphaA);
      vertex(buffer, matrix, b, r, g, bl, alphaB);
      vertex(buffer, matrix, c, r, g, bl, alphaC);
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, Vec3 p, int r, int g, int b, float alpha) {
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      buffer.addVertex(matrix, (float)p.x, (float)p.y, (float)p.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(b, 0, 255), a);
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

      private static ArthurAfkGoldenOrbsVfxClient.Basis of(AbstractClientPlayer player) {
         float yaw = player.yBodyRot * (float) (Math.PI / 180.0);
         Vec3 forward = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
         if (forward.lengthSqr() < 1.0E-8) {
            forward = new Vec3(0.0, 0.0, 1.0);
         }

         forward = forward.normalize();
         Vec3 right = new Vec3(forward.z, 0.0, -forward.x).normalize();
         return new ArthurAfkGoldenOrbsVfxClient.Basis(player.position(), forward, right);
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

   private static final class PacketState {
      private final long startedAtMillis;
      private final long seed;

      private PacketState(long startedAtMillis, long seed) {
         this.startedAtMillis = startedAtMillis;
         this.seed = seed;
      }

      private float animationTime(long now) {
         long elapsed = Math.max(0L, now - this.startedAtMillis);
         return (float)(elapsed % 20125L) / 1000.0F;
      }
   }

   private static final class PlayerFxState {
      private final List<ArthurAfkGoldenOrbsVfxClient.TrailPoint> swordPoints = new ArrayList<>();
      private Vec3 currentSword;
      private long lastSwordSample;

      private void trimSword(long now) {
         this.swordPoints.removeIf(point -> now - point.bornAtMillis > 820L);
      }

      private void reset() {
         this.swordPoints.clear();
         this.currentSword = null;
         this.lastSwordSample = 0L;
      }
   }

   private static final class TrailPoint {
      private final Vec3 position;
      private final long bornAtMillis;

      private TrailPoint(Vec3 position, long bornAtMillis) {
         this.position = position;
         this.bornAtMillis = bornAtMillis;
      }
   }
}
