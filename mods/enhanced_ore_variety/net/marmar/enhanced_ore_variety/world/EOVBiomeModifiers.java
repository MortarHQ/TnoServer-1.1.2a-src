package net.marmar.enhanced_ore_variety.world;

import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.RemoveFeaturesBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class EOVBiomeModifiers {
   public static final ResourceKey<BiomeModifier> REMOVE_COPPER_ORE = registerKey("remove_copper_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_COPPER_LARGE_ORE = registerKey("remove_copper_large_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_COAL_UPPER_ORE = registerKey("remove_coal_upper_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_COAL_LOWER_ORE = registerKey("remove_coal_lower_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_IRON_UPPER_ORE = registerKey("remove_iron_upper_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_IRON_MIDDLE_ORE = registerKey("remove_iron_middle_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_IRON_SMALL_ORE = registerKey("remove_iron_small_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_GOLD_ORE_EXTRA = registerKey("remove_gold_ore_extra");
   public static final ResourceKey<BiomeModifier> REMOVE_GOLD_ORE = registerKey("remove_gold_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_GOLD_ORE_LOWER = registerKey("remove_gold_ore_lower");
   public static final ResourceKey<BiomeModifier> REMOVE_NETHER_GOLD_ORE = registerKey("remove_nether_gold_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_QUARTZ_ORE = registerKey("remove_quartz_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_REDSTONE_ORE = registerKey("remove_redstone_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_REDSTONE_LOWER_ORE = registerKey("remove_redstone_lower_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_LAPIS_ORE = registerKey("remove_lapis_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_LAPIS_BURIED_ORE = registerKey("remove_lapis_buried_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_EMERALD_ORE = registerKey("remove_emerald_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_DIAMOND_ORE = registerKey("remove_diamond_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_DIAMOND_LARGE_ORE = registerKey("remove_diamond_large_ore");
   public static final ResourceKey<BiomeModifier> REMOVE_DIAMOND_BURIED_ORE = registerKey("remove_diamond_buried_ore");
   public static final ResourceKey<BiomeModifier> ADD_COPPER_ORE = registerKey("add_copper_ore");
   public static final ResourceKey<BiomeModifier> ADD_COPPER_LARGE_ORE = registerKey("add_copper_large_ore");
   public static final ResourceKey<BiomeModifier> ADD_COAL_UPPER_ORE = registerKey("add_coal_upper_ore");
   public static final ResourceKey<BiomeModifier> ADD_COAL_LOWER_ORE = registerKey("add_coal_lower_ore");
   public static final ResourceKey<BiomeModifier> ADD_IRON_UPPER_ORE = registerKey("add_iron_upper_ore");
   public static final ResourceKey<BiomeModifier> ADD_IRON_MIDDLE_ORE = registerKey("add_iron_middle_ore");
   public static final ResourceKey<BiomeModifier> ADD_IRON_SMALL_ORE = registerKey("add_iron_small_ore");
   public static final ResourceKey<BiomeModifier> ADD_GOLD_ORE_EXTRA = registerKey("add_gold_ore_extra");
   public static final ResourceKey<BiomeModifier> ADD_GOLD_ORE = registerKey("add_gold_ore");
   public static final ResourceKey<BiomeModifier> ADD_GOLD_ORE_LOWER = registerKey("add_gold_ore_lower");
   public static final ResourceKey<BiomeModifier> ADD_NETHER_GOLD_ORE = registerKey("add_nether_gold_ore");
   public static final ResourceKey<BiomeModifier> ADD_QUARTZ_ORE = registerKey("add_quartz_ore");
   public static final ResourceKey<BiomeModifier> ADD_REDSTONE_ORE = registerKey("add_redstone_ore");
   public static final ResourceKey<BiomeModifier> ADD_REDSTONE_LOWER_ORE = registerKey("add_redstone_lower_ore");
   public static final ResourceKey<BiomeModifier> ADD_LAPIS_ORE = registerKey("add_lapis_ore");
   public static final ResourceKey<BiomeModifier> ADD_LAPIS_BURIED_ORE = registerKey("add_lapis_buried_ore");
   public static final ResourceKey<BiomeModifier> ADD_EMERALD_ORE = registerKey("add_emerald_ore");
   public static final ResourceKey<BiomeModifier> ADD_DIAMOND_ORE = registerKey("add_diamond_ore");
   public static final ResourceKey<BiomeModifier> ADD_DIAMOND_LARGE_ORE = registerKey("add_diamond_large_ore");
   public static final ResourceKey<BiomeModifier> ADD_DIAMOND_BURIED_ORE = registerKey("add_diamond_buried_ore");

   public static void bootstrap(BootstrapContext<BiomeModifier> context) {
      removeVanillaOres(context);
      addEnhancedOres(context);
   }

   public static void removeVanillaOres(BootstrapContext<BiomeModifier> pContext) {
      HolderGetter<PlacedFeature> placedFeatures = pContext.lookup(Registries.PLACED_FEATURE);
      HolderGetter<Biome> biomes = pContext.lookup(Registries.BIOME);
      pContext.register(
         REMOVE_COPPER_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_COPPER)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_COPPER_LARGE_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_COPPER_LARGE)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_COAL_UPPER_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_COAL_UPPER)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_COAL_LOWER_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_COAL_LOWER)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_IRON_UPPER_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_IRON_UPPER)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_IRON_MIDDLE_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_IRON_MIDDLE)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_IRON_SMALL_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_IRON_SMALL)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_GOLD_ORE_EXTRA,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_GOLD_EXTRA)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_GOLD_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_GOLD)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_GOLD_ORE_LOWER,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_GOLD_LOWER)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_NETHER_GOLD_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_NETHER),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_GOLD_NETHER)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_QUARTZ_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_NETHER),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_QUARTZ_NETHER)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_REDSTONE_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_REDSTONE)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_REDSTONE_LOWER_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_REDSTONE_LOWER)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_LAPIS_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_LAPIS)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_LAPIS_BURIED_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_LAPIS_BURIED)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_EMERALD_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_EMERALD)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_DIAMOND_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_DIAMOND)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_DIAMOND_LARGE_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_DIAMOND_LARGE)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
      pContext.register(
         REMOVE_DIAMOND_BURIED_ORE,
         new RemoveFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_DIAMOND_BURIED)}),
            Set.of(Decoration.UNDERGROUND_ORES)
         )
      );
   }

   public static void addEnhancedOres(BootstrapContext<BiomeModifier> pContext) {
      HolderGetter<PlacedFeature> placedFeatures = pContext.lookup(Registries.PLACED_FEATURE);
      HolderGetter<Biome> biomes = pContext.lookup(Registries.BIOME);
      pContext.register(
         ADD_COAL_UPPER_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.UPPER_COAL_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_COAL_LOWER_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.LOWER_COAL_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_COPPER_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.COPPER_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_COPPER_LARGE_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.LARGE_COPPER_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_IRON_UPPER_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.UPPER_IRON_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_IRON_MIDDLE_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.MIDDLE_IRON_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_IRON_SMALL_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.SMALL_IRON_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_GOLD_ORE_EXTRA,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_BADLANDS),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.EXTRA_GOLD_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_GOLD_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.GOLD_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_GOLD_ORE_LOWER,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.LOWER_GOLD_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_NETHER_GOLD_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_NETHER),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_GOLD_NETHER)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_QUARTZ_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_NETHER),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(OrePlacements.ORE_QUARTZ_NETHER)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_REDSTONE_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.REDSTONE_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_REDSTONE_LOWER_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.LOWER_REDSTONE_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_LAPIS_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.LAPIS_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_LAPIS_BURIED_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.LAPIS_BURIED_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_EMERALD_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.EMERALD_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_DIAMOND_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.DIAMOND_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_DIAMOND_LARGE_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.LARGE_DIAMOND_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
      pContext.register(
         ADD_DIAMOND_BURIED_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(EOVPlacedFeatures.BURIED_DIAMOND_ORES_PLACED)}),
            Decoration.UNDERGROUND_ORES
         )
      );
   }

   private static ResourceKey<BiomeModifier> registerKey(String name) {
      return ResourceKey.create(Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath("enhanced_ore_variety", name));
   }
}
