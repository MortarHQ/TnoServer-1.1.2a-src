package com.shin.tensura_kumodesu.config.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class KumodesuUniqueSkillConfig extends ManasConfig {
   public KumodesuUniqueSkillConfig.Resolve Resolve = new KumodesuUniqueSkillConfig.Resolve();
   public KumodesuUniqueSkillConfig.Determination Determination = new KumodesuUniqueSkillConfig.Determination();
   public KumodesuUniqueSkillConfig.Perseverance Perseverance = new KumodesuUniqueSkillConfig.Perseverance();

   public String getFileName() {
      return "tensura_kumodesu/ability/unique_config";
   }

   public static class Determination extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 50000.0;
      @Comment("The multiplier of HP that the user needs to below to trigger Determination.")
      public double hpMultiplier = 0.5;
      @Comment("The bonus attack damage when Determination is triggered with mastery (doubled with Mastery).")
      public double bonusAttack = 20.0;
      @Comment("The bonus attack speed when Determination is triggered with mastery (doubled with Mastery).")
      public double bonusAttackSpeed = 1.6;
      @Comment("The bonus armor when Determination is triggered with mastery (doubled with Mastery).")
      public double bonusArmor = 20.0;
   }

   public static class Perseverance extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 25000.0;
      @Comment("The multiplier of HP that the user needs to below to trigger Perseverance.")
      public double hpMultiplier = 0.75;
      @Comment("The bonus attack damage when Perseverance is triggered with mastery (doubled with Mastery).")
      public double bonusAttack = 40.0;
      @Comment("The bonus attack speed when Perseverance is triggered with mastery (doubled with Mastery).")
      public double bonusAttackSpeed = 3.2;
      @Comment("The bonus armor when Perseverance is triggered with mastery (doubled with Mastery).")
      public double bonusArmor = 40.0;
   }

   public static class Resolve extends ManasSubConfig {
      @Comment("Magicule Acquirement Cost.")
      public double mpAcquirement = 100000.0;
      @Comment("The multiplier of HP that the user needs to be below to trigger Resolve.")
      public double hpMultiplier = 0.25;
      @Comment("The bonus attack damage when Resolve is triggered with mastery (doubled with Mastery).")
      public double bonusAttack = 10.0;
      @Comment("The bonus attack speed when Resolve is triggered with mastery (doubled with Mastery).")
      public double bonusAttackSpeed = 0.8;
   }
}
