package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;

public class LunarMonstrosityEmergePhase extends BehaviorPhase<LunarMonstrosity> {
   public static final int ID = 7;

   public LunarMonstrosityEmergePhase() {
      super(7, 1, 45, 0);
   }

   public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
      return false;
   }

   public void tick(LunarMonstrosity entity) {
   }

   public boolean canContinue(LunarMonstrosity entity) {
      return true;
   }
}
