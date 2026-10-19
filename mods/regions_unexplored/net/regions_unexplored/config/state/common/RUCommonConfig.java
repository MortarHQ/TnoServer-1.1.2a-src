package net.regions_unexplored.config.state.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.BiomeInjector.ClimateParameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.regions_unexplored.RegionsUnexplored;
import net.regions_unexplored.config.json5.CommentedMapCodec;
import net.regions_unexplored.config.json5.CommentedUnboundedMapCodec;
import net.regions_unexplored.registry.data.RUBiomes;
import org.jetbrains.annotations.NotNull;

public class RUCommonConfig {
   public static final int CURRENT_VERSION = 1;
   public static final RUCommonConfig DEFAULT = new RUCommonConfig(
      1, RUCommonConfig.BiomeGroups.DEFAULT, RUCommonConfig.BiomePlacements.DEFAULT, RUCommonConfig.Misc.DEFAULT, RUCommonConfig.VanillaChanges.DEFAULT
   );
   public static final Codec<RUCommonConfig> CODEC = RecordCodecBuilder.create(
      i -> i.group(
            CommentedMapCodec.commented(Codec.INT, "config_version", "Don't touch this!").orElse(1).forGetter(c -> c.configVersion),
            CommentedMapCodec.optionalCommented(
                  RUCommonConfig.BiomeGroups.CODEC,
                  RUCommonConfig.BiomeGroups.DEFAULT,
                  "biome_groups",
                  "Biome groups allows several biomes of similar styles to more consistently spawn adjacent to one another."
               )
               .forGetter(c -> c.biomeGroups),
            RUCommonConfig.BiomePlacements.CODEC.fieldOf("biome_placements").orElse(RUCommonConfig.BiomePlacements.DEFAULT).forGetter(c -> c.biomePlacements),
            RUCommonConfig.Misc.CODEC.fieldOf("misc").orElse(RUCommonConfig.Misc.DEFAULT).forGetter(c -> c.misc),
            RUCommonConfig.VanillaChanges.CODEC.fieldOf("vanilla_changes").orElse(RUCommonConfig.VanillaChanges.DEFAULT).forGetter(c -> c.vanillaChanges)
         )
         .apply(i, RUCommonConfig::new)
   );
   public int configVersion;
   public RUCommonConfig.BiomeGroups biomeGroups;
   public RUCommonConfig.BiomePlacements biomePlacements;
   public RUCommonConfig.Misc misc;
   public RUCommonConfig.VanillaChanges vanillaChanges;

   public RUCommonConfig(
      int configVersion,
      RUCommonConfig.BiomeGroups biomeGroups,
      RUCommonConfig.BiomePlacements biomePlacements,
      RUCommonConfig.Misc misc,
      RUCommonConfig.VanillaChanges vanillaChanges
   ) {
      this.configVersion = configVersion;
      this.biomeGroups = biomeGroups;
      this.biomePlacements = biomePlacements;
      this.misc = misc;
      this.vanillaChanges = vanillaChanges;
   }

   public RUCommonConfig.Misc.BranchMode getBranchMode() {
      return this.misc.branchMode;
   }

   public boolean test(String key) {
      if (key.startsWith("vanilla_changes/")) {
         return this.vanillaChanges.toggles.getOrDefault(key.substring(16), false);
      }

      return switch (key) {
         case "custom_dirts" -> this.misc.customDirts;
         case "painted_planks" -> this.misc.paintedPlanks;
         default -> {
            RegionsUnexplored.LOGGER.error("Unknown key in config predicate:  {}", key);
            yield false;
         }
      };
   }

   public static class BiomeGroups {
      private static final RUCommonConfig.BiomeGroups DEFAULT = new RUCommonConfig.BiomeGroups(
         Map.of(
            "rivers",
            BiomeTarget.ofWeighted(60, Biomes.RIVER),
            "swamps",
            BiomeTarget.ofWeighted(100, Biomes.SWAMP),
            "plains",
            BiomeTarget.ofWeighted(100, Biomes.PLAINS),
            "forests",
            BiomeTarget.ofWeighted(50, Biomes.FOREST),
            "boreal_taigas",
            BiomeTarget.ofWeighted(75, Biomes.TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA),
            "pine_and_redwood_taigas",
            BiomeTarget.ofWeighted(75, Biomes.TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA),
            "jungles",
            BiomeTarget.ofWeighted(50, Biomes.JUNGLE, Biomes.SPARSE_JUNGLE)
         )
      );
      public static final Codec<RUCommonConfig.BiomeGroups> CODEC = Codec.unboundedMap(
            Codec.STRING
               .validate(
                  string -> ResourceLocation.isValidPath(string)
                     ? DataResult.success(string)
                     : DataResult.error(() -> "Invalid character(s) in group name: " + string)
               ),
            BiomeTarget.CODEC
         )
         .xmap(RUCommonConfig.BiomeGroups::new, g -> g.groups);
      public Map<String, BiomeTarget> groups;

      public BiomeGroups(Map<String, BiomeTarget> groups) {
         this.groups = new HashMap<>(groups);
      }
   }

   public static class BiomePlacements {
      private static final RUCommonConfig.BiomePlacements DEFAULT = new RUCommonConfig.BiomePlacements(
         Map.ofEntries(
            Map.entry(RUBiomes.ALPHA_GROVE, BiomeTarget.ofWeighted(20, Biomes.MUSHROOM_FIELDS)),
            Map.entry(RUBiomes.ASHEN_WOODLAND, BiomeTarget.ofWeighted(20, Biomes.MUSHROOM_FIELDS)),
            Map.entry(RUBiomes.TROPICS, BiomeTarget.ofWeighted(20, Biomes.MUSHROOM_FIELDS)),
            Map.entry(RUBiomes.HYACINTH_DEEPS, BiomeTarget.ofWeighted(50, Biomes.DEEP_FROZEN_OCEAN)),
            Map.entry(RUBiomes.ROCKY_REEF, BiomeTarget.ofWeighted(50, Biomes.WARM_OCEAN)),
            Map.entry(RUBiomes.GRASSY_BEACH, BiomeTarget.ofWeighted(50, Biomes.BEACH)),
            Map.entry(RUBiomes.GRAVEL_BEACH, BiomeTarget.ofWeighted(50, Biomes.BEACH)),
            Map.entry(
               RUBiomes.COLD_RIVER,
               BiomeTarget.ofGroupToggle("rivers", Biomes.RIVER, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.below(-0.1)))
            ),
            Map.entry(
               RUBiomes.MUDDY_RIVER,
               BiomeTarget.ofGroupToggle("rivers", Biomes.RIVER, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.between(-0.1, 0.55)))
            ),
            Map.entry(
               RUBiomes.TROPICAL_RIVER,
               BiomeTarget.ofGroupToggle(
                  "rivers",
                  Biomes.RIVER,
                  Map.of(
                     ClimateParameter.TEMPERATURE,
                     BiomeTarget.DoubleRange.above(0.55),
                     ClimateParameter.HUMIDITY,
                     BiomeTarget.DoubleRange.above(0.3),
                     ClimateParameter.EROSION,
                     BiomeTarget.DoubleRange.above(0.15)
                  )
               )
            ),
            Map.entry(
               RUBiomes.FEN,
               BiomeTarget.ofGroupToggle(
                  "swamps",
                  Biomes.SWAMP,
                  Map.of(
                     ClimateParameter.TEMPERATURE,
                     BiomeTarget.DoubleRange.between(-0.45, -0.1),
                     ClimateParameter.HUMIDITY,
                     BiomeTarget.DoubleRange.above(-0.35)
                  )
               )
            ),
            Map.entry(
               RUBiomes.FUNGAL_FEN,
               BiomeTarget.ofGroupToggle(
                  "swamps",
                  Biomes.SWAMP,
                  Map.of(
                     ClimateParameter.TEMPERATURE,
                     BiomeTarget.DoubleRange.between(-0.45, -0.1),
                     ClimateParameter.HUMIDITY,
                     BiomeTarget.DoubleRange.below(-0.35)
                  )
               )
            ),
            Map.entry(
               RUBiomes.BAYOU,
               BiomeTarget.ofGroupToggle(
                  "swamps",
                  Biomes.SWAMP,
                  Map.of(
                     ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.between(-0.1, 0.2), ClimateParameter.HUMIDITY, BiomeTarget.DoubleRange.below(3.0)
                  )
               )
            ),
            Map.entry(
               RUBiomes.OLD_GROWTH_BAYOU,
               BiomeTarget.ofGroupToggle(
                  "swamps",
                  Biomes.SWAMP,
                  Map.of(
                     ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.between(-0.1, 0.2), ClimateParameter.HUMIDITY, BiomeTarget.DoubleRange.above(3.0)
                  )
               )
            ),
            Map.entry(RUBiomes.MARSH, BiomeTarget.ofWeighted(50, Biomes.SWAMP)),
            Map.entry(RUBiomes.TUNDRA, BiomeTarget.ofWeighted(100, Biomes.SNOWY_PLAINS, Map.of(ClimateParameter.HUMIDITY, BiomeTarget.DoubleRange.below(-0.1)))),
            Map.entry(RUBiomes.ORCHARD, BiomeTarget.ofWeighted(50, Biomes.SUNFLOWER_PLAINS, Biomes.FLOWER_FOREST)),
            Map.entry(RUBiomes.FLOWER_FIELDS, BiomeTarget.ofWeighted(30, Biomes.SUNFLOWER_PLAINS, Biomes.FLOWER_FOREST)),
            Map.entry(RUBiomes.POPPY_FIELDS, BiomeTarget.ofWeighted(70, Biomes.SUNFLOWER_PLAINS, Biomes.FLOWER_FOREST)),
            Map.entry(
               RUBiomes.SHRUBLAND,
               BiomeTarget.ofGroupToggle("plains", Biomes.PLAINS, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.below(-0.1)))
            ),
            Map.entry(
               RUBiomes.CLOVER_PLAINS,
               BiomeTarget.ofGroupToggle("plains", Biomes.PLAINS, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.between(-0.1, 0.2)))
            ),
            Map.entry(
               RUBiomes.GRASSLAND, BiomeTarget.ofGroupToggle("plains", Biomes.PLAINS, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.above(0.2)))
            ),
            Map.entry(RUBiomes.HIGHLAND_FIELDS, BiomeTarget.ofWeighted(70, Biomes.MEADOW)),
            Map.entry(RUBiomes.WISTERIA_GROVE, BiomeTarget.ofWeighted(30, Biomes.MEADOW)),
            Map.entry(RUBiomes.MAGNOLIA_WOODLAND, BiomeTarget.ofWeighted(50, Biomes.CHERRY_GROVE)),
            Map.entry(
               RUBiomes.WILLOW_FOREST,
               BiomeTarget.ofGroupToggle("forests", Biomes.FOREST, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.below(-0.1)))
            ),
            Map.entry(
               RUBiomes.MAPLE_FOREST,
               BiomeTarget.ofGroupToggle("forests", Biomes.FOREST, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.between(-0.1, 0.2)))
            ),
            Map.entry(
               RUBiomes.OLD_GROWTH_FOREST,
               BiomeTarget.ofGroupToggle("forests", Biomes.FOREST, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.above(0.2)))
            ),
            Map.entry(RUBiomes.AUTUMNAL_MAPLE_FOREST, BiomeTarget.ofWeighted(50, Biomes.BIRCH_FOREST)),
            Map.entry(RUBiomes.SILVER_BIRCH_FOREST, BiomeTarget.ofWeighted(50, Biomes.OLD_GROWTH_BIRCH_FOREST)),
            Map.entry(RUBiomes.BLACKWOOD_TAIGA, BiomeTarget.ofWeighted(50, Biomes.DARK_FOREST)),
            Map.entry(RUBiomes.PINE_TAIGA, BiomeTarget.ofGroupToggle("pine_and_redwood_taigas", Biomes.TAIGA)),
            Map.entry(RUBiomes.FROZEN_PINE_TAIGA, BiomeTarget.ofGroupToggle("pine_and_redwood_taigas", Biomes.SNOWY_TAIGA)),
            Map.entry(RUBiomes.REDWOODS, BiomeTarget.ofGroupToggle("pine_and_redwood_taigas", Biomes.OLD_GROWTH_SPRUCE_TAIGA)),
            Map.entry(RUBiomes.SPARSE_REDWOODS, BiomeTarget.ofGroupToggle("pine_and_redwood_taigas", Biomes.OLD_GROWTH_PINE_TAIGA)),
            Map.entry(RUBiomes.BOREAL_TAIGA, BiomeTarget.ofGroupToggle("boreal_taigas", Biomes.TAIGA)),
            Map.entry(RUBiomes.COLD_BOREAL_TAIGA, BiomeTarget.ofGroupToggle("boreal_taigas", Biomes.SNOWY_TAIGA)),
            Map.entry(RUBiomes.OLD_GROWTH_BOREAL_TAIGA, BiomeTarget.ofGroupToggle("boreal_taigas", Biomes.OLD_GROWTH_SPRUCE_TAIGA)),
            Map.entry(RUBiomes.OLD_GROWTH_GOLDEN_BOREAL_TAIGA, BiomeTarget.ofGroupToggle("boreal_taigas", Biomes.OLD_GROWTH_PINE_TAIGA)),
            Map.entry(
               RUBiomes.PINE_SLOPES, BiomeTarget.ofWeighted(50, Biomes.GROVE, Map.of(ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.above(-0.45)))
            ),
            Map.entry(RUBiomes.DRY_BUSHLAND, BiomeTarget.ofWeighted(100, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU)),
            Map.entry(RUBiomes.PRAIRIE, BiomeTarget.ofWeighted(100, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU)),
            Map.entry(RUBiomes.JOSHUA_DESERT, BiomeTarget.ofWeighted(50, Biomes.DESERT)),
            Map.entry(RUBiomes.SAGUARO_DESERT, BiomeTarget.ofWeighted(50, Biomes.DESERT)),
            Map.entry(RUBiomes.RAINFOREST, BiomeTarget.ofGroupToggle("jungles", Biomes.JUNGLE)),
            Map.entry(RUBiomes.SPARSE_RAINFOREST, BiomeTarget.ofGroupToggle("jungles", Biomes.SPARSE_JUNGLE)),
            Map.entry(RUBiomes.BAMBOO_FOREST, BiomeTarget.ofWeighted(100, Biomes.BAMBOO_JUNGLE)),
            Map.entry(RUBiomes.WINDSWEPT_MAPLE_FOREST, BiomeTarget.ofWeighted(100, Biomes.WINDSWEPT_FOREST)),
            Map.entry(RUBiomes.TOWERING_CLIFFS, BiomeTarget.ofWeighted(100, Biomes.WINDSWEPT_SAVANNA)),
            Map.entry(RUBiomes.EUCALYPTUS_FOREST, BiomeTarget.ofWeighted(50, Biomes.WOODED_BADLANDS)),
            Map.entry(RUBiomes.MYCOTOXIC_UNDERGROWTH, BiomeTarget.nether(60, Biomes.NETHER_WASTES)),
            Map.entry(RUBiomes.GLISTERING_MEADOW, BiomeTarget.nether(60, Biomes.SOUL_SAND_VALLEY)),
            Map.entry(RUBiomes.BLACKSTONE_BASIN, BiomeTarget.nether(60, Biomes.CRIMSON_FOREST)),
            Map.entry(RUBiomes.INFERNAL_HOLT, BiomeTarget.nether(60, Biomes.BASALT_DELTAS)),
            Map.entry(
               RUBiomes.ANCIENT_DELTA,
               BiomeTarget.ofToggle(List.of(Biomes.DRIPSTONE_CAVES), Map.of(ClimateParameter.HUMIDITY, BiomeTarget.DoubleRange.below(-0.4)))
            ),
            Map.entry(
               RUBiomes.BIOSHROOM_CAVES, BiomeTarget.ofToggle(List.of(Biomes.LUSH_CAVES), Map.of(ClimateParameter.EROSION, BiomeTarget.DoubleRange.above(0.3)))
            ),
            Map.entry(RUBiomes.PRISMACHASM, BiomeTarget.ofSpecial()),
            Map.entry(RUBiomes.REDSTONE_CAVES, BiomeTarget.ofSpecial()),
            Map.entry(RUBiomes.INFERNO, BiomeTarget.ofSpecial()),
            Map.entry(RUBiomes.CHALK_CLIFFS, BiomeTarget.ofSpecial()),
            Map.entry(RUBiomes.OUTBACK, BiomeTarget.ofToggle(List.of(Biomes.DESERT), Map.of(ClimateParameter.EROSION, BiomeTarget.DoubleRange.below(0.15)))),
            Map.entry(
               RUBiomes.SPIRES,
               BiomeTarget.ofToggle(
                  List.of(Biomes.FROZEN_RIVER, Biomes.ICE_SPIKES, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.TAIGA),
                  Map.of(ClimateParameter.EROSION, BiomeTarget.DoubleRange.above(0.6), ClimateParameter.TEMPERATURE, BiomeTarget.DoubleRange.below(-0.45))
               )
            ),
            Map.entry(
               RUBiomes.ICY_HEIGHTS,
               BiomeTarget.ofToggle(
                  List.of(Biomes.ICE_SPIKES, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA),
                  Map.of(
                     ClimateParameter.EROSION,
                     BiomeTarget.DoubleRange.between(0.45, 0.55),
                     ClimateParameter.CONTINENTALNESS,
                     BiomeTarget.DoubleRange.below(0.03),
                     ClimateParameter.WEIRDNESS,
                     BiomeTarget.DoubleRange.above(0.0)
                  )
               )
            ),
            Map.entry(
               RUBiomes.BAOBAB_SAVANNA,
               BiomeTarget.ofToggle(List.of(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU), Map.of(ClimateParameter.HUMIDITY, BiomeTarget.DoubleRange.below(-0.35)))
            )
         ),
         1.0F
      );
      public static final Codec<RUCommonConfig.BiomePlacements> CODEC = Codec.withAlternative(
         RecordCodecBuilder.create(
            i -> i.group(
                  Codec.unboundedMap(ResourceKey.codec(Registries.BIOME), BiomeTarget.CODEC).fieldOf("placements").forGetter(g -> g.placements),
                  CommentedMapCodec.optionalCommented(
                        Codec.FLOAT, 1.0F, "weight_multiplier", "Multiplies the weighting of most biomes in the layout. Higher value = more RU biomes."
                     )
                     .forGetter(g -> g.weightMultiplier)
               )
               .apply(i, RUCommonConfig.BiomePlacements::create)
         ),
         Codec.unboundedMap(ResourceKey.codec(Registries.BIOME), BiomeTarget.CODEC).xmap(RUCommonConfig.BiomePlacements::create, g -> g.placements)
      );
      public Map<ResourceKey<Biome>, BiomeTarget> placements;
      public float weightMultiplier;

      public BiomePlacements(Map<ResourceKey<Biome>, BiomeTarget> placements, float weightMultiplier) {
         this.placements = new HashMap<>(placements);
         this.weightMultiplier = weightMultiplier;
      }

      public static RUCommonConfig.BiomePlacements create(Map<ResourceKey<Biome>, BiomeTarget> basePlacements) {
         return create(basePlacements, 1.0F);
      }

      public static RUCommonConfig.BiomePlacements create(Map<ResourceKey<Biome>, BiomeTarget> basePlacements, float weightMultiplier) {
         RUCommonConfig.BiomePlacements placements = new RUCommonConfig.BiomePlacements(basePlacements, weightMultiplier);

         for (Entry<ResourceKey<Biome>, BiomeTarget> entry : DEFAULT.placements.entrySet()) {
            if (!placements.placements.containsKey(entry.getKey())) {
               placements.placements.put(entry.getKey(), entry.getValue());
            }
         }

         return placements;
      }
   }

   public static class Misc {
      public static final RUCommonConfig.Misc DEFAULT = new RUCommonConfig.Misc(RUCommonConfig.Misc.BranchMode.PLACE_BRANCHES, true, false, false);
      public static final Codec<RUCommonConfig.Misc> CODEC = RecordCodecBuilder.create(
         i -> i.group(
               CommentedMapCodec.optionalCommented(
                     RUCommonConfig.Misc.BranchMode.CODEC,
                     RUCommonConfig.Misc.BranchMode.PLACE_BRANCHES,
                     "branch_mode",
                     "\"place_branches\" = place RU's dedicated branch blocks, \"place_logs\" = place log blocks, \"dont_place\" = don't place any branches"
                  )
                  .orElse(RUCommonConfig.Misc.BranchMode.PLACE_BRANCHES)
                  .forGetter(m -> m.branchMode),
               CommentedMapCodec.optionalCommented(Codec.BOOL, true, "custom_dirts", "Controls the Peat and Silt dirt block family generation")
                  .orElse(true)
                  .forGetter(m -> m.customDirts),
               CommentedMapCodec.optionalCommented(
                     Codec.BOOL, false, "small_oak_trees", "Oak trees with thin fence-like log blocks will generate in some forests"
                  )
                  .orElse(false)
                  .forGetter(m -> m.smallOakTrees),
               CommentedMapCodec.optionalCommented(Codec.BOOL, false, "painted_planks", "Re-enables the recipes of the legacy Painted Plank blocks")
                  .orElse(false)
                  .forGetter(m -> m.paintedPlanks)
            )
            .apply(i, RUCommonConfig.Misc::new)
      );
      public RUCommonConfig.Misc.BranchMode branchMode;
      public boolean customDirts;
      public boolean smallOakTrees;
      public boolean paintedPlanks;

      public Misc(RUCommonConfig.Misc.BranchMode branchMode, boolean customDirts, boolean smallOakTrees, boolean paintedPlanks) {
         this.branchMode = branchMode;
         this.customDirts = customDirts;
         this.smallOakTrees = smallOakTrees;
         this.paintedPlanks = paintedPlanks;
      }

      public enum BranchMode implements StringRepresentable {
         PLACE_BRANCHES("place_branches"),
         PLACE_LOGS("place_logs"),
         DONT_PLACE("dont_place");

         private static final Codec<RUCommonConfig.Misc.BranchMode> CODEC = StringRepresentable.fromValues(RUCommonConfig.Misc.BranchMode::values);
         private final String name;

         BranchMode(String name) {
            this.name = name;
         }

         public boolean cannotPlace() {
            return this == DONT_PLACE;
         }

         public BlockState selectBlock(Block branch, Block log) {
            return (this == PLACE_BRANCHES ? branch : log).defaultBlockState();
         }

         @NotNull
         public String getSerializedName() {
            return this.name;
         }
      }
   }

   public static class VanillaChanges {
      public static final Map<String, String> TOGGLES = Map.ofEntries(
         Map.entry("badlands_saguaros", "Saguaro Cactis will generate in Badlands and Woodland Badlands."),
         Map.entry("badlands_steppe_grass", "Steppe Grass will generate in all Badlands."),
         Map.entry("basalt_deltas_ash_vents", "Ash Vents will generate in Basalt Deltas."),
         Map.entry("beach_palm_trees", "Palm trees will generate in Beaches in warm areas."),
         Map.entry("birch_aspen_trees", "Birch trees will generate with Aspen shaping."),
         Map.entry("birch_orange_coneflowers", "Orange Coneflower patches will generate in Birch Forests."),
         Map.entry("common_grass_sprouts", "Grass Sprouts will generate alongside Short Grass in most biomes."),
         Map.entry("common_shrubs", "Shrubs will generate in a variety of biomes."),
         Map.entry("desert_sandy_grass", "Sandy Grass will generate in Deserts."),
         Map.entry("forest_fancy_oaks", "Fancy oak tree will generate more frequently in Forests."),
         Map.entry("forest_flowers", "RU's tall flowers will generate in forested biomes."),
         Map.entry("jungle_bamboo_trees", "Bamboo trees will rarely generate in Jungles."),
         Map.entry("jungle_elephant_ears", "Elephant Ears will generate in Jungles."),
         Map.entry("jungle_hibiscuses", "Hibiscuses will generate in Jungles."),
         Map.entry("mangrove_flowering_lilies", "Flowering Lily Pads will generate in Mangrove Swamps."),
         Map.entry("oak_taller_trees", "Regular oak trees generate 2-3 blocks taller."),
         Map.entry("plains_bushes", "Small bushes will generate in Plains."),
         Map.entry("savanna_bushes", "Small bushes will generate in Savannas."),
         Map.entry("snowy_frozen_grass", "Frozen Grass will generate in snowy biomes."),
         Map.entry("swamp_cattails", "Cattails will generate in Swamp and Mangrove Swamps."),
         Map.entry("swamp_willow_trees", "Swamps will generate with rooted Willow and Oak trees."),
         Map.entry("taiga_pine_trees", "Pine trees will generate with Pine wood blocks in all Taigas."),
         Map.entry("taiga_purple_coneflowers", "Purple Coneflower patches will generate in Taigas.")
      );
      public static final RUCommonConfig.VanillaChanges DEFAULT = new RUCommonConfig.VanillaChanges(
         TOGGLES.keySet().stream().collect(Collectors.toMap(key -> (String)key, value -> true))
      );
      public static final Codec<RUCommonConfig.VanillaChanges> CODEC = new CommentedUnboundedMapCodec(Codec.STRING, Codec.BOOL, TOGGLES)
         .xmap(RUCommonConfig.VanillaChanges::new, v -> v.toggles);
      public Map<String, Boolean> toggles;

      public VanillaChanges(Map<String, Boolean> toggles) {
         this.toggles = new HashMap<>(toggles);

         for (String key : toggles.keySet()) {
            if (!TOGGLES.containsKey(key)) {
               this.toggles.remove(key);
            }
         }

         for (Entry<String, String> entry : TOGGLES.entrySet()) {
            if (!this.toggles.containsKey(entry.getKey())) {
               this.toggles.put(entry.getKey(), true);
            }
         }
      }
   }
}
