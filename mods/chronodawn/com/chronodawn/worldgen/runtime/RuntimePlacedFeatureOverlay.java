package com.chronodawn.worldgen.runtime;

import com.chronodawn.config.ChronoDawnConfig;
import com.chronodawn.config.OreSettings;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RuntimePlacedFeatureOverlay {
   public static final String TIME_CRYSTAL_PATH = "data/chronodawn/worldgen/placed_feature/ore_time_crystal.json";
   public static final String ENTROPY_CRYSTAL_PATH = "data/chronodawn/worldgen/placed_feature/ore_entropy_crystal.json";
   public static final String TEMPORAL_AMBER_PATH = "data/chronodawn/worldgen/placed_feature/ore_temporal_amber.json";
   public static final String CLOCKSTONE_PATH = "data/chronodawn/worldgen/placed_feature/ore_clockstone.json";

   private RuntimePlacedFeatureOverlay() {
   }

   public static Map<String, byte[]> generate(ChronoDawnConfig config) {
      Map<String, byte[]> out = new LinkedHashMap<>();
      out.put(
         "data/chronodawn/worldgen/placed_feature/ore_time_crystal.json",
         generateOre("ore_time_crystal", "minecraft:trapezoid", config.world().ores().timeCrystal())
      );
      out.put(
         "data/chronodawn/worldgen/placed_feature/ore_entropy_crystal.json",
         generateOre("ore_entropy_crystal", "minecraft:trapezoid", config.world().ores().entropyCrystal())
      );
      out.put(
         "data/chronodawn/worldgen/placed_feature/ore_temporal_amber.json",
         generateOre("ore_temporal_amber", "minecraft:uniform", config.world().ores().temporalAmber())
      );
      out.put(
         "data/chronodawn/worldgen/placed_feature/ore_clockstone.json",
         generateOre("ore_clockstone", "minecraft:trapezoid", config.world().ores().clockstone())
      );
      return out;
   }

   static byte[] generateOre(String featureId, String heightDistributionType, OreSettings settings) {
      int emittedCount = settings.enabled() ? settings.count() : 0;
      String json = "{\n  \"feature\": \"chronodawn:"
         + featureId
         + "\",\n  \"placement\": [\n    {\n      \"type\": \"minecraft:count\",\n      \"count\": "
         + emittedCount
         + "\n    },\n    {\n      \"type\": \"minecraft:in_square\"\n    },\n    {\n      \"type\": \"minecraft:height_range\",\n      \"height\": {\n        \"type\": \""
         + heightDistributionType
         + "\",\n        \"min_inclusive\": {\n          \"absolute\": "
         + settings.yMin()
         + "\n        },\n        \"max_inclusive\": {\n          \"absolute\": "
         + settings.yMax()
         + "\n        }\n      }\n    },\n    {\n      \"type\": \"minecraft:biome\"\n    }\n  ]\n}\n";
      return json.getBytes(StandardCharsets.UTF_8);
   }
}
