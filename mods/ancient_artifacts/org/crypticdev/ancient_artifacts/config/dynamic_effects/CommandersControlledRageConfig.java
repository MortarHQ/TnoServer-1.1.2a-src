package org.crypticdev.ancient_artifacts.config.dynamic_effects;

import io.github.manasmods.manascore.config.api.Comment;
import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;

public class CommandersControlledRageConfig extends SpecialEffectConfig {
   public double bonusThresholdPerRefinement = 0.05;
   public double magiculePercentForNextIncrease = 0.25;
   public double magiculeCostReductionPerIncrease = 0.1;
   @Comment("Max Refinement -1 is no max")
   public int max_refinements = 3;
   public boolean mastery_unlock = true;

   @Override
   public int max_refinements() {
      return 0;
   }

   public String getFileName() {
      return "ancient_artifacts/effect/commanders_controlled_rage";
   }
}
