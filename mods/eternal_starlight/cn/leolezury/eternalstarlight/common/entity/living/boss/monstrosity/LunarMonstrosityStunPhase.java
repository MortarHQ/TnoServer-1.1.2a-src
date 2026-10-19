package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class LunarMonstrosityStunPhase extends BehaviorPhase<LunarMonstrosity> {
   public static final int ID = -1;

   public LunarMonstrosityStunPhase() {
      super(-1, 1, 100, 0);
   }

   public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
      return false;
   }

   public void tick(LunarMonstrosity entity) {
   }

   public boolean canContinue(LunarMonstrosity entity) {
      return true;
   }

   public void onStop(LunarMonstrosity entity) {
   }
}
