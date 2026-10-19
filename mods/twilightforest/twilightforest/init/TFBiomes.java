package twilightforest.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.enums.extensions.TFGrassColorModifierEnumExtension;
import twilightforest.world.registration.biomes.BiomeHelper;

public class TFBiomes {
   @Autowired
   private static TFGrassColorModifierEnumExtension grassColorModifierEnumExtension;
   public static final ResourceKey<Biome> FOREST = makeKey("forest");
   public static final ResourceKey<Biome> DENSE_FOREST = makeKey("dense_forest");
   public static final ResourceKey<Biome> FIREFLY_FOREST = makeKey("firefly_forest");
   public static final ResourceKey<Biome> CLEARING = makeKey("clearing");
   public static final ResourceKey<Biome> OAK_SAVANNAH = makeKey("oak_savannah");
   public static final ResourceKey<Biome> STREAM = makeKey("stream");
   public static final ResourceKey<Biome> LAKE = makeKey("lake");
   public static final ResourceKey<Biome> MUSHROOM_FOREST = makeKey("mushroom_forest");
   public static final ResourceKey<Biome> DENSE_MUSHROOM_FOREST = makeKey("dense_mushroom_forest");
   public static final ResourceKey<Biome> ENCHANTED_FOREST = makeKey("enchanted_forest");
   public static final ResourceKey<Biome> SPOOKY_FOREST = makeKey("spooky_forest");
   public static final ResourceKey<Biome> SWAMP = makeKey("swamp");
   public static final ResourceKey<Biome> FIRE_SWAMP = makeKey("fire_swamp");
   public static final ResourceKey<Biome> DARK_FOREST = makeKey("dark_forest");
   public static final ResourceKey<Biome> DARK_FOREST_CENTER = makeKey("dark_forest_center");
   public static final ResourceKey<Biome> SNOWY_FOREST = makeKey("snowy_forest");
   public static final ResourceKey<Biome> GLACIER = makeKey("glacier");
   public static final ResourceKey<Biome> HIGHLANDS = makeKey("highlands");
   public static final ResourceKey<Biome> HIGHLANDS_UNDERGROUND = makeKey("highlands_underground");
   public static final ResourceKey<Biome> THORNLANDS = makeKey("thornlands");
   public static final ResourceKey<Biome> FINAL_PLATEAU = makeKey("final_plateau");
   public static final ResourceKey<Biome> UNDERGROUND = makeKey("underground");

   private static ResourceKey<Biome> makeKey(String name) {
      return ResourceKey.create(Registries.BIOME, TwilightForestMod.prefix(name));
   }

   public static void bootstrap(BootstrapContext<Biome> context) {
      HolderGetter<PlacedFeature> featureGetter = context.lookup(Registries.PLACED_FEATURE);
      HolderGetter<ConfiguredWorldCarver<?>> carverGetter = context.lookup(Registries.CONFIGURED_CARVER);
      context.register(
         FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.fireflyParticles(BiomeHelper.defaultAmbientBuilder()),
               BiomeHelper.defaultMobSpawning(),
               BiomeHelper.twilightForestGen(featureGetter, carverGetter)
            )
            .build()
      );
      context.register(
         DENSE_FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.fireflyParticles(BiomeHelper.defaultAmbientBuilder()).waterColor(21794).waterFogColor(21794),
               BiomeHelper.defaultMobSpawning(),
               BiomeHelper.denseForestGen(featureGetter, carverGetter)
            )
            .temperature(0.7F)
            .downfall(0.8F)
            .build()
      );
      context.register(
         FIREFLY_FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.fireflyForestParticles(BiomeHelper.defaultAmbientBuilder()),
               BiomeHelper.defaultMobSpawning(),
               BiomeHelper.fireflyForestGen(featureGetter, carverGetter)
            )
            .temperature(0.5F)
            .downfall(1.0F)
            .build()
      );
      context.register(
         CLEARING,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder(), BiomeHelper.defaultMobSpawning(), BiomeHelper.clearingGen(featureGetter, carverGetter)
            )
            .temperature(0.8F)
            .downfall(0.4F)
            .build()
      );
      context.register(
         OAK_SAVANNAH,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder(), BiomeHelper.defaultMobSpawning(), BiomeHelper.oakSavannaGen(featureGetter, carverGetter)
            )
            .temperature(0.9F)
            .downfall(0.0F)
            .build()
      );
      context.register(
         MUSHROOM_FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.fireflyParticles(BiomeHelper.defaultAmbientBuilder()),
               BiomeHelper.defaultMobSpawning(),
               BiomeHelper.mushroomForestGen(featureGetter, carverGetter)
            )
            .temperature(0.8F)
            .downfall(0.8F)
            .build()
      );
      context.register(
         DENSE_MUSHROOM_FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.fireflyParticles(BiomeHelper.defaultAmbientBuilder()),
               BiomeHelper.defaultMobSpawning(),
               BiomeHelper.denseMushroomForestGen(featureGetter, carverGetter)
            )
            .temperature(0.8F)
            .downfall(1.0F)
            .build()
      );
      context.register(
         SPOOKY_FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder()
                  .grassColorOverride(12865827)
                  .foliageColorOverride(16745729)
                  .fogColor(8549265)
                  .waterColor(12355671)
                  .waterFogColor(12355671)
                  .grassColorModifier(grassColorModifierEnumExtension.SPOOKY_FOREST),
               BiomeHelper.spookSpawning(),
               BiomeHelper.spookyForestGen(featureGetter, carverGetter)
            )
            .temperature(0.5F)
            .downfall(1.0F)
            .build()
      );
      context.register(
         ENCHANTED_FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.fireflyParticles(BiomeHelper.defaultAmbientBuilder())
                  .foliageColorOverride(65535)
                  .grassColorOverride(65535)
                  .grassColorModifier(grassColorModifierEnumExtension.ENCHANTED_FOREST),
               BiomeHelper.defaultMobSpawning(),
               BiomeHelper.enchantedForestGen(featureGetter, carverGetter)
            )
            .hasPrecipitation(false)
            .build()
      );
      context.register(
         STREAM,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder(), BiomeHelper.defaultMobSpawning(), BiomeHelper.streamsAndLakes(featureGetter, carverGetter, false)
            )
            .temperature(0.5F)
            .downfall(0.1F)
            .build()
      );
      context.register(
         LAKE,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder(), BiomeHelper.defaultMobSpawning(), BiomeHelper.streamsAndLakes(featureGetter, carverGetter, true)
            )
            .temperature(0.66F)
            .downfall(1.0F)
            .build()
      );
      context.register(
         SWAMP,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder()
                  .skyColor(8466)
                  .fogColor(16161)
                  .grassColorOverride(6056270)
                  .foliageColorOverride(4809015)
                  .waterColor(9811295)
                  .waterFogColor(9811295)
                  .grassColorModifier(grassColorModifierEnumExtension.SWAMP),
               BiomeHelper.swampSpawning(),
               BiomeHelper.swampGen(featureGetter, carverGetter)
            )
            .temperature(0.8F)
            .downfall(0.9F)
            .build()
      );
      context.register(
         FIRE_SWAMP,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.whiteAshParticles(BiomeHelper.defaultAmbientBuilder())
                  .waterColor(2950912)
                  .fogColor(3672576)
                  .grassColorOverride(5713443)
                  .foliageColorOverride(6563343)
                  .waterColor(7089196)
                  .waterFogColor(7089196),
               new Builder(),
               BiomeHelper.fireSwampGen(featureGetter, carverGetter)
            )
            .hasPrecipitation(false)
            .temperature(1.0F)
            .downfall(0.4F)
            .build()
      );
      context.register(
         DARK_FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder()
                  .skyColor(0)
                  .fogColor(0)
                  .grassColorOverride(4941652)
                  .foliageColorOverride(3890751)
                  .grassColorModifier(grassColorModifierEnumExtension.DARK_FOREST),
               BiomeHelper.darkForestSpawning(),
               BiomeHelper.darkForestGen(featureGetter, carverGetter)
            )
            .temperature(0.7F)
            .downfall(0.8F)
            .build()
      );
      context.register(
         DARK_FOREST_CENTER,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder()
                  .skyColor(0)
                  .fogColor(4796416)
                  .grassColorOverride(6714688)
                  .foliageColorOverride(16351774)
                  .grassColorModifier(grassColorModifierEnumExtension.DARK_FOREST_CENTER),
               new Builder(),
               BiomeHelper.darkForestCenterGen(featureGetter, carverGetter)
            )
            .build()
      );
      context.register(
         SNOWY_FOREST,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder().skyColor(8421504).fogColor(16777215).foliageColorOverride(16777215).grassColorOverride(16777215),
               BiomeHelper.snowForestSpawning(),
               BiomeHelper.snowyForestGen(featureGetter, carverGetter)
            )
            .hasPrecipitation(true)
            .temperature(0.09F)
            .downfall(0.9F)
            .build()
      );
      context.register(
         GLACIER,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder().skyColor(1248552).fogColor(3547016),
               BiomeHelper.penguinSpawning(),
               BiomeHelper.glacierGen(featureGetter, carverGetter)
            )
            .temperature(0.08F)
            .downfall(0.1F)
            .hasPrecipitation(true)
            .build()
      );
      context.register(
         HIGHLANDS,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder(), BiomeHelper.defaultMobSpawning(), BiomeHelper.highlandsGen(featureGetter, carverGetter)
            )
            .temperature(0.4F)
            .downfall(0.7F)
            .build()
      );
      context.register(
         HIGHLANDS_UNDERGROUND,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder(), BiomeHelper.undergroundMobSpawning(), BiomeHelper.highlandsUndergroundGen(featureGetter, carverGetter)
            )
            .temperature(0.35F)
            .downfall(0.0F)
            .build()
      );
      context.register(
         THORNLANDS,
         BiomeHelper.biomeWithDefaults(BiomeHelper.defaultAmbientBuilder(), new Builder(), BiomeHelper.thornlandsGen(featureGetter, carverGetter))
            .temperature(0.3F)
            .downfall(0.2F)
            .build()
      );
      context.register(
         FINAL_PLATEAU,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder(),
               BiomeHelper.ravenSpawning(),
               new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder(featureGetter, carverGetter)
            )
            .temperature(1.0F)
            .downfall(0.2F)
            .build()
      );
      context.register(
         UNDERGROUND,
         BiomeHelper.biomeWithDefaults(
               BiomeHelper.defaultAmbientBuilder(), BiomeHelper.undergroundMobSpawning(), BiomeHelper.undergroundGen(featureGetter, carverGetter)
            )
            .temperature(0.7F)
            .downfall(0.0F)
            .build()
      );
   }
}
