package org.crypticdev.stextras.config;

import io.github.manasmods.manascore.config.ConfigRegistry;
import org.crypticdev.stextras.config.ability.skill.STExtrasExtraSkillConfig;
import org.crypticdev.stextras.config.ability.skill.STExtrasIntrinsicSkillConfig;
import org.crypticdev.stextras.config.ability.skill.STExtrasUniqueSkillConfig;
import org.crypticdev.stextras.config.race.KoboldConfig;
import org.crypticdev.stextras.config.race.MysticFoxConfig;
import org.crypticdev.stextras.config.race.WispfolkConfig;

public class STExtarsConfigs {
   public static void init() {
      ConfigRegistry.registerConfig(new STGeneralConfig());
      ConfigRegistry.registerConfig(new STPrestigeConfig());
      ConfigRegistry.registerConfig(new STSkillLockConfig());
      ConfigRegistry.registerConfig(new TensuraConfigPatcherStateConfig());
      ConfigRegistry.registerConfig(new STExtrasIntrinsicSkillConfig());
      ConfigRegistry.registerConfig(new STExtrasExtraSkillConfig());
      ConfigRegistry.registerConfig(new STExtrasUniqueSkillConfig());
      ConfigRegistry.registerConfig(new KoboldConfig());
      ConfigRegistry.registerConfig(new MysticFoxConfig());
      ConfigRegistry.registerConfig(new WispfolkConfig());
      TensuraConfigPatcher.init();
   }
}
