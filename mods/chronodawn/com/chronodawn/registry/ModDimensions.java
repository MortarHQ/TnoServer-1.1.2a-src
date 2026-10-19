package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
   public static final ResourceKey<Level> CHRONO_DAWN_DIMENSION = ResourceKey.create(
      Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")
   );
   public static final ResourceKey<DimensionType> CHRONO_DAWN_DIMENSION_TYPE = ResourceKey.create(
      Registries.DIMENSION_TYPE, CompatResourceLocation.create("chronodawn", "chronodawn")
   );

   public static void register() {
      ChronoDawn.LOGGER.debug("Registered ModDimensions (keys prepared, JSON datapacks will define the dimension)");
   }
}
