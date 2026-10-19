package net.crypticmc.tr_unique_monsters.config;

import io.github.manasmods.manascore.config.ConfigRegistry;
import net.crypticmc.tr_unique_monsters.config.configs.BaseConfig;
import net.crypticmc.tr_unique_monsters.config.configs.ability.skill.ExtraSkillConfig;

public class TRUniqueMobsConfigs {
   public static void init() {
      ConfigRegistry.registerConfig(new BaseConfig());
      ConfigRegistry.registerConfig(new ExtraSkillConfig());
   }
}
