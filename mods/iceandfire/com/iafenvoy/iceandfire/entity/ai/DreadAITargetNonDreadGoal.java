package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.util.IDreadMob;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class DreadAITargetNonDreadGoal extends NearestAttackableTargetGoal<LivingEntity> {
   public DreadAITargetNonDreadGoal(Mob entityIn, Class<LivingEntity> classTarget, boolean checkSight, Predicate<LivingEntity> targetSelector) {
      super(entityIn, classTarget, 0, checkSight, false, targetSelector);
   }

   protected boolean canAttack(LivingEntity target, TargetingConditions targetPredicate) {
      return !super.canAttack(target, targetPredicate) ? false : !(target instanceof IDreadMob) && DragonUtils.isAlive(target);
   }
}
