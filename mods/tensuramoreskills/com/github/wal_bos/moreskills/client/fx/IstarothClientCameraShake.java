package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;

public final class IstarothClientCameraShake {
   private static float intensity;
   private static float targetIntensity;
   private static int age;

   private IstarothClientCameraShake() {
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      Player player = minecraft.player;
      if (player == null) {
         intensity = 0.0F;
         targetIntensity = 0.0F;
         age = 0;
      } else {
         MobEffectInstance effect = player.getEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN));
         if (effect != null && effect.getAmplifier() >= 6) {
            int amp = effect.getAmplifier();
            int duration = effect.getDuration();
            float base = amp >= 7 ? 1.35F : 0.55F;
            if (duration <= 120) {
               base += 0.3F;
            }

            if (duration <= 80) {
               base += 0.45F;
            }

            if (duration <= 40) {
               base += 0.65F;
            }

            if (duration <= 20) {
               base += 0.85F;
            }

            targetIntensity = base;
         } else {
            targetIntensity = 0.0F;
         }

         intensity = intensity + (targetIntensity - intensity) * 0.22F;
         if (intensity < 0.01F) {
            intensity = 0.0F;
            age = 0;
         } else {
            age++;
         }
      }
   }

   public static void onCameraAngles(ComputeCameraAngles event) {
      if (!(intensity <= 0.0F)) {
         float t = age + (float)event.getPartialTick();
         float pitchShake = Mth.sin(t * 1.95F) * 0.7F + Mth.sin(t * 4.7F) * 0.34F + Mth.sin(t * 11.2F) * 0.16F;
         float yawShake = Mth.cos(t * 1.55F) * 0.8F + Mth.sin(t * 5.4F) * 0.28F + Mth.cos(t * 10.1F) * 0.14F;
         float rollShake = Mth.sin(t * 2.8F) * 1.15F + Mth.cos(t * 8.25F) * 0.35F;
         event.setPitch(event.getPitch() + pitchShake * intensity);
         event.setYaw(event.getYaw() + yawShake * intensity);
         event.setRoll(event.getRoll() + rollShake * intensity);
      }
   }
}
