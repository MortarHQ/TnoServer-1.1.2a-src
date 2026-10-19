package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

public final class IstarothGrayShader {
   private static final ResourceLocation GRAY_SHADER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "shaders/post/istaroth_gray.json");
   private static boolean active;
   private static boolean failedThisEffect;
   private static int retryCooldown;

   private IstarothGrayShader() {
   }

   public static void onClientTick(Post event) {
      tick();
   }

   private static void tick() {
      Minecraft minecraft = Minecraft.getInstance();
      Player player = minecraft.player;
      if (player != null && minecraft.level != null) {
         MobEffectInstance effect = player.getEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN));
         boolean shouldBeActive = effect != null && effect.getAmplifier() >= 6;
         if (shouldBeActive) {
            if (!failedThisEffect) {
               enable(minecraft);
            }
         } else {
            disable(minecraft);
            failedThisEffect = false;
            retryCooldown = 0;
         }
      } else {
         disable(minecraft);
         failedThisEffect = false;
         retryCooldown = 0;
      }
   }

   private static void enable(Minecraft minecraft) {
      if (!active) {
         if (retryCooldown-- <= 0) {
            ResourceLocation programJson = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "shaders/program/istaroth_gray.json");
            ResourceLocation fragment = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "shaders/program/istaroth_gray.fsh");
            boolean postExists = minecraft.getResourceManager().getResource(GRAY_SHADER).isPresent();
            boolean programExists = minecraft.getResourceManager().getResource(programJson).isPresent();
            boolean fragmentExists = minecraft.getResourceManager().getResource(fragment).isPresent();
            if (postExists && programExists && fragmentExists) {
               try {
                  minecraft.gameRenderer.loadEffect(GRAY_SHADER);
                  if (minecraft.gameRenderer.currentEffect() != null) {
                     active = true;
                     failedThisEffect = false;
                  } else {
                     active = false;
                     retryCooldown = 40;
                  }
               } catch (Throwable ignored) {
                  active = false;
                  failedThisEffect = true;
               }
            } else {
               active = false;
               failedThisEffect = true;
            }
         }
      }
   }

   private static void disable(Minecraft minecraft) {
      if (active) {
         try {
            minecraft.gameRenderer.shutdownEffect();
         } catch (Throwable var2) {
         }

         active = false;
      }
   }
}
