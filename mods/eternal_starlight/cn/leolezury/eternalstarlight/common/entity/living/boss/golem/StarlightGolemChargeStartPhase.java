package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class StarlightGolemChargeStartPhase extends BehaviorPhase<StarlightGolem> {
   public static final int ID = 4;

   public StarlightGolemChargeStartPhase() {
      super(4, 1, 35, 0, 5);
   }

   public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
      return cooldownOver && entity.getTarget() != null && entity.getAttackEnergy() <= 0 && entity.getPhase() == 0;
   }

   public void onStart(StarlightGolem entity) {
      entity.setAttackEnergy(100);
      entity.turnOnEnergyBlocks();
      entity.clearChargeHurtCountAndAmount();
   }

   public void tick(StarlightGolem entity) {
      if (entity.hasProtection()) {
         entity.heal(0.04F);
      }
   }

   public boolean canContinue(StarlightGolem entity) {
      return true;
   }
}
