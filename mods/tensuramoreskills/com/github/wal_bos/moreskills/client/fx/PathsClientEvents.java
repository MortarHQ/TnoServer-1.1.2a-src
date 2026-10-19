package com.github.wal_bos.moreskills.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Pre;

public class PathsClientEvents {
   private static boolean playedCurrentFlashSound = false;

   public static void onClientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc != null) {
         ClientLevel level = mc.level;
         boolean inPaths = level != null && level.dimension().equals(PathsClientState.PATHS);
         if (level != null) {
            if (PathsClientState.lastDimension == null) {
               PathsClientState.lastDimension = level.dimension();
            } else if (!level.dimension().equals(PathsClientState.lastDimension)) {
               if (level.dimension().equals(PathsClientState.PATHS)) {
                  PathsClientState.triggerWhiteFlash(40);
                  playedCurrentFlashSound = false;
               } else if (PathsClientState.lastDimension.equals(PathsClientState.PATHS)) {
                  PathsClientState.triggerBlackFlash(20);
                  playedCurrentFlashSound = false;
               }

               PathsClientState.lastDimension = level.dimension();
            }
         }

         PathsClientState.tick(inPaths);
         if (!playedCurrentFlashSound && PathsClientState.getFlashMode() != PathsClientState.FlashMode.NONE && mc.player != null) {
            playedCurrentFlashSound = true;
            if (PathsClientState.getFlashMode() == PathsClientState.FlashMode.WHITE) {
               mc.player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 0.55F, 1.45F);
               mc.player.playSound(SoundEvents.BEACON_POWER_SELECT, 0.35F, 1.65F);
            } else {
               mc.player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 0.45F, 0.65F);
               mc.player.playSound(SoundEvents.END_PORTAL_SPAWN, 0.22F, 0.55F);
            }
         }

         if (PathsClientState.getFlashMode() == PathsClientState.FlashMode.NONE) {
            playedCurrentFlashSound = false;
         }
      }
   }

   public static void renderFlash(Pre event) {
      if (!(PathsClientState.flashAlpha <= 0.01F)) {
         PathsFlashOverlay.render(event.getGuiGraphics(), PathsClientState.getFlashMode(), PathsClientState.flashAlpha);
      }
   }
}
