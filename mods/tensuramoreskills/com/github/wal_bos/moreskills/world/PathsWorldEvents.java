package com.github.wal_bos.moreskills.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;

public class PathsWorldEvents {
   public static final ResourceKey<Level> PATHS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "paths"));

   public static void onChangeDim(PlayerChangedDimensionEvent event) {
      if (PATHS.equals(event.getTo())) {
         ;
      }
   }
}
