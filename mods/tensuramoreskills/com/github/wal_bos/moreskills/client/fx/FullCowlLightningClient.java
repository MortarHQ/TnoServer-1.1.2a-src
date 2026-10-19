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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;

public final class FullCowlLightningClient {
   private static final Map<Integer, FullCowlLightningClient.ActiveLightningShot> ACTIVE = new ConcurrentHashMap<>();
   private static float time;

   private FullCowlLightningClient() {
   }

   public static void enable(int entityId, int duration, int percent, boolean instantLightning, boolean hundredPercentUnlocked) {
      int life = Math.max(4, duration);
      int power = Mth.clamp(percent, 1, 100);
      ACTIVE.put(entityId, new FullCowlLightningClient.ActiveLightningShot(life, power, hash(entityId * 918.37F + duration * 13.11F + percent * 71.7F)));
   }

   public static void disable(int entityId) {
      ACTIVE.remove(entityId);
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      time += 0.22F;
      if (minecraft.level == null) {
         ACTIVE.clear();
      } else {
         Iterator<Entry<Integer, FullCowlLightningClient.ActiveLightningShot>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<Integer, FullCowlLightningClient.ActiveLightningShot> entry = iterator.next();
            Entity entity = minecraft.level.getEntity(entry.getKey());
            FullCowlLightningClient.ActiveLightningShot shot = entry.getValue();
            shot.age++;
            if (!(shot.age < shot.life && entity instanceof LivingEntity living) || !living.isAlive()) {
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

            for (Entry<Integer, FullCowlLightningClient.ActiveLightningShot> entry : ACTIVE.entrySet()) {
               if (minecraft.level.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
                  renderShot(matrix, cam, living, entry.getValue(), partialTick);
               }
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void renderShot(Matrix4f matrix, Vec3 cam, LivingEntity entity, FullCowlLightningClient.ActiveLightningShot shot, float partialTick) {
      double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
      double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
      double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());
      Vec3 start = new Vec3(x, y + entity.getBbHeight() * 0.66, z);
      Vec3 look = entity.getLookAngle();
      if (look.lengthSqr() < 0.001) {
         float yaw = (float)Math.toRadians(entity.yBodyRot);
         look = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
      }

      look = look.normalize();
      float power = getVisualPower(shot.power);
      double range = 75.0 + power * 95.0;
      Vec3 end = start.add(look.scale(range));
      float lifeFade = 1.0F - (float)shot.age / Math.max(1, shot.life);
      lifeFade = Mth.clamp(lifeFade, 0.0F, 1.0F);
      float alpha = Mth.clamp(lifeFade * lifeFade * 1.35F, 0.0F, 1.0F);
      float width = 0.18F + power * 0.36F;
      renderBloom(matrix, cam, start, look, power, alpha, shot.seed);
      renderMainBolt(matrix, cam, start, end, width, alpha, shot.seed, power);
      renderBranchStorm(matrix, cam, start, end, width, alpha, shot.seed, power);
      renderImpactBloom(matrix, cam, end, look, power, alpha, shot.seed);
   }

   private static void renderBloom(Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 look, float power, float alpha, float seed) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 normalA = getNormalA(look);
      Vec3 normalB = look.cross(normalA).normalize();
      int rings = 4;
      int segments = 32;

      for (int ring = 0; ring < rings; ring++) {
         double radius = 0.8 + ring * 0.65 + power * 1.2;
         Vec3 center = start.add(look.scale(ring * 0.65));

         for (int i = 0; i < segments; i++) {
            double a0 = (double)i / segments * Math.PI * 2.0 + time * (1.8 + ring * 0.35);
            double a1 = (i + 0.65) / segments * Math.PI * 2.0 + time * (1.8 + ring * 0.35);
            Vec3 p0 = center.add(normalA.scale(Math.cos(a0) * radius)).add(normalB.scale(Math.sin(a0) * radius));
            Vec3 p1 = center.add(normalA.scale(Math.cos(a1) * radius)).add(normalB.scale(Math.sin(a1) * radius));
            drew |= addRibbon(buffer, matrix, cam, p0, p1, 0.045F + power * 0.045F, 0.18F, 0.72F, 1.0F, alpha * 0.28F);
            drew |= addRibbon(buffer, matrix, cam, p0, p1, 0.018F + power * 0.025F, 1.0F, 0.94F, 0.55F, alpha * 0.5F);
         }
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderMainBolt(Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, float width, float alpha, float seed, float power) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      drew |= addJaggedLightning(buffer, matrix, cam, start, end, 34, width * 6.8F, 0.02F, 0.1F, 1.0F, alpha * 0.22F, seed + 1.0F, 5.5 + power * 5.5);
      drew |= addJaggedLightning(buffer, matrix, cam, start, end, 30, width * 4.2F, 0.45F, 0.05F, 1.0F, alpha * 0.2F, seed + 2.0F, 4.5 + power * 4.0);
      drew |= addJaggedLightning(buffer, matrix, cam, start, end, 28, width * 2.8F, 1.0F, 0.72F, 0.08F, alpha * 0.24F, seed + 3.0F, 3.0 + power * 3.0);
      drew |= addJaggedLightning(buffer, matrix, cam, start, end, 40, width * 1.18F, 0.45F, 0.95F, 1.0F, alpha * 0.92F, seed + 4.0F, 2.0 + power * 2.0);
      drew |= addJaggedLightning(buffer, matrix, cam, start, end, 42, width * 0.46F, 1.0F, 1.0F, 0.92F, alpha, seed + 5.0F, 1.1 + power * 1.2);
      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderBranchStorm(Matrix4f matrix, Vec3 cam, Vec3 start, Vec3 end, float width, float alpha, float seed, float power) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 main = end.subtract(start);
      if (!(main.lengthSqr() < 0.001)) {
         Vec3 dir = main.normalize();
         Vec3 normalA = getNormalA(main);
         Vec3 normalB = dir.cross(normalA).normalize();
         int count = 18 + Mth.floor(power * 18.0F);

         for (int i = 0; i < count; i++) {
            float localSeed = hash(seed * 41.0F + i * 17.0F);
            float along = 0.08F + hash(localSeed * 19.0F) * 0.84F;
            Vec3 branchStart = start.lerp(end, along);
            double sideA = hash(localSeed * 23.0F) > 0.5F ? 1.0 : -1.0;
            double sideB = hash(localSeed * 29.0F) > 0.5F ? 1.0 : -1.0;
            double branchLength = 7.0 + power * 18.0 + hash(localSeed * 31.0F) * 22.0;
            Vec3 branchDir = dir.scale(0.3 + hash(localSeed * 37.0F) * 0.3)
               .add(normalA.scale(sideA * (0.55 + hash(localSeed * 43.0F) * 0.85)))
               .add(normalB.scale(sideB * (0.35 + hash(localSeed * 47.0F) * 0.75)));
            if (!(branchDir.lengthSqr() < 0.001)) {
               branchDir = branchDir.normalize();
               Vec3 branchEnd = branchStart.add(branchDir.scale(branchLength));
               float branchAlpha = alpha * (0.34F + hash(localSeed * 53.0F) * 0.4F);
               float branchWidth = width * (0.25F + hash(localSeed * 59.0F) * 0.34F);
               drew |= addJaggedLightning(
                  buffer,
                  matrix,
                  cam,
                  branchStart,
                  branchEnd,
                  8,
                  branchWidth * 2.4F,
                  0.1F,
                  0.48F,
                  1.0F,
                  branchAlpha * 0.18F,
                  localSeed + 61.0F,
                  3.0 + power * 4.0
               );
               drew |= addJaggedLightning(
                  buffer, matrix, cam, branchStart, branchEnd, 9, branchWidth, 0.72F, 0.95F, 1.0F, branchAlpha * 0.7F, localSeed + 67.0F, 1.4 + power * 2.2
               );
               drew |= addJaggedLightning(
                  buffer,
                  matrix,
                  cam,
                  branchStart,
                  branchEnd,
                  9,
                  branchWidth * 0.42F,
                  1.0F,
                  0.97F,
                  0.7F,
                  branchAlpha * 0.88F,
                  localSeed + 71.0F,
                  0.8 + power * 1.2
               );
            }
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static void renderImpactBloom(Matrix4f matrix, Vec3 cam, Vec3 end, Vec3 look, float power, float alpha, float seed) {
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      boolean drew = false;
      Vec3 normalA = getNormalA(look);
      Vec3 normalB = look.cross(normalA).normalize();
      int spikes = 26;

      for (int i = 0; i < spikes; i++) {
         float localSeed = hash(seed * 83.0F + i * 19.0F);
         double angle = (double)i / spikes * Math.PI * 2.0 + time * 2.7;
         double len = 3.5 + power * 12.0 + hash(localSeed * 13.0F) * 10.0;
         Vec3 dir = normalA.scale(Math.cos(angle)).add(normalB.scale(Math.sin(angle))).add(look.scale(0.25 + hash(localSeed * 17.0F) * 0.45)).normalize();
         Vec3 spikeEnd = end.add(dir.scale(len));
         drew |= addJaggedLightning(
            buffer, matrix, cam, end, spikeEnd, 4, 0.05F + power * 0.07F, 0.24F, 0.68F, 1.0F, alpha * 0.32F, localSeed + 91.0F, 2.0 + power * 2.5
         );
         drew |= addJaggedLightning(
            buffer, matrix, cam, end, spikeEnd, 4, 0.018F + power * 0.03F, 1.0F, 0.95F, 0.62F, alpha * 0.72F, localSeed + 97.0F, 0.9 + power * 1.2
         );
      }

      if (drew) {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static boolean addJaggedLightning(
      BufferBuilder buffer,
      Matrix4f matrix,
      Vec3 cam,
      Vec3 start,
      Vec3 end,
      int bends,
      float width,
      float r,
      float g,
      float b,
      float alpha,
      float seed,
      double chaos
   ) {
      if (!(alpha <= 0.001F) && !(width <= 0.001F)) {
         Vec3 main = end.subtract(start);
         if (main.lengthSqr() < 1.0E-6) {
            return false;
         }

         Vec3 normalA = getNormalA(main);
         Vec3 normalB = main.normalize().cross(normalA).normalize();
         Vec3 previous = start;
         boolean drew = false;

         for (int i = 1; i <= bends; i++) {
            float t = (float)i / bends;
            Vec3 point = start.lerp(end, t);
            if (i < bends) {
               float strength = Mth.sin(t * (float) Math.PI);
               double a = (hash(seed * 13.0F + i * 17.0F + time * 5.0F) - 0.5) * chaos * strength;
               double b2 = (hash(seed * 19.0F + i * 23.0F - time * 4.0F) - 0.5) * chaos * 0.82 * strength;
               point = point.add(normalA.scale(a)).add(normalB.scale(b2));
            }

            drew |= addRibbon(buffer, matrix, cam, previous, point, width * (1.0F - t * 0.12F), r, g, b, alpha * (1.0F - t * 0.1F));
            previous = point;
         }

         return drew;
      } else {
         return false;
      }
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
         buffer.addVertex(matrix, (float)b1.x, (float)b1.y, (float)b1.z).setColor(r, g, blue, alpha * 0.78F);
         buffer.addVertex(matrix, (float)b0.x, (float)b0.y, (float)b0.z).setColor(r, g, blue, alpha * 0.78F);
         return true;
      } else {
         return false;
      }
   }

   private static Vec3 getNormalA(Vec3 main) {
      Vec3 normal = main.normalize().cross(new Vec3(0.0, 1.0, 0.0));
      if (normal.lengthSqr() < 1.0E-6) {
         normal = new Vec3(1.0, 0.0, 0.0);
      }

      return normal.normalize();
   }

   private static float getVisualPower(int percent) {
      float normalized = Mth.clamp(percent / 100.0F, 0.01F, 1.0F);
      return 0.25F + Mth.sqrt(normalized) * 0.95F;
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }

   private static final class ActiveLightningShot {
      private final int life;
      private final int power;
      private final float seed;
      private int age;

      private ActiveLightningShot(int life, int power, float seed) {
         this.life = life;
         this.power = power;
         this.seed = seed;
      }
   }
}
