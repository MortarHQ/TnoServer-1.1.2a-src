package io.github.Memoires.mysticism.config.entity;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class MysticismSpawnRateConfig extends ManasConfig {
   @Comment("Spawn probability for mobs (1 in X chance). First value is Day, Second is Night.\nExamples: 1=100%, 5=20%, 10=10%. 0=disabled.")
   public MysticismSpawnRateConfig.SpawnChance SpawnChance = new MysticismSpawnRateConfig.SpawnChance();

   public String getFileName() {
      return "mysticism/entity/spawn_rate_config";
   }

   public static class SpawnChance extends ManasSubConfig {
      @Comment("One of how many chances for Memoires to spawn.")
      public int memoiresChance = 1000;
      @Comment("One of how many chances for an Okami to spawn.")
      public int okamiChance = 8;
      @Comment("One of how many chances for a Shadow Imp to spawn.")
      public int shadowImpChange = 10;
   }
}
