package org.crypticdev.ancient_artifacts.config;

import io.github.manasmods.manascore.config.api.ManasConfig;
import java.util.List;

public class LootInstanceConfig extends ManasConfig {
   public String loot_id;
   public float chance = 0.05F;
   public List<String> itemWeights = List.of(
      "ancient_artifacts:shadowbind_bracelet:1",
      "ancient_artifacts:lion_emblem:1",
      "ancient_artifacts:war_pendant:4",
      "ancient_artifacts:crimson_mizuhiki:4",
      "ancient_artifacts:amulet_of_the_sylphs:4",
      "ancient_artifacts:titanstride_greaves:10",
      "ancient_artifacts:magicule_conduit_belt:10"
   );

   public LootInstanceConfig(String loot_id) {
      this.loot_id = loot_id;
   }

   public LootInstanceConfig(String loot_id, float chance, List<String> ItemWeight) {
      this.loot_id = loot_id;
      this.chance = chance;
      this.itemWeights = ItemWeight;
   }

   public String getFileName() {
      return "ancient_artifacts/loot/" + this.loot_id.replace(':', '/');
   }
}
