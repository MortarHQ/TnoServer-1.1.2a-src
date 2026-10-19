package org.crypticdev.ancient_artifacts;

import io.github.manasmods.manascore.config.ConfigRegistry;
import org.crypticdev.ancient_artifacts.config.DefaultLootInstanceConfigs;
import org.crypticdev.ancient_artifacts.config.LootInstanceConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.BeastAuraConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.CommandersControlledRageConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.CurrentMasteryConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.KingsAwakeningConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.OgresWrathConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.RisingInfernoConfig;

public class EffectConfigs {
   public static void register() {
      for (String config : AncientArtifacts.CONFIG.loot_changes) {
         if (DefaultLootInstanceConfigs.list.containsKey(config)) {
            ConfigRegistry.registerConfig(DefaultLootInstanceConfigs.list.get(config));
         } else {
            ConfigRegistry.registerConfig(new LootInstanceConfig(config));
         }

         AncientArtifacts.lootInstanceConfigs.add((LootInstanceConfig)ConfigRegistry.getConfig(LootInstanceConfig.class));
      }

      ConfigRegistry.registerConfig(new OgresWrathConfig());
      ConfigRegistry.registerConfig(new RisingInfernoConfig());
      ConfigRegistry.registerConfig(new CommandersControlledRageConfig());
      ConfigRegistry.registerConfig(new CurrentMasteryConfig());
      ConfigRegistry.registerConfig(new BeastAuraConfig());
      ConfigRegistry.registerConfig(new KingsAwakeningConfig());
   }
}
