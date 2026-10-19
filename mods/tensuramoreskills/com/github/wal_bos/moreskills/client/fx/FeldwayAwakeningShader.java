package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

public final class FeldwayAwakeningShader {
   private static final ResourceLocation SHADER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "shaders/post/feldway_awakening.json");
   private static boolean active;
   private static boolean failedThisEffect;
   private static int retryCooldown;

   private FeldwayAwakeningShader() {
   }

   public static void onClientTick(Post event) {
      tick();
   }

   private static void tick() {
      Minecraft minecraft = Minecraft.getInstance();
      Player player = minecraft.player;
      if (player != null && minecraft.level != null) {
         boolean shouldBeActive = false;

         for (Entity entity : minecraft.level.entitiesForRendering()) {
            if (entity instanceof FeldwayAwakeningEntity awakening && awakening.isAlive() && awakening.distanceToSqr(player) <= 52900.0) {
               shouldBeActive = true;
               break;
            }
         }

         if (!shouldBeActive && FeldwayAvatarBeamClient.shouldUseAvatarShader(player)) {
            shouldBeActive = true;
         }

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
            ResourceLocation programJson = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "shaders/program/feldway_awakening.json");
            ResourceLocation fragment = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "shaders/program/feldway_awakening.fsh");
            boolean postExists = minecraft.getResourceManager().getResource(SHADER).isPresent();
            boolean programExists = minecraft.getResourceManager().getResource(programJson).isPresent();
            boolean fragmentExists = minecraft.getResourceManager().getResource(fragment).isPresent();
            if (postExists && programExists && fragmentExists) {
               try {
                  minecraft.gameRenderer.loadEffect(SHADER);
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
