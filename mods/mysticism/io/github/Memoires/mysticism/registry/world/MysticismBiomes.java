package io.github.Memoires.mysticism.registry.world;

import io.github.Memoires.mysticism.world.biome.elemental_realm.DarknessBiome;
import io.github.Memoires.mysticism.world.biome.elemental_realm.EarthBiome;
import io.github.Memoires.mysticism.world.biome.elemental_realm.FireBiome;
import io.github.Memoires.mysticism.world.biome.elemental_realm.LightBiome;
import io.github.Memoires.mysticism.world.biome.elemental_realm.SpatialBiome;
import io.github.Memoires.mysticism.world.biome.elemental_realm.WaterBiome;
import io.github.Memoires.mysticism.world.biome.elemental_realm.WindBiome;
import io.github.Memoires.mysticism.world.biome.kamui_dimension.KamuiBiome;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class MysticismBiomes {
   public static final ResourceKey<Biome> FIRE_BIOME = create("fire_biome");
   public static final ResourceKey<Biome> WATER_BIOME = create("water_biome");
   public static final ResourceKey<Biome> EARTH_BIOME = create("earth_biome");
   public static final ResourceKey<Biome> WIND_BIOME = create("wind_biome");
   public static final ResourceKey<Biome> SPACE_BIOME = create("space_biome");
   public static final ResourceKey<Biome> LIGHT_BIOME = create("light_biome");
   public static final ResourceKey<Biome> DARKNESS_BIOME = create("darkness_biome");
   public static final ResourceKey<Biome> KAMUI_BIOME = create("kamui_biome");

   public static void bootstrap(BootstrapContext<Biome> context) {
      context.register(FIRE_BIOME, FireBiome.create(context));
      context.register(WATER_BIOME, WaterBiome.create(context));
      context.register(EARTH_BIOME, EarthBiome.create(context));
      context.register(WIND_BIOME, WindBiome.create(context));
      context.register(SPACE_BIOME, SpatialBiome.create(context));
      context.register(LIGHT_BIOME, LightBiome.create(context));
      context.register(DARKNESS_BIOME, DarknessBiome.create(context));
      context.register(KAMUI_BIOME, KamuiBiome.create(context));
   }

   public static ResourceKey<Biome> create(String name) {
      return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }
}
