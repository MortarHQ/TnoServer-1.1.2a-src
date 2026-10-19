package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DeathWormEntity;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class DeathWormAIWanderGoal extends WaterAvoidingRandomStrollGoal {
   private final DeathWormEntity worm;

   public DeathWormAIWanderGoal(DeathWormEntity creatureIn, double speedIn) {
      super(creatureIn, speedIn);
      this.worm = creatureIn;
   }

   public boolean canUse() {
      return !this.worm.isInSand() && !this.worm.isVehicle() && super.canUse();
   }

   public boolean canContinueToUse() {
      return !this.worm.isInSand() && !this.worm.isVehicle() && super.canContinueToUse();
   }
}
