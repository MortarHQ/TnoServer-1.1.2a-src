package com.github.wal_bos.moreskills.client.fx;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

public class PathsClientEffectsRegister {
   public static final ResourceLocation PATHS_EFFECTS_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "paths");

   public static void registerEffects(RegisterDimensionSpecialEffectsEvent event) {
      event.register(PATHS_EFFECTS_ID, new PathsDimensionEffects());
   }
}
