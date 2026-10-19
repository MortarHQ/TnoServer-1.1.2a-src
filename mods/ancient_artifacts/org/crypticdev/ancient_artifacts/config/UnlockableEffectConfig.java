package org.crypticdev.ancient_artifacts.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;

public class UnlockableEffectConfig extends ManasConfig {
   public String id;
   public double baseValue;
   public double valuePerRefinement;
   @Comment("Max Refinement -1 is no max")
   public int maxRefinement;

   public UnlockableEffectConfig(String id) {
      this.id = id;
   }

   public UnlockableEffectConfig(String id, double defaultBaseValue, double defaultValuePerRefinement, int defaultMaxRefinementSteps) {
      this.id = id;
      this.baseValue = defaultBaseValue;
      this.valuePerRefinement = defaultValuePerRefinement;
      this.maxRefinement = defaultMaxRefinementSteps;
   }

   public String getFileName() {
      return "ancient_artifacts/effect/" + this.id;
   }
}
