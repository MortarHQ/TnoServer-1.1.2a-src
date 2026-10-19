package com.chronodawn.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class PortalEffectHandler {
   private static ResourceKey<Level> lastDimension = null;

   public static void onClientTick() {
      Minecraft minecraft = Minecraft.getInstance();
      LocalPlayer player = minecraft.player;
      if (player != null && minecraft.level != null) {
         PortalFadeHandler.tick();
         checkDimensionChange(minecraft);
      }
   }

   private static void checkDimensionChange(Minecraft minecraft) {
      if (minecraft.level != null) {
         ResourceKey<Level> currentDim = minecraft.level.dimension();
         if (lastDimension != null && !lastDimension.equals(currentDim)) {
            PortalFadeHandler.triggerFadeIn();
         }

         lastDimension = currentDim;
      }
   }
}
