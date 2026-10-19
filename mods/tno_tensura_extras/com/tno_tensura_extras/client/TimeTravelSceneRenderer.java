package com.tno_tensura_extras.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4f;

public final class TimeTravelSceneRenderer {
   private static final float TAU = (float) (Math.PI * 2);
   private static final float HALF_PI = (float) (Math.PI / 2);
   private static final int RING_COUNT = 6;
   private static final int WATCH_COUNT = 62;
   private static final int SPECK_COUNT = 150;
   private static final int MAX_ENTRIES = 212;
   private static final float WAVE_INTERVAL = 3.6F;
   private static final float WAVE_LIFETIME = 5.0F;
   private static final TimeTravelSceneRenderer.OrbitRing[] RINGS = new TimeTravelSceneRenderer.OrbitRing[6];
   private static final TimeTravelSceneRenderer.SceneWatch[] WATCHES = new TimeTravelSceneRenderer.SceneWatch[62];
   private static final TimeTravelSceneRenderer.SceneSpeck[] SPECKS = new TimeTravelSceneRenderer.SceneSpeck[150];
   private static final TimeTravelSceneRenderer.DrawEntry[] DRAW_ENTRIES = new TimeTravelSceneRenderer.DrawEntry[212];
   private static final List<TimeTravelSceneRenderer.Wave> WAVES = new ArrayList<>();
   private static final TimeTravelSceneRenderer.Camera CAMERA = new TimeTravelSceneRenderer.Camera();
   private static final TimeTravelSceneRenderer.Vec3 WORLD = new TimeTravelSceneRenderer.Vec3();
   private static final TimeTravelSceneRenderer.Vec3 WORLD_SECOND = new TimeTravelSceneRenderer.Vec3();
   private static final TimeTravelSceneRenderer.Projected PROJECTED = new TimeTravelSceneRenderer.Projected();
   private static final TimeTravelSceneRenderer.Projected PROJECTED_SECOND = new TimeTravelSceneRenderer.Projected();
   private static final TimeTravelSceneRenderer.Basis BASIS = new TimeTravelSceneRenderer.Basis();
   private static final float[] POLYGON_X = new float[128];
   private static final float[] POLYGON_Y = new float[128];
   private static final RandomSource RANDOM = RandomSource.create(407709077105L);
   private static final RenderType SCENE_RENDER_TYPE = RenderType.create(
      "tno_time_travel_scene",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      4194304,
      false,
      false,
      CompositeState.builder()
         .setShaderState(RenderStateShard.RENDERTYPE_GUI_OVERLAY_SHADER)
         .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
         .setCullState(RenderStateShard.NO_CULL)
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .createCompositeState(false)
   );
   private static float lastSeconds = -1.0F;
   private static float nextWave = 1.2F;
   private static float parallaxX;
   private static float parallaxY;
   private static float heroClockStartSeconds = -1.0F;
   private static final TimeTravelSceneRenderer.SceneWatch HERO = new TimeTravelSceneRenderer.SceneWatch(0.201F, 0.075F, 1.16F, false, 1);
   private static float currentSceneSeconds;

   private TimeTravelSceneRenderer() {
   }

   public static void reset() {
      lastSeconds = -1.0F;
      nextWave = 1.2F;
      WAVES.clear();
      parallaxX = 0.0F;
      parallaxY = 0.0F;
      heroClockStartSeconds = -1.0F;
   }

   public static void render(GuiGraphics graphics, Minecraft minecraft, float elapsedSeconds, float overlayOpacity) {
      if (!(overlayOpacity <= 0.0F)) {
         int width = graphics.guiWidth();
         int height = graphics.guiHeight();
         if (width > 0 && height > 0) {
            if (lastSeconds >= 0.0F && elapsedSeconds < lastSeconds) {
               reset();
            }

            lastSeconds = elapsedSeconds;
            updateWaves(elapsedSeconds);
            updateParallax(minecraft);
            CAMERA.update(width, height, elapsedSeconds, parallaxX, parallaxY);
            TimeTravelSceneRenderer.Batch batch = new TimeTravelSceneRenderer.Batch(graphics);
            drawOrbitRings(batch, overlayOpacity);
            drawWaves(batch, elapsedSeconds, overlayOpacity);
            float heroScreenRadius = HERO.base * CAMERA.scale * (CAMERA.focal / CAMERA.cameraDistance);
            float heroX = CAMERA.centerX + CAMERA.panX;
            float heroY = CAMERA.centerY + CAMERA.panY;
            int entryCount = buildDepthList(heroX, heroY, heroScreenRadius);
            Arrays.sort(DRAW_ENTRIES, 0, entryCount, (left, right) -> Float.compare(right.depth, left.depth));

            for (int i = 0; i < entryCount; i++) {
               TimeTravelSceneRenderer.DrawEntry entry = DRAW_ENTRIES[i];
               if (entry.speck) {
                  drawSpeck(batch, SPECKS[entry.index], elapsedSeconds, overlayOpacity);
               } else {
                  drawWatch(batch, WATCHES[entry.index], elapsedSeconds, entry.fade, overlayOpacity);
               }
            }

            drawHero(batch, elapsedSeconds, overlayOpacity);
         }
      }
   }

   private static void buildScene() {
      for (int i = 0; i < 6; i++) {
         RINGS[i] = new TimeTravelSceneRenderer.OrbitRing(
            0.46F + i * 0.125F + random(-0.02F, 0.02F),
            random(-0.06F, 0.06F),
            random(-0.045F, 0.045F),
            random(0.005F, 0.016F) * sign(),
            random(0.0F, (float) (Math.PI * 2)),
            i % 2 == 0 ? 2 : 6,
            i % 2 == 0 ? 9 : 11,
            i != 2 && i != 4 ? 0.45F : 0.62F
         );
      }

      for (int i = 0; i < 62; i++) {
         boolean big = i < 9;
         TimeTravelSceneRenderer.SceneWatch watch = new TimeTravelSceneRenderer.SceneWatch();
         watch.radius = big ? random(0.54F, 0.84F) : random(0.2F, 1.02F);
         watch.y = big ? (i % 2 == 0 ? 1.0F : -1.0F) * random(0.44F, 0.7F) : random(-0.86F, 0.86F);
         watch.inclination = random(-0.11F, 0.11F);
         watch.angle = big ? i / 9.0F * (float) (Math.PI * 2) + random(-0.18F, 0.18F) : random(0.0F, (float) (Math.PI * 2));
         watch.speed = random(0.014F, 0.042F) * (RANDOM.nextFloat() < 0.78F ? 1.0F : -1.0F);
         watch.base = big ? random(0.098F, 0.15F) : random(0.019F, 0.08F);
         watch.ax = random(0.12F, 0.5F);
         watch.ay = random(0.12F, 0.5F);
         watch.wx = random(0.05F, 0.19F) * sign();
         watch.wy = random(0.05F, 0.19F) * sign();
         watch.px = random(0.0F, (float) (Math.PI * 2));
         watch.py = random(0.0F, (float) (Math.PI * 2));
         watch.spin = random(0.02F, 0.11F) * sign();
         watch.roll = random(0.0F, (float) (Math.PI * 2));
         watch.timeOffset = random(0.0F, 43200.0F);
         watch.timeRate = random(0.4F, 3.4F) * (RANDOM.nextFloat() < 0.8F ? 1.0F : -1.0F);
         watch.crown = RANDOM.nextFloat() < 0.55F;
         watch.thickness = random(0.065F, 0.115F);
         watch.bold = big ? 1.0F : 0.85F;
         watch.mode = 0;
         WATCHES[i] = watch;
      }

      for (int i = 0; i < 150; i++) {
         SPECKS[i] = new TimeTravelSceneRenderer.SceneSpeck(
            random(0.16F, 1.02F),
            random(-0.85F, 0.85F),
            random(-0.16F, 0.16F),
            random(0.0F, (float) (Math.PI * 2)),
            random(0.012F, 0.05F) * sign(),
            random(8.0E-4F, 0.0026F),
            RANDOM.nextFloat() < 0.34F
         );
      }
   }

   private static int buildDepthList(float heroX, float heroY, float heroRadius) {
      int count = 0;

      for (int i = 0; i < SPECKS.length; i++) {
         TimeTravelSceneRenderer.SceneSpeck speck = SPECKS[i];
         CAMERA.worldPosition(speck.radius, speck.angle + speck.speed * currentSeconds(), speck.y, speck.inclination, WORLD);
         CAMERA.project(WORLD, PROJECTED);
         TimeTravelSceneRenderer.DrawEntry entry = DRAW_ENTRIES[count++];
         entry.speck = true;
         entry.index = i;
         entry.depth = PROJECTED.depth;
         entry.fade = 1.0F;
      }

      for (int i = 0; i < WATCHES.length; i++) {
         TimeTravelSceneRenderer.SceneWatch watch = WATCHES[i];
         CAMERA.worldPosition(watch.radius, watch.angle + currentSeconds() * watch.speed, watch.y, watch.inclination, WORLD);
         CAMERA.project(WORLD, PROJECTED);
         float distance = Mth.sqrt((PROJECTED.x - heroX) * (PROJECTED.x - heroX) + (PROJECTED.y - heroY) * (PROJECTED.y - heroY))
            - watch.base * CAMERA.scale * PROJECTED.scale;
         float fade = Mth.clamp((distance - heroRadius * 1.02F) / (heroRadius * 0.4F), 0.0F, 1.0F);
         if (!(fade <= 0.0F)) {
            TimeTravelSceneRenderer.DrawEntry entry = DRAW_ENTRIES[count++];
            entry.speck = false;
            entry.index = i;
            entry.depth = PROJECTED.depth;
            entry.fade = fade;
         }
      }

      return count;
   }

   private static float currentSeconds() {
      return currentSceneSeconds;
   }

   private static void drawOrbitRings(TimeTravelSceneRenderer.Batch batch, float opacity) {
      for (TimeTravelSceneRenderer.OrbitRing ring : RINGS) {
         int color = color(0, opacity * 0.72F);
         int segments = 74;

         for (int i = 0; i < segments; i++) {
            int cycle = ring.dashOn + ring.dashOff;
            if (i % cycle < ring.dashOn) {
               float first = ring.startAngle + (float)i / segments * (float) (Math.PI * 2);
               float second = ring.startAngle + (float)(i + 1) / segments * (float) (Math.PI * 2);
               CAMERA.worldPosition(ring.radius, first + currentSceneSeconds * ring.speed, ring.y, ring.inclination, WORLD);
               CAMERA.project(WORLD, PROJECTED);
               CAMERA.worldPosition(ring.radius, second + currentSceneSeconds * ring.speed, ring.y, ring.inclination, WORLD_SECOND);
               CAMERA.project(WORLD_SECOND, PROJECTED_SECOND);
               drawLine(batch, PROJECTED.x, PROJECTED.y, PROJECTED_SECOND.x, PROJECTED_SECOND.y, Mth.clamp(ring.lineWidth, 0.5F, 1.6F), color);
            }
         }
      }
   }

   private static void drawWaves(TimeTravelSceneRenderer.Batch batch, float elapsedSeconds, float opacity) {
      int colorBase = color(0, opacity * 0.55F);

      for (TimeTravelSceneRenderer.Wave wave : WAVES) {
         float age = elapsedSeconds - wave.startTime;
         if (!(age < 0.0F) && !(age >= 5.0F)) {
            float life = 1.0F - age / 5.0F;
            float radius = 0.1F + age * 0.075F;
            int segments = 74;

            for (int i = 0; i < segments; i++) {
               if (i % 12 <= 1) {
                  float first = (float)i / segments * (float) (Math.PI * 2);
                  float second = (float)(i + 1) / segments * (float) (Math.PI * 2);
                  CAMERA.worldPosition(radius, first, 0.0F, 0.0F, WORLD);
                  CAMERA.project(WORLD, PROJECTED);
                  CAMERA.worldPosition(radius, second, 0.0F, 0.0F, WORLD_SECOND);
                  CAMERA.project(WORLD_SECOND, PROJECTED_SECOND);
                  drawLine(
                     batch, PROJECTED.x, PROJECTED.y, PROJECTED_SECOND.x, PROJECTED_SECOND.y, Math.max(0.5F, life * 0.85F), multiplyAlpha(colorBase, life)
                  );
               }
            }
         }
      }
   }

   private static void drawSpeck(TimeTravelSceneRenderer.Batch batch, TimeTravelSceneRenderer.SceneSpeck speck, float elapsedSeconds, float opacity) {
      CAMERA.worldPosition(speck.radius, speck.angle + elapsedSeconds * speck.speed, speck.y, speck.inclination, WORLD);
      CAMERA.project(WORLD, PROJECTED);
      float radius = speck.size * CAMERA.scale * PROJECTED.scale;
      if (!(radius < 0.35F)) {
         int black = color(0, opacity * 0.92F);
         int white = color(16777215, opacity);
         if (speck.ring) {
            drawScreenDisc(batch, PROJECTED.x, PROJECTED.y, radius, white, black, Mth.clamp(radius * 0.34F, 0.35F, 1.1F));
         } else {
            drawScreenDisc(batch, PROJECTED.x, PROJECTED.y, radius, black, 0, 0.0F);
         }
      }
   }

   private static void drawHero(TimeTravelSceneRenderer.Batch batch, float elapsedSeconds, float opacity) {
      CAMERA.worldPosition(0.0F, 0.0F, 0.0F, 0.0F, WORLD);
      float roll = Mth.sin(elapsedSeconds * 0.068F) * 0.022F;
      float tilt = Mth.sin(elapsedSeconds * 0.051F) * 0.05F + 0.3F;
      float turn = Mth.sin(elapsedSeconds * 0.044F) * 0.055F;
      CAMERA.orient(roll, tilt, turn, BASIS);
      if (heroClockStartSeconds < 0.0F) {
         heroClockStartSeconds = currentDaySeconds();
      }

      float realSeconds = heroClockStartSeconds + elapsedSeconds * 60.0F;
      drawWatchWithBasis(
         batch, HERO, WORLD, BASIS, 1.0F, realSeconds / 43200.0F * (float) (Math.PI * 2), realSeconds / 3600.0F * (float) (Math.PI * 2), opacity
      );
   }

   private static void drawWatch(TimeTravelSceneRenderer.Batch batch, TimeTravelSceneRenderer.SceneWatch watch, float elapsedSeconds, float fade, float opacity) {
      CAMERA.worldPosition(watch.radius, watch.angle + elapsedSeconds * watch.speed, watch.y, watch.inclination, WORLD);
      CAMERA.orient(
         watch.roll + elapsedSeconds * watch.spin,
         watch.ax * Mth.sin(elapsedSeconds * watch.wx + watch.px),
         watch.ay * Mth.sin(elapsedSeconds * watch.wy + watch.py),
         BASIS
      );
      float clockSeconds = watch.timeOffset + elapsedSeconds * 60.0F * watch.timeRate;
      drawWatchWithBasis(
         batch, watch, WORLD, BASIS, fade, clockSeconds / 43200.0F * (float) (Math.PI * 2), clockSeconds / 3600.0F * (float) (Math.PI * 2), opacity
      );
   }

   private static void drawWatchWithBasis(
      TimeTravelSceneRenderer.Batch batch,
      TimeTravelSceneRenderer.SceneWatch watch,
      TimeTravelSceneRenderer.Vec3 center,
      TimeTravelSceneRenderer.Basis basis,
      float sizeFade,
      float hourAngle,
      float minuteAngle,
      float opacity
   ) {
      CAMERA.project(center, PROJECTED);
      float radiusWorld = watch.base * CAMERA.scale * sizeFade;
      float projectedRadius = radiusWorld * PROJECTED.scale;
      if (!(projectedRadius < 1.6F)) {
         float lineWidth = Mth.clamp(projectedRadius * (watch.mode == 1 ? 0.03F : 0.0175F) * watch.bold, 0.34F, watch.mode == 1 ? 5.6F : 1.9F);
         int outline = color(0, opacity);
         int face = color(16777215, opacity);
         int inner = color(0, opacity * 0.8F);
         float thickness = radiusWorld * watch.thickness;
         TimeTravelSceneRenderer.Vec3 back = WORLD_SECOND;
         back.set(center.x + basis.n.x * thickness, center.y + basis.n.y * thickness, center.z + basis.n.z * thickness);
         drawDial(batch, back, basis, radiusWorld, projectedRadius, face, outline, lineWidth);
         if (projectedRadius > 46.0F) {
            float half = thickness * 0.55F;
            back.set(center.x + basis.n.x * half, center.y + basis.n.y * half, center.z + basis.n.z * half);
            drawDial(batch, back, basis, radiusWorld, projectedRadius, face, outline, lineWidth);
         }

         if (projectedRadius > 74.0F && watch.mode == 0) {
            drawRimHatching(batch, center, basis, radiusWorld, thickness, outline, lineWidth);
         }

         if (watch.crown && projectedRadius > 14.0F) {
            drawCrown(batch, center, basis, radiusWorld, face, outline, lineWidth, projectedRadius);
         }

         drawDial(batch, center, basis, radiusWorld, projectedRadius, face, outline, lineWidth);
         if (watch.mode == 1) {
            drawDiscOutline(batch, center, basis, radiusWorld * 0.895F, Math.max(0.7F, lineWidth * 0.26F), inner);
         } else if (projectedRadius > 26.0F) {
            drawDiscOutline(batch, center, basis, radiusWorld * 0.9F, Math.max(0.4F, lineWidth * 0.5F), inner);
         }

         if (!(projectedRadius < 7.0F)) {
            int tickCount = watch.mode == 1 ? 4 : (projectedRadius > 20.0F ? 12 : 4);
            float innerRadius = watch.mode == 1 ? 0.74F : (projectedRadius > 46.0F ? 0.8F : 0.84F);
            float tickWidth = Mth.clamp(projectedRadius * (watch.mode == 1 ? 0.02F : 0.021F), 0.36F, 3.4F);

            for (int i = 0; i < tickCount; i++) {
               float angle = (float)i / tickCount * (float) (Math.PI * 2);
               float u = Mth.sin(angle);
               float v = -Mth.cos(angle);
               drawDialSegment(batch, center, basis, radiusWorld, u * 0.9F, v * 0.9F, u * innerRadius, v * innerRadius, tickWidth, inner);
            }

            if (!(projectedRadius < 11.0F)) {
               float handWidth = Mth.clamp(projectedRadius * 0.03F, 0.5F, 5.2F) / projectedRadius;
               drawHand(batch, center, basis, radiusWorld, hourAngle, 0.44F, 0.055F, handWidth * 0.62F, inner);
               drawHand(batch, center, basis, radiusWorld, minuteAngle, 0.68F, 0.055F, handWidth * 0.46F, inner);
               drawDisc(batch, center, basis, radiusWorld * 0.048F, 16, inner, 0, 0.0F);
            }
         }
      }
   }

   private static void drawRimHatching(
      TimeTravelSceneRenderer.Batch batch,
      TimeTravelSceneRenderer.Vec3 center,
      TimeTravelSceneRenderer.Basis basis,
      float radius,
      float thickness,
      int color,
      float lineWidth
   ) {
      CAMERA.project(center, PROJECTED);
      float centerX = PROJECTED.x;
      float centerY = PROJECTED.y;
      TimeTravelSceneRenderer.Vec3 rim = WORLD_SECOND;
      rim.set(center.x + basis.n.x * thickness, center.y + basis.n.y * thickness, center.z + basis.n.z * thickness);
      CAMERA.project(rim, PROJECTED_SECOND);
      float depthX = PROJECTED_SECOND.x - centerX;
      float depthY = PROJECTED_SECOND.y - centerY;
      dialPoint(center, basis, radius, 1.0F, 0.0F, POLYGON_X, POLYGON_Y, 0);
      float axisUX = POLYGON_X[0] - centerX;
      float axisUY = POLYGON_Y[0] - centerY;
      dialPoint(center, basis, radius, 0.0F, 1.0F, POLYGON_X, POLYGON_Y, 0);
      float axisVX = POLYGON_X[0] - centerX;
      float axisVY = POLYGON_Y[0] - centerY;
      float hatchAngle = (float)Mth.atan2(axisVX * depthX + axisVY * depthY, axisUX * depthX + axisUY * depthY);
      float hatchWidth = Math.max(0.4F, lineWidth * 0.36F);

      for (int i = -2; i <= 2; i++) {
         float angle = hatchAngle + i * 0.135F;
         dialPoint(center, basis, radius, Mth.cos(angle), Mth.sin(angle), POLYGON_X, POLYGON_Y, 0);
         drawLine(batch, POLYGON_X[0], POLYGON_Y[0], POLYGON_X[0] + depthX, POLYGON_Y[0] + depthY, hatchWidth, color);
      }
   }

   private static void drawDial(
      TimeTravelSceneRenderer.Batch batch,
      TimeTravelSceneRenderer.Vec3 center,
      TimeTravelSceneRenderer.Basis basis,
      float radiusWorld,
      float projectedRadius,
      int face,
      int outline,
      float lineWidth
   ) {
      int segments = projectedRadius > 90.0F ? 104 : (projectedRadius > 42.0F ? 68 : (projectedRadius > 16.0F ? 42 : 26));
      drawDisc(batch, center, basis, radiusWorld, segments, face, outline, lineWidth);
   }

   private static void drawCrown(
      TimeTravelSceneRenderer.Batch batch,
      TimeTravelSceneRenderer.Vec3 center,
      TimeTravelSceneRenderer.Basis basis,
      float radius,
      int face,
      int outline,
      float lineWidth,
      float projectedRadius
   ) {
      int count = 6;

      for (int i = 0; i < count; i++) {
         float u = switch (i) {
            case 0 -> -0.15F;
            case 1 -> -0.13F;
            case 2 -> 0.13F;
            case 3 -> 0.15F;
            case 4 -> 0.13F;
            default -> -0.13F;
         };

         float v = switch (i) {
            case 0, 3 -> -0.96F;
            case 1, 2 -> -1.2F;
            default -> -0.99F;
         };
         dialPoint(center, basis, radius, u, v, POLYGON_X, POLYGON_Y, i);
      }

      drawPolygon(batch, POLYGON_X, POLYGON_Y, count, face);
      drawPolyline(batch, POLYGON_X, POLYGON_Y, count, true, lineWidth, outline);
      if (projectedRadius > 44.0F) {
         for (int i = 1; i <= 3; i++) {
            float u = -0.14F + i * 0.07F;
            dialPoint(center, basis, radius, u, -0.99F, POLYGON_X, POLYGON_Y, 0);
            dialPoint(center, basis, radius, u, -1.17F, POLYGON_X, POLYGON_Y, 1);
            drawLine(batch, POLYGON_X[0], POLYGON_Y[0], POLYGON_X[1], POLYGON_Y[1], Math.max(0.4F, lineWidth * 0.5F), outline);
         }
      }

      TimeTravelSceneRenderer.Vec3 bow = WORLD_SECOND;
      bow.set(center.x - 1.36F * radius * basis.v.x, center.y - 1.36F * radius * basis.v.y, center.z - 1.36F * radius * basis.v.z);
      drawDisc(batch, bow, basis, radius * 0.17F, 22, face, outline, lineWidth);
      if (projectedRadius > 40.0F) {
         drawDiscOutline(batch, bow, basis, radius * 0.085F, Math.max(0.4F, lineWidth * 0.5F), outline);
      }
   }

   private static void drawHand(
      TimeTravelSceneRenderer.Batch batch,
      TimeTravelSceneRenderer.Vec3 center,
      TimeTravelSceneRenderer.Basis basis,
      float radius,
      float angle,
      float length,
      float tail,
      float width,
      int color
   ) {
      float s = Mth.sin(angle);
      float c = Mth.cos(angle);
      float u = s;
      float v = -c;
      float perpendicularU = c;
      float perpendicularV = s;
      handPoint(center, basis, radius, u, v, length, 0.0F, perpendicularU, perpendicularV, POLYGON_X, POLYGON_Y, 0);
      handPoint(center, basis, radius, u, v, length * 0.3F, width, perpendicularU, perpendicularV, POLYGON_X, POLYGON_Y, 1);
      handPoint(center, basis, radius, u, v, -tail, width * 0.55F, perpendicularU, perpendicularV, POLYGON_X, POLYGON_Y, 2);
      handPoint(center, basis, radius, u, v, -tail, -width * 0.55F, perpendicularU, perpendicularV, POLYGON_X, POLYGON_Y, 3);
      handPoint(center, basis, radius, u, v, length * 0.3F, -width, perpendicularU, perpendicularV, POLYGON_X, POLYGON_Y, 4);
      drawPolygon(batch, POLYGON_X, POLYGON_Y, 5, color);
   }

   private static void handPoint(
      TimeTravelSceneRenderer.Vec3 center,
      TimeTravelSceneRenderer.Basis basis,
      float radius,
      float u,
      float v,
      float along,
      float across,
      float perpendicularU,
      float perpendicularV,
      float[] x,
      float[] y,
      int index
   ) {
      float localU = u * along + perpendicularU * across;
      float localV = v * along + perpendicularV * across;
      dialPoint(center, basis, radius, localU, localV, x, y, index);
   }

   private static void drawDialSegment(
      TimeTravelSceneRenderer.Batch batch,
      TimeTravelSceneRenderer.Vec3 center,
      TimeTravelSceneRenderer.Basis basis,
      float radius,
      float u1,
      float v1,
      float u2,
      float v2,
      float width,
      int color
   ) {
      dialPoint(center, basis, radius, u1, v1, POLYGON_X, POLYGON_Y, 0);
      dialPoint(center, basis, radius, u2, v2, POLYGON_X, POLYGON_Y, 1);
      drawLine(batch, POLYGON_X[0], POLYGON_Y[0], POLYGON_X[1], POLYGON_Y[1], width, color);
   }

   private static void drawDisc(
      TimeTravelSceneRenderer.Batch batch,
      TimeTravelSceneRenderer.Vec3 center,
      TimeTravelSceneRenderer.Basis basis,
      float radius,
      int segments,
      int fill,
      int outline,
      float lineWidth
   ) {
      int count = Math.min(segments, POLYGON_X.length);

      for (int i = 0; i < count; i++) {
         float angle = (float)i / count * (float) (Math.PI * 2);
         dialPoint(center, basis, radius, Mth.cos(angle), Mth.sin(angle), POLYGON_X, POLYGON_Y, i);
      }

      if (fill != 0) {
         drawPolygon(batch, POLYGON_X, POLYGON_Y, count, fill);
      }

      if (outline != 0 && lineWidth > 0.0F) {
         drawPolyline(batch, POLYGON_X, POLYGON_Y, count, true, lineWidth, outline);
      }
   }

   private static void drawDiscOutline(
      TimeTravelSceneRenderer.Batch batch, TimeTravelSceneRenderer.Vec3 center, TimeTravelSceneRenderer.Basis basis, float radius, float lineWidth, int color
   ) {
      int segments = radius * CAMERA.centerScale() > 90.0F ? 104 : (radius * CAMERA.centerScale() > 42.0F ? 68 : 42);
      drawDisc(batch, center, basis, radius, segments, 0, color, lineWidth);
   }

   private static void drawScreenDisc(TimeTravelSceneRenderer.Batch batch, float centerX, float centerY, float radius, int fill, int outline, float lineWidth) {
      int segments = radius > 18.0F ? 32 : (radius > 6.0F ? 20 : 12);
      int count = Math.min(segments, POLYGON_X.length);

      for (int i = 0; i < count; i++) {
         float angle = (float)i / count * (float) (Math.PI * 2);
         POLYGON_X[i] = centerX + Mth.cos(angle) * radius;
         POLYGON_Y[i] = centerY + Mth.sin(angle) * radius;
      }

      if (fill != 0) {
         drawPolygon(batch, POLYGON_X, POLYGON_Y, count, fill);
      }

      if (outline != 0 && lineWidth > 0.0F) {
         drawPolyline(batch, POLYGON_X, POLYGON_Y, count, true, lineWidth, outline);
      }
   }

   private static void dialPoint(
      TimeTravelSceneRenderer.Vec3 center, TimeTravelSceneRenderer.Basis basis, float radius, float u, float v, float[] x, float[] y, int index
   ) {
      CAMERA.project(
         center.x + radius * (u * basis.u.x + v * basis.v.x),
         center.y + radius * (u * basis.u.y + v * basis.v.y),
         center.z + radius * (u * basis.u.z + v * basis.v.z),
         PROJECTED
      );
      x[index] = PROJECTED.x;
      y[index] = PROJECTED.y;
   }

   private static void drawPolyline(TimeTravelSceneRenderer.Batch batch, float[] x, float[] y, int count, boolean closed, float width, int color) {
      int limit = closed ? count : count - 1;

      for (int i = 0; i < limit; i++) {
         int next = (i + 1) % count;
         drawLine(batch, x[i], y[i], x[next], y[next], width, color);
      }
   }

   private static void drawPolygon(TimeTravelSceneRenderer.Batch batch, float[] x, float[] y, int count, int color) {
      if (count >= 3 && color != 0 && color >>> 24 != 0) {
         for (int i = 1; i < count - 1; i++) {
            float x1 = x[0];
            float y1 = y[0];
            float x2 = x[i];
            float y2 = y[i];
            float x3 = x[i + 1];
            float y3 = y[i + 1];
            float area = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
            if (area > 0.0F) {
               batch.triangle(x1, y1, x3, y3, x2, y2, color);
            } else {
               batch.triangle(x1, y1, x2, y2, x3, y3, color);
            }
         }
      }
   }

   private static void drawLine(TimeTravelSceneRenderer.Batch batch, float x1, float y1, float x2, float y2, float width, int color) {
      if (color != 0 && color >>> 24 != 0) {
         float dx = x2 - x1;
         float dy = y2 - y1;
         float length = Mth.sqrt(dx * dx + dy * dy);
         if (!(length < 0.001F)) {
            float half = Math.max(0.18F, width * 0.5F);
            float nx = -dy / length * half;
            float ny = dx / length * half;
            batch.vertex(x1 + nx, y1 + ny, color);
            batch.vertex(x2 + nx, y2 + ny, color);
            batch.vertex(x2 - nx, y2 - ny, color);
            batch.vertex(x1 - nx, y1 - ny, color);
         }
      }
   }

   private static void updateWaves(float elapsedSeconds) {
      int safety;
      for (safety = 0; elapsedSeconds >= nextWave && safety++ < 24; nextWave += 3.6F) {
         WAVES.add(new TimeTravelSceneRenderer.Wave(nextWave));
         if (WAVES.size() > 8) {
            WAVES.remove(0);
         }
      }

      if (safety >= 24) {
         nextWave = elapsedSeconds + 3.6F;
      }

      Iterator<TimeTravelSceneRenderer.Wave> iterator = WAVES.iterator();

      while (iterator.hasNext()) {
         if (elapsedSeconds - iterator.next().startTime >= 5.0F) {
            iterator.remove();
         }
      }
   }

   private static void updateParallax(Minecraft minecraft) {
      Window window = minecraft.getWindow();
      double screenWidth = Math.max(1.0, window.getScreenWidth());
      double screenHeight = Math.max(1.0, window.getScreenHeight());
      float targetX = Mth.clamp((float)(minecraft.mouseHandler.xpos() / screenWidth - 0.5), -0.5F, 0.5F);
      float targetY = Mth.clamp((float)(minecraft.mouseHandler.ypos() / screenHeight - 0.5), -0.5F, 0.5F);
      parallaxX = parallaxX + (targetX - parallaxX) * 0.045F;
      parallaxY = parallaxY + (targetY - parallaxY) * 0.045F;
   }

   private static float currentDaySeconds() {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null) {
         long dayTime = Math.floorMod(minecraft.level.getDayTime() + 6000L, 24000L);
         return (float)dayTime / 24000.0F * 86400.0F;
      } else {
         long millis = System.currentTimeMillis();
         return (float)(millis % 86400000L) / 1000.0F;
      }
   }

   private static float random(float min, float max) {
      return min + RANDOM.nextFloat() * (max - min);
   }

   private static float sign() {
      return RANDOM.nextBoolean() ? 1.0F : -1.0F;
   }

   private static int color(int rgb, float alpha) {
      int a = Mth.clamp((int)(Mth.clamp(alpha, 0.0F, 1.0F) * 255.0F), 0, 255);
      return a << 24 | rgb & 16777215;
   }

   private static int multiplyAlpha(int argb, float factor) {
      int alpha = Mth.clamp((int)((argb >>> 24 & 0xFF) * Mth.clamp(factor, 0.0F, 1.0F)), 0, 255);
      return alpha << 24 | argb & 16777215;
   }

   static {
      buildScene();

      for (int i = 0; i < DRAW_ENTRIES.length; i++) {
         DRAW_ENTRIES[i] = new TimeTravelSceneRenderer.DrawEntry();
      }
   }

   private static final class Basis {
      private final TimeTravelSceneRenderer.Vec3 u = new TimeTravelSceneRenderer.Vec3();
      private final TimeTravelSceneRenderer.Vec3 v = new TimeTravelSceneRenderer.Vec3();
      private final TimeTravelSceneRenderer.Vec3 n = new TimeTravelSceneRenderer.Vec3();
   }

   private static final class Batch {
      private final VertexConsumer vertices;
      private final Matrix4f matrix;

      private Batch(GuiGraphics graphics) {
         this.vertices = graphics.bufferSource().getBuffer(TimeTravelSceneRenderer.SCENE_RENDER_TYPE);
         this.matrix = graphics.pose().last().pose();
      }

      private void vertex(float x, float y, int color) {
         this.vertices.addVertex(this.matrix, x, y, 0.0F).setColor(color);
      }

      private void triangle(float x1, float y1, float x2, float y2, float x3, float y3, int color) {
         this.vertex(x1, y1, color);
         this.vertex(x2, y2, color);
         this.vertex(x3, y3, color);
         this.vertex(x3, y3, color);
      }
   }

   private static final class Camera {
      private float scale;
      private float centerX;
      private float centerY;
      private float focal;
      private float cameraDistance;
      private float yaw;
      private float yawCos;
      private float yawSin;
      private float pitchCos;
      private float pitchSin;
      private float panX;
      private float panY;

      private void update(int width, int height, float seconds, float pointerX, float pointerY) {
         this.scale = Math.min(width, height);
         this.centerX = width * 0.5F;
         this.centerY = height * 0.5F;
         this.focal = this.scale * 1.15F;
         this.cameraDistance = this.scale * 1.4F;
         this.yaw = seconds * 0.026F + pointerX * 0.42F;
         float pitch = -0.3F + Mth.sin(seconds * 0.11F) * 0.045F - pointerY * 0.1F;
         this.yawCos = Mth.cos(this.yaw);
         this.yawSin = Mth.sin(this.yaw);
         this.pitchCos = Mth.cos(pitch);
         this.pitchSin = Mth.sin(pitch);
         this.panX = -pointerX * this.scale * 0.05F;
         this.panY = -pointerY * this.scale * 0.035F;
         TimeTravelSceneRenderer.currentSceneSeconds = seconds;
      }

      private float centerScale() {
         return this.focal / this.cameraDistance;
      }

      private void worldPosition(float radius, float angle, float yValue, float inclination, TimeTravelSceneRenderer.Vec3 out) {
         float x = radius * Mth.cos(angle);
         float z0 = radius * Mth.sin(angle);
         float inclinationCos = Mth.cos(inclination);
         float inclinationSin = Mth.sin(inclination);
         float y = yValue * inclinationCos - z0 * inclinationSin;
         float z = yValue * inclinationSin + z0 * inclinationCos;
         out.x = (x * this.yawCos + z * this.yawSin) * this.scale;
         out.y = y * this.scale;
         out.z = (z * this.yawCos - x * this.yawSin) * this.scale;
      }

      private void project(TimeTravelSceneRenderer.Vec3 point, TimeTravelSceneRenderer.Projected out) {
         this.project(point.x, point.y, point.z, out);
      }

      private void project(float x, float y, float z, TimeTravelSceneRenderer.Projected out) {
         float rotatedY = y * this.pitchCos - z * this.pitchSin;
         float rotatedZ = y * this.pitchSin + z * this.pitchCos;
         float distance = this.cameraDistance + rotatedZ;
         float denominator = Math.max(this.scale * 0.16F, distance);
         float k = this.focal / denominator;
         out.x = this.centerX + x * k + this.panX;
         out.y = this.centerY + rotatedY * k + this.panY;
         out.scale = k;
         out.depth = rotatedZ;
      }

      private void orient(float roll, float tiltX, float turnY, TimeTravelSceneRenderer.Basis out) {
         this.rotate(1.0F, 0.0F, 0.0F, roll, tiltX, turnY, out.u);
         this.rotate(0.0F, 1.0F, 0.0F, roll, tiltX, turnY, out.v);
         this.rotate(0.0F, 0.0F, 1.0F, roll, tiltX, turnY, out.n);
      }

      private void rotate(float x, float y, float z, float roll, float tiltX, float turnY, TimeTravelSceneRenderer.Vec3 out) {
         float rollCos = Mth.cos(roll);
         float rollSin = Mth.sin(roll);
         float tiltCos = Mth.cos(tiltX);
         float tiltSin = Mth.sin(tiltX);
         float turnCos = Mth.cos(turnY);
         float turnSin = Mth.sin(turnY);
         float a = x * rollCos - y * rollSin;
         float b = x * rollSin + y * rollCos;
         float b2 = b * tiltCos - z * tiltSin;
         float c2 = b * tiltSin + z * tiltCos;
         float a3 = a * turnCos + c2 * turnSin;
         float c3 = c2 * turnCos - a * turnSin;
         out.x = a3 * this.yawCos + c3 * this.yawSin;
         out.y = b2;
         out.z = c3 * this.yawCos - a3 * this.yawSin;
      }
   }

   private static final class DrawEntry {
      private boolean speck;
      private int index;
      private float depth;
      private float fade;
   }

   private record OrbitRing(float radius, float y, float inclination, float speed, float startAngle, int dashOn, int dashOff, float lineWidth) {
   }

   private static final class Projected {
      private float x;
      private float y;
      private float scale;
      private float depth;
   }

   private record SceneSpeck(float radius, float y, float inclination, float angle, float speed, float size, boolean ring) {
   }

   private static final class SceneWatch {
      private float radius;
      private float y;
      private float inclination;
      private float angle;
      private float speed;
      private float base;
      private float ax;
      private float ay;
      private float wx;
      private float wy;
      private float px;
      private float py;
      private float spin;
      private float roll;
      private float timeOffset;
      private float timeRate;
      private boolean crown;
      private float thickness;
      private float bold;
      private int mode;

      private SceneWatch() {
      }

      private SceneWatch(float base, float thickness, float bold, boolean crown, int mode) {
         this.base = base;
         this.thickness = thickness;
         this.bold = bold;
         this.crown = crown;
         this.mode = mode;
      }
   }

   private static final class Vec3 {
      private float x;
      private float y;
      private float z;

      private Vec3() {
      }

      private Vec3(float x, float y, float z) {
         this.set(x, y, z);
      }

      private void set(float x, float y, float z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }
   }

   private record Wave(float startTime) {
   }
}
