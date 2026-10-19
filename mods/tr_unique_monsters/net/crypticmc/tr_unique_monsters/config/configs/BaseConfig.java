package net.crypticmc.tr_unique_monsters.config.configs;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import java.util.List;

@SyncToClient
public class BaseConfig extends ManasConfig {
   @Comment("Sets the maximum EP multiplier a unique monster can receive at spawn (1.1 - 10).")
   public double maxEpMultiplier = 10.0;
   @Comment("Per-entity overrides for unique spawn settings.\nFormat: <entity_id>,<MonsterChance>,<maxEpMultiplier>\n")
   public List<String> uniqueMonsterEntityOverrides = List.of("tensura:blade_tiger,0.1,5", "tensura:spear_toro,0.090,5", "tensura:hound_dog,0.087,10");
   @Comment("A list of entity IDs that are excluded from this system and will never spawn as unique monsters.")
   public List<String> uniqueMonsterBlacklist = List.of(
      "tensura:hinata_sakaguchi",
      "tensura:elemental_colossus",
      "tensura:orc_disaster",
      "tensura:orc_lord",
      "tensura:shizu",
      "tensura:ifrit",
      "tensura:charybdis"
   );
   @Comment("A list of dimension IDs where this system is disabled.")
   public List<String> uniqueMonsterDimensionBlacklist = List.of("tensura:labyrinth");
   @Comment("Controls the chance for any eligible monster to spawn as a unique variant (0.1% - 100%).")
   public double uniqueMonsterChance = 0.1;
   @Comment("Controls whether or not unique monsters are tameable.")
   public boolean uniqueMonsterTameable = false;

   public String getFileName() {
      return "tr_UniqueMonsters/base_config";
   }
}
