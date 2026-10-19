package com.faboslav.friendsandfoes.common.entity.ai.goal.mauler;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public final class MaulerWanderAroundFarGoal extends WaterAvoidingRandomStrollGoal {
   public MaulerWanderAroundFarGoal(PathfinderMob pathAwareEntity, double d) {
      super(pathAwareEntity, d);
   }

   public boolean canUse() {
      return ((MaulerEntity)this.mob).isBurrowedDown() ? false : super.canUse();
   }
}
