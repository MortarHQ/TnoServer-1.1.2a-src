package com.github.wal_bos.moreskills.client.fx;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class PathsClientState {
   public static final ResourceKey<Level> PATHS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "paths"));
   private static PathsClientState.FlashMode flashMode = PathsClientState.FlashMode.NONE;
   private static int flashTicks = 0;
   private static int flashDuration = 0;
   private static float flashStrength = 1.0F;
   private static int manualShakeTicks = 0;
   private static int manualShakeDuration = 0;
   private static float manualShakeStrength = 0.0F;
   private static float manualShakeImpact = 0.0F;
   public static float flashAlpha = 0.0F;
   public static float ambience = 0.0F;
   public static ResourceKey<Level> lastDimension = null;

   public static void triggerWhiteFlash(int ticks) {
      triggerFlash(PathsClientState.FlashMode.WHITE, ticks, 1.0F);
   }

   public static void triggerBlackFlash(int ticks) {
      triggerFlash(PathsClientState.FlashMode.BLACK, ticks, 1.0F);
   }

   public static void triggerFlash(PathsClientState.FlashMode mode, int ticks, float strength) {
      if (mode != null && mode != PathsClientState.FlashMode.NONE) {
         flashMode = mode;
         flashDuration = Math.max(1, ticks);
         flashTicks = flashDuration;
         flashStrength = Mth.clamp(strength, 0.0F, 1.0F);
      }
   }

   public static void triggerManualShake(int ticks, float strength, float impact) {
      manualShakeDuration = Math.max(1, ticks);
      manualShakeTicks = manualShakeDuration;
      manualShakeStrength = Math.max(manualShakeStrength, Mth.clamp(strength, 0.0F, 20.0F));
      manualShakeImpact = Math.max(manualShakeImpact, Mth.clamp(impact, 0.0F, 20.0F));
      if (manualShakeStrength > 0.0F) {
         FeldwayAwakeningCameraShake.avatarPulse(manualShakeStrength, manualShakeDuration);
      }

      if (manualShakeImpact > 0.0F) {
         FeldwayAwakeningCameraShake.avatarImpact(manualShakeImpact, Math.max(1, Math.min(manualShakeDuration, 30)));
      }
   }

   public static PathsClientState.FlashMode getFlashMode() {
      return flashMode;
   }

   public static void tick(boolean inPaths) {
      ambience = Mth.lerp(inPaths ? 0.035F : 0.08F, ambience, inPaths ? 1.0F : 0.0F);
      tickFlash();
      tickManualShake();
   }

   private static void tickFlash() {
      if (flashMode == PathsClientState.FlashMode.NONE) {
         flashAlpha = Mth.lerp(0.2F, flashAlpha, 0.0F);
      } else {
         if (flashTicks > 0) {
            float progress = 1.0F - (float)flashTicks / flashDuration;
            float envelope = progress < 0.18F ? progress / 0.18F : 1.0F - (progress - 0.18F) / 0.82F;
            envelope = Mth.clamp(envelope, 0.0F, 1.0F);
            flashAlpha = Math.max(flashAlpha, envelope * flashStrength);
            flashTicks--;
         } else {
            flashMode = PathsClientState.FlashMode.NONE;
         }

         if (flashMode == PathsClientState.FlashMode.NONE) {
            flashAlpha = Mth.lerp(0.25F, flashAlpha, 0.0F);
         }
      }
   }

   private static void tickManualShake() {
      if (manualShakeTicks > 0) {
         manualShakeTicks--;
         float progress = (float)manualShakeTicks / Math.max(1, manualShakeDuration);
         float fadedStrength = manualShakeStrength * Mth.clamp(progress, 0.0F, 1.0F);
         if (manualShakeTicks % 8 == 0 && fadedStrength > 0.025F) {
            FeldwayAwakeningCameraShake.avatarPulse(fadedStrength, 12);
         }

         if (manualShakeTicks <= 0) {
            manualShakeDuration = 0;
            manualShakeStrength = 0.0F;
            manualShakeImpact = 0.0F;
         }
      }
   }

   public enum FlashMode {
      NONE,
      WHITE,
      BLACK;
   }
}
