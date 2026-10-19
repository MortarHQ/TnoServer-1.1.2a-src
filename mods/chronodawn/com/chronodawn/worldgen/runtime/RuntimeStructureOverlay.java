package com.chronodawn.worldgen.runtime;

import com.chronodawn.config.ChronoDawnConfig;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RuntimeStructureOverlay {
   public static final String ANCIENT_RUINS_PATH = "data/chronodawn/worldgen/structure_set/ancient_ruins.json";

   private RuntimeStructureOverlay() {
   }

   public static Map<String, byte[]> generate(ChronoDawnConfig config) {
      Map<String, byte[]> out = new LinkedHashMap<>();
      out.put("data/chronodawn/worldgen/structure_set/ancient_ruins.json", generateAncientRuins(config.world().structures().ancientRuins()));
      return out;
   }

   static byte[] generateAncientRuins(ChronoDawnConfig.AncientRuins ar) {
      String structuresArray = ar.enabled() ? "{\n      \"structure\": \"chronodawn:ancient_ruins\",\n      \"weight\": 1\n    }" : "";
      String json = "{\n  \"structures\": ["
         + (structuresArray.isEmpty() ? "" : "\n    " + structuresArray + "\n  ")
         + "],\n  \"placement\": {\n    \"type\": \"minecraft:random_spread\",\n    \"salt\": "
         + ar.salt()
         + ",\n    \"spacing\": "
         + ar.spacing()
         + ",\n    \"separation\": "
         + ar.separation()
         + "\n  }\n}\n";
      return json.getBytes(StandardCharsets.UTF_8);
   }
}
