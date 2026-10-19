package io.github.Memoires.mysticism.registry.world;

import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class MysticismPlacedFeatures {
   public static final ResourceKey<PlacedFeature> FIRE_BLOB = create("fire_blob");
   public static final ResourceKey<PlacedFeature> WATER_BLOB = create("water_blob");
   public static final ResourceKey<PlacedFeature> EARTH_BLOB = create("earth_blob");
   public static final ResourceKey<PlacedFeature> WIND_BLOB = create("wind_blob");
   public static final ResourceKey<PlacedFeature> SPATIAL_BLOB = create("spatial_blob");
   public static final ResourceKey<PlacedFeature> CHORUS = create("chorus");
   public static final ResourceKey<PlacedFeature> LAVA_LAKE = create("lava_lake");
   public static final ResourceKey<PlacedFeature> PODZOL_LAKE = create("podzol_lake");
   public static final ResourceKey<PlacedFeature> COARSE_LAKE = create("coarse_lake");
   public static final ResourceKey<PlacedFeature> KAMUI_CUBE = create("kamui_cube");
   public static final ResourceKey<PlacedFeature> ICE_ORE = create("ice_ore");

   public static void bootstrap(BootstrapContext<PlacedFeature> context) {
      HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
      PlacementUtils.register(
         context,
         FIRE_BLOB,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.FIRE_BLOB),
         List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         WATER_BLOB,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.WATER_BLOB),
         List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         EARTH_BLOB,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.EARTH_BLOB),
         List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         WIND_BLOB,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.WIND_BLOB),
         List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         SPATIAL_BLOB,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.SPATIAL_BLOB),
         List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         CHORUS,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.CHORUS),
         List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         LAVA_LAKE,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.LAVA_LAKE),
         List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         PODZOL_LAKE,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.PODZOL_LAKE),
         List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         COARSE_LAKE,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.COARSE_LAKE),
         List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         KAMUI_CUBE,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.KAMUI_CUBE),
         List.of(InSquarePlacement.spread(), CountPlacement.of(1), BiomeFilter.biome())
      );
      PlacementUtils.register(
         context,
         ICE_ORE,
         configuredFeatures.getOrThrow(MysticismConfiguredFeatures.ICE_ORE),
         List.of(
            CountPlacement.of(75),
            InSquarePlacement.spread(),
            BiomeFilter.biome(),
            HeightRangePlacement.triangle(VerticalAnchor.absolute(55), VerticalAnchor.absolute(100))
         )
      );
   }

   public static ResourceKey<PlacedFeature> create(String name) {
      return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }
}
