package com.chronodawn.core.dimension;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ChronoDawnBiomeProvider {
   public static final ResourceKey<Biome> CHRONO_DAWN_PLAINS = ResourceKey.create(
      Registries.BIOME, CompatResourceLocation.create("chronodawn", "chronodawn_plains")
   );
   public static final ResourceKey<Biome> CHRONO_DAWN_DESERT = ResourceKey.create(
      Registries.BIOME, CompatResourceLocation.create("chronodawn", "chronodawn_desert")
   );
   public static final ResourceKey<Biome> CHRONO_DAWN_PRAIRIES = ResourceKey.create(
      Registries.BIOME, CompatResourceLocation.create("chronodawn", "chronodawn_prairies")
   );
   public static final ResourceKey<Biome> CHRONO_DAWN_DARK_FOREST = ResourceKey.create(
      Registries.BIOME, CompatResourceLocation.create("chronodawn", "chronodawn_dark_forest")
   );
   public static final ResourceKey<Biome> CHRONO_DAWN_FADED_PLAINS = ResourceKey.create(
      Registries.BIOME, CompatResourceLocation.create("chronodawn", "chronodawn_faded_plains")
   );

   public static void init() {
      ChronoDawn.LOGGER.debug("ChronoDawn biome provider initialized (biomes defined via JSON datapacks)");
   }

   public static ResourceKey<Biome> getDefaultBiome() {
      return CHRONO_DAWN_PLAINS;
   }

   public static boolean isChronoDawnBiome(ResourceKey<Biome> biomeKey) {
      String namespace = biomeKey.location().getNamespace();
      return namespace.equals("chronodawn");
   }
}
