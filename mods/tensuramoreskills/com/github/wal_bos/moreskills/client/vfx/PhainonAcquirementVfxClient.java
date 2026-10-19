package com.github.wal_bos.moreskills.client.vfx;

import com.github.wal_bos.moreskills.client.fx.AsmodayBlackHoleClient;
import com.github.wal_bos.moreskills.client.fx.AsmodaySpaceClient;
import com.github.wal_bos.moreskills.net.msg.PhainonAcquirementVfxPayload;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class PhainonAcquirementVfxClient {
   private static final Map<UUID, PhainonAcquirementVfxClient.State> ACTIVE = new ConcurrentHashMap<>();
   private static final ResourceLocation POST_SHADER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "shaders/post/phainon_color_drain_post.json");
   private static final int DURATION = 640;
   private static final int BLACKHOLE_OBJECT_TICKS = 200;
   private static final int BLACKHOLE_HIDE_TICK = 122;
   private static final int ASMODAY_DRAIN_DURATION = 820;
   private static boolean initialized;
   private static boolean postActive;
   private static int postTime;
   private static float shaderX = 0.5F;
   private static float shaderY = 0.45F;
   private static float shaderStrength;
   private static float shaderVoid;
   private static float shaderGold;
   private static float shaderShock;
   private static float shaderTime;

   private PhainonAcquirementVfxClient() {
   }

   public static void handle(PhainonAcquirementVfxPayload payload) {
      init();
      if (payload.start()) {
         PhainonAcquirementVfxClient.State state = new PhainonAcquirementVfxClient.State(payload.entityId(), payload.uuid());
         applyOptionalPayloadPosition(payload, state);
         ACTIVE.put(payload.uuid(), state);
         AsmodaySpaceClient.enablePhainon(820);
      } else {
         ACTIVE.remove(payload.uuid());
         if (ACTIVE.isEmpty()) {
            postTime = 70;
         }
      }
   }

   private static void applyOptionalPayloadPosition(PhainonAcquirementVfxPayload payload, PhainonAcquirementVfxClient.State state) {
      Double x = optionalPayloadDouble(payload, "x");
      Double y = optionalPayloadDouble(payload, "y");
      Double z = optionalPayloadDouble(payload, "z");
      if (x != null && y != null && z != null) {
         if (finite(x) && finite(y) && finite(z)) {
            state.hasWorldPosition = true;
            state.lastX = x;
            state.lastY = y;
            state.lastZ = z;
            state.lastLookX = 0.0;
            state.lastLookY = 0.0;
            state.lastLookZ = 1.0;
            state.anchorScale = Math.max(state.anchorScale, 1.0F);
         }
      }
   }

   private static Double optionalPayloadDouble(PhainonAcquirementVfxPayload payload, String methodName) {
      try {
         Method method = payload.getClass().getMethod(methodName);
         if (method.invoke(payload) instanceof Number number) {
            return number.doubleValue();
         }
      } catch (ReflectiveOperationException var5) {
      }

      return null;
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         AsmodaySpaceClient.init();
         NeoForge.EVENT_BUS.addListener(PhainonAcquirementVfxClient::clientTick);
         NeoForge.EVENT_BUS.addListener(PhainonAcquirementVfxClient::renderLevel);
      }
   }

   private static void clientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level == null) {
         ACTIVE.clear();
         shaderStrength = 0.0F;
         shaderVoid = 0.0F;
         shaderGold = 0.0F;
         shaderShock = 0.0F;
         if (postActive) {
            disablePostShader();
         }
      } else {
         Iterator<PhainonAcquirementVfxClient.State> iterator = ACTIVE.values().iterator();

         while (iterator.hasNext()) {
            PhainonAcquirementVfxClient.State state = iterator.next();
            state.age++;
            if (state.age >= 122 && !state.blackholeShrunk) {
               shrinkRealAsmodayBlackHoleClient(state);
            }

            if (state.age > 760) {
               iterator.remove();
            }
         }

         shaderTime += 0.12F;
         if (!ACTIVE.isEmpty()) {
            AsmodaySpaceClient.enablePhainon(72);
         } else if (postTime > 0) {
            postTime--;
         } else if (postActive) {
            disablePostShader();
         }

         updatePostShaderTarget(minecraft, 1.0F);
      }
   }

   private static void renderLevel(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null && !ACTIVE.isEmpty()) {
            Camera camera = minecraft.gameRenderer.getMainCamera();
            Vec3 cam = camera.getPosition();
            PoseStack poseStack = event.getPoseStack();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            updatePostShaderTarget(minecraft, partial);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);

            for (PhainonAcquirementVfxClient.State state : ACTIVE.values()) {
               Entity entity = minecraft.level.getEntity(state.entityId);
               double ex;
               double ey;
               double ez;
               float entityScale;
               Vec3 look;
               if (entity != null) {
                  ex = Mth.lerp(partial, entity.xOld, entity.getX());
                  ey = Mth.lerp(partial, entity.yOld, entity.getY());
                  ez = Mth.lerp(partial, entity.zOld, entity.getZ());
                  entityScale = Math.max(1.0F, entity.getBbHeight());
                  look = entity.getLookAngle();
                  if (look.lengthSqr() < 1.0E-4) {
                     look = new Vec3(0.0, 0.0, 1.0);
                  }

                  state.hasWorldPosition = true;
                  state.lastX = ex;
                  state.lastY = ey;
                  state.lastZ = ez;
                  state.lastLookX = look.x;
                  state.lastLookY = look.y;
                  state.lastLookZ = look.z;
                  state.anchorScale = Math.max(state.anchorScale, entityScale);
               } else {
                  if (!state.hasWorldPosition) {
                     continue;
                  }

                  ex = state.lastX;
                  ey = state.lastY;
                  ez = state.lastZ;
                  entityScale = Math.max(1.0F, state.anchorScale > 0.0F ? state.anchorScale : 1.0F);
                  look = new Vec3(state.lastLookX, state.lastLookY, state.lastLookZ);
                  if (look.lengthSqr() < 1.0E-4) {
                     look = new Vec3(0.0, 0.0, 1.0);
                  }
               }

               float age = state.age + partial;
               float p = Mth.clamp(age / 640.0F, 0.0F, 1.0F);
               float appear = smooth(range(p, 0.01F, 0.1F));
               Vec3 behind = look.normalize().scale(-(15.0 + entityScale * 6.0));
               Vec3 phainonCenter = new Vec3(ex, ey + entityScale * 0.54, ez);
               Vec3 liveHoleCenter = new Vec3(ex + behind.x, ey + entityScale * 1.62 + appear * entityScale * 1.55, ez + behind.z);
               if (!state.blackholeAnchored) {
                  state.blackholeAnchored = true;
                  state.holeX = liveHoleCenter.x;
                  state.holeY = liveHoleCenter.y;
                  state.holeZ = liveHoleCenter.z;
                  state.groundY = (float)(ey - liveHoleCenter.y);
                  state.anchorScale = entityScale;
                  callRealAsmodayBlackHoleClient(state);
               }

               if (state.age <= 226) {
                  Vec3 anchoredHoleCenter = new Vec3(state.holeX, state.holeY, state.holeZ);
                  poseStack.pushPose();
                  poseStack.translate(anchoredHoleCenter.x - cam.x, anchoredHoleCenter.y - cam.y, anchoredHoleCenter.z - cam.z);
                  renderBlackHoleScene(poseStack.last().pose(), camera, state, entityScale, partial, state.groundY);
                  poseStack.popPose();
               }

               poseStack.pushPose();
               poseStack.translate(phainonCenter.x - cam.x, phainonCenter.y - cam.y, phainonCenter.z - cam.z);
               renderBodyAuraScene(poseStack.last().pose(), state, entityScale, partial);
               poseStack.popPose();
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void callRealAsmodayBlackHoleClient(PhainonAcquirementVfxClient.State state) {
      if (!state.asmodayBlackHoleCalled) {
         if (finite(state.holeX) && finite(state.holeY) && finite(state.holeZ)) {
            state.asmodayBlackHoleCalled = true;
            float radius = Math.max(20.0F, Math.max(1.0F, state.anchorScale) * 26.0F);
            AsmodayBlackHoleClient.add(state.asmodayBlackHoleId, state.holeX, state.holeY, state.holeZ, radius, 820);
         }
      }
   }

   private static void shrinkRealAsmodayBlackHoleClient(PhainonAcquirementVfxClient.State state) {
      if (!state.blackholeShrunk && state.asmodayBlackHoleCalled) {
         if (finite(state.holeX) && finite(state.holeY) && finite(state.holeZ)) {
            state.blackholeShrunk = true;
            int remaining = Math.max(80, 820 - state.age);
            AsmodayBlackHoleClient.add(state.asmodayBlackHoleId, state.holeX, state.holeY, state.holeZ, 0.1F, remaining);
         }
      }
   }

   private static void renderBlackHoleScene(
      Matrix4f matrix, Camera camera, PhainonAcquirementVfxClient.State state, float entityScale, float partial, float groundY
   ) {
      float age = state.age + partial;
      float q = Mth.clamp(age / 200.0F, 0.0F, 1.0F);
      float fadeIn = smooth(range(q, 0.0F, 0.075F));
      float fadeOut = 1.0F - smooth(range(q, 0.965F, 1.0F));
      float fade = fadeIn * fadeOut;
      float cubeLife = 1.0F - smooth(range(q, 0.54F, 0.66F));
      float blackhole = smooth(range(q, 0.015F, 0.13F)) * (1.0F - smooth(range(q, 0.5F, 0.6F)));
      float collapse = smooth(range(q, 0.16F, 0.52F)) * (1.0F - smooth(range(q, 0.58F, 0.7F)));
      float explosion = smooth(range(q, 0.48F, 0.56F)) * (1.0F - smooth(range(q, 0.98F, 1.0F)));
      float divine = smooth(range(q, 0.46F, 0.66F)) * fadeOut;
      float grand = smooth(range(q, 0.44F, 0.54F)) * (1.0F - smooth(range(q, 0.98F, 1.0F))) * fade;
      float time = shaderTime + age * 0.036F;
      float pulse = 0.93F + 0.07F * Mth.sin(time * 2.5F);
      float scale = Math.max(1.0F, state.anchorScale > 0.0F ? state.anchorScale : entityScale) * 8.8F;
      float radius = scale * (0.24F + blackhole * 1.45F + collapse * 0.34F + explosion * 0.38F + grand * 0.2F);
      RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      renderBystanderWorldVoidCanopy(matrix, fade, blackhole, collapse, explosion, divine, grand, time, scale);
      renderBystanderAbyssCurtainStorm(matrix, fade, blackhole, collapse, explosion, divine, grand, time, scale);
      renderGroundAsmodayCubes(matrix, fade * cubeLife * (blackhole * 1.9F + collapse * 1.35F), time, groundY, scale);
      renderGroundCubeBeams(matrix, fade * cubeLife * (blackhole * 1.55F + collapse * 1.1F), time, groundY, scale);
      renderGroundCubePillars(matrix, fade * cubeLife * (blackhole * 1.7F + collapse * 1.45F), time, groundY, scale);
      renderGroundRedFractures(matrix, fade * cubeLife * (blackhole * 1.25F + collapse * 1.45F), time, groundY, scale);
      if (blackhole > 0.001F && radius > 0.08F) {
         renderAsmodayRedHole(matrix, camera, age, fade * blackhole, time, pulse, radius);
      }

      renderGrandPhainonBirthExplosion(matrix, fade, explosion, divine, grand, time, scale);
      renderRedBlackShock(matrix, fade, explosion, time, scale);
      renderGoldenExplosionArrival(matrix, fade, explosion, divine, time, scale);
      renderGoldenBlackHoleRain(matrix, fade, explosion, divine, time, scale);
      renderGoldenBlackHoleNovaShards(matrix, fade, explosion, divine, time, scale);
      renderBlackGoldCubeConstellation(matrix, fade, explosion, divine, time, scale);
      renderGoldenCataclysmSpears(matrix, fade, explosion, divine, time, scale);
      renderGoldenBlackHoleCrownStorm(matrix, fade, explosion, divine, time, scale);
      renderGoldenBlackHoleJudgementLances(matrix, fade, explosion, divine, time, scale);
      renderGoldenBlackHoleSolarVeins(matrix, fade, explosion, divine, time, scale);
      renderBlackHoleFinalExplosionCross(matrix, fade, explosion, divine, time, scale);
      renderAnchoredGoldenAfterburstRain(matrix, fade, explosion, divine, time, scale);
      renderGoldenSupernovaBloom(matrix, fade, explosion, divine, grand, time, scale);
      renderGoldenAbyssBreakRays(matrix, fade, explosion, divine, grand, time, scale);
      renderGoldenOriginCrown(matrix, fade, explosion, divine, grand, time, scale);
      renderGoldenPetalSupernova(matrix, fade, explosion, divine, grand, time, scale);
      renderGoldenAbsoluteDawnBurst(matrix, fade, explosion, divine, grand, time, scale);
      renderPhainonSolarPetalDetonation(matrix, fade, explosion, divine, grand, time, scale);
      renderBystanderDawnPetalBlast(matrix, fade, explosion, divine, grand, time, scale);
      renderGoldenSupernovaBloom(matrix, fade, explosion * 1.2F, divine * 1.1F, grand * 1.35F, time + 4.0F, scale * 1.18F);
      renderGoldenPetalSupernova(matrix, fade, explosion * 1.25F, divine * 1.1F, grand * 1.3F, time + 7.0F, scale * 1.12F);
      renderGoldenAbsoluteDawnBurst(matrix, fade, explosion * 1.18F, divine * 1.1F, grand * 1.28F, time + 11.0F, scale * 1.15F);
   }

   private static void renderBodyAuraScene(Matrix4f matrix, PhainonAcquirementVfxClient.State state, float entityScale, float partial) {
      float age = state.age + partial;
      float p = Mth.clamp(age / 640.0F, 0.0F, 1.0F);
      float fadeOut = 1.0F - smooth(range(p, 0.965F, 1.0F));
      float birthGold = smooth(range(age, 92.0F, 170.0F)) * (1.0F - smooth(range(age, 520.0F, 640.0F))) * fadeOut;
      float birthFlash = smooth(range(age, 96.0F, 126.0F)) * (1.0F - smooth(range(age, 170.0F, 260.0F))) * fadeOut;
      float gold = Math.max(smooth(range(p, 0.36F, 0.58F)), birthGold * 0.78F) * fadeOut;
      float explosionGold = Math.max(smooth(range(p, 0.32F, 0.48F)) * (1.0F - smooth(range(p, 0.74F, 0.9F))), birthFlash * 1.4F) * fadeOut;
      float finalGold = Math.max(smooth(range(p, 0.56F, 0.88F)), birthGold * 0.92F) * fadeOut;
      float time = shaderTime + age * 0.045F;
      float scale = Math.max(1.0F, entityScale);
      renderBystanderPhainonSolarCoronaShell(matrix, gold, explosionGold, finalGold, time, scale);
      renderBystanderSunfallWorldSheets(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenSoftRadiance(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenAuroraAura(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenAuroraHaloStorm(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenAscensionPillars(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenFeatherStorm(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenSolarBursts(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenCrownRings(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenCoronaBreath(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenFallingStarlight(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenSeamlessRadiationVeil(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenDivineDustOrbit(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenHeavenlyGlyphRain(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenSolarWingCurtains(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenRoyalStarfield(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenAuraCrownCascade(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenWorldTreeThreads(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenHeavenfallCurtain(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenSolarPetalMandala(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenAbsoluteHaloScript(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenStarCorePulses(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenDivineIgnitionWings(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenRadiantCrownRain(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenAuraSunBreath(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenPetalAuraStorm(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenBlindingSolarPetalCrown(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenSeamlessRadiationVeil(matrix, gold * 1.15F, explosionGold * 1.25F, finalGold * 1.25F, time + 3.0F, scale * 1.22F);
      renderGoldenHeavenfallCurtain(matrix, gold * 1.1F, explosionGold * 1.25F, finalGold * 1.3F, time + 6.0F, scale * 1.18F);
      renderGoldenSolarWingCurtains(matrix, gold * 1.1F, explosionGold * 1.2F, finalGold * 1.25F, time + 9.0F, scale * 1.2F);
      renderGoldenPetalAuraStorm(matrix, gold * 1.08F, explosionGold * 1.2F, finalGold * 1.28F, time + 12.0F, scale * 1.16F);
      renderGoldenBlindingSolarPetalCrown(matrix, gold * 1.05F, explosionGold * 1.18F, finalGold * 1.25F, time + 15.0F, scale * 1.14F);
      renderGoldenBystanderRadiantSheets(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenHeavenPetalCurtain(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenCathedralLightWalls(matrix, gold, explosionGold, finalGold, time, scale);
      renderGoldenBystanderRadiantSheets(matrix, gold * 1.15F, explosionGold * 1.2F, finalGold * 1.28F, time + 18.0F, scale * 1.22F);
      renderGoldenHeavenPetalCurtain(matrix, gold * 1.1F, explosionGold * 1.18F, finalGold * 1.25F, time + 21.0F, scale * 1.18F);
      renderGoldenCathedralLightWalls(matrix, gold * 1.08F, explosionGold * 1.15F, finalGold * 1.22F, time + 24.0F, scale * 1.16F);
      renderGoldenAbsolutePetalSanctuary(matrix, gold, explosionGold, finalGold, time, scale);
      renderBystanderGoldenPetalHaloField(matrix, gold, explosionGold, finalGold, time, scale);
      renderBystanderPhainonSolarCoronaShell(matrix, gold * 1.15F, explosionGold * 1.2F, finalGold * 1.3F, time + 31.0F, scale * 1.18F);
      renderBystanderSunfallWorldSheets(matrix, gold * 1.1F, explosionGold * 1.15F, finalGold * 1.28F, time + 37.0F, scale * 1.16F);
   }

   private static void renderBystanderWorldVoidCanopy(
      Matrix4f matrix, float fade, float blackhole, float collapse, float explosion, float divine, float grand, float time, float scale
   ) {
      float strength = fade * (blackhole * 1.05F + collapse * 0.85F + explosion * 0.45F + divine * 0.35F + grand * 0.45F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.blendFuncSeparate(770, 771, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int rings = 16;
         int slices = 96;
         float domeRadius = scale * (18.0F + blackhole * 18.0F + collapse * 12.0F + explosion * 34.0F + grand * 48.0F);
         float domeHeight = scale * (10.0F + blackhole * 10.0F + collapse * 7.0F + explosion * 24.0F + grand * 30.0F);
         float alphaBase = Mth.clamp(strength * 0.34F, 0.0F, 0.82F);

         for (int r = 0; r < rings; r++) {
            float t0 = (float)r / rings;
            float t1 = (float)(r + 1) / rings;
            float a0 = t0 * (float) (Math.PI / 2);
            float a1 = t1 * (float) (Math.PI / 2);
            float y0 = Mth.sin(a0) * domeHeight - scale * 2.0F;
            float y1 = Mth.sin(a1) * domeHeight - scale * 2.0F;
            float rr0 = Mth.cos(a0) * domeRadius;
            float rr1 = Mth.cos(a1) * domeRadius;
            float band0 = 1.0F - t0 * 0.7F;
            float band1 = 1.0F - t1 * 0.7F;

            for (int i = 0; i < slices; i++) {
               float u0 = (float)i / slices;
               float u1 = (float)(i + 1) / slices;
               float ang0 = u0 * (float) (Math.PI * 2) + time * 0.025F;
               float ang1 = u1 * (float) (Math.PI * 2) + time * 0.025F;
               float pulse0 = 0.72F + 0.28F * Mth.sin(time * 1.4F + i * 0.37F + r);
               float pulse1 = 0.72F + 0.28F * Mth.sin(time * 1.4F + (i + 1) * 0.37F + r);
               float aBand0 = alphaBase * band0 * pulse0;
               float aBand1 = alphaBase * band1 * pulse1;
               float x00 = Mth.cos(ang0) * rr0;
               float z00 = Mth.sin(ang0) * rr0;
               float x01 = Mth.cos(ang1) * rr0;
               float z01 = Mth.sin(ang1) * rr0;
               float x10 = Mth.cos(ang0) * rr1;
               float z10 = Mth.sin(ang0) * rr1;
               float x11 = Mth.cos(ang1) * rr1;
               float z11 = Mth.sin(ang1) * rr1;
               buffer.addVertex(matrix, x00, y0, z00).setColor(0.0F, 0.0F, 0.0F, aBand0);
               buffer.addVertex(matrix, x10, y1, z10).setColor(0.02F, 0.0F, 0.0F, aBand1);
               buffer.addVertex(matrix, x11, y1, z11).setColor(0.04F, 0.0F, 0.0F, aBand1 * 0.88F);
               buffer.addVertex(matrix, x00, y0, z00).setColor(0.0F, 0.0F, 0.0F, aBand0);
               buffer.addVertex(matrix, x11, y1, z11).setColor(0.04F, 0.0F, 0.0F, aBand1 * 0.88F);
               buffer.addVertex(matrix, x01, y0, z01).setColor(0.0F, 0.0F, 0.0F, aBand0 * 0.92F);
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         renderGlowSphere(
            matrix,
            scale * (8.0F + blackhole * 22.0F + collapse * 18.0F + explosion * 46.0F + grand * 68.0F),
            0.34F,
            0.0F,
            0.0F,
            strength * 0.05F,
            18,
            64,
            time * 0.035F
         );
         renderGlowSphere(
            matrix, scale * (12.0F + blackhole * 30.0F + explosion * 64.0F + grand * 90.0F), 0.02F, 0.0F, 0.0F, strength * 0.042F, 18, 64, -time * 0.025F
         );
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      }
   }

   private static void renderBystanderAbyssCurtainStorm(
      Matrix4f matrix, float fade, float blackhole, float collapse, float explosion, float divine, float grand, float time, float scale
   ) {
      float strength = fade * (blackhole * 0.9F + collapse * 1.1F + explosion * 0.8F + divine * 0.45F + grand * 0.7F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.blendFuncSeparate(770, 771, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int curtains = 64;
         int segments = 42;

         for (int c = 0; c < curtains; c++) {
            float seed = c * 97.17F;
            float yaw = c * (float) (Math.PI * 2) / curtains + time * (0.012F + hash(seed) * 0.02F);
            float radius = scale * (6.0F + hash(seed + 1.0F) * 28.0F + blackhole * 16.0F + explosion * 28.0F + grand * 36.0F);
            float height = scale * (11.0F + hash(seed + 2.0F) * 18.0F + explosion * 26.0F + grand * 34.0F);
            float width = scale * (0.8F + hash(seed + 3.0F) * 3.2F + collapse * 3.0F + grand * 5.0F);
            float alpha = Mth.clamp(strength * (0.055F + hash(seed + 4.0F) * 0.075F), 0.0F, 0.7F);
            Vec3 side = new Vec3(Mth.cos(yaw + (float) (Math.PI / 2)), 0.0, Mth.sin(yaw + (float) (Math.PI / 2)));

            for (int i = 0; i < segments; i++) {
               float t0 = (float)i / segments;
               float t1 = (float)(i + 1) / segments;
               float y0 = -scale * 2.0F + t0 * height;
               float y1 = -scale * 2.0F + t1 * height;
               float soft0 = Mth.sin(t0 * (float) Math.PI);
               float soft1 = Mth.sin(t1 * (float) Math.PI);
               float twist0 = yaw + Mth.sin(t0 * 8.0F + time * 0.8F + seed) * 0.16F;
               float twist1 = yaw + Mth.sin(t1 * 8.0F + time * 0.8F + seed) * 0.16F;
               float r0 = radius + soft0 * scale * (2.0F + explosion * 10.0F + grand * 16.0F);
               float r1 = radius + soft1 * scale * (2.0F + explosion * 10.0F + grand * 16.0F);
               Vec3 p0 = new Vec3(Mth.cos(twist0) * r0, y0, Mth.sin(twist0) * r0);
               Vec3 p1 = new Vec3(Mth.cos(twist1) * r1, y1, Mth.sin(twist1) * r1);
               float w0 = width * (0.25F + soft0 * 0.75F);
               float w1 = width * (0.25F + soft1 * 0.75F);
               float a0 = alpha * soft0;
               float a1 = alpha * soft1;
               addQuad(
                  buffer,
                  matrix,
                  new PhainonAcquirementVfxClient.RibbonVertex((float)(p0.x - side.x * w0), (float)p0.y, (float)(p0.z - side.z * w0), 0.0F, 0.0F, 0.0F, a0),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)(p1.x - side.x * w1), (float)p1.y, (float)(p1.z - side.z * w1), 0.07F, 0.0F, 0.0F, a1),
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p1.x + side.x * w1), (float)p1.y, (float)(p1.z + side.z * w1), 0.02F, 0.0F, 0.0F, a1 * 0.85F
                  ),
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p0.x + side.x * w0), (float)p0.y, (float)(p0.z + side.z * w0), 0.0F, 0.0F, 0.0F, a0 * 0.7F
                  ),
                  1.0F
               );
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      }
   }

   private static void renderBystanderDawnPetalBlast(Matrix4f matrix, float fade, float explosion, float divine, float grand, float time, float scale) {
      float strength = fade * (explosion * 2.6F + divine * 1.35F + grand * 3.75F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int layer = 0; layer < 16; layer++) {
            int petals = 72 + layer * 18;
            float spin = time * (0.65F + layer * 0.045F) * (layer % 2 == 0 ? 1.0F : -1.0F);
            float lift = scale * (-2.5F + layer * 0.42F);
            float base = scale * (1.0F + layer * 0.56F + explosion * 8.0F + grand * 18.0F);

            for (int i = 0; i < petals; i++) {
               float seed = layer * 10000.0F + i * 41.77F;
               float angle = i * (float) (Math.PI * 2) / petals + spin + hash(seed) * 0.08F;
               float out = scale * (20.0F + hash(seed + 1.0F) * (74.0F + explosion * 70.0F + grand * 130.0F));
               float width = scale * (0.18F + hash(seed + 2.0F) * 0.74F) * (1.0F + grand * 0.5F);
               float y = lift + scale * ((hash(seed + 3.0F) - 0.5F) * (5.0F + explosion * 14.0F + grand * 20.0F));
               float tipY = y + scale * ((hash(seed + 4.0F) - 0.35F) * (10.0F + explosion * 20.0F + grand * 28.0F));
               float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
               float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
               float x0 = Mth.cos(angle) * base;
               float z0 = Mth.sin(angle) * base;
               float x1 = Mth.cos(angle + Mth.sin(seed) * 0.045F) * (base + out);
               float z1 = Mth.sin(angle + Mth.sin(seed) * 0.045F) * (base + out);
               float alpha = strength * (0.02F + hash(seed + 5.0F) * 0.07F);
               buffer.addVertex(matrix, x0 - sx, y, z0 - sz).setColor(1.0F, 0.34F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x1, tipY, z1).setColor(1.0F, 0.98F, 0.32F, alpha);
               buffer.addVertex(matrix, x0 + sx, y, z0 + sz).setColor(1.0F, 0.7F, 0.04F, 0.0F);
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);

         for (int i = 0; i < 10; i++) {
            float wave = 0.5F + 0.5F * Mth.sin(time * (0.52F + i * 0.04F) + i);
            renderRing(
               matrix,
               scale * (0.25F + i * 0.1F),
               scale * (24.0F + explosion * 90.0F + grand * 150.0F + i * 8.0F + wave * 14.0F),
               440,
               time * (1.0F + i * 0.05F),
               1.0F,
               0.84F,
               0.1F,
               strength * (0.05F + i * 0.004F),
               scale * (0.48F + i * 0.08F)
            );
         }

         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      }
   }

   private static void renderBystanderPhainonSolarCoronaShell(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.75F + explosionGold * 1.45F + finalGold * 2.75F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         renderGlowSphere(matrix, scale * (7.0F + explosionGold * 17.0F + finalGold * 22.0F), 1.0F, 0.82F, 0.08F, strength * 0.045F, 24, 80, time * 0.02F);
         renderGlowSphere(matrix, scale * (12.0F + explosionGold * 28.0F + finalGold * 36.0F), 1.0F, 0.54F, 0.02F, strength * 0.028F, 22, 72, -time * 0.018F);

         for (int i = 0; i < 14; i++) {
            float wave = 0.5F + 0.5F * Mth.sin(time * (0.42F + i * 0.035F) + i * 0.77F);
            renderOffsetRing(
               matrix,
               scale * (0.65F + i * 0.09F),
               scale * (5.0F + i * 1.6F + explosionGold * 7.0F + finalGold * 11.0F + wave * 4.0F),
               scale * (-0.8F + i * 0.5F),
               340,
               time * (0.72F + i * 0.045F),
               1.0F,
               0.86F,
               0.12F,
               strength * (0.028F + i * 0.004F),
               scale * (0.1F + i * 0.012F)
            );
         }

         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      }
   }

   private static void renderBystanderSunfallWorldSheets(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.8F + explosionGold * 1.2F + finalGold * 2.35F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 620; i++) {
            float seed = i * 31.371F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.012F + hash(seed + 1.0F) * 0.026F);
            float radius = scale * (1.5F + hash(seed + 2.0F) * (16.0F + finalGold * 18.0F + explosionGold * 12.0F));
            float fall = Mth.frac(hash(seed + 3.0F) + time * (0.05F + hash(seed + 4.0F) * 0.07F));
            float y0 = scale * (22.0F + finalGold * 18.0F + explosionGold * 10.0F) - fall * scale * (28.0F + finalGold * 20.0F + explosionGold * 12.0F);
            float len = scale * (1.6F + hash(seed + 5.0F) * 5.5F) * (1.0F + finalGold * 0.65F + explosionGold * 1.0F);
            float width = scale * (0.016F + hash(seed + 6.0F) * 0.06F);
            float alpha = strength * Mth.sin(fall * (float) Math.PI) * (0.025F + hash(seed + 7.0F) * 0.07F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            wrote |= addBeam(
               buffer,
               matrix,
               x,
               y0,
               z,
               x * 0.9F + Mth.sin(seed) * scale * 0.25F,
               y0 - len,
               z * 0.9F + Mth.cos(seed) * scale * 0.25F,
               width,
               1.0F,
               0.9F,
               0.2F,
               alpha
            );
         }

         safeDraw(buffer, wrote);
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      }
   }

   private static void renderBystanderGoldenPetalHaloField(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.9F + explosionGold * 1.55F + finalGold * 3.1F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int crown = 0; crown < 14; crown++) {
            int petals = 96 + crown * 18;
            float y = scale * (-1.3F + crown * 0.55F);
            float spin = time * (0.38F + crown * 0.036F) * (crown % 2 == 0 ? 1.0F : -1.0F);
            float radiusBase = scale * (1.5F + crown * 0.55F + finalGold * 3.4F + explosionGold * 2.2F);

            for (int i = 0; i < petals; i++) {
               float seed = crown * 7100.0F + i * 33.771F;
               float angle = i * (float) (Math.PI * 2) / petals + spin + hash(seed) * 0.09F;
               float radius = radiusBase + hash(seed + 1.0F) * scale * (3.5F + finalGold * 7.5F + explosionGold * 5.0F);
               float flutter = Mth.sin(time * (1.45F + hash(seed + 2.0F)) + seed) * scale * 0.3F;
               float len = scale * (0.75F + hash(seed + 3.0F) * 2.75F) * (1.0F + finalGold * 1.0F + explosionGold * 0.75F);
               float width = scale * (0.06F + hash(seed + 4.0F) * 0.28F);
               float x = Mth.cos(angle) * radius;
               float z = Mth.sin(angle) * radius;
               float tx = Mth.cos(angle + 0.16F + Mth.sin(seed) * 0.1F) * len;
               float tz = Mth.sin(angle + 0.16F + Mth.sin(seed) * 0.1F) * len;
               float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
               float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
               float alpha = strength * (0.018F + hash(seed + 5.0F) * 0.06F) * (0.62F + 0.38F * Mth.sin(time * 2.2F + seed));
               buffer.addVertex(matrix, x - sx, y + flutter, z - sz).setColor(1.0F, 0.44F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x + tx, y + flutter + len * 0.24F, z + tz).setColor(1.0F, 0.98F, 0.34F, alpha);
               buffer.addVertex(matrix, x + sx, y + flutter, z + sz).setColor(1.0F, 0.72F, 0.04F, 0.0F);
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      }
   }

   private static void renderGrandPhainonBirthExplosion(Matrix4f matrix, float fade, float explosion, float divine, float grand, float time, float scale) {
      float strength = fade * (grand * 2.95F + explosion * 1.85F + divine * 0.85F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         renderGlowSphere(matrix, scale * (2.4F + grand * 44.0F + explosion * 18.0F), 1.0F, 0.76F, 0.06F, strength * 0.08F, 20, 64, time * 0.05F);
         renderGlowSphere(matrix, scale * (1.5F + grand * 28.0F), 1.0F, 0.95F, 0.3F, strength * 0.05F, 18, 58, -time * 0.06F);
         renderRing(matrix, scale * 0.25F, scale * (8.0F + grand * 56.0F), 300, time * 1.1F, 1.0F, 0.8F, 0.08F, strength * 0.32F, scale * 0.34F);
         renderRing(matrix, scale * 0.7F, scale * (12.0F + grand * 72.0F), 300, -time * 0.76F, 1.0F, 0.52F, 0.02F, strength * 0.22F, scale * 0.52F);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 420; i++) {
            float seed = i * 47.113F;
            float angle = i * (float) (Math.PI * 2) / 420.0F + hash(seed) * 0.15F;
            float pitch = -0.45F + hash(seed + 1.0F) * 1.15F;
            float inner = scale * (0.18F + hash(seed + 2.0F) * 1.2F);
            float outer = scale * (12.0F + grand * (46.0F + hash(seed + 3.0F) * 62.0F));
            float y0 = (hash(seed + 4.0F) - 0.5F) * scale * 1.4F;
            float y1 = y0 + Mth.sin(pitch) * scale * (6.0F + grand * 18.0F);
            float width = scale * (0.018F + hash(seed + 5.0F) * 0.075F) * (1.0F + grand * 0.9F);
            float alpha = strength * (0.06F + hash(seed + 6.0F) * 0.13F);
            wrote |= addBeam(
               buffer,
               matrix,
               Mth.cos(angle) * inner,
               y0,
               Mth.sin(angle) * inner,
               Mth.cos(angle) * outer,
               y1,
               Mth.sin(angle) * outer,
               width,
               1.0F,
               0.84F,
               0.12F,
               alpha
            );
         }

         for (int i = 0; i < 260; i++) {
            float seed = i * 59.39F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.02F + hash(seed + 1.0F) * 0.05F);
            float radius = scale * (2.0F + hash(seed + 2.0F) * (18.0F + grand * 44.0F));
            float y = scale * ((hash(seed + 3.0F) - 0.42F) * (8.0F + grand * 18.0F));
            float size = scale * (0.04F + hash(seed + 4.0F) * 0.18F);
            float alpha = strength * (0.045F + hash(seed + 5.0F) * 0.1F);
            wrote |= addBillboardQuad(buffer, matrix, Mth.cos(angle) * radius, y, Mth.sin(angle) * radius, size, 1.0F, 0.9F, 0.22F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenDivineIgnitionWings(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.8F + explosionGold * 1.6F + finalGold * 1.2F;
      if (!(strength <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int sideIndex = -1; sideIndex <= 1; sideIndex += 2) {
            for (int i = 0; i < 150; i++) {
               float seed = i * 17.81F + sideIndex * 101.0F;
               float t0 = i / 150.0F;
               float t1 = (i + 1) / 150.0F;
               float y0 = scale * (-0.55F + t0 * (8.5F + finalGold * 6.0F));
               float y1 = scale * (-0.55F + t1 * (8.5F + finalGold * 6.0F));
               float spread0 = Mth.sin(t0 * (float) Math.PI) * scale * (1.8F + explosionGold * 4.5F + finalGold * 2.7F);
               float spread1 = Mth.sin(t1 * (float) Math.PI) * scale * (1.8F + explosionGold * 4.5F + finalGold * 2.7F);
               float back0 = -scale * (0.55F + t0 * (1.2F + hash(seed) * 2.2F));
               float back1 = -scale * (0.55F + t1 * (1.2F + hash(seed + 1.0F) * 2.2F));
               float flutter0 = Mth.sin(time * 1.2F + seed + t0 * 12.0F) * scale * 0.18F;
               float flutter1 = Mth.sin(time * 1.2F + seed + t1 * 12.0F) * scale * 0.18F;
               float width = scale * (0.15F + hash(seed + 2.0F) * 0.35F);
               float alpha = strength * (0.03F + hash(seed + 3.0F) * 0.075F) * Mth.sin(t0 * (float) Math.PI);
               addQuad(
                  buffer,
                  matrix,
                  new PhainonAcquirementVfxClient.RibbonVertex(sideIndex * (spread0 - width), y0, back0 + flutter0, 1.0F, 0.58F, 0.02F, 0.0F),
                  new PhainonAcquirementVfxClient.RibbonVertex(sideIndex * spread1, y1, back1 + flutter1, 1.0F, 0.96F, 0.28F, alpha),
                  new PhainonAcquirementVfxClient.RibbonVertex(sideIndex * (spread1 + width), y1, back1 + flutter1, 1.0F, 0.78F, 0.06F, alpha * 0.45F),
                  new PhainonAcquirementVfxClient.RibbonVertex(sideIndex * spread0, y0, back0 + flutter0, 1.0F, 0.44F, 0.0F, 0.0F),
                  1.0F
               );
               wrote = true;
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenRadiantCrownRain(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.65F + explosionGold * 1.25F + finalGold * 1.7F;
      if (!(strength <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 420; i++) {
            float seed = i * 23.47F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.018F + hash(seed + 1.0F) * 0.032F);
            float radius = scale * (0.8F + hash(seed + 2.0F) * (7.5F + finalGold * 7.0F + explosionGold * 7.0F));
            float local = Mth.frac(hash(seed + 3.0F) + time * (0.05F + hash(seed + 4.0F) * 0.055F));
            float y = scale * (11.0F + finalGold * 7.0F + explosionGold * 4.0F) - local * scale * (13.5F + finalGold * 8.0F);
            float length = scale * (0.7F + hash(seed + 5.0F) * 3.0F);
            float width = scale * (0.01F + hash(seed + 6.0F) * 0.036F);
            float alpha = strength * Mth.sin(local * (float) Math.PI) * (0.035F + hash(seed + 7.0F) * 0.08F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            wrote |= addBeam(buffer, matrix, x, y, z, x * 0.92F, y - length, z * 0.92F, width, 1.0F, 0.86F, 0.16F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenAuraSunBreath(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.55F + explosionGold * 1.35F + finalGold * 1.35F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);

         for (int i = 0; i < 8; i++) {
            float wave = Mth.sin(time * (0.7F + i * 0.05F)) * 0.5F + 0.5F;
            float inner = scale * (0.45F + i * 0.12F);
            float outer = scale * (3.0F + i * 1.15F + finalGold * 6.0F + explosionGold * 4.0F + wave * 1.7F);
            float alpha = strength * (0.03F + i * 0.01F);
            renderOffsetRing(
               matrix, inner, outer, scale * (-0.45F + i * 0.42F), 240, time * (0.5F + i * 0.08F), 1.0F, 0.78F, 0.08F, alpha, scale * (0.08F + i * 0.02F)
            );
         }
      }
   }

   private static void renderAsmodayRedHole(Matrix4f matrix, Camera camera, float age, float fade, float time, float pulse, float radius) {
      if (!(fade <= 5.0E-4F) && !(radius <= 0.08F)) {
         float grow = Mth.clamp(age / 10.0F, 0.0F, 1.0F);
         float r = Math.max(radius * grow, radius * 0.22F);
         if (!(r <= 0.1F)) {
            float yDiskYaw = horizontalYawToCamera(camera.getPosition(), Vec3.ZERO);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            renderAsmodaySpawnShell(matrix, age, fade * 1.25F, time, r);
            renderAsmodayOuterGlow(matrix, time, fade * 1.5F, r);
            renderAsmodayDistantWarpedDust(matrix, time, fade * 1.7F, r);
            renderAsmodayAccretionVolume(matrix, time, fade * 1.3F, pulse, false, r);
            renderAsmodayPhotonHalo(matrix, time, fade * 1.7F, pulse, false, r);
            renderAsmodayLensedWall(matrix, time, fade * 1.4F, false, r);
            renderAsmodayInnerFilaments(matrix, time, fade * 1.7F, false, r);
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            renderAsmodayBlackSphere(matrix, r * 0.742F);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.blendFuncSeparate(770, 1, 1, 0);
            renderAsmodayAccretionVolume(matrix, time, fade * 1.55F, pulse, true, r);
            renderAsmodayCondensedYDisk(matrix, time, fade * 1.65F, pulse, false, yDiskYaw, r);
            renderAsmodayCondensedYDisk(matrix, time, fade * 1.95F, pulse, true, yDiskYaw, r);
            renderAsmodayPhotonHalo(matrix, time, fade * 2.05F, pulse, true, r);
            renderAsmodayLensedWall(matrix, time, fade * 1.7F, true, r);
            renderAsmodayInnerFilaments(matrix, time, fade * 2.05F, true, r);
            renderAsmodayEquatorialLightning(matrix, time, fade * 2.25F, r);
            renderAsmodayPolarSpewer(matrix, time, fade * 1.5F, r);
            renderAsmodayOuterRedDust(matrix, time, fade * 2.0F, r);
         }
      }
   }

   private static void renderGoldenBystanderRadiantSheets(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.95F + explosionGold * 1.55F + finalGold * 2.85F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int sheetCount = 52;
         int segments = 96;

         for (int s = 0; s < sheetCount; s++) {
            float seed = s * 83.177F;
            float yaw = s * (float) (Math.PI * 2) / sheetCount;
            yaw += time * (0.012F + hash(seed + 1.0F) * 0.03F);
            yaw += Mth.sin(time * 0.11F + seed) * 0.13F;
            float height = scale * (8.5F + hash(seed + 2.0F) * 12.0F + finalGold * 15.0F + explosionGold * 8.0F);
            float baseRadius = scale * (0.85F + hash(seed + 3.0F) * 2.6F);
            float spread = scale * (1.8F + hash(seed + 4.0F) * 4.2F) * (1.0F + finalGold * 0.8F + explosionGold * 0.95F);
            float alphaBase = strength * (0.014F + hash(seed + 5.0F) * 0.04F);
            Vec3 side = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));

            for (int i = 0; i < segments; i++) {
               float t0 = (float)i / segments;
               float t1 = (float)(i + 1) / segments;
               float soft0 = Mth.sin(t0 * (float) Math.PI);
               float soft1 = Mth.sin(t1 * (float) Math.PI);
               float ripple0 = Mth.sin(t0 * 18.0F + time * 1.35F + seed) * 0.18F;
               float ripple1 = Mth.sin(t1 * 18.0F + time * 1.35F + seed) * 0.18F;
               float twist0 = yaw + soft0 * (0.38F + hash(seed + 6.0F) * 0.95F) + ripple0;
               float twist1 = yaw + soft1 * (0.38F + hash(seed + 6.0F) * 0.95F) + ripple1;
               float r0 = baseRadius + soft0 * scale * (2.0F + finalGold * 4.5F + explosionGold * 5.5F);
               float r1 = baseRadius + soft1 * scale * (2.0F + finalGold * 4.5F + explosionGold * 5.5F);
               float y0 = -scale * 1.1F + t0 * height + Mth.sin(time * 0.8F + seed + t0 * 14.0F) * scale * 0.22F;
               float y1 = -scale * 1.1F + t1 * height + Mth.sin(time * 0.8F + seed + t1 * 14.0F) * scale * 0.22F;
               float width0 = spread * (0.18F + soft0 * 0.82F);
               float width1 = spread * (0.18F + soft1 * 0.82F);
               Vec3 p0 = new Vec3(Mth.cos(twist0) * r0, y0, Mth.sin(twist0) * r0);
               Vec3 p1 = new Vec3(Mth.cos(twist1) * r1, y1, Mth.sin(twist1) * r1);
               float shimmer = 0.6F + 0.4F * Mth.sin(time * 2.1F + seed + t0 * 9.0F);
               float a0 = alphaBase * soft0 * shimmer;
               float a1 = alphaBase * soft1 * shimmer;
               addQuad(
                  buffer,
                  matrix,
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p0.x - side.x * width0), (float)p0.y, (float)(p0.z - side.z * width0), 1.0F, 0.46F, 0.0F, 0.0F
                  ),
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p1.x - side.x * width1), (float)p1.y, (float)(p1.z - side.z * width1), 1.0F, 0.98F, 0.34F, a1
                  ),
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p1.x + side.x * width1), (float)p1.y, (float)(p1.z + side.z * width1), 1.0F, 0.78F, 0.05F, a1 * 0.85F
                  ),
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p0.x + side.x * width0), (float)p0.y, (float)(p0.z + side.z * width0), 1.0F, 0.42F, 0.0F, a0 * 0.22F
                  ),
                  1.0F
               );
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);

         for (int i = 0; i < 7; i++) {
            float wave = Mth.sin(time * (0.45F + i * 0.04F) + i * 0.7F) * 0.5F + 0.5F;
            renderOffsetRing(
               matrix,
               scale * (0.45F + i * 0.12F),
               scale * (6.5F + i * 1.8F + finalGold * 13.0F + explosionGold * 8.0F + wave * 4.0F),
               scale * (-0.65F + i * 0.65F),
               320,
               time * (0.55F + i * 0.08F),
               1.0F,
               0.78F,
               0.08F,
               strength * (0.03F + i * 0.006F),
               scale * (0.12F + i * 0.025F)
            );
         }
      }
   }

   private static void renderGoldenHeavenPetalCurtain(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.88F + explosionGold * 1.75F + finalGold * 2.75F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int layer = 0; layer < 11; layer++) {
            int petals = 96 + layer * 22;
            float spin = time * (0.36F + layer * 0.05F) * (layer % 2 == 0 ? 1.0F : -1.0F);
            float yBase = scale * (-1.15F + layer * 0.6F);
            float radiusBase = scale * (1.1F + layer * 0.62F + finalGold * 2.0F + explosionGold * 4.2F);

            for (int i = 0; i < petals; i++) {
               float seed = layer * 9000.0F + i * 37.557F;
               float angle = i * (float) (Math.PI * 2) / petals + spin + hash(seed) * 0.13F;
               float fall = Mth.frac(hash(seed + 1.0F) + time * (0.03F + hash(seed + 2.0F) * 0.05F));
               float fallSoft = Mth.sin(fall * (float) Math.PI);
               float flutter = Mth.sin(time * (1.15F + hash(seed + 3.0F) * 1.15F) + seed) * scale * 0.28F;
               float radius = radiusBase + hash(seed + 4.0F) * scale * (3.0F + finalGold * 5.8F + explosionGold * 8.0F);
               float y = yBase + flutter + hash(seed + 5.0F) * scale * (1.5F + finalGold * 2.6F);
               y += scale * (3.5F - fall * (7.0F + finalGold * 3.0F));
               float length = scale * (0.75F + hash(seed + 6.0F) * 2.8F) * (1.0F + finalGold * 1.05F + explosionGold * 1.35F);
               float width = scale * (0.075F + hash(seed + 7.0F) * 0.28F) * (1.0F + finalGold * 0.25F);
               float x = Mth.cos(angle) * radius;
               float z = Mth.sin(angle) * radius;
               float curve = 0.18F + Mth.sin(seed) * 0.16F;
               float tx = Mth.cos(angle + curve) * length;
               float tz = Mth.sin(angle + curve) * length;
               float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
               float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
               float flicker = 0.65F + 0.35F * Mth.sin(time * 2.8F + seed);
               float alpha = strength * fallSoft * flicker * (0.026F + hash(seed + 8.0F) * 0.076F);
               buffer.addVertex(matrix, x - sx, y, z - sz).setColor(1.0F, 0.42F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x + tx, y + length * 0.3F, z + tz).setColor(1.0F, 0.99F, 0.38F, alpha);
               buffer.addVertex(matrix, x + sx, y, z + sz).setColor(1.0F, 0.72F, 0.04F, 0.0F);
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);
         BufferBuilder streaks = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wroteStreaks = false;

         for (int i = 0; i < 520; i++) {
            float seed = i * 47.331F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.012F + hash(seed + 1.0F) * 0.022F);
            float radius = scale * (0.8F + hash(seed + 2.0F) * (13.0F + finalGold * 13.0F + explosionGold * 11.0F));
            float fall = Mth.frac(hash(seed + 3.0F) + time * (0.07F + hash(seed + 4.0F) * 0.09F));
            float y0 = scale * (16.0F + finalGold * 13.0F + explosionGold * 8.0F) - fall * scale * (21.0F + finalGold * 12.0F);
            float len = scale * (0.9F + hash(seed + 5.0F) * 3.7F);
            float width = scale * (0.01F + hash(seed + 6.0F) * 0.034F);
            float alpha = strength * Mth.sin(fall * (float) Math.PI) * (0.024F + hash(seed + 7.0F) * 0.06F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            wroteStreaks |= addBeam(
               streaks,
               matrix,
               x,
               y0,
               z,
               x * 0.92F + Mth.sin(seed) * scale * 0.18F,
               y0 - len,
               z * 0.92F + Mth.cos(seed) * scale * 0.18F,
               width,
               1.0F,
               0.9F,
               0.22F,
               alpha
            );
         }

         safeDraw(streaks, wroteStreaks);
      }
   }

   private static void renderGoldenCathedralLightWalls(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.8F + explosionGold * 1.45F + finalGold * 2.95F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int walls = 28;
         int segments = 72;

         for (int w = 0; w < walls; w++) {
            float seed = w * 101.731F;
            float angle = w * (float) (Math.PI * 2) / walls;
            angle += time * (0.008F + hash(seed + 1.0F) * 0.018F);
            angle += Mth.sin(time * 0.2F + seed) * 0.035F;
            float radius = scale * (4.0F + hash(seed + 2.0F) * 5.0F + finalGold * 7.5F + explosionGold * 5.5F);
            float wallWidth = scale * (1.0F + hash(seed + 3.0F) * 2.1F) * (1.0F + finalGold * 0.35F);
            float height = scale * (10.0F + hash(seed + 4.0F) * 12.0F + finalGold * 16.0F);
            float alphaBase = strength * (0.016F + hash(seed + 5.0F) * 0.042F);
            Vec3 center = new Vec3(Mth.cos(angle) * radius, 0.0, Mth.sin(angle) * radius);
            Vec3 side = new Vec3(Mth.cos(angle + (float) (Math.PI / 2)), 0.0, Mth.sin(angle + (float) (Math.PI / 2)));
            float archPhase = time * (0.45F + hash(seed + 6.0F) * 0.35F) + seed;

            for (int i = 0; i < segments; i++) {
               float t0 = (float)i / segments;
               float t1 = (float)(i + 1) / segments;
               float y0 = -scale * 1.0F + t0 * height;
               float y1 = -scale * 1.0F + t1 * height;
               float arch0 = Mth.sin(t0 * (float) Math.PI);
               float arch1 = Mth.sin(t1 * (float) Math.PI);
               float crown0 = Mth.sin(t0 * (float) Math.PI * 3.0F + archPhase) * scale * 0.16F;
               float crown1 = Mth.sin(t1 * (float) Math.PI * 3.0F + archPhase) * scale * 0.16F;
               float width0 = wallWidth * (0.28F + arch0 * 1.45F);
               float width1 = wallWidth * (0.28F + arch1 * 1.45F);
               float shimmer0 = 0.62F + 0.38F * Mth.sin(archPhase + t0 * 22.0F);
               float shimmer1 = 0.62F + 0.38F * Mth.sin(archPhase + t1 * 22.0F);
               float a0 = alphaBase * arch0 * shimmer0;
               float a1 = alphaBase * arch1 * shimmer1;
               Vec3 p00 = center.subtract(side.scale(width0));
               Vec3 p01 = center.add(side.scale(width0));
               Vec3 p10 = center.subtract(side.scale(width1));
               Vec3 p11 = center.add(side.scale(width1));
               addQuad(
                  buffer,
                  matrix,
                  new PhainonAcquirementVfxClient.RibbonVertex((float)p00.x, y0 + crown0, (float)p00.z, 1.0F, 0.44F, 0.0F, 0.0F),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)p10.x, y1 + crown1, (float)p10.z, 1.0F, 0.99F, 0.38F, a1),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)p11.x, y1 + crown1, (float)p11.z, 1.0F, 0.82F, 0.1F, a1 * 0.92F),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)p01.x, y0 + crown0, (float)p01.z, 1.0F, 0.42F, 0.0F, a0 * 0.22F),
                  1.0F
               );
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);
         BufferBuilder pillars = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrotePillars = false;

         for (int i = 0; i < 180; i++) {
            float seed = i * 59.117F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.006F + hash(seed + 1.0F) * 0.014F);
            float radius = scale * (4.5F + hash(seed + 2.0F) * (10.0F + finalGold * 11.0F));
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            float y0 = -scale * 1.0F;
            float y1 = scale * (8.0F + hash(seed + 3.0F) * 18.0F + finalGold * 13.0F);
            float width = scale * (0.02F + hash(seed + 4.0F) * 0.055F);
            float pulse = 0.55F + 0.45F * Mth.sin(time * (1.1F + hash(seed + 5.0F)) + seed);
            float alpha = strength * pulse * (0.025F + hash(seed + 6.0F) * 0.06F);
            wrotePillars |= addBeam(
               pillars, matrix, x, y0, z, x + Mth.sin(seed) * scale * 0.2F, y1, z + Mth.cos(seed) * scale * 0.2F, width, 1.0F, 0.88F, 0.16F, alpha
            );
         }

         safeDraw(pillars, wrotePillars);
      }
   }

   private static void renderPhainonSolarPetalDetonation(Matrix4f matrix, float fade, float explosion, float divine, float grand, float time, float scale) {
      float strength = fade * (explosion * 2.9F + divine * 1.6F + grand * 4.4F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         renderGlowSphere(matrix, scale * (2.0F + explosion * 44.0F + grand * 132.0F), 1.0F, 0.88F, 0.1F, strength * 0.07F, 26, 86, time * 0.025F);
         renderGlowSphere(matrix, scale * (1.0F + explosion * 24.0F + grand * 74.0F), 1.0F, 0.99F, 0.42F, strength * 0.055F, 22, 72, -time * 0.03F);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int layer = 0; layer < 12; layer++) {
            int petals = 54 + layer * 18;
            float spin = time * (0.82F + layer * 0.055F) * (layer % 2 == 0 ? 1.0F : -1.0F);
            float lift = scale * (-2.0F + layer * 0.34F);
            float baseRadius = scale * (1.8F + layer * 0.65F + explosion * 8.0F + grand * 14.0F);

            for (int i = 0; i < petals; i++) {
               float seed = layer * 10000.0F + i * 41.713F;
               float angle = i * (float) (Math.PI * 2) / petals + spin + hash(seed) * 0.07F;
               float burst = scale * (12.0F + hash(seed + 1.0F) * (60.0F + explosion * 64.0F + grand * 115.0F));
               float width = scale * (0.2F + hash(seed + 2.0F) * 0.62F) * (1.0F + grand * 0.55F);
               float y = lift + scale * ((hash(seed + 3.0F) - 0.5F) * (5.0F + explosion * 14.0F + grand * 18.0F));
               float tipY = y + scale * ((hash(seed + 4.0F) - 0.35F) * (10.0F + explosion * 20.0F + grand * 24.0F));
               float r0 = baseRadius;
               float r1 = baseRadius + burst;
               float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
               float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
               float x0 = Mth.cos(angle) * r0;
               float z0 = Mth.sin(angle) * r0;
               float x1 = Mth.cos(angle + Mth.sin(seed) * 0.045F) * r1;
               float z1 = Mth.sin(angle + Mth.sin(seed) * 0.045F) * r1;
               float alpha = strength * (0.026F + hash(seed + 5.0F) * 0.08F);
               buffer.addVertex(matrix, x0 - sx, y, z0 - sz).setColor(1.0F, 0.42F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x1, tipY, z1).setColor(1.0F, 1.0F, 0.38F, alpha);
               buffer.addVertex(matrix, x0 + sx, y, z0 + sz).setColor(1.0F, 0.72F, 0.04F, 0.0F);
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);

         for (int i = 0; i < 8; i++) {
            float wave = Mth.sin(time * (0.6F + i * 0.055F) + i) * 0.5F + 0.5F;
            renderRing(
               matrix,
               scale * (0.3F + i * 0.14F),
               scale * (18.0F + explosion * 80.0F + grand * 130.0F + i * 7.0F + wave * 10.0F),
               420,
               time * (1.0F + i * 0.06F),
               1.0F,
               0.82F,
               0.08F,
               strength * (0.06F + i * 0.006F),
               scale * (0.42F + i * 0.1F)
            );
         }
      }
   }

   private static void renderGoldenAbsolutePetalSanctuary(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.65F + explosionGold * 1.25F + finalGold * 2.8F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int crown = 0; crown < 10; crown++) {
            int petals = 88 + crown * 20;
            float y = scale * (-0.85F + crown * 0.55F);
            float spin = time * (0.42F + crown * 0.045F) * (crown % 2 == 0 ? 1.0F : -1.0F);
            float radiusBase = scale * (1.25F + crown * 0.48F + finalGold * 2.6F + explosionGold * 2.0F);

            for (int i = 0; i < petals; i++) {
               float seed = crown * 7000.0F + i * 33.771F;
               float angle = i * (float) (Math.PI * 2) / petals + spin + hash(seed) * 0.09F;
               float radius = radiusBase + hash(seed + 1.0F) * scale * (2.8F + finalGold * 6.4F + explosionGold * 4.0F);
               float flutter = Mth.sin(time * (1.6F + hash(seed + 2.0F)) + seed) * scale * 0.22F;
               float len = scale * (0.7F + hash(seed + 3.0F) * 2.15F) * (1.0F + finalGold * 0.9F + explosionGold * 0.65F);
               float width = scale * (0.055F + hash(seed + 4.0F) * 0.22F);
               float x = Mth.cos(angle) * radius;
               float z = Mth.sin(angle) * radius;
               float tx = Mth.cos(angle + 0.16F + Mth.sin(seed) * 0.1F) * len;
               float tz = Mth.sin(angle + 0.16F + Mth.sin(seed) * 0.1F) * len;
               float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
               float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
               float alpha = strength * (0.018F + hash(seed + 5.0F) * 0.055F) * (0.62F + 0.38F * Mth.sin(time * 2.2F + seed));
               buffer.addVertex(matrix, x - sx, y + flutter, z - sz).setColor(1.0F, 0.44F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x + tx, y + flutter + len * 0.24F, z + tz).setColor(1.0F, 0.98F, 0.34F, alpha);
               buffer.addVertex(matrix, x + sx, y + flutter, z + sz).setColor(1.0F, 0.72F, 0.04F, 0.0F);
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);

         for (int i = 0; i < 12; i++) {
            float wave = Mth.sin(time * (0.38F + i * 0.035F) + i * 0.7F) * 0.5F + 0.5F;
            renderOffsetRing(
               matrix,
               scale * (0.55F + i * 0.1F),
               scale * (4.5F + i * 1.45F + finalGold * 9.5F + explosionGold * 5.0F + wave * 2.8F),
               scale * (-0.5F + i * 0.44F),
               320,
               time * (0.7F + i * 0.055F),
               1.0F,
               0.9F,
               0.16F,
               strength * (0.026F + i * 0.004F),
               scale * (0.1F + i * 0.015F)
            );
         }
      }
   }

   private static void renderAsmodaySpawnShell(Matrix4f matrix, float age, float fade, float time, float radius) {
      if (!(age > 24.0F) && !(radius <= 0.05F)) {
         float t = Mth.clamp(age / 24.0F, 0.0F, 1.0F);
         float alpha = (1.0F - t) * 0.3F * fade;
         renderGlowSphere(matrix, radius * (0.9F + t * 2.05F), 1.0F, 0.0F, 0.0F, alpha, 12, 28, time * 0.14F);
      }
   }

   private static void renderAsmodayOuterGlow(Matrix4f matrix, float time, float fade, float radius) {
      renderGlowSphere(matrix, radius * 2.35F, 1.0F, 0.0F, 0.0F, 0.052F * fade, 12, 34, time * 0.02F);
      renderGlowSphere(matrix, radius * 1.8F, 0.7F, 0.0F, 0.0F, 0.062F * fade, 12, 34, -time * 0.04F);
      renderGlowSphere(matrix, radius * 1.18F, 1.0F, 0.02F, 0.0F, 0.115F * fade, 14, 40, time * 0.07F);
   }

   private static void renderAsmodayAccretionVolume(Matrix4f matrix, float time, float fade, float pulse, boolean frontPass, float radius) {
      if (!(fade <= 5.0E-4F) && !(radius <= 0.05F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         float pass = frontPass ? 1.0F : 0.72F;
         wrote |= addRibbonLayer(
            buffer,
            matrix,
            radius * 0.8F,
            radius * 1.44F,
            radius * 0.28F,
            radius * 0.24F,
            fade * 0.2F * pulse * pass,
            time * 1.3F,
            0.0F,
            0.0F,
            0.11F,
            frontPass,
            148
         );
         wrote |= addRibbonLayer(
            buffer,
            matrix,
            radius * 0.86F,
            radius * 1.72F,
            radius * 0.2F,
            radius * 0.18F,
            fade * 0.15F * pulse * pass,
            -time * 1.8F,
            2.3F,
            0.0F,
            -0.09F,
            frontPass,
            148
         );
         wrote |= addRibbonLayer(
            buffer,
            matrix,
            radius * 1.05F,
            radius * 2.2F,
            radius * 0.13F,
            radius * 0.12F,
            fade * 0.11F * pulse * pass,
            time * 0.92F,
            4.1F,
            0.0F,
            0.05F,
            frontPass,
            128
         );
         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static boolean addRibbonLayer(
      BufferBuilder buffer,
      Matrix4f matrix,
      float inner,
      float outer,
      float width,
      float height,
      float alpha,
      float flow,
      float phase,
      float colorMix,
      float tilt,
      boolean frontPass,
      int segments
   ) {
      if (!(alpha <= 5.0E-4F) && !(inner <= 0.0F) && !(outer <= 0.0F) && segments > 0) {
         boolean wrote = false;

         for (int i = 0; i < segments; i++) {
            float u0 = (float)i / segments;
            float u1 = (float)(i + 1) / segments;
            float angle0 = u0 * (float) (Math.PI * 2);
            float angle1 = u1 * (float) (Math.PI * 2);

            for (int band = 0; band < 3; band++) {
               float h0 = (band - 1.0F) / 1.5F;
               float h1 = band / 1.5F;
               PhainonAcquirementVfxClient.RibbonVertex v00 = ribbonVertex(inner, h0, u0, angle0, width, height, flow, phase, colorMix, tilt, frontPass);
               PhainonAcquirementVfxClient.RibbonVertex v01 = ribbonVertex(inner, h1, u0, angle0, width, height, flow, phase, colorMix, tilt, frontPass);
               PhainonAcquirementVfxClient.RibbonVertex v10 = ribbonVertex(outer, h0, u1, angle1, width, height, flow, phase, colorMix, tilt, frontPass);
               PhainonAcquirementVfxClient.RibbonVertex v11 = ribbonVertex(outer, h1, u1, angle1, width, height, flow, phase, colorMix, tilt, frontPass);
               addQuad(buffer, matrix, v00, v10, v11, v01, alpha);
               wrote = true;
            }
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static PhainonAcquirementVfxClient.RibbonVertex ribbonVertex(
      float radius, float h, float u, float angle, float width, float height, float flow, float phase, float colorMix, float tilt, boolean frontPass
   ) {
      float c = Mth.cos(angle);
      float s = Mth.sin(angle);
      float compression = Math.max(0.0F, -s) * (frontPass ? 0.48F : 0.12F);
      float turbulence = Mth.sin(u * 82.0F + flow * 4.7F + phase) * 0.5F + Mth.sin(u * 191.0F - flow * 3.0F) * 0.5F;
      float radialNoise = turbulence * width * (0.13F + compression);
      float yNoise = Mth.sin(u * 42.0F + flow * 1.65F + radius) * height * 0.055F * (frontPass ? 1.0F : 0.16F);
      float r = radius + radialNoise;
      float y = h * height * (0.075F + compression * (frontPass ? 0.26F : 0.075F)) + yNoise + tilt * radius * c;
      float x = c * r;
      float z = s * r;
      float horizonMask = eventHorizonMask(x, y, z, frontPass);
      float hot = Mth.clamp(1.0F - (radius - 0.742F) / 0.72F, 0.0F, 1.0F);
      float streak = 0.54F + 0.46F * Mth.sin(u * 390.0F + flow * 7.3F + phase);
      float threads = 0.6F + 0.4F * Mth.sin(u * 108.0F - flow * 3.8F + radius * 4.0F);
      float side = frontPass ? 0.52F + 0.42F * Math.max(0.0F, s) : 0.1F + 0.2F * Math.max(0.0F, -s);
      float edge = Mth.clamp(1.0F - Math.abs(h) * 0.44F, 0.0F, 1.0F);
      float innerPower = 1.0F + hot * hot * hot * (frontPass ? 10.5F : 3.5F);
      float glow = (0.18F + hot * 0.82F) * (0.28F + streak * 0.72F) * threads * side * edge * innerPower;
      return new PhainonAcquirementVfxClient.RibbonVertex(x, y, z, 1.0F, 0.0F, 0.0F, glow * horizonMask);
   }

   private static void renderAsmodayCondensedYDisk(Matrix4f matrix, float time, float fade, float pulse, boolean frontPass, float yaw, float radius) {
      if (!(fade <= 5.0E-4F) && !(pulse <= 5.0E-4F) && !(radius <= 0.05F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         wrote |= addCondensedYDiskTube(
            buffer, matrix, radius * 0.88F, radius * 1.82F, radius * 0.03F, radius * 0.14F, fade * pulse * 0.21F, time * 2.1F, 0.0F, frontPass, yaw
         );
         wrote |= addCondensedYDiskTube(
            buffer, matrix, radius * 0.98F, radius * 2.28F, radius * 0.02F, radius * 0.09F, fade * pulse * 0.14F, -time * 2.9F, 1.7F, frontPass, yaw
         );
         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static boolean addCondensedYDiskTube(
      BufferBuilder buffer,
      Matrix4f matrix,
      float inner,
      float outer,
      float tube,
      float depth,
      float alpha,
      float flow,
      float phase,
      boolean frontPass,
      float yaw
   ) {
      if (!(alpha <= 5.0E-4F) && !(inner <= 0.0F) && !(outer <= 0.0F)) {
         int segments = 160;
         boolean wrote = false;
         float cy = Mth.cos(yaw);
         float sy = Mth.sin(yaw);

         for (int i = 0; i < segments; i++) {
            float u0 = (float)i / segments;
            float u1 = (float)(i + 1) / segments;
            PhainonAcquirementVfxClient.YDiskVertex a = yDiskVertex(inner, tube, depth, -1.0F, u0, flow, phase, alpha, frontPass, cy, sy);
            PhainonAcquirementVfxClient.YDiskVertex b = yDiskVertex(outer, tube, depth, -1.0F, u1, flow, phase, alpha, frontPass, cy, sy);
            PhainonAcquirementVfxClient.YDiskVertex c = yDiskVertex(outer, tube, depth, 1.0F, u1, flow, phase, alpha, frontPass, cy, sy);
            PhainonAcquirementVfxClient.YDiskVertex d = yDiskVertex(inner, tube, depth, 1.0F, u0, flow, phase, alpha, frontPass, cy, sy);
            addQuad(buffer, matrix, a.toRibbon(), b.toRibbon(), c.toRibbon(), d.toRibbon(), 1.0F);
            wrote = true;
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static PhainonAcquirementVfxClient.YDiskVertex yDiskVertex(
      float radius, float tube, float depth, float band, float u, float flow, float phase, float alpha, boolean frontPass, float cy, float sy
   ) {
      float angle = u * (float) (Math.PI * 2);
      float c = Mth.cos(angle);
      float s = Mth.sin(angle);
      float wobble = Mth.sin(u * 96.0F + flow * 2.0F + phase) * tube;
      float localX = c * radius;
      float localY = s * radius * depth + band * tube + wobble;
      float x = localX * cy;
      float z = localX * sy;
      float y = localY;
      float mask = yDiskHorizonMask(localX, localY, frontPass);
      float glow = (0.52F + 0.48F * Mth.sin(u * 310.0F + flow * 6.2F + phase)) * mask * alpha;
      return new PhainonAcquirementVfxClient.YDiskVertex(x, y, z, glow);
   }

   private static float yDiskHorizonMask(float localX, float localY, boolean frontPass) {
      float dist = Mth.sqrt(localX * localX + localY * localY);
      float hole = Mth.clamp((dist - 0.72F) / 0.32F, 0.0F, 1.0F);
      float front = frontPass ? Mth.clamp((localY + 0.1F) / 0.35F, 0.0F, 1.0F) : Mth.clamp((-localY + 0.1F) / 0.35F, 0.0F, 1.0F);
      return hole * front;
   }

   private static float eventHorizonMask(float x, float y, float z, boolean frontPass) {
      float dist = Mth.sqrt(x * x + y * y + z * z);
      float hole = Mth.clamp((dist - 0.72F) / 0.3F, 0.0F, 1.0F);
      float side = frontPass ? Mth.clamp((z + 0.18F) / 0.48F, 0.0F, 1.0F) : Mth.clamp((-z + 0.18F) / 0.48F, 0.0F, 1.0F);
      return hole * side;
   }

   private static void renderAsmodayPhotonHalo(Matrix4f matrix, float time, float fade, float pulse, boolean frontPass, float radius) {
      renderHaloRing(matrix, radius * 0.98F, radius * 0.025F, 1.0F, 0.0F, 0.0F, fade * pulse * (frontPass ? 0.75F : 0.28F), 190, time * 2.4F, 0.0F);
      renderHaloRing(matrix, radius * 1.08F, radius * 0.02F, 1.0F, 0.0F, 0.0F, fade * pulse * (frontPass ? 0.5F : 0.2F), 190, -time * 2.0F, radius * 0.01F);
      renderHaloRing(matrix, radius * 1.2F, radius * 0.018F, 0.75F, 0.0F, 0.0F, fade * pulse * (frontPass ? 0.35F : 0.14F), 170, time * 1.4F, -radius * 0.01F);
   }

   private static void renderHaloRing(
      Matrix4f matrix, float radius, float width, float red, float green, float blue, float alpha, int segments, float flow, float lift
   ) {
      if (!(alpha <= 5.0E-4F) && !(radius <= 0.0F) && !(width <= 0.0F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

         for (int i = 0; i < segments; i++) {
            float u0 = (float)i / segments;
            float u1 = (float)(i + 1) / segments;
            float a0 = u0 * (float) (Math.PI * 2);
            float a1 = u1 * (float) (Math.PI * 2);
            PhainonAcquirementVfxClient.RibbonVertex v0 = haloVertex(radius - width, -width, a0, u0, lift, red, green, blue, alpha);
            PhainonAcquirementVfxClient.RibbonVertex v1 = haloVertex(radius + width, width, a0, u0, lift, red, green, blue, alpha);
            PhainonAcquirementVfxClient.RibbonVertex v2 = haloVertex(radius + width, width, a1, u1, lift, red, green, blue, alpha);
            PhainonAcquirementVfxClient.RibbonVertex v3 = haloVertex(radius - width, -width, a1, u1, lift, red, green, blue, alpha);
            addQuad(buffer, matrix, v0, v1, v2, v3, 1.0F);
         }

         safeDraw(buffer);
      }
   }

   private static PhainonAcquirementVfxClient.RibbonVertex haloVertex(
      float radius, float h, float angle, float u, float lift, float red, float green, float blue, float alpha
   ) {
      float sparkle = 0.6F + 0.4F * Mth.sin(u * 400.0F + shaderTime * 6.0F);
      return new PhainonAcquirementVfxClient.RibbonVertex(Mth.cos(angle) * radius, h + lift, Mth.sin(angle) * radius, red, green, blue, alpha * sparkle);
   }

   private static void renderAsmodayLensedWall(Matrix4f matrix, float time, float fade, boolean front, float radius) {
      if (!(fade <= 5.0E-4F) && !(radius <= 0.05F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 52; i++) {
            float inner = radius * (1.02F + hash(i * 9.11F) * 0.4F);
            float outer = radius * (1.55F + hash(i * 7.33F) * 1.08F);
            float width = radius * (0.012F + hash(i * 3.91F) * 0.024F);
            float alpha = fade * (0.038F + hash(i * 4.27F) * 0.062F);
            float flow = time * (0.35F + hash(i * 2.19F) * 0.55F) + i;
            wrote |= addVerticalBand(buffer, matrix, inner, outer, width, alpha, flow, front);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static boolean addVerticalBand(BufferBuilder buffer, Matrix4f matrix, float inner, float outer, float width, float alpha, float flow, boolean front) {
      if (alpha <= 5.0E-4F) {
         return false;
      }

      int segments = 18;
      float start = hash(flow * 3.1F) * (float) (Math.PI * 2);
      float arc = 0.12F + hash(flow * 4.4F) * 0.38F;
      boolean wrote = false;

      for (int i = 0; i < segments; i++) {
         float t0 = (float)i / segments;
         float t1 = (float)(i + 1) / segments;
         float a0 = start + arc * t0;
         float a1 = start + arc * t1;
         PhainonAcquirementVfxClient.RibbonVertex v0 = verticalBandVertex(inner, -width, a0, t0, alpha, front);
         PhainonAcquirementVfxClient.RibbonVertex v1 = verticalBandVertex(outer, width, a0, t0, alpha, front);
         PhainonAcquirementVfxClient.RibbonVertex v2 = verticalBandVertex(outer, width, a1, t1, alpha, front);
         PhainonAcquirementVfxClient.RibbonVertex v3 = verticalBandVertex(inner, -width, a1, t1, alpha, front);
         addQuad(buffer, matrix, v0, v1, v2, v3, 1.0F);
         wrote = true;
      }

      return wrote;
   }

   private static PhainonAcquirementVfxClient.RibbonVertex verticalBandVertex(float radius, float offset, float angle, float u, float alpha, boolean front) {
      float c = Mth.cos(angle);
      float s = Mth.sin(angle);
      float y = offset + Mth.sin(u * (float) Math.PI) * radius * 0.18F;
      float mask = front ? Mth.clamp((s + 0.2F) / 0.55F, 0.0F, 1.0F) : Mth.clamp((-s + 0.2F) / 0.55F, 0.0F, 1.0F);
      return new PhainonAcquirementVfxClient.RibbonVertex(c * radius, y, s * radius, 1.0F, 0.0F, 0.0F, alpha * mask);
   }

   private static void renderAsmodayInnerFilaments(Matrix4f matrix, float time, float fade, boolean frontPass, float radius) {
      if (!(fade <= 5.0E-4F) && !(radius <= 0.05F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 72; i++) {
            float start = hash(i * 5.17F) * (float) (Math.PI * 2);
            float end = start + (0.08F + hash(i * 3.19F) * 0.25F);
            float curve = hash(i * 6.39F) * 0.55F;
            float width = radius * (0.006F + hash(i * 8.13F) * 0.016F);
            float alpha = fade * (0.06F + hash(i * 9.41F) * 0.095F);
            wrote |= addCurvedFilament(buffer, matrix, start, end, curve + time * 0.25F, width, alpha, frontPass, radius);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static boolean addCurvedFilament(
      BufferBuilder buffer, Matrix4f matrix, float start, float end, float curve, float width, float alpha, boolean frontPass, float radius
   ) {
      if (!(alpha <= 5.0E-4F) && !(width <= 0.0F) && !(radius <= 0.0F)) {
         int segments = 10;
         Vec3 prevA = null;
         Vec3 prevB = null;
         boolean wrote = false;

         for (int i = 0; i <= segments; i++) {
            float t = (float)i / segments;
            float angle = Mth.lerp(t, start, end);
            float r = radius * (0.88F + t * 0.68F);
            float y = Mth.sin(t * (float) Math.PI + curve) * radius * 0.1F;
            float x = Mth.cos(angle) * r;
            float z = Mth.sin(angle) * r;
            float side = frontPass
               ? Mth.clamp((z + radius * 0.1F) / (radius * 0.45F), 0.0F, 1.0F)
               : Mth.clamp((-z + radius * 0.1F) / (radius * 0.45F), 0.0F, 1.0F);
            Vec3 center = new Vec3(x, y, z);
            Vec3 normal = new Vec3(-Mth.sin(angle), 0.0, Mth.cos(angle)).normalize().scale(width);
            Vec3 a = center.add(normal);
            Vec3 b = center.subtract(normal);
            if (prevA != null && prevB != null) {
               addQuad(
                  buffer,
                  matrix,
                  new PhainonAcquirementVfxClient.RibbonVertex((float)prevA.x, (float)prevA.y, (float)prevA.z, 1.0F, 0.0F, 0.0F, alpha * side),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)a.x, (float)a.y, (float)a.z, 1.0F, 0.0F, 0.0F, alpha * side),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)b.x, (float)b.y, (float)b.z, 1.0F, 0.0F, 0.0F, alpha * side),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)prevB.x, (float)prevB.y, (float)prevB.z, 1.0F, 0.0F, 0.0F, alpha * side),
                  1.0F
               );
               wrote = true;
            }

            prevA = a;
            prevB = b;
         }

         return wrote;
      } else {
         return false;
      }
   }

   private static void renderAsmodayEquatorialLightning(Matrix4f matrix, float time, float fade, float radius) {
      if (!(fade <= 5.0E-4F) && !(radius <= 0.05F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 70; i++) {
            float seed = i * 19.7F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.16F + hash(seed + 1.0F) * 0.18F);
            float r0 = radius * (0.82F + hash(seed + 2.0F) * 0.18F);
            float r1 = radius * (1.34F + hash(seed + 3.0F) * 0.56F);
            float y = (hash(seed + 4.0F) - 0.5F) * radius * 0.22F;
            float width = radius * (0.004F + hash(seed + 5.0F) * 0.011F);
            float alpha = fade * (0.17F + hash(seed + 6.0F) * 0.2F);
            wrote |= addBeam(
               buffer,
               matrix,
               Mth.cos(angle) * r0,
               y,
               Mth.sin(angle) * r0,
               Mth.cos(angle + 0.025F) * r1,
               y + (hash(seed + 7.0F) - 0.5F) * radius * 0.18F,
               Mth.sin(angle + 0.025F) * r1,
               width,
               1.0F,
               0.0F,
               0.0F,
               alpha
            );
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderAsmodayDistantWarpedDust(Matrix4f matrix, float time, float fade, float radius) {
      if (!(fade <= 5.0E-4F) && !(radius <= 0.05F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 190; i++) {
            float seed = i * 13.11F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.03F + hash(seed + 1.0F) * 0.07F);
            float r = radius * (1.8F + hash(seed + 2.0F) * 5.8F);
            float y = (hash(seed + 3.0F) - 0.5F) * radius * 1.8F;
            float size = radius * (0.01F + hash(seed + 4.0F) * 0.024F);
            float alpha = fade * (0.035F + hash(seed + 5.0F) * 0.08F);
            float x = Mth.cos(angle) * r;
            float z = Mth.sin(angle) * r;
            if (alpha > 5.0E-4F && size > 5.0E-4F) {
               addBillboardQuad(buffer, matrix, x, y, z, size, 1.0F, 0.0F, 0.0F, alpha);
               wrote = true;
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderAsmodayPolarSpewer(Matrix4f matrix, float time, float fade, float radius) {
      renderPolarJetShell(matrix, 1.0F, 0.0F, 0.0F, 0.4F * fade, 1.0F, time, radius);
      renderPolarJetShell(matrix, 0.55F, 0.0F, 0.0F, 0.25F * fade, -1.0F, -time, radius);
      renderPolarJetCore(matrix, 1.0F, 0.0F, 0.0F, 0.42F * fade, 1.0F, time * 1.15F, radius);
      renderPolarJetCore(matrix, 0.55F, 0.0F, 0.0F, 0.27F * fade, -1.0F, -time * 1.15F, radius);
   }

   private static void renderPolarJetShell(Matrix4f matrix, float red, float green, float blue, float alpha, float sign, float time, float radius) {
      if (!(alpha <= 5.0E-4F) && !(radius <= 0.1F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
         int segments = 48;

         for (int i = 0; i <= segments; i++) {
            float t = (float)i / segments;
            float y = sign * Mth.lerp(t, radius * 0.62F, radius * 6.1F);
            float width = radius * (0.36F * (1.0F - t) + 0.056F);
            float twist = time * 1.08F + t * 12.5F;
            float x = Mth.cos(twist) * width;
            float z = Mth.sin(twist) * width;
            float a = alpha * (1.0F - t) * (0.58F + 0.42F * Mth.sin(t * 28.0F + time * 4.0F));
            buffer.addVertex(matrix, -x, y, -z).setColor(red, green, blue, 0.0F);
            buffer.addVertex(matrix, x, y, z).setColor(red, green, blue, a);
         }

         safeDraw(buffer);
      }
   }

   private static void renderPolarJetCore(Matrix4f matrix, float red, float green, float blue, float alpha, float sign, float time, float radius) {
      if (!(alpha <= 5.0E-4F) && !(radius <= 0.1F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int streams = 7;
         int segments = 24;
         boolean wrote = false;

         for (int stream = 0; stream < streams; stream++) {
            float base = stream * (float) (Math.PI * 2) / streams + time * 0.74F;

            for (int i = 0; i < segments; i++) {
               float t0 = (float)i / segments;
               float t1 = (float)(i + 1) / segments;
               float y0 = sign * Mth.lerp(t0, radius * 0.78F, radius * 6.2F);
               float y1 = sign * Mth.lerp(t1, radius * 0.78F, radius * 6.2F);
               float w0 = radius * (0.09F * (1.0F - t0) + 0.014F);
               float w1 = radius * (0.09F * (1.0F - t1) + 0.014F);
               float a0 = alpha * (1.0F - t0);
               float a1 = alpha * (1.0F - t1);
               float ang0 = base + t0 * 9.2F + Mth.sin(time + stream) * 0.38F;
               float ang1 = base + t1 * 9.2F + Mth.sin(time + stream) * 0.38F;
               float x0 = Mth.cos(ang0) * radius * 0.22F * t0;
               float z0 = Mth.sin(ang0) * radius * 0.22F * t0;
               float x1 = Mth.cos(ang1) * radius * 0.22F * t1;
               float z1 = Mth.sin(ang1) * radius * 0.22F * t1;
               wrote |= addBeam(buffer, matrix, x0, y0, z0, x1, y1, z1, Mth.lerp(t0, w0, w1), red, green, blue, Math.max(a0, a1));
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderAsmodayOuterRedDust(Matrix4f matrix, float time, float fade, float radius) {
      if (!(fade <= 5.0E-4F) && !(radius <= 0.05F) && Float.isFinite(fade) && Float.isFinite(radius)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 150; i++) {
            float seed = i * 8.37F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.16F + hash(seed + 1.0F) * 0.25F);
            float r = radius * (1.12F + hash(seed + 2.0F) * 1.45F);
            float y = (hash(seed + 3.0F) - 0.5F) * radius * 0.65F;
            float size = radius * (0.012F + hash(seed + 4.0F) * 0.03F);
            float alpha = fade * (0.12F + hash(seed + 5.0F) * 0.2F);
            if (Float.isFinite(angle)
               && Float.isFinite(r)
               && Float.isFinite(y)
               && Float.isFinite(size)
               && Float.isFinite(alpha)
               && alpha > 5.0E-4F
               && size > 5.0E-4F) {
               addBillboardQuad(buffer, matrix, Mth.cos(angle) * r, y, Mth.sin(angle) * r, size, 1.0F, 0.0F, 0.0F, alpha);
               wrote = true;
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGroundAsmodayCubes(Matrix4f matrix, float strength, float time, float groundY, float scale) {
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.blendFuncSeparate(770, 771, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 4300; i++) {
            float seed = i * 17.071F;
            int stream = i % 19;
            int step = i / 19;
            float delay = step * 0.018F;
            float local = Mth.frac(time * (0.05F + hash(seed + 1.0F) * 0.046F) + delay + hash(seed + 2.0F));
            float progress = smooth(local);
            float angle = stream * (float) (Math.PI * 2) / 19.0F + hash(seed + 3.0F) * 0.92F + progress * (1.08F + hash(seed + 4.0F) * 2.45F);
            float startRadius = scale * (22.0F + hash(seed + 5.0F) * 20.0F);
            float endRadius = scale * (0.65F + hash(seed + 6.0F) * 1.65F);
            float radius = Mth.lerp(progress, startRadius, endRadius);
            float sidePack = (hash(seed + 7.0F) - 0.5F) * scale * 3.0F;
            float x = Mth.cos(angle) * radius + Mth.cos(angle + (float) (Math.PI / 2)) * sidePack;
            float z = Mth.sin(angle) * radius + Mth.sin(angle + (float) (Math.PI / 2)) * sidePack;
            float groundBurst = smooth(range(local, 0.0F, 0.38F));
            float y = Mth.lerp(groundBurst, groundY + 0.05F + hash(seed + 8.0F) * 1.1F, (hash(seed + 9.0F) - 0.5F) * scale * 0.95F);
            float alpha = strength * (1.0F - smooth(range(local, 0.88F, 1.0F))) * (0.48F + hash(seed + 10.0F) * 0.62F);
            float size = scale * (0.042F + hash(seed + 11.0F) * 0.092F);
            PhainonAcquirementVfxClient.Basis basis = cubeBasisFromSeed(time * 1.05F, seed);
            renderSolidCube(buffer, matrix, new Vec3(x, y, z), basis, size, alpha);
            renderWireCube(buffer, matrix, new Vec3(x, y, z), basis, size * 1.12, alpha * 1.45F);
            wrote = true;
         }

         if (wrote) {
            safeDraw(buffer);
         }

         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         RenderSystem.enableDepthTest();
      }
   }

   private static void renderGroundCubeBeams(Matrix4f matrix, float strength, float time, float groundY, float scale) {
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 680; i++) {
            float seed = i * 11.371F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float radius = scale * (16.0F + hash(seed + 1.0F) * 25.0F);
            float x0 = Mth.cos(angle) * radius;
            float z0 = Mth.sin(angle) * radius;
            float x1 = Mth.cos(angle + 0.44F * hash(seed + 2.0F)) * scale * (0.8F + hash(seed + 3.0F) * 3.8F);
            float z1 = Mth.sin(angle + 0.44F * hash(seed + 4.0F)) * scale * (0.8F + hash(seed + 5.0F) * 3.8F);
            float a = strength * (0.05F + hash(seed + 6.0F) * 0.095F);
            wrote |= addBeam(buffer, matrix, x0, groundY + 0.05F, z0, x1, (hash(seed + 7.0F) - 0.5F) * scale * 0.55F, z1, scale * 0.015F, 1.0F, 0.0F, 0.0F, a);
         }

         if (wrote) {
            safeDraw(buffer);
         }

         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         RenderSystem.enableDepthTest();
      }
   }

   private static void renderGroundCubePillars(Matrix4f matrix, float strength, float time, float groundY, float scale) {
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.blendFuncSeparate(770, 771, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 1300; i++) {
            float seed = i * 23.717F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float radius = scale * (2.5F + hash(seed + 1.0F) * 33.0F);
            float local = Mth.frac(time * (0.032F + hash(seed + 2.0F) * 0.036F) + hash(seed + 3.0F));
            float rise = smooth(range(local, 0.02F, 0.64F));
            float fall = smooth(range(local, 0.74F, 1.0F));
            float x = Mth.cos(angle) * radius + Mth.sin(time + seed) * scale * 0.2F;
            float z = Mth.sin(angle) * radius + Mth.cos(time + seed) * scale * 0.2F;
            float y = groundY + 0.1F + rise * scale * (0.8F + hash(seed + 4.0F) * 3.6F) - fall * scale * 1.4F;
            float alpha = strength * (1.0F - fall) * (0.36F + hash(seed + 5.0F) * 0.72F);
            float size = scale * (0.05F + hash(seed + 6.0F) * 0.14F);
            PhainonAcquirementVfxClient.Basis basis = cubeBasisFromSeed(time * 1.55F, seed);
            renderSolidCube(buffer, matrix, new Vec3(x, y, z), basis, size, alpha * 0.82F);
            renderWireCube(buffer, matrix, new Vec3(x, y, z), basis, size * 1.15, alpha * 1.65F);
            wrote = true;
         }

         if (wrote) {
            safeDraw(buffer);
         }

         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         RenderSystem.enableDepthTest();
      }
   }

   private static void renderGroundRedFractures(Matrix4f matrix, float strength, float time, float groundY, float scale) {
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 340; i++) {
            float seed = i * 31.137F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float radius0 = scale * (1.5F + hash(seed + 1.0F) * 35.0F);
            float length = scale * (1.0F + hash(seed + 2.0F) * 5.5F);
            float bend = (hash(seed + 3.0F) - 0.5F) * 0.65F;
            float x0 = Mth.cos(angle) * radius0;
            float z0 = Mth.sin(angle) * radius0;
            float x1 = Mth.cos(angle + bend) * (radius0 - length);
            float z1 = Mth.sin(angle + bend) * (radius0 - length);
            float alpha = strength * (0.045F + hash(seed + 4.0F) * 0.08F) * (0.7F + 0.3F * Mth.sin(time * 5.0F + seed));
            float width = scale * (0.01F + hash(seed + 5.0F) * 0.018F);
            wrote |= addBeam(buffer, matrix, x0, groundY + 0.055F, z0, x1, groundY + 0.06F, z1, width, 1.0F, 0.0F, 0.0F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }

         RenderSystem.enableDepthTest();
      }
   }

   private static void renderRedBlackShock(Matrix4f matrix, float fade, float explosion, float time, float scale) {
      if (!(explosion <= 5.0E-4F)) {
         float strength = fade * explosion;
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         renderRing(matrix, scale * 0.2F, scale * (2.0F + explosion * 24.0F), 240, time * 1.1F, 1.0F, 0.0F, 0.0F, strength * 0.3F, scale * 0.12F);
         renderRing(matrix, scale * 0.5F, scale * (4.0F + explosion * 34.0F), 240, -time * 0.8F, 0.55F, 0.0F, 0.0F, strength * 0.22F, scale * 0.18F);
         renderGlowSphere(matrix, scale * (1.0F + explosion * 17.0F), 1.0F, 0.0F, 0.0F, strength * 0.05F, 14, 44, time * 0.1F);
      }
   }

   private static void renderGoldenExplosionArrival(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.15F + divine * 0.72F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         renderGlowSphere(matrix, scale * (2.0F + explosion * 30.0F + divine * 5.0F), 1.0F, 0.8F, 0.1F, strength * 0.032F, 18, 54, time * 0.04F);
         renderRing(
            matrix,
            scale * (1.0F + explosion * 4.0F),
            scale * (4.0F + explosion * 38.0F + divine * 10.0F),
            260,
            time * 0.65F,
            1.0F,
            0.78F,
            0.08F,
            strength * 0.16F,
            scale * 0.2F
         );
         renderRing(
            matrix,
            scale * (3.0F + explosion * 7.0F),
            scale * (7.0F + explosion * 48.0F + divine * 12.0F),
            260,
            -time * 0.45F,
            1.0F,
            0.5F,
            0.02F,
            strength * 0.1F,
            scale * 0.28F
         );
      }
   }

   private static void renderGoldenSoftRadiance(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.55F + explosionGold * 1.1F + finalGold * 1.35F;
      if (!(strength <= 5.0E-4F)) {
         renderGlowSphere(matrix, scale * (2.0F + explosionGold * 4.0F + finalGold * 2.6F), 1.0F, 0.8F, 0.1F, strength * 0.06F, 20, 58, time * 0.03F);
         renderGlowSphere(matrix, scale * (3.6F + explosionGold * 9.0F + finalGold * 5.0F), 1.0F, 0.62F, 0.02F, strength * 0.03F, 18, 54, -time * 0.02F);
         renderGlowSphere(matrix, scale * (6.0F + explosionGold * 15.0F + finalGold * 8.0F), 1.0F, 0.45F, 0.0F, strength * 0.015F, 18, 54, time * 0.018F);
      }
   }

   private static void renderGoldenAuroraAura(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.75F + explosionGold * 1.25F + finalGold * 1.65F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int ribbons = 96;
         int segments = 72;
         float height = scale * (8.5F + finalGold * 10.5F + explosionGold * 6.5F);
         float baseRadius = scale * (1.05F + finalGold * 0.65F + explosionGold * 1.1F);

         for (int r = 0; r < ribbons; r++) {
            float seed = r * 31.31F;
            float yaw = r * (float) (Math.PI * 2) / ribbons + hash(seed) * 0.42F + time * (0.06F + hash(seed + 1.0F) * 0.055F);
            float ribbonHeight = height * (0.72F + hash(seed + 2.0F) * 0.7F);
            float width = scale * (0.12F + hash(seed + 3.0F) * 0.24F) * (1.0F + finalGold * 1.1F + explosionGold * 0.85F);
            float radius = baseRadius + hash(seed + 4.0F) * scale * (1.0F + explosionGold * 2.4F);
            float alpha = strength * (0.12F + hash(seed + 5.0F) * 0.16F);

            for (int i = 0; i < segments; i++) {
               float t0 = (float)i / segments;
               float t1 = (float)(i + 1) / segments;
               Vec3 p0 = goldenRibbonPoint(yaw, radius, ribbonHeight, time, seed, t0, scale, explosionGold);
               Vec3 p1 = goldenRibbonPoint(yaw, radius, ribbonHeight, time, seed, t1, scale, explosionGold);
               Vec3 dir = p1.subtract(p0);
               if (!(dir.lengthSqr() < 1.0E-4)) {
                  Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
                  if (side.lengthSqr() < 1.0E-4) {
                     side = new Vec3(1.0, 0.0, 0.0);
                  }

                  side = side.normalize().scale(width * (0.9F - t0 * 0.22F));
                  float soft0 = Mth.sin(t0 * (float) Math.PI);
                  float soft1 = Mth.sin(t1 * (float) Math.PI);
                  float a0 = alpha * soft0 * (0.72F + finalGold * 0.62F + explosionGold * 0.55F);
                  float a1 = alpha * soft1 * (0.72F + finalGold * 0.62F + explosionGold * 0.55F);
                  addQuad(
                     buffer,
                     matrix,
                     new PhainonAcquirementVfxClient.RibbonVertex((float)(p0.x - side.x), (float)p0.y, (float)(p0.z - side.z), 1.0F, 0.7F, 0.04F, a0 * 0.18F),
                     new PhainonAcquirementVfxClient.RibbonVertex((float)(p1.x - side.x), (float)p1.y, (float)(p1.z - side.z), 1.0F, 0.96F, 0.3F, a1),
                     new PhainonAcquirementVfxClient.RibbonVertex((float)(p1.x + side.x), (float)p1.y, (float)(p1.z + side.z), 1.0F, 0.8F, 0.08F, a1),
                     new PhainonAcquirementVfxClient.RibbonVertex((float)(p0.x + side.x), (float)p0.y, (float)(p0.z + side.z), 1.0F, 0.48F, 0.0F, a0 * 0.18F),
                     1.0F
                  );
                  wrote = true;
               }
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static Vec3 goldenRibbonPoint(float yaw, float radius, float height, float time, float seed, float t, float scale, float explosionGold) {
      float curve = Mth.sin(t * (float) Math.PI);
      float twist = yaw + curve * (0.55F + hash(seed + 9.0F) * 1.18F) + time * (0.06F + hash(seed + 10.0F) * 0.055F);
      float r = radius + curve * scale * (0.85F + hash(seed + 11.0F) * 1.6F + explosionGold * 4.0F) + Mth.sin(t * 12.0F + time * 1.0F + seed) * scale * 0.12F;
      float y = -scale * 0.75F + t * height + Mth.sin(t * 18.0F + seed + time * 0.9F) * scale * 0.12F;
      return new Vec3(Mth.cos(twist) * r, y, Mth.sin(twist) * r);
   }

   private static void renderGoldenAuroraHaloStorm(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.7F + explosionGold * 1.2F + finalGold * 1.6F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);

         for (int i = 0; i < 13; i++) {
            float h = scale * (-0.8F + i * 0.58F);
            float spin = time * (0.28F + i * 0.035F) * (i % 2 == 0 ? 1.0F : -1.0F);
            float inner = scale * (0.7F + i * 0.08F);
            float outer = scale * (2.4F + i * 0.42F + finalGold * 2.3F + explosionGold * 5.5F);
            float alpha = strength * (0.04F + i * 0.007F);
            renderOffsetRing(matrix, inner, outer, h, 220, spin, 1.0F, 0.78F, 0.08F, alpha, scale * 0.055F);
         }
      }
   }

   private static void renderGoldenAscensionPillars(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.7F + explosionGold * 1.2F + finalGold * 1.35F;
      if (!(strength <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 230; i++) {
            float seed = i * 13.711F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.045F + hash(seed + 1.0F) * 0.04F);
            float radius = scale * (0.9F + hash(seed + 2.0F) * (4.3F + explosionGold * 8.0F));
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            float local = Mth.frac(time * (0.032F + hash(seed + 3.0F) * 0.024F) + hash(seed + 4.0F));
            float y0 = -scale * 1.0F + local * scale * (8.2F + finalGold * 3.0F);
            float y1 = y0 + scale * (2.0F + hash(seed + 5.0F) * 4.8F + explosionGold * 3.5F);
            float width = scale * (0.018F + hash(seed + 6.0F) * 0.05F);
            float alpha = strength * (0.048F + hash(seed + 7.0F) * 0.09F) * Mth.sin(local * (float) Math.PI);
            wrote |= addBeam(
               buffer, matrix, x, y0, z, x + Mth.sin(seed) * scale * 0.18F, y1, z + Mth.cos(seed) * scale * 0.18F, width, 1.0F, 0.82F, 0.1F, alpha
            );
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenFeatherStorm(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.45F + explosionGold * 1.0F + finalGold * 1.1F;
      if (!(strength <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 340; i++) {
            float seed = i * 7.917F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.1F + hash(seed + 1.0F) * 0.08F);
            float radius = scale * (1.4F + hash(seed + 2.0F) * (6.5F + explosionGold * 8.0F));
            float y = scale * (-0.6F + hash(seed + 3.0F) * 7.5F);
            float len = scale * (0.45F + hash(seed + 4.0F) * 1.4F);
            float width = scale * (0.05F + hash(seed + 5.0F) * 0.12F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            float tx = Mth.cos(angle + 0.45F) * len;
            float tz = Mth.sin(angle + 0.45F) * len;
            float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
            float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
            float alpha = strength * (0.05F + hash(seed + 6.0F) * 0.075F);
            buffer.addVertex(matrix, x - sx, y, z - sz).setColor(1.0F, 0.54F, 0.0F, 0.0F);
            buffer.addVertex(matrix, x + tx, y + len * 0.35F, z + tz).setColor(1.0F, 0.94F, 0.26F, alpha);
            buffer.addVertex(matrix, x + sx, y, z + sz).setColor(1.0F, 0.68F, 0.04F, 0.0F);
            wrote = true;
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenSolarBursts(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.4F + explosionGold * 1.15F + finalGold * 1.1F;
      if (!(strength <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 260; i++) {
            float seed = i * 5.37F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float pitch = -0.35F + hash(seed + 1.0F) * 1.35F;
            float len = scale * (4.0F + hash(seed + 2.0F) * 10.0F) * (1.0F + finalGold * 1.3F + explosionGold * 2.2F);
            float start = scale * (0.65F + hash(seed + 3.0F) * 0.65F);
            float width = scale * (0.018F + hash(seed + 4.0F) * 0.05F);
            float wave = Mth.sin(time * (0.7F + hash(seed + 5.0F)) + seed) * scale * 0.2F;
            float x0 = Mth.cos(angle) * start;
            float z0 = Mth.sin(angle) * start;
            float y0 = -scale * 0.2F + hash(seed + 6.0F) * scale * 2.4F;
            float x1 = Mth.cos(angle) * (start + len);
            float z1 = Mth.sin(angle) * (start + len);
            float y1 = y0 + Mth.sin(pitch) * len + wave;
            float alpha = strength * (0.075F + hash(seed + 7.0F) * 0.095F);
            wrote |= addBeam(buffer, matrix, x0, y0, z0, x1, y1, z1, width, 1.0F, 0.75F, 0.05F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenCrownRings(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.35F + explosionGold * 0.9F + finalGold * 0.9F;
      if (!(strength <= 5.0E-4F)) {
         renderRing(matrix, scale * 1.0F, scale * (3.8F + explosionGold * 4.0F), 220, time * 1.2F, 1.0F, 0.82F, 0.12F, strength * 0.18F, scale * 0.08F);
         renderRing(matrix, scale * 1.3F, scale * (5.2F + explosionGold * 7.5F), 220, -time * 0.9F, 1.0F, 0.55F, 0.02F, strength * 0.12F, scale * 0.12F);
         renderGlowSphere(matrix, scale * (2.0F + finalGold * 3.0F + explosionGold * 7.0F), 1.0F, 0.74F, 0.08F, strength * 0.035F, 18, 50, time * 0.04F);
      }
   }

   private static void renderGoldenCoronaBreath(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.45F + explosionGold * 1.2F + finalGold * 0.85F;
      if (!(strength <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 110; i++) {
            float seed = i * 41.17F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.035F + hash(seed + 1.0F) * 0.035F);
            float local = Mth.frac(time * (0.03F + hash(seed + 2.0F) * 0.035F) + hash(seed + 3.0F));
            float spread = smooth(local);
            float r0 = scale * (0.85F + hash(seed + 4.0F) * 0.6F);
            float r1 = scale * (4.0F + spread * (12.0F + explosionGold * 20.0F) + hash(seed + 5.0F) * 5.0F);
            float y0 = scale * (-0.25F + hash(seed + 6.0F) * 1.7F);
            float y1 = y0 + scale * (hash(seed + 7.0F) - 0.2F) * (2.0F + explosionGold * 3.0F);
            float width = scale * (0.05F + hash(seed + 8.0F) * 0.12F) * (1.0F - spread * 0.45F);
            float alpha = strength * Mth.sin(local * (float) Math.PI) * (0.04F + hash(seed + 9.0F) * 0.055F);
            wrote |= addBeam(
               buffer,
               matrix,
               Mth.cos(angle) * r0,
               y0,
               Mth.sin(angle) * r0,
               Mth.cos(angle + 0.25F * Mth.sin(seed)) * r1,
               y1,
               Mth.sin(angle + 0.25F * Mth.sin(seed)) * r1,
               width,
               1.0F,
               0.82F,
               0.1F,
               alpha
            );
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenFallingStarlight(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.8F + explosionGold * 1.35F + finalGold * 1.75F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 520; i++) {
            float seed = i * 17.231F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.018F + hash(seed + 1.0F) * 0.026F);
            float radius = scale * (0.45F + hash(seed + 2.0F) * (8.8F + explosionGold * 11.0F + finalGold * 5.5F));
            float fall = Mth.frac(hash(seed + 3.0F) + time * (0.055F + hash(seed + 4.0F) * 0.08F));
            float y0 = scale * (10.5F + finalGold * 8.5F + explosionGold * 7.0F) - fall * scale * (13.8F + finalGold * 10.0F + explosionGold * 10.0F);
            float drift = Mth.sin(time * 0.75F + seed) * scale * (0.18F + hash(seed + 5.0F) * 0.3F);
            float x = Mth.cos(angle) * radius + drift;
            float z = Mth.sin(angle) * radius + Mth.cos(time * 0.62F + seed) * scale * 0.22F;
            float length = scale * (0.55F + hash(seed + 6.0F) * 2.35F) * (1.0F + explosionGold * 1.75F);
            float width = scale * (0.01F + hash(seed + 7.0F) * 0.035F);
            float twinkle = 0.45F + 0.55F * Mth.sin(time * (4.0F + hash(seed + 8.0F) * 4.0F) + seed);
            float alpha = strength * twinkle * Mth.sin(fall * (float) Math.PI) * (0.035F + hash(seed + 9.0F) * 0.075F);
            wrote |= addBeam(buffer, matrix, x, y0, z, x + drift * 0.12F, y0 - length, z + Mth.sin(seed) * scale * 0.16F, width, 1.0F, 0.86F, 0.16F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenSeamlessRadiationVeil(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.65F + explosionGold * 1.05F + finalGold * 1.55F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int veils = 44;
         int segments = 96;

         for (int v = 0; v < veils; v++) {
            float seed = v * 23.87F;
            float yaw = v * (float) (Math.PI * 2) / veils + time * (0.025F + hash(seed) * 0.04F);
            float height = scale * (5.8F + hash(seed + 1.0F) * 8.0F + finalGold * 8.0F + explosionGold * 5.5F);
            float baseRadius = scale * (0.72F + hash(seed + 2.0F) * 1.2F);
            float width = scale * (0.48F + hash(seed + 3.0F) * 0.8F) * (1.0F + finalGold * 0.6F);
            float alpha = strength * (0.026F + hash(seed + 4.0F) * 0.045F);

            for (int i = 0; i < segments; i++) {
               float t0 = (float)i / segments;
               float t1 = (float)(i + 1) / segments;
               float c0 = Mth.sin(t0 * (float) Math.PI);
               float c1 = Mth.sin(t1 * (float) Math.PI);
               float twist0 = yaw + c0 * (0.55F + hash(seed + 5.0F)) + Mth.sin(t0 * 9.0F + time * 0.7F + seed) * 0.11F;
               float twist1 = yaw + c1 * (0.55F + hash(seed + 5.0F)) + Mth.sin(t1 * 9.0F + time * 0.7F + seed) * 0.11F;
               float r0 = baseRadius + c0 * scale * (1.4F + finalGold * 2.8F + explosionGold * 4.2F);
               float r1 = baseRadius + c1 * scale * (1.4F + finalGold * 2.8F + explosionGold * 4.2F);
               float y0 = -scale * 0.95F + t0 * height;
               float y1 = -scale * 0.95F + t1 * height;
               float side0 = width * (0.25F + c0 * 0.75F);
               float side1 = width * (0.25F + c1 * 0.75F);
               float a0 = alpha * c0 * (0.75F + 0.25F * Mth.sin(time * 2.0F + seed));
               float a1 = alpha * c1 * (0.75F + 0.25F * Mth.sin(time * 2.0F + seed));
               Vec3 p0 = new Vec3(Mth.cos(twist0) * r0, y0, Mth.sin(twist0) * r0);
               Vec3 p1 = new Vec3(Mth.cos(twist1) * r1, y1, Mth.sin(twist1) * r1);
               Vec3 side = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
               addQuad(
                  buffer,
                  matrix,
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p0.x - side.x * side0), (float)p0.y, (float)(p0.z - side.z * side0), 1.0F, 0.56F, 0.0F, 0.0F
                  ),
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p1.x - side.x * side1), (float)p1.y, (float)(p1.z - side.z * side1), 1.0F, 0.98F, 0.34F, a1
                  ),
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p1.x + side.x * side1), (float)p1.y, (float)(p1.z + side.z * side1), 1.0F, 0.78F, 0.04F, a1
                  ),
                  new PhainonAcquirementVfxClient.RibbonVertex(
                     (float)(p0.x + side.x * side0), (float)p0.y, (float)(p0.z + side.z * side0), 1.0F, 0.42F, 0.0F, a0
                  ),
                  1.0F
               );
               wrote = true;
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenDivineDustOrbit(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.55F + explosionGold * 0.85F + finalGold * 1.35F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 430; i++) {
            float seed = i * 11.903F;
            float band = hash(seed + 1.0F);
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.1F + band * 0.18F) * (i % 2 == 0 ? 1.0F : -1.0F);
            float radius = scale * (0.75F + band * (5.8F + finalGold * 3.5F + explosionGold * 7.0F));
            float y = scale * (-0.8F + hash(seed + 2.0F) * (5.8F + finalGold * 5.0F));
            float wave = Mth.sin(angle * 3.0F + time + seed) * scale * (0.1F + hash(seed + 3.0F) * 0.35F);
            float size = scale * (0.02F + hash(seed + 4.0F) * 0.07F) * (1.0F + explosionGold * 0.9F);
            float alpha = strength * (0.035F + hash(seed + 5.0F) * 0.07F) * (0.6F + 0.4F * Mth.sin(time * 3.8F + seed));
            addBillboardQuad(buffer, matrix, Mth.cos(angle) * radius, y + wave, Mth.sin(angle) * radius, size, 1.0F, 0.88F, 0.18F, alpha);
            wrote = true;
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenBlackHoleRain(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.5F + divine * 0.95F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 360; i++) {
            float seed = i * 29.41F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.02F + hash(seed + 1.0F) * 0.045F);
            float radius = scale * (0.5F + hash(seed + 2.0F) * (7.0F + explosion * 18.0F + divine * 9.0F));
            float local = Mth.frac(hash(seed + 3.0F) + time * (0.055F + hash(seed + 4.0F) * 0.06F));
            float y0 = scale * (10.0F + divine * 7.0F) - local * scale * (15.0F + explosion * 14.0F + divine * 10.0F);
            float length = scale * (0.7F + hash(seed + 5.0F) * 3.6F) * (1.0F + explosion * 1.7F);
            float width = scale * (0.013F + hash(seed + 6.0F) * 0.042F);
            float alpha = strength * Mth.sin(local * (float) Math.PI) * (0.035F + hash(seed + 7.0F) * 0.085F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            wrote |= addBeam(buffer, matrix, x, y0, z, x * 0.72F, y0 - length, z * 0.72F, width, 1.0F, 0.8F, 0.08F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenBlackHoleNovaShards(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.7F + divine * 0.9F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 300; i++) {
            float seed = i * 37.113F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float dist = scale * (1.1F + explosion * (9.0F + hash(seed + 1.0F) * 32.0F) + divine * hash(seed + 2.0F) * 11.0F);
            float y = scale * ((hash(seed + 3.0F) - 0.35F) * (4.0F + explosion * 12.0F));
            float len = scale * (0.45F + hash(seed + 4.0F) * 2.6F) * (1.0F + explosion * 1.8F);
            float width = scale * (0.045F + hash(seed + 5.0F) * 0.16F);
            float spin = time * (0.9F + hash(seed + 6.0F) * 1.6F) + seed;
            float x = Mth.cos(angle) * dist;
            float z = Mth.sin(angle) * dist;
            float dx = Mth.cos(angle + spin * 0.08F) * len;
            float dz = Mth.sin(angle + spin * 0.08F) * len;
            float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
            float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
            float alpha = strength * (0.045F + hash(seed + 7.0F) * 0.09F);
            buffer.addVertex(matrix, x - sx, y, z - sz).setColor(1.0F, 0.48F, 0.0F, 0.0F);
            buffer.addVertex(matrix, x + dx, y + len * (0.2F + hash(seed + 8.0F) * 0.55F), z + dz).setColor(1.0F, 0.94F, 0.24F, alpha);
            buffer.addVertex(matrix, x + sx, y, z + sz).setColor(1.0F, 0.72F, 0.06F, 0.0F);
            wrote = true;
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenBlackHoleCrownStorm(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.85F + divine * 1.15F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);

         for (int i = 0; i < 18; i++) {
            float y = scale * (-2.0F + i * 0.32F + Mth.sin(time * 0.6F + i) * 0.18F);
            float spin = time * (0.72F + i * 0.035F) * (i % 2 == 0 ? 1.0F : -1.0F);
            float inner = scale * (0.65F + i * 0.08F + explosion * 2.0F);
            float outer = scale * (3.8F + i * 0.48F + explosion * 34.0F + divine * 8.0F);
            float alpha = strength * (0.03F + i * 0.003F);
            renderOffsetRing(matrix, inner, outer, y, 260, spin, 1.0F, 0.72F, 0.03F, alpha, scale * (0.08F + i * 0.004F));
         }

         renderGlowSphere(matrix, scale * (4.0F + explosion * 42.0F + divine * 11.0F), 1.0F, 0.68F, 0.04F, strength * 0.026F, 20, 64, time * 0.03F);
      }
   }

   private static void renderGoldenBlackHoleJudgementLances(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.95F + divine * 0.85F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 420; i++) {
            float seed = i * 43.773F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float local = Mth.frac(time * (0.06F + hash(seed + 1.0F) * 0.08F) + hash(seed + 2.0F));
            float radius = scale * (2.0F + hash(seed + 3.0F) * (15.0F + explosion * 46.0F + divine * 16.0F));
            float x0 = Mth.cos(angle) * radius;
            float z0 = Mth.sin(angle) * radius;
            float pull = 1.0F - smooth(local);
            float x1 = x0 * pull * 0.22F;
            float z1 = z0 * pull * 0.22F;
            float y0 = scale * (13.0F + hash(seed + 4.0F) * 16.0F + divine * 12.0F);
            float y1 = scale * ((hash(seed + 5.0F) - 0.5F) * (4.0F + explosion * 16.0F));
            float width = scale * (0.014F + hash(seed + 6.0F) * 0.05F) * (1.0F + explosion * 1.1F);
            float alpha = strength * Mth.sin(local * (float) Math.PI) * (0.026F + hash(seed + 7.0F) * 0.078F);
            wrote |= addBeam(buffer, matrix, x0, y0, z0, x1, y1, z1, width, 1.0F, 0.88F, 0.18F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenBlackHoleSolarVeins(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.55F + divine * 1.05F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 280; i++) {
            float seed = i * 52.19F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.05F + hash(seed + 1.0F) * 0.08F);
            float start = scale * (0.65F + hash(seed + 2.0F) * 1.2F);
            float end = scale * (5.0F + hash(seed + 3.0F) * (22.0F + explosion * 55.0F + divine * 16.0F));
            float bend = (hash(seed + 4.0F) - 0.5F) * 0.75F;
            float y0 = scale * ((hash(seed + 5.0F) - 0.5F) * 2.6F);
            float y1 = y0 + scale * ((hash(seed + 6.0F) - 0.45F) * (5.0F + explosion * 13.0F));
            float width = scale * (0.018F + hash(seed + 7.0F) * 0.06F);
            float pulse = 0.55F + 0.45F * Mth.sin(time * (2.0F + hash(seed + 8.0F) * 4.0F) + seed);
            float alpha = strength * pulse * (0.035F + hash(seed + 9.0F) * 0.085F);
            wrote |= addBeam(
               buffer,
               matrix,
               Mth.cos(angle) * start,
               y0,
               Mth.sin(angle) * start,
               Mth.cos(angle + bend) * end,
               y1,
               Mth.sin(angle + bend) * end,
               width,
               1.0F,
               0.66F,
               0.02F,
               alpha
            );
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenHeavenfallCurtain(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.75F + explosionGold * 1.2F + finalGold * 1.95F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 780; i++) {
            float seed = i * 19.447F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.012F + hash(seed + 1.0F) * 0.03F);
            float radius = scale * (0.25F + hash(seed + 2.0F) * (10.5F + finalGold * 7.0F + explosionGold * 10.0F));
            float fall = Mth.frac(hash(seed + 3.0F) + time * (0.08F + hash(seed + 4.0F) * 0.1F));
            float y0 = scale * (15.0F + finalGold * 12.0F + explosionGold * 8.0F) - fall * scale * (18.0F + finalGold * 13.0F + explosionGold * 12.0F);
            float len = scale * (0.7F + hash(seed + 5.0F) * 3.0F) * (1.0F + finalGold * 0.8F + explosionGold * 1.0F);
            float width = scale * (0.009F + hash(seed + 6.0F) * 0.032F);
            float drift = Mth.sin(seed + time * 0.65F) * scale * (0.12F + hash(seed + 7.0F) * 0.28F);
            float alpha = strength * Mth.sin(fall * (float) Math.PI) * (0.026F + hash(seed + 8.0F) * 0.065F);
            float x = Mth.cos(angle) * radius + drift;
            float z = Mth.sin(angle) * radius + Mth.cos(seed + time * 0.55F) * scale * 0.2F;
            wrote |= addBeam(buffer, matrix, x, y0, z, x + drift * 0.18F, y0 - len, z + Mth.sin(seed) * scale * 0.12F, width, 1.0F, 0.9F, 0.22F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenSolarPetalMandala(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.55F + explosionGold * 1.05F + finalGold * 1.45F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int ring = 0; ring < 5; ring++) {
            int petals = 26 + ring * 10;
            float ringRadius = scale * (1.25F + ring * 0.58F + finalGold * 0.85F + explosionGold * 1.4F);
            float spin = time * (0.35F + ring * 0.07F) * (ring % 2 == 0 ? 1.0F : -1.0F);

            for (int i = 0; i < petals; i++) {
               float seed = ring * 1000.0F + i * 27.19F;
               float angle = i * (float) (Math.PI * 2) / petals + spin + hash(seed) * 0.09F;
               float len = scale * (0.7F + ring * 0.22F + hash(seed + 1.0F) * 0.85F) * (1.0F + finalGold * 0.65F + explosionGold * 1.25F);
               float width = scale * (0.12F + hash(seed + 2.0F) * 0.28F);
               float y = scale * (0.35F + ring * 0.34F + Mth.sin(time + seed) * 0.08F);
               float x = Mth.cos(angle) * ringRadius;
               float z = Mth.sin(angle) * ringRadius;
               float tx = Mth.cos(angle) * len;
               float tz = Mth.sin(angle) * len;
               float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
               float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
               float alpha = strength * (0.04F + hash(seed + 3.0F) * 0.07F);
               buffer.addVertex(matrix, x - sx, y, z - sz).setColor(1.0F, 0.42F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x + tx, y + len * 0.22F, z + tz).setColor(1.0F, 0.96F, 0.3F, alpha);
               buffer.addVertex(matrix, x + sx, y, z + sz).setColor(1.0F, 0.7F, 0.04F, 0.0F);
               wrote = true;
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenAbsoluteHaloScript(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.35F + explosionGold * 0.7F + finalGold * 1.25F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 340; i++) {
            float seed = i * 61.733F;
            float band = i % 4;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.18F + band * 0.035F) * (i % 2 == 0 ? 1.0F : -1.0F);
            float radius = scale * (2.2F + band * 1.05F + hash(seed + 1.0F) * (2.6F + finalGold * 3.4F + explosionGold * 4.4F));
            float y = scale * (1.0F + band * 0.85F + Mth.sin(time * 0.8F + seed) * 0.18F);
            float size = scale * (0.03F + hash(seed + 2.0F) * 0.09F);
            float alpha = strength * (0.032F + hash(seed + 3.0F) * 0.065F) * (0.65F + 0.35F * Mth.sin(time * 3.0F + seed));
            addBillboardQuad(buffer, matrix, Mth.cos(angle) * radius, y, Mth.sin(angle) * radius, size, 1.0F, 0.88F, 0.12F, alpha);
            wrote = true;
            if (i % 3 == 0) {
               float angle2 = angle + 0.06F + hash(seed + 4.0F) * 0.12F;
               wrote |= addBeam(
                  buffer,
                  matrix,
                  Mth.cos(angle) * radius,
                  y,
                  Mth.sin(angle) * radius,
                  Mth.cos(angle2) * (radius + size * 5.0F),
                  y + size * 1.6F,
                  Mth.sin(angle2) * (radius + size * 5.0F),
                  size * 0.22F,
                  1.0F,
                  0.82F,
                  0.08F,
                  alpha * 0.7F
               );
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenStarCorePulses(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.55F + explosionGold * 1.25F + finalGold * 1.35F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);

         for (int i = 0; i < 7; i++) {
            float pulse = Mth.frac(time * (0.06F + i * 0.012F) + i * 0.137F);
            float radius = scale * (0.65F + pulse * (5.0F + finalGold * 8.0F + explosionGold * 9.0F));
            float alpha = strength * (1.0F - pulse) * (0.055F + i * 0.01F);
            renderGlowSphere(matrix, radius, 1.0F, 0.78F, 0.08F, alpha, 16, 48, time * (0.02F + i * 0.01F));
         }
      }
   }

   private static void renderBlackGoldCubeConstellation(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.25F + divine * 0.85F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.disableDepthTest();
         RenderSystem.blendFuncSeparate(770, 771, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 620; i++) {
            float seed = i * 43.771F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.045F + hash(seed + 1.0F) * 0.06F);
            float local = Mth.frac(hash(seed + 2.0F) + time * (0.03F + hash(seed + 3.0F) * 0.028F));
            float pull = smooth(local);
            float radius = scale * Mth.lerp(pull, 28.0F + hash(seed + 4.0F) * 26.0F, 1.2F + hash(seed + 5.0F) * 5.0F);
            float y = scale * ((hash(seed + 6.0F) - 0.5F) * (8.0F + explosion * 16.0F));
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            float alpha = strength * Mth.sin(local * (float) Math.PI) * (0.35F + hash(seed + 7.0F) * 0.7F);
            float size = scale * (0.035F + hash(seed + 8.0F) * 0.09F) * (1.0F + explosion * 0.9F);
            PhainonAcquirementVfxClient.Basis basis = cubeBasisFromSeed(time * 0.92F, seed);
            renderSolidCube(buffer, matrix, new Vec3(x, y, z), basis, size, alpha);
            renderWireCube(buffer, matrix, new Vec3(x, y, z), basis, size * 1.16, alpha * 1.6F);
            wrote = true;
         }

         if (wrote) {
            safeDraw(buffer);
         }

         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         RenderSystem.enableDepthTest();
      }
   }

   private static void renderGoldenCataclysmSpears(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.8F + divine * 0.95F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 220; i++) {
            float seed = i * 61.113F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float height = scale * (10.0F + hash(seed + 1.0F) * 18.0F + explosion * 22.0F);
            float radius = scale * (1.2F + hash(seed + 2.0F) * (18.0F + explosion * 20.0F));
            float local = Mth.frac(hash(seed + 3.0F) + time * (0.042F + hash(seed + 4.0F) * 0.05F));
            float descend = smooth(local);
            float x0 = Mth.cos(angle) * radius;
            float z0 = Mth.sin(angle) * radius;
            float y0 = height - descend * scale * (20.0F + explosion * 28.0F);
            float x1 = x0 * (0.4F + hash(seed + 5.0F) * 0.25F);
            float z1 = z0 * (0.4F + hash(seed + 6.0F) * 0.25F);
            float y1 = y0 - scale * (3.5F + hash(seed + 7.0F) * 6.5F);
            float width = scale * (0.025F + hash(seed + 8.0F) * 0.065F);
            float alpha = strength * Mth.sin(local * (float) Math.PI) * (0.05F + hash(seed + 9.0F) * 0.105F);
            wrote |= addBeam(buffer, matrix, x0, y0, z0, x1, y1, z1, width, 1.0F, 0.84F, 0.1F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenHeavenlyGlyphRain(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.55F + explosionGold * 1.1F + finalGold * 1.65F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 360; i++) {
            float seed = i * 71.917F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.03F + hash(seed + 1.0F) * 0.04F);
            float radius = scale * (1.2F + hash(seed + 2.0F) * (9.0F + finalGold * 7.0F + explosionGold * 9.0F));
            float fall = Mth.frac(hash(seed + 3.0F) + time * (0.03F + hash(seed + 4.0F) * 0.05F));
            float y = scale * (11.0F + finalGold * 9.0F) - fall * scale * (15.0F + finalGold * 12.0F + explosionGold * 10.0F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            float s = scale * (0.055F + hash(seed + 5.0F) * 0.16F);
            float rot = time * (0.6F + hash(seed + 6.0F) * 1.3F) + seed;
            float ca = Mth.cos(rot) * s;
            float sa = Mth.sin(rot) * s;
            float alpha = strength * Mth.sin(fall * (float) Math.PI) * (0.04F + hash(seed + 7.0F) * 0.08F);
            if (finite(alpha) && alpha > 5.0E-4F) {
               buffer.addVertex(matrix, x - ca * 1.8F, y - sa * 1.8F, z).setColor(1.0F, 0.55F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x + sa, y - ca, z).setColor(1.0F, 0.96F, 0.3F, alpha);
               buffer.addVertex(matrix, x + ca * 1.8F, y + sa * 1.8F, z).setColor(1.0F, 0.84F, 0.08F, alpha);
               buffer.addVertex(matrix, x - sa, y + ca, z).setColor(1.0F, 0.48F, 0.0F, 0.0F);
               wrote = true;
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenSolarWingCurtains(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.7F + explosionGold * 1.15F + finalGold * 1.8F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;
         int wings = 14;
         int segments = 84;

         for (int w = 0; w < wings; w++) {
            float seed = w * 101.31F;
            float side = w % 2 == 0 ? 1.0F : -1.0F;
            float yaw = side * (0.5F + w / 2 * 0.18F) + Mth.sin(time * 0.18F + seed) * 0.16F;
            float height = scale * (6.5F + hash(seed + 1.0F) * 7.5F + finalGold * 8.0F);
            float width = scale * (2.2F + hash(seed + 2.0F) * 3.2F + explosionGold * 4.5F + finalGold * 3.0F);
            float alpha = strength * (0.022F + hash(seed + 3.0F) * 0.042F);

            for (int i = 0; i < segments; i++) {
               float t0 = (float)i / segments;
               float t1 = (float)(i + 1) / segments;
               float f0 = Mth.sin(t0 * (float) Math.PI);
               float f1 = Mth.sin(t1 * (float) Math.PI);
               float y0 = -scale * 0.7F + t0 * height;
               float y1 = -scale * 0.7F + t1 * height;
               float spread0 = width * f0;
               float spread1 = width * f1;
               float wave0 = Mth.sin(t0 * 18.0F + time * 0.9F + seed) * scale * 0.18F;
               float wave1 = Mth.sin(t1 * 18.0F + time * 0.9F + seed) * scale * 0.18F;
               Vec3 p0 = new Vec3(Mth.cos(yaw) * (scale * 0.6F + spread0), y0, Mth.sin(yaw) * (scale * 0.6F + spread0) + wave0);
               Vec3 p1 = new Vec3(Mth.cos(yaw) * (scale * 0.6F + spread1), y1, Mth.sin(yaw) * (scale * 0.6F + spread1) + wave1);
               Vec3 inner0 = new Vec3(Mth.cos(yaw) * scale * 0.45F, y0, Mth.sin(yaw) * scale * 0.45F);
               Vec3 inner1 = new Vec3(Mth.cos(yaw) * scale * 0.45F, y1, Mth.sin(yaw) * scale * 0.45F);
               addQuad(
                  buffer,
                  matrix,
                  new PhainonAcquirementVfxClient.RibbonVertex((float)inner0.x, (float)inner0.y, (float)inner0.z, 1.0F, 0.44F, 0.0F, 0.0F),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)inner1.x, (float)inner1.y, (float)inner1.z, 1.0F, 0.9F, 0.22F, alpha * f1),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)p1.x, (float)p1.y, (float)p1.z, 1.0F, 0.76F, 0.05F, alpha * f1),
                  new PhainonAcquirementVfxClient.RibbonVertex((float)p0.x, (float)p0.y, (float)p0.z, 1.0F, 0.42F, 0.0F, 0.0F),
                  1.0F
               );
               wrote = true;
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenRoyalStarfield(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.55F + explosionGold * 0.85F + finalGold * 1.5F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 520; i++) {
            float seed = i * 19.331F;
            float angle = hash(seed) * (float) (Math.PI * 2) + time * (0.012F + hash(seed + 1.0F) * 0.035F);
            float radius = scale * (1.5F + hash(seed + 2.0F) * (13.0F + finalGold * 10.0F));
            float y = scale * (-1.0F + hash(seed + 3.0F) * (12.0F + finalGold * 9.0F));
            float pulse = 0.45F + 0.55F * Mth.sin(time * (2.4F + hash(seed + 4.0F) * 4.0F) + seed);
            float size = scale * (0.018F + hash(seed + 5.0F) * 0.085F) * (0.55F + pulse);
            float alpha = strength * pulse * (0.032F + hash(seed + 6.0F) * 0.07F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            wrote |= addBillboardQuad(buffer, matrix, x, y, z, size, 1.0F, 0.9F, 0.2F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderBlackHoleFinalExplosionCross(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 2.1F + divine * 0.75F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 144; i++) {
            float seed = i * 61.331F;
            float angle = i * (float) (Math.PI * 2) / 144.0F + Mth.sin(time * 0.25F + seed) * 0.025F;
            float inner = scale * (0.3F + hash(seed) * 0.75F);
            float outer = scale * (8.0F + explosion * 42.0F + hash(seed + 1.0F) * 28.0F);
            float y0 = (hash(seed + 2.0F) - 0.5F) * scale * 1.2F;
            float y1 = y0 + (hash(seed + 3.0F) - 0.5F) * scale * (2.0F + explosion * 6.0F);
            float width = scale * (0.022F + hash(seed + 4.0F) * 0.072F);
            float alpha = strength * (0.08F + hash(seed + 5.0F) * 0.13F);
            wrote |= addBeam(
               buffer,
               matrix,
               Mth.cos(angle) * inner,
               y0,
               Mth.sin(angle) * inner,
               Mth.cos(angle) * outer,
               y1,
               Mth.sin(angle) * outer,
               width,
               1.0F,
               0.74F,
               0.04F,
               alpha
            );
         }

         if (wrote) {
            safeDraw(buffer);
         }

         renderGlowSphere(matrix, scale * (2.0F + explosion * 30.0F), 1.0F, 0.62F, 0.02F, strength * 0.06F, 18, 54, time * 0.05F);
         renderGlowSphere(matrix, scale * (1.0F + explosion * 18.0F), 1.0F, 0.02F, 0.0F, strength * 0.05F, 16, 48, -time * 0.08F);
      }
   }

   private static void renderAnchoredGoldenAfterburstRain(Matrix4f matrix, float fade, float explosion, float divine, float time, float scale) {
      float strength = fade * (explosion * 1.55F + divine * 1.35F);
      if (!(strength <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 420; i++) {
            float seed = i * 71.41F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float radius = scale * (1.0F + hash(seed + 1.0F) * (16.0F + explosion * 28.0F));
            float fall = Mth.frac(hash(seed + 2.0F) + time * (0.04F + hash(seed + 3.0F) * 0.05F));
            float y = scale * (13.0F + divine * 8.0F) - fall * scale * (18.0F + divine * 10.0F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            float length = scale * (0.9F + hash(seed + 4.0F) * 4.2F);
            float width = scale * (0.012F + hash(seed + 5.0F) * 0.04F);
            float alpha = strength * Mth.sin(fall * (float) Math.PI) * (0.045F + hash(seed + 6.0F) * 0.075F);
            wrote |= addBeam(buffer, matrix, x, y, z, x * 0.88F, y - length, z * 0.88F, width, 1.0F, 0.84F, 0.12F, alpha);
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenAuraCrownCascade(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.65F + explosionGold * 1.05F + finalGold * 1.85F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);

         for (int i = 0; i < 18; i++) {
            float y = scale * (-0.5F + i * 0.46F);
            float spin = time * (0.48F + i * 0.035F) * (i % 2 == 0 ? 1.0F : -1.0F);
            float inner = scale * (0.55F + i * 0.035F);
            float outer = scale * (2.8F + i * 0.34F + finalGold * 4.0F + explosionGold * 6.0F);
            float alpha = strength * (0.042F + hash(i * 9.17F) * 0.055F);
            renderOffsetRing(matrix, inner, outer, y, 240, spin, 1.0F, 0.86F, 0.12F, alpha, scale * 0.065F);
         }
      }
   }

   private static void renderGoldenWorldTreeThreads(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.55F + explosionGold * 0.85F + finalGold * 1.45F;
      if (!(strength <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int branch = 0; branch < 96; branch++) {
            float seed = branch * 43.713F;
            float yaw = hash(seed) * (float) (Math.PI * 2) + time * (0.018F + hash(seed + 1.0F) * 0.02F);
            float baseRadius = scale * (0.55F + hash(seed + 2.0F) * 1.2F);
            float height = scale * (5.0F + hash(seed + 3.0F) * 9.5F + finalGold * 6.0F);
            float width = scale * (0.014F + hash(seed + 4.0F) * 0.045F);
            float alpha = strength * (0.045F + hash(seed + 5.0F) * 0.085F);
            Vec3 prev = null;

            for (int i = 0; i <= 18; i++) {
               float t = i / 18.0F;
               float curve = Mth.sin(t * (float) Math.PI);
               float angle = yaw + curve * (0.8F + hash(seed + 6.0F) * 1.6F);
               float r = baseRadius + curve * scale * (1.5F + hash(seed + 7.0F) * 4.5F + explosionGold * 4.0F);
               Vec3 now = new Vec3(Mth.cos(angle) * r, -scale * 0.65F + t * height, Mth.sin(angle) * r);
               if (prev != null) {
                  float a = alpha * Mth.sin(t * (float) Math.PI);
                  wrote |= addBeam(
                     buffer, matrix, (float)prev.x, (float)prev.y, (float)prev.z, (float)now.x, (float)now.y, (float)now.z, width, 1.0F, 0.92F, 0.24F, a
                  );
               }

               prev = now;
            }
         }

         if (wrote) {
            safeDraw(buffer);
         }
      }
   }

   private static void renderGoldenSupernovaBloom(Matrix4f matrix, float fade, float explosion, float divine, float grand, float time, float scale) {
      float strength = fade * (explosion * 2.2F + divine * 1.15F + grand * 3.4F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         renderGlowSphere(matrix, scale * (2.0F + explosion * 34.0F + grand * 62.0F), 1.0F, 0.78F, 0.05F, strength * 0.07F, 22, 72, time * 0.045F);
         renderGlowSphere(matrix, scale * (1.2F + explosion * 20.0F + grand * 38.0F), 1.0F, 0.98F, 0.35F, strength * 0.052F, 20, 68, -time * 0.055F);
         renderRing(
            matrix, scale * 0.2F, scale * (14.0F + explosion * 60.0F + grand * 84.0F), 340, time * 1.35F, 1.0F, 0.82F, 0.1F, strength * 0.3F, scale * 0.55F
         );
         renderRing(
            matrix, scale * 0.85F, scale * (19.0F + explosion * 76.0F + grand * 100.0F), 340, -time * 0.88F, 1.0F, 0.52F, 0.02F, strength * 0.22F, scale * 0.8F
         );
      }
   }

   private static void renderGoldenAbyssBreakRays(Matrix4f matrix, float fade, float explosion, float divine, float grand, float time, float scale) {
      float strength = fade * (explosion * 2.05F + divine * 0.95F + grand * 2.85F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 520; i++) {
            float seed = i * 47.371F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float pitch = -0.75F + hash(seed + 1.0F) * 1.7F;
            float inner = scale * (0.55F + hash(seed + 2.0F) * 1.45F);
            float outer = scale * (18.0F + hash(seed + 3.0F) * (58.0F + grand * 80.0F + explosion * 42.0F));
            float wave = Mth.sin(time * (0.8F + hash(seed + 4.0F) * 1.8F) + seed) * scale * 0.45F;
            float x0 = Mth.cos(angle) * inner;
            float z0 = Mth.sin(angle) * inner;
            float y0 = (hash(seed + 5.0F) - 0.5F) * scale * 2.4F;
            float x1 = Mth.cos(angle + Mth.sin(seed) * 0.12F) * outer;
            float z1 = Mth.sin(angle + Mth.sin(seed) * 0.12F) * outer;
            float y1 = y0 + Mth.sin(pitch) * scale * (8.0F + explosion * 18.0F + grand * 22.0F) + wave;
            float width = scale * (0.022F + hash(seed + 6.0F) * 0.09F) * (1.0F + grand * 0.9F);
            float alpha = strength * (0.035F + hash(seed + 7.0F) * 0.092F);
            wrote |= addBeam(buffer, matrix, x0, y0, z0, x1, y1, z1, width, 1.0F, 0.78F, 0.06F, alpha);
         }

         safeDraw(buffer, wrote);
      }
   }

   private static void renderGoldenOriginCrown(Matrix4f matrix, float fade, float explosion, float divine, float grand, float time, float scale) {
      float strength = fade * (explosion * 1.55F + divine * 1.35F + grand * 2.35F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 360; i++) {
            float seed = i * 31.811F;
            float angle = i * (float) (Math.PI * 2) / 360.0F + time * (0.16F + hash(seed) * 0.22F);
            float local = Mth.sin(time * 1.2F + seed) * 0.5F + 0.5F;
            float radius = scale * (5.0F + hash(seed + 1.0F) * (18.0F + grand * 26.0F));
            float height = scale * (1.5F + hash(seed + 2.0F) * (8.0F + explosion * 10.0F + grand * 12.0F));
            float y = scale * (-0.25F + hash(seed + 3.0F) * 3.0F);
            float width = scale * (0.1F + hash(seed + 4.0F) * 0.34F);
            float alpha = strength * (0.042F + hash(seed + 5.0F) * 0.09F) * (0.65F + local * 0.35F);
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;
            float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
            float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
            buffer.addVertex(matrix, x - sx, y, z - sz).setColor(1.0F, 0.45F, 0.0F, 0.0F);
            buffer.addVertex(matrix, Mth.cos(angle) * (radius * 1.05F), y + height, Mth.sin(angle) * (radius * 1.05F)).setColor(1.0F, 0.96F, 0.3F, alpha);
            buffer.addVertex(matrix, x + sx, y, z + sz).setColor(1.0F, 0.68F, 0.04F, 0.0F);
            wrote = true;
         }

         safeDraw(buffer, wrote);
      }
   }

   private static void renderGoldenPetalSupernova(Matrix4f matrix, float fade, float explosion, float divine, float grand, float time, float scale) {
      float strength = fade * (explosion * 2.8F + divine * 1.45F + grand * 4.25F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int ring = 0; ring < 9; ring++) {
            int petals = 42 + ring * 14;
            float ringLift = scale * (-1.2F + ring * 0.36F);
            float spin = time * (0.58F + ring * 0.055F) * (ring % 2 == 0 ? 1.0F : -1.0F);
            float baseRadius = scale * (2.0F + ring * 0.75F + explosion * 11.0F + grand * 17.0F);

            for (int i = 0; i < petals; i++) {
               float seed = ring * 10000.0F + i * 37.719F;
               float angle = i * (float) (Math.PI * 2) / petals + spin + hash(seed) * 0.075F;
               float burst = scale * (10.0F + hash(seed + 1.0F) * (46.0F + grand * 82.0F + explosion * 38.0F));
               float length = scale * (1.6F + hash(seed + 2.0F) * 5.8F) * (1.0F + grand * 1.25F + explosion * 0.75F);
               float width = scale * (0.18F + hash(seed + 3.0F) * 0.55F) * (1.0F + grand * 0.55F);
               float y = ringLift + scale * ((hash(seed + 4.0F) - 0.5F) * (4.0F + explosion * 10.0F + grand * 12.0F));
               float r0 = baseRadius;
               float r1 = baseRadius + burst;
               float tipY = y + scale * ((hash(seed + 5.0F) - 0.35F) * (8.0F + explosion * 14.0F + grand * 18.0F));
               float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
               float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
               float x0 = Mth.cos(angle) * r0;
               float z0 = Mth.sin(angle) * r0;
               float x1 = Mth.cos(angle + Mth.sin(seed) * 0.05F) * r1;
               float z1 = Mth.sin(angle + Mth.sin(seed) * 0.05F) * r1;
               float alpha = strength * (0.035F + hash(seed + 6.0F) * 0.085F);
               buffer.addVertex(matrix, x0 - sx, y, z0 - sz).setColor(1.0F, 0.44F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x1, tipY + length * 0.22F, z1).setColor(1.0F, 0.98F, 0.32F, alpha);
               buffer.addVertex(matrix, x0 + sx, y, z0 + sz).setColor(1.0F, 0.72F, 0.04F, 0.0F);
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);
         renderGlowSphere(matrix, scale * (4.0F + explosion * 50.0F + grand * 105.0F), 1.0F, 0.82F, 0.1F, strength * 0.075F, 24, 80, time * 0.035F);
         renderRing(
            matrix, scale * 0.35F, scale * (22.0F + explosion * 90.0F + grand * 128.0F), 420, time * 1.55F, 1.0F, 0.88F, 0.12F, strength * 0.24F, scale * 1.1F
         );
      }
   }

   private static void renderGoldenAbsoluteDawnBurst(Matrix4f matrix, float fade, float explosion, float divine, float grand, float time, float scale) {
      float strength = fade * (explosion * 2.45F + divine * 1.55F + grand * 3.9F);
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int i = 0; i < 760; i++) {
            float seed = i * 67.133F;
            float angle = hash(seed) * (float) (Math.PI * 2);
            float pitch = -0.95F + hash(seed + 1.0F) * 1.9F;
            float r0 = scale * (0.25F + hash(seed + 2.0F) * 1.4F);
            float r1 = scale * (24.0F + hash(seed + 3.0F) * (72.0F + grand * 110.0F + explosion * 54.0F));
            float bend = (hash(seed + 4.0F) - 0.5F) * 0.22F;
            float x0 = Mth.cos(angle) * r0;
            float z0 = Mth.sin(angle) * r0;
            float y0 = scale * ((hash(seed + 5.0F) - 0.5F) * 2.0F);
            float x1 = Mth.cos(angle + bend) * r1;
            float z1 = Mth.sin(angle + bend) * r1;
            float y1 = y0 + Mth.sin(pitch) * scale * (9.0F + explosion * 22.0F + grand * 32.0F);
            float width = scale * (0.016F + hash(seed + 6.0F) * 0.085F) * (1.0F + grand * 0.8F);
            float alpha = strength * (0.03F + hash(seed + 7.0F) * 0.088F);
            wrote |= addBeam(buffer, matrix, x0, y0, z0, x1, y1, z1, width, 1.0F, 0.86F, 0.12F, alpha);
         }

         safeDraw(buffer, wrote);
      }
   }

   private static void renderGoldenPetalAuraStorm(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.8F + explosionGold * 1.75F + finalGold * 2.15F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         boolean wrote = false;

         for (int layer = 0; layer < 7; layer++) {
            int petals = 72 + layer * 18;
            float yBase = scale * (-0.65F + layer * 0.72F);
            float spin = time * (0.65F + layer * 0.065F) * (layer % 2 == 0 ? 1.0F : -1.0F);
            float radiusBase = scale * (1.1F + layer * 0.55F + finalGold * 1.6F + explosionGold * 3.8F);

            for (int i = 0; i < petals; i++) {
               float seed = layer * 9000.0F + i * 29.557F;
               float angle = i * (float) (Math.PI * 2) / petals + spin + hash(seed) * 0.12F;
               float flutter = Mth.sin(time * (1.8F + hash(seed + 1.0F) * 1.2F) + seed) * scale * 0.18F;
               float radius = radiusBase + hash(seed + 2.0F) * scale * (2.5F + finalGold * 4.0F + explosionGold * 5.0F);
               float y = yBase + flutter + hash(seed + 3.0F) * scale * (0.6F + finalGold * 1.5F);
               float length = scale * (0.55F + hash(seed + 4.0F) * 1.65F) * (1.0F + finalGold * 0.65F + explosionGold * 0.9F);
               float width = scale * (0.055F + hash(seed + 5.0F) * 0.17F);
               float x = Mth.cos(angle) * radius;
               float z = Mth.sin(angle) * radius;
               float tx = Mth.cos(angle + 0.22F + Mth.sin(seed) * 0.12F) * length;
               float tz = Mth.sin(angle + 0.22F + Mth.sin(seed) * 0.12F) * length;
               float sx = Mth.cos(angle + (float) (Math.PI / 2)) * width;
               float sz = Mth.sin(angle + (float) (Math.PI / 2)) * width;
               float alpha = strength * (0.03F + hash(seed + 6.0F) * 0.07F) * (0.55F + 0.45F * Mth.sin(time * 2.4F + seed));
               buffer.addVertex(matrix, x - sx, y, z - sz).setColor(1.0F, 0.46F, 0.0F, 0.0F);
               buffer.addVertex(matrix, x + tx, y + length * 0.2F, z + tz).setColor(1.0F, 0.98F, 0.34F, alpha);
               buffer.addVertex(matrix, x + sx, y, z + sz).setColor(1.0F, 0.72F, 0.04F, 0.0F);
               wrote = true;
            }
         }

         safeDraw(buffer, wrote);
      }
   }

   private static void renderGoldenBlindingSolarPetalCrown(Matrix4f matrix, float gold, float explosionGold, float finalGold, float time, float scale) {
      float strength = gold * 0.65F + explosionGold * 1.4F + finalGold * 2.35F;
      if (!(strength <= 5.0E-4F)) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);

         for (int i = 0; i < 11; i++) {
            float wave = Mth.sin(time * (0.55F + i * 0.04F) + i * 0.7F) * 0.5F + 0.5F;
            float inner = scale * (0.6F + i * 0.1F);
            float outer = scale * (4.5F + i * 1.35F + finalGold * 8.0F + explosionGold * 7.0F + wave * 3.5F);
            float y = scale * (-0.25F + i * 0.44F);
            float alpha = strength * (0.05F + i * 0.006F);
            renderOffsetRing(matrix, inner, outer, y, 320, time * (0.9F + i * 0.065F), 1.0F, 0.88F, 0.16F, alpha, scale * (0.12F + i * 0.018F));
         }
      }
   }

   private static void renderAsmodayBlackSphere(Matrix4f matrix, float radius) {
      if (!(radius <= 0.05F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
         int stacks = 30;
         int slices = 70;

         for (int i = 0; i < stacks; i++) {
            double theta1 = Math.PI * i / stacks;
            double theta2 = Math.PI * (i + 1) / stacks;

            for (int j = 0; j < slices; j++) {
               double phi1 = (float) (Math.PI * 2) * j / slices;
               double phi2 = (float) (Math.PI * 2) * (j + 1) / slices;
               addBlackSphereVertex(buffer, matrix, radius, theta1, phi1);
               addBlackSphereVertex(buffer, matrix, radius, theta2, phi1);
               addBlackSphereVertex(buffer, matrix, radius, theta2, phi2);
               addBlackSphereVertex(buffer, matrix, radius, theta1, phi1);
               addBlackSphereVertex(buffer, matrix, radius, theta2, phi2);
               addBlackSphereVertex(buffer, matrix, radius, theta1, phi2);
            }
         }

         safeDraw(buffer);
      }
   }

   private static void addBlackSphereVertex(BufferBuilder buffer, Matrix4f matrix, float radius, double theta, double phi) {
      float x = (float)(Math.sin(theta) * Math.cos(phi) * radius);
      float y = (float)(Math.cos(theta) * radius);
      float z = (float)(Math.sin(theta) * Math.sin(phi) * radius);
      buffer.addVertex(matrix, x, y, z).setColor(0.0F, 0.0F, 0.0F, 1.0F);
   }

   private static void renderGlowSphere(Matrix4f matrix, float radius, float red, float green, float blue, float alpha, int stacks, int slices, float rotation) {
      if (!(alpha <= 5.0E-4F) && !(radius <= 5.0E-4F) && stacks > 0 && slices > 0) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

         for (int i = 0; i < stacks; i++) {
            double theta1 = Math.PI * i / stacks;
            double theta2 = Math.PI * (i + 1) / stacks;

            for (int j = 0; j < slices; j++) {
               double phi1 = (float) (Math.PI * 2) * j / slices + rotation;
               double phi2 = (float) (Math.PI * 2) * (j + 1) / slices + rotation;
               addGlowSphereVertex(buffer, matrix, radius, theta1, phi1, red, green, blue, alpha);
               addGlowSphereVertex(buffer, matrix, radius, theta2, phi1, red, green, blue, alpha * 0.7F);
               addGlowSphereVertex(buffer, matrix, radius, theta2, phi2, red, green, blue, alpha * 0.7F);
               addGlowSphereVertex(buffer, matrix, radius, theta1, phi1, red, green, blue, alpha);
               addGlowSphereVertex(buffer, matrix, radius, theta2, phi2, red, green, blue, alpha * 0.7F);
               addGlowSphereVertex(buffer, matrix, radius, theta1, phi2, red, green, blue, alpha);
            }
         }

         safeDraw(buffer);
      }
   }

   private static void addGlowSphereVertex(
      BufferBuilder buffer, Matrix4f matrix, float radius, double theta, double phi, float red, float green, float blue, float alpha
   ) {
      float x = (float)(Math.sin(theta) * Math.cos(phi) * radius);
      float y = (float)(Math.cos(theta) * radius);
      float z = (float)(Math.sin(theta) * Math.sin(phi) * radius);
      buffer.addVertex(matrix, x, y, z).setColor(red, green, blue, alpha);
   }

   private static void renderRing(Matrix4f matrix, float inner, float outer, int segments, float spin, float r, float g, float b, float alpha, float wobble) {
      if (!(alpha <= 5.0E-4F) && !(inner <= 0.0F) && !(outer <= 0.0F) && segments > 0) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

         for (int i = 0; i < segments; i++) {
            float a0 = (float) (Math.PI * 2) * i / segments + spin;
            float a1 = (float) (Math.PI * 2) * (i + 1) / segments + spin;
            float w0 = Mth.sin(a0 * 3.0F + spin) * wobble;
            float w1 = Mth.sin(a1 * 3.0F + spin) * wobble;
            buffer.addVertex(matrix, Mth.cos(a0) * inner, w0, Mth.sin(a0) * inner).setColor(r, g, b, 0.0F);
            buffer.addVertex(matrix, Mth.cos(a0) * outer, w0, Mth.sin(a0) * outer).setColor(r, g, b, alpha);
            buffer.addVertex(matrix, Mth.cos(a1) * outer, w1, Mth.sin(a1) * outer).setColor(r, g, b, alpha);
            buffer.addVertex(matrix, Mth.cos(a1) * inner, w1, Mth.sin(a1) * inner).setColor(r, g, b, 0.0F);
         }

         safeDraw(buffer);
      }
   }

   private static void renderOffsetRing(
      Matrix4f matrix, float inner, float outer, float y, int segments, float spin, float r, float g, float b, float alpha, float wobble
   ) {
      if (!(alpha <= 5.0E-4F) && !(inner <= 0.0F) && !(outer <= 0.0F) && segments > 0) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

         for (int i = 0; i < segments; i++) {
            float a0 = (float) (Math.PI * 2) * i / segments + spin;
            float a1 = (float) (Math.PI * 2) * (i + 1) / segments + spin;
            float w0 = Mth.sin(a0 * 3.0F + spin) * wobble;
            float w1 = Mth.sin(a1 * 3.0F + spin) * wobble;
            buffer.addVertex(matrix, Mth.cos(a0) * inner, y + w0, Mth.sin(a0) * inner).setColor(r, g, b, 0.0F);
            buffer.addVertex(matrix, Mth.cos(a0) * outer, y + w0, Mth.sin(a0) * outer).setColor(r, g, b, alpha);
            buffer.addVertex(matrix, Mth.cos(a1) * outer, y + w1, Mth.sin(a1) * outer).setColor(r, g, b, alpha);
            buffer.addVertex(matrix, Mth.cos(a1) * inner, y + w1, Mth.sin(a1) * inner).setColor(r, g, b, 0.0F);
         }

         safeDraw(buffer);
      }
   }

   private static void renderSolidCube(BufferBuilder buffer, Matrix4f matrix, Vec3 center, PhainonAcquirementVfxClient.Basis basis, double size, float alpha) {
      if (!(alpha <= 5.0E-4F) && !(size <= 5.0E-4)) {
         double h = size * 0.5;
         Vec3 r = basis.right.scale(h);
         Vec3 u = basis.up.scale(h);
         Vec3 f = basis.forward.scale(h);
         Vec3 p000 = center.subtract(r).subtract(u).subtract(f);
         Vec3 p001 = center.subtract(r).subtract(u).add(f);
         Vec3 p010 = center.subtract(r).add(u).subtract(f);
         Vec3 p011 = center.subtract(r).add(u).add(f);
         Vec3 p100 = center.add(r).subtract(u).subtract(f);
         Vec3 p101 = center.add(r).subtract(u).add(f);
         Vec3 p110 = center.add(r).add(u).subtract(f);
         Vec3 p111 = center.add(r).add(u).add(f);
         float a = Mth.clamp(alpha * 3.8F, 0.0F, 1.0F);
         addCubeFace(buffer, matrix, p000, p100, p110, p010, 0.0F, 0.0F, 0.0F, a);
         addCubeFace(buffer, matrix, p101, p001, p011, p111, 0.0F, 0.0F, 0.0F, a);
         addCubeFace(buffer, matrix, p001, p000, p010, p011, 0.0F, 0.0F, 0.0F, a);
         addCubeFace(buffer, matrix, p100, p101, p111, p110, 0.0F, 0.0F, 0.0F, a);
         addCubeFace(buffer, matrix, p010, p110, p111, p011, 0.0F, 0.0F, 0.0F, a);
         addCubeFace(buffer, matrix, p001, p101, p100, p000, 0.0F, 0.0F, 0.0F, a);
      }
   }

   private static void renderWireCube(BufferBuilder buffer, Matrix4f matrix, Vec3 center, PhainonAcquirementVfxClient.Basis basis, double size, float alpha) {
      if (!(alpha <= 5.0E-4F) && !(size <= 5.0E-4)) {
         double h = size * 0.5;
         Vec3 r = basis.right.scale(h);
         Vec3 u = basis.up.scale(h);
         Vec3 f = basis.forward.scale(h);
         Vec3[] p = new Vec3[]{
            center.subtract(r).subtract(u).subtract(f),
            center.add(r).subtract(u).subtract(f),
            center.add(r).add(u).subtract(f),
            center.subtract(r).add(u).subtract(f),
            center.subtract(r).subtract(u).add(f),
            center.add(r).subtract(u).add(f),
            center.add(r).add(u).add(f),
            center.subtract(r).add(u).add(f)
         };
         double w = size * 0.07;
         float a = Mth.clamp(alpha * 3.6F, 0.0F, 1.0F);
         addCubeLine(buffer, matrix, p[0], p[1], w, a);
         addCubeLine(buffer, matrix, p[1], p[2], w, a);
         addCubeLine(buffer, matrix, p[2], p[3], w, a);
         addCubeLine(buffer, matrix, p[3], p[0], w, a);
         addCubeLine(buffer, matrix, p[4], p[5], w, a);
         addCubeLine(buffer, matrix, p[5], p[6], w, a);
         addCubeLine(buffer, matrix, p[6], p[7], w, a);
         addCubeLine(buffer, matrix, p[7], p[4], w, a);
         addCubeLine(buffer, matrix, p[0], p[4], w, a);
         addCubeLine(buffer, matrix, p[1], p[5], w, a);
         addCubeLine(buffer, matrix, p[2], p[6], w, a);
         addCubeLine(buffer, matrix, p[3], p[7], w, a);
      }
   }

   private static void addCubeLine(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, double width, float alpha) {
      if (!(alpha <= 5.0E-4F) && !(width <= 1.0E-4)) {
         Vec3 dir = b.subtract(a);
         if (!(dir.lengthSqr() < 1.0E-4)) {
            Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
            if (side.lengthSqr() < 1.0E-4) {
               side = dir.cross(new Vec3(1.0, 0.0, 0.0));
            }

            if (!(side.lengthSqr() < 1.0E-4)) {
               side = side.normalize().scale(width);
               Vec3 p0 = a.add(side);
               Vec3 p1 = b.add(side);
               Vec3 p2 = b.subtract(side);
               Vec3 p3 = a.subtract(side);
               addCubeFace(buffer, matrix, p0, p1, p2, p3, 1.0F, 0.0F, 0.0F, alpha);
            }
         }
      }
   }

   private static void addCubeFace(BufferBuilder buffer, Matrix4f matrix, Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, float r, float g, float b, float a) {
      if (!(a <= 5.0E-4F)) {
         buffer.addVertex(matrix, (float)p0.x, (float)p0.y, (float)p0.z).setColor(r, g, b, a);
         buffer.addVertex(matrix, (float)p1.x, (float)p1.y, (float)p1.z).setColor(r, g, b, a);
         buffer.addVertex(matrix, (float)p2.x, (float)p2.y, (float)p2.z).setColor(r, g, b, a);
         buffer.addVertex(matrix, (float)p0.x, (float)p0.y, (float)p0.z).setColor(r, g, b, a);
         buffer.addVertex(matrix, (float)p2.x, (float)p2.y, (float)p2.z).setColor(r, g, b, a);
         buffer.addVertex(matrix, (float)p3.x, (float)p3.y, (float)p3.z).setColor(r, g, b, a);
      }
   }

   private static PhainonAcquirementVfxClient.Basis cubeBasisFromSeed(float time, float seed) {
      Vec3 forward = new Vec3(Mth.sin(time * 0.19F + seed), Mth.cos(time * 0.13F + seed * 0.7F), Mth.sin(time * 0.11F + seed * 0.3F));
      if (forward.lengthSqr() < 1.0E-4) {
         forward = new Vec3(0.0, 1.0, 0.0);
      }

      forward = forward.normalize();
      Vec3 right = forward.cross(new Vec3(0.0, 1.0, 0.0));
      if (right.lengthSqr() < 1.0E-4) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 up = right.cross(forward).normalize();
      return new PhainonAcquirementVfxClient.Basis(forward, right, up);
   }

   private static void addQuad(
      BufferBuilder buffer,
      Matrix4f matrix,
      PhainonAcquirementVfxClient.RibbonVertex a,
      PhainonAcquirementVfxClient.RibbonVertex b,
      PhainonAcquirementVfxClient.RibbonVertex c,
      PhainonAcquirementVfxClient.RibbonVertex d,
      float alphaMul
   ) {
      if (finite(a.x)
         && finite(a.y)
         && finite(a.z)
         && finite(b.x)
         && finite(b.y)
         && finite(b.z)
         && finite(c.x)
         && finite(c.y)
         && finite(c.z)
         && finite(d.x)
         && finite(d.y)
         && finite(d.z)) {
         float aa = a.a * alphaMul;
         float ba = b.a * alphaMul;
         float ca = c.a * alphaMul;
         float da = d.a * alphaMul;
         if (finite(aa) && !(aa <= 5.0E-4F) || finite(ba) && !(ba <= 5.0E-4F) || finite(ca) && !(ca <= 5.0E-4F) || finite(da) && !(da <= 5.0E-4F)) {
            buffer.addVertex(matrix, a.x, a.y, a.z).setColor(a.r, a.g, a.b, finite(aa) ? aa : 0.0F);
            buffer.addVertex(matrix, b.x, b.y, b.z).setColor(b.r, b.g, b.b, finite(ba) ? ba : 0.0F);
            buffer.addVertex(matrix, c.x, c.y, c.z).setColor(c.r, c.g, c.b, finite(ca) ? ca : 0.0F);
            buffer.addVertex(matrix, d.x, d.y, d.z).setColor(d.r, d.g, d.b, finite(da) ? da : 0.0F);
         }
      }
   }

   private static boolean addBeam(
      BufferBuilder buffer, Matrix4f matrix, float x0, float y0, float z0, float x1, float y1, float z1, float width, float r, float g, float b, float a
   ) {
      if (!(a <= 5.0E-4F) && !(width <= 1.0E-5F)) {
         Vec3 dir = new Vec3(x1 - x0, y1 - y0, z1 - z0);
         if (dir.lengthSqr() < 1.0E-4) {
            return false;
         }

         Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
         if (side.lengthSqr() < 1.0E-4) {
            side = dir.cross(new Vec3(1.0, 0.0, 0.0));
         }

         if (side.lengthSqr() < 1.0E-4) {
            return false;
         }

         side = side.normalize().scale(width);
         buffer.addVertex(matrix, (float)(x0 - side.x), (float)(y0 - side.y), (float)(z0 - side.z)).setColor(r, g, b, 0.0F);
         buffer.addVertex(matrix, (float)(x0 + side.x), (float)(y0 + side.y), (float)(z0 + side.z)).setColor(r, g, b, a);
         buffer.addVertex(matrix, (float)(x1 + side.x), (float)(y1 + side.y), (float)(z1 + side.z)).setColor(r, g, b, a);
         buffer.addVertex(matrix, (float)(x1 - side.x), (float)(y1 - side.y), (float)(z1 - side.z)).setColor(r, g, b, 0.0F);
         return true;
      } else {
         return false;
      }
   }

   private static boolean addBillboardQuad(BufferBuilder buffer, Matrix4f matrix, float x, float y, float z, float size, float r, float g, float b, float a) {
      if (finite(x) && finite(y) && finite(z) && finite(size) && finite(a) && !(a <= 5.0E-4F) && !(size <= 5.0E-4F)) {
         buffer.addVertex(matrix, x - size, y - size, z).setColor(r, g, b, 0.0F);
         buffer.addVertex(matrix, x + size, y - size, z).setColor(r, g, b, a);
         buffer.addVertex(matrix, x + size, y + size, z).setColor(r, g, b, a);
         buffer.addVertex(matrix, x - size, y + size, z).setColor(r, g, b, 0.0F);
         return true;
      } else {
         return false;
      }
   }

   private static void safeDraw(BufferBuilder buffer) {
      try {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      } catch (IllegalStateException var2) {
      }
   }

   private static void safeDraw(BufferBuilder buffer, boolean wrote) {
      if (wrote) {
         safeDraw(buffer);
      }
   }

   private static boolean finite(float value) {
      return !Float.isNaN(value) && !Float.isInfinite(value);
   }

   private static boolean finite(double value) {
      return !Double.isNaN(value) && !Double.isInfinite(value);
   }

   private static void updatePostShaderTarget(Minecraft minecraft, float partial) {
      PhainonAcquirementVfxClient.State best = null;
      Entity bestEntity = null;
      double bestX = 0.0;
      double bestY = 0.0;
      double bestZ = 0.0;
      double bestDistance = Double.MAX_VALUE;
      if (minecraft.level != null) {
         Vec3 cameraPos = minecraft.gameRenderer.getMainCamera().getPosition();

         for (PhainonAcquirementVfxClient.State state : ACTIVE.values()) {
            Entity entity = minecraft.level.getEntity(state.entityId);
            double ex;
            double ey;
            double ez;
            if (entity != null) {
               ex = Mth.lerp(partial, entity.xOld, entity.getX());
               ey = Mth.lerp(partial, entity.yOld, entity.getY());
               ez = Mth.lerp(partial, entity.zOld, entity.getZ());
               state.hasWorldPosition = true;
               state.lastX = ex;
               state.lastY = ey;
               state.lastZ = ez;
            } else {
               if (!state.hasWorldPosition) {
                  continue;
               }

               ex = state.lastX;
               ey = state.lastY;
               ez = state.lastZ;
            }

            double distance = cameraPos.distanceTo(new Vec3(ex, ey, ez));
            if (distance < bestDistance) {
               bestDistance = distance;
               best = state;
               bestEntity = entity;
               bestX = ex;
               bestY = ey;
               bestZ = ez;
            }
         }
      }

      if (best == null) {
         shaderStrength *= 0.84F;
         shaderVoid *= 0.84F;
         shaderGold *= 0.84F;
         shaderShock *= 0.84F;
         uploadPostUniforms();
      } else {
         Camera camera = minecraft.gameRenderer.getMainCamera();
         float p = Mth.clamp((best.age + partial) / 640.0F, 0.0F, 1.0F);
         float blackhole = smooth(range(p, 0.006F, 0.105F)) * (1.0F - smooth(range(p, 0.9F, 0.985F)));
         float explosion = smooth(range(p, 0.315F, 0.455F)) * (1.0F - smooth(range(p, 0.68F, 0.82F)));
         float gold = smooth(range(p, 0.22F, 0.38F));
         float finalGold = smooth(range(p, 0.36F, 0.88F));
         float longDrain = smooth(range(p, 0.0F, 0.035F)) * (1.0F - smooth(range(p, 0.965F, 1.0F)));
         float fadeOut = 1.0F - smooth(range(p, 0.965F, 1.0F));
         Vec3 look;
         float entityScale;
         if (bestEntity != null) {
            entityScale = Math.max(1.0F, bestEntity.getBbHeight());
            look = bestEntity.getLookAngle();
            if (look.lengthSqr() < 1.0E-4) {
               look = new Vec3(0.0, 0.0, 1.0);
            }

            best.lastLookX = look.x;
            best.lastLookY = look.y;
            best.lastLookZ = look.z;
            best.anchorScale = Math.max(best.anchorScale, entityScale);
         } else {
            entityScale = Math.max(1.0F, best.anchorScale > 0.0F ? best.anchorScale : 1.0F);
            look = new Vec3(best.lastLookX, best.lastLookY, best.lastLookZ);
            if (look.lengthSqr() < 1.0E-4) {
               look = new Vec3(0.0, 0.0, 1.0);
            }
         }

         Vec3 behind = look.normalize().scale(-(15.0 + entityScale * 6.0));
         Vec3 target = new Vec3(bestX + behind.x, bestY + entityScale * 1.62 + blackhole * entityScale * 1.55, bestZ + behind.z);
         Vec3 rel = target.subtract(camera.getPosition());
         Vector3f view = new Vector3f((float)rel.x, (float)rel.y, (float)rel.z);
         Quaternionf inverse = new Quaternionf(camera.rotation()).conjugate();
         view.rotate(inverse);
         float depth = -view.z;
         if (depth > 0.05F) {
            int width = Math.max(1, minecraft.getWindow().getWidth());
            int height = Math.max(1, minecraft.getWindow().getHeight());
            float aspect = (float)width / height;
            float fov = 70.0F;

            try {
               fov = ((Integer)minecraft.options.fov().get()).floatValue();
            } catch (Exception var33) {
            }

            float tan = (float)Math.tan(Math.toRadians(fov * 0.5F));
            shaderX = Mth.clamp(0.5F + view.x / depth / (tan * aspect) * 0.5F, -0.35F, 1.35F);
            shaderY = Mth.clamp(0.5F - view.y / depth / tan * 0.5F, -0.35F, 1.35F);
         }

         float sceneWide = 1.75F;
         shaderStrength = Mth.clamp(longDrain * sceneWide * (2.3F + blackhole * 1.5F + explosion * 1.25F + gold * 0.95F + finalGold * 1.15F), 0.0F, 3.85F);
         shaderVoid = Mth.clamp((1.05F + blackhole * 1.55F + explosion * 0.7F) * longDrain, 0.0F, 2.15F);
         shaderGold = Mth.clamp((gold * 1.35F + finalGold * 1.85F + explosion * 1.2F) * fadeOut, 0.0F, 2.6F);
         shaderShock = Mth.clamp(explosion * 1.95F + finalGold * 0.55F, 0.0F, 2.1F);
         uploadPostUniforms();
      }
   }

   private static void enablePostShader(int duration) {
      AsmodaySpaceClient.enablePhainon(duration);
      postActive = false;
      postTime = Math.max(postTime, duration);
   }

   private static void disablePostShader() {
      postActive = false;
      shaderStrength = 0.0F;
      shaderVoid = 0.0F;
      shaderGold = 0.0F;
      shaderShock = 0.0F;
   }

   private static void uploadPostUniforms() {
      Object effect = Minecraft.getInstance().gameRenderer.currentEffect();
      if (effect != null) {
         try {
            for (Object pass : getPasses(effect)) {
               Object shader = getShader(pass);
               setUniform2(shader, "Center", shaderX, shaderY);
               setUniform1(shader, "Strength", shaderStrength);
               setUniform1(shader, "Void", shaderVoid);
               setUniform1(shader, "Gold", shaderGold);
               setUniform1(shader, "Shock", shaderShock);
               setUniform1(shader, "Time", shaderTime);
            }
         } catch (Exception var4) {
         }
      }
   }

   private static List<?> getPasses(Object effect) throws IllegalAccessException {
      for (Field field : effect.getClass().getDeclaredFields()) {
         if (List.class.isAssignableFrom(field.getType())) {
            field.setAccessible(true);
            if (field.get(effect) instanceof List<?> list && !list.isEmpty()) {
               return list;
            }
         }
      }

      return List.of();
   }

   private static Object getShader(Object pass) throws Exception {
      for (Method method : pass.getClass().getDeclaredMethods()) {
         if (method.getParameterCount() == 0 && method.getReturnType().getSimpleName().equals("EffectInstance")) {
            method.setAccessible(true);
            return method.invoke(pass);
         }
      }

      for (Field field : pass.getClass().getDeclaredFields()) {
         if (field.getType().getSimpleName().equals("EffectInstance")) {
            field.setAccessible(true);
            return field.get(pass);
         }
      }

      return null;
   }

   private static void setUniform1(Object shader, String name, float value) throws Exception {
      if (shader != null) {
         Method method = shader.getClass().getMethod("safeGetUniform", String.class);
         if (method.invoke(shader, name) instanceof Uniform uniform) {
            uniform.set(value);
         }
      }
   }

   private static void setUniform2(Object shader, String name, float x, float y) throws Exception {
      if (shader != null) {
         Method method = shader.getClass().getMethod("safeGetUniform", String.class);
         if (method.invoke(shader, name) instanceof Uniform uniform) {
            uniform.set(x, y);
         }
      }
   }

   private static float horizontalYawToCamera(Vec3 camera, Vec3 center) {
      double dx = camera.x - center.x;
      double dz = camera.z - center.z;
      return Math.abs(dx) < 1.0E-5 && Math.abs(dz) < 1.0E-5 ? 0.0F : (float)Math.atan2(-dx, dz);
   }

   private static float range(float value, float start, float end) {
      return Mth.clamp((value - start) / (end - start), 0.0F, 1.0F);
   }

   private static float smooth(float value) {
      value = Mth.clamp(value, 0.0F, 1.0F);
      return value * value * (3.0F - 2.0F * value);
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private record RibbonVertex(float x, float y, float z, float r, float g, float b, float a) {
   }

   private static final class State {
      private final int entityId;
      private final UUID uuid;
      private final UUID asmodayBlackHoleId;
      private int age;
      private boolean asmodayBlackHoleCalled;
      private boolean blackholeShrunk;
      private boolean blackholeAnchored;
      private boolean hasWorldPosition;
      private double lastX;
      private double lastY;
      private double lastZ;
      private double lastLookX = 0.0;
      private double lastLookY = 0.0;
      private double lastLookZ = 1.0;
      private double holeX;
      private double holeY;
      private double holeZ;
      private float groundY;
      private float anchorScale = 1.0F;

      private State(int entityId, UUID uuid) {
         this.entityId = entityId;
         this.uuid = uuid;
         this.asmodayBlackHoleId = new UUID(uuid.getMostSignificantBits() ^ 5784945504459640396L, uuid.getLeastSignificantBits() ^ 4707191038375188812L);
      }
   }

   private record YDiskVertex(float x, float y, float z, float a) {
      private PhainonAcquirementVfxClient.RibbonVertex toRibbon() {
         return new PhainonAcquirementVfxClient.RibbonVertex(this.x, this.y, this.z, 1.0F, 0.0F, 0.0F, this.a);
      }
   }
}
