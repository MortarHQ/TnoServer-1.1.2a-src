package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class LunarMonstrosityDigPhase extends BehaviorPhase<LunarMonstrosity> {
   public static final int ID = 5;

   public LunarMonstrosityDigPhase() {
      super(5, 1, 31, 300, 6);
   }

   public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
      return cooldownOver && entity.getTarget() != null && !this.canReachTarget(entity, 10.0) || entity.fleeFromLavaCooldown <= 0 && entity.isInLava();
   }

   public void onStart(LunarMonstrosity entity) {
      if (entity.isInLava()) {
         entity.fleeFromLavaCooldown = 100;
      }
   }

   public void tick(LunarMonstrosity entity) {
   }

   public boolean canContinue(LunarMonstrosity entity) {
      return true;
   }
}
