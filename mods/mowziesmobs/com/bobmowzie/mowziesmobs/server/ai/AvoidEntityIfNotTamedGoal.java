package com.bobmowzie.mowziesmobs.server.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class AvoidEntityIfNotTamedGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
   public AvoidEntityIfNotTamedGoal(PathfinderMob entityIn, Class classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
      super(entityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
   }

   public boolean canUse() {
      boolean isTamed = this.mob instanceof TamableAnimal && ((TamableAnimal)this.mob).isTame();
      return super.canUse() && !isTamed;
   }
}
