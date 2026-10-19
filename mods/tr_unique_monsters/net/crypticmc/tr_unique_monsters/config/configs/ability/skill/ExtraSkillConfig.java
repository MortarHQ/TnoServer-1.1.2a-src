package net.crypticmc.tr_unique_monsters.config.configs.ability.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class ExtraSkillConfig extends ManasConfig {
   public ExtraSkillConfig.AppraisalEye AppraisalEye = new ExtraSkillConfig.AppraisalEye();

   public String getFileName() {
      return "tr_UniqueMonsters/ability/skill/extra_config";
   }

   public static class AppraisalEye extends ManasSubConfig {
      @Comment("The Presence Sense Boost when activated.")
      public double scanPresenceLevel = 1.0;
      @Comment("The Presence Sense Radius Boost when activated.")
      public double scanRadius = 15.0;
      @Comment("The Base Zoom Multiplier.")
      public double remoteBaseZoom = 4.0;
      @Comment("The Bonus Presence Sense Level when activated.")
      public int remoteSenseLevel = 2;
      @Comment("The Presence Sense Radius when activated.")
      public int remoteSenseRadius = 50;
      @Comment("The cooldown in second After Releasing Scan Mode")
      public int scanReleaseCooldown = 30;
      @Comment("The cooldown in second After Releasing Remote Analysis Mode")
      public int remoteReleaseCooldown = 5;
      @Comment("The cooldown in second After Releasing Scan Mode When Mastered")
      public int scanReleaseCooldownMastered = 2;
      @Comment("The cooldown in second After Releasing Remote Analysis Mode")
      public int remoteReleaseCooldownMastered = 0;
   }
}
