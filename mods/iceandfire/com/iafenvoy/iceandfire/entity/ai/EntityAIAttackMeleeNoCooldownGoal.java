package com.iafenvoy.iceandfire.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class EntityAIAttackMeleeNoCooldownGoal extends MeleeAttackGoal {
   public EntityAIAttackMeleeNoCooldownGoal(PathfinderMob creature, double speed, boolean memory) {
      super(creature, speed, memory);
   }

   public void tick() {
      if (this.mob.getTarget() != null) {
         super.tick();
      }
   }
}
