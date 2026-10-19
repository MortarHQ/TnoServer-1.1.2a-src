package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class StarlightGolemChargeEndPhase extends BehaviorPhase<StarlightGolem> {
   public static final int ID = 6;

   public StarlightGolemChargeEndPhase() {
      super(6, 1, 35, 0);
   }

   public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
      return false;
   }

   public void tick(StarlightGolem entity) {
      if (entity.hasProtection()) {
         entity.heal(0.02F);
      }
   }

   public boolean canContinue(StarlightGolem entity) {
      return true;
   }

   public void onStop(StarlightGolem entity) {
      entity.getBehaviorManager().getCooldowns().put(4, 800);
   }
}
