package com.github.wal_bos.moreskills.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public final class PainPlanetaryDevastationClient {
   private static final Map<Long, PainPlanetaryDevastationClient.PlanetVisual> PLANETS = new HashMap<>();
   private static final Map<Long, PainPlanetaryDevastationClient.DebrisVisual> DEBRIS = new HashMap<>();
   private static boolean initialized;

   private PainPlanetaryDevastationClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(PainPlanetaryDevastationClient::render);
      }
   }

   public static void planet(long seed, double x, double y, double z, float radius, float power) {
      init();
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null) {
         double now = minecraft.level.getGameTime();
         boolean realBlocks = power >= 10.0F;
         float phase = realBlocks ? power - 10.0F : power;
         PainPlanetaryDevastationClient.PlanetVisual visual = PLANETS.get(seed);
         if (visual == null) {
            visual = new PainPlanetaryDevastationClient.PlanetVisual(seed, new Vec3(x, y, z), Math.max(8.0F, radius), realBlocks, now);
            PLANETS.put(seed, visual);
         }

         visual.update(new Vec3(x, y, z), Math.max(8.0F, radius), phase, realBlocks, now);
      }
   }

   public static void stop(long seed) {
      PainPlanetaryDevastationClient.PlanetVisual visual = PLANETS.remove(seed);
      if (visual != null) {
         visual.explode();
      }
   }

   public static void push(long seed, double x, double y, double z, float radius, float power) {
      init();
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null) {
         DEBRIS.put(
            seed,
            new PainPlanetaryDevastationClient.DebrisVisual(
               seed, new Vec3(x, y, z), Math.max(8.0F, radius), Math.max(0.1F, power), true, minecraft.level.getGameTime()
            )
         );

         while (DEBRIS.size() > 10) {
            DEBRIS.remove(DEBRIS.keySet().iterator().next());
         }
      }
   }

   public static void pull(long seed, double x, double y, double z, float radius, float power) {
      init();
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null) {
         DEBRIS.put(
            seed,
            new PainPlanetaryDevastationClient.DebrisVisual(
               seed, new Vec3(x, y, z), Math.max(8.0F, radius), Math.max(0.1F, power), false, minecraft.level.getGameTime()
            )
         );

         while (DEBRIS.size() > 10) {
            DEBRIS.remove(DEBRIS.keySet().iterator().next());
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level == null) {
            PLANETS.clear();
            DEBRIS.clear();
         } else {
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            double time = (float)minecraft.level.getGameTime() + partial;
            Vec3 camera = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            BufferSource buffer = minecraft.renderBuffers().bufferSource();
            Iterator<Entry<Long, PainPlanetaryDevastationClient.PlanetVisual>> planets = PLANETS.entrySet().iterator();

            while (planets.hasNext()) {
               PainPlanetaryDevastationClient.PlanetVisual visual = planets.next().getValue();
               if (visual.dead(time)) {
                  planets.remove();
               } else {
                  visual.render(minecraft, poseStack, buffer, camera, time);
               }
            }

            Iterator<Entry<Long, PainPlanetaryDevastationClient.DebrisVisual>> debris = DEBRIS.entrySet().iterator();

            while (debris.hasNext()) {
               PainPlanetaryDevastationClient.DebrisVisual visual = debris.next().getValue();
               if (visual.dead(time)) {
                  debris.remove();
               } else {
                  visual.render(minecraft, poseStack, buffer, camera, time);
               }
            }

            buffer.endBatch();
         }
      }
   }

   private static void renderBlock(
      Minecraft minecraft,
      PoseStack poseStack,
      BufferSource buffer,
      Vec3 camera,
      BlockState state,
      double x,
      double y,
      double z,
      float yaw,
      float pitch,
      float roll,
      float scale
   ) {
      poseStack.pushPose();
      poseStack.translate(x - camera.x, y - camera.y, z - camera.z);
      poseStack.mulPose(new Quaternionf().rotateY(yaw).rotateX(pitch).rotateZ(roll));
      poseStack.scale(scale, scale, scale);
      poseStack.translate(-0.5, -0.5, -0.5);
      minecraft.getBlockRenderer().renderSingleBlock(state, poseStack, buffer, 15728880, OverlayTexture.NO_OVERLAY);
      poseStack.popPose();
   }

   private static void renderBlackSphere(PoseStack poseStack, Vec3 camera, Vec3 center, float radius, float alpha) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      poseStack.pushPose();
      poseStack.translate(center.x - camera.x, center.y - camera.y, center.z - camera.z);
      Matrix4f matrix = poseStack.last().pose();
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
      int rings = 18;
      int slices = 36;

      for (int y = 0; y < rings; y++) {
         double v0 = (-Math.PI / 2) + Math.PI * y / rings;
         double v1 = (-Math.PI / 2) + Math.PI * (y + 1) / rings;

         for (int x = 0; x < slices; x++) {
            double u0 = (Math.PI * 2) * x / slices;
            double u1 = (Math.PI * 2) * (x + 1) / slices;
            vertex(buffer, matrix, radius, u0, v0, alpha);
            vertex(buffer, matrix, radius, u1, v0, alpha);
            vertex(buffer, matrix, radius, u1, v1, alpha);
            vertex(buffer, matrix, radius, u0, v0, alpha);
            vertex(buffer, matrix, radius, u1, v1, alpha);
            vertex(buffer, matrix, radius, u0, v1, alpha);
         }
      }

      BufferUploader.drawWithShader(buffer.buildOrThrow());
      poseStack.popPose();
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   private static void vertex(BufferBuilder buffer, Matrix4f matrix, float radius, double u, double v, float alpha) {
      float x = (float)(Math.cos(v) * Math.cos(u)) * radius;
      float y = (float)Math.sin(v) * radius;
      float z = (float)(Math.cos(v) * Math.sin(u)) * radius;
      buffer.addVertex(matrix, x, y, z).setColor(0, 0, 0, Mth.clamp((int)(alpha * 255.0F), 0, 255));
   }

   private static Vec3 sphereOffset(long seed, int index, float radius) {
      double u = hash(seed, index + 13000) * 2.0 - 1.0;
      double a = hash(seed, index + 14000) * (float) (Math.PI * 2);
      double d = Math.sqrt(Math.max(0.0, 1.0 - u * u));
      double fill = Math.cbrt(0.05 + hash(seed, index + 15000) * 0.95);
      double r = radius * fill;
      return new Vec3(Math.cos(a) * d * r, u * r, Math.sin(a) * d * r);
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

   private static final class DebrisVisual {
      private static final int COUNT = 72;
      private final long seed;
      private final Vec3 origin;
      private final float radius;
      private final float power;
      private final boolean push;
      private final double startTime;
      private final BlockState[] states = new BlockState[72];
      private final Vec3[] starts = new Vec3[72];
      private final Vec3[] velocities = new Vec3[72];
      private final float[] size = new float[72];

      DebrisVisual(long seed, Vec3 origin, float radius, float power, boolean push, double startTime) {
         this.seed = seed;
         this.origin = origin;
         this.radius = radius;
         this.power = power;
         this.push = push;
         this.startTime = startTime;
         Minecraft minecraft = Minecraft.getInstance();

         for (int i = 0; i < 72; i++) {
            double angle = PainPlanetaryDevastationClient.hash(seed, i) * (float) (Math.PI * 2);
            double dist = 2.5 + PainPlanetaryDevastationClient.hash(seed, i + 100) * radius;
            double height = -1.4 + PainPlanetaryDevastationClient.hash(seed, i + 200) * 4.8;
            Vec3 start = new Vec3(origin.x + Math.cos(angle) * dist, origin.y + height, origin.z + Math.sin(angle) * dist);
            Vec3 dir = push ? start.subtract(origin) : origin.subtract(start);
            if (dir.lengthSqr() < 0.01) {
               dir = new Vec3(Math.cos(angle), 0.35, Math.sin(angle));
            }

            this.starts[i] = start;
            this.velocities[i] = dir.normalize()
               .scale((push ? 0.55 : 0.78) + PainPlanetaryDevastationClient.hash(seed, i + 300) * 0.8 + power * 0.018)
               .add(0.0, push ? 0.18 + PainPlanetaryDevastationClient.hash(seed, i + 400) * 0.42 : 0.1, 0.0);
            this.size[i] = 0.72F + PainPlanetaryDevastationClient.hash(seed, i + 500) * 0.24F;
            BlockState state = Blocks.DIRT.defaultBlockState();
            if (minecraft.level != null) {
               BlockPos sample = BlockPos.containing(start);

               for (int dy = 4; dy >= -8; dy--) {
                  BlockState candidate = minecraft.level.getBlockState(sample.offset(0, dy, 0));
                  if (!candidate.isAir()) {
                     state = candidate;
                     break;
                  }
               }
            }

            this.states[i] = state;
         }
      }

      boolean dead(double time) {
         return time - this.startTime > 95.0;
      }

      void render(Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double time) {
         double age = Math.max(0.0, time - this.startTime);
         double fade = Mth.clamp(age / 95.0, 0.0, 1.0);

         for (int i = 0; i < 72; i++) {
            Vec3 v = this.velocities[i];
            Vec3 p = this.starts[i].add(v.scale(age));
            p = p.add(0.0, -0.018 * age * age, 0.0);
            if (!this.push) {
               double brake = Mth.clamp(age / 40.0, 0.0, 1.0);
               p = p.lerp(this.origin, brake * brake * 0.72);
            }

            float scale = this.size[i] * (float)(1.0 - fade * 0.22);
            float yaw = (float)(PainPlanetaryDevastationClient.hash(this.seed, i + 600) * (float) (Math.PI * 2) + age * 0.065);
            PainPlanetaryDevastationClient.renderBlock(
               minecraft, poseStack, buffer, camera, this.states[i], p.x, p.y, p.z, yaw, (float)(age * 0.045 + i), -yaw * 0.35F, scale
            );
         }
      }
   }

   private static final class PlanetVisual {
      private static final int CHUNKS = 900;
      private final long seed;
      private final double startTime;
      private final BlockState[] states = new BlockState[900];
      private final Vec3[] starts = new Vec3[900];
      private final Vec3[] offsets = new Vec3[900];
      private final float[] size = new float[900];
      private Vec3 previous;
      private Vec3 target;
      private double previousTime;
      private double targetTime;
      private double touched;
      private float radius;
      private float phase;
      private boolean realBlocks;
      private boolean exploding;
      private double explodeTime;

      PlanetVisual(long seed, Vec3 pos, float radius, boolean realBlocks, double now) {
         this.seed = seed;
         this.previous = pos;
         this.target = pos;
         this.previousTime = now;
         this.targetTime = now + 1.0;
         this.touched = now;
         this.startTime = now;
         this.radius = radius;
         this.realBlocks = realBlocks;
         Minecraft minecraft = Minecraft.getInstance();

         for (int i = 0; i < 900; i++) {
            double angle = PainPlanetaryDevastationClient.hash(seed, i) * (float) (Math.PI * 2);
            double dist = 18.0 + PainPlanetaryDevastationClient.hash(seed, i + 1000) * radius * 5.2;
            double y = -22.0 + PainPlanetaryDevastationClient.hash(seed, i + 2000) * 42.0;
            this.starts[i] = new Vec3(pos.x + Math.cos(angle) * dist, pos.y + y, pos.z + Math.sin(angle) * dist);
            this.offsets[i] = PainPlanetaryDevastationClient.sphereOffset(seed, i, radius * (realBlocks ? 0.76F : 0.62F));
            this.size[i] = 0.72F + PainPlanetaryDevastationClient.hash(seed, i + 4000) * 0.24F;
            BlockState state = Blocks.DIRT.defaultBlockState();
            if (minecraft.level != null) {
               BlockPos sample = BlockPos.containing(this.starts[i]);

               for (int dy = 6; dy >= -10; dy--) {
                  BlockState candidate = minecraft.level.getBlockState(sample.offset(0, dy, 0));
                  if (!candidate.isAir()) {
                     state = candidate;
                     break;
                  }
               }
            }

            this.states[i] = state;
         }
      }

      void update(Vec3 pos, float radius, float phase, boolean realBlocks, double now) {
         Vec3 current = this.current(now);
         this.previous = current;
         this.target = pos;
         this.previousTime = now;
         this.targetTime = now + 2.0;
         this.touched = now;
         this.radius = Math.max(this.radius * 0.98F, radius);
         this.phase = phase;
         this.realBlocks = realBlocks;
      }

      void explode() {
         Minecraft minecraft = Minecraft.getInstance();
         this.exploding = true;
         this.explodeTime = minecraft.level == null ? this.touched : minecraft.level.getGameTime();
      }

      boolean dead(double time) {
         return !this.exploding && time - this.touched > 1200.0 ? true : this.exploding && time - this.explodeTime > 70.0;
      }

      Vec3 current(double time) {
         double denom = Math.max(1.0, this.targetTime - this.previousTime);
         double t = Mth.clamp((time - this.previousTime) / denom, 0.0, 1.0);
         double s = t * t * (3.0 - 2.0 * t);
         return this.previous.lerp(this.target, s);
      }

      void render(Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double time) {
         Vec3 center = this.current(time);
         float age = (float)(time - this.startTime);
         float grow = Mth.clamp(age / 24.0F, 0.0F, 1.0F);
         float r = this.radius * (0.35F + grow * 0.65F);
         PainPlanetaryDevastationClient.renderBlackSphere(poseStack, camera, center, 0.62F, 0.98F);
         if (this.exploding) {
            this.renderExplosion(minecraft, poseStack, buffer, camera, center, time);
         } else {
            this.renderSuckedBlocks(minecraft, poseStack, buffer, camera, center, time, r);
         }
      }

      void renderSuckedBlocks(Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, Vec3 center, double time, float r) {
         float age = (float)(time - this.startTime);
         float suck = Mth.clamp(age / (this.realBlocks ? 86.0F : 72.0F), 0.0F, 1.0F);
         suck = suck * suck * (3.0F - 2.0F * suck);

         for (int i = 0; i < 900; i++) {
            float delay = PainPlanetaryDevastationClient.hash(this.seed, i + 5000) * (this.realBlocks ? 0.16F : 0.18F);
            float local = Mth.clamp((suck - delay) / Math.max(0.05F, 1.0F - delay), 0.0F, 1.0F);
            local = local * local * (3.0F - 2.0F * local);
            if (!this.realBlocks || !(local > 0.94F)) {
               Vec3 end = center.add(this.offsets[i]);
               Vec3 start = this.starts[i];
               Vec3 p = start.lerp(end, local);
               double lift = Math.sin(local * Math.PI)
                  * (
                     this.realBlocks
                        ? 10.0 + PainPlanetaryDevastationClient.hash(this.seed, i + 6000) * 16.0
                        : 7.0 + PainPlanetaryDevastationClient.hash(this.seed, i + 6000) * 10.0
                  );
               float yaw = PainPlanetaryDevastationClient.hash(this.seed, i + 7000) * (float) (Math.PI * 2) + age * 0.018F;
               float pitch = PainPlanetaryDevastationClient.hash(this.seed, i + 8000) * (float) (Math.PI * 2) + age * 0.012F;
               PainPlanetaryDevastationClient.renderBlock(
                  minecraft,
                  poseStack,
                  buffer,
                  camera,
                  this.states[i],
                  p.x,
                  p.y + lift,
                  p.z,
                  yaw,
                  pitch,
                  -yaw * 0.4F,
                  this.realBlocks ? this.size[i] * 0.92F : this.size[i]
               );
            }
         }
      }

      void renderExplosion(Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, Vec3 center, double time) {
         double t = Mth.clamp((time - this.explodeTime) / 70.0, 0.0, 1.0);
         double blast = 1.0 - Math.pow(1.0 - t, 3.0);

         for (int i = 0; i < 900; i++) {
            Vec3 off = this.offsets[i]
               .normalize()
               .scale(
                  this.radius * (0.5 + PainPlanetaryDevastationClient.hash(this.seed, i + 9000))
                     + blast * this.radius * (3.0 + PainPlanetaryDevastationClient.hash(this.seed, i + 10000) * 4.0)
               );
            double x = center.x + off.x;
            double z = center.z + off.z;
            double yy = center.y + off.y + blast * (18.0 + PainPlanetaryDevastationClient.hash(this.seed, i + 11000) * 44.0) - t * t * 55.0;
            float yaw = (float)(PainPlanetaryDevastationClient.hash(this.seed, i + 12000) * (float) (Math.PI * 2) + time * 0.08);
            PainPlanetaryDevastationClient.renderBlock(
               minecraft, poseStack, buffer, camera, this.states[i], x, yy, z, yaw, (float)time * 0.13F, -yaw, this.size[i] * (float)(1.0 - t * 0.45)
            );
         }
      }
   }
}
