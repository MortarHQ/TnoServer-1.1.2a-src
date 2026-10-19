package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class DragonAIWatchClosestGoal extends LookAtPlayerGoal {
   public DragonAIWatchClosestGoal(PathfinderMob LivingEntityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
      super(LivingEntityIn, watchTargetClass, maxDistance);
   }

   public boolean canUse() {
      return this.mob instanceof DragonBaseEntity && ((DragonBaseEntity)this.mob).getAnimation() == DragonBaseEntity.ANIMATION_SHAKEPREY
         ? false
         : super.canUse();
   }

   public boolean canContinueToUse() {
      return this.mob instanceof DragonBaseEntity && !((DragonBaseEntity)this.mob).canMove() ? false : super.canContinueToUse();
   }
}
