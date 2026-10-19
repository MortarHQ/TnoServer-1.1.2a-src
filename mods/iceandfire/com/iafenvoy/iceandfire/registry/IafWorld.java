package com.iafenvoy.iceandfire.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class IafWorld {
   public static final ResourceKey<Level> DREAD_LAND = ResourceKey.create(
      Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("iceandfire", "dread_land")
   );
}
