package com.github.wal_bos.moreskills.client.fx;

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
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class AsmodayBlackHoleClient {
   private static final Map<UUID, AsmodayBlackHoleClient.ClientBlackHole> BLACK_HOLES = new ConcurrentHashMap<>();
   private static final ResourceLocation POST_SHADER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "shaders/post/asmoday_black_hole_post.json");
   private static boolean postActive;
   private static int postTime;
   private static float shaderX = 0.5F;
   private static float shaderY = 0.5F;
   private static float shaderStrength = 0.0F;
   private static float shaderTime = 0.0F;

   private AsmodayBlackHoleClient() {
   }

   public static void add(UUID id, double x, double y, double z, float radius, int duration) {
      BLACK_HOLES.put(id, new AsmodayBlackHoleClient.ClientBlackHole(id, new Vec3(x, y, z), radius, duration));
      AsmodaySpaceClient.enable(duration);
      enablePostShader(duration);
   }

   public static void registerShaders(RegisterShadersEvent event) {
   }

   public static void onClientTick(Post event) {
      Iterator<AsmodayBlackHoleClient.ClientBlackHole> iterator = BLACK_HOLES.values().iterator();

      while (iterator.hasNext()) {
         AsmodayBlackHoleClient.ClientBlackHole hole = iterator.next();
         hole.age++;
         if (hole.age > hole.duration) {
            iterator.remove();
         }
      }

      AsmodaySpaceClient.onClientTick(event);
      shaderTime += 0.12F;
      if (postTime > 0) {
         postTime--;
      } else if (postActive && BLACK_HOLES.isEmpty()) {
         disablePostShader();
      }
   }

   public static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null && !BLACK_HOLES.isEmpty()) {
            Camera camera = minecraft.gameRenderer.getMainCamera();
            Vec3 cam = camera.getPosition();
            PoseStack poseStack = event.getPoseStack();
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            updatePostShaderTarget(minecraft, camera, partialTick);
            AsmodaySpaceClient.render(poseStack, camera);
            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);

            for (AsmodayBlackHoleClient.ClientBlackHole hole : BLACK_HOLES.values()) {
               renderHole(poseStack, cam, camera, hole, partialTick);
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void updatePostShaderTarget(Minecraft minecraft, Camera camera, float partialTick) {
      AsmodayBlackHoleClient.ClientBlackHole best = null;
      double bestDistance = Double.MAX_VALUE;

      for (AsmodayBlackHoleClient.ClientBlackHole hole : BLACK_HOLES.values()) {
         double distance = camera.getPosition().distanceTo(hole.center);
         if (distance < bestDistance) {
            bestDistance = distance;
            best = hole;
         }
      }

      if (best == null) {
         shaderStrength = 0.0F;
         uploadPostUniforms();
      } else {
         Vec3 rel = best.center.subtract(camera.getPosition());
         Vector3f view = new Vector3f((float)rel.x, (float)rel.y, (float)rel.z);
         Quaternionf inverse = new Quaternionf(camera.rotation()).conjugate();
         view.rotate(inverse);
         float z = -view.z;
         if (z <= 0.05F) {
            shaderStrength = 0.0F;
            uploadPostUniforms();
         } else {
            int width = Math.max(1, minecraft.getWindow().getWidth());
            int height = Math.max(1, minecraft.getWindow().getHeight());
            float aspect = (float)width / height;
            float fov = 70.0F;

            try {
               fov = ((Integer)minecraft.options.fov().get()).floatValue();
            } catch (Exception var21) {
            }

            float tan = (float)Math.tan(Math.toRadians(fov * 0.5F));
            float sx = 0.5F + view.x / z / (tan * aspect) * 0.5F;
            float sy = 0.5F - view.y / z / tan * 0.5F;
            shaderX = Mth.clamp(sx, -0.35F, 1.35F);
            shaderY = Mth.clamp(sy, -0.35F, 1.35F);
            float distance = (float)bestDistance;
            float near = Mth.clamp(1.0F - distance / (best.radius * 2.35F), 0.0F, 1.0F);
            float edge = Mth.clamp(1.0F - Math.abs(distance - best.radius) / (best.radius * 1.65F), 0.0F, 1.0F);
            float lifeFade = Math.min(1.0F, Math.max(0.0F, (best.duration - (best.age + partialTick)) / 30.0F));
            shaderStrength = Mth.clamp((near * near * 1.85F + edge * 0.46F) * lifeFade, 0.0F, 2.05F);
            uploadPostUniforms();
         }
      }
   }

   private static void renderHole(PoseStack poseStack, Vec3 cam, Camera camera, AsmodayBlackHoleClient.ClientBlackHole hole, float partialTick) {
      poseStack.pushPose();
      poseStack.translate(hole.center.x - cam.x, hole.center.y - cam.y, hole.center.z - cam.z);
      float age = hole.age + partialTick;
      float grow = Math.min(1.0F, age / 14.0F);
      float fade = Math.min(1.0F, Math.max(0.0F, (hole.duration - age) / 26.0F));
      float radius = hole.radius * grow;
      float time = shaderTime;
      float pulse = 0.93F + 0.07F * (float)Math.sin(time * 2.5F);
      float yDiskYaw = horizontalYawToCamera(camera.getPosition(), hole.center);
      poseStack.scale(radius, radius, radius);
      Matrix4f matrix = poseStack.last().pose();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      renderSpawnShell(matrix, age, fade, time);
      renderOuterGlow(matrix, time, fade);
      renderDistantWarpedDust(matrix, time, fade);
      renderAccretionVolume(matrix, time, fade, pulse, false);
      renderPhotonHalo(matrix, time, fade, pulse, false);
      renderLensedWall(matrix, time, fade, false);
      renderInnerFilaments(matrix, time, fade, false);
      RenderSystem.disableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(true);
      renderBlackSphere(matrix, 0.742F);
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.blendFuncSeparate(770, 1, 1, 0);
      renderAccretionVolume(matrix, time, fade, pulse, true);
      renderCondensedYDisk(matrix, time, fade, pulse, false, yDiskYaw);
      renderCondensedYDisk(matrix, time, fade, pulse, true, yDiskYaw);
      renderPhotonHalo(matrix, time, fade, pulse, true);
      renderLensedWall(matrix, time, fade, true);
      renderInnerFilaments(matrix, time, fade, true);
      renderEquatorialLightning(matrix, time, fade);
      renderPolarSpewer(matrix, time, fade);
      renderOuterRedDust(matrix, time, fade);
      poseStack.popPose();
   }

   private static float horizontalYawToCamera(Vec3 camera, Vec3 center) {
      double dx = camera.x - center.x;
      double dz = camera.z - center.z;
      return Math.abs(dx) < 1.0E-5 && Math.abs(dz) < 1.0E-5 ? 0.0F : (float)Math.atan2(-dx, dz);
   }

   private static void renderSpawnShell(Matrix4f matrix, float age, float fade, float time) {
      if (!(age > 24.0F)) {
         float t = Mth.clamp(age / 24.0F, 0.0F, 1.0F);
         float alpha = (1.0F - t) * 0.18F * fade;
         float radius = 0.9F + t * 2.05F;
         renderGlowSphere(matrix, radius, 1.0F, 0.72F, 0.24F, alpha, 12, 24, time * 0.14F);
      }
   }

   private static void renderOuterGlow(Matrix4f matrix, float time, float fade) {
      renderGlowSphere(matrix, 2.08F, 1.0F, 0.12F, 0.018F, 0.02F * fade, 10, 28, time * 0.02F);
      renderGlowSphere(matrix, 1.58F, 1.0F, 0.22F, 0.035F, 0.032F * fade, 10, 28, -time * 0.04F);
      renderGlowSphere(matrix, 1.14F, 1.0F, 0.72F, 0.18F, 0.055F * fade, 12, 32, time * 0.07F);
      renderGlowSphere(matrix, 0.805F, 1.0F, 0.95F, 0.52F, 0.105F * fade, 10, 36, -time * 0.16F);
   }

   private static void renderBlackSphere(Matrix4f matrix, float radius) {
      int stacks = 28;
      int slices = 56;
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

      for (int stack = 0; stack < stacks; stack++) {
         float v0 = (float)stack / stacks;
         float v1 = (float)(stack + 1) / stacks;
         double phi0 = (-Math.PI / 2) + Math.PI * v0;
         double phi1 = (-Math.PI / 2) + Math.PI * v1;

         for (int slice = 0; slice < slices; slice++) {
            float u0 = (float)slice / slices;
            float u1 = (float)(slice + 1) / slices;
            double theta0 = (Math.PI * 2) * u0;
            double theta1 = (Math.PI * 2) * u1;
            addBlackSphereVertex(buffer, matrix, radius, theta0, phi0);
            addBlackSphereVertex(buffer, matrix, radius, theta0, phi1);
            addBlackSphereVertex(buffer, matrix, radius, theta1, phi1);
            addBlackSphereVertex(buffer, matrix, radius, theta1, phi0);
         }
      }

      BufferUploader.drawWithShader(buffer.buildOrThrow());
   }

   private static void addBlackSphereVertex(BufferBuilder buffer, Matrix4f matrix, float radius, double theta, double phi) {
      float x = (float)(Math.cos(phi) * Math.cos(theta)) * radius;
      float y = (float)Math.sin(phi) * radius;
      float z = (float)(Math.cos(phi) * Math.sin(theta)) * radius;
      buffer.addVertex(matrix, x, y, z).setColor(0.0F, 0.0F, 0.0F, 1.0F);
   }

   private static void renderGlowSphere(Matrix4f matrix, float radius, float red, float green, float blue, float alpha, int stacks, int slices, float rotation) {
      if (!(alpha <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

         for (int stack = 0; stack < stacks; stack++) {
            float v0 = (float)stack / stacks;
            float v1 = (float)(stack + 1) / stacks;
            double phi0 = (-Math.PI / 2) + Math.PI * v0;
            double phi1 = (-Math.PI / 2) + Math.PI * v1;

            for (int slice = 0; slice < slices; slice++) {
               float u0 = (float)slice / slices;
               float u1 = (float)(slice + 1) / slices;
               double theta0 = (Math.PI * 2) * u0 + rotation;
               double theta1 = (Math.PI * 2) * u1 + rotation;
               addGlowSphereVertex(buffer, matrix, radius, theta0, phi0, red, green, blue, alpha, rotation);
               addGlowSphereVertex(buffer, matrix, radius, theta0, phi1, red, green, blue, alpha, rotation);
               addGlowSphereVertex(buffer, matrix, radius, theta1, phi1, red, green, blue, alpha, rotation);
               addGlowSphereVertex(buffer, matrix, radius, theta1, phi0, red, green, blue, alpha, rotation);
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void addGlowSphereVertex(
      BufferBuilder buffer, Matrix4f matrix, float radius, double theta, double phi, float red, float green, float blue, float alpha, float rotation
   ) {
      float x = (float)(Math.cos(phi) * Math.cos(theta)) * radius;
      float y = (float)Math.sin(phi) * radius;
      float z = (float)(Math.cos(phi) * Math.sin(theta)) * radius;
      float rim = Mth.clamp((float)Math.sqrt(x * x + z * z) / radius, 0.0F, 1.0F);
      float wave = 0.62F + 0.38F * (float)Math.sin(theta * 11.0 + phi * 6.0 + rotation * 8.0F);
      float a = alpha * rim * rim * wave;
      buffer.addVertex(matrix, x, y, z).setColor(red, green, blue, a);
   }

   private static void renderAccretionVolume(Matrix4f matrix, float time, float fade, float pulse, boolean frontPass) {
      if (!(fade <= 5.0E-4F) && !(pulse <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         int baseRings = frontPass ? 38 : 15;

         for (int i = 0; i < baseRings; i++) {
            float p = i / Math.max(1.0F, baseRings - 1.0F);
            float close = 1.0F - p;
            float inner = Mth.lerp(p, 0.755F, frontPass ? 1.72F : 1.18F);
            float outer = Mth.lerp(p, 0.94F, frontPass ? 7.8F : 3.25F);
            float width = 0.01F + p * (frontPass ? 0.022F : 0.006F);
            float height = 0.006F + p * (frontPass ? 0.04F : 0.01F);
            float innerBoost = 1.0F + close * close * close * (frontPass ? 8.8F : 3.4F);
            float alpha = fade * pulse * (frontPass ? 0.26F : 0.058F) * innerBoost * (1.0F - p * 0.7F);
            float flow = time * Mth.lerp(p, 4.8F, 0.85F) + i * 0.438F;
            float phase = i * 1.873F;
            float colorMix = p;
            float tilt = (float)Math.sin(i * 2.17F) * (frontPass ? 0.0015F + p * 0.004F : 4.0E-4F + p * 0.001F);
            drew |= addRibbonLayer(buffer, matrix, inner, outer, width, height, alpha, flow, phase, colorMix, tilt, frontPass, frontPass ? 72 : 40);
         }

         int hotRings = frontPass ? 22 : 8;

         for (int i = 0; i < hotRings; i++) {
            float p = i / Math.max(1.0F, hotRings - 1.0F);
            float close = 1.0F - p;
            float inner = Mth.lerp(p, 0.745F, frontPass ? 0.92F : 0.835F);
            float outer = Mth.lerp(p, 0.805F, frontPass ? 2.65F : 1.32F);
            float width = 0.006F + p * (frontPass ? 0.014F : 0.004F);
            float height = 0.003F + p * (frontPass ? 0.024F : 0.006F);
            float innerBoost = 1.0F + close * close * close * (frontPass ? 13.5F : 4.8F);
            float alpha = fade * pulse * (frontPass ? 0.65F : 0.135F) * innerBoost * (1.0F - p * 0.68F);
            float flow = time * Mth.lerp(p, 7.2F, 2.05F) + i * 0.911F;
            float phase = i * 2.491F;
            float tilt = (float)Math.sin(i * 1.41F) * (frontPass ? 0.001F + p * 0.0025F : 3.0E-4F + p * 8.0E-4F);
            drew |= addRibbonLayer(buffer, matrix, inner, outer, width, height, alpha, flow, phase, p * 0.42F, tilt, frontPass, frontPass ? 66 : 36);
         }

         int plasmaRings = frontPass ? 14 : 5;

         for (int i = 0; i < plasmaRings; i++) {
            float p = i / Math.max(1.0F, plasmaRings - 1.0F);
            float close = 1.0F - p;
            float inner = Mth.lerp(p, 0.742F, frontPass ? 0.79F : 0.768F);
            float outer = Mth.lerp(p, 0.77F, frontPass ? 1.12F : 0.885F);
            float width = 0.003F + p * (frontPass ? 0.006F : 0.0015F);
            float height = 0.002F + p * (frontPass ? 0.012F : 0.003F);
            float innerBoost = 1.0F + close * close * close * (frontPass ? 18.0F : 6.2F);
            float alpha = fade * pulse * (frontPass ? 1.05F : 0.22F) * innerBoost * (1.0F - p * 0.45F);
            float flow = time * Mth.lerp(p, 10.5F, 4.2F) + i * 1.311F;
            float phase = i * 3.771F;
            drew |= addRibbonLayer(
               buffer, matrix, inner, outer, width, height, alpha, flow, phase, 0.0F, frontPass ? 0.001F : 3.0E-4F, frontPass, frontPass ? 60 : 32
            );
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
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
      if (!(alpha <= 5.0E-4F) && segments > 0) {
         int radialCuts = frontPass ? 5 : 4;
         boolean drew = false;

         for (int r = 0; r < radialCuts; r++) {
            float r0 = (float)r / radialCuts;
            float r1 = (float)(r + 1) / radialCuts;
            float radiusA = Mth.lerp(r0, inner, outer);
            float radiusB = Mth.lerp(r1, inner, outer);
            float hA = -1.0F;
            float hB = 1.0F;

            for (int s = 0; s < segments; s++) {
               float u0 = (float)s / segments;
               float u1 = (float)(s + 1) / segments;
               float a0 = u0 * (float) (Math.PI * 2) + phase;
               float a1 = u1 * (float) (Math.PI * 2) + phase;
               AsmodayBlackHoleClient.RibbonVertex v00 = ribbonVertex(radiusA, hA, u0, a0, width, height, flow, phase, colorMix, tilt, frontPass);
               AsmodayBlackHoleClient.RibbonVertex v01 = ribbonVertex(radiusB, hA, u0, a0, width, height, flow, phase, colorMix, tilt, frontPass);
               AsmodayBlackHoleClient.RibbonVertex v11 = ribbonVertex(radiusB, hB, u1, a1, width, height, flow, phase, colorMix, tilt, frontPass);
               AsmodayBlackHoleClient.RibbonVertex v10 = ribbonVertex(radiusA, hB, u1, a1, width, height, flow, phase, colorMix, tilt, frontPass);
               float outerScale = frontPass ? 0.42F : 0.14F;
               buffer.addVertex(matrix, v00.x, v00.y, v00.z).setColor(v00.r, v00.g, v00.b, v00.a * alpha);
               buffer.addVertex(matrix, v01.x, v01.y, v01.z).setColor(v01.r, v01.g, v01.b, v01.a * alpha * outerScale);
               buffer.addVertex(matrix, v11.x, v11.y, v11.z).setColor(v11.r, v11.g, v11.b, v11.a * alpha * outerScale);
               buffer.addVertex(matrix, v10.x, v10.y, v10.z).setColor(v10.r, v10.g, v10.b, v10.a * alpha);
               drew = true;
            }
         }

         return drew;
      } else {
         return false;
      }
   }

   private static AsmodayBlackHoleClient.RibbonVertex ribbonVertex(
      float radius, float h, float u, float angle, float width, float height, float flow, float phase, float colorMix, float tilt, boolean frontPass
   ) {
      float orbital = angle + flow / Math.max(0.68F, radius * 0.6F);
      float spiral = orbital + radius * 0.26F;
      float c = (float)Math.cos(spiral);
      float s = (float)Math.sin(spiral);
      float backThin = frontPass ? 1.0F : Mth.lerp(Mth.clamp(Math.max(0.0F, -s), 0.0F, 1.0F), 0.72F, 0.38F);
      float compression = Mth.clamp((radius - 0.7F) / 4.5F, 0.0F, 1.0F);
      float close = Mth.clamp(1.0F - (radius - 0.742F) / 0.48F, 0.0F, 1.0F);
      float lens = 1.0F - Mth.clamp((radius - 0.7F) / 0.92F, 0.0F, 1.0F);
      float verticalLift = lens * lens * (0.075F + 0.18F * Math.max(0.0F, s)) * (frontPass ? 0.48F : 0.12F);
      float lowerFold = lens * lens * (0.025F + 0.055F * Math.max(0.0F, -s)) * (frontPass ? 0.26F : 0.08F);
      float turbulence = (float)Math.sin(u * 82.0F + flow * 4.7F + phase) * 0.5F + (float)Math.sin(u * 191.0F - flow * 3.0F) * 0.5F;
      float radialNoise = turbulence * width * (0.13F + compression) * backThin;
      float yNoise = (float)Math.sin(u * 42.0F + flow * 1.65F + radius) * height * 0.055F * (frontPass ? 1.0F : 0.16F);
      float r = radius * backThin + radialNoise;
      float y = h * height * (0.075F + compression * (frontPass ? 0.26F : 0.075F)) + yNoise + verticalLift - lowerFold + tilt * radius * c;
      float x = c * r;
      float z = s * r;
      float horizonMask = eventHorizonMask(x, y, z, frontPass);
      float hot = Mth.clamp(1.0F - (radius - 0.742F) / 0.72F, 0.0F, 1.0F);
      float streak = 0.54F + 0.46F * (float)Math.sin(u * 390.0F + flow * 7.3F + phase);
      float threads = 0.6F + 0.4F * (float)Math.sin(u * 108.0F - flow * 3.8F + radius * 4.0F);
      float side = frontPass ? 0.52F + 0.42F * Math.max(0.0F, s) : 0.1F + 0.2F * Math.max(0.0F, -s);
      float edge = Mth.clamp(1.0F - Math.abs(h) * 0.44F, 0.0F, 1.0F);
      float innerPower = 1.0F + close * close * close * (frontPass ? 10.5F : 3.5F);
      float glow = (0.18F + hot * 0.82F) * (0.28F + streak * 0.72F) * threads * side * edge * innerPower;
      float red = 1.0F;
      float green = Mth.clamp(Mth.lerp(colorMix, 1.0F, 0.11F), 0.0F, 1.0F);
      float blue = Mth.clamp(Mth.lerp(colorMix, 0.82F + hot * 0.18F, 0.018F), 0.0F, 1.0F);
      float alpha = glow * (1.0F - compression * 0.36F) * horizonMask;
      return new AsmodayBlackHoleClient.RibbonVertex(x, y, z, red, green, blue, alpha);
   }

   private static void renderCondensedYDisk(Matrix4f matrix, float time, float fade, float pulse, boolean frontPass, float yaw) {
      if (!(fade <= 5.0E-4F) && !(pulse <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         boolean drew = false;
         int rings = frontPass ? 30 : 14;

         for (int i = 0; i < rings; i++) {
            float p = i / Math.max(1.0F, rings - 1.0F);
            float inner = Mth.lerp(p, 0.754F, frontPass ? 0.798F : 0.785F);
            float outer = Mth.lerp(p, 0.798F, frontPass ? 1.015F : 0.925F);
            float tube = Mth.lerp(p, 0.007F, frontPass ? 0.026F : 0.016F);
            float depth = Mth.lerp(p, 0.016F, frontPass ? 0.07F : 0.034F);
            float alpha = fade * pulse * (frontPass ? 2.45F : 0.82F) * (1.0F - p * 0.64F);
            float flow = time * Mth.lerp(p, 6.2F, 1.8F) + i * 0.721F;
            float phase = i * 1.917F;
            drew |= addCondensedYDiskTube(buffer, matrix, inner, outer, tube, depth, alpha, flow, phase, frontPass, yaw);
         }

         int hotRings = frontPass ? 16 : 7;

         for (int i = 0; i < hotRings; i++) {
            float p = i / Math.max(1.0F, hotRings - 1.0F);
            float inner = Mth.lerp(p, 0.748F, frontPass ? 0.772F : 0.764F);
            float outer = Mth.lerp(p, 0.772F, frontPass ? 0.87F : 0.82F);
            float tube = Mth.lerp(p, 0.005F, frontPass ? 0.017F : 0.01F);
            float depth = Mth.lerp(p, 0.012F, frontPass ? 0.05F : 0.024F);
            float alpha = fade * pulse * (frontPass ? 3.35F : 1.12F) * (1.0F - p * 0.54F);
            float flow = time * Mth.lerp(p, 9.8F, 4.2F) + i * 1.337F;
            float phase = i * 2.411F;
            drew |= addCondensedYDiskTube(buffer, matrix, inner, outer, tube, depth, alpha, flow, phase, frontPass, yaw);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
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
      if (alpha <= 5.0E-4F) {
         return false;
      }

      int segments = frontPass ? 128 : 72;
      int bands = frontPass ? 7 : 4;
      boolean drew = false;

      for (int band = 0; band < bands; band++) {
         float b0 = (float)band / bands;
         float b1 = (float)(band + 1) / bands;

         for (int i = 0; i < segments; i++) {
            float u0 = (float)i / segments;
            float u1 = (float)(i + 1) / segments;
            AsmodayBlackHoleClient.YDiskVertex v00 = yDiskVertex(inner, tube, depth, b0, u0, flow, phase, alpha, frontPass, yaw);
            AsmodayBlackHoleClient.YDiskVertex v01 = yDiskVertex(outer, tube, depth, b0, u0, flow, phase, alpha * 0.22F, frontPass, yaw);
            AsmodayBlackHoleClient.YDiskVertex v11 = yDiskVertex(outer, tube, depth, b1, u1, flow, phase, alpha * 0.22F, frontPass, yaw);
            AsmodayBlackHoleClient.YDiskVertex v10 = yDiskVertex(inner, tube, depth, b1, u1, flow, phase, alpha, frontPass, yaw);
            buffer.addVertex(matrix, v00.x, v00.y, v00.z).setColor(v00.r, v00.g, v00.b, v00.a);
            buffer.addVertex(matrix, v01.x, v01.y, v01.z).setColor(v01.r, v01.g, v01.b, v01.a);
            buffer.addVertex(matrix, v11.x, v11.y, v11.z).setColor(v11.r, v11.g, v11.b, v11.a);
            buffer.addVertex(matrix, v10.x, v10.y, v10.z).setColor(v10.r, v10.g, v10.b, v10.a);
            drew = true;
         }
      }

      return drew;
   }

   private static AsmodayBlackHoleClient.YDiskVertex yDiskVertex(
      float radius, float tube, float depth, float band, float u, float flow, float phase, float alpha, boolean frontPass, float yaw
   ) {
      float angle = u * (float) (Math.PI * 2) + flow + phase;
      float c = (float)Math.cos(angle);
      float s = (float)Math.sin(angle);
      float cross = (band - 0.5F) * 2.0F;
      float twist = (float)Math.sin(u * 24.0F + flow * 2.0F + phase) * 0.5F + 0.5F;
      float hotNoise = 0.7F + 0.3F * (float)Math.sin(u * 360.0F + flow * 7.2F + phase);
      float strandNoise = 0.6F + 0.4F * (float)Math.sin(u * 150.0F - flow * 4.4F);
      float radialPush = cross * tube * (0.58F + twist * 0.42F);
      float ringRadius = radius + radialPush;
      float localX = c * ringRadius;
      float localY = s * ringRadius;
      float localZ = cross * depth * (frontPass ? 1.0F : 0.42F);
      float yawCos = (float)Math.cos(yaw);
      float yawSin = (float)Math.sin(yaw);
      float x = localX * yawCos - localZ * yawSin;
      float y = localY;
      float z = localX * yawSin + localZ * yawCos;
      float mask = yDiskHorizonMask(localX, localY, frontPass);
      float side = frontPass ? 0.78F + 0.22F * Math.max(0.0F, -localZ) : 0.28F + 0.24F * Math.max(0.0F, localZ);
      float core = Mth.clamp(1.0F - Math.abs(cross), 0.0F, 1.0F);
      float edge = 0.38F + core * 0.62F;
      float close = Mth.clamp(1.0F - (radius - 0.748F) / 0.18F, 0.0F, 1.0F);
      float glow = alpha * side * edge * hotNoise * strandNoise * mask * (1.0F + close * close * 1.15F);
      float hot = Mth.clamp(1.0F - (radius - 0.745F) / 0.24F, 0.0F, 1.0F);
      float red = 1.0F;
      float green = Mth.clamp(0.14F + hot * 0.86F, 0.0F, 1.0F);
      float blue = Mth.clamp(0.02F + hot * 0.68F, 0.0F, 1.0F);
      return new AsmodayBlackHoleClient.YDiskVertex(x, y, z, red, green, blue, glow);
   }

   private static float yDiskHorizonMask(float localX, float localY, boolean frontPass) {
      float silhouette = (float)Math.sqrt(localX * localX + localY * localY);
      float hardHole = 0.742F;
      float softEdge = frontPass ? 0.772F : 0.79F;
      float mask = Mth.clamp((silhouette - hardHole) / Math.max(1.0E-4F, softEdge - hardHole), 0.0F, 1.0F);
      mask = mask * mask * (3.0F - 2.0F * mask);
      if (silhouette < 0.743F) {
         return 0.0F;
      }

      if (silhouette < 0.77F) {
         mask *= frontPass ? 0.68F : 0.24F;
      }

      if (!frontPass && silhouette < 0.825F) {
         mask *= 0.52F;
      }

      return mask;
   }

   private static float eventHorizonMask(float x, float y, float z, boolean frontPass) {
      float silhouette = (float)Math.sqrt(x * x + y * y);
      float hardHole = 0.79F;
      float softEdge = 0.99F;
      float mask = Mth.clamp((silhouette - hardHole) / Math.max(1.0E-4F, softEdge - hardHole), 0.0F, 1.0F);
      mask = mask * mask * (3.0F - 2.0F * mask);
      if (frontPass && silhouette < 1.06F) {
         mask *= 0.1F;
      }

      if (!frontPass && silhouette < 1.18F) {
         mask *= 0.015F;
      }

      if (Math.abs(y) < 0.018F && silhouette > 0.875F && silhouette < 1.055F) {
         mask = Math.max(mask, frontPass ? 0.18F : 0.035F);
      }

      return mask;
   }

   private static void renderPhotonHalo(Matrix4f matrix, float time, float fade, float pulse, boolean frontPass) {
      float pass = frontPass ? 1.0F : 0.18F;
      renderHaloRing(
         matrix,
         0.735F,
         frontPass ? 0.035F : 0.014F,
         1.0F,
         0.98F,
         0.8F,
         1.12F * fade * pulse * pass,
         frontPass ? 84 : 44,
         time * 2.4F,
         frontPass ? 0.016F : 0.002F
      );
      renderHaloRing(
         matrix, 0.81F, frontPass ? 0.07F : 0.022F, 1.0F, 0.44F, 0.08F, 0.5F * fade * pass, frontPass ? 76 : 38, -time * 2.0F, frontPass ? 0.04F : 0.006F
      );
      renderHaloRing(
         matrix, 0.92F, frontPass ? 0.105F : 0.03F, 1.0F, 0.18F, 0.025F, 0.19F * fade * pass, frontPass ? 68 : 34, time * 1.3F, frontPass ? 0.07F : 0.01F
      );
   }

   private static void renderHaloRing(
      Matrix4f matrix, float radius, float width, float red, float green, float blue, float alpha, int segments, float flow, float lift
   ) {
      if (!(alpha <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

         for (int i = 0; i < segments; i++) {
            float u0 = (float)i / segments;
            float u1 = (float)(i + 1) / segments;
            float a0 = u0 * (float) (Math.PI * 2) + flow;
            float a1 = u1 * (float) (Math.PI * 2) + flow;
            AsmodayBlackHoleClient.HaloVertex v00 = haloVertex(radius, 0.0F, a0, u0, lift, red, green, blue, alpha);
            AsmodayBlackHoleClient.HaloVertex v01 = haloVertex(radius + width, 0.0F, a0, u0, lift, red, green, blue, alpha * 0.1F);
            AsmodayBlackHoleClient.HaloVertex v11 = haloVertex(radius + width, 0.0F, a1, u1, lift, red, green, blue, alpha * 0.1F);
            AsmodayBlackHoleClient.HaloVertex v10 = haloVertex(radius, 0.0F, a1, u1, lift, red, green, blue, alpha);
            buffer.addVertex(matrix, v00.x, v00.y, v00.z).setColor(v00.r, v00.g, v00.b, v00.a);
            buffer.addVertex(matrix, v01.x, v01.y, v01.z).setColor(v01.r, v01.g, v01.b, v01.a);
            buffer.addVertex(matrix, v11.x, v11.y, v11.z).setColor(v11.r, v11.g, v11.b, v11.a);
            buffer.addVertex(matrix, v10.x, v10.y, v10.z).setColor(v10.r, v10.g, v10.b, v10.a);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static AsmodayBlackHoleClient.HaloVertex haloVertex(
      float radius, float h, float angle, float u, float lift, float red, float green, float blue, float alpha
   ) {
      float c = (float)Math.cos(angle);
      float s = (float)Math.sin(angle);
      float wave = 0.7F + 0.3F * (float)Math.sin(u * 140.0F + angle * 2.0F);
      float y = h + lift * Math.max(0.0F, s) * Math.max(0.0F, s);
      float x = c * radius;
      float z = s * radius;
      return new AsmodayBlackHoleClient.HaloVertex(x, y, z, red, green, blue, alpha * wave);
   }

   private static void renderLensedWall(Matrix4f matrix, float time, float fade, boolean front) {
      if (!(fade <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int bands = front ? 10 : 3;
         boolean drew = false;

         for (int i = 0; i < bands; i++) {
            float p = i / Math.max(1.0F, bands - 1.0F);
            float inner = 0.805F + p * 0.04F;
            float outer = front ? 1.78F + p * 0.24F : 1.12F + p * 0.08F;
            float width = front ? 0.012F + p * 0.008F : 0.004F + p * 0.002F;
            float alpha = fade * (front ? 0.28F : 0.032F) * (1.0F - p * 0.58F);
            float flow = time * (front ? 1.35F : 0.92F) + i * 0.68F;
            drew |= addVerticalBand(buffer, matrix, inner, outer, width, alpha, flow, front);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static boolean addVerticalBand(BufferBuilder buffer, Matrix4f matrix, float inner, float outer, float width, float alpha, float flow, boolean front) {
      if (alpha <= 5.0E-4F) {
         return false;
      }

      int segments = front ? 58 : 30;

      for (int i = 0; i < segments; i++) {
         float u0 = (float)i / segments;
         float u1 = (float)(i + 1) / segments;
         float a0 = u0 * (float) (Math.PI * 2) + flow;
         float a1 = u1 * (float) (Math.PI * 2) + flow;
         AsmodayBlackHoleClient.BandVertex v00 = verticalBandVertex(inner, -width, a0, u0, alpha, front);
         AsmodayBlackHoleClient.BandVertex v01 = verticalBandVertex(outer, -width * 0.15F, a0, u0, alpha * 0.06F, front);
         AsmodayBlackHoleClient.BandVertex v11 = verticalBandVertex(outer, width * 0.15F, a1, u1, alpha * 0.06F, front);
         AsmodayBlackHoleClient.BandVertex v10 = verticalBandVertex(inner, width, a1, u1, alpha, front);
         buffer.addVertex(matrix, v00.x, v00.y, v00.z).setColor(v00.r, v00.g, v00.b, v00.a);
         buffer.addVertex(matrix, v01.x, v01.y, v01.z).setColor(v01.r, v01.g, v01.b, v01.a);
         buffer.addVertex(matrix, v11.x, v11.y, v11.z).setColor(v11.r, v11.g, v11.b, v11.a);
         buffer.addVertex(matrix, v10.x, v10.y, v10.z).setColor(v10.r, v10.g, v10.b, v10.a);
      }

      return true;
   }

   private static AsmodayBlackHoleClient.BandVertex verticalBandVertex(float radius, float offset, float angle, float u, float alpha, boolean front) {
      float c = (float)Math.cos(angle);
      float s = (float)Math.sin(angle);
      float side = front ? Math.max(0.0F, s) : Math.max(0.0F, -s);
      float thin = front ? 1.0F : 0.42F;
      float y = c * radius * thin + offset;
      float z = s * radius * thin;
      float x = offset * (front ? 0.1F : 0.018F) + (float)Math.sin(u * 14.0F) * (front ? 0.003F : 8.0E-4F);
      float silhouetteMask = eventHorizonMask(x, y, z, front);
      float hot = Mth.clamp(1.0F - (radius - 0.72F) / 1.5F, 0.0F, 1.0F);
      float a = alpha * (0.18F + side * 0.82F) * (0.5F + 0.5F * Math.abs(c)) * silhouetteMask;
      float r = 1.0F;
      float g = Mth.clamp(0.22F + hot * 0.72F, 0.0F, 1.0F);
      float b = Mth.clamp(0.035F + hot * 0.55F, 0.0F, 1.0F);
      return new AsmodayBlackHoleClient.BandVertex(x, y, z, r, g, b, a);
   }

   private static void renderInnerFilaments(Matrix4f matrix, float time, float fade, boolean frontPass) {
      if (!(fade <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int count = frontPass ? 26 : 6;
         boolean drew = false;

         for (int i = 0; i < count; i++) {
            float seed = i * 11.137F;
            float start = 0.84F + i % 5 * 0.026F;
            float end = frontPass ? 2.1F + i % 8 * 0.24F : 1.2F + i % 4 * 0.06F;
            float angle = seed + time * (0.34F + i % 5 * 0.038F);
            float curve = frontPass ? 1.18F + i % 6 * 0.2F : 0.42F + i % 4 * 0.08F;
            float width = frontPass ? 0.005F + i % 3 * 0.002F : 0.002F;
            float alpha = fade * (frontPass ? 0.16F : 0.018F) * (0.5F + i % 5 * 0.1F);
            drew |= addCurvedFilament(buffer, matrix, start, end, angle, curve, width, alpha, frontPass);
         }

         if (drew) {
            BufferUploader.drawWithShader(buffer.buildOrThrow());
         }
      }
   }

   private static boolean addCurvedFilament(
      BufferBuilder buffer, Matrix4f matrix, float start, float end, float angle, float curve, float width, float alpha, boolean frontPass
   ) {
      if (alpha <= 5.0E-4F) {
         return false;
      }

      int segments = frontPass ? 8 : 5;

      for (int i = 0; i < segments; i++) {
         float t0 = (float)i / segments;
         float t1 = (float)(i + 1) / segments;
         float r0 = Mth.lerp(t0, start, end);
         float r1 = Mth.lerp(t1, start, end);
         float a0 = angle + t0 * curve;
         float a1 = angle + t1 * curve;
         float y0 = (float)Math.sin(t0 * (float) Math.PI) * (frontPass ? 0.024F : 0.006F) * (1.0F - t0);
         float y1 = (float)Math.sin(t1 * (float) Math.PI) * (frontPass ? 0.024F : 0.006F) * (1.0F - t1);
         float x0 = (float)Math.cos(a0) * r0;
         float z0 = (float)Math.sin(a0) * r0;
         float x1 = (float)Math.cos(a1) * r1;
         float z1 = (float)Math.sin(a1) * r1;
         float nx0 = (float)(-Math.sin(a0)) * width;
         float nz0 = (float)Math.cos(a0) * width;
         float nx1 = (float)(-Math.sin(a1)) * width;
         float nz1 = (float)Math.cos(a1) * width;
         float aStart = alpha * (1.0F - t0) * eventHorizonMask(x0, y0, z0, frontPass);
         float aEnd = alpha * (1.0F - t1) * eventHorizonMask(x1, y1, z1, frontPass);
         buffer.addVertex(matrix, x0 - nx0, y0, z0 - nz0).setColor(1.0F, 0.95F, 0.62F, aStart);
         buffer.addVertex(matrix, x0 + nx0, y0, z0 + nz0).setColor(1.0F, 0.34F, 0.04F, aStart * 0.18F);
         buffer.addVertex(matrix, x1 + nx1, y1, z1 + nz1).setColor(1.0F, 0.34F, 0.04F, aEnd * 0.18F);
         buffer.addVertex(matrix, x1 - nx1, y1, z1 - nz1).setColor(1.0F, 0.95F, 0.62F, aEnd);
      }

      return true;
   }

   private static void renderEquatorialLightning(Matrix4f matrix, float time, float fade) {
      if (!(fade <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int count = 14;

         for (int i = 0; i < count; i++) {
            float seed = i * 4.918F;
            float angle = seed + time * (0.48F + i % 4 * 0.05F);
            float radius = 0.86F + i % 10 * 0.095F;
            float length = 0.34F + i % 5 * 0.06F;
            float width = 0.004F + i % 3 * 0.0015F;
            float c = (float)Math.cos(angle);
            float s = (float)Math.sin(angle);
            float tx = -s * length;
            float tz = c * length;
            float px = -s * width;
            float pz = c * width;
            float y = (float)Math.sin(seed + time * 3.0F) * 0.018F;
            float alpha = fade * (0.08F + i % 5 * 0.026F);
            float x = c * radius;
            float z = s * radius;
            float mask = eventHorizonMask(x, y, z, true);
            buffer.addVertex(matrix, x - tx - px, y, z - tz - pz).setColor(1.0F, 0.3F, 0.03F, 0.0F);
            buffer.addVertex(matrix, x - px, y, z - pz).setColor(1.0F, 0.98F, 0.72F, alpha * mask);
            buffer.addVertex(matrix, x + px, y, z + pz).setColor(1.0F, 0.98F, 0.72F, alpha * mask);
            buffer.addVertex(matrix, x + tx + px, y, z + tz + pz).setColor(1.0F, 0.3F, 0.03F, 0.0F);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderDistantWarpedDust(Matrix4f matrix, float time, float fade) {
      if (!(fade <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int count = 20;

         for (int i = 0; i < count; i++) {
            float seed = i * 6.193F;
            float angle = seed + time * 0.055F;
            float radius = 4.2F + i % 10 * 0.72F;
            float y = (float)Math.sin(seed + time * 0.35F) * 0.07F;
            float length = 0.1F + i % 5 * 0.028F;
            float width = 0.004F;
            float c = (float)Math.cos(angle);
            float s = (float)Math.sin(angle);
            float x = c * radius;
            float z = s * radius;
            float tx = -s * length;
            float tz = c * length;
            float alpha = fade * (0.018F + i % 4 * 0.004F);
            buffer.addVertex(matrix, x - tx, y - width, z - tz).setColor(1.0F, 0.13F, 0.02F, 0.0F);
            buffer.addVertex(matrix, x, y + width, z).setColor(1.0F, 0.34F, 0.07F, alpha);
            buffer.addVertex(matrix, x + tx, y + width, z + tz).setColor(1.0F, 0.13F, 0.02F, 0.0F);
            buffer.addVertex(matrix, x, y - width, z).setColor(1.0F, 0.34F, 0.07F, alpha);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderPolarSpewer(Matrix4f matrix, float time, float fade) {
      renderPolarJetShell(matrix, 1.0F, 0.88F, 0.28F, 0.3F * fade, 1.0F, time);
      renderPolarJetShell(matrix, 1.0F, 0.38F, 0.08F, 0.17F * fade, -1.0F, -time);
      renderPolarJetCore(matrix, 1.0F, 0.98F, 0.68F, 0.38F * fade, 1.0F, time * 1.15F);
      renderPolarJetCore(matrix, 1.0F, 0.46F, 0.12F, 0.22F * fade, -1.0F, -time * 1.15F);
   }

   private static void renderPolarJetShell(Matrix4f matrix, float red, float green, float blue, float alpha, float sign, float time) {
      if (!(alpha <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
         int segments = 40;

         for (int i = 0; i <= segments; i++) {
            float t = (float)i / segments;
            float y = sign * Mth.lerp(t, 0.62F, 5.6F);
            float width = 0.34F * (1.0F - t) + 0.052F;
            float twist = time * 1.08F + t * 11.5F;
            float x = (float)Math.cos(twist) * width;
            float z = (float)Math.sin(twist) * width;
            float a = alpha * (1.0F - t) * (0.58F + 0.42F * (float)Math.sin(t * 28.0F + time * 4.0F));
            buffer.addVertex(matrix, -x, y, -z).setColor(red, green, blue, 0.0F);
            buffer.addVertex(matrix, x, y, z).setColor(red, green, blue, a);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderPolarJetCore(Matrix4f matrix, float red, float green, float blue, float alpha, float sign, float time) {
      if (!(alpha <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int streams = 5;
         int segments = 18;

         for (int stream = 0; stream < streams; stream++) {
            float base = stream * (float) (Math.PI * 2) / streams + time * 0.74F;

            for (int i = 0; i < segments; i++) {
               float t0 = (float)i / segments;
               float t1 = (float)(i + 1) / segments;
               float y0 = sign * Mth.lerp(t0, 0.58F, 5.85F);
               float y1 = sign * Mth.lerp(t1, 0.58F, 5.85F);
               float spiral0 = base + t0 * 10.8F;
               float spiral1 = base + t1 * 10.8F;
               float r0 = 0.05F + t0 * 0.25F;
               float r1 = 0.05F + t1 * 0.25F;
               float width0 = 0.022F * (1.0F - t0) + 0.006F;
               float width1 = 0.022F * (1.0F - t1) + 0.006F;
               float x0 = (float)Math.cos(spiral0) * r0;
               float z0 = (float)Math.sin(spiral0) * r0;
               float x1 = (float)Math.cos(spiral1) * r1;
               float z1 = (float)Math.sin(spiral1) * r1;
               float nx0 = (float)(-Math.sin(spiral0)) * width0;
               float nz0 = (float)Math.cos(spiral0) * width0;
               float nx1 = (float)(-Math.sin(spiral1)) * width1;
               float nz1 = (float)Math.cos(spiral1) * width1;
               float a0 = alpha * (1.0F - t0);
               float a1 = alpha * (1.0F - t1);
               buffer.addVertex(matrix, x0 - nx0, y0, z0 - nz0).setColor(red, green, blue, a0);
               buffer.addVertex(matrix, x0 + nx0, y0, z0 + nz0).setColor(red, green, blue, a0 * 0.2F);
               buffer.addVertex(matrix, x1 + nx1, y1, z1 + nz1).setColor(red, green, blue, a1 * 0.2F);
               buffer.addVertex(matrix, x1 - nx1, y1, z1 - nz1).setColor(red, green, blue, a1);
            }
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void renderOuterRedDust(Matrix4f matrix, float time, float fade) {
      if (!(fade <= 5.0E-4F)) {
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         int count = 22;

         for (int i = 0; i < count; i++) {
            float seed = i * 3.81F;
            float angle = seed + time * 0.1F;
            float dist = 4.0F + i % 10 * 0.58F;
            float y = (float)Math.sin(seed + time * 0.5F) * 0.04F;
            float len = 0.07F + i % 4 * 0.018F;
            float width = 0.004F;
            float x = (float)Math.cos(angle) * dist;
            float z = (float)Math.sin(angle) * dist;
            float tx = (float)(-Math.sin(angle)) * len;
            float tz = (float)Math.cos(angle) * len;
            float alpha = fade * (0.022F + i % 4 * 0.006F);
            buffer.addVertex(matrix, x - tx, y - width, z - tz).setColor(0.95F, 0.1F, 0.03F, 0.0F);
            buffer.addVertex(matrix, x, y + width, z).setColor(1.0F, 0.24F, 0.06F, alpha);
            buffer.addVertex(matrix, x + tx, y + width, z + tz).setColor(0.95F, 0.1F, 0.03F, 0.0F);
            buffer.addVertex(matrix, x, y - width, z).setColor(1.0F, 0.24F, 0.06F, alpha);
         }

         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private static void enablePostShader(int duration) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.gameRenderer.currentEffect() == null) {
         try {
            minecraft.gameRenderer.loadEffect(POST_SHADER);
            postActive = true;
            postTime = Math.max(postTime, duration);
         } catch (Exception ignored) {
            postActive = false;
         }
      } else {
         postTime = Math.max(postTime, duration);
      }
   }

   private static void disablePostShader() {
      Minecraft minecraft = Minecraft.getInstance();

      try {
         minecraft.gameRenderer.shutdownEffect();
      } catch (Exception var2) {
      }

      postActive = false;
      shaderStrength = 0.0F;
   }

   private static void uploadPostUniforms() {
      Object effect = Minecraft.getInstance().gameRenderer.currentEffect();
      if (effect != null) {
         try {
            for (Object pass : getPasses(effect)) {
               Object shader = getShader(pass);
               setUniform2(shader, "Center", shaderX, shaderY);
               setUniform1(shader, "Strength", shaderStrength);
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

   private record BandVertex(float x, float y, float z, float r, float g, float b, float a) {
   }

   private static final class ClientBlackHole {
      private final UUID id;
      private final Vec3 center;
      private final float radius;
      private final int duration;
      private int age;

      private ClientBlackHole(UUID id, Vec3 center, float radius, int duration) {
         this.id = id;
         this.center = center;
         this.radius = radius;
         this.duration = duration;
      }
   }

   private record HaloVertex(float x, float y, float z, float r, float g, float b, float a) {
   }

   private record RibbonVertex(float x, float y, float z, float r, float g, float b, float a) {
   }

   private record YDiskVertex(float x, float y, float z, float r, float g, float b, float a) {
   }
}
