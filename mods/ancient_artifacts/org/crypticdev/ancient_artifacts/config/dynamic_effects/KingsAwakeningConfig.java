package org.crypticdev.ancient_artifacts.config.dynamic_effects;

import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;

public class KingsAwakeningConfig extends SpecialEffectConfig {
   @Override
   public int max_refinements() {
      return 0;
   }

   public String getFileName() {
      return "ancient_artifacts/effect/kings_awakening";
   }
}
