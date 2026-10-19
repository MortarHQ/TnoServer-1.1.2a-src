package twilightforest.world.registration.biomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import twilightforest.init.TFCaveCarvers;
import twilightforest.init.TFConfiguredFeatures;
import twilightforest.init.TFEntities;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFPlacedFeatures;

public abstract class BiomeHelper {
   public static Builder twilightForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLUEBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_JUNGLE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_LARGE_FERN);
      addForestVegetationAlt(biome);
      commonFeatures(biome);
      addOreberries(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_VANILLA_TF_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_LARGE_TWILIGHT_OAK_TREE);
      addCanopyTrees(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MEGA_CANOPY_TREE);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_WATER);
      return biome;
   }

   public static Builder denseForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_RASPBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLUEBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLACKBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_JUNGLE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_LARGE_FERN);
      addForestVegetationAlt(biome);
      commonFeatures(biome);
      addOreberries(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DENSE_CANOPY_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_VANILLA_TF_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_OAK_BUSH_DENSE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_FOREST_MEGA_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MEGA_CANOPY_TREE);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_WATER);
      return biome;
   }

   public static Builder fireflyForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLACKBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_JUNGLE);
      addForestVegetationAlt(biome);
      commonFeatures(biome);
      addOreberries(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_FIREFLY_FOREST_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_VANILLA_TF_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_LARGE_TWILIGHT_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_LAMPPOST_PLACER);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MUSHGLOOM_CLUSTER);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.FLOWER_FOREST_FLOWERS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_WATER);
      return biome;
   }

   public static Builder clearingGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_RASPBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLUEBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_FOREST);
      addForestVegetation(biome);
      commonFeatures(biome);
      addOreberries(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.FLOWER_FOREST_FLOWERS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_WATER);
      return biome;
   }

   public static Builder oakSavannaGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_TAIGA_2);
      addForestVegetation(biome);
      commonFeatures(biome);
      addOreberries(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SAVANNAH_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_LARGE_TWILIGHT_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SAVANNAH_MEGA_OAK_TREE);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_WATER);
      return biome;
   }

   public static Builder enchantedForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      BiomeDefaultFeatures.addDefaultSoftDisks(biome);
      BiomeDefaultFeatures.addDefaultGrass(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
      BiomeDefaultFeatures.addSurfaceFreezing(biome);
      withWoodRoots(biome);
      addCaves(biome);
      addSmallStoneClusters(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DENSE_FERNS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DENSE_LARGE_FERNS);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_DENSE_LAKE_WATER);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_FLOWER_PLACER);
      commonFeatures(biome);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_IRON_OREBERRIES_ENCHANTED_FOREST);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_GOLD_OREBERRIES_ENCHANTED_FOREST);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_COPPER_OREBERRIES_ENCHANTED_FOREST);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_ESSENCE_OREBERRIES_ENCHANTED_FOREST);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_ENCHANTED_FOREST_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DENSE_CANOPY_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_FIDDLEHEAD);
      biome.addFeature(Decoration.TOP_LAYER_MODIFICATION, TFPlacedFeatures.PLACED_ENCHANTED_FOREST_VINES);
      addCanopyTrees(biome);
      return biome;
   }

   public static Builder spookyForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_JUNGLE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MAYAPPLE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_LARGE_TWILIGHT_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DEAD_CANOPY_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_PUMPKIN_LAMPPOST);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_TF_OAK_FALLEN_LOG);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_CANOPY_FALLEN_LOG);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_WEBS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_FALLEN_LEAVES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
      biome.addFeature(Decoration.SURFACE_STRUCTURES, TFPlacedFeatures.PLACED_GRAVEYARD);
      return biome;
   }

   public static Builder mushroomForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_RASPBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLUEBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLACKBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_JUNGLE);
      addForestVegetationAlt(biome);
      commonFeatures(biome);
      addOreberries(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MYCELIUM_BLOB);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_VANILLA_TF_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_LARGE_TWILIGHT_OAK_TREE);
      addCanopyMushrooms(biome, false);
      addCanopyTrees(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MEGA_CANOPY_TREE);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_WATER);
      return biome;
   }

   public static Builder denseMushroomForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLACKBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_JUNGLE);
      addForestVegetationAlt(biome);
      commonFeatures(biome);
      addOreberries(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MYCELIUM_BLOB);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_VANILLA_TF_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_LARGE_TWILIGHT_OAK_TREE);
      addCanopyMushrooms(biome, true);
      addCanopyTrees(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MEGA_CANOPY_TREE);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_WATER);
      return biome;
   }

   public static Builder thornlandsGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      commonFeaturesWithoutBuildings(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_THORNS);
      return biome;
   }

   public static Builder highlandsGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_BLUEBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
      commonFeatures(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_HIGHLANDS_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.FOREST_ROCK);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SPARSE_MUSHGLOOMS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SPRUCE_FALLEN_LOG);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_FERNS);
      addHighlandCaves(biome);
      addSmallStoneClusters(biome);
      return biome;
   }

   public static Builder highlandsUndergroundGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      addHighlandCaves(biome);
      return biome;
   }

   public static Builder streamsAndLakes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, boolean isLake) {
      Builder biome = new Builder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, isLake ? AquaticPlacements.SEAGRASS_DEEP : AquaticPlacements.SEAGRASS_NORMAL);
      BiomeDefaultFeatures.addDefaultSeagrass(biome);
      BiomeDefaultFeatures.addSurfaceFreezing(biome);
      addLegacyOres(biome);
      addOreberries(biome);
      addSmallStoneClusters(biome);
      return biome;
   }

   public static Builder swampGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SWAMP_RASPBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SWAMP_BLACKBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA_2);
      commonFeatures(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MANGROVE_TREE);
      addSwampTrees(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.VINES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MANGROVE_FALLEN_LOG);
      lilypads(biome);
      return biome;
   }

   public static Builder fireSwampGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = defaultGenSettingBuilder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA_2);
      commonFeaturesWithoutBuildings(biome);
      addSwampTrees(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_FIRE_JET);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SMOKER);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_LAVA);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.VINES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
      return biome;
   }

   public static Builder darkForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      addDarkForestVegetation(biome);
      addCaves(biome);
      return biome;
   }

   public static Builder darkForestCenterGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      return darkForestGen(featureGetter, carverGetter);
   }

   public static Builder snowyForestGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SNOWY_BLUEBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MALOBERRY_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SNOWY_FOREST_TREES);
      biome.addFeature(Decoration.TOP_LAYER_MODIFICATION, TFPlacedFeatures.PLACED_SNOW_UNDER_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SPRUCE_FALLEN_LOG);
      BiomeDefaultFeatures.addSurfaceFreezing(biome);
      addCaves(biome);
      biome.addFeature(Decoration.LAKES, TFPlacedFeatures.PLACED_LAKE_FROZEN);
      return biome;
   }

   public static Builder glacierGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      BiomeDefaultFeatures.addSurfaceFreezing(biome);
      addCaves(biome);
      return biome;
   }

   public static Builder undergroundGen(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      BiomeDefaultFeatures.addDefaultSoftDisks(biome);
      BiomeDefaultFeatures.addSurfaceFreezing(biome);
      withWoodRoots(biome);
      addCaves(biome);
      addSmallStoneClusters(biome);
      return biome;
   }

   public static void withWoodRoots(Builder biome) {
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_WOOD_ROOTS_SPREAD);
   }

   public static void commonFeatures(Builder biome) {
      biome.addFeature(Decoration.SURFACE_STRUCTURES, TFPlacedFeatures.PLACED_DRUID_HUT);
      biome.addFeature(Decoration.SURFACE_STRUCTURES, TFPlacedFeatures.PLACED_WELL_PLACER);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_GROVE_RUINS);
      biome.addFeature(Decoration.SURFACE_STRUCTURES, TFPlacedFeatures.PLACED_FOUNDATION);
      commonFeaturesWithoutBuildings(biome);
   }

   public static void commonFeaturesWithoutBuildings(Builder biome) {
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_STONE_CIRCLE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_OUTSIDE_STALAGMITE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MONOLITH);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_HOLLOW_STUMP);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_HOLLOW_LOG);
   }

   public static void lilypads(Builder biome) {
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_HUGE_LILY_PAD);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_HUGE_WATER_LILY);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
   }

   public static void addForestVegetation(Builder biome) {
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MAYAPPLE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_FLOWER_PLACER);
   }

   public static void addForestVegetationAlt(Builder biome) {
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MAYAPPLE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_FLOWER_PLACER_ALT);
   }

   public static void addDarkForestVegetation(Builder biome) {
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_FOREST_TREE_MIX);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_FOREST_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARKWOOD_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_GRASS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_DEAD_BUSHES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_PUMPKINS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_MUSHGLOOMS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_BROWN_MUSHROOMS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DARK_RED_MUSHROOMS);
   }

   public static void addCanopyTrees(Builder biome) {
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_CANOPY_TREES);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS);
   }

   public static void addCanopyMushrooms(Builder biome, boolean dense) {
      BiomeDefaultFeatures.addDefaultMushrooms(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_VANILLA_TF_BIG_MUSH);
      biome.addFeature(Decoration.VEGETAL_DECORATION, dense ? TFPlacedFeatures.PLACED_CANOPY_MUSHROOMS_DENSE : TFPlacedFeatures.PLACED_CANOPY_MUSHROOMS_SPARSE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_MUSHGLOOM_CLUSTER);
   }

   public static void addSwampTrees(Builder biome) {
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_SWAMPY_OAK_TREE);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_OAK_BUSH);
   }

   public static void addSmallStoneClusters(Builder biome) {
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_SMALL_ANDESITE);
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_SMALL_DIORITE);
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_SMALL_GRANITE);
   }

   public static net.minecraft.world.level.biome.BiomeSpecialEffects.Builder whiteAshParticles(
      net.minecraft.world.level.biome.BiomeSpecialEffects.Builder builder
   ) {
      builder.ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.05F));
      return builder;
   }

   public static net.minecraft.world.level.biome.BiomeSpecialEffects.Builder fireflyForestParticles(
      net.minecraft.world.level.biome.BiomeSpecialEffects.Builder builder
   ) {
      builder.ambientParticle(new AmbientParticleSettings((ParticleOptions)TFParticleType.WANDERING_FIREFLY.get(), 0.001F));
      return builder;
   }

   public static net.minecraft.world.level.biome.BiomeSpecialEffects.Builder fireflyParticles(
      net.minecraft.world.level.biome.BiomeSpecialEffects.Builder builder
   ) {
      builder.ambientParticle(new AmbientParticleSettings((ParticleOptions)TFParticleType.WANDERING_FIREFLY.get(), 2.5E-4F));
      return builder;
   }

   public static void addCaves(Builder biome) {
      biome.addCarver(Carving.AIR, TFCaveCarvers.TFCAVES_CONFIGURED);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_PLANT_ROOTS);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_TORCH_BERRIES);
      addLegacyOres(biome);
   }

   public static void addHighlandCaves(Builder biome) {
      biome.addCarver(Carving.AIR, TFCaveCarvers.HIGHLANDCAVES_CONFIGURED);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_TROLL_ROOTS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_TROLL_MUSHGLOOMS);
      addLegacyOres(biome);
   }

   public static void addLegacyOres(Builder biome) {
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_LEGACY_COAL_ORE);
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_LEGACY_IRON_ORE);
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_LEGACY_GOLD_ORE);
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_LEGACY_REDSTONE_ORE);
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_LEGACY_DIAMOND_ORE);
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_LEGACY_LAPIS_ORE);
      biome.addFeature(Decoration.UNDERGROUND_ORES, TFPlacedFeatures.PLACED_LEGACY_COPPER_ORE);
   }

   public static void addOreberries(Builder biome) {
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_IRON_OREBERRIES);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_GOLD_OREBERRIES);
      biome.addFeature(Decoration.UNDERGROUND_DECORATION, TFPlacedFeatures.PLACED_COPPER_OREBERRIES);
   }

   public static net.minecraft.world.level.biome.MobSpawnSettings.Builder penguinSpawning() {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder spawnInfo = new net.minecraft.world.level.biome.MobSpawnSettings.Builder();
      spawnInfo.creatureGenerationProbability(0.15F);
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.PENGUIN.get(), 10, 2, 4));
      return spawnInfo;
   }

   public static net.minecraft.world.level.biome.MobSpawnSettings.Builder darkForestSpawning() {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder spawnInfo = new net.minecraft.world.level.biome.MobSpawnSettings.Builder();
      spawnInfo.creatureGenerationProbability(0.05F);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.ENDERMAN, 2, 1, 2)).addMobCharge(EntityType.ENDERMAN, 0.75, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.ZOMBIE, 5, 1, 2)).addMobCharge(EntityType.ZOMBIE, 0.7, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.SKELETON, 5, 1, 2)).addMobCharge(EntityType.SKELETON, 0.8, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.MIST_WOLF.get(), 5, 1, 1))
         .addMobCharge((EntityType)TFEntities.MIST_WOLF.get(), 0.75, 0.2);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.SKELETON_DRUID.get(), 5, 1, 1))
         .addMobCharge((EntityType)TFEntities.SKELETON_DRUID.get(), 0.8, 0.2);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.KING_SPIDER.get(), 1, 1, 1))
         .addMobCharge((EntityType)TFEntities.KING_SPIDER.get(), 0.85, 0.25);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.KOBOLD.get(), 10, 1, 3))
         .addMobCharge((EntityType)TFEntities.KOBOLD.get(), 0.7, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.WITCH, 2, 1, 1)).addMobCharge(EntityType.WITCH, 0.75, 0.15);
      return spawnInfo;
   }

   public static net.minecraft.world.level.biome.MobSpawnSettings.Builder snowForestSpawning() {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder spawnInfo = new net.minecraft.world.level.biome.MobSpawnSettings.Builder();
      spawnInfo.creatureGenerationProbability(0.05F);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.WINTER_WOLF.get(), 5, 1, 1))
         .addMobCharge((EntityType)TFEntities.WINTER_WOLF.get(), 0.6, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.YETI.get(), 5, 1, 1))
         .addMobCharge((EntityType)TFEntities.YETI.get(), 0.6, 0.15);
      return spawnInfo;
   }

   public static net.minecraft.world.level.biome.MobSpawnSettings.Builder ravenSpawning() {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder spawnInfo = new net.minecraft.world.level.biome.MobSpawnSettings.Builder();
      spawnInfo.creatureGenerationProbability(0.3F);
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.RAVEN.get(), 10, 4, 4));
      return spawnInfo;
   }

   public static net.minecraft.world.level.biome.MobSpawnSettings.Builder swampSpawning() {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder spawnInfo = new net.minecraft.world.level.biome.MobSpawnSettings.Builder();
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.ZOMBIE, 10, 1, 2)).addMobCharge(EntityType.ZOMBIE, 0.7, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.MOSQUITO_SWARM.get(), 10, 1, 1))
         .addMobCharge((EntityType)TFEntities.MOSQUITO_SWARM.get(), 0.7, 0.15);
      return spawnInfo;
   }

   public static net.minecraft.world.level.biome.MobSpawnSettings.Builder spookSpawning() {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder spawnInfo = new net.minecraft.world.level.biome.MobSpawnSettings.Builder();
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.SPIDER, 50, 1, 2)).addMobCharge(EntityType.SPIDER, 0.75, 0.25);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.SKELETON, 20, 1, 2)).addMobCharge(EntityType.SKELETON, 0.85, 0.25);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.SKELETON_DRUID.get(), 5, 1, 1))
         .addMobCharge((EntityType)TFEntities.SKELETON_DRUID.get(), 0.95, 0.25);
      spawnInfo.addSpawn(MobCategory.AMBIENT, new SpawnerData(EntityType.BAT, 20, 2, 4));
      return spawnInfo;
   }

   public static net.minecraft.world.level.biome.BiomeSpecialEffects.Builder defaultAmbientBuilder() {
      return new net.minecraft.world.level.biome.BiomeSpecialEffects.Builder()
         .fogColor(12648408)
         .waterColor(4159204)
         .waterFogColor(329011)
         .skyColor(2105930)
         .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
         .backgroundMusic(TFConfiguredFeatures.TFMUSICTYPE);
   }

   public static Builder defaultGenSettingBuilder(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
      Builder biome = new Builder(featureGetter, carverGetter);
      BiomeDefaultFeatures.addDefaultSoftDisks(biome);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_TALL_GRASS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_BADLANDS);
      biome.addFeature(Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PATCH_GRASS_SAVANNA);
      biome.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
      BiomeDefaultFeatures.addSurfaceFreezing(biome);
      withWoodRoots(biome);
      addCaves(biome);
      addSmallStoneClusters(biome);
      return biome;
   }

   public static net.minecraft.world.level.biome.MobSpawnSettings.Builder defaultMobSpawning() {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder spawnInfo = new net.minecraft.world.level.biome.MobSpawnSettings.Builder();
      spawnInfo.creatureGenerationProbability(0.15F);
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.BIGHORN_SHEEP.get(), 12, 4, 4));
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.BOAR.get(), 10, 4, 4));
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData(EntityType.CHICKEN, 10, 4, 4));
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.DEER.get(), 15, 4, 5));
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData(EntityType.WOLF, 5, 4, 4));
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.TINY_BIRD.get(), 15, 4, 8));
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.SQUIRREL.get(), 10, 2, 4));
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.DWARF_RABBIT.get(), 10, 4, 5));
      spawnInfo.addSpawn(MobCategory.CREATURE, new SpawnerData((EntityType)TFEntities.RAVEN.get(), 10, 1, 2));
      return spawnInfo;
   }

   public static net.minecraft.world.level.biome.MobSpawnSettings.Builder undergroundMobSpawning() {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder spawnInfo = new net.minecraft.world.level.biome.MobSpawnSettings.Builder();
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.SPIDER, 10, 2, 3)).addMobCharge(EntityType.SPIDER, 0.2, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.ZOMBIE, 10, 1, 2)).addMobCharge(EntityType.ZOMBIE, 0.2, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.SKELETON, 10, 1, 1)).addMobCharge(EntityType.SKELETON, 0.3, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.CREEPER, 1, 1, 1)).addMobCharge(EntityType.CREEPER, 0.35, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.SLIME, 10, 2, 4)).addMobCharge(EntityType.SLIME, 0.2, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.ENDERMAN, 1, 1, 2)).addMobCharge(EntityType.ENDERMAN, 0.4, 0.15);
      spawnInfo.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TFEntities.KOBOLD.get(), 10, 1, 3))
         .addMobCharge((EntityType)TFEntities.KOBOLD.get(), 0.2, 0.15);
      spawnInfo.addSpawn(MobCategory.AMBIENT, new SpawnerData(EntityType.BAT, 10, 1, 1));
      return spawnInfo;
   }

   public static BiomeBuilder biomeWithDefaults(
      net.minecraft.world.level.biome.BiomeSpecialEffects.Builder biomeAmbience,
      net.minecraft.world.level.biome.MobSpawnSettings.Builder mobSpawnInfo,
      Builder biomeGenerationSettings
   ) {
      return new BiomeBuilder()
         .hasPrecipitation(true)
         .temperature(0.5F)
         .downfall(0.5F)
         .specialEffects(biomeAmbience.build())
         .mobSpawnSettings(mobSpawnInfo.build())
         .generationSettings(biomeGenerationSettings.build())
         .temperatureAdjustment(TemperatureModifier.NONE);
   }
}
