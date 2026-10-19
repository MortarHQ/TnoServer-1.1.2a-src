package com.tno_tensura_extras.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import com.tno_tensura_extras.registry.ExtrasSounds;
import com.tno_tensura_extras.util.TimeTravelManager;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Pre;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

public final class TimeTravelClient {
   private static final long FADE_IN_NANOS = 1200000000L;
   private static final long FADE_OUT_NANOS = 1000000000L;
   private static final long FADE_OUT_TIMEOUT_NANOS = 10000000000L;
   private static final float NANOS_PER_SECOND = 1.0E9F;
   private static final float TWO_PI = (float) (Math.PI * 2);
   private static final float HALF_PI = (float) (Math.PI / 2);
   private static final float MINUTE_RADIANS_PER_SECOND = (float) (Math.PI / 6);
   private static final float HOUR_RADIANS_PER_SECOND = 0.043633234F;
   private static final int REOPEN_TIMEOUT_TICKS = 6000;
   private static final long TIME_SPACE_MUSIC_RETRY_NANOS = 250000000L;
   private static final TimeTravelClient.ClockSpec[] CLOCKS = new TimeTravelClient.ClockSpec[]{
      new TimeTravelClient.ClockSpec(0.07F, 0.15F, 0.1F, 0.018F, 0.23F, 0.4F, 0.95F, 0.88F, -1271183790),
      new TimeTravelClient.ClockSpec(0.28F, 0.18F, 0.14F, -0.026F, 0.17F, 2.2F, 0.84F, 0.86F, -1304013983),
      new TimeTravelClient.ClockSpec(0.48F, 0.08F, 0.065F, 0.05F, 0.3F, 4.4F, 1.12F, 0.9F, -1471391387),
      new TimeTravelClient.ClockSpec(0.7F, 0.16F, 0.09F, -0.04F, 0.2F, 0.8F, 0.72F, 0.82F, -1438888872),
      new TimeTravelClient.ClockSpec(0.94F, 0.1F, 0.15F, 0.016F, 0.24F, 3.2F, 1.18F, 0.86F, -1287961007),
      new TimeTravelClient.ClockSpec(0.1F, 0.52F, 0.17F, -0.018F, 0.16F, 5.3F, 0.88F, 0.84F, -1304145828),
      new TimeTravelClient.ClockSpec(0.5F, 0.5F, 0.27F, 0.012F, 0.1F, 1.4F, 1.0F, 0.92F, -534104768),
      new TimeTravelClient.ClockSpec(0.89F, 0.48F, 0.13F, 0.032F, 0.26F, 1.1F, 1.25F, 0.88F, -1321383338),
      new TimeTravelClient.ClockSpec(0.23F, 0.86F, 0.11F, 0.05F, 0.19F, 2.7F, 0.78F, 0.86F, -1455403430),
      new TimeTravelClient.ClockSpec(0.67F, 0.84F, 0.15F, -0.024F, 0.23F, 4.9F, 1.05F, 0.82F, -1304737708),
      new TimeTravelClient.ClockSpec(0.98F, 0.84F, 0.18F, 0.018F, 0.18F, 0.5F, 0.9F, 0.86F, -1388624302)
   };
   private static TimeTravelClient.Phase phase = TimeTravelClient.Phase.IDLE;
   private static float opacity;
   private static String levelId;
   private static boolean reopenRequested;
   private static boolean newWorldLoginSeen;
   private static int worldReadyTicks;
   private static int reopenWaitTicks;
   private static long phaseStartNanos;
   private static long animationStartNanos;
   private static long fadeOutElapsedNanos;
   private static long fadeOutLastRenderNanos;
   private static SoundInstance timeSpaceMusic;
   private static long timeSpaceMusicRetryNanos;

   private TimeTravelClient() {
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (phase == TimeTravelClient.Phase.IDLE && TimeTravelManager.isPending()) {
         beginTransition(minecraft);
      }

      if (phase != TimeTravelClient.Phase.IDLE) {
         if (!TimeTravelManager.isPending() && !isFadeOutActive() && !reopenRequested) {
            resetClientState();
         } else if (!finishFadeOutIfNeeded(System.nanoTime())) {
            suppressMusic(minecraft);
            updateOpacity();
            switch (phase) {
               case WAITING:
                  handleWaiting(minecraft);
               case IDLE:
               case FADE_IN:
               case FADE_OUT:
            }
         }
      }
   }

   private static void handleWaiting(Minecraft minecraft) {
      if (TimeTravelManager.isResetFailed() && !reopenRequested) {
         TimeTravelManager.cancelClientReset();
         resetClientState();
      } else {
         if ((TimeTravelManager.isResetReady() || newWorldLoginSeen)
            && reopenRequested
            && newWorldLoginSeen
            && minecraft.hasSingleplayerServer()
            && minecraft.level != null
            && minecraft.player != null
            && minecraft.getConnection() != null
            && minecraft.screen == null) {
            if (++worldReadyTicks >= 2) {
               startFadeOut();
               return;
            }
         } else {
            worldReadyTicks = 0;
         }

         if (reopenRequested && ++reopenWaitTicks > 6000) {
            TimeTravelManager.cancelClientReset();
            resetClientState();
         } else if (TimeTravelManager.isResetReady()
            && !reopenRequested
            && !minecraft.hasSingleplayerServer()
            && minecraft.level == null
            && levelId != null
            && !levelId.isBlank()) {
            reopenRequested = true;
            newWorldLoginSeen = false;
            worldReadyTicks = 0;
            reopenWaitTicks = 0;
            TimeTravelManager.markClientReopenStarted();

            try {
               minecraft.createWorldOpenFlows().openWorld(levelId, () -> {
                  TimeTravelManager.cancelClientReset();
                  resetClientState();
               });
            } catch (RuntimeException exception) {
               TimeTravelManager.cancelClientReset();
               resetClientState();
            }
         }
      }
   }

   public static void onRenderFramePre(Pre event) {
      if (phase == TimeTravelClient.Phase.IDLE && TimeTravelManager.isPending()) {
         beginTransition(Minecraft.getInstance());
      }

      finishFadeOutIfNeeded(System.nanoTime());
   }

   public static void onRenderGuiPre(net.neoforged.neoforge.client.event.RenderGuiEvent.Pre event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (isTransitionActive() || TimeTravelManager.isPending()) {
         if (phase == TimeTravelClient.Phase.IDLE) {
            beginTransition(minecraft);
         }

         suppressMusic(minecraft);
         event.setCanceled(true);
      }
   }

   public static void renderFinalFrameOverlay() {
      Minecraft minecraft = Minecraft.getInstance();
      if (isTransitionActive() || TimeTravelManager.isPending()) {
         if (phase == TimeTravelClient.Phase.IDLE) {
            beginTransition(minecraft);
         }

         suppressMusic(minecraft);
         updateOpacity();
         if (!(opacity <= 0.0F)) {
            Window window = minecraft.getWindow();
            float guiWidth = (float)(window.getWidth() / window.getGuiScale());
            float guiHeight = (float)(window.getHeight() / window.getGuiScale());
            if (!(guiWidth <= 0.0F) && !(guiHeight <= 0.0F)) {
               Matrix4f previousProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
               VertexSorting previousVertexSorting = RenderSystem.getVertexSorting();
               Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
               modelViewStack.pushMatrix();

               try {
                  RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0.0F, guiWidth, guiHeight, 0.0F, 1000.0F, 21000.0F), VertexSorting.ORTHOGRAPHIC_Z);
                  modelViewStack.translation(0.0F, 0.0F, -11000.0F);
                  RenderSystem.applyModelViewMatrix();
                  GuiGraphics graphics = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());
                  if (renderOverlay(graphics, opacity) && phase == TimeTravelClient.Phase.FADE_OUT) {
                     advanceFadeOutAfterRender();
                  }
               } catch (RuntimeException | LinkageError var11) {
               } finally {
                  modelViewStack.popMatrix();
                  RenderSystem.applyModelViewMatrix();
                  RenderSystem.setProjectionMatrix(previousProjection, previousVertexSorting);
               }
            }
         }
      }
   }

   public static void onScreenRenderPost(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if ((isTransitionActive() || TimeTravelManager.isPending()) && event.getScreen() == minecraft.screen) {
         if (phase == TimeTravelClient.Phase.IDLE) {
            beginTransition(minecraft);
         }

         suppressMusic(minecraft);
      }
   }

   public static void onSelectMusic(SelectMusicEvent event) {
      if (isTransitionActive() || TimeTravelManager.isPending()) {
         event.overrideMusic(null);
      }
   }

   public static void onClientPlayerLoggingIn(LoggingIn event) {
      if (isTransitionActive() && reopenRequested) {
         newWorldLoginSeen = true;
         worldReadyTicks = 0;
      }
   }

   private static boolean renderOverlay(GuiGraphics graphics, float overlayOpacity) {
      int alpha = Mth.clamp((int)(overlayOpacity * 255.0F), 0, 255);
      int color = alpha << 24 | 16777215;

      try {
         graphics.drawManaged(() -> {
            fillOverlay(graphics, 0, 0, graphics.guiWidth(), graphics.guiHeight(), color);

            try {
               drawTimeSpace(graphics, overlayOpacity);
            } catch (RuntimeException | LinkageError var4) {
            }
         });
         return true;
      } catch (RuntimeException | LinkageError ignored) {
         try {
            fillOverlay(graphics, 0, 0, graphics.guiWidth(), graphics.guiHeight(), color);
            graphics.flush();
            return true;
         } catch (RuntimeException | LinkageError ignoredAgain) {
            return false;
         }
      }
   }

   private static void fillOverlay(GuiGraphics graphics, int left, int top, int right, int bottom, int color) {
      graphics.fill(RenderType.guiOverlay(), left, top, right, bottom, color);
   }

   private static void beginTransition(Minecraft minecraft) {
      long now = System.nanoTime();
      phase = TimeTravelClient.Phase.FADE_IN;
      opacity = 0.0F;
      levelId = TimeTravelManager.getPendingLevelId();
      reopenRequested = false;
      newWorldLoginSeen = false;
      worldReadyTicks = 0;
      reopenWaitTicks = 0;
      phaseStartNanos = now;
      animationStartNanos = now;
      TimeTravelSceneRenderer.reset();
      suppressMusic(minecraft);
   }

   private static void updateOpacity() {
      if (phase == TimeTravelClient.Phase.FADE_IN) {
         float progress = progressSince(phaseStartNanos, 1200000000L);
         opacity = smoothStep(progress);
         if (progress >= 1.0F) {
            opacity = 1.0F;
            phase = TimeTravelClient.Phase.WAITING;
            phaseStartNanos = System.nanoTime();
         }
      } else if (phase == TimeTravelClient.Phase.WAITING) {
         opacity = 1.0F;
      }
   }

   private static void advanceFadeOutAfterRender() {
      if (phase == TimeTravelClient.Phase.FADE_OUT) {
         long now = System.nanoTime();
         if (fadeOutLastRenderNanos == 0L) {
            fadeOutLastRenderNanos = now;
         } else {
            long delta = now - fadeOutLastRenderNanos;
            fadeOutLastRenderNanos = now;
            if (delta < 0L) {
               delta = 0L;
            }

            fadeOutElapsedNanos = Math.min(1000000000L, fadeOutElapsedNanos + Math.min(delta, 100000000L));
            float progress = Mth.clamp((float)fadeOutElapsedNanos / 1.0E9F, 0.0F, 1.0F);
            opacity = 1.0F - smoothStep(progress);
            finishFadeOutIfNeeded(now);
         }
      }
   }

   private static boolean finishFadeOutIfNeeded(long now) {
      if (phase != TimeTravelClient.Phase.FADE_OUT) {
         return false;
      }

      if (opacity > 0.0F && fadeOutElapsedNanos < 1000000000L && now - phaseStartNanos < 10000000000L) {
         return false;
      }

      TimeTravelManager.completeClientReset();
      resetClientState();
      return true;
   }

   private static float progressSince(long startNanos, long durationNanos) {
      return startNanos == 0L ? 1.0F : Mth.clamp((float)(System.nanoTime() - startNanos) / (float)durationNanos, 0.0F, 1.0F);
   }

   private static float smoothStep(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static void startFadeOut() {
      if (phase != TimeTravelClient.Phase.FADE_OUT) {
         phase = TimeTravelClient.Phase.FADE_OUT;
         phaseStartNanos = System.nanoTime();
         opacity = 1.0F;
         fadeOutElapsedNanos = 0L;
         fadeOutLastRenderNanos = 0L;
      }
   }

   private static boolean isTransitionActive() {
      return phase != TimeTravelClient.Phase.IDLE;
   }

   private static boolean isFadeOutActive() {
      return phase == TimeTravelClient.Phase.FADE_OUT;
   }

   private static void suppressMusic(Minecraft minecraft) {
      try {
         minecraft.getMusicManager().stopPlaying();
      } catch (RuntimeException | LinkageError var4) {
      }

      try {
         TimeTravelClient.FancyMenuMusic.stop();
      } catch (RuntimeException | LinkageError var3) {
      }

      try {
         startTimeSpaceMusic(minecraft);
      } catch (RuntimeException | LinkageError ignored) {
         timeSpaceMusic = null;
         timeSpaceMusicRetryNanos = 0L;
      }
   }

   private static void startTimeSpaceMusic(Minecraft minecraft) {
      if (timeSpaceMusic != null) {
         try {
            if (minecraft.getSoundManager().isActive(timeSpaceMusic)) {
               return;
            }

            minecraft.getSoundManager().stop(timeSpaceMusic);
         } catch (RuntimeException var5) {
         }

         timeSpaceMusic = null;
      }

      long now = System.nanoTime();
      if (now >= timeSpaceMusicRetryNanos) {
         timeSpaceMusic = new SimpleSoundInstance(
            ExtrasSounds.TIME_SPACE_ID, SoundSource.MUSIC, 1.0F, 1.0F, SoundInstance.createUnseededRandom(), true, 0, Attenuation.NONE, 0.0, 0.0, 0.0, true
         );

         try {
            minecraft.getSoundManager().play(timeSpaceMusic);
            timeSpaceMusicRetryNanos = now + 250000000L;
         } catch (RuntimeException | LinkageError ignored) {
            timeSpaceMusic = null;
            timeSpaceMusicRetryNanos = now + 250000000L;
         }
      }
   }

   private static void stopTimeSpaceMusic(Minecraft minecraft) {
      if (timeSpaceMusic != null) {
         try {
            minecraft.getSoundManager().stop(timeSpaceMusic);
         } catch (RuntimeException | LinkageError var3) {
         }

         timeSpaceMusic = null;
      }

      try {
         minecraft.getSoundManager().stop(ExtrasSounds.TIME_SPACE_ID, SoundSource.MUSIC);
      } catch (RuntimeException | LinkageError var2) {
      }

      timeSpaceMusicRetryNanos = 0L;
   }

   private static void drawTimeSpace(GuiGraphics graphics, float overlayOpacity) {
      if (animationStartNanos != 0L) {
         float seconds = (float)(System.nanoTime() - animationStartNanos) / 1.0E9F;
         TimeTravelSceneRenderer.render(graphics, Minecraft.getInstance(), seconds, overlayOpacity);
      }
   }

   private static void drawOrbit(GuiGraphics graphics, float centerX, float centerY, float radiusX, float radiusY, float rotation, int color) {
      if (color >>> 24 != 0 && !(radiusX < 2.0F) && !(radiusY < 2.0F)) {
         int segments = 96;
         float cos = Mth.cos(rotation);
         float sin = Mth.sin(rotation);

         for (int i = 0; i < segments; i += 2) {
            float angle = (float) (Math.PI * 2) * i / segments;
            float localX = Mth.cos(angle) * radiusX;
            float localY = Mth.sin(angle) * radiusY;
            float x = centerX + localX * cos - localY * sin;
            float y = centerY + localX * sin + localY * cos;
            int pixelX = Mth.floor(x);
            int pixelY = Mth.floor(y);
            fillOverlay(graphics, pixelX, pixelY, pixelX + 2, pixelY + 2, color);
         }
      }
   }

   private static void drawClock(GuiGraphics graphics, float centerX, float centerY, int radius, float bodyRotation, float handTime, float ellipse, int color) {
      if (color >>> 24 != 0 && radius >= 2) {
         int shadowColor = multiplyAlpha(1428171316, (color >>> 24) / 255.0F);
         int faceColor = multiplyAlpha(486079487, (color >>> 24) / 255.0F);
         int innerColor = multiplyAlpha(2082943548, (color >>> 24) / 255.0F);
         int handColor = multiplyAlpha(-266590931, (color >>> 24) / 255.0F);
         graphics.pose().pushPose();
         graphics.pose().translate(centerX, centerY, 0.0F);
         graphics.pose().mulPose(Axis.ZP.rotation(bodyRotation));
         graphics.pose().scale(1.0F, ellipse, 1.0F);
         drawPixelDisk(graphics, radius - 2, faceColor);
         drawPixelRing(graphics, radius + 2, 2, shadowColor);
         drawPixelRing(graphics, radius, 2, color);
         drawPixelRing(graphics, Math.max(2, radius - 5), 1, innerColor);
         drawPocketWatchCrown(graphics, radius, color);

         for (int i = 0; i < 12; i++) {
            float angle = (float) (-Math.PI / 2) + (float) (Math.PI * 2) * i / 12.0F;
            float length = i % 3 == 0 ? radius * 0.15F : radius * 0.075F;
            float distance = radius - 5.0F - length * 0.5F;
            drawRadialBar(graphics, 0.0F, 0.0F, angle, distance, length, i % 3 == 0 ? 2.0F : 1.0F, innerColor);
         }

         float hourAngle = (float) (-Math.PI / 2) + handTime * 0.043633234F;
         float minuteAngle = (float) (-Math.PI / 2) + handTime * (float) (Math.PI / 6);
         float hourLength = radius * 0.38F;
         float minuteLength = radius * 0.62F;
         drawRadialBar(graphics, 0.0F, 0.0F, hourAngle, hourLength * 0.5F, hourLength, Math.max(2.0F, radius * 0.035F), handColor);
         drawRadialBar(graphics, 0.0F, 0.0F, minuteAngle, minuteLength * 0.5F, minuteLength, Math.max(1.0F, radius * 0.022F), handColor);
         int hubRadius = Math.max(1, radius / 18);
         fillOverlay(graphics, -hubRadius, -hubRadius, hubRadius + 1, hubRadius + 1, handColor);
         int hubInner = Math.max(1, hubRadius / 2);
         fillOverlay(graphics, -hubInner, -hubInner, hubInner + 1, hubInner + 1, color);
         graphics.pose().popPose();
      }
   }

   private static void drawPixelDisk(GuiGraphics graphics, int radius, int color) {
      if (radius >= 1 && color >>> 24 != 0) {
         int radiusSquared = radius * radius;

         for (int y = -radius; y <= radius; y++) {
            int x = Mth.floor((float)Math.sqrt(Math.max(0, radiusSquared - y * y)));
            fillOverlay(graphics, -x, y, x + 1, y + 1, color);
         }
      }
   }

   private static void drawPixelRing(GuiGraphics graphics, int radius, int thickness, int color) {
      if (radius >= 2 && thickness >= 1 && color >>> 24 != 0) {
         int innerRadius = Math.max(0, radius - thickness);
         int outerSquared = radius * radius;
         int innerSquared = innerRadius * innerRadius;

         for (int y = -radius; y <= radius; y++) {
            int ySquared = y * y;
            int outerX = Mth.floor((float)Math.sqrt(Math.max(0, outerSquared - ySquared)));
            if (ySquared >= innerSquared) {
               fillOverlay(graphics, -outerX, y, outerX + 1, y + 1, color);
            } else {
               int innerX = Mth.floor((float)Math.sqrt(Math.max(0, innerSquared - ySquared)));
               fillOverlay(graphics, -outerX, y, -innerX, y + 1, color);
               fillOverlay(graphics, innerX + 1, y, outerX + 1, y + 1, color);
            }
         }
      }
   }

   private static void drawPocketWatchCrown(GuiGraphics graphics, int radius, int color) {
      int stemWidth = Math.max(1, radius / 16);
      fillOverlay(graphics, -stemWidth, -radius - 5, stemWidth + 1, -radius + 1, color);
      int crownHalfWidth = Math.max(2, radius / 7);
      int crownHeight = Math.max(2, radius / 12);
      fillOverlay(graphics, -crownHalfWidth, -radius - 9, crownHalfWidth + 1, -radius - 9 + crownHeight, color);
      fillOverlay(graphics, -Math.max(2, crownHalfWidth - 2), -radius - 12, Math.max(2, crownHalfWidth - 2) + 1, -radius - 9, color);
   }

   private static void drawRadialBar(GuiGraphics graphics, float centerX, float centerY, float angle, float distance, float length, float thickness, int color) {
      graphics.pose().pushPose();
      graphics.pose().translate(centerX, centerY, 0.0F);
      graphics.pose().mulPose(Axis.ZP.rotation(angle));
      graphics.pose().translate(distance, 0.0F, 0.0F);
      int halfLength = Math.max(1, Mth.floor(length * 0.5F));
      int halfThickness = Math.max(1, Mth.floor(thickness * 0.5F));
      fillOverlay(graphics, -halfLength, -halfThickness, halfLength + 1, halfThickness + 1, color);
      graphics.pose().popPose();
   }

   private static int multiplyAlpha(int color, float factor) {
      int alpha = Mth.clamp((int)((color >>> 24 & 0xFF) * Mth.clamp(factor, 0.0F, 1.0F)), 0, 255);
      return alpha << 24 | color & 16777215;
   }

   private static void resetClientState() {
      stopTimeSpaceMusic(Minecraft.getInstance());
      phase = TimeTravelClient.Phase.IDLE;
      opacity = 0.0F;
      levelId = null;
      reopenRequested = false;
      newWorldLoginSeen = false;
      worldReadyTicks = 0;
      reopenWaitTicks = 0;
      phaseStartNanos = 0L;
      animationStartNanos = 0L;
      fadeOutElapsedNanos = 0L;
      fadeOutLastRenderNanos = 0L;
   }

   private record ClockSpec(float x, float y, float radiusScale, float rotationSpeed, float floatSpeed, float phase, float handSpeed, float ellipse, int color) {
   }

   private static final class FancyMenuMusic {
      private static final String GLOBAL_HANDLER = "de.keksuccino.fancymenu.customization.global.GlobalCustomizationHandler";
      private static final String AUDIO_BUILDER = "de.keksuccino.fancymenu.customization.element.elements.audio.AudioElementBuilder";
      private static boolean resolved;
      private static Method stopCurrentMenuMusic;
      private static Method stopAllActiveAudios;

      private static void stop() {
         resolve();
         invoke(stopCurrentMenuMusic);
         invoke(stopAllActiveAudios);
      }

      private static void resolve() {
         if (!resolved) {
            resolved = true;

            try {
               ClassLoader loader = TimeTravelClient.class.getClassLoader();
               Class<?> globalClass = Class.forName("de.keksuccino.fancymenu.customization.global.GlobalCustomizationHandler", false, loader);
               stopCurrentMenuMusic = globalClass.getDeclaredMethod("stopCurrentMenuMusic");
               stopCurrentMenuMusic.setAccessible(true);
            } catch (ReflectiveOperationException | RuntimeException | LinkageError ignored) {
               stopCurrentMenuMusic = null;
            }

            try {
               ClassLoader loader = TimeTravelClient.class.getClassLoader();
               Class<?> audioClass = Class.forName("de.keksuccino.fancymenu.customization.element.elements.audio.AudioElementBuilder", false, loader);
               stopAllActiveAudios = audioClass.getMethod("stopAllActiveAudios");
            } catch (ReflectiveOperationException | RuntimeException | LinkageError ignored) {
               stopAllActiveAudios = null;
            }
         }
      }

      private static void invoke(Method method) {
         if (method != null) {
            try {
               method.invoke(null);
            } catch (ReflectiveOperationException | RuntimeException | LinkageError var2) {
            }
         }
      }
   }

   private enum Phase {
      IDLE,
      FADE_IN,
      WAITING,
      FADE_OUT;
   }
}
