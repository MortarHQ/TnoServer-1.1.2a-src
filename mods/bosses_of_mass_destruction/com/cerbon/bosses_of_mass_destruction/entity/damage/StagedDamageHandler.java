package com.cerbon.bosses_of_mass_destruction.entity.damage;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class StagedDamageHandler implements IDamageHandler {
   private final List<Float> hpPercentRageModes;
   private final Runnable whenHpBelowThreshold;
   private float previousHpRatio = 1.0F;

   public StagedDamageHandler(List<Float> hpPercentRageModes, Runnable whenHpBelowThreshold) {
      this.hpPercentRageModes = hpPercentRageModes;
      this.whenHpBelowThreshold = whenHpBelowThreshold;
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
      this.previousHpRatio = this.hpPercent(stats);
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
      float newHpRatio = this.hpPercent(stats);
      float firstRageMode = MathUtils.roundedStep(this.previousHpRatio, this.hpPercentRageModes, false);
      float secondRageMode = MathUtils.roundedStep(newHpRatio, this.hpPercentRageModes, false);
      if (firstRageMode != secondRageMode) {
         this.whenHpBelowThreshold.run();
      }
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      return true;
   }

   private float hpPercent(IEntityStats stats) {
      return stats.getHealth() / stats.getMaxHealth();
   }
}
