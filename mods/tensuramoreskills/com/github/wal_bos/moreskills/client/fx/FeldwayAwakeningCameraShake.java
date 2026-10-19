package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;

public final class FeldwayAwakeningCameraShake {
   private static final Map<UUID, Float> LAST_PROGRESS = new HashMap<>();
   private static final Map<UUID, Float> HIT_PULSE = new HashMap<>();
   private static float intensity;
   private static float targetIntensity;
   private static float impactIntensity;
   private static float avatarRumble;
   private static float avatarImpact;
   private static int avatarRumbleTicks;
   private static int avatarImpactTicks;
   private static int age;

   private FeldwayAwakeningCameraShake() {
   }

   public static void avatarPulse(float strength, int ticks) {
      avatarRumble = Math.max(avatarRumble, strength);
      avatarRumbleTicks = Math.max(avatarRumbleTicks, ticks);
   }

   public static void avatarImpact(float strength, int ticks) {
      avatarImpact = Math.max(avatarImpact, strength);
      avatarImpactTicks = Math.max(avatarImpactTicks, ticks);
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      Player player = minecraft.player;
      if (minecraft.level != null && player != null) {
         List<FeldwayAwakeningEntity> awakenings = minecraft.level
            .getEntitiesOfClass(FeldwayAwakeningEntity.class, player.getBoundingBox().inflate(240.0), e -> e != null && e.isAlive());
         Set<UUID> seen = new HashSet<>();
         float best = 0.0F;
         float impactBest = 0.0F;

         for (FeldwayAwakeningEntity awakening : awakenings) {
            UUID id = awakening.getUUID();
            seen.add(id);
            float progress = awakening.getAwakeningProgress();
            float prev = LAST_PROGRESS.getOrDefault(id, progress);
            float pulse = HIT_PULSE.getOrDefault(id, 0.0F);
            if (prev < 0.79F && progress >= 0.79F) {
               pulse = Math.max(pulse, 0.85F);
            }

            if (prev < 0.865F && progress >= 0.865F) {
               pulse = Math.max(pulse, 3.6F);
            }

            if (prev < 0.9F && progress >= 0.9F) {
               pulse = Math.max(pulse, 1.6F);
            }

            if (prev < 0.96F && progress >= 0.96F) {
               pulse = Math.max(pulse, 2.9F);
            }

            pulse *= 0.86F;
            if (pulse < 0.02F) {
               pulse = 0.0F;
            }

            LAST_PROGRESS.put(id, progress);
            HIT_PULSE.put(id, pulse);
            double dist = player.distanceTo(awakening);
            float distanceFactor = (float)Mth.clamp(1.0 - dist / 240.0, 0.0, 1.0);
            float build = smooth(progress);
            float descent = pow3(Mth.clamp((progress - 0.79F) / 0.085F, 0.0F, 1.0F));
            float impact = smooth(Mth.clamp((progress - 0.865F) / 0.025F, 0.0F, 1.0F));
            float dominion = smooth(Mth.clamp((progress - 0.89F) / 0.07F, 0.0F, 1.0F));
            float coronation = smooth(Mth.clamp((progress - 0.925F) / 0.075F, 0.0F, 1.0F));
            float rumble = (0.06F + build * 1.7F + descent * 2.4F + impact * 3.2F + dominion * 3.8F + coronation * 4.6F) * distanceFactor;
            float slam = pulse * (6.8F + impact * 3.5F + coronation * 2.0F) * distanceFactor;
            best = Math.max(best, rumble + slam * 0.3F);
            impactBest = Math.max(impactBest, slam);
         }

         LAST_PROGRESS.keySet().removeIf(idx -> !seen.contains(idx));
         HIT_PULSE.keySet().removeIf(idx -> !seen.contains(idx));
         if (avatarRumbleTicks > 0) {
            avatarRumbleTicks--;
            best = Math.max(best, avatarRumble);
            avatarRumble *= 0.94F;
         } else {
            avatarRumble *= 0.82F;
         }

         if (avatarImpactTicks > 0) {
            avatarImpactTicks--;
            impactBest = Math.max(impactBest, avatarImpact);
            avatarImpact *= 0.88F;
         } else {
            avatarImpact *= 0.72F;
         }

         if (avatarRumble < 0.01F) {
            avatarRumble = 0.0F;
         }

         if (avatarImpact < 0.01F) {
            avatarImpact = 0.0F;
         }

         targetIntensity = best;
         intensity = intensity + (targetIntensity - intensity) * 0.18F;
         impactIntensity = impactIntensity + (impactBest - impactIntensity) * 0.52F;
         impactIntensity *= 0.82F;
         if (intensity < 0.01F && impactIntensity < 0.01F && avatarRumble <= 0.0F && avatarImpact <= 0.0F) {
            intensity = 0.0F;
            impactIntensity = 0.0F;
            age = 0;
         } else {
            age++;
         }
      } else {
         LAST_PROGRESS.clear();
         HIT_PULSE.clear();
         intensity = 0.0F;
         targetIntensity = 0.0F;
         impactIntensity = 0.0F;
         avatarRumble = 0.0F;
         avatarImpact = 0.0F;
         avatarRumbleTicks = 0;
         avatarImpactTicks = 0;
         age = 0;
      }
   }

   public static void onCameraAngles(ComputeCameraAngles event) {
      if (!(intensity <= 0.0F) || !(impactIntensity <= 0.0F)) {
         float t = age + (float)event.getPartialTick();
         float pitchShake = Mth.sin(t * 0.68F) * 0.5F + Mth.sin(t * 2.1F) * 0.42F + Mth.sin(t * 6.2F) * 0.2F;
         float yawShake = Mth.cos(t * 0.72F) * 0.58F + Mth.sin(t * 2.45F) * 0.38F + Mth.cos(t * 5.7F) * 0.18F;
         float rollShake = Mth.sin(t * 0.92F) * 0.86F + Mth.cos(t * 3.8F) * 0.38F + Mth.sin(t * 7.9F) * 0.16F;
         float impactPitch = Mth.sin(t * 13.0F) * 0.86F + Mth.sin(t * 21.0F) * 0.46F + Mth.cos(t * 31.0F) * 0.22F;
         float impactYaw = Mth.cos(t * 12.0F) * 0.94F + Mth.sin(t * 19.5F) * 0.42F + Mth.cos(t * 28.5F) * 0.2F;
         float impactRoll = Mth.sin(t * 14.0F) * 1.18F + Mth.cos(t * 23.0F) * 0.52F + Mth.sin(t * 34.0F) * 0.26F;
         event.setPitch(event.getPitch() + pitchShake * intensity + impactPitch * impactIntensity);
         event.setYaw(event.getYaw() + yawShake * intensity + impactYaw * impactIntensity);
         event.setRoll(event.getRoll() + rollShake * intensity + impactRoll * impactIntensity);
      }
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static float pow3(float value) {
      float inverse = 1.0F - value;
      return 1.0F - inverse * inverse * inverse;
   }
}
