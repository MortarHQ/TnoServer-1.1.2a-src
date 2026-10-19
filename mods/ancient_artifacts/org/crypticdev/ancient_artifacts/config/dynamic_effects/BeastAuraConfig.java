package org.crypticdev.ancient_artifacts.config.dynamic_effects;

import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;

public class BeastAuraConfig extends SpecialEffectConfig {
   public int max_refinements = -1;
   public int ap_max_usage_increase_per_refinement = 2000;
   public int ap_max_usage_mastered = 10000;
   public int ap_max_usage = 6000;

   @Override
   public int max_refinements() {
      return this.max_refinements;
   }

   public String getFileName() {
      return "ancient_artifacts/effect/beast_aura";
   }
}
