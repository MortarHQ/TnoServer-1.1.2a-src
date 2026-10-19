package org.crypticdev.ancient_artifacts.config;

import java.util.List;
import java.util.Map;

public class DefaultLootInstanceConfigs {
   public static Map<String, LootInstanceConfig> list = Map.of(
      "artifacts:artifact",
      new LootInstanceConfig(
         "artifacts:artifact",
         0.05F,
         List.of(
            "ancient_artifacts:shadowbind_bracelet:1",
            "ancient_artifacts:lion_emblem:1",
            "ancient_artifacts:war_pendant:4",
            "ancient_artifacts:crimson_mizuhiki:4",
            "ancient_artifacts:amulet_of_the_sylphs:4",
            "ancient_artifacts:titanstride_greaves:10",
            "ancient_artifacts:magicule_conduit_belt:10"
         )
      )
   );
}
