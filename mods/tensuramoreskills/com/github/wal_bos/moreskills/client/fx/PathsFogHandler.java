package com.github.wal_bos.moreskills.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;

public class PathsFogHandler {
   public static void fogColor(ComputeFogColor event) {
      Minecraft mc = Minecraft.getInstance();
      ClientLevel level = mc.level;
      if (level != null && level.dimension().equals(PathsClientState.PATHS)) {
         float ambience = PathsClientState.ambience;
         event.setRed(Mth.lerp(ambience, 0.07F, 0.04F));
         event.setGreen(Mth.lerp(ambience, 0.11F, 0.07F));
         event.setBlue(Mth.lerp(ambience, 0.18F, 0.12F));
      }
   }

   public static void fogDensity(RenderFog event) {
      Minecraft mc = Minecraft.getInstance();
      ClientLevel level = mc.level;
      if (level != null && level.dimension().equals(PathsClientState.PATHS)) {
         if (event.getMode() == FogMode.FOG_TERRAIN) {
            float ambience = PathsClientState.ambience;
            float near = Mth.lerp(ambience, 4000000.0F, 200000.0F);
            float far = Mth.lerp(ambience, 200000.0F, 2000000.0F);
            event.setNearPlaneDistance(near);
            event.setFarPlaneDistance(far);
            event.setCanceled(true);
         }
      }
   }
}
